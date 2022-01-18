// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import javax.persistence.PersistenceException;
import com.avaje.ebean.config.GlobalProperties;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import com.avaje.ebean.text.json.JsonContext;
import com.avaje.ebean.cache.ServerCacheManager;
import com.avaje.ebean.text.csv.CsvReader;
import java.util.List;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import javax.persistence.OptimisticLockException;
import java.util.Map;
import java.util.logging.Logger;

public final class Ebean
{
    private static final Logger logger;
    private static final String EBVERSION = "2.7.3";
    private static final ServerManager serverMgr;
    
    public static EbeanServer getServer(final String name) {
        return Ebean.serverMgr.get(name);
    }
    
    public static ExpressionFactory getExpressionFactory() {
        return Ebean.serverMgr.getPrimaryServer().getExpressionFactory();
    }
    
    protected static void register(final EbeanServer server, final boolean isPrimaryServer) {
        Ebean.serverMgr.register(server, isPrimaryServer);
    }
    
    public static Object nextId(final Class<?> beanType) {
        return Ebean.serverMgr.getPrimaryServer().nextId(beanType);
    }
    
    public static void logComment(final String msg) {
        Ebean.serverMgr.getPrimaryServer().logComment(msg);
    }
    
    public static Transaction beginTransaction() {
        return Ebean.serverMgr.getPrimaryServer().beginTransaction();
    }
    
    public static Transaction beginTransaction(final TxIsolation isolation) {
        return Ebean.serverMgr.getPrimaryServer().beginTransaction(isolation);
    }
    
    public static Transaction currentTransaction() {
        return Ebean.serverMgr.getPrimaryServer().currentTransaction();
    }
    
    public static void commitTransaction() {
        Ebean.serverMgr.getPrimaryServer().commitTransaction();
    }
    
    public static void rollbackTransaction() {
        Ebean.serverMgr.getPrimaryServer().rollbackTransaction();
    }
    
    public static void endTransaction() {
        Ebean.serverMgr.getPrimaryServer().endTransaction();
    }
    
    public static InvalidValue validate(final Object bean) {
        return Ebean.serverMgr.getPrimaryServer().validate(bean);
    }
    
    public static InvalidValue[] validate(final Object bean, final String propertyName, final Object value) {
        return Ebean.serverMgr.getPrimaryServer().validate(bean, propertyName, value);
    }
    
    public static Map<String, ValuePair> diff(final Object a, final Object b) {
        return Ebean.serverMgr.getPrimaryServer().diff(a, b);
    }
    
    public static void save(final Object bean) throws OptimisticLockException {
        Ebean.serverMgr.getPrimaryServer().save(bean);
    }
    
    public static void update(final Object bean) {
        Ebean.serverMgr.getPrimaryServer().update(bean);
    }
    
    public static void update(final Object bean, final Set<String> updateProps) {
        Ebean.serverMgr.getPrimaryServer().update(bean, updateProps);
    }
    
    public static int save(final Iterator<?> iterator) throws OptimisticLockException {
        return Ebean.serverMgr.getPrimaryServer().save(iterator);
    }
    
    public static int save(final Collection<?> c) throws OptimisticLockException {
        return save(c.iterator());
    }
    
    public static int deleteManyToManyAssociations(final Object ownerBean, final String propertyName) {
        return Ebean.serverMgr.getPrimaryServer().deleteManyToManyAssociations(ownerBean, propertyName);
    }
    
    public static void saveManyToManyAssociations(final Object ownerBean, final String propertyName) {
        Ebean.serverMgr.getPrimaryServer().saveManyToManyAssociations(ownerBean, propertyName);
    }
    
    public static void saveAssociation(final Object ownerBean, final String propertyName) {
        Ebean.serverMgr.getPrimaryServer().saveAssociation(ownerBean, propertyName);
    }
    
    public static void delete(final Object bean) throws OptimisticLockException {
        Ebean.serverMgr.getPrimaryServer().delete(bean);
    }
    
    public static int delete(final Class<?> beanType, final Object id) {
        return Ebean.serverMgr.getPrimaryServer().delete(beanType, id);
    }
    
    public static void delete(final Class<?> beanType, final Collection<?> ids) {
        Ebean.serverMgr.getPrimaryServer().delete(beanType, ids);
    }
    
    public static int delete(final Iterator<?> it) throws OptimisticLockException {
        return Ebean.serverMgr.getPrimaryServer().delete(it);
    }
    
    public static int delete(final Collection<?> c) throws OptimisticLockException {
        return delete(c.iterator());
    }
    
    public static void refresh(final Object bean) {
        Ebean.serverMgr.getPrimaryServer().refresh(bean);
    }
    
    public static void refreshMany(final Object bean, final String manyPropertyName) {
        Ebean.serverMgr.getPrimaryServer().refreshMany(bean, manyPropertyName);
    }
    
    public static <T> T getReference(final Class<T> beanType, final Object id) {
        return Ebean.serverMgr.getPrimaryServer().getReference(beanType, id);
    }
    
    public static <T> void sort(final List<T> list, final String sortByClause) {
        Ebean.serverMgr.getPrimaryServer().sort(list, sortByClause);
    }
    
    public static <T> T find(final Class<T> beanType, final Object id) {
        return Ebean.serverMgr.getPrimaryServer().find(beanType, id);
    }
    
    public static SqlQuery createSqlQuery(final String sql) {
        return Ebean.serverMgr.getPrimaryServer().createSqlQuery(sql);
    }
    
    public static SqlQuery createNamedSqlQuery(final String namedQuery) {
        return Ebean.serverMgr.getPrimaryServer().createNamedSqlQuery(namedQuery);
    }
    
    public static SqlUpdate createSqlUpdate(final String sql) {
        return Ebean.serverMgr.getPrimaryServer().createSqlUpdate(sql);
    }
    
    public static CallableSql createCallableSql(final String sql) {
        return Ebean.serverMgr.getPrimaryServer().createCallableSql(sql);
    }
    
    public static SqlUpdate createNamedSqlUpdate(final String namedQuery) {
        return Ebean.serverMgr.getPrimaryServer().createNamedSqlUpdate(namedQuery);
    }
    
    public static <T> Query<T> createNamedQuery(final Class<T> beanType, final String namedQuery) {
        return Ebean.serverMgr.getPrimaryServer().createNamedQuery(beanType, namedQuery);
    }
    
    public static <T> Query<T> createQuery(final Class<T> beanType, final String query) {
        return Ebean.serverMgr.getPrimaryServer().createQuery(beanType, query);
    }
    
    public static <T> Update<T> createNamedUpdate(final Class<T> beanType, final String namedUpdate) {
        return Ebean.serverMgr.getPrimaryServer().createNamedUpdate(beanType, namedUpdate);
    }
    
    public static <T> Update<T> createUpdate(final Class<T> beanType, final String ormUpdate) {
        return Ebean.serverMgr.getPrimaryServer().createUpdate(beanType, ormUpdate);
    }
    
    public static <T> CsvReader<T> createCsvReader(final Class<T> beanType) {
        return Ebean.serverMgr.getPrimaryServer().createCsvReader(beanType);
    }
    
    public static <T> Query<T> createQuery(final Class<T> beanType) {
        return Ebean.serverMgr.getPrimaryServer().createQuery(beanType);
    }
    
    public static <T> Query<T> find(final Class<T> beanType) {
        return Ebean.serverMgr.getPrimaryServer().find(beanType);
    }
    
    public static <T> Filter<T> filter(final Class<T> beanType) {
        return Ebean.serverMgr.getPrimaryServer().filter(beanType);
    }
    
    public static int execute(final SqlUpdate sqlUpdate) {
        return Ebean.serverMgr.getPrimaryServer().execute(sqlUpdate);
    }
    
    public static int execute(final CallableSql callableSql) {
        return Ebean.serverMgr.getPrimaryServer().execute(callableSql);
    }
    
    public static void execute(final TxScope scope, final TxRunnable r) {
        Ebean.serverMgr.getPrimaryServer().execute(scope, r);
    }
    
    public static void execute(final TxRunnable r) {
        Ebean.serverMgr.getPrimaryServer().execute(r);
    }
    
    public static <T> T execute(final TxScope scope, final TxCallable<T> c) {
        return Ebean.serverMgr.getPrimaryServer().execute(scope, c);
    }
    
    public static <T> T execute(final TxCallable<T> c) {
        return Ebean.serverMgr.getPrimaryServer().execute(c);
    }
    
    public static void externalModification(final String tableName, final boolean inserts, final boolean updates, final boolean deletes) {
        Ebean.serverMgr.getPrimaryServer().externalModification(tableName, inserts, updates, deletes);
    }
    
    public static BeanState getBeanState(final Object bean) {
        return Ebean.serverMgr.getPrimaryServer().getBeanState(bean);
    }
    
    public static ServerCacheManager getServerCacheManager() {
        return Ebean.serverMgr.getPrimaryServer().getServerCacheManager();
    }
    
    public static BackgroundExecutor getBackgroundExecutor() {
        return Ebean.serverMgr.getPrimaryServer().getBackgroundExecutor();
    }
    
    public static void runCacheWarming() {
        Ebean.serverMgr.getPrimaryServer().runCacheWarming();
    }
    
    public static void runCacheWarming(final Class<?> beanType) {
        Ebean.serverMgr.getPrimaryServer().runCacheWarming(beanType);
    }
    
    public static JsonContext createJsonContext() {
        return Ebean.serverMgr.getPrimaryServer().createJsonContext();
    }
    
    static {
        logger = Logger.getLogger(Ebean.class.getName());
        final String version = System.getProperty("java.version");
        Ebean.logger.info("Ebean Version[2.7.3] Java Version[" + version + "]");
        serverMgr = new ServerManager();
    }
    
    private static final class ServerManager
    {
        private final ConcurrentHashMap<String, EbeanServer> concMap;
        private final HashMap<String, EbeanServer> syncMap;
        private final Object monitor;
        private EbeanServer primaryServer;
        
        private ServerManager() {
            this.concMap = new ConcurrentHashMap<String, EbeanServer>();
            this.syncMap = new HashMap<String, EbeanServer>();
            this.monitor = new Object();
            if (GlobalProperties.isSkipPrimaryServer()) {
                Ebean.logger.fine("GlobalProperties.isSkipPrimaryServer()");
            }
            else {
                final String primaryName = this.getPrimaryServerName();
                Ebean.logger.fine("primaryName:" + primaryName);
                if (primaryName != null && primaryName.trim().length() > 0) {
                    this.primaryServer = this.getWithCreate(primaryName.trim());
                }
            }
        }
        
        private String getPrimaryServerName() {
            final String serverName = GlobalProperties.get("ebean.default.datasource", null);
            return GlobalProperties.get("datasource.default", serverName);
        }
        
        private EbeanServer getPrimaryServer() {
            if (this.primaryServer == null) {
                String msg = "The default EbeanServer has not been defined?";
                msg += " This is normally set via the ebean.datasource.default property.";
                msg += " Otherwise it should be registered programatically via registerServer()";
                throw new PersistenceException(msg);
            }
            return this.primaryServer;
        }
        
        private EbeanServer get(final String name) {
            if (name == null || name.length() == 0) {
                return this.primaryServer;
            }
            final EbeanServer server = this.concMap.get(name);
            if (server != null) {
                return server;
            }
            return this.getWithCreate(name);
        }
        
        private EbeanServer getWithCreate(final String name) {
            synchronized (this.monitor) {
                EbeanServer server = this.syncMap.get(name);
                if (server == null) {
                    server = EbeanServerFactory.create(name);
                    this.register(server, false);
                }
                return server;
            }
        }
        
        private void register(final EbeanServer server, final boolean isPrimaryServer) {
            synchronized (this.monitor) {
                this.concMap.put(server.getName(), server);
                final EbeanServer existingServer = this.syncMap.put(server.getName(), server);
                if (existingServer != null) {
                    final String msg = "Existing EbeanServer [" + server.getName() + "] is being replaced?";
                    Ebean.logger.warning(msg);
                }
                if (isPrimaryServer) {
                    this.primaryServer = server;
                }
            }
        }
    }
}
