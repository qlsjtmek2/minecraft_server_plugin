// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public class GeneratedCounter implements GeneratedProperty
{
    final int numberType;
    
    public GeneratedCounter(final int numberType) {
        this.numberType = numberType;
    }
    
    public Object getInsertValue(final BeanProperty prop, final Object bean) {
        final Integer i = 1;
        return BasicTypeConverter.convert(i, this.numberType);
    }
    
    public Object getUpdateValue(final BeanProperty prop, final Object bean) {
        final Number currVal = (Number)prop.getValue(bean);
        final Integer nextVal = currVal.intValue() + 1;
        return BasicTypeConverter.convert(nextVal, this.numberType);
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
