// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.ThreadFactory;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import com.google.common.annotations.Beta;

@Beta
public final class JdkFutureAdapters
{
    public static <V> ListenableFuture<V> listenInPoolThread(final Future<V> future) {
        if (future instanceof ListenableFuture) {
            return (ListenableFuture<V>)(ListenableFuture)future;
        }
        return new ListenableFutureAdapter<V>(future);
    }
    
    @VisibleForTesting
    static <V> ListenableFuture<V> listenInPoolThread(final Future<V> future, final Executor executor) {
        Preconditions.checkNotNull(executor);
        if (future instanceof ListenableFuture) {
            return (ListenableFuture<V>)(ListenableFuture)future;
        }
        return new ListenableFutureAdapter<V>(future, executor);
    }
    
    private static class ListenableFutureAdapter<V> extends ForwardingFuture<V> implements ListenableFuture<V>
    {
        private static final ThreadFactory threadFactory;
        private static final Executor defaultAdapterExecutor;
        private final Executor adapterExecutor;
        private final ExecutionList executionList;
        private final AtomicBoolean hasListeners;
        private final Future<V> delegate;
        
        ListenableFutureAdapter(final Future<V> delegate) {
            this(delegate, ListenableFutureAdapter.defaultAdapterExecutor);
        }
        
        ListenableFutureAdapter(final Future<V> delegate, final Executor adapterExecutor) {
            this.executionList = new ExecutionList();
            this.hasListeners = new AtomicBoolean(false);
            this.delegate = Preconditions.checkNotNull(delegate);
            this.adapterExecutor = Preconditions.checkNotNull(adapterExecutor);
        }
        
        protected Future<V> delegate() {
            return this.delegate;
        }
        
        public void addListener(final Runnable listener, final Executor exec) {
            this.executionList.add(listener, exec);
            if (this.hasListeners.compareAndSet(false, true)) {
                if (this.delegate.isDone()) {
                    this.executionList.execute();
                    return;
                }
                this.adapterExecutor.execute(new Runnable() {
                    public void run() {
                        try {
                            ListenableFutureAdapter.this.delegate.get();
                        }
                        catch (Error e) {
                            throw e;
                        }
                        catch (InterruptedException e2) {
                            Thread.currentThread().interrupt();
                            throw new AssertionError((Object)e2);
                        }
                        catch (Throwable t) {}
                        ListenableFutureAdapter.this.executionList.execute();
                    }
                });
            }
        }
        
        static {
            threadFactory = new ThreadFactoryBuilder().setNameFormat("ListenableFutureAdapter-thread-%d").build();
            defaultAdapterExecutor = Executors.newCachedThreadPool(ListenableFutureAdapter.threadFactory);
        }
    }
}
