// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.api.BindParams;
import com.avaje.ebean.config.dbplatform.SqlLimitRequest;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryLimitRequest;
import com.avaje.ebeaninternal.server.util.BindParamsParser;
import com.avaje.ebean.config.dbplatform.SqlLimitResponse;
import com.avaje.ebean.RawSql;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebean.config.dbplatform.SqlLimiter;

public class CQueryBuilderRawSql implements Constants
{
    private final SqlLimiter sqlLimiter;
    
    CQueryBuilderRawSql(final SqlLimiter sqlLimiter) {
        this.sqlLimiter = sqlLimiter;
    }
    
    public SqlLimitResponse buildSql(final OrmQueryRequest<?> request, final CQueryPredicates predicates, final RawSql.Sql rsql) {
        if (!rsql.isParsed()) {
            String sql = rsql.getUnparsedSql();
            final BindParams bindParams = request.getQuery().getBindParams();
            if (bindParams != null && bindParams.requiresNamedParamsPrepare()) {
                sql = BindParamsParser.parse(bindParams, sql);
            }
            return new SqlLimitResponse(sql, false);
        }
        final String orderBy = this.getOrderBy(predicates, rsql);
        String sql2 = this.buildMainQuery(orderBy, request, predicates, rsql);
        final SpiQuery<?> query = request.getQuery();
        if (query.hasMaxRowsOrFirstRow() && this.sqlLimiter != null) {
            return this.sqlLimiter.limit(new OrmQueryLimitRequest(sql2, orderBy, query));
        }
        sql2 = "select " + sql2;
        return new SqlLimitResponse(sql2, false);
    }
    
    private String buildMainQuery(final String orderBy, final OrmQueryRequest<?> request, final CQueryPredicates predicates, final RawSql.Sql sql) {
        final StringBuilder sb = new StringBuilder();
        sb.append(sql.getPreFrom());
        sb.append(" ");
        sb.append('\n');
        String s = sql.getPreWhere();
        final BindParams bindParams = request.getQuery().getBindParams();
        if (bindParams != null && bindParams.requiresNamedParamsPrepare()) {
            s = BindParamsParser.parse(bindParams, s);
        }
        sb.append(s);
        sb.append(" ");
        String dynamicWhere = null;
        if (request.getQuery().getId() != null) {
            final BeanDescriptor<?> descriptor = request.getBeanDescriptor();
            dynamicWhere = descriptor.getIdBinderIdSql();
        }
        final String dbWhere = predicates.getDbWhere();
        if (!this.isEmpty(dbWhere)) {
            if (dynamicWhere == null) {
                dynamicWhere = dbWhere;
            }
            else {
                dynamicWhere = dynamicWhere + " and " + dbWhere;
            }
        }
        if (!this.isEmpty(dynamicWhere)) {
            sb.append('\n');
            if (sql.isAndWhereExpr()) {
                sb.append("and ");
            }
            else {
                sb.append("where ");
            }
            sb.append(dynamicWhere);
            sb.append(" ");
        }
        final String preHaving = sql.getPreHaving();
        if (!this.isEmpty(preHaving)) {
            sb.append('\n');
            sb.append(preHaving);
            sb.append(" ");
        }
        final String dbHaving = predicates.getDbHaving();
        if (!this.isEmpty(dbHaving)) {
            sb.append(" ");
            sb.append('\n');
            if (sql.isAndHavingExpr()) {
                sb.append("and ");
            }
            else {
                sb.append("having ");
            }
            sb.append(dbHaving);
            sb.append(" ");
        }
        if (!this.isEmpty(orderBy)) {
            sb.append('\n');
            sb.append(" order by ").append(orderBy);
        }
        return sb.toString().trim();
    }
    
    private boolean isEmpty(final String s) {
        return s == null || s.length() == 0;
    }
    
    private String getOrderBy(final CQueryPredicates predicates, final RawSql.Sql sql) {
        final String orderBy = predicates.getDbOrderBy();
        if (orderBy != null) {
            return orderBy;
        }
        return sql.getOrderBy();
    }
}
