// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import java.io.Serializable;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.LinkedHashMap;
import java.util.Map;

public class BeanPathUpdate
{
    private final Map<String, BeanPathUpdateIds> map;
    
    public BeanPathUpdate() {
        this.map = new LinkedHashMap<String, BeanPathUpdateIds>();
    }
    
    public void add(final BeanDescriptor<?> desc, final String path, final Object id) {
        final String key = desc.getFullName() + ":" + path;
        BeanPathUpdateIds pathIds = this.map.get(key);
        if (pathIds == null) {
            pathIds = new BeanPathUpdateIds(desc, path);
            this.map.put(key, pathIds);
        }
        pathIds.addId((Serializable)id);
    }
}
