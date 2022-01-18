// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import java.util.Collection;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class BeanDeltaMap
{
    private Map<String, BeanDeltaList> deltaMap;
    
    public BeanDeltaMap() {
        this.deltaMap = new HashMap<String, BeanDeltaList>();
    }
    
    public BeanDeltaMap(final List<BeanDelta> deltaBeans) {
        this.deltaMap = new HashMap<String, BeanDeltaList>();
        if (deltaBeans != null) {
            for (int i = 0; i < deltaBeans.size(); ++i) {
                final BeanDelta deltaBean = deltaBeans.get(i);
                this.addBeanDelta(deltaBean);
            }
        }
    }
    
    public String toString() {
        return this.deltaMap.values().toString();
    }
    
    public void addBeanDelta(final BeanDelta beanDelta) {
        final BeanDescriptor<?> d = beanDelta.getBeanDescriptor();
        final BeanDeltaList list = this.getDeltaBeanList(d);
        list.add(beanDelta);
    }
    
    public Collection<BeanDeltaList> deltaLists() {
        return this.deltaMap.values();
    }
    
    private BeanDeltaList getDeltaBeanList(final BeanDescriptor<?> d) {
        BeanDeltaList deltaList = this.deltaMap.get(d.getFullName());
        if (deltaList == null) {
            deltaList = new BeanDeltaList(d);
            this.deltaMap.put(d.getFullName(), deltaList);
        }
        return deltaList;
    }
}
