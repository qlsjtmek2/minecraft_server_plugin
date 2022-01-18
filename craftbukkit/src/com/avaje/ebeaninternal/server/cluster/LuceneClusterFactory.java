// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster;

public interface LuceneClusterFactory
{
    LuceneClusterListener createListener(final ClusterManager p0, final int p1);
    
    LuceneClusterIndexSync createIndexSync();
}
