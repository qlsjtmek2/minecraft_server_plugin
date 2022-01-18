// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.common;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;

public interface BootupEbeanManager
{
    EbeanServer createServer(final ServerConfig p0);
    
    EbeanServer createServer(final String p0);
    
    void shutdown();
}
