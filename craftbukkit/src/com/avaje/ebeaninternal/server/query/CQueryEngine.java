// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.bean.BeanCollectionTouched;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.QueryIterator;
import java.sql.SQLException;
import com.avaje.ebeaninternal.api.BeanIdList;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebeaninternal.server.persist.Binder;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebeaninternal.server.jmx.MAdminLogging;
import java.util.logging.Logger;

public class CQueryEngine
{
    private static final Logger logger;
    private final CQueryBuilder queryBuilder;
    private final MAdminLogging logControl;
    private final BackgroundExecutor backgroundExecutor;
    private final int defaultSecondaryQueryBatchSize = 100;
    
    public CQueryEngine(final DatabasePlatform dbPlatform, final MAdminLogging logControl, final Binder binder, final BackgroundExecutor backgroundExecutor, final LuceneIndexManager luceneIndexManager) {
        this.logControl = logControl;
        this.backgroundExecutor = backgroundExecutor;
        this.queryBuilder = new CQueryBuilder(backgroundExecutor, dbPlatform, binder, luceneIndexManager);
    }
    
    public <T> CQuery<T> buildQuery(final OrmQueryRequest<T> request) {
        return this.queryBuilder.buildQuery(request);
    }
    
    public <T> BeanIdList findIds(final OrmQueryRequest<T> request) {
        final CQueryFetchIds rcQuery = this.queryBuilder.buildFetchIdsQuery(request);
        try {
            String sql = rcQuery.getGeneratedSql();
            sql = sql.replace('\n', ' ');
            if (this.logControl.isDebugGeneratedSql()) {
                System.out.println(sql);
            }
            request.logSql(sql);
            final BeanIdList list = rcQuery.findIds();
            if (request.isLogSummary()) {
                request.getTransaction().logInternal(rcQuery.getSummary());
            }
            if (!list.isFetchingInBackground() && request.getQuery().isFutureFetch()) {
                CQueryEngine.logger.fine("Future findIds completed!");
                request.getTransaction().end();
            }
            return list;
        }
        catch (SQLException e) {
            throw CQuery.createPersistenceException(e, request.getTransaction(), rcQuery.getBindLog(), rcQuery.getGeneratedSql());
        }
    }
    
    public <T> int findRowCount(final OrmQueryRequest<T> request) {
        final CQueryRowCount rcQuery = this.queryBuilder.buildRowCountQuery(request);
        try {
            String sql = rcQuery.getGeneratedSql();
            sql = sql.replace('\n', ' ');
            if (this.logControl.isDebugGeneratedSql()) {
                System.out.println(sql);
            }
            request.logSql(sql);
            final int rowCount = rcQuery.findRowCount();
            if (request.isLogSummary()) {
                request.getTransaction().logInternal(rcQuery.getSummary());
            }
            if (request.getQuery().isFutureFetch()) {
                CQueryEngine.logger.fine("Future findRowCount completed!");
                request.getTransaction().end();
            }
            return rowCount;
        }
        catch (SQLException e) {
            throw CQuery.createPersistenceException(e, request.getTransaction(), rcQuery.getBindLog(), rcQuery.getGeneratedSql());
        }
    }
    
    public <T> QueryIterator<T> findIterate(final OrmQueryRequest<T> request) {
        final CQuery<T> cquery = this.queryBuilder.buildQuery(request);
        request.setCancelableQuery(cquery);
        try {
            if (this.logControl.isDebugGeneratedSql()) {
                this.logSqlToConsole(cquery);
            }
            if (request.isLogSql()) {
                this.logSql(cquery);
            }
            if (!cquery.prepareBindExecuteQuery()) {
                CQueryEngine.logger.finest("Future fetch already cancelled");
                return null;
            }
            final int iterateBufferSize = request.getSecondaryQueriesMinBatchSize(100);
            final QueryIterator<T> readIterate = cquery.readIterate(iterateBufferSize, request);
            if (request.isLogSummary()) {
                this.logFindManySummary(cquery);
            }
            return readIterate;
        }
        catch (SQLException e) {
            throw cquery.createPersistenceException(e);
        }
    }
    
    public <T> BeanCollection<T> findMany(final OrmQueryRequest<T> request) {
        boolean useBackgroundToContinueFetch = false;
        final CQuery<T> cquery = this.queryBuilder.buildQuery(request);
        request.setCancelableQuery(cquery);
        try {
            if (this.logControl.isDebugGeneratedSql()) {
                this.logSqlToConsole(cquery);
            }
            if (request.isLogSql()) {
                this.logSql(cquery);
            }
            if (!cquery.prepareBindExecuteQuery()) {
                CQueryEngine.logger.finest("Future fetch already cancelled");
                return null;
            }
            final BeanCollection<T> beanCollection = cquery.readCollection();
            if (request.getParentState() != 0) {
                beanCollection.setSharedInstance();
            }
            final BeanCollectionTouched collectionTouched = request.getQuery().getBeanCollectionTouched();
            if (collectionTouched != null) {
                beanCollection.setBeanCollectionTouched(collectionTouched);
            }
            if (cquery.useBackgroundToContinueFetch()) {
                request.setBackgroundFetching();
                useBackgroundToContinueFetch = true;
                final BackgroundFetch fetch = new BackgroundFetch(cquery);
                final FutureTask<Integer> future = new FutureTask<Integer>(fetch);
                beanCollection.setBackgroundFetch(future);
                this.backgroundExecutor.execute(future);
            }
            if (request.isLogSummary()) {
                this.logFindManySummary(cquery);
            }
            request.executeSecondaryQueries(100);
            return beanCollection;
        }
        catch (SQLException e) {
            throw cquery.createPersistenceException(e);
        }
        finally {
            if (!useBackgroundToContinueFetch) {
                if (cquery != null) {
                    cquery.close();
                }
                if (request.getQuery().isFutureFetch()) {
                    CQueryEngine.logger.fine("Future fetch completed!");
                    request.getTransaction().end();
                }
            }
        }
    }
    
    public <T> T find(final OrmQueryRequest<T> request) {
        T bean = null;
        final CQuery<T> cquery = this.queryBuilder.buildQuery(request);
        try {
            if (this.logControl.isDebugGeneratedSql()) {
                this.logSqlToConsole(cquery);
            }
            if (request.isLogSql()) {
                this.logSql(cquery);
            }
            cquery.prepareBindExecuteQuery();
            if (cquery.readBean()) {
                bean = cquery.getLoadedBean();
            }
            if (request.isLogSummary()) {
                this.logFindBeanSummary(cquery);
            }
            request.executeSecondaryQueries(100);
            return bean;
        }
        catch (SQLException e) {
            throw cquery.createPersistenceException(e);
        }
        finally {
            cquery.close();
        }
    }
    
    private void logSqlToConsole(final CQuery<?> cquery) {
        final SpiQuery<?> query = cquery.getQueryRequest().getQuery();
        final String loadMode = query.getLoadMode();
        final String loadDesc = query.getLoadDescription();
        final String sql = cquery.getGeneratedSql();
        final String summary = cquery.getSummary();
        final StringBuilder sb = new StringBuilder(1000);
        sb.append("<sql ");
        if (query.isAutofetchTuned()) {
            sb.append("tuned='true' ");
        }
        if (loadMode != null) {
            sb.append("mode='").append(loadMode).append("' ");
        }
        sb.append("summary='").append(summary);
        if (loadDesc != null) {
            sb.append("' load='").append(loadDesc);
        }
        sb.append("' >");
        sb.append('\n');
        sb.append(sql);
        sb.append('\n').append("</sql>");
        System.out.println(sb.toString());
    }
    
    private void logSql(final CQuery<?> query) {
        String sql = query.getGeneratedSql();
        sql = sql.replace('\n', ' ');
        query.getTransaction().logInternal(sql);
    }
    
    private void logFindBeanSummary(final CQuery<?> q) {
        final SpiQuery<?> query = q.getQueryRequest().getQuery();
        final String loadMode = query.getLoadMode();
        final String loadDesc = query.getLoadDescription();
        final String lazyLoadProp = query.getLazyLoadProperty();
        final ObjectGraphNode node = query.getParentNode();
        final String originKey = (node == null) ? null : node.getOriginQueryPoint().getKey();
        final StringBuilder msg = new StringBuilder(200);
        msg.append("FindBean ");
        if (loadMode != null) {
            msg.append("mode[").append(loadMode).append("] ");
        }
        msg.append("type[").append(q.getBeanName()).append("] ");
        if (query.isAutofetchTuned()) {
            msg.append("tuned[true] ");
        }
        if (originKey != null) {
            msg.append("origin[").append(originKey).append("] ");
        }
        if (lazyLoadProp != null) {
            msg.append("lazyLoadProp[").append(lazyLoadProp).append("] ");
        }
        if (loadDesc != null) {
            msg.append("load[").append(loadDesc).append("] ");
        }
        msg.append("exeMicros[").append(q.getQueryExecutionTimeMicros());
        msg.append("] rows[").append(q.getLoadedRowDetail());
        msg.append("] bind[").append(q.getBindLog()).append("]");
        q.getTransaction().logInternal(msg.toString());
    }
    
    private void logFindManySummary(final CQuery<?> q) {
        final SpiQuery<?> query = q.getQueryRequest().getQuery();
        final String loadMode = query.getLoadMode();
        final String loadDesc = query.getLoadDescription();
        final String lazyLoadProp = query.getLazyLoadProperty();
        final ObjectGraphNode node = query.getParentNode();
        final String originKey = (node == null) ? null : node.getOriginQueryPoint().getKey();
        final StringBuilder msg = new StringBuilder(200);
        msg.append("FindMany ");
        if (loadMode != null) {
            msg.append("mode[").append(loadMode).append("] ");
        }
        msg.append("type[").append(q.getBeanName()).append("] ");
        if (query.isAutofetchTuned()) {
            msg.append("tuned[true] ");
        }
        if (originKey != null) {
            msg.append("origin[").append(originKey).append("] ");
        }
        if (lazyLoadProp != null) {
            msg.append("lazyLoadProp[").append(lazyLoadProp).append("] ");
        }
        if (loadDesc != null) {
            msg.append("load[").append(loadDesc).append("] ");
        }
        msg.append("exeMicros[").append(q.getQueryExecutionTimeMicros());
        msg.append("] rows[").append(q.getLoadedRowDetail());
        msg.append("] name[").append(q.getName());
        msg.append("] predicates[").append(q.getLogWhereSql());
        msg.append("] bind[").append(q.getBindLog()).append("]");
        q.getTransaction().logInternal(msg.toString());
    }
    
    static {
        logger = Logger.getLogger(CQueryEngine.class.getName());
    }
}
