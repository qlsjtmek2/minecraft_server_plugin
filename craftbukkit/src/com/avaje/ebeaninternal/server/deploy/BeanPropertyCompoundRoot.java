// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.type.CtCompoundProperty;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.lang.reflect.Method;
import com.avaje.ebeaninternal.server.reflect.BeanReflectSetter;

public class BeanPropertyCompoundRoot
{
    private final BeanReflectSetter setter;
    private final Method writeMethod;
    private final String name;
    private final String fullBeanName;
    private final LinkedHashMap<String, BeanPropertyCompoundScalar> propMap;
    private final ArrayList<BeanPropertyCompoundScalar> propList;
    private List<CtCompoundProperty> nonScalarProperties;
    
    public BeanPropertyCompoundRoot(final DeployBeanProperty deploy) {
        this.fullBeanName = deploy.getFullBeanName();
        this.name = deploy.getName();
        this.setter = deploy.getSetter();
        this.writeMethod = deploy.getWriteMethod();
        this.propList = new ArrayList<BeanPropertyCompoundScalar>();
        this.propMap = new LinkedHashMap<String, BeanPropertyCompoundScalar>();
    }
    
    public BeanProperty[] getScalarProperties() {
        return this.propList.toArray(new BeanProperty[this.propList.size()]);
    }
    
    public void register(final BeanPropertyCompoundScalar prop) {
        this.propList.add(prop);
        this.propMap.put(prop.getName(), prop);
    }
    
    public BeanPropertyCompoundScalar getCompoundScalarProperty(final String propName) {
        return this.propMap.get(propName);
    }
    
    public List<CtCompoundProperty> getNonScalarProperties() {
        return this.nonScalarProperties;
    }
    
    public void setNonScalarProperties(final List<CtCompoundProperty> nonScalarProperties) {
        this.nonScalarProperties = nonScalarProperties;
    }
    
    public void setRootValue(final Object bean, final Object value) {
        try {
            if (bean instanceof EntityBean) {
                this.setter.set(bean, value);
            }
            else {
                final Object[] args = { value };
                this.writeMethod.invoke(bean, args);
            }
        }
        catch (Exception ex) {
            final String beanType = (bean == null) ? "null" : bean.getClass().getName();
            final String msg = "set " + this.name + " with arg[" + value + "] on [" + this.fullBeanName + "] with type[" + beanType + "] threw error";
            throw new RuntimeException(msg, ex);
        }
    }
    
    public void setRootValueIntercept(final Object bean, final Object value) {
        try {
            if (bean instanceof EntityBean) {
                this.setter.setIntercept(bean, value);
            }
            else {
                final Object[] args = { value };
                this.writeMethod.invoke(bean, args);
            }
        }
        catch (Exception ex) {
            final String beanType = (bean == null) ? "null" : bean.getClass().getName();
            final String msg = "setIntercept " + this.name + " arg[" + value + "] on [" + this.fullBeanName + "] with type[" + beanType + "] threw error";
            throw new RuntimeException(msg, ex);
        }
    }
}
