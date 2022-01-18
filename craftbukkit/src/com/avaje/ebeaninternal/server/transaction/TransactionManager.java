// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import java.util.List;
import com.avaje.ebeaninternal.api.TransactionEvent;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import com.avaje.ebean.TxIsolation;
import com.avaje.ebeaninternal.api.SpiTransaction;
import java.sql.Connection;
import java.util.logging.Level;
import java.sql.SQLException;
import javax.persistence.PersistenceException;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebean.config.ServerConfig;
import java.util.concurrent.atomic.AtomicLong;
import com.avaje.ebeaninternal.server.cluster.ClusterManager;
import com.avaje.ebean.BackgroundExecutor;
import javax.sql.DataSource;
import com.avaje.ebean.LogLevel;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptorManager;
import com.avaje.ebeaninternal.server.lucene.LuceneIndexManager;
import java.util.logging.Logger;

public class TransactionManager
{
    private static final Logger logger;
    private final LuceneIndexManager luceneIndexManager;
    private final BeanDescriptorManager beanDescriptorManager;
    private LogLevel logLevel;
    private final TransactionLogManager transLogger;
    private final String prefix;
    private final String externalTransPrefix;
    private final DataSource dataSource;
    private final OnQueryOnly onQueryOnly;
    private final boolean defaultBatchMode;
    private final BackgroundExecutor backgroundExecutor;
    private final ClusterManager clusterManager;
    private final int commitDebugLevel;
    private final String serverName;
    private AtomicLong transactionCounter;
    private int clusterDebugLevel;
    
    public TransactionManager(final ClusterManager clusterManager, final LuceneIndexManager luceneIndexManager, final BackgroundExecutor backgroundExecutor, final ServerConfig config, final BeanDescriptorManager descMgr) {
        this.transactionCounter = new AtomicLong(1000L);
        this.beanDescriptorManager = descMgr;
        this.clusterManager = clusterManager;
        this.luceneIndexManager = luceneIndexManager;
        this.serverName = config.getName();
        this.logLevel = config.getLoggingLevel();
        this.transLogger = new TransactionLogManager(config);
        this.backgroundExecutor = backgroundExecutor;
        this.dataSource = config.getDataSource();
        this.commitDebugLevel = GlobalProperties.getInt("ebean.commit.debuglevel", 0);
        this.clusterDebugLevel = GlobalProperties.getInt("ebean.cluster.debuglevel", 0);
        this.defaultBatchMode = config.isPersistBatching();
        this.prefix = GlobalProperties.get("transaction.prefix", "");
        this.externalTransPrefix = GlobalProperties.get("transaction.prefix", "e");
        final String value = GlobalProperties.get("transaction.onqueryonly", "ROLLBACK").toUpperCase().trim();
        this.onQueryOnly = this.getOnQueryOnly(value, this.dataSource);
    }
    
    public void shutdown() {
        this.transLogger.shutdown();
    }
    
    public BeanDescriptorManager getBeanDescriptorManager() {
        return this.beanDescriptorManager;
    }
    
    public LogLevel getTransactionLogLevel() {
        return this.logLevel;
    }
    
    public void setTransactionLogLevel(final LogLevel logLevel) {
        this.logLevel = logLevel;
    }
    
    private OnQueryOnly getOnQueryOnly(final String onQueryOnly, final DataSource ds) {
        if (onQueryOnly.equals("COMMIT")) {
            return OnQueryOnly.COMMIT;
        }
        if (!onQueryOnly.startsWith("CLOSE")) {
            return OnQueryOnly.ROLLBACK;
        }
        if (!this.isReadCommitedIsolation(ds)) {
            final String m = "transaction.queryonlyclose is true but the transaction Isolation Level is not READ_COMMITTED";
            throw new PersistenceException(m);
        }
        return OnQueryOnly.CLOSE_ON_READCOMMITTED;
    }
    
    private boolean isReadCommitedIsolation(final DataSource ds) {
        Connection c = null;
        try {
            c = ds.getConnection();
            final int isolationLevel = c.getTransactionIsolation();
            return isolationLevel == 2;
        }
        catch (SQLException ex) {
            final String m = "Errored trying to determine the default Isolation Level";
            throw new PersistenceException(m, ex);
        }
        finally {
            try {
                if (c != null) {
                    c.close();
                }
            }
            catch (SQLException ex2) {
                TransactionManager.logger.log(Level.SEVERE, "closing connection", ex2);
            }
        }
    }
    
    public String getServerName() {
        return this.serverName;
    }
    
    public DataSource getDataSource() {
        return this.dataSource;
    }
    
    public int getClusterDebugLevel() {
        return this.clusterDebugLevel;
    }
    
    public void setClusterDebugLevel(final int clusterDebugLevel) {
        this.clusterDebugLevel = clusterDebugLevel;
    }
    
    public OnQueryOnly getOnQueryOnly() {
        return this.onQueryOnly;
    }
    
    public TransactionLogManager getLogger() {
        return this.transLogger;
    }
    
    public void log(final TransactionLogBuffer logBuffer) {
        if (!logBuffer.isEmpty()) {
            this.transLogger.log(logBuffer);
        }
    }
    
    public SpiTransaction wrapExternalConnection(final Connection c) {
        return this.wrapExternalConnection(this.externalTransPrefix + c.hashCode(), c);
    }
    
    public SpiTransaction wrapExternalConnection(final String id, final Connection c) {
        final ExternalJdbcTransaction t = new ExternalJdbcTransaction(id, true, this.logLevel, c, this);
        if (this.defaultBatchMode) {
            t.setBatchMode(true);
        }
        return t;
    }
    
    public SpiTransaction createTransaction(final boolean explicit, final int isolationLevel) {
        try {
            final long id = this.transactionCounter.incrementAndGet();
            final Connection c = this.dataSource.getConnection();
            final JdbcTransaction t = new JdbcTransaction(this.prefix + id, explicit, this.logLevel, c, this);
            if (this.defaultBatchMode) {
                t.setBatchMode(true);
            }
            if (isolationLevel > -1) {
                c.setTransactionIsolation(isolationLevel);
            }
            if (this.commitDebugLevel >= 3) {
                String msg = "Transaction [" + t.getId() + "] begin";
                if (isolationLevel > -1) {
                    final TxIsolation txi = TxIsolation.fromLevel(isolationLevel);
                    msg = msg + " isolationLevel[" + txi + "]";
                }
                TransactionManager.logger.info(msg);
            }
            return t;
        }
        catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
    }
    
    public SpiTransaction createQueryTransaction() {
        try {
            final long id = this.transactionCounter.incrementAndGet();
            final Connection c = this.dataSource.getConnection();
            final JdbcTransaction t = new JdbcTransaction(this.prefix + id, false, this.logLevel, c, this);
            if (this.defaultBatchMode) {
                t.setBatchMode(true);
            }
            if (this.commitDebugLevel >= 3) {
                TransactionManager.logger.info("Transaction [" + t.getId() + "] begin - queryOnly");
            }
            return t;
        }
        catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
    }
    
    public void notifyOfRollback(final SpiTransaction transaction, final Throwable cause) {
        try {
            if (transaction.isLogSummary() || this.commitDebugLevel >= 1) {
                String msg = "Rollback";
                if (cause != null) {
                    msg = msg + " error: " + this.formatThrowable(cause);
                }
                if (transaction.isLogSummary()) {
                    transaction.logInternal(msg);
                }
                if (this.commitDebugLevel >= 1) {
                    TransactionManager.logger.info("Transaction [" + transaction.getId() + "] " + msg);
                }
            }
            this.log(transaction.getLogBuffer());
        }
        catch (Exception ex) {
            final String m = "Potentially Transaction Log incomplete due to error:";
            TransactionManager.logger.log(Level.SEVERE, m, ex);
        }
    }
    
    public void notifyOfQueryOnly(final boolean onCommit, final SpiTransaction transaction, final Throwable cause) {
        try {
            if (this.commitDebugLevel >= 2) {
                String msg;
                if (onCommit) {
                    msg = "Commit queryOnly";
                }
                else {
                    msg = "Rollback queryOnly";
                    if (cause != null) {
                        msg = msg + " error: " + this.formatThrowable(cause);
                    }
                }
                if (transaction.isLogSummary()) {
                    transaction.logInternal(msg);
                }
                TransactionManager.logger.info("Transaction [" + transaction.getId() + "] " + msg);
            }
            this.log(transaction.getLogBuffer());
        }
        catch (Exception ex) {
            final String m = "Potentially Transaction Log incomplete due to error:";
            TransactionManager.logger.log(Level.SEVERE, m, ex);
        }
    }
    
    private String formatThrowable(final Throwable e) {
        if (e == null) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        this.formatThrowable(e, sb);
        return sb.toString();
    }
    
    private void formatThrowable(final Throwable e, final StringBuilder sb) {
        sb.append(e.toString());
        final StackTraceElement[] stackTrace = e.getStackTrace();
        if (stackTrace.length > 0) {
            sb.append(" stack0: ");
            sb.append(stackTrace[0]);
        }
        final Throwable cause = e.getCause();
        if (cause != null) {
            sb.append(" cause: ");
            this.formatThrowable(cause, sb);
        }
    }
    
    public void notifyOfCommit(final SpiTransaction transaction) {
        try {
            this.log(transaction.getLogBuffer());
            final PostCommitProcessing postCommit = new PostCommitProcessing(this.clusterManager, this.luceneIndexManager, this, transaction, transaction.getEvent());
            postCommit.notifyLocalCacheIndex();
            postCommit.notifyCluster();
            this.backgroundExecutor.execute(postCommit.notifyPersistListeners());
            if (this.commitDebugLevel >= 1) {
                TransactionManager.logger.info("Transaction [" + transaction.getId() + "] commit");
            }
        }
        catch (Exception ex) {
            final String m = "NotifyOfCommit failed. Cache/Lucene potentially not notified.";
            TransactionManager.logger.log(Level.SEVERE, m, ex);
        }
    }
    
    public void externalModification(final TransactionEventTable tableEvents) {
        final TransactionEvent event = new TransactionEvent();
        event.add(tableEvents);
        final PostCommitProcessing postCommit = new PostCommitProcessing(this.clusterManager, this.luceneIndexManager, this, null, event);
        postCommit.notifyLocalCacheIndex();
        this.backgroundExecutor.execute(postCommit.notifyPersistListeners());
    }
    
    public void remoteTransactionEvent(final RemoteTransactionEvent remoteEvent) {
        if (this.clusterDebugLevel > 0 || TransactionManager.logger.isLoggable(Level.FINE)) {
            TransactionManager.logger.info("Cluster Received: " + remoteEvent.toString());
        }
        this.luceneIndexManager.processEvent(remoteEvent, null);
        final List<TransactionEventTable.TableIUD> tableIUDList = remoteEvent.getTableIUDList();
        if (tableIUDList != null) {
            for (int i = 0; i < tableIUDList.size(); ++i) {
                final TransactionEventTable.TableIUD tableIUD = tableIUDList.get(i);
                this.beanDescriptorManager.cacheNotify(tableIUD);
            }
        }
        final List<BeanPersistIds> beanPersistList = remoteEvent.getBeanPersistList();
        if (beanPersistList != null) {
            for (int j = 0; j < beanPersistList.size(); ++j) {
                final BeanPersistIds beanPersist = beanPersistList.get(j);
                beanPersist.notifyCacheAndListener();
            }
        }
        final List<IndexEvent> indexEventList = remoteEvent.getIndexEventList();
        if (indexEventList != null) {
            for (int k = 0; k < indexEventList.size(); ++k) {
                final IndexEvent indexEvent = indexEventList.get(k);
                this.luceneIndexManager.processEvent(indexEvent);
            }
        }
    }
    
    static {
        logger = Logger.getLogger(TransactionManager.class.getName());
    }
    
    public enum OnQueryOnly
    {
        ROLLBACK, 
        CLOSE_ON_READCOMMITTED, 
        COMMIT;
    }
}
