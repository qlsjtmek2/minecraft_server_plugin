// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.meta;

import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorMap;
import java.util.List;

public class DeployBeanTable
{
    private final Class<?> beanType;
    private String baseTable;
    private List<DeployBeanProperty> idProperties;
    
    public DeployBeanTable(final Class<?> beanType) {
        this.beanType = beanType;
    }
    
    public String getBaseTable() {
        return this.baseTable;
    }
    
    public void setBaseTable(final String baseTable) {
        this.baseTable = baseTable;
    }
    
    public BeanProperty[] createIdProperties(final BeanDescriptorMap owner) {
        final BeanProperty[] props = new BeanProperty[this.idProperties.size()];
        for (int i = 0; i < this.idProperties.size(); ++i) {
            props[i] = this.createProperty(owner, this.idProperties.get(i));
        }
        return props;
    }
    
    private BeanProperty createProperty(final BeanDescriptorMap owner, final DeployBeanProperty prop) {
        if (prop instanceof DeployBeanPropertyAssocOne) {
            return new BeanPropertyAssocOne<Object>(owner, (DeployBeanPropertyAssocOne<?>)prop);
        }
        return new BeanProperty(prop);
    }
    
    public void setIdProperties(final List<DeployBeanProperty> idProperties) {
        this.idProperties = idProperties;
    }
    
    public Class<?> getBeanType() {
        return this.beanType;
    }
}
