// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene.cluster;

import com.avaje.ebeaninternal.server.cluster.LuceneClusterIndexSync;
import com.avaje.ebeaninternal.server.cluster.LuceneClusterListener;
import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import com.avaje.ebeaninternal.server.cluster.LuceneClusterFactory;

public class SLuceneClusterFactory implements LuceneClusterFactory
{
    public LuceneClusterListener createListener(final ClusterManager m, final int port) {
        return new SLuceneClusterSocketListener(m, port);
    }
    
    public LuceneClusterIndexSync createIndexSync() {
        return new SLuceneIndexSync();
    }
}
