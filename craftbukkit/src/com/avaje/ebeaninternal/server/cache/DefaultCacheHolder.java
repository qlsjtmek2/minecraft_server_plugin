// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cache;

import com.avaje.ebean.annotation.CacheTuning;
import java.util.Iterator;
import com.avaje.ebean.cache.ServerCacheOptions;
import com.avaje.ebean.cache.ServerCacheFactory;
import java.util.HashMap;
import com.avaje.ebean.cache.ServerCache;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultCacheHolder
{
    private final ConcurrentHashMap<Class<?>, ServerCache> concMap;
    private final HashMap<Class<?>, ServerCache> synchMap;
    private final Object monitor;
    private final ServerCacheFactory cacheFactory;
    private final ServerCacheOptions defaultOptions;
    private final boolean useBeanTuning;
    
    public DefaultCacheHolder(final ServerCacheFactory cacheFactory, final ServerCacheOptions defaultOptions, final boolean useBeanTuning) {
        this.concMap = new ConcurrentHashMap<Class<?>, ServerCache>();
        this.synchMap = new HashMap<Class<?>, ServerCache>();
        this.monitor = new Object();
        this.cacheFactory = cacheFactory;
        this.defaultOptions = defaultOptions;
        this.useBeanTuning = useBeanTuning;
    }
    
    public ServerCacheOptions getDefaultOptions() {
        return this.defaultOptions;
    }
    
    public ServerCache getCache(final Class<?> beanType) {
        ServerCache cache = this.concMap.get(beanType);
        if (cache != null) {
            return cache;
        }
        synchronized (this.monitor) {
            cache = this.synchMap.get(beanType);
            if (cache == null) {
                final ServerCacheOptions options = this.getCacheOptions(beanType);
                cache = this.cacheFactory.createCache(beanType, options);
                this.synchMap.put(beanType, cache);
                this.concMap.put(beanType, cache);
            }
            return cache;
        }
    }
    
    public boolean isCaching(final Class<?> beanType) {
        return this.concMap.containsKey(beanType);
    }
    
    public void clearAll() {
        for (final ServerCache serverCache : this.concMap.values()) {
            serverCache.clear();
        }
    }
    
    private ServerCacheOptions getCacheOptions(final Class<?> beanType) {
        if (this.useBeanTuning) {
            final CacheTuning cacheTuning = beanType.getAnnotation(CacheTuning.class);
            if (cacheTuning != null) {
                final ServerCacheOptions o = new ServerCacheOptions(cacheTuning);
                o.applyDefaults(this.defaultOptions);
                return o;
            }
        }
        return this.defaultOptions.copy();
    }
}
