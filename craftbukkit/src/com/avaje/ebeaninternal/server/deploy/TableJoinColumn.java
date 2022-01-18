// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.core.InternString;
import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoinColumn;

public class TableJoinColumn
{
    private final String localDbColumn;
    private final String foreignDbColumn;
    private final boolean insertable;
    private final boolean updateable;
    
    public TableJoinColumn(final DeployTableJoinColumn deploy) {
        this.localDbColumn = InternString.intern(deploy.getLocalDbColumn());
        this.foreignDbColumn = InternString.intern(deploy.getForeignDbColumn());
        this.insertable = deploy.isInsertable();
        this.updateable = deploy.isUpdateable();
    }
    
    public String toString() {
        return this.localDbColumn + " = " + this.foreignDbColumn;
    }
    
    public String getForeignDbColumn() {
        return this.foreignDbColumn;
    }
    
    public String getLocalDbColumn() {
        return this.localDbColumn;
    }
    
    public boolean isInsertable() {
        return this.insertable;
    }
    
    public boolean isUpdateable() {
        return this.updateable;
    }
}
