// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import com.google.common.base.Objects;
import java.util.Arrays;
import java.lang.reflect.Array;
import javax.annotation.Nullable;
import com.google.common.base.Preconditions;
import java.util.Collection;
import com.google.common.annotations.Beta;
import java.io.Serializable;

@Beta
public final class ArrayTable<R, C, V> implements Table<R, C, V>, Serializable
{
    private final ImmutableList<R> rowList;
    private final ImmutableList<C> columnList;
    private final ImmutableMap<R, Integer> rowKeyToIndex;
    private final ImmutableMap<C, Integer> columnKeyToIndex;
    private final V[][] array;
    private transient CellSet cellSet;
    private transient ColumnMap columnMap;
    private transient RowMap rowMap;
    private transient Collection<V> values;
    private static final long serialVersionUID = 0L;
    
    public static <R, C, V> ArrayTable<R, C, V> create(final Iterable<? extends R> rowKeys, final Iterable<? extends C> columnKeys) {
        return new ArrayTable<R, C, V>(rowKeys, columnKeys);
    }
    
    public static <R, C, V> ArrayTable<R, C, V> create(final Table<R, C, V> table) {
        return new ArrayTable<R, C, V>(table);
    }
    
    public static <R, C, V> ArrayTable<R, C, V> create(final ArrayTable<R, C, V> table) {
        return new ArrayTable<R, C, V>(table);
    }
    
    private ArrayTable(final Iterable<? extends R> rowKeys, final Iterable<? extends C> columnKeys) {
        this.rowList = ImmutableList.copyOf(rowKeys);
        this.columnList = ImmutableList.copyOf(columnKeys);
        Preconditions.checkArgument(!this.rowList.isEmpty());
        Preconditions.checkArgument(!this.columnList.isEmpty());
        final ImmutableMap.Builder<R, Integer> rowBuilder = ImmutableMap.builder();
        for (int i = 0; i < this.rowList.size(); ++i) {
            rowBuilder.put(this.rowList.get(i), i);
        }
        this.rowKeyToIndex = rowBuilder.build();
        final ImmutableMap.Builder<C, Integer> columnBuilder = ImmutableMap.builder();
        for (int j = 0; j < this.columnList.size(); ++j) {
            columnBuilder.put(this.columnList.get(j), j);
        }
        this.columnKeyToIndex = columnBuilder.build();
        final V[][] tmpArray = (V[][])new Object[this.rowList.size()][this.columnList.size()];
        this.array = tmpArray;
    }
    
    private ArrayTable(final Table<R, C, V> table) {
        this((Iterable)table.rowKeySet(), (Iterable)table.columnKeySet());
        this.putAll((Table<? extends R, ? extends C, ? extends V>)table);
    }
    
    private ArrayTable(final ArrayTable<R, C, V> table) {
        this.rowList = table.rowList;
        this.columnList = table.columnList;
        this.rowKeyToIndex = table.rowKeyToIndex;
        this.columnKeyToIndex = table.columnKeyToIndex;
        final V[][] copy = (V[][])new Object[this.rowList.size()][this.columnList.size()];
        this.array = copy;
        for (int i = 0; i < this.rowList.size(); ++i) {
            System.arraycopy(table.array[i], 0, copy[i], 0, table.array[i].length);
        }
    }
    
    public ImmutableList<R> rowKeyList() {
        return this.rowList;
    }
    
    public ImmutableList<C> columnKeyList() {
        return this.columnList;
    }
    
    public V at(final int rowIndex, final int columnIndex) {
        return (V)this.array[rowIndex][columnIndex];
    }
    
    public V set(final int rowIndex, final int columnIndex, @Nullable final V value) {
        final V oldValue = (V)this.array[rowIndex][columnIndex];
        this.array[rowIndex][columnIndex] = value;
        return oldValue;
    }
    
    public V[][] toArray(final Class<V> valueClass) {
        final V[][] copy = (V[][])Array.newInstance(valueClass, this.rowList.size(), this.columnList.size());
        for (int i = 0; i < this.rowList.size(); ++i) {
            System.arraycopy(this.array[i], 0, copy[i], 0, this.array[i].length);
        }
        return copy;
    }
    
    @Deprecated
    public void clear() {
        throw new UnsupportedOperationException();
    }
    
    public void eraseAll() {
        for (final V[] row : this.array) {
            Arrays.fill(row, null);
        }
    }
    
    public boolean contains(@Nullable final Object rowKey, @Nullable final Object columnKey) {
        return this.containsRow(rowKey) && this.containsColumn(columnKey);
    }
    
    public boolean containsColumn(@Nullable final Object columnKey) {
        return this.columnKeyToIndex.containsKey(columnKey);
    }
    
    public boolean containsRow(@Nullable final Object rowKey) {
        return this.rowKeyToIndex.containsKey(rowKey);
    }
    
    public boolean containsValue(@Nullable final Object value) {
        for (final Object[] arr$2 : this.array) {
            final V[] row = (V[])arr$2;
            for (final V element : arr$2) {
                if (Objects.equal(value, element)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public V get(@Nullable final Object rowKey, @Nullable final Object columnKey) {
        final Integer rowIndex = this.rowKeyToIndex.get(rowKey);
        final Integer columnIndex = this.columnKeyToIndex.get(columnKey);
        return this.getIndexed(rowIndex, columnIndex);
    }
    
    private V getIndexed(final Integer rowIndex, final Integer columnIndex) {
        return (V)((rowIndex == null || columnIndex == null) ? null : this.array[rowIndex][columnIndex]);
    }
    
    public boolean isEmpty() {
        return false;
    }
    
    public V put(final R rowKey, final C columnKey, @Nullable final V value) {
        Preconditions.checkNotNull(rowKey);
        Preconditions.checkNotNull(columnKey);
        final Integer rowIndex = this.rowKeyToIndex.get(rowKey);
        Preconditions.checkArgument(rowIndex != null, "Row %s not in %s", rowKey, this.rowList);
        final Integer columnIndex = this.columnKeyToIndex.get(columnKey);
        Preconditions.checkArgument(columnIndex != null, "Column %s not in %s", columnKey, this.columnList);
        return this.set(rowIndex, columnIndex, value);
    }
    
    public void putAll(final Table<? extends R, ? extends C, ? extends V> table) {
        for (final Cell<? extends R, ? extends C, ? extends V> cell : table.cellSet()) {
            this.put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
        }
    }
    
    @Deprecated
    public V remove(final Object rowKey, final Object columnKey) {
        throw new UnsupportedOperationException();
    }
    
    public V erase(@Nullable final Object rowKey, @Nullable final Object columnKey) {
        final Integer rowIndex = this.rowKeyToIndex.get(rowKey);
        final Integer columnIndex = this.columnKeyToIndex.get(columnKey);
        if (rowIndex == null || columnIndex == null) {
            return null;
        }
        return this.set(rowIndex, columnIndex, null);
    }
    
    public int size() {
        return this.rowList.size() * this.columnList.size();
    }
    
    public boolean equals(@Nullable final Object obj) {
        if (obj instanceof Table) {
            final Table<?, ?, ?> other = (Table<?, ?, ?>)obj;
            return this.cellSet().equals(other.cellSet());
        }
        return false;
    }
    
    public int hashCode() {
        return this.cellSet().hashCode();
    }
    
    public String toString() {
        return this.rowMap().toString();
    }
    
    public Set<Cell<R, C, V>> cellSet() {
        final CellSet set = this.cellSet;
        return (set == null) ? (this.cellSet = new CellSet()) : set;
    }
    
    public Map<R, V> column(final C columnKey) {
        Preconditions.checkNotNull(columnKey);
        final Integer columnIndex = this.columnKeyToIndex.get(columnKey);
        return (Map<R, V>)((columnIndex == null) ? ImmutableMap.of() : new Column(columnIndex));
    }
    
    public ImmutableSet<C> columnKeySet() {
        return this.columnKeyToIndex.keySet();
    }
    
    public Map<C, Map<R, V>> columnMap() {
        final ColumnMap map = this.columnMap;
        return (map == null) ? (this.columnMap = new ColumnMap()) : map;
    }
    
    public Map<C, V> row(final R rowKey) {
        Preconditions.checkNotNull(rowKey);
        final Integer rowIndex = this.rowKeyToIndex.get(rowKey);
        return (Map<C, V>)((rowIndex == null) ? ImmutableMap.of() : new Row(rowIndex));
    }
    
    public ImmutableSet<R> rowKeySet() {
        return this.rowKeyToIndex.keySet();
    }
    
    public Map<R, Map<C, V>> rowMap() {
        final RowMap map = this.rowMap;
        return (map == null) ? (this.rowMap = new RowMap()) : map;
    }
    
    public Collection<V> values() {
        final Collection<V> v = this.values;
        return (v == null) ? (this.values = new Values()) : v;
    }
    
    private class CellSet extends AbstractSet<Cell<R, C, V>>
    {
        public Iterator<Cell<R, C, V>> iterator() {
            return new AbstractIndexedListIterator<Cell<R, C, V>>(this.size()) {
                protected Cell<R, C, V> get(final int index) {
                    return new Tables.AbstractCell<R, C, V>() {
                        final int rowIndex = index / ArrayTable.this.columnList.size();
                        final int columnIndex = index % ArrayTable.this.columnList.size();
                        
                        public R getRowKey() {
                            return (R)ArrayTable.this.rowList.get(this.rowIndex);
                        }
                        
                        public C getColumnKey() {
                            return (C)ArrayTable.this.columnList.get(this.columnIndex);
                        }
                        
                        public V getValue() {
                            return (V)ArrayTable.this.array[this.rowIndex][this.columnIndex];
                        }
                    };
                }
            };
        }
        
        public int size() {
            return ArrayTable.this.size();
        }
        
        public boolean contains(final Object obj) {
            if (obj instanceof Cell) {
                final Cell<?, ?, ?> cell = (Cell<?, ?, ?>)obj;
                final Integer rowIndex = ArrayTable.this.rowKeyToIndex.get(cell.getRowKey());
                final Integer columnIndex = ArrayTable.this.columnKeyToIndex.get(cell.getColumnKey());
                return rowIndex != null && columnIndex != null && Objects.equal(ArrayTable.this.array[rowIndex][columnIndex], cell.getValue());
            }
            return false;
        }
    }
    
    private class Column extends AbstractMap<R, V>
    {
        final int columnIndex;
        ColumnEntrySet entrySet;
        
        Column(final int columnIndex) {
            this.columnIndex = columnIndex;
        }
        
        public Set<Map.Entry<R, V>> entrySet() {
            final ColumnEntrySet set = this.entrySet;
            return (set == null) ? (this.entrySet = new ColumnEntrySet(this.columnIndex)) : set;
        }
        
        public V get(final Object rowKey) {
            final Integer rowIndex = ArrayTable.this.rowKeyToIndex.get(rowKey);
            return (V)ArrayTable.this.getIndexed(rowIndex, this.columnIndex);
        }
        
        public boolean containsKey(final Object rowKey) {
            return ArrayTable.this.rowKeyToIndex.containsKey(rowKey);
        }
        
        public V put(final R rowKey, final V value) {
            Preconditions.checkNotNull(rowKey);
            final Integer rowIndex = ArrayTable.this.rowKeyToIndex.get(rowKey);
            Preconditions.checkArgument(rowIndex != null, "Row %s not in %s", rowKey, ArrayTable.this.rowList);
            return ArrayTable.this.set(rowIndex, this.columnIndex, value);
        }
        
        public Set<R> keySet() {
            return ArrayTable.this.rowKeySet();
        }
    }
    
    private class ColumnEntrySet extends AbstractSet<Map.Entry<R, V>>
    {
        final int columnIndex;
        
        ColumnEntrySet(final int columnIndex) {
            this.columnIndex = columnIndex;
        }
        
        public Iterator<Map.Entry<R, V>> iterator() {
            return new AbstractIndexedListIterator<Map.Entry<R, V>>(this.size()) {
                protected Map.Entry<R, V> get(final int rowIndex) {
                    return new AbstractMapEntry<R, V>() {
                        public R getKey() {
                            return (R)ArrayTable.this.rowList.get(rowIndex);
                        }
                        
                        public V getValue() {
                            return (V)ArrayTable.this.array[rowIndex][ColumnEntrySet.this.columnIndex];
                        }
                        
                        public V setValue(final V value) {
                            return ArrayTable.this.set(rowIndex, ColumnEntrySet.this.columnIndex, value);
                        }
                    };
                }
            };
        }
        
        public int size() {
            return ArrayTable.this.rowList.size();
        }
    }
    
    private class ColumnMap extends AbstractMap<C, Map<R, V>>
    {
        transient ColumnMapEntrySet entrySet;
        
        public Set<Map.Entry<C, Map<R, V>>> entrySet() {
            final ColumnMapEntrySet set = this.entrySet;
            return (set == null) ? (this.entrySet = new ColumnMapEntrySet()) : set;
        }
        
        public Map<R, V> get(final Object columnKey) {
            final Integer columnIndex = ArrayTable.this.columnKeyToIndex.get(columnKey);
            return (columnIndex == null) ? null : new Column(columnIndex);
        }
        
        public boolean containsKey(final Object columnKey) {
            return ArrayTable.this.containsColumn(columnKey);
        }
        
        public Set<C> keySet() {
            return ArrayTable.this.columnKeySet();
        }
        
        public Map<R, V> remove(final Object columnKey) {
            throw new UnsupportedOperationException();
        }
    }
    
    private class ColumnMapEntrySet extends AbstractSet<Map.Entry<C, Map<R, V>>>
    {
        public Iterator<Map.Entry<C, Map<R, V>>> iterator() {
            return new AbstractIndexedListIterator<Map.Entry<C, Map<R, V>>>(this.size()) {
                protected Map.Entry<C, Map<R, V>> get(final int index) {
                    return (Map.Entry<C, Map<R, V>>)Maps.immutableEntry(ArrayTable.this.columnList.get(index), new Column(index));
                }
            };
        }
        
        public int size() {
            return ArrayTable.this.columnList.size();
        }
    }
    
    private class Row extends AbstractMap<C, V>
    {
        final int rowIndex;
        RowEntrySet entrySet;
        
        Row(final int rowIndex) {
            this.rowIndex = rowIndex;
        }
        
        public Set<Map.Entry<C, V>> entrySet() {
            final RowEntrySet set = this.entrySet;
            return (set == null) ? (this.entrySet = new RowEntrySet(this.rowIndex)) : set;
        }
        
        public V get(final Object columnKey) {
            final Integer columnIndex = ArrayTable.this.columnKeyToIndex.get(columnKey);
            return (V)ArrayTable.this.getIndexed(this.rowIndex, columnIndex);
        }
        
        public boolean containsKey(final Object columnKey) {
            return ArrayTable.this.containsColumn(columnKey);
        }
        
        public V put(final C columnKey, final V value) {
            Preconditions.checkNotNull(columnKey);
            final Integer columnIndex = ArrayTable.this.columnKeyToIndex.get(columnKey);
            Preconditions.checkArgument(columnIndex != null, "Column %s not in %s", columnKey, ArrayTable.this.columnList);
            return ArrayTable.this.set(this.rowIndex, columnIndex, value);
        }
        
        public Set<C> keySet() {
            return ArrayTable.this.columnKeySet();
        }
    }
    
    private class RowEntrySet extends AbstractSet<Map.Entry<C, V>>
    {
        final int rowIndex;
        
        RowEntrySet(final int rowIndex) {
            this.rowIndex = rowIndex;
        }
        
        public Iterator<Map.Entry<C, V>> iterator() {
            return new AbstractIndexedListIterator<Map.Entry<C, V>>(this.size()) {
                protected Map.Entry<C, V> get(final int columnIndex) {
                    return new AbstractMapEntry<C, V>() {
                        public C getKey() {
                            return (C)ArrayTable.this.columnList.get(columnIndex);
                        }
                        
                        public V getValue() {
                            return (V)ArrayTable.this.array[RowEntrySet.this.rowIndex][columnIndex];
                        }
                        
                        public V setValue(final V value) {
                            return ArrayTable.this.set(RowEntrySet.this.rowIndex, columnIndex, value);
                        }
                    };
                }
            };
        }
        
        public int size() {
            return ArrayTable.this.columnList.size();
        }
    }
    
    private class RowMap extends AbstractMap<R, Map<C, V>>
    {
        transient RowMapEntrySet entrySet;
        
        public Set<Map.Entry<R, Map<C, V>>> entrySet() {
            final RowMapEntrySet set = this.entrySet;
            return (set == null) ? (this.entrySet = new RowMapEntrySet()) : set;
        }
        
        public Map<C, V> get(final Object rowKey) {
            final Integer rowIndex = ArrayTable.this.rowKeyToIndex.get(rowKey);
            return (rowIndex == null) ? null : new Row(rowIndex);
        }
        
        public boolean containsKey(final Object rowKey) {
            return ArrayTable.this.containsRow(rowKey);
        }
        
        public Set<R> keySet() {
            return ArrayTable.this.rowKeySet();
        }
        
        public Map<C, V> remove(final Object rowKey) {
            throw new UnsupportedOperationException();
        }
    }
    
    private class RowMapEntrySet extends AbstractSet<Map.Entry<R, Map<C, V>>>
    {
        public Iterator<Map.Entry<R, Map<C, V>>> iterator() {
            return new AbstractIndexedListIterator<Map.Entry<R, Map<C, V>>>(this.size()) {
                protected Map.Entry<R, Map<C, V>> get(final int index) {
                    return (Map.Entry<R, Map<C, V>>)Maps.immutableEntry(ArrayTable.this.rowList.get(index), new Row(index));
                }
            };
        }
        
        public int size() {
            return ArrayTable.this.rowList.size();
        }
    }
    
    private class Values extends AbstractCollection<V>
    {
        public Iterator<V> iterator() {
            return new AbstractIndexedListIterator<V>(this.size()) {
                protected V get(final int index) {
                    final int rowIndex = index / ArrayTable.this.columnList.size();
                    final int columnIndex = index % ArrayTable.this.columnList.size();
                    return (V)ArrayTable.this.array[rowIndex][columnIndex];
                }
            };
        }
        
        public int size() {
            return ArrayTable.this.size();
        }
        
        public boolean contains(final Object value) {
            return ArrayTable.this.containsValue(value);
        }
    }
}
