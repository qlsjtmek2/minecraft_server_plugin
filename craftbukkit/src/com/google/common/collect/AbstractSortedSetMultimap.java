// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Set;
import javax.annotation.Nullable;
import java.util.SortedSet;
import java.util.Collection;
import java.util.Map;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
abstract class AbstractSortedSetMultimap<K, V> extends AbstractSetMultimap<K, V> implements SortedSetMultimap<K, V>
{
    private static final long serialVersionUID = 430848587173315748L;
    
    protected AbstractSortedSetMultimap(final Map<K, Collection<V>> map) {
        super(map);
    }
    
    abstract SortedSet<V> createCollection();
    
    public SortedSet<V> get(@Nullable final K key) {
        return (SortedSet<V>)(SortedSet)super.get(key);
    }
    
    public SortedSet<V> removeAll(@Nullable final Object key) {
        return (SortedSet<V>)(SortedSet)super.removeAll(key);
    }
    
    public SortedSet<V> replaceValues(final K key, final Iterable<? extends V> values) {
        return (SortedSet<V>)(SortedSet)super.replaceValues(key, values);
    }
    
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }
    
    public Collection<V> values() {
        return super.values();
    }
}
