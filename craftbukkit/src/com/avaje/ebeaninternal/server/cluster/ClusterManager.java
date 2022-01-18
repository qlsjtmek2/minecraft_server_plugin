// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.cluster;

import com.avaje.ebeaninternal.server.transaction.RemoteTransactionEvent;
import java.util.logging.Level;
import com.avaje.ebeaninternal.api.ClassUtil;
import com.avaje.ebeaninternal.server.cluster.socket.SocketClusterBroadcast;
import com.avaje.ebeaninternal.server.cluster.mcast.McastClusterManager;
import com.avaje.ebeaninternal.server.lucene.cluster.SLuceneClusterFactory;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebean.EbeanServer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class ClusterManager
{
    private static final Logger logger;
    private final ClusterBroadcast broadcast;
    private final ConcurrentHashMap<String, EbeanServer> serverMap;
    private LuceneClusterListener luceneListener;
    private LuceneClusterIndexSync luceneIndexSync;
    private boolean started;
    
    public ClusterManager() {
        this.serverMap = new ConcurrentHashMap<String, EbeanServer>();
        final String clusterType = GlobalProperties.get("ebean.cluster.type", null);
        if (clusterType == null || clusterType.trim().length() == 0) {
            this.broadcast = null;
        }
        else {
            final LuceneClusterFactory luceneFactory = new SLuceneClusterFactory();
            final int lucenePort = GlobalProperties.getInt("ebean.cluster.lucene.port", 9991);
            this.luceneListener = luceneFactory.createListener(this, lucenePort);
            final String masterHostPort = GlobalProperties.get("ebean.cluster.lucene.masterHostPort", null);
            (this.luceneIndexSync = luceneFactory.createIndexSync()).setMasterHost(masterHostPort);
            this.luceneIndexSync.setMode((masterHostPort == null) ? LuceneClusterIndexSync.Mode.MASTER_MODE : LuceneClusterIndexSync.Mode.SLAVE_MODE);
            ClusterManager.logger.info("... luceneListener using [" + lucenePort + "]");
            try {
                if ("mcast".equalsIgnoreCase(clusterType)) {
                    this.broadcast = new McastClusterManager();
                }
                else if ("socket".equalsIgnoreCase(clusterType)) {
                    this.broadcast = new SocketClusterBroadcast();
                }
                else {
                    ClusterManager.logger.info("Clustering using [" + clusterType + "]");
                    this.broadcast = (ClusterBroadcast)ClassUtil.newInstance(clusterType);
                }
            }
            catch (Exception e) {
                final String msg = "Error initialising ClusterManager type [" + clusterType + "]";
                ClusterManager.logger.log(Level.SEVERE, msg, e);
                throw new RuntimeException(e);
            }
        }
    }
    
    public void registerServer(final EbeanServer server) {
        synchronized (this.serverMap) {
            if (!this.started) {
                this.startup();
            }
            this.serverMap.put(server.getName(), server);
        }
    }
    
    public LuceneClusterIndexSync getLuceneClusterIndexSync() {
        return this.luceneIndexSync;
    }
    
    public EbeanServer getServer(final String name) {
        synchronized (this.serverMap) {
            return this.serverMap.get(name);
        }
    }
    
    private void startup() {
        this.started = true;
        if (this.broadcast != null) {
            this.broadcast.startup(this);
        }
        if (this.luceneListener != null) {
            this.luceneListener.startup();
        }
    }
    
    public boolean isClustering() {
        return this.broadcast != null;
    }
    
    public void broadcast(final RemoteTransactionEvent remoteTransEvent) {
        if (this.broadcast != null) {
            this.broadcast.broadcast(remoteTransEvent);
        }
    }
    
    public void shutdown() {
        if (this.luceneListener != null) {
            this.luceneListener.shutdown();
        }
        if (this.broadcast != null) {
            ClusterManager.logger.info("ClusterManager shutdown ");
            this.broadcast.shutdown();
        }
    }
    
    static {
        logger = Logger.getLogger(ClusterManager.class.getName());
    }
}
