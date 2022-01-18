// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.io.Serializable;
import java.util.Set;
import java.util.NoSuchElementException;
import java.util.Iterator;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Collection;
import java.util.Arrays;
import com.google.common.base.Preconditions;
import java.util.Map;
import java.util.Comparator;
import com.google.common.annotations.GwtCompatible;
import java.util.SortedMap;

@GwtCompatible(serializable = true, emulated = true)
public class ImmutableSortedMap<K, V> extends ImmutableSortedMapFauxverideShim<K, V> implements SortedMap<K, V>
{
    private static final Comparator<Comparable> NATURAL_ORDER;
    private static final ImmutableSortedMap<Comparable, Object> NATURAL_EMPTY_MAP;
    final transient ImmutableList<Map.Entry<K, V>> entries;
    private final transient Comparator<? super K> comparator;
    private transient ImmutableSet<Map.Entry<K, V>> entrySet;
    private transient ImmutableSortedSet<K> keySet;
    private transient ImmutableCollection<V> values;
    private static final long serialVersionUID = 0L;
    
    public static <K, V> ImmutableSortedMap<K, V> of() {
        return (ImmutableSortedMap<K, V>)ImmutableSortedMap.NATURAL_EMPTY_MAP;
    }
    
    private static <K, V> ImmutableSortedMap<K, V> emptyMap(final Comparator<? super K> comparator) {
        if (ImmutableSortedMap.NATURAL_ORDER.equals(comparator)) {
            return (ImmutableSortedMap<K, V>)ImmutableSortedMap.NATURAL_EMPTY_MAP;
        }
        return new ImmutableSortedMap<K, V>(ImmutableList.of(), comparator);
    }
    
    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(final K k1, final V v1) {
        return new ImmutableSortedMap<K, V>((ImmutableList<Map.Entry<K, V>>)ImmutableList.of((Map.Entry<K, V>)ImmutableMap.entryOf(k1, v1)), Ordering.natural());
    }
    
    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(final K k1, final V v1, final K k2, final V v2) {
        return new Builder<K, V>(Ordering.natural()).put(k1, v1).put(k2, v2).build();
    }
    
    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3) {
        return new Builder<K, V>(Ordering.natural()).put(k1, v1).put(k2, v2).put(k3, v3).build();
    }
    
    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3, final K k4, final V v4) {
        return new Builder<K, V>(Ordering.natural()).put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).build();
    }
    
    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3, final K k4, final V v4, final K k5, final V v5) {
        return new Builder<K, V>(Ordering.natural()).put(k1, v1).put(k2, v2).put(k3, v3).put(k4, v4).put(k5, v5).build();
    }
    
    public static <K, V> ImmutableSortedMap<K, V> copyOf(final Map<? extends K, ? extends V> map) {
        final Ordering<K> naturalOrder = Ordering.natural();
        return copyOfInternal(map, (Comparator<? super K>)naturalOrder);
    }
    
    public static <K, V> ImmutableSortedMap<K, V> copyOf(final Map<? extends K, ? extends V> map, final Comparator<? super K> comparator) {
        return (ImmutableSortedMap<K, V>)copyOfInternal((Map<?, ?>)map, (Comparator<? super Object>)Preconditions.checkNotNull(comparator));
    }
    
    public static <K, V> ImmutableSortedMap<K, V> copyOfSorted(final SortedMap<K, ? extends V> map) {
        Comparator<? super K> comparator = map.comparator();
        if (comparator == null) {
            comparator = (Comparator<? super K>)ImmutableSortedMap.NATURAL_ORDER;
        }
        return copyOfInternal((Map<? extends K, ? extends V>)map, comparator);
    }
    
    private static <K, V> ImmutableSortedMap<K, V> copyOfInternal(final Map<? extends K, ? extends V> map, final Comparator<? super K> comparator) {
        boolean sameComparator = false;
        if (map instanceof SortedMap) {
            final SortedMap<?, ?> sortedMap = (SortedMap<?, ?>)(SortedMap)map;
            final Comparator<?> comparator2 = sortedMap.comparator();
            sameComparator = ((comparator2 == null) ? (comparator == ImmutableSortedMap.NATURAL_ORDER) : comparator.equals(comparator2));
        }
        if (sameComparator && map instanceof ImmutableSortedMap) {
            final ImmutableSortedMap<K, V> kvMap = (ImmutableSortedMap<K, V>)(ImmutableSortedMap)map;
            if (!kvMap.isPartialView()) {
                return kvMap;
            }
        }
        final Map.Entry<K, V>[] entries = map.entrySet().toArray(new Map.Entry[0]);
        for (int i = 0; i < entries.length; ++i) {
            final Map.Entry<K, V> entry = entries[i];
            entries[i] = ImmutableMap.entryOf(entry.getKey(), entry.getValue());
        }
        final List<Map.Entry<K, V>> list = Arrays.asList(entries);
        if (!sameComparator) {
            sortEntries(list, comparator);
            validateEntries(list, comparator);
        }
        return new ImmutableSortedMap<K, V>(ImmutableList.copyOf((Collection<? extends Map.Entry<K, V>>)list), comparator);
    }
    
    private static <K, V> void sortEntries(final List<Map.Entry<K, V>> entries, final Comparator<? super K> comparator) {
        final Comparator<Map.Entry<K, V>> entryComparator = new Comparator<Map.Entry<K, V>>() {
            public int compare(final Map.Entry<K, V> entry1, final Map.Entry<K, V> entry2) {
                return comparator.compare(entry1.getKey(), entry2.getKey());
            }
        };
        Collections.sort(entries, entryComparator);
    }
    
    private static <K, V> void validateEntries(final List<Map.Entry<K, V>> entries, final Comparator<? super K> comparator) {
        for (int i = 1; i < entries.size(); ++i) {
            if (comparator.compare((Object)entries.get(i - 1).getKey(), (Object)entries.get(i).getKey()) == 0) {
                throw new IllegalArgumentException("Duplicate keys in mappings " + entries.get(i - 1) + " and " + entries.get(i));
            }
        }
    }
    
    public static <K extends Comparable<K>, V> Builder<K, V> naturalOrder() {
        return new Builder<K, V>(Ordering.natural());
    }
    
    public static <K, V> Builder<K, V> orderedBy(final Comparator<K> comparator) {
        return new Builder<K, V>(comparator);
    }
    
    public static <K extends Comparable<K>, V> Builder<K, V> reverseOrder() {
        return new Builder<K, V>(Ordering.natural().reverse());
    }
    
    ImmutableSortedMap(final ImmutableList<Map.Entry<K, V>> entries, final Comparator<? super K> comparator) {
        this.entries = entries;
        this.comparator = comparator;
    }
    
    public int size() {
        return this.entries.size();
    }
    
    Comparator<Object> unsafeComparator() {
        return (Comparator<Object>)this.comparator;
    }
    
    public V get(@Nullable final Object key) {
        if (key == null) {
            return null;
        }
        int i;
        try {
            i = this.index(key, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.INVERTED_INSERTION_INDEX);
        }
        catch (ClassCastException e) {
            return null;
        }
        return (V)((i >= 0) ? this.entries.get(i).getValue() : null);
    }
    
    public boolean containsValue(@Nullable final Object value) {
        return value != null && Iterators.contains(this.valueIterator(), value);
    }
    
    boolean isPartialView() {
        return this.entries.isPartialView();
    }
    
    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        final ImmutableSet<Map.Entry<K, V>> es = this.entrySet;
        return (es == null) ? (this.entrySet = this.createEntrySet()) : es;
    }
    
    private ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        return (ImmutableSet<Map.Entry<K, V>>)(this.isEmpty() ? ImmutableSet.of() : new EntrySet((ImmutableSortedMap<Object, Object>)this));
    }
    
    public ImmutableSortedSet<K> keySet() {
        final ImmutableSortedSet<K> ks = this.keySet;
        return (ks == null) ? (this.keySet = this.createKeySet()) : ks;
    }
    
    private ImmutableSortedSet<K> createKeySet() {
        if (this.isEmpty()) {
            return ImmutableSortedSet.emptySet(this.comparator);
        }
        return new RegularImmutableSortedSet<K>((ImmutableList<K>)new TransformedImmutableList<Map.Entry<K, V>, K>(this.entries) {
            K transform(final Map.Entry<K, V> entry) {
                return entry.getKey();
            }
        }, this.comparator);
    }
    
    public ImmutableCollection<V> values() {
        final ImmutableCollection<V> v = this.values;
        return (v == null) ? (this.values = new Values<V>(this)) : v;
    }
    
    UnmodifiableIterator<V> valueIterator() {
        final UnmodifiableIterator<Map.Entry<K, V>> entryIterator = this.entries.iterator();
        return new UnmodifiableIterator<V>() {
            public boolean hasNext() {
                return entryIterator.hasNext();
            }
            
            public V next() {
                return ((Map.Entry)entryIterator.next()).getValue();
            }
        };
    }
    
    public Comparator<? super K> comparator() {
        return this.comparator;
    }
    
    public K firstKey() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.entries.get(0).getKey();
    }
    
    public K lastKey() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.entries.get(this.size() - 1).getKey();
    }
    
    public ImmutableSortedMap<K, V> headMap(final K toKey) {
        return this.headMap(toKey, false);
    }
    
    ImmutableSortedMap<K, V> headMap(final K toKey, final boolean inclusive) {
        int index;
        if (inclusive) {
            index = this.index(toKey, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER) + 1;
        }
        else {
            index = this.index(toKey, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        }
        return this.createSubmap(0, index);
    }
    
    public ImmutableSortedMap<K, V> subMap(final K fromKey, final K toKey) {
        return this.subMap(fromKey, true, toKey, false);
    }
    
    ImmutableSortedMap<K, V> subMap(final K fromKey, final boolean fromInclusive, final K toKey, final boolean toInclusive) {
        Preconditions.checkNotNull(fromKey);
        Preconditions.checkNotNull(toKey);
        Preconditions.checkArgument(this.comparator.compare((Object)fromKey, (Object)toKey) <= 0);
        return this.tailMap(fromKey, fromInclusive).headMap(toKey, toInclusive);
    }
    
    public ImmutableSortedMap<K, V> tailMap(final K fromKey) {
        return this.tailMap(fromKey, true);
    }
    
    ImmutableSortedMap<K, V> tailMap(final K fromKey, final boolean inclusive) {
        int index;
        if (inclusive) {
            index = this.index(fromKey, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        }
        else {
            index = this.index(fromKey, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER) + 1;
        }
        return this.createSubmap(index, this.size());
    }
    
    private ImmutableList<K> keyList() {
        return (ImmutableList<K>)new TransformedImmutableList<Map.Entry<K, V>, K>(this.entries) {
            K transform(final Map.Entry<K, V> entry) {
                return entry.getKey();
            }
        };
    }
    
    private int index(final Object key, final SortedLists.KeyPresentBehavior presentBehavior, final SortedLists.KeyAbsentBehavior absentBehavior) {
        return SortedLists.binarySearch(this.keyList(), (Object)Preconditions.checkNotNull(key), this.unsafeComparator(), presentBehavior, absentBehavior);
    }
    
    private ImmutableSortedMap<K, V> createSubmap(final int newFromIndex, final int newToIndex) {
        if (newFromIndex < newToIndex) {
            return new ImmutableSortedMap<K, V>(this.entries.subList(newFromIndex, newToIndex), this.comparator);
        }
        return emptyMap(this.comparator);
    }
    
    Object writeReplace() {
        return new SerializedForm(this);
    }
    
    static {
        NATURAL_ORDER = Ordering.natural();
        NATURAL_EMPTY_MAP = new ImmutableSortedMap<Comparable, Object>(ImmutableList.of(), ImmutableSortedMap.NATURAL_ORDER);
    }
    
    public static class Builder<K, V> extends ImmutableMap.Builder<K, V>
    {
        private final Comparator<? super K> comparator;
        
        public Builder(final Comparator<? super K> comparator) {
            this.comparator = Preconditions.checkNotNull(comparator);
        }
        
        public Builder<K, V> put(final K key, final V value) {
            this.entries.add(ImmutableMap.entryOf(key, value));
            return this;
        }
        
        public Builder<K, V> putAll(final Map<? extends K, ? extends V> map) {
            for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
                this.put(entry.getKey(), entry.getValue());
            }
            return this;
        }
        
        public ImmutableSortedMap<K, V> build() {
            sortEntries((List<Map.Entry<Object, Object>>)this.entries, this.comparator);
            validateEntries((List<Map.Entry<Object, Object>>)this.entries, this.comparator);
            return new ImmutableSortedMap<K, V>(ImmutableList.copyOf((Collection<? extends Map.Entry<K, V>>)this.entries), this.comparator);
        }
    }
    
    private static class EntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>>
    {
        final transient ImmutableSortedMap<K, V> map;
        
        EntrySet(final ImmutableSortedMap<K, V> map) {
            this.map = map;
        }
        
        boolean isPartialView() {
            return this.map.isPartialView();
        }
        
        public int size() {
            return this.map.size();
        }
        
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return this.map.entries.iterator();
        }
        
        public boolean contains(final Object target) {
            if (target instanceof Map.Entry) {
                final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)target;
                final V mappedValue = this.map.get(entry.getKey());
                return mappedValue != null && mappedValue.equals(entry.getValue());
            }
            return false;
        }
        
        Object writeReplace() {
            return new EntrySetSerializedForm((ImmutableSortedMap<Object, Object>)this.map);
        }
    }
    
    private static class EntrySetSerializedForm<K, V> implements Serializable
    {
        final ImmutableSortedMap<K, V> map;
        private static final long serialVersionUID = 0L;
        
        EntrySetSerializedForm(final ImmutableSortedMap<K, V> map) {
            this.map = map;
        }
        
        Object readResolve() {
            return this.map.entrySet();
        }
    }
    
    private static class Values<V> extends ImmutableCollection<V>
    {
        private final ImmutableSortedMap<?, V> map;
        
        Values(final ImmutableSortedMap<?, V> map) {
            this.map = map;
        }
        
        public int size() {
            return this.map.size();
        }
        
        public UnmodifiableIterator<V> iterator() {
            return this.map.valueIterator();
        }
        
        public boolean contains(final Object target) {
            return this.map.containsValue(target);
        }
        
        boolean isPartialView() {
            return true;
        }
        
        Object writeReplace() {
            return new ValuesSerializedForm((ImmutableSortedMap<?, Object>)this.map);
        }
    }
    
    private static class ValuesSerializedForm<V> implements Serializable
    {
        final ImmutableSortedMap<?, V> map;
        private static final long serialVersionUID = 0L;
        
        ValuesSerializedForm(final ImmutableSortedMap<?, V> map) {
            this.map = map;
        }
        
        Object readResolve() {
            return this.map.values();
        }
    }
    
    private static class SerializedForm extends ImmutableMap.SerializedForm
    {
        private final Comparator<Object> comparator;
        private static final long serialVersionUID = 0L;
        
        SerializedForm(final ImmutableSortedMap<?, ?> sortedMap) {
            super(sortedMap);
            this.comparator = (Comparator<Object>)sortedMap.comparator();
        }
        
        Object readResolve() {
            final ImmutableSortedMap.Builder<Object, Object> builder = new ImmutableSortedMap.Builder<Object, Object>(this.comparator);
            return this.createMap(builder);
        }
    }
}
