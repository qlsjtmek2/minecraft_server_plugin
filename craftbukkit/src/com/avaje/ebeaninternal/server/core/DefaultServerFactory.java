// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import java.sql.Connection;
import java.util.logging.Level;
import java.sql.SQLException;
import com.avaje.ebean.config.DataSourceConfig;
import com.avaje.ebeaninternal.server.lib.sql.DataSourceGlobalManager;
import javax.persistence.PersistenceException;
import com.avaje.ebean.config.NamingConvention;
import com.avaje.ebean.config.UnderscoreNamingConvention;
import com.avaje.ebean.cache.ServerCacheFactory;
import com.avaje.ebeaninternal.server.cache.DefaultServerCacheManager;
import com.avaje.ebeaninternal.server.cache.DefaultServerCacheFactory;
import com.avaje.ebean.cache.ServerCacheOptions;
import com.avaje.ebeaninternal.server.jdbc.StandardPstmtDelegate;
import com.avaje.ebeaninternal.server.lib.sql.DataSourcePool;
import javax.sql.DataSource;
import com.avaje.ebean.cache.ServerCacheManager;
import com.avaje.ebean.config.PstmtDelegate;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import java.util.TimerTask;
import java.util.Timer;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebeaninternal.server.jdbc.OraclePstmtBatch;
import com.avaje.ebeaninternal.server.lib.thread.ThreadPool;
import com.avaje.ebeaninternal.server.lib.thread.ThreadPoolManager;
import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import java.util.ArrayList;
import java.util.List;
import com.avaje.ebeaninternal.server.lib.ShutdownManager;
import com.avaje.ebean.config.GlobalProperties;
import java.util.concurrent.atomic.AtomicInteger;
import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import java.util.logging.Logger;
import com.avaje.ebean.common.BootupEbeanManager;

public class DefaultServerFactory implements BootupEbeanManager
{
    private static final Logger logger;
    private final ClusterManager clusterManager;
    private final JndiDataSourceLookup jndiDataSourceFactory;
    private final BootupClassPathSearch bootupClassSearch;
    private final AtomicInteger serverId;
    private final XmlConfigLoader xmlConfigLoader;
    private final XmlConfig xmlConfig;
    
    public DefaultServerFactory() {
        this.serverId = new AtomicInteger(1);
        this.clusterManager = new ClusterManager();
        this.jndiDataSourceFactory = new JndiDataSourceLookup();
        final List<String> packages = this.getSearchJarsPackages(GlobalProperties.get("ebean.search.packages", null));
        final List<String> jars = this.getSearchJarsPackages(GlobalProperties.get("ebean.search.jars", null));
        this.bootupClassSearch = new BootupClassPathSearch(null, packages, jars);
        this.xmlConfigLoader = new XmlConfigLoader(null);
        this.xmlConfig = this.xmlConfigLoader.load();
        ShutdownManager.registerServerFactory(this);
    }
    
    private List<String> getSearchJarsPackages(final String searchPackages) {
        final List<String> hitList = new ArrayList<String>();
        if (searchPackages != null) {
            final String[] entries = searchPackages.split("[ ,;]");
            for (int i = 0; i < entries.length; ++i) {
                hitList.add(entries[i].trim());
            }
        }
        return hitList;
    }
    
    public void shutdown() {
        this.clusterManager.shutdown();
    }
    
    public SpiEbeanServer createServer(final String name) {
        final ConfigBuilder b = new ConfigBuilder();
        final ServerConfig config = b.build(name);
        return this.createServer(config);
    }
    
    private BackgroundExecutor createBackgroundExecutor(final ServerConfig serverConfig, final int uniqueServerId) {
        final String namePrefix = "Ebean-" + serverConfig.getName();
        final int schedulePoolSize = GlobalProperties.getInt("backgroundExecutor.schedulePoolsize", 1);
        final int minPoolSize = GlobalProperties.getInt("backgroundExecutor.minPoolSize", 1);
        final int poolSize = GlobalProperties.getInt("backgroundExecutor.poolsize", 20);
        final int maxPoolSize = GlobalProperties.getInt("backgroundExecutor.maxPoolSize", poolSize);
        final int idleSecs = GlobalProperties.getInt("backgroundExecutor.idlesecs", 60);
        final int shutdownSecs = GlobalProperties.getInt("backgroundExecutor.shutdownSecs", 30);
        final boolean useTrad = GlobalProperties.getBoolean("backgroundExecutor.traditional", true);
        if (useTrad) {
            final ThreadPool pool = ThreadPoolManager.getThreadPool(namePrefix);
            pool.setMinSize(minPoolSize);
            pool.setMaxSize(maxPoolSize);
            pool.setMaxIdleTime(idleSecs * 1000);
            return new TraditionalBackgroundExecutor(pool, schedulePoolSize, shutdownSecs, namePrefix);
        }
        return new DefaultBackgroundExecutor(poolSize, schedulePoolSize, idleSecs, shutdownSecs, namePrefix);
    }
    
    public SpiEbeanServer createServer(final ServerConfig serverConfig) {
        synchronized (this) {
            this.setNamingConvention(serverConfig);
            final BootupClasses bootupClasses = this.getBootupClasses(serverConfig);
            this.setDataSource(serverConfig);
            final boolean online = this.checkDataSource(serverConfig);
            this.setDatabasePlatform(serverConfig);
            if (serverConfig.getDbEncrypt() != null) {
                serverConfig.getDatabasePlatform().setDbEncrypt(serverConfig.getDbEncrypt());
            }
            final DatabasePlatform dbPlatform = serverConfig.getDatabasePlatform();
            PstmtBatch pstmtBatch = null;
            if (dbPlatform.getName().startsWith("oracle")) {
                PstmtDelegate pstmtDelegate = serverConfig.getPstmtDelegate();
                if (pstmtDelegate == null) {
                    pstmtDelegate = this.getOraclePstmtDelegate(serverConfig.getDataSource());
                }
                if (pstmtDelegate != null) {
                    pstmtBatch = new OraclePstmtBatch(pstmtDelegate);
                }
                if (pstmtBatch == null) {
                    DefaultServerFactory.logger.warning("Can not support JDBC batching with Oracle without a PstmtDelegate");
                    serverConfig.setPersistBatching(false);
                }
            }
            serverConfig.getNamingConvention().setDatabasePlatform(serverConfig.getDatabasePlatform());
            final ServerCacheManager cacheManager = this.getCacheManager(serverConfig);
            final int uniqueServerId = this.serverId.incrementAndGet();
            final BackgroundExecutor bgExecutor = this.createBackgroundExecutor(serverConfig, uniqueServerId);
            final InternalConfiguration c = new InternalConfiguration(this.xmlConfig, this.clusterManager, cacheManager, bgExecutor, serverConfig, bootupClasses, pstmtBatch);
            final DefaultServer server = new DefaultServer(c, cacheManager);
            cacheManager.init(server);
            final ArrayList<?> list = MBeanServerFactory.findMBeanServer(null);
            MBeanServer mbeanServer;
            if (list.size() == 0) {
                mbeanServer = MBeanServerFactory.createMBeanServer();
            }
            else {
                mbeanServer = (MBeanServer)list.get(0);
            }
            server.registerMBeans(mbeanServer, uniqueServerId);
            this.executeDDL(server, online);
            server.initialise();
            if (online) {
                if (this.clusterManager.isClustering()) {
                    this.clusterManager.registerServer(server);
                }
                final int delaySecs = GlobalProperties.getInt("ebean.cacheWarmingDelay", 30);
                final long sleepMillis = 1000 * delaySecs;
                if (sleepMillis > 0L) {
                    final Timer t = new Timer("EbeanCacheWarmer", true);
                    t.schedule(new CacheWarmer(sleepMillis, server), sleepMillis);
                }
            }
            server.start();
            return server;
        }
    }
    
    private PstmtDelegate getOraclePstmtDelegate(final DataSource ds) {
        if (ds instanceof DataSourcePool) {
            return new StandardPstmtDelegate();
        }
        return null;
    }
    
    private ServerCacheManager getCacheManager(final ServerConfig serverConfig) {
        ServerCacheOptions beanOptions = null;
        if (beanOptions == null) {
            beanOptions = new ServerCacheOptions();
            beanOptions.setMaxSize(GlobalProperties.getInt("cache.maxSize", 1000));
            beanOptions.setMaxIdleSecs(GlobalProperties.getInt("cache.maxIdleTime", 600));
            beanOptions.setMaxSecsToLive(GlobalProperties.getInt("cache.maxTimeToLive", 21600));
        }
        ServerCacheOptions queryOptions = null;
        if (queryOptions == null) {
            queryOptions = new ServerCacheOptions();
            queryOptions.setMaxSize(GlobalProperties.getInt("querycache.maxSize", 100));
            queryOptions.setMaxIdleSecs(GlobalProperties.getInt("querycache.maxIdleTime", 600));
            queryOptions.setMaxSecsToLive(GlobalProperties.getInt("querycache.maxTimeToLive", 21600));
        }
        ServerCacheFactory cacheFactory = null;
        if (cacheFactory == null) {
            cacheFactory = new DefaultServerCacheFactory();
        }
        return new DefaultServerCacheManager(cacheFactory, beanOptions, queryOptions);
    }
    
    private BootupClasses getBootupClasses(final ServerConfig serverConfig) {
        final BootupClasses bootupClasses = this.getBootupClasses1(serverConfig);
        bootupClasses.addPersistControllers(serverConfig.getPersistControllers());
        return bootupClasses;
    }
    
    private BootupClasses getBootupClasses1(final ServerConfig serverConfig) {
        final List<Class<?>> entityClasses = serverConfig.getClasses();
        if (entityClasses != null && entityClasses.size() > 0) {
            return new BootupClasses(serverConfig.getClasses());
        }
        final List<String> jars = serverConfig.getJars();
        final List<String> packages = serverConfig.getPackages();
        if ((packages != null && !packages.isEmpty()) || (jars != null && !jars.isEmpty())) {
            final BootupClassPathSearch search = new BootupClassPathSearch(null, packages, jars);
            return search.getBootupClasses();
        }
        return this.bootupClassSearch.getBootupClasses().createCopy();
    }
    
    private void executeDDL(final SpiEbeanServer server, final boolean online) {
        server.getDdlGenerator().execute(online);
    }
    
    private void setNamingConvention(final ServerConfig config) {
        if (config.getNamingConvention() == null) {
            final UnderscoreNamingConvention nc = new UnderscoreNamingConvention();
            config.setNamingConvention(nc);
            final String v = config.getProperty("namingConvention.useForeignKeyPrefix");
            if (v != null) {
                final boolean useForeignKeyPrefix = Boolean.valueOf(v);
                nc.setUseForeignKeyPrefix(useForeignKeyPrefix);
            }
            final String sequenceFormat = config.getProperty("namingConvention.sequenceFormat");
            if (sequenceFormat != null) {
                nc.setSequenceFormat(sequenceFormat);
            }
        }
    }
    
    private void setDatabasePlatform(final ServerConfig config) {
        final DatabasePlatform dbPlatform = config.getDatabasePlatform();
        if (dbPlatform == null) {
            final DatabasePlatformFactory factory = new DatabasePlatformFactory();
            final DatabasePlatform db = factory.create(config);
            config.setDatabasePlatform(db);
            DefaultServerFactory.logger.info("DatabasePlatform name:" + config.getName() + " platform:" + db.getName());
        }
    }
    
    private void setDataSource(final ServerConfig config) {
        if (config.getDataSource() == null) {
            final DataSource ds = this.getDataSourceFromConfig(config);
            config.setDataSource(ds);
        }
    }
    
    private DataSource getDataSourceFromConfig(final ServerConfig config) {
        DataSource ds = null;
        if (config.getDataSourceJndiName() != null) {
            ds = this.jndiDataSourceFactory.lookup(config.getDataSourceJndiName());
            if (ds == null) {
                final String m = "JNDI lookup for DataSource " + config.getDataSourceJndiName() + " returned null.";
                throw new PersistenceException(m);
            }
            return ds;
        }
        else {
            final DataSourceConfig dsConfig = config.getDataSourceConfig();
            if (dsConfig == null) {
                final String i = "No DataSourceConfig definded for " + config.getName();
                throw new PersistenceException(i);
            }
            if (!dsConfig.isOffline()) {
                if (dsConfig.getHeartbeatSql() == null) {
                    final String heartbeatSql = this.getHeartbeatSql(dsConfig.getDriver());
                    dsConfig.setHeartbeatSql(heartbeatSql);
                }
                return DataSourceGlobalManager.getDataSource(config.getName(), dsConfig);
            }
            if (config.getDatabasePlatformName() == null) {
                final String i = "You MUST specify a DatabasePlatformName on ServerConfig when offline";
                throw new PersistenceException(i);
            }
            return null;
        }
    }
    
    private String getHeartbeatSql(final String driver) {
        if (driver != null) {
            final String d = driver.toLowerCase();
            if (d.contains("oracle")) {
                return "select 'x' from dual";
            }
            if (d.contains(".h2.") || d.contains(".mysql.") || d.contains("postgre")) {
                return "select 1";
            }
        }
        return null;
    }
    
    private boolean checkDataSource(final ServerConfig serverConfig) {
        if (serverConfig.getDataSource() == null) {
            if (serverConfig.getDataSourceConfig().isOffline()) {
                return false;
            }
            throw new RuntimeException("DataSource not set?");
        }
        else {
            Connection c = null;
            try {
                c = serverConfig.getDataSource().getConnection();
                if (c.getAutoCommit()) {
                    final String m = "DataSource [" + serverConfig.getName() + "] has autoCommit defaulting to true!";
                    DefaultServerFactory.logger.warning(m);
                }
                return true;
            }
            catch (SQLException ex) {
                throw new PersistenceException(ex);
            }
            finally {
                if (c != null) {
                    try {
                        c.close();
                    }
                    catch (SQLException ex2) {
                        DefaultServerFactory.logger.log(Level.SEVERE, null, ex2);
                    }
                }
            }
        }
    }
    
    static {
        logger = Logger.getLogger(DefaultServerFactory.class.getName());
    }
    
    private static class CacheWarmer extends TimerTask
    {
        private static final Logger log;
        private final long sleepMillis;
        private final EbeanServer server;
        
        CacheWarmer(final long sleepMillis, final EbeanServer server) {
            this.sleepMillis = sleepMillis;
            this.server = server;
        }
        
        public void run() {
            try {
                Thread.sleep(this.sleepMillis);
            }
            catch (InterruptedException e) {
                final String msg = "Error while sleeping prior to cache warming";
                CacheWarmer.log.log(Level.SEVERE, msg, e);
            }
            this.server.runCacheWarming();
        }
        
        static {
            log = Logger.getLogger(CacheWarmer.class.getName());
        }
    }
}
