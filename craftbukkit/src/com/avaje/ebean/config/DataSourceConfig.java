// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

import com.avaje.ebeaninternal.server.lib.sql.TransactionIsolation;

public class DataSourceConfig
{
    private String url;
    private String username;
    private String password;
    private String driver;
    private int minConnections;
    private int maxConnections;
    private int isolationLevel;
    private String heartbeatSql;
    private boolean captureStackTrace;
    private int maxStackTraceSize;
    private int leakTimeMinutes;
    private int maxInactiveTimeSecs;
    private int pstmtCacheSize;
    private int cstmtCacheSize;
    private int waitTimeoutMillis;
    private String poolListener;
    private boolean offline;
    
    public DataSourceConfig() {
        this.minConnections = 2;
        this.maxConnections = 20;
        this.isolationLevel = 2;
        this.maxStackTraceSize = 5;
        this.leakTimeMinutes = 30;
        this.maxInactiveTimeSecs = 900;
        this.pstmtCacheSize = 20;
        this.cstmtCacheSize = 20;
        this.waitTimeoutMillis = 1000;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(final String url) {
        this.url = url;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(final String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(final String password) {
        this.password = password;
    }
    
    public String getDriver() {
        return this.driver;
    }
    
    public void setDriver(final String driver) {
        this.driver = driver;
    }
    
    public int getIsolationLevel() {
        return this.isolationLevel;
    }
    
    public void setIsolationLevel(final int isolationLevel) {
        this.isolationLevel = isolationLevel;
    }
    
    public int getMinConnections() {
        return this.minConnections;
    }
    
    public void setMinConnections(final int minConnections) {
        this.minConnections = minConnections;
    }
    
    public int getMaxConnections() {
        return this.maxConnections;
    }
    
    public void setMaxConnections(final int maxConnections) {
        this.maxConnections = maxConnections;
    }
    
    public String getHeartbeatSql() {
        return this.heartbeatSql;
    }
    
    public void setHeartbeatSql(final String heartbeatSql) {
        this.heartbeatSql = heartbeatSql;
    }
    
    public boolean isCaptureStackTrace() {
        return this.captureStackTrace;
    }
    
    public void setCaptureStackTrace(final boolean captureStackTrace) {
        this.captureStackTrace = captureStackTrace;
    }
    
    public int getMaxStackTraceSize() {
        return this.maxStackTraceSize;
    }
    
    public void setMaxStackTraceSize(final int maxStackTraceSize) {
        this.maxStackTraceSize = maxStackTraceSize;
    }
    
    public int getLeakTimeMinutes() {
        return this.leakTimeMinutes;
    }
    
    public void setLeakTimeMinutes(final int leakTimeMinutes) {
        this.leakTimeMinutes = leakTimeMinutes;
    }
    
    public int getPstmtCacheSize() {
        return this.pstmtCacheSize;
    }
    
    public void setPstmtCacheSize(final int pstmtCacheSize) {
        this.pstmtCacheSize = pstmtCacheSize;
    }
    
    public int getCstmtCacheSize() {
        return this.cstmtCacheSize;
    }
    
    public void setCstmtCacheSize(final int cstmtCacheSize) {
        this.cstmtCacheSize = cstmtCacheSize;
    }
    
    public int getWaitTimeoutMillis() {
        return this.waitTimeoutMillis;
    }
    
    public void setWaitTimeoutMillis(final int waitTimeoutMillis) {
        this.waitTimeoutMillis = waitTimeoutMillis;
    }
    
    public int getMaxInactiveTimeSecs() {
        return this.maxInactiveTimeSecs;
    }
    
    public void setMaxInactiveTimeSecs(final int maxInactiveTimeSecs) {
        this.maxInactiveTimeSecs = maxInactiveTimeSecs;
    }
    
    public String getPoolListener() {
        return this.poolListener;
    }
    
    public void setPoolListener(final String poolListener) {
        this.poolListener = poolListener;
    }
    
    public boolean isOffline() {
        return this.offline;
    }
    
    public void setOffline(final boolean offline) {
        this.offline = offline;
    }
    
    public void loadSettings(final String serverName) {
        final String prefix = "datasource." + serverName + ".";
        this.username = GlobalProperties.get(prefix + "username", null);
        this.password = GlobalProperties.get(prefix + "password", null);
        String v = GlobalProperties.get(prefix + "databaseDriver", null);
        this.driver = GlobalProperties.get(prefix + "driver", v);
        v = GlobalProperties.get(prefix + "databaseUrl", null);
        this.url = GlobalProperties.get(prefix + "url", v);
        this.captureStackTrace = GlobalProperties.getBoolean(prefix + "captureStackTrace", false);
        this.maxStackTraceSize = GlobalProperties.getInt(prefix + "maxStackTraceSize", 5);
        this.leakTimeMinutes = GlobalProperties.getInt(prefix + "leakTimeMinutes", 30);
        this.maxInactiveTimeSecs = GlobalProperties.getInt(prefix + "maxInactiveTimeSecs", 900);
        this.minConnections = GlobalProperties.getInt(prefix + "minConnections", 0);
        this.maxConnections = GlobalProperties.getInt(prefix + "maxConnections", 20);
        this.pstmtCacheSize = GlobalProperties.getInt(prefix + "pstmtCacheSize", 20);
        this.cstmtCacheSize = GlobalProperties.getInt(prefix + "cstmtCacheSize", 20);
        this.waitTimeoutMillis = GlobalProperties.getInt(prefix + "waitTimeout", 1000);
        this.heartbeatSql = GlobalProperties.get(prefix + "heartbeatSql", null);
        this.poolListener = GlobalProperties.get(prefix + "poolListener", null);
        this.offline = GlobalProperties.getBoolean(prefix + "offline", false);
        final String isoLevel = GlobalProperties.get(prefix + "isolationlevel", "READ_COMMITTED");
        this.isolationLevel = TransactionIsolation.getLevel(isoLevel);
    }
}
