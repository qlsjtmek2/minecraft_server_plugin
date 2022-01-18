// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.cache;

import com.avaje.ebean.annotation.CacheTuning;

public class ServerCacheOptions
{
    private int maxSize;
    private int maxIdleSecs;
    private int maxSecsToLive;
    
    public ServerCacheOptions() {
    }
    
    public ServerCacheOptions(final CacheTuning cacheTuning) {
        this.maxSize = cacheTuning.maxSize();
        this.maxIdleSecs = cacheTuning.maxIdleSecs();
        this.maxSecsToLive = cacheTuning.maxSecsToLive();
    }
    
    public ServerCacheOptions(final ServerCacheOptions d) {
        this.maxSize = d.getMaxSize();
        this.maxIdleSecs = d.getMaxIdleSecs();
        this.maxSecsToLive = d.getMaxIdleSecs();
    }
    
    public void applyDefaults(final ServerCacheOptions defaults) {
        if (this.maxSize == 0) {
            this.maxSize = defaults.getMaxSize();
        }
        if (this.maxIdleSecs == 0) {
            this.maxIdleSecs = defaults.getMaxIdleSecs();
        }
        if (this.maxSecsToLive == 0) {
            this.maxSecsToLive = defaults.getMaxSecsToLive();
        }
    }
    
    public ServerCacheOptions copy() {
        final ServerCacheOptions copy = new ServerCacheOptions();
        copy.maxSize = this.maxSize;
        copy.maxIdleSecs = this.maxIdleSecs;
        copy.maxSecsToLive = this.maxSecsToLive;
        return copy;
    }
    
    public int getMaxSize() {
        return this.maxSize;
    }
    
    public void setMaxSize(final int maxSize) {
        this.maxSize = maxSize;
    }
    
    public int getMaxIdleSecs() {
        return this.maxIdleSecs;
    }
    
    public void setMaxIdleSecs(final int maxIdleSecs) {
        this.maxIdleSecs = maxIdleSecs;
    }
    
    public int getMaxSecsToLive() {
        return this.maxSecsToLive;
    }
    
    public void setMaxSecsToLive(final int maxSecsToLive) {
        this.maxSecsToLive = maxSecsToLive;
    }
}
