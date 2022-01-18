// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.meta;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public class DeployBeanPropertyAssocOne<T> extends DeployBeanPropertyAssoc<T>
{
    boolean oneToOne;
    boolean oneToOneExported;
    boolean importedPrimaryKey;
    DeployBeanEmbedded deployEmbedded;
    
    public DeployBeanPropertyAssocOne(final DeployBeanDescriptor<?> desc, final Class<T> targetType) {
        super(desc, targetType);
    }
    
    public DeployBeanEmbedded getDeployEmbedded() {
        if (this.deployEmbedded == null) {
            this.deployEmbedded = new DeployBeanEmbedded();
        }
        return this.deployEmbedded;
    }
    
    public String getDbColumn() {
        final DeployTableJoinColumn[] columns = this.tableJoin.columns();
        if (columns.length == 1) {
            return columns[0].getLocalDbColumn();
        }
        return super.getDbColumn();
    }
    
    public String getElPlaceHolder(final BeanDescriptor.EntityType et) {
        return super.getElPlaceHolder(et);
    }
    
    public boolean isOneToOne() {
        return this.oneToOne;
    }
    
    public void setOneToOne(final boolean oneToOne) {
        this.oneToOne = oneToOne;
    }
    
    public boolean isOneToOneExported() {
        return this.oneToOneExported;
    }
    
    public void setOneToOneExported(final boolean oneToOneExported) {
        this.oneToOneExported = oneToOneExported;
    }
    
    public boolean isImportedPrimaryKey() {
        return this.importedPrimaryKey;
    }
    
    public void setImportedPrimaryKey(final boolean importedPrimaryKey) {
        this.importedPrimaryKey = importedPrimaryKey;
    }
}
