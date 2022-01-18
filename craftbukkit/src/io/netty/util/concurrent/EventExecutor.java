// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;

public interface EventExecutor extends EventExecutorGroup, ScheduledExecutorService
{
    EventExecutor next();
    
    EventExecutorGroup parent();
    
    boolean inEventLoop();
    
    boolean inEventLoop(final Thread p0);
    
     <V> Promise<V> newPromise();
    
     <V> Future<V> newSucceededFuture(final V p0);
    
     <V> Future<V> newFailedFuture(final Throwable p0);
    
    Future<?> submit(final Runnable p0);
    
     <T> Future<T> submit(final Runnable p0, final T p1);
    
     <T> Future<T> submit(final Callable<T> p0);
    
    ScheduledFuture<?> schedule(final Runnable p0, final long p1, final TimeUnit p2);
    
     <V> ScheduledFuture<V> schedule(final Callable<V> p0, final long p1, final TimeUnit p2);
    
    ScheduledFuture<?> scheduleAtFixedRate(final Runnable p0, final long p1, final long p2, final TimeUnit p3);
    
    ScheduledFuture<?> scheduleWithFixedDelay(final Runnable p0, final long p1, final long p2, final TimeUnit p3);
}
