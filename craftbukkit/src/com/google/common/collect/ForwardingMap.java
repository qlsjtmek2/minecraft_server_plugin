// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.collect;

import com.google.common.base.Supplier;
import java.util.Iterator;
import com.google.common.base.Objects;
import com.google.common.annotations.Beta;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Set;
import com.google.common.annotations.GwtCompatible;
import java.util.Map;

@GwtCompatible
public abstract class ForwardingMap<K, V> extends ForwardingObject implements Map<K, V>
{
    protected abstract Map<K, V> delegate();
    
    public int size() {
        return this.delegate().size();
    }
    
    public boolean isEmpty() {
        return this.delegate().isEmpty();
    }
    
    public V remove(final Object object) {
        return this.delegate().remove(object);
    }
    
    public void clear() {
        this.delegate().clear();
    }
    
    public boolean containsKey(final Object key) {
        return this.delegate().containsKey(key);
    }
    
    public boolean containsValue(final Object value) {
        return this.delegate().containsValue(value);
    }
    
    public V get(final Object key) {
        return this.delegate().get(key);
    }
    
    public V put(final K key, final V value) {
        return this.delegate().put(key, value);
    }
    
    public void putAll(final Map<? extends K, ? extends V> map) {
        this.delegate().putAll(map);
    }
    
    public Set<K> keySet() {
        return this.delegate().keySet();
    }
    
    public Collection<V> values() {
        return this.delegate().values();
    }
    
    public Set<Entry<K, V>> entrySet() {
        return this.delegate().entrySet();
    }
    
    public boolean equals(@Nullable final Object object) {
        return object == this || this.delegate().equals(object);
    }
    
    public int hashCode() {
        return this.delegate().hashCode();
    }
    
    @Beta
    protected void standardPutAll(final Map<? extends K, ? extends V> map) {
        Maps.putAllImpl((Map<Object, Object>)this, map);
    }
    
    @Beta
    protected V standardRemove(@Nullable final Object key) {
        final Iterator<Entry<K, V>> entryIterator = this.entrySet().iterator();
        while (entryIterator.hasNext()) {
            final Entry<K, V> entry = entryIterator.next();
            if (Objects.equal(entry.getKey(), key)) {
                final V value = entry.getValue();
                entryIterator.remove();
                return value;
            }
        }
        return null;
    }
    
    @Beta
    protected void standardClear() {
        final Iterator<Entry<K, V>> entryIterator = this.entrySet().iterator();
        while (entryIterator.hasNext()) {
            entryIterator.next();
            entryIterator.remove();
        }
    }
    
    @Deprecated
    @Beta
    protected Set<K> standardKeySet() {
        return (Set<K>)new StandardKeySet();
    }
    
    @Beta
    protected boolean standardContainsKey(@Nullable final Object key) {
        return Maps.containsKeyImpl(this, key);
    }
    
    @Deprecated
    @Beta
    protected Collection<V> standardValues() {
        return (Collection<V>)new StandardValues();
    }
    
    @Beta
    protected boolean standardContainsValue(@Nullable final Object value) {
        return Maps.containsValueImpl(this, value);
    }
    
    @Deprecated
    @Beta
    protected Set<Entry<K, V>> standardEntrySet(final Supplier<Iterator<Entry<K, V>>> entryIteratorSupplier) {
        return (Set<Entry<K, V>>)new StandardEntrySet() {
            public Iterator<Entry<K, V>> iterator() {
                return entryIteratorSupplier.get();
            }
        };
    }
    
    @Beta
    protected boolean standardIsEmpty() {
        return !this.entrySet().iterator().hasNext();
    }
    
    @Beta
    protected boolean standardEquals(@Nullable final Object object) {
        return Maps.equalsImpl(this, object);
    }
    
    @Beta
    protected int standardHashCode() {
        return Sets.hashCodeImpl(this.entrySet());
    }
    
    @Beta
    protected String standardToString() {
        return Maps.toStringImpl(this);
    }
    
    @Beta
    protected class StandardKeySet extends Maps.KeySet<K, V>
    {
        Map<K, V> map() {
            return (Map<K, V>)ForwardingMap.this;
        }
    }
    
    @Beta
    protected class StandardValues extends Maps.Values<K, V>
    {
        Map<K, V> map() {
            return (Map<K, V>)ForwardingMap.this;
        }
    }
    
    @Beta
    protected abstract class StandardEntrySet extends Maps.EntrySet<K, V>
    {
        Map<K, V> map() {
            return (Map<K, V>)ForwardingMap.this;
        }
    }
}
