// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Collection;
import java.util.Set;
import com.google.common.base.Objects;
import java.io.Serializable;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.util.Map;
import javax.annotation.Nullable;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
@Beta
public final class Tables
{
    public static <R, C, V> Table.Cell<R, C, V> immutableCell(@Nullable final R rowKey, @Nullable final C columnKey, @Nullable final V value) {
        return new ImmutableCell<R, C, V>(rowKey, columnKey, value);
    }
    
    public static <R, C, V> Table<C, R, V> transpose(final Table<R, C, V> table) {
        return (table instanceof TransposeTable) ? ((TransposeTable)table).original : new TransposeTable<C, R, V>((Table<Object, Object, Object>)table);
    }
    
    public static <R, C, V> Table<R, C, V> newCustomTable(final Map<R, Map<C, V>> backingMap, final Supplier<? extends Map<C, V>> factory) {
        Preconditions.checkArgument(backingMap.isEmpty());
        Preconditions.checkNotNull(factory);
        return new StandardTable<R, C, V>(backingMap, factory);
    }
    
    public static <R, C, V1, V2> Table<R, C, V2> transformValues(final Table<R, C, V1> fromTable, final Function<? super V1, V2> function) {
        return new TransformedTable<R, C, Object, V2>(fromTable, function);
    }
    
    static final class ImmutableCell<R, C, V> extends AbstractCell<R, C, V> implements Serializable
    {
        private final R rowKey;
        private final C columnKey;
        private final V value;
        private static final long serialVersionUID = 0L;
        
        ImmutableCell(@Nullable final R rowKey, @Nullable final C columnKey, @Nullable final V value) {
            this.rowKey = rowKey;
            this.columnKey = columnKey;
            this.value = value;
        }
        
        public R getRowKey() {
            return this.rowKey;
        }
        
        public C getColumnKey() {
            return this.columnKey;
        }
        
        public V getValue() {
            return this.value;
        }
    }
    
    abstract static class AbstractCell<R, C, V> implements Table.Cell<R, C, V>
    {
        public boolean equals(final Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj instanceof Table.Cell) {
                final Table.Cell<?, ?, ?> other = (Table.Cell<?, ?, ?>)obj;
                return Objects.equal(this.getRowKey(), other.getRowKey()) && Objects.equal(this.getColumnKey(), other.getColumnKey()) && Objects.equal(this.getValue(), other.getValue());
            }
            return false;
        }
        
        public int hashCode() {
            return Objects.hashCode(this.getRowKey(), this.getColumnKey(), this.getValue());
        }
        
        public String toString() {
            return "(" + this.getRowKey() + "," + this.getColumnKey() + ")=" + this.getValue();
        }
    }
    
    private static class TransposeTable<C, R, V> implements Table<C, R, V>
    {
        final Table<R, C, V> original;
        private static final Function<Cell<?, ?, ?>, Cell<?, ?, ?>> TRANSPOSE_CELL;
        CellSet cellSet;
        
        TransposeTable(final Table<R, C, V> original) {
            this.original = Preconditions.checkNotNull(original);
        }
        
        public void clear() {
            this.original.clear();
        }
        
        public Map<C, V> column(final R columnKey) {
            return this.original.row(columnKey);
        }
        
        public Set<R> columnKeySet() {
            return this.original.rowKeySet();
        }
        
        public Map<R, Map<C, V>> columnMap() {
            return this.original.rowMap();
        }
        
        public boolean contains(@Nullable final Object rowKey, @Nullable final Object columnKey) {
            return this.original.contains(columnKey, rowKey);
        }
        
        public boolean containsColumn(@Nullable final Object columnKey) {
            return this.original.containsRow(columnKey);
        }
        
        public boolean containsRow(@Nullable final Object rowKey) {
            return this.original.containsColumn(rowKey);
        }
        
        public boolean containsValue(@Nullable final Object value) {
            return this.original.containsValue(value);
        }
        
        public V get(@Nullable final Object rowKey, @Nullable final Object columnKey) {
            return this.original.get(columnKey, rowKey);
        }
        
        public boolean isEmpty() {
            return this.original.isEmpty();
        }
        
        public V put(final C rowKey, final R columnKey, final V value) {
            return this.original.put(columnKey, rowKey, value);
        }
        
        public void putAll(final Table<? extends C, ? extends R, ? extends V> table) {
            this.original.putAll(Tables.transpose(table));
        }
        
        public V remove(@Nullable final Object rowKey, @Nullable final Object columnKey) {
            return this.original.remove(columnKey, rowKey);
        }
        
        public Map<R, V> row(final C rowKey) {
            return this.original.column(rowKey);
        }
        
        public Set<C> rowKeySet() {
            return this.original.columnKeySet();
        }
        
        public Map<C, Map<R, V>> rowMap() {
            return this.original.columnMap();
        }
        
        public int size() {
            return this.original.size();
        }
        
        public Collection<V> values() {
            return this.original.values();
        }
        
        public boolean equals(@Nullable final Object obj) {
            if (obj == this) {
                return true;
            }
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
        
        public Set<Cell<C, R, V>> cellSet() {
            final CellSet result = this.cellSet;
            return (result == null) ? (this.cellSet = new CellSet()) : result;
        }
        
        static {
            TRANSPOSE_CELL = new Function<Cell<?, ?, ?>, Cell<?, ?, ?>>() {
                public Cell<?, ?, ?> apply(final Cell<?, ?, ?> cell) {
                    return Tables.immutableCell(cell.getColumnKey(), cell.getRowKey(), cell.getValue());
                }
            };
        }
        
        class CellSet extends Collections2.TransformedCollection<Cell<R, C, V>, Cell<C, R, V>> implements Set<Cell<C, R, V>>
        {
            CellSet() {
                super(TransposeTable.this.original.cellSet(), TransposeTable.TRANSPOSE_CELL);
            }
            
            public boolean equals(final Object obj) {
                if (obj == this) {
                    return true;
                }
                if (!(obj instanceof Set)) {
                    return false;
                }
                final Set<?> os = (Set<?>)obj;
                return os.size() == this.size() && this.containsAll(os);
            }
            
            public int hashCode() {
                return Sets.hashCodeImpl(this);
            }
            
            public boolean contains(final Object obj) {
                if (obj instanceof Cell) {
                    final Cell<?, ?, ?> cell = (Cell<?, ?, ?>)obj;
                    return TransposeTable.this.original.cellSet().contains(Tables.immutableCell(cell.getColumnKey(), cell.getRowKey(), cell.getValue()));
                }
                return false;
            }
            
            public boolean remove(final Object obj) {
                if (obj instanceof Cell) {
                    final Cell<?, ?, ?> cell = (Cell<?, ?, ?>)obj;
                    return TransposeTable.this.original.cellSet().remove(Tables.immutableCell(cell.getColumnKey(), cell.getRowKey(), cell.getValue()));
                }
                return false;
            }
        }
    }
    
    private static class TransformedTable<R, C, V1, V2> implements Table<R, C, V2>
    {
        final Table<R, C, V1> fromTable;
        final Function<? super V1, V2> function;
        CellSet cellSet;
        Collection<V2> values;
        Map<R, Map<C, V2>> rowMap;
        Map<C, Map<R, V2>> columnMap;
        
        TransformedTable(final Table<R, C, V1> fromTable, final Function<? super V1, V2> function) {
            this.fromTable = Preconditions.checkNotNull(fromTable);
            this.function = Preconditions.checkNotNull(function);
        }
        
        public boolean contains(final Object rowKey, final Object columnKey) {
            return this.fromTable.contains(rowKey, columnKey);
        }
        
        public boolean containsRow(final Object rowKey) {
            return this.fromTable.containsRow(rowKey);
        }
        
        public boolean containsColumn(final Object columnKey) {
            return this.fromTable.containsColumn(columnKey);
        }
        
        public boolean containsValue(final Object value) {
            return this.values().contains(value);
        }
        
        public V2 get(final Object rowKey, final Object columnKey) {
            return this.contains(rowKey, columnKey) ? this.function.apply((Object)this.fromTable.get(rowKey, columnKey)) : null;
        }
        
        public boolean isEmpty() {
            return this.fromTable.isEmpty();
        }
        
        public int size() {
            return this.fromTable.size();
        }
        
        public void clear() {
            this.fromTable.clear();
        }
        
        public V2 put(final R rowKey, final C columnKey, final V2 value) {
            throw new UnsupportedOperationException();
        }
        
        public void putAll(final Table<? extends R, ? extends C, ? extends V2> table) {
            throw new UnsupportedOperationException();
        }
        
        public V2 remove(final Object rowKey, final Object columnKey) {
            return this.contains(rowKey, columnKey) ? this.function.apply((Object)this.fromTable.remove(rowKey, columnKey)) : null;
        }
        
        public Map<C, V2> row(final R rowKey) {
            return Maps.transformValues(this.fromTable.row(rowKey), this.function);
        }
        
        public Map<R, V2> column(final C columnKey) {
            return Maps.transformValues(this.fromTable.column(columnKey), this.function);
        }
        
        Function<Cell<R, C, V1>, Cell<R, C, V2>> cellFunction() {
            return new Function<Cell<R, C, V1>, Cell<R, C, V2>>() {
                public Cell<R, C, V2> apply(final Cell<R, C, V1> cell) {
                    return Tables.immutableCell(cell.getRowKey(), cell.getColumnKey(), TransformedTable.this.function.apply((Object)cell.getValue()));
                }
            };
        }
        
        public Set<Cell<R, C, V2>> cellSet() {
            return (this.cellSet == null) ? (this.cellSet = new CellSet()) : this.cellSet;
        }
        
        public Set<R> rowKeySet() {
            return this.fromTable.rowKeySet();
        }
        
        public Set<C> columnKeySet() {
            return this.fromTable.columnKeySet();
        }
        
        public Collection<V2> values() {
            return (this.values == null) ? (this.values = Collections2.transform(this.fromTable.values(), this.function)) : this.values;
        }
        
        Map<R, Map<C, V2>> createRowMap() {
            final Function<Map<C, V1>, Map<C, V2>> rowFunction = new Function<Map<C, V1>, Map<C, V2>>() {
                public Map<C, V2> apply(final Map<C, V1> row) {
                    return Maps.transformValues(row, TransformedTable.this.function);
                }
            };
            return Maps.transformValues(this.fromTable.rowMap(), rowFunction);
        }
        
        public Map<R, Map<C, V2>> rowMap() {
            return (this.rowMap == null) ? (this.rowMap = this.createRowMap()) : this.rowMap;
        }
        
        Map<C, Map<R, V2>> createColumnMap() {
            final Function<Map<R, V1>, Map<R, V2>> columnFunction = new Function<Map<R, V1>, Map<R, V2>>() {
                public Map<R, V2> apply(final Map<R, V1> column) {
                    return Maps.transformValues(column, TransformedTable.this.function);
                }
            };
            return Maps.transformValues(this.fromTable.columnMap(), columnFunction);
        }
        
        public Map<C, Map<R, V2>> columnMap() {
            return (this.columnMap == null) ? (this.columnMap = this.createColumnMap()) : this.columnMap;
        }
        
        public boolean equals(@Nullable final Object obj) {
            if (obj == this) {
                return true;
            }
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
        
        class CellSet extends Collections2.TransformedCollection<Cell<R, C, V1>, Cell<R, C, V2>> implements Set<Cell<R, C, V2>>
        {
            CellSet() {
                super(TransformedTable.this.fromTable.cellSet(), TransformedTable.this.cellFunction());
            }
            
            public boolean equals(final Object obj) {
                return Sets.equalsImpl(this, obj);
            }
            
            public int hashCode() {
                return Sets.hashCodeImpl(this);
            }
            
            public boolean contains(final Object obj) {
                if (obj instanceof Cell) {
                    final Cell<?, ?, ?> cell = (Cell<?, ?, ?>)obj;
                    return Objects.equal(cell.getValue(), TransformedTable.this.get(cell.getRowKey(), cell.getColumnKey())) && (cell.getValue() != null || TransformedTable.this.fromTable.contains(cell.getRowKey(), cell.getColumnKey()));
                }
                return false;
            }
            
            public boolean remove(final Object obj) {
                if (this.contains(obj)) {
                    final Cell<?, ?, ?> cell = (Cell<?, ?, ?>)obj;
                    TransformedTable.this.fromTable.remove(cell.getRowKey(), cell.getColumnKey());
                    return true;
                }
                return false;
            }
        }
    }
}
