// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.NoSuchElementException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import com.google.common.base.Function;
import java.util.Iterator;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.SortedSet;
import com.google.common.base.Supplier;
import java.util.SortedMap;
import java.util.TreeMap;
import com.google.common.base.Preconditions;
import java.util.Comparator;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true)
@Beta
public class TreeBasedTable<R, C, V> extends StandardRowSortedTable<R, C, V>
{
    private final Comparator<? super C> columnComparator;
    private static final long serialVersionUID = 0L;
    
    public static <R extends Comparable, C extends Comparable, V> TreeBasedTable<R, C, V> create() {
        return new TreeBasedTable<R, C, V>(Ordering.natural(), Ordering.natural());
    }
    
    public static <R, C, V> TreeBasedTable<R, C, V> create(final Comparator<? super R> rowComparator, final Comparator<? super C> columnComparator) {
        Preconditions.checkNotNull(rowComparator);
        Preconditions.checkNotNull(columnComparator);
        return new TreeBasedTable<R, C, V>(rowComparator, columnComparator);
    }
    
    public static <R, C, V> TreeBasedTable<R, C, V> create(final TreeBasedTable<R, C, ? extends V> table) {
        final TreeBasedTable<R, C, V> result = new TreeBasedTable<R, C, V>(table.rowComparator(), table.columnComparator());
        result.putAll((Table)table);
        return result;
    }
    
    TreeBasedTable(final Comparator<? super R> rowComparator, final Comparator<? super C> columnComparator) {
        super(new TreeMap(rowComparator), new Factory(columnComparator));
        this.columnComparator = columnComparator;
    }
    
    public Comparator<? super R> rowComparator() {
        return this.rowKeySet().comparator();
    }
    
    public Comparator<? super C> columnComparator() {
        return this.columnComparator;
    }
    
    public SortedMap<C, V> row(final R rowKey) {
        return new TreeRow(rowKey);
    }
    
    public SortedSet<R> rowKeySet() {
        return super.rowKeySet();
    }
    
    public SortedMap<R, Map<C, V>> rowMap() {
        return super.rowMap();
    }
    
    public boolean contains(@Nullable final Object rowKey, @Nullable final Object columnKey) {
        return super.contains(rowKey, columnKey);
    }
    
    public boolean containsColumn(@Nullable final Object columnKey) {
        return super.containsColumn(columnKey);
    }
    
    public boolean containsRow(@Nullable final Object rowKey) {
        return super.containsRow(rowKey);
    }
    
    public boolean containsValue(@Nullable final Object value) {
        return super.containsValue(value);
    }
    
    public V get(@Nullable final Object rowKey, @Nullable final Object columnKey) {
        return super.get(rowKey, columnKey);
    }
    
    public boolean equals(@Nullable final Object obj) {
        return super.equals(obj);
    }
    
    public V remove(@Nullable final Object rowKey, @Nullable final Object columnKey) {
        return super.remove(rowKey, columnKey);
    }
    
    Iterator<C> createColumnKeyIterator() {
        return new MergingIterator<C>(Iterables.transform((Iterable<Map<C, V>>)this.backingMap.values(), (Function<? super Map<C, V>, ? extends Iterator<C>>)new Function<Map<C, V>, Iterator<C>>() {
            public Iterator<C> apply(final Map<C, V> input) {
                return input.keySet().iterator();
            }
        }), this.columnComparator());
    }
    
    private static class Factory<C, V> implements Supplier<TreeMap<C, V>>, Serializable
    {
        final Comparator<? super C> comparator;
        private static final long serialVersionUID = 0L;
        
        Factory(final Comparator<? super C> comparator) {
            this.comparator = comparator;
        }
        
        public TreeMap<C, V> get() {
            return new TreeMap<C, V>(this.comparator);
        }
    }
    
    private class TreeRow extends Row implements SortedMap<C, V>
    {
        @Nullable
        final C lowerBound;
        @Nullable
        final C upperBound;
        transient SortedMap<C, V> wholeRow;
        
        TreeRow(final TreeBasedTable treeBasedTable, final R rowKey) {
            this(rowKey, null, null);
        }
        
        TreeRow(@Nullable final R rowKey, @Nullable final C lowerBound, final C upperBound) {
            super((StandardTable<R, C, V>)TreeBasedTable.this, rowKey);
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
            Preconditions.checkArgument(lowerBound == null || upperBound == null || this.compare(lowerBound, upperBound) <= 0);
        }
        
        public Comparator<? super C> comparator() {
            return TreeBasedTable.this.columnComparator();
        }
        
        int compare(final Object a, final Object b) {
            final Comparator<Object> cmp = (Comparator<Object>)this.comparator();
            return cmp.compare(a, b);
        }
        
        boolean rangeContains(@Nullable final Object o) {
            return o != null && (this.lowerBound == null || this.compare(this.lowerBound, o) <= 0) && (this.upperBound == null || this.compare(this.upperBound, o) > 0);
        }
        
        public SortedMap<C, V> subMap(final C fromKey, final C toKey) {
            Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(fromKey)) && this.rangeContains(Preconditions.checkNotNull(toKey)));
            return new TreeRow((R)this.rowKey, fromKey, toKey);
        }
        
        public SortedMap<C, V> headMap(final C toKey) {
            Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(toKey)));
            return new TreeRow((R)this.rowKey, this.lowerBound, toKey);
        }
        
        public SortedMap<C, V> tailMap(final C fromKey) {
            Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(fromKey)));
            return new TreeRow((R)this.rowKey, fromKey, this.upperBound);
        }
        
        public C firstKey() {
            final SortedMap<C, V> backing = this.backingRowMap();
            if (backing == null) {
                throw new NoSuchElementException();
            }
            return this.backingRowMap().firstKey();
        }
        
        public C lastKey() {
            final SortedMap<C, V> backing = this.backingRowMap();
            if (backing == null) {
                throw new NoSuchElementException();
            }
            return this.backingRowMap().lastKey();
        }
        
        SortedMap<C, V> wholeRow() {
            if (this.wholeRow == null || (this.wholeRow.isEmpty() && TreeBasedTable.this.backingMap.containsKey(this.rowKey))) {
                this.wholeRow = (SortedMap<C, V>)(SortedMap)TreeBasedTable.this.backingMap.get(this.rowKey);
            }
            return this.wholeRow;
        }
        
        SortedMap<C, V> backingRowMap() {
            return (SortedMap<C, V>)(SortedMap)super.backingRowMap();
        }
        
        SortedMap<C, V> computeBackingRowMap() {
            SortedMap<C, V> map = this.wholeRow();
            if (map != null) {
                if (this.lowerBound != null) {
                    map = map.tailMap(this.lowerBound);
                }
                if (this.upperBound != null) {
                    map = map.headMap(this.upperBound);
                }
                return map;
            }
            return null;
        }
        
        void maintainEmptyInvariant() {
            if (this.wholeRow() != null && this.wholeRow.isEmpty()) {
                TreeBasedTable.this.backingMap.remove(this.rowKey);
                this.wholeRow = null;
                this.backingRowMap = null;
            }
        }
        
        public boolean containsKey(final Object key) {
            return this.rangeContains(key) && super.containsKey(key);
        }
        
        public V put(final C key, final V value) {
            Preconditions.checkArgument(this.rangeContains(Preconditions.checkNotNull(key)));
            return super.put(key, value);
        }
    }
    
    private static class MergingIterator<T> extends AbstractIterator<T>
    {
        private final Queue<PeekingIterator<T>> queue;
        private final Comparator<? super T> comparator;
        private T lastValue;
        
        public MergingIterator(final Iterable<? extends Iterator<T>> iterators, final Comparator<? super T> itemComparator) {
            this.lastValue = null;
            this.comparator = itemComparator;
            final Comparator<PeekingIterator<T>> heapComparator = new Comparator<PeekingIterator<T>>() {
                public int compare(final PeekingIterator<T> o1, final PeekingIterator<T> o2) {
                    return MergingIterator.this.comparator.compare(o1.peek(), o2.peek());
                }
            };
            this.queue = new PriorityQueue<PeekingIterator<T>>(Math.max(1, Iterables.size(iterators)), heapComparator);
            for (final Iterator<T> iterator : iterators) {
                if (iterator.hasNext()) {
                    this.queue.add(Iterators.peekingIterator((Iterator<? extends T>)iterator));
                }
            }
        }
        
        protected T computeNext() {
            while (!this.queue.isEmpty()) {
                final PeekingIterator<T> nextIter = this.queue.poll();
                final T next = nextIter.next();
                final boolean duplicate = this.lastValue != null && this.comparator.compare((Object)next, (Object)this.lastValue) == 0;
                if (nextIter.hasNext()) {
                    this.queue.add(nextIter);
                }
                if (!duplicate) {
                    return this.lastValue = next;
                }
            }
            this.lastValue = null;
            return this.endOfData();
        }
    }
}
