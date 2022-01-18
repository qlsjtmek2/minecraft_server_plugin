// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.Collection;
import java.util.Map;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
abstract class AbstractSetMultimap<K, V> extends AbstractMultimap<K, V> implements SetMultimap<K, V>
{
    private static final long serialVersionUID = 7431625294878419160L;
    
    protected AbstractSetMultimap(final Map<K, Collection<V>> map) {
        super(map);
    }
    
    abstract Set<V> createCollection();
    
    public Set<V> get(@Nullable final K key) {
        return (Set<V>)(Set)super.get(key);
    }
    
    public Set<Map.Entry<K, V>> entries() {
        return (Set<Map.Entry<K, V>>)(Set)super.entries();
    }
    
    public Set<V> removeAll(@Nullable final Object key) {
        return (Set<V>)(Set)super.removeAll(key);
    }
    
    public Set<V> replaceValues(@Nullable final K key, final Iterable<? extends V> values) {
        return (Set<V>)(Set)super.replaceValues(key, values);
    }
    
    public Map<K, Collection<V>> asMap() {
        return super.asMap();
    }
    
    public boolean put(final K key, final V value) {
        return super.put(key, value);
    }
    
    public boolean equals(@Nullable final Object object) {
        return super.equals(object);
    }
}
