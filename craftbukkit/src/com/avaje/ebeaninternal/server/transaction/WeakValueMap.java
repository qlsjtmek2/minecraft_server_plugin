// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import java.lang.ref.WeakReference;
import java.lang.ref.Reference;
import java.util.HashMap;
import java.util.Map;
import java.lang.ref.ReferenceQueue;

public class WeakValueMap<K, V>
{
    protected final ReferenceQueue<V> refQueue;
    private final Map<K, WeakReferenceWithKey<K, V>> backing;
    
    public WeakValueMap() {
        this.refQueue = new ReferenceQueue<V>();
        this.backing = new HashMap<K, WeakReferenceWithKey<K, V>>();
    }
    
    private WeakReferenceWithKey<K, V> createReference(final K key, final V value) {
        return new WeakReferenceWithKey<K, V>(key, value, this.refQueue);
    }
    
    private void expunge() {
        Reference ref;
        while ((ref = this.refQueue.poll()) != null) {
            this.backing.remove(((WeakReferenceWithKey)ref).getKey());
        }
    }
    
    public Object putIfAbsent(final K key, final V value) {
        this.expunge();
        final Reference<V> ref = (Reference<V>)this.backing.get(key);
        if (ref != null) {
            final V existingValue = ref.get();
            if (existingValue != null) {
                return existingValue;
            }
        }
        this.backing.put(key, this.createReference(key, value));
        return null;
    }
    
    public void put(final K key, final V value) {
        this.expunge();
        this.backing.put(key, this.createReference(key, value));
    }
    
    public V get(final K key) {
        this.expunge();
        final Reference<V> v = (Reference<V>)this.backing.get(key);
        return (v == null) ? null : v.get();
    }
    
    public int size() {
        this.expunge();
        return this.backing.size();
    }
    
    public boolean isEmpty() {
        this.expunge();
        return this.backing.isEmpty();
    }
    
    public boolean containsKey(final Object key) {
        this.expunge();
        return this.backing.containsKey(key);
    }
    
    public V remove(final K key) {
        this.expunge();
        final Reference<V> v = (Reference<V>)this.backing.remove(key);
        return (v == null) ? null : v.get();
    }
    
    public void clear() {
        this.expunge();
        this.backing.clear();
        this.expunge();
    }
    
    public String toString() {
        this.expunge();
        return this.backing.toString();
    }
    
    private static class WeakReferenceWithKey<K, V> extends WeakReference<V>
    {
        private final K key;
        
        public WeakReferenceWithKey(final K key, final V referent, final ReferenceQueue<? super V> q) {
            super(referent, q);
            this.key = key;
        }
        
        public K getKey() {
            return this.key;
        }
    }
}
