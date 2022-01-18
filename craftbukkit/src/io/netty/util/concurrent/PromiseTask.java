// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;

class PromiseTask<V> extends DefaultPromise<V> implements RunnableFuture<V>
{
    protected final Callable<V> task;
    
    PromiseTask(final EventExecutor executor, final Runnable runnable, final V result) {
        this(executor, Executors.callable(runnable, result));
    }
    
    PromiseTask(final EventExecutor executor, final Callable<V> callable) {
        super(executor);
        this.task = callable;
    }
    
    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }
    
    @Override
    public boolean equals(final Object obj) {
        return this == obj;
    }
    
    @Override
    public void run() {
        try {
            final V result = this.task.call();
            this.setSuccessInternal(result);
        }
        catch (Throwable e) {
            this.setFailureInternal(e);
        }
    }
    
    @Override
    public Promise<V> setFailure(final Throwable cause) {
        throw new IllegalStateException();
    }
    
    protected final Promise<V> setFailureInternal(final Throwable cause) {
        super.setFailure(cause);
        return this;
    }
    
    @Override
    public boolean tryFailure(final Throwable cause) {
        return false;
    }
    
    protected final boolean tryFailureInternal(final Throwable cause) {
        return super.tryFailure(cause);
    }
    
    @Override
    public Promise<V> setSuccess(final V result) {
        throw new IllegalStateException();
    }
    
    protected final Promise<V> setSuccessInternal(final V result) {
        super.setSuccess(result);
        return this;
    }
    
    @Override
    public boolean trySuccess(final V result) {
        return false;
    }
    
    protected final boolean trySuccessInternal(final V result) {
        return super.trySuccess(result);
    }
}
