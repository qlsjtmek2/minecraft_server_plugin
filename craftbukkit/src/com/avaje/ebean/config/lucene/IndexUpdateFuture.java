// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.lucene;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

public interface IndexUpdateFuture
{
    Class<?> getBeanType();
    
    boolean isCancelled();
    
    boolean cancel(final boolean p0);
    
    Integer get() throws InterruptedException, ExecutionException;
    
    Integer get(final long p0, final TimeUnit p1) throws InterruptedException, ExecutionException, TimeoutException;
    
    boolean isDone();
}
