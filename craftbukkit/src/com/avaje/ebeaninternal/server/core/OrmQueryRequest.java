// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.Transaction;
import com.avaje.ebeaninternal.server.query.CancelableQuery;
import com.avaje.ebeaninternal.server.deploy.CopyContext;
import com.avaje.ebeaninternal.server.query.CQueryPlan;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import javax.persistence.PersistenceException;
import java.util.Map;
import java.util.Set;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.QueryResultVisitor;
import com.avaje.ebeaninternal.api.BeanIdList;
import java.util.List;
import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.server.deploy.DeployPropertyParserMap;
import com.avaje.ebeaninternal.server.deploy.DeployParser;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebeaninternal.server.loadcontext.DLoadContext;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebean.RawSql;
import com.avaje.ebeaninternal.api.LoadContext;
import com.avaje.ebean.event.BeanFinder;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.event.BeanQueryRequest;

public final class OrmQueryRequest<T> extends BeanRequest implements BeanQueryRequest<T>, SpiOrmQueryRequest<T>
{
    private final BeanDescriptor<T> beanDescriptor;
    private final OrmQueryEngine queryEngine;
    private final SpiQuery<T> query;
    private final boolean vanillaMode;
    private final BeanFinder<T> finder;
    private final LoadContext graphContext;
    private final int parentState;
    private final RawSql rawSql;
    private PersistenceContext persistenceContext;
    private Integer cacheKey;
    private int queryPlanHash;
    private boolean backgroundFetching;
    private boolean useBeanCache;
    private boolean useBeanCacheReadOnly;
    private LuceneOrmQueryRequest luceneQueryRequest;
    
    public OrmQueryRequest(final SpiEbeanServer server, final OrmQueryEngine queryEngine, final SpiQuery<T> query, final BeanDescriptor<T> desc, final SpiTransaction t) {
        super(server, t);
        this.beanDescriptor = desc;
        this.rawSql = query.getRawSql();
        this.finder = this.beanDescriptor.getBeanFinder();
        this.queryEngine = queryEngine;
        this.query = query;
        this.vanillaMode = query.isVanillaMode(server.isVanillaMode());
        this.parentState = this.determineParentState(query);
        final int defaultBatchSize = server.getLazyLoadBatchSize();
        (this.graphContext = new DLoadContext(this.ebeanServer, this.beanDescriptor, defaultBatchSize, this.parentState, query)).registerSecondaryQueries(query);
    }
    
    private int determineParentState(final SpiQuery<T> query) {
        if (query.isSharedInstance()) {
            return 3;
        }
        final Boolean queryReadOnly = query.isReadOnly();
        if (queryReadOnly != null) {
            if (Boolean.TRUE.equals(queryReadOnly)) {
                return 2;
            }
            return 1;
        }
        else {
            if (query.getMode().equals(SpiQuery.Mode.NORMAL) && this.beanDescriptor.calculateReadOnly(query.isReadOnly())) {
                return 2;
            }
            return 0;
        }
    }
    
    public void executeSecondaryQueries(final int defaultQueryBatch) {
        this.graphContext.executeSecondaryQueries(this, defaultQueryBatch);
    }
    
    public int getSecondaryQueriesMinBatchSize(final int defaultQueryBatch) {
        return this.graphContext.getSecondaryQueriesMinBatchSize(this, defaultQueryBatch);
    }
    
    public int getParentState() {
        return this.parentState;
    }
    
    public BeanDescriptor<T> getBeanDescriptor() {
        return this.beanDescriptor;
    }
    
    public LIndex getLuceneIndex() {
        return this.beanDescriptor.getLuceneIndex();
    }
    
    public LoadContext getGraphContext() {
        return this.graphContext;
    }
    
    public void calculateQueryPlanHash() {
        this.queryPlanHash = this.query.queryPlanHash(this);
    }
    
    public boolean isRawSql() {
        return this.rawSql != null;
    }
    
    public DeployParser createDeployParser() {
        if (this.rawSql != null) {
            return new DeployPropertyParserMap(this.rawSql.getColumnMapping().getMapping());
        }
        return this.beanDescriptor.createDeployPropertyParser();
    }
    
    public boolean isSqlSelect() {
        return this.query.isSqlSelect() && this.query.getRawSql() == null;
    }
    
    public PersistenceContext getPersistenceContext() {
        return this.persistenceContext;
    }
    
    public void initTransIfRequired() {
        if (this.query.createOwnTransaction()) {
            this.transaction = this.ebeanServer.createQueryTransaction();
            this.createdTransaction = true;
        }
        else if (this.transaction == null) {
            this.transaction = this.ebeanServer.getCurrentServerTransaction();
            if (this.transaction == null) {
                this.transaction = this.ebeanServer.createQueryTransaction();
                this.createdTransaction = true;
            }
        }
        this.persistenceContext = this.getPersistenceContext(this.query, this.transaction);
        this.graphContext.setPersistenceContext(this.persistenceContext);
    }
    
    private PersistenceContext getPersistenceContext(final SpiQuery<?> query, final SpiTransaction t) {
        PersistenceContext ctx = query.getPersistenceContext();
        if (ctx == null) {
            ctx = t.getPersistenceContext();
        }
        return ctx;
    }
    
    public void endTransIfRequired() {
        if (this.createdTransaction && !this.backgroundFetching) {
            this.transaction.rollback();
        }
    }
    
    public void setBackgroundFetching() {
        this.backgroundFetching = true;
    }
    
    public boolean isFindById() {
        return this.query.getType() == Query.Type.BEAN;
    }
    
    public boolean isVanillaMode() {
        return this.vanillaMode;
    }
    
    public LuceneOrmQueryRequest getLuceneOrmQueryRequest() {
        return this.luceneQueryRequest;
    }
    
    public void setLuceneOrmQueryRequest(final LuceneOrmQueryRequest luceneQueryRequest) {
        this.luceneQueryRequest = luceneQueryRequest;
    }
    
    public boolean isLuceneQuery() {
        return this.luceneQueryRequest != null;
    }
    
    public Object findId() {
        return this.queryEngine.findId((OrmQueryRequest<Object>)this);
    }
    
    public int findRowCount() {
        return this.queryEngine.findRowCount((OrmQueryRequest<Object>)this);
    }
    
    public List<Object> findIds() {
        final BeanIdList idList = this.queryEngine.findIds((OrmQueryRequest<Object>)this);
        return idList.getIdList();
    }
    
    public void findVisit(final QueryResultVisitor<T> visitor) {
        final QueryIterator<T> it = this.queryEngine.findIterate(this);
        try {
            while (it.hasNext() && visitor.accept(it.next())) {}
        }
        finally {
            it.close();
        }
    }
    
    public QueryIterator<T> findIterate() {
        return this.queryEngine.findIterate(this);
    }
    
    public List<T> findList() {
        final BeanCollection<T> bc = this.queryEngine.findMany(this);
        return (List<T>)(this.vanillaMode ? bc.getActualCollection() : bc);
    }
    
    public Set<?> findSet() {
        final BeanCollection<T> bc = this.queryEngine.findMany(this);
        return (Set<?>)(this.vanillaMode ? bc.getActualCollection() : bc);
    }
    
    public Map<?, ?> findMap() {
        final String mapKey = this.query.getMapKey();
        if (mapKey == null) {
            final BeanProperty[] ids = this.beanDescriptor.propertiesId();
            if (ids.length != 1) {
                final String msg = "No mapKey specified for query";
                throw new PersistenceException(msg);
            }
            this.query.setMapKey(ids[0].getName());
        }
        final BeanCollection<T> bc = this.queryEngine.findMany(this);
        return (Map<?, ?>)(this.vanillaMode ? bc.getActualCollection() : bc);
    }
    
    public Query.Type getQueryType() {
        return this.query.getType();
    }
    
    public BeanFinder<T> getBeanFinder() {
        return this.finder;
    }
    
    public SpiQuery<T> getQuery() {
        return this.query;
    }
    
    public BeanPropertyAssocMany<?> getManyProperty() {
        return this.beanDescriptor.getManyProperty(this.query);
    }
    
    public CQueryPlan getQueryPlan() {
        return this.beanDescriptor.getQueryPlan(this.queryPlanHash);
    }
    
    public int getQueryPlanHash() {
        return this.queryPlanHash;
    }
    
    public void putQueryPlan(final CQueryPlan queryPlan) {
        this.beanDescriptor.putQueryPlan(this.queryPlanHash, queryPlan);
    }
    
    public boolean isUseBeanCache() {
        return this.useBeanCache;
    }
    
    public boolean isUseBeanCacheReadOnly() {
        return this.useBeanCacheReadOnly;
    }
    
    private boolean calculateUseBeanCache() {
        this.useBeanCache = this.beanDescriptor.calculateUseCache(this.query.isUseBeanCache());
        if (this.useBeanCache) {
            this.useBeanCacheReadOnly = this.beanDescriptor.calculateReadOnly(this.query.isReadOnly());
        }
        return this.useBeanCache;
    }
    
    public T getFromPersistenceContextOrCache() {
        if (this.query.isLoadBeanCache()) {
            return null;
        }
        SpiTransaction t = this.transaction;
        if (t == null) {
            t = this.ebeanServer.getCurrentServerTransaction();
        }
        if (t != null) {
            final PersistenceContext context = t.getPersistenceContext();
            if (context != null) {
                final Object o = context.get(this.beanDescriptor.getBeanType(), this.query.getId());
                if (o != null) {
                    return (T)o;
                }
            }
        }
        if (!this.calculateUseBeanCache()) {
            return null;
        }
        final Object cachedBean = this.beanDescriptor.cacheGet(this.query.getId());
        if (cachedBean == null) {
            return null;
        }
        if (this.useBeanCacheReadOnly) {
            return (T)cachedBean;
        }
        return this.beanDescriptor.createCopyForUpdate(cachedBean, this.vanillaMode);
    }
    
    public BeanCollection<T> getFromQueryCache() {
        if (!this.query.isUseQueryCache()) {
            return null;
        }
        if (this.query.getType() == null) {
            this.cacheKey = this.query.queryHash();
        }
        else {
            this.cacheKey = 31 * this.query.queryHash() + this.query.getType().hashCode();
        }
        final BeanCollection<T> bc = this.beanDescriptor.queryCacheGet(this.cacheKey);
        if (bc != null && Boolean.FALSE.equals(this.query.isReadOnly())) {
            final CopyContext ctx = new CopyContext(this.vanillaMode, false);
            return new CopyBeanCollection<T>(bc, this.beanDescriptor, ctx, 5).copy();
        }
        return bc;
    }
    
    public void putToQueryCache(final BeanCollection<T> queryResult) {
        this.beanDescriptor.queryCachePut(this.cacheKey, queryResult);
    }
    
    public void setCancelableQuery(final CancelableQuery cancelableQuery) {
        this.query.setCancelableQuery(cancelableQuery);
    }
    
    public void logSql(final String sql) {
        if (this.transaction.isLogSql()) {
            this.transaction.logInternal(sql);
        }
    }
}
