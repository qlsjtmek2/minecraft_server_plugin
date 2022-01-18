// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.AbstractCollection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.AbstractSet;
import com.google.common.base.Joiner;
import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.Comparator;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import java.util.Collections;
import java.util.Iterator;
import com.google.common.base.Preconditions;
import java.util.SortedSet;
import java.util.Set;
import java.util.List;
import com.google.common.base.Supplier;
import java.util.Collection;
import java.util.Map;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
public final class Multimaps
{
    public static <K, V> Multimap<K, V> newMultimap(final Map<K, Collection<V>> map, final Supplier<? extends Collection<V>> factory) {
        return new CustomMultimap<K, V>(map, factory);
    }
    
    public static <K, V> ListMultimap<K, V> newListMultimap(final Map<K, Collection<V>> map, final Supplier<? extends List<V>> factory) {
        return new CustomListMultimap<K, V>(map, factory);
    }
    
    public static <K, V> SetMultimap<K, V> newSetMultimap(final Map<K, Collection<V>> map, final Supplier<? extends Set<V>> factory) {
        return new CustomSetMultimap<K, V>(map, factory);
    }
    
    public static <K, V> SortedSetMultimap<K, V> newSortedSetMultimap(final Map<K, Collection<V>> map, final Supplier<? extends SortedSet<V>> factory) {
        return new CustomSortedSetMultimap<K, V>(map, factory);
    }
    
    public static <K, V, M extends Multimap<K, V>> M invertFrom(final Multimap<? extends V, ? extends K> source, final M dest) {
        Preconditions.checkNotNull(dest);
        for (final Map.Entry<? extends V, ? extends K> entry : source.entries()) {
            dest.put((K)entry.getValue(), (V)entry.getKey());
        }
        return dest;
    }
    
    public static <K, V> Multimap<K, V> synchronizedMultimap(final Multimap<K, V> multimap) {
        return Synchronized.multimap(multimap, null);
    }
    
    public static <K, V> Multimap<K, V> unmodifiableMultimap(final Multimap<K, V> delegate) {
        if (delegate instanceof UnmodifiableMultimap || delegate instanceof ImmutableMultimap) {
            return delegate;
        }
        return new UnmodifiableMultimap<K, V>(delegate);
    }
    
    @Deprecated
    public static <K, V> Multimap<K, V> unmodifiableMultimap(final ImmutableMultimap<K, V> delegate) {
        return Preconditions.checkNotNull(delegate);
    }
    
    public static <K, V> SetMultimap<K, V> synchronizedSetMultimap(final SetMultimap<K, V> multimap) {
        return Synchronized.setMultimap(multimap, null);
    }
    
    public static <K, V> SetMultimap<K, V> unmodifiableSetMultimap(final SetMultimap<K, V> delegate) {
        if (delegate instanceof UnmodifiableSetMultimap || delegate instanceof ImmutableSetMultimap) {
            return delegate;
        }
        return new UnmodifiableSetMultimap<K, V>(delegate);
    }
    
    @Deprecated
    public static <K, V> SetMultimap<K, V> unmodifiableSetMultimap(final ImmutableSetMultimap<K, V> delegate) {
        return Preconditions.checkNotNull(delegate);
    }
    
    public static <K, V> SortedSetMultimap<K, V> synchronizedSortedSetMultimap(final SortedSetMultimap<K, V> multimap) {
        return Synchronized.sortedSetMultimap(multimap, null);
    }
    
    public static <K, V> SortedSetMultimap<K, V> unmodifiableSortedSetMultimap(final SortedSetMultimap<K, V> delegate) {
        if (delegate instanceof UnmodifiableSortedSetMultimap) {
            return delegate;
        }
        return new UnmodifiableSortedSetMultimap<K, V>(delegate);
    }
    
    public static <K, V> ListMultimap<K, V> synchronizedListMultimap(final ListMultimap<K, V> multimap) {
        return Synchronized.listMultimap(multimap, null);
    }
    
    public static <K, V> ListMultimap<K, V> unmodifiableListMultimap(final ListMultimap<K, V> delegate) {
        if (delegate instanceof UnmodifiableListMultimap || delegate instanceof ImmutableListMultimap) {
            return delegate;
        }
        return new UnmodifiableListMultimap<K, V>(delegate);
    }
    
    @Deprecated
    public static <K, V> ListMultimap<K, V> unmodifiableListMultimap(final ImmutableListMultimap<K, V> delegate) {
        return Preconditions.checkNotNull(delegate);
    }
    
    private static <V> Collection<V> unmodifiableValueCollection(final Collection<V> collection) {
        if (collection instanceof SortedSet) {
            return (Collection<V>)Collections.unmodifiableSortedSet((SortedSet<Object>)(SortedSet)collection);
        }
        if (collection instanceof Set) {
            return (Collection<V>)Collections.unmodifiableSet((Set<?>)(Set)collection);
        }
        if (collection instanceof List) {
            return (Collection<V>)Collections.unmodifiableList((List<?>)(List)collection);
        }
        return Collections.unmodifiableCollection((Collection<? extends V>)collection);
    }
    
    private static <K, V> Map.Entry<K, Collection<V>> unmodifiableAsMapEntry(final Map.Entry<K, Collection<V>> entry) {
        Preconditions.checkNotNull(entry);
        return new AbstractMapEntry<K, Collection<V>>() {
            public K getKey() {
                return entry.getKey();
            }
            
            public Collection<V> getValue() {
                return (Collection<V>)unmodifiableValueCollection((Collection<Object>)entry.getValue());
            }
        };
    }
    
    private static <K, V> Collection<Map.Entry<K, V>> unmodifiableEntries(final Collection<Map.Entry<K, V>> entries) {
        if (entries instanceof Set) {
            return (Collection<Map.Entry<K, V>>)Maps.unmodifiableEntrySet((Set<Map.Entry<Object, Object>>)(Set)entries);
        }
        return (Collection<Map.Entry<K, V>>)new Maps.UnmodifiableEntries(Collections.unmodifiableCollection((Collection<? extends Map.Entry<Object, Object>>)entries));
    }
    
    private static <K, V> Set<Map.Entry<K, Collection<V>>> unmodifiableAsMapEntries(final Set<Map.Entry<K, Collection<V>>> asMapEntries) {
        return (Set<Map.Entry<K, Collection<V>>>)new UnmodifiableAsMapEntries(Collections.unmodifiableSet((Set<? extends Map.Entry<Object, Collection<Object>>>)asMapEntries));
    }
    
    public static <K, V> SetMultimap<K, V> forMap(final Map<K, V> map) {
        return new MapMultimap<K, V>(map);
    }
    
    @Beta
    public static <K, V1, V2> Multimap<K, V2> transformValues(final Multimap<K, V1> fromMultimap, final Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        final Maps.EntryTransformer<K, V1, V2> transformer = new Maps.EntryTransformer<K, V1, V2>() {
            public V2 transformEntry(final K key, final V1 value) {
                return function.apply(value);
            }
        };
        return transformEntries(fromMultimap, transformer);
    }
    
    @Beta
    public static <K, V1, V2> Multimap<K, V2> transformEntries(final Multimap<K, V1> fromMap, final Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
        return new TransformedEntriesMultimap<K, Object, V2>(fromMap, transformer);
    }
    
    @Beta
    public static <K, V1, V2> ListMultimap<K, V2> transformValues(final ListMultimap<K, V1> fromMultimap, final Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        final Maps.EntryTransformer<K, V1, V2> transformer = new Maps.EntryTransformer<K, V1, V2>() {
            public V2 transformEntry(final K key, final V1 value) {
                return function.apply(value);
            }
        };
        return transformEntries(fromMultimap, transformer);
    }
    
    @Beta
    public static <K, V1, V2> ListMultimap<K, V2> transformEntries(final ListMultimap<K, V1> fromMap, final Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
        return new TransformedEntriesListMultimap<K, Object, V2>(fromMap, transformer);
    }
    
    public static <K, V> ImmutableListMultimap<K, V> index(final Iterable<V> values, final Function<? super V, K> keyFunction) {
        return index(values.iterator(), keyFunction);
    }
    
    @Deprecated
    @Beta
    public static <K, V, I extends java.lang.Object> ImmutableListMultimap<K, V> index(final I values, final Function<? super V, K> keyFunction) {
        final Iterable<V> valuesIterable = Preconditions.checkNotNull((Iterable<V>)values);
        return index(valuesIterable, keyFunction);
    }
    
    public static <K, V> ImmutableListMultimap<K, V> index(final Iterator<V> values, final Function<? super V, K> keyFunction) {
        Preconditions.checkNotNull(keyFunction);
        final ImmutableListMultimap.Builder<K, V> builder = ImmutableListMultimap.builder();
        while (values.hasNext()) {
            final V value = values.next();
            Preconditions.checkNotNull(value, values);
            builder.put(keyFunction.apply((Object)value), value);
        }
        return builder.build();
    }
    
    private static class CustomMultimap<K, V> extends AbstractMultimap<K, V>
    {
        transient Supplier<? extends Collection<V>> factory;
        @GwtIncompatible("java serialization not supported")
        private static final long serialVersionUID = 0L;
        
        CustomMultimap(final Map<K, Collection<V>> map, final Supplier<? extends Collection<V>> factory) {
            super(map);
            this.factory = Preconditions.checkNotNull(factory);
        }
        
        protected Collection<V> createCollection() {
            return (Collection<V>)this.factory.get();
        }
        
        @GwtIncompatible("java.io.ObjectOutputStream")
        private void writeObject(final ObjectOutputStream stream) throws IOException {
            stream.defaultWriteObject();
            stream.writeObject(this.factory);
            stream.writeObject(this.backingMap());
        }
        
        @GwtIncompatible("java.io.ObjectInputStream")
        private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            this.factory = (Supplier<? extends Collection<V>>)stream.readObject();
            final Map<K, Collection<V>> map = (Map<K, Collection<V>>)stream.readObject();
            this.setMap(map);
        }
    }
    
    private static class CustomListMultimap<K, V> extends AbstractListMultimap<K, V>
    {
        transient Supplier<? extends List<V>> factory;
        @GwtIncompatible("java serialization not supported")
        private static final long serialVersionUID = 0L;
        
        CustomListMultimap(final Map<K, Collection<V>> map, final Supplier<? extends List<V>> factory) {
            super(map);
            this.factory = Preconditions.checkNotNull(factory);
        }
        
        protected List<V> createCollection() {
            return (List<V>)this.factory.get();
        }
        
        @GwtIncompatible("java.io.ObjectOutputStream")
        private void writeObject(final ObjectOutputStream stream) throws IOException {
            stream.defaultWriteObject();
            stream.writeObject(this.factory);
            stream.writeObject(this.backingMap());
        }
        
        @GwtIncompatible("java.io.ObjectInputStream")
        private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            this.factory = (Supplier<? extends List<V>>)stream.readObject();
            final Map<K, Collection<V>> map = (Map<K, Collection<V>>)stream.readObject();
            this.setMap(map);
        }
    }
    
    private static class CustomSetMultimap<K, V> extends AbstractSetMultimap<K, V>
    {
        transient Supplier<? extends Set<V>> factory;
        @GwtIncompatible("not needed in emulated source")
        private static final long serialVersionUID = 0L;
        
        CustomSetMultimap(final Map<K, Collection<V>> map, final Supplier<? extends Set<V>> factory) {
            super(map);
            this.factory = Preconditions.checkNotNull(factory);
        }
        
        protected Set<V> createCollection() {
            return (Set<V>)this.factory.get();
        }
        
        @GwtIncompatible("java.io.ObjectOutputStream")
        private void writeObject(final ObjectOutputStream stream) throws IOException {
            stream.defaultWriteObject();
            stream.writeObject(this.factory);
            stream.writeObject(this.backingMap());
        }
        
        @GwtIncompatible("java.io.ObjectInputStream")
        private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            this.factory = (Supplier<? extends Set<V>>)stream.readObject();
            final Map<K, Collection<V>> map = (Map<K, Collection<V>>)stream.readObject();
            this.setMap(map);
        }
    }
    
    private static class CustomSortedSetMultimap<K, V> extends AbstractSortedSetMultimap<K, V>
    {
        transient Supplier<? extends SortedSet<V>> factory;
        transient Comparator<? super V> valueComparator;
        @GwtIncompatible("not needed in emulated source")
        private static final long serialVersionUID = 0L;
        
        CustomSortedSetMultimap(final Map<K, Collection<V>> map, final Supplier<? extends SortedSet<V>> factory) {
            super(map);
            this.factory = Preconditions.checkNotNull(factory);
            this.valueComparator = (Comparator<? super V>)((SortedSet)factory.get()).comparator();
        }
        
        protected SortedSet<V> createCollection() {
            return (SortedSet<V>)this.factory.get();
        }
        
        public Comparator<? super V> valueComparator() {
            return this.valueComparator;
        }
        
        @GwtIncompatible("java.io.ObjectOutputStream")
        private void writeObject(final ObjectOutputStream stream) throws IOException {
            stream.defaultWriteObject();
            stream.writeObject(this.factory);
            stream.writeObject(this.backingMap());
        }
        
        @GwtIncompatible("java.io.ObjectInputStream")
        private void readObject(final ObjectInputStream stream) throws IOException, ClassNotFoundException {
            stream.defaultReadObject();
            this.factory = (Supplier<? extends SortedSet<V>>)stream.readObject();
            this.valueComparator = (Comparator<? super V>)((SortedSet)this.factory.get()).comparator();
            final Map<K, Collection<V>> map = (Map<K, Collection<V>>)stream.readObject();
            this.setMap(map);
        }
    }
    
    private static class UnmodifiableMultimap<K, V> extends ForwardingMultimap<K, V> implements Serializable
    {
        final Multimap<K, V> delegate;
        transient Collection<Map.Entry<K, V>> entries;
        transient Multiset<K> keys;
        transient Set<K> keySet;
        transient Collection<V> values;
        transient Map<K, Collection<V>> map;
        private static final long serialVersionUID = 0L;
        
        UnmodifiableMultimap(final Multimap<K, V> delegate) {
            this.delegate = Preconditions.checkNotNull(delegate);
        }
        
        protected Multimap<K, V> delegate() {
            return this.delegate;
        }
        
        public void clear() {
            throw new UnsupportedOperationException();
        }
        
        public Map<K, Collection<V>> asMap() {
            Map<K, Collection<V>> result = this.map;
            if (result == null) {
                final Map<K, Collection<V>> unmodifiableMap = Collections.unmodifiableMap((Map<? extends K, ? extends Collection<V>>)this.delegate.asMap());
                result = (this.map = new ForwardingMap<K, Collection<V>>() {
                    Set<Map.Entry<K, Collection<V>>> entrySet;
                    Collection<Collection<V>> asMapValues;
                    
                    protected Map<K, Collection<V>> delegate() {
                        return unmodifiableMap;
                    }
                    
                    public Set<Map.Entry<K, Collection<V>>> entrySet() {
                        final Set<Map.Entry<K, Collection<V>>> result = this.entrySet;
                        return (result == null) ? (this.entrySet = (Set<Map.Entry<K, Collection<V>>>)unmodifiableAsMapEntries((Set<Map.Entry<Object, Collection<Object>>>)unmodifiableMap.entrySet())) : result;
                    }
                    
                    public Collection<V> get(final Object key) {
                        final Collection<V> collection = unmodifiableMap.get(key);
                        return (Collection<V>)((collection == null) ? null : unmodifiableValueCollection((Collection<Object>)collection));
                    }
                    
                    public Collection<Collection<V>> values() {
                        final Collection<Collection<V>> result = this.asMapValues;
                        return (result == null) ? (this.asMapValues = (Collection<Collection<V>>)new UnmodifiableAsMapValues(unmodifiableMap.values())) : result;
                    }
                    
                    public boolean containsValue(final Object o) {
                        return this.values().contains(o);
                    }
                });
            }
            return result;
        }
        
        public Collection<Map.Entry<K, V>> entries() {
            Collection<Map.Entry<K, V>> result = this.entries;
            if (result == null) {
                result = (this.entries = (Collection<Map.Entry<K, V>>)unmodifiableEntries((Collection<Map.Entry<Object, Object>>)this.delegate.entries()));
            }
            return result;
        }
        
        public Collection<V> get(final K key) {
            return (Collection<V>)unmodifiableValueCollection((Collection<Object>)this.delegate.get(key));
        }
        
        public Multiset<K> keys() {
            Multiset<K> result = this.keys;
            if (result == null) {
                result = (this.keys = Multisets.unmodifiableMultiset((Multiset<? extends K>)this.delegate.keys()));
            }
            return result;
        }
        
        public Set<K> keySet() {
            Set<K> result = this.keySet;
            if (result == null) {
                result = (this.keySet = Collections.unmodifiableSet((Set<? extends K>)this.delegate.keySet()));
            }
            return result;
        }
        
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
        
        public Collection<V> removeAll(final Object key) {
            throw new UnsupportedOperationException();
        }
        
        public Collection<V> replaceValues(final K key, final Iterable<? extends V> values) {
            throw new UnsupportedOperationException();
        }
        
        public Collection<V> values() {
            Collection<V> result = this.values;
            if (result == null) {
                result = (this.values = Collections.unmodifiableCollection((Collection<? extends V>)this.delegate.values()));
            }
            return result;
        }
    }
    
    private static class UnmodifiableAsMapValues<V> extends ForwardingCollection<Collection<V>>
    {
        final Collection<Collection<V>> delegate;
        
        UnmodifiableAsMapValues(final Collection<Collection<V>> delegate) {
            this.delegate = Collections.unmodifiableCollection((Collection<? extends Collection<V>>)delegate);
        }
        
        protected Collection<Collection<V>> delegate() {
            return this.delegate;
        }
        
        public Iterator<Collection<V>> iterator() {
            final Iterator<Collection<V>> iterator = this.delegate.iterator();
            return new Iterator<Collection<V>>() {
                public boolean hasNext() {
                    return iterator.hasNext();
                }
                
                public Collection<V> next() {
                    return (Collection<V>)unmodifiableValueCollection((Collection<Object>)iterator.next());
                }
                
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
        
        public Object[] toArray() {
            return this.standardToArray();
        }
        
        public <T> T[] toArray(final T[] array) {
            return this.standardToArray(array);
        }
        
        public boolean contains(final Object o) {
            return this.standardContains(o);
        }
        
        public boolean containsAll(final Collection<?> c) {
            return this.standardContainsAll(c);
        }
    }
    
    private static class UnmodifiableListMultimap<K, V> extends UnmodifiableMultimap<K, V> implements ListMultimap<K, V>
    {
        private static final long serialVersionUID = 0L;
        
        UnmodifiableListMultimap(final ListMultimap<K, V> delegate) {
            super(delegate);
        }
        
        public ListMultimap<K, V> delegate() {
            return (ListMultimap<K, V>)(ListMultimap)super.delegate();
        }
        
        public List<V> get(final K key) {
            return Collections.unmodifiableList((List<? extends V>)this.delegate().get(key));
        }
        
        public List<V> removeAll(final Object key) {
            throw new UnsupportedOperationException();
        }
        
        public List<V> replaceValues(final K key, final Iterable<? extends V> values) {
            throw new UnsupportedOperationException();
        }
    }
    
    private static class UnmodifiableSetMultimap<K, V> extends UnmodifiableMultimap<K, V> implements SetMultimap<K, V>
    {
        private static final long serialVersionUID = 0L;
        
        UnmodifiableSetMultimap(final SetMultimap<K, V> delegate) {
            super(delegate);
        }
        
        public SetMultimap<K, V> delegate() {
            return (SetMultimap<K, V>)(SetMultimap)super.delegate();
        }
        
        public Set<V> get(final K key) {
            return Collections.unmodifiableSet((Set<? extends V>)this.delegate().get(key));
        }
        
        public Set<Map.Entry<K, V>> entries() {
            return Maps.unmodifiableEntrySet(this.delegate().entries());
        }
        
        public Set<V> removeAll(final Object key) {
            throw new UnsupportedOperationException();
        }
        
        public Set<V> replaceValues(final K key, final Iterable<? extends V> values) {
            throw new UnsupportedOperationException();
        }
    }
    
    private static class UnmodifiableSortedSetMultimap<K, V> extends UnmodifiableSetMultimap<K, V> implements SortedSetMultimap<K, V>
    {
        private static final long serialVersionUID = 0L;
        
        UnmodifiableSortedSetMultimap(final SortedSetMultimap<K, V> delegate) {
            super(delegate);
        }
        
        public SortedSetMultimap<K, V> delegate() {
            return (SortedSetMultimap<K, V>)(SortedSetMultimap)super.delegate();
        }
        
        public SortedSet<V> get(final K key) {
            return Collections.unmodifiableSortedSet(this.delegate().get(key));
        }
        
        public SortedSet<V> removeAll(final Object key) {
            throw new UnsupportedOperationException();
        }
        
        public SortedSet<V> replaceValues(final K key, final Iterable<? extends V> values) {
            throw new UnsupportedOperationException();
        }
        
        public Comparator<? super V> valueComparator() {
            return this.delegate().valueComparator();
        }
    }
    
    static class UnmodifiableAsMapEntries<K, V> extends ForwardingSet<Map.Entry<K, Collection<V>>>
    {
        private final Set<Map.Entry<K, Collection<V>>> delegate;
        
        UnmodifiableAsMapEntries(final Set<Map.Entry<K, Collection<V>>> delegate) {
            this.delegate = delegate;
        }
        
        protected Set<Map.Entry<K, Collection<V>>> delegate() {
            return this.delegate;
        }
        
        public Iterator<Map.Entry<K, Collection<V>>> iterator() {
            final Iterator<Map.Entry<K, Collection<V>>> iterator = this.delegate.iterator();
            return new ForwardingIterator<Map.Entry<K, Collection<V>>>() {
                protected Iterator<Map.Entry<K, Collection<V>>> delegate() {
                    return iterator;
                }
                
                public Map.Entry<K, Collection<V>> next() {
                    return (Map.Entry<K, Collection<V>>)unmodifiableAsMapEntry((Map.Entry<Object, Collection<Object>>)iterator.next());
                }
            };
        }
        
        public Object[] toArray() {
            return this.standardToArray();
        }
        
        public <T> T[] toArray(final T[] array) {
            return this.standardToArray(array);
        }
        
        public boolean contains(final Object o) {
            return Maps.containsEntryImpl(this.delegate(), o);
        }
        
        public boolean containsAll(final Collection<?> c) {
            return this.standardContainsAll(c);
        }
        
        public boolean equals(@Nullable final Object object) {
            return this.standardEquals(object);
        }
    }
    
    private static class MapMultimap<K, V> implements SetMultimap<K, V>, Serializable
    {
        final Map<K, V> map;
        transient Map<K, Collection<V>> asMap;
        private static final Joiner.MapJoiner JOINER;
        private static final long serialVersionUID = 7845222491160860175L;
        
        MapMultimap(final Map<K, V> map) {
            this.map = Preconditions.checkNotNull(map);
        }
        
        public int size() {
            return this.map.size();
        }
        
        public boolean isEmpty() {
            return this.map.isEmpty();
        }
        
        public boolean containsKey(final Object key) {
            return this.map.containsKey(key);
        }
        
        public boolean containsValue(final Object value) {
            return this.map.containsValue(value);
        }
        
        public boolean containsEntry(final Object key, final Object value) {
            return this.map.entrySet().contains(Maps.immutableEntry(key, value));
        }
        
        public Set<V> get(final K key) {
            return new AbstractSet<V>() {
                public Iterator<V> iterator() {
                    return new Iterator<V>() {
                        int i;
                        
                        public boolean hasNext() {
                            return this.i == 0 && MapMultimap.this.map.containsKey(key);
                        }
                        
                        public V next() {
                            if (!this.hasNext()) {
                                throw new NoSuchElementException();
                            }
                            ++this.i;
                            return MapMultimap.this.map.get(key);
                        }
                        
                        public void remove() {
                            Preconditions.checkState(this.i == 1);
                            this.i = -1;
                            MapMultimap.this.map.remove(key);
                        }
                    };
                }
                
                public int size() {
                    return MapMultimap.this.map.containsKey(key) ? 1 : 0;
                }
            };
        }
        
        public boolean put(final K key, final V value) {
            throw new UnsupportedOperationException();
        }
        
        public boolean putAll(final K key, final Iterable<? extends V> values) {
            throw new UnsupportedOperationException();
        }
        
        public boolean putAll(final Multimap<? extends K, ? extends V> multimap) {
            throw new UnsupportedOperationException();
        }
        
        public Set<V> replaceValues(final K key, final Iterable<? extends V> values) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final Object key, final Object value) {
            return this.map.entrySet().remove(Maps.immutableEntry(key, value));
        }
        
        public Set<V> removeAll(final Object key) {
            final Set<V> values = new HashSet<V>(2);
            if (!this.map.containsKey(key)) {
                return values;
            }
            values.add(this.map.remove(key));
            return values;
        }
        
        public void clear() {
            this.map.clear();
        }
        
        public Set<K> keySet() {
            return this.map.keySet();
        }
        
        public Multiset<K> keys() {
            return Multisets.forSet(this.map.keySet());
        }
        
        public Collection<V> values() {
            return this.map.values();
        }
        
        public Set<Map.Entry<K, V>> entries() {
            return this.map.entrySet();
        }
        
        public Map<K, Collection<V>> asMap() {
            Map<K, Collection<V>> result = this.asMap;
            if (result == null) {
                result = (this.asMap = new AsMap());
            }
            return result;
        }
        
        public boolean equals(@Nullable final Object object) {
            if (object == this) {
                return true;
            }
            if (object instanceof Multimap) {
                final Multimap<?, ?> that = (Multimap<?, ?>)object;
                return this.size() == that.size() && this.asMap().equals(that.asMap());
            }
            return false;
        }
        
        public int hashCode() {
            return this.map.hashCode();
        }
        
        public String toString() {
            if (this.map.isEmpty()) {
                return "{}";
            }
            final StringBuilder builder = Collections2.newStringBuilderForCollection(this.map.size()).append('{');
            MapMultimap.JOINER.appendTo(builder, (Map<?, ?>)this.map);
            return builder.append("]}").toString();
        }
        
        static {
            JOINER = Joiner.on("], ").withKeyValueSeparator("=[").useForNull("null");
        }
        
        class AsMapEntries extends AbstractSet<Map.Entry<K, Collection<V>>>
        {
            public int size() {
                return MapMultimap.this.map.size();
            }
            
            public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                return new Iterator<Map.Entry<K, Collection<V>>>() {
                    final Iterator<K> keys = MapMultimap.this.map.keySet().iterator();
                    
                    public boolean hasNext() {
                        return this.keys.hasNext();
                    }
                    
                    public Map.Entry<K, Collection<V>> next() {
                        final K key = this.keys.next();
                        return new AbstractMapEntry<K, Collection<V>>() {
                            public K getKey() {
                                return key;
                            }
                            
                            public Collection<V> getValue() {
                                return MapMultimap.this.get(key);
                            }
                        };
                    }
                    
                    public void remove() {
                        this.keys.remove();
                    }
                };
            }
            
            public boolean contains(final Object o) {
                if (!(o instanceof Map.Entry)) {
                    return false;
                }
                final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
                if (!(entry.getValue() instanceof Set)) {
                    return false;
                }
                final Set<?> set = (Set<?>)entry.getValue();
                return set.size() == 1 && MapMultimap.this.containsEntry(entry.getKey(), set.iterator().next());
            }
            
            public boolean remove(final Object o) {
                if (!(o instanceof Map.Entry)) {
                    return false;
                }
                final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
                if (!(entry.getValue() instanceof Set)) {
                    return false;
                }
                final Set<?> set = (Set<?>)entry.getValue();
                return set.size() == 1 && MapMultimap.this.map.entrySet().remove(Maps.immutableEntry(entry.getKey(), set.iterator().next()));
            }
        }
        
        class AsMap extends Maps.ImprovedAbstractMap<K, Collection<V>>
        {
            protected Set<Map.Entry<K, Collection<V>>> createEntrySet() {
                return new AsMapEntries();
            }
            
            public boolean containsKey(final Object key) {
                return MapMultimap.this.map.containsKey(key);
            }
            
            public Collection<V> get(final Object key) {
                final Collection<V> collection = MapMultimap.this.get(key);
                return collection.isEmpty() ? null : collection;
            }
            
            public Collection<V> remove(final Object key) {
                final Collection<V> collection = MapMultimap.this.removeAll(key);
                return collection.isEmpty() ? null : collection;
            }
        }
    }
    
    private static class TransformedEntriesMultimap<K, V1, V2> implements Multimap<K, V2>
    {
        final Multimap<K, V1> fromMultimap;
        final Maps.EntryTransformer<? super K, ? super V1, V2> transformer;
        private transient Map<K, Collection<V2>> asMap;
        private transient Collection<Map.Entry<K, V2>> entries;
        private transient Collection<V2> values;
        
        TransformedEntriesMultimap(final Multimap<K, V1> fromMultimap, final Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
            this.fromMultimap = Preconditions.checkNotNull(fromMultimap);
            this.transformer = Preconditions.checkNotNull(transformer);
        }
        
        Collection<V2> transform(final K key, final Collection<V1> values) {
            return Collections2.transform(values, (Function<? super V1, V2>)new Function<V1, V2>() {
                public V2 apply(final V1 value) {
                    return TransformedEntriesMultimap.this.transformer.transformEntry((Object)key, (Object)value);
                }
            });
        }
        
        public Map<K, Collection<V2>> asMap() {
            if (this.asMap == null) {
                final Map<K, Collection<V2>> aM = Maps.transformEntries(this.fromMultimap.asMap(), (Maps.EntryTransformer<? super K, ? super Collection<V1>, Collection<V2>>)new Maps.EntryTransformer<K, Collection<V1>, Collection<V2>>() {
                    public Collection<V2> transformEntry(final K key, final Collection<V1> value) {
                        return TransformedEntriesMultimap.this.transform(key, value);
                    }
                });
                return this.asMap = aM;
            }
            return this.asMap;
        }
        
        public void clear() {
            this.fromMultimap.clear();
        }
        
        public boolean containsEntry(final Object key, final Object value) {
            final Collection<V2> values = this.get(key);
            return values.contains(value);
        }
        
        public boolean containsKey(final Object key) {
            return this.fromMultimap.containsKey(key);
        }
        
        public boolean containsValue(final Object value) {
            return this.values().contains(value);
        }
        
        public Collection<Map.Entry<K, V2>> entries() {
            if (this.entries == null) {
                final Collection<Map.Entry<K, V2>> es = (Collection<Map.Entry<K, V2>>)new TransformedEntries(this.transformer);
                return this.entries = es;
            }
            return this.entries;
        }
        
        public Collection<V2> get(final K key) {
            return this.transform(key, this.fromMultimap.get(key));
        }
        
        public boolean isEmpty() {
            return this.fromMultimap.isEmpty();
        }
        
        public Set<K> keySet() {
            return this.fromMultimap.keySet();
        }
        
        public Multiset<K> keys() {
            return this.fromMultimap.keys();
        }
        
        public boolean put(final K key, final V2 value) {
            throw new UnsupportedOperationException();
        }
        
        public boolean putAll(final K key, final Iterable<? extends V2> values) {
            throw new UnsupportedOperationException();
        }
        
        public boolean putAll(final Multimap<? extends K, ? extends V2> multimap) {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final Object key, final Object value) {
            return this.get(key).remove(value);
        }
        
        public Collection<V2> removeAll(final Object key) {
            return this.transform(key, this.fromMultimap.removeAll(key));
        }
        
        public Collection<V2> replaceValues(final K key, final Iterable<? extends V2> values) {
            throw new UnsupportedOperationException();
        }
        
        public int size() {
            return this.fromMultimap.size();
        }
        
        public Collection<V2> values() {
            if (this.values == null) {
                final Collection<V2> vs = Collections2.transform(this.fromMultimap.entries(), (Function<? super Map.Entry<K, V1>, V2>)new Function<Map.Entry<K, V1>, V2>() {
                    public V2 apply(final Map.Entry<K, V1> entry) {
                        return TransformedEntriesMultimap.this.transformer.transformEntry((Object)entry.getKey(), (Object)entry.getValue());
                    }
                });
                return this.values = vs;
            }
            return this.values;
        }
        
        public boolean equals(final Object obj) {
            if (obj instanceof Multimap) {
                final Multimap<?, ?> other = (Multimap<?, ?>)obj;
                return this.asMap().equals(other.asMap());
            }
            return false;
        }
        
        public int hashCode() {
            return this.asMap().hashCode();
        }
        
        public String toString() {
            return this.asMap().toString();
        }
        
        private class TransformedEntries extends Collections2.TransformedCollection<Map.Entry<K, V1>, Map.Entry<K, V2>>
        {
            TransformedEntries(final Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
                super(TransformedEntriesMultimap.this.fromMultimap.entries(), new Function<Map.Entry<K, V1>, Map.Entry<K, V2>>() {
                    public Map.Entry<K, V2> apply(final Map.Entry<K, V1> entry) {
                        return new AbstractMapEntry<K, V2>() {
                            public K getKey() {
                                return entry.getKey();
                            }
                            
                            public V2 getValue() {
                                return transformer.transformEntry(entry.getKey(), entry.getValue());
                            }
                        };
                    }
                });
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Map.Entry) {
                    final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
                    return TransformedEntriesMultimap.this.containsEntry(entry.getKey(), entry.getValue());
                }
                return false;
            }
            
            public boolean remove(final Object o) {
                if (o instanceof Map.Entry) {
                    final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
                    final Collection<V2> values = TransformedEntriesMultimap.this.get(entry.getKey());
                    return values.remove(entry.getValue());
                }
                return false;
            }
        }
    }
    
    private static final class TransformedEntriesListMultimap<K, V1, V2> extends TransformedEntriesMultimap<K, V1, V2> implements ListMultimap<K, V2>
    {
        TransformedEntriesListMultimap(final ListMultimap<K, V1> fromMultimap, final Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
            super(fromMultimap, transformer);
        }
        
        List<V2> transform(final K key, final Collection<V1> values) {
            return Lists.transform((List<Object>)(List)values, (Function<? super Object, ? extends V2>)new Function<V1, V2>() {
                public V2 apply(final V1 value) {
                    return (V2)TransformedEntriesListMultimap.this.transformer.transformEntry((Object)key, (Object)value);
                }
            });
        }
        
        public List<V2> get(final K key) {
            return this.transform(key, this.fromMultimap.get(key));
        }
        
        public List<V2> removeAll(final Object key) {
            return this.transform(key, this.fromMultimap.removeAll(key));
        }
        
        public List<V2> replaceValues(final K key, final Iterable<? extends V2> values) {
            throw new UnsupportedOperationException();
        }
    }
    
    abstract static class Keys<K, V> extends AbstractMultiset<K>
    {
        private Set<Multiset.Entry<K>> entrySet;
        
        abstract Multimap<K, V> multimap();
        
        public Set<Multiset.Entry<K>> entrySet() {
            return (this.entrySet == null) ? (this.entrySet = this.createEntrySet()) : this.entrySet;
        }
        
        Iterator<Multiset.Entry<K>> entryIterator() {
            final Iterator<Map.Entry<K, Collection<V>>> backingIterator = this.multimap().asMap().entrySet().iterator();
            return new Iterator<Multiset.Entry<K>>() {
                public boolean hasNext() {
                    return backingIterator.hasNext();
                }
                
                public Multiset.Entry<K> next() {
                    final Map.Entry<K, Collection<V>> backingEntry = backingIterator.next();
                    return new Multisets.AbstractEntry<K>() {
                        public K getElement() {
                            return backingEntry.getKey();
                        }
                        
                        public int getCount() {
                            return backingEntry.getValue().size();
                        }
                    };
                }
                
                public void remove() {
                    backingIterator.remove();
                }
            };
        }
        
        int distinctElements() {
            return this.multimap().asMap().size();
        }
        
        Set<Multiset.Entry<K>> createEntrySet() {
            return (Set<Multiset.Entry<K>>)new KeysEntrySet();
        }
        
        public boolean contains(@Nullable final Object element) {
            return this.multimap().containsKey(element);
        }
        
        public Iterator<K> iterator() {
            return Iterators.transform(this.multimap().entries().iterator(), (Function<? super Map.Entry<K, V>, ? extends K>)new Function<Map.Entry<K, V>, K>() {
                public K apply(final Map.Entry<K, V> entry) {
                    return entry.getKey();
                }
            });
        }
        
        public int count(@Nullable final Object element) {
            try {
                if (this.multimap().containsKey(element)) {
                    final Collection<V> values = this.multimap().asMap().get(element);
                    return (values == null) ? 0 : values.size();
                }
                return 0;
            }
            catch (ClassCastException e) {
                return 0;
            }
            catch (NullPointerException e2) {
                return 0;
            }
        }
        
        public int remove(@Nullable final Object element, final int occurrences) {
            Preconditions.checkArgument(occurrences >= 0);
            if (occurrences == 0) {
                return this.count(element);
            }
            Collection<V> values;
            try {
                values = this.multimap().asMap().get(element);
            }
            catch (ClassCastException e) {
                return 0;
            }
            catch (NullPointerException e2) {
                return 0;
            }
            if (values == null) {
                return 0;
            }
            final int oldCount = values.size();
            if (occurrences >= oldCount) {
                values.clear();
            }
            else {
                final Iterator<V> iterator = values.iterator();
                for (int i = 0; i < occurrences; ++i) {
                    iterator.next();
                    iterator.remove();
                }
            }
            return oldCount;
        }
        
        public void clear() {
            this.multimap().clear();
        }
        
        public Set<K> elementSet() {
            return this.multimap().keySet();
        }
        
        class KeysEntrySet extends Multisets.EntrySet<K>
        {
            Multiset<K> multiset() {
                return (Multiset<K>)Keys.this;
            }
            
            public Iterator<Multiset.Entry<K>> iterator() {
                return Keys.this.entryIterator();
            }
            
            public int size() {
                return Keys.this.distinctElements();
            }
            
            public boolean isEmpty() {
                return Keys.this.multimap().isEmpty();
            }
            
            public boolean contains(@Nullable final Object o) {
                if (o instanceof Multiset.Entry) {
                    final Multiset.Entry<?> entry = (Multiset.Entry<?>)o;
                    final Collection<V> collection = Keys.this.multimap().asMap().get(entry.getElement());
                    return collection != null && collection.size() == entry.getCount();
                }
                return false;
            }
            
            public boolean remove(@Nullable final Object o) {
                if (o instanceof Multiset.Entry) {
                    final Multiset.Entry<?> entry = (Multiset.Entry<?>)o;
                    final Collection<V> collection = Keys.this.multimap().asMap().get(entry.getElement());
                    if (collection != null && collection.size() == entry.getCount()) {
                        collection.clear();
                        return true;
                    }
                }
                return false;
            }
        }
    }
    
    abstract static class Values<K, V> extends AbstractCollection<V>
    {
        abstract Multimap<K, V> multimap();
        
        public Iterator<V> iterator() {
            final Iterator<Map.Entry<K, V>> backingIterator = this.multimap().entries().iterator();
            return new Iterator<V>() {
                public boolean hasNext() {
                    return backingIterator.hasNext();
                }
                
                public V next() {
                    return backingIterator.next().getValue();
                }
                
                public void remove() {
                    backingIterator.remove();
                }
            };
        }
        
        public int size() {
            return this.multimap().size();
        }
        
        public boolean contains(@Nullable final Object o) {
            return this.multimap().containsValue(o);
        }
        
        public void clear() {
            this.multimap().clear();
        }
    }
    
    abstract static class Entries<K, V> extends AbstractCollection<Map.Entry<K, V>>
    {
        abstract Multimap<K, V> multimap();
        
        public int size() {
            return this.multimap().size();
        }
        
        public boolean contains(@Nullable final Object o) {
            if (o instanceof Map.Entry) {
                final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
                return this.multimap().containsEntry(entry.getKey(), entry.getValue());
            }
            return false;
        }
        
        public boolean remove(@Nullable final Object o) {
            if (o instanceof Map.Entry) {
                final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
                return this.multimap().remove(entry.getKey(), entry.getValue());
            }
            return false;
        }
        
        public void clear() {
            this.multimap().clear();
        }
    }
    
    abstract static class EntrySet<K, V> extends Entries<K, V> implements Set<Map.Entry<K, V>>
    {
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
        
        public boolean equals(@Nullable final Object obj) {
            return Sets.equalsImpl(this, obj);
        }
    }
    
    abstract static class AsMap<K, V> extends Maps.ImprovedAbstractMap<K, Collection<V>>
    {
        abstract Multimap<K, V> multimap();
        
        public abstract int size();
        
        abstract Iterator<Map.Entry<K, Collection<V>>> entryIterator();
        
        protected Set<Map.Entry<K, Collection<V>>> createEntrySet() {
            return (Set<Map.Entry<K, Collection<V>>>)new EntrySet();
        }
        
        void removeValuesForKey(final Object key) {
            this.multimap().removeAll(key);
        }
        
        public Collection<V> get(final Object key) {
            return this.containsKey(key) ? this.multimap().get((K)key) : null;
        }
        
        public Collection<V> remove(final Object key) {
            return this.containsKey(key) ? this.multimap().removeAll(key) : null;
        }
        
        public Set<K> keySet() {
            return this.multimap().keySet();
        }
        
        public boolean isEmpty() {
            return this.multimap().isEmpty();
        }
        
        public boolean containsKey(final Object key) {
            return this.multimap().containsKey(key);
        }
        
        public void clear() {
            this.multimap().clear();
        }
        
        class EntrySet extends Maps.EntrySet<K, Collection<V>>
        {
            Map<K, Collection<V>> map() {
                return (Map<K, Collection<V>>)AsMap.this;
            }
            
            public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                return AsMap.this.entryIterator();
            }
            
            public boolean remove(final Object o) {
                if (!this.contains(o)) {
                    return false;
                }
                final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
                AsMap.this.removeValuesForKey(entry.getKey());
                return true;
            }
        }
    }
}
