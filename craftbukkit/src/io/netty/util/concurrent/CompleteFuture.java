// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.concurrent;

import java.util.concurrent.TimeUnit;

public abstract class CompleteFuture<V> extends AbstractFuture<V>
{
    private final EventExecutor executor;
    
    protected CompleteFuture(final EventExecutor executor) {
        this.executor = executor;
    }
    
    protected EventExecutor executor() {
        return this.executor;
    }
    
    @Override
    public Future<V> addListener(final GenericFutureListener<? extends Future<V>> listener) {
        if (listener == null) {
            throw new NullPointerException("listener");
        }
        DefaultPromise.notifyListener(this.executor(), this, listener);
        return this;
    }
    
    @Override
    public Future<V> addListeners(final GenericFutureListener<? extends Future<V>>... listeners) {
        if (listeners == null) {
            throw new NullPointerException("listeners");
        }
        for (final GenericFutureListener<? extends Future<V>> l : listeners) {
            if (l == null) {
                break;
            }
            DefaultPromise.notifyListener(this.executor(), this, l);
        }
        return this;
    }
    
    @Override
    public Future<V> removeListener(final GenericFutureListener<? extends Future<V>> listener) {
        return this;
    }
    
    @Override
    public Future<V> removeListeners(final GenericFutureListener<? extends Future<V>>... listeners) {
        return this;
    }
    
    @Override
    public Future<V> await() throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return this;
    }
    
    @Override
    public boolean await(final long timeout, final TimeUnit unit) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return true;
    }
    
    @Override
    public Future<V> sync() throws InterruptedException {
        return this;
    }
    
    @Override
    public Future<V> syncUninterruptibly() {
        return this;
    }
    
    @Override
    public boolean await(final long timeoutMillis) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        return true;
    }
    
    @Override
    public Future<V> awaitUninterruptibly() {
        return this;
    }
    
    @Override
    public boolean awaitUninterruptibly(final long timeout, final TimeUnit unit) {
        return true;
    }
    
    @Override
    public boolean awaitUninterruptibly(final long timeoutMillis) {
        return true;
    }
    
    @Override
    public boolean isDone() {
        return true;
    }
}
