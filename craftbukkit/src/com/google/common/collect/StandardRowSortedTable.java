// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.Comparator;
import java.util.Set;
import com.google.common.base.Supplier;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
class StandardRowSortedTable<R, C, V> extends StandardTable<R, C, V> implements RowSortedTable<R, C, V>
{
    private transient SortedSet<R> rowKeySet;
    private transient RowSortedMap rowMap;
    private static final long serialVersionUID = 0L;
    
    StandardRowSortedTable(final SortedMap<R, Map<C, V>> backingMap, final Supplier<? extends Map<C, V>> factory) {
        super(backingMap, factory);
    }
    
    private SortedMap<R, Map<C, V>> sortedBackingMap() {
        return (SortedMap<R, Map<C, V>>)(SortedMap)this.backingMap;
    }
    
    public SortedSet<R> rowKeySet() {
        final SortedSet<R> result = this.rowKeySet;
        return (result == null) ? (this.rowKeySet = new RowKeySortedSet()) : result;
    }
    
    public SortedMap<R, Map<C, V>> rowMap() {
        final RowSortedMap result = this.rowMap;
        return (result == null) ? (this.rowMap = new RowSortedMap()) : result;
    }
    
    private class RowKeySortedSet extends RowKeySet implements SortedSet<R>
    {
        public Comparator<? super R> comparator() {
            return (Comparator<? super R>)StandardRowSortedTable.this.sortedBackingMap().comparator();
        }
        
        public R first() {
            return StandardRowSortedTable.this.sortedBackingMap().firstKey();
        }
        
        public R last() {
            return StandardRowSortedTable.this.sortedBackingMap().lastKey();
        }
        
        public SortedSet<R> headSet(final R toElement) {
            Preconditions.checkNotNull(toElement);
            return new StandardRowSortedTable((SortedMap<Object, Map<Object, Object>>)StandardRowSortedTable.this.sortedBackingMap().headMap(toElement), (Supplier<? extends Map<Object, Object>>)StandardRowSortedTable.this.factory).rowKeySet();
        }
        
        public SortedSet<R> subSet(final R fromElement, final R toElement) {
            Preconditions.checkNotNull(fromElement);
            Preconditions.checkNotNull(toElement);
            return new StandardRowSortedTable((SortedMap<Object, Map<Object, Object>>)StandardRowSortedTable.this.sortedBackingMap().subMap(fromElement, toElement), (Supplier<? extends Map<Object, Object>>)StandardRowSortedTable.this.factory).rowKeySet();
        }
        
        public SortedSet<R> tailSet(final R fromElement) {
            Preconditions.checkNotNull(fromElement);
            return new StandardRowSortedTable((SortedMap<Object, Map<Object, Object>>)StandardRowSortedTable.this.sortedBackingMap().tailMap(fromElement), (Supplier<? extends Map<Object, Object>>)StandardRowSortedTable.this.factory).rowKeySet();
        }
    }
    
    private class RowSortedMap extends RowMap implements SortedMap<R, Map<C, V>>
    {
        public Comparator<? super R> comparator() {
            return (Comparator<? super R>)StandardRowSortedTable.this.sortedBackingMap().comparator();
        }
        
        public R firstKey() {
            return StandardRowSortedTable.this.sortedBackingMap().firstKey();
        }
        
        public R lastKey() {
            return StandardRowSortedTable.this.sortedBackingMap().lastKey();
        }
        
        public SortedMap<R, Map<C, V>> headMap(final R toKey) {
            Preconditions.checkNotNull(toKey);
            return new StandardRowSortedTable((SortedMap<Object, Map<Object, Object>>)StandardRowSortedTable.this.sortedBackingMap().headMap(toKey), (Supplier<? extends Map<Object, Object>>)StandardRowSortedTable.this.factory).rowMap();
        }
        
        public SortedMap<R, Map<C, V>> subMap(final R fromKey, final R toKey) {
            Preconditions.checkNotNull(fromKey);
            Preconditions.checkNotNull(toKey);
            return new StandardRowSortedTable((SortedMap<Object, Map<Object, Object>>)StandardRowSortedTable.this.sortedBackingMap().subMap(fromKey, toKey), (Supplier<? extends Map<Object, Object>>)StandardRowSortedTable.this.factory).rowMap();
        }
        
        public SortedMap<R, Map<C, V>> tailMap(final R fromKey) {
            Preconditions.checkNotNull(fromKey);
            return new StandardRowSortedTable((SortedMap<Object, Map<Object, Object>>)StandardRowSortedTable.this.sortedBackingMap().tailMap(fromKey), (Supplier<? extends Map<Object, Object>>)StandardRowSortedTable.this.factory).rowMap();
        }
    }
}
