// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebean.config.ScalarTypeConverter;
import com.avaje.ebeaninternal.server.type.CtCompoundProperty;

public class BeanPropertyCompoundScalar extends BeanProperty
{
    private final BeanPropertyCompoundRoot rootProperty;
    private final CtCompoundProperty ctProperty;
    private final ScalarTypeConverter typeConverter;
    
    public BeanPropertyCompoundScalar(final BeanPropertyCompoundRoot rootProperty, final DeployBeanProperty scalarDeploy, final CtCompoundProperty ctProperty, final ScalarTypeConverter<?, ?> typeConverter) {
        super(scalarDeploy);
        this.rootProperty = rootProperty;
        this.ctProperty = ctProperty;
        this.typeConverter = typeConverter;
    }
    
    public Object getValue(Object valueObject) {
        if (this.typeConverter != null) {
            valueObject = this.typeConverter.unwrapValue(valueObject);
        }
        return this.ctProperty.getValue(valueObject);
    }
    
    public void setValue(final Object bean, final Object value) {
        this.setValueInCompound(bean, value, false);
    }
    
    public void setValueInCompound(final Object bean, final Object value, final boolean intercept) {
        Object compoundValue = this.ctProperty.setValue(bean, value);
        if (compoundValue != null) {
            if (this.typeConverter != null) {
                compoundValue = this.typeConverter.wrapValue(compoundValue);
            }
            if (intercept) {
                this.rootProperty.setRootValueIntercept(bean, compoundValue);
            }
            else {
                this.rootProperty.setRootValue(bean, compoundValue);
            }
        }
    }
    
    public void setValueIntercept(final Object bean, final Object value) {
        this.setValueInCompound(bean, value, true);
    }
    
    public Object getValueIntercept(final Object bean) {
        return this.getValue(bean);
    }
    
    public Object elGetReference(final Object bean) {
        return this.getValue(bean);
    }
    
    public Object elGetValue(final Object bean) {
        return this.getValue(bean);
    }
    
    public void elSetReference(final Object bean) {
        super.elSetReference(bean);
    }
    
    public void elSetValue(final Object bean, final Object value, final boolean populate, final boolean reference) {
        super.elSetValue(bean, value, populate, reference);
    }
}
