// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import com.google.common.collect.ForwardingObject;

public abstract class ForwardingFuture<V> extends ForwardingObject implements Future<V>
{
    protected abstract Future<V> delegate();
    
    public boolean cancel(final boolean mayInterruptIfRunning) {
        return this.delegate().cancel(mayInterruptIfRunning);
    }
    
    public boolean isCancelled() {
        return this.delegate().isCancelled();
    }
    
    public boolean isDone() {
        return this.delegate().isDone();
    }
    
    public V get() throws InterruptedException, ExecutionException {
        return this.delegate().get();
    }
    
    public V get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return this.delegate().get(timeout, unit);
    }
    
    public abstract static class SimpleForwardingFuture<V> extends ForwardingFuture<V>
    {
        private final Future<V> delegate;
        
        protected SimpleForwardingFuture(final Future<V> delegate) {
            this.delegate = Preconditions.checkNotNull(delegate);
        }
        
        protected final Future<V> delegate() {
            return this.delegate;
        }
    }
}
