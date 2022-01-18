// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster;

import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;

public interface ClusterBroadcast
{
    void startup(final ClusterManager p0);
    
    void shutdown();
    
    void broadcast(final RemoteTransactionEvent p0);
}
