// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;
import java.util.Map;

@GwtCompatible(serializable = true, emulated = true)
public abstract class ImmutableMap<K, V> implements Map<K, V>, Serializable
{
    public static <K, V> ImmutableMap<K, V> of() {
        return (ImmutableMap<K, V>)EmptyImmutableMap.INSTANCE;
    }
    
    public static <K, V> ImmutableMap<K, V> of(final K k1, final V v1) {
        return new SingletonImmutableMap<K, V>(Preconditions.checkNotNull(k1), Preconditions.checkNotNull(v1));
    }
    
    public static <K, V> ImmutableMap<K, V> of(final K k1, final V v1, final K k2, final V v2) {
        return new RegularImmutableMap<K, V>((Entry<?, ?>[])new Entry[] { entryOf(k1, v1), entryOf(k2, v2) });
    }
    
    public static <K, V> ImmutableMap<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3) {
        return new RegularImmutableMap<K, V>((Entry<?, ?>[])new Entry[] { entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3) });
    }
    
    public static <K, V> ImmutableMap<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3, final K k4, final V v4) {
        return new RegularImmutableMap<K, V>((Entry<?, ?>[])new Entry[] { entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4) });
    }
    
    public static <K, V> ImmutableMap<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3, final K k4, final V v4, final K k5, final V v5) {
        return new RegularImmutableMap<K, V>((Entry<?, ?>[])new Entry[] { entryOf(k1, v1), entryOf(k2, v2), entryOf(k3, v3), entryOf(k4, v4), entryOf(k5, v5) });
    }
    
    public static <K, V> Builder<K, V> builder() {
        return new Builder<K, V>();
    }
    
    static <K, V> Entry<K, V> entryOf(final K key, final V value) {
        return Maps.immutableEntry((K)Preconditions.checkNotNull((K)key, (Object)"null key"), (V)Preconditions.checkNotNull((V)value, (Object)"null value"));
    }
    
    public static <K, V> ImmutableMap<K, V> copyOf(final Map<? extends K, ? extends V> map) {
        if (map instanceof ImmutableMap && !(map instanceof ImmutableSortedMap)) {
            final ImmutableMap<K, V> kvMap = (ImmutableMap<K, V>)(ImmutableMap)map;
            if (!kvMap.isPartialView()) {
                return kvMap;
            }
        }
        final Entry<K, V>[] entries = map.entrySet().toArray(new Entry[0]);
        switch (entries.length) {
            case 0: {
                return of();
            }
            case 1: {
                return new SingletonImmutableMap<K, V>(entryOf(entries[0].getKey(), entries[0].getValue()));
            }
            default: {
                for (int i = 0; i < entries.length; ++i) {
                    final K k = entries[i].getKey();
                    final V v = entries[i].getValue();
                    entries[i] = entryOf(k, v);
                }
                return new RegularImmutableMap<K, V>((Entry<?, ?>[])entries);
            }
        }
    }
    
    public final V put(final K k, final V v) {
        throw new UnsupportedOperationException();
    }
    
    public final V remove(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    public final void putAll(final Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }
    
    public final void clear() {
        throw new UnsupportedOperationException();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    public boolean containsKey(@Nullable final Object key) {
        return this.get(key) != null;
    }
    
    public abstract boolean containsValue(@Nullable final Object p0);
    
    public abstract V get(@Nullable final Object p0);
    
    public abstract ImmutableSet<Entry<K, V>> entrySet();
    
    public abstract ImmutableSet<K> keySet();
    
    public abstract ImmutableCollection<V> values();
    
    public boolean equals(@Nullable final Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof Map) {
            final Map<?, ?> that = (Map<?, ?>)object;
            return this.entrySet().equals(that.entrySet());
        }
        return false;
    }
    
    abstract boolean isPartialView();
    
    public int hashCode() {
        return this.entrySet().hashCode();
    }
    
    public String toString() {
        return Maps.toStringImpl(this);
    }
    
    Object writeReplace() {
        return new SerializedForm(this);
    }
    
    public static class Builder<K, V>
    {
        final ArrayList<Entry<K, V>> entries;
        
        public Builder() {
            this.entries = Lists.newArrayList();
        }
        
        public Builder<K, V> put(final K key, final V value) {
            this.entries.add(ImmutableMap.entryOf(key, value));
            return this;
        }
        
        public Builder<K, V> putAll(final Map<? extends K, ? extends V> map) {
            this.entries.ensureCapacity(this.entries.size() + map.size());
            for (final Entry<? extends K, ? extends V> entry : map.entrySet()) {
                this.put(entry.getKey(), entry.getValue());
            }
            return this;
        }
        
        public ImmutableMap<K, V> build() {
            return fromEntryList(this.entries);
        }
        
        private static <K, V> ImmutableMap<K, V> fromEntryList(final List<Entry<K, V>> entries) {
            final int size = entries.size();
            switch (size) {
                case 0: {
                    return ImmutableMap.of();
                }
                case 1: {
                    return new SingletonImmutableMap<K, V>(Iterables.getOnlyElement(entries));
                }
                default: {
                    final Entry<?, ?>[] entryArray = entries.toArray(new Entry[entries.size()]);
                    return new RegularImmutableMap<K, V>(entryArray);
                }
            }
        }
    }
    
    static class SerializedForm implements Serializable
    {
        private final Object[] keys;
        private final Object[] values;
        private static final long serialVersionUID = 0L;
        
        SerializedForm(final ImmutableMap<?, ?> map) {
            this.keys = new Object[map.size()];
            this.values = new Object[map.size()];
            int i = 0;
            for (final Entry<?, ?> entry : map.entrySet()) {
                this.keys[i] = entry.getKey();
                this.values[i] = entry.getValue();
                ++i;
            }
        }
        
        Object readResolve() {
            final Builder<Object, Object> builder = new Builder<Object, Object>();
            return this.createMap(builder);
        }
        
        Object createMap(final Builder<Object, Object> builder) {
            for (int i = 0; i < this.keys.length; ++i) {
                builder.put(this.keys[i], this.values[i]);
            }
            return builder.build();
        }
    }
}
