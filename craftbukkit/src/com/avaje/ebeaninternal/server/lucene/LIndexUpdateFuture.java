// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import com.avaje.ebean.config.lucene.IndexUpdateFuture;
import java.util.concurrent.Future;

public class LIndexUpdateFuture implements Future<Integer>, IndexUpdateFuture
{
    private final Class<?> beanType;
    private final Runnable commitRunnable;
    private final FutureTask<Void> commitFuture;
    private FutureTask<Integer> task;
    
    public LIndexUpdateFuture(final Class<?> beanType) {
        this.beanType = beanType;
        this.commitRunnable = new DummyRunnable();
        this.commitFuture = new FutureTask<Void>(this.commitRunnable, null);
    }
    
    public Class<?> getBeanType() {
        return this.beanType;
    }
    
    public Runnable getCommitRunnable() {
        return this.commitFuture;
    }
    
    public void setTask(final FutureTask<Integer> task) {
        this.task = task;
    }
    
    public boolean isCancelled() {
        return false;
    }
    
    public boolean cancel(final boolean mayInterruptIfRunning) {
        return false;
    }
    
    public Integer get() throws InterruptedException, ExecutionException {
        this.commitFuture.get();
        return this.task.get();
    }
    
    public Integer get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        this.commitFuture.get(timeout, unit);
        return this.task.get(0L, unit);
    }
    
    public boolean isDone() {
        return this.commitFuture.isDone();
    }
    
    private static class DummyRunnable implements Runnable
    {
        public void run() {
            System.out.println("-- dummy runnable");
        }
    }
}
