// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.querydefn;

import com.avaje.ebeaninternal.api.SpiExpressionList;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Expression;
import com.avaje.ebean.PagingList;
import com.avaje.ebean.FutureRowCount;
import com.avaje.ebean.FutureList;
import com.avaje.ebean.FutureIds;
import java.util.Map;
import java.util.Set;
import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.QueryResultVisitor;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.FetchConfig;
import com.avaje.ebean.JoinConfig;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebean.bean.CallStack;
import java.util.Iterator;
import com.avaje.ebean.bean.ObjectGraphOrigin;
import com.avaje.ebean.meta.MetaAutoFetchStatistic;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.deploy.DRawSqlSelect;
import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
import com.avaje.ebean.RawSql;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebeaninternal.util.DefaultExpressionList;
import com.avaje.ebeaninternal.api.BindParams;
import java.util.List;
import com.avaje.ebean.OrderBy;
import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.server.query.CancelableQuery;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.autofetch.AutoFetchManager;
import com.avaje.ebeaninternal.server.deploy.TableJoin;
import com.avaje.ebean.QueryListener;
import com.avaje.ebean.bean.EntityBean;
import java.util.ArrayList;
import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebean.bean.BeanCollectionTouched;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebeaninternal.api.SpiQuery;

public class DefaultOrmQuery<T> implements SpiQuery<T>
{
    private static final long serialVersionUID = 6838006264714672460L;
    private final Class<T> beanType;
    private final transient EbeanServer server;
    private transient BeanCollectionTouched beanCollectionTouched;
    private final transient ExpressionFactory expressionFactory;
    private transient ArrayList<EntityBean> contextAdditions;
    private transient QueryListener<T> queryListener;
    private transient TableJoin includeTableJoin;
    private transient AutoFetchManager autoFetchManager;
    private transient BeanDescriptor<?> beanDescriptor;
    private boolean cancelled;
    private transient CancelableQuery cancelableQuery;
    private String name;
    private Query.UseIndex useIndex;
    private Query.Type type;
    private Mode mode;
    private OrmQueryDetail detail;
    private int maxRows;
    private int firstRow;
    private String rawWhereClause;
    private OrderBy<T> orderBy;
    private String loadMode;
    private String loadDescription;
    private String generatedSql;
    private String query;
    private String additionalWhere;
    private String additionalHaving;
    private String lazyLoadProperty;
    private String lazyLoadManyPath;
    private Boolean vanillaMode;
    private boolean distinct;
    private boolean futureFetch;
    private boolean sharedInstance;
    private List<Object> partialIds;
    private int backgroundFetchAfter;
    private int timeout;
    private String mapKey;
    private Object id;
    private BindParams bindParams;
    private DefaultExpressionList<T> whereExpressions;
    private DefaultExpressionList<T> havingExpressions;
    private int bufferFetchSizeHint;
    private boolean usageProfiling;
    private boolean loadBeanCache;
    private Boolean useBeanCache;
    private Boolean useQueryCache;
    private Boolean readOnly;
    private boolean sqlSelect;
    private Boolean autoFetch;
    private boolean autoFetchTuned;
    private ObjectGraphNode parentNode;
    private int queryPlanHash;
    private transient PersistenceContext persistenceContext;
    private ManyWhereJoins manyWhereJoins;
    private RawSql rawSql;
    
    public DefaultOrmQuery(final Class<T> beanType, final EbeanServer server, final ExpressionFactory expressionFactory, final String query) {
        this.mode = Mode.NORMAL;
        this.timeout = -1;
        this.usageProfiling = true;
        this.beanType = beanType;
        this.server = server;
        this.expressionFactory = expressionFactory;
        this.detail = new OrmQueryDetail();
        this.name = "";
        if (query != null) {
            this.setQuery(query);
        }
    }
    
    public DefaultOrmQuery(final Class<T> beanType, final EbeanServer server, final ExpressionFactory expressionFactory, final DeployNamedQuery namedQuery) throws PersistenceException {
        this.mode = Mode.NORMAL;
        this.timeout = -1;
        this.usageProfiling = true;
        this.beanType = beanType;
        this.server = server;
        this.expressionFactory = expressionFactory;
        this.detail = new OrmQueryDetail();
        if (namedQuery == null) {
            this.name = "";
        }
        else {
            this.name = namedQuery.getName();
            this.sqlSelect = namedQuery.isSqlSelect();
            if (this.sqlSelect) {
                final DRawSqlSelect sqlSelect = namedQuery.getSqlSelect();
                this.additionalWhere = sqlSelect.getWhereClause();
                this.additionalHaving = sqlSelect.getHavingClause();
            }
            else if (namedQuery.isRawSql()) {
                this.rawSql = namedQuery.getRawSql();
            }
            else {
                this.setQuery(namedQuery.getQuery());
            }
        }
    }
    
    public void setBeanDescriptor(final BeanDescriptor<?> beanDescriptor) {
        this.beanDescriptor = beanDescriptor;
    }
    
    public boolean selectAllForLazyLoadProperty() {
        if (this.lazyLoadProperty != null && !this.detail.containsProperty(this.lazyLoadProperty)) {
            this.detail.select("*");
            return true;
        }
        return false;
    }
    
    public RawSql getRawSql() {
        return this.rawSql;
    }
    
    public DefaultOrmQuery<T> setRawSql(final RawSql rawSql) {
        this.rawSql = rawSql;
        return this;
    }
    
    public String getLazyLoadProperty() {
        return this.lazyLoadProperty;
    }
    
    public void setLazyLoadProperty(final String lazyLoadProperty) {
        this.lazyLoadProperty = lazyLoadProperty;
    }
    
    public String getLazyLoadManyPath() {
        return this.lazyLoadManyPath;
    }
    
    public ExpressionFactory getExpressionFactory() {
        return this.expressionFactory;
    }
    
    public void setParentState(final int parentState) {
        if (parentState == 3) {
            this.setSharedInstance();
        }
        else if (parentState == 2) {
            this.setReadOnly(true);
        }
    }
    
    public MetaAutoFetchStatistic getMetaAutoFetchStatistic() {
        if (this.parentNode != null && this.server != null) {
            final ObjectGraphOrigin origin = this.parentNode.getOriginQueryPoint();
            return this.server.find(MetaAutoFetchStatistic.class, origin.getKey());
        }
        return null;
    }
    
    public boolean initManyWhereJoins() {
        this.manyWhereJoins = new ManyWhereJoins();
        if (this.whereExpressions != null) {
            this.whereExpressions.containsMany(this.beanDescriptor, this.manyWhereJoins);
        }
        return !this.manyWhereJoins.isEmpty();
    }
    
    public ManyWhereJoins getManyWhereJoins() {
        return this.manyWhereJoins;
    }
    
    public List<OrmQueryProperties> removeQueryJoins() {
        final List<OrmQueryProperties> queryJoins = this.detail.removeSecondaryQueries();
        if (queryJoins != null && this.orderBy != null) {
            for (int i = 0; i < queryJoins.size(); ++i) {
                final OrmQueryProperties joinPath = queryJoins.get(i);
                final List<OrderBy.Property> properties = this.orderBy.getProperties();
                final Iterator<OrderBy.Property> it = properties.iterator();
                while (it.hasNext()) {
                    final OrderBy.Property property = it.next();
                    if (property.getProperty().startsWith(joinPath.getPath())) {
                        it.remove();
                        joinPath.addSecJoinOrderProperty(property);
                    }
                }
            }
        }
        return queryJoins;
    }
    
    public List<OrmQueryProperties> removeLazyJoins() {
        return this.detail.removeSecondaryLazyQueries();
    }
    
    public void setLazyLoadManyPath(final String lazyLoadManyPath) {
        this.lazyLoadManyPath = lazyLoadManyPath;
    }
    
    public void convertManyFetchJoinsToQueryJoins(final boolean allowOne, final int queryBatch) {
        this.detail.convertManyFetchJoinsToQueryJoins(this.beanDescriptor, this.lazyLoadManyPath, allowOne, queryBatch);
    }
    
    public void setSelectId() {
        this.detail.clear();
        this.select(this.beanDescriptor.getIdBinder().getIdProperty());
    }
    
    public DefaultOrmQuery<T> copy() {
        final DefaultOrmQuery<T> copy = new DefaultOrmQuery<T>(this.beanType, this.server, this.expressionFactory, (String)null);
        copy.name = this.name;
        copy.includeTableJoin = this.includeTableJoin;
        copy.autoFetchManager = this.autoFetchManager;
        copy.query = this.query;
        copy.additionalWhere = this.additionalWhere;
        copy.additionalHaving = this.additionalHaving;
        copy.distinct = this.distinct;
        copy.backgroundFetchAfter = this.backgroundFetchAfter;
        copy.timeout = this.timeout;
        copy.mapKey = this.mapKey;
        copy.id = this.id;
        copy.vanillaMode = this.vanillaMode;
        copy.loadBeanCache = this.loadBeanCache;
        copy.useBeanCache = this.useBeanCache;
        copy.useQueryCache = this.useQueryCache;
        copy.readOnly = this.readOnly;
        copy.sqlSelect = this.sqlSelect;
        if (this.detail != null) {
            copy.detail = this.detail.copy();
        }
        copy.firstRow = this.firstRow;
        copy.maxRows = this.maxRows;
        copy.rawWhereClause = this.rawWhereClause;
        if (this.orderBy != null) {
            copy.orderBy = this.orderBy.copy();
        }
        if (this.bindParams != null) {
            copy.bindParams = this.bindParams.copy();
        }
        if (this.whereExpressions != null) {
            copy.whereExpressions = this.whereExpressions.copy(copy);
        }
        if (this.havingExpressions != null) {
            copy.havingExpressions = this.havingExpressions.copy(copy);
        }
        copy.usageProfiling = this.usageProfiling;
        copy.autoFetch = this.autoFetch;
        copy.parentNode = this.parentNode;
        return copy;
    }
    
    public Query.Type getType() {
        return this.type;
    }
    
    public void setType(final Query.Type type) {
        this.type = type;
    }
    
    public Query.UseIndex getUseIndex() {
        return this.useIndex;
    }
    
    public DefaultOrmQuery<T> setUseIndex(final Query.UseIndex useIndex) {
        this.useIndex = useIndex;
        return this;
    }
    
    public String getLoadDescription() {
        return this.loadDescription;
    }
    
    public String getLoadMode() {
        return this.loadMode;
    }
    
    public void setLoadDescription(final String loadMode, final String loadDescription) {
        this.loadMode = loadMode;
        this.loadDescription = loadDescription;
    }
    
    public PersistenceContext getPersistenceContext() {
        return this.persistenceContext;
    }
    
    public void setPersistenceContext(final PersistenceContext persistenceContext) {
        this.persistenceContext = persistenceContext;
    }
    
    public boolean isDetailEmpty() {
        return this.detail.isEmpty();
    }
    
    public boolean isAutofetchTuned() {
        return this.autoFetchTuned;
    }
    
    public void setAutoFetchTuned(final boolean autoFetchTuned) {
        this.autoFetchTuned = autoFetchTuned;
    }
    
    public Boolean isAutofetch() {
        return this.sqlSelect ? Boolean.FALSE : this.autoFetch;
    }
    
    public DefaultOrmQuery<T> setAutoFetch(final boolean autoFetch) {
        return this.setAutofetch(autoFetch);
    }
    
    public DefaultOrmQuery<T> setAutofetch(final boolean autoFetch) {
        this.autoFetch = autoFetch;
        return this;
    }
    
    public AutoFetchManager getAutoFetchManager() {
        return this.autoFetchManager;
    }
    
    public void setAutoFetchManager(final AutoFetchManager autoFetchManager) {
        this.autoFetchManager = autoFetchManager;
    }
    
    public void deriveSharedInstance() {
        if (!this.sharedInstance && (Boolean.TRUE.equals(this.useQueryCache) || (Boolean.TRUE.equals(this.readOnly) && (Boolean.TRUE.equals(this.useBeanCache) || Boolean.TRUE.equals(this.loadBeanCache))))) {
            this.sharedInstance = true;
        }
    }
    
    public boolean isSharedInstance() {
        return this.sharedInstance;
    }
    
    public void setSharedInstance() {
        this.sharedInstance = true;
    }
    
    public Mode getMode() {
        return this.mode;
    }
    
    public void setMode(final Mode mode) {
        this.mode = mode;
    }
    
    public boolean isUsageProfiling() {
        return this.usageProfiling;
    }
    
    public void setUsageProfiling(final boolean usageProfiling) {
        this.usageProfiling = usageProfiling;
    }
    
    public void setParentNode(final ObjectGraphNode parentNode) {
        this.parentNode = parentNode;
    }
    
    public ObjectGraphNode getParentNode() {
        return this.parentNode;
    }
    
    public ObjectGraphNode setOrigin(final CallStack callStack) {
        final ObjectGraphOrigin o = new ObjectGraphOrigin(this.calculateOriginQueryHash(), callStack, this.beanType.getName());
        return this.parentNode = new ObjectGraphNode(o, null);
    }
    
    private int calculateOriginQueryHash() {
        int hc = this.beanType.getName().hashCode();
        hc = hc * 31 + ((this.type == null) ? 0 : this.type.ordinal());
        return hc;
    }
    
    private int calculateHash(final BeanQueryRequest<?> request) {
        int hc = this.beanType.getName().hashCode();
        hc = hc * 31 + ((this.type == null) ? 0 : this.type.ordinal());
        hc = hc * 31 + ((this.useIndex == null) ? 0 : this.useIndex.hashCode());
        hc = hc * 31 + ((this.rawSql == null) ? 0 : this.rawSql.queryHash());
        hc = hc * 31 + (this.autoFetchTuned ? 31 : 0);
        hc = hc * 31 + (this.distinct ? 31 : 0);
        hc = hc * 31 + ((this.query == null) ? 0 : this.query.hashCode());
        hc = hc * 31 + this.detail.queryPlanHash(request);
        hc = hc * 31 + ((this.firstRow == 0) ? 0 : this.firstRow);
        hc = hc * 31 + ((this.maxRows == 0) ? 0 : this.maxRows);
        hc = hc * 31 + ((this.orderBy == null) ? 0 : this.orderBy.hash());
        hc = hc * 31 + ((this.rawWhereClause == null) ? 0 : this.rawWhereClause.hashCode());
        hc = hc * 31 + ((this.additionalWhere == null) ? 0 : this.additionalWhere.hashCode());
        hc = hc * 31 + ((this.additionalHaving == null) ? 0 : this.additionalHaving.hashCode());
        hc = hc * 31 + ((this.mapKey == null) ? 0 : this.mapKey.hashCode());
        hc = hc * 31 + ((this.id != null) ? 1 : 0);
        if (this.bindParams != null) {
            hc = hc * 31 + this.bindParams.getQueryPlanHash();
        }
        if (request == null) {
            hc = hc * 31 + ((this.whereExpressions == null) ? 0 : this.whereExpressions.queryAutoFetchHash());
            hc = hc * 31 + ((this.havingExpressions == null) ? 0 : this.havingExpressions.queryAutoFetchHash());
        }
        else {
            hc = hc * 31 + ((this.whereExpressions == null) ? 0 : this.whereExpressions.queryPlanHash(request));
            hc = hc * 31 + ((this.havingExpressions == null) ? 0 : this.havingExpressions.queryPlanHash(request));
        }
        return hc;
    }
    
    public int queryAutofetchHash() {
        return this.calculateHash(null);
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        return this.queryPlanHash = this.calculateHash(request);
    }
    
    public int queryBindHash() {
        int hc = (this.id == null) ? 0 : this.id.hashCode();
        hc = hc * 31 + ((this.whereExpressions == null) ? 0 : this.whereExpressions.queryBindHash());
        hc = hc * 31 + ((this.havingExpressions == null) ? 0 : this.havingExpressions.queryBindHash());
        hc = hc * 31 + ((this.bindParams == null) ? 0 : this.bindParams.queryBindHash());
        hc = hc * 31 + ((this.contextAdditions == null) ? 0 : this.contextAdditions.hashCode());
        return hc;
    }
    
    public int queryHash() {
        int hc = this.queryPlanHash;
        hc = hc * 31 + this.queryBindHash();
        return hc;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isSqlSelect() {
        return this.sqlSelect;
    }
    
    public boolean isRawSql() {
        return this.rawSql != null;
    }
    
    public String getAdditionalWhere() {
        return this.additionalWhere;
    }
    
    public int getTimeout() {
        return this.timeout;
    }
    
    public String getAdditionalHaving() {
        return this.additionalHaving;
    }
    
    public boolean hasMaxRowsOrFirstRow() {
        return this.maxRows > 0 || this.firstRow > 0;
    }
    
    public boolean isVanillaMode(final boolean defaultVanillaMode) {
        if (this.vanillaMode != null) {
            return this.vanillaMode;
        }
        return defaultVanillaMode;
    }
    
    public DefaultOrmQuery<T> setVanillaMode(final boolean vanillaMode) {
        this.vanillaMode = vanillaMode;
        return this;
    }
    
    public Boolean isReadOnly() {
        return this.readOnly;
    }
    
    public DefaultOrmQuery<T> setReadOnly(final boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }
    
    public Boolean isUseBeanCache() {
        return this.useBeanCache;
    }
    
    public boolean isUseQueryCache() {
        return Boolean.TRUE.equals(this.useQueryCache);
    }
    
    public DefaultOrmQuery<T> setUseCache(final boolean useBeanCache) {
        this.useBeanCache = useBeanCache;
        return this;
    }
    
    public DefaultOrmQuery<T> setUseQueryCache(final boolean useQueryCache) {
        this.useQueryCache = useQueryCache;
        return this;
    }
    
    public boolean isLoadBeanCache() {
        return this.loadBeanCache;
    }
    
    public DefaultOrmQuery<T> setLoadBeanCache(final boolean loadBeanCache) {
        this.loadBeanCache = loadBeanCache;
        return this;
    }
    
    public DefaultOrmQuery<T> setTimeout(final int secs) {
        this.timeout = secs;
        return this;
    }
    
    public DefaultOrmQuery<T> setQuery(final String queryString) throws PersistenceException {
        this.query = queryString;
        final OrmQueryDetailParser parser = new OrmQueryDetailParser(queryString);
        parser.parse();
        parser.assign(this);
        return this;
    }
    
    protected void setOrmQueryDetail(final OrmQueryDetail detail) {
        this.detail = detail;
    }
    
    protected void setRawWhereClause(final String rawWhereClause) {
        this.rawWhereClause = rawWhereClause;
    }
    
    public DefaultOrmQuery<T> setProperties(final String columns) {
        return this.select(columns);
    }
    
    public void setDefaultSelectClause() {
        this.detail.setDefaultSelectClause(this.beanDescriptor);
    }
    
    public DefaultOrmQuery<T> select(final String columns) {
        this.detail.select(columns);
        return this;
    }
    
    public DefaultOrmQuery<T> join(final String property) {
        return this.join(property, (String)null, (JoinConfig)null);
    }
    
    public DefaultOrmQuery<T> join(final String property, final JoinConfig joinConfig) {
        return this.join(property, (String)null, joinConfig);
    }
    
    public DefaultOrmQuery<T> join(final String property, final String columns) {
        return this.join(property, columns, (JoinConfig)null);
    }
    
    public DefaultOrmQuery<T> join(final String property, final String columns, final JoinConfig joinConfig) {
        FetchConfig c;
        if (joinConfig == null) {
            c = null;
        }
        else {
            c = new FetchConfig();
            c.lazy(joinConfig.getLazyBatchSize());
            if (joinConfig.isQueryAll()) {
                c.query(joinConfig.getQueryBatchSize());
            }
            else {
                c.queryFirst(joinConfig.getQueryBatchSize());
            }
        }
        this.detail.addFetch(property, columns, c);
        return this;
    }
    
    public DefaultOrmQuery<T> fetch(final String property) {
        return this.fetch(property, (String)null, (FetchConfig)null);
    }
    
    public DefaultOrmQuery<T> fetch(final String property, final FetchConfig joinConfig) {
        return this.fetch(property, (String)null, joinConfig);
    }
    
    public DefaultOrmQuery<T> fetch(final String property, final String columns) {
        return this.fetch(property, columns, (FetchConfig)null);
    }
    
    public DefaultOrmQuery<T> fetch(final String property, final String columns, final FetchConfig config) {
        this.detail.addFetch(property, columns, config);
        return this;
    }
    
    public List<Object> findIds() {
        return this.server.findIds((Query<Object>)this, null);
    }
    
    public int findRowCount() {
        return this.server.findRowCount((Query<Object>)this, null);
    }
    
    public void findVisit(final QueryResultVisitor<T> visitor) {
        this.server.findVisit(this, visitor, null);
    }
    
    public QueryIterator<T> findIterate() {
        return this.server.findIterate((Query<T>)this, null);
    }
    
    public List<T> findList() {
        return this.server.findList((Query<T>)this, null);
    }
    
    public Set<T> findSet() {
        return this.server.findSet((Query<T>)this, null);
    }
    
    public Map<?, T> findMap() {
        return this.server.findMap((Query<T>)this, null);
    }
    
    public <K> Map<K, T> findMap(final String keyProperty, final Class<K> keyType) {
        this.setMapKey(keyProperty);
        return (Map<K, T>)this.findMap();
    }
    
    public T findUnique() {
        return this.server.findUnique((Query<T>)this, null);
    }
    
    public FutureIds<T> findFutureIds() {
        return this.server.findFutureIds((Query<T>)this, null);
    }
    
    public FutureList<T> findFutureList() {
        return this.server.findFutureList((Query<T>)this, null);
    }
    
    public FutureRowCount<T> findFutureRowCount() {
        return this.server.findFutureRowCount((Query<T>)this, null);
    }
    
    public PagingList<T> findPagingList(final int pageSize) {
        return this.server.findPagingList((Query<T>)this, null, pageSize);
    }
    
    public DefaultOrmQuery<T> setParameter(final int position, final Object value) {
        if (this.bindParams == null) {
            this.bindParams = new BindParams();
        }
        this.bindParams.setParameter(position, value);
        return this;
    }
    
    public DefaultOrmQuery<T> setParameter(final String name, final Object value) {
        if (this.bindParams == null) {
            this.bindParams = new BindParams();
        }
        this.bindParams.setParameter(name, value);
        return this;
    }
    
    public OrderBy<T> getOrderBy() {
        return this.orderBy;
    }
    
    public String getRawWhereClause() {
        return this.rawWhereClause;
    }
    
    public OrderBy<T> orderBy() {
        return this.order();
    }
    
    public OrderBy<T> order() {
        if (this.orderBy == null) {
            this.orderBy = new OrderBy<T>(this, null);
        }
        return this.orderBy;
    }
    
    public DefaultOrmQuery<T> setOrderBy(final String orderByClause) {
        return this.order(orderByClause);
    }
    
    public DefaultOrmQuery<T> orderBy(final String orderByClause) {
        return this.order(orderByClause);
    }
    
    public DefaultOrmQuery<T> order(final String orderByClause) {
        if (orderByClause == null || orderByClause.trim().length() == 0) {
            this.orderBy = null;
        }
        else {
            this.orderBy = new OrderBy<T>(this, orderByClause);
        }
        return this;
    }
    
    public DefaultOrmQuery<T> setOrderBy(final OrderBy<T> orderBy) {
        return this.setOrder(orderBy);
    }
    
    public DefaultOrmQuery<T> setOrder(final OrderBy<T> orderBy) {
        this.orderBy = orderBy;
        if (orderBy != null) {
            orderBy.setQuery(this);
        }
        return this;
    }
    
    public boolean isDistinct() {
        return this.distinct;
    }
    
    public DefaultOrmQuery<T> setDistinct(final boolean isDistinct) {
        this.distinct = isDistinct;
        return this;
    }
    
    public QueryListener<T> getListener() {
        return this.queryListener;
    }
    
    public DefaultOrmQuery<T> setListener(final QueryListener<T> queryListener) {
        this.queryListener = queryListener;
        return this;
    }
    
    public Class<T> getBeanType() {
        return this.beanType;
    }
    
    public void setDetail(final OrmQueryDetail detail) {
        this.detail = detail;
    }
    
    public boolean tuneFetchProperties(final OrmQueryDetail tunedDetail) {
        return this.detail.tuneFetchProperties(tunedDetail);
    }
    
    public OrmQueryDetail getDetail() {
        return this.detail;
    }
    
    public final ArrayList<EntityBean> getContextAdditions() {
        return this.contextAdditions;
    }
    
    public void contextAdd(final EntityBean bean) {
        if (this.contextAdditions == null) {
            this.contextAdditions = new ArrayList<EntityBean>();
        }
        this.contextAdditions.add(bean);
    }
    
    public String toString() {
        return "Query [" + this.whereExpressions + "]";
    }
    
    public TableJoin getIncludeTableJoin() {
        return this.includeTableJoin;
    }
    
    public void setIncludeTableJoin(final TableJoin includeTableJoin) {
        this.includeTableJoin = includeTableJoin;
    }
    
    public int getFirstRow() {
        return this.firstRow;
    }
    
    public DefaultOrmQuery<T> setFirstRow(final int firstRow) {
        this.firstRow = firstRow;
        return this;
    }
    
    public int getMaxRows() {
        return this.maxRows;
    }
    
    public DefaultOrmQuery<T> setMaxRows(final int maxRows) {
        this.maxRows = maxRows;
        return this;
    }
    
    public String getMapKey() {
        return this.mapKey;
    }
    
    public DefaultOrmQuery<T> setMapKey(final String mapKey) {
        this.mapKey = mapKey;
        return this;
    }
    
    public int getBackgroundFetchAfter() {
        return this.backgroundFetchAfter;
    }
    
    public DefaultOrmQuery<T> setBackgroundFetchAfter(final int backgroundFetchAfter) {
        this.backgroundFetchAfter = backgroundFetchAfter;
        return this;
    }
    
    public Object getId() {
        return this.id;
    }
    
    public DefaultOrmQuery<T> setId(final Object id) {
        this.id = id;
        return this;
    }
    
    public BindParams getBindParams() {
        return this.bindParams;
    }
    
    public String getQuery() {
        return this.query;
    }
    
    public DefaultOrmQuery<T> addWhere(final String addToWhereClause) {
        return this.where(addToWhereClause);
    }
    
    public DefaultOrmQuery<T> addWhere(final Expression expression) {
        return this.where(expression);
    }
    
    public ExpressionList<T> addWhere() {
        return this.where();
    }
    
    public DefaultOrmQuery<T> where(final String addToWhereClause) {
        if (this.additionalWhere == null) {
            this.additionalWhere = addToWhereClause;
        }
        else {
            this.additionalWhere = this.additionalWhere + " " + addToWhereClause;
        }
        return this;
    }
    
    public DefaultOrmQuery<T> where(final Expression expression) {
        if (this.whereExpressions == null) {
            this.whereExpressions = new DefaultExpressionList<T>(this, null);
        }
        this.whereExpressions.add(expression);
        return this;
    }
    
    public ExpressionList<T> where() {
        if (this.whereExpressions == null) {
            this.whereExpressions = new DefaultExpressionList<T>(this, null);
        }
        return this.whereExpressions;
    }
    
    public ExpressionList<T> filterMany(final String prop) {
        final OrmQueryProperties chunk = this.detail.getChunk(prop, true);
        return (ExpressionList<T>)chunk.filterMany((Query<Object>)this);
    }
    
    public void setFilterMany(final String prop, final ExpressionList<?> filterMany) {
        if (filterMany != null) {
            final OrmQueryProperties chunk = this.detail.getChunk(prop, true);
            chunk.setFilterMany((SpiExpressionList)filterMany);
        }
    }
    
    public DefaultOrmQuery<T> addHaving(final String addToHavingClause) {
        return this.having(addToHavingClause);
    }
    
    public DefaultOrmQuery<T> addHaving(final Expression expression) {
        return this.having(expression);
    }
    
    public ExpressionList<T> addHaving() {
        return this.having();
    }
    
    public DefaultOrmQuery<T> having(final String addToHavingClause) {
        if (this.additionalHaving == null) {
            this.additionalHaving = addToHavingClause;
        }
        else {
            this.additionalHaving = this.additionalHaving + " " + addToHavingClause;
        }
        return this;
    }
    
    public DefaultOrmQuery<T> having(final Expression expression) {
        if (this.havingExpressions == null) {
            this.havingExpressions = new DefaultExpressionList<T>(this, null);
        }
        this.havingExpressions.add(expression);
        return this;
    }
    
    public ExpressionList<T> having() {
        if (this.havingExpressions == null) {
            this.havingExpressions = new DefaultExpressionList<T>(this, null);
        }
        return this.havingExpressions;
    }
    
    public SpiExpressionList<T> getHavingExpressions() {
        return this.havingExpressions;
    }
    
    public SpiExpressionList<T> getWhereExpressions() {
        return this.whereExpressions;
    }
    
    public boolean createOwnTransaction() {
        return !this.futureFetch && (this.backgroundFetchAfter > 0 || this.queryListener != null);
    }
    
    public String getGeneratedSql() {
        return this.generatedSql;
    }
    
    public void setGeneratedSql(final String generatedSql) {
        this.generatedSql = generatedSql;
    }
    
    public Query<T> setBufferFetchSizeHint(final int bufferFetchSizeHint) {
        this.bufferFetchSizeHint = bufferFetchSizeHint;
        return this;
    }
    
    public int getBufferFetchSizeHint() {
        return this.bufferFetchSizeHint;
    }
    
    public void setBeanCollectionTouched(final BeanCollectionTouched notify) {
        this.beanCollectionTouched = notify;
    }
    
    public BeanCollectionTouched getBeanCollectionTouched() {
        return this.beanCollectionTouched;
    }
    
    public List<Object> getIdList() {
        return this.partialIds;
    }
    
    public void setIdList(final List<Object> partialIds) {
        this.partialIds = partialIds;
    }
    
    public boolean isFutureFetch() {
        return this.futureFetch;
    }
    
    public void setFutureFetch(final boolean backgroundFetch) {
        this.futureFetch = backgroundFetch;
    }
    
    public void setCancelableQuery(final CancelableQuery cancelableQuery) {
        synchronized (this) {
            this.cancelableQuery = cancelableQuery;
        }
    }
    
    public void cancel() {
        synchronized (this) {
            this.cancelled = true;
            if (this.cancelableQuery != null) {
                this.cancelableQuery.cancel();
            }
        }
    }
    
    public boolean isCancelled() {
        synchronized (this) {
            return this.cancelled;
        }
    }
}
