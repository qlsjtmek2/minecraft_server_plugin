// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Set;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
public abstract class ForwardingMultimap<K, V> extends ForwardingObject implements Multimap<K, V>
{
    protected abstract Multimap<K, V> delegate();
    
    public Map<K, Collection<V>> asMap() {
        return this.delegate().asMap();
    }
    
    public void clear() {
        this.delegate().clear();
    }
    
    public boolean containsEntry(@Nullable final Object key, @Nullable final Object value) {
        return this.delegate().containsEntry(key, value);
    }
    
    public boolean containsKey(@Nullable final Object key) {
        return this.delegate().containsKey(key);
    }
    
    public boolean containsValue(@Nullable final Object value) {
        return this.delegate().containsValue(value);
    }
    
    public Collection<Map.Entry<K, V>> entries() {
        return this.delegate().entries();
    }
    
    public Collection<V> get(@Nullable final K key) {
        return this.delegate().get(key);
    }
    
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }
    
    public Multiset<K> keys() {
        return this.delegate().keys();
    }
    
    public Set<K> keySet() {
        return this.delegate().keySet();
    }
    
    public boolean put(final K key, final V value) {
        return this.delegate().put(key, value);
    }
    
    public boolean putAll(final K key, final Iterable<? extends V> values) {
        return this.delegate().putAll(key, values);
    }
    
    public boolean putAll(final Multimap<? extends K, ? extends V> multimap) {
        return this.delegate().putAll(multimap);
    }
    
    public boolean remove(@Nullable final Object key, @Nullable final Object value) {
        return this.delegate().remove(key, value);
    }
    
    public Collection<V> removeAll(@Nullable final Object key) {
        return this.delegate().removeAll(key);
    }
    
    public Collection<V> replaceValues(final K key, final Iterable<? extends V> values) {
        return this.delegate().replaceValues(key, values);
    }
    
    public int size() {
        return this.delegate().size();
    }
    
    public Collection<V> values() {
        return this.delegate().values();
    }
    
    public boolean equals(@Nullable final Object object) {
        return object == this || this.delegate().equals(object);
    }
    
    public int hashCode() {
        return this.delegate().hashCode();
    }
}
