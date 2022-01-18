// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.timeout;

import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ScheduledFuture;
import io.netty.channel.ChannelStateHandlerAdapter;

public class ReadTimeoutHandler extends ChannelStateHandlerAdapter
{
    private final long timeoutMillis;
    private volatile ScheduledFuture<?> timeout;
    private volatile long lastReadTime;
    private volatile int state;
    private boolean closed;
    
    public ReadTimeoutHandler(final int timeoutSeconds) {
        this(timeoutSeconds, TimeUnit.SECONDS);
    }
    
    public ReadTimeoutHandler(final long timeout, final TimeUnit unit) {
        if (unit == null) {
            throw new NullPointerException("unit");
        }
        if (timeout <= 0L) {
            this.timeoutMillis = 0L;
        }
        else {
            this.timeoutMillis = Math.max(unit.toMillis(timeout), 1L);
        }
    }
    
    @Override
    public void beforeAdd(final ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel().isActive() && ctx.channel().isRegistered()) {
            this.initialize(ctx);
        }
    }
    
    @Override
    public void beforeRemove(final ChannelHandlerContext ctx) throws Exception {
        this.destroy();
    }
    
    @Override
    public void channelRegistered(final ChannelHandlerContext ctx) throws Exception {
        if (ctx.channel().isActive()) {
            this.initialize(ctx);
        }
        super.channelRegistered(ctx);
    }
    
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        this.initialize(ctx);
        super.channelActive(ctx);
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        this.destroy();
        super.channelInactive(ctx);
    }
    
    @Override
    public void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
        this.lastReadTime = System.currentTimeMillis();
        ctx.fireInboundBufferUpdated();
    }
    
    private void initialize(final ChannelHandlerContext ctx) {
        switch (this.state) {
            case 1:
            case 2: {}
            default: {
                this.state = 1;
                this.lastReadTime = System.currentTimeMillis();
                if (this.timeoutMillis > 0L) {
                    this.timeout = ctx.executor().schedule((Runnable)new ReadTimeoutTask(ctx), this.timeoutMillis, TimeUnit.MILLISECONDS);
                }
            }
        }
    }
    
    private void destroy() {
        this.state = 2;
        if (this.timeout != null) {
            this.timeout.cancel(false);
            this.timeout = null;
        }
    }
    
    protected void readTimedOut(final ChannelHandlerContext ctx) throws Exception {
        if (!this.closed) {
            ctx.fireExceptionCaught((Throwable)ReadTimeoutException.INSTANCE);
            ctx.close();
            this.closed = true;
        }
    }
    
    private final class ReadTimeoutTask implements Runnable
    {
        private final ChannelHandlerContext ctx;
        
        ReadTimeoutTask(final ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }
        
        @Override
        public void run() {
            if (!this.ctx.channel().isOpen()) {
                return;
            }
            final long currentTime = System.currentTimeMillis();
            final long nextDelay = ReadTimeoutHandler.this.timeoutMillis - (currentTime - ReadTimeoutHandler.this.lastReadTime);
            if (nextDelay <= 0L) {
                ReadTimeoutHandler.this.timeout = this.ctx.executor().schedule((Runnable)this, ReadTimeoutHandler.this.timeoutMillis, TimeUnit.MILLISECONDS);
                try {
                    ReadTimeoutHandler.this.readTimedOut(this.ctx);
                }
                catch (Throwable t) {
                    this.ctx.fireExceptionCaught(t);
                }
            }
            else {
                ReadTimeoutHandler.this.timeout = this.ctx.executor().schedule((Runnable)this, nextDelay, TimeUnit.MILLISECONDS);
            }
        }
    }
}
