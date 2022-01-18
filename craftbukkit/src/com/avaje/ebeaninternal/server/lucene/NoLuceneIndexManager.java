// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebean.config.lucene.IndexDefn;
import java.io.IOException;
import com.avaje.ebeaninternal.server.cluster.LuceneClusterIndexSync;
import com.avaje.ebeaninternal.server.transaction.IndexEvent;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import com.avaje.ebeaninternal.api.SpiEbeanServer;

public class NoLuceneIndexManager implements LuceneIndexManager
{
    public void start() {
    }
    
    public void shutdown() {
    }
    
    public void setServer(final SpiEbeanServer server) {
    }
    
    public boolean isLuceneAvailable() {
        return false;
    }
    
    public void processEvent(final RemoteTransactionEvent txnEvent, final SpiTransaction t) {
        throw new RuntimeException("Never Called");
    }
    
    public void processEvent(final IndexEvent indexEvent) {
        throw new RuntimeException("Never Called");
    }
    
    public LuceneClusterIndexSync getClusterIndexSync() {
        throw new RuntimeException("Never Called");
    }
    
    public void notifyCluster(final IndexEvent event) {
        throw new RuntimeException("Never Called");
    }
    
    public void addIndex(final LIndex index) throws IOException {
        throw new RuntimeException("Never Called");
    }
    
    public LIndex getIndex(final String defnName) {
        throw new RuntimeException("Never Called");
    }
    
    public LIndex create(final IndexDefn<?> indexDefn, final BeanDescriptor<?> descriptor) throws IOException {
        throw new RuntimeException("Never Called");
    }
    
    public Query.UseIndex getDefaultUseIndex() {
        return Query.UseIndex.NO;
    }
    
    public LIndex getIndexByTypeAndName(final Class<?> beanType, final String name) {
        throw new RuntimeException("Never Called");
    }
    
    public String getIndexDirectory(final String indexName) {
        throw new RuntimeException("Never Called");
    }
    
    public SpiEbeanServer getServer() {
        throw new RuntimeException("Never Called");
    }
}
