// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.Collection;
import java.util.Map;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class MultithreadEventExecutorGroup implements EventExecutorGroup
{
    public static final int DEFAULT_POOL_SIZE;
    private static final AtomicInteger poolId;
    private final EventExecutor[] children;
    private final AtomicInteger childIndex;
    
    protected MultithreadEventExecutorGroup(int nThreads, ThreadFactory threadFactory, final Object... args) {
        this.childIndex = new AtomicInteger();
        if (nThreads < 0) {
            throw new IllegalArgumentException(String.format("nThreads: %d (expected: >= 0)", nThreads));
        }
        if (nThreads == 0) {
            nThreads = MultithreadEventExecutorGroup.DEFAULT_POOL_SIZE;
        }
        if (threadFactory == null) {
            threadFactory = new DefaultThreadFactory();
        }
        this.children = new SingleThreadEventExecutor[nThreads];
        for (int i = 0; i < nThreads; ++i) {
            boolean success = false;
            try {
                this.children[i] = this.newChild(threadFactory, args);
                success = true;
            }
            catch (Exception e) {
                throw new IllegalStateException("failed to create a child event loop", e);
            }
            finally {
                if (!success) {
                    for (int j = 0; j < i; ++j) {
                        this.children[j].shutdown();
                    }
                }
            }
        }
    }
    
    @Override
    public EventExecutor next() {
        return this.children[Math.abs(this.childIndex.getAndIncrement() % this.children.length)];
    }
    
    protected Set<EventExecutor> children() {
        final Set<EventExecutor> children = Collections.newSetFromMap(new LinkedHashMap<EventExecutor, Boolean>());
        Collections.addAll(children, this.children);
        return children;
    }
    
    protected abstract EventExecutor newChild(final ThreadFactory p0, final Object... p1) throws Exception;
    
    @Override
    public void shutdown() {
        if (this.isShutdown()) {
            return;
        }
        for (final EventExecutor l : this.children) {
            l.shutdown();
        }
    }
    
    @Override
    public boolean isShutdown() {
        for (final EventExecutor l : this.children) {
            if (!l.isShutdown()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean isTerminated() {
        for (final EventExecutor l : this.children) {
            if (!l.isTerminated()) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
        final long deadline = System.nanoTime() + unit.toNanos(timeout);
    Label_0084:
        for (final EventExecutor l : this.children) {
            long timeLeft;
            do {
                timeLeft = deadline - System.nanoTime();
                if (timeLeft <= 0L) {
                    break Label_0084;
                }
            } while (!l.awaitTermination(timeLeft, TimeUnit.NANOSECONDS));
        }
        return this.isTerminated();
    }
    
    static {
        DEFAULT_POOL_SIZE = Runtime.getRuntime().availableProcessors() * 2;
        poolId = new AtomicInteger();
    }
    
    private final class DefaultThreadFactory implements ThreadFactory
    {
        private final AtomicInteger nextId;
        private final String prefix;
        
        DefaultThreadFactory() {
            this.nextId = new AtomicInteger();
            String typeName = MultithreadEventExecutorGroup.this.getClass().getSimpleName();
            typeName = Character.toLowerCase(typeName.charAt(0)) + typeName.substring(1);
            this.prefix = typeName + '-' + MultithreadEventExecutorGroup.poolId.incrementAndGet() + '-';
        }
        
        @Override
        public Thread newThread(final Runnable r) {
            final Thread t = new Thread(r, this.prefix + this.nextId.incrementAndGet());
            try {
                if (t.isDaemon()) {
                    t.setDaemon(false);
                }
                if (t.getPriority() != 10) {
                    t.setPriority(10);
                }
            }
            catch (Exception ex) {}
            return t;
        }
    }
}
