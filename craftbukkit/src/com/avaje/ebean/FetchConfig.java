// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

public class FetchConfig
{
    private int lazyBatchSize;
    private int queryBatchSize;
    private boolean queryAll;
    
    public FetchConfig() {
        this.lazyBatchSize = -1;
        this.queryBatchSize = -1;
    }
    
    public FetchConfig lazy() {
        this.lazyBatchSize = 0;
        return this;
    }
    
    public FetchConfig lazy(final int lazyBatchSize) {
        this.lazyBatchSize = lazyBatchSize;
        return this;
    }
    
    public FetchConfig query() {
        this.queryBatchSize = 0;
        this.queryAll = true;
        return this;
    }
    
    public FetchConfig query(final int queryBatchSize) {
        this.queryBatchSize = queryBatchSize;
        this.queryAll = true;
        return this;
    }
    
    public FetchConfig queryFirst(final int queryBatchSize) {
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
