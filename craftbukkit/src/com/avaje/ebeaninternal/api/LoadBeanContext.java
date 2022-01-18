// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.bean.PersistenceContext;

public interface LoadBeanContext extends LoadSecondaryQuery
{
    void configureQuery(final SpiQuery<?> p0, final String p1);
    
    String getFullPath();
    
    PersistenceContext getPersistenceContext();
    
    BeanDescriptor<?> getBeanDescriptor();
    
    int getBatchSize();
}
