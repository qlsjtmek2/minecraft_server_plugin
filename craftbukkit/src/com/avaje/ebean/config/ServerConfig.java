// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.config;

import com.avaje.ebeaninternal.server.core.DetectLucene;
import com.avaje.ebean.config.ldap.LdapContextFactory;
import com.avaje.ebeaninternal.api.ClassUtil;
import java.util.ArrayList;
import com.avaje.ebean.config.lucene.LuceneConfig;
import com.avaje.ebean.config.ldap.LdapConfig;
import com.avaje.ebean.config.dbplatform.DbEncrypt;
import com.avaje.ebean.event.BeanQueryAdapter;
import com.avaje.ebean.event.BeanPersistListener;
import com.avaje.ebean.event.BeanPersistController;
import javax.sql.DataSource;
import com.avaje.ebean.LogLevel;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import java.util.List;

public class ServerConfig
{
    private static final int DEFAULT_QUERY_BATCH_SIZE = 100;
    private String name;
    private String resourceDirectory;
    private int enhanceLogLevel;
    private boolean register;
    private boolean defaultServer;
    private boolean validateOnSave;
    private List<Class<?>> classes;
    private List<String> packages;
    private List<String> searchJars;
    private AutofetchConfig autofetchConfig;
    private String databasePlatformName;
    private DatabasePlatform databasePlatform;
    private int databaseSequenceBatchSize;
    private boolean persistBatching;
    private int persistBatchSize;
    private int lazyLoadBatchSize;
    private int queryBatchSize;
    private boolean ddlGenerate;
    private boolean ddlRun;
    private boolean debugSql;
    private boolean debugLazyLoad;
    private boolean useJtaTransactionManager;
    private ExternalTransactionManager externalTransactionManager;
    private boolean loggingToJavaLogger;
    private String loggingDirectory;
    private LogLevel loggingLevel;
    private PstmtDelegate pstmtDelegate;
    private DataSource dataSource;
    private DataSourceConfig dataSourceConfig;
    private String dataSourceJndiName;
    private String databaseBooleanTrue;
    private String databaseBooleanFalse;
    private NamingConvention namingConvention;
    private boolean updateChangesOnly;
    private List<BeanPersistController> persistControllers;
    private List<BeanPersistListener<?>> persistListeners;
    private List<BeanQueryAdapter> queryAdapters;
    private EncryptKeyManager encryptKeyManager;
    private EncryptDeployManager encryptDeployManager;
    private Encryptor encryptor;
    private DbEncrypt dbEncrypt;
    private LdapConfig ldapConfig;
    private LuceneConfig luceneConfig;
    private boolean vanillaMode;
    private boolean vanillaRefMode;
    
    public ServerConfig() {
        this.register = true;
        this.validateOnSave = true;
        this.classes = new ArrayList<Class<?>>();
        this.packages = new ArrayList<String>();
        this.searchJars = new ArrayList<String>();
        this.autofetchConfig = new AutofetchConfig();
        this.databaseSequenceBatchSize = 20;
        this.persistBatchSize = 20;
        this.lazyLoadBatchSize = 1;
        this.queryBatchSize = -1;
        this.loggingDirectory = "logs";
        this.loggingLevel = LogLevel.NONE;
        this.dataSourceConfig = new DataSourceConfig();
        this.updateChangesOnly = true;
        this.persistControllers = new ArrayList<BeanPersistController>();
        this.persistListeners = new ArrayList<BeanPersistListener<?>>();
        this.queryAdapters = new ArrayList<BeanQueryAdapter>();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public boolean isRegister() {
        return this.register;
    }
    
    public void setRegister(final boolean register) {
        this.register = register;
    }
    
    public boolean isDefaultServer() {
        return this.defaultServer;
    }
    
    public void setDefaultServer(final boolean defaultServer) {
        this.defaultServer = defaultServer;
    }
    
    public boolean isPersistBatching() {
        return this.persistBatching;
    }
    
    public boolean isUsePersistBatching() {
        return this.persistBatching;
    }
    
    public void setPersistBatching(final boolean persistBatching) {
        this.persistBatching = persistBatching;
    }
    
    public void setUsePersistBatching(final boolean persistBatching) {
        this.persistBatching = persistBatching;
    }
    
    public int getPersistBatchSize() {
        return this.persistBatchSize;
    }
    
    public void setPersistBatchSize(final int persistBatchSize) {
        this.persistBatchSize = persistBatchSize;
    }
    
    public int getLazyLoadBatchSize() {
        return this.lazyLoadBatchSize;
    }
    
    public int getQueryBatchSize() {
        return this.queryBatchSize;
    }
    
    public void setQueryBatchSize(final int queryBatchSize) {
        this.queryBatchSize = queryBatchSize;
    }
    
    public void setLazyLoadBatchSize(final int lazyLoadBatchSize) {
        this.lazyLoadBatchSize = lazyLoadBatchSize;
    }
    
    public void setDatabaseSequenceBatchSize(final int databaseSequenceBatchSize) {
        this.databaseSequenceBatchSize = databaseSequenceBatchSize;
    }
    
    public boolean isUseJtaTransactionManager() {
        return this.useJtaTransactionManager;
    }
    
    public void setUseJtaTransactionManager(final boolean useJtaTransactionManager) {
        this.useJtaTransactionManager = useJtaTransactionManager;
    }
    
    public ExternalTransactionManager getExternalTransactionManager() {
        return this.externalTransactionManager;
    }
    
    public void setExternalTransactionManager(final ExternalTransactionManager externalTransactionManager) {
        this.externalTransactionManager = externalTransactionManager;
    }
    
    public boolean isVanillaMode() {
        return this.vanillaMode;
    }
    
    public void setVanillaMode(final boolean vanillaMode) {
        this.vanillaMode = vanillaMode;
    }
    
    public boolean isVanillaRefMode() {
        return this.vanillaRefMode;
    }
    
    public void setVanillaRefMode(final boolean vanillaRefMode) {
        this.vanillaRefMode = vanillaRefMode;
    }
    
    public boolean isValidateOnSave() {
        return this.validateOnSave;
    }
    
    public void setValidateOnSave(final boolean validateOnSave) {
        this.validateOnSave = validateOnSave;
    }
    
    public int getEnhanceLogLevel() {
        return this.enhanceLogLevel;
    }
    
    public void setEnhanceLogLevel(final int enhanceLogLevel) {
        this.enhanceLogLevel = enhanceLogLevel;
    }
    
    public NamingConvention getNamingConvention() {
        return this.namingConvention;
    }
    
    public void setNamingConvention(final NamingConvention namingConvention) {
        this.namingConvention = namingConvention;
    }
    
    public AutofetchConfig getAutofetchConfig() {
        return this.autofetchConfig;
    }
    
    public void setAutofetchConfig(final AutofetchConfig autofetchConfig) {
        this.autofetchConfig = autofetchConfig;
    }
    
    public PstmtDelegate getPstmtDelegate() {
        return this.pstmtDelegate;
    }
    
    public void setPstmtDelegate(final PstmtDelegate pstmtDelegate) {
        this.pstmtDelegate = pstmtDelegate;
    }
    
    public DataSource getDataSource() {
        return this.dataSource;
    }
    
    public void setDataSource(final DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
    public DataSourceConfig getDataSourceConfig() {
        return this.dataSourceConfig;
    }
    
    public void setDataSourceConfig(final DataSourceConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }
    
    public String getDataSourceJndiName() {
        return this.dataSourceJndiName;
    }
    
    public void setDataSourceJndiName(final String dataSourceJndiName) {
        this.dataSourceJndiName = dataSourceJndiName;
    }
    
    public String getDatabaseBooleanTrue() {
        return this.databaseBooleanTrue;
    }
    
    public void setDatabaseBooleanTrue(final String databaseTrue) {
        this.databaseBooleanTrue = databaseTrue;
    }
    
    public String getDatabaseBooleanFalse() {
        return this.databaseBooleanFalse;
    }
    
    public void setDatabaseBooleanFalse(final String databaseFalse) {
        this.databaseBooleanFalse = databaseFalse;
    }
    
    public int getDatabaseSequenceBatchSize() {
        return this.databaseSequenceBatchSize;
    }
    
    public void setDatabaseSequenceBatch(final int databaseSequenceBatchSize) {
        this.databaseSequenceBatchSize = databaseSequenceBatchSize;
    }
    
    public String getDatabasePlatformName() {
        return this.databasePlatformName;
    }
    
    public void setDatabasePlatformName(final String databasePlatformName) {
        this.databasePlatformName = databasePlatformName;
    }
    
    public DatabasePlatform getDatabasePlatform() {
        return this.databasePlatform;
    }
    
    public void setDatabasePlatform(final DatabasePlatform databasePlatform) {
        this.databasePlatform = databasePlatform;
    }
    
    public EncryptKeyManager getEncryptKeyManager() {
        return this.encryptKeyManager;
    }
    
    public void setEncryptKeyManager(final EncryptKeyManager encryptKeyManager) {
        this.encryptKeyManager = encryptKeyManager;
    }
    
    public EncryptDeployManager getEncryptDeployManager() {
        return this.encryptDeployManager;
    }
    
    public void setEncryptDeployManager(final EncryptDeployManager encryptDeployManager) {
        this.encryptDeployManager = encryptDeployManager;
    }
    
    public Encryptor getEncryptor() {
        return this.encryptor;
    }
    
    public void setEncryptor(final Encryptor encryptor) {
        this.encryptor = encryptor;
    }
    
    public DbEncrypt getDbEncrypt() {
        return this.dbEncrypt;
    }
    
    public void setDbEncrypt(final DbEncrypt dbEncrypt) {
        this.dbEncrypt = dbEncrypt;
    }
    
    public boolean isDebugSql() {
        return this.debugSql;
    }
    
    public void setDebugSql(final boolean debugSql) {
        this.debugSql = debugSql;
    }
    
    public boolean isDebugLazyLoad() {
        return this.debugLazyLoad;
    }
    
    public void setDebugLazyLoad(final boolean debugLazyLoad) {
        this.debugLazyLoad = debugLazyLoad;
    }
    
    public LogLevel getLoggingLevel() {
        return this.loggingLevel;
    }
    
    public void setLoggingLevel(final LogLevel logLevel) {
        this.loggingLevel = logLevel;
    }
    
    public String getLoggingDirectory() {
        return this.loggingDirectory;
    }
    
    public String getLoggingDirectoryWithEval() {
        return GlobalProperties.evaluateExpressions(this.loggingDirectory);
    }
    
    public void setLoggingDirectory(final String loggingDirectory) {
        this.loggingDirectory = loggingDirectory;
    }
    
    public boolean isLoggingToJavaLogger() {
        return this.loggingToJavaLogger;
    }
    
    public void setLoggingToJavaLogger(final boolean transactionLogToJavaLogger) {
        this.loggingToJavaLogger = transactionLogToJavaLogger;
    }
    
    public boolean isUseJuliTransactionLogger() {
        return this.isLoggingToJavaLogger();
    }
    
    public void setUseJuliTransactionLogger(final boolean transactionLogToJavaLogger) {
        this.setLoggingToJavaLogger(transactionLogToJavaLogger);
    }
    
    public void setDdlGenerate(final boolean ddlGenerate) {
        this.ddlGenerate = ddlGenerate;
    }
    
    public void setDdlRun(final boolean ddlRun) {
        this.ddlRun = ddlRun;
    }
    
    public boolean isDdlGenerate() {
        return this.ddlGenerate;
    }
    
    public boolean isDdlRun() {
        return this.ddlRun;
    }
    
    public LdapConfig getLdapConfig() {
        return this.ldapConfig;
    }
    
    public void setLdapConfig(final LdapConfig ldapConfig) {
        this.ldapConfig = ldapConfig;
    }
    
    public LuceneConfig getLuceneConfig() {
        return this.luceneConfig;
    }
    
    public void setLuceneConfig(final LuceneConfig luceneConfig) {
        this.luceneConfig = luceneConfig;
    }
    
    public void addClass(final Class<?> cls) {
        if (this.classes == null) {
            this.classes = new ArrayList<Class<?>>();
        }
        this.classes.add(cls);
    }
    
    public void addPackage(final String packageName) {
        if (this.packages == null) {
            this.packages = new ArrayList<String>();
        }
        this.packages.add(packageName);
    }
    
    public List<String> getPackages() {
        return this.packages;
    }
    
    public void setPackages(final List<String> packages) {
        this.packages = packages;
    }
    
    public void addJar(final String jarName) {
        if (this.searchJars == null) {
            this.searchJars = new ArrayList<String>();
        }
        this.searchJars.add(jarName);
    }
    
    public List<String> getJars() {
        return this.searchJars;
    }
    
    public void setJars(final List<String> searchJars) {
        this.searchJars = searchJars;
    }
    
    public void setClasses(final List<Class<?>> classes) {
        this.classes = classes;
    }
    
    public List<Class<?>> getClasses() {
        return this.classes;
    }
    
    public boolean isUpdateChangesOnly() {
        return this.updateChangesOnly;
    }
    
    public void setUpdateChangesOnly(final boolean updateChangesOnly) {
        this.updateChangesOnly = updateChangesOnly;
    }
    
    public String getResourceDirectory() {
        return this.resourceDirectory;
    }
    
    public void setResourceDirectory(final String resourceDirectory) {
        this.resourceDirectory = resourceDirectory;
    }
    
    public void add(final BeanQueryAdapter beanQueryAdapter) {
        this.queryAdapters.add(beanQueryAdapter);
    }
    
    public List<BeanQueryAdapter> getQueryAdapters() {
        return this.queryAdapters;
    }
    
    public void setQueryAdapters(final List<BeanQueryAdapter> queryAdapters) {
        this.queryAdapters = queryAdapters;
    }
    
    public void add(final BeanPersistController beanPersistController) {
        this.persistControllers.add(beanPersistController);
    }
    
    public List<BeanPersistController> getPersistControllers() {
        return this.persistControllers;
    }
    
    public void setPersistControllers(final List<BeanPersistController> persistControllers) {
        this.persistControllers = persistControllers;
    }
    
    public void add(final BeanPersistListener<?> beanPersistListener) {
        this.persistListeners.add(beanPersistListener);
    }
    
    public List<BeanPersistListener<?>> getPersistListeners() {
        return this.persistListeners;
    }
    
    public void setPersistListeners(final List<BeanPersistListener<?>> persistListeners) {
        this.persistListeners = persistListeners;
    }
    
    public void loadFromProperties() {
        final ConfigPropertyMap p = new ConfigPropertyMap(this.name);
        this.loadSettings(p);
    }
    
    public GlobalProperties.PropertySource getPropertySource() {
        return GlobalProperties.getPropertySource(this.name);
    }
    
    public String getProperty(final String propertyName, final String defaultValue) {
        final GlobalProperties.PropertySource p = new ConfigPropertyMap(this.name);
        return p.get(propertyName, defaultValue);
    }
    
    public String getProperty(final String propertyName) {
        return this.getProperty(propertyName, null);
    }
    
    private <T> T createInstance(final GlobalProperties.PropertySource p, final Class<T> type, final String key) {
        final String classname = p.get(key, null);
        if (classname == null) {
            return null;
        }
        try {
            final Class<?> cls = ClassUtil.forName(classname, this.getClass());
            return (T)cls.newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private void loadSettings(final ConfigPropertyMap p) {
        if (this.autofetchConfig == null) {
            this.autofetchConfig = new AutofetchConfig();
        }
        this.autofetchConfig.loadSettings(p);
        if (this.dataSourceConfig == null) {
            this.dataSourceConfig = new DataSourceConfig();
        }
        this.dataSourceConfig.loadSettings(p.getServerName());
        if (this.ldapConfig == null) {
            final LdapContextFactory ctxFact = this.createInstance(p, LdapContextFactory.class, "ldapContextFactory");
            if (ctxFact != null) {
                (this.ldapConfig = new LdapConfig()).setContextFactory(ctxFact);
                this.ldapConfig.setVanillaMode(p.getBoolean("ldapVanillaMode", false));
            }
        }
        if (this.luceneConfig == null && DetectLucene.isPresent()) {
            (this.luceneConfig = new LuceneConfig()).loadSettings(this.name);
        }
        this.useJtaTransactionManager = p.getBoolean("useJtaTransactionManager", false);
        this.namingConvention = this.createNamingConvention(p);
        this.databasePlatform = this.createInstance(p, DatabasePlatform.class, "databasePlatform");
        this.encryptKeyManager = this.createInstance(p, EncryptKeyManager.class, "encryptKeyManager");
        this.encryptDeployManager = this.createInstance(p, EncryptDeployManager.class, "encryptDeployManager");
        this.encryptor = this.createInstance(p, Encryptor.class, "encryptor");
        this.dbEncrypt = this.createInstance(p, DbEncrypt.class, "dbEncrypt");
        final String jarsProp = p.get("search.jars", p.get("jars", null));
        if (jarsProp != null) {
            this.searchJars = this.getSearchJarsPackages(jarsProp);
        }
        final String packagesProp = p.get("search.packages", p.get("packages", null));
        if (this.packages != null) {
            this.packages = this.getSearchJarsPackages(packagesProp);
        }
        this.vanillaMode = p.getBoolean("vanillaMode", false);
        this.vanillaRefMode = p.getBoolean("vanillaRefMode", false);
        this.updateChangesOnly = p.getBoolean("updateChangesOnly", true);
        final boolean batchMode = p.getBoolean("batch.mode", false);
        this.persistBatching = p.getBoolean("persistBatching", batchMode);
        final int batchSize = p.getInt("batch.size", 20);
        this.persistBatchSize = p.getInt("persistBatchSize", batchSize);
        this.dataSourceJndiName = p.get("dataSourceJndiName", null);
        this.databaseSequenceBatchSize = p.getInt("databaseSequenceBatchSize", 20);
        this.databaseBooleanTrue = p.get("databaseBooleanTrue", null);
        this.databaseBooleanFalse = p.get("databaseBooleanFalse", null);
        this.databasePlatformName = p.get("databasePlatformName", null);
        this.lazyLoadBatchSize = p.getInt("lazyLoadBatchSize", 1);
        this.queryBatchSize = p.getInt("queryBatchSize", 100);
        this.ddlGenerate = p.getBoolean("ddl.generate", false);
        this.ddlRun = p.getBoolean("ddl.run", false);
        this.debugSql = p.getBoolean("debug.sql", false);
        this.debugLazyLoad = p.getBoolean("debug.lazyload", false);
        this.loggingLevel = this.getLogLevelValue(p);
        String s = p.get("useJuliTransactionLogger", null);
        s = p.get("loggingToJavaLogger", s);
        this.loggingToJavaLogger = "true".equalsIgnoreCase(s);
        s = p.get("log.directory", "logs");
        this.loggingDirectory = p.get("logging.directory", s);
        this.classes = this.getClasses(p);
    }
    
    private LogLevel getLogLevelValue(final ConfigPropertyMap p) {
        String logValue = p.get("logging", "NONE");
        logValue = p.get("log.level", logValue);
        logValue = p.get("logging.level", logValue);
        if (logValue.trim().equalsIgnoreCase("ALL")) {
            logValue = "SQL";
        }
        return Enum.valueOf(LogLevel.class, logValue.toUpperCase());
    }
    
    private NamingConvention createNamingConvention(final GlobalProperties.PropertySource p) {
        final NamingConvention nc = this.createInstance(p, NamingConvention.class, "namingconvention");
        if (nc == null) {
            return null;
        }
        if (nc instanceof AbstractNamingConvention) {
            final AbstractNamingConvention anc = (AbstractNamingConvention)nc;
            final String v = p.get("namingConvention.useForeignKeyPrefix", null);
            if (v != null) {
                final boolean useForeignKeyPrefix = Boolean.valueOf(v);
                anc.setUseForeignKeyPrefix(useForeignKeyPrefix);
            }
            final String sequenceFormat = p.get("namingConvention.sequenceFormat", null);
            if (sequenceFormat != null) {
                anc.setSequenceFormat(sequenceFormat);
            }
        }
        return nc;
    }
    
    private ArrayList<Class<?>> getClasses(final GlobalProperties.PropertySource p) {
        final String classNames = p.get("classes", null);
        if (classNames == null) {
            return null;
        }
        final ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        final String[] split = classNames.split("[ ,;]");
        for (int i = 0; i < split.length; ++i) {
            final String cn = split[i].trim();
            if (cn.length() > 0 && !"class".equalsIgnoreCase(cn)) {
                try {
                    classes.add(ClassUtil.forName(cn, this.getClass()));
                }
                catch (ClassNotFoundException e) {
                    final String msg = "Error registering class [" + cn + "] from [" + classNames + "]";
                    throw new RuntimeException(msg, e);
                }
            }
        }
        return classes;
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
}
