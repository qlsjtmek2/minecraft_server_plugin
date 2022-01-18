// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.cache;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import com.google.common.annotations.Beta;
import com.google.common.base.Function;

@Beta
public interface Cache<K, V> extends Function<K, V>
{
    V get(final K p0) throws ExecutionException;
    
    V getUnchecked(final K p0);
    
    V apply(final K p0);
    
    void invalidate(final Object p0);
    
    void invalidateAll();
    
    long size();
    
    CacheStats stats();
    
    ConcurrentMap<K, V> asMap();
    
    void cleanUp();
}
