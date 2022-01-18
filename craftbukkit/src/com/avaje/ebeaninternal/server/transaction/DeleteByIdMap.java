// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebeaninternal.server.core.PersistRequest;
import java.util.Collection;
import java.io.Serializable;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public final class DeleteByIdMap
{
    private final Map<String, BeanPersistIds> beanMap;
    
    public DeleteByIdMap() {
        this.beanMap = new LinkedHashMap<String, BeanPersistIds>();
    }
    
    public String toString() {
        return this.beanMap.toString();
    }
    
    public void notifyCache() {
        for (final BeanPersistIds deleteIds : this.beanMap.values()) {
            final BeanDescriptor<?> d = deleteIds.getBeanDescriptor();
            final List<Serializable> idValues = deleteIds.getDeleteIds();
            if (idValues != null) {
                d.queryCacheClear();
                for (int i = 0; i < idValues.size(); ++i) {
                    d.cacheRemove(idValues.get(i));
                }
            }
        }
    }
    
    public boolean isEmpty() {
        return this.beanMap.isEmpty();
    }
    
    public Collection<BeanPersistIds> values() {
        return this.beanMap.values();
    }
    
    public void add(final BeanDescriptor<?> desc, final Object id) {
        final BeanPersistIds r = this.getPersistIds(desc);
        r.addId(PersistRequest.Type.DELETE, (Serializable)id);
    }
    
    public void addList(final BeanDescriptor<?> desc, final List<Object> idList) {
        final BeanPersistIds r = this.getPersistIds(desc);
        for (int i = 0; i < idList.size(); ++i) {
            r.addId(PersistRequest.Type.DELETE, idList.get(i));
        }
    }
    
    private BeanPersistIds getPersistIds(final BeanDescriptor<?> desc) {
        final String beanType = desc.getFullName();
        BeanPersistIds r = this.beanMap.get(beanType);
        if (r == null) {
            r = new BeanPersistIds(desc);
            this.beanMap.put(beanType, r);
        }
        return r;
    }
}
