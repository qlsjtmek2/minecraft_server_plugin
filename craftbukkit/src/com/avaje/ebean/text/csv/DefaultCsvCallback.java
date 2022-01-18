// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.text.csv;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.Transaction;
import java.util.logging.Logger;

public class DefaultCsvCallback<T> implements CsvCallback<T>
{
    private static final Logger logger;
    protected Transaction transaction;
    protected boolean createdTransaction;
    protected EbeanServer server;
    protected int logInfoFrequency;
    protected int persistBatchSize;
    protected long startTime;
    protected long exeTime;
    
    public DefaultCsvCallback() {
        this(30, 1000);
    }
    
    public DefaultCsvCallback(final int persistBatchSize, final int logInfoFrequency) {
        this.persistBatchSize = persistBatchSize;
        this.logInfoFrequency = logInfoFrequency;
    }
    
    public void begin(final EbeanServer server) {
        this.server = server;
        this.startTime = System.currentTimeMillis();
        this.initTransactionIfRequired();
    }
    
    public void readHeader(final String[] line) {
    }
    
    public boolean processLine(final int row, final String[] line) {
        return true;
    }
    
    public void processBean(final int row, final String[] line, final T bean) {
        this.server.save(bean, this.transaction);
        if (this.logInfoFrequency > 0 && row % this.logInfoFrequency == 0) {
            DefaultCsvCallback.logger.info("processed " + row + " rows");
        }
    }
    
    public void end(final int row) {
        this.commitTransactionIfCreated();
        this.exeTime = System.currentTimeMillis() - this.startTime;
        DefaultCsvCallback.logger.info("Csv finished, rows[" + row + "] exeMillis[" + this.exeTime + "]");
    }
    
    public void endWithError(final int row, final Exception e) {
        this.rollbackTransactionIfCreated(e);
    }
    
    protected void initTransactionIfRequired() {
        this.transaction = this.server.currentTransaction();
        if (this.transaction == null || !this.transaction.isActive()) {
            this.transaction = this.server.beginTransaction();
            this.createdTransaction = true;
            if (this.persistBatchSize > 1) {
                DefaultCsvCallback.logger.info("Creating transaction, batchSize[" + this.persistBatchSize + "]");
                this.transaction.setBatchMode(true);
                this.transaction.setBatchSize(this.persistBatchSize);
            }
            else {
                this.transaction.setBatchMode(false);
                DefaultCsvCallback.logger.info("Creating transaction with no JDBC batching");
            }
        }
    }
    
    protected void commitTransactionIfCreated() {
        if (this.createdTransaction) {
            this.transaction.commit();
            DefaultCsvCallback.logger.info("Committed transaction");
        }
    }
    
    protected void rollbackTransactionIfCreated(final Throwable e) {
        if (this.createdTransaction) {
            this.transaction.rollback(e);
            DefaultCsvCallback.logger.info("Rolled back transaction");
        }
    }
    
    static {
        logger = Logger.getLogger(DefaultCsvCallback.class.getName());
    }
}
