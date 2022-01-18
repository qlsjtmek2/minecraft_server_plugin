// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

public class RawSqlBuilder
{
    public static final String IGNORE_COLUMN = "$$_IGNORE_COLUMN_$$";
    private final RawSql.Sql sql;
    private final RawSql.ColumnMapping columnMapping;
    
    public static RawSqlBuilder unparsed(final String sql) {
        final RawSql.Sql s = new RawSql.Sql(sql);
        return new RawSqlBuilder(s, new RawSql.ColumnMapping());
    }
    
    public static RawSqlBuilder parse(final String sql) {
        final RawSql.Sql sql2 = DRawSqlParser.parse(sql);
        final String select = sql2.getPreFrom();
        final RawSql.ColumnMapping mapping = DRawSqlColumnsParser.parse(select);
        return new RawSqlBuilder(sql2, mapping);
    }
    
    private RawSqlBuilder(final RawSql.Sql sql, final RawSql.ColumnMapping columnMapping) {
        this.sql = sql;
        this.columnMapping = columnMapping;
    }
    
    public RawSqlBuilder columnMapping(final String dbColumn, final String propertyName) {
        this.columnMapping.columnMapping(dbColumn, propertyName);
        return this;
    }
    
    public RawSqlBuilder columnMappingIgnore(final String dbColumn) {
        return this.columnMapping(dbColumn, "$$_IGNORE_COLUMN_$$");
    }
    
    public RawSql create() {
        return new RawSql(this.sql, this.columnMapping.createImmutableCopy());
    }
    
    protected RawSql.Sql getSql() {
        return this.sql;
    }
}
