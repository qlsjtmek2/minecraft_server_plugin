// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

public class MsSqlServer2005Platform extends DatabasePlatform
{
    public MsSqlServer2005Platform() {
        this.name = "mssqlserver2005";
        this.dbIdentity.setIdType(IdType.IDENTITY);
        this.dbIdentity.setSupportsGetGeneratedKeys(true);
        this.dbIdentity.setSupportsIdentity(true);
        this.openQuote = "[";
        this.closeQuote = "]";
        this.dbTypeMap.put(16, new DbType("bit default 0"));
        this.dbTypeMap.put(-5, new DbType("numeric", 19));
        this.dbTypeMap.put(7, new DbType("float(16)"));
        this.dbTypeMap.put(8, new DbType("float(32)"));
        this.dbTypeMap.put(-6, new DbType("smallint"));
        this.dbTypeMap.put(3, new DbType("numeric", 28));
        this.dbTypeMap.put(2004, new DbType("image"));
        this.dbTypeMap.put(2005, new DbType("text"));
        this.dbTypeMap.put(-4, new DbType("image"));
        this.dbTypeMap.put(-1, new DbType("text"));
        this.dbTypeMap.put(91, new DbType("datetime"));
        this.dbTypeMap.put(92, new DbType("datetime"));
        this.dbTypeMap.put(93, new DbType("datetime"));
    }
}
