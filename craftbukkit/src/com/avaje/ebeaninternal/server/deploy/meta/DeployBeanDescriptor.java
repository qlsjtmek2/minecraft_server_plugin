// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy.meta;

import com.avaje.ebean.meta.MetaAutoFetchStatistic;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Comparator;
import java.util.Collections;
import com.avaje.ebeaninternal.server.deploy.ChainedBeanQueryAdapter;
import com.avaje.ebeaninternal.server.deploy.ChainedBeanPersistListener;
import com.avaje.ebeaninternal.server.deploy.ChainedBeanPersistController;
import java.util.logging.Level;
import java.util.Iterator;
import java.util.Collection;
import java.lang.reflect.Modifier;
import com.avaje.ebeaninternal.server.deploy.InheritInfo;
import java.util.ArrayList;
import com.avaje.ebean.config.lucene.IndexDefn;
import com.avaje.ebean.Query;
import com.avaje.ebean.event.BeanFinder;
import com.avaje.ebeaninternal.server.core.ReferenceOptions;
import com.avaje.ebean.event.BeanQueryAdapter;
import com.avaje.ebean.event.BeanPersistListener;
import com.avaje.ebean.event.BeanPersistController;
import com.avaje.ebeaninternal.server.reflect.BeanReflect;
import com.avaje.ebean.config.TableName;
import java.util.HashMap;
import com.avaje.ebeaninternal.server.deploy.CompoundUniqueContraint;
import java.util.List;
import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
import com.avaje.ebean.config.dbplatform.IdGenerator;
import com.avaje.ebean.config.dbplatform.IdType;
import com.avaje.ebeaninternal.server.deploy.DRawSqlMeta;
import com.avaje.ebeaninternal.server.deploy.DeployNamedUpdate;
import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
import java.util.Map;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

public class DeployBeanDescriptor<T>
{
    private static final PropOrder PROP_ORDER;
    private static final String I_SCALAOBJECT = "scala.ScalaObject";
    private static final Logger logger;
    private static final String META_BEAN_PREFIX;
    private LinkedHashMap<String, DeployBeanProperty> propMap;
    private final Class<T> beanType;
    private BeanDescriptor.EntityType entityType;
    private final Map<String, DeployNamedQuery> namedQueries;
    private final Map<String, DeployNamedUpdate> namedUpdates;
    private final Map<String, DRawSqlMeta> rawSqlMetas;
    private DeployBeanPropertyAssocOne<?> unidirectional;
    private IdType idType;
    private String idGeneratorName;
    private IdGenerator idGenerator;
    private String sequenceName;
    private String ldapBaseDn;
    private String[] ldapObjectclasses;
    private String selectLastInsertedId;
    private String lazyFetchIncludes;
    private ConcurrencyMode concurrencyMode;
    private boolean updateChangesOnly;
    private String[] dependantTables;
    private List<CompoundUniqueContraint> compoundUniqueConstraints;
    private HashMap<String, String> extraAttrMap;
    private String baseTable;
    private TableName baseTableFull;
    private BeanReflect beanReflect;
    private Class<?> factoryType;
    private List<BeanPersistController> persistControllers;
    private List<BeanPersistListener<T>> persistListeners;
    private List<BeanQueryAdapter> queryAdapters;
    private ReferenceOptions referenceOptions;
    private BeanFinder<T> beanFinder;
    private Query.UseIndex useIndex;
    private IndexDefn<?> indexDefn;
    private ArrayList<DeployTableJoin> tableJoinList;
    private InheritInfo inheritInfo;
    private String name;
    private boolean processedRawSqlExtend;
    
    public DeployBeanDescriptor(final Class<T> beanType) {
        this.propMap = new LinkedHashMap<String, DeployBeanProperty>();
        this.namedQueries = new LinkedHashMap<String, DeployNamedQuery>();
        this.namedUpdates = new LinkedHashMap<String, DeployNamedUpdate>();
        this.rawSqlMetas = new LinkedHashMap<String, DRawSqlMeta>();
        this.concurrencyMode = ConcurrencyMode.ALL;
        this.extraAttrMap = new HashMap<String, String>();
        this.persistControllers = new ArrayList<BeanPersistController>();
        this.persistListeners = new ArrayList<BeanPersistListener<T>>();
        this.queryAdapters = new ArrayList<BeanQueryAdapter>();
        this.tableJoinList = new ArrayList<DeployTableJoin>();
        this.beanType = beanType;
    }
    
    public boolean isAbstract() {
        return Modifier.isAbstract(this.beanType.getModifiers());
    }
    
    public Query.UseIndex getUseIndex() {
        return this.useIndex;
    }
    
    public void setUseIndex(final Query.UseIndex useIndex) {
        this.useIndex = useIndex;
    }
    
    public IndexDefn<?> getIndexDefn() {
        return this.indexDefn;
    }
    
    public void setIndexDefn(final IndexDefn<?> indexDefn) {
        this.indexDefn = indexDefn;
    }
    
    public boolean isScalaObject() {
        final Class<?>[] interfaces = this.beanType.getInterfaces();
        for (int i = 0; i < interfaces.length; ++i) {
            final String iname = interfaces[i].getName();
            if ("scala.ScalaObject".equals(iname)) {
                return true;
            }
        }
        return false;
    }
    
    public Collection<DRawSqlMeta> getRawSqlMeta() {
        if (!this.processedRawSqlExtend) {
            this.rawSqlProcessExtend();
            this.processedRawSqlExtend = true;
        }
        return this.rawSqlMetas.values();
    }
    
    private void rawSqlProcessExtend() {
        for (final DRawSqlMeta rawSqlMeta : this.rawSqlMetas.values()) {
            final String extend = rawSqlMeta.getExtend();
            if (extend != null) {
                final DRawSqlMeta parentQuery = this.rawSqlMetas.get(extend);
                if (parentQuery == null) {
                    throw new RuntimeException("parent query [" + extend + "] not found for sql-select " + rawSqlMeta.getName());
                }
                rawSqlMeta.extend(parentQuery);
            }
        }
    }
    
    public DeployBeanTable createDeployBeanTable() {
        final DeployBeanTable beanTable = new DeployBeanTable(this.getBeanType());
        beanTable.setBaseTable(this.baseTable);
        beanTable.setIdProperties(this.propertiesId());
        return beanTable;
    }
    
    public boolean checkReadAndWriteMethods() {
        if (this.isMeta()) {
            return true;
        }
        boolean missingMethods = false;
        for (final DeployBeanProperty prop : this.propMap.values()) {
            if (!prop.isTransient()) {
                String m = "";
                if (prop.getReadMethod() == null) {
                    m += " missing readMethod ";
                }
                if (prop.getWriteMethod() == null) {
                    m += " missing writeMethod ";
                }
                if ("".equals(m)) {
                    continue;
                }
                m += ". Should it be transient?";
                final String msg = "Bean property " + this.getFullName() + "." + prop.getName() + " has " + m;
                DeployBeanDescriptor.logger.log(Level.SEVERE, msg);
                missingMethods = true;
            }
        }
        return !missingMethods;
    }
    
    public void setEntityType(final BeanDescriptor.EntityType entityType) {
        this.entityType = entityType;
    }
    
    public boolean isEmbedded() {
        return BeanDescriptor.EntityType.EMBEDDED.equals(this.entityType);
    }
    
    public boolean isBaseTableType() {
        final BeanDescriptor.EntityType et = this.getEntityType();
        return BeanDescriptor.EntityType.ORM.equals(et);
    }
    
    public BeanDescriptor.EntityType getEntityType() {
        if (this.entityType == null) {
            this.entityType = (this.isMeta() ? BeanDescriptor.EntityType.META : BeanDescriptor.EntityType.ORM);
        }
        return this.entityType;
    }
    
    private boolean isMeta() {
        return this.beanType.getName().startsWith(DeployBeanDescriptor.META_BEAN_PREFIX);
    }
    
    public void add(final DRawSqlMeta rawSqlMeta) {
        this.rawSqlMetas.put(rawSqlMeta.getName(), rawSqlMeta);
        if ("default".equals(rawSqlMeta.getName())) {
            this.setEntityType(BeanDescriptor.EntityType.SQL);
        }
    }
    
    public void add(final DeployNamedUpdate namedUpdate) {
        this.namedUpdates.put(namedUpdate.getName(), namedUpdate);
    }
    
    public void add(final DeployNamedQuery namedQuery) {
        this.namedQueries.put(namedQuery.getName(), namedQuery);
        if ("default".equals(namedQuery.getName())) {
            this.setEntityType(BeanDescriptor.EntityType.SQL);
        }
    }
    
    public Map<String, DeployNamedQuery> getNamedQueries() {
        return this.namedQueries;
    }
    
    public Map<String, DeployNamedUpdate> getNamedUpdates() {
        return this.namedUpdates;
    }
    
    public BeanReflect getBeanReflect() {
        return this.beanReflect;
    }
    
    public Class<T> getBeanType() {
        return this.beanType;
    }
    
    public Class<?> getFactoryType() {
        return this.factoryType;
    }
    
    public void setFactoryType(final Class<?> factoryType) {
        this.factoryType = factoryType;
    }
    
    public void setBeanReflect(final BeanReflect beanReflect) {
        this.beanReflect = beanReflect;
    }
    
    public InheritInfo getInheritInfo() {
        return this.inheritInfo;
    }
    
    public void setInheritInfo(final InheritInfo inheritInfo) {
        this.inheritInfo = inheritInfo;
    }
    
    public ReferenceOptions getReferenceOptions() {
        return this.referenceOptions;
    }
    
    public void setReferenceOptions(final ReferenceOptions referenceOptions) {
        this.referenceOptions = referenceOptions;
    }
    
    public DeployBeanPropertyAssocOne<?> getUnidirectional() {
        return this.unidirectional;
    }
    
    public void setUnidirectional(final DeployBeanPropertyAssocOne<?> unidirectional) {
        this.unidirectional = unidirectional;
    }
    
    public ConcurrencyMode getConcurrencyMode() {
        return this.concurrencyMode;
    }
    
    public void setConcurrencyMode(final ConcurrencyMode concurrencyMode) {
        this.concurrencyMode = concurrencyMode;
    }
    
    public String getLdapBaseDn() {
        return this.ldapBaseDn;
    }
    
    public void setLdapBaseDn(final String ldapBaseDn) {
        this.ldapBaseDn = ldapBaseDn;
    }
    
    public String[] getLdapObjectclasses() {
        return this.ldapObjectclasses;
    }
    
    public void setLdapObjectclasses(final String[] ldapObjectclasses) {
        this.ldapObjectclasses = ldapObjectclasses;
    }
    
    public boolean isUpdateChangesOnly() {
        return this.updateChangesOnly;
    }
    
    public void setUpdateChangesOnly(final boolean updateChangesOnly) {
        this.updateChangesOnly = updateChangesOnly;
    }
    
    public String[] getDependantTables() {
        return this.dependantTables;
    }
    
    public void addCompoundUniqueConstraint(final CompoundUniqueContraint c) {
        if (this.compoundUniqueConstraints == null) {
            this.compoundUniqueConstraints = new ArrayList<CompoundUniqueContraint>();
        }
        this.compoundUniqueConstraints.add(c);
    }
    
    public CompoundUniqueContraint[] getCompoundUniqueConstraints() {
        if (this.compoundUniqueConstraints == null) {
            return null;
        }
        return this.compoundUniqueConstraints.toArray(new CompoundUniqueContraint[this.compoundUniqueConstraints.size()]);
    }
    
    public void setDependantTables(final String[] dependantTables) {
        this.dependantTables = dependantTables;
    }
    
    public BeanFinder<T> getBeanFinder() {
        return this.beanFinder;
    }
    
    public void setBeanFinder(final BeanFinder<T> beanFinder) {
        this.beanFinder = beanFinder;
    }
    
    public BeanPersistController getPersistController() {
        if (this.persistControllers.size() == 0) {
            return null;
        }
        if (this.persistControllers.size() == 1) {
            return this.persistControllers.get(0);
        }
        return new ChainedBeanPersistController(this.persistControllers);
    }
    
    public BeanPersistListener<T> getPersistListener() {
        if (this.persistListeners.size() == 0) {
            return null;
        }
        if (this.persistListeners.size() == 1) {
            return this.persistListeners.get(0);
        }
        return new ChainedBeanPersistListener<T>(this.persistListeners);
    }
    
    public BeanQueryAdapter getQueryAdapter() {
        if (this.queryAdapters.size() == 0) {
            return null;
        }
        if (this.queryAdapters.size() == 1) {
            return this.queryAdapters.get(0);
        }
        return new ChainedBeanQueryAdapter(this.queryAdapters);
    }
    
    public void addPersistController(final BeanPersistController controller) {
        this.persistControllers.add(controller);
    }
    
    public void addPersistListener(final BeanPersistListener<T> listener) {
        this.persistListeners.add(listener);
    }
    
    public void addQueryAdapter(final BeanQueryAdapter queryAdapter) {
        this.queryAdapters.add(queryAdapter);
    }
    
    public boolean isUseIdGenerator() {
        return this.idType == IdType.GENERATOR;
    }
    
    public String getBaseTable() {
        return this.baseTable;
    }
    
    public TableName getBaseTableFull() {
        return this.baseTableFull;
    }
    
    public void setBaseTable(final TableName baseTableFull) {
        this.baseTableFull = baseTableFull;
        this.baseTable = ((baseTableFull == null) ? null : baseTableFull.getQualifiedName());
    }
    
    public void sortProperties() {
        final ArrayList<DeployBeanProperty> list = new ArrayList<DeployBeanProperty>();
        list.addAll(this.propMap.values());
        Collections.sort(list, DeployBeanDescriptor.PROP_ORDER);
        this.propMap = new LinkedHashMap<String, DeployBeanProperty>(list.size());
        for (int i = 0; i < list.size(); ++i) {
            this.addBeanProperty(list.get(i));
        }
    }
    
    public DeployBeanProperty addBeanProperty(final DeployBeanProperty prop) {
        return this.propMap.put(prop.getName(), prop);
    }
    
    public DeployBeanProperty getBeanProperty(final String propName) {
        return this.propMap.get(propName);
    }
    
    public Map<String, String> getExtraAttributeMap() {
        return this.extraAttrMap;
    }
    
    public String getExtraAttribute(final String key) {
        return this.extraAttrMap.get(key);
    }
    
    public void setExtraAttribute(final String key, final String value) {
        this.extraAttrMap.put(key, value);
    }
    
    public String getFullName() {
        return this.beanType.getName();
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public IdType getIdType() {
        return this.idType;
    }
    
    public void setIdType(final IdType idType) {
        this.idType = idType;
    }
    
    public String getSequenceName() {
        return this.sequenceName;
    }
    
    public void setSequenceName(final String sequenceName) {
        this.sequenceName = sequenceName;
    }
    
    public String getSelectLastInsertedId() {
        return this.selectLastInsertedId;
    }
    
    public void setSelectLastInsertedId(final String selectLastInsertedId) {
        this.selectLastInsertedId = selectLastInsertedId;
    }
    
    public String getIdGeneratorName() {
        return this.idGeneratorName;
    }
    
    public void setIdGeneratorName(final String idGeneratorName) {
        this.idGeneratorName = idGeneratorName;
    }
    
    public IdGenerator getIdGenerator() {
        return this.idGenerator;
    }
    
    public void setIdGenerator(final IdGenerator idGenerator) {
        this.idGenerator = idGenerator;
        if (idGenerator != null && idGenerator.isDbSequence()) {
            this.setSequenceName(idGenerator.getName());
        }
    }
    
    public String getLazyFetchIncludes() {
        return this.lazyFetchIncludes;
    }
    
    public void setLazyFetchIncludes(final String lazyFetchIncludes) {
        if (lazyFetchIncludes != null && lazyFetchIncludes.length() > 0) {
            this.lazyFetchIncludes = lazyFetchIncludes;
        }
    }
    
    public String toString() {
        return this.getFullName();
    }
    
    public void addTableJoin(final DeployTableJoin join) {
        this.tableJoinList.add(join);
    }
    
    public List<DeployTableJoin> getTableJoins() {
        return this.tableJoinList;
    }
    
    public Iterator<DeployBeanProperty> propertiesAll() {
        return this.propMap.values().iterator();
    }
    
    public String getDefaultSelectClause() {
        final StringBuilder sb = new StringBuilder();
        boolean hasLazyFetch = false;
        for (final DeployBeanProperty prop : this.propMap.values()) {
            if (prop.isTransient()) {
                continue;
            }
            if (prop instanceof DeployBeanPropertyAssocMany) {
                continue;
            }
            if (prop.isFetchEager()) {
                sb.append(prop.getName()).append(",");
            }
            else {
                hasLazyFetch = true;
            }
        }
        if (!hasLazyFetch) {
            return null;
        }
        final String selectClause = sb.toString();
        return selectClause.substring(0, selectClause.length() - 1);
    }
    
    public String[] getDefaultSelectDbArray(final Set<String> defaultSelect) {
        final ArrayList<String> list = new ArrayList<String>();
        for (final DeployBeanProperty p : this.propMap.values()) {
            if (defaultSelect != null) {
                if (!defaultSelect.contains(p.getName())) {
                    continue;
                }
                list.add(p.getDbColumn());
            }
            else {
                if (p.isTransient() || !p.isDbRead()) {
                    continue;
                }
                list.add(p.getDbColumn());
            }
        }
        return list.toArray(new String[list.size()]);
    }
    
    public Set<String> parseDefaultSelectClause(final String rawList) {
        if (rawList == null) {
            return null;
        }
        final String[] res = rawList.split(",");
        final LinkedHashSet<String> set = new LinkedHashSet<String>(res.length + 3);
        String temp = null;
        for (int i = 0; i < res.length; ++i) {
            temp = res[i].trim();
            if (temp.length() > 0) {
                set.add(temp);
            }
        }
        return Collections.unmodifiableSet((Set<? extends String>)set);
    }
    
    public String getSinglePrimaryKeyColumn() {
        final List<DeployBeanProperty> ids = this.propertiesId();
        if (ids.size() != 1) {
            return null;
        }
        final DeployBeanProperty p = ids.get(0);
        if (p instanceof DeployBeanPropertyAssoc) {
            return null;
        }
        return p.getDbColumn();
    }
    
    public List<DeployBeanProperty> propertiesId() {
        final ArrayList<DeployBeanProperty> list = new ArrayList<DeployBeanProperty>(2);
        for (final DeployBeanProperty prop : this.propMap.values()) {
            if (prop.isId()) {
                list.add(prop);
            }
        }
        return list;
    }
    
    public DeployBeanPropertyAssocOne<?> findJoinToTable(final String tableName) {
        final List<DeployBeanPropertyAssocOne<?>> assocOne = this.propertiesAssocOne();
        for (final DeployBeanPropertyAssocOne<?> prop : assocOne) {
            final DeployTableJoin tableJoin = prop.getTableJoin();
            if (tableJoin != null && tableJoin.getTable().equalsIgnoreCase(tableName)) {
                return prop;
            }
        }
        return null;
    }
    
    public List<DeployBeanPropertyAssocOne<?>> propertiesAssocOne() {
        final ArrayList<DeployBeanPropertyAssocOne<?>> list = new ArrayList<DeployBeanPropertyAssocOne<?>>();
        for (final DeployBeanProperty prop : this.propMap.values()) {
            if (prop instanceof DeployBeanPropertyAssocOne && !prop.isEmbedded()) {
                list.add((DeployBeanPropertyAssocOne<?>)prop);
            }
        }
        return list;
    }
    
    public List<DeployBeanPropertyAssocMany<?>> propertiesAssocMany() {
        final ArrayList<DeployBeanPropertyAssocMany<?>> list = new ArrayList<DeployBeanPropertyAssocMany<?>>();
        for (final DeployBeanProperty prop : this.propMap.values()) {
            if (prop instanceof DeployBeanPropertyAssocMany) {
                list.add((DeployBeanPropertyAssocMany<?>)prop);
            }
        }
        return list;
    }
    
    public List<DeployBeanProperty> propertiesVersion() {
        final ArrayList<DeployBeanProperty> list = new ArrayList<DeployBeanProperty>();
        for (final DeployBeanProperty prop : this.propMap.values()) {
            if (prop instanceof DeployBeanPropertyAssoc) {
                continue;
            }
            if (prop.isId() || !prop.isVersionColumn()) {
                continue;
            }
            list.add(prop);
        }
        return list;
    }
    
    public List<DeployBeanProperty> propertiesBase() {
        final ArrayList<DeployBeanProperty> list = new ArrayList<DeployBeanProperty>();
        for (final DeployBeanProperty prop : this.propMap.values()) {
            if (prop instanceof DeployBeanPropertyAssoc) {
                continue;
            }
            if (prop.isId()) {
                continue;
            }
            list.add(prop);
        }
        return list;
    }
    
    static {
        PROP_ORDER = new PropOrder();
        logger = Logger.getLogger(DeployBeanDescriptor.class.getName());
        META_BEAN_PREFIX = MetaAutoFetchStatistic.class.getName().substring(0, 20);
    }
    
    static class PropOrder implements Comparator<DeployBeanProperty>
    {
        public int compare(final DeployBeanProperty o1, final DeployBeanProperty o2) {
            final int v2 = o1.getSortOrder();
            final int v3 = o2.getSortOrder();
            return (v3 < v2) ? -1 : ((v3 == v2) ? 0 : 1);
        }
    }
}
