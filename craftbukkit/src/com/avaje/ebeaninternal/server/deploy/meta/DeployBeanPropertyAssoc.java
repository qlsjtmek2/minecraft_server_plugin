// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.meta;

import com.avaje.ebean.config.ScalarTypeConverter;
import com.avaje.ebeaninternal.server.type.ScalarType;
import com.avaje.ebeaninternal.server.deploy.BeanTable;
import com.avaje.ebeaninternal.server.deploy.BeanCascadeInfo;

public abstract class DeployBeanPropertyAssoc<T> extends DeployBeanProperty
{
    Class<T> targetType;
    BeanCascadeInfo cascadeInfo;
    BeanTable beanTable;
    DeployTableJoin tableJoin;
    boolean isOuterJoin;
    String extraWhere;
    String mappedBy;
    
    public DeployBeanPropertyAssoc(final DeployBeanDescriptor<?> desc, final Class<T> targetType) {
        super(desc, targetType, null, null);
        this.cascadeInfo = new BeanCascadeInfo();
        this.tableJoin = new DeployTableJoin();
        this.isOuterJoin = false;
        this.targetType = targetType;
    }
    
    public boolean isScalar() {
        return false;
    }
    
    public Class<T> getTargetType() {
        return this.targetType;
    }
    
    public boolean isOuterJoin() {
        return this.isOuterJoin;
    }
    
    public void setOuterJoin(final boolean isOuterJoin) {
        this.isOuterJoin = isOuterJoin;
    }
    
    public String getExtraWhere() {
        return this.extraWhere;
    }
    
    public void setExtraWhere(final String extraWhere) {
        this.extraWhere = extraWhere;
    }
    
    public DeployTableJoin getTableJoin() {
        return this.tableJoin;
    }
    
    public BeanTable getBeanTable() {
        return this.beanTable;
    }
    
    public void setBeanTable(final BeanTable beanTable) {
        this.beanTable = beanTable;
        this.getTableJoin().setTable(beanTable.getBaseTable());
    }
    
    public BeanCascadeInfo getCascadeInfo() {
        return this.cascadeInfo;
    }
    
    public String getMappedBy() {
        return this.mappedBy;
    }
    
    public void setMappedBy(final String mappedBy) {
        if (!"".equals(mappedBy)) {
            this.mappedBy = mappedBy;
        }
    }
}
