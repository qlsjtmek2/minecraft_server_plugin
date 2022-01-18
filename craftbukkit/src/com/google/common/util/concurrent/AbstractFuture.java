// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import java.util.concurrent.CancellationException;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import javax.annotation.Nullable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;

public abstract class AbstractFuture<V> implements ListenableFuture<V>
{
    private final Sync<V> sync;
    private final ExecutionList executionList;
    
    public AbstractFuture() {
        this.sync = new Sync<V>();
        this.executionList = new ExecutionList();
    }
    
    public V get(final long timeout, final TimeUnit unit) throws InterruptedException, TimeoutException, ExecutionException {
        return this.sync.get(unit.toNanos(timeout));
    }
    
    public V get() throws InterruptedException, ExecutionException {
        return this.sync.get();
    }
    
    public boolean isDone() {
        return this.sync.isDone();
    }
    
    public boolean isCancelled() {
        return this.sync.isCancelled();
    }
    
    public boolean cancel(final boolean mayInterruptIfRunning) {
        if (!this.sync.cancel()) {
            return false;
        }
        this.done();
        if (mayInterruptIfRunning) {
            this.interruptTask();
        }
        return true;
    }
    
    protected void interruptTask() {
    }
    
    public void addListener(final Runnable listener, final Executor exec) {
        this.executionList.add(listener, exec);
    }
    
    protected boolean set(@Nullable final V value) {
        final boolean result = this.sync.set(value);
        if (result) {
            this.done();
        }
        return result;
    }
    
    protected boolean setException(final Throwable throwable) {
        final boolean result = this.sync.setException(Preconditions.checkNotNull(throwable));
        if (result) {
            this.done();
        }
        if (throwable instanceof Error) {
            throw (Error)throwable;
        }
        return result;
    }
    
    @Deprecated
    @Beta
    protected final boolean cancel() {
        final boolean result = this.sync.cancel();
        if (result) {
            this.done();
        }
        return result;
    }
    
    @Deprecated
    @Beta
    protected void done() {
        this.executionList.execute();
    }
    
    static final class Sync<V> extends AbstractQueuedSynchronizer
    {
        private static final long serialVersionUID = 0L;
        static final int RUNNING = 0;
        static final int COMPLETING = 1;
        static final int COMPLETED = 2;
        static final int CANCELLED = 4;
        private V value;
        private Throwable exception;
        
        protected int tryAcquireShared(final int ignored) {
            if (this.isDone()) {
                return 1;
            }
            return -1;
        }
        
        protected boolean tryReleaseShared(final int finalState) {
            this.setState(finalState);
            return true;
        }
        
        V get(final long nanos) throws TimeoutException, CancellationException, ExecutionException, InterruptedException {
            if (!this.tryAcquireSharedNanos(-1, nanos)) {
                throw new TimeoutException("Timeout waiting for task.");
            }
            return this.getValue();
        }
        
        V get() throws CancellationException, ExecutionException, InterruptedException {
            this.acquireSharedInterruptibly(-1);
            return this.getValue();
        }
        
        private V getValue() throws CancellationException, ExecutionException {
            final int state = this.getState();
            switch (state) {
                case 2: {
                    if (this.exception != null) {
                        throw new ExecutionException(this.exception);
                    }
                    return this.value;
                }
                case 4: {
                    throw new CancellationException("Task was cancelled.");
                }
                default: {
                    throw new IllegalStateException("Error, synchronizer in invalid state: " + state);
                }
            }
        }
        
        boolean isDone() {
            return (this.getState() & 0x6) != 0x0;
        }
        
        boolean isCancelled() {
            return this.getState() == 4;
        }
        
        boolean set(@Nullable final V v) {
            return this.complete(v, null, 2);
        }
        
        boolean setException(final Throwable t) {
            return this.complete(null, t, 2);
        }
        
        boolean cancel() {
            return this.complete(null, null, 4);
        }
        
        private boolean complete(@Nullable final V v, final Throwable t, final int finalState) {
            if (this.compareAndSetState(0, 1)) {
                this.value = v;
                this.exception = t;
                this.releaseShared(finalState);
                return true;
            }
            return false;
        }
    }
}
