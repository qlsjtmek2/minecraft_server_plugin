// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.text.TextException;
import com.avaje.ebeaninternal.server.text.json.ReadJsonContext;
import com.avaje.ebean.text.json.JsonWriteBeanVisitor;
import com.avaje.ebean.text.json.JsonWriter;
import com.avaje.ebeaninternal.server.text.json.WriteJsonContext;
import com.avaje.ebeaninternal.server.persist.DmlUtil;
import java.util.LinkedHashSet;
import com.avaje.ebeaninternal.server.el.ElPropertyChainBuilder;
import com.avaje.ebeaninternal.server.el.ElPropertyDeploy;
import com.avaje.ebeaninternal.server.el.ElComparatorProperty;
import com.avaje.ebeaninternal.server.el.ElComparatorCompound;
import com.avaje.ebeaninternal.util.SortByClause;
import com.avaje.ebeaninternal.util.SortByClauseParser;
import java.util.Comparator;
import java.util.Collections;
import com.avaje.ebeaninternal.server.query.SplitName;
import javax.persistence.PersistenceException;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.type.DataBind;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import com.avaje.ebeaninternal.api.SpiQuery;
import java.util.Collection;
import java.util.ArrayList;
import com.avaje.ebean.InvalidValue;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import com.avaje.ebean.config.EncryptKey;
import com.avaje.ebeaninternal.server.core.DefaultSqlUpdate;
import com.avaje.ebean.SqlUpdate;
import java.util.List;
import com.avaje.ebeaninternal.server.ldap.LdapPersistenceException;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.Attribute;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.Attributes;
import java.util.logging.Level;
import com.avaje.ebeaninternal.api.TransactionEvent;
import java.util.HashSet;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.bean.BeanLoader;
import com.avaje.ebean.bean.EntityBean;
import com.avaje.ebean.bean.BeanCollectionLoader;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanPropertyLists;
import com.avaje.ebeaninternal.server.core.InternString;
import com.avaje.ebeaninternal.server.deploy.meta.DeployBeanDescriptor;
import com.avaje.ebeaninternal.server.transaction.IndexInvalidate;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebean.cache.ServerCache;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.server.core.ReferenceOptions;
import com.avaje.ebean.cache.ServerCacheManager;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebeaninternal.server.type.TypeManager;
import com.avaje.ebean.validation.factory.Validator;
import java.util.Set;
import com.avaje.ebean.config.lucene.IndexDefn;
import com.avaje.ebean.event.BeanFinder;
import com.avaje.ebean.event.BeanQueryAdapter;
import com.avaje.ebean.event.BeanPersistListener;
import com.avaje.ebean.event.BeanPersistController;
import java.util.LinkedHashMap;
import com.avaje.ebeaninternal.server.reflect.BeanReflect;
import java.util.Map;
import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
import com.avaje.ebean.config.dbplatform.IdGenerator;
import com.avaje.ebean.config.dbplatform.IdType;
import com.avaje.ebeaninternal.server.el.ElComparator;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import com.avaje.ebeaninternal.server.query.CQueryPlan;
import com.avaje.ebeaninternal.api.SpiUpdatePlan;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class BeanDescriptor<T>
{
    private static final Logger logger;
    private final ConcurrentHashMap<Integer, SpiUpdatePlan> updatePlanCache;
    private final ConcurrentHashMap<Integer, CQueryPlan> queryPlanCache;
    private final ConcurrentHashMap<String, ElPropertyValue> elGetCache;
    private final ConcurrentHashMap<String, ElComparator<T>> comparatorCache;
    private final ConcurrentHashMap<String, BeanFkeyProperty> fkeyMap;
    private final String serverName;
    private final EntityType entityType;
    private final IdType idType;
    private final IdGenerator idGenerator;
    private final String sequenceName;
    private final String ldapBaseDn;
    private final String[] ldapObjectclasses;
    private final String selectLastInsertedId;
    private final boolean autoFetchTunable;
    private final String lazyFetchIncludes;
    private final ConcurrencyMode concurrencyMode;
    private final String[] dependantTables;
    private final CompoundUniqueContraint[] compoundUniqueConstraints;
    private final Map<String, String> extraAttrMap;
    private final String baseTable;
    private final BeanReflect beanReflect;
    private final LinkedHashMap<String, BeanProperty> propMap;
    private final LinkedHashMap<String, BeanProperty> propMapByDbColumn;
    private final Class<T> beanType;
    private final BeanDescriptorMap owner;
    private final Class<?> factoryType;
    private final boolean enhancedBean;
    private volatile BeanPersistController persistController;
    private volatile BeanPersistListener<T> persistListener;
    private volatile BeanQueryAdapter queryAdapter;
    private final BeanFinder<T> beanFinder;
    private final IndexDefn<?> luceneIndexDefn;
    private final TableJoin[] derivedTableJoins;
    private final InheritInfo inheritInfo;
    private final BeanProperty[] propertiesId;
    private final BeanProperty[] propertiesVersion;
    private final BeanProperty[] propertiesLocal;
    private final BeanPropertyAssocOne<?> unidirectional;
    private final int namesOfManyPropsHash;
    private final Set<String> namesOfManyProps;
    private final BeanPropertyAssocMany<?>[] propertiesMany;
    private final BeanPropertyAssocMany<?>[] propertiesManySave;
    private final BeanPropertyAssocMany<?>[] propertiesManyDelete;
    private final BeanPropertyAssocMany<?>[] propertiesManyToMany;
    private final BeanPropertyAssocOne<?>[] propertiesOne;
    private final BeanPropertyAssocOne<?>[] propertiesOneImported;
    private final BeanPropertyAssocOne<?>[] propertiesOneImportedSave;
    private final BeanPropertyAssocOne<?>[] propertiesOneImportedDelete;
    private final BeanPropertyAssocOne<?>[] propertiesOneExported;
    private final BeanPropertyAssocOne<?>[] propertiesOneExportedSave;
    private final BeanPropertyAssocOne<?>[] propertiesOneExportedDelete;
    private final BeanPropertyAssocOne<?>[] propertiesEmbedded;
    private final BeanProperty[] propertiesBaseScalar;
    private final BeanPropertyCompound[] propertiesBaseCompound;
    private final BeanProperty[] propertiesTransient;
    final BeanProperty[] propertiesNonTransient;
    private final BeanProperty propertyFirstVersion;
    private final BeanProperty propertySingleId;
    private final String fullName;
    private final Map<String, DeployNamedQuery> namedQueries;
    private final Map<String, DeployNamedUpdate> namedUpdates;
    private final boolean hasLocalValidation;
    private final boolean hasCascadeValidation;
    private final BeanProperty[] propertiesValidationLocal;
    private final BeanProperty[] propertiesValidationCascade;
    private final Validator[] beanValidators;
    private boolean saveRecurseSkippable;
    private boolean deleteRecurseSkippable;
    private final TypeManager typeManager;
    private final IdBinder idBinder;
    private String idBinderInLHSSql;
    private String idBinderIdSql;
    private String deleteByIdSql;
    private String deleteByIdInSql;
    private final String name;
    private final String baseTableAlias;
    private final boolean updateChangesOnly;
    private final ServerCacheManager cacheManager;
    private final ReferenceOptions referenceOptions;
    private final String defaultSelectClause;
    private final Set<String> defaultSelectClauseSet;
    private final String[] defaultSelectDbArray;
    private final String descriptorId;
    private final Query.UseIndex useIndex;
    private SpiEbeanServer ebeanServer;
    private ServerCache beanCache;
    private ServerCache queryCache;
    private LIndex luceneIndex;
    private Set<IndexInvalidate> luceneIndexInvalidations;
    
    public BeanDescriptor(final BeanDescriptorMap owner, final TypeManager typeManager, final DeployBeanDescriptor<T> deploy, final String descriptorId) {
        this.updatePlanCache = new ConcurrentHashMap<Integer, SpiUpdatePlan>();
        this.queryPlanCache = new ConcurrentHashMap<Integer, CQueryPlan>();
        this.elGetCache = new ConcurrentHashMap<String, ElPropertyValue>();
        this.comparatorCache = new ConcurrentHashMap<String, ElComparator<T>>();
        this.fkeyMap = new ConcurrentHashMap<String, BeanFkeyProperty>();
        this.owner = owner;
        this.cacheManager = owner.getCacheManager();
        this.serverName = owner.getServerName();
        this.luceneIndexDefn = deploy.getIndexDefn();
        this.entityType = deploy.getEntityType();
        this.name = InternString.intern(deploy.getName());
        this.baseTableAlias = "t0";
        this.fullName = InternString.intern(deploy.getFullName());
        this.descriptorId = descriptorId;
        this.useIndex = deploy.getUseIndex();
        this.typeManager = typeManager;
        this.beanType = deploy.getBeanType();
        this.factoryType = deploy.getFactoryType();
        this.enhancedBean = this.beanType.equals(this.factoryType);
        this.namedQueries = deploy.getNamedQueries();
        this.namedUpdates = deploy.getNamedUpdates();
        this.inheritInfo = deploy.getInheritInfo();
        this.beanFinder = deploy.getBeanFinder();
        this.persistController = deploy.getPersistController();
        this.persistListener = deploy.getPersistListener();
        this.queryAdapter = deploy.getQueryAdapter();
        this.referenceOptions = deploy.getReferenceOptions();
        this.defaultSelectClause = deploy.getDefaultSelectClause();
        this.defaultSelectClauseSet = deploy.parseDefaultSelectClause(this.defaultSelectClause);
        this.defaultSelectDbArray = deploy.getDefaultSelectDbArray(this.defaultSelectClauseSet);
        this.idType = deploy.getIdType();
        this.idGenerator = deploy.getIdGenerator();
        this.ldapBaseDn = deploy.getLdapBaseDn();
        this.ldapObjectclasses = deploy.getLdapObjectclasses();
        this.sequenceName = deploy.getSequenceName();
        this.selectLastInsertedId = deploy.getSelectLastInsertedId();
        this.lazyFetchIncludes = InternString.intern(deploy.getLazyFetchIncludes());
        this.concurrencyMode = deploy.getConcurrencyMode();
        this.updateChangesOnly = deploy.isUpdateChangesOnly();
        this.dependantTables = deploy.getDependantTables();
        this.compoundUniqueConstraints = deploy.getCompoundUniqueConstraints();
        this.extraAttrMap = deploy.getExtraAttributeMap();
        this.baseTable = InternString.intern(deploy.getBaseTable());
        this.beanReflect = deploy.getBeanReflect();
        this.autoFetchTunable = (EntityType.ORM.equals(this.entityType) && this.beanFinder == null);
        final DeployBeanPropertyLists listHelper = new DeployBeanPropertyLists(owner, this, deploy);
        this.propMap = listHelper.getPropertyMap();
        this.propMapByDbColumn = this.getReverseMap(this.propMap);
        this.propertiesTransient = listHelper.getTransients();
        this.propertiesNonTransient = listHelper.getNonTransients();
        this.propertiesBaseScalar = listHelper.getBaseScalar();
        this.propertiesBaseCompound = listHelper.getBaseCompound();
        this.propertiesId = listHelper.getId();
        this.propertiesVersion = listHelper.getVersion();
        this.propertiesEmbedded = listHelper.getEmbedded();
        this.propertiesLocal = listHelper.getLocal();
        this.unidirectional = listHelper.getUnidirectional();
        this.propertiesOne = listHelper.getOnes();
        this.propertiesOneExported = listHelper.getOneExported();
        this.propertiesOneExportedSave = listHelper.getOneExportedSave();
        this.propertiesOneExportedDelete = listHelper.getOneExportedDelete();
        this.propertiesOneImported = listHelper.getOneImported();
        this.propertiesOneImportedSave = listHelper.getOneImportedSave();
        this.propertiesOneImportedDelete = listHelper.getOneImportedDelete();
        this.propertiesMany = listHelper.getMany();
        this.propertiesManySave = listHelper.getManySave();
        this.propertiesManyDelete = listHelper.getManyDelete();
        this.propertiesManyToMany = listHelper.getManyToMany();
        this.namesOfManyProps = this.deriveManyPropNames();
        this.namesOfManyPropsHash = this.namesOfManyProps.hashCode();
        this.derivedTableJoins = listHelper.getTableJoin();
        this.propertyFirstVersion = listHelper.getFirstVersion();
        if (this.propertiesId.length == 1) {
            this.propertySingleId = this.propertiesId[0];
        }
        else {
            this.propertySingleId = null;
        }
        this.saveRecurseSkippable = (0 == this.propertiesOneExportedSave.length + this.propertiesOneImportedSave.length + this.propertiesManySave.length);
        this.deleteRecurseSkippable = (0 == this.propertiesOneExportedDelete.length + this.propertiesOneImportedDelete.length + this.propertiesManyDelete.length);
        this.propertiesValidationLocal = listHelper.getPropertiesWithValidators(false);
        this.propertiesValidationCascade = listHelper.getPropertiesWithValidators(true);
        this.beanValidators = listHelper.getBeanValidators();
        this.hasLocalValidation = (this.propertiesValidationLocal.length > 0 || this.beanValidators.length > 0);
        this.hasCascadeValidation = (this.propertiesValidationCascade.length > 0 || this.beanValidators.length > 0);
        this.idBinder = owner.createIdBinder(this.propertiesId);
    }
    
    private LinkedHashMap<String, BeanProperty> getReverseMap(final LinkedHashMap<String, BeanProperty> propMap) {
        final LinkedHashMap<String, BeanProperty> revMap = new LinkedHashMap<String, BeanProperty>(propMap.size() * 2);
        for (final BeanProperty prop : propMap.values()) {
            if (prop.getDbColumn() != null) {
                revMap.put(prop.getDbColumn(), prop);
            }
        }
        return revMap;
    }
    
    public void setEbeanServer(final SpiEbeanServer ebeanServer) {
        this.ebeanServer = ebeanServer;
        for (int i = 0; i < this.propertiesMany.length; ++i) {
            this.propertiesMany[i].setLoader(ebeanServer);
        }
    }
    
    public T createCopy(final Object source, final CopyContext ctx, final int maxDepth) {
        final Object destBean = this.createBean(ctx.isVanillaMode());
        for (int j = 0; j < this.propertiesId.length; ++j) {
            this.propertiesId[j].copyProperty(source, destBean, ctx, maxDepth);
        }
        final Object destId = this.getId(destBean);
        final Object existing = ctx.putIfAbsent(destId, destBean);
        if (existing != null) {
            return (T)existing;
        }
        for (int i = 0; i < this.propertiesNonTransient.length; ++i) {
            this.propertiesNonTransient[i].copyProperty(source, destBean, ctx, maxDepth);
        }
        if (destBean instanceof EntityBean) {
            final EntityBeanIntercept copyEbi = ((EntityBean)destBean)._ebean_getIntercept();
            if (source instanceof EntityBean) {
                final EntityBeanIntercept origEbi = ((EntityBean)source)._ebean_getIntercept();
                origEbi.copyStateTo(copyEbi);
            }
            copyEbi.setBeanLoader(0, this.ebeanServer);
            copyEbi.setPersistenceContext(ctx.getPersistenceContext());
            if (ctx.isSharing()) {
                copyEbi.setSharedInstance();
            }
        }
        return (T)destBean;
    }
    
    public T createCopyForUpdate(final Object orig, final boolean vanilla) {
        return this.createCopy(orig, new CopyContext(false, false), 3);
    }
    
    public T createCopyForSharing(final Object orig) {
        return this.createCopy(orig, new CopyContext(false, true), 3);
    }
    
    public ConcurrencyMode determineConcurrencyMode(final Object bean) {
        if (this.propertyFirstVersion == null) {
            return ConcurrencyMode.NONE;
        }
        final Object v = this.propertyFirstVersion.getValue(bean);
        return (v == null) ? ConcurrencyMode.NONE : ConcurrencyMode.VERSION;
    }
    
    public Set<String> getDirtyEmbeddedProperties(final Object bean) {
        HashSet<String> dirtyProperties = null;
        for (int i = 0; i < this.propertiesEmbedded.length; ++i) {
            final Object embValue = this.propertiesEmbedded[i].getValue(bean);
            if (embValue instanceof EntityBean) {
                if (((EntityBean)embValue)._ebean_getIntercept().isDirty()) {
                    if (dirtyProperties == null) {
                        dirtyProperties = new HashSet<String>();
                    }
                    dirtyProperties.add(this.propertiesEmbedded[i].getName());
                }
            }
            else {
                if (dirtyProperties == null) {
                    dirtyProperties = new HashSet<String>();
                }
                dirtyProperties.add(this.propertiesEmbedded[i].getName());
            }
        }
        return dirtyProperties;
    }
    
    public Set<String> determineLoadedProperties(final Object bean) {
        final HashSet<String> nonNullProps = new HashSet<String>();
        for (int j = 0; j < this.propertiesId.length; ++j) {
            if (this.propertiesId[j].getValue(bean) != null) {
                nonNullProps.add(this.propertiesId[j].getName());
            }
        }
        for (int i = 0; i < this.propertiesNonTransient.length; ++i) {
            if (this.propertiesNonTransient[i].getValue(bean) != null) {
                nonNullProps.add(this.propertiesNonTransient[i].getName());
            }
        }
        return nonNullProps;
    }
    
    public SpiEbeanServer getEbeanServer() {
        return this.ebeanServer;
    }
    
    public EntityType getEntityType() {
        return this.entityType;
    }
    
    public IndexDefn<?> getLuceneIndexDefn() {
        return this.luceneIndexDefn;
    }
    
    public Query.UseIndex getUseIndex() {
        return this.useIndex;
    }
    
    public LIndex getLuceneIndex() {
        return this.luceneIndex;
    }
    
    public void setLuceneIndex(final LIndex luceneIndex) {
        this.luceneIndex = luceneIndex;
    }
    
    public void addIndexInvalidate(final IndexInvalidate e) {
        if (this.luceneIndexInvalidations == null) {
            this.luceneIndexInvalidations = new HashSet<IndexInvalidate>();
        }
        this.luceneIndexInvalidations.add(e);
    }
    
    public boolean isNotifyLucene(final TransactionEvent txnEvent) {
        if (this.luceneIndexInvalidations != null) {
            for (final IndexInvalidate invalidate : this.luceneIndexInvalidations) {
                txnEvent.addIndexInvalidate(invalidate);
            }
        }
        return this.luceneIndex != null;
    }
    
    public void initialiseId() {
        if (BeanDescriptor.logger.isLoggable(Level.FINER)) {
            BeanDescriptor.logger.finer("BeanDescriptor initialise " + this.fullName);
        }
        if (this.inheritInfo != null) {
            this.inheritInfo.setDescriptor(this);
        }
        if (this.isEmbedded()) {
            final Iterator<BeanProperty> it = this.propertiesAll();
            while (it.hasNext()) {
                final BeanProperty prop = it.next();
                prop.initialise();
            }
        }
        else {
            final BeanProperty[] idProps = this.propertiesId();
            for (int i = 0; i < idProps.length; ++i) {
                idProps[i].initialise();
            }
        }
    }
    
    public void initialiseOther() {
        if (!this.isEmbedded()) {
            final Iterator<BeanProperty> it = this.propertiesAll();
            while (it.hasNext()) {
                final BeanProperty prop = it.next();
                if (!prop.isId()) {
                    prop.initialise();
                }
            }
        }
        if (this.unidirectional != null) {
            this.unidirectional.initialise();
        }
        this.idBinder.initialise();
        this.idBinderInLHSSql = this.idBinder.getBindIdInSql(this.baseTableAlias);
        this.idBinderIdSql = this.idBinder.getBindIdSql(this.baseTableAlias);
        final String idBinderInLHSSqlNoAlias = this.idBinder.getBindIdInSql(null);
        final String idEqualsSql = this.idBinder.getBindIdSql(null);
        this.deleteByIdSql = "delete from " + this.baseTable + " where " + idEqualsSql;
        this.deleteByIdInSql = "delete from " + this.baseTable + " where " + idBinderInLHSSqlNoAlias + " ";
        if (!this.isEmbedded()) {
            for (final DeployNamedUpdate namedUpdate : this.namedUpdates.values()) {
                final DeployUpdateParser parser = new DeployUpdateParser(this);
                namedUpdate.initialise(parser);
            }
        }
    }
    
    public void initInheritInfo() {
        if (this.inheritInfo != null) {
            if (this.saveRecurseSkippable) {
                this.saveRecurseSkippable = this.inheritInfo.isSaveRecurseSkippable();
            }
            if (this.deleteRecurseSkippable) {
                this.deleteRecurseSkippable = this.inheritInfo.isDeleteRecurseSkippable();
            }
        }
    }
    
    public void cacheInitialise() {
        if (this.referenceOptions != null && this.referenceOptions.isUseCache()) {
            this.beanCache = this.cacheManager.getBeanCache(this.beanType);
        }
    }
    
    protected boolean hasInheritance() {
        return this.inheritInfo != null;
    }
    
    protected boolean isDynamicSubclass() {
        return !this.beanType.equals(this.factoryType);
    }
    
    public void setLdapObjectClasses(final Attributes attributes) {
        if (this.ldapObjectclasses != null) {
            final BasicAttribute ocAttrs = new BasicAttribute("objectclass");
            for (int i = 0; i < this.ldapObjectclasses.length; ++i) {
                ocAttrs.add(this.ldapObjectclasses[i]);
            }
            attributes.put(ocAttrs);
        }
    }
    
    public Attributes createAttributes() {
        final Attributes attrs = new BasicAttributes(true);
        this.setLdapObjectClasses(attrs);
        return attrs;
    }
    
    public String getLdapBaseDn() {
        return this.ldapBaseDn;
    }
    
    public LdapName createLdapNameById(final Object id) throws InvalidNameException {
        final LdapName baseDn = new LdapName(this.ldapBaseDn);
        this.idBinder.createLdapNameById(baseDn, id);
        return baseDn;
    }
    
    public LdapName createLdapName(final Object bean) {
        try {
            final LdapName name = new LdapName(this.ldapBaseDn);
            if (bean != null) {
                this.idBinder.createLdapNameByBean(name, bean);
            }
            return name;
        }
        catch (InvalidNameException e) {
            throw new LdapPersistenceException(e);
        }
    }
    
    public SqlUpdate deleteById(final Object id, final List<Object> idList) {
        if (id != null) {
            return this.deleteById(id);
        }
        return this.deleteByIdList(idList);
    }
    
    private SqlUpdate deleteByIdList(final List<Object> idList) {
        final StringBuilder sb = new StringBuilder(this.deleteByIdInSql);
        final String inClause = this.idBinder.getIdInValueExprDelete(idList.size());
        sb.append(inClause);
        final DefaultSqlUpdate delete = new DefaultSqlUpdate(sb.toString());
        for (int i = 0; i < idList.size(); ++i) {
            this.idBinder.bindId(delete, idList.get(i));
        }
        return delete;
    }
    
    private SqlUpdate deleteById(final Object id) {
        final DefaultSqlUpdate sqlDelete = new DefaultSqlUpdate(this.deleteByIdSql);
        final Object[] bindValues = this.idBinder.getBindValues(id);
        for (int i = 0; i < bindValues.length; ++i) {
            sqlDelete.addParameter(bindValues[i]);
        }
        return sqlDelete;
    }
    
    public void add(final BeanFkeyProperty fkey) {
        this.fkeyMap.put(fkey.getName(), fkey);
    }
    
    public void initialiseFkeys() {
        for (int i = 0; i < this.propertiesOneImported.length; ++i) {
            this.propertiesOneImported[i].addFkey();
        }
    }
    
    public boolean calculateUseCache(final Boolean queryUseCache) {
        if (queryUseCache != null) {
            return queryUseCache;
        }
        return this.referenceOptions != null && this.referenceOptions.isUseCache();
    }
    
    public boolean calculateReadOnly(final Boolean queryReadOnly) {
        if (queryReadOnly != null) {
            return queryReadOnly;
        }
        return this.referenceOptions != null && this.referenceOptions.isReadOnly();
    }
    
    public ReferenceOptions getReferenceOptions() {
        return this.referenceOptions;
    }
    
    public EncryptKey getEncryptKey(final BeanProperty p) {
        return this.owner.getEncryptKey(this.baseTable, p.getDbColumn());
    }
    
    public EncryptKey getEncryptKey(final String tableName, final String columnName) {
        return this.owner.getEncryptKey(tableName, columnName);
    }
    
    public void runCacheWarming() {
        if (this.referenceOptions == null) {
            return;
        }
        final String warmingQuery = this.referenceOptions.getWarmingQuery();
        if (warmingQuery != null && warmingQuery.trim().length() > 0) {
            final Query<T> query = this.ebeanServer.createQuery(this.beanType, warmingQuery);
            query.setUseCache(true);
            query.setReadOnly(true);
            query.setLoadBeanCache(true);
            final List<T> list = query.findList();
            if (BeanDescriptor.logger.isLoggable(Level.INFO)) {
                final String msg = "Loaded " + this.beanType + " cache with [" + list.size() + "] beans";
                BeanDescriptor.logger.info(msg);
            }
        }
    }
    
    public boolean hasDefaultSelectClause() {
        return this.defaultSelectClause != null;
    }
    
    public String getDefaultSelectClause() {
        return this.defaultSelectClause;
    }
    
    public Set<String> getDefaultSelectClauseSet() {
        return this.defaultSelectClauseSet;
    }
    
    public String[] getDefaultSelectDbArray() {
        return this.defaultSelectDbArray;
    }
    
    public boolean isInheritanceRoot() {
        return this.inheritInfo == null || this.inheritInfo.isRoot();
    }
    
    public boolean isQueryCaching() {
        return this.queryCache != null;
    }
    
    public boolean isBeanCaching() {
        return this.beanCache != null;
    }
    
    public boolean isCacheNotify(final boolean isInsertRequest) {
        if (isInsertRequest) {
            return this.queryCache != null;
        }
        return this.beanCache != null || this.queryCache != null;
    }
    
    public boolean isUsingL2Cache() {
        return this.beanCache != null || this.luceneIndex != null;
    }
    
    public boolean isLuceneIndexed() {
        return this.luceneIndex != null;
    }
    
    public void cacheNotify(final TransactionEventTable.TableIUD tableIUD) {
        if (tableIUD.isUpdateOrDelete()) {
            this.cacheClear();
        }
        this.queryCacheClear();
    }
    
    public void queryCacheClear() {
        if (this.queryCache != null) {
            this.queryCache.clear();
        }
    }
    
    public BeanCollection<T> queryCacheGet(final Object id) {
        if (this.queryCache == null) {
            return null;
        }
        return (BeanCollection<T>)this.queryCache.get(id);
    }
    
    public void queryCachePut(final Object id, final BeanCollection<T> query) {
        if (this.queryCache == null) {
            this.queryCache = this.cacheManager.getQueryCache(this.beanType);
        }
        this.queryCache.put(id, query);
    }
    
    public void cacheClear() {
        if (this.beanCache != null) {
            this.beanCache.clear();
        }
    }
    
    public T cachePutObject(final Object bean) {
        final Object cacheBean = this.createCopyForSharing(bean);
        return (T)this.cachePut(cacheBean, true);
    }
    
    public T cachePut(T bean, final boolean alreadyShared) {
        if (this.beanCache == null) {
            this.beanCache = this.cacheManager.getBeanCache(this.beanType);
        }
        if (!alreadyShared) {
            bean = this.createCopyForSharing(bean);
        }
        final Object id = this.getId(bean);
        return (T)this.beanCache.put(id, bean);
    }
    
    public T cacheGet(final Object id) {
        if (this.beanCache == null) {
            return null;
        }
        return (T)this.beanCache.get(id);
    }
    
    public void cacheRemove(final Object id) {
        if (this.beanCache != null) {
            this.beanCache.remove(id);
        }
    }
    
    public String getBaseTableAlias() {
        return this.baseTableAlias;
    }
    
    public boolean refreshFromCache(final EntityBeanIntercept ebi, final Object id) {
        if (ebi.isUseCache() && ebi.isReference()) {
            final Object cacheBean = this.cacheGet(id);
            if (cacheBean != null) {
                final String lazyLoadProperty = ebi.getLazyLoadProperty();
                final Set<String> loadedProps = ((EntityBean)cacheBean)._ebean_getIntercept().getLoadedProps();
                if (loadedProps == null || loadedProps.contains(lazyLoadProperty)) {
                    this.refreshFromCacheBean(ebi, cacheBean, true);
                    return true;
                }
            }
        }
        return false;
    }
    
    private void refreshFromCacheBean(final EntityBeanIntercept ebi, final Object cacheBean, final boolean isLazyLoad) {
        new BeanRefreshFromCacheHelp(this, ebi, cacheBean, isLazyLoad).refresh();
    }
    
    public void preAllocateIds(final int batchSize) {
        if (this.idGenerator != null) {
            this.idGenerator.preAllocateIds(batchSize);
        }
    }
    
    public Object nextId(final Transaction t) {
        if (this.idGenerator != null) {
            return this.idGenerator.nextId(t);
        }
        return null;
    }
    
    public DeployPropertyParser createDeployPropertyParser() {
        return new DeployPropertyParser(this);
    }
    
    public String convertOrmUpdateToSql(final String ormUpdateStatement) {
        return new DeployUpdateParser(this).parse(ormUpdateStatement);
    }
    
    public void clearQueryStatistics() {
        for (final CQueryPlan queryPlan : this.queryPlanCache.values()) {
            queryPlan.resetStatistics();
        }
    }
    
    public void postLoad(final Object bean, final Set<String> includedProperties) {
        final BeanPersistController c = this.persistController;
        if (c != null) {
            c.postLoad(bean, includedProperties);
        }
    }
    
    public Iterator<CQueryPlan> queryPlans() {
        return this.queryPlanCache.values().iterator();
    }
    
    public CQueryPlan getQueryPlan(final Integer key) {
        return this.queryPlanCache.get(key);
    }
    
    public void putQueryPlan(final Integer key, final CQueryPlan plan) {
        this.queryPlanCache.put(key, plan);
    }
    
    public SpiUpdatePlan getUpdatePlan(final Integer key) {
        return this.updatePlanCache.get(key);
    }
    
    public void putUpdatePlan(final Integer key, final SpiUpdatePlan plan) {
        this.updatePlanCache.put(key, plan);
    }
    
    public TypeManager getTypeManager() {
        return this.typeManager;
    }
    
    public boolean isUpdateChangesOnly() {
        return this.updateChangesOnly;
    }
    
    public boolean isSaveRecurseSkippable() {
        return this.saveRecurseSkippable;
    }
    
    public boolean isDeleteRecurseSkippable() {
        return this.deleteRecurseSkippable;
    }
    
    public boolean hasLocalValidation() {
        return this.hasLocalValidation;
    }
    
    public boolean hasCascadeValidation() {
        return this.hasCascadeValidation;
    }
    
    public InvalidValue validate(final boolean cascade, final Object bean) {
        if (!this.hasCascadeValidation) {
            return null;
        }
        List<InvalidValue> errList = null;
        Set<String> loadedProps = null;
        if (bean instanceof EntityBean) {
            final EntityBeanIntercept ebi = ((EntityBean)bean)._ebean_getIntercept();
            loadedProps = ebi.getLoadedProps();
        }
        if (loadedProps != null) {
            for (final String propName : loadedProps) {
                final BeanProperty property = this.getBeanProperty(propName);
                if (property != null && property.hasValidationRules(cascade)) {
                    final Object value = property.getValue(bean);
                    final List<InvalidValue> errs = property.validate(cascade, value);
                    if (errs == null) {
                        continue;
                    }
                    if (errList == null) {
                        errList = new ArrayList<InvalidValue>();
                    }
                    errList.addAll(errs);
                }
            }
        }
        else {
            final BeanProperty[] props = cascade ? this.propertiesValidationCascade : this.propertiesValidationLocal;
            for (int i = 0; i < props.length; ++i) {
                final BeanProperty prop = props[i];
                final Object value = prop.getValue(bean);
                final List<InvalidValue> errs = prop.validate(cascade, value);
                if (errs != null) {
                    if (errList == null) {
                        errList = new ArrayList<InvalidValue>();
                    }
                    errList.addAll(errs);
                }
            }
        }
        for (int j = 0; j < this.beanValidators.length; ++j) {
            if (!this.beanValidators[j].isValid(bean)) {
                if (errList == null) {
                    errList = new ArrayList<InvalidValue>();
                }
                final Validator v = this.beanValidators[j];
                errList.add(new InvalidValue(v.getKey(), v.getAttributes(), this.getFullName(), null, bean));
            }
        }
        if (errList == null) {
            return null;
        }
        return new InvalidValue(null, this.getFullName(), bean, InvalidValue.toArray(errList));
    }
    
    public BeanPropertyAssocMany<?> getManyProperty(final SpiQuery<?> query) {
        final OrmQueryDetail detail = query.getDetail();
        for (int i = 0; i < this.propertiesMany.length; ++i) {
            if (detail.includes(this.propertiesMany[i].getName())) {
                return this.propertiesMany[i];
            }
        }
        return null;
    }
    
    public IdBinder getIdBinder() {
        return this.idBinder;
    }
    
    public String getIdBinderIdSql() {
        return this.idBinderIdSql;
    }
    
    public String getIdBinderInLHSSql() {
        return this.idBinderInLHSSql;
    }
    
    public void bindId(final DataBind dataBind, final Object idValue) throws SQLException {
        this.idBinder.bindId(dataBind, idValue);
    }
    
    public Object[] getBindIdValues(final Object idValue) {
        return this.idBinder.getBindValues(idValue);
    }
    
    public DeployNamedQuery getNamedQuery(final String name) {
        return this.namedQueries.get(name);
    }
    
    public DeployNamedQuery addNamedQuery(final DeployNamedQuery deployNamedQuery) {
        return this.namedQueries.put(deployNamedQuery.getName(), deployNamedQuery);
    }
    
    public DeployNamedUpdate getNamedUpdate(final String name) {
        return this.namedUpdates.get(name);
    }
    
    public Object createBean(final boolean vanillaMode) {
        return vanillaMode ? this.createVanillaBean() : this.createEntityBean();
    }
    
    public Object createVanillaBean() {
        return this.beanReflect.createVanillaBean();
    }
    
    public EntityBean createEntityBean() {
        try {
            final EntityBean eb = (EntityBean)this.beanReflect.createEntityBean();
            return eb;
        }
        catch (Exception ex) {
            throw new PersistenceException(ex);
        }
    }
    
    public T createReference(final boolean vanillaMode, final Object id, final Object parent, final ReferenceOptions options) {
        try {
            final Object bean = this.createBean(vanillaMode);
            this.convertSetId(id, bean);
            if (!vanillaMode) {
                final EntityBean eb = (EntityBean)bean;
                final EntityBeanIntercept ebi = eb._ebean_getIntercept();
                ebi.setBeanLoader(0, this.ebeanServer);
                if (parent != null) {
                    ebi.setParentBean(parent);
                }
                if (options != null) {
                    ebi.setUseCache(options.isUseCache());
                    ebi.setReadOnly(options.isReadOnly());
                }
                ebi.setReference();
            }
            return (T)bean;
        }
        catch (Exception ex) {
            throw new PersistenceException(ex);
        }
    }
    
    public BeanProperty getBeanPropertyFromDbColumn(final String dbColumn) {
        return this.propMapByDbColumn.get(dbColumn);
    }
    
    public BeanProperty getBeanPropertyFromPath(final String path) {
        final String[] split = SplitName.splitBegin(path);
        if (split[1] == null) {
            return this._findBeanProperty(split[0]);
        }
        final BeanPropertyAssoc<?> assocProp = (BeanPropertyAssoc<?>)this._findBeanProperty(split[0]);
        final BeanDescriptor<?> targetDesc = assocProp.getTargetDescriptor();
        return targetDesc.getBeanPropertyFromPath(split[1]);
    }
    
    public BeanDescriptor<?> getBeanDescriptor(final String path) {
        if (path == null) {
            return this;
        }
        final String[] splitBegin = SplitName.splitBegin(path);
        final BeanProperty beanProperty = this.propMap.get(splitBegin[0]);
        if (beanProperty instanceof BeanPropertyAssoc) {
            final BeanPropertyAssoc<?> assocProp = (BeanPropertyAssoc<?>)beanProperty;
            return assocProp.getTargetDescriptor().getBeanDescriptor(splitBegin[1]);
        }
        throw new RuntimeException("Error getting BeanDescriptor for path " + path + " from " + this.getFullName());
    }
    
    public <U> BeanDescriptor<U> getBeanDescriptor(final Class<U> otherType) {
        return this.owner.getBeanDescriptor(otherType);
    }
    
    public BeanPropertyAssocOne<?> getUnidirectional() {
        if (this.unidirectional != null) {
            return this.unidirectional;
        }
        if (this.inheritInfo != null && !this.inheritInfo.isRoot()) {
            return this.inheritInfo.getParent().getBeanDescriptor().getUnidirectional();
        }
        return null;
    }
    
    public Object getValue(final Object bean, final String property) {
        return this.getBeanProperty(property).getValue(bean);
    }
    
    public boolean isUseIdGenerator() {
        return this.idGenerator != null;
    }
    
    public String getDescriptorId() {
        return this.descriptorId;
    }
    
    public Class<T> getBeanType() {
        return this.beanType;
    }
    
    public Class<?> getFactoryType() {
        return this.factoryType;
    }
    
    public String getFullName() {
        return this.fullName;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String toString() {
        return this.fullName;
    }
    
    public Object getId(final Object bean) {
        if (this.propertySingleId == null) {
            final LinkedHashMap<String, Object> idMap = new LinkedHashMap<String, Object>();
            for (int i = 0; i < this.propertiesId.length; ++i) {
                final Object value = this.propertiesId[i].getValue(bean);
                idMap.put(this.propertiesId[i].getName(), value);
            }
            return idMap;
        }
        if (this.inheritInfo != null && !this.enhancedBean) {
            return this.propertySingleId.getValueViaReflection(bean);
        }
        return this.propertySingleId.getValue(bean);
    }
    
    public boolean isComplexId() {
        return this.idBinder.isComplexId();
    }
    
    public String getDefaultOrderBy() {
        return this.idBinder.getDefaultOrderBy();
    }
    
    public Object convertId(final Object idValue) {
        return this.idBinder.convertSetId(idValue, null);
    }
    
    public Object convertSetId(final Object idValue, final Object bean) {
        return this.idBinder.convertSetId(idValue, bean);
    }
    
    public BeanProperty getBeanProperty(final String propName) {
        return this.propMap.get(propName);
    }
    
    public void sort(final List<T> list, final String sortByClause) {
        final ElComparator<T> comparator = this.getElComparator(sortByClause);
        Collections.sort(list, comparator);
    }
    
    public ElComparator<T> getElComparator(final String propNameOrSortBy) {
        ElComparator<T> c = this.comparatorCache.get(propNameOrSortBy);
        if (c == null) {
            c = this.createComparator(propNameOrSortBy);
            this.comparatorCache.put(propNameOrSortBy, c);
        }
        return c;
    }
    
    public boolean lazyLoadMany(final EntityBeanIntercept ebi) {
        final String lazyLoadProperty = ebi.getLazyLoadProperty();
        final BeanProperty lazyLoadBeanProp = this.getBeanProperty(lazyLoadProperty);
        if (lazyLoadBeanProp instanceof BeanPropertyAssocMany) {
            final BeanPropertyAssocMany<?> manyProp = (BeanPropertyAssocMany<?>)lazyLoadBeanProp;
            manyProp.createReference(ebi.getOwner());
            final Set<String> loadedProps = ebi.getLoadedProps();
            final HashSet<String> newLoadedProps = new HashSet<String>();
            if (loadedProps != null) {
                newLoadedProps.addAll((Collection<?>)loadedProps);
            }
            newLoadedProps.add(lazyLoadProperty);
            ebi.setLoadedProps(newLoadedProps);
            ebi.setLoadedLazy();
            return true;
        }
        return false;
    }
    
    private ElComparator<T> createComparator(final String sortByClause) {
        final SortByClause sortBy = SortByClauseParser.parse(sortByClause);
        if (sortBy.size() == 1) {
            return this.createPropertyComparator(sortBy.getProperties().get(0));
        }
        final ElComparator<T>[] comparators = (ElComparator<T>[])new ElComparator[sortBy.size()];
        final List<SortByClause.Property> sortProps = sortBy.getProperties();
        for (int i = 0; i < sortProps.size(); ++i) {
            final SortByClause.Property sortProperty = sortProps.get(i);
            comparators[i] = this.createPropertyComparator(sortProperty);
        }
        return new ElComparatorCompound<T>(comparators);
    }
    
    private ElComparator<T> createPropertyComparator(final SortByClause.Property sortProp) {
        final ElPropertyValue elGetValue = this.getElGetValue(sortProp.getName());
        Boolean nullsHigh = sortProp.getNullsHigh();
        if (nullsHigh == null) {
            nullsHigh = Boolean.TRUE;
        }
        return new ElComparatorProperty<T>(elGetValue, sortProp.isAscending(), nullsHigh);
    }
    
    public ElPropertyValue getElGetValue(final String propName) {
        return this.getElPropertyValue(propName, false);
    }
    
    public ElPropertyDeploy getElPropertyDeploy(final String propName) {
        final ElPropertyDeploy fk = this.fkeyMap.get(propName);
        if (fk != null) {
            return fk;
        }
        return this.getElPropertyValue(propName, true);
    }
    
    private ElPropertyValue getElPropertyValue(final String propName, final boolean propertyDeploy) {
        ElPropertyValue elGetValue = this.elGetCache.get(propName);
        if (elGetValue == null) {
            elGetValue = this.buildElGetValue(propName, null, propertyDeploy);
            if (elGetValue == null) {
                return null;
            }
            if (elGetValue instanceof BeanFkeyProperty) {
                this.fkeyMap.put(propName, (BeanFkeyProperty)elGetValue);
            }
            else {
                this.elGetCache.put(propName, elGetValue);
            }
        }
        return elGetValue;
    }
    
    protected ElPropertyValue buildElGetValue(final String propName, final ElPropertyChainBuilder chain, final boolean propertyDeploy) {
        if (propertyDeploy && chain != null) {
            final BeanFkeyProperty fk = this.fkeyMap.get(propName);
            if (fk != null) {
                return fk.create(chain.getExpression());
            }
        }
        final int basePos = propName.indexOf(46);
        if (basePos > -1) {
            final String baseName = propName.substring(0, basePos);
            final String remainder = propName.substring(basePos + 1);
            final BeanProperty assocProp = this._findBeanProperty(baseName);
            if (assocProp == null) {
                return null;
            }
            return assocProp.buildElPropertyValue(propName, remainder, chain, propertyDeploy);
        }
        else {
            final BeanProperty property = this._findBeanProperty(propName);
            if (chain == null) {
                return property;
            }
            if (property == null) {
                throw new PersistenceException("No property found for [" + propName + "] in expression " + chain.getExpression());
            }
            if (property.containsMany()) {
                chain.setContainsMany(true);
            }
            return chain.add(property).build();
        }
    }
    
    public BeanProperty findBeanProperty(final String propName) {
        final int basePos = propName.indexOf(46);
        if (basePos > -1) {
            final String baseName = propName.substring(0, basePos);
            return this._findBeanProperty(baseName);
        }
        return this._findBeanProperty(propName);
    }
    
    private BeanProperty _findBeanProperty(final String propName) {
        final BeanProperty prop = this.propMap.get(propName);
        if (prop == null && this.inheritInfo != null) {
            return this.inheritInfo.findSubTypeProperty(propName);
        }
        return prop;
    }
    
    protected Object getBeanPropertyWithInheritance(final Object bean, final String propName) {
        final BeanDescriptor<?> desc = this.getBeanDescriptor(bean.getClass());
        final BeanProperty beanProperty = desc.findBeanProperty(propName);
        return beanProperty.getValue(bean);
    }
    
    public String getServerName() {
        return this.serverName;
    }
    
    public boolean isAutoFetchTunable() {
        return this.autoFetchTunable;
    }
    
    public InheritInfo getInheritInfo() {
        return this.inheritInfo;
    }
    
    public boolean isEmbedded() {
        return EntityType.EMBEDDED.equals(this.entityType);
    }
    
    public boolean isBaseTableType() {
        return EntityType.ORM.equals(this.entityType);
    }
    
    public ConcurrencyMode getConcurrencyMode() {
        return this.concurrencyMode;
    }
    
    public String[] getDependantTables() {
        return this.dependantTables;
    }
    
    public CompoundUniqueContraint[] getCompoundUniqueConstraints() {
        return this.compoundUniqueConstraints;
    }
    
    public BeanPersistListener<T> getPersistListener() {
        return this.persistListener;
    }
    
    public BeanFinder<T> getBeanFinder() {
        return this.beanFinder;
    }
    
    public BeanQueryAdapter getQueryAdapter() {
        return this.queryAdapter;
    }
    
    public void deregister(final BeanPersistListener<?> listener) {
        final BeanPersistListener<T> currListener = this.persistListener;
        if (currListener != null) {
            if (currListener instanceof ChainedBeanPersistListener) {
                this.persistListener = (BeanPersistListener<T>)((ChainedBeanPersistListener)currListener).deregister(listener);
            }
            else if (currListener.equals(listener)) {
                this.persistListener = null;
            }
        }
    }
    
    public void deregister(final BeanPersistController controller) {
        final BeanPersistController c = this.persistController;
        if (c != null) {
            if (c instanceof ChainedBeanPersistController) {
                this.persistController = ((ChainedBeanPersistController)c).deregister(controller);
            }
            else if (c.equals(controller)) {
                this.persistController = null;
            }
        }
    }
    
    public void register(final BeanPersistListener<?> newPersistListener) {
        if (PersistListenerManager.isRegisterFor(this.beanType, newPersistListener)) {
            final BeanPersistListener<T> currListener = this.persistListener;
            if (currListener == null) {
                this.persistListener = (BeanPersistListener<T>)newPersistListener;
            }
            else if (currListener instanceof ChainedBeanPersistListener) {
                this.persistListener = (BeanPersistListener<T>)((ChainedBeanPersistListener)currListener).register(newPersistListener);
            }
            else {
                this.persistListener = new ChainedBeanPersistListener<T>(currListener, (BeanPersistListener<T>)newPersistListener);
            }
        }
    }
    
    public void register(final BeanPersistController newController) {
        if (newController.isRegisterFor(this.beanType)) {
            final BeanPersistController c = this.persistController;
            if (c == null) {
                this.persistController = newController;
            }
            else if (c instanceof ChainedBeanPersistController) {
                this.persistController = ((ChainedBeanPersistController)c).register(newController);
            }
            else {
                this.persistController = new ChainedBeanPersistController(c, newController);
            }
        }
    }
    
    public BeanPersistController getPersistController() {
        return this.persistController;
    }
    
    public boolean isSqlSelectBased() {
        return EntityType.SQL.equals(this.entityType);
    }
    
    public boolean isLdapEntityType() {
        return EntityType.LDAP.equals(this.entityType);
    }
    
    public String getBaseTable() {
        return this.baseTable;
    }
    
    public String getExtraAttribute(final String key) {
        return this.extraAttrMap.get(key);
    }
    
    public IdType getIdType() {
        return this.idType;
    }
    
    public String getSequenceName() {
        return this.sequenceName;
    }
    
    public String getSelectLastInsertedId() {
        return this.selectLastInsertedId;
    }
    
    public IdGenerator getIdGenerator() {
        return this.idGenerator;
    }
    
    public String getLazyFetchIncludes() {
        return this.lazyFetchIncludes;
    }
    
    public TableJoin[] tableJoins() {
        return this.derivedTableJoins;
    }
    
    public Iterator<BeanProperty> propertiesAll() {
        return this.propMap.values().iterator();
    }
    
    public BeanProperty[] propertiesId() {
        return this.propertiesId;
    }
    
    public BeanProperty[] propertiesNonTransient() {
        return this.propertiesNonTransient;
    }
    
    public BeanProperty[] propertiesTransient() {
        return this.propertiesTransient;
    }
    
    public BeanProperty getSingleIdProperty() {
        return this.propertySingleId;
    }
    
    public BeanPropertyAssocOne<?>[] propertiesEmbedded() {
        return this.propertiesEmbedded;
    }
    
    public BeanPropertyAssocOne<?>[] propertiesOne() {
        return this.propertiesOne;
    }
    
    public BeanPropertyAssocOne<?>[] propertiesOneImported() {
        return this.propertiesOneImported;
    }
    
    public BeanPropertyAssocOne<?>[] propertiesOneImportedSave() {
        return this.propertiesOneImportedSave;
    }
    
    public BeanPropertyAssocOne<?>[] propertiesOneImportedDelete() {
        return this.propertiesOneImportedDelete;
    }
    
    public BeanPropertyAssocOne<?>[] propertiesOneExported() {
        return this.propertiesOneExported;
    }
    
    public BeanPropertyAssocOne<?>[] propertiesOneExportedSave() {
        return this.propertiesOneExportedSave;
    }
    
    public BeanPropertyAssocOne<?>[] propertiesOneExportedDelete() {
        return this.propertiesOneExportedDelete;
    }
    
    private Set<String> deriveManyPropNames() {
        final LinkedHashSet<String> names = new LinkedHashSet<String>();
        for (int i = 0; i < this.propertiesMany.length; ++i) {
            names.add(this.propertiesMany[i].getName());
        }
        return Collections.unmodifiableSet((Set<? extends String>)names);
    }
    
    public int getNamesOfManyPropsHash() {
        return this.namesOfManyPropsHash;
    }
    
    public Set<String> getNamesOfManyProps() {
        return this.namesOfManyProps;
    }
    
    public BeanPropertyAssocMany<?>[] propertiesMany() {
        return this.propertiesMany;
    }
    
    public BeanPropertyAssocMany<?>[] propertiesManySave() {
        return this.propertiesManySave;
    }
    
    public BeanPropertyAssocMany<?>[] propertiesManyDelete() {
        return this.propertiesManyDelete;
    }
    
    public BeanPropertyAssocMany<?>[] propertiesManyToMany() {
        return this.propertiesManyToMany;
    }
    
    public BeanProperty firstVersionProperty() {
        return this.propertyFirstVersion;
    }
    
    public boolean isVanillaInsert(final Object bean) {
        if (this.propertyFirstVersion == null) {
            return true;
        }
        final Object versionValue = this.propertyFirstVersion.getValue(bean);
        return DmlUtil.isNullOrZero(versionValue);
    }
    
    public boolean isStatelessUpdate(final Object bean) {
        if (this.propertyFirstVersion == null) {
            final Object versionValue = this.getId(bean);
            return !DmlUtil.isNullOrZero(versionValue);
        }
        final Object versionValue = this.propertyFirstVersion.getValue(bean);
        return !DmlUtil.isNullOrZero(versionValue);
    }
    
    public BeanProperty[] propertiesVersion() {
        return this.propertiesVersion;
    }
    
    public BeanProperty[] propertiesBaseScalar() {
        return this.propertiesBaseScalar;
    }
    
    public BeanPropertyCompound[] propertiesBaseCompound() {
        return this.propertiesBaseCompound;
    }
    
    public BeanProperty[] propertiesLocal() {
        return this.propertiesLocal;
    }
    
    public void jsonWrite(final WriteJsonContext ctx, final Object bean) {
        if (bean != null) {
            ctx.appendObjectBegin();
            final WriteJsonContext.WriteBeanState prevState = ctx.pushBeanState(bean);
            if (this.inheritInfo != null) {
                final InheritInfo localInheritInfo = this.inheritInfo.readType(bean.getClass());
                final String discValue = localInheritInfo.getDiscriminatorStringValue();
                final String discColumn = localInheritInfo.getDiscriminatorColumn();
                ctx.appendKeyValue(discColumn, "\"" + discValue + "\"");
                final BeanDescriptor<?> localDescriptor = localInheritInfo.getBeanDescriptor();
                localDescriptor.jsonWriteProperties(ctx, bean);
            }
            else {
                this.jsonWriteProperties(ctx, bean);
            }
            ctx.pushPreviousState(prevState);
            ctx.appendObjectEnd();
        }
    }
    
    private void jsonWriteProperties(final WriteJsonContext ctx, final Object bean) {
        final boolean referenceBean = ctx.isReferenceBean();
        final JsonWriteBeanVisitor<T> beanVisitor = (JsonWriteBeanVisitor<T>)ctx.getBeanVisitor();
        Set<String> props = ctx.getIncludeProperties();
        boolean explicitAllProps;
        if (props == null) {
            explicitAllProps = false;
        }
        else {
            explicitAllProps = props.contains("*");
            if (explicitAllProps || props.isEmpty()) {
                props = null;
            }
        }
        for (int i = 0; i < this.propertiesId.length; ++i) {
            final Object idValue = this.propertiesId[i].getValue(bean);
            if (idValue != null && (props == null || props.contains(this.propertiesId[i].getName()))) {
                this.propertiesId[i].jsonWrite(ctx, bean);
            }
        }
        if (!explicitAllProps && props == null) {
            props = ctx.getLoadedProps();
        }
        if (props != null) {
            for (final String prop : props) {
                final BeanProperty p = this.getBeanProperty(prop);
                if (!p.isId()) {
                    p.jsonWrite(ctx, bean);
                }
            }
        }
        else if (!referenceBean) {
            for (int j = 0; j < this.propertiesNonTransient.length; ++j) {
                this.propertiesNonTransient[j].jsonWrite(ctx, bean);
            }
        }
        if (beanVisitor != null) {
            beanVisitor.visit((T)bean, ctx);
        }
    }
    
    public T jsonReadBean(final ReadJsonContext ctx, final String path) {
        final ReadJsonContext.ReadBeanState beanState = this.jsonRead(ctx, path);
        if (beanState == null) {
            return null;
        }
        beanState.setLoadedState();
        return (T)beanState.getBean();
    }
    
    public ReadJsonContext.ReadBeanState jsonRead(final ReadJsonContext ctx, final String path) {
        if (!ctx.readObjectBegin()) {
            return null;
        }
        if (this.inheritInfo == null) {
            return this.jsonReadObject(ctx, path);
        }
        final String discColumn = this.inheritInfo.getRoot().getDiscriminatorColumn();
        if (!ctx.readKeyNext()) {
            final String msg = "Error reading inheritance discriminator - expected [" + discColumn + "] but no json key?";
            throw new TextException(msg);
        }
        final String propName = ctx.getTokenKey();
        if (!propName.equalsIgnoreCase(discColumn)) {
            final String msg2 = "Error reading inheritance discriminator - expected [" + discColumn + "] but read [" + propName + "]";
            throw new TextException(msg2);
        }
        final String discValue = ctx.readScalarValue();
        if (!ctx.readValueNext()) {
            final String msg3 = "Error reading inheritance discriminator [" + discColumn + "]. Expected more json name values?";
            throw new TextException(msg3);
        }
        final InheritInfo localInheritInfo = this.inheritInfo.readType(discValue);
        final BeanDescriptor<?> localDescriptor = localInheritInfo.getBeanDescriptor();
        return localDescriptor.jsonReadObject(ctx, path);
    }
    
    private ReadJsonContext.ReadBeanState jsonReadObject(final ReadJsonContext ctx, final String path) {
        final T bean = (T)this.createEntityBean();
        ctx.pushBean(bean, path, this);
        while (ctx.readKeyNext()) {
            final String propName = ctx.getTokenKey();
            final BeanProperty p = this.getBeanProperty(propName);
            if (p != null) {
                p.jsonRead(ctx, bean);
                ctx.setProperty(propName);
            }
            else {
                ctx.readUnmappedJson(propName);
            }
            if (!ctx.readValueNext()) {
                return ctx.popBeanState();
            }
        }
        return ctx.popBeanState();
    }
    
    public void setLoadedProps(final EntityBeanIntercept ebi, final Set<String> loadedProps) {
        if (this.isLoadedReference(loadedProps)) {
            ebi.setReference();
        }
        else {
            ebi.setLoadedProps(loadedProps);
        }
    }
    
    public boolean isLoadedReference(final Set<String> loadedProps) {
        if (loadedProps != null && loadedProps.size() == this.propertiesId.length) {
            for (int i = 0; i < this.propertiesId.length; ++i) {
                if (!loadedProps.contains(this.propertiesId[i].getName())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    static {
        logger = Logger.getLogger(BeanDescriptor.class.getName());
    }
    
    public enum EntityType
    {
        ORM, 
        EMBEDDED, 
        SQL, 
        META, 
        LDAP, 
        XMLELEMENT;
    }
}
