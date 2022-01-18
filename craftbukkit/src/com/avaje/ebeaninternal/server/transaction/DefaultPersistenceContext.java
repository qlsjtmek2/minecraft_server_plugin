// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebeaninternal.server.subclass.SubClassUtil;
import java.util.Iterator;
import java.util.Map;
import com.avaje.ebeaninternal.api.Monitor;
import java.util.HashMap;
import com.avaje.ebean.bean.PersistenceContext;

public final class DefaultPersistenceContext implements PersistenceContext
{
    private final HashMap<String, ClassContext> typeCache;
    private final Monitor monitor;
    
    public DefaultPersistenceContext() {
        this.typeCache = new HashMap<String, ClassContext>();
        this.monitor = new Monitor();
    }
    
    public void put(final Object id, final Object bean) {
        synchronized (this.monitor) {
            this.getClassContext(bean.getClass()).put(id, bean);
        }
    }
    
    public Object putIfAbsent(final Object id, final Object bean) {
        synchronized (this.monitor) {
            return this.getClassContext(bean.getClass()).putIfAbsent(id, bean);
        }
    }
    
    public Object get(final Class<?> beanType, final Object id) {
        synchronized (this.monitor) {
            return this.getClassContext(beanType).get(id);
        }
    }
    
    public int size(final Class<?> beanType) {
        synchronized (this.monitor) {
            final ClassContext classMap = this.typeCache.get(beanType.getName());
            return (classMap == null) ? 0 : classMap.size();
        }
    }
    
    public void clear() {
        synchronized (this.monitor) {
            this.typeCache.clear();
        }
    }
    
    public void clear(final Class<?> beanType) {
        synchronized (this.monitor) {
            final ClassContext classMap = this.typeCache.get(beanType.getName());
            if (classMap != null) {
                classMap.clear();
            }
        }
    }
    
    public void clear(final Class<?> beanType, final Object id) {
        synchronized (this.monitor) {
            final ClassContext classMap = this.typeCache.get(beanType.getName());
            if (classMap != null && id != null) {
                classMap.remove(id);
            }
        }
    }
    
    public String toString() {
        synchronized (this.monitor) {
            final StringBuilder sb = new StringBuilder();
            for (final Map.Entry<String, ClassContext> entry : this.typeCache.entrySet()) {
                if (entry.getValue().size() > 0) {
                    sb.append(entry.getKey() + ":" + entry.getValue().size() + "; ");
                }
            }
            return sb.toString();
        }
    }
    
    private ClassContext getClassContext(final Class<?> beanType) {
        final String clsName = SubClassUtil.getSuperClassName(beanType.getName());
        ClassContext classMap = this.typeCache.get(clsName);
        if (classMap == null) {
            classMap = new ClassContext();
            this.typeCache.put(clsName, classMap);
        }
        return classMap;
    }
    
    private static class ClassContext
    {
        private final WeakValueMap<Object, Object> map;
        
        private ClassContext() {
            this.map = new WeakValueMap<Object, Object>();
        }
        
        private Object get(final Object id) {
            return this.map.get(id);
        }
        
        private Object putIfAbsent(final Object id, final Object bean) {
            return this.map.putIfAbsent(id, bean);
        }
        
        private void put(final Object id, final Object b) {
            this.map.put(id, b);
        }
        
        private int size() {
            return this.map.size();
        }
        
        private void clear() {
            this.map.clear();
        }
        
        private Object remove(final Object id) {
            return this.map.remove(id);
        }
    }
}
