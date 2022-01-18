// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lucene;

import java.util.logging.Level;
import java.io.IOException;
import com.avaje.ebean.config.lucene.IndexDefn;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.Iterator;
import java.util.Collection;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import com.avaje.ebeaninternal.server.transaction.IndexEvent;
import java.io.File;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebean.Query;
import org.apache.lucene.analysis.Analyzer;
import com.avaje.ebean.BackgroundExecutor;
import com.avaje.ebeaninternal.server.cluster.LuceneClusterIndexSync;
import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class DefaultLuceneIndexManager implements LuceneIndexManager, Runnable
{
    private static final Logger logger;
    private final ConcurrentHashMap<String, LIndex> indexMap;
    private final ClusterManager clusterManager;
    private final LuceneClusterIndexSync clusterIndexSync;
    private final BackgroundExecutor backgroundExecutor;
    private final Analyzer defaultAnalyzer;
    private final String baseDir;
    private final LIndexFactory indexFactory;
    private final boolean luceneAvailable;
    private final Query.UseIndex defaultUseIndex;
    private final String serverName;
    private SpiEbeanServer server;
    private Thread thread;
    private volatile boolean shutdown;
    private volatile boolean shutdownComplete;
    private long manageFreqMillis;
    
    public DefaultLuceneIndexManager(final ClusterManager clusterManager, final BackgroundExecutor backgroundExecutor, final Analyzer defaultAnalyzer, final String baseDir, final String serverName, final Query.UseIndex defaultUseIndex) {
        this.manageFreqMillis = 100L;
        this.luceneAvailable = true;
        this.serverName = serverName;
        this.clusterManager = clusterManager;
        this.clusterIndexSync = clusterManager.getLuceneClusterIndexSync();
        this.backgroundExecutor = backgroundExecutor;
        this.defaultUseIndex = defaultUseIndex;
        this.defaultAnalyzer = defaultAnalyzer;
        this.baseDir = baseDir + File.separator + serverName + File.separator;
        this.indexMap = new ConcurrentHashMap<String, LIndex>();
        this.indexFactory = new LIndexFactory(this);
        this.thread = new Thread(this, "Ebean-" + serverName + "-LuceneManager");
    }
    
    public void notifyCluster(final IndexEvent event) {
        if (this.clusterIndexSync != null && this.clusterIndexSync.isMaster()) {
            System.out.println("-- notifyCluster commit ... ");
            final RemoteTransactionEvent e = new RemoteTransactionEvent(this.serverName);
            e.addIndexEvent(event);
            this.clusterManager.broadcast(e);
        }
    }
    
    protected void execute(final LIndexSync indexSync) {
        if (this.clusterIndexSync != null) {
            final IndexSynchRun r = new IndexSynchRun(this.clusterIndexSync, indexSync);
            this.backgroundExecutor.execute(r);
        }
    }
    
    public void processEvent(final IndexEvent indexEvent) {
        if (this.clusterIndexSync == null) {
            return;
        }
        final String masterHost = this.clusterIndexSync.getMasterHost();
        if (masterHost == null) {
            DefaultLuceneIndexManager.logger.warning("Master got IndexEvent " + indexEvent + " ?");
        }
        else {
            final String idxName = indexEvent.getIndexName();
            if (idxName != null) {
                final LIndex index = this.getIndex(idxName);
                if (index == null) {
                    DefaultLuceneIndexManager.logger.warning("Can't find Lucene Index [" + idxName + "]");
                }
                else {
                    index.queueSync(masterHost);
                }
            }
        }
    }
    
    public void processEvent(final RemoteTransactionEvent txnEvent, final SpiTransaction localTransaction) {
        final Collection<IndexUpdates> events = IndexUpdatesBuilder.create(this.server, txnEvent);
        for (final IndexUpdates e : events) {
            final BeanDescriptor<?> beanDescriptor = e.getBeanDescriptor();
            final LIndex luceneIndex = beanDescriptor.getLuceneIndex();
            if (luceneIndex != null) {
                final LIndexUpdateFuture future = luceneIndex.process(e);
                if (localTransaction == null) {
                    continue;
                }
                localTransaction.addIndexUpdateFuture(future);
            }
        }
    }
    
    public LuceneClusterIndexSync getClusterIndexSync() {
        return this.clusterIndexSync;
    }
    
    public boolean isLuceneAvailable() {
        return this.luceneAvailable;
    }
    
    public Query.UseIndex getDefaultUseIndex() {
        return this.defaultUseIndex;
    }
    
    public LIndex create(final IndexDefn<?> indexDefn, final BeanDescriptor<?> descriptor) throws IOException {
        return this.indexFactory.create(indexDefn, descriptor);
    }
    
    public SpiEbeanServer getServer() {
        return this.server;
    }
    
    public void setServer(final SpiEbeanServer server) {
        this.server = server;
    }
    
    public Analyzer getDefaultAnalyzer() {
        return this.defaultAnalyzer;
    }
    
    public void addIndex(final LIndex index) throws IOException {
        synchronized (this.indexMap) {
            this.indexMap.put(index.getName(), index);
        }
    }
    
    public LIndex getIndex(final String name) {
        return this.indexMap.get(name);
    }
    
    public String getIndexDirectory(final String indexName) {
        return this.baseDir + indexName;
    }
    
    public void start() {
        this.thread.setDaemon(true);
        this.thread.start();
        DefaultLuceneIndexManager.logger.info("Lucene Manager started");
    }
    
    public void shutdown() {
        this.shutdown = true;
        synchronized (this.thread) {
            try {
                this.thread.wait(20000L);
            }
            catch (InterruptedException e) {
                DefaultLuceneIndexManager.logger.info("InterruptedException:" + e);
            }
        }
        if (!this.shutdownComplete) {
            final String msg = "WARNING: Shutdown of Lucene Manager did not complete?";
            System.err.println(msg);
            DefaultLuceneIndexManager.logger.warning(msg);
        }
    }
    
    private void fireOnStartup() {
        if (this.clusterIndexSync != null && !this.clusterIndexSync.isMaster()) {
            final String masterHost = this.clusterIndexSync.getMasterHost();
            if (masterHost != null) {
                for (final LIndex index : this.indexMap.values()) {
                    index.queueSync(masterHost);
                }
            }
        }
    }
    
    public void run() {
        this.fireOnStartup();
        while (!this.shutdown) {
            synchronized (this.indexMap) {
                final long start = System.currentTimeMillis();
                for (final LIndex idx : this.indexMap.values()) {
                    idx.manage(this);
                }
                final long exeTime = System.currentTimeMillis() - start;
                final long sleepMillis = this.manageFreqMillis - exeTime;
                if (sleepMillis <= 0L) {
                    continue;
                }
                try {
                    Thread.sleep(sleepMillis);
                }
                catch (InterruptedException e) {
                    DefaultLuceneIndexManager.logger.log(Level.INFO, "Interrupted", e);
                }
            }
        }
        this.shutdownComplete = true;
        synchronized (this.thread) {
            this.thread.notifyAll();
        }
    }
    
    static {
        logger = Logger.getLogger(DefaultLuceneIndexManager.class.getName());
    }
    
    private static class IndexSynchRun implements Runnable
    {
        private final LuceneClusterIndexSync clusterIndexSync;
        private final LIndex index;
        private final String masterHost;
        
        private IndexSynchRun(final LuceneClusterIndexSync clusterIndexSync, final LIndexSync indexSync) {
            this.clusterIndexSync = clusterIndexSync;
            this.index = indexSync.getIndex();
            this.masterHost = indexSync.getMasterHost();
        }
        
        public void run() {
            boolean success = false;
            try {
                this.clusterIndexSync.sync(this.index, this.masterHost);
                success = true;
            }
            catch (IOException e) {
                final String msg = "Failed to sync Lucene index " + this.index;
                DefaultLuceneIndexManager.logger.log(Level.SEVERE, msg, e);
            }
            finally {
                this.index.syncFinished(success);
            }
        }
    }
}
