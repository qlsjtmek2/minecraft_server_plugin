// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.aio;

import io.netty.channel.EventLoop;
import java.nio.channels.CompletionHandler;
import io.netty.channel.Channel;

public abstract class AioCompletionHandler<V, A extends Channel> implements CompletionHandler<V, A>
{
    private static final int MAX_STACK_DEPTH = 8;
    private static final ThreadLocal<Integer> STACK_DEPTH;
    
    protected abstract void completed0(final V p0, final A p1);
    
    protected abstract void failed0(final Throwable p0, final A p1);
    
    @Override
    public final void completed(final V result, final A channel) {
        final EventLoop loop = channel.eventLoop();
        if (loop.inEventLoop()) {
            final Integer d = AioCompletionHandler.STACK_DEPTH.get();
            if (d < 8) {
                AioCompletionHandler.STACK_DEPTH.set(d + 1);
                try {
                    this.completed0(result, channel);
                }
                finally {
                    AioCompletionHandler.STACK_DEPTH.set(d);
                }
            }
            else {
                loop.execute(new AioEventLoop.RecursionBreakingRunnable() {
                    @Override
                    public void run() {
                        AioCompletionHandler.this.completed0(result, channel);
                    }
                });
            }
        }
        else {
            loop.execute(new Runnable() {
                @Override
                public void run() {
                    AioCompletionHandler.this.completed0(result, channel);
                }
            });
        }
    }
    
    @Override
    public final void failed(final Throwable exc, final A channel) {
        final EventLoop loop = channel.eventLoop();
        if (loop.inEventLoop()) {
            final Integer d = AioCompletionHandler.STACK_DEPTH.get();
            if (d < 8) {
                AioCompletionHandler.STACK_DEPTH.set(d + 1);
                try {
                    this.failed0(exc, channel);
                }
                finally {
                    AioCompletionHandler.STACK_DEPTH.set(d);
                }
            }
            else {
                loop.execute(new AioEventLoop.RecursionBreakingRunnable() {
                    @Override
                    public void run() {
                        AioCompletionHandler.this.failed0(exc, channel);
                    }
                });
            }
        }
        else {
            loop.execute(new Runnable() {
                @Override
                public void run() {
                    AioCompletionHandler.this.failed0(exc, channel);
                }
            });
        }
    }
    
    static {
        STACK_DEPTH = new ThreadLocal<Integer>() {
            @Override
            protected Integer initialValue() {
                return 0;
            }
        };
    }
}
