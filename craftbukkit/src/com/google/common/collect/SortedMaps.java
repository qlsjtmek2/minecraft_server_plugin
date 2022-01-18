// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Predicates;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Iterator;
import com.google.common.base.Objects;
import java.util.Comparator;
import java.util.Map;
import com.google.common.base.Preconditions;
import com.google.common.base.Function;
import java.util.SortedMap;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.Beta;

@Beta
@GwtCompatible
public final class SortedMaps
{
    public static <K, V1, V2> SortedMap<K, V2> transformValues(final SortedMap<K, V1> fromMap, final Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        final Maps.EntryTransformer<K, V1, V2> transformer = new Maps.EntryTransformer<K, V1, V2>() {
            public V2 transformEntry(final K key, final V1 value) {
                return function.apply(value);
            }
        };
        return transformEntries(fromMap, transformer);
    }
    
    public static <K, V1, V2> SortedMap<K, V2> transformEntries(final SortedMap<K, V1> fromMap, final Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
        return new TransformedEntriesSortedMap<K, Object, V2>(fromMap, transformer);
    }
    
    public static <K, V> SortedMapDifference<K, V> difference(final SortedMap<K, ? extends V> left, final Map<? extends K, ? extends V> right) {
        final Comparator<? super K> comparator = orNaturalOrder((Comparator<? super Object>)left.comparator());
        final SortedMap<K, V> onlyOnLeft = (SortedMap<K, V>)Maps.newTreeMap(comparator);
        final SortedMap<K, V> onlyOnRight = (SortedMap<K, V>)Maps.newTreeMap(comparator);
        onlyOnRight.putAll((Map<?, ?>)right);
        final SortedMap<K, V> onBoth = (SortedMap<K, V>)Maps.newTreeMap(comparator);
        final SortedMap<K, MapDifference.ValueDifference<V>> differences = (SortedMap<K, MapDifference.ValueDifference<V>>)Maps.newTreeMap(comparator);
        boolean eq = true;
        for (final Map.Entry<? extends K, ? extends V> entry : left.entrySet()) {
            final K leftKey = (K)entry.getKey();
            final V leftValue = (V)entry.getValue();
            if (right.containsKey(leftKey)) {
                final V rightValue = onlyOnRight.remove(leftKey);
                if (Objects.equal(leftValue, rightValue)) {
                    onBoth.put(leftKey, leftValue);
                }
                else {
                    eq = false;
                    differences.put(leftKey, Maps.ValueDifferenceImpl.create(leftValue, rightValue));
                }
            }
            else {
                eq = false;
                onlyOnLeft.put(leftKey, leftValue);
            }
        }
        final boolean areEqual = eq && onlyOnRight.isEmpty();
        return sortedMapDifference(areEqual, onlyOnLeft, onlyOnRight, onBoth, differences);
    }
    
    private static <K, V> SortedMapDifference<K, V> sortedMapDifference(final boolean areEqual, final SortedMap<K, V> onlyOnLeft, final SortedMap<K, V> onlyOnRight, final SortedMap<K, V> onBoth, final SortedMap<K, MapDifference.ValueDifference<V>> differences) {
        return new SortedMapDifferenceImpl<K, V>(areEqual, Collections.unmodifiableSortedMap((SortedMap<K, ? extends V>)onlyOnLeft), Collections.unmodifiableSortedMap((SortedMap<K, ? extends V>)onlyOnRight), Collections.unmodifiableSortedMap((SortedMap<K, ? extends V>)onBoth), Collections.unmodifiableSortedMap((SortedMap<K, ? extends MapDifference.ValueDifference<V>>)differences));
    }
    
    static <E> Comparator<? super E> orNaturalOrder(@Nullable final Comparator<? super E> comparator) {
        if (comparator != null) {
            return comparator;
        }
        return (Comparator<? super E>)Ordering.natural();
    }
    
    @GwtIncompatible("untested")
    public static <K, V> SortedMap<K, V> filterKeys(final SortedMap<K, V> unfiltered, final Predicate<? super K> keyPredicate) {
        Preconditions.checkNotNull(keyPredicate);
        final Predicate<Map.Entry<K, V>> entryPredicate = new Predicate<Map.Entry<K, V>>() {
            public boolean apply(final Map.Entry<K, V> input) {
                return keyPredicate.apply(input.getKey());
            }
        };
        return (SortedMap<K, V>)filterEntries((SortedMap<Object, Object>)unfiltered, (Predicate<? super Map.Entry<Object, Object>>)entryPredicate);
    }
    
    @GwtIncompatible("untested")
    public static <K, V> SortedMap<K, V> filterValues(final SortedMap<K, V> unfiltered, final Predicate<? super V> valuePredicate) {
        Preconditions.checkNotNull(valuePredicate);
        final Predicate<Map.Entry<K, V>> entryPredicate = new Predicate<Map.Entry<K, V>>() {
            public boolean apply(final Map.Entry<K, V> input) {
                return valuePredicate.apply(input.getValue());
            }
        };
        return (SortedMap<K, V>)filterEntries((SortedMap<Object, Object>)unfiltered, (Predicate<? super Map.Entry<Object, Object>>)entryPredicate);
    }
    
    @GwtIncompatible("untested")
    public static <K, V> SortedMap<K, V> filterEntries(final SortedMap<K, V> unfiltered, final Predicate<? super Map.Entry<K, V>> entryPredicate) {
        Preconditions.checkNotNull(entryPredicate);
        return (unfiltered instanceof FilteredSortedMap) ? filterFiltered((FilteredSortedMap<K, V>)(FilteredSortedMap)unfiltered, entryPredicate) : new FilteredSortedMap<K, V>(Preconditions.checkNotNull(unfiltered), entryPredicate);
    }
    
    private static <K, V> SortedMap<K, V> filterFiltered(final FilteredSortedMap<K, V> map, final Predicate<? super Map.Entry<K, V>> entryPredicate) {
        final Predicate<Map.Entry<K, V>> predicate = Predicates.and(map.predicate, entryPredicate);
        return new FilteredSortedMap<K, V>(map.sortedMap(), predicate);
    }
    
    static class TransformedEntriesSortedMap<K, V1, V2> extends Maps.TransformedEntriesMap<K, V1, V2> implements SortedMap<K, V2>
    {
        protected SortedMap<K, V1> fromMap() {
            return (SortedMap<K, V1>)(SortedMap)this.fromMap;
        }
        
        TransformedEntriesSortedMap(final SortedMap<K, V1> fromMap, final Maps.EntryTransformer<? super K, ? super V1, V2> transformer) {
            super(fromMap, transformer);
        }
        
        public Comparator<? super K> comparator() {
            return this.fromMap().comparator();
        }
        
        public K firstKey() {
            return this.fromMap().firstKey();
        }
        
        public SortedMap<K, V2> headMap(final K toKey) {
            return SortedMaps.transformEntries(this.fromMap().headMap(toKey), this.transformer);
        }
        
        public K lastKey() {
            return this.fromMap().lastKey();
        }
        
        public SortedMap<K, V2> subMap(final K fromKey, final K toKey) {
            return SortedMaps.transformEntries(this.fromMap().subMap(fromKey, toKey), this.transformer);
        }
        
        public SortedMap<K, V2> tailMap(final K fromKey) {
            return SortedMaps.transformEntries(this.fromMap().tailMap(fromKey), this.transformer);
        }
    }
    
    static class SortedMapDifferenceImpl<K, V> extends Maps.MapDifferenceImpl<K, V> implements SortedMapDifference<K, V>
    {
        SortedMapDifferenceImpl(final boolean areEqual, final SortedMap<K, V> onlyOnLeft, final SortedMap<K, V> onlyOnRight, final SortedMap<K, V> onBoth, final SortedMap<K, MapDifference.ValueDifference<V>> differences) {
            super(areEqual, onlyOnLeft, onlyOnRight, onBoth, differences);
        }
        
        public SortedMap<K, MapDifference.ValueDifference<V>> entriesDiffering() {
            return (SortedMap<K, MapDifference.ValueDifference<V>>)(SortedMap)super.entriesDiffering();
        }
        
        public SortedMap<K, V> entriesInCommon() {
            return (SortedMap<K, V>)(SortedMap)super.entriesInCommon();
        }
        
        public SortedMap<K, V> entriesOnlyOnLeft() {
            return (SortedMap<K, V>)(SortedMap)super.entriesOnlyOnLeft();
        }
        
        public SortedMap<K, V> entriesOnlyOnRight() {
            return (SortedMap<K, V>)(SortedMap)super.entriesOnlyOnRight();
        }
    }
    
    private static class FilteredSortedMap<K, V> extends Maps.FilteredEntryMap<K, V> implements SortedMap<K, V>
    {
        FilteredSortedMap(final SortedMap<K, V> unfiltered, final Predicate<? super Map.Entry<K, V>> entryPredicate) {
            super(unfiltered, entryPredicate);
        }
        
        SortedMap<K, V> sortedMap() {
            return (SortedMap<K, V>)(SortedMap)this.unfiltered;
        }
        
        public Comparator<? super K> comparator() {
            return this.sortedMap().comparator();
        }
        
        public K firstKey() {
            return this.keySet().iterator().next();
        }
        
        public K lastKey() {
            SortedMap<K, V> headMap = this.sortedMap();
            K key;
            while (true) {
                key = headMap.lastKey();
                if (this.apply(key, this.unfiltered.get(key))) {
                    break;
                }
                headMap = this.sortedMap().headMap(key);
            }
            return key;
        }
        
        public SortedMap<K, V> headMap(final K toKey) {
            return new FilteredSortedMap((SortedMap<Object, Object>)this.sortedMap().headMap(toKey), (Predicate<? super Map.Entry<Object, Object>>)this.predicate);
        }
        
        public SortedMap<K, V> subMap(final K fromKey, final K toKey) {
            return new FilteredSortedMap((SortedMap<Object, Object>)this.sortedMap().subMap(fromKey, toKey), (Predicate<? super Map.Entry<Object, Object>>)this.predicate);
        }
        
        public SortedMap<K, V> tailMap(final K fromKey) {
            return new FilteredSortedMap((SortedMap<Object, Object>)this.sortedMap().tailMap(fromKey), (Predicate<? super Map.Entry<Object, Object>>)this.predicate);
        }
    }
}
