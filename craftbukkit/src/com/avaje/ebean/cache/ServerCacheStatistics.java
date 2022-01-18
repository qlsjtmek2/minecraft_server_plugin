// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.cache;

public class ServerCacheStatistics
{
    protected String cacheName;
    protected int maxSize;
    protected int size;
    protected int hitCount;
    protected int missCount;
    
    public String getCacheName() {
        return this.cacheName;
    }
    
    public void setCacheName(final String cacheName) {
        this.cacheName = cacheName;
    }
    
    public int getHitCount() {
        return this.hitCount;
    }
    
    public void setHitCount(final int hitCount) {
        this.hitCount = hitCount;
    }
    
    public int getMissCount() {
        return this.missCount;
    }
    
    public void setMissCount(final int missCount) {
        this.missCount = missCount;
    }
    
    public int getSize() {
        return this.size;
    }
    
    public void setSize(final int size) {
        this.size = size;
    }
    
    public int getMaxSize() {
        return this.maxSize;
    }
    
    public void setMaxSize(final int maxSize) {
        this.maxSize = maxSize;
    }
    
    public int getHitRatio() {
        final int totalCount = this.hitCount + this.missCount;
        if (totalCount == 0) {
            return 0;
        }
        return this.hitCount * 100 / totalCount;
    }
}
