// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import java.util.Iterator;
import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;
import java.util.Map;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible(serializable = true, emulated = true)
final class SingletonImmutableMap<K, V> extends ImmutableMap<K, V>
{
    final transient K singleKey;
    final transient V singleValue;
    private transient Map.Entry<K, V> entry;
    private transient ImmutableSet<Map.Entry<K, V>> entrySet;
    private transient ImmutableSet<K> keySet;
    private transient ImmutableCollection<V> values;
    
    SingletonImmutableMap(final K singleKey, final V singleValue) {
        this.singleKey = singleKey;
        this.singleValue = singleValue;
    }
    
    SingletonImmutableMap(final Map.Entry<K, V> entry) {
        this.entry = entry;
        this.singleKey = entry.getKey();
        this.singleValue = entry.getValue();
    }
    
    private Map.Entry<K, V> entry() {
        final Map.Entry<K, V> e = this.entry;
        return (e == null) ? (this.entry = Maps.immutableEntry(this.singleKey, this.singleValue)) : e;
    }
    
    public V get(@Nullable final Object key) {
        return this.singleKey.equals(key) ? this.singleValue : null;
    }
    
    public int size() {
        return 1;
    }
    
    public boolean isEmpty() {
        return false;
    }
    
    public boolean containsKey(@Nullable final Object key) {
        return this.singleKey.equals(key);
    }
    
    public boolean containsValue(@Nullable final Object value) {
        return this.singleValue.equals(value);
    }
    
    boolean isPartialView() {
        return false;
    }
    
    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        final ImmutableSet<Map.Entry<K, V>> es = this.entrySet;
        return (es == null) ? (this.entrySet = ImmutableSet.of(this.entry())) : es;
    }
    
    public ImmutableSet<K> keySet() {
        final ImmutableSet<K> ks = this.keySet;
        return (ks == null) ? (this.keySet = ImmutableSet.of(this.singleKey)) : ks;
    }
    
    public ImmutableCollection<V> values() {
        final ImmutableCollection<V> v = this.values;
        return (v == null) ? (this.values = new Values<V>(this.singleValue)) : v;
    }
    
    public boolean equals(@Nullable final Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Map)) {
            return false;
        }
        final Map<?, ?> that = (Map<?, ?>)object;
        if (that.size() != 1) {
            return false;
        }
        final Map.Entry<?, ?> entry = that.entrySet().iterator().next();
        return this.singleKey.equals(entry.getKey()) && this.singleValue.equals(entry.getValue());
    }
    
    public int hashCode() {
        return this.singleKey.hashCode() ^ this.singleValue.hashCode();
    }
    
    public String toString() {
        return '{' + this.singleKey.toString() + '=' + this.singleValue.toString() + '}';
    }
    
    private static class Values<V> extends ImmutableCollection<V>
    {
        final V singleValue;
        
        Values(final V singleValue) {
            this.singleValue = singleValue;
        }
        
        public boolean contains(final Object object) {
            return this.singleValue.equals(object);
        }
        
        public boolean isEmpty() {
            return false;
        }
        
        public int size() {
            return 1;
        }
        
        public UnmodifiableIterator<V> iterator() {
            return Iterators.singletonIterator(this.singleValue);
        }
        
        boolean isPartialView() {
            return true;
        }
    }
}
