// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public final class ListenableFutureTask<V> extends FutureTask<V> implements ListenableFuture<V>
{
    private final ExecutionList executionList;
    
    public static <V> ListenableFutureTask<V> create(final Callable<V> callable) {
        return new ListenableFutureTask<V>(callable);
    }
    
    public static <V> ListenableFutureTask<V> create(final Runnable runnable, @Nullable final V result) {
        return new ListenableFutureTask<V>(runnable, result);
    }
    
    public ListenableFutureTask(final Callable<V> callable) {
        super(callable);
        this.executionList = new ExecutionList();
    }
    
    public ListenableFutureTask(final Runnable runnable, @Nullable final V result) {
        super(runnable, result);
        this.executionList = new ExecutionList();
    }
    
    public void addListener(final Runnable listener, final Executor exec) {
        this.executionList.add(listener, exec);
    }
    
    protected void done() {
        this.executionList.execute();
    }
}
