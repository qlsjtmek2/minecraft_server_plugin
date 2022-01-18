// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.oio;

import java.net.ConnectException;
import io.netty.channel.ChannelPromise;
import java.net.SocketAddress;
import io.netty.channel.ThreadPerChannelEventLoop;
import io.netty.channel.EventLoop;
import io.netty.channel.Channel;
import io.netty.channel.AbstractChannel;

public abstract class AbstractOioChannel extends AbstractChannel
{
    protected static final int SO_TIMEOUT = 1000;
    private boolean readInProgress;
    private final Runnable readTask;
    
    protected AbstractOioChannel(final Channel parent, final Integer id) {
        super(parent, id);
        this.readTask = new Runnable() {
            @Override
            public void run() {
                AbstractOioChannel.this.readInProgress = false;
                AbstractOioChannel.this.doRead();
            }
        };
    }
    
    @Override
    protected AbstractUnsafe newUnsafe() {
        return new DefaultOioUnsafe();
    }
    
    @Override
    protected boolean isCompatible(final EventLoop loop) {
        return loop instanceof ThreadPerChannelEventLoop;
    }
    
    @Override
    protected boolean isFlushPending() {
        return false;
    }
    
    protected abstract void doConnect(final SocketAddress p0, final SocketAddress p1) throws Exception;
    
    @Override
    protected void doBeginRead() throws Exception {
        if (this.readInProgress) {
            return;
        }
        this.readInProgress = true;
        this.eventLoop().execute(this.readTask);
    }
    
    protected abstract void doRead();
    
    private final class DefaultOioUnsafe extends AbstractUnsafe
    {
        @Override
        public void connect(final SocketAddress remoteAddress, final SocketAddress localAddress, final ChannelPromise promise) {
            if (AbstractOioChannel.this.eventLoop().inEventLoop()) {
                if (!this.ensureOpen(promise)) {
                    return;
                }
                try {
                    final boolean wasActive = AbstractOioChannel.this.isActive();
                    AbstractOioChannel.this.doConnect(remoteAddress, localAddress);
                    promise.setSuccess();
                    if (!wasActive && AbstractOioChannel.this.isActive()) {
                        AbstractOioChannel.this.pipeline().fireChannelActive();
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
                AbstractOioChannel.this.eventLoop().execute(new Runnable() {
                    @Override
                    public void run() {
                        DefaultOioUnsafe.this.connect(remoteAddress, localAddress, promise);
                    }
                });
            }
        }
    }
}
