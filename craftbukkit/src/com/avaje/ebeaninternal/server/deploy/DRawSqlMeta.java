// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.annotation.SqlSelect;

public class DRawSqlMeta
{
    private String name;
    private String tableAlias;
    private String extend;
    private String query;
    private boolean debug;
    private String where;
    private String having;
    private String columnMapping;
    
    public DRawSqlMeta(final SqlSelect sqlSelect) {
        this.debug = sqlSelect.debug();
        this.name = sqlSelect.name();
        this.tableAlias = toNull(sqlSelect.tableAlias());
        this.extend = toNull(sqlSelect.extend());
        this.having = toNull(sqlSelect.having());
        this.where = toNull(sqlSelect.where());
        this.columnMapping = toNull(sqlSelect.columnMapping());
        this.query = toNull(sqlSelect.query());
    }
    
    public DRawSqlMeta(final String name, final String extend, final String query, final boolean debug, final String where, final String having, final String columnMapping) {
        this.name = name;
        this.extend = extend;
        this.query = query;
        this.debug = debug;
        this.having = having;
        this.where = where;
        this.columnMapping = columnMapping;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setTableAlias(final String tableAlias) {
        this.tableAlias = tableAlias;
    }
    
    public String getTableAlias() {
        return this.tableAlias;
    }
    
    public String getExtend() {
        return this.extend;
    }
    
    public void setExtend(final String extend) {
        this.extend = extend;
    }
    
    public String getQuery() {
        return this.query;
    }
    
    public void setQuery(final String query) {
        this.query = query;
    }
    
    public boolean isDebug() {
        return this.debug;
    }
    
    public void setDebug(final boolean debug) {
        this.debug = debug;
    }
    
    public String getWhere() {
        return this.where;
    }
    
    public void setWhere(final String where) {
        this.where = where;
    }
    
    public String getHaving() {
        return this.having;
    }
    
    public void setHaving(final String having) {
        this.having = having;
    }
    
    public String getColumnMapping() {
        return this.columnMapping;
    }
    
    public void setColumnMapping(final String columnMapping) {
        this.columnMapping = columnMapping;
    }
    
    public void extend(final DRawSqlMeta parentQuery) {
        this.extendQuery(parentQuery.getQuery());
        this.extendColumnMapping(parentQuery.getColumnMapping());
    }
    
    private void extendQuery(final String parentSql) {
        if (this.query == null) {
            this.query = parentSql;
        }
        else {
            this.query = parentSql + " " + this.query;
        }
    }
    
    private void extendColumnMapping(final String parentColumnMapping) {
        if (this.columnMapping == null) {
            this.columnMapping = parentColumnMapping;
        }
    }
    
    private static String toNull(final String s) {
        if (s != null && s.equals("")) {
            return null;
        }
        return s;
    }
}
