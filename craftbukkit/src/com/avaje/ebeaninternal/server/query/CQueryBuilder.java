// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import com.avaje.ebean.config.dbplatform.SqlLimitRequest;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryLimitRequest;
import java.util.Set;
import com.avaje.ebeaninternal.server.el.ElPropertyValue;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
import javax.persistence.PersistenceException;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.text.PathProperties;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryDetail;
import com.avaje.ebeaninternal.server.lucene.LIndex;
import com.avaje.ebean.OrderBy;
import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebean.Query;
import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebeaninternal.server.persist.Binder;
import com.avaje.ebean.config.dbplatform.SqlLimiter;

public class CQueryBuilder implements Constants
{
    private final String tableAliasPlaceHolder;
    private final String columnAliasPrefix;
    private final SqlLimiter sqlLimiter;
    private final RawSqlSelectClauseBuilder sqlSelectBuilder;
    private final CQueryBuilderRawSql rawSqlHandler;
    private final Binder binder;
    private final BackgroundExecutor backgroundExecutor;
    private final boolean selectCountWithAlias;
    private final boolean luceneAvailable;
    private final Query.UseIndex defaultUseIndex;
    
    public CQueryBuilder(final BackgroundExecutor backgroundExecutor, final DatabasePlatform dbPlatform, final Binder binder, final LuceneIndexManager luceneIndexManager) {
        this.luceneAvailable = luceneIndexManager.isLuceneAvailable();
        this.defaultUseIndex = luceneIndexManager.getDefaultUseIndex();
        this.backgroundExecutor = backgroundExecutor;
        this.binder = binder;
        this.tableAliasPlaceHolder = GlobalProperties.get("ebean.tableAliasPlaceHolder", "${ta}");
        this.columnAliasPrefix = GlobalProperties.get("ebean.columnAliasPrefix", "c");
        this.sqlSelectBuilder = new RawSqlSelectClauseBuilder(dbPlatform, binder);
        this.sqlLimiter = dbPlatform.getSqlLimiter();
        this.rawSqlHandler = new CQueryBuilderRawSql(this.sqlLimiter);
        this.selectCountWithAlias = dbPlatform.isSelectCountWithAlias();
    }
    
    protected String getOrderBy(String orderBy, final BeanPropertyAssocMany<?> many, final BeanDescriptor<?> desc, final boolean hasListener) {
        String manyOrderBy = null;
        if (many != null) {
            manyOrderBy = many.getFetchOrderBy();
            if (manyOrderBy != null) {
                manyOrderBy = prefixOrderByFields(many.getName(), manyOrderBy);
            }
        }
        if (orderBy == null && (hasListener || manyOrderBy != null)) {
            final StringBuffer sb = new StringBuffer();
            final BeanProperty[] uids = desc.propertiesId();
            for (int i = 0; i < uids.length; ++i) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(uids[i].getName());
            }
            orderBy = sb.toString();
        }
        if (manyOrderBy != null) {
            orderBy = orderBy + " , " + manyOrderBy;
        }
        return orderBy;
    }
    
    public static String prefixOrderByFields(final String name, final String orderBy) {
        final StringBuilder sb = new StringBuilder();
        for (final String token : orderBy.split(",")) {
            if (sb.length() > 0) {
                sb.append(", ");
            }
            sb.append(name);
            sb.append(".");
            sb.append(token.trim());
        }
        return sb.toString();
    }
    
    public <T> CQueryFetchIds buildFetchIdsQuery(final OrmQueryRequest<T> request) {
        final SpiQuery<T> query = request.getQuery();
        query.setSelectId();
        final CQueryPredicates predicates = new CQueryPredicates(this.binder, request);
        CQueryPlan queryPlan = request.getQueryPlan();
        if (queryPlan != null) {
            predicates.prepare(false);
            final String sql = queryPlan.getSql();
            return new CQueryFetchIds(request, predicates, sql, this.backgroundExecutor);
        }
        String sql;
        if (this.isLuceneSupported(request) && predicates.isLuceneResolvable()) {
            final SqlTree sqlTree = this.createLuceneSqlTree(request, predicates);
            queryPlan = new CQueryPlanLucene(request, sqlTree);
            sql = "Lucene Index";
        }
        else {
            predicates.prepare(true);
            final SqlTree sqlTree = this.createSqlTree(request, predicates);
            final SqlLimitResponse s = this.buildSql(null, request, predicates, sqlTree);
            sql = s.getSql();
            queryPlan = new CQueryPlan(sql, sqlTree, false, s.isIncludesRowNumberColumn(), predicates.getLogWhereSql());
        }
        request.putQueryPlan(queryPlan);
        return new CQueryFetchIds(request, predicates, sql, this.backgroundExecutor);
    }
    
    public <T> CQueryRowCount buildRowCountQuery(final OrmQueryRequest<T> request) {
        final SpiQuery<T> query = request.getQuery();
        query.setOrder(null);
        final boolean hasMany = !query.getManyWhereJoins().isEmpty();
        query.setSelectId();
        String sqlSelect = "select count(*)";
        if (hasMany) {
            query.setDistinct(true);
            sqlSelect = null;
        }
        final CQueryPredicates predicates = new CQueryPredicates(this.binder, request);
        CQueryPlan queryPlan = request.getQueryPlan();
        if (queryPlan != null) {
            predicates.prepare(false);
            final String sql = queryPlan.getSql();
            return new CQueryRowCount(request, predicates, sql);
        }
        predicates.prepare(true);
        final SqlTree sqlTree = this.createSqlTree(request, predicates);
        final SqlLimitResponse s = this.buildSql(sqlSelect, request, predicates, sqlTree);
        String sql2 = s.getSql();
        if (hasMany) {
            sql2 = "select count(*) from ( " + sql2 + ")";
            if (this.selectCountWithAlias) {
                sql2 += " as c";
            }
        }
        queryPlan = new CQueryPlan(sql2, sqlTree, false, s.isIncludesRowNumberColumn(), predicates.getLogWhereSql());
        request.putQueryPlan(queryPlan);
        return new CQueryRowCount(request, predicates, sql2);
    }
    
    private boolean isLuceneSupported(final OrmQueryRequest<?> request) {
        if (!this.luceneAvailable) {
            return false;
        }
        Query.UseIndex useIndex = request.getQuery().getUseIndex();
        if (useIndex == null) {
            useIndex = request.getBeanDescriptor().getUseIndex();
            if (useIndex == null) {
                useIndex = this.defaultUseIndex;
            }
        }
        return !Query.UseIndex.NO.equals(useIndex);
    }
    
    public <T> CQuery<T> buildQuery(final OrmQueryRequest<T> request) {
        if (request.isSqlSelect()) {
            return this.sqlSelectBuilder.build(request);
        }
        final CQueryPredicates predicates = new CQueryPredicates(this.binder, request);
        CQueryPlan queryPlan = request.getQueryPlan();
        if (queryPlan != null) {
            if (queryPlan.isLucene()) {
                predicates.isLuceneResolvable();
            }
            else {
                predicates.prepare(false);
            }
            return new CQuery<T>(request, predicates, queryPlan);
        }
        if (this.isLuceneSupported(request) && predicates.isLuceneResolvable()) {
            final SqlTree sqlTree = this.createLuceneSqlTree(request, predicates);
            queryPlan = new CQueryPlanLucene(request, sqlTree);
        }
        else {
            predicates.prepare(true);
            final SqlTree sqlTree = this.createSqlTree(request, predicates);
            final SqlLimitResponse res = this.buildSql(null, request, predicates, sqlTree);
            final boolean rawSql = request.isRawSql();
            if (rawSql) {
                queryPlan = new CQueryPlanRawSql(request, res, sqlTree, predicates.getLogWhereSql());
            }
            else {
                queryPlan = new CQueryPlan(request, res, sqlTree, rawSql, predicates.getLogWhereSql(), null);
            }
        }
        request.putQueryPlan(queryPlan);
        return new CQuery<T>(request, predicates, queryPlan);
    }
    
    private SqlTree createSqlTree(final OrmQueryRequest<?> request, final CQueryPredicates predicates) {
        if (request.isRawSql()) {
            return this.createRawSqlSqlTree(request, predicates);
        }
        return new SqlTreeBuilder(this.tableAliasPlaceHolder, this.columnAliasPrefix, request, predicates).build();
    }
    
    private SqlTree createLuceneSqlTree(final OrmQueryRequest<?> request, final CQueryPredicates predicates) {
        final LIndex luceneIndex = request.getLuceneIndex();
        final OrmQueryDetail ormQueryDetail = luceneIndex.getOrmQueryDetail();
        return new SqlTreeBuilder(request, predicates, ormQueryDetail).build();
    }
    
    private SqlTree createRawSqlSqlTree(final OrmQueryRequest<?> request, final CQueryPredicates predicates) {
        final BeanDescriptor<?> descriptor = request.getBeanDescriptor();
        final RawSql.ColumnMapping columnMapping = request.getQuery().getRawSql().getColumnMapping();
        final PathProperties pathProps = new PathProperties();
        final Iterator<RawSql.ColumnMapping.Column> it = columnMapping.getColumns();
        while (it.hasNext()) {
            final RawSql.ColumnMapping.Column column = it.next();
            String propertyName = column.getPropertyName();
            if (!"$$_IGNORE_COLUMN_$$".equals(propertyName)) {
                final ElPropertyValue el = descriptor.getElGetValue(propertyName);
                if (el == null) {
                    final String msg = "Property [" + propertyName + "] not found on " + descriptor.getFullName();
                    throw new PersistenceException(msg);
                }
                final BeanProperty beanProperty = el.getBeanProperty();
                if (beanProperty.isId()) {
                    propertyName = SplitName.parent(propertyName);
                }
                else if (beanProperty instanceof BeanPropertyAssocOne) {
                    String msg2 = "Column [" + column.getDbColumn() + "] mapped to complex Property[" + propertyName + "]";
                    msg2 += ". It should be mapped to a simple property (proably the Id property). ";
                    throw new PersistenceException(msg2);
                }
                if (propertyName == null) {
                    continue;
                }
                final String[] pathProp = SplitName.split(propertyName);
                pathProps.addToPath(pathProp[0], pathProp[1]);
            }
        }
        final OrmQueryDetail detail = new OrmQueryDetail();
        for (final String path : pathProps.getPaths()) {
            final Set<String> props = pathProps.get(path);
            detail.getChunk(path, true).setDefaultProperties(null, props);
        }
        return new SqlTreeBuilder(request, predicates, detail).build();
    }
    
    private SqlLimitResponse buildSql(final String selectClause, final OrmQueryRequest<?> request, final CQueryPredicates predicates, final SqlTree select) {
        final SpiQuery<?> query = request.getQuery();
        final RawSql rawSql = query.getRawSql();
        if (rawSql != null) {
            return this.rawSqlHandler.buildSql(request, predicates, rawSql.getSql());
        }
        final BeanPropertyAssocMany<?> manyProp = select.getManyProperty();
        boolean useSqlLimiter = false;
        final StringBuilder sb = new StringBuilder(500);
        if (selectClause != null) {
            sb.append(selectClause);
        }
        else {
            useSqlLimiter = (query.hasMaxRowsOrFirstRow() && manyProp == null);
            if (!useSqlLimiter) {
                sb.append("select ");
                if (query.isDistinct()) {
                    sb.append("distinct ");
                }
            }
            sb.append(select.getSelectSql());
        }
        sb.append(" ").append('\n');
        sb.append("from ");
        sb.append(select.getFromSql());
        final String inheritanceWhere = select.getInheritanceWhereSql();
        boolean hasWhere = false;
        if (inheritanceWhere.length() > 0) {
            sb.append(" ").append('\n').append("where");
            sb.append(inheritanceWhere);
            hasWhere = true;
        }
        if (request.isFindById() || query.getId() != null) {
            if (hasWhere) {
                sb.append(" and ");
            }
            else {
                sb.append('\n').append("where ");
            }
            final BeanDescriptor<?> desc = request.getBeanDescriptor();
            final String idSql = desc.getIdBinderIdSql();
            sb.append(idSql).append(" ");
            hasWhere = true;
        }
        final String dbWhere = predicates.getDbWhere();
        if (!this.isEmpty(dbWhere)) {
            if (!hasWhere) {
                hasWhere = true;
                sb.append(" ").append('\n').append("where ");
            }
            else {
                sb.append("and ");
            }
            sb.append(dbWhere);
        }
        final String dbFilterMany = predicates.getDbFilterMany();
        if (!this.isEmpty(dbFilterMany)) {
            if (!hasWhere) {
                sb.append(" ").append('\n').append("where ");
            }
            else {
                sb.append("and ");
            }
            sb.append(dbFilterMany);
        }
        final String dbOrderBy = predicates.getDbOrderBy();
        if (dbOrderBy != null) {
            sb.append(" ").append('\n');
            sb.append("order by ").append(dbOrderBy);
        }
        if (useSqlLimiter) {
            final SqlLimitRequest r = new OrmQueryLimitRequest(sb.toString(), dbOrderBy, query);
            return this.sqlLimiter.limit(r);
        }
        return new SqlLimitResponse(sb.toString(), false);
    }
    
    private boolean isEmpty(final String s) {
        return s == null || s.length() == 0;
    }
}
