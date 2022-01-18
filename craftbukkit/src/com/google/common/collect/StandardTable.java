// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Predicates;
import com.google.common.base.Predicate;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.LinkedHashMap;
import com.google.common.base.Preconditions;
import java.util.Iterator;
import javax.annotation.Nullable;
import java.util.Set;
import com.google.common.base.Supplier;
import java.util.Map;
import com.google.common.annotations.GwtCompatible;
import java.io.Serializable;

@GwtCompatible
class StandardTable<R, C, V> implements Table<R, C, V>, Serializable
{
    final Map<R, Map<C, V>> backingMap;
    final Supplier<? extends Map<C, V>> factory;
    private transient CellSet cellSet;
    private transient RowKeySet rowKeySet;
    private transient Set<C> columnKeySet;
    private transient Values values;
    private transient RowMap rowMap;
    private transient ColumnMap columnMap;
    private static final long serialVersionUID = 0L;
    
    StandardTable(final Map<R, Map<C, V>> backingMap, final Supplier<? extends Map<C, V>> factory) {
        this.backingMap = backingMap;
        this.factory = factory;
    }
    
    public boolean contains(@Nullable final Object rowKey, @Nullable final Object columnKey) {
        if (rowKey == null || columnKey == null) {
            return false;
        }
        final Map<C, V> map = Maps.safeGet(this.backingMap, rowKey);
        return map != null && Maps.safeContainsKey(map, columnKey);
    }
    
    public boolean containsColumn(@Nullable final Object columnKey) {
        if (columnKey == null) {
            return false;
        }
        for (final Map<C, V> map : this.backingMap.values()) {
            if (Maps.safeContainsKey(map, columnKey)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean containsRow(@Nullable final Object rowKey) {
        return rowKey != null && Maps.safeContainsKey(this.backingMap, rowKey);
    }
    
    public boolean containsValue(@Nullable final Object value) {
        if (value == null) {
            return false;
        }
        for (final Map<C, V> map : this.backingMap.values()) {
            if (map.containsValue(value)) {
                return true;
            }
        }
        return false;
    }
    
    public V get(@Nullable final Object rowKey, @Nullable final Object columnKey) {
        if (rowKey == null || columnKey == null) {
            return null;
        }
        final Map<C, V> map = Maps.safeGet(this.backingMap, rowKey);
        return (map == null) ? null : Maps.safeGet(map, columnKey);
    }
    
    public boolean isEmpty() {
        return this.backingMap.isEmpty();
    }
    
    public int size() {
        int size = 0;
        for (final Map<C, V> map : this.backingMap.values()) {
            size += map.size();
        }
        return size;
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
    
    public void clear() {
        this.backingMap.clear();
    }
    
    private Map<C, V> getOrCreate(final R rowKey) {
        Map<C, V> map = this.backingMap.get(rowKey);
        if (map == null) {
            map = (Map<C, V>)this.factory.get();
            this.backingMap.put(rowKey, map);
        }
        return map;
    }
    
    public V put(final R rowKey, final C columnKey, final V value) {
        Preconditions.checkNotNull(rowKey);
        Preconditions.checkNotNull(columnKey);
        Preconditions.checkNotNull(value);
        return this.getOrCreate(rowKey).put(columnKey, value);
    }
    
    public void putAll(final Table<? extends R, ? extends C, ? extends V> table) {
        for (final Cell<? extends R, ? extends C, ? extends V> cell : table.cellSet()) {
            this.put(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
        }
    }
    
    public V remove(@Nullable final Object rowKey, @Nullable final Object columnKey) {
        if (rowKey == null || columnKey == null) {
            return null;
        }
        final Map<C, V> map = Maps.safeGet(this.backingMap, rowKey);
        if (map == null) {
            return null;
        }
        final V value = map.remove(columnKey);
        if (map.isEmpty()) {
            this.backingMap.remove(rowKey);
        }
        return value;
    }
    
    private Map<R, V> removeColumn(final Object column) {
        final Map<R, V> output = new LinkedHashMap<R, V>();
        final Iterator<Map.Entry<R, Map<C, V>>> iterator = this.backingMap.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<R, Map<C, V>> entry = iterator.next();
            final V value = entry.getValue().remove(column);
            if (value != null) {
                output.put(entry.getKey(), value);
                if (!entry.getValue().isEmpty()) {
                    continue;
                }
                iterator.remove();
            }
        }
        return output;
    }
    
    private boolean containsMapping(final Object rowKey, final Object columnKey, final Object value) {
        return value != null && value.equals(this.get(rowKey, columnKey));
    }
    
    private boolean removeMapping(final Object rowKey, final Object columnKey, final Object value) {
        if (this.containsMapping(rowKey, columnKey, value)) {
            this.remove(rowKey, columnKey);
            return true;
        }
        return false;
    }
    
    public Set<Cell<R, C, V>> cellSet() {
        final CellSet result = this.cellSet;
        return (result == null) ? (this.cellSet = new CellSet()) : result;
    }
    
    public Map<C, V> row(final R rowKey) {
        return new Row(rowKey);
    }
    
    public Map<R, V> column(final C columnKey) {
        return new Column(columnKey);
    }
    
    public Set<R> rowKeySet() {
        final Set<R> result = this.rowKeySet;
        return (result == null) ? (this.rowKeySet = new RowKeySet()) : result;
    }
    
    public Set<C> columnKeySet() {
        final Set<C> result = this.columnKeySet;
        return (result == null) ? (this.columnKeySet = new ColumnKeySet()) : result;
    }
    
    Iterator<C> createColumnKeyIterator() {
        return new ColumnKeyIterator();
    }
    
    public Collection<V> values() {
        final Values result = this.values;
        return (result == null) ? (this.values = new Values()) : result;
    }
    
    public Map<R, Map<C, V>> rowMap() {
        final RowMap result = this.rowMap;
        return (result == null) ? (this.rowMap = new RowMap()) : result;
    }
    
    public Map<C, Map<R, V>> columnMap() {
        final ColumnMap result = this.columnMap;
        return (result == null) ? (this.columnMap = new ColumnMap()) : result;
    }
    
    static <K, V> Iterator<K> keyIteratorImpl(final Map<K, V> map) {
        final Iterator<Map.Entry<K, V>> entryIterator = map.entrySet().iterator();
        return new Iterator<K>() {
            public boolean hasNext() {
                return entryIterator.hasNext();
            }
            
            public K next() {
                return entryIterator.next().getKey();
            }
            
            public void remove() {
                entryIterator.remove();
            }
        };
    }
    
    static <K, V> Iterator<V> valueIteratorImpl(final Map<K, V> map) {
        final Iterator<Map.Entry<K, V>> entryIterator = map.entrySet().iterator();
        return new Iterator<V>() {
            public boolean hasNext() {
                return entryIterator.hasNext();
            }
            
            public V next() {
                return entryIterator.next().getValue();
            }
            
            public void remove() {
                entryIterator.remove();
            }
        };
    }
    
    private abstract class TableCollection<T> extends AbstractCollection<T>
    {
        public boolean isEmpty() {
            return StandardTable.this.backingMap.isEmpty();
        }
        
        public void clear() {
            StandardTable.this.backingMap.clear();
        }
    }
    
    private abstract class TableSet<T> extends AbstractSet<T>
    {
        public boolean isEmpty() {
            return StandardTable.this.backingMap.isEmpty();
        }
        
        public void clear() {
            StandardTable.this.backingMap.clear();
        }
    }
    
    private class CellSet extends TableSet<Cell<R, C, V>>
    {
        public Iterator<Cell<R, C, V>> iterator() {
            return new CellIterator();
        }
        
        public int size() {
            return StandardTable.this.size();
        }
        
        public boolean contains(final Object obj) {
            if (obj instanceof Cell) {
                final Cell<?, ?, ?> cell = (Cell<?, ?, ?>)obj;
                return StandardTable.this.containsMapping(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
            }
            return false;
        }
        
        public boolean remove(final Object obj) {
            if (obj instanceof Cell) {
                final Cell<?, ?, ?> cell = (Cell<?, ?, ?>)obj;
                return StandardTable.this.removeMapping(cell.getRowKey(), cell.getColumnKey(), cell.getValue());
            }
            return false;
        }
    }
    
    private class CellIterator implements Iterator<Cell<R, C, V>>
    {
        final Iterator<Map.Entry<R, Map<C, V>>> rowIterator;
        Map.Entry<R, Map<C, V>> rowEntry;
        Iterator<Map.Entry<C, V>> columnIterator;
        
        private CellIterator() {
            this.rowIterator = StandardTable.this.backingMap.entrySet().iterator();
            this.columnIterator = Iterators.emptyModifiableIterator();
        }
        
        public boolean hasNext() {
            return this.rowIterator.hasNext() || this.columnIterator.hasNext();
        }
        
        public Cell<R, C, V> next() {
            if (!this.columnIterator.hasNext()) {
                this.rowEntry = this.rowIterator.next();
                this.columnIterator = this.rowEntry.getValue().entrySet().iterator();
            }
            final Map.Entry<C, V> columnEntry = this.columnIterator.next();
            return Tables.immutableCell(this.rowEntry.getKey(), columnEntry.getKey(), columnEntry.getValue());
        }
        
        public void remove() {
            this.columnIterator.remove();
            if (this.rowEntry.getValue().isEmpty()) {
                this.rowIterator.remove();
            }
        }
    }
    
    class Row extends AbstractMap<C, V>
    {
        final R rowKey;
        Map<C, V> backingRowMap;
        Set<C> keySet;
        Set<Map.Entry<C, V>> entrySet;
        
        Row(final R rowKey) {
            this.rowKey = Preconditions.checkNotNull(rowKey);
        }
        
        Map<C, V> backingRowMap() {
            return (this.backingRowMap == null || (this.backingRowMap.isEmpty() && StandardTable.this.backingMap.containsKey(this.rowKey))) ? (this.backingRowMap = this.computeBackingRowMap()) : this.backingRowMap;
        }
        
        Map<C, V> computeBackingRowMap() {
            return StandardTable.this.backingMap.get(this.rowKey);
        }
        
        void maintainEmptyInvariant() {
            if (this.backingRowMap() != null && this.backingRowMap.isEmpty()) {
                StandardTable.this.backingMap.remove(this.rowKey);
                this.backingRowMap = null;
            }
        }
        
        public boolean containsKey(final Object key) {
            final Map<C, V> backingRowMap = this.backingRowMap();
            return key != null && backingRowMap != null && Maps.safeContainsKey(backingRowMap, key);
        }
        
        public V get(final Object key) {
            final Map<C, V> backingRowMap = this.backingRowMap();
            return (key != null && backingRowMap != null) ? Maps.safeGet(backingRowMap, key) : null;
        }
        
        public V put(final C key, final V value) {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(value);
            if (this.backingRowMap != null && !this.backingRowMap.isEmpty()) {
                return this.backingRowMap.put(key, value);
            }
            return StandardTable.this.put(this.rowKey, key, value);
        }
        
        public V remove(final Object key) {
            try {
                final Map<C, V> backingRowMap = this.backingRowMap();
                if (backingRowMap == null) {
                    return null;
                }
                final V result = backingRowMap.remove(key);
                this.maintainEmptyInvariant();
                return result;
            }
            catch (ClassCastException e) {
                return null;
            }
        }
        
        public void clear() {
            final Map<C, V> backingRowMap = this.backingRowMap();
            if (backingRowMap != null) {
                backingRowMap.clear();
            }
            this.maintainEmptyInvariant();
        }
        
        public Set<C> keySet() {
            final Set<C> result = this.keySet;
            if (result == null) {
                return this.keySet = (Set<C>)new Maps.KeySet<C, V>() {
                    Map<C, V> map() {
                        return Row.this;
                    }
                };
            }
            return result;
        }
        
        public Set<Map.Entry<C, V>> entrySet() {
            final Set<Map.Entry<C, V>> result = this.entrySet;
            if (result == null) {
                return this.entrySet = (Set<Map.Entry<C, V>>)new RowEntrySet();
            }
            return result;
        }
        
        private class RowEntrySet extends Maps.EntrySet<C, V>
        {
            Map<C, V> map() {
                return Row.this;
            }
            
            public int size() {
                final Map<C, V> map = Row.this.backingRowMap();
                return (map == null) ? 0 : map.size();
            }
            
            public Iterator<Map.Entry<C, V>> iterator() {
                final Map<C, V> map = Row.this.backingRowMap();
                if (map == null) {
                    return Iterators.emptyModifiableIterator();
                }
                final Iterator<Map.Entry<C, V>> iterator = map.entrySet().iterator();
                return new Iterator<Map.Entry<C, V>>() {
                    public boolean hasNext() {
                        return iterator.hasNext();
                    }
                    
                    public Map.Entry<C, V> next() {
                        final Map.Entry<C, V> entry = iterator.next();
                        return new ForwardingMapEntry<C, V>() {
                            protected Map.Entry<C, V> delegate() {
                                return entry;
                            }
                            
                            public V setValue(final V value) {
                                return super.setValue(Preconditions.checkNotNull(value));
                            }
                            
                            public boolean equals(final Object object) {
                                return this.standardEquals(object);
                            }
                        };
                    }
                    
                    public void remove() {
                        iterator.remove();
                        Row.this.maintainEmptyInvariant();
                    }
                };
            }
        }
    }
    
    private class Column extends Maps.ImprovedAbstractMap<R, V>
    {
        final C columnKey;
        Values columnValues;
        KeySet keySet;
        
        Column(final C columnKey) {
            this.columnKey = Preconditions.checkNotNull(columnKey);
        }
        
        public V put(final R key, final V value) {
            return StandardTable.this.put(key, this.columnKey, value);
        }
        
        public V get(final Object key) {
            return StandardTable.this.get(key, this.columnKey);
        }
        
        public boolean containsKey(final Object key) {
            return StandardTable.this.contains(key, this.columnKey);
        }
        
        public V remove(final Object key) {
            return StandardTable.this.remove(key, this.columnKey);
        }
        
        public Set<Map.Entry<R, V>> createEntrySet() {
            return new EntrySet();
        }
        
        public Collection<V> values() {
            final Values result = this.columnValues;
            return (result == null) ? (this.columnValues = new Values()) : result;
        }
        
        boolean removePredicate(final Predicate<? super Map.Entry<R, V>> predicate) {
            boolean changed = false;
            final Iterator<Map.Entry<R, Map<C, V>>> iterator = StandardTable.this.backingMap.entrySet().iterator();
            while (iterator.hasNext()) {
                final Map.Entry<R, Map<C, V>> entry = iterator.next();
                final Map<C, V> map = entry.getValue();
                final V value = map.get(this.columnKey);
                if (value != null && predicate.apply((Object)new ImmutableEntry((K)entry.getKey(), (V)value))) {
                    map.remove(this.columnKey);
                    changed = true;
                    if (!map.isEmpty()) {
                        continue;
                    }
                    iterator.remove();
                }
            }
            return changed;
        }
        
        public Set<R> keySet() {
            final KeySet result = this.keySet;
            return (result == null) ? (this.keySet = new KeySet()) : result;
        }
        
        class EntrySet extends AbstractSet<Map.Entry<R, V>>
        {
            public Iterator<Map.Entry<R, V>> iterator() {
                return new EntrySetIterator();
            }
            
            public int size() {
                int size = 0;
                for (final Map<C, V> map : StandardTable.this.backingMap.values()) {
                    if (map.containsKey(Column.this.columnKey)) {
                        ++size;
                    }
                }
                return size;
            }
            
            public boolean isEmpty() {
                return !StandardTable.this.containsColumn(Column.this.columnKey);
            }
            
            public void clear() {
                final Predicate<Map.Entry<R, V>> predicate = Predicates.alwaysTrue();
                Column.this.removePredicate(predicate);
            }
            
            public boolean contains(final Object o) {
                if (o instanceof Map.Entry) {
                    final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)o;
                    return StandardTable.this.containsMapping(entry.getKey(), Column.this.columnKey, entry.getValue());
                }
                return false;
            }
            
            public boolean remove(final Object obj) {
                if (obj instanceof Map.Entry) {
                    final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)obj;
                    return StandardTable.this.removeMapping(entry.getKey(), Column.this.columnKey, entry.getValue());
                }
                return false;
            }
            
            public boolean removeAll(final Collection<?> c) {
                boolean changed = false;
                for (final Object obj : c) {
                    changed |= this.remove(obj);
                }
                return changed;
            }
            
            public boolean retainAll(final Collection<?> c) {
                return Column.this.removePredicate(Predicates.not(Predicates.in((Collection<? extends Map.Entry<R, V>>)c)));
            }
        }
        
        class EntrySetIterator extends AbstractIterator<Map.Entry<R, V>>
        {
            final Iterator<Map.Entry<R, Map<C, V>>> iterator;
            
            EntrySetIterator() {
                this.iterator = StandardTable.this.backingMap.entrySet().iterator();
            }
            
            protected Map.Entry<R, V> computeNext() {
                while (this.iterator.hasNext()) {
                    final Map.Entry<R, Map<C, V>> entry = this.iterator.next();
                    if (entry.getValue().containsKey(Column.this.columnKey)) {
                        return new AbstractMapEntry<R, V>() {
                            public R getKey() {
                                return entry.getKey();
                            }
                            
                            public V getValue() {
                                return entry.getValue().get(Column.this.columnKey);
                            }
                            
                            public V setValue(final V value) {
                                return entry.getValue().put(Column.this.columnKey, Preconditions.checkNotNull(value));
                            }
                        };
                    }
                }
                return this.endOfData();
            }
        }
        
        class KeySet extends AbstractSet<R>
        {
            public Iterator<R> iterator() {
                return StandardTable.keyIteratorImpl((Map<R, Object>)Column.this);
            }
            
            public int size() {
                return Column.this.entrySet().size();
            }
            
            public boolean isEmpty() {
                return !StandardTable.this.containsColumn(Column.this.columnKey);
            }
            
            public boolean contains(final Object obj) {
                return StandardTable.this.contains(obj, Column.this.columnKey);
            }
            
            public boolean remove(final Object obj) {
                return StandardTable.this.remove(obj, Column.this.columnKey) != null;
            }
            
            public void clear() {
                Column.this.entrySet().clear();
            }
            
            public boolean removeAll(final Collection<?> c) {
                boolean changed = false;
                for (final Object obj : c) {
                    changed |= this.remove(obj);
                }
                return changed;
            }
            
            public boolean retainAll(final Collection<?> c) {
                Preconditions.checkNotNull(c);
                final Predicate<Map.Entry<R, V>> predicate = new Predicate<Map.Entry<R, V>>() {
                    public boolean apply(final Map.Entry<R, V> entry) {
                        return !c.contains(entry.getKey());
                    }
                };
                return Column.this.removePredicate(predicate);
            }
        }
        
        class Values extends AbstractCollection<V>
        {
            public Iterator<V> iterator() {
                return StandardTable.valueIteratorImpl((Map<Object, V>)Column.this);
            }
            
            public int size() {
                return Column.this.entrySet().size();
            }
            
            public boolean isEmpty() {
                return !StandardTable.this.containsColumn(Column.this.columnKey);
            }
            
            public void clear() {
                Column.this.entrySet().clear();
            }
            
            public boolean remove(final Object obj) {
                if (obj == null) {
                    return false;
                }
                final Iterator<Map<C, V>> iterator = StandardTable.this.backingMap.values().iterator();
                while (iterator.hasNext()) {
                    final Map<C, V> map = iterator.next();
                    if (map.entrySet().remove(new ImmutableEntry(Column.this.columnKey, obj))) {
                        if (map.isEmpty()) {
                            iterator.remove();
                        }
                        return true;
                    }
                }
                return false;
            }
            
            public boolean removeAll(final Collection<?> c) {
                Preconditions.checkNotNull(c);
                final Predicate<Map.Entry<R, V>> predicate = new Predicate<Map.Entry<R, V>>() {
                    public boolean apply(final Map.Entry<R, V> entry) {
                        return c.contains(entry.getValue());
                    }
                };
                return Column.this.removePredicate(predicate);
            }
            
            public boolean retainAll(final Collection<?> c) {
                Preconditions.checkNotNull(c);
                final Predicate<Map.Entry<R, V>> predicate = new Predicate<Map.Entry<R, V>>() {
                    public boolean apply(final Map.Entry<R, V> entry) {
                        return !c.contains(entry.getValue());
                    }
                };
                return Column.this.removePredicate(predicate);
            }
        }
    }
    
    class RowKeySet extends TableSet<R>
    {
        public Iterator<R> iterator() {
            return StandardTable.keyIteratorImpl(StandardTable.this.rowMap());
        }
        
        public int size() {
            return StandardTable.this.backingMap.size();
        }
        
        public boolean contains(final Object obj) {
            return StandardTable.this.containsRow(obj);
        }
        
        public boolean remove(final Object obj) {
            return obj != null && StandardTable.this.backingMap.remove(obj) != null;
        }
    }
    
    private class ColumnKeySet extends TableSet<C>
    {
        public Iterator<C> iterator() {
            return StandardTable.this.createColumnKeyIterator();
        }
        
        public int size() {
            return Iterators.size(this.iterator());
        }
        
        public boolean remove(final Object obj) {
            if (obj == null) {
                return false;
            }
            boolean changed = false;
            final Iterator<Map<C, V>> iterator = StandardTable.this.backingMap.values().iterator();
            while (iterator.hasNext()) {
                final Map<C, V> map = iterator.next();
                if (map.keySet().remove(obj)) {
                    changed = true;
                    if (!map.isEmpty()) {
                        continue;
                    }
                    iterator.remove();
                }
            }
            return changed;
        }
        
        public boolean removeAll(final Collection<?> c) {
            Preconditions.checkNotNull(c);
            boolean changed = false;
            final Iterator<Map<C, V>> iterator = StandardTable.this.backingMap.values().iterator();
            while (iterator.hasNext()) {
                final Map<C, V> map = iterator.next();
                if (Iterators.removeAll(map.keySet().iterator(), c)) {
                    changed = true;
                    if (!map.isEmpty()) {
                        continue;
                    }
                    iterator.remove();
                }
            }
            return changed;
        }
        
        public boolean retainAll(final Collection<?> c) {
            Preconditions.checkNotNull(c);
            boolean changed = false;
            final Iterator<Map<C, V>> iterator = StandardTable.this.backingMap.values().iterator();
            while (iterator.hasNext()) {
                final Map<C, V> map = iterator.next();
                if (map.keySet().retainAll(c)) {
                    changed = true;
                    if (!map.isEmpty()) {
                        continue;
                    }
                    iterator.remove();
                }
            }
            return changed;
        }
        
        public boolean contains(final Object obj) {
            if (obj == null) {
                return false;
            }
            for (final Map<C, V> map : StandardTable.this.backingMap.values()) {
                if (map.containsKey(obj)) {
                    return true;
                }
            }
            return false;
        }
    }
    
    private class ColumnKeyIterator extends AbstractIterator<C>
    {
        final Map<C, V> seen;
        final Iterator<Map<C, V>> mapIterator;
        Iterator<Map.Entry<C, V>> entryIterator;
        
        private ColumnKeyIterator() {
            this.seen = (Map<C, V>)StandardTable.this.factory.get();
            this.mapIterator = StandardTable.this.backingMap.values().iterator();
            this.entryIterator = (Iterator<Map.Entry<C, V>>)Iterators.emptyIterator();
        }
        
        protected C computeNext() {
            while (true) {
                if (this.entryIterator.hasNext()) {
                    final Map.Entry<C, V> entry = this.entryIterator.next();
                    if (!this.seen.containsKey(entry.getKey())) {
                        this.seen.put(entry.getKey(), entry.getValue());
                        return entry.getKey();
                    }
                    continue;
                }
                else {
                    if (!this.mapIterator.hasNext()) {
                        return this.endOfData();
                    }
                    this.entryIterator = this.mapIterator.next().entrySet().iterator();
                }
            }
        }
    }
    
    private class Values extends TableCollection<V>
    {
        public Iterator<V> iterator() {
            final Iterator<Cell<R, C, V>> cellIterator = StandardTable.this.cellSet().iterator();
            return new Iterator<V>() {
                public boolean hasNext() {
                    return cellIterator.hasNext();
                }
                
                public V next() {
                    return cellIterator.next().getValue();
                }
                
                public void remove() {
                    cellIterator.remove();
                }
            };
        }
        
        public int size() {
            return StandardTable.this.size();
        }
    }
    
    class RowMap extends Maps.ImprovedAbstractMap<R, Map<C, V>>
    {
        public boolean containsKey(final Object key) {
            return StandardTable.this.containsRow(key);
        }
        
        public Map<C, V> get(final Object key) {
            return StandardTable.this.containsRow(key) ? StandardTable.this.row(key) : null;
        }
        
        public Set<R> keySet() {
            return StandardTable.this.rowKeySet();
        }
        
        public Map<C, V> remove(final Object key) {
            return (key == null) ? null : StandardTable.this.backingMap.remove(key);
        }
        
        protected Set<Map.Entry<R, Map<C, V>>> createEntrySet() {
            return new EntrySet();
        }
        
        class EntrySet extends TableSet<Map.Entry<R, Map<C, V>>>
        {
            public Iterator<Map.Entry<R, Map<C, V>>> iterator() {
                return new EntryIterator();
            }
            
            public int size() {
                return StandardTable.this.backingMap.size();
            }
            
            public boolean contains(final Object obj) {
                if (obj instanceof Map.Entry) {
                    final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)obj;
                    return entry.getKey() != null && entry.getValue() instanceof Map && Collections2.safeContains(StandardTable.this.backingMap.entrySet(), entry);
                }
                return false;
            }
            
            public boolean remove(final Object obj) {
                if (obj instanceof Map.Entry) {
                    final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)obj;
                    return entry.getKey() != null && entry.getValue() instanceof Map && StandardTable.this.backingMap.entrySet().remove(entry);
                }
                return false;
            }
        }
        
        class EntryIterator implements Iterator<Map.Entry<R, Map<C, V>>>
        {
            final Iterator<R> delegate;
            
            EntryIterator() {
                this.delegate = StandardTable.this.backingMap.keySet().iterator();
            }
            
            public boolean hasNext() {
                return this.delegate.hasNext();
            }
            
            public Map.Entry<R, Map<C, V>> next() {
                final R rowKey = this.delegate.next();
                return new ImmutableEntry<R, Map<C, V>>(rowKey, StandardTable.this.row(rowKey));
            }
            
            public void remove() {
                this.delegate.remove();
            }
        }
    }
    
    private class ColumnMap extends Maps.ImprovedAbstractMap<C, Map<R, V>>
    {
        ColumnMapValues columnMapValues;
        
        public Map<R, V> get(final Object key) {
            return StandardTable.this.containsColumn(key) ? StandardTable.this.column(key) : null;
        }
        
        public boolean containsKey(final Object key) {
            return StandardTable.this.containsColumn(key);
        }
        
        public Map<R, V> remove(final Object key) {
            return (Map<R, V>)(StandardTable.this.containsColumn(key) ? StandardTable.this.removeColumn(key) : null);
        }
        
        public Set<Map.Entry<C, Map<R, V>>> createEntrySet() {
            return new ColumnMapEntrySet();
        }
        
        public Set<C> keySet() {
            return StandardTable.this.columnKeySet();
        }
        
        public Collection<Map<R, V>> values() {
            final ColumnMapValues result = this.columnMapValues;
            return (result == null) ? (this.columnMapValues = new ColumnMapValues()) : result;
        }
        
        class ColumnMapEntrySet extends TableSet<Map.Entry<C, Map<R, V>>>
        {
            public Iterator<Map.Entry<C, Map<R, V>>> iterator() {
                final Iterator<C> columnIterator = StandardTable.this.columnKeySet().iterator();
                return new UnmodifiableIterator<Map.Entry<C, Map<R, V>>>() {
                    public boolean hasNext() {
                        return columnIterator.hasNext();
                    }
                    
                    public Map.Entry<C, Map<R, V>> next() {
                        final C columnKey = columnIterator.next();
                        return new ImmutableEntry<C, Map<R, V>>(columnKey, StandardTable.this.column(columnKey));
                    }
                };
            }
            
            public int size() {
                return StandardTable.this.columnKeySet().size();
            }
            
            public boolean contains(final Object obj) {
                if (obj instanceof Map.Entry) {
                    final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)obj;
                    if (StandardTable.this.containsColumn(entry.getKey())) {
                        final C columnKey = (C)entry.getKey();
                        return ColumnMap.this.get(columnKey).equals(entry.getValue());
                    }
                }
                return false;
            }
            
            public boolean remove(final Object obj) {
                if (this.contains(obj)) {
                    final Map.Entry<?, ?> entry = (Map.Entry<?, ?>)obj;
                    StandardTable.this.removeColumn(entry.getKey());
                    return true;
                }
                return false;
            }
            
            public boolean removeAll(final Collection<?> c) {
                boolean changed = false;
                for (final Object obj : c) {
                    changed |= this.remove(obj);
                }
                return changed;
            }
            
            public boolean retainAll(final Collection<?> c) {
                boolean changed = false;
                for (final C columnKey : Lists.newArrayList((Iterator<?>)StandardTable.this.columnKeySet().iterator())) {
                    if (!c.contains(new ImmutableEntry(columnKey, StandardTable.this.column(columnKey)))) {
                        StandardTable.this.removeColumn(columnKey);
                        changed = true;
                    }
                }
                return changed;
            }
        }
        
        private class ColumnMapValues extends TableCollection<Map<R, V>>
        {
            public Iterator<Map<R, V>> iterator() {
                return StandardTable.valueIteratorImpl((Map<Object, Map<R, V>>)ColumnMap.this);
            }
            
            public boolean remove(final Object obj) {
                for (final Map.Entry<C, Map<R, V>> entry : ColumnMap.this.entrySet()) {
                    if (entry.getValue().equals(obj)) {
                        StandardTable.this.removeColumn(entry.getKey());
                        return true;
                    }
                }
                return false;
            }
            
            public boolean removeAll(final Collection<?> c) {
                Preconditions.checkNotNull(c);
                boolean changed = false;
                for (final C columnKey : Lists.newArrayList((Iterator<?>)StandardTable.this.columnKeySet().iterator())) {
                    if (c.contains(StandardTable.this.column(columnKey))) {
                        StandardTable.this.removeColumn(columnKey);
                        changed = true;
                    }
                }
                return changed;
            }
            
            public boolean retainAll(final Collection<?> c) {
                Preconditions.checkNotNull(c);
                boolean changed = false;
                for (final C columnKey : Lists.newArrayList((Iterator<?>)StandardTable.this.columnKeySet().iterator())) {
                    if (!c.contains(StandardTable.this.column(columnKey))) {
                        StandardTable.this.removeColumn(columnKey);
                        changed = true;
                    }
                }
                return changed;
            }
            
            public int size() {
                return StandardTable.this.columnKeySet().size();
            }
        }
    }
}
