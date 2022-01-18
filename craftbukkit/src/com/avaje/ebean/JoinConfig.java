// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

public class JoinConfig
{
    private int lazyBatchSize;
    private int queryBatchSize;
    private boolean queryAll;
    
    public JoinConfig() {
        this.lazyBatchSize = -1;
        this.queryBatchSize = -1;
    }
    
    public JoinConfig lazy() {
        this.lazyBatchSize = 0;
        return this;
    }
    
    public JoinConfig lazy(final int lazyBatchSize) {
        this.lazyBatchSize = lazyBatchSize;
        return this;
    }
    
    public JoinConfig query() {
        this.queryBatchSize = 0;
        this.queryAll = true;
        return this;
    }
    
    public JoinConfig query(final int queryBatchSize) {
        this.queryBatchSize = queryBatchSize;
        this.queryAll = true;
        return this;
    }
    
    public JoinConfig queryFirst(final int queryBatchSize) {
        this.queryBatchSize = queryBatchSize;
        this.queryAll = false;
        return this;
    }
    
    public int getLazyBatchSize() {
        return this.lazyBatchSize;
    }
    
    public int getQueryBatchSize() {
        return this.queryBatchSize;
    }
    
    public boolean isQueryAll() {
        return this.queryAll;
    }
}
