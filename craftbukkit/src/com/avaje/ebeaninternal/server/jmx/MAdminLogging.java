// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.jmx;

import com.avaje.ebean.LogLevel;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.server.transaction.TransactionManager;
import com.avaje.ebean.AdminLogging;

public class MAdminLogging implements MAdminLoggingMBean, AdminLogging
{
    private final TransactionManager transactionManager;
    private boolean debugSql;
    private boolean debugLazyLoad;
    
    public MAdminLogging(final ServerConfig serverConfig, final TransactionManager txManager) {
        this.transactionManager = txManager;
        this.debugSql = serverConfig.isDebugSql();
        this.debugLazyLoad = serverConfig.isDebugLazyLoad();
    }
    
    public void setLogLevel(final LogLevel logLevel) {
        this.transactionManager.setTransactionLogLevel(logLevel);
    }
    
    public LogLevel getLogLevel() {
        return this.transactionManager.getTransactionLogLevel();
    }
    
    public boolean isDebugGeneratedSql() {
        return this.debugSql;
    }
    
    public void setDebugGeneratedSql(final boolean debugSql) {
        this.debugSql = debugSql;
    }
    
    public boolean isDebugLazyLoad() {
        return this.debugLazyLoad;
    }
    
    public void setDebugLazyLoad(final boolean debugLazyLoad) {
        this.debugLazyLoad = debugLazyLoad;
    }
}
