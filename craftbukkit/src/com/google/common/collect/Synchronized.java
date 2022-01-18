// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.ListIterator;
import java.util.Comparator;
import java.util.Iterator;
import java.io.IOException;
import java.io.ObjectOutputStream;
import com.google.common.base.Preconditions;
import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.util.SortedMap;
import java.util.Map;
import java.util.RandomAccess;
import java.util.List;
import java.util.SortedSet;
import com.google.common.annotations.VisibleForTesting;
import java.util.Set;
import javax.annotation.Nullable;
import java.util.Collection;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
final class Synchronized
{
    private static <E> Collection<E> collection(final Collection<E> collection, @Nullable final Object mutex) {
        return new SynchronizedCollection<E>((Collection)collection, mutex);
    }
    
    @VisibleForTesting
    static <E> Set<E> set(final Set<E> set, @Nullable final Object mutex) {
        return new SynchronizedSet<E>(set, mutex);
    }
    
    private static <E> SortedSet<E> sortedSet(final SortedSet<E> set, @Nullable final Object mutex) {
        return new SynchronizedSortedSet<E>(set, mutex);
    }
    
    private static <E> List<E> list(final List<E> list, @Nullable final Object mutex) {
        return (list instanceof RandomAccess) ? new SynchronizedRandomAccessList<E>(list, mutex) : new SynchronizedList<E>(list, mutex);
    }
    
    static <E> Multiset<E> multiset(final Multiset<E> multiset, @Nullable final Object mutex) {
        if (multiset instanceof SynchronizedMultiset || multiset instanceof ImmutableMultiset) {
            return multiset;
        }
        return new SynchronizedMultiset<E>(multiset, mutex);
    }
    
    static <K, V> Multimap<K, V> multimap(final Multimap<K, V> multimap, @Nullable final Object mutex) {
        if (multimap instanceof SynchronizedMultimap || multimap instanceof ImmutableMultimap) {
            return multimap;
        }
        return new SynchronizedMultimap<K, V>(multimap, mutex);
    }
    
    static <K, V> ListMultimap<K, V> listMultimap(final ListMultimap<K, V> multimap, @Nullable final Object mutex) {
        if (multimap instanceof SynchronizedListMultimap || multimap instanceof ImmutableListMultimap) {
            return multimap;
        }
        return new SynchronizedListMultimap<K, V>(multimap, mutex);
    }
    
    static <K, V> SetMultimap<K, V> setMultimap(final SetMultimap<K, V> multimap, @Nullable final Object mutex) {
        if (multimap instanceof SynchronizedSetMultimap || multimap instanceof ImmutableSetMultimap) {
            return multimap;
        }
        return new SynchronizedSetMultimap<K, V>(multimap, mutex);
    }
    
    static <K, V> SortedSetMultimap<K, V> sortedSetMultimap(final SortedSetMultimap<K, V> multimap, @Nullable final Object mutex) {
        if (multimap instanceof SynchronizedSortedSetMultimap) {
            return multimap;
        }
        return new SynchronizedSortedSetMultimap<K, V>(multimap, mutex);
    }
    
    private static <E> Collection<E> typePreservingCollection(final Collection<E> collection, @Nullable final Object mutex) {
        if (collection instanceof SortedSet) {
            return (Collection<E>)sortedSet((SortedSet<Object>)(SortedSet)collection, mutex);
        }
        if (collection instanceof Set) {
            return (Collection<E>)set((Set<Object>)(Set)collection, mutex);
        }
        if (collection instanceof List) {
            return (Collection<E>)list((List<Object>)(List)collection, mutex);
        }
        return (Collection<E>)collection((Collection<Object>)collection, mutex);
    }
    
    private static <E> Set<E> typePreservingSet(final Set<E> set, @Nullable final Object mutex) {
        if (set instanceof SortedSet) {
            return (Set<E>)sortedSet((SortedSet<Object>)(SortedSet)set, mutex);
        }
        return (Set<E>)set((Set<Object>)set, mutex);
    }
    
    @VisibleForTesting
    static <K, V> Map<K, V> map(final Map<K, V> map, @Nullable final Object mutex) {
        return new SynchronizedMap<K, V>(map, mutex);
    }
    
    static <K, V> SortedMap<K, V> sortedMap(final SortedMap<K, V> sortedMap, @Nullable final Object mutex) {
        return new SynchronizedSortedMap<K, V>(sortedMap, mutex);
    }
    
    static <K, V> BiMap<K, V> biMap(final BiMap<K, V> bimap, @Nullable final Object mutex) {
        if (bimap instanceof SynchronizedBiMap || bimap instanceof ImmutableBiMap) {
            return bimap;
        }
        return new SynchronizedBiMap<K, V>((BiMap)bimap, mutex, (BiMap)null);
    }
    
    static class SynchronizedObject implements Serializable
    {
        final Object delegate;
        final Object mutex;
        @GwtIncompatible("not needed in emulated source")
        private static final long serialVersionUID = 0L;
        
        SynchronizedObject(final Object delegate, @Nullable final Object mutex) {
            this.delegate = Preconditions.checkNotNull(delegate);
            this.mutex = ((mutex == null) ? this : mutex);
        }
        
        Object delegate() {
            return this.delegate;
        }
        
        public String toString() {
            synchronized (this.mutex) {
                return this.delegate.toString();
            }
        }
        
        @GwtIncompatible("java.io.ObjectOutputStream")
        private void writeObject(final ObjectOutputStream stream) throws IOException {
            synchronized (this.mutex) {
                stream.defaultWriteObject();
            }
        }
    }
    
    @VisibleForTesting
    static class SynchronizedCollection<E> extends SynchronizedObject implements Collection<E>
    {
        private static final long serialVersionUID = 0L;
        
        private SynchronizedCollection(final Collection<E> delegate, @Nullable final Object mutex) {
            super(delegate, mutex);
        }
        
        Collection<E> delegate() {
            return (Collection<E>)super.delegate();
        }
        
        public boolean add(final E e) {
            synchronized (this.mutex) {
                return this.delegate().add(e);
            }
        }
        
        public boolean addAll(final Collection<? extends E> c) {
            synchronized (this.mutex) {
                return this.delegate().addAll(c);
            }
        }
        
        public void clear() {
            synchronized (this.mutex) {
                this.delegate().clear();
            }
        }
        
        public boolean contains(final Object o) {
            synchronized (this.mutex) {
                return this.delegate().contains(o);
            }
        }
        
        public boolean containsAll(final Collection<?> c) {
            synchronized (this.mutex) {
                return this.delegate().containsAll(c);
            }
        }
        
        public boolean isEmpty() {
            synchronized (this.mutex) {
                return this.delegate().isEmpty();
            }
        }
        
        public Iterator<E> iterator() {
            return this.delegate().iterator();
        }
        
        public boolean remove(final Object o) {
            synchronized (this.mutex) {
                return this.delegate().remove(o);
            }
        }
        
        public boolean removeAll(final Collection<?> c) {
            synchronized (this.mutex) {
                return this.delegate().removeAll(c);
            }
        }
        
        public boolean retainAll(final Collection<?> c) {
            synchronized (this.mutex) {
                return this.delegate().retainAll(c);
            }
        }
        
        public int size() {
            synchronized (this.mutex) {
                return this.delegate().size();
            }
        }
        
        public Object[] toArray() {
            synchronized (this.mutex) {
                return this.delegate().toArray();
            }
        }
        
        public <T> T[] toArray(final T[] a) {
            synchronized (this.mutex) {
                return this.delegate().toArray(a);
            }
        }
    }
    
    static class SynchronizedSet<E> extends SynchronizedCollection<E> implements Set<E>
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedSet(final Set<E> delegate, @Nullable final Object mutex) {
            super((Collection)delegate, mutex);
        }
        
        Set<E> delegate() {
            return (Set<E>)(Set)super.delegate();
        }
        
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            synchronized (this.mutex) {
                return this.delegate().equals(o);
            }
        }
        
        public int hashCode() {
            synchronized (this.mutex) {
                return this.delegate().hashCode();
            }
        }
    }
    
    static class SynchronizedSortedSet<E> extends SynchronizedSet<E> implements SortedSet<E>
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedSortedSet(final SortedSet<E> delegate, @Nullable final Object mutex) {
            super(delegate, mutex);
        }
        
        SortedSet<E> delegate() {
            return (SortedSet<E>)(SortedSet)super.delegate();
        }
        
        public Comparator<? super E> comparator() {
            synchronized (this.mutex) {
                return this.delegate().comparator();
            }
        }
        
        public SortedSet<E> subSet(final E fromElement, final E toElement) {
            synchronized (this.mutex) {
                return (SortedSet<E>)sortedSet((SortedSet<Object>)this.delegate().subSet(fromElement, toElement), this.mutex);
            }
        }
        
        public SortedSet<E> headSet(final E toElement) {
            synchronized (this.mutex) {
                return (SortedSet<E>)sortedSet((SortedSet<Object>)this.delegate().headSet(toElement), this.mutex);
            }
        }
        
        public SortedSet<E> tailSet(final E fromElement) {
            synchronized (this.mutex) {
                return (SortedSet<E>)sortedSet((SortedSet<Object>)this.delegate().tailSet(fromElement), this.mutex);
            }
        }
        
        public E first() {
            synchronized (this.mutex) {
                return this.delegate().first();
            }
        }
        
        public E last() {
            synchronized (this.mutex) {
                return this.delegate().last();
            }
        }
    }
    
    private static class SynchronizedList<E> extends SynchronizedCollection<E> implements List<E>
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedList(final List<E> delegate, @Nullable final Object mutex) {
            super((Collection)delegate, mutex);
        }
        
        List<E> delegate() {
            return (List<E>)(List)super.delegate();
        }
        
        public void add(final int index, final E element) {
            synchronized (this.mutex) {
                this.delegate().add(index, element);
            }
        }
        
        public boolean addAll(final int index, final Collection<? extends E> c) {
            synchronized (this.mutex) {
                return this.delegate().addAll(index, c);
            }
        }
        
        public E get(final int index) {
            synchronized (this.mutex) {
                return this.delegate().get(index);
            }
        }
        
        public int indexOf(final Object o) {
            synchronized (this.mutex) {
                return this.delegate().indexOf(o);
            }
        }
        
        public int lastIndexOf(final Object o) {
            synchronized (this.mutex) {
                return this.delegate().lastIndexOf(o);
            }
        }
        
        public ListIterator<E> listIterator() {
            return this.delegate().listIterator();
        }
        
        public ListIterator<E> listIterator(final int index) {
            return this.delegate().listIterator(index);
        }
        
        public E remove(final int index) {
            synchronized (this.mutex) {
                return this.delegate().remove(index);
            }
        }
        
        public E set(final int index, final E element) {
            synchronized (this.mutex) {
                return this.delegate().set(index, element);
            }
        }
        
        public List<E> subList(final int fromIndex, final int toIndex) {
            synchronized (this.mutex) {
                return (List<E>)list((List<Object>)this.delegate().subList(fromIndex, toIndex), this.mutex);
            }
        }
        
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            synchronized (this.mutex) {
                return this.delegate().equals(o);
            }
        }
        
        public int hashCode() {
            synchronized (this.mutex) {
                return this.delegate().hashCode();
            }
        }
    }
    
    private static class SynchronizedRandomAccessList<E> extends SynchronizedList<E> implements RandomAccess
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedRandomAccessList(final List<E> list, @Nullable final Object mutex) {
            super(list, mutex);
        }
    }
    
    private static class SynchronizedMultiset<E> extends SynchronizedCollection<E> implements Multiset<E>
    {
        transient Set<E> elementSet;
        transient Set<Entry<E>> entrySet;
        private static final long serialVersionUID = 0L;
        
        SynchronizedMultiset(final Multiset<E> delegate, @Nullable final Object mutex) {
            super((Collection)delegate, mutex);
        }
        
        Multiset<E> delegate() {
            return (Multiset<E>)(Multiset)super.delegate();
        }
        
        public int count(final Object o) {
            synchronized (this.mutex) {
                return this.delegate().count(o);
            }
        }
        
        public int add(final E e, final int n) {
            synchronized (this.mutex) {
                return this.delegate().add(e, n);
            }
        }
        
        public int remove(final Object o, final int n) {
            synchronized (this.mutex) {
                return this.delegate().remove(o, n);
            }
        }
        
        public int setCount(final E element, final int count) {
            synchronized (this.mutex) {
                return this.delegate().setCount(element, count);
            }
        }
        
        public boolean setCount(final E element, final int oldCount, final int newCount) {
            synchronized (this.mutex) {
                return this.delegate().setCount(element, oldCount, newCount);
            }
        }
        
        public Set<E> elementSet() {
            synchronized (this.mutex) {
                if (this.elementSet == null) {
                    this.elementSet = (Set<E>)typePreservingSet((Set<Object>)this.delegate().elementSet(), this.mutex);
                }
                return this.elementSet;
            }
        }
        
        public Set<Entry<E>> entrySet() {
            synchronized (this.mutex) {
                if (this.entrySet == null) {
                    this.entrySet = (Set<Entry<E>>)typePreservingSet((Set<Object>)this.delegate().entrySet(), this.mutex);
                }
                return this.entrySet;
            }
        }
        
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            synchronized (this.mutex) {
                return this.delegate().equals(o);
            }
        }
        
        public int hashCode() {
            synchronized (this.mutex) {
                return this.delegate().hashCode();
            }
        }
    }
    
    private static class SynchronizedMultimap<K, V> extends SynchronizedObject implements Multimap<K, V>
    {
        transient Set<K> keySet;
        transient Collection<V> valuesCollection;
        transient Collection<Map.Entry<K, V>> entries;
        transient Map<K, Collection<V>> asMap;
        transient Multiset<K> keys;
        private static final long serialVersionUID = 0L;
        
        Multimap<K, V> delegate() {
            return (Multimap<K, V>)super.delegate();
        }
        
        SynchronizedMultimap(final Multimap<K, V> delegate, @Nullable final Object mutex) {
            super(delegate, mutex);
        }
        
        public int size() {
            synchronized (this.mutex) {
                return this.delegate().size();
            }
        }
        
        public boolean isEmpty() {
            synchronized (this.mutex) {
                return this.delegate().isEmpty();
            }
        }
        
        public boolean containsKey(final Object key) {
            synchronized (this.mutex) {
                return this.delegate().containsKey(key);
            }
        }
        
        public boolean containsValue(final Object value) {
            synchronized (this.mutex) {
                return this.delegate().containsValue(value);
            }
        }
        
        public boolean containsEntry(final Object key, final Object value) {
            synchronized (this.mutex) {
                return this.delegate().containsEntry(key, value);
            }
        }
        
        public Collection<V> get(final K key) {
            synchronized (this.mutex) {
                return (Collection<V>)typePreservingCollection((Collection<Object>)this.delegate().get(key), this.mutex);
            }
        }
        
        public boolean put(final K key, final V value) {
            synchronized (this.mutex) {
                return this.delegate().put(key, value);
            }
        }
        
        public boolean putAll(final K key, final Iterable<? extends V> values) {
            synchronized (this.mutex) {
                return this.delegate().putAll(key, values);
            }
        }
        
        public boolean putAll(final Multimap<? extends K, ? extends V> multimap) {
            synchronized (this.mutex) {
                return this.delegate().putAll(multimap);
            }
        }
        
        public Collection<V> replaceValues(final K key, final Iterable<? extends V> values) {
            synchronized (this.mutex) {
                return this.delegate().replaceValues(key, values);
            }
        }
        
        public boolean remove(final Object key, final Object value) {
            synchronized (this.mutex) {
                return this.delegate().remove(key, value);
            }
        }
        
        public Collection<V> removeAll(final Object key) {
            synchronized (this.mutex) {
                return this.delegate().removeAll(key);
            }
        }
        
        public void clear() {
            synchronized (this.mutex) {
                this.delegate().clear();
            }
        }
        
        public Set<K> keySet() {
            synchronized (this.mutex) {
                if (this.keySet == null) {
                    this.keySet = (Set<K>)typePreservingSet((Set<Object>)this.delegate().keySet(), this.mutex);
                }
                return this.keySet;
            }
        }
        
        public Collection<V> values() {
            synchronized (this.mutex) {
                if (this.valuesCollection == null) {
                    this.valuesCollection = (Collection<V>)collection((Collection<Object>)this.delegate().values(), this.mutex);
                }
                return this.valuesCollection;
            }
        }
        
        public Collection<Map.Entry<K, V>> entries() {
            synchronized (this.mutex) {
                if (this.entries == null) {
                    this.entries = (Collection<Map.Entry<K, V>>)typePreservingCollection((Collection<Object>)this.delegate().entries(), this.mutex);
                }
                return this.entries;
            }
        }
        
        public Map<K, Collection<V>> asMap() {
            synchronized (this.mutex) {
                if (this.asMap == null) {
                    this.asMap = (Map<K, Collection<V>>)new SynchronizedAsMap((Map<Object, Collection<Object>>)this.delegate().asMap(), this.mutex);
                }
                return this.asMap;
            }
        }
        
        public Multiset<K> keys() {
            synchronized (this.mutex) {
                if (this.keys == null) {
                    this.keys = Synchronized.multiset(this.delegate().keys(), this.mutex);
                }
                return this.keys;
            }
        }
        
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            synchronized (this.mutex) {
                return this.delegate().equals(o);
            }
        }
        
        public int hashCode() {
            synchronized (this.mutex) {
                return this.delegate().hashCode();
            }
        }
    }
    
    private static class SynchronizedListMultimap<K, V> extends SynchronizedMultimap<K, V> implements ListMultimap<K, V>
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedListMultimap(final ListMultimap<K, V> delegate, @Nullable final Object mutex) {
            super(delegate, mutex);
        }
        
        ListMultimap<K, V> delegate() {
            return (ListMultimap<K, V>)(ListMultimap)super.delegate();
        }
        
        public List<V> get(final K key) {
            synchronized (this.mutex) {
                return (List<V>)list((List<Object>)this.delegate().get(key), this.mutex);
            }
        }
        
        public List<V> removeAll(final Object key) {
            synchronized (this.mutex) {
                return this.delegate().removeAll(key);
            }
        }
        
        public List<V> replaceValues(final K key, final Iterable<? extends V> values) {
            synchronized (this.mutex) {
                return this.delegate().replaceValues(key, values);
            }
        }
    }
    
    private static class SynchronizedSetMultimap<K, V> extends SynchronizedMultimap<K, V> implements SetMultimap<K, V>
    {
        transient Set<Map.Entry<K, V>> entrySet;
        private static final long serialVersionUID = 0L;
        
        SynchronizedSetMultimap(final SetMultimap<K, V> delegate, @Nullable final Object mutex) {
            super(delegate, mutex);
        }
        
        SetMultimap<K, V> delegate() {
            return (SetMultimap<K, V>)(SetMultimap)super.delegate();
        }
        
        public Set<V> get(final K key) {
            synchronized (this.mutex) {
                return Synchronized.set(this.delegate().get(key), this.mutex);
            }
        }
        
        public Set<V> removeAll(final Object key) {
            synchronized (this.mutex) {
                return this.delegate().removeAll(key);
            }
        }
        
        public Set<V> replaceValues(final K key, final Iterable<? extends V> values) {
            synchronized (this.mutex) {
                return this.delegate().replaceValues(key, values);
            }
        }
        
        public Set<Map.Entry<K, V>> entries() {
            synchronized (this.mutex) {
                if (this.entrySet == null) {
                    this.entrySet = Synchronized.set(this.delegate().entries(), this.mutex);
                }
                return this.entrySet;
            }
        }
    }
    
    private static class SynchronizedSortedSetMultimap<K, V> extends SynchronizedSetMultimap<K, V> implements SortedSetMultimap<K, V>
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedSortedSetMultimap(final SortedSetMultimap<K, V> delegate, @Nullable final Object mutex) {
            super(delegate, mutex);
        }
        
        SortedSetMultimap<K, V> delegate() {
            return (SortedSetMultimap<K, V>)(SortedSetMultimap)super.delegate();
        }
        
        public SortedSet<V> get(final K key) {
            synchronized (this.mutex) {
                return (SortedSet<V>)sortedSet((SortedSet<Object>)this.delegate().get(key), this.mutex);
            }
        }
        
        public SortedSet<V> removeAll(final Object key) {
            synchronized (this.mutex) {
                return this.delegate().removeAll(key);
            }
        }
        
        public SortedSet<V> replaceValues(final K key, final Iterable<? extends V> values) {
            synchronized (this.mutex) {
                return this.delegate().replaceValues(key, values);
            }
        }
        
        public Comparator<? super V> valueComparator() {
            synchronized (this.mutex) {
                return this.delegate().valueComparator();
            }
        }
    }
    
    private static class SynchronizedAsMapEntries<K, V> extends SynchronizedSet<Map.Entry<K, Collection<V>>>
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedAsMapEntries(final Set<Map.Entry<K, Collection<V>>> delegate, @Nullable final Object mutex) {
            super(delegate, mutex);
        }
        
        public Iterator<Map.Entry<K, Collection<V>>> iterator() {
            final Iterator<Map.Entry<K, Collection<V>>> iterator = super.iterator();
            return new ForwardingIterator<Map.Entry<K, Collection<V>>>() {
                protected Iterator<Map.Entry<K, Collection<V>>> delegate() {
                    return iterator;
                }
                
                public Map.Entry<K, Collection<V>> next() {
                    final Map.Entry<K, Collection<V>> entry = iterator.next();
                    return new ForwardingMapEntry<K, Collection<V>>() {
                        protected Map.Entry<K, Collection<V>> delegate() {
                            return entry;
                        }
                        
                        public Collection<V> getValue() {
                            return (Collection<V>)typePreservingCollection((Collection<Object>)entry.getValue(), SynchronizedAsMapEntries.this.mutex);
                        }
                    };
                }
            };
        }
        
        public Object[] toArray() {
            synchronized (this.mutex) {
                return ObjectArrays.toArrayImpl(this.delegate());
            }
        }
        
        public <T> T[] toArray(final T[] array) {
            synchronized (this.mutex) {
                return ObjectArrays.toArrayImpl(this.delegate(), array);
            }
        }
        
        public boolean contains(final Object o) {
            synchronized (this.mutex) {
                return Maps.containsEntryImpl(this.delegate(), o);
            }
        }
        
        public boolean containsAll(final Collection<?> c) {
            synchronized (this.mutex) {
                return Collections2.containsAllImpl(this.delegate(), c);
            }
        }
        
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            synchronized (this.mutex) {
                return Sets.equalsImpl(this.delegate(), o);
            }
        }
        
        public boolean remove(final Object o) {
            synchronized (this.mutex) {
                return Maps.removeEntryImpl(this.delegate(), o);
            }
        }
        
        public boolean removeAll(final Collection<?> c) {
            synchronized (this.mutex) {
                return Iterators.removeAll(this.delegate().iterator(), c);
            }
        }
        
        public boolean retainAll(final Collection<?> c) {
            synchronized (this.mutex) {
                return Iterators.retainAll(this.delegate().iterator(), c);
            }
        }
    }
    
    private static class SynchronizedMap<K, V> extends SynchronizedObject implements Map<K, V>
    {
        transient Set<K> keySet;
        transient Collection<V> values;
        transient Set<Entry<K, V>> entrySet;
        private static final long serialVersionUID = 0L;
        
        SynchronizedMap(final Map<K, V> delegate, @Nullable final Object mutex) {
            super(delegate, mutex);
        }
        
        Map<K, V> delegate() {
            return (Map<K, V>)super.delegate();
        }
        
        public void clear() {
            synchronized (this.mutex) {
                this.delegate().clear();
            }
        }
        
        public boolean containsKey(final Object key) {
            synchronized (this.mutex) {
                return this.delegate().containsKey(key);
            }
        }
        
        public boolean containsValue(final Object value) {
            synchronized (this.mutex) {
                return this.delegate().containsValue(value);
            }
        }
        
        public Set<Entry<K, V>> entrySet() {
            synchronized (this.mutex) {
                if (this.entrySet == null) {
                    this.entrySet = Synchronized.set(this.delegate().entrySet(), this.mutex);
                }
                return this.entrySet;
            }
        }
        
        public V get(final Object key) {
            synchronized (this.mutex) {
                return this.delegate().get(key);
            }
        }
        
        public boolean isEmpty() {
            synchronized (this.mutex) {
                return this.delegate().isEmpty();
            }
        }
        
        public Set<K> keySet() {
            synchronized (this.mutex) {
                if (this.keySet == null) {
                    this.keySet = Synchronized.set(this.delegate().keySet(), this.mutex);
                }
                return this.keySet;
            }
        }
        
        public V put(final K key, final V value) {
            synchronized (this.mutex) {
                return this.delegate().put(key, value);
            }
        }
        
        public void putAll(final Map<? extends K, ? extends V> map) {
            synchronized (this.mutex) {
                this.delegate().putAll(map);
            }
        }
        
        public V remove(final Object key) {
            synchronized (this.mutex) {
                return this.delegate().remove(key);
            }
        }
        
        public int size() {
            synchronized (this.mutex) {
                return this.delegate().size();
            }
        }
        
        public Collection<V> values() {
            synchronized (this.mutex) {
                if (this.values == null) {
                    this.values = (Collection<V>)collection((Collection<Object>)this.delegate().values(), this.mutex);
                }
                return this.values;
            }
        }
        
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            synchronized (this.mutex) {
                return this.delegate().equals(o);
            }
        }
        
        public int hashCode() {
            synchronized (this.mutex) {
                return this.delegate().hashCode();
            }
        }
    }
    
    static class SynchronizedSortedMap<K, V> extends SynchronizedMap<K, V> implements SortedMap<K, V>
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedSortedMap(final SortedMap<K, V> delegate, @Nullable final Object mutex) {
            super(delegate, mutex);
        }
        
        SortedMap<K, V> delegate() {
            return (SortedMap<K, V>)(SortedMap)super.delegate();
        }
        
        public Comparator<? super K> comparator() {
            synchronized (this.mutex) {
                return this.delegate().comparator();
            }
        }
        
        public K firstKey() {
            synchronized (this.mutex) {
                return this.delegate().firstKey();
            }
        }
        
        public SortedMap<K, V> headMap(final K toKey) {
            synchronized (this.mutex) {
                return Synchronized.sortedMap(this.delegate().headMap(toKey), this.mutex);
            }
        }
        
        public K lastKey() {
            synchronized (this.mutex) {
                return this.delegate().lastKey();
            }
        }
        
        public SortedMap<K, V> subMap(final K fromKey, final K toKey) {
            synchronized (this.mutex) {
                return Synchronized.sortedMap(this.delegate().subMap(fromKey, toKey), this.mutex);
            }
        }
        
        public SortedMap<K, V> tailMap(final K fromKey) {
            synchronized (this.mutex) {
                return Synchronized.sortedMap(this.delegate().tailMap(fromKey), this.mutex);
            }
        }
    }
    
    @VisibleForTesting
    static class SynchronizedBiMap<K, V> extends SynchronizedMap<K, V> implements BiMap<K, V>, Serializable
    {
        private transient Set<V> valueSet;
        private transient BiMap<V, K> inverse;
        private static final long serialVersionUID = 0L;
        
        private SynchronizedBiMap(final BiMap<K, V> delegate, @Nullable final Object mutex, @Nullable final BiMap<V, K> inverse) {
            super(delegate, mutex);
            this.inverse = inverse;
        }
        
        BiMap<K, V> delegate() {
            return (BiMap<K, V>)(BiMap)super.delegate();
        }
        
        public Set<V> values() {
            synchronized (this.mutex) {
                if (this.valueSet == null) {
                    this.valueSet = Synchronized.set(this.delegate().values(), this.mutex);
                }
                return this.valueSet;
            }
        }
        
        public V forcePut(final K key, final V value) {
            synchronized (this.mutex) {
                return this.delegate().forcePut(key, value);
            }
        }
        
        public BiMap<V, K> inverse() {
            synchronized (this.mutex) {
                if (this.inverse == null) {
                    this.inverse = (BiMap<V, K>)new SynchronizedBiMap((BiMap<Object, Object>)this.delegate().inverse(), this.mutex, (BiMap<Object, Object>)this);
                }
                return this.inverse;
            }
        }
    }
    
    private static class SynchronizedAsMap<K, V> extends SynchronizedMap<K, Collection<V>>
    {
        transient Set<Map.Entry<K, Collection<V>>> asMapEntrySet;
        transient Collection<Collection<V>> asMapValues;
        private static final long serialVersionUID = 0L;
        
        SynchronizedAsMap(final Map<K, Collection<V>> delegate, @Nullable final Object mutex) {
            super(delegate, mutex);
        }
        
        public Collection<V> get(final Object key) {
            synchronized (this.mutex) {
                final Collection<V> collection = super.get(key);
                return (Collection<V>)((collection == null) ? null : typePreservingCollection((Collection<Object>)collection, this.mutex));
            }
        }
        
        public Set<Map.Entry<K, Collection<V>>> entrySet() {
            synchronized (this.mutex) {
                if (this.asMapEntrySet == null) {
                    this.asMapEntrySet = (Set<Map.Entry<K, Collection<V>>>)new SynchronizedAsMapEntries((Set<Map.Entry<Object, Collection<Object>>>)this.delegate().entrySet(), this.mutex);
                }
                return this.asMapEntrySet;
            }
        }
        
        public Collection<Collection<V>> values() {
            synchronized (this.mutex) {
                if (this.asMapValues == null) {
                    this.asMapValues = (Collection<Collection<V>>)new SynchronizedAsMapValues((Collection<Collection<Object>>)this.delegate().values(), this.mutex);
                }
                return this.asMapValues;
            }
        }
        
        public boolean containsValue(final Object o) {
            return this.values().contains(o);
        }
    }
    
    private static class SynchronizedAsMapValues<V> extends SynchronizedCollection<Collection<V>>
    {
        private static final long serialVersionUID = 0L;
        
        SynchronizedAsMapValues(final Collection<Collection<V>> delegate, @Nullable final Object mutex) {
            super((Collection)delegate, mutex);
        }
        
        public Iterator<Collection<V>> iterator() {
            final Iterator<Collection<V>> iterator = super.iterator();
            return new ForwardingIterator<Collection<V>>() {
                protected Iterator<Collection<V>> delegate() {
                    return iterator;
                }
                
                public Collection<V> next() {
                    return (Collection<V>)typePreservingCollection((Collection<Object>)iterator.next(), SynchronizedAsMapValues.this.mutex);
                }
            };
        }
    }
}
