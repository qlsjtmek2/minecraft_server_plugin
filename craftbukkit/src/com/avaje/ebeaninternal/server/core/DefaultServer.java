// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.Ebean;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import com.avaje.ebeaninternal.util.ParamTypeHelper;
import java.lang.reflect.Type;
import com.avaje.ebean.event.BeanPersistController;
import com.avaje.ebeaninternal.server.deploy.BeanManager;
import java.util.Collection;
import com.avaje.ebeaninternal.server.query.SqlQueryFutureList;
import com.avaje.ebeaninternal.server.query.CallableSqlQueryList;
import com.avaje.ebeaninternal.api.SpiSqlQuery;
import com.avaje.ebean.SqlFutureList;
import com.avaje.ebean.SqlRow;
import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.QueryResultVisitor;
import com.avaje.ebeaninternal.server.query.LimitOffsetPagingQuery;
import com.avaje.ebeaninternal.server.transaction.DefaultPersistenceContext;
import com.avaje.ebean.PagingList;
import com.avaje.ebeaninternal.server.query.QueryFutureList;
import com.avaje.ebeaninternal.server.query.CallableQueryList;
import com.avaje.ebean.FutureList;
import com.avaje.ebeaninternal.server.query.QueryFutureIds;
import com.avaje.ebeaninternal.server.query.CallableQueryIds;
import java.util.Collections;
import java.util.ArrayList;
import com.avaje.ebean.FutureIds;
import com.avaje.ebeaninternal.server.query.QueryFutureRowCount;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import com.avaje.ebeaninternal.server.query.CallableQueryRowCount;
import com.avaje.ebean.FutureRowCount;
import java.util.Set;
import com.avaje.ebean.event.BeanQueryAdapter;
import com.avaje.ebean.bean.CallStack;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebeaninternal.server.ldap.LdapOrmQueryRequest;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.CallableSql;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebeaninternal.server.deploy.DNativeQuery;
import com.avaje.ebeaninternal.server.querydefn.DefaultRelationalQuery;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebeaninternal.server.deploy.DeployNamedUpdate;
import com.avaje.ebeaninternal.server.querydefn.DefaultOrmUpdate;
import com.avaje.ebean.Update;
import com.avaje.ebeaninternal.server.ldap.DefaultLdapOrmQuery;
import com.avaje.ebeaninternal.server.text.csv.TCsvReader;
import com.avaje.ebean.text.csv.CsvReader;
import com.avaje.ebeaninternal.server.el.ElFilter;
import com.avaje.ebean.Filter;
import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
import com.avaje.ebeaninternal.server.querydefn.DefaultOrmQuery;
import com.avaje.ebeaninternal.api.SpiTransactionScopeManager;
import com.avaje.ebean.TxType;
import com.avaje.ebean.TxRunnable;
import com.avaje.ebeaninternal.api.ScopeTrans;
import com.avaje.ebean.TxScope;
import com.avaje.ebean.TxCallable;
import com.avaje.ebean.TxIsolation;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;
import com.avaje.ebean.bean.PersistenceContext;
import java.util.Arrays;
import java.io.IOException;
import com.avaje.ebean.EbeanServer;
import java.io.ObjectInputStream;
import java.io.InputStream;
import java.util.Iterator;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import com.avaje.ebean.ValuePair;
import java.util.Map;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebeaninternal.api.LoadBeanRequest;
import com.avaje.ebeaninternal.api.LoadManyContext;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.api.LoadManyRequest;
import com.avaje.ebeaninternal.server.query.CQuery;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.Query;
import javax.persistence.PersistenceException;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.BeanState;
import javax.management.InstanceAlreadyExistsException;
import java.util.logging.Level;
import javax.management.ObjectName;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.config.lucene.LuceneIndex;
import com.avaje.ebean.config.ldap.LdapConfig;
import com.avaje.ebeaninternal.server.lib.ShutdownManager;
import com.avaje.ebeaninternal.server.jmx.MAdminAutofetch;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebeaninternal.server.ldap.expression.LdapExpressionFactory;
import javax.management.MBeanServer;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebean.text.json.JsonContext;
import com.avaje.ebean.config.EncryptKeyManager;
import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebeaninternal.server.ddl.DdlGenerator;
import com.avaje.ebeaninternal.server.query.CQueryEngine;
import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
import com.avaje.ebean.cache.ServerCacheManager;
import com.avaje.ebeaninternal.server.ldap.LdapOrmQueryEngine;
import com.avaje.ebeaninternal.server.transaction.TransactionScopeManager;
import com.avaje.ebeaninternal.server.transaction.TransactionManager;
import com.avaje.ebean.AdminAutofetch;
import com.avaje.ebean.AdminLogging;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebean.InvalidValue;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.api.SpiEbeanServer;

public final class DefaultServer implements SpiEbeanServer
{
    private static final Logger logger;
    private static final InvalidValue[] EMPTY_INVALID_VALUES;
    private final String serverName;
    private final DatabasePlatform databasePlatform;
    private final AdminLogging adminLogging;
    private final AdminAutofetch adminAutofetch;
    private final TransactionManager transactionManager;
    private final TransactionScopeManager transactionScopeManager;
    private final int maxCallStack;
    private final boolean rollbackOnChecked;
    private final boolean defaultDeleteMissingChildren;
    private final boolean defaultUpdateNullProperties;
    private final boolean vanillaMode;
    private final boolean vanillaRefMode;
    private final LdapOrmQueryEngine ldapQueryEngine;
    private final Persister persister;
    private final OrmQueryEngine queryEngine;
    private final RelationalQueryEngine relationalQueryEngine;
    private final ServerCacheManager serverCacheManager;
    private final BeanDescriptorManager beanDescriptorManager;
    private final DiffHelp diffHelp;
    private final AutoFetchManager autoFetchManager;
    private final CQueryEngine cqueryEngine;
    private final DdlGenerator ddlGenerator;
    private final ExpressionFactory ldapExpressionFactory;
    private final ExpressionFactory expressionFactory;
    private final BackgroundExecutor backgroundExecutor;
    private final DefaultBeanLoader beanLoader;
    private final EncryptKeyManager encryptKeyManager;
    private final JsonContext jsonContext;
    private final LuceneIndexManager luceneIndexManager;
    private String mbeanName;
    private MBeanServer mbeanServer;
    private int lazyLoadBatchSize;
    private int queryBatchSize;
    private PstmtBatch pstmtBatch;
    private static final int IGNORE_LEADING_ELEMENTS = 5;
    private static final String AVAJE_EBEAN;
    
    public DefaultServer(final InternalConfiguration config, final ServerCacheManager cache) {
        this.diffHelp = new DiffHelp();
        this.ldapExpressionFactory = new LdapExpressionFactory();
        this.vanillaMode = config.getServerConfig().isVanillaMode();
        this.vanillaRefMode = config.getServerConfig().isVanillaRefMode();
        this.serverCacheManager = cache;
        this.pstmtBatch = config.getPstmtBatch();
        this.databasePlatform = config.getDatabasePlatform();
        this.backgroundExecutor = config.getBackgroundExecutor();
        this.serverName = config.getServerConfig().getName();
        this.lazyLoadBatchSize = config.getServerConfig().getLazyLoadBatchSize();
        this.queryBatchSize = config.getServerConfig().getQueryBatchSize();
        this.cqueryEngine = config.getCQueryEngine();
        this.expressionFactory = config.getExpressionFactory();
        this.adminLogging = config.getLogControl();
        this.encryptKeyManager = config.getServerConfig().getEncryptKeyManager();
        (this.beanDescriptorManager = config.getBeanDescriptorManager()).setEbeanServer(this);
        this.maxCallStack = GlobalProperties.getInt("ebean.maxCallStack", 5);
        this.defaultUpdateNullProperties = "true".equalsIgnoreCase(config.getServerConfig().getProperty("defaultUpdateNullProperties", "false"));
        this.defaultDeleteMissingChildren = "true".equalsIgnoreCase(config.getServerConfig().getProperty("defaultDeleteMissingChildren", "true"));
        this.rollbackOnChecked = GlobalProperties.getBoolean("ebean.transaction.rollbackOnChecked", true);
        this.transactionManager = config.getTransactionManager();
        this.transactionScopeManager = config.getTransactionScopeManager();
        this.persister = config.createPersister(this);
        this.queryEngine = config.createOrmQueryEngine();
        this.relationalQueryEngine = config.createRelationalQueryEngine();
        this.autoFetchManager = config.createAutoFetchManager(this);
        this.adminAutofetch = new MAdminAutofetch(this.autoFetchManager);
        this.ddlGenerator = new DdlGenerator(this, config.getDatabasePlatform(), config.getServerConfig());
        this.beanLoader = new DefaultBeanLoader(this, config.getDebugLazyLoad());
        this.jsonContext = config.createJsonContext(this);
        final LdapConfig ldapConfig = config.getServerConfig().getLdapConfig();
        if (ldapConfig == null) {
            this.ldapQueryEngine = null;
        }
        else {
            this.ldapQueryEngine = new LdapOrmQueryEngine(ldapConfig.isVanillaMode(), ldapConfig.getContextFactory());
        }
        (this.luceneIndexManager = config.getLuceneIndexManager()).setServer(this);
        ShutdownManager.register(new Shutdown());
    }
    
    public LuceneIndexManager getLuceneIndexManager() {
        return this.luceneIndexManager;
    }
    
    public LuceneIndex getLuceneIndex(final Class<?> beanType) {
        return this.luceneIndexManager.getIndex(beanType.getName());
    }
    
    public boolean isDefaultDeleteMissingChildren() {
        return this.defaultDeleteMissingChildren;
    }
    
    public boolean isDefaultUpdateNullProperties() {
        return this.defaultUpdateNullProperties;
    }
    
    public boolean isVanillaMode() {
        return this.vanillaMode;
    }
    
    public int getLazyLoadBatchSize() {
        return this.lazyLoadBatchSize;
    }
    
    public PstmtBatch getPstmtBatch() {
        return this.pstmtBatch;
    }
    
    public DatabasePlatform getDatabasePlatform() {
        return this.databasePlatform;
    }
    
    public BackgroundExecutor getBackgroundExecutor() {
        return this.backgroundExecutor;
    }
    
    public ExpressionFactory getExpressionFactory() {
        return this.expressionFactory;
    }
    
    public DdlGenerator getDdlGenerator() {
        return this.ddlGenerator;
    }
    
    public AdminLogging getAdminLogging() {
        return this.adminLogging;
    }
    
    public AdminAutofetch getAdminAutofetch() {
        return this.adminAutofetch;
    }
    
    public AutoFetchManager getAutoFetchManager() {
        return this.autoFetchManager;
    }
    
    public void initialise() {
        if (this.encryptKeyManager != null) {
            this.encryptKeyManager.initialise();
        }
        final List<BeanDescriptor<?>> list = this.beanDescriptorManager.getBeanDescriptorList();
        for (int i = 0; i < list.size(); ++i) {
            list.get(i).cacheInitialise();
        }
    }
    
    public void start() {
        this.luceneIndexManager.start();
    }
    
    public void registerMBeans(final MBeanServer mbeanServer, final int uniqueServerId) {
        this.mbeanServer = mbeanServer;
        this.mbeanName = "Ebean:server=" + this.serverName + uniqueServerId;
        ObjectName adminName;
        ObjectName autofethcName;
        try {
            adminName = new ObjectName(this.mbeanName + ",function=Logging");
            autofethcName = new ObjectName(this.mbeanName + ",key=AutoFetch");
        }
        catch (Exception e) {
            final String msg = "Failed to register the JMX beans for Ebean server [" + this.serverName + "].";
            DefaultServer.logger.log(Level.SEVERE, msg, e);
            return;
        }
        try {
            mbeanServer.registerMBean(this.adminLogging, adminName);
            mbeanServer.registerMBean(this.adminAutofetch, autofethcName);
        }
        catch (InstanceAlreadyExistsException e2) {
            final String msg = "JMX beans for Ebean server [" + this.serverName + "] already registered. Will try unregister/register" + e2.getMessage();
            DefaultServer.logger.log(Level.WARNING, msg);
            try {
                mbeanServer.unregisterMBean(adminName);
                mbeanServer.unregisterMBean(autofethcName);
                mbeanServer.registerMBean(this.adminLogging, adminName);
                mbeanServer.registerMBean(this.adminAutofetch, autofethcName);
            }
            catch (Exception ae) {
                final String amsg = "Unable to unregister/register the JMX beans for Ebean server [" + this.serverName + "].";
                DefaultServer.logger.log(Level.SEVERE, amsg, ae);
            }
        }
        catch (Exception e) {
            final String msg = "Error registering MBean[" + this.mbeanName + "]";
            DefaultServer.logger.log(Level.SEVERE, msg, e);
        }
    }
    
    public String getName() {
        return this.serverName;
    }
    
    public BeanState getBeanState(final Object bean) {
        if (bean instanceof EntityBean) {
            return new DefaultBeanState((EntityBean)bean);
        }
        return null;
    }
    
    public void runCacheWarming() {
        final List<BeanDescriptor<?>> descList = this.beanDescriptorManager.getBeanDescriptorList();
        for (int i = 0; i < descList.size(); ++i) {
            descList.get(i).runCacheWarming();
        }
    }
    
    public void runCacheWarming(final Class<?> beanType) {
        final BeanDescriptor<?> desc = this.beanDescriptorManager.getBeanDescriptor(beanType);
        if (desc == null) {
            final String msg = "Is " + beanType + " an entity? Could not find a BeanDescriptor";
            throw new PersistenceException(msg);
        }
        desc.runCacheWarming();
    }
    
    public <T> CQuery<T> compileQuery(final Query<T> query, final Transaction t) {
        final SpiOrmQueryRequest<T> qr = this.createQueryRequest(Query.Type.SUBQUERY, query, t);
        final OrmQueryRequest<T> orm = (OrmQueryRequest<T>)(OrmQueryRequest)qr;
        return this.cqueryEngine.buildQuery(orm);
    }
    
    public CQueryEngine getQueryEngine() {
        return this.cqueryEngine;
    }
    
    public ServerCacheManager getServerCacheManager() {
        return this.serverCacheManager;
    }
    
    public AutoFetchManager getProfileListener() {
        return this.autoFetchManager;
    }
    
    public RelationalQueryEngine getRelationalQueryEngine() {
        return this.relationalQueryEngine;
    }
    
    public void refreshMany(final Object parentBean, final String propertyName, final Transaction t) {
        this.beanLoader.refreshMany(parentBean, propertyName, t);
    }
    
    public void refreshMany(final Object parentBean, final String propertyName) {
        this.beanLoader.refreshMany(parentBean, propertyName);
    }
    
    public void loadMany(final LoadManyRequest loadRequest) {
        this.beanLoader.loadMany(loadRequest);
    }
    
    public void loadMany(final BeanCollection<?> bc, final boolean onlyIds) {
        this.beanLoader.loadMany(bc, null, onlyIds);
    }
    
    public void refresh(final Object bean) {
        this.beanLoader.refresh(bean);
    }
    
    public void loadBean(final LoadBeanRequest loadRequest) {
        this.beanLoader.loadBean(loadRequest);
    }
    
    public void loadBean(final EntityBeanIntercept ebi) {
        this.beanLoader.loadBean(ebi);
    }
    
    public InvalidValue validate(final Object bean) {
        if (bean == null) {
            return null;
        }
        final BeanDescriptor<?> beanDescriptor = this.getBeanDescriptor(bean.getClass());
        return beanDescriptor.validate(true, bean);
    }
    
    public InvalidValue[] validate(final Object bean, final String propertyName, Object value) {
        if (bean == null) {
            return null;
        }
        final BeanDescriptor<?> beanDescriptor = this.getBeanDescriptor(bean.getClass());
        final BeanProperty prop = beanDescriptor.getBeanProperty(propertyName);
        if (prop == null) {
            final String msg = "property " + propertyName + " was not found?";
            throw new PersistenceException(msg);
        }
        if (value == null) {
            value = prop.getValue(bean);
        }
        final List<InvalidValue> errors = prop.validate(true, value);
        if (errors == null) {
            return DefaultServer.EMPTY_INVALID_VALUES;
        }
        return InvalidValue.toArray(errors);
    }
    
    public Map<String, ValuePair> diff(final Object a, final Object b) {
        if (a == null) {
            return null;
        }
        final BeanDescriptor<?> desc = this.getBeanDescriptor(a.getClass());
        return this.diffHelp.diff(a, b, desc);
    }
    
    public void externalModification(final TransactionEventTable tableEvent) {
        final SpiTransaction t = this.transactionScopeManager.get();
        if (t != null) {
            t.getEvent().add(tableEvent);
        }
        else {
            this.transactionManager.externalModification(tableEvent);
        }
    }
    
    public void externalModification(final String tableName, final boolean inserts, final boolean updates, final boolean deletes) {
        final TransactionEventTable evt = new TransactionEventTable();
        evt.add(tableName, inserts, updates, deletes);
        this.externalModification(evt);
    }
    
    public void clearQueryStatistics() {
        for (final BeanDescriptor<?> desc : this.getBeanDescriptors()) {
            desc.clearQueryStatistics();
        }
    }
    
    public <T> T createEntityBean(final Class<T> type) {
        final BeanDescriptor<T> desc = this.getBeanDescriptor(type);
        return (T)desc.createEntityBean();
    }
    
    public ObjectInputStream createProxyObjectInputStream(final InputStream is) {
        try {
            return new ProxyBeanObjectInputStream(is, this);
        }
        catch (IOException e) {
            throw new PersistenceException(e);
        }
    }
    
    public <T> T getReference(final Class<T> type, Object id) {
        if (id == null) {
            throw new NullPointerException("The id is null");
        }
        final BeanDescriptor desc = this.getBeanDescriptor(type);
        id = desc.convertId(id);
        Object ref = null;
        PersistenceContext ctx = null;
        final SpiTransaction t = this.transactionScopeManager.get();
        if (t != null) {
            ctx = t.getPersistenceContext();
            ref = ctx.get(type, id);
        }
        if (ref == null) {
            final ReferenceOptions opts = desc.getReferenceOptions();
            if (opts != null && opts.isUseCache()) {
                ref = desc.cacheGet(id);
                if (ref != null && !opts.isReadOnly()) {
                    ref = desc.createCopyForUpdate(ref, this.vanillaMode);
                }
            }
        }
        if (ref == null) {
            final InheritInfo inheritInfo = desc.getInheritInfo();
            if (inheritInfo != null) {
                final BeanProperty[] idProps = desc.propertiesId();
                String idNames = null;
                switch (idProps.length) {
                    case 0: {
                        throw new PersistenceException("No ID properties for this type? " + desc);
                    }
                    case 1: {
                        idNames = idProps[0].getName();
                        break;
                    }
                    default: {
                        idNames = Arrays.toString(idProps);
                        idNames = idNames.substring(1, idNames.length() - 1);
                        break;
                    }
                }
                final Query<T> query = this.createQuery(type);
                query.select(idNames).setId(id);
                ref = query.findUnique();
            }
            else {
                ref = desc.createReference(this.vanillaRefMode, id, null, desc.getReferenceOptions());
            }
            if (ctx != null && ref instanceof EntityBean) {
                ctx.put(id, ref);
            }
        }
        return (T)ref;
    }
    
    public Transaction createTransaction() {
        return this.transactionManager.createTransaction(true, -1);
    }
    
    public Transaction createTransaction(final TxIsolation isolation) {
        return this.transactionManager.createTransaction(true, isolation.getLevel());
    }
    
    public void logComment(final String msg) {
        final Transaction t = this.transactionScopeManager.get();
        if (t != null) {
            t.log(msg);
        }
    }
    
    public <T> T execute(final TxCallable<T> c) {
        return this.execute(null, c);
    }
    
    public <T> T execute(final TxScope scope, final TxCallable<T> c) {
        final ScopeTrans scopeTrans = this.createScopeTrans(scope);
        try {
            return c.call();
        }
        catch (Error e) {
            throw scopeTrans.caughtError(e);
        }
        catch (RuntimeException e2) {
            throw scopeTrans.caughtThrowable(e2);
        }
        finally {
            scopeTrans.onFinally();
        }
    }
    
    public void execute(final TxRunnable r) {
        this.execute(null, r);
    }
    
    public void execute(final TxScope scope, final TxRunnable r) {
        final ScopeTrans scopeTrans = this.createScopeTrans(scope);
        try {
            r.run();
        }
        catch (Error e) {
            throw scopeTrans.caughtError(e);
        }
        catch (RuntimeException e2) {
            throw scopeTrans.caughtThrowable(e2);
        }
        finally {
            scopeTrans.onFinally();
        }
    }
    
    private boolean createNewTransaction(final SpiTransaction t, final TxScope scope) {
        final TxType type = scope.getType();
        switch (type) {
            case REQUIRED: {
                return t == null;
            }
            case REQUIRES_NEW: {
                return true;
            }
            case MANDATORY: {
                if (t == null) {
                    throw new PersistenceException("Transaction missing when MANDATORY");
                }
                return true;
            }
            case NEVER: {
                if (t != null) {
                    throw new PersistenceException("Transaction exists for Transactional NEVER");
                }
                return false;
            }
            case SUPPORTS: {
                return false;
            }
            case NOT_SUPPORTED: {
                throw new RuntimeException("NOT_SUPPORTED should already be handled?");
            }
            default: {
                throw new RuntimeException("Should never get here?");
            }
        }
    }
    
    public ScopeTrans createScopeTrans(TxScope txScope) {
        if (txScope == null) {
            txScope = new TxScope();
        }
        SpiTransaction suspended = null;
        SpiTransaction t = this.transactionScopeManager.get();
        boolean newTransaction;
        if (txScope.getType().equals(TxType.NOT_SUPPORTED)) {
            newTransaction = false;
            suspended = t;
            t = null;
        }
        else {
            newTransaction = this.createNewTransaction(t, txScope);
            if (newTransaction) {
                suspended = t;
                int isoLevel = -1;
                final TxIsolation isolation = txScope.getIsolation();
                if (isolation != null) {
                    isoLevel = isolation.getLevel();
                }
                t = this.transactionManager.createTransaction(true, isoLevel);
            }
        }
        this.transactionScopeManager.replace(t);
        return new ScopeTrans(this.rollbackOnChecked, newTransaction, t, txScope, suspended, this.transactionScopeManager);
    }
    
    public SpiTransaction getCurrentServerTransaction() {
        return this.transactionScopeManager.get();
    }
    
    public Transaction beginTransaction() {
        final SpiTransaction t = this.transactionManager.createTransaction(true, -1);
        this.transactionScopeManager.set(t);
        return t;
    }
    
    public Transaction beginTransaction(final TxIsolation isolation) {
        final SpiTransaction t = this.transactionManager.createTransaction(true, isolation.getLevel());
        this.transactionScopeManager.set(t);
        return t;
    }
    
    public Transaction currentTransaction() {
        return this.transactionScopeManager.get();
    }
    
    public void commitTransaction() {
        this.transactionScopeManager.commit();
    }
    
    public void rollbackTransaction() {
        this.transactionScopeManager.rollback();
    }
    
    public void endTransaction() {
        this.transactionScopeManager.end();
    }
    
    public Object nextId(final Class<?> beanType) {
        final BeanDescriptor<?> desc = this.getBeanDescriptor(beanType);
        return desc.nextId(null);
    }
    
    public <T> void sort(final List<T> list, final String sortByClause) {
        if (list == null) {
            throw new NullPointerException("list is null");
        }
        if (sortByClause == null) {
            throw new NullPointerException("sortByClause is null");
        }
        if (list.size() == 0) {
            return;
        }
        final Class<T> beanType = (Class<T>)list.get(0).getClass();
        final BeanDescriptor<T> beanDescriptor = this.getBeanDescriptor(beanType);
        if (beanDescriptor == null) {
            final String m = "BeanDescriptor not found, is [" + beanType + "] an entity bean?";
            throw new PersistenceException(m);
        }
        beanDescriptor.sort(list, sortByClause);
    }
    
    public <T> Query<T> createQuery(final Class<T> beanType) throws PersistenceException {
        return this.createQuery(beanType, null);
    }
    
    public <T> Query<T> createNamedQuery(final Class<T> beanType, final String namedQuery) throws PersistenceException {
        final BeanDescriptor<?> desc = this.getBeanDescriptor((Class<?>)beanType);
        if (desc == null) {
            throw new PersistenceException("Is " + beanType.getName() + " an Entity Bean? BeanDescriptor not found?");
        }
        final DeployNamedQuery deployQuery = desc.getNamedQuery(namedQuery);
        if (deployQuery == null) {
            throw new PersistenceException("named query " + namedQuery + " was not found for " + desc.getFullName());
        }
        return new DefaultOrmQuery<T>(beanType, this, this.expressionFactory, deployQuery);
    }
    
    public <T> Filter<T> filter(final Class<T> beanType) {
        final BeanDescriptor<T> desc = this.getBeanDescriptor(beanType);
        if (desc == null) {
            final String m = beanType.getName() + " is NOT an Entity Bean registered with this server?";
            throw new PersistenceException(m);
        }
        return new ElFilter<T>(desc);
    }
    
    public <T> CsvReader<T> createCsvReader(final Class<T> beanType) {
        final BeanDescriptor<T> descriptor = this.getBeanDescriptor(beanType);
        if (descriptor == null) {
            throw new NullPointerException("BeanDescriptor for " + beanType.getName() + " not found");
        }
        return new TCsvReader<T>(this, descriptor);
    }
    
    public <T> Query<T> find(final Class<T> beanType) {
        return (Query<T>)this.createQuery((Class<Object>)beanType);
    }
    
    public <T> Query<T> createQuery(final Class<T> beanType, final String query) {
        final BeanDescriptor<?> desc = this.getBeanDescriptor((Class<?>)beanType);
        if (desc == null) {
            final String m = beanType.getName() + " is NOT an Entity Bean registered with this server?";
            throw new PersistenceException(m);
        }
        switch (desc.getEntityType()) {
            case SQL: {
                if (query != null) {
                    throw new PersistenceException("You must used Named queries for this Entity " + desc.getFullName());
                }
                final DeployNamedQuery defaultSqlSelect = desc.getNamedQuery("default");
                return new DefaultOrmQuery<T>(beanType, this, this.expressionFactory, defaultSqlSelect);
            }
            case LDAP: {
                return new DefaultLdapOrmQuery<T>(beanType, this, this.ldapExpressionFactory, query);
            }
            default: {
                return new DefaultOrmQuery<T>(beanType, this, this.expressionFactory, query);
            }
        }
    }
    
    public <T> Update<T> createNamedUpdate(final Class<T> beanType, final String namedUpdate) {
        final BeanDescriptor<?> desc = this.getBeanDescriptor((Class<?>)beanType);
        if (desc == null) {
            final String m = beanType.getName() + " is NOT an Entity Bean registered with this server?";
            throw new PersistenceException(m);
        }
        final DeployNamedUpdate deployUpdate = desc.getNamedUpdate(namedUpdate);
        if (deployUpdate == null) {
            throw new PersistenceException("named update " + namedUpdate + " was not found for " + desc.getFullName());
        }
        return new DefaultOrmUpdate<T>(beanType, this, desc.getBaseTable(), deployUpdate);
    }
    
    public <T> Update<T> createUpdate(final Class<T> beanType, final String ormUpdate) {
        final BeanDescriptor<?> desc = this.getBeanDescriptor((Class<?>)beanType);
        if (desc == null) {
            final String m = beanType.getName() + " is NOT an Entity Bean registered with this server?";
            throw new PersistenceException(m);
        }
        return new DefaultOrmUpdate<T>(beanType, this, desc.getBaseTable(), ormUpdate);
    }
    
    public SqlQuery createSqlQuery(final String sql) {
        return new DefaultRelationalQuery(this, sql);
    }
    
    public SqlQuery createNamedSqlQuery(final String namedQuery) {
        final DNativeQuery nq = this.beanDescriptorManager.getNativeQuery(namedQuery);
        if (nq == null) {
            throw new PersistenceException("SqlQuery " + namedQuery + " not found.");
        }
        return new DefaultRelationalQuery(this, nq.getQuery());
    }
    
    public SqlUpdate createSqlUpdate(final String sql) {
        return new DefaultSqlUpdate(this, sql);
    }
    
    public CallableSql createCallableSql(final String sql) {
        return new DefaultCallableSql(this, sql);
    }
    
    public SqlUpdate createNamedSqlUpdate(final String namedQuery) {
        final DNativeQuery nq = this.beanDescriptorManager.getNativeQuery(namedQuery);
        if (nq == null) {
            throw new PersistenceException("SqlUpdate " + namedQuery + " not found.");
        }
        return new DefaultSqlUpdate(this, nq.getQuery());
    }
    
    public <T> T find(final Class<T> beanType, final Object uid) {
        return this.find(beanType, uid, null);
    }
    
    public <T> T find(final Class<T> beanType, final Object id, final Transaction t) {
        if (id == null) {
            throw new NullPointerException("The id is null");
        }
        final Query<T> query = this.createQuery(beanType).setId(id);
        return this.findId(query, t);
    }
    
    public <T> SpiOrmQueryRequest<T> createQueryRequest(final Query.Type type, final Query<T> q, final Transaction t) {
        final SpiQuery<T> query = (SpiQuery<T>)(SpiQuery)q;
        query.setType(type);
        query.deriveSharedInstance();
        final BeanDescriptor<T> desc = this.beanDescriptorManager.getBeanDescriptor(query.getBeanType());
        query.setBeanDescriptor(desc);
        if (desc.isLdapEntityType()) {
            return new LdapOrmQueryRequest<T>(query, desc, this.ldapQueryEngine);
        }
        if (desc.isAutoFetchTunable() && !query.isSqlSelect()) {
            if (!this.autoFetchManager.tuneQuery(query)) {
                query.setDefaultSelectClause();
            }
        }
        if (query.selectAllForLazyLoadProperty() && DefaultServer.logger.isLoggable(Level.FINE)) {
            DefaultServer.logger.log(Level.FINE, "Using selectAllForLazyLoadProperty");
        }
        if (query.getParentNode() == null) {
            final CallStack callStack = this.createCallStack();
            query.setOrigin(callStack);
        }
        if (query.initManyWhereJoins()) {
            query.setDistinct(true);
        }
        boolean allowOneManyFetch = true;
        if (SpiQuery.Mode.LAZYLOAD_MANY.equals(query.getMode())) {
            allowOneManyFetch = false;
        }
        else if (query.hasMaxRowsOrFirstRow() && !query.isRawSql() && !query.isSqlSelect() && query.getBackgroundFetchAfter() == 0) {
            allowOneManyFetch = false;
        }
        query.convertManyFetchJoinsToQueryJoins(allowOneManyFetch, this.queryBatchSize);
        final SpiTransaction serverTrans = (SpiTransaction)t;
        final OrmQueryRequest<T> request = new OrmQueryRequest<T>(this, this.queryEngine, query, desc, serverTrans);
        final BeanQueryAdapter queryAdapter = desc.getQueryAdapter();
        if (queryAdapter != null) {
            queryAdapter.preQuery(request);
        }
        request.calculateQueryPlanHash();
        return request;
    }
    
    private <T> T findId(final Query<T> query, final Transaction t) {
        final SpiOrmQueryRequest<T> request = this.createQueryRequest(Query.Type.BEAN, query, t);
        T bean = request.getFromPersistenceContextOrCache();
        if (bean != null) {
            return bean;
        }
        try {
            request.initTransIfRequired();
            bean = (T)request.findId();
            request.endTransIfRequired();
            return bean;
        }
        catch (RuntimeException ex) {
            request.rollbackTransIfRequired();
            throw ex;
        }
    }
    
    public <T> T findUnique(final Query<T> query, final Transaction t) {
        final SpiQuery<T> q = (SpiQuery<T>)(SpiQuery)query;
        final Object id = q.getId();
        if (id != null) {
            return (T)this.findId((Query<Object>)query, t);
        }
        final List<T> list = this.findList(query, t);
        if (list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            final String m = "Unique expecting 0 or 1 rows but got [" + list.size() + "]";
            throw new PersistenceException(m);
        }
        return list.get(0);
    }
    
    public <T> Set<T> findSet(final Query<T> query, final Transaction t) {
        final SpiOrmQueryRequest request = this.createQueryRequest(Query.Type.SET, query, t);
        final Object result = request.getFromQueryCache();
        if (result != null) {
            return (Set<T>)result;
        }
        try {
            request.initTransIfRequired();
            final Set<T> set = (Set<T>)request.findSet();
            request.endTransIfRequired();
            return set;
        }
        catch (RuntimeException ex) {
            request.rollbackTransIfRequired();
            throw ex;
        }
    }
    
    public <T> Map<?, T> findMap(final Query<T> query, final Transaction t) {
        final SpiOrmQueryRequest request = this.createQueryRequest(Query.Type.MAP, query, t);
        final Object result = request.getFromQueryCache();
        if (result != null) {
            return (Map<?, T>)result;
        }
        try {
            request.initTransIfRequired();
            final Map<?, T> map = (Map<?, T>)request.findMap();
            request.endTransIfRequired();
            return map;
        }
        catch (RuntimeException ex) {
            request.rollbackTransIfRequired();
            throw ex;
        }
    }
    
    public <T> int findRowCount(final Query<T> query, final Transaction t) {
        final SpiQuery<T> copy = ((SpiQuery)query).copy();
        return this.findRowCountWithCopy((Query<Object>)copy, t);
    }
    
    public <T> int findRowCountWithCopy(final Query<T> query, final Transaction t) {
        final SpiOrmQueryRequest<T> request = this.createQueryRequest(Query.Type.ROWCOUNT, query, t);
        try {
            request.initTransIfRequired();
            final int rowCount = request.findRowCount();
            request.endTransIfRequired();
            return rowCount;
        }
        catch (RuntimeException ex) {
            request.rollbackTransIfRequired();
            throw ex;
        }
    }
    
    public <T> List<Object> findIds(final Query<T> query, final Transaction t) {
        final SpiQuery<T> copy = ((SpiQuery)query).copy();
        return this.findIdsWithCopy((Query<Object>)copy, t);
    }
    
    public <T> List<Object> findIdsWithCopy(final Query<T> query, final Transaction t) {
        final SpiOrmQueryRequest<T> request = this.createQueryRequest(Query.Type.ID_LIST, query, t);
        try {
            request.initTransIfRequired();
            final List<Object> list = request.findIds();
            request.endTransIfRequired();
            return list;
        }
        catch (RuntimeException ex) {
            request.rollbackTransIfRequired();
            throw ex;
        }
    }
    
    public <T> FutureRowCount<T> findFutureRowCount(final Query<T> q, final Transaction t) {
        final SpiQuery<T> copy = ((SpiQuery)q).copy();
        copy.setFutureFetch(true);
        final Transaction newTxn = this.createTransaction();
        final CallableQueryRowCount<T> call = new CallableQueryRowCount<T>(this, copy, newTxn);
        final FutureTask<Integer> futureTask = new FutureTask<Integer>(call);
        final QueryFutureRowCount<T> queryFuture = new QueryFutureRowCount<T>(copy, futureTask);
        this.backgroundExecutor.execute(futureTask);
        return queryFuture;
    }
    
    public <T> FutureIds<T> findFutureIds(final Query<T> query, final Transaction t) {
        final SpiQuery<T> copy = ((SpiQuery)query).copy();
        copy.setFutureFetch(true);
        final List<Object> idList = Collections.synchronizedList(new ArrayList<Object>());
        copy.setIdList(idList);
        final Transaction newTxn = this.createTransaction();
        final CallableQueryIds<T> call = new CallableQueryIds<T>(this, copy, newTxn);
        final FutureTask<List<Object>> futureTask = new FutureTask<List<Object>>(call);
        final QueryFutureIds<T> queryFuture = new QueryFutureIds<T>(copy, futureTask);
        this.backgroundExecutor.execute(futureTask);
        return queryFuture;
    }
    
    public <T> FutureList<T> findFutureList(final Query<T> query, final Transaction t) {
        final SpiQuery<T> spiQuery = (SpiQuery<T>)(SpiQuery)query;
        spiQuery.setFutureFetch(true);
        if (spiQuery.getPersistenceContext() == null) {
            if (t != null) {
                spiQuery.setPersistenceContext(((SpiTransaction)t).getPersistenceContext());
            }
            else {
                final SpiTransaction st = this.getCurrentServerTransaction();
                if (st != null) {
                    spiQuery.setPersistenceContext(st.getPersistenceContext());
                }
            }
        }
        final Transaction newTxn = this.createTransaction();
        final CallableQueryList<T> call = new CallableQueryList<T>(this, query, newTxn);
        final FutureTask<List<T>> futureTask = new FutureTask<List<T>>(call);
        this.backgroundExecutor.execute(futureTask);
        return new QueryFutureList<T>(query, futureTask);
    }
    
    public <T> PagingList<T> findPagingList(final Query<T> query, final Transaction t, final int pageSize) {
        final SpiQuery<T> spiQuery = (SpiQuery<T>)(SpiQuery)query;
        PersistenceContext pc = spiQuery.getPersistenceContext();
        if (pc == null) {
            final SpiTransaction currentTransaction = this.getCurrentServerTransaction();
            if (currentTransaction != null) {
                pc = currentTransaction.getPersistenceContext();
            }
            if (pc == null) {
                pc = new DefaultPersistenceContext();
            }
            spiQuery.setPersistenceContext(pc);
        }
        return new LimitOffsetPagingQuery<T>(this, spiQuery, pageSize);
    }
    
    public <T> void findVisit(final Query<T> query, final QueryResultVisitor<T> visitor, final Transaction t) {
        final SpiOrmQueryRequest<T> request = this.createQueryRequest(Query.Type.LIST, query, t);
        try {
            request.initTransIfRequired();
            request.findVisit(visitor);
        }
        catch (RuntimeException ex) {
            request.rollbackTransIfRequired();
            throw ex;
        }
    }
    
    public <T> QueryIterator<T> findIterate(final Query<T> query, final Transaction t) {
        final SpiOrmQueryRequest<T> request = this.createQueryRequest(Query.Type.LIST, query, t);
        try {
            request.initTransIfRequired();
            return request.findIterate();
        }
        catch (RuntimeException ex) {
            request.rollbackTransIfRequired();
            throw ex;
        }
    }
    
    public <T> List<T> findList(final Query<T> query, final Transaction t) {
        final SpiOrmQueryRequest<T> request = this.createQueryRequest(Query.Type.LIST, query, t);
        final Object result = request.getFromQueryCache();
        if (result != null) {
            return (List<T>)result;
        }
        try {
            request.initTransIfRequired();
            final List<T> list = request.findList();
            request.endTransIfRequired();
            return list;
        }
        catch (RuntimeException ex) {
            request.rollbackTransIfRequired();
            throw ex;
        }
    }
    
    public SqlRow findUnique(final SqlQuery query, final Transaction t) {
        final List<SqlRow> list = this.findList(query, t);
        if (list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            final String m = "Unique expecting 0 or 1 rows but got [" + list.size() + "]";
            throw new PersistenceException(m);
        }
        return list.get(0);
    }
    
    public SqlFutureList findFutureList(final SqlQuery query, final Transaction t) {
        final SpiSqlQuery spiQuery = (SpiSqlQuery)query;
        spiQuery.setFutureFetch(true);
        final Transaction newTxn = this.createTransaction();
        final CallableSqlQueryList call = new CallableSqlQueryList(this, query, newTxn);
        final FutureTask<List<SqlRow>> futureTask = new FutureTask<List<SqlRow>>(call);
        this.backgroundExecutor.execute(futureTask);
        return new SqlQueryFutureList(query, futureTask);
    }
    
    public List<SqlRow> findList(final SqlQuery query, final Transaction t) {
        final RelationalQueryRequest request = new RelationalQueryRequest(this, this.relationalQueryEngine, query, t);
        try {
            request.initTransIfRequired();
            final List<SqlRow> list = request.findList();
            request.endTransIfRequired();
            return list;
        }
        catch (RuntimeException ex) {
            request.rollbackTransIfRequired();
            throw ex;
        }
    }
    
    public Set<SqlRow> findSet(final SqlQuery query, final Transaction t) {
        final RelationalQueryRequest request = new RelationalQueryRequest(this, this.relationalQueryEngine, query, t);
        try {
            request.initTransIfRequired();
            final Set<SqlRow> set = request.findSet();
            request.endTransIfRequired();
            return set;
        }
        catch (RuntimeException ex) {
            request.rollbackTransIfRequired();
            throw ex;
        }
    }
    
    public Map<?, SqlRow> findMap(final SqlQuery query, final Transaction t) {
        final RelationalQueryRequest request = new RelationalQueryRequest(this, this.relationalQueryEngine, query, t);
        try {
            request.initTransIfRequired();
            final Map<?, SqlRow> map = request.findMap();
            request.endTransIfRequired();
            return map;
        }
        catch (RuntimeException ex) {
            request.rollbackTransIfRequired();
            throw ex;
        }
    }
    
    public void save(final Object bean) {
        this.save(bean, null);
    }
    
    public void save(final Object bean, final Transaction t) {
        if (bean == null) {
            throw new NullPointerException(Message.msg("bean.isnull"));
        }
        this.persister.save(bean, t);
    }
    
    public void update(final Object bean) {
        this.update(bean, null, null);
    }
    
    public void update(final Object bean, final Set<String> updateProps) {
        this.update(bean, updateProps, null);
    }
    
    public void update(final Object bean, final Transaction t) {
        this.update(bean, null, t);
    }
    
    public void update(final Object bean, final Set<String> updateProps, final Transaction t) {
        this.update(bean, updateProps, t, this.defaultDeleteMissingChildren, this.defaultUpdateNullProperties);
    }
    
    public void update(final Object bean, final Set<String> updateProps, final Transaction t, final boolean deleteMissingChildren, final boolean updateNullProperties) {
        if (bean == null) {
            throw new NullPointerException(Message.msg("bean.isnull"));
        }
        this.persister.forceUpdate(bean, updateProps, t, deleteMissingChildren, updateNullProperties);
    }
    
    public void insert(final Object bean) {
        this.insert(bean, null);
    }
    
    public void insert(final Object bean, final Transaction t) {
        if (bean == null) {
            throw new NullPointerException(Message.msg("bean.isnull"));
        }
        this.persister.forceInsert(bean, t);
    }
    
    public int deleteManyToManyAssociations(final Object ownerBean, final String propertyName) {
        return this.deleteManyToManyAssociations(ownerBean, propertyName, null);
    }
    
    public int deleteManyToManyAssociations(final Object ownerBean, final String propertyName, final Transaction t) {
        final TransWrapper wrap = this.initTransIfRequired(t);
        try {
            final SpiTransaction trans = wrap.transaction;
            final int rc = this.persister.deleteManyToManyAssociations(ownerBean, propertyName, trans);
            wrap.commitIfCreated();
            return rc;
        }
        catch (RuntimeException e) {
            wrap.rollbackIfCreated();
            throw e;
        }
    }
    
    public void saveManyToManyAssociations(final Object ownerBean, final String propertyName) {
        this.saveManyToManyAssociations(ownerBean, propertyName, null);
    }
    
    public void saveManyToManyAssociations(final Object ownerBean, final String propertyName, final Transaction t) {
        final TransWrapper wrap = this.initTransIfRequired(t);
        try {
            final SpiTransaction trans = wrap.transaction;
            this.persister.saveManyToManyAssociations(ownerBean, propertyName, trans);
            wrap.commitIfCreated();
        }
        catch (RuntimeException e) {
            wrap.rollbackIfCreated();
            throw e;
        }
    }
    
    public void saveAssociation(final Object ownerBean, final String propertyName) {
        this.saveAssociation(ownerBean, propertyName, null);
    }
    
    public void saveAssociation(final Object ownerBean, final String propertyName, final Transaction t) {
        if (ownerBean instanceof EntityBean) {
            final Set<String> loadedProps = ((EntityBean)ownerBean)._ebean_getIntercept().getLoadedProps();
            if (loadedProps != null && !loadedProps.contains(propertyName)) {
                DefaultServer.logger.fine("Skip saveAssociation as property " + propertyName + " is not loaded");
                return;
            }
        }
        final TransWrapper wrap = this.initTransIfRequired(t);
        try {
            final SpiTransaction trans = wrap.transaction;
            this.persister.saveAssociation(ownerBean, propertyName, trans);
            wrap.commitIfCreated();
        }
        catch (RuntimeException e) {
            wrap.rollbackIfCreated();
            throw e;
        }
    }
    
    public int save(final Iterator<?> it) {
        return this.save(it, null);
    }
    
    public int save(final Collection<?> c) {
        return this.save(c.iterator(), null);
    }
    
    public int save(final Iterator<?> it, final Transaction t) {
        final TransWrapper wrap = this.initTransIfRequired(t);
        try {
            final SpiTransaction trans = wrap.transaction;
            int saveCount = 0;
            while (it.hasNext()) {
                final Object bean = it.next();
                this.persister.save(bean, trans);
                ++saveCount;
            }
            wrap.commitIfCreated();
            return saveCount;
        }
        catch (RuntimeException e) {
            wrap.rollbackIfCreated();
            throw e;
        }
    }
    
    public int delete(final Class<?> beanType, final Object id) {
        return this.delete(beanType, id, null);
    }
    
    public int delete(final Class<?> beanType, final Object id, final Transaction t) {
        final TransWrapper wrap = this.initTransIfRequired(t);
        try {
            final SpiTransaction trans = wrap.transaction;
            final int rowCount = this.persister.delete(beanType, id, trans);
            wrap.commitIfCreated();
            return rowCount;
        }
        catch (RuntimeException e) {
            wrap.rollbackIfCreated();
            throw e;
        }
    }
    
    public void delete(final Class<?> beanType, final Collection<?> ids) {
        this.delete(beanType, ids, null);
    }
    
    public void delete(final Class<?> beanType, final Collection<?> ids, final Transaction t) {
        final TransWrapper wrap = this.initTransIfRequired(t);
        try {
            final SpiTransaction trans = wrap.transaction;
            this.persister.deleteMany(beanType, ids, trans);
            wrap.commitIfCreated();
        }
        catch (RuntimeException e) {
            wrap.rollbackIfCreated();
            throw e;
        }
    }
    
    public void delete(final Object bean) {
        this.delete(bean, null);
    }
    
    public void delete(final Object bean, final Transaction t) {
        if (bean == null) {
            throw new NullPointerException(Message.msg("bean.isnull"));
        }
        this.persister.delete(bean, t);
    }
    
    public int delete(final Iterator<?> it) {
        return this.delete(it, null);
    }
    
    public int delete(final Collection<?> c) {
        return this.delete(c.iterator(), null);
    }
    
    public int delete(final Iterator<?> it, final Transaction t) {
        final TransWrapper wrap = this.initTransIfRequired(t);
        try {
            final SpiTransaction trans = wrap.transaction;
            int deleteCount = 0;
            while (it.hasNext()) {
                final Object bean = it.next();
                this.persister.delete(bean, trans);
                ++deleteCount;
            }
            wrap.commitIfCreated();
            return deleteCount;
        }
        catch (RuntimeException e) {
            wrap.rollbackIfCreated();
            throw e;
        }
    }
    
    public int execute(final CallableSql callSql, final Transaction t) {
        return this.persister.executeCallable(callSql, t);
    }
    
    public int execute(final CallableSql callSql) {
        return this.execute(callSql, null);
    }
    
    public int execute(final SqlUpdate updSql, final Transaction t) {
        return this.persister.executeSqlUpdate(updSql, t);
    }
    
    public int execute(final SqlUpdate updSql) {
        return this.execute(updSql, null);
    }
    
    public int execute(final Update<?> update, final Transaction t) {
        return this.persister.executeOrmUpdate(update, t);
    }
    
    public int execute(final Update<?> update) {
        return this.execute(update, null);
    }
    
    public <T> BeanManager<T> getBeanManager(final Class<T> beanClass) {
        return this.beanDescriptorManager.getBeanManager(beanClass);
    }
    
    public List<BeanDescriptor<?>> getBeanDescriptors() {
        return this.beanDescriptorManager.getBeanDescriptorList();
    }
    
    public void register(final BeanPersistController c) {
        final List<BeanDescriptor<?>> list = this.beanDescriptorManager.getBeanDescriptorList();
        for (int i = 0; i < list.size(); ++i) {
            list.get(i).register(c);
        }
    }
    
    public void deregister(final BeanPersistController c) {
        final List<BeanDescriptor<?>> list = this.beanDescriptorManager.getBeanDescriptorList();
        for (int i = 0; i < list.size(); ++i) {
            list.get(i).deregister(c);
        }
    }
    
    public boolean isSupportedType(final Type genericType) {
        final ParamTypeHelper.TypeInfo typeInfo = ParamTypeHelper.getTypeInfo(genericType);
        return typeInfo != null && this.getBeanDescriptor(typeInfo.getBeanType()) != null;
    }
    
    public Object getBeanId(final Object bean) {
        final BeanDescriptor<?> desc = this.getBeanDescriptor(bean.getClass());
        if (desc == null) {
            final String m = bean.getClass().getName() + " is NOT an Entity Bean registered with this server?";
            throw new PersistenceException(m);
        }
        return desc.getId(bean);
    }
    
    public <T> BeanDescriptor<T> getBeanDescriptor(final Class<T> beanClass) {
        return this.beanDescriptorManager.getBeanDescriptor(beanClass);
    }
    
    public List<BeanDescriptor<?>> getBeanDescriptors(final String tableName) {
        return this.beanDescriptorManager.getBeanDescriptors(tableName);
    }
    
    public BeanDescriptor<?> getBeanDescriptorById(final String descriptorId) {
        return this.beanDescriptorManager.getBeanDescriptorById(descriptorId);
    }
    
    public void remoteTransactionEvent(final RemoteTransactionEvent event) {
        this.transactionManager.remoteTransactionEvent(event);
    }
    
    TransWrapper initTransIfRequired(final Transaction t) {
        if (t != null) {
            return new TransWrapper((SpiTransaction)t, false);
        }
        boolean wasCreated = false;
        SpiTransaction trans = this.transactionScopeManager.get();
        if (trans == null) {
            trans = this.transactionManager.createTransaction(false, -1);
            wasCreated = true;
        }
        return new TransWrapper(trans, wasCreated);
    }
    
    public SpiTransaction createServerTransaction(final boolean isExplicit, final int isolationLevel) {
        return this.transactionManager.createTransaction(isExplicit, isolationLevel);
    }
    
    public SpiTransaction createQueryTransaction() {
        return this.transactionManager.createQueryTransaction();
    }
    
    public CallStack createCallStack() {
        StackTraceElement[] stackTrace;
        int startIndex;
        for (stackTrace = Thread.currentThread().getStackTrace(), startIndex = 5; startIndex < stackTrace.length && stackTrace[startIndex].getClassName().startsWith(DefaultServer.AVAJE_EBEAN); ++startIndex) {}
        int stackLength = stackTrace.length - startIndex;
        if (stackLength > this.maxCallStack) {
            stackLength = this.maxCallStack;
        }
        final StackTraceElement[] finalTrace = new StackTraceElement[stackLength];
        for (int i = 0; i < stackLength; ++i) {
            finalTrace[i] = stackTrace[i + startIndex];
        }
        if (stackLength < 1) {
            throw new RuntimeException("StackTraceElement size 0?  stack: " + Arrays.toString(stackTrace));
        }
        return new CallStack(finalTrace);
    }
    
    public JsonContext createJsonContext() {
        return this.jsonContext;
    }
    
    static {
        logger = Logger.getLogger(DefaultServer.class.getName());
        EMPTY_INVALID_VALUES = new InvalidValue[0];
        AVAJE_EBEAN = Ebean.class.getName().substring(0, 15);
    }
    
    private final class Shutdown implements Runnable
    {
        public void run() {
            try {
                if (DefaultServer.this.mbeanServer != null) {
                    DefaultServer.this.mbeanServer.unregisterMBean(new ObjectName(DefaultServer.this.mbeanName + ",function=Logging"));
                    DefaultServer.this.mbeanServer.unregisterMBean(new ObjectName(DefaultServer.this.mbeanName + ",key=AutoFetch"));
                }
            }
            catch (Exception e) {
                final String msg = "Error unregistering Ebean " + DefaultServer.this.mbeanName;
                DefaultServer.logger.log(Level.SEVERE, msg, e);
            }
            DefaultServer.this.transactionManager.shutdown();
            DefaultServer.this.autoFetchManager.shutdown();
        }
    }
}
