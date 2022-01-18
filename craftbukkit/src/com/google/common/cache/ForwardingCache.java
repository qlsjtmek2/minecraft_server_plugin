// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.cache;

import com.google.common.base.Preconditions;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nullable;
import com.google.common.annotations.Beta;
import com.google.common.collect.ForwardingObject;

@Beta
public abstract class ForwardingCache<K, V> extends ForwardingObject implements Cache<K, V>
{
    protected abstract Cache<K, V> delegate();
    
    @Nullable
    public V get(@Nullable final K key) throws ExecutionException {
        return this.delegate().get(key);
    }
    
    @Nullable
    public V getUnchecked(@Nullable final K key) {
        return this.delegate().getUnchecked(key);
    }
    
    @Deprecated
    @Nullable
    public V apply(@Nullable final K key) {
        return this.delegate().apply(key);
    }
    
    public void invalidate(@Nullable final Object key) {
        this.delegate().invalidate(key);
    }
    
    public void invalidateAll() {
        this.delegate().invalidateAll();
    }
    
    public long size() {
        return this.delegate().size();
    }
    
    public CacheStats stats() {
        return this.delegate().stats();
    }
    
    public ConcurrentMap<K, V> asMap() {
        return this.delegate().asMap();
    }
    
    public void cleanUp() {
        this.delegate().cleanUp();
    }
    
    @Beta
    public abstract static class SimpleForwardingCache<K, V> extends ForwardingCache<K, V>
    {
        private final Cache<K, V> delegate;
        
        protected SimpleForwardingCache(final Cache<K, V> delegate) {
            this.delegate = Preconditions.checkNotNull(delegate);
        }
        
        protected final Cache<K, V> delegate() {
            return this.delegate;
        }
    }
}
