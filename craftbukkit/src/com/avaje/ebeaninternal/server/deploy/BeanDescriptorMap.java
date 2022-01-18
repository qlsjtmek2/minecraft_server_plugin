// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebeaninternal.server.deploy.id.IdBinder;
import com.avaje.ebean.config.EncryptKey;
import com.avaje.ebean.cache.ServerCacheManager;

public interface BeanDescriptorMap
{
    String getServerName();
    
    ServerCacheManager getCacheManager();
    
     <T> BeanDescriptor<T> getBeanDescriptor(final Class<T> p0);
    
    EncryptKey getEncryptKey(final String p0, final String p1);
    
    IdBinder createIdBinder(final BeanProperty[] p0);
}
