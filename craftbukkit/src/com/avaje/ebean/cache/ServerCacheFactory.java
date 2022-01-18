// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.cache;

import com.avaje.ebean.EbeanServer;

public interface ServerCacheFactory
{
    void init(final EbeanServer p0);
    
    ServerCache createCache(final Class<?> p0, final ServerCacheOptions p1);
}
