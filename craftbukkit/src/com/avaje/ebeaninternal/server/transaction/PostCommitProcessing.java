// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import java.util.Set;
import com.avaje.ebeaninternal.api.TransactionEventBeans;
import java.util.logging.Level;
import java.util.Iterator;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import java.util.List;
import com.avaje.ebeaninternal.api.TransactionEvent;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import java.util.logging.Logger;

public final class PostCommitProcessing
{
    private static final Logger logger;
    private final ClusterManager clusterManager;
    private final LuceneIndexManager luceneIndexManager;
    private final SpiTransaction transaction;
    private final TransactionEvent event;
    private final String serverName;
    private final TransactionManager manager;
    private final List<PersistRequestBean<?>> persistBeanRequests;
    private final BeanPersistIdMap beanPersistIdMap;
    private final BeanDeltaMap beanDeltaMap;
    private final RemoteTransactionEvent remoteTransactionEvent;
    private final DeleteByIdMap deleteByIdMap;
    
    public PostCommitProcessing(final ClusterManager clusterManager, final LuceneIndexManager luceneIndexManager, final TransactionManager manager, final SpiTransaction transaction, final TransactionEvent event) {
        this.clusterManager = clusterManager;
        this.luceneIndexManager = luceneIndexManager;
        this.manager = manager;
        this.serverName = manager.getServerName();
        this.transaction = transaction;
        this.event = event;
        this.deleteByIdMap = event.getDeleteByIdMap();
        this.persistBeanRequests = this.createPersistBeanRequests();
        this.beanPersistIdMap = this.createBeanPersistIdMap();
        this.beanDeltaMap = new BeanDeltaMap(event.getBeanDeltas());
        this.remoteTransactionEvent = this.createRemoteTransactionEvent();
    }
    
    public void notifyLocalCacheIndex() {
        if (this.luceneIndexManager.isLuceneAvailable()) {
            this.luceneIndexManager.processEvent(this.remoteTransactionEvent, this.transaction);
        }
        this.processTableEvents(this.event.getEventTables());
        this.event.notifyCache();
    }
    
    private void processTableEvents(final TransactionEventTable tableEvents) {
        if (tableEvents != null && !tableEvents.isEmpty()) {
            final BeanDescriptorManager dm = this.manager.getBeanDescriptorManager();
            for (final TransactionEventTable.TableIUD tableIUD : tableEvents.values()) {
                dm.cacheNotify(tableIUD);
            }
        }
    }
    
    public void notifyCluster() {
        if (this.remoteTransactionEvent != null && !this.remoteTransactionEvent.isEmpty()) {
            if (this.manager.getClusterDebugLevel() > 0 || PostCommitProcessing.logger.isLoggable(Level.FINE)) {
                PostCommitProcessing.logger.info("Cluster Send: " + this.remoteTransactionEvent.toString());
            }
            this.clusterManager.broadcast(this.remoteTransactionEvent);
        }
    }
    
    public Runnable notifyPersistListeners() {
        return new Runnable() {
            public void run() {
                PostCommitProcessing.this.localPersistListenersNotify();
            }
        };
    }
    
    private void localPersistListenersNotify() {
        if (this.persistBeanRequests != null) {
            for (int i = 0; i < this.persistBeanRequests.size(); ++i) {
                this.persistBeanRequests.get(i).notifyLocalPersistListener();
            }
        }
    }
    
    private List<PersistRequestBean<?>> createPersistBeanRequests() {
        final TransactionEventBeans eventBeans = this.event.getEventBeans();
        if (eventBeans != null) {
            return eventBeans.getRequests();
        }
        return null;
    }
    
    private BeanPersistIdMap createBeanPersistIdMap() {
        if (this.persistBeanRequests == null) {
            return null;
        }
        final BeanPersistIdMap m = new BeanPersistIdMap();
        for (int i = 0; i < this.persistBeanRequests.size(); ++i) {
            this.persistBeanRequests.get(i).addToPersistMap(m);
        }
        return m;
    }
    
    private RemoteTransactionEvent createRemoteTransactionEvent() {
        if (!this.clusterManager.isClustering() && !this.luceneIndexManager.isLuceneAvailable()) {
            return null;
        }
        final RemoteTransactionEvent remoteTransactionEvent = new RemoteTransactionEvent(this.serverName);
        if (this.beanDeltaMap != null) {
            for (final BeanDeltaList deltaList : this.beanDeltaMap.deltaLists()) {
                remoteTransactionEvent.addBeanDeltaList(deltaList);
            }
        }
        if (this.beanPersistIdMap != null) {
            for (final BeanPersistIds beanPersist : this.beanPersistIdMap.values()) {
                remoteTransactionEvent.addBeanPersistIds(beanPersist);
            }
        }
        if (this.deleteByIdMap != null) {
            remoteTransactionEvent.setDeleteByIdMap(this.deleteByIdMap);
        }
        final TransactionEventTable eventTables = this.event.getEventTables();
        if (eventTables != null && !eventTables.isEmpty()) {
            for (final TransactionEventTable.TableIUD tableIUD : eventTables.values()) {
                remoteTransactionEvent.addTableIUD(tableIUD);
            }
        }
        final Set<IndexInvalidate> indexInvalidations = this.event.getIndexInvalidations();
        if (indexInvalidations != null) {
            for (final IndexInvalidate indexInvalidate : indexInvalidations) {
                remoteTransactionEvent.addIndexInvalidate(indexInvalidate);
            }
        }
        return remoteTransactionEvent;
    }
    
    static {
        logger = Logger.getLogger(PostCommitProcessing.class.getName());
    }
}
