// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.querydefn;

import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.config.dbplatform.SqlLimitRequest;

public class OrmQueryLimitRequest implements SqlLimitRequest
{
    final SpiQuery<?> ormQuery;
    final String sql;
    final String sqlOrderBy;
    
    public OrmQueryLimitRequest(final String sql, final String sqlOrderBy, final SpiQuery<?> ormQuery) {
        this.sql = sql;
        this.sqlOrderBy = sqlOrderBy;
        this.ormQuery = ormQuery;
    }
    
    public String getDbOrderBy() {
        return this.sqlOrderBy;
    }
    
    public String getDbSql() {
        return this.sql;
    }
    
    public int getFirstRow() {
        return this.ormQuery.getFirstRow();
    }
    
    public int getMaxRows() {
        return this.ormQuery.getMaxRows();
    }
    
    public boolean isDistinct() {
        return this.ormQuery.isDistinct();
    }
}
