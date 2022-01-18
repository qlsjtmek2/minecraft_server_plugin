// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.util.Map;
import java.util.Set;
import java.util.List;
import java.io.Serializable;

public interface Query<T> extends Serializable
{
    Query<T> setUseIndex(final UseIndex p0);
    
    UseIndex getUseIndex();
    
    Type getType();
    
    RawSql getRawSql();
    
    Query<T> setRawSql(final RawSql p0);
    
    void cancel();
    
    Query<T> copy();
    
    ExpressionFactory getExpressionFactory();
    
    boolean isAutofetchTuned();
    
    Query<T> setAutofetch(final boolean p0);
    
    Query<T> setQuery(final String p0);
    
    Query<T> select(final String p0);
    
    Query<T> fetch(final String p0, final String p1);
    
    Query<T> join(final String p0, final String p1);
    
    Query<T> fetch(final String p0, final String p1, final FetchConfig p2);
    
    Query<T> join(final String p0, final String p1, final JoinConfig p2);
    
    Query<T> fetch(final String p0);
    
    Query<T> join(final String p0);
    
    Query<T> fetch(final String p0, final FetchConfig p1);
    
    Query<T> join(final String p0, final JoinConfig p1);
    
    List<Object> findIds();
    
    QueryIterator<T> findIterate();
    
    void findVisit(final QueryResultVisitor<T> p0);
    
    List<T> findList();
    
    Set<T> findSet();
    
    Map<?, T> findMap();
    
     <K> Map<K, T> findMap(final String p0, final Class<K> p1);
    
    T findUnique();
    
    int findRowCount();
    
    FutureRowCount<T> findFutureRowCount();
    
    FutureIds<T> findFutureIds();
    
    FutureList<T> findFutureList();
    
    PagingList<T> findPagingList(final int p0);
    
    Query<T> setParameter(final String p0, final Object p1);
    
    Query<T> setParameter(final int p0, final Object p1);
    
    Query<T> setListener(final QueryListener<T> p0);
    
    Query<T> setId(final Object p0);
    
    Query<T> where(final String p0);
    
    Query<T> where(final Expression p0);
    
    ExpressionList<T> where();
    
    ExpressionList<T> filterMany(final String p0);
    
    ExpressionList<T> having();
    
    Query<T> having(final String p0);
    
    Query<T> having(final Expression p0);
    
    Query<T> orderBy(final String p0);
    
    Query<T> order(final String p0);
    
    OrderBy<T> order();
    
    OrderBy<T> orderBy();
    
    Query<T> setOrder(final OrderBy<T> p0);
    
    Query<T> setOrderBy(final OrderBy<T> p0);
    
    Query<T> setDistinct(final boolean p0);
    
    Query<T> setVanillaMode(final boolean p0);
    
    int getFirstRow();
    
    Query<T> setFirstRow(final int p0);
    
    int getMaxRows();
    
    Query<T> setMaxRows(final int p0);
    
    Query<T> setBackgroundFetchAfter(final int p0);
    
    Query<T> setMapKey(final String p0);
    
    Query<T> setUseCache(final boolean p0);
    
    Query<T> setUseQueryCache(final boolean p0);
    
    Query<T> setReadOnly(final boolean p0);
    
    Query<T> setLoadBeanCache(final boolean p0);
    
    Query<T> setTimeout(final int p0);
    
    Query<T> setBufferFetchSizeHint(final int p0);
    
    String getGeneratedSql();
    
    public enum UseIndex
    {
        NO, 
        DEFAULT, 
        YES_IDS, 
        YES_OBJECTS;
    }
    
    public enum Type
    {
        BEAN, 
        LIST, 
        SET, 
        MAP, 
        ID_LIST, 
        ROWCOUNT, 
        SUBQUERY;
    }
}
