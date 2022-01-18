// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.io.Serializable;
import java.util.Set;
import java.util.Collection;
import javax.annotation.Nullable;
import com.google.common.base.Supplier;
import com.google.common.base.Preconditions;
import java.util.Map;
import java.util.HashMap;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true)
@Beta
public class HashBasedTable<R, C, V> extends StandardTable<R, C, V>
{
    private static final long serialVersionUID = 0L;
    
    public static <R, C, V> HashBasedTable<R, C, V> create() {
        return new HashBasedTable<R, C, V>(new HashMap<R, Map<C, V>>(), new Factory<C, V>(0));
    }
    
    public static <R, C, V> HashBasedTable<R, C, V> create(final int expectedRows, final int expectedCellsPerRow) {
        Preconditions.checkArgument(expectedCellsPerRow >= 0);
        final Map<R, Map<C, V>> backingMap = (Map<R, Map<C, V>>)Maps.newHashMapWithExpectedSize(expectedRows);
        return new HashBasedTable<R, C, V>(backingMap, new Factory<C, V>(expectedCellsPerRow));
    }
    
    public static <R, C, V> HashBasedTable<R, C, V> create(final Table<? extends R, ? extends C, ? extends V> table) {
        final HashBasedTable<R, C, V> result = create();
        result.putAll(table);
        return result;
    }
    
    HashBasedTable(final Map<R, Map<C, V>> backingMap, final Factory<C, V> factory) {
        super(backingMap, factory);
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
    
    private static class Factory<C, V> implements Supplier<Map<C, V>>, Serializable
    {
        final int expectedSize;
        private static final long serialVersionUID = 0L;
        
        Factory(final int expectedSize) {
            this.expectedSize = expectedSize;
        }
        
        public Map<C, V> get() {
            return (Map<C, V>)Maps.newHashMapWithExpectedSize(this.expectedSize);
        }
    }
}
