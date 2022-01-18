// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public class GeneratedCounterInteger implements GeneratedProperty
{
    public Object getInsertValue(final BeanProperty prop, final Object bean) {
        return 1;
    }
    
    public Object getUpdateValue(final BeanProperty prop, final Object bean) {
        final Integer i = (Integer)prop.getValue(bean);
        return i + 1;
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
