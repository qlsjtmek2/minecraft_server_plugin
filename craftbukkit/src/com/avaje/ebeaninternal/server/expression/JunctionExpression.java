// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.expression;

import com.avaje.ebean.QueryListener;
import com.avaje.ebean.OrderBy;
import java.util.Collection;
import java.util.Set;
import com.avaje.ebean.PagingList;
import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.QueryResultVisitor;
import com.avaje.ebean.FutureRowCount;
import com.avaje.ebean.FutureList;
import com.avaje.ebean.FutureIds;
import java.util.Map;
import com.avaje.ebean.event.BeanQueryRequest;
import com.avaje.ebean.Expression;
import com.avaje.ebeaninternal.api.ManyWhereJoins;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiLuceneExpr;
import com.avaje.ebeaninternal.api.SpiExpressionRequest;
import java.util.List;
import com.avaje.ebeaninternal.server.query.LuceneResolvableRequest;
import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.util.DefaultExpressionList;
import com.avaje.ebean.ExpressionList;
import com.avaje.ebeaninternal.api.SpiExpression;
import com.avaje.ebean.Junction;

abstract class JunctionExpression<T> implements Junction<T>, SpiExpression, ExpressionList<T>
{
    private static final long serialVersionUID = -7422204102750462676L;
    private static final String OR = " or ";
    private static final String AND = " and ";
    private final DefaultExpressionList<T> exprList;
    private final String joinType;
    
    JunctionExpression(final String joinType, final Query<T> query, final ExpressionList<T> parent) {
        this.joinType = joinType;
        this.exprList = new DefaultExpressionList<T>(query, parent);
    }
    
    public boolean isLuceneResolvable(final LuceneResolvableRequest req) {
        final List<SpiExpression> list = this.exprList.internalList();
        for (int i = 0; i < list.size(); ++i) {
            if (!list.get(i).isLuceneResolvable(req)) {
                return false;
            }
        }
        return true;
    }
    
    public SpiLuceneExpr createLuceneExpr(final SpiExpressionRequest request) {
        final boolean disjunction = " or ".equals(this.joinType);
        return new JunctionExpressionLucene().createLuceneExpr(request, this.exprList.internalList(), disjunction);
    }
    
    public void containsMany(final BeanDescriptor<?> desc, final ManyWhereJoins manyWhereJoin) {
        final List<SpiExpression> list = this.exprList.internalList();
        for (int i = 0; i < list.size(); ++i) {
            list.get(i).containsMany(desc, manyWhereJoin);
        }
    }
    
    public Junction<T> add(final Expression item) {
        final SpiExpression i = (SpiExpression)item;
        this.exprList.add(i);
        return this;
    }
    
    public void addBindValues(final SpiExpressionRequest request) {
        final List<SpiExpression> list = this.exprList.internalList();
        for (int i = 0; i < list.size(); ++i) {
            final SpiExpression item = list.get(i);
            item.addBindValues(request);
        }
    }
    
    public void addSql(final SpiExpressionRequest request) {
        final List<SpiExpression> list = this.exprList.internalList();
        if (!list.isEmpty()) {
            request.append("(");
            for (int i = 0; i < list.size(); ++i) {
                final SpiExpression item = list.get(i);
                if (i > 0) {
                    request.append(this.joinType);
                }
                item.addSql(request);
            }
            request.append(") ");
        }
    }
    
    public int queryAutoFetchHash() {
        int hc = JunctionExpression.class.getName().hashCode();
        hc = hc * 31 + this.joinType.hashCode();
        final List<SpiExpression> list = this.exprList.internalList();
        for (int i = 0; i < list.size(); ++i) {
            hc = hc * 31 + list.get(i).queryAutoFetchHash();
        }
        return hc;
    }
    
    public int queryPlanHash(final BeanQueryRequest<?> request) {
        int hc = JunctionExpression.class.getName().hashCode();
        hc = hc * 31 + this.joinType.hashCode();
        final List<SpiExpression> list = this.exprList.internalList();
        for (int i = 0; i < list.size(); ++i) {
            hc = hc * 31 + list.get(i).queryPlanHash(request);
        }
        return hc;
    }
    
    public int queryBindHash() {
        int hc = JunctionExpression.class.getName().hashCode();
        final List<SpiExpression> list = this.exprList.internalList();
        for (int i = 0; i < list.size(); ++i) {
            hc = hc * 31 + list.get(i).queryBindHash();
        }
        return hc;
    }
    
    public ExpressionList<T> endJunction() {
        return this.exprList.endJunction();
    }
    
    public ExpressionList<T> allEq(final Map<String, Object> propertyMap) {
        return this.exprList.allEq(propertyMap);
    }
    
    public ExpressionList<T> and(final Expression expOne, final Expression expTwo) {
        return this.exprList.and(expOne, expTwo);
    }
    
    public ExpressionList<T> between(final String propertyName, final Object value1, final Object value2) {
        return this.exprList.between(propertyName, value1, value2);
    }
    
    public ExpressionList<T> betweenProperties(final String lowProperty, final String highProperty, final Object value) {
        return this.exprList.betweenProperties(lowProperty, highProperty, value);
    }
    
    public Junction<T> conjunction() {
        return this.exprList.conjunction();
    }
    
    public ExpressionList<T> contains(final String propertyName, final String value) {
        return this.exprList.contains(propertyName, value);
    }
    
    public Junction<T> disjunction() {
        return this.exprList.disjunction();
    }
    
    public ExpressionList<T> endsWith(final String propertyName, final String value) {
        return this.exprList.endsWith(propertyName, value);
    }
    
    public ExpressionList<T> eq(final String propertyName, final Object value) {
        return this.exprList.eq(propertyName, value);
    }
    
    public ExpressionList<T> exampleLike(final Object example) {
        return this.exprList.exampleLike(example);
    }
    
    public ExpressionList<T> filterMany(final String prop) {
        throw new RuntimeException("filterMany not allowed on Junction expression list");
    }
    
    public FutureIds<T> findFutureIds() {
        return this.exprList.findFutureIds();
    }
    
    public FutureList<T> findFutureList() {
        return this.exprList.findFutureList();
    }
    
    public FutureRowCount<T> findFutureRowCount() {
        return this.exprList.findFutureRowCount();
    }
    
    public List<Object> findIds() {
        return this.exprList.findIds();
    }
    
    public void findVisit(final QueryResultVisitor<T> visitor) {
        this.exprList.findVisit(visitor);
    }
    
    public QueryIterator<T> findIterate() {
        return this.exprList.findIterate();
    }
    
    public List<T> findList() {
        return this.exprList.findList();
    }
    
    public Map<?, T> findMap() {
        return this.exprList.findMap();
    }
    
    public <K> Map<K, T> findMap(final String keyProperty, final Class<K> keyType) {
        return this.exprList.findMap(keyProperty, keyType);
    }
    
    public PagingList<T> findPagingList(final int pageSize) {
        return this.exprList.findPagingList(pageSize);
    }
    
    public int findRowCount() {
        return this.exprList.findRowCount();
    }
    
    public Set<T> findSet() {
        return this.exprList.findSet();
    }
    
    public T findUnique() {
        return this.exprList.findUnique();
    }
    
    public ExpressionList<T> ge(final String propertyName, final Object value) {
        return this.exprList.ge(propertyName, value);
    }
    
    public ExpressionList<T> gt(final String propertyName, final Object value) {
        return this.exprList.gt(propertyName, value);
    }
    
    public ExpressionList<T> having() {
        throw new RuntimeException("having() not allowed on Junction expression list");
    }
    
    public ExpressionList<T> icontains(final String propertyName, final String value) {
        return this.exprList.icontains(propertyName, value);
    }
    
    public ExpressionList<T> idEq(final Object value) {
        return this.exprList.idEq(value);
    }
    
    public ExpressionList<T> idIn(final List<?> idValues) {
        return this.exprList.idIn(idValues);
    }
    
    public ExpressionList<T> iendsWith(final String propertyName, final String value) {
        return this.exprList.iendsWith(propertyName, value);
    }
    
    public ExpressionList<T> ieq(final String propertyName, final String value) {
        return this.exprList.ieq(propertyName, value);
    }
    
    public ExpressionList<T> iexampleLike(final Object example) {
        return this.exprList.iexampleLike(example);
    }
    
    public ExpressionList<T> ilike(final String propertyName, final String value) {
        return this.exprList.ilike(propertyName, value);
    }
    
    public ExpressionList<T> in(final String propertyName, final Collection<?> values) {
        return this.exprList.in(propertyName, values);
    }
    
    public ExpressionList<T> in(final String propertyName, final Object... values) {
        return this.exprList.in(propertyName, values);
    }
    
    public ExpressionList<T> in(final String propertyName, final Query<?> subQuery) {
        return this.exprList.in(propertyName, subQuery);
    }
    
    public ExpressionList<T> isNotNull(final String propertyName) {
        return this.exprList.isNotNull(propertyName);
    }
    
    public ExpressionList<T> isNull(final String propertyName) {
        return this.exprList.isNull(propertyName);
    }
    
    public ExpressionList<T> istartsWith(final String propertyName, final String value) {
        return this.exprList.istartsWith(propertyName, value);
    }
    
    public Query<T> join(final String assocProperty, final String assocProperties) {
        return this.exprList.join(assocProperty, assocProperties);
    }
    
    public Query<T> join(final String assocProperties) {
        return this.exprList.join(assocProperties);
    }
    
    public ExpressionList<T> le(final String propertyName, final Object value) {
        return this.exprList.le(propertyName, value);
    }
    
    public ExpressionList<T> like(final String propertyName, final String value) {
        return this.exprList.like(propertyName, value);
    }
    
    public ExpressionList<T> lt(final String propertyName, final Object value) {
        return this.exprList.lt(propertyName, value);
    }
    
    public ExpressionList<T> lucene(final String propertyName, final String value) {
        return this.exprList.lucene(propertyName, value);
    }
    
    public ExpressionList<T> ne(final String propertyName, final Object value) {
        return this.exprList.ne(propertyName, value);
    }
    
    public ExpressionList<T> not(final Expression exp) {
        return this.exprList.not(exp);
    }
    
    public ExpressionList<T> or(final Expression expOne, final Expression expTwo) {
        return this.exprList.or(expOne, expTwo);
    }
    
    public OrderBy<T> order() {
        return this.exprList.order();
    }
    
    public Query<T> order(final String orderByClause) {
        return this.exprList.order(orderByClause);
    }
    
    public OrderBy<T> orderBy() {
        return this.exprList.orderBy();
    }
    
    public Query<T> orderBy(final String orderBy) {
        return this.exprList.orderBy(orderBy);
    }
    
    public Query<T> query() {
        return this.exprList.query();
    }
    
    public ExpressionList<T> raw(final String raw, final Object value) {
        return this.exprList.raw(raw, value);
    }
    
    public ExpressionList<T> raw(final String raw, final Object[] values) {
        return this.exprList.raw(raw, values);
    }
    
    public ExpressionList<T> raw(final String raw) {
        return this.exprList.raw(raw);
    }
    
    public Query<T> select(final String properties) {
        return this.exprList.select(properties);
    }
    
    public Query<T> setBackgroundFetchAfter(final int backgroundFetchAfter) {
        return this.exprList.setBackgroundFetchAfter(backgroundFetchAfter);
    }
    
    public Query<T> setFirstRow(final int firstRow) {
        return this.exprList.setFirstRow(firstRow);
    }
    
    public Query<T> setListener(final QueryListener<T> queryListener) {
        return this.exprList.setListener(queryListener);
    }
    
    public Query<T> setMapKey(final String mapKey) {
        return this.exprList.setMapKey(mapKey);
    }
    
    public Query<T> setMaxRows(final int maxRows) {
        return this.exprList.setMaxRows(maxRows);
    }
    
    public Query<T> setOrderBy(final String orderBy) {
        return this.exprList.setOrderBy(orderBy);
    }
    
    public Query<T> setUseCache(final boolean useCache) {
        return this.exprList.setUseCache(useCache);
    }
    
    public ExpressionList<T> startsWith(final String propertyName, final String value) {
        return this.exprList.startsWith(propertyName, value);
    }
    
    public ExpressionList<T> where() {
        return this.exprList.where();
    }
    
    static class Conjunction<T> extends JunctionExpression<T>
    {
        private static final long serialVersionUID = -645619859900030678L;
        
        Conjunction(final Query<T> query, final ExpressionList<T> parent) {
            super(" and ", query, parent);
        }
    }
    
    static class Disjunction<T> extends JunctionExpression<T>
    {
        private static final long serialVersionUID = -8464470066692221413L;
        
        Disjunction(final Query<T> query, final ExpressionList<T> parent) {
            super(" or ", query, parent);
        }
    }
}
