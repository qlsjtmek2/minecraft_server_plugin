// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.concurrent;

public abstract class CompletePromise<V> extends CompleteFuture<V> implements Promise<V>
{
    protected CompletePromise(final EventExecutor executor) {
        super(executor);
    }
    
    @Override
    public Promise<V> setFailure(final Throwable cause) {
        throw new IllegalStateException();
    }
    
    @Override
    public boolean tryFailure(final Throwable cause) {
        return false;
    }
    
    @Override
    public Promise<V> setSuccess(final V result) {
        throw new IllegalStateException();
    }
    
    @Override
    public boolean trySuccess(final V result) {
        return false;
    }
    
    @Override
    public Promise<V> await() throws InterruptedException {
        return this;
    }
    
    @Override
    public Promise<V> awaitUninterruptibly() {
        return this;
    }
    
    @Override
    public Promise<V> syncUninterruptibly() {
        return this;
    }
    
    @Override
    public Promise<V> sync() throws InterruptedException {
        return this;
    }
    
    @Override
    public Promise<V> addListener(final GenericFutureListener<? extends Future<V>> listener) {
        return (Promise<V>)(Promise)super.addListener(listener);
    }
    
    @Override
    public Promise<V> addListeners(final GenericFutureListener<? extends Future<V>>... listeners) {
        return (Promise<V>)(Promise)super.addListeners(listeners);
    }
    
    @Override
    public Promise<V> removeListener(final GenericFutureListener<? extends Future<V>> listener) {
        return (Promise<V>)(Promise)super.removeListener(listener);
    }
    
    @Override
    public Promise<V> removeListeners(final GenericFutureListener<? extends Future<V>>... listeners) {
        return (Promise<V>)(Promise)super.removeListeners(listeners);
    }
}
