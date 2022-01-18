// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.annotation.NamedUpdate;

public class DeployNamedUpdate
{
    private final String name;
    private final String updateStatement;
    private final boolean notifyCache;
    private String sqlUpdateStatement;
    
    public DeployNamedUpdate(final NamedUpdate update) {
        this.name = update.name();
        this.updateStatement = update.update();
        this.notifyCache = update.notifyCache();
    }
    
    public void initialise(final DeployUpdateParser parser) {
        this.sqlUpdateStatement = parser.parse(this.updateStatement);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getSqlUpdateStatement() {
        return this.sqlUpdateStatement;
    }
    
    public boolean isNotifyCache() {
        return this.notifyCache;
    }
}
