// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public class GeneratedUpdateLong implements GeneratedProperty
{
    public Object getInsertValue(final BeanProperty prop, final Object bean) {
        return System.currentTimeMillis();
    }
    
    public Object getUpdateValue(final BeanProperty prop, final Object bean) {
        return System.currentTimeMillis();
    }
    
    public boolean includeInUpdate() {
        return true;
    }
    
    public boolean includeInInsert() {
        return true;
    }
    
    public boolean isDDLNotNullable() {
        return true;
    }
}
