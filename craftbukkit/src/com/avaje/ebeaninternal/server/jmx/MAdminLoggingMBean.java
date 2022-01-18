// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.jmx;

import com.avaje.ebean.LogLevel;

public interface MAdminLoggingMBean
{
    LogLevel getLogLevel();
    
    void setLogLevel(final LogLevel p0);
    
    boolean isDebugGeneratedSql();
    
    void setDebugGeneratedSql(final boolean p0);
    
    boolean isDebugLazyLoad();
    
    void setDebugLazyLoad(final boolean p0);
}
