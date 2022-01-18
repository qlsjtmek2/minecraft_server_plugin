// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.Beta;

@Beta
@GwtCompatible
public abstract class ForwardingTable<R, C, V> extends ForwardingObject implements Table<R, C, V>
{
    protected abstract Table<R, C, V> delegate();
    
    public Set<Cell<R, C, V>> cellSet() {
        return this.delegate().cellSet();
    }
    
    public void clear() {
        this.delegate().clear();
    }
    
    public Map<R, V> column(final C columnKey) {
        return this.delegate().column(columnKey);
    }
    
    public Set<C> columnKeySet() {
        return this.delegate().columnKeySet();
    }
    
    public Map<C, Map<R, V>> columnMap() {
        return this.delegate().columnMap();
    }
    
    public boolean contains(final Object rowKey, final Object columnKey) {
        return this.delegate().contains(rowKey, columnKey);
    }
    
    public boolean containsColumn(final Object columnKey) {
        return this.delegate().containsColumn(columnKey);
    }
    
    public boolean containsRow(final Object rowKey) {
        return this.delegate().containsRow(rowKey);
    }
    
    public boolean containsValue(final Object value) {
        return this.delegate().containsValue(value);
    }
    
    public V get(final Object rowKey, final Object columnKey) {
        return this.delegate().get(rowKey, columnKey);
    }
    
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }
    
    public V put(final R rowKey, final C columnKey, final V value) {
        return this.delegate().put(rowKey, columnKey, value);
    }
    
    public void putAll(final Table<? extends R, ? extends C, ? extends V> table) {
        this.delegate().putAll(table);
    }
    
    public V remove(final Object rowKey, final Object columnKey) {
        return this.delegate().remove(rowKey, columnKey);
    }
    
    public Map<C, V> row(final R rowKey) {
        return this.delegate().row(rowKey);
    }
    
    public Set<R> rowKeySet() {
        return this.delegate().rowKeySet();
    }
    
    public Map<R, Map<C, V>> rowMap() {
        return this.delegate().rowMap();
    }
    
    public int size() {
        return this.delegate().size();
    }
    
    public Collection<V> values() {
        return this.delegate().values();
    }
    
    public boolean equals(final Object obj) {
        return obj == this || this.delegate().equals(obj);
    }
    
    public int hashCode() {
        return this.delegate().hashCode();
    }
}
