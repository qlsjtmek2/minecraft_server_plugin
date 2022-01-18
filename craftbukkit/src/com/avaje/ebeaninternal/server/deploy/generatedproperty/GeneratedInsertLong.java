// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public class GeneratedInsertLong implements GeneratedProperty
{
    public Object getInsertValue(final BeanProperty prop, final Object bean) {
        return System.currentTimeMillis();
    }
    
    public Object getUpdateValue(final BeanProperty prop, final Object bean) {
        return prop.getValue(bean);
    }
    
    public boolean includeInUpdate() {
        return false;
    }
    
    public boolean includeInInsert() {
        return true;
    }
    
    public boolean isDDLNotNullable() {
        return true;
    }
}
