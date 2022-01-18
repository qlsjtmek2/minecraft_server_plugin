// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.concurrent;

public interface Promise<V> extends Future<V>
{
    Promise<V> setSuccess(final V p0);
    
    boolean trySuccess(final V p0);
    
    Promise<V> setFailure(final Throwable p0);
    
    boolean tryFailure(final Throwable p0);
    
    Promise<V> addListener(final GenericFutureListener<? extends Future<V>> p0);
    
    Promise<V> addListeners(final GenericFutureListener<? extends Future<V>>... p0);
    
    Promise<V> removeListener(final GenericFutureListener<? extends Future<V>> p0);
    
    Promise<V> removeListeners(final GenericFutureListener<? extends Future<V>>... p0);
    
    Promise<V> await() throws InterruptedException;
    
    Promise<V> awaitUninterruptibly();
    
    Promise<V> sync() throws InterruptedException;
    
    Promise<V> syncUninterruptibly();
}
