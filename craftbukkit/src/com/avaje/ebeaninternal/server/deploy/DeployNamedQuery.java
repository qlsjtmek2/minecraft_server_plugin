// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import javax.persistence.NamedQuery;
import com.avaje.ebean.RawSql;
import javax.persistence.QueryHint;

public class DeployNamedQuery
{
    private final String name;
    private final String query;
    private final QueryHint[] hints;
    private final DRawSqlSelect sqlSelect;
    private final RawSql rawSql;
    
    public DeployNamedQuery(final NamedQuery namedQuery) {
        this.name = namedQuery.name();
        this.query = namedQuery.query();
        this.hints = namedQuery.hints();
        this.sqlSelect = null;
        this.rawSql = null;
    }
    
    public DeployNamedQuery(final String name, final String query, final QueryHint[] hints) {
        this.name = name;
        this.query = query;
        this.hints = hints;
        this.sqlSelect = null;
        this.rawSql = null;
    }
    
    public DeployNamedQuery(final String name, final RawSql rawSql) {
        this.name = name;
        this.query = null;
        this.hints = null;
        this.sqlSelect = null;
        this.rawSql = rawSql;
    }
    
    public DeployNamedQuery(final DRawSqlSelect sqlSelect) {
        this.name = sqlSelect.getName();
        this.query = null;
        this.hints = null;
        this.sqlSelect = sqlSelect;
        this.rawSql = null;
    }
    
    public boolean isRawSql() {
        return this.rawSql != null;
    }
    
    public boolean isSqlSelect() {
        return this.sqlSelect != null;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getQuery() {
        return this.query;
    }
    
    public QueryHint[] getHints() {
        return this.hints;
    }
    
    public RawSql getRawSql() {
        return this.rawSql;
    }
    
    public DRawSqlSelect getSqlSelect() {
        return this.sqlSelect;
    }
}
