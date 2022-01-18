// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.common;

import com.avaje.ebean.bean.BeanCollection;
import java.util.Collections;
import java.util.Set;
import java.util.Iterator;
import java.util.Collection;
import java.io.ObjectStreamException;
import com.avaje.ebean.bean.SerializeControl;
import com.avaje.ebean.bean.BeanCollectionLoader;
import java.util.LinkedHashMap;
import java.util.Map;

public final class BeanMap<K, E> extends AbstractBeanCollection<E> implements Map<K, E>
{
    private Map<K, E> map;
    
    public BeanMap(final Map<K, E> map) {
        this.map = map;
    }
    
    public BeanMap() {
        this((Map)new LinkedHashMap());
    }
    
    public BeanMap(final BeanCollectionLoader ebeanServer, final Object ownerBean, final String propertyName) {
        super(ebeanServer, ownerBean, propertyName);
    }
    
    Object readResolve() throws ObjectStreamException {
        if (SerializeControl.isVanillaCollections()) {
            return this.map;
        }
        return this;
    }
    
    Object writeReplace() throws ObjectStreamException {
        if (SerializeControl.isVanillaCollections()) {
            return this.map;
        }
        return this;
    }
    
    public void internalAdd(final Object bean) {
        throw new RuntimeException("Not allowed for map");
    }
    
    public boolean isPopulated() {
        return this.map != null;
    }
    
    public boolean isReference() {
        return this.map == null;
    }
    
    public boolean checkEmptyLazyLoad() {
        if (this.map == null) {
            this.map = new LinkedHashMap<K, E>();
            return true;
        }
        return false;
    }
    
    private void initClear() {
        synchronized (this) {
            if (this.map == null) {
                if (this.modifyListening) {
                    this.lazyLoadCollection(true);
                }
                else {
                    this.map = new LinkedHashMap<K, E>();
                }
            }
            this.touched();
        }
    }
    
    private void init() {
        synchronized (this) {
            if (this.map == null) {
                this.lazyLoadCollection(false);
            }
            this.touched();
        }
    }
    
    public void setActualMap(final Map<?, ?> map) {
        this.map = (Map<K, E>)map;
    }
    
    public Map<K, E> getActualMap() {
        return this.map;
    }
    
    public Collection<E> getActualDetails() {
        return this.map.values();
    }
    
    public Object getActualCollection() {
        return this.map;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("BeanMap ");
        if (this.isSharedInstance()) {
            sb.append("sharedInstance ");
        }
        else if (this.isReadOnly()) {
            sb.append("readOnly ");
        }
        if (this.map == null) {
            sb.append("deferred ");
        }
        else {
            sb.append("size[").append(this.map.size()).append("]");
            sb.append(" hasMoreRows[").append(this.hasMoreRows).append("]");
            sb.append(" map").append(this.map);
        }
        return sb.toString();
    }
    
    public boolean equals(final Object obj) {
        this.init();
        return this.map.equals(obj);
    }
    
    public int hashCode() {
        this.init();
        return this.map.hashCode();
    }
    
    public void clear() {
        this.checkReadOnly();
        this.initClear();
        if (this.modifyRemoveListening) {
            for (final K key : this.map.keySet()) {
                final E o = this.map.remove(key);
                this.modifyRemoval(o);
            }
        }
        this.map.clear();
    }
    
    public boolean containsKey(final Object key) {
        this.init();
        return this.map.containsKey(key);
    }
    
    public boolean containsValue(final Object value) {
        this.init();
        return this.map.containsValue(value);
    }
    
    public Set<Entry<K, E>> entrySet() {
        this.init();
        if (this.isReadOnly()) {
            return Collections.unmodifiableSet((Set<? extends Entry<K, E>>)this.map.entrySet());
        }
        if (this.modifyListening) {
            final Set<Entry<K, E>> s = this.map.entrySet();
            return new ModifySet<Entry<K, E>>((BeanCollection<Entry<K, E>>)this, s);
        }
        return this.map.entrySet();
    }
    
    public E get(final Object key) {
        this.init();
        return this.map.get(key);
    }
    
    public boolean isEmpty() {
        this.init();
        return this.map.isEmpty();
    }
    
    public Set<K> keySet() {
        this.init();
        if (this.isReadOnly()) {
            return Collections.unmodifiableSet((Set<? extends K>)this.map.keySet());
        }
        return this.map.keySet();
    }
    
    public E put(final K key, final E value) {
        this.checkReadOnly();
        this.init();
        if (this.modifyListening) {
            final Object o = this.map.put(key, value);
            this.modifyAddition(value);
            this.modifyRemoval(o);
        }
        return this.map.put(key, value);
    }
    
    public void putAll(final Map<? extends K, ? extends E> t) {
        this.checkReadOnly();
        this.init();
        if (this.modifyListening) {
            for (final Entry entry : t.entrySet()) {
                final Object o = this.map.put(entry.getKey(), entry.getValue());
                this.modifyAddition(entry.getValue());
                this.modifyRemoval(o);
            }
        }
        this.map.putAll(t);
    }
    
    public E remove(final Object key) {
        this.checkReadOnly();
        this.init();
        if (this.modifyRemoveListening) {
            final E o = this.map.remove(key);
            this.modifyRemoval(o);
            return o;
        }
        return this.map.remove(key);
    }
    
    public int size() {
        this.init();
        return this.map.size();
    }
    
    public Collection<E> values() {
        this.init();
        if (this.isReadOnly()) {
            return Collections.unmodifiableCollection((Collection<? extends E>)this.map.values());
        }
        if (this.modifyListening) {
            final Collection<E> c = this.map.values();
            return new ModifyCollection<E>(this, c);
        }
        return this.map.values();
    }
}
