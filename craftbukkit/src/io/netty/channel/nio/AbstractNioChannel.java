// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.nio;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;
import io.netty.channel.ConnectTimeoutException;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.nio.channels.CancelledKeyException;
import io.netty.channel.EventLoop;
import io.netty.channel.ChannelException;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import io.netty.channel.Channel;
import java.net.SocketAddress;
import java.util.concurrent.ScheduledFuture;
import io.netty.channel.ChannelPromise;
import java.util.Queue;
import java.nio.channels.SelectionKey;
import java.nio.channels.SelectableChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.channel.AbstractChannel;

public abstract class AbstractNioChannel extends AbstractChannel
{
    private static final InternalLogger logger;
    private final SelectableChannel ch;
    protected final int readInterestOp;
    private volatile SelectionKey selectionKey;
    private volatile boolean inputShutdown;
    final Queue<NioTask<SelectableChannel>> writableTasks;
    private ChannelPromise connectPromise;
    private ScheduledFuture<?> connectTimeoutFuture;
    private SocketAddress requestedRemoteAddress;
    
    protected AbstractNioChannel(final Channel parent, final Integer id, final SelectableChannel ch, final int readInterestOp) {
        super(parent, id);
        this.writableTasks = new ConcurrentLinkedQueue<NioTask<SelectableChannel>>();
        this.ch = ch;
        this.readInterestOp = readInterestOp;
        try {
            ch.configureBlocking(false);
        }
        catch (IOException e3) {
            try {
                ch.close();
            }
            catch (IOException e2) {
                if (AbstractNioChannel.logger.isWarnEnabled()) {
                    AbstractNioChannel.logger.warn("Failed to close a partially initialized socket.", e2);
                }
            }
            throw new ChannelException("Failed to enter non-blocking mode.", e3);
        }
    }
    
    @Override
    public boolean isOpen() {
        return this.ch.isOpen();
    }
    
    @Override
    public NioUnsafe unsafe() {
        return (NioUnsafe)super.unsafe();
    }
    
    protected SelectableChannel javaChannel() {
        return this.ch;
    }
    
    @Override
    public NioEventLoop eventLoop() {
        return (NioEventLoop)super.eventLoop();
    }
    
    protected SelectionKey selectionKey() {
        assert this.selectionKey != null;
        return this.selectionKey;
    }
    
    protected boolean isInputShutdown() {
        return this.inputShutdown;
    }
    
    void setInputShutdown() {
        this.inputShutdown = true;
    }
    
    @Override
    protected boolean isCompatible(final EventLoop loop) {
        return loop instanceof NioEventLoop;
    }
    
    @Override
    protected boolean isFlushPending() {
        final SelectionKey selectionKey = this.selectionKey;
        return selectionKey.isValid() && (selectionKey.interestOps() & 0x4) != 0x0;
    }
    
    @Override
    protected Runnable doRegister() throws Exception {
        boolean selected = false;
        while (true) {
            try {
                this.selectionKey = this.javaChannel().register(this.eventLoop().selector, 0, this);
                return null;
            }
            catch (CancelledKeyException e) {
                if (!selected) {
                    this.eventLoop().selectNow();
                    selected = true;
                    continue;
                }
                throw e;
            }
            break;
        }
    }
    
    @Override
    protected Runnable doDeregister() throws Exception {
        this.eventLoop().cancel(this.selectionKey());
        return null;
    }
    
    @Override
    protected void doBeginRead() throws Exception {
        if (this.inputShutdown) {
            return;
        }
        final SelectionKey selectionKey = this.selectionKey;
        if (!selectionKey.isValid()) {
            return;
        }
        final int interestOps = selectionKey.interestOps();
        if ((interestOps & this.readInterestOp) == 0x0) {
            selectionKey.interestOps(interestOps | this.readInterestOp);
        }
    }
    
    protected abstract boolean doConnect(final SocketAddress p0, final SocketAddress p1) throws Exception;
    
    protected abstract void doFinishConnect() throws Exception;
    
    static {
        logger = InternalLoggerFactory.getInstance(AbstractNioChannel.class);
    }
    
    protected abstract class AbstractNioUnsafe extends AbstractUnsafe implements NioUnsafe
    {
        @Override
        public SelectableChannel ch() {
            return AbstractNioChannel.this.javaChannel();
        }
        
        @Override
        public void connect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
            if (AbstractNioChannel.this.eventLoop().inEventLoop()) {
                if (!this.ensureOpen(promise)) {
                    return;
                }
                try {
                    if (AbstractNioChannel.this.connectPromise != null) {
                        throw new IllegalStateException("connection attempt already made");
                    }
                    final boolean wasActive = AbstractNioChannel.this.isActive();
                    if (AbstractNioChannel.this.doConnect(remoteAddress, localAddress)) {
                        promise.setSuccess();
                        if (!wasActive && AbstractNioChannel.this.isActive()) {
                            AbstractNioChannel.this.pipeline().fireChannelActive();
                        }
                    }
                    else {
                        AbstractNioChannel.this.connectPromise = promise;
                        AbstractNioChannel.this.requestedRemoteAddress = remoteAddress;
                        final int connectTimeoutMillis = AbstractNioChannel.this.config().getConnectTimeoutMillis();
                        if (connectTimeoutMillis > 0) {
                            AbstractNioChannel.this.connectTimeoutFuture = AbstractNioChannel.this.eventLoop().schedule(new Runnable() {
                                @Override
                                public void run() {
                                    final ChannelPromise connectPromise = AbstractNioChannel.this.connectPromise;
                                    final ConnectTimeoutException cause = new ConnectTimeoutException("connection timed out: " + remoteAddress);
                                    if (connectPromise != null && connectPromise.tryFailure(cause)) {
                                        AbstractNioUnsafe.this.close(AbstractNioUnsafe.this.voidFuture());
                                    }
                                }
                            }, connectTimeoutMillis, TimeUnit.MILLISECONDS);
                        }
                    }
                }
                catch (Throwable t) {
                    if (t instanceof ConnectException) {
                        final Throwable newT = new ConnectException(t.getMessage() + ": " + remoteAddress);
                        newT.setStackTrace(t.getStackTrace());
                        t = newT;
                    }
                    promise.setFailure(t);
                    this.closeIfClosed();
                }
            }
            else {
                AbstractNioChannel.this.eventLoop().execute(new Runnable() {
                    @Override
                    public void run() {
                        AbstractNioUnsafe.this.connect(remoteAddress, localAddress, promise);
                    }
                });
            }
        }
        
        @Override
        public void finishConnect() {
            assert AbstractNioChannel.this.eventLoop().inEventLoop();
            assert AbstractNioChannel.this.connectPromise != null;
            try {
                final boolean wasActive = AbstractNioChannel.this.isActive();
                AbstractNioChannel.this.doFinishConnect();
                AbstractNioChannel.this.connectPromise.setSuccess();
                if (!wasActive && AbstractNioChannel.this.isActive()) {
                    AbstractNioChannel.this.pipeline().fireChannelActive();
                }
            }
            catch (Throwable t) {
                if (t instanceof ConnectException) {
                    final Throwable newT = new ConnectException(t.getMessage() + ": " + AbstractNioChannel.this.requestedRemoteAddress);
                    newT.setStackTrace(t.getStackTrace());
                    t = newT;
                }
                AbstractNioChannel.this.connectPromise.setFailure(t);
                this.closeIfClosed();
            }
            finally {
                AbstractNioChannel.this.connectTimeoutFuture.cancel(false);
                AbstractNioChannel.this.connectPromise = null;
            }
        }
    }
    
    public interface NioUnsafe extends Channel.Unsafe
    {
        SelectableChannel ch();
        
        void finishConnect();
        
        void read();
    }
}
