// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebean.bean.ObjectGraphNode;

public interface LoadManyContext extends LoadSecondaryQuery
{
    void configureQuery(final SpiQuery<?> p0);
    
    String getFullPath();
    
    ObjectGraphNode getObjectGraphNode();
    
    PersistenceContext getPersistenceContext();
    
    int getBatchSize();
    
    BeanDescriptor<?> getBeanDescriptor();
    
    BeanPropertyAssocMany<?> getBeanProperty();
}
