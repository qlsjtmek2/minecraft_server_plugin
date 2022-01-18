// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.AbstractSet;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.io.Serializable;
import com.google.common.base.Objects;
import java.util.Collection;
import com.google.common.base.Predicates;
import com.google.common.base.Predicate;
import java.util.Set;
import com.google.common.annotations.GwtIncompatible;
import java.util.Enumeration;
import java.util.Properties;
import com.google.common.base.Function;
import java.util.Collections;
import com.google.common.annotations.Beta;
import java.util.Iterator;
import com.google.common.base.Equivalence;
import com.google.common.base.Equivalences;
import java.util.IdentityHashMap;
import java.util.EnumMap;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentMap;
import java.util.LinkedHashMap;
import java.util.Map;
import com.google.common.base.Preconditions;
import java.util.HashMap;
import com.google.common.base.Joiner;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(emulated = true)
public final class Maps
{
    static final Joiner.MapJoiner STANDARD_JOINER;
    
    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<K, V>();
    }
    
    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(final int expectedSize) {
        return new HashMap<K, V>(capacity(expectedSize));
    }
    
    static int capacity(final int expectedSize) {
        if (expectedSize < 3) {
            Preconditions.checkArgument(expectedSize >= 0);
            return expectedSize + 1;
        }
        if (expectedSize < 1073741824) {
            return expectedSize + expectedSize / 3;
        }
        return Integer.MAX_VALUE;
    }
    
    public static <K, V> HashMap<K, V> newHashMap(final Map<? extends K, ? extends V> map) {
        return new HashMap<K, V>(map);
    }
    
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<K, V>();
    }
    
    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(final Map<? extends K, ? extends V> map) {
        return new LinkedHashMap<K, V>(map);
    }
    
    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return new MapMaker().makeMap();
    }
    
    public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap<K, V>();
    }
    
    public static <K, V> TreeMap<K, V> newTreeMap(final SortedMap<K, ? extends V> map) {
        return new TreeMap<K, V>(map);
    }
    
    public static <C, K extends C, V> TreeMap<K, V> newTreeMap(@Nullable final Comparator<C> comparator) {
        return new TreeMap<K, V>(comparator);
    }
    
    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(final Class<K> type) {
        return new EnumMap<K, V>(Preconditions.checkNotNull(type));
    }
    
    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(final Map<K, ? extends V> map) {
        return new EnumMap<K, V>(map);
    }
    
    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
        return new IdentityHashMap<K, V>();
    }
    
    public static <K, V> BiMap<K, V> synchronizedBiMap(final BiMap<K, V> bimap) {
        return Synchronized.biMap(bimap, null);
    }
    
    public static <K, V> MapDifference<K, V> difference(final Map<? extends K, ? extends V> left, final Map<? extends K, ? extends V> right) {
        return difference(left, right, (Equivalence<? super V>)Equivalences.equals());
    }
    
    @Beta
    public static <K, V> MapDifference<K, V> difference(final Map<? extends K, ? extends V> left, final Map<? extends K, ? extends V> right, final Equivalence<? super V> valueEquivalence) {
        Preconditions.checkNotNull(valueEquivalence);
        final Map<K, V> onlyOnLeft = (Map<K, V>)newHashMap();
        final Map<K, V> onlyOnRight = new HashMap<K, V>(right);
        final Map<K, V> onBoth = (Map<K, V>)newHashMap();
        final Map<K, MapDifference.ValueDifference<V>> differences = (Map<K, MapDifference.ValueDifference<V>>)newHashMap();
        boolean eq = true;
        for (final Map.Entry<? extends K, ? extends V> entry : left.entrySet()) {
            final K leftKey = (K)entry.getKey();
            final V leftValue = (V)entry.getValue();
            if (right.containsKey(leftKey)) {
                final V rightValue = onlyOnRight.remove(leftKey);
                if (valueEquivalence.equivalent((Object)leftValue, (Object)rightValue)) {
                    onBoth.put(leftKey, leftValue);
                }
                else {
                    eq = false;
                    differences.put(leftKey, ValueDifferenceImpl.create(leftValue, rightValue));
                }
            }
            else {
                eq = false;
                onlyOnLeft.put(leftKey, leftValue);
            }
        }
        final boolean areEqual = eq && onlyOnRight.isEmpty();
        return mapDifference(areEqual, onlyOnLeft, onlyOnRight, onBoth, differences);
    }
    
    private static <K, V> MapDifference<K, V> mapDifference(final boolean areEqual, final Map<K, V> onlyOnLeft, final Map<K, V> onlyOnRight, final Map<K, V> onBoth, final Map<K, MapDifference.ValueDifference<V>> differences) {
        return new MapDifferenceImpl<K, V>(areEqual, Collections.unmodifiableMap((Map<? extends K, ? extends V>)onlyOnLeft), Collections.unmodifiableMap((Map<? extends K, ? extends V>)onlyOnRight), Collections.unmodifiableMap((Map<? extends K, ? extends V>)onBoth), Collections.unmodifiableMap((Map<? extends K, ? extends MapDifference.ValueDifference<V>>)differences));
    }
    
    public static <K, V> ImmutableMap<K, V> uniqueIndex(final Iterable<V> values, final Function<? super V, K> keyFunction) {
        return uniqueIndex(values.iterator(), keyFunction);
    }
    
    @Deprecated
    @Beta
    public static <K, V, I extends java.lang.Object> ImmutableMap<K, V> uniqueIndex(final I values, final Function<? super V, K> keyFunction) {
        final Iterable<V> valuesIterable = Preconditions.checkNotNull((Iterable<V>)values);
        return uniqueIndex(valuesIterable, keyFunction);
    }
    
    public static <K, V> ImmutableMap<K, V> uniqueIndex(final Iterator<V> values, final Function<? super V, K> keyFunction) {
        Preconditions.checkNotNull(keyFunction);
        final ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
        while (values.hasNext()) {
            final V value = values.next();
            builder.put(keyFunction.apply((Object)value), value);
        }
        return builder.build();
    }
    
    @GwtIncompatible("java.util.Properties")
    public static ImmutableMap<String, String> fromProperties(final Properties properties) {
        final ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        final Enumeration<?> e = properties.propertyNames();
        while (e.hasMoreElements()) {
            final String key = (String)e.nextElement();
            builder.put(key, properties.getProperty(key));
        }
        return builder.build();
    }
    
    @GwtCompatible(serializable = true)
    public static <K, V> Map.Entry<K, V> immutableEntry(@Nullable final K key, @Nullable final V value) {
        return new ImmutableEntry<K, V>(key, value);
    }
    
    static <K, V> Set<Map.Entry<K, V>> unmodifiableEntrySet(final Set<Map.Entry<K, V>> entrySet) {
        return new UnmodifiableEntrySet<K, V>(Collections.unmodifiableSet((Set<? extends Map.Entry<K, V>>)entrySet));
    }
    
    static <K, V> Map.Entry<K, V> unmodifiableEntry(final Map.Entry<K, V> entry) {
        Preconditions.checkNotNull(entry);
        return new AbstractMapEntry<K, V>() {
            public K getKey() {
                return entry.getKey();
            }
            
            public V getValue() {
                return entry.getValue();
            }
        };
    }
    
    public static <K, V> BiMap<K, V> unmodifiableBiMap(final BiMap<? extends K, ? extends V> bimap) {
        return new UnmodifiableBiMap<K, V>(bimap, null);
    }
    
    public static <K, V1, V2> Map<K, V2> transformValues(final Map<K, V1> fromMap, final Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        final EntryTransformer<K, V1, V2> transformer = new EntryTransformer<K, V1, V2>() {
            public V2 transformEntry(final K key, final V1 value) {
                return function.apply(value);
            }
        };
        return transformEntries(fromMap, transformer);
    }
    
    public static <K, V1, V2> Map<K, V2> transformEntries(final Map<K, V1> fromMap, final EntryTransformer<? super K, ? super V1, V2> transformer) {
        return (Map<K, V2>)new TransformedEntriesMap((Map<Object, Object>)fromMap, (EntryTransformer<? super Object, ? super Object, Object>)transformer);
    }
    
    public static <K, V> Map<K, V> filterKeys(final Map<K, V> unfiltered, final Predicate<? super K> keyPredicate) {
        Preconditions.checkNotNull(keyPredicate);
        final Predicate<Map.Entry<K, V>> entryPredicate = new Predicate<Map.Entry<K, V>>() {
            public boolean apply(final Map.Entry<K, V> input) {
                return keyPredicate.apply(input.getKey());
            }
        };
        return (unfiltered instanceof AbstractFilteredMap) ? filterFiltered((AbstractFilteredMap<K, V>)(AbstractFilteredMap)unfiltered, entryPredicate) : new FilteredKeyMap<K, V>(Preconditions.checkNotNull(unfiltered), keyPredicate, entryPredicate);
    }
    
    public static <K, V> Map<K, V> filterValues(final Map<K, V> unfiltered, final Predicate<? super V> valuePredicate) {
        Preconditions.checkNotNull(valuePredicate);
        final Predicate<Map.Entry<K, V>> entryPredicate = new Predicate<Map.Entry<K, V>>() {
            public boolean apply(final Map.Entry<K, V> input) {
                return valuePredicate.apply(input.getValue());
            }
        };
        return (Map<K, V>)filterEntries((Map<Object, Object>)unfiltered, (Predicate<? super Map.Entry<Object, Object>>)entryPredicate);
    }
    
    public static <K, V> Map<K, V> filterEntries(final Map<K, V> unfiltered, final Predicate<? super Map.Entry<K, V>> entryPredicate) {
        Preconditions.checkNotNull(entryPredicate);
        return (unfiltered instanceof AbstractFilteredMap) ? filterFiltered((AbstractFilteredMap<K, V>)(AbstractFilteredMap)unfiltered, entryPredicate) : new FilteredEntryMap<K, V>(Preconditions.checkNotNull(unfiltered), entryPredicate);
    }
    
    private static <K, V> Map<K, V> filterFiltered(final AbstractFilteredMap<K, V> map, final Predicate<? super Map.Entry<K, V>> entryPredicate) {
        final Predicate<Map.Entry<K, V>> predicate = Predicates.and(map.predicate, entryPredicate);
        return new FilteredEntryMap<K, V>(map.unfiltered, predicate);
    }
    
    static <V> V safeGet(final Map<?, V> map, final Object key) {
        try {
            return map.get(key);
        }
        catch (ClassCastException e) {
            return null;
        }
    }
    
    static boolean safeContainsKey(final Map<?, ?> map, final Object key) {
        try {
            return map.containsKey(key);
        }
        catch (ClassCastException e) {
            return false;
        }
    }
    
    static <K, V> boolean containsEntryImpl(final Collection<Map.Entry<K, V>> c, final Object o) {
        return o instanceof Map.Entry && c.contains(unmodifiableEntry((Map.Entry<Object, Object>)o));
    }
    
    static <K, V> boolean removeEntryImpl(final Collection<Map.Entry<K, V>> c, final Object o) {
        return o instanceof Map.Entry && c.remove(unmodifiableEntry((Map.Entry<Object, Object>)o));
    }
    
    static boolean equalsImpl(final Map<?, ?> map, final Object object) {
        if (map == object) {
            return true;
        }
        if (object instanceof Map) {
            final Map<?, ?> o = (Map<?, ?>)object;
            return map.entrySet().equals(o.entrySet());
        }
        return false;
    }
    
    static int hashCodeImpl(final Map<?, ?> map) {
        return Sets.hashCodeImpl(map.entrySet());
    }
    
    static String toStringImpl(final Map<?, ?> map) {
        final StringBuilder sb = Collections2.newStringBuilderForCollection(map.size()).append('{');
        Maps.STANDARD_JOINER.appendTo(sb, map);
        return sb.append('}').toString();
    }
    
    static <K, V> void putAllImpl(final Map<K, V> self, final Map<? extends K, ? extends V> map) {
        for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
            self.put((K)entry.getKey(), (V)entry.getValue());
        }
    }
    
    static boolean containsKeyImpl(final Map<?, ?> map, @Nullable final Object key) {
        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            if (Objects.equal(entry.getKey(), key)) {
                return true;
            }
        }
        return false;
    }
    
    static boolean containsValueImpl(final Map<?, ?> map, @Nullable final Object value) {
        for (final Map.Entry<?, ?> entry : map.entrySet()) {
            if (Objects.equal(entry.getValue(), value)) {
                return true;
            }
        }
        return false;
    }
    
    static {
        STANDARD_JOINER = Collections2.STANDARD_JOINER.withKeyValueSeparator("=");
    }
    
    static class MapDifferenceImpl<K, V> implements MapDifference<K, V>
    {
        final boolean areEqual;
        final Map<K, V> onlyOnLeft;
        final Map<K, V> onlyOnRight;
        final Map<K, V> onBoth;
        final Map<K, ValueDifference<V>> differences;
        
        MapDifferenceImpl(final boolean areEqual, final Map<K, V> onlyOnLeft, final Map<K, V> onlyOnRight, final Map<K, V> onBoth, final Map<K, ValueDifference<V>> differences) {
            this.areEqual = areEqual;
            this.onlyOnLeft = onlyOnLeft;
            this.onlyOnRight = onlyOnRight;
            this.onBoth = onBoth;
            this.differences = differences;
        }
        
        public boolean areEqual() {
            return this.areEqual;
        }
        
        public Map<K, V> entriesOnlyOnLeft() {
            return this.onlyOnLeft;
        }
        
        public Map<K, V> entriesOnlyOnRight() {
            return this.onlyOnRight;
        }
        
        public Map<K, V> entriesInCommon() {
            return this.onBoth;
        }
        
        public Map<K, ValueDifference<V>> entriesDiffering() {
            return this.differences;
        }
        
        public boolean equals(final Object object) {
            if (object == this) {
                return true;
            }
            if (object instanceof MapDifference) {
                final MapDifference<?, ?> other = (MapDifference<?, ?>)object;
                return this.entriesOnlyOnLeft().equals(other.entriesOnlyOnLeft()) && this.entriesOnlyOnRight().equals(other.entriesOnlyOnRight()) && this.entriesInCommon().equals(other.entriesInCommon()) && this.entriesDiffering().equals(other.entriesDiffering());
            }
            return false;
        }
        
        public int hashCode() {
            return Objects.hashCode(this.entriesOnlyOnLeft(), this.entriesOnlyOnRight(), this.entriesInCommon(), this.entriesDiffering());
        }
        
        public String toString() {
            if (this.areEqual) {
                return "equal";
            }
            final StringBuilder result = new StringBuilder("not equal");
            if (!this.onlyOnLeft.isEmpty()) {
                result.append(": only on left=").append(this.onlyOnLeft);
            }
            if (!this.onlyOnRight.isEmpty()) {
                result.append(": only on right=").append(this.onlyOnRight);
            }
            if (!this.differences.isEmpty()) {
                result.append(": value differences=").append(this.differences);
            }
            return result.toString();
        }
    }
    
    static class ValueDifferenceImpl<V> implements MapDifference.ValueDifference<V>
    {
        private final V left;
        private final V right;
        
        static <V> MapDifference.ValueDifference<V> create(@Nullable final V left, @Nullable final V right) {
            return new ValueDifferenceImpl<V>(left, right);
        }
        
        private ValueDifferenceImpl(@Nullable final V left, @Nullable final V right) {
            this.left = left;
            this.right = right;
        }
        
        public V leftValue() {
            return this.left;
        }
        
        public V rightValue() {
            return this.right;
        }
        
        public boolean equals(@Nullable final Object object) {
            if (object instanceof MapDifference.ValueDifference) {
                final MapDifference.ValueDifference<?> that = (MapDifference.ValueDifference<?>)object;
                return Objects.equal(this.left, that.leftValue()) && Objects.equal(this.right, that.rightValue());
            }
            return false;
        }
        
        public int hashCode() {
            return Objects.hashCode(this.left, this.right);
        }
        
        public String toString() {
            return "(" + this.left + ", " + this.right + ")";
        }
    }
    
    static class UnmodifiableEntries<K, V> extends ForwardingCollection<Map.Entry<K, V>>
    {
        private final Collection<Map.Entry<K, V>> entries;
        
        UnmodifiableEntries(final Collection<Map.Entry<K, V>> entries) {
            this.entries = entries;
        }
        
        protected Collection<Map.Entry<K, V>> delegate() {
            return this.entries;
        }
        
        public Iterator<Map.Entry<K, V>> iterator() {
            final Iterator<Map.Entry<K, V>> delegate = super.iterator();
            return new ForwardingIterator<Map.Entry<K, V>>() {
                public Map.Entry<K, V> next() {
                    return Maps.unmodifiableEntry(super.next());
                }
                
                public void remove() {
                    throw new UnsupportedOperationException();
                }
                
                protected Iterator<Map.Entry<K, V>> delegate() {
                    return delegate;
                }
            };
        }
        
        public boolean add(final Map.Entry<K, V> element) {
            throw new UnsupportedOperationException();
        }
        
        public boolean addAll(final Collection<? extends Map.Entry<K, V>> collection) {
            throw new UnsupportedOperationException();
        }
        
        public void clear() {
            throw new UnsupportedOperationException();
        }
        
        public boolean remove(final Object object) {
            throw new UnsupportedOperationException();
        }
        
        public boolean removeAll(final Collection<?> collection) {
            throw new UnsupportedOperationException();
        }
        
        public boolean retainAll(final Collection<?> collection) {
            throw new UnsupportedOperationException();
        }
        
        public Object[] toArray() {
            return this.standardToArray();
        }
        
        public <T> T[] toArray(final T[] array) {
            return this.standardToArray(array);
        }
    }
    
    static class UnmodifiableEntrySet<K, V> extends UnmodifiableEntries<K, V> implements Set<Map.Entry<K, V>>
    {
        UnmodifiableEntrySet(final Set<Map.Entry<K, V>> entries) {
            super(entries);
        }
        
        public boolean equals(@Nullable final Object object) {
            return Sets.equalsImpl(this, object);
        }
        
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
    }
    
    private static class UnmodifiableBiMap<K, V> extends ForwardingMap<K, V> implements BiMap<K, V>, Serializable
    {
        final Map<K, V> unmodifiableMap;
        final BiMap<? extends K, ? extends V> delegate;
        transient BiMap<V, K> inverse;
        transient Set<V> values;
        private static final long serialVersionUID = 0L;
        
        UnmodifiableBiMap(final BiMap<? extends K, ? extends V> delegate, @Nullable final BiMap<V, K> inverse) {
            this.unmodifiableMap = Collections.unmodifiableMap((Map<? extends K, ? extends V>)delegate);
            this.delegate = delegate;
            this.inverse = inverse;
        }
        
        protected Map<K, V> delegate() {
            return this.unmodifiableMap;
        }
        
        public V forcePut(final K key, final V value) {
            throw new UnsupportedOperationException();
        }
        
        public BiMap<V, K> inverse() {
            final BiMap<V, K> result = this.inverse;
            return (result == null) ? (this.inverse = (BiMap<V, K>)new UnmodifiableBiMap(this.delegate.inverse(), (BiMap<Object, Object>)this)) : result;
        }
        
        public Set<V> values() {
            final Set<V> result = this.values;
            return (result == null) ? (this.values = Collections.unmodifiableSet(this.delegate.values())) : result;
        }
    }
    
    static class TransformedEntriesMap<K, V1, V2> extends AbstractMap<K, V2>
    {
        final Map<K, V1> fromMap;
        final EntryTransformer<? super K, ? super V1, V2> transformer;
        Set<Map.Entry<K, V2>> entrySet;
        Collection<V2> values;
        
        TransformedEntriesMap(final Map<K, V1> fromMap, final EntryTransformer<? super K, ? super V1, V2> transformer) {
            this.fromMap = Preconditions.checkNotNull(fromMap);
            this.transformer = Preconditions.checkNotNull(transformer);
        }
        
        public int size() {
            return this.fromMap.size();
        }
        
        public boolean containsKey(final Object key) {
            return this.fromMap.containsKey(key);
        }
        
        public V2 get(final Object key) {
            final V1 value = this.fromMap.get(key);
            return (value != null || this.fromMap.containsKey(key)) ? this.transformer.transformEntry((Object)key, (Object)value) : null;
        }
        
        public V2 remove(final Object key) {
            return this.fromMap.containsKey(key) ? this.transformer.transformEntry((Object)key, (Object)this.fromMap.remove(key)) : null;
        }
        
        public void clear() {
            this.fromMap.clear();
        }
        
        public Set<K> keySet() {
            return this.fromMap.keySet();
        }
        
        public Set<Map.Entry<K, V2>> entrySet() {
            Set<Map.Entry<K, V2>> result = this.entrySet;
            if (result == null) {
                result = (this.entrySet = (Set<Map.Entry<K, V2>>)new EntrySet<K, V2>() {
                    Map<K, V2> map() {
                        return (Map<K, V2>)TransformedEntriesMap.this;
                    }
                    
                    public Iterator<Map.Entry<K, V2>> iterator() {
                        final Iterator<Map.Entry<K, V1>> backingIterator = TransformedEntriesMap.this.fromMap.entrySet().iterator();
                        return Iterators.transform(backingIterator, (Function<? super Map.Entry<K, V1>, ? extends Map.Entry<K, V2>>)new Function<Map.Entry<K, V1>, Map.Entry<K, V2>>() {
                            public Map.Entry<K, V2> apply(final Map.Entry<K, V1> entry) {
                                return Maps.immutableEntry(entry.getKey(), TransformedEntriesMap.this.transformer.transformEntry((Object)entry.getKey(), (Object)entry.getValue()));
                            }
                        });
                    }
                });
            }
            return result;
        }
        
        public Collection<V2> values() {
            final Collection<V2> result = this.values;
            if (result == null) {
                return this.values = (Collection<V2>)new Values<K, V2>() {
                    Map<K, V2> map() {
                        return (Map<K, V2>)TransformedEntriesMap.this;
                    }
                };
            }
            return result;
        }
    }
    
    private abstract static class AbstractFilteredMap<K, V> extends AbstractMap<K, V>
    {
        final Map<K, V> unfiltered;
        final Predicate<? super Map.Entry<K, V>> predicate;
        Collection<V> values;
        
        AbstractFilteredMap(final Map<K, V> unfiltered, final Predicate<? super Map.Entry<K, V>> predicate) {
            this.unfiltered = unfiltered;
            this.predicate = predicate;
        }
        
        boolean apply(final Object key, final V value) {
            return this.predicate.apply((Object)Maps.immutableEntry(key, value));
        }
        
        public V put(final K key, final V value) {
            Preconditions.checkArgument(this.apply(key, value));
            return this.unfiltered.put(key, value);
        }
        
        public void putAll(final Map<? extends K, ? extends V> map) {
            for (final Map.Entry<? extends K, ? extends V> entry : map.entrySet()) {
                Preconditions.checkArgument(this.apply(entry.getKey(), entry.getValue()));
            }
            this.unfiltered.putAll(map);
        }
        
        public boolean containsKey(final Object key) {
            return this.unfiltered.containsKey(key) && this.apply(key, this.unfiltered.get(key));
        }
        
        public V get(final Object key) {
            final V value = this.unfiltered.get(key);
            return (value != null && this.apply(key, value)) ? value : null;
        }
        
        public boolean isEmpty() {
            return this.entrySet().isEmpty();
        }
        
        public V remove(final Object key) {
            return this.containsKey(key) ? this.unfiltered.remove(key) : null;
        }
        
        public Collection<V> values() {
            final Collection<V> result = this.values;
            return (result == null) ? (this.values = new Values()) : result;
        }
        
        class Values extends AbstractCollection<V>
        {
            public Iterator<V> iterator() {
                final Iterator<Map.Entry<K, V>> entryIterator = (Iterator<Map.Entry<K, V>>)AbstractFilteredMap.this.entrySet().iterator();
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
                return AbstractFilteredMap.this.entrySet().size();
            }
            
            public void clear() {
                AbstractFilteredMap.this.entrySet().clear();
            }
            
            public boolean isEmpty() {
                return AbstractFilteredMap.this.entrySet().isEmpty();
            }
            
            public boolean remove(final Object o) {
                final Iterator<Map.Entry<K, V>> iterator = AbstractFilteredMap.this.unfiltered.entrySet().iterator();
                while (iterator.hasNext()) {
                    final Map.Entry<K, V> entry = iterator.next();
                    if (Objects.equal(o, entry.getValue()) && AbstractFilteredMap.this.predicate.apply(entry)) {
                        iterator.remove();
                        return true;
                    }
                }
                return false;
            }
            
            public boolean removeAll(final Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                boolean changed = false;
                final Iterator<Map.Entry<K, V>> iterator = AbstractFilteredMap.this.unfiltered.entrySet().iterator();
                while (iterator.hasNext()) {
                    final Map.Entry<K, V> entry = iterator.next();
                    if (collection.contains(entry.getValue()) && AbstractFilteredMap.this.predicate.apply(entry)) {
                        iterator.remove();
                        changed = true;
                    }
                }
                return changed;
            }
            
            public boolean retainAll(final Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                boolean changed = false;
                final Iterator<Map.Entry<K, V>> iterator = AbstractFilteredMap.this.unfiltered.entrySet().iterator();
                while (iterator.hasNext()) {
                    final Map.Entry<K, V> entry = iterator.next();
                    if (!collection.contains(entry.getValue()) && AbstractFilteredMap.this.predicate.apply(entry)) {
                        iterator.remove();
                        changed = true;
                    }
                }
                return changed;
            }
            
            public Object[] toArray() {
                return Lists.newArrayList(this.iterator()).toArray();
            }
            
            public <T> T[] toArray(final T[] array) {
                return Lists.newArrayList(this.iterator()).toArray(array);
            }
        }
    }
    
    private static class FilteredKeyMap<K, V> extends AbstractFilteredMap<K, V>
    {
        Predicate<? super K> keyPredicate;
        Set<Map.Entry<K, V>> entrySet;
        Set<K> keySet;
        
        FilteredKeyMap(final Map<K, V> unfiltered, final Predicate<? super K> keyPredicate, final Predicate<Map.Entry<K, V>> entryPredicate) {
            super(unfiltered, entryPredicate);
            this.keyPredicate = keyPredicate;
        }
        
        public Set<Map.Entry<K, V>> entrySet() {
            final Set<Map.Entry<K, V>> result = this.entrySet;
            return (result == null) ? (this.entrySet = Sets.filter(this.unfiltered.entrySet(), this.predicate)) : result;
        }
        
        public Set<K> keySet() {
            final Set<K> result = this.keySet;
            return (result == null) ? (this.keySet = Sets.filter(this.unfiltered.keySet(), this.keyPredicate)) : result;
        }
        
        public boolean containsKey(final Object key) {
            return this.unfiltered.containsKey(key) && this.keyPredicate.apply((Object)key);
        }
    }
    
    static class FilteredEntryMap<K, V> extends AbstractFilteredMap<K, V>
    {
        final Set<Map.Entry<K, V>> filteredEntrySet;
        Set<Map.Entry<K, V>> entrySet;
        Set<K> keySet;
        
        FilteredEntryMap(final Map<K, V> unfiltered, final Predicate<? super Map.Entry<K, V>> entryPredicate) {
            super(unfiltered, entryPredicate);
            this.filteredEntrySet = Sets.filter(unfiltered.entrySet(), this.predicate);
        }
        
        public Set<Map.Entry<K, V>> entrySet() {
            final Set<Map.Entry<K, V>> result = this.entrySet;
            return (result == null) ? (this.entrySet = new EntrySet()) : result;
        }
        
        public Set<K> keySet() {
            final Set<K> result = this.keySet;
            return (result == null) ? (this.keySet = new KeySet()) : result;
        }
        
        private class EntrySet extends ForwardingSet<Map.Entry<K, V>>
        {
            protected Set<Map.Entry<K, V>> delegate() {
                return FilteredEntryMap.this.filteredEntrySet;
            }
            
            public Iterator<Map.Entry<K, V>> iterator() {
                final Iterator<Map.Entry<K, V>> iterator = FilteredEntryMap.this.filteredEntrySet.iterator();
                return new UnmodifiableIterator<Map.Entry<K, V>>() {
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }
                    
                    public Map.Entry<K, V> next() {
                        final Map.Entry<K, V> entry = iterator.next();
                        return new ForwardingMapEntry<K, V>() {
                            protected Map.Entry<K, V> delegate() {
                                return entry;
                            }
                            
                            public V setValue(final V value) {
                                Preconditions.checkArgument(FilteredEntryMap.this.apply(entry.getKey(), value));
                                return super.setValue(value);
                            }
                        };
                    }
                };
            }
        }
        
        private class KeySet extends AbstractSet<K>
        {
            public Iterator<K> iterator() {
                final Iterator<Map.Entry<K, V>> iterator = FilteredEntryMap.this.filteredEntrySet.iterator();
                return new UnmodifiableIterator<K>() {
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }
                    
                    public K next() {
                        return iterator.next().getKey();
                    }
                };
            }
            
            public int size() {
                return FilteredEntryMap.this.filteredEntrySet.size();
            }
            
            public void clear() {
                FilteredEntryMap.this.filteredEntrySet.clear();
            }
            
            public boolean contains(final Object o) {
                return FilteredEntryMap.this.containsKey(o);
            }
            
            public boolean remove(final Object o) {
                if (FilteredEntryMap.this.containsKey(o)) {
                    FilteredEntryMap.this.unfiltered.remove(o);
                    return true;
                }
                return false;
            }
            
            public boolean removeAll(final Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                boolean changed = false;
                for (final Object obj : collection) {
                    changed |= this.remove(obj);
                }
                return changed;
            }
            
            public boolean retainAll(final Collection<?> collection) {
                Preconditions.checkNotNull(collection);
                boolean changed = false;
                final Iterator<Map.Entry<K, V>> iterator = (Iterator<Map.Entry<K, V>>)FilteredEntryMap.this.unfiltered.entrySet().iterator();
                while (iterator.hasNext()) {
                    final Map.Entry<K, V> entry = iterator.next();
                    if (!collection.contains(entry.getKey()) && FilteredEntryMap.this.predicate.apply((Object)entry)) {
                        iterator.remove();
                        changed = true;
                    }
                }
                return changed;
            }
            
            public Object[] toArray() {
                return Lists.newArrayList(this.iterator()).toArray();
            }
            
            public <T> T[] toArray(final T[] array) {
                return Lists.newArrayList(this.iterator()).toArray(array);
            }
        }
    }
    
    @GwtCompatible
    abstract static class ImprovedAbstractMap<K, V> extends AbstractMap<K, V>
    {
        private Set<Map.Entry<K, V>> entrySet;
        private Set<K> keySet;
        private Collection<V> values;
        
        protected abstract Set<Map.Entry<K, V>> createEntrySet();
        
        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> result = this.entrySet;
            if (result == null) {
                result = (this.entrySet = this.createEntrySet());
            }
            return result;
        }
        
        public Set<K> keySet() {
            final Set<K> result = this.keySet;
            if (result == null) {
                return this.keySet = (Set<K>)new KeySet<K, V>() {
                    Map<K, V> map() {
                        return (Map<K, V>)ImprovedAbstractMap.this;
                    }
                };
            }
            return result;
        }
        
        public Collection<V> values() {
            final Collection<V> result = this.values;
            if (result == null) {
                return this.values = (Collection<V>)new Values<K, V>() {
                    Map<K, V> map() {
                        return (Map<K, V>)ImprovedAbstractMap.this;
                    }
                };
            }
            return result;
        }
        
        public boolean isEmpty() {
            return this.entrySet().isEmpty();
        }
    }
    
    abstract static class KeySet<K, V> extends AbstractSet<K>
    {
        abstract Map<K, V> map();
        
        public Iterator<K> iterator() {
            return Iterators.transform(this.map().entrySet().iterator(), (Function<? super Map.Entry<K, V>, ? extends K>)new Function<Map.Entry<K, V>, K>() {
                public K apply(final Map.Entry<K, V> entry) {
                    return entry.getKey();
                }
            });
        }
        
        public int size() {
            return this.map().size();
        }
        
        public boolean isEmpty() {
            return this.map().isEmpty();
        }
        
        public boolean contains(final Object o) {
            return this.map().containsKey(o);
        }
        
        public boolean remove(final Object o) {
            if (this.contains(o)) {
                this.map().remove(o);
                return true;
            }
            return false;
        }
        
        public boolean removeAll(final Collection<?> c) {
            return super.removeAll(Preconditions.checkNotNull(c));
        }
        
        public void clear() {
            this.map().clear();
        }
    }
    
    abstract static class Values<K, V> extends AbstractCollection<V>
    {
        abstract Map<K, V> map();
        
        public Iterator<V> iterator() {
            return Iterators.transform(this.map().entrySet().iterator(), (Function<? super Map.Entry<K, V>, ? extends V>)new Function<Map.Entry<K, V>, V>() {
                public V apply(final Map.Entry<K, V> entry) {
                    return entry.getValue();
                }
            });
        }
        
        public boolean remove(final Object o) {
            try {
                return super.remove(o);
            }
            catch (UnsupportedOperationException e) {
                for (final Map.Entry<K, V> entry : this.map().entrySet()) {
                    if (Objects.equal(o, entry.getValue())) {
                        this.map().remove(entry.getKey());
                        return true;
                    }
                }
                return false;
            }
        }
        
        public boolean removeAll(final Collection<?> c) {
            try {
                return super.removeAll(Preconditions.checkNotNull(c));
            }
            catch (UnsupportedOperationException e) {
                final Set<K> toRemove = (Set<K>)Sets.newHashSet();
                for (final Map.Entry<K, V> entry : this.map().entrySet()) {
                    if (c.contains(entry.getValue())) {
                        toRemove.add(entry.getKey());
                    }
                }
                return this.map().keySet().removeAll(toRemove);
            }
        }
        
        public boolean retainAll(final Collection<?> c) {
            try {
                return super.retainAll(Preconditions.checkNotNull(c));
            }
            catch (UnsupportedOperationException e) {
                final Set<K> toRetain = (Set<K>)Sets.newHashSet();
                for (final Map.Entry<K, V> entry : this.map().entrySet()) {
                    if (c.contains(entry.getValue())) {
                        toRetain.add(entry.getKey());
                    }
                }
                return this.map().keySet().retainAll(toRetain);
            }
        }
        
        public int size() {
            return this.map().size();
        }
        
        public boolean isEmpty() {
            return this.map().isEmpty();
        }
        
        public boolean contains(@Nullable final Object o) {
            return this.map().containsValue(o);
        }
        
        public void clear() {
            this.map().clear();
        }
    }
    
    abstract static class EntrySet<K, V> extends AbstractSet<Map.Entry<K, V>>
    {
        abstract Map<K, V> map();
        
        public int size() {
            return this.map().size();
        }
        
        public void clear() {
            this.map().clear();
        }
        
        public boolean contains(final Object o) {
            if (o instanceof Map.Entry) {
                final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
                final Object key = entry.getKey();
                final V value = this.map().get(key);
                return Objects.equal(value, entry.getValue()) && (value != null || this.map().containsKey(key));
            }
            return false;
        }
        
        public boolean isEmpty() {
            return this.map().isEmpty();
        }
        
        public boolean remove(final Object o) {
            if (this.contains(o)) {
                final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
                return this.map().keySet().remove(entry.getKey());
            }
            return false;
        }
        
        public boolean removeAll(final Collection<?> c) {
            try {
                return super.removeAll(Preconditions.checkNotNull(c));
            }
            catch (UnsupportedOperationException e) {
                boolean changed = true;
                for (final Object o : c) {
                    changed |= this.remove(o);
                }
                return changed;
            }
        }
        
        public boolean retainAll(final Collection<?> c) {
            try {
                return super.retainAll(Preconditions.checkNotNull(c));
            }
            catch (UnsupportedOperationException e) {
                final Set<Object> keys = Sets.newHashSetWithExpectedSize(c.size());
                for (final Object o : c) {
                    if (this.contains(o)) {
                        final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
                        keys.add(entry.getKey());
                    }
                }
                return this.map().keySet().retainAll(keys);
            }
        }
    }
    
    public interface EntryTransformer<K, V1, V2>
    {
        V2 transformEntry(@Nullable final K p0, @Nullable final V1 p1);
    }
}
