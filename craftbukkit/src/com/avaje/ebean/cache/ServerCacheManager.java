// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.cache;

import com.avaje.ebean.EbeanServer;

public interface ServerCacheManager
{
    void init(final EbeanServer p0);
    
    boolean isBeanCaching(final Class<?> p0);
    
    ServerCache getBeanCache(final Class<?> p0);
    
    ServerCache getQueryCache(final Class<?> p0);
    
    void clear(final Class<?> p0);
    
    void clearAll();
}
