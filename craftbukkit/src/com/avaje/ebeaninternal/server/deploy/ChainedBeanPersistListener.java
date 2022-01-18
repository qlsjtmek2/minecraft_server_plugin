// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import java.util.Set;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import com.avaje.ebean.event.BeanPersistListener;

public class ChainedBeanPersistListener<T> implements BeanPersistListener<T>
{
    private final List<BeanPersistListener<T>> list;
    private final BeanPersistListener<T>[] chain;
    
    public ChainedBeanPersistListener(final BeanPersistListener<T> c1, final BeanPersistListener<T> c2) {
        this(addList(c1, c2));
    }
    
    private static <T> List<BeanPersistListener<T>> addList(final BeanPersistListener<T> c1, final BeanPersistListener<T> c2) {
        final ArrayList<BeanPersistListener<T>> addList = new ArrayList<BeanPersistListener<T>>(2);
        addList.add(c1);
        addList.add(c2);
        return addList;
    }
    
    public ChainedBeanPersistListener(final List<BeanPersistListener<T>> list) {
        this.list = list;
        this.chain = list.toArray(new BeanPersistListener[list.size()]);
    }
    
    public ChainedBeanPersistListener<T> register(final BeanPersistListener<T> c) {
        if (this.list.contains(c)) {
            return this;
        }
        final List<BeanPersistListener<T>> newList = new ArrayList<BeanPersistListener<T>>();
        newList.addAll(this.list);
        newList.add(c);
        return new ChainedBeanPersistListener<T>(newList);
    }
    
    public ChainedBeanPersistListener<T> deregister(final BeanPersistListener<T> c) {
        if (!this.list.contains(c)) {
            return this;
        }
        final ArrayList<BeanPersistListener<T>> newList = new ArrayList<BeanPersistListener<T>>();
        newList.addAll(this.list);
        newList.remove(c);
        return new ChainedBeanPersistListener<T>(newList);
    }
    
    public boolean deleted(final T bean) {
        boolean notifyCluster = false;
        for (int i = 0; i < this.chain.length; ++i) {
            if (this.chain[i].deleted(bean)) {
                notifyCluster = true;
            }
        }
        return notifyCluster;
    }
    
    public boolean inserted(final T bean) {
        boolean notifyCluster = false;
        for (int i = 0; i < this.chain.length; ++i) {
            if (this.chain[i].inserted(bean)) {
                notifyCluster = true;
            }
        }
        return notifyCluster;
    }
    
    public void remoteDelete(final Object id) {
        for (int i = 0; i < this.chain.length; ++i) {
            this.chain[i].remoteDelete(id);
        }
    }
    
    public void remoteInsert(final Object id) {
        for (int i = 0; i < this.chain.length; ++i) {
            this.chain[i].remoteInsert(id);
        }
    }
    
    public void remoteUpdate(final Object id) {
        for (int i = 0; i < this.chain.length; ++i) {
            this.chain[i].remoteUpdate(id);
        }
    }
    
    public boolean updated(final T bean, final Set<String> updatedProperties) {
        boolean notifyCluster = false;
        for (int i = 0; i < this.chain.length; ++i) {
            if (this.chain[i].updated(bean, updatedProperties)) {
                notifyCluster = true;
            }
        }
        return notifyCluster;
    }
}
