// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.logging.Level;
import com.avaje.ebeaninternal.server.querydefn.OrmQueryProperties;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.api.SpiExpressionList;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.core.SpiOrmQueryRequest;
import com.avaje.ebeaninternal.util.DefaultExpressionRequest;
import com.avaje.ebeaninternal.server.util.BindParamsParser;
import com.avaje.ebeaninternal.server.deploy.DeployParser;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.type.DataBind;
import java.util.Set;
import java.util.ArrayList;
import com.avaje.ebeaninternal.api.BindParams;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;
import com.avaje.ebeaninternal.server.persist.Binder;
import java.util.logging.Logger;

public class CQueryPredicates
{
    private static final Logger logger;
    private final Binder binder;
    private final OrmQueryRequest<?> request;
    private final SpiQuery<?> query;
    private final Object idValue;
    private boolean rawSql;
    private final BindParams bindParams;
    private BindParams.OrderedList havingNamedParams;
    private ArrayList<Object> filterManyExprBindValues;
    private String filterManyExprSql;
    private ArrayList<Object> whereExprBindValues;
    private String whereExprSql;
    private String whereRawSql;
    private ArrayList<Object> havingExprBindValues;
    private String havingExprSql;
    private String havingRawSql;
    private String dbHaving;
    private String dbWhere;
    private String dbFilterMany;
    private String logicalOrderBy;
    private String dbOrderBy;
    private Set<String> predicateIncludes;
    
    public CQueryPredicates(final Binder binder, final OrmQueryRequest<?> request) {
        this.binder = binder;
        this.request = request;
        this.query = request.getQuery();
        this.bindParams = this.query.getBindParams();
        this.idValue = this.query.getId();
    }
    
    public boolean isLuceneResolvable() {
        final CQueryPredicatesLuceneResolve luceneResolve = new CQueryPredicatesLuceneResolve(this.request);
        return luceneResolve.isLuceneResolvable();
    }
    
    public String bind(final DataBind dataBind) throws SQLException {
        final StringBuilder bindLog = new StringBuilder();
        if (this.idValue != null) {
            this.request.getBeanDescriptor().bindId(dataBind, this.idValue);
            bindLog.append(this.idValue);
        }
        if (this.bindParams != null) {
            this.binder.bind(this.bindParams, dataBind, bindLog);
        }
        if (this.whereExprBindValues != null) {
            for (int i = 0; i < this.whereExprBindValues.size(); ++i) {
                final Object bindValue = this.whereExprBindValues.get(i);
                this.binder.bindObject(dataBind, bindValue);
                if (i > 0 || this.idValue != null) {
                    bindLog.append(", ");
                }
                bindLog.append(bindValue);
            }
        }
        if (this.filterManyExprBindValues != null) {
            for (int i = 0; i < this.filterManyExprBindValues.size(); ++i) {
                final Object bindValue = this.filterManyExprBindValues.get(i);
                this.binder.bindObject(dataBind, bindValue);
                if (i > 0 || this.idValue != null) {
                    bindLog.append(", ");
                }
                bindLog.append(bindValue);
            }
        }
        if (this.havingNamedParams != null) {
            bindLog.append(" havingNamed ");
            this.binder.bind(this.havingNamedParams.list(), dataBind, bindLog);
        }
        if (this.havingExprBindValues != null) {
            bindLog.append(" having ");
            for (int i = 0; i < this.havingExprBindValues.size(); ++i) {
                final Object bindValue = this.havingExprBindValues.get(i);
                this.binder.bindObject(dataBind, bindValue);
                if (i > 0) {
                    bindLog.append(", ");
                }
                bindLog.append(bindValue);
            }
        }
        return bindLog.toString();
    }
    
    private void buildBindHavingRawSql(final boolean buildSql, final boolean parseRaw, final DeployParser deployParser) {
        if (buildSql || this.bindParams != null) {
            this.havingRawSql = this.query.getAdditionalHaving();
            if (parseRaw) {
                this.havingRawSql = deployParser.parse(this.havingRawSql);
            }
            if (this.havingRawSql != null && this.bindParams != null) {
                this.havingNamedParams = BindParamsParser.parseNamedParams(this.bindParams, this.havingRawSql);
                this.havingRawSql = this.havingNamedParams.getPreparedSql();
            }
        }
    }
    
    private void buildBindWhereRawSql(final boolean buildSql, final boolean parseRaw, final DeployParser parser) {
        if (buildSql || this.bindParams != null) {
            this.whereRawSql = this.buildWhereRawSql();
            final boolean hasRaw = !"".equals(this.whereRawSql);
            if (hasRaw && parseRaw) {
                parser.setEncrypted(true);
                this.whereRawSql = parser.parse(this.whereRawSql);
                parser.setEncrypted(false);
            }
            if (this.bindParams != null) {
                if (hasRaw) {
                    this.whereRawSql = BindParamsParser.parse(this.bindParams, this.whereRawSql, this.request.getBeanDescriptor());
                }
                else if (this.query.isRawSql() && !buildSql) {
                    final String s = this.query.getRawSql().getSql().getPreWhere();
                    if (this.bindParams.requiresNamedParamsPrepare()) {
                        BindParamsParser.parse(this.bindParams, s);
                    }
                }
            }
        }
    }
    
    private String buildWhereRawSql() {
        String whereRaw = this.query.getRawWhereClause();
        if (whereRaw == null) {
            whereRaw = "";
        }
        final String additionalWhere = this.query.getAdditionalWhere();
        if (additionalWhere != null) {
            whereRaw += additionalWhere;
        }
        return whereRaw;
    }
    
    public void prepare(final boolean buildSql) {
        final DeployParser deployParser = this.request.createDeployParser();
        this.prepare(buildSql, true, deployParser);
    }
    
    public void prepareRawSql(final DeployParser deployParser) {
        this.prepare(true, false, deployParser);
    }
    
    private void prepare(final boolean buildSql, final boolean parseRaw, final DeployParser deployParser) {
        this.buildBindWhereRawSql(buildSql, parseRaw, deployParser);
        this.buildBindHavingRawSql(buildSql, parseRaw, deployParser);
        final SpiExpressionList<?> whereExp = this.query.getWhereExpressions();
        if (whereExp != null) {
            final DefaultExpressionRequest whereReq = new DefaultExpressionRequest(this.request, deployParser);
            this.whereExprBindValues = whereExp.buildBindValues(whereReq);
            if (buildSql) {
                this.whereExprSql = whereExp.buildSql(whereReq);
            }
        }
        final BeanPropertyAssocMany<?> manyProperty = this.request.getManyProperty();
        if (manyProperty != null) {
            final OrmQueryProperties chunk = this.query.getDetail().getChunk(manyProperty.getName(), false);
            final SpiExpressionList<?> filterMany = chunk.getFilterMany();
            if (filterMany != null) {
                final DefaultExpressionRequest filterReq = new DefaultExpressionRequest(this.request, deployParser);
                this.filterManyExprBindValues = filterMany.buildBindValues(filterReq);
                if (buildSql) {
                    this.filterManyExprSql = filterMany.buildSql(filterReq);
                }
            }
        }
        final SpiExpressionList<?> havingExpr = this.query.getHavingExpressions();
        if (havingExpr != null) {
            final DefaultExpressionRequest havingReq = new DefaultExpressionRequest(this.request, deployParser);
            this.havingExprBindValues = havingExpr.buildBindValues(havingReq);
            if (buildSql) {
                this.havingExprSql = havingExpr.buildSql(havingReq);
            }
        }
        if (buildSql) {
            this.parsePropertiesToDbColumns(deployParser);
        }
    }
    
    private void parsePropertiesToDbColumns(final DeployParser deployParser) {
        this.dbWhere = this.deriveWhere(deployParser);
        this.dbFilterMany = this.deriveFilterMany(deployParser);
        this.dbHaving = this.deriveHaving(deployParser);
        this.logicalOrderBy = this.deriveOrderByWithMany(this.request.getManyProperty());
        if (this.logicalOrderBy != null) {
            this.dbOrderBy = deployParser.parse(this.logicalOrderBy);
        }
        this.predicateIncludes = deployParser.getIncludes();
    }
    
    private String deriveFilterMany(final DeployParser deployParser) {
        if (this.isEmpty(this.filterManyExprSql)) {
            return null;
        }
        return deployParser.parse(this.filterManyExprSql);
    }
    
    private String deriveWhere(final DeployParser deployParser) {
        return this.parse(this.whereRawSql, this.whereExprSql, deployParser);
    }
    
    public void parseTableAlias(final SqlTreeAlias alias) {
        if (this.dbWhere != null) {
            this.dbWhere = alias.parseWhere(this.dbWhere);
        }
        if (this.dbFilterMany != null) {
            this.dbFilterMany = alias.parse(this.dbFilterMany);
        }
        if (this.dbHaving != null) {
            this.dbHaving = alias.parseWhere(this.dbHaving);
        }
        if (this.dbOrderBy != null) {
            this.dbOrderBy = alias.parse(this.dbOrderBy);
        }
    }
    
    private boolean isEmpty(final String s) {
        return s == null || s.length() == 0;
    }
    
    private String parse(final String raw, final String expr, final DeployParser deployParser) {
        final StringBuilder sb = new StringBuilder();
        if (!this.isEmpty(raw)) {
            sb.append(raw);
        }
        if (!this.isEmpty(expr)) {
            if (sb.length() > 0) {
                sb.append(" and ");
            }
            sb.append(deployParser.parse(expr));
        }
        return sb.toString();
    }
    
    private String deriveHaving(final DeployParser deployParser) {
        return this.parse(this.havingRawSql, this.havingExprSql, deployParser);
    }
    
    private String parseOrderBy() {
        return CQueryOrderBy.parse(this.request.getBeanDescriptor(), this.query);
    }
    
    private String deriveOrderByWithMany(final BeanPropertyAssocMany<?> manyProp) {
        if (manyProp == null) {
            return this.parseOrderBy();
        }
        String orderBy = this.parseOrderBy();
        final BeanDescriptor<?> desc = this.request.getBeanDescriptor();
        final String orderById = desc.getDefaultOrderBy();
        if (orderBy == null) {
            orderBy = orderById;
        }
        final String manyOrderBy = manyProp.getFetchOrderBy();
        if (manyOrderBy != null) {
            orderBy = orderBy + " , " + CQueryBuilder.prefixOrderByFields(manyProp.getName(), manyOrderBy);
        }
        if (this.request.isFindById()) {
            return orderBy;
        }
        if (orderBy.startsWith(orderById)) {
            return orderBy;
        }
        final int manyPos = orderBy.indexOf(manyProp.getName());
        final int idPos = orderBy.indexOf(" " + orderById);
        if (manyPos == -1) {
            return orderBy;
        }
        if (idPos <= -1 || idPos >= manyPos) {
            if (idPos > manyPos) {
                String msg = "A Query on [" + desc + "] includes a join to a 'many' association [" + manyProp.getName();
                msg = msg + "] with an incorrect orderBy [" + orderBy + "]. The id property [" + orderById + "]";
                msg = msg + " must come before the many property [" + manyProp.getName() + "] in the orderBy.";
                msg += " Ebean has automatically modified the orderBy clause to do this.";
                CQueryPredicates.logger.log(Level.WARNING, msg);
            }
            orderBy = orderBy.substring(0, manyPos) + orderById + ", " + orderBy.substring(manyPos);
        }
        return orderBy;
    }
    
    public ArrayList<Object> getWhereExprBindValues() {
        return this.whereExprBindValues;
    }
    
    public String getDbHaving() {
        return this.dbHaving;
    }
    
    public String getDbWhere() {
        return this.dbWhere;
    }
    
    public String getDbFilterMany() {
        return this.dbFilterMany;
    }
    
    public String getDbOrderBy() {
        return this.dbOrderBy;
    }
    
    public Set<String> getPredicateIncludes() {
        return this.predicateIncludes;
    }
    
    public String getWhereRawSql() {
        return this.whereRawSql;
    }
    
    public String getWhereExpressionSql() {
        return this.whereExprSql;
    }
    
    public String getHavingRawSql() {
        return this.havingRawSql;
    }
    
    public String getHavingExpressionSql() {
        return this.havingExprSql;
    }
    
    public String getLogWhereSql() {
        if (this.rawSql) {
            return "";
        }
        if (this.dbWhere == null && this.dbFilterMany == null) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        if (this.dbWhere != null) {
            sb.append(this.dbWhere);
        }
        if (this.dbFilterMany != null) {
            if (sb.length() > 0) {
                sb.append(" and ");
            }
            sb.append(this.dbFilterMany);
        }
        String logPred = sb.toString();
        if (logPred.length() > 400) {
            logPred = logPred.substring(0, 400) + " ...";
        }
        return logPred;
    }
    
    static {
        logger = Logger.getLogger(CQueryPredicates.class.getName());
    }
}
