// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

import com.avaje.ebean.config.dbplatform.DatabasePlatform;

public interface NamingConvention
{
    void setDatabasePlatform(final DatabasePlatform p0);
    
    TableName getTableName(final Class<?> p0);
    
    TableName getM2MJoinTableName(final TableName p0, final TableName p1);
    
    String getColumnFromProperty(final Class<?> p0, final String p1);
    
    String getPropertyFromColumn(final Class<?> p0, final String p1);
    
    String getSequenceName(final String p0, final String p1);
    
    boolean isUseForeignKeyPrefix();
}
