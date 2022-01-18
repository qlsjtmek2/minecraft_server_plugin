// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Map;
import com.google.common.annotations.GwtCompatible;
import java.util.concurrent.ConcurrentMap;

@GwtCompatible
public abstract class ForwardingConcurrentMap<K, V> extends ForwardingMap<K, V> implements ConcurrentMap<K, V>
{
    protected abstract ConcurrentMap<K, V> delegate();
    
    public V putIfAbsent(final K key, final V value) {
        return this.delegate().putIfAbsent(key, value);
    }
    
    public boolean remove(final Object key, final Object value) {
        return this.delegate().remove(key, value);
    }
    
    public V replace(final K key, final V value) {
        return this.delegate().replace(key, value);
    }
    
    public boolean replace(final K key, final V oldValue, final V newValue) {
        return this.delegate().replace(key, oldValue, newValue);
    }
}
