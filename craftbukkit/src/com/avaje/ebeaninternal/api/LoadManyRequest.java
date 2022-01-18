// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import com.avaje.ebean.Transaction;
import com.avaje.ebean.bean.BeanCollection;
import java.util.List;

public class LoadManyRequest extends LoadRequest
{
    private final List<BeanCollection<?>> batch;
    private final LoadManyContext loadContext;
    private final boolean onlyIds;
    
    public LoadManyRequest(final LoadManyContext loadContext, final List<BeanCollection<?>> batch, final Transaction transaction, final int batchSize, final boolean lazy, final boolean onlyIds) {
        super(transaction, batchSize, lazy);
        this.loadContext = loadContext;
        this.batch = batch;
        this.onlyIds = onlyIds;
    }
    
    public String getDescription() {
        final String fullPath = this.loadContext.getFullPath();
        final String s = "path:" + fullPath + " batch:" + this.batchSize + " actual:" + this.batch.size();
        return s;
    }
    
    public List<BeanCollection<?>> getBatch() {
        return this.batch;
    }
    
    public LoadManyContext getLoadContext() {
        return this.loadContext;
    }
    
    public boolean isOnlyIds() {
        return this.onlyIds;
    }
}
