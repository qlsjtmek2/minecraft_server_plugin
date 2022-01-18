// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import java.io.Serializable;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
import com.avaje.ebeaninternal.server.reflect.BeanReflectSetter;
import com.avaje.ebeaninternal.server.reflect.BeanReflectGetter;
import com.avaje.ebeaninternal.server.reflect.BeanReflect;
import com.avaje.ebeaninternal.server.reflect.EnhanceBeanReflectFactory;
import javax.persistence.QueryHint;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import com.avaje.ebeaninternal.server.lib.util.Dnode;
import com.avaje.ebean.validation.factory.NotNullValidatorFactory;
import com.avaje.ebean.validation.factory.LengthValidatorFactory;
import com.avaje.ebeaninternal.server.core.Message;
import com.avaje.ebean.config.dbplatform.IdType;
import com.avaje.ebean.config.TableName;
import com.avaje.ebean.event.BeanFinder;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssoc;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.meta.DeployTableJoin;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyAssocOne;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanProperty;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanTable;
import com.avaje.ebean.config.dbplatform.IdGenerator;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import javax.persistence.PersistenceException;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebeaninternal.server.deploy.id.IdBinderEmbedded;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebean.config.lucene.IndexDefn;
import java.io.IOException;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import com.avaje.ebean.config.EncryptKey;
import java.util.logging.Level;
import java.util.Comparator;
import java.util.Collections;
import java.util.Collection;
import java.util.ArrayList;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import java.util.Iterator;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.server.subclass.SubClassUtil;
import com.avaje.ebeaninternal.server.core.InternString;
import java.util.HashMap;
import com.avaje.ebeaninternal.server.core.InternalConfiguration;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebeaninternal.server.core.XmlConfig;
import com.avaje.ebeaninternal.server.deploy.id.IdBinderFactory;
import com.avaje.ebean.config.EncryptKeyManager;
import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebean.cache.ServerCacheManager;
import com.avaje.ebeaninternal.server.idgen.UuidIdGenerator;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import javax.sql.DataSource;
import com.avaje.ebean.config.dbplatform.DbIdentity;
import java.util.Set;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.parse.DeployBeanInfo;
import java.util.Map;
import com.avaje.ebeaninternal.server.core.BootupClasses;
import java.util.HashSet;
import com.avaje.ebeaninternal.server.deploy.parse.DeployCreateProperties;
import com.avaje.ebean.config.NamingConvention;
import com.avaje.ebeaninternal.server.subclass.SubClassManager;
import com.avaje.ebeaninternal.server.type.TypeManager;
import com.avaje.ebeaninternal.server.deploy.parse.DeployUtil;
import com.avaje.ebeaninternal.server.reflect.BeanReflectFactory;
import com.avaje.ebeaninternal.server.deploy.parse.DeployInherit;
import com.avaje.ebeaninternal.server.deploy.parse.TransientProperties;
import com.avaje.ebeaninternal.server.deploy.parse.ReadAnnotations;
import java.util.logging.Logger;

public class BeanDescriptorManager implements BeanDescriptorMap
{
    private static final Logger logger;
    private static final BeanDescComparator beanDescComparator;
    private final ReadAnnotations readAnnotations;
    private final TransientProperties transientProperties;
    private final DeployInherit deplyInherit;
    private final BeanReflectFactory reflectFactory;
    private final DeployUtil deployUtil;
    private final TypeManager typeManager;
    private final PersistControllerManager persistControllerManager;
    private final BeanFinderManager beanFinderManager;
    private final PersistListenerManager persistListenerManager;
    private final BeanQueryAdapterManager beanQueryAdapterManager;
    private final SubClassManager subClassManager;
    private final NamingConvention namingConvention;
    private final DeployCreateProperties createProperties;
    private final DeployOrmXml deployOrmXml;
    private final BeanManagerFactory beanManagerFactory;
    private int enhancedClassCount;
    private int subclassClassCount;
    private final HashSet<String> subclassedEntities;
    private final boolean updateChangesOnly;
    private final BootupClasses bootupClasses;
    private final String serverName;
    private Map<Class<?>, DeployBeanInfo<?>> deplyInfoMap;
    private final Map<Class<?>, BeanTable> beanTableMap;
    private final Map<String, BeanDescriptor<?>> descMap;
    private final Map<String, BeanDescriptor<?>> idDescMap;
    private final Map<String, BeanManager<?>> beanManagerMap;
    private final Map<String, List<BeanDescriptor<?>>> tableToDescMap;
    private List<BeanDescriptor<?>> immutableDescriptorList;
    private final Set<Integer> descriptorUniqueIds;
    private final DbIdentity dbIdentity;
    private final DataSource dataSource;
    private final DatabasePlatform databasePlatform;
    private final UuidIdGenerator uuidIdGenerator;
    private final ServerCacheManager cacheManager;
    private final BackgroundExecutor backgroundExecutor;
    private final int dbSequenceBatchSize;
    private final EncryptKeyManager encryptKeyManager;
    private final IdBinderFactory idBinderFactory;
    private final XmlConfig xmlConfig;
    private final LuceneIndexManager luceneManager;
    
    public BeanDescriptorManager(final InternalConfiguration config, final LuceneIndexManager luceneManager) {
        this.readAnnotations = new ReadAnnotations();
        this.subclassedEntities = new HashSet<String>();
        this.deplyInfoMap = new HashMap<Class<?>, DeployBeanInfo<?>>();
        this.beanTableMap = new HashMap<Class<?>, BeanTable>();
        this.descMap = new HashMap<String, BeanDescriptor<?>>();
        this.idDescMap = new HashMap<String, BeanDescriptor<?>>();
        this.beanManagerMap = new HashMap<String, BeanManager<?>>();
        this.tableToDescMap = new HashMap<String, List<BeanDescriptor<?>>>();
        this.descriptorUniqueIds = new HashSet<Integer>();
        this.uuidIdGenerator = new UuidIdGenerator();
        this.luceneManager = luceneManager;
        this.serverName = InternString.intern(config.getServerConfig().getName());
        this.cacheManager = config.getCacheManager();
        this.xmlConfig = config.getXmlConfig();
        this.dbSequenceBatchSize = config.getServerConfig().getDatabaseSequenceBatchSize();
        this.backgroundExecutor = config.getBackgroundExecutor();
        this.dataSource = config.getServerConfig().getDataSource();
        this.encryptKeyManager = config.getServerConfig().getEncryptKeyManager();
        this.databasePlatform = config.getServerConfig().getDatabasePlatform();
        this.idBinderFactory = new IdBinderFactory(this.databasePlatform.isIdInExpandedForm());
        this.bootupClasses = config.getBootupClasses();
        this.createProperties = config.getDeployCreateProperties();
        this.subClassManager = config.getSubClassManager();
        this.typeManager = config.getTypeManager();
        this.namingConvention = config.getServerConfig().getNamingConvention();
        this.dbIdentity = config.getDatabasePlatform().getDbIdentity();
        this.deplyInherit = config.getDeployInherit();
        this.deployOrmXml = config.getDeployOrmXml();
        this.deployUtil = config.getDeployUtil();
        this.beanManagerFactory = new BeanManagerFactory(config.getServerConfig(), config.getDatabasePlatform());
        this.updateChangesOnly = config.getServerConfig().isUpdateChangesOnly();
        this.persistControllerManager = new PersistControllerManager(this.bootupClasses);
        this.persistListenerManager = new PersistListenerManager(this.bootupClasses);
        this.beanQueryAdapterManager = new BeanQueryAdapterManager(this.bootupClasses);
        this.beanFinderManager = new DefaultBeanFinderManager();
        this.reflectFactory = this.createReflectionFactory();
        this.transientProperties = new TransientProperties();
    }
    
    public BeanDescriptor<?> getBeanDescriptorById(final String descriptorId) {
        return this.idDescMap.get(descriptorId);
    }
    
    public <T> BeanDescriptor<T> getBeanDescriptor(final Class<T> entityType) {
        final String className = SubClassUtil.getSuperClassName(entityType.getName());
        return (BeanDescriptor<T>)this.descMap.get(className);
    }
    
    public <T> BeanDescriptor<T> getBeanDescriptor(String entityClassName) {
        entityClassName = SubClassUtil.getSuperClassName(entityClassName);
        return (BeanDescriptor<T>)this.descMap.get(entityClassName);
    }
    
    public String getServerName() {
        return this.serverName;
    }
    
    public ServerCacheManager getCacheManager() {
        return this.cacheManager;
    }
    
    public NamingConvention getNamingConvention() {
        return this.namingConvention;
    }
    
    public void setEbeanServer(final SpiEbeanServer internalEbean) {
        for (final BeanDescriptor<?> desc : this.immutableDescriptorList) {
            desc.setEbeanServer(internalEbean);
        }
    }
    
    public IdBinder createIdBinder(final BeanProperty[] uids) {
        return this.idBinderFactory.createIdBinder(uids);
    }
    
    public void deploy() {
        try {
            this.createListeners();
            this.readEmbeddedDeployment();
            this.readEntityDeploymentInitial();
            this.assignLuceneIndexDefns();
            this.readEntityBeanTable();
            this.readEntityDeploymentAssociations();
            this.readInheritedIdGenerators();
            this.readEntityRelationships();
            this.readRawSqlQueries();
            final List<BeanDescriptor<?>> list = new ArrayList<BeanDescriptor<?>>(this.descMap.values());
            Collections.sort(list, BeanDescriptorManager.beanDescComparator);
            this.immutableDescriptorList = Collections.unmodifiableList((List<? extends BeanDescriptor<?>>)list);
            for (final BeanDescriptor<?> d : list) {
                this.idDescMap.put(d.getDescriptorId(), d);
            }
            this.initialiseAll();
            this.readForeignKeys();
            this.readTableToDescriptor();
            this.logStatus();
            this.deplyInfoMap.clear();
            this.deplyInfoMap = null;
        }
        catch (RuntimeException e) {
            final String msg = "Error in deployment";
            BeanDescriptorManager.logger.log(Level.SEVERE, msg, e);
            throw e;
        }
    }
    
    public EncryptKey getEncryptKey(final String tableName, final String columnName) {
        return this.encryptKeyManager.getEncryptKey(tableName, columnName);
    }
    
    public void cacheNotify(final TransactionEventTable.TableIUD tableIUD) {
        final List<BeanDescriptor<?>> list = this.getBeanDescriptors(tableIUD.getTable());
        if (list != null) {
            for (int i = 0; i < list.size(); ++i) {
                list.get(i).cacheNotify(tableIUD);
            }
        }
    }
    
    public List<BeanDescriptor<?>> getBeanDescriptors(final String tableName) {
        return this.tableToDescMap.get(tableName.toLowerCase());
    }
    
    private void readTableToDescriptor() {
        for (final BeanDescriptor<?> desc : this.descMap.values()) {
            String baseTable = desc.getBaseTable();
            if (baseTable == null) {
                continue;
            }
            baseTable = baseTable.toLowerCase();
            List<BeanDescriptor<?>> list = this.tableToDescMap.get(baseTable);
            if (list == null) {
                list = new ArrayList<BeanDescriptor<?>>(1);
                this.tableToDescMap.put(baseTable, list);
            }
            list.add(desc);
        }
    }
    
    private void readForeignKeys() {
        for (final BeanDescriptor<?> d : this.descMap.values()) {
            d.initialiseFkeys();
        }
    }
    
    private void initialiseAll() {
        for (final BeanDescriptor<?> d : this.descMap.values()) {
            d.initialiseId();
        }
        for (final BeanDescriptor<?> d : this.descMap.values()) {
            d.initInheritInfo();
        }
        for (final BeanDescriptor<?> d : this.descMap.values()) {
            d.initialiseOther();
        }
        for (final BeanDescriptor<?> d : this.descMap.values()) {
            final IndexDefn<?> luceneIndexDefn = d.getLuceneIndexDefn();
            if (luceneIndexDefn != null) {
                try {
                    final LIndex luceneIndex = this.luceneManager.create(luceneIndexDefn, d);
                    d.setLuceneIndex(luceneIndex);
                }
                catch (IOException e) {
                    final String msg = "Error creating Lucene Index " + luceneIndexDefn.getClass().getName();
                    BeanDescriptorManager.logger.log(Level.SEVERE, msg, e);
                }
            }
        }
        for (final BeanDescriptor<?> d : this.descMap.values()) {
            if (!d.isEmbedded()) {
                final BeanManager<?> m = this.beanManagerFactory.create(d);
                this.beanManagerMap.put(d.getFullName(), m);
                this.checkForValidEmbeddedId(d);
            }
        }
    }
    
    private void checkForValidEmbeddedId(final BeanDescriptor<?> d) {
        final IdBinder idBinder = d.getIdBinder();
        if (idBinder != null && idBinder instanceof IdBinderEmbedded) {
            final IdBinderEmbedded embId = (IdBinderEmbedded)idBinder;
            final BeanDescriptor<?> idBeanDescriptor = embId.getIdBeanDescriptor();
            final Class<?> idType = idBeanDescriptor.getBeanType();
            try {
                idType.getDeclaredMethod("hashCode", (Class<?>[])new Class[0]);
                idType.getDeclaredMethod("equals", Object.class);
            }
            catch (NoSuchMethodException e) {
                this.checkMissingHashCodeOrEquals(e, idType, d.getBeanType());
            }
        }
    }
    
    private void checkMissingHashCodeOrEquals(final Exception source, final Class<?> idType, final Class<?> beanType) {
        String msg = "SERIOUS ERROR: The hashCode() and equals() methods *MUST* be implemented ";
        msg = msg + "on Embedded bean " + idType + " as it is used as an Id for " + beanType;
        if (GlobalProperties.getBoolean("ebean.strict", true)) {
            throw new PersistenceException(msg, source);
        }
        BeanDescriptorManager.logger.log(Level.SEVERE, msg, source);
    }
    
    public List<BeanDescriptor<?>> getBeanDescriptorList() {
        return this.immutableDescriptorList;
    }
    
    public Map<Class<?>, BeanTable> getBeanTables() {
        return this.beanTableMap;
    }
    
    public BeanTable getBeanTable(final Class<?> type) {
        return this.beanTableMap.get(type);
    }
    
    public Map<String, BeanDescriptor<?>> getBeanDescriptors() {
        return this.descMap;
    }
    
    public <T> BeanManager<T> getBeanManager(final Class<T> entityType) {
        return (BeanManager<T>)this.getBeanManager(entityType.getName());
    }
    
    public BeanManager<?> getBeanManager(String beanClassName) {
        beanClassName = SubClassUtil.getSuperClassName(beanClassName);
        return this.beanManagerMap.get(beanClassName);
    }
    
    public DNativeQuery getNativeQuery(final String name) {
        return this.deployOrmXml.getNativeQuery(name);
    }
    
    private void assignLuceneIndexDefns() {
        final List<IndexDefn<?>> list = this.bootupClasses.getLuceneIndexInstances();
        for (int i = 0; i < list.size(); ++i) {
            final IndexDefn<?> indexDefn = list.get(i);
            final Class<?> entityClass = this.getIndexDefnEntityClass(indexDefn.getClass());
            final DeployBeanInfo<?> deployBeanInfo = this.deplyInfoMap.get(entityClass);
            if (deployBeanInfo == null) {
                final String msg = "Could not find entity deployment for " + entityClass;
                throw new PersistenceException(msg);
            }
            deployBeanInfo.getDescriptor().setIndexDefn(indexDefn);
        }
    }
    
    private Class<?> getIndexDefnEntityClass(final Class<?> controller) {
        final Class<?> cls = ParamTypeUtil.findParamType(controller, IndexDefn.class);
        if (cls == null) {
            final String msg = "Could not determine the entity class (generics parameter type) from " + controller + " using reflection.";
            throw new PersistenceException(msg);
        }
        return cls;
    }
    
    private void createListeners() {
        final int qa = this.beanQueryAdapterManager.getRegisterCount();
        final int cc = this.persistControllerManager.getRegisterCount();
        final int lc = this.persistListenerManager.getRegisterCount();
        final int fc = this.beanFinderManager.createBeanFinders(this.bootupClasses.getBeanFinders());
        BeanDescriptorManager.logger.fine("BeanPersistControllers[" + cc + "] BeanFinders[" + fc + "] BeanPersistListeners[" + lc + "] BeanQueryAdapters[" + qa + "]");
    }
    
    private void logStatus() {
        final String msg = "Entities enhanced[" + this.enhancedClassCount + "] subclassed[" + this.subclassClassCount + "]";
        BeanDescriptorManager.logger.info(msg);
        if (this.enhancedClassCount > 0 && this.subclassClassCount > 0) {
            final String subclassEntityNames = this.subclassedEntities.toString();
            final String m = "Mixing enhanced and subclassed entities. Subclassed classes:" + subclassEntityNames;
            BeanDescriptorManager.logger.warning(m);
        }
    }
    
    private <T> BeanDescriptor<T> createEmbedded(final Class<T> beanClass) {
        final DeployBeanInfo<T> info = this.createDeployBeanInfo(beanClass);
        this.readDeployAssociations(info);
        final Integer key = this.getUniqueHash(info.getDescriptor());
        return new BeanDescriptor<T>(this, this.typeManager, info.getDescriptor(), key.toString());
    }
    
    private void registerBeanDescriptor(final BeanDescriptor<?> desc) {
        this.descMap.put(desc.getBeanType().getName(), desc);
    }
    
    private void readEmbeddedDeployment() {
        final ArrayList<Class<?>> embeddedClasses = this.bootupClasses.getEmbeddables();
        for (int i = 0; i < embeddedClasses.size(); ++i) {
            final Class<?> cls = embeddedClasses.get(i);
            if (BeanDescriptorManager.logger.isLoggable(Level.FINER)) {
                final String msg = "load deployinfo for embeddable:" + cls.getName();
                BeanDescriptorManager.logger.finer(msg);
            }
            final BeanDescriptor<?> embDesc = this.createEmbedded(cls);
            this.registerBeanDescriptor(embDesc);
        }
    }
    
    private void readEntityDeploymentInitial() {
        final ArrayList<Class<?>> entityClasses = this.bootupClasses.getEntities();
        for (final Class<?> entityClass : entityClasses) {
            final DeployBeanInfo<?> info = this.createDeployBeanInfo(entityClass);
            this.deplyInfoMap.put(entityClass, info);
        }
    }
    
    private void readEntityBeanTable() {
        for (final DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
            final BeanTable beanTable = this.createBeanTable(info);
            this.beanTableMap.put(beanTable.getBeanType(), beanTable);
        }
    }
    
    private void readEntityDeploymentAssociations() {
        for (final DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
            this.readDeployAssociations(info);
        }
    }
    
    private void readInheritedIdGenerators() {
        for (final DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
            final DeployBeanDescriptor<?> descriptor = info.getDescriptor();
            final InheritInfo inheritInfo = descriptor.getInheritInfo();
            if (inheritInfo != null && !inheritInfo.isRoot()) {
                final DeployBeanInfo<?> rootBeanInfo = this.deplyInfoMap.get(inheritInfo.getRoot().getType());
                final IdGenerator rootIdGen = rootBeanInfo.getDescriptor().getIdGenerator();
                if (rootIdGen == null) {
                    continue;
                }
                descriptor.setIdGenerator(rootIdGen);
            }
        }
    }
    
    private BeanTable createBeanTable(final DeployBeanInfo<?> info) {
        final DeployBeanDescriptor<?> deployDescriptor = info.getDescriptor();
        final DeployBeanTable beanTable = deployDescriptor.createDeployBeanTable();
        return new BeanTable(beanTable, this);
    }
    
    private void readRawSqlQueries() {
        for (final DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
            final DeployBeanDescriptor<?> deployDesc = info.getDescriptor();
            final BeanDescriptor<?> desc = this.getBeanDescriptor(deployDesc.getBeanType());
            for (final DRawSqlMeta rawSqlMeta : deployDesc.getRawSqlMeta()) {
                if (rawSqlMeta.getQuery() == null) {
                    continue;
                }
                final DeployNamedQuery nq = new DRawSqlSelectBuilder(this.namingConvention, desc, rawSqlMeta).parse();
                desc.addNamedQuery(nq);
            }
        }
    }
    
    private void readEntityRelationships() {
        for (final DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
            this.checkMappedBy(info);
        }
        for (final DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
            this.secondaryPropsJoins(info);
        }
        for (final DeployBeanInfo<?> info : this.deplyInfoMap.values()) {
            final DeployBeanDescriptor<?> deployBeanDescriptor = info.getDescriptor();
            final Integer key = this.getUniqueHash(deployBeanDescriptor);
            this.registerBeanDescriptor(new BeanDescriptor<Object>(this, this.typeManager, info.getDescriptor(), key.toString()));
        }
    }
    
    private Integer getUniqueHash(final DeployBeanDescriptor<?> deployBeanDescriptor) {
        final int hashCode = deployBeanDescriptor.getFullName().hashCode();
        for (int i = 0; i < 100000; ++i) {
            final Integer key = hashCode + i;
            if (!this.descriptorUniqueIds.contains(key)) {
                return key;
            }
        }
        throw new RuntimeException("Failed to generate a unique hash for " + deployBeanDescriptor.getFullName());
    }
    
    private void secondaryPropsJoins(final DeployBeanInfo<?> info) {
        final DeployBeanDescriptor<?> descriptor = info.getDescriptor();
        for (final DeployBeanProperty prop : descriptor.propertiesBase()) {
            if (prop.isSecondaryTable()) {
                final String tableName = prop.getSecondaryTable();
                final DeployBeanPropertyAssocOne<?> assocOne = descriptor.findJoinToTable(tableName);
                if (assocOne == null) {
                    final String msg = "Error with property " + prop.getFullBeanName() + ". Could not find a Relationship to table " + tableName + ". Perhaps you could use a @JoinColumn instead.";
                    throw new RuntimeException(msg);
                }
                final DeployTableJoin tableJoin = assocOne.getTableJoin();
                prop.setSecondaryTableJoin(tableJoin, assocOne.getName());
            }
        }
    }
    
    private void checkMappedBy(final DeployBeanInfo<?> info) {
        for (final DeployBeanPropertyAssocOne<?> oneProp : info.getDescriptor().propertiesAssocOne()) {
            if (!oneProp.isTransient() && oneProp.getMappedBy() != null) {
                this.checkMappedByOneToOne(info, oneProp);
            }
        }
        for (final DeployBeanPropertyAssocMany<?> manyProp : info.getDescriptor().propertiesAssocMany()) {
            if (!manyProp.isTransient()) {
                if (manyProp.isManyToMany()) {
                    this.checkMappedByManyToMany(info, manyProp);
                }
                else {
                    this.checkMappedByOneToMany(info, manyProp);
                }
            }
        }
    }
    
    private DeployBeanDescriptor<?> getTargetDescriptor(final DeployBeanPropertyAssoc<?> prop) {
        final Class<?> targetType = prop.getTargetType();
        final DeployBeanInfo<?> info = this.deplyInfoMap.get(targetType);
        if (info == null) {
            final String msg = "Can not find descriptor [" + targetType + "] for " + prop.getFullBeanName();
            throw new PersistenceException(msg);
        }
        return info.getDescriptor();
    }
    
    private boolean findMappedBy(final DeployBeanPropertyAssocMany<?> prop) {
        final Class<?> owningType = prop.getOwningType();
        final Set<String> matchSet = new HashSet<String>();
        final DeployBeanDescriptor<?> targetDesc = this.getTargetDescriptor(prop);
        final List<DeployBeanPropertyAssocOne<?>> ones = targetDesc.propertiesAssocOne();
        for (final DeployBeanPropertyAssocOne<?> possibleMappedBy : ones) {
            final Class<?> possibleMappedByType = possibleMappedBy.getTargetType();
            if (possibleMappedByType.equals(owningType)) {
                prop.setMappedBy(possibleMappedBy.getName());
                matchSet.add(possibleMappedBy.getName());
            }
        }
        if (matchSet.size() == 0) {
            return false;
        }
        if (matchSet.size() == 1) {
            return true;
        }
        if (matchSet.size() == 2) {
            final String name = prop.getName();
            final String targetType = prop.getTargetType().getName();
            final String shortTypeName = targetType.substring(targetType.lastIndexOf(".") + 1);
            final int p = name.indexOf(shortTypeName);
            if (p > 1) {
                final String searchName = name.substring(0, p).toLowerCase();
                for (final String possibleMappedBy2 : matchSet) {
                    final String possibleLower = possibleMappedBy2.toLowerCase();
                    if (possibleLower.indexOf(searchName) > -1) {
                        prop.setMappedBy(possibleMappedBy2);
                        String m = "Implicitly found mappedBy for " + targetDesc + "." + prop;
                        m = m + " by searching for [" + searchName + "] against " + matchSet;
                        BeanDescriptorManager.logger.fine(m);
                        return true;
                    }
                }
            }
        }
        String msg = "Error on " + prop.getFullBeanName() + " missing mappedBy.";
        msg = msg + " There are [" + matchSet.size() + "] possible properties in " + targetDesc;
        msg += " that this association could be mapped to. Please specify one using ";
        msg += "the mappedBy attribute on @OneToMany.";
        throw new PersistenceException(msg);
    }
    
    private void makeUnidirectional(final DeployBeanInfo<?> info, final DeployBeanPropertyAssocMany<?> oneToMany) {
        final DeployBeanDescriptor<?> targetDesc = this.getTargetDescriptor(oneToMany);
        final Class<?> owningType = oneToMany.getOwningType();
        if (!oneToMany.getCascadeInfo().isSave()) {
            final Class<?> targetType = oneToMany.getTargetType();
            String msg = "Error on " + oneToMany.getFullBeanName() + ". @OneToMany MUST have ";
            msg += "Cascade.PERSIST or Cascade.ALL because this is a unidirectional ";
            msg = msg + "relationship. That is, there is no property of type " + owningType + " on " + targetType;
            throw new PersistenceException(msg);
        }
        oneToMany.setUnidirectional(true);
        final DeployBeanPropertyAssocOne<?> unidirectional = new DeployBeanPropertyAssocOne<Object>(targetDesc, owningType);
        unidirectional.setUndirectionalShadow(true);
        unidirectional.setNullable(false);
        unidirectional.setDbRead(true);
        unidirectional.setDbInsertable(true);
        unidirectional.setDbUpdateable(false);
        targetDesc.setUnidirectional(unidirectional);
        final BeanTable beanTable = this.getBeanTable(owningType);
        unidirectional.setBeanTable(beanTable);
        unidirectional.setName(beanTable.getBaseTable());
        info.setBeanJoinType(unidirectional, true);
        final DeployTableJoin oneToManyJoin = oneToMany.getTableJoin();
        if (!oneToManyJoin.hasJoinColumns()) {
            throw new RuntimeException("No join columns");
        }
        final DeployTableJoin unidirectionalJoin = unidirectional.getTableJoin();
        unidirectionalJoin.setColumns(oneToManyJoin.columns(), true);
    }
    
    private void checkMappedByOneToOne(final DeployBeanInfo<?> info, final DeployBeanPropertyAssocOne<?> prop) {
        final String mappedBy = prop.getMappedBy();
        final DeployBeanDescriptor<?> targetDesc = this.getTargetDescriptor(prop);
        final DeployBeanProperty mappedProp = targetDesc.getBeanProperty(mappedBy);
        if (mappedProp == null) {
            String m = "Error on " + prop.getFullBeanName();
            m = m + "  Can not find mappedBy property [" + targetDesc + "." + mappedBy + "] ";
            throw new PersistenceException(m);
        }
        if (!(mappedProp instanceof DeployBeanPropertyAssocOne)) {
            String m = "Error on " + prop.getFullBeanName();
            m = m + ". mappedBy property [" + targetDesc + "." + mappedBy + "]is not a OneToOne?";
            throw new PersistenceException(m);
        }
        final DeployBeanPropertyAssocOne<?> mappedAssocOne = (DeployBeanPropertyAssocOne<?>)mappedProp;
        if (!mappedAssocOne.isOneToOne()) {
            String i = "Error on " + prop.getFullBeanName();
            i = i + ". mappedBy property [" + targetDesc + "." + mappedBy + "]is not a OneToOne?";
            throw new PersistenceException(i);
        }
        final DeployTableJoin tableJoin = prop.getTableJoin();
        if (!tableJoin.hasJoinColumns()) {
            final DeployTableJoin otherTableJoin = mappedAssocOne.getTableJoin();
            otherTableJoin.copyTo(tableJoin, true, tableJoin.getTable());
        }
    }
    
    private void checkMappedByOneToMany(final DeployBeanInfo<?> info, final DeployBeanPropertyAssocMany<?> prop) {
        if (prop.getMappedBy() == null && !this.findMappedBy(prop)) {
            this.makeUnidirectional(info, prop);
            return;
        }
        final String mappedBy = prop.getMappedBy();
        final DeployBeanDescriptor<?> targetDesc = this.getTargetDescriptor(prop);
        final DeployBeanProperty mappedProp = targetDesc.getBeanProperty(mappedBy);
        if (mappedProp == null) {
            String m = "Error on " + prop.getFullBeanName();
            m = m + "  Can not find mappedBy property [" + mappedBy + "] ";
            m = m + "in [" + targetDesc + "]";
            throw new PersistenceException(m);
        }
        if (!(mappedProp instanceof DeployBeanPropertyAssocOne)) {
            String m = "Error on " + prop.getFullBeanName();
            m = m + ". mappedBy property [" + mappedBy + "]is not a ManyToOne?";
            m = m + "in [" + targetDesc + "]";
            throw new PersistenceException(m);
        }
        final DeployBeanPropertyAssocOne<?> mappedAssocOne = (DeployBeanPropertyAssocOne<?>)mappedProp;
        final DeployTableJoin tableJoin = prop.getTableJoin();
        if (!tableJoin.hasJoinColumns()) {
            final DeployTableJoin otherTableJoin = mappedAssocOne.getTableJoin();
            otherTableJoin.copyTo(tableJoin, true, tableJoin.getTable());
        }
    }
    
    private void checkMappedByManyToMany(final DeployBeanInfo<?> info, final DeployBeanPropertyAssocMany<?> prop) {
        final String mappedBy = prop.getMappedBy();
        if (mappedBy == null) {
            return;
        }
        final DeployBeanDescriptor<?> targetDesc = this.getTargetDescriptor(prop);
        final DeployBeanProperty mappedProp = targetDesc.getBeanProperty(mappedBy);
        if (mappedProp == null) {
            String m = "Error on " + prop.getFullBeanName();
            m = m + "  Can not find mappedBy property [" + mappedBy + "] ";
            m = m + "in [" + targetDesc + "]";
            throw new PersistenceException(m);
        }
        if (!(mappedProp instanceof DeployBeanPropertyAssocMany)) {
            String m = "Error on " + prop.getFullBeanName();
            m = m + ". mappedBy property [" + targetDesc + "." + mappedBy + "] is not a ManyToMany?";
            throw new PersistenceException(m);
        }
        final DeployBeanPropertyAssocMany<?> mappedAssocMany = (DeployBeanPropertyAssocMany<?>)mappedProp;
        if (!mappedAssocMany.isManyToMany()) {
            String i = "Error on " + prop.getFullBeanName();
            i = i + ". mappedBy property [" + targetDesc + "." + mappedBy + "] is not a ManyToMany?";
            throw new PersistenceException(i);
        }
        final DeployTableJoin mappedIntJoin = mappedAssocMany.getIntersectionJoin();
        final DeployTableJoin mappendInverseJoin = mappedAssocMany.getInverseJoin();
        final String intTableName = mappedIntJoin.getTable();
        final DeployTableJoin tableJoin = prop.getTableJoin();
        mappedIntJoin.copyTo(tableJoin, true, targetDesc.getBaseTable());
        final DeployTableJoin intJoin = new DeployTableJoin();
        mappendInverseJoin.copyTo(intJoin, false, intTableName);
        prop.setIntersectionJoin(intJoin);
        final DeployTableJoin inverseJoin = new DeployTableJoin();
        mappedIntJoin.copyTo(inverseJoin, false, intTableName);
        prop.setInverseJoin(inverseJoin);
    }
    
    private <T> void setBeanControllerFinderListener(final DeployBeanDescriptor<T> descriptor) {
        final Class<T> beanType = descriptor.getBeanType();
        this.persistControllerManager.addPersistControllers(descriptor);
        this.persistListenerManager.addPersistListeners(descriptor);
        this.beanQueryAdapterManager.addQueryAdapter(descriptor);
        final BeanFinder<T> beanFinder = this.beanFinderManager.getBeanFinder(beanType);
        if (beanFinder != null) {
            descriptor.setBeanFinder(beanFinder);
            BeanDescriptorManager.logger.fine("BeanFinder on[" + descriptor.getFullName() + "] " + beanFinder.getClass().getName());
        }
    }
    
    private <T> DeployBeanInfo<T> createDeployBeanInfo(final Class<T> beanClass) {
        final DeployBeanDescriptor<T> desc = new DeployBeanDescriptor<T>(beanClass);
        desc.setUpdateChangesOnly(this.updateChangesOnly);
        this.setBeanControllerFinderListener(desc);
        this.deplyInherit.process(desc);
        this.createProperties.createProperties(desc);
        final DeployBeanInfo<T> info = new DeployBeanInfo<T>(this.deployUtil, desc);
        this.readAnnotations.readInitial(info);
        return info;
    }
    
    private <T> void readDeployAssociations(final DeployBeanInfo<T> info) {
        final DeployBeanDescriptor<T> desc = info.getDescriptor();
        this.readAnnotations.readAssociations(info, this);
        this.readXml(desc);
        if (!BeanDescriptor.EntityType.ORM.equals(desc.getEntityType())) {
            desc.setBaseTable(null);
        }
        this.transientProperties.process(desc);
        this.setScalarType(desc);
        if (!desc.isEmbedded()) {
            this.setIdGeneration(desc);
            this.setConcurrencyMode(desc);
        }
        this.autoAddValidators(desc);
        this.createByteCode(desc);
    }
    
    private <T> IdType setIdGeneration(final DeployBeanDescriptor<T> desc) {
        if (desc.propertiesId().size() == 0) {
            if (desc.isBaseTableType()) {
                if (desc.getBeanFinder() == null) {
                    BeanDescriptorManager.logger.warning(Message.msg("deploy.nouid", desc.getFullName()));
                }
            }
            return null;
        }
        if (IdType.SEQUENCE.equals(desc.getIdType()) && !this.dbIdentity.isSupportsSequence()) {
            BeanDescriptorManager.logger.info("Explicit sequence on " + desc.getFullName() + " but not supported by DB Platform - ignored");
            desc.setIdType(null);
        }
        if (IdType.IDENTITY.equals(desc.getIdType()) && !this.dbIdentity.isSupportsIdentity()) {
            BeanDescriptorManager.logger.info("Explicit Identity on " + desc.getFullName() + " but not supported by DB Platform - ignored");
            desc.setIdType(null);
        }
        if (desc.getIdType() == null) {
            desc.setIdType(this.dbIdentity.getIdType());
        }
        if (IdType.GENERATOR.equals(desc.getIdType())) {
            final String genName = desc.getIdGeneratorName();
            if ("auto.uuid".equals(genName)) {
                desc.setIdGenerator(this.uuidIdGenerator);
                return IdType.GENERATOR;
            }
        }
        if (desc.getBaseTable() == null) {
            return null;
        }
        if (IdType.IDENTITY.equals(desc.getIdType())) {
            final String selectLastInsertedId = this.dbIdentity.getSelectLastInsertedId(desc.getBaseTable());
            desc.setSelectLastInsertedId(selectLastInsertedId);
            return IdType.IDENTITY;
        }
        String seqName = desc.getIdGeneratorName();
        if (seqName != null) {
            BeanDescriptorManager.logger.fine("explicit sequence " + seqName + " on " + desc.getFullName());
        }
        else {
            final String primaryKeyColumn = desc.getSinglePrimaryKeyColumn();
            seqName = this.namingConvention.getSequenceName(desc.getBaseTable(), primaryKeyColumn);
        }
        final IdGenerator seqIdGen = this.createSequenceIdGenerator(seqName);
        desc.setIdGenerator(seqIdGen);
        return IdType.SEQUENCE;
    }
    
    private IdGenerator createSequenceIdGenerator(final String seqName) {
        return this.databasePlatform.createSequenceIdGenerator(this.backgroundExecutor, this.dataSource, seqName, this.dbSequenceBatchSize);
    }
    
    private void createByteCode(final DeployBeanDescriptor<?> deploy) {
        this.setEntityBeanClass(deploy);
        this.setBeanReflect(deploy);
    }
    
    private void autoAddValidators(final DeployBeanDescriptor<?> deployDesc) {
        for (final DeployBeanProperty prop : deployDesc.propertiesBase()) {
            this.autoAddValidators(prop);
        }
    }
    
    private void autoAddValidators(final DeployBeanProperty prop) {
        if (String.class.equals(prop.getPropertyType()) && prop.getDbLength() > 0 && !prop.containsValidatorType(LengthValidatorFactory.LengthValidator.class)) {
            prop.addValidator(LengthValidatorFactory.create(0, prop.getDbLength()));
        }
        if (!prop.isNullable() && !prop.isId() && !prop.isGenerated() && !prop.containsValidatorType(NotNullValidatorFactory.NotNullValidator.class)) {
            prop.addValidator(NotNullValidatorFactory.NOT_NULL);
        }
    }
    
    private void setScalarType(final DeployBeanDescriptor<?> deployDesc) {
        final Iterator<DeployBeanProperty> it = deployDesc.propertiesAll();
        while (it.hasNext()) {
            final DeployBeanProperty prop = it.next();
            if (prop instanceof DeployBeanPropertyAssoc) {
                continue;
            }
            this.deployUtil.setScalarType(prop);
        }
    }
    
    private void readXml(final DeployBeanDescriptor<?> deployDesc) {
        final List<Dnode> eXml = this.xmlConfig.findEntityXml(deployDesc.getFullName());
        this.readXmlRawSql(deployDesc, eXml);
        final Dnode entityXml = this.deployOrmXml.findEntityDeploymentXml(deployDesc.getFullName());
        if (entityXml != null) {
            this.readXmlNamedQueries(deployDesc, entityXml);
            this.readXmlSql(deployDesc, entityXml);
        }
    }
    
    private void readXmlSql(final DeployBeanDescriptor<?> deployDesc, final Dnode entityXml) {
        final List<Dnode> sqlSelectList = entityXml.findAll("sql-select", entityXml.getLevel() + 1);
        for (int i = 0; i < sqlSelectList.size(); ++i) {
            final Dnode sqlSelect = sqlSelectList.get(i);
            this.readSqlSelect(deployDesc, sqlSelect);
        }
    }
    
    private String findContent(final Dnode node, final String nodeName) {
        final Dnode found = node.find(nodeName);
        if (found != null) {
            return found.getNodeContent();
        }
        return null;
    }
    
    private void readSqlSelect(final DeployBeanDescriptor<?> deployDesc, final Dnode sqlSelect) {
        final String name = sqlSelect.getStringAttr("name", "default");
        final String extend = sqlSelect.getStringAttr("extend", null);
        final String queryDebug = sqlSelect.getStringAttr("debug", null);
        final boolean debug = queryDebug != null && queryDebug.equalsIgnoreCase("true");
        final String query = this.findContent(sqlSelect, "query");
        final String where = this.findContent(sqlSelect, "where");
        final String having = this.findContent(sqlSelect, "having");
        final String columnMapping = this.findContent(sqlSelect, "columnMapping");
        final DRawSqlMeta m = new DRawSqlMeta(name, extend, query, debug, where, having, columnMapping);
        deployDesc.add(m);
    }
    
    private void readXmlRawSql(final DeployBeanDescriptor<?> deployDesc, final List<Dnode> entityXml) {
        final List<Dnode> rawSqlQueries = this.xmlConfig.find(entityXml, "raw-sql");
        for (int i = 0; i < rawSqlQueries.size(); ++i) {
            final Dnode rawSqlDnode = rawSqlQueries.get(i);
            final String name = rawSqlDnode.getAttribute("name");
            if (this.isEmpty(name)) {
                throw new IllegalStateException("raw-sql for " + deployDesc.getFullName() + " missing name attribute");
            }
            final Dnode queryNode = rawSqlDnode.find("query");
            if (queryNode == null) {
                throw new IllegalStateException("raw-sql for " + deployDesc.getFullName() + " missing query element");
            }
            final String sql = queryNode.getNodeContent();
            if (this.isEmpty(sql)) {
                throw new IllegalStateException("raw-sql for " + deployDesc.getFullName() + " has empty sql in the query element?");
            }
            final List<Dnode> columnMappings = rawSqlDnode.findAll("columnMapping", 1);
            final RawSqlBuilder rawSqlBuilder = RawSqlBuilder.parse(sql);
            for (int j = 0; j < columnMappings.size(); ++j) {
                final Dnode cm = columnMappings.get(j);
                final String column = cm.getAttribute("column");
                final String property = cm.getAttribute("property");
                rawSqlBuilder.columnMapping(column, property);
            }
            final RawSql rawSql = rawSqlBuilder.create();
            final DeployNamedQuery namedQuery = new DeployNamedQuery(name, rawSql);
            deployDesc.add(namedQuery);
        }
    }
    
    private boolean isEmpty(final String s) {
        return s == null || s.trim().length() == 0;
    }
    
    private void readXmlNamedQueries(final DeployBeanDescriptor<?> deployDesc, final Dnode entityXml) {
        final List<Dnode> namedQueries = entityXml.findAll("named-query", 1);
        for (final Dnode namedQueryXml : namedQueries) {
            final String name = namedQueryXml.getAttribute("name");
            final Dnode query = namedQueryXml.find("query");
            if (query == null) {
                BeanDescriptorManager.logger.warning("orm.xml " + deployDesc.getFullName() + " named-query missing query element?");
            }
            else {
                final String oql = query.getNodeContent();
                if (name == null || oql == null) {
                    BeanDescriptorManager.logger.warning("orm.xml " + deployDesc.getFullName() + " named-query has no query content?");
                }
                else {
                    final DeployNamedQuery q = new DeployNamedQuery(name, oql, null);
                    deployDesc.add(q);
                }
            }
        }
    }
    
    private BeanReflectFactory createReflectionFactory() {
        return new EnhanceBeanReflectFactory();
    }
    
    private void setBeanReflect(final DeployBeanDescriptor<?> desc) {
        final Class<?> beanType = desc.getBeanType();
        final Class<?> factType = desc.getFactoryType();
        final BeanReflect beanReflect = this.reflectFactory.create(beanType, factType);
        desc.setBeanReflect(beanReflect);
        try {
            final Iterator<DeployBeanProperty> it = desc.propertiesAll();
            while (it.hasNext()) {
                final DeployBeanProperty prop = it.next();
                final String propName = prop.getName();
                if (desc.isAbstract() || beanReflect.isVanillaOnly()) {
                    prop.setGetter(ReflectGetter.create(prop));
                    prop.setSetter(ReflectSetter.create(prop));
                }
                else {
                    final BeanReflectGetter getter = beanReflect.getGetter(propName);
                    final BeanReflectSetter setter = beanReflect.getSetter(propName);
                    prop.setGetter(getter);
                    prop.setSetter(setter);
                    if (getter == null) {
                        final String m = "BeanReflectGetter for " + prop.getFullBeanName() + " was not found?";
                        throw new RuntimeException(m);
                    }
                    continue;
                }
            }
        }
        catch (IllegalArgumentException e) {
            final Class<?> superClass = desc.getBeanType().getSuperclass();
            final String msg = "Error with [" + desc.getFullName() + "] I believe it is not enhanced but it's superClass [" + superClass + "] is?" + " (You are not allowed to mix enhancement in a single inheritance hierarchy)";
            throw new PersistenceException(msg, e);
        }
    }
    
    private void setConcurrencyMode(final DeployBeanDescriptor<?> desc) {
        if (!desc.getConcurrencyMode().equals(ConcurrencyMode.ALL)) {
            return;
        }
        if (this.checkForVersionProperties(desc)) {
            desc.setConcurrencyMode(ConcurrencyMode.VERSION);
        }
    }
    
    private boolean checkForVersionProperties(final DeployBeanDescriptor<?> desc) {
        boolean hasVersionProperty = false;
        final List<DeployBeanProperty> props = desc.propertiesBase();
        for (int i = 0; i < props.size(); ++i) {
            if (props.get(i).isVersionColumn()) {
                hasVersionProperty = true;
            }
        }
        return hasVersionProperty;
    }
    
    private boolean hasEntityBeanInterface(final Class<?> beanClass) {
        final Class<?>[] interfaces = beanClass.getInterfaces();
        for (int i = 0; i < interfaces.length; ++i) {
            if (interfaces[i].equals(EntityBean.class)) {
                return true;
            }
        }
        return false;
    }
    
    private void setEntityBeanClass(final DeployBeanDescriptor<?> desc) {
        final Class<?> beanClass = desc.getBeanType();
        if (desc.isAbstract()) {
            if (this.hasEntityBeanInterface(beanClass)) {
                this.checkEnhanced(desc, beanClass);
            }
            else {
                this.checkSubclass(desc, beanClass);
            }
            return;
        }
        try {
            Object testBean = null;
            try {
                testBean = beanClass.newInstance();
            }
            catch (InstantiationException e) {
                BeanDescriptorManager.logger.fine("no default constructor on " + beanClass + " e:" + e);
            }
            catch (IllegalAccessException e2) {
                BeanDescriptorManager.logger.fine("no default constructor on " + beanClass + " e:" + e2);
            }
            if (!(testBean instanceof EntityBean)) {
                this.checkSubclass(desc, beanClass);
            }
            else {
                final String className = beanClass.getName();
                try {
                    final String marker = ((EntityBean)testBean)._ebean_getMarker();
                    if (!marker.equals(className)) {
                        final String msg = "Error with [" + desc.getFullName() + "] It has not been enhanced but it's superClass [" + beanClass.getSuperclass() + "] is?" + " (You are not allowed to mix enhancement in a single inheritance hierarchy)" + " marker[" + marker + "] className[" + className + "]";
                        throw new PersistenceException(msg);
                    }
                }
                catch (AbstractMethodError e3) {
                    throw new PersistenceException("Old Ebean v1.0 enhancement detected in Ebean v1.1 - please do a clean enhancement.", e3);
                }
                this.checkEnhanced(desc, beanClass);
            }
        }
        catch (PersistenceException ex) {
            throw ex;
        }
        catch (Exception ex2) {
            throw new PersistenceException(ex2);
        }
    }
    
    private void checkEnhanced(final DeployBeanDescriptor<?> desc, final Class<?> beanClass) {
        this.checkInheritedClasses(true, beanClass);
        desc.setFactoryType(beanClass);
        if (!beanClass.getName().startsWith("com.avaje.ebean.meta")) {
            ++this.enhancedClassCount;
        }
    }
    
    private void checkSubclass(final DeployBeanDescriptor<?> desc, final Class<?> beanClass) {
        this.checkInheritedClasses(false, beanClass);
        desc.checkReadAndWriteMethods();
        ++this.subclassClassCount;
        final BeanDescriptor.EntityType entityType = desc.getEntityType();
        if (BeanDescriptor.EntityType.XMLELEMENT.equals(entityType)) {
            desc.setFactoryType(beanClass);
        }
        else {
            final Class<?> subClass = this.subClassManager.resolve(beanClass.getName());
            desc.setFactoryType(subClass);
            this.subclassedEntities.add(desc.getName());
        }
    }
    
    private void checkInheritedClasses(final boolean ensureEnhanced, final Class<?> beanClass) {
        final Class<?> superclass = beanClass.getSuperclass();
        if (Object.class.equals(superclass)) {
            return;
        }
        final boolean isClassEnhanced = EntityBean.class.isAssignableFrom(superclass);
        if (ensureEnhanced != isClassEnhanced) {
            String msg;
            if (ensureEnhanced) {
                msg = "Class [" + superclass + "] is not enhanced and [" + beanClass + "] is - (you can not mix!!)";
            }
            else {
                msg = "Class [" + superclass + "] is enhanced and [" + beanClass + "] is not - (you can not mix!!)";
            }
            throw new IllegalStateException(msg);
        }
        this.checkInheritedClasses(ensureEnhanced, superclass);
    }
    
    static {
        logger = Logger.getLogger(BeanDescriptorManager.class.getName());
        beanDescComparator = new BeanDescComparator();
    }
    
    private static final class BeanDescComparator implements Comparator<BeanDescriptor<?>>, Serializable
    {
        private static final long serialVersionUID = 1L;
        
        public int compare(final BeanDescriptor<?> o1, final BeanDescriptor<?> o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }
}
