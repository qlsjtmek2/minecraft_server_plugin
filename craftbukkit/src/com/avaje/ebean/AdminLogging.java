// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

public interface AdminLogging
{
    void setLogLevel(final LogLevel p0);
    
    LogLevel getLogLevel();
    
    boolean isDebugGeneratedSql();
    
    void setDebugGeneratedSql(final boolean p0);
    
    boolean isDebugLazyLoad();
    
    void setDebugLazyLoad(final boolean p0);
}
