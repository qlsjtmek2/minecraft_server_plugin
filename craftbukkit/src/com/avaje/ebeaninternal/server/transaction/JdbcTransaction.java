// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import javax.persistence.RollbackException;
import java.util.logging.Level;
import java.sql.SQLException;
import com.avaje.ebean.config.lucene.IndexUpdateFuture;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import com.avaje.ebeaninternal.server.lucene.PersistenceLuceneException;
import java.util.ArrayList;
import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.server.lucene.LIndexUpdateFuture;
import java.util.List;
import java.util.HashSet;
import com.avaje.ebean.LogLevel;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.api.TransactionEvent;
import com.avaje.ebeaninternal.server.persist.BatchControl;
import java.sql.Connection;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.api.SpiTransaction;

public class JdbcTransaction implements SpiTransaction
{
    private static final Logger logger;
    private static final String illegalStateMessage = "Transaction is Inactive";
    protected final TransactionManager manager;
    final String id;
    final boolean explicit;
    final TransactionManager.OnQueryOnly onQueryOnly;
    boolean active;
    Connection connection;
    BatchControl batchControl;
    TransactionEvent event;
    PersistenceContext persistenceContext;
    boolean persistCascade;
    boolean queryOnly;
    boolean localReadOnly;
    LogLevel logLevel;
    boolean batchMode;
    int batchSize;
    boolean batchFlushOnQuery;
    Boolean batchGetGeneratedKeys;
    Boolean batchFlushOnMixed;
    int depth;
    HashSet<Integer> persistingBeans;
    TransactionLogBuffer logBuffer;
    List<LIndexUpdateFuture> indexUpdateFutures;
    
    public JdbcTransaction(final String id, final boolean explicit, final LogLevel logLevel, final Connection connection, final TransactionManager manager) {
        this.persistCascade = true;
        this.queryOnly = true;
        this.batchSize = -1;
        this.batchFlushOnQuery = true;
        this.depth = 0;
        try {
            this.active = true;
            this.id = id;
            this.explicit = explicit;
            this.logLevel = logLevel;
            this.manager = manager;
            this.connection = connection;
            this.onQueryOnly = ((manager == null) ? TransactionManager.OnQueryOnly.ROLLBACK : manager.getOnQueryOnly());
            this.persistenceContext = new DefaultPersistenceContext();
            this.logBuffer = new TransactionLogBuffer(50, id);
        }
        catch (Exception e) {
            throw new PersistenceException(e);
        }
    }
    
    public String toString() {
        return "Trans[" + this.id + "]";
    }
    
    public void addIndexUpdateFuture(final LIndexUpdateFuture future) {
        if (this.indexUpdateFutures == null) {
            this.indexUpdateFutures = new ArrayList<LIndexUpdateFuture>();
        }
        this.indexUpdateFutures.add(future);
    }
    
    public void waitForIndexUpdates() {
        if (this.indexUpdateFutures != null) {
            try {
                for (final IndexUpdateFuture f : this.indexUpdateFutures) {
                    f.get();
                }
            }
            catch (InterruptedException e) {
                throw new PersistenceLuceneException(e);
            }
            catch (ExecutionException e2) {
                throw new PersistenceLuceneException(e2);
            }
        }
    }
    
    public void registerBean(final Integer persistingBean) {
        if (this.persistingBeans == null) {
            this.persistingBeans = new HashSet<Integer>();
        }
        this.persistingBeans.add(persistingBean);
    }
    
    public void unregisterBean(final Integer persistedBean) {
        if (this.persistingBeans != null) {
            this.persistingBeans.remove(persistedBean);
        }
    }
    
    public boolean isRegisteredBean(final Integer persistingBean) {
        return this.persistingBeans != null && this.persistingBeans.contains(persistingBean);
    }
    
    public int depth(final int diff) {
        return this.depth += diff;
    }
    
    public boolean isReadOnly() {
        if (!this.isActive()) {
            throw new IllegalStateException("Transaction is Inactive");
        }
        try {
            return this.connection.isReadOnly();
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    public void setReadOnly(final boolean readOnly) {
        if (!this.isActive()) {
            throw new IllegalStateException("Transaction is Inactive");
        }
        try {
            this.localReadOnly = readOnly;
            this.connection.setReadOnly(readOnly);
        }
        catch (SQLException e) {
            throw new PersistenceException(e);
        }
    }
    
    public void setBatchMode(final boolean batchMode) {
        if (!this.isActive()) {
            throw new IllegalStateException("Transaction is Inactive");
        }
        this.batchMode = batchMode;
    }
    
    public void setBatchGetGeneratedKeys(final boolean getGeneratedKeys) {
        this.batchGetGeneratedKeys = getGeneratedKeys;
        if (this.batchControl != null) {
            this.batchControl.setGetGeneratedKeys(getGeneratedKeys);
        }
    }
    
    public void setBatchFlushOnMixed(final boolean batchFlushOnMixed) {
        this.batchFlushOnMixed = batchFlushOnMixed;
        if (this.batchControl != null) {
            this.batchControl.setBatchFlushOnMixed(batchFlushOnMixed);
        }
    }
    
    public int getBatchSize() {
        return this.batchSize;
    }
    
    public void setBatchSize(final int batchSize) {
        this.batchSize = batchSize;
        if (this.batchControl != null) {
            this.batchControl.setBatchSize(batchSize);
        }
    }
    
    public boolean isBatchFlushOnQuery() {
        return this.batchFlushOnQuery;
    }
    
    public void setBatchFlushOnQuery(final boolean batchFlushOnQuery) {
        this.batchFlushOnQuery = batchFlushOnQuery;
    }
    
    public boolean isBatchThisRequest() {
        return (this.explicit || this.depth > 0) && this.batchMode;
    }
    
    public BatchControl getBatchControl() {
        return this.batchControl;
    }
    
    public void setBatchControl(final BatchControl batchControl) {
        this.queryOnly = false;
        this.batchControl = batchControl;
        if (this.batchGetGeneratedKeys != null) {
            batchControl.setGetGeneratedKeys(this.batchGetGeneratedKeys);
        }
        if (this.batchSize != -1) {
            batchControl.setBatchSize(this.batchSize);
        }
        if (this.batchFlushOnMixed != null) {
            batchControl.setBatchFlushOnMixed(this.batchFlushOnMixed);
        }
    }
    
    public void flushBatch() {
        if (!this.isActive()) {
            throw new IllegalStateException("Transaction is Inactive");
        }
        if (this.batchControl != null) {
            this.batchControl.flush();
        }
    }
    
    public void batchFlush() {
        this.flushBatch();
    }
    
    public PersistenceContext getPersistenceContext() {
        return this.persistenceContext;
    }
    
    public void setPersistenceContext(final PersistenceContext context) {
        if (!this.isActive()) {
            throw new IllegalStateException("Transaction is Inactive");
        }
        this.persistenceContext = context;
    }
    
    public TransactionEvent getEvent() {
        this.queryOnly = false;
        if (this.event == null) {
            this.event = new TransactionEvent();
        }
        return this.event;
    }
    
    public void setLoggingOn(final boolean loggingOn) {
        if (loggingOn) {
            this.logLevel = LogLevel.SQL;
        }
        else {
            this.logLevel = LogLevel.NONE;
        }
    }
    
    public boolean isExplicit() {
        return this.explicit;
    }
    
    public boolean isLogSql() {
        return this.logLevel.ordinal() >= LogLevel.SQL.ordinal();
    }
    
    public boolean isLogSummary() {
        return this.logLevel.ordinal() >= LogLevel.SUMMARY.ordinal();
    }
    
    public LogLevel getLogLevel() {
        return this.logLevel;
    }
    
    public void setLogLevel(final LogLevel logLevel) {
        this.logLevel = logLevel;
    }
    
    public void log(final String msg) {
        if (this.isLogSummary()) {
            this.logInternal(msg);
        }
    }
    
    public void logInternal(final String msg) {
        if (this.manager != null && this.logBuffer.add(msg)) {
            this.manager.log(this.logBuffer);
            this.logBuffer = this.logBuffer.newBuffer();
        }
    }
    
    public String getId() {
        return this.id;
    }
    
    public Connection getInternalConnection() {
        if (!this.isActive()) {
            throw new IllegalStateException("Transaction is Inactive");
        }
        return this.connection;
    }
    
    public Connection getConnection() {
        this.queryOnly = false;
        return this.getInternalConnection();
    }
    
    protected void deactivate() {
        try {
            if (this.localReadOnly) {
                this.connection.setReadOnly(false);
            }
        }
        catch (SQLException e) {
            JdbcTransaction.logger.log(Level.SEVERE, "Error setting to readOnly?", e);
        }
        try {
            this.connection.close();
        }
        catch (Exception ex) {
            JdbcTransaction.logger.log(Level.SEVERE, "Error closing connection", ex);
        }
        this.connection = null;
        this.active = false;
    }
    
    public TransactionLogBuffer getLogBuffer() {
        return this.logBuffer;
    }
    
    protected void notifyCommit() {
        if (this.manager == null) {
            return;
        }
        if (this.queryOnly) {
            this.manager.notifyOfQueryOnly(true, this, null);
        }
        else {
            this.manager.notifyOfCommit(this);
        }
    }
    
    private void commitQueryOnly() {
        try {
            switch (this.onQueryOnly) {
                case ROLLBACK: {
                    this.connection.rollback();
                    break;
                }
                case COMMIT: {
                    this.connection.commit();
                    break;
                }
                case CLOSE_ON_READCOMMITTED: {
                    break;
                }
                default: {
                    this.connection.rollback();
                    break;
                }
            }
        }
        catch (SQLException e) {
            final String m = "Error when ending a query only transaction via " + this.onQueryOnly;
            JdbcTransaction.logger.log(Level.SEVERE, m, e);
        }
    }
    
    public void commit() throws RollbackException {
        if (!this.isActive()) {
            throw new IllegalStateException("Transaction is Inactive");
        }
        try {
            if (this.queryOnly) {
                this.commitQueryOnly();
            }
            else {
                if (this.batchControl != null && !this.batchControl.isEmpty()) {
                    this.batchControl.flush();
                }
                this.connection.commit();
            }
            this.deactivate();
            this.notifyCommit();
        }
        catch (Exception e) {
            throw new RollbackException(e);
        }
    }
    
    protected void notifyRollback(final Throwable cause) {
        if (this.manager == null) {
            return;
        }
        if (this.queryOnly) {
            this.manager.notifyOfQueryOnly(false, this, cause);
        }
        else {
            this.manager.notifyOfRollback(this, cause);
        }
    }
    
    public void rollback() throws PersistenceException {
        this.rollback(null);
    }
    
    public void rollback(final Throwable cause) throws PersistenceException {
        if (!this.isActive()) {
            throw new IllegalStateException("Transaction is Inactive");
        }
        try {
            this.connection.rollback();
            this.deactivate();
            this.notifyRollback(cause);
        }
        catch (Exception ex) {
            throw new PersistenceException(ex);
        }
    }
    
    public void end() throws PersistenceException {
        if (this.isActive()) {
            this.rollback();
        }
    }
    
    public boolean isActive() {
        return this.active;
    }
    
    public boolean isPersistCascade() {
        return this.persistCascade;
    }
    
    public void setPersistCascade(final boolean persistCascade) {
        this.persistCascade = persistCascade;
    }
    
    public void addModification(final String tableName, final boolean inserts, final boolean updates, final boolean deletes) {
        this.getEvent().add(tableName, inserts, updates, deletes);
    }
    
    public final TransactionManager getTransactionManger() {
        return this.manager;
    }
    
    static {
        logger = Logger.getLogger(JdbcTransaction.class.getName());
    }
}
