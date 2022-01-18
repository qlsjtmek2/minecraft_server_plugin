// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import com.avaje.ebeaninternal.api.BeanIdList;
import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.bean.BeanCollection;

public interface OrmQueryEngine
{
     <T> T findId(final OrmQueryRequest<T> p0);
    
     <T> BeanCollection<T> findMany(final OrmQueryRequest<T> p0);
    
     <T> QueryIterator<T> findIterate(final OrmQueryRequest<T> p0);
    
     <T> int findRowCount(final OrmQueryRequest<T> p0);
    
     <T> BeanIdList findIds(final OrmQueryRequest<T> p0);
}
