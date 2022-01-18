// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import java.util.Map;
import java.util.Iterator;
import java.util.Set;
import java.util.List;
import com.avaje.ebean.common.BeanMap;
import com.avaje.ebean.common.BeanSet;
import com.avaje.ebean.common.BeanList;
import com.avaje.ebeaninternal.server.deploy.CopyContext;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.bean.BeanCollection;

public class CopyBeanCollection<T>
{
    private final BeanCollection<T> bc;
    private final BeanDescriptor<T> desc;
    private final CopyContext ctx;
    private final int maxDepth;
    
    public CopyBeanCollection(final BeanCollection<T> bc, final BeanDescriptor<T> desc, final CopyContext ctx, final int maxDepth) {
        this.bc = bc;
        this.desc = desc;
        this.ctx = ctx;
        this.maxDepth = maxDepth;
    }
    
    public BeanCollection<T> copy() {
        if (this.bc instanceof BeanList) {
            return this.copyList();
        }
        if (this.bc instanceof BeanSet) {
            return this.copySet();
        }
        if (this.bc instanceof BeanMap) {
            return this.copyMap();
        }
        final String msg = "Invalid beanCollection type " + this.bc.getClass().getName();
        throw new RuntimeException(msg);
    }
    
    private BeanCollection<T> copyList() {
        final BeanList<T> newList = new BeanList<T>();
        final List<T> actualList = ((BeanList)this.bc).getActualList();
        for (int i = 0; i < actualList.size(); ++i) {
            final T t = actualList.get(i);
            newList.add(this.desc.createCopy(t, this.ctx, this.maxDepth));
        }
        return newList;
    }
    
    private BeanCollection<T> copySet() {
        final BeanSet<T> newSet = new BeanSet<T>();
        final Set<T> actualSet = ((BeanSet)this.bc).getActualSet();
        for (final T t : actualSet) {
            newSet.add(this.desc.createCopy(t, this.ctx, this.maxDepth));
        }
        return newSet;
    }
    
    private BeanCollection<T> copyMap() {
        final BeanMap<Object, T> newMap = new BeanMap<Object, T>();
        final Map<Object, T> actualMap = ((BeanMap)this.bc).getActualMap();
        for (final Map.Entry<Object, T> entry : actualMap.entrySet()) {
            newMap.put(entry.getKey(), this.desc.createCopy(entry.getValue(), this.ctx, this.maxDepth));
        }
        return (BeanCollection<T>)newMap;
    }
}
