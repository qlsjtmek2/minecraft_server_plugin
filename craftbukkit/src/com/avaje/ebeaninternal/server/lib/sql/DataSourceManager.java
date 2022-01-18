// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.sql;

import com.avaje.ebean.config.DataSourceConfig;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import com.avaje.ebeaninternal.api.ClassUtil;
import java.util.logging.Level;
import com.avaje.ebeaninternal.server.lib.BackgroundThread;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebeaninternal.server.lib.BackgroundRunnable;
import java.util.Hashtable;
import java.util.logging.Logger;

public class DataSourceManager implements DataSourceNotify
{
    private static final Logger logger;
    private final DataSourceAlertListener alertlistener;
    private final Hashtable<String, DataSourcePool> dsMap;
    private final Object monitor;
    private final BackgroundRunnable dbChecker;
    private final int dbUpFreqInSecs;
    private final int dbDownFreqInSecs;
    private boolean shuttingDown;
    
    public DataSourceManager() {
        this.dsMap = new Hashtable<String, DataSourcePool>();
        this.monitor = new Object();
        this.alertlistener = this.createAlertListener();
        this.dbUpFreqInSecs = GlobalProperties.getInt("datasource.heartbeatfreq", 30);
        this.dbDownFreqInSecs = GlobalProperties.getInt("datasource.deadbeatfreq", 10);
        this.dbChecker = new BackgroundRunnable(new Checker(), this.dbUpFreqInSecs);
        try {
            BackgroundThread.add(this.dbChecker);
        }
        catch (Exception e) {
            DataSourceManager.logger.log(Level.SEVERE, null, e);
        }
    }
    
    private DataSourceAlertListener createAlertListener() throws DataSourceException {
        final String alertCN = GlobalProperties.get("datasource.alert.class", null);
        if (alertCN == null) {
            return new SimpleAlerter();
        }
        try {
            return (DataSourceAlertListener)ClassUtil.newInstance(alertCN, this.getClass());
        }
        catch (Exception ex) {
            throw new DataSourceException(ex);
        }
    }
    
    public void notifyDataSourceUp(final String dataSourceName) {
        this.dbChecker.setFreqInSecs(this.dbUpFreqInSecs);
        if (this.alertlistener != null) {
            this.alertlistener.dataSourceUp(dataSourceName);
        }
    }
    
    public void notifyDataSourceDown(final String dataSourceName) {
        this.dbChecker.setFreqInSecs(this.dbDownFreqInSecs);
        if (this.alertlistener != null) {
            this.alertlistener.dataSourceDown(dataSourceName);
        }
    }
    
    public void notifyWarning(final String subject, final String msg) {
        if (this.alertlistener != null) {
            this.alertlistener.warning(subject, msg);
        }
    }
    
    public boolean isShuttingDown() {
        synchronized (this.monitor) {
            return this.shuttingDown;
        }
    }
    
    public void shutdown() {
        synchronized (this.monitor) {
            this.shuttingDown = true;
            final Iterator<DataSourcePool> it = this.dsMap.values().iterator();
            while (it.hasNext()) {
                try {
                    final DataSourcePool ds = it.next();
                    ds.shutdown();
                }
                catch (DataSourceException e) {
                    DataSourceManager.logger.log(Level.SEVERE, null, e);
                }
            }
        }
    }
    
    public List<DataSourcePool> getPools() {
        synchronized (this.monitor) {
            final ArrayList<DataSourcePool> list = new ArrayList<DataSourcePool>();
            list.addAll(this.dsMap.values());
            return list;
        }
    }
    
    public DataSourcePool getDataSource(final String name) {
        return this.getDataSource(name, null);
    }
    
    public DataSourcePool getDataSource(final String name, DataSourceConfig dsConfig) {
        if (name == null) {
            throw new IllegalArgumentException("name not defined");
        }
        synchronized (this.monitor) {
            DataSourcePool pool = this.dsMap.get(name);
            if (pool == null) {
                if (dsConfig == null) {
                    dsConfig = new DataSourceConfig();
                    dsConfig.loadSettings(name);
                }
                pool = new DataSourcePool(this, name, dsConfig);
                this.dsMap.put(name, pool);
            }
            return pool;
        }
    }
    
    private void checkDataSource() {
        synchronized (this.monitor) {
            if (!this.isShuttingDown()) {
                for (final DataSourcePool ds : this.dsMap.values()) {
                    ds.checkDataSource();
                }
            }
        }
    }
    
    static {
        logger = Logger.getLogger(DataSourceManager.class.getName());
    }
    
    private final class Checker implements Runnable
    {
        public void run() {
            DataSourceManager.this.checkDataSource();
        }
    }
}
