// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cache;

import com.avaje.ebean.cache.ServerCache;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.cache.ServerCacheOptions;
import com.avaje.ebean.cache.ServerCacheFactory;
import com.avaje.ebean.cache.ServerCacheManager;

public class DefaultServerCacheManager implements ServerCacheManager
{
    private final DefaultCacheHolder beanCache;
    private final DefaultCacheHolder queryCache;
    private final ServerCacheFactory cacheFactory;
    
    public DefaultServerCacheManager(final ServerCacheFactory cacheFactory, final ServerCacheOptions defaultBeanOptions, final ServerCacheOptions defaultQueryOptions) {
        this.cacheFactory = cacheFactory;
        this.beanCache = new DefaultCacheHolder(cacheFactory, defaultBeanOptions, true);
        this.queryCache = new DefaultCacheHolder(cacheFactory, defaultQueryOptions, false);
    }
    
    public void init(final EbeanServer server) {
        this.cacheFactory.init(server);
    }
    
    public void clear(final Class<?> beanType) {
        if (this.isBeanCaching(beanType)) {
            this.getBeanCache(beanType).clear();
        }
        if (this.isQueryCaching(beanType)) {
            this.getQueryCache(beanType).clear();
        }
    }
    
    public void clearAll() {
        this.beanCache.clearAll();
        this.queryCache.clearAll();
    }
    
    public ServerCache getQueryCache(final Class<?> beanType) {
        return this.queryCache.getCache(beanType);
    }
    
    public ServerCache getBeanCache(final Class<?> beanType) {
        return this.beanCache.getCache(beanType);
    }
    
    public boolean isBeanCaching(final Class<?> beanType) {
        return this.beanCache.isCaching(beanType);
    }
    
    public boolean isQueryCaching(final Class<?> beanType) {
        return this.queryCache.isCaching(beanType);
    }
}
