// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;
import java.util.Map;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true, emulated = true)
public abstract class ImmutableBiMap<K, V> extends ImmutableMap<K, V> implements BiMap<K, V>
{
    private static final ImmutableBiMap<Object, Object> EMPTY_IMMUTABLE_BIMAP;
    
    public static <K, V> ImmutableBiMap<K, V> of() {
        return (ImmutableBiMap<K, V>)ImmutableBiMap.EMPTY_IMMUTABLE_BIMAP;
    }
    
    public static <K, V> ImmutableBiMap<K, V> of(final K k1, final V v1) {
        return new RegularImmutableBiMap<K, V>(ImmutableMap.of(k1, v1));
    }
    
    public static <K, V> ImmutableBiMap<K, V> of(final K k1, final V v1, final K k2, final V v2) {
        return new RegularImmutableBiMap<K, V>(ImmutableMap.of(k1, v1, k2, v2));
    }
    
    public static <K, V> ImmutableBiMap<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3) {
        return new RegularImmutableBiMap<K, V>(ImmutableMap.of(k1, v1, k2, v2, k3, v3));
    }
    
    public static <K, V> ImmutableBiMap<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3, final K k4, final V v4) {
        return new RegularImmutableBiMap<K, V>(ImmutableMap.of(k1, v1, k2, v2, k3, v3, k4, v4));
    }
    
    public static <K, V> ImmutableBiMap<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3, final K k4, final V v4, final K k5, final V v5) {
        return new RegularImmutableBiMap<K, V>(ImmutableMap.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5));
    }
    
    public static <K, V> Builder<K, V> builder() {
        return new Builder<K, V>();
    }
    
    public static <K, V> ImmutableBiMap<K, V> copyOf(final Map<? extends K, ? extends V> map) {
        if (map instanceof ImmutableBiMap) {
            final ImmutableBiMap<K, V> bimap = (ImmutableBiMap<K, V>)(ImmutableBiMap)map;
            if (!bimap.isPartialView()) {
                return bimap;
            }
        }
        if (map.isEmpty()) {
            return of();
        }
        final ImmutableMap<K, V> immutableMap = ImmutableMap.copyOf(map);
        return new RegularImmutableBiMap<K, V>(immutableMap);
    }
    
    abstract ImmutableMap<K, V> delegate();
    
    public abstract ImmutableBiMap<V, K> inverse();
    
    public boolean containsKey(@Nullable final Object key) {
        return this.delegate().containsKey(key);
    }
    
    public boolean containsValue(@Nullable final Object value) {
        return this.inverse().containsKey(value);
    }
    
    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        return this.delegate().entrySet();
    }
    
    public V get(@Nullable final Object key) {
        return this.delegate().get(key);
    }
    
    public ImmutableSet<K> keySet() {
        return this.delegate().keySet();
    }
    
    public ImmutableSet<V> values() {
        return (ImmutableSet<V>)this.inverse().keySet();
    }
    
    public V forcePut(final K key, final V value) {
        throw new UnsupportedOperationException();
    }
    
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }
    
    public int size() {
        return this.delegate().size();
    }
    
    public boolean equals(@Nullable final Object object) {
        return object == this || this.delegate().equals(object);
    }
    
    public int hashCode() {
        return this.delegate().hashCode();
    }
    
    public String toString() {
        return this.delegate().toString();
    }
    
    Object writeReplace() {
        return new SerializedForm(this);
    }
    
    static {
        EMPTY_IMMUTABLE_BIMAP = new EmptyBiMap();
    }
    
    public static final class Builder<K, V> extends ImmutableMap.Builder<K, V>
    {
        public Builder<K, V> put(final K key, final V value) {
            super.put(key, value);
            return this;
        }
        
        public Builder<K, V> putAll(final Map<? extends K, ? extends V> map) {
            super.putAll(map);
            return this;
        }
        
        public ImmutableBiMap<K, V> build() {
            final ImmutableMap<K, V> map = super.build();
            if (map.isEmpty()) {
                return ImmutableBiMap.of();
            }
            return new RegularImmutableBiMap<K, V>(map);
        }
    }
    
    static class EmptyBiMap extends ImmutableBiMap<Object, Object>
    {
        ImmutableMap<Object, Object> delegate() {
            return ImmutableMap.of();
        }
        
        public ImmutableBiMap<Object, Object> inverse() {
            return this;
        }
        
        boolean isPartialView() {
            return false;
        }
        
        Object readResolve() {
            return ImmutableBiMap.EMPTY_IMMUTABLE_BIMAP;
        }
    }
    
    private static class SerializedForm extends ImmutableMap.SerializedForm
    {
        private static final long serialVersionUID = 0L;
        
        SerializedForm(final ImmutableBiMap<?, ?> bimap) {
            super(bimap);
        }
        
        Object readResolve() {
            final ImmutableBiMap.Builder<Object, Object> builder = new ImmutableBiMap.Builder<Object, Object>();
            return this.createMap(builder);
        }
    }
}
