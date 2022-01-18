// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.util.concurrent;

import java.util.concurrent.TimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;
import com.google.common.annotations.Beta;
import java.util.concurrent.Future;

@Deprecated
@Beta
public interface UninterruptibleFuture<V> extends Future<V>
{
    V get() throws ExecutionException;
    
    V get(final long p0, final TimeUnit p1) throws ExecutionException, TimeoutException;
}
