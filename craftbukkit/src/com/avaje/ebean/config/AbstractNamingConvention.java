// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import java.util.logging.Logger;

public abstract class AbstractNamingConvention implements NamingConvention
{
    private static final Logger logger;
    public static final String DEFAULT_SEQ_FORMAT = "{table}_seq";
    public static final String TABLE_PKCOLUMN_SEQ_FORMAT = "{table}_{column}_seq";
    private String catalog;
    private String schema;
    private String sequenceFormat;
    protected DatabasePlatform databasePlatform;
    protected int maxConstraintNameLength;
    protected int rhsPrefixLength;
    protected boolean useForeignKeyPrefix;
    
    public AbstractNamingConvention(final String sequenceFormat, final boolean useForeignKeyPrefix) {
        this.rhsPrefixLength = 3;
        this.useForeignKeyPrefix = true;
        this.sequenceFormat = sequenceFormat;
        this.useForeignKeyPrefix = useForeignKeyPrefix;
    }
    
    public AbstractNamingConvention(final String sequenceFormat) {
        this.rhsPrefixLength = 3;
        this.useForeignKeyPrefix = true;
        this.sequenceFormat = sequenceFormat;
    }
    
    public AbstractNamingConvention() {
        this("{table}_seq");
    }
    
    public void setDatabasePlatform(final DatabasePlatform databasePlatform) {
        this.databasePlatform = databasePlatform;
        this.maxConstraintNameLength = databasePlatform.getDbDdlSyntax().getMaxConstraintNameLength();
        AbstractNamingConvention.logger.finer("Using maxConstraintNameLength of " + this.maxConstraintNameLength);
    }
    
    public String getSequenceName(final String tableName, String pkColumn) {
        final String s = this.sequenceFormat.replace("{table}", tableName);
        if (pkColumn == null) {
            pkColumn = "";
        }
        return s.replace("{column}", pkColumn);
    }
    
    public String getCatalog() {
        return this.catalog;
    }
    
    public void setCatalog(final String catalog) {
        this.catalog = catalog;
    }
    
    public String getSchema() {
        return this.schema;
    }
    
    public void setSchema(final String schema) {
        this.schema = schema;
    }
    
    public String getSequenceFormat() {
        return this.sequenceFormat;
    }
    
    public void setSequenceFormat(final String sequenceFormat) {
        this.sequenceFormat = sequenceFormat;
    }
    
    public boolean isUseForeignKeyPrefix() {
        return this.useForeignKeyPrefix;
    }
    
    public void setUseForeignKeyPrefix(final boolean useForeignKeyPrefix) {
        this.useForeignKeyPrefix = useForeignKeyPrefix;
    }
    
    protected abstract TableName getTableNameByConvention(final Class<?> p0);
    
    public TableName getTableName(final Class<?> beanClass) {
        TableName tableName = this.getTableNameFromAnnotation(beanClass);
        if (tableName == null) {
            final Class<?> supCls = beanClass.getSuperclass();
            final Inheritance inheritance = supCls.getAnnotation(Inheritance.class);
            if (inheritance != null) {
                return this.getTableName(supCls);
            }
            tableName = this.getTableNameByConvention(beanClass);
        }
        String catalog = tableName.getCatalog();
        if (this.isEmpty(catalog)) {
            catalog = this.getCatalog();
        }
        String schema = tableName.getSchema();
        if (this.isEmpty(schema)) {
            schema = this.getSchema();
        }
        return new TableName(catalog, schema, tableName.getName());
    }
    
    public TableName getM2MJoinTableName(final TableName lhsTable, final TableName rhsTable) {
        final StringBuilder buffer = new StringBuilder();
        buffer.append(lhsTable.getName());
        buffer.append("_");
        String rhsTableName = rhsTable.getName();
        if (rhsTableName.indexOf(95) < this.rhsPrefixLength) {
            rhsTableName = rhsTableName.substring(rhsTableName.indexOf(95) + 1);
        }
        buffer.append(rhsTableName);
        final int maxConstraintNameLength = this.databasePlatform.getDbDdlSyntax().getMaxConstraintNameLength();
        if (buffer.length() > maxConstraintNameLength) {
            buffer.setLength(maxConstraintNameLength);
        }
        return new TableName(lhsTable.getCatalog(), lhsTable.getSchema(), buffer.toString());
    }
    
    protected TableName getTableNameFromAnnotation(final Class<?> beanClass) {
        final Table t = this.findTableAnnotation(beanClass);
        if (t != null && !this.isEmpty(t.name())) {
            return new TableName(this.quoteIdentifiers(t.catalog()), this.quoteIdentifiers(t.schema()), this.quoteIdentifiers(t.name()));
        }
        return null;
    }
    
    protected Table findTableAnnotation(final Class<?> cls) {
        if (cls.equals(Object.class)) {
            return null;
        }
        final Table table = cls.getAnnotation(Table.class);
        if (table != null) {
            return table;
        }
        return this.findTableAnnotation(cls.getSuperclass());
    }
    
    protected String quoteIdentifiers(final String s) {
        return this.databasePlatform.convertQuotedIdentifiers(s);
    }
    
    protected boolean isEmpty(final String s) {
        return s == null || s.trim().length() == 0;
    }
    
    static {
        logger = Logger.getLogger(AbstractNamingConvention.class.getName());
    }
}
