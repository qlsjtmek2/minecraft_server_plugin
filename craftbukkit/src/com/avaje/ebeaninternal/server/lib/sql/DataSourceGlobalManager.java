// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.sql;

import com.avaje.ebean.config.DataSourceConfig;
import java.util.List;

public final class DataSourceGlobalManager
{
    private static final DataSourceManager manager;
    
    public static boolean isShuttingDown() {
        return DataSourceGlobalManager.manager.isShuttingDown();
    }
    
    public static void shutdown() {
        DataSourceGlobalManager.manager.shutdown();
    }
    
    public static List<DataSourcePool> getPools() {
        return DataSourceGlobalManager.manager.getPools();
    }
    
    public static DataSourcePool getDataSource(final String name) {
        return DataSourceGlobalManager.manager.getDataSource(name);
    }
    
    public static DataSourcePool getDataSource(final String name, final DataSourceConfig dsConfig) {
        return DataSourceGlobalManager.manager.getDataSource(name, dsConfig);
    }
    
    static {
        manager = new DataSourceManager();
    }
}
