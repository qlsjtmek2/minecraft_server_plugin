// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.cache;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.common.annotations.Beta;

@Beta
public abstract class AbstractCache<K, V> implements Cache<K, V>
{
    public V getUnchecked(final K key) {
        try {
            return this.get(key);
        }
        catch (ExecutionException e) {
            throw new UncheckedExecutionException(e.getCause());
        }
    }
    
    public final V apply(final K key) {
        return this.getUnchecked(key);
    }
    
    public void cleanUp() {
    }
    
    public long size() {
        throw new UnsupportedOperationException();
    }
    
    public void invalidate(final Object key) {
        throw new UnsupportedOperationException();
    }
    
    public void invalidateAll() {
        throw new UnsupportedOperationException();
    }
    
    public CacheStats stats() {
        throw new UnsupportedOperationException();
    }
    
    public ConcurrentMap<K, V> asMap() {
        throw new UnsupportedOperationException();
    }
    
    @Beta
    public static class SimpleStatsCounter implements StatsCounter
    {
        private final AtomicLong hitCount;
        private final AtomicLong missCount;
        private final AtomicLong loadSuccessCount;
        private final AtomicLong loadExceptionCount;
        private final AtomicLong totalLoadTime;
        private final AtomicLong evictionCount;
        
        public SimpleStatsCounter() {
            this.hitCount = new AtomicLong();
            this.missCount = new AtomicLong();
            this.loadSuccessCount = new AtomicLong();
            this.loadExceptionCount = new AtomicLong();
            this.totalLoadTime = new AtomicLong();
            this.evictionCount = new AtomicLong();
        }
        
        public void recordHit() {
            this.hitCount.incrementAndGet();
        }
        
        public void recordLoadSuccess(final long loadTime) {
            this.missCount.incrementAndGet();
            this.loadSuccessCount.incrementAndGet();
            this.totalLoadTime.addAndGet(loadTime);
        }
        
        public void recordLoadException(final long loadTime) {
            this.missCount.incrementAndGet();
            this.loadExceptionCount.incrementAndGet();
            this.totalLoadTime.addAndGet(loadTime);
        }
        
        public void recordConcurrentMiss() {
            this.missCount.incrementAndGet();
        }
        
        public void recordEviction() {
            this.evictionCount.incrementAndGet();
        }
        
        public CacheStats snapshot() {
            return new CacheStats(this.hitCount.get(), this.missCount.get(), this.loadSuccessCount.get(), this.loadExceptionCount.get(), this.totalLoadTime.get(), this.evictionCount.get());
        }
        
        public void incrementBy(final StatsCounter other) {
            final CacheStats otherStats = other.snapshot();
            this.hitCount.addAndGet(otherStats.hitCount());
            this.missCount.addAndGet(otherStats.missCount());
            this.loadSuccessCount.addAndGet(otherStats.loadSuccessCount());
            this.loadExceptionCount.addAndGet(otherStats.loadExceptionCount());
            this.totalLoadTime.addAndGet(otherStats.totalLoadTime());
            this.evictionCount.addAndGet(otherStats.evictionCount());
        }
    }
    
    @Beta
    public interface StatsCounter
    {
        void recordHit();
        
        void recordLoadSuccess(final long p0);
        
        void recordLoadException(final long p0);
        
        void recordConcurrentMiss();
        
        void recordEviction();
        
        CacheStats snapshot();
    }
}
