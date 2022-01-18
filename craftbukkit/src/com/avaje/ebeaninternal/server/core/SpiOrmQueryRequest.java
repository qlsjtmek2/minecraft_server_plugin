// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.bean.BeanCollection;
import java.util.Map;
import java.util.Set;
import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.QueryResultVisitor;
import java.util.List;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.api.SpiQuery;

public interface SpiOrmQueryRequest<T>
{
    SpiQuery<T> getQuery();
    
    BeanDescriptor<?> getBeanDescriptor();
    
    void initTransIfRequired();
    
    void endTransIfRequired();
    
    void rollbackTransIfRequired();
    
    Object findId();
    
    int findRowCount();
    
    List<Object> findIds();
    
    void findVisit(final QueryResultVisitor<T> p0);
    
    QueryIterator<T> findIterate();
    
    List<T> findList();
    
    Set<?> findSet();
    
    Map<?, ?> findMap();
    
    T getFromPersistenceContextOrCache();
    
    BeanCollection<T> getFromQueryCache();
}
