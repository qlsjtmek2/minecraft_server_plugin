// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.util.concurrent.EventExecutor;
import java.util.concurrent.ThreadFactory;
import io.netty.util.concurrent.MultithreadEventExecutorGroup;

public abstract class MultithreadEventLoopGroup extends MultithreadEventExecutorGroup implements EventLoopGroup
{
    protected MultithreadEventLoopGroup(final int nThreads, final ThreadFactory threadFactory, final Object... args) {
        super(nThreads, threadFactory, args);
    }
    
    @Override
    public EventLoop next() {
        return (EventLoop)super.next();
    }
    
    @Override
    public ChannelFuture register(final Channel channel) {
        return this.next().register(channel);
    }
    
    @Override
    public ChannelFuture register(final Channel channel, final ChannelPromise promise) {
        return this.next().register(channel, promise);
    }
}
