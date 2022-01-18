// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import java.io.Serializable;
import com.avaje.ebeaninternal.server.core.PersistRequest;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public final class BeanPersistIdMap
{
    private final Map<String, BeanPersistIds> beanMap;
    
    public BeanPersistIdMap() {
        this.beanMap = new LinkedHashMap<String, BeanPersistIds>();
    }
    
    public String toString() {
        return this.beanMap.toString();
    }
    
    public boolean isEmpty() {
        return this.beanMap.isEmpty();
    }
    
    public Collection<BeanPersistIds> values() {
        return this.beanMap.values();
    }
    
    public void add(final BeanDescriptor<?> desc, final PersistRequest.Type type, final Object id) {
        final BeanPersistIds r = this.getPersistIds(desc);
        r.addId(type, (Serializable)id);
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
