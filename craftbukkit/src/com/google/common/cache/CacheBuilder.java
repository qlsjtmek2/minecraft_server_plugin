// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.cache;

import com.google.common.collect.ForwardingConcurrentMap;
import java.util.concurrent.ExecutionException;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.concurrent.ConcurrentMap;
import java.util.AbstractMap;
import com.google.common.base.Suppliers;
import com.google.common.base.Ascii;
import javax.annotation.CheckReturnValue;
import java.util.concurrent.TimeUnit;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Ticker;
import com.google.common.base.Equivalence;
import com.google.common.base.Supplier;
import com.google.common.annotations.Beta;

@Beta
public final class CacheBuilder<K, V>
{
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
    private static final int DEFAULT_EXPIRATION_NANOS = 0;
    static final Supplier<? extends AbstractCache.StatsCounter> DEFAULT_STATS_COUNTER;
    static final CacheStats EMPTY_STATS;
    static final Supplier<AbstractCache.SimpleStatsCounter> CACHE_STATS_COUNTER;
    static final int UNSET_INT = -1;
    int initialCapacity;
    int concurrencyLevel;
    int maximumSize;
    CustomConcurrentHashMap.Strength keyStrength;
    CustomConcurrentHashMap.Strength valueStrength;
    long expireAfterWriteNanos;
    long expireAfterAccessNanos;
    RemovalCause nullRemovalCause;
    Equivalence<Object> keyEquivalence;
    Equivalence<Object> valueEquivalence;
    RemovalListener<? super K, ? super V> removalListener;
    Ticker ticker;
    
    CacheBuilder() {
        this.initialCapacity = -1;
        this.concurrencyLevel = -1;
        this.maximumSize = -1;
        this.expireAfterWriteNanos = -1L;
        this.expireAfterAccessNanos = -1L;
    }
    
    public static CacheBuilder<Object, Object> newBuilder() {
        return new CacheBuilder<Object, Object>();
    }
    
    private boolean useNullCache() {
        return this.nullRemovalCause == null;
    }
    
    CacheBuilder<K, V> keyEquivalence(final Equivalence<Object> equivalence) {
        Preconditions.checkState(this.keyEquivalence == null, "key equivalence was already set to %s", this.keyEquivalence);
        this.keyEquivalence = Preconditions.checkNotNull(equivalence);
        return this;
    }
    
    Equivalence<Object> getKeyEquivalence() {
        return Objects.firstNonNull(this.keyEquivalence, this.getKeyStrength().defaultEquivalence());
    }
    
    CacheBuilder<K, V> valueEquivalence(final Equivalence<Object> equivalence) {
        Preconditions.checkState(this.valueEquivalence == null, "value equivalence was already set to %s", this.valueEquivalence);
        this.valueEquivalence = Preconditions.checkNotNull(equivalence);
        return this;
    }
    
    Equivalence<Object> getValueEquivalence() {
        return Objects.firstNonNull(this.valueEquivalence, this.getValueStrength().defaultEquivalence());
    }
    
    public CacheBuilder<K, V> initialCapacity(final int initialCapacity) {
        Preconditions.checkState(this.initialCapacity == -1, "initial capacity was already set to %s", this.initialCapacity);
        Preconditions.checkArgument(initialCapacity >= 0);
        this.initialCapacity = initialCapacity;
        return this;
    }
    
    int getInitialCapacity() {
        return (this.initialCapacity == -1) ? 16 : this.initialCapacity;
    }
    
    public CacheBuilder<K, V> concurrencyLevel(final int concurrencyLevel) {
        Preconditions.checkState(this.concurrencyLevel == -1, "concurrency level was already set to %s", this.concurrencyLevel);
        Preconditions.checkArgument(concurrencyLevel > 0);
        this.concurrencyLevel = concurrencyLevel;
        return this;
    }
    
    int getConcurrencyLevel() {
        return (this.concurrencyLevel == -1) ? 4 : this.concurrencyLevel;
    }
    
    public CacheBuilder<K, V> maximumSize(final int size) {
        Preconditions.checkState(this.maximumSize == -1, "maximum size was already set to %s", this.maximumSize);
        Preconditions.checkArgument(size >= 0, (Object)"maximum size must not be negative");
        this.maximumSize = size;
        if (this.maximumSize == 0) {
            this.nullRemovalCause = RemovalCause.SIZE;
        }
        return this;
    }
    
    CacheBuilder<K, V> strongKeys() {
        return this.setKeyStrength(CustomConcurrentHashMap.Strength.STRONG);
    }
    
    public CacheBuilder<K, V> weakKeys() {
        return this.setKeyStrength(CustomConcurrentHashMap.Strength.WEAK);
    }
    
    CacheBuilder<K, V> setKeyStrength(final CustomConcurrentHashMap.Strength strength) {
        Preconditions.checkState(this.keyStrength == null, "Key strength was already set to %s", this.keyStrength);
        this.keyStrength = Preconditions.checkNotNull(strength);
        return this;
    }
    
    CustomConcurrentHashMap.Strength getKeyStrength() {
        return Objects.firstNonNull(this.keyStrength, CustomConcurrentHashMap.Strength.STRONG);
    }
    
    CacheBuilder<K, V> strongValues() {
        return this.setValueStrength(CustomConcurrentHashMap.Strength.STRONG);
    }
    
    public CacheBuilder<K, V> weakValues() {
        return this.setValueStrength(CustomConcurrentHashMap.Strength.WEAK);
    }
    
    public CacheBuilder<K, V> softValues() {
        return this.setValueStrength(CustomConcurrentHashMap.Strength.SOFT);
    }
    
    CacheBuilder<K, V> setValueStrength(final CustomConcurrentHashMap.Strength strength) {
        Preconditions.checkState(this.valueStrength == null, "Value strength was already set to %s", this.valueStrength);
        this.valueStrength = Preconditions.checkNotNull(strength);
        return this;
    }
    
    CustomConcurrentHashMap.Strength getValueStrength() {
        return Objects.firstNonNull(this.valueStrength, CustomConcurrentHashMap.Strength.STRONG);
    }
    
    public CacheBuilder<K, V> expireAfterWrite(final long duration, final TimeUnit unit) {
        this.checkExpiration(duration, unit);
        this.expireAfterWriteNanos = unit.toNanos(duration);
        if (duration == 0L && this.nullRemovalCause == null) {
            this.nullRemovalCause = RemovalCause.EXPIRED;
        }
        return this;
    }
    
    private void checkExpiration(final long duration, final TimeUnit unit) {
        Preconditions.checkState(this.expireAfterWriteNanos == -1L, "expireAfterWrite was already set to %s ns", this.expireAfterWriteNanos);
        Preconditions.checkState(this.expireAfterAccessNanos == -1L, "expireAfterAccess was already set to %s ns", this.expireAfterAccessNanos);
        Preconditions.checkArgument(duration >= 0L, "duration cannot be negative: %s %s", duration, unit);
    }
    
    long getExpireAfterWriteNanos() {
        return (this.expireAfterWriteNanos == -1L) ? 0L : this.expireAfterWriteNanos;
    }
    
    public CacheBuilder<K, V> expireAfterAccess(final long duration, final TimeUnit unit) {
        this.checkExpiration(duration, unit);
        this.expireAfterAccessNanos = unit.toNanos(duration);
        if (duration == 0L && this.nullRemovalCause == null) {
            this.nullRemovalCause = RemovalCause.EXPIRED;
        }
        return this;
    }
    
    long getExpireAfterAccessNanos() {
        return (this.expireAfterAccessNanos == -1L) ? 0L : this.expireAfterAccessNanos;
    }
    
    public CacheBuilder<K, V> ticker(final Ticker ticker) {
        Preconditions.checkState(this.ticker == null);
        this.ticker = Preconditions.checkNotNull(ticker);
        return this;
    }
    
    Ticker getTicker() {
        return Objects.firstNonNull(this.ticker, Ticker.systemTicker());
    }
    
    @CheckReturnValue
    public <K1 extends K, V1 extends V> CacheBuilder<K1, V1> removalListener(final RemovalListener<? super K1, ? super V1> listener) {
        Preconditions.checkState(this.removalListener == null);
        this.removalListener = Preconditions.checkNotNull(listener);
        return (CacheBuilder<K1, V1>)this;
    }
    
     <K1 extends K, V1 extends V> RemovalListener<K1, V1> getRemovalListener() {
        return Objects.firstNonNull((RemovalListener<K1, V1>)this.removalListener, (RemovalListener<K1, V1>)NullListener.INSTANCE);
    }
    
    public <K1 extends K, V1 extends V> Cache<K1, V1> build(final CacheLoader<? super K1, V1> loader) {
        return (Cache<K1, V1>)(this.useNullCache() ? new ComputingCache<K1, V1>((CacheBuilder<? super Object, ? super Object>)this, CacheBuilder.CACHE_STATS_COUNTER, (CacheLoader<? super Object, Object>)loader) : new NullCache<K1, V1>((CacheBuilder<? super Object, ? super Object>)this, CacheBuilder.CACHE_STATS_COUNTER, (CacheLoader<? super Object, Object>)loader));
    }
    
    public String toString() {
        final Objects.ToStringHelper s = Objects.toStringHelper(this);
        if (this.initialCapacity != -1) {
            s.add("initialCapacity", this.initialCapacity);
        }
        if (this.concurrencyLevel != -1) {
            s.add("concurrencyLevel", this.concurrencyLevel);
        }
        if (this.maximumSize != -1) {
            s.add("maximumSize", this.maximumSize);
        }
        if (this.expireAfterWriteNanos != -1L) {
            s.add("expireAfterWrite", this.expireAfterWriteNanos + "ns");
        }
        if (this.expireAfterAccessNanos != -1L) {
            s.add("expireAfterAccess", this.expireAfterAccessNanos + "ns");
        }
        if (this.keyStrength != null) {
            s.add("keyStrength", Ascii.toLowerCase(this.keyStrength.toString()));
        }
        if (this.valueStrength != null) {
            s.add("valueStrength", Ascii.toLowerCase(this.valueStrength.toString()));
        }
        if (this.keyEquivalence != null) {
            s.addValue("keyEquivalence");
        }
        if (this.valueEquivalence != null) {
            s.addValue("valueEquivalence");
        }
        if (this.removalListener != null) {
            s.addValue("removalListener");
        }
        return s.toString();
    }
    
    static {
        DEFAULT_STATS_COUNTER = Suppliers.ofInstance((AbstractCache.StatsCounter)new AbstractCache.StatsCounter() {
            public void recordHit() {
            }
            
            public void recordLoadSuccess(final long loadTime) {
            }
            
            public void recordLoadException(final long loadTime) {
            }
            
            public void recordConcurrentMiss() {
            }
            
            public void recordEviction() {
            }
            
            public CacheStats snapshot() {
                return CacheBuilder.EMPTY_STATS;
            }
        });
        EMPTY_STATS = new CacheStats(0L, 0L, 0L, 0L, 0L, 0L);
        CACHE_STATS_COUNTER = new Supplier<AbstractCache.SimpleStatsCounter>() {
            public AbstractCache.SimpleStatsCounter get() {
                return new AbstractCache.SimpleStatsCounter();
            }
        };
    }
    
    enum NullListener implements RemovalListener<Object, Object>
    {
        INSTANCE;
        
        public void onRemoval(final RemovalNotification<Object, Object> notification) {
        }
    }
    
    static class NullConcurrentMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V>, Serializable
    {
        private static final long serialVersionUID = 0L;
        private final RemovalListener<K, V> removalListener;
        private final RemovalCause removalCause;
        
        NullConcurrentMap(final CacheBuilder<? super K, ? super V> builder) {
            this.removalListener = builder.getRemovalListener();
            this.removalCause = builder.nullRemovalCause;
        }
        
        public boolean containsKey(@Nullable final Object key) {
            return false;
        }
        
        public boolean containsValue(@Nullable final Object value) {
            return false;
        }
        
        public V get(@Nullable final Object key) {
            return null;
        }
        
        void notifyRemoval(final K key, final V value) {
            final RemovalNotification<K, V> notification = new RemovalNotification<K, V>(key, value, this.removalCause);
            this.removalListener.onRemoval(notification);
        }
        
        public V put(final K key, final V value) {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(value);
            this.notifyRemoval(key, value);
            return null;
        }
        
        public V putIfAbsent(final K key, final V value) {
            return this.put(key, value);
        }
        
        public V remove(@Nullable final Object key) {
            return null;
        }
        
        public boolean remove(@Nullable final Object key, @Nullable final Object value) {
            return false;
        }
        
        public V replace(final K key, final V value) {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(value);
            return null;
        }
        
        public boolean replace(final K key, @Nullable final V oldValue, final V newValue) {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(newValue);
            return false;
        }
        
        public Set<Map.Entry<K, V>> entrySet() {
            return Collections.emptySet();
        }
    }
    
    static final class NullComputingConcurrentMap<K, V> extends NullConcurrentMap<K, V>
    {
        private static final long serialVersionUID = 0L;
        final CacheLoader<? super K, ? extends V> loader;
        
        NullComputingConcurrentMap(final CacheBuilder<? super K, ? super V> builder, final CacheLoader<? super K, ? extends V> loader) {
            super(builder);
            this.loader = Preconditions.checkNotNull(loader);
        }
        
        public V get(final Object k) {
            final V value = this.compute(k);
            Preconditions.checkNotNull(value, (Object)(this.loader + " returned null for key " + k + "."));
            this.notifyRemoval((K)k, value);
            return value;
        }
        
        private V compute(final K key) {
            Preconditions.checkNotNull(key);
            try {
                return (V)this.loader.load((Object)key);
            }
            catch (Exception e) {
                throw new UncheckedExecutionException(e);
            }
            catch (Error e2) {
                throw new ExecutionError(e2);
            }
        }
    }
    
    static final class NullCache<K, V> extends AbstractCache<K, V>
    {
        final NullConcurrentMap<K, V> map;
        final CacheLoader<? super K, V> loader;
        final StatsCounter statsCounter;
        ConcurrentMap<K, V> asMap;
        
        NullCache(final CacheBuilder<? super K, ? super V> builder, final Supplier<? extends StatsCounter> statsCounterSupplier, final CacheLoader<? super K, V> loader) {
            this.map = new NullConcurrentMap<K, V>(builder);
            this.statsCounter = (StatsCounter)statsCounterSupplier.get();
            this.loader = Preconditions.checkNotNull(loader);
        }
        
        public V get(final K key) throws ExecutionException {
            final V value = this.compute(key);
            this.map.notifyRemoval(key, value);
            return value;
        }
        
        private V compute(final K key) throws ExecutionException {
            Preconditions.checkNotNull(key);
            final long start = System.nanoTime();
            V value = null;
            try {
                value = this.loader.load((Object)key);
            }
            catch (RuntimeException e) {
                throw new UncheckedExecutionException(e);
            }
            catch (Exception e2) {
                throw new ExecutionException(e2);
            }
            catch (Error e3) {
                throw new ExecutionError(e3);
            }
            finally {
                final long elapsed = System.nanoTime() - start;
                if (value == null) {
                    this.statsCounter.recordLoadException(elapsed);
                }
                else {
                    this.statsCounter.recordLoadSuccess(elapsed);
                }
                this.statsCounter.recordEviction();
            }
            if (value == null) {
                throw new NullPointerException();
            }
            return value;
        }
        
        public long size() {
            return 0L;
        }
        
        public void invalidate(final Object key) {
        }
        
        public void invalidateAll() {
        }
        
        public CacheStats stats() {
            return this.statsCounter.snapshot();
        }
        
        public ConcurrentMap<K, V> asMap() {
            final ConcurrentMap<K, V> am = this.asMap;
            return (am != null) ? am : (this.asMap = new CacheAsMap<K, V>(this.map));
        }
    }
    
    static final class CacheAsMap<K, V> extends ForwardingConcurrentMap<K, V>
    {
        private final ConcurrentMap<K, V> delegate;
        
        CacheAsMap(final ConcurrentMap<K, V> delegate) {
            this.delegate = delegate;
        }
        
        protected ConcurrentMap<K, V> delegate() {
            return this.delegate;
        }
        
        public V put(final K key, final V value) {
            throw new UnsupportedOperationException();
        }
        
        public void putAll(final Map<? extends K, ? extends V> map) {
            throw new UnsupportedOperationException();
        }
        
        public V putIfAbsent(final K key, final V value) {
            throw new UnsupportedOperationException();
        }
        
        public V replace(final K key, final V value) {
            throw new UnsupportedOperationException();
        }
        
        public boolean replace(final K key, final V oldValue, final V newValue) {
            throw new UnsupportedOperationException();
        }
    }
}
