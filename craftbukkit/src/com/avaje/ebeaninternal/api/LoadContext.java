// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;

public interface LoadContext
{
    int getSecondaryQueriesMinBatchSize(final OrmQueryRequest<?> p0, final int p1);
    
    void executeSecondaryQueries(final OrmQueryRequest<?> p0, final int p1);
    
    void registerSecondaryQueries(final SpiQuery<?> p0);
    
    boolean isUseAutofetchManager();
    
    ObjectGraphNode getObjectGraphNode(final String p0);
    
    int getParentState();
    
    PersistenceContext getPersistenceContext();
    
    void setPersistenceContext(final PersistenceContext p0);
    
    void register(final String p0, final EntityBeanIntercept p1);
    
    void register(final String p0, final BeanCollection<?> p1);
    
    LoadBeanContext getBeanContext(final String p0);
    
    LoadManyContext getManyContext(final String p0);
}
