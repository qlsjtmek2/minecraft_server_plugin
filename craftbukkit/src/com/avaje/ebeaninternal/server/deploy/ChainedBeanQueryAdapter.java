// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.event.BeanQueryRequest;
import java.util.Collection;
import java.util.Comparator;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import com.avaje.ebean.event.BeanQueryAdapter;

public class ChainedBeanQueryAdapter implements BeanQueryAdapter
{
    private static final Sorter SORTER;
    private final List<BeanQueryAdapter> list;
    private final BeanQueryAdapter[] chain;
    
    public ChainedBeanQueryAdapter(final BeanQueryAdapter c1, final BeanQueryAdapter c2) {
        this(addList(c1, c2));
    }
    
    private static List<BeanQueryAdapter> addList(final BeanQueryAdapter c1, final BeanQueryAdapter c2) {
        final ArrayList<BeanQueryAdapter> addList = new ArrayList<BeanQueryAdapter>(2);
        addList.add(c1);
        addList.add(c2);
        return addList;
    }
    
    public ChainedBeanQueryAdapter(final List<BeanQueryAdapter> list) {
        this.list = list;
        final BeanQueryAdapter[] c = list.toArray(new BeanQueryAdapter[list.size()]);
        Arrays.sort(c, ChainedBeanQueryAdapter.SORTER);
        this.chain = c;
    }
    
    public ChainedBeanQueryAdapter register(final BeanQueryAdapter c) {
        if (this.list.contains(c)) {
            return this;
        }
        final List<BeanQueryAdapter> newList = new ArrayList<BeanQueryAdapter>();
        newList.addAll(this.list);
        newList.add(c);
        return new ChainedBeanQueryAdapter(newList);
    }
    
    public ChainedBeanQueryAdapter deregister(final BeanQueryAdapter c) {
        if (!this.list.contains(c)) {
            return this;
        }
        final ArrayList<BeanQueryAdapter> newList = new ArrayList<BeanQueryAdapter>();
        newList.addAll(this.list);
        newList.remove(c);
        return new ChainedBeanQueryAdapter(newList);
    }
    
    public int getExecutionOrder() {
        return 0;
    }
    
    public boolean isRegisterFor(final Class<?> cls) {
        return false;
    }
    
    public void preQuery(final BeanQueryRequest<?> request) {
        for (int i = 0; i < this.chain.length; ++i) {
            this.chain[i].preQuery(request);
        }
    }
    
    static {
        SORTER = new Sorter();
    }
    
    private static class Sorter implements Comparator<BeanQueryAdapter>
    {
        public int compare(final BeanQueryAdapter o1, final BeanQueryAdapter o2) {
            final int i1 = o1.getExecutionOrder();
            final int i2 = o2.getExecutionOrder();
            return (i1 < i2) ? -1 : ((i1 == i2) ? 0 : 1);
        }
    }
}
