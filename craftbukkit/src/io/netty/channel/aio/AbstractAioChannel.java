// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.aio;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;
import io.netty.channel.ConnectTimeoutException;
import io.netty.channel.EventLoop;
import io.netty.channel.Channel;
import java.net.SocketAddress;
import java.util.concurrent.ScheduledFuture;
import io.netty.channel.ChannelPromise;
import java.nio.channels.AsynchronousChannel;
import io.netty.channel.AbstractChannel;

public abstract class AbstractAioChannel extends AbstractChannel
{
    protected volatile AsynchronousChannel ch;
    protected ChannelPromise connectPromise;
    protected ScheduledFuture<?> connectTimeoutFuture;
    private SocketAddress requestedRemoteAddress;
    
    protected AbstractAioChannel(final Channel parent, final Integer id, final AsynchronousChannel ch) {
        super(parent, id);
        this.ch = ch;
    }
    
    protected AsynchronousChannel javaChannel() {
        if (this.ch == null) {
            throw new IllegalStateException("Try to access Channel before eventLoop was registered");
        }
        return this.ch;
    }
    
    @Override
    public boolean isOpen() {
        return this.ch == null || this.ch.isOpen();
    }
    
    @Override
    protected boolean isCompatible(final EventLoop loop) {
        return loop instanceof AioEventLoop;
    }
    
    @Override
    protected AbstractUnsafe newUnsafe() {
        return new DefaultAioUnsafe();
    }
    
    protected abstract void doConnect(final SocketAddress p0, final SocketAddress p1, final ChannelPromise p2);
    
    protected final class DefaultAioUnsafe extends AbstractUnsafe
    {
        @Override
        public void connect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
            if (AbstractAioChannel.this.eventLoop().inEventLoop()) {
                if (!this.ensureOpen(promise)) {
                    return;
                }
                try {
                    if (AbstractAioChannel.this.connectPromise != null) {
                        throw new IllegalStateException("connection attempt already made");
                    }
                    AbstractAioChannel.this.connectPromise = promise;
                    AbstractAioChannel.this.requestedRemoteAddress = remoteAddress;
                    AbstractAioChannel.this.doConnect(remoteAddress, localAddress, promise);
                    final int connectTimeoutMillis = AbstractAioChannel.this.config().getConnectTimeoutMillis();
                    if (connectTimeoutMillis > 0) {
                        AbstractAioChannel.this.connectTimeoutFuture = AbstractAioChannel.this.eventLoop().schedule((Runnable)new Runnable() {
                            @Override
                            public void run() {
                                final ChannelPromise connectFuture = AbstractAioChannel.this.connectPromise;
                                final ConnectTimeoutException cause = new ConnectTimeoutException("connection timed out: " + remoteAddress);
                                if (connectFuture != null && connectFuture.tryFailure(cause)) {
                                    DefaultAioUnsafe.this.close(DefaultAioUnsafe.this.voidFuture());
                                }
                            }
                        }, (long)connectTimeoutMillis, TimeUnit.MILLISECONDS);
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
                AbstractAioChannel.this.eventLoop().execute(new Runnable() {
                    @Override
                    public void run() {
                        DefaultAioUnsafe.this.connect(remoteAddress, localAddress, promise);
                    }
                });
            }
        }
        
        public void connectFailed(Throwable t) {
            if (t instanceof ConnectException) {
                final Throwable newT = new ConnectException(t.getMessage() + ": " + AbstractAioChannel.this.requestedRemoteAddress);
                newT.setStackTrace(t.getStackTrace());
                t = newT;
            }
            AbstractAioChannel.this.connectPromise.setFailure(t);
            this.closeIfClosed();
        }
        
        public void connectSuccess() {
            assert AbstractAioChannel.this.eventLoop().inEventLoop();
            assert AbstractAioChannel.this.connectPromise != null;
            try {
                AbstractAioChannel.this.connectPromise.setSuccess();
                AbstractAioChannel.this.pipeline().fireChannelActive();
            }
            catch (Throwable t) {
                AbstractAioChannel.this.connectPromise.setFailure(t);
                this.closeIfClosed();
            }
            finally {
                AbstractAioChannel.this.connectTimeoutFuture.cancel(false);
                AbstractAioChannel.this.connectPromise = null;
            }
        }
    }
}
