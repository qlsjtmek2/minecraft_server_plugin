// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
import com.avaje.ebeaninternal.server.deploy.DeployParser;
import com.avaje.ebeaninternal.server.deploy.DRawSqlSelect;
import com.avaje.ebeaninternal.server.deploy.DeployNamedQuery;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import javax.persistence.PersistenceException;
import java.util.logging.Level;
import com.avaje.ebean.config.dbplatform.SqlLimitRequest;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryLimitRequest;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebean.config.dbplatform.SqlLimiter;
import com.avaje.ebeaninternal.server.persist.Binder;
import java.util.logging.Logger;

public class RawSqlSelectClauseBuilder
{
    private static final Logger logger;
    private final Binder binder;
    private final SqlLimiter dbQueryLimiter;
    
    public RawSqlSelectClauseBuilder(final DatabasePlatform dbPlatform, final Binder binder) {
        this.binder = binder;
        this.dbQueryLimiter = dbPlatform.getSqlLimiter();
    }
    
    public <T> CQuery<T> build(final OrmQueryRequest<T> request) throws PersistenceException {
        final SpiQuery<T> query = request.getQuery();
        final BeanDescriptor<T> desc = request.getBeanDescriptor();
        final DeployNamedQuery namedQuery = desc.getNamedQuery(query.getName());
        final DRawSqlSelect sqlSelect = namedQuery.getSqlSelect();
        final DeployParser parser = sqlSelect.createDeployPropertyParser();
        final CQueryPredicates predicates = new CQueryPredicates(this.binder, request);
        predicates.prepareRawSql(parser);
        final SqlTreeAlias alias = new SqlTreeAlias(sqlSelect.getTableAlias());
        predicates.parseTableAlias(alias);
        String sql = null;
        try {
            boolean includeRowNumColumn = false;
            final String orderBy = sqlSelect.getOrderBy(predicates);
            sql = sqlSelect.buildSql(orderBy, predicates, request);
            if (query.hasMaxRowsOrFirstRow() && this.dbQueryLimiter != null) {
                final SqlLimitResponse limitSql = this.dbQueryLimiter.limit(new OrmQueryLimitRequest(sql, orderBy, query));
                includeRowNumColumn = limitSql.isIncludesRowNumberColumn();
                sql = limitSql.getSql();
            }
            else {
                sql = "select " + sql;
            }
            final SqlTree sqlTree = sqlSelect.getSqlTree();
            final CQueryPlan queryPlan = new CQueryPlan(sql, sqlTree, true, includeRowNumColumn, "");
            final CQuery<T> compiledQuery = new CQuery<T>(request, predicates, queryPlan);
            return compiledQuery;
        }
        catch (Exception e) {
            final String msg = "Error with " + desc.getFullName() + " query:\r" + sql;
            RawSqlSelectClauseBuilder.logger.log(Level.SEVERE, msg);
            throw new PersistenceException(e);
        }
    }
    
    static {
        logger = Logger.getLogger(RawSqlSelectClauseBuilder.class.getName());
    }
}
