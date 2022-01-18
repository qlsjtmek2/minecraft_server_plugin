// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

public class SqlLimitResponse
{
    final String sql;
    final boolean includesRowNumberColumn;
    
    public SqlLimitResponse(final String sql, final boolean includesRowNumberColumn) {
        this.sql = sql;
        this.includesRowNumberColumn = includesRowNumberColumn;
    }
    
    public String getSql() {
        return this.sql;
    }
    
    public boolean isIncludesRowNumberColumn() {
        return this.includesRowNumberColumn;
    }
}
