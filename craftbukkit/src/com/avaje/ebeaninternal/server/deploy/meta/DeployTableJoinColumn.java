// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.meta;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanTable;
import javax.persistence.JoinColumn;

public class DeployTableJoinColumn
{
    String localDbColumn;
    String foreignDbColumn;
    boolean insertable;
    boolean updateable;
    
    public DeployTableJoinColumn(final String localDbColumn, final String foreignDbColumn) {
        this(localDbColumn, foreignDbColumn, true, true);
    }
    
    public DeployTableJoinColumn(final String localDbColumn, final String foreignDbColumn, final boolean insertable, final boolean updateable) {
        this.localDbColumn = this.nullEmptyString(localDbColumn);
        this.foreignDbColumn = this.nullEmptyString(foreignDbColumn);
        this.insertable = insertable;
        this.updateable = updateable;
    }
    
    public DeployTableJoinColumn(final boolean order, final JoinColumn jc, final BeanTable beanTable) {
        this(jc.referencedColumnName(), jc.name(), jc.insertable(), jc.updatable());
        this.setReferencedColumn(beanTable);
        if (!order) {
            this.reverse();
        }
    }
    
    private void setReferencedColumn(final BeanTable beanTable) {
        if (this.localDbColumn == null) {
            final BeanProperty[] idProperties = beanTable.getIdProperties();
            if (idProperties.length == 1) {
                this.localDbColumn = idProperties[0].getDbColumn();
            }
        }
    }
    
    public DeployTableJoinColumn reverse() {
        final String temp = this.localDbColumn;
        this.localDbColumn = this.foreignDbColumn;
        this.foreignDbColumn = temp;
        return this;
    }
    
    private String nullEmptyString(final String s) {
        if ("".equals(s)) {
            return null;
        }
        return s;
    }
    
    public DeployTableJoinColumn copy(final boolean reverse) {
        if (reverse) {
            return new DeployTableJoinColumn(this.foreignDbColumn, this.localDbColumn, this.insertable, this.updateable);
        }
        return new DeployTableJoinColumn(this.localDbColumn, this.foreignDbColumn, this.insertable, this.updateable);
    }
    
    public String toString() {
        return this.localDbColumn + " = " + this.foreignDbColumn;
    }
    
    public boolean hasNullColumn() {
        return this.localDbColumn == null || this.foreignDbColumn == null;
    }
    
    public String getNonNullColumn() {
        if (this.localDbColumn == null && this.foreignDbColumn == null) {
            throw new IllegalStateException("expecting only one null column?");
        }
        if (this.localDbColumn != null && this.foreignDbColumn != null) {
            throw new IllegalStateException("expecting one null column?");
        }
        if (this.localDbColumn != null) {
            return this.localDbColumn;
        }
        return this.foreignDbColumn;
    }
    
    public boolean isInsertable() {
        return this.insertable;
    }
    
    public boolean isUpdateable() {
        return this.updateable;
    }
    
    public String getForeignDbColumn() {
        return this.foreignDbColumn;
    }
    
    public void setForeignDbColumn(final String foreignDbColumn) {
        this.foreignDbColumn = foreignDbColumn;
    }
    
    public String getLocalDbColumn() {
        return this.localDbColumn;
    }
    
    public void setLocalDbColumn(final String localDbColumn) {
        this.localDbColumn = localDbColumn;
    }
}
