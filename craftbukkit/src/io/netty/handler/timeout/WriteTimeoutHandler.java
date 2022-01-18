// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.timeout;

import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.Future;
import io.netty.channel.ChannelFuture;
import java.util.concurrent.ScheduledFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.FileRegion;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelHandlerContext;
import java.util.concurrent.TimeUnit;
import io.netty.channel.ChannelOperationHandlerAdapter;

public class WriteTimeoutHandler extends ChannelOperationHandlerAdapter
{
    private final long timeoutMillis;
    private boolean closed;
    
    public WriteTimeoutHandler(final int timeoutSeconds) {
        this(timeoutSeconds, TimeUnit.SECONDS);
    }
    
    public WriteTimeoutHandler(final long timeout, final TimeUnit unit) {
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
    public void flush(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        this.scheduleTimeout(ctx, promise);
        ctx.flush(promise);
    }
    
    @Override
    public void sendFile(final ChannelHandlerContext ctx, final FileRegion region, final ChannelPromise promise) throws Exception {
        this.scheduleTimeout(ctx, promise);
        super.sendFile(ctx, region, promise);
    }
    
    private void scheduleTimeout(final ChannelHandlerContext ctx, final ChannelPromise future) {
        if (this.timeoutMillis > 0L) {
            final ScheduledFuture<?> sf = ctx.executor().schedule((Runnable)new Runnable() {
                @Override
                public void run() {
                    if (future.tryFailure(WriteTimeoutException.INSTANCE)) {
                        try {
                            WriteTimeoutHandler.this.writeTimedOut(ctx);
                        }
                        catch (Throwable t) {
                            ctx.fireExceptionCaught(t);
                        }
                    }
                }
            }, this.timeoutMillis, TimeUnit.MILLISECONDS);
            future.addListener((GenericFutureListener<? extends Future<Void>>)new ChannelFutureListener() {
                @Override
                public void operationComplete(final ChannelFuture future) throws Exception {
                    sf.cancel(false);
                }
            });
        }
    }
    
    protected void writeTimedOut(final ChannelHandlerContext ctx) throws Exception {
        if (!this.closed) {
            ctx.fireExceptionCaught((Throwable)WriteTimeoutException.INSTANCE);
            ctx.close();
            this.closed = true;
        }
    }
}
