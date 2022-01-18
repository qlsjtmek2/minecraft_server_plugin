// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.io.Serializable;

public interface ExpressionList<T> extends Serializable
{
    Query<T> query();
    
    Query<T> order(final String p0);
    
    OrderBy<T> order();
    
    OrderBy<T> orderBy();
    
    Query<T> orderBy(final String p0);
    
    Query<T> setOrderBy(final String p0);
    
    QueryIterator<T> findIterate();
    
    void findVisit(final QueryResultVisitor<T> p0);
    
    List<T> findList();
    
    List<Object> findIds();
    
    int findRowCount();
    
    Set<T> findSet();
    
    Map<?, T> findMap();
    
     <K> Map<K, T> findMap(final String p0, final Class<K> p1);
    
    T findUnique();
    
    FutureRowCount<T> findFutureRowCount();
    
    FutureIds<T> findFutureIds();
    
    FutureList<T> findFutureList();
    
    PagingList<T> findPagingList(final int p0);
    
    ExpressionList<T> filterMany(final String p0);
    
    Query<T> select(final String p0);
    
    Query<T> join(final String p0);
    
    Query<T> join(final String p0, final String p1);
    
    Query<T> setFirstRow(final int p0);
    
    Query<T> setMaxRows(final int p0);
    
    Query<T> setBackgroundFetchAfter(final int p0);
    
    Query<T> setMapKey(final String p0);
    
    Query<T> setListener(final QueryListener<T> p0);
    
    Query<T> setUseCache(final boolean p0);
    
    ExpressionList<T> having();
    
    ExpressionList<T> where();
    
    ExpressionList<T> add(final Expression p0);
    
    ExpressionList<T> eq(final String p0, final Object p1);
    
    ExpressionList<T> ne(final String p0, final Object p1);
    
    ExpressionList<T> ieq(final String p0, final String p1);
    
    ExpressionList<T> between(final String p0, final Object p1, final Object p2);
    
    ExpressionList<T> betweenProperties(final String p0, final String p1, final Object p2);
    
    ExpressionList<T> gt(final String p0, final Object p1);
    
    ExpressionList<T> ge(final String p0, final Object p1);
    
    ExpressionList<T> lt(final String p0, final Object p1);
    
    ExpressionList<T> le(final String p0, final Object p1);
    
    ExpressionList<T> isNull(final String p0);
    
    ExpressionList<T> isNotNull(final String p0);
    
    ExpressionList<T> exampleLike(final Object p0);
    
    ExpressionList<T> iexampleLike(final Object p0);
    
    ExpressionList<T> like(final String p0, final String p1);
    
    ExpressionList<T> ilike(final String p0, final String p1);
    
    ExpressionList<T> startsWith(final String p0, final String p1);
    
    ExpressionList<T> istartsWith(final String p0, final String p1);
    
    ExpressionList<T> endsWith(final String p0, final String p1);
    
    ExpressionList<T> iendsWith(final String p0, final String p1);
    
    ExpressionList<T> contains(final String p0, final String p1);
    
    ExpressionList<T> lucene(final String p0, final String p1);
    
    ExpressionList<T> icontains(final String p0, final String p1);
    
    ExpressionList<T> in(final String p0, final Query<?> p1);
    
    ExpressionList<T> in(final String p0, final Object... p1);
    
    ExpressionList<T> in(final String p0, final Collection<?> p1);
    
    ExpressionList<T> idIn(final List<?> p0);
    
    ExpressionList<T> idEq(final Object p0);
    
    ExpressionList<T> allEq(final Map<String, Object> p0);
    
    ExpressionList<T> raw(final String p0, final Object p1);
    
    ExpressionList<T> raw(final String p0, final Object[] p1);
    
    ExpressionList<T> raw(final String p0);
    
    ExpressionList<T> and(final Expression p0, final Expression p1);
    
    ExpressionList<T> or(final Expression p0, final Expression p1);
    
    ExpressionList<T> not(final Expression p0);
    
    Junction<T> conjunction();
    
    Junction<T> disjunction();
    
    ExpressionList<T> endJunction();
}
