// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutorService;
import com.google.common.collect.ForwardingObject;

public abstract class ForwardingExecutorService extends ForwardingObject implements ExecutorService
{
    protected abstract ExecutorService delegate();
    
    public boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
        return this.delegate().awaitTermination(timeout, unit);
    }
    
    public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return this.delegate().invokeAll(tasks);
    }
    
    public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks, final long timeout, final TimeUnit unit) throws InterruptedException {
        return this.delegate().invokeAll(tasks, timeout, unit);
    }
    
    public <T> T invokeAny(final Collection<? extends Callable<T>> tasks) throws InterruptedException, ExecutionException {
        return this.delegate().invokeAny(tasks);
    }
    
    public <T> T invokeAny(final Collection<? extends Callable<T>> tasks, final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.delegate().invokeAny(tasks, timeout, unit);
    }
    
    public boolean isShutdown() {
        return this.delegate().isShutdown();
    }
    
    public boolean isTerminated() {
        return this.delegate().isTerminated();
    }
    
    public void shutdown() {
        this.delegate().shutdown();
    }
    
    public List<Runnable> shutdownNow() {
        return this.delegate().shutdownNow();
    }
    
    public void execute(final Runnable command) {
        this.delegate().execute(command);
    }
    
    public <T> Future<T> submit(final Callable<T> task) {
        return this.delegate().submit(task);
    }
    
    public Future<?> submit(final Runnable task) {
        return this.delegate().submit(task);
    }
    
    public <T> Future<T> submit(final Runnable task, final T result) {
        return this.delegate().submit(task, result);
    }
}
