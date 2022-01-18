// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

import java.util.HashMap;
import java.util.Map;

public class DbTypeMap
{
    private final Map<Integer, DbType> typeMap;
    
    public DbTypeMap() {
        this.typeMap = new HashMap<Integer, DbType>();
        this.loadDefaults();
    }
    
    private void loadDefaults() {
        this.put(16, new DbType("boolean"));
        this.put(-7, new DbType("bit"));
        this.put(4, new DbType("integer"));
        this.put(-5, new DbType("bigint"));
        this.put(7, new DbType("float"));
        this.put(8, new DbType("double"));
        this.put(5, new DbType("smallint"));
        this.put(-6, new DbType("tinyint"));
        this.put(3, new DbType("decimal", 38));
        this.put(12, new DbType("varchar", 255));
        this.put(1, new DbType("char", 1));
        this.put(2004, new DbType("blob"));
        this.put(2005, new DbType("clob"));
        this.put(-4, new DbType("longvarbinary"));
        this.put(-1, new DbType("lonvarchar"));
        this.put(-3, new DbType("varbinary", 255));
        this.put(-2, new DbType("binary", 255));
        this.put(91, new DbType("date"));
        this.put(92, new DbType("time"));
        this.put(93, new DbType("timestamp"));
    }
    
    public void put(final int jdbcType, final DbType dbType) {
        this.typeMap.put(jdbcType, dbType);
    }
    
    public DbType get(final int jdbcType) {
        final DbType dbType = this.typeMap.get(jdbcType);
        if (dbType == null) {
            final String m = "No DB type for JDBC type " + jdbcType;
            throw new RuntimeException(m);
        }
        return dbType;
    }
}
