// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.channel;

import io.netty.util.concurrent.EventExecutor;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.Map;
import java.util.Collections;
import io.netty.util.internal.PlatformDependent;
import java.util.concurrent.Executors;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ThreadFactory;

public class ThreadPerChannelEventLoopGroup implements EventLoopGroup
{
    private static final Object[] NO_ARGS;
    private static final StackTraceElement[] STACK_ELEMENTS;
    private final Object[] childArgs;
    private final int maxChannels;
    final ThreadFactory threadFactory;
    final Set<ThreadPerChannelEventLoop> activeChildren;
    final Queue<ThreadPerChannelEventLoop> idleChildren;
    private final ChannelException tooManyChannels;
    
    protected ThreadPerChannelEventLoopGroup() {
        this(0);
    }
    
    protected ThreadPerChannelEventLoopGroup(final int maxChannels) {
        this(maxChannels, Executors.defaultThreadFactory(), new Object[0]);
    }
    
    protected ThreadPerChannelEventLoopGroup(final int maxChannels, final ThreadFactory threadFactory, final Object... args) {
        this.activeChildren = Collections.newSetFromMap((Map<ThreadPerChannelEventLoop, Boolean>)PlatformDependent.newConcurrentHashMap());
        this.idleChildren = new ConcurrentLinkedQueue<ThreadPerChannelEventLoop>();
        if (maxChannels < 0) {
            throw new IllegalArgumentException(String.format("maxChannels: %d (expected: >= 0)", maxChannels));
        }
        if (threadFactory == null) {
            throw new NullPointerException("threadFactory");
        }
        if (args == null) {
            this.childArgs = ThreadPerChannelEventLoopGroup.NO_ARGS;
        }
        else {
            this.childArgs = args.clone();
        }
        this.maxChannels = maxChannels;
        this.threadFactory = threadFactory;
        (this.tooManyChannels = new ChannelException("too many channels (max: " + maxChannels + ')')).setStackTrace(ThreadPerChannelEventLoopGroup.STACK_ELEMENTS);
    }
    
    protected ThreadPerChannelEventLoop newChild(final Object... args) throws Exception {
        return new ThreadPerChannelEventLoop(this);
    }
    
    @Override
    public EventLoop next() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public void shutdown() {
        for (final EventLoop l : this.activeChildren) {
            l.shutdown();
        }
        for (final EventLoop l : this.idleChildren) {
            l.shutdown();
        }
    }
    
    @Override
    public boolean isShutdown() {
        for (final EventLoop l : this.activeChildren) {
            if (!l.isShutdown()) {
                return false;
            }
        }
        for (final EventLoop l : this.idleChildren) {
            if (!l.isShutdown()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean isTerminated() {
        for (final EventLoop l : this.activeChildren) {
            if (!l.isTerminated()) {
                return false;
            }
        }
        for (final EventLoop l : this.idleChildren) {
            if (!l.isTerminated()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
        final long deadline = System.nanoTime() + unit.toNanos(timeout);
        for (final EventLoop l : this.activeChildren) {
            long timeLeft;
            do {
                timeLeft = deadline - System.nanoTime();
                if (timeLeft <= 0L) {
                    return this.isTerminated();
                }
            } while (!l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS));
        }
        for (final EventLoop l : this.idleChildren) {
            long timeLeft;
            do {
                timeLeft = deadline - System.nanoTime();
                if (timeLeft <= 0L) {
                    return this.isTerminated();
                }
            } while (!l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS));
        }
        return this.isTerminated();
    }
    
    @Override
    public ChannelFuture register(final Channel channel) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        try {
            return this.nextChild().register(channel);
        }
        catch (Throwable t) {
            return channel.newFailedFuture(t);
        }
    }
    
    @Override
    public ChannelFuture register(final Channel channel, final ChannelPromise promise) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        try {
            return this.nextChild().register(channel, promise);
        }
        catch (Throwable t) {
            promise.setFailure(t);
            return promise;
        }
    }
    
    private EventLoop nextChild() throws Exception {
        ThreadPerChannelEventLoop loop = this.idleChildren.poll();
        if (loop == null) {
            if (this.maxChannels > 0 && this.activeChildren.size() >= this.maxChannels) {
                throw this.tooManyChannels;
            }
            loop = this.newChild(this.childArgs);
        }
        this.activeChildren.add(loop);
        return loop;
    }
    
    static {
        NO_ARGS = new Object[0];
        STACK_ELEMENTS = new StackTraceElement[0];
    }
}
