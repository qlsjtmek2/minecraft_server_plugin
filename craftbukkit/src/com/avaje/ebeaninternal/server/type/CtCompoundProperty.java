// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.config.CompoundTypeProperty;

public class CtCompoundProperty
{
    private final String relativeName;
    private final CtCompoundProperty parent;
    private final CtCompoundType<?> compoundType;
    private final CompoundTypeProperty property;
    
    public CtCompoundProperty(final String relativeName, final CtCompoundProperty parent, final CtCompoundType<?> ctType, final CompoundTypeProperty<?, ?> property) {
        this.relativeName = relativeName;
        this.parent = parent;
        this.compoundType = ctType;
        this.property = property;
    }
    
    public String getRelativeName() {
        return this.relativeName;
    }
    
    public String getPropertyName() {
        return this.property.getName();
    }
    
    public String toString() {
        return this.relativeName;
    }
    
    public Object getValue(Object valueObject) {
        if (valueObject == null) {
            return null;
        }
        if (this.parent != null) {
            valueObject = this.parent.getValue(valueObject);
        }
        return this.property.getValue(valueObject);
    }
    
    public Object setValue(final Object bean, final Object value) {
        final Object compoundValue = ImmutableCompoundTypeBuilder.set(this.compoundType, this.property.getName(), value);
        if (compoundValue != null && this.parent != null) {
            return this.parent.setValue(bean, compoundValue);
        }
        return compoundValue;
    }
}
