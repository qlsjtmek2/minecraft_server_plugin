// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.util.HashMap;

public class BeanToDbMap<B, D>
{
    final HashMap<B, D> keyMap;
    final HashMap<D, B> valueMap;
    final boolean allowNulls;
    
    public BeanToDbMap() {
        this(false);
    }
    
    public BeanToDbMap(final boolean allowNulls) {
        this.allowNulls = allowNulls;
        this.keyMap = new HashMap<B, D>();
        this.valueMap = new HashMap<D, B>();
    }
    
    public BeanToDbMap<B, D> add(final B beanValue, final D dbValue) {
        this.keyMap.put(beanValue, dbValue);
        this.valueMap.put(dbValue, beanValue);
        return this;
    }
    
    public D getDbValue(final B beanValue) {
        if (beanValue == null) {
            return null;
        }
        final D dbValue = this.keyMap.get(beanValue);
        if (dbValue == null && !this.allowNulls) {
            final String msg = "DB value for " + beanValue + " not found in " + this.valueMap;
            throw new IllegalArgumentException(msg);
        }
        return dbValue;
    }
    
    public B getBeanValue(final D dbValue) {
        if (dbValue == null) {
            return null;
        }
        final B beanValue = this.valueMap.get(dbValue);
        if (beanValue == null && !this.allowNulls) {
            final String msg = "Bean value for " + dbValue + " not found in " + this.valueMap;
            throw new IllegalArgumentException(msg);
        }
        return beanValue;
    }
}
