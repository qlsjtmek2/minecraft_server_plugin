// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.concurrent;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

public abstract class AbstractFuture<V> implements Future<V>
{
    @Override
    public boolean cancel(final boolean mayInterruptIfRunning) {
        return false;
    }
    
    @Override
    public boolean isCancelled() {
        return false;
    }
    
    @Override
    public V get() throws InterruptedException, ExecutionException {
        this.await();
        final Throwable cause = this.cause();
        if (cause == null) {
            return this.getNow();
        }
        throw new ExecutionException(cause);
    }
    
    @Override
    public V get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        if (!this.await(timeout, unit)) {
            throw new TimeoutException();
        }
        final Throwable cause = this.cause();
        if (cause == null) {
            return this.getNow();
        }
        throw new ExecutionException(cause);
    }
}
