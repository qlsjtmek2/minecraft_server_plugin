// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;

public abstract class ForwardingListeningExecutorService extends ForwardingExecutorService implements ListeningExecutorService
{
    protected abstract ListeningExecutorService delegate();
    
    public <T> ListenableFuture<T> submit(final Callable<T> task) {
        return this.delegate().submit(task);
    }
    
    public ListenableFuture<?> submit(final Runnable task) {
        return this.delegate().submit(task);
    }
    
    public <T> ListenableFuture<T> submit(final Runnable task, final T result) {
        return this.delegate().submit(task, result);
    }
}
