// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebeaninternal.api.SpiEbeanServer;
import java.io.IOException;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.config.lucene.IndexDefn;
import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.server.transaction.IndexEvent;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import com.avaje.ebeaninternal.server.cluster.LuceneClusterIndexSync;

public interface LuceneIndexManager
{
    void start();
    
    void shutdown();
    
    boolean isLuceneAvailable();
    
    LuceneClusterIndexSync getClusterIndexSync();
    
    void processEvent(final RemoteTransactionEvent p0, final SpiTransaction p1);
    
    void processEvent(final IndexEvent p0);
    
    Query.UseIndex getDefaultUseIndex();
    
    LIndex create(final IndexDefn<?> p0, final BeanDescriptor<?> p1) throws IOException;
    
    LIndex getIndex(final String p0);
    
    SpiEbeanServer getServer();
    
    void setServer(final SpiEbeanServer p0);
    
    void addIndex(final LIndex p0) throws IOException;
    
    String getIndexDirectory(final String p0);
    
    void notifyCluster(final IndexEvent p0);
}
