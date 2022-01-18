// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.AbstractMap;
import javax.annotation.Nullable;
import java.io.Serializable;
import com.google.common.base.Ascii;
import com.google.common.base.Function;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import com.google.common.annotations.Beta;
import com.google.common.base.Objects;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Ticker;
import com.google.common.base.Equivalence;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
public final class MapMaker extends GenericMapMaker<Object, Object>
{
    private static final int DEFAULT_INITIAL_CAPACITY = 16;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 4;
    private static final int DEFAULT_EXPIRATION_NANOS = 0;
    static final int UNSET_INT = -1;
    boolean useCustomMap;
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
    Ticker ticker;
    
    public MapMaker() {
        this.initialCapacity = -1;
        this.concurrencyLevel = -1;
        this.maximumSize = -1;
        this.expireAfterWriteNanos = -1L;
        this.expireAfterAccessNanos = -1L;
    }
    
    private boolean useNullMap() {
        return this.nullRemovalCause == null;
    }
    
    @GwtIncompatible("To be supported")
    MapMaker keyEquivalence(final Equivalence<Object> equivalence) {
        Preconditions.checkState(this.keyEquivalence == null, "key equivalence was already set to %s", this.keyEquivalence);
        this.keyEquivalence = Preconditions.checkNotNull(equivalence);
        this.useCustomMap = true;
        return this;
    }
    
    Equivalence<Object> getKeyEquivalence() {
        return Objects.firstNonNull(this.keyEquivalence, this.getKeyStrength().defaultEquivalence());
    }
    
    @GwtIncompatible("To be supported")
    MapMaker valueEquivalence(final Equivalence<Object> equivalence) {
        Preconditions.checkState(this.valueEquivalence == null, "value equivalence was already set to %s", this.valueEquivalence);
        this.valueEquivalence = Preconditions.checkNotNull(equivalence);
        this.useCustomMap = true;
        return this;
    }
    
    Equivalence<Object> getValueEquivalence() {
        return Objects.firstNonNull(this.valueEquivalence, this.getValueStrength().defaultEquivalence());
    }
    
    public MapMaker initialCapacity(final int initialCapacity) {
        Preconditions.checkState(this.initialCapacity == -1, "initial capacity was already set to %s", this.initialCapacity);
        Preconditions.checkArgument(initialCapacity >= 0);
        this.initialCapacity = initialCapacity;
        return this;
    }
    
    int getInitialCapacity() {
        return (this.initialCapacity == -1) ? 16 : this.initialCapacity;
    }
    
    @Deprecated
    @Beta
    public MapMaker maximumSize(final int size) {
        Preconditions.checkState(this.maximumSize == -1, "maximum size was already set to %s", this.maximumSize);
        Preconditions.checkArgument(size >= 0, (Object)"maximum size must not be negative");
        this.maximumSize = size;
        this.useCustomMap = true;
        if (this.maximumSize == 0) {
            this.nullRemovalCause = RemovalCause.SIZE;
        }
        return this;
    }
    
    public MapMaker concurrencyLevel(final int concurrencyLevel) {
        Preconditions.checkState(this.concurrencyLevel == -1, "concurrency level was already set to %s", this.concurrencyLevel);
        Preconditions.checkArgument(concurrencyLevel > 0);
        this.concurrencyLevel = concurrencyLevel;
        return this;
    }
    
    int getConcurrencyLevel() {
        return (this.concurrencyLevel == -1) ? 4 : this.concurrencyLevel;
    }
    
    MapMaker strongKeys() {
        return this.setKeyStrength(CustomConcurrentHashMap.Strength.STRONG);
    }
    
    @GwtIncompatible("java.lang.ref.WeakReference")
    public MapMaker weakKeys() {
        return this.setKeyStrength(CustomConcurrentHashMap.Strength.WEAK);
    }
    
    @Deprecated
    @GwtIncompatible("java.lang.ref.SoftReference")
    public MapMaker softKeys() {
        return this.setKeyStrength(CustomConcurrentHashMap.Strength.SOFT);
    }
    
    MapMaker setKeyStrength(final CustomConcurrentHashMap.Strength strength) {
        Preconditions.checkState(this.keyStrength == null, "Key strength was already set to %s", this.keyStrength);
        this.keyStrength = Preconditions.checkNotNull(strength);
        if (strength != CustomConcurrentHashMap.Strength.STRONG) {
            this.useCustomMap = true;
        }
        return this;
    }
    
    CustomConcurrentHashMap.Strength getKeyStrength() {
        return Objects.firstNonNull(this.keyStrength, CustomConcurrentHashMap.Strength.STRONG);
    }
    
    MapMaker strongValues() {
        return this.setValueStrength(CustomConcurrentHashMap.Strength.STRONG);
    }
    
    @GwtIncompatible("java.lang.ref.WeakReference")
    public MapMaker weakValues() {
        return this.setValueStrength(CustomConcurrentHashMap.Strength.WEAK);
    }
    
    @GwtIncompatible("java.lang.ref.SoftReference")
    public MapMaker softValues() {
        return this.setValueStrength(CustomConcurrentHashMap.Strength.SOFT);
    }
    
    MapMaker setValueStrength(final CustomConcurrentHashMap.Strength strength) {
        Preconditions.checkState(this.valueStrength == null, "Value strength was already set to %s", this.valueStrength);
        this.valueStrength = Preconditions.checkNotNull(strength);
        if (strength != CustomConcurrentHashMap.Strength.STRONG) {
            this.useCustomMap = true;
        }
        return this;
    }
    
    CustomConcurrentHashMap.Strength getValueStrength() {
        return Objects.firstNonNull(this.valueStrength, CustomConcurrentHashMap.Strength.STRONG);
    }
    
    @Deprecated
    public MapMaker expiration(final long duration, final TimeUnit unit) {
        return this.expireAfterWrite(duration, unit);
    }
    
    @Deprecated
    public MapMaker expireAfterWrite(final long duration, final TimeUnit unit) {
        this.checkExpiration(duration, unit);
        this.expireAfterWriteNanos = unit.toNanos(duration);
        if (duration == 0L && this.nullRemovalCause == null) {
            this.nullRemovalCause = RemovalCause.EXPIRED;
        }
        this.useCustomMap = true;
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
    
    @Deprecated
    @GwtIncompatible("To be supported")
    public MapMaker expireAfterAccess(final long duration, final TimeUnit unit) {
        this.checkExpiration(duration, unit);
        this.expireAfterAccessNanos = unit.toNanos(duration);
        if (duration == 0L && this.nullRemovalCause == null) {
            this.nullRemovalCause = RemovalCause.EXPIRED;
        }
        this.useCustomMap = true;
        return this;
    }
    
    long getExpireAfterAccessNanos() {
        return (this.expireAfterAccessNanos == -1L) ? 0L : this.expireAfterAccessNanos;
    }
    
    Ticker getTicker() {
        return Objects.firstNonNull(this.ticker, Ticker.systemTicker());
    }
    
    @GwtIncompatible("To be supported")
     <K, V> GenericMapMaker<K, V> removalListener(final RemovalListener<K, V> listener) {
        Preconditions.checkState(this.removalListener == null);
        super.removalListener = Preconditions.checkNotNull((RemovalListener<K0, V0>)listener);
        this.useCustomMap = true;
        return (GenericMapMaker<K, V>)this;
    }
    
    @Deprecated
    @Beta
    @GwtIncompatible("To be supported")
    public <K, V> GenericMapMaker<K, V> evictionListener(final MapEvictionListener<K, V> listener) {
        Preconditions.checkState(this.removalListener == null);
        super.removalListener = new MapMakerRemovalListener<Object, Object>((MapEvictionListener<K0, V0>)listener);
        this.useCustomMap = true;
        return (GenericMapMaker<K, V>)this;
    }
    
    public <K, V> ConcurrentMap<K, V> makeMap() {
        if (!this.useCustomMap) {
            return new ConcurrentHashMap<K, V>(this.getInitialCapacity(), 0.75f, this.getConcurrencyLevel());
        }
        return (ConcurrentMap<K, V>)((this.nullRemovalCause == null) ? new CustomConcurrentHashMap<Object, Object>(this) : new NullConcurrentMap<Object, Object>(this));
    }
    
    @GwtIncompatible("CustomConcurrentHashMap")
     <K, V> CustomConcurrentHashMap<K, V> makeCustomMap() {
        return new CustomConcurrentHashMap<K, V>(this);
    }
    
    @Deprecated
    public <K, V> ConcurrentMap<K, V> makeComputingMap(final Function<? super K, ? extends V> computingFunction) {
        return (ConcurrentMap<K, V>)(this.useNullMap() ? new ComputingConcurrentHashMap.ComputingMapAdapter<Object, Object>(this, computingFunction) : new NullComputingConcurrentMap<Object, Object>(this, computingFunction));
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
    
    static final class MapMakerRemovalListener<K, V> implements RemovalListener<K, V>, Serializable
    {
        private static final long serialVersionUID = 0L;
        private final MapEvictionListener<K, V> listener;
        
        public MapMakerRemovalListener(final MapEvictionListener<K, V> listener) {
            this.listener = Preconditions.checkNotNull(listener);
        }
        
        public void onRemoval(final RemovalNotification<K, V> notification) {
            if (notification.wasEvicted()) {
                this.listener.onEviction(notification.getKey(), notification.getValue());
            }
        }
    }
    
    static final class RemovalNotification<K, V> extends ImmutableEntry<K, V>
    {
        private static final long serialVersionUID = 0L;
        private final RemovalCause cause;
        
        RemovalNotification(@Nullable final K key, @Nullable final V value, final RemovalCause cause) {
            super(key, value);
            this.cause = cause;
        }
        
        public RemovalCause getCause() {
            return this.cause;
        }
        
        public boolean wasEvicted() {
            return this.cause.wasEvicted();
        }
    }
    
    enum RemovalCause
    {
        EXPLICIT {
            boolean wasEvicted() {
                return false;
            }
        }, 
        REPLACED {
            boolean wasEvicted() {
                return false;
            }
        }, 
        COLLECTED {
            boolean wasEvicted() {
                return true;
            }
        }, 
        EXPIRED {
            boolean wasEvicted() {
                return true;
            }
        }, 
        SIZE {
            boolean wasEvicted() {
                return true;
            }
        };
        
        abstract boolean wasEvicted();
    }
    
    static class NullConcurrentMap<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V>, Serializable
    {
        private static final long serialVersionUID = 0L;
        private final RemovalListener<K, V> removalListener;
        private final RemovalCause removalCause;
        
        NullConcurrentMap(final MapMaker mapMaker) {
            this.removalListener = mapMaker.getRemovalListener();
            this.removalCause = mapMaker.nullRemovalCause;
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
        final Function<? super K, ? extends V> computingFunction;
        
        NullComputingConcurrentMap(final MapMaker mapMaker, final Function<? super K, ? extends V> computingFunction) {
            super(mapMaker);
            this.computingFunction = Preconditions.checkNotNull(computingFunction);
        }
        
        public V get(final Object k) {
            final V value = this.compute(k);
            Preconditions.checkNotNull(value, (Object)(this.computingFunction + " returned null for key " + k + "."));
            this.notifyRemoval((K)k, value);
            return value;
        }
        
        private V compute(final K key) {
            Preconditions.checkNotNull(key);
            try {
                return (V)this.computingFunction.apply((Object)key);
            }
            catch (ComputationException e) {
                throw e;
            }
            catch (Throwable t) {
                throw new ComputationException(t);
            }
        }
    }
    
    interface RemovalListener<K, V>
    {
        void onRemoval(final RemovalNotification<K, V> p0);
    }
}
