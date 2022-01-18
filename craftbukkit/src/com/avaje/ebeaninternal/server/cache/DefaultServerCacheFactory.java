// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cache;

import com.avaje.ebean.cache.ServerCache;
import com.avaje.ebean.cache.ServerCacheOptions;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.cache.ServerCacheFactory;

public class DefaultServerCacheFactory implements ServerCacheFactory
{
    private EbeanServer ebeanServer;
    
    public void init(final EbeanServer ebeanServer) {
        this.ebeanServer = ebeanServer;
    }
    
    public ServerCache createCache(final Class<?> beanType, final ServerCacheOptions cacheOptions) {
        final ServerCache cache = new DefaultServerCache("Bean:" + beanType, cacheOptions);
        cache.init(this.ebeanServer);
        return cache;
    }
}
