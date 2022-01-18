// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.Callable;
import java.util.concurrent.AbstractExecutorService;

public abstract class AbstractEventExecutor extends AbstractExecutorService implements EventExecutor
{
    @Override
    public EventExecutor next() {
        return this;
    }
    
    @Override
    public <V> Promise<V> newPromise() {
        return new DefaultPromise<V>(this);
    }
    
    @Override
    public <V> Future<V> newSucceededFuture(final V result) {
        return new SucceededFuture<V>(this, result);
    }
    
    @Override
    public <V> Future<V> newFailedFuture(final Throwable cause) {
        return new FailedFuture<V>(this, cause);
    }
    
    @Override
    public Future<?> submit(final Runnable task) {
        return (Future<?>)(Future)super.submit(task);
    }
    
    @Override
    public <T> Future<T> submit(final Runnable task, final T result) {
        return (Future<T>)(Future)super.submit(task, result);
    }
    
    @Override
    public <T> Future<T> submit(final Callable<T> task) {
        return (Future<T>)(Future)super.submit(task);
    }
    
    @Override
    protected final <T> RunnableFuture<T> newTaskFor(final Runnable runnable, final T value) {
        return new PromiseTask<T>(this, runnable, value);
    }
    
    @Override
    protected final <T> RunnableFuture<T> newTaskFor(final Callable<T> callable) {
        return new PromiseTask<T>(this, callable);
    }
    
    @Override
    public ScheduledFuture<?> schedule(final Runnable command, final long delay, final TimeUnit unit) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public <V> ScheduledFuture<V> schedule(final Callable<V> callable, final long delay, final TimeUnit unit) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(final Runnable command, final long initialDelay, final long period, final TimeUnit unit) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(final Runnable command, final long initialDelay, final long delay, final TimeUnit unit) {
        throw new UnsupportedOperationException();
    }
}
