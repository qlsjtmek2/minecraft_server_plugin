// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import com.avaje.ebean.Transaction;
import com.avaje.ebean.bean.EntityBeanIntercept;
import java.util.List;

public class LoadBeanRequest extends LoadRequest
{
    private final List<EntityBeanIntercept> batch;
    private final LoadBeanContext loadContext;
    private final String lazyLoadProperty;
    
    public LoadBeanRequest(final LoadBeanContext loadContext, final List<EntityBeanIntercept> batch, final Transaction transaction, final int batchSize, final boolean lazy, final String lazyLoadProperty) {
        super(transaction, batchSize, lazy);
        this.loadContext = loadContext;
        this.batch = batch;
        this.lazyLoadProperty = lazyLoadProperty;
    }
    
    public String getDescription() {
        final String fullPath = this.loadContext.getFullPath();
        final String s = "path:" + fullPath + " batch:" + this.batchSize + " actual:" + this.batch.size();
        return s;
    }
    
    public List<EntityBeanIntercept> getBatch() {
        return this.batch;
    }
    
    public LoadBeanContext getLoadContext() {
        return this.loadContext;
    }
    
    public String getLazyLoadProperty() {
        return this.lazyLoadProperty;
    }
}
