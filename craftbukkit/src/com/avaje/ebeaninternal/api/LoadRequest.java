// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import com.avaje.ebean.Transaction;

public abstract class LoadRequest
{
    protected final boolean lazy;
    protected final int batchSize;
    protected final Transaction transaction;
    
    public LoadRequest(final Transaction transaction, final int batchSize, final boolean lazy) {
        this.transaction = transaction;
        this.batchSize = batchSize;
        this.lazy = lazy;
    }
    
    public boolean isLazy() {
        return this.lazy;
    }
    
    public int getBatchSize() {
        return this.batchSize;
    }
    
    public Transaction getTransaction() {
        return this.transaction;
    }
}
