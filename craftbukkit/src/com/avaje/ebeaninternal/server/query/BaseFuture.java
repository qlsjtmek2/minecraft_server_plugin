// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Future;

public abstract class BaseFuture<T> implements Future<T>
{
    private final FutureTask<T> futureTask;
    
    public BaseFuture(final FutureTask<T> futureTask) {
        this.futureTask = futureTask;
    }
    
    public boolean cancel(final boolean mayInterruptIfRunning) {
        return this.futureTask.cancel(mayInterruptIfRunning);
    }
    
    public T get() throws InterruptedException, ExecutionException {
        return this.futureTask.get();
    }
    
    public T get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.futureTask.get(timeout, unit);
    }
    
    public boolean isCancelled() {
        return this.futureTask.isCancelled();
    }
    
    public boolean isDone() {
        return this.futureTask.isDone();
    }
}
