// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.cache;

import com.avaje.ebean.EbeanServer;

public interface ServerCache
{
    void init(final EbeanServer p0);
    
    ServerCacheOptions getOptions();
    
    void setOptions(final ServerCacheOptions p0);
    
    Object get(final Object p0);
    
    Object put(final Object p0, final Object p1);
    
    Object putIfAbsent(final Object p0, final Object p1);
    
    Object remove(final Object p0);
    
    void clear();
    
    int size();
    
    int getHitRatio();
    
    ServerCacheStatistics getStatistics(final boolean p0);
}
