// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.concurrent;

import java.util.concurrent.TimeUnit;

public interface EventExecutorGroup
{
    EventExecutor next();
    
    void shutdown();
    
    boolean isShutdown();
    
    boolean isTerminated();
    
    boolean awaitTermination(final long p0, final TimeUnit p1) throws InterruptedException;
}
