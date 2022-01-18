// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.annotations.GwtIncompatible;
import java.util.Collections;
import java.util.List;
import com.google.common.annotations.Beta;
import java.util.Arrays;
import com.google.common.base.Preconditions;
import java.util.TreeMap;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Collection;
import javax.annotation.Nullable;
import java.util.Map;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;

@GwtCompatible(emulated = true)
public abstract class ImmutableMultimap<K, V> implements Multimap<K, V>, Serializable
{
    final transient ImmutableMap<K, ? extends ImmutableCollection<V>> map;
    final transient int size;
    private transient ImmutableCollection<Map.Entry<K, V>> entries;
    private transient ImmutableMultiset<K> keys;
    private transient ImmutableCollection<V> values;
    private static final long serialVersionUID = 0L;
    
    public static <K, V> ImmutableMultimap<K, V> of() {
        return (ImmutableMultimap<K, V>)ImmutableListMultimap.of();
    }
    
    public static <K, V> ImmutableMultimap<K, V> of(final K k1, final V v1) {
        return ImmutableListMultimap.of(k1, v1);
    }
    
    public static <K, V> ImmutableMultimap<K, V> of(final K k1, final V v1, final K k2, final V v2) {
        return ImmutableListMultimap.of(k1, v1, k2, v2);
    }
    
    public static <K, V> ImmutableMultimap<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3) {
        return ImmutableListMultimap.of(k1, v1, k2, v2, k3, v3);
    }
    
    public static <K, V> ImmutableMultimap<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3, final K k4, final V v4) {
        return ImmutableListMultimap.of(k1, v1, k2, v2, k3, v3, k4, v4);
    }
    
    public static <K, V> ImmutableMultimap<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3, final K k4, final V v4, final K k5, final V v5) {
        return ImmutableListMultimap.of(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
    }
    
    public static <K, V> Builder<K, V> builder() {
        return new Builder<K, V>();
    }
    
    public static <K, V> ImmutableMultimap<K, V> copyOf(final Multimap<? extends K, ? extends V> multimap) {
        if (multimap instanceof ImmutableMultimap) {
            final ImmutableMultimap<K, V> kvMultimap = (ImmutableMultimap<K, V>)(ImmutableMultimap)multimap;
            if (!kvMultimap.isPartialView()) {
                return kvMultimap;
            }
        }
        return (ImmutableMultimap<K, V>)ImmutableListMultimap.copyOf((Multimap<?, ?>)multimap);
    }
    
    ImmutableMultimap(final ImmutableMap<K, ? extends ImmutableCollection<V>> map, final int size) {
        this.map = map;
        this.size = size;
    }
    
    public ImmutableCollection<V> removeAll(final Object key) {
        throw new UnsupportedOperationException();
    }
    
    public ImmutableCollection<V> replaceValues(final K key, final Iterable<? extends V> values) {
        throw new UnsupportedOperationException();
    }
    
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public abstract ImmutableCollection<V> get(final K p0);
    
    public boolean put(final K key, final V value) {
        throw new UnsupportedOperationException();
    }
    
    public boolean putAll(final K key, final Iterable<? extends V> values) {
        throw new UnsupportedOperationException();
    }
    
    public boolean putAll(final Multimap<? extends K, ? extends V> multimap) {
        throw new UnsupportedOperationException();
    }
    
    public boolean remove(final Object key, final Object value) {
        throw new UnsupportedOperationException();
    }
    
    boolean isPartialView() {
        return this.map.isPartialView();
    }
    
    public boolean containsEntry(@Nullable final Object key, @Nullable final Object value) {
        final Collection<V> values = (Collection<V>)this.map.get(key);
        return values != null && values.contains(value);
    }
    
    public boolean containsKey(@Nullable final Object key) {
        return this.map.containsKey(key);
    }
    
    public boolean containsValue(@Nullable final Object value) {
        for (final Collection<V> valueCollection : this.map.values()) {
            if (valueCollection.contains(value)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public int size() {
        return this.size;
    }
    
    public boolean equals(@Nullable final Object object) {
        if (object instanceof Multimap) {
            final Multimap<?, ?> that = (Multimap<?, ?>)object;
            return this.map.equals(that.asMap());
        }
        return false;
    }
    
    public int hashCode() {
        return this.map.hashCode();
    }
    
    public String toString() {
        return this.map.toString();
    }
    
    public ImmutableSet<K> keySet() {
        return this.map.keySet();
    }
    
    public ImmutableMap<K, Collection<V>> asMap() {
        return (ImmutableMap<K, Collection<V>>)this.map;
    }
    
    public ImmutableCollection<Map.Entry<K, V>> entries() {
        final ImmutableCollection<Map.Entry<K, V>> result = this.entries;
        return (result == null) ? (this.entries = (ImmutableCollection<Map.Entry<K, V>>)new EntryCollection((ImmutableMultimap<Object, Object>)this)) : result;
    }
    
    public ImmutableMultiset<K> keys() {
        final ImmutableMultiset<K> result = this.keys;
        return (result == null) ? (this.keys = this.createKeys()) : result;
    }
    
    private ImmutableMultiset<K> createKeys() {
        final ImmutableMultiset.Builder<K> builder = ImmutableMultiset.builder();
        for (final Map.Entry<K, ? extends ImmutableCollection<V>> entry : this.map.entrySet()) {
            builder.addCopies(entry.getKey(), ((ImmutableCollection)entry.getValue()).size());
        }
        return builder.build();
    }
    
    public ImmutableCollection<V> values() {
        final ImmutableCollection<V> result = this.values;
        return (result == null) ? (this.values = new Values<V>(this)) : result;
    }
    
    private static class BuilderMultimap<K, V> extends AbstractMultimap<K, V>
    {
        private static final long serialVersionUID = 0L;
        
        BuilderMultimap() {
            super(new LinkedHashMap());
        }
        
        Collection<V> createCollection() {
            return (Collection<V>)Lists.newArrayList();
        }
    }
    
    private static class SortedKeyBuilderMultimap<K, V> extends AbstractMultimap<K, V>
    {
        private static final long serialVersionUID = 0L;
        
        SortedKeyBuilderMultimap(final Comparator<? super K> keyComparator, final Multimap<K, V> multimap) {
            super(new TreeMap(keyComparator));
            this.putAll((Multimap<? extends K, ? extends V>)multimap);
        }
        
        Collection<V> createCollection() {
            return (Collection<V>)Lists.newArrayList();
        }
    }
    
    public static class Builder<K, V>
    {
        Multimap<K, V> builderMultimap;
        Comparator<? super V> valueComparator;
        
        public Builder() {
            this.builderMultimap = new BuilderMultimap<K, V>();
        }
        
        public Builder<K, V> put(final K key, final V value) {
            this.builderMultimap.put(Preconditions.checkNotNull(key), Preconditions.checkNotNull(value));
            return this;
        }
        
        public Builder<K, V> putAll(final K key, final Iterable<? extends V> values) {
            final Collection<V> valueList = this.builderMultimap.get(Preconditions.checkNotNull(key));
            for (final V value : values) {
                valueList.add(Preconditions.checkNotNull(value));
            }
            return this;
        }
        
        public Builder<K, V> putAll(final K key, final V... values) {
            return this.putAll(key, (Iterable<? extends V>)Arrays.asList(values));
        }
        
        public Builder<K, V> putAll(final Multimap<? extends K, ? extends V> multimap) {
            for (final Map.Entry<? extends K, ? extends Collection<? extends V>> entry : multimap.asMap().entrySet()) {
                this.putAll(entry.getKey(), (Iterable<? extends V>)entry.getValue());
            }
            return this;
        }
        
        @Beta
        public Builder<K, V> orderKeysBy(final Comparator<? super K> keyComparator) {
            this.builderMultimap = new SortedKeyBuilderMultimap<K, V>(Preconditions.checkNotNull(keyComparator), this.builderMultimap);
            return this;
        }
        
        @Beta
        public Builder<K, V> orderValuesBy(final Comparator<? super V> valueComparator) {
            this.valueComparator = Preconditions.checkNotNull(valueComparator);
            return this;
        }
        
        public ImmutableMultimap<K, V> build() {
            if (this.valueComparator != null) {
                for (final Collection<V> values : this.builderMultimap.asMap().values()) {
                    final List<V> list = (List<V>)(List)values;
                    Collections.sort(list, this.valueComparator);
                }
            }
            return ImmutableMultimap.copyOf((Multimap<? extends K, ? extends V>)this.builderMultimap);
        }
    }
    
    @GwtIncompatible("java serialization is not supported")
    static class FieldSettersHolder
    {
        static final Serialization.FieldSetter<ImmutableMultimap> MAP_FIELD_SETTER;
        static final Serialization.FieldSetter<ImmutableMultimap> SIZE_FIELD_SETTER;
        
        static {
            MAP_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "map");
            SIZE_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "size");
        }
    }
    
    private static class EntryCollection<K, V> extends ImmutableCollection<Map.Entry<K, V>>
    {
        final ImmutableMultimap<K, V> multimap;
        private static final long serialVersionUID = 0L;
        
        EntryCollection(final ImmutableMultimap<K, V> multimap) {
            this.multimap = multimap;
        }
        
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            final Iterator<? extends Map.Entry<K, ? extends ImmutableCollection<V>>> mapIterator = this.multimap.map.entrySet().iterator();
            return new UnmodifiableIterator<Map.Entry<K, V>>() {
                K key;
                Iterator<V> valueIterator;
                
                public boolean hasNext() {
                    return (this.key != null && this.valueIterator.hasNext()) || mapIterator.hasNext();
                }
                
                public Map.Entry<K, V> next() {
                    if (this.key == null || !this.valueIterator.hasNext()) {
                        final Map.Entry<K, ? extends ImmutableCollection<V>> entry = mapIterator.next();
                        this.key = entry.getKey();
                        this.valueIterator = (Iterator<V>)((ImmutableCollection)entry.getValue()).iterator();
                    }
                    return Maps.immutableEntry(this.key, this.valueIterator.next());
                }
            };
        }
        
        boolean isPartialView() {
            return this.multimap.isPartialView();
        }
        
        public int size() {
            return this.multimap.size();
        }
        
        public boolean contains(final Object object) {
            if (object instanceof Map.Entry) {
                final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)object;
                return this.multimap.containsEntry(entry.getKey(), entry.getValue());
            }
            return false;
        }
    }
    
    private static class Values<V> extends ImmutableCollection<V>
    {
        final ImmutableMultimap<?, V> multimap;
        private static final long serialVersionUID = 0L;
        
        Values(final ImmutableMultimap<?, V> multimap) {
            this.multimap = multimap;
        }
        
        public UnmodifiableIterator<V> iterator() {
            final Iterator<? extends Map.Entry<?, V>> entryIterator = this.multimap.entries().iterator();
            return new UnmodifiableIterator<V>() {
                public boolean hasNext() {
                    return entryIterator.hasNext();
                }
                
                public V next() {
                    return entryIterator.next().getValue();
                }
            };
        }
        
        public int size() {
            return this.multimap.size();
        }
        
        boolean isPartialView() {
            return true;
        }
    }
}
