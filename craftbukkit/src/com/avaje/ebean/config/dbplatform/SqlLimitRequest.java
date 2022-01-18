// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config.dbplatform;

public interface SqlLimitRequest
{
    boolean isDistinct();
    
    int getFirstRow();
    
    int getMaxRows();
    
    String getDbSql();
    
    String getDbOrderBy();
}
