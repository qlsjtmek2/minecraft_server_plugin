// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel.aio;

import java.util.Queue;
import java.util.Iterator;
import java.util.Collection;
import java.util.ArrayList;
import io.netty.channel.ChannelPromise;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.channel.ChannelFuture;
import java.util.Map;
import java.util.Collections;
import java.util.IdentityHashMap;
import io.netty.channel.EventLoopGroup;
import java.util.concurrent.ThreadFactory;
import io.netty.channel.ChannelFutureListener;
import java.util.concurrent.LinkedBlockingDeque;
import io.netty.channel.Channel;
import java.util.Set;
import io.netty.channel.SingleThreadEventLoop;

final class AioEventLoop extends SingleThreadEventLoop
{
    private final Set<Channel> channels;
    private LinkedBlockingDeque<Runnable> taskQueue;
    private final ChannelFutureListener registrationListener;
    private final ChannelFutureListener deregistrationListener;
    
    AioEventLoop(final AioEventLoopGroup parent, final ThreadFactory threadFactory) {
        super(parent, threadFactory);
        this.channels = Collections.newSetFromMap(new IdentityHashMap<Channel, Boolean>());
        this.registrationListener = new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    return;
                }
                final Channel ch = future.channel();
                AioEventLoop.this.channels.add(ch);
                ch.closeFuture().addListener((GenericFutureListener<? extends Future<Void>>)AioEventLoop.this.deregistrationListener);
            }
        };
        this.deregistrationListener = new ChannelFutureListener() {
            @Override
            public void operationComplete(final ChannelFuture future) throws Exception {
                AioEventLoop.this.channels.remove(future.channel());
            }
        };
    }
    
    @Override
    public ChannelFuture register(final Channel channel) {
        return super.register(channel).addListener((GenericFutureListener<? extends Future<Void>>)this.registrationListener);
    }
    
    @Override
    public ChannelFuture register(final Channel channel, final ChannelPromise future) {
        return super.register(channel, future).addListener((GenericFutureListener<? extends Future<Void>>)this.registrationListener);
    }
    
    @Override
    protected void run() {
        while (true) {
            try {
                final Runnable task = this.takeTask();
                task.run();
            }
            catch (InterruptedException ex) {}
            if (this.isShutdown()) {
                this.closeAll();
                if (this.confirmShutdown()) {
                    break;
                }
                continue;
            }
        }
    }
    
    private void closeAll() {
        final Collection<Channel> channels = new ArrayList<Channel>(this.channels.size());
        for (final Channel ch : this.channels) {
            channels.add(ch);
        }
        for (final Channel ch : channels) {
            ch.unsafe().close(ch.unsafe().voidFuture());
        }
    }
    
    @Override
    protected Queue<Runnable> newTaskQueue() {
        return this.taskQueue = new LinkedBlockingDeque<Runnable>();
    }
    
    @Override
    protected void addTask(final Runnable task) {
        if (task instanceof RecursionBreakingRunnable) {
            if (task == null) {
                throw new NullPointerException("task");
            }
            if (this.isTerminated()) {
                reject();
            }
            this.taskQueue.addFirst(task);
        }
        else {
            super.addTask(task);
        }
    }
    
    interface RecursionBreakingRunnable extends Runnable
    {
    }
}
