// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.cache;

import java.util.concurrent.ConcurrentMap;
import com.google.common.base.Preconditions;
import java.util.concurrent.ExecutionException;
import com.google.common.base.Supplier;
import java.io.Serializable;

class ComputingCache<K, V> extends AbstractCache<K, V> implements Serializable
{
    final CustomConcurrentHashMap<K, V> map;
    private static final long serialVersionUID = 1L;
    
    ComputingCache(final CacheBuilder<? super K, ? super V> builder, final Supplier<? extends StatsCounter> statsCounterSupplier, final CacheLoader<? super K, V> loader) {
        this.map = new CustomConcurrentHashMap<K, V>(builder, statsCounterSupplier, loader);
    }
    
    public V get(final K key) throws ExecutionException {
        return this.map.getOrCompute(key);
    }
    
    public void invalidate(final Object key) {
        Preconditions.checkNotNull(key);
        this.map.remove(key);
    }
    
    public void invalidateAll() {
        this.map.clear();
    }
    
    public long size() {
        return this.map.longSize();
    }
    
    public ConcurrentMap<K, V> asMap() {
        return this.map;
    }
    
    public CacheStats stats() {
        final SimpleStatsCounter aggregator = new SimpleStatsCounter();
        for (final CustomConcurrentHashMap.Segment<K, V> segment : this.map.segments) {
            aggregator.incrementBy(segment.statsCounter);
        }
        return aggregator.snapshot();
    }
    
    public void cleanUp() {
        this.map.cleanUp();
    }
    
    Object writeReplace() {
        return this.map.cacheSerializationProxy();
    }
}
