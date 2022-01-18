// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Set;
import java.util.Map;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
final class WellBehavedMap<K, V> extends ForwardingMap<K, V>
{
    private final Map<K, V> delegate;
    private Set<Map.Entry<K, V>> entrySet;
    
    private WellBehavedMap(final Map<K, V> delegate) {
        this.delegate = delegate;
    }
    
    static <K, V> WellBehavedMap<K, V> wrap(final Map<K, V> delegate) {
        return new WellBehavedMap<K, V>(delegate);
    }
    
    protected Map<K, V> delegate() {
        return this.delegate;
    }
    
    public Set<Map.Entry<K, V>> entrySet() {
        final Set<Map.Entry<K, V>> es = this.entrySet;
        if (es != null) {
            return es;
        }
        return this.entrySet = Sets.transform(this.delegate.keySet(), (Sets.InvertibleFunction<K, Map.Entry<K, V>>)new KeyToEntryConverter((Map<Object, Object>)this));
    }
    
    private static class KeyToEntryConverter<K, V> extends Sets.InvertibleFunction<K, Map.Entry<K, V>>
    {
        final Map<K, V> map;
        
        KeyToEntryConverter(final Map<K, V> map) {
            this.map = map;
        }
        
        public Map.Entry<K, V> apply(final K key) {
            return new AbstractMapEntry<K, V>() {
                public K getKey() {
                    return key;
                }
                
                public V getValue() {
                    return KeyToEntryConverter.this.map.get(key);
                }
                
                public V setValue(final V value) {
                    return KeyToEntryConverter.this.map.put(key, value);
                }
            };
        }
        
        public K invert(final Map.Entry<K, V> entry) {
            return entry.getKey();
        }
    }
}
