// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import java.util.Set;
import com.avaje.ebean.event.BeanPersistRequest;
import java.util.Collection;
import java.util.Comparator;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import com.avaje.ebean.event.BeanPersistController;

public class ChainedBeanPersistController implements BeanPersistController
{
    private static final Sorter SORTER;
    private final List<BeanPersistController> list;
    private final BeanPersistController[] chain;
    
    public ChainedBeanPersistController(final BeanPersistController c1, final BeanPersistController c2) {
        this(addList(c1, c2));
    }
    
    private static List<BeanPersistController> addList(final BeanPersistController c1, final BeanPersistController c2) {
        final ArrayList<BeanPersistController> addList = new ArrayList<BeanPersistController>(2);
        addList.add(c1);
        addList.add(c2);
        return addList;
    }
    
    public ChainedBeanPersistController(final List<BeanPersistController> list) {
        this.list = list;
        final BeanPersistController[] c = list.toArray(new BeanPersistController[list.size()]);
        Arrays.sort(c, ChainedBeanPersistController.SORTER);
        this.chain = c;
    }
    
    public ChainedBeanPersistController register(final BeanPersistController c) {
        if (this.list.contains(c)) {
            return this;
        }
        final ArrayList<BeanPersistController> newList = new ArrayList<BeanPersistController>();
        newList.addAll(this.list);
        newList.add(c);
        return new ChainedBeanPersistController(newList);
    }
    
    public ChainedBeanPersistController deregister(final BeanPersistController c) {
        if (!this.list.contains(c)) {
            return this;
        }
        final ArrayList<BeanPersistController> newList = new ArrayList<BeanPersistController>();
        newList.addAll(this.list);
        newList.remove(c);
        return new ChainedBeanPersistController(newList);
    }
    
    public int getExecutionOrder() {
        return 0;
    }
    
    public boolean isRegisterFor(final Class<?> cls) {
        return false;
    }
    
    public void postDelete(final BeanPersistRequest<?> request) {
        for (int i = 0; i < this.chain.length; ++i) {
            this.chain[i].postDelete(request);
        }
    }
    
    public void postInsert(final BeanPersistRequest<?> request) {
        for (int i = 0; i < this.chain.length; ++i) {
            this.chain[i].postInsert(request);
        }
    }
    
    public void postLoad(final Object bean, final Set<String> includedProperties) {
        for (int i = 0; i < this.chain.length; ++i) {
            this.chain[i].postLoad(bean, includedProperties);
        }
    }
    
    public void postUpdate(final BeanPersistRequest<?> request) {
        for (int i = 0; i < this.chain.length; ++i) {
            this.chain[i].postUpdate(request);
        }
    }
    
    public boolean preDelete(final BeanPersistRequest<?> request) {
        for (int i = 0; i < this.chain.length; ++i) {
            if (!this.chain[i].preDelete(request)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean preInsert(final BeanPersistRequest<?> request) {
        for (int i = 0; i < this.chain.length; ++i) {
            if (!this.chain[i].preInsert(request)) {
                return false;
            }
        }
        return true;
    }
    
    public boolean preUpdate(final BeanPersistRequest<?> request) {
        for (int i = 0; i < this.chain.length; ++i) {
            if (!this.chain[i].preUpdate(request)) {
                return false;
            }
        }
        return true;
    }
    
    static {
        SORTER = new Sorter();
    }
    
    private static class Sorter implements Comparator<BeanPersistController>
    {
        public int compare(final BeanPersistController o1, final BeanPersistController o2) {
            final int i1 = o1.getExecutionOrder();
            final int i2 = o2.getExecutionOrder();
            return (i1 < i2) ? -1 : ((i1 == i2) ? 0 : 1);
        }
    }
}
