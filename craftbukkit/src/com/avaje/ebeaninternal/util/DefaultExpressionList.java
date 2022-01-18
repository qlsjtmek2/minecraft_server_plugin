// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.util;

import com.avaje.ebean.Junction;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebean.Expression;
import com.avaje.ebean.QueryListener;
import java.util.Map;
import java.util.Set;
import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.QueryResultVisitor;
import com.avaje.ebean.PagingList;
import com.avaje.ebean.FutureList;
import com.avaje.ebean.FutureRowCount;
import com.avaje.ebean.FutureIds;
import com.avaje.ebean.OrderBy;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.Collection;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import java.util.List;
import com.avaje.ebean.ExpressionFactory;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.api.SpiExpression;
import java.util.ArrayList;
import com.avaje.ebeaninternal.api.SpiExpressionList;

public class DefaultExpressionList<T> implements SpiExpressionList<T>
{
    private static final long serialVersionUID = -6992345500247035947L;
    private final ArrayList<SpiExpression> list;
    private final Query<T> query;
    private final ExpressionList<T> parentExprList;
    private transient ExpressionFactory expr;
    private final String exprLang;
    private final String listAndStart;
    private final String listAndEnd;
    private final String listAndJoin;
    
    public DefaultExpressionList(final Query<T> query, final ExpressionList<T> parentExprList) {
        this(query, query.getExpressionFactory(), parentExprList);
    }
    
    public DefaultExpressionList(final Query<T> query, final ExpressionFactory expr, final ExpressionList<T> parentExprList) {
        this.list = new ArrayList<SpiExpression>();
        this.query = query;
        this.expr = expr;
        this.exprLang = expr.getLang();
        this.parentExprList = parentExprList;
        if ("ldap".equals(this.exprLang)) {
            this.listAndStart = "(&";
            this.listAndEnd = ")";
            this.listAndJoin = "";
        }
        else {
            this.listAndStart = "";
            this.listAndEnd = "";
            this.listAndJoin = " and ";
        }
    }
    
    public void trimPath(final int prefixTrim) {
        throw new RuntimeException("Only allowed on FilterExpressionList");
    }
    
    public List<SpiExpression> internalList() {
        return this.list;
    }
    
    public boolean isLuceneResolvable(final LuceneResolvableRequest req) {
        for (int i = 0; i < this.list.size(); ++i) {
            if (!this.list.get(i).isLuceneResolvable(req)) {
                return false;
            }
        }
        return true;
    }
    
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request, final SpiLuceneExpr.ExprOccur occur) {
        final LuceneQueryList queryList = new LuceneQueryList(occur);
        for (int i = 0; i < this.list.size(); ++i) {
            final SpiLuceneExpr query = this.list.get(i).createLuceneExpr(request);
            queryList.add(query);
        }
        return queryList;
    }
    
    public void setExpressionFactory(final ExpressionFactory expr) {
        this.expr = expr;
    }
    
    public DefaultExpressionList<T> copy(final Query<T> query) {
        final DefaultExpressionList<T> copy = new DefaultExpressionList<T>(query, this.expr, null);
        copy.list.addAll(this.list);
        return copy;
    }
    
    public void containsMany(final BeanDescriptor<?> desc, final ManyWhereJoins whereManyJoins) {
        for (int i = 0; i < this.list.size(); ++i) {
            this.list.get(i).containsMany(desc, whereManyJoins);
        }
    }
    
    public ExpressionList<T> endJunction() {
        return (this.parentExprList == null) ? this : this.parentExprList;
    }
    
    public Query<T> query() {
        return this.query;
    }
    
    public ExpressionList<T> where() {
        return this.query.where();
    }
    
    public OrderBy<T> order() {
        return this.query.order();
    }
    
    public OrderBy<T> orderBy() {
        return this.query.order();
    }
    
    public Query<T> order(final String orderByClause) {
        return this.query.order(orderByClause);
    }
    
    public Query<T> orderBy(final String orderBy) {
        return this.query.order(orderBy);
    }
    
    public Query<T> setOrderBy(final String orderBy) {
        return this.query.order(orderBy);
    }
    
    public FutureIds<T> findFutureIds() {
        return this.query.findFutureIds();
    }
    
    public FutureRowCount<T> findFutureRowCount() {
        return this.query.findFutureRowCount();
    }
    
    public FutureList<T> findFutureList() {
        return this.query.findFutureList();
    }
    
    public PagingList<T> findPagingList(final int pageSize) {
        return this.query.findPagingList(pageSize);
    }
    
    public int findRowCount() {
        return this.query.findRowCount();
    }
    
    public List<Object> findIds() {
        return this.query.findIds();
    }
    
    public void findVisit(final QueryResultVisitor<T> visitor) {
        this.query.findVisit(visitor);
    }
    
    public QueryIterator<T> findIterate() {
        return this.query.findIterate();
    }
    
    public List<T> findList() {
        return this.query.findList();
    }
    
    public Set<T> findSet() {
        return this.query.findSet();
    }
    
    public Map<?, T> findMap() {
        return this.query.findMap();
    }
    
    public <K> Map<K, T> findMap(final String keyProperty, final Class<K> keyType) {
        return this.query.findMap(keyProperty, keyType);
    }
    
    public T findUnique() {
        return this.query.findUnique();
    }
    
    public ExpressionList<T> filterMany(final String prop) {
        return this.query.filterMany(prop);
    }
    
    public Query<T> select(final String fetchProperties) {
        return this.query.select(fetchProperties);
    }
    
    public Query<T> join(final String assocProperties) {
        return this.query.fetch(assocProperties);
    }
    
    public Query<T> join(final String assocProperty, final String assocProperties) {
        return this.query.fetch(assocProperty, assocProperties);
    }
    
    public Query<T> setFirstRow(final int firstRow) {
        return this.query.setFirstRow(firstRow);
    }
    
    public Query<T> setMaxRows(final int maxRows) {
        return this.query.setMaxRows(maxRows);
    }
    
    public Query<T> setBackgroundFetchAfter(final int backgroundFetchAfter) {
        return this.query.setBackgroundFetchAfter(backgroundFetchAfter);
    }
    
    public Query<T> setMapKey(final String mapKey) {
        return this.query.setMapKey(mapKey);
    }
    
    public Query<T> setListener(final QueryListener<T> queryListener) {
        return this.query.setListener(queryListener);
    }
    
    public Query<T> setUseCache(final boolean useCache) {
        return this.query.setUseCache(useCache);
    }
    
    public ExpressionList<T> having() {
        return this.query.having();
    }
    
    public ExpressionList<T> add(final Expression expr) {
        this.list.add((SpiExpression)expr);
        return this;
    }
    
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
    
    public String buildSql(final SpiExpressionRequest request) {
        request.append(this.listAndStart);
        for (int i = 0, size = this.list.size(); i < size; ++i) {
            final SpiExpression expression = this.list.get(i);
            if (i > 0) {
                request.append(this.listAndJoin);
            }
            expression.addSql(request);
        }
        request.append(this.listAndEnd);
        return request.getSql();
    }
    
    public ArrayList<Object> buildBindValues(final SpiExpressionRequest request) {
        for (int i = 0, size = this.list.size(); i < size; ++i) {
            final SpiExpression expression = this.list.get(i);
            expression.addBindValues(request);
        }
        return request.getBindValues();
    }
    
    public int queryAutoFetchHash() {
        int hash = DefaultExpressionList.class.getName().hashCode();
        for (int i = 0, size = this.list.size(); i < size; ++i) {
            final SpiExpression expression = this.list.get(i);
            hash = hash * 31 + expression.queryAutoFetchHash();
        }
        return hash;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        int hash = DefaultExpressionList.class.getName().hashCode();
        for (int i = 0, size = this.list.size(); i < size; ++i) {
            final SpiExpression expression = this.list.get(i);
            hash = hash * 31 + expression.queryPlanHash(request);
        }
        return hash;
    }
    
    public int queryBindHash() {
        int hash = DefaultExpressionList.class.getName().hashCode();
        for (int i = 0, size = this.list.size(); i < size; ++i) {
            final SpiExpression expression = this.list.get(i);
            hash = hash * 31 + expression.queryBindHash();
        }
        return hash;
    }
    
    public ExpressionList<T> eq(final String propertyName, final Object value) {
        this.add(this.expr.eq(propertyName, value));
        return this;
    }
    
    public ExpressionList<T> ieq(final String propertyName, final String value) {
        this.add(this.expr.ieq(propertyName, value));
        return this;
    }
    
    public ExpressionList<T> ne(final String propertyName, final Object value) {
        this.add(this.expr.ne(propertyName, value));
        return this;
    }
    
    public ExpressionList<T> allEq(final Map<String, Object> propertyMap) {
        this.add(this.expr.allEq(propertyMap));
        return this;
    }
    
    public ExpressionList<T> and(final Expression expOne, final Expression expTwo) {
        this.add(this.expr.and(expOne, expTwo));
        return this;
    }
    
    public ExpressionList<T> between(final String propertyName, final Object value1, final Object value2) {
        this.add(this.expr.between(propertyName, value1, value2));
        return this;
    }
    
    public ExpressionList<T> betweenProperties(final String lowProperty, final String highProperty, final Object value) {
        this.add(this.expr.betweenProperties(lowProperty, highProperty, value));
        return this;
    }
    
    public Junction<T> conjunction() {
        final Junction<T> conjunction = this.expr.conjunction(this.query, this);
        this.add(conjunction);
        return conjunction;
    }
    
    public ExpressionList<T> contains(final String propertyName, final String value) {
        this.add(this.expr.contains(propertyName, value));
        return this;
    }
    
    public ExpressionList<T> lucene(final String propertyName, final String value) {
        this.add(this.expr.lucene(propertyName, value));
        return this;
    }
    
    public Junction<T> disjunction() {
        final Junction<T> disjunction = this.expr.disjunction(this.query, this);
        this.add(disjunction);
        return disjunction;
    }
    
    public ExpressionList<T> endsWith(final String propertyName, final String value) {
        this.add(this.expr.endsWith(propertyName, value));
        return this;
    }
    
    public ExpressionList<T> ge(final String propertyName, final Object value) {
        this.add(this.expr.ge(propertyName, value));
        return this;
    }
    
    public ExpressionList<T> gt(final String propertyName, final Object value) {
        this.add(this.expr.gt(propertyName, value));
        return this;
    }
    
    public ExpressionList<T> icontains(final String propertyName, final String value) {
        this.add(this.expr.icontains(propertyName, value));
        return this;
    }
    
    public ExpressionList<T> idIn(final List<?> idList) {
        this.add(this.expr.idIn(idList));
        return this;
    }
    
    public ExpressionList<T> idEq(final Object value) {
        this.add(this.expr.idEq(value));
        return this;
    }
    
    public ExpressionList<T> iendsWith(final String propertyName, final String value) {
        this.add(this.expr.iendsWith(propertyName, value));
        return this;
    }
    
    public ExpressionList<T> ilike(final String propertyName, final String value) {
        this.add(this.expr.ilike(propertyName, value));
        return this;
    }
    
    public ExpressionList<T> in(final String propertyName, final Query<?> subQuery) {
        this.add(this.expr.in(propertyName, subQuery));
        return this;
    }
    
    public ExpressionList<T> in(final String propertyName, final Collection<?> values) {
        this.add(this.expr.in(propertyName, values));
        return this;
    }
    
    public ExpressionList<T> in(final String propertyName, final Object... values) {
        this.add(this.expr.in(propertyName, values));
        return this;
    }
    
    public ExpressionList<T> isNotNull(final String propertyName) {
        this.add(this.expr.isNotNull(propertyName));
        return this;
    }
    
    public ExpressionList<T> isNull(final String propertyName) {
        this.add(this.expr.isNull(propertyName));
        return this;
    }
    
    public ExpressionList<T> istartsWith(final String propertyName, final String value) {
        this.add(this.expr.istartsWith(propertyName, value));
        return this;
    }
    
    public ExpressionList<T> le(final String propertyName, final Object value) {
        this.add(this.expr.le(propertyName, value));
        return this;
    }
    
    public ExpressionList<T> exampleLike(final Object example) {
        this.add(this.expr.exampleLike(example));
        return this;
    }
    
    public ExpressionList<T> iexampleLike(final Object example) {
        this.add(this.expr.iexampleLike(example));
        return this;
    }
    
    public ExpressionList<T> like(final String propertyName, final String value) {
        this.add(this.expr.like(propertyName, value));
        return this;
    }
    
    public ExpressionList<T> lt(final String propertyName, final Object value) {
        this.add(this.expr.lt(propertyName, value));
        return this;
    }
    
    public ExpressionList<T> not(final Expression exp) {
        this.add(this.expr.not(exp));
        return this;
    }
    
    public ExpressionList<T> or(final Expression expOne, final Expression expTwo) {
        this.add(this.expr.or(expOne, expTwo));
        return this;
    }
    
    public ExpressionList<T> raw(final String raw, final Object value) {
        this.add(this.expr.raw(raw, value));
        return this;
    }
    
    public ExpressionList<T> raw(final String raw, final Object[] values) {
        this.add(this.expr.raw(raw, values));
        return this;
    }
    
    public ExpressionList<T> raw(final String raw) {
        this.add(this.expr.raw(raw));
        return this;
    }
    
    public ExpressionList<T> startsWith(final String propertyName, final String value) {
        this.add(this.expr.startsWith(propertyName, value));
        return this;
    }
}
