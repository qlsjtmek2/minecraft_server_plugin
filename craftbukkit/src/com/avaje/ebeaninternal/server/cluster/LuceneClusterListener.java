// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster;

public interface LuceneClusterListener
{
    int getPort();
    
    void startup();
    
    void shutdown();
}
