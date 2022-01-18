// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.local;

import io.netty.channel.ChannelException;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.AlreadyConnectedException;
import io.netty.buffer.BufType;
import java.util.Collections;
import java.util.Collection;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.NotYetConnectedException;
import io.netty.buffer.MessageBuf;
import io.netty.channel.ChannelPipeline;
import io.netty.util.concurrent.SingleThreadEventExecutor;
import java.net.SocketAddress;
import io.netty.channel.SingleThreadEventLoop;
import io.netty.channel.EventLoop;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.Channel;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.AbstractChannel;

public class LocalChannel extends AbstractChannel
{
    private static final ChannelMetadata METADATA;
    private final ChannelConfig config;
    private final Runnable shutdownHook;
    private volatile int state;
    private volatile LocalChannel peer;
    private volatile LocalAddress localAddress;
    private volatile LocalAddress remoteAddress;
    private volatile ChannelPromise connectPromise;
    private volatile boolean readInProgress;
    
    public LocalChannel() {
        this(null);
    }
    
    public LocalChannel(final Integer id) {
        super(null, id);
        this.config = new DefaultChannelConfig(this);
        this.shutdownHook = new Runnable() {
            @Override
            public void run() {
                LocalChannel.this.unsafe().close(LocalChannel.this.unsafe().voidFuture());
            }
        };
    }
    
    LocalChannel(final LocalServerChannel parent, final LocalChannel peer) {
        super(parent, null);
        this.config = new DefaultChannelConfig(this);
        this.shutdownHook = new Runnable() {
            @Override
            public void run() {
                LocalChannel.this.unsafe().close(LocalChannel.this.unsafe().voidFuture());
            }
        };
        this.peer = peer;
        this.localAddress = parent.localAddress();
        this.remoteAddress = peer.localAddress();
    }
    
    @Override
    public ChannelMetadata metadata() {
        return LocalChannel.METADATA;
    }
    
    @Override
    public ChannelConfig config() {
        return this.config;
    }
    
    @Override
    public LocalServerChannel parent() {
        return (LocalServerChannel)super.parent();
    }
    
    @Override
    public LocalAddress localAddress() {
        return (LocalAddress)super.localAddress();
    }
    
    @Override
    public LocalAddress remoteAddress() {
        return (LocalAddress)super.remoteAddress();
    }
    
    @Override
    public boolean isOpen() {
        return this.state < 3;
    }
    
    @Override
    public boolean isActive() {
        return this.state == 2;
    }
    
    @Override
    protected AbstractUnsafe newUnsafe() {
        return new LocalUnsafe();
    }
    
    @Override
    protected boolean isCompatible(final EventLoop loop) {
        return loop instanceof SingleThreadEventLoop;
    }
    
    @Override
    protected SocketAddress localAddress0() {
        return this.localAddress;
    }
    
    @Override
    protected SocketAddress remoteAddress0() {
        return this.remoteAddress;
    }
    
    @Override
    protected Runnable doRegister() throws Exception {
        final LocalChannel peer = this.peer;
        Runnable postRegisterTask;
        if (peer != null) {
            this.state = 2;
            peer.remoteAddress = this.parent().localAddress();
            peer.state = 2;
            final EventLoop peerEventLoop = peer.eventLoop();
            postRegisterTask = new Runnable() {
                @Override
                public void run() {
                    peerEventLoop.execute(new Runnable() {
                        @Override
                        public void run() {
                            peer.connectPromise.setSuccess();
                            peer.pipeline().fireChannelActive();
                        }
                    });
                }
            };
        }
        else {
            postRegisterTask = null;
        }
        ((SingleThreadEventExecutor)this.eventLoop()).addShutdownHook(this.shutdownHook);
        return postRegisterTask;
    }
    
    @Override
    protected void doBind(final SocketAddress localAddress) throws Exception {
        this.localAddress = LocalChannelRegistry.register(this, this.localAddress, localAddress);
        this.state = 1;
    }
    
    @Override
    protected void doDisconnect() throws Exception {
        this.doClose();
    }
    
    @Override
    protected void doPreClose() throws Exception {
        if (this.state > 2) {
            return;
        }
        if (this.parent() == null) {
            LocalChannelRegistry.unregister(this.localAddress);
        }
        this.localAddress = null;
        this.state = 3;
    }
    
    @Override
    protected void doClose() throws Exception {
        final LocalChannel peer = this.peer;
        if (peer != null && peer.isActive()) {
            peer.unsafe().close(peer.unsafe().voidFuture());
            this.peer = null;
        }
    }
    
    @Override
    protected Runnable doDeregister() throws Exception {
        if (this.isOpen()) {
            this.unsafe().close(this.unsafe().voidFuture());
        }
        ((SingleThreadEventExecutor)this.eventLoop()).removeShutdownHook(this.shutdownHook);
        return null;
    }
    
    @Override
    protected void doBeginRead() throws Exception {
        if (this.readInProgress) {
            return;
        }
        final ChannelPipeline pipeline = this.pipeline();
        final MessageBuf<Object> buf = pipeline.inboundMessageBuffer();
        if (buf.isEmpty()) {
            this.readInProgress = true;
            return;
        }
        pipeline.fireInboundBufferUpdated();
        pipeline.fireChannelReadSuspended();
    }
    
    @Override
    protected void doFlushMessageBuffer(final MessageBuf<Object> buf) throws Exception {
        if (this.state < 2) {
            throw new NotYetConnectedException();
        }
        if (this.state > 2) {
            throw new ClosedChannelException();
        }
        final LocalChannel peer = this.peer;
        final ChannelPipeline peerPipeline = peer.pipeline();
        final EventLoop peerLoop = peer.eventLoop();
        if (peerLoop == this.eventLoop()) {
            buf.drainTo(peerPipeline.inboundMessageBuffer());
            finishPeerRead(peer, peerPipeline);
        }
        else {
            final Object[] msgs = buf.toArray();
            buf.clear();
            peerLoop.execute(new Runnable() {
                @Override
                public void run() {
                    final MessageBuf<Object> buf = peerPipeline.inboundMessageBuffer();
                    Collections.addAll(buf, msgs);
                    finishPeerRead(peer, peerPipeline);
                }
            });
        }
    }
    
    private static void finishPeerRead(final LocalChannel peer, final ChannelPipeline peerPipeline) {
        if (peer.readInProgress) {
            peer.readInProgress = false;
            peerPipeline.fireInboundBufferUpdated();
            peerPipeline.fireChannelReadSuspended();
        }
    }
    
    @Override
    protected boolean isFlushPending() {
        return false;
    }
    
    static {
        METADATA = new ChannelMetadata(BufType.MESSAGE, false);
    }
    
    private class LocalUnsafe extends AbstractUnsafe
    {
        @Override
        public void connect(final SocketAddress remoteAddress, SocketAddress localAddress, final ChannelPromise promise) {
            if (LocalChannel.this.eventLoop().inEventLoop()) {
                if (!this.ensureOpen(promise)) {
                    return;
                }
                if (LocalChannel.this.state == 2) {
                    final Exception cause = new AlreadyConnectedException();
                    promise.setFailure((Throwable)cause);
                    LocalChannel.this.pipeline().fireExceptionCaught((Throwable)cause);
                    return;
                }
                if (LocalChannel.this.connectPromise != null) {
                    throw new ConnectionPendingException();
                }
                LocalChannel.this.connectPromise = promise;
                if (LocalChannel.this.state != 1 && localAddress == null) {
                    localAddress = new LocalAddress(LocalChannel.this);
                }
                if (localAddress != null) {
                    try {
                        LocalChannel.this.doBind(localAddress);
                    }
                    catch (Throwable t) {
                        promise.setFailure(t);
                        LocalChannel.this.pipeline().fireExceptionCaught(t);
                        this.close(this.voidFuture());
                        return;
                    }
                }
                final Channel boundChannel = LocalChannelRegistry.get(remoteAddress);
                if (!(boundChannel instanceof LocalServerChannel)) {
                    final Exception cause2 = new ChannelException("connection refused");
                    promise.setFailure((Throwable)cause2);
                    this.close(this.voidFuture());
                    return;
                }
                final LocalServerChannel serverChannel = (LocalServerChannel)boundChannel;
                LocalChannel.this.peer = serverChannel.serve(LocalChannel.this);
            }
            else {
                final SocketAddress localAddress2 = localAddress;
                LocalChannel.this.eventLoop().execute(new Runnable() {
                    @Override
                    public void run() {
                        LocalUnsafe.this.connect(remoteAddress, localAddress2, promise);
                    }
                });
            }
        }
    }
}
