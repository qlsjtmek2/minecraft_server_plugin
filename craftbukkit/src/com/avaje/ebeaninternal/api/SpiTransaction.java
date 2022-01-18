// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import java.sql.Connection;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.server.persist.BatchControl;
import com.avaje.ebeaninternal.server.transaction.TransactionLogBuffer;
import com.avaje.ebeaninternal.server.lucene.LIndexUpdateFuture;
import com.avaje.ebean.Transaction;

public interface SpiTransaction extends Transaction
{
    boolean isLogSql();
    
    boolean isLogSummary();
    
    void logInternal(final String p0);
    
    void addIndexUpdateFuture(final LIndexUpdateFuture p0);
    
    TransactionLogBuffer getLogBuffer();
    
    void registerBean(final Integer p0);
    
    void unregisterBean(final Integer p0);
    
    boolean isRegisteredBean(final Integer p0);
    
    String getId();
    
    int getBatchSize();
    
    int depth(final int p0);
    
    boolean isExplicit();
    
    TransactionEvent getEvent();
    
    boolean isPersistCascade();
    
    boolean isBatchThisRequest();
    
    BatchControl getBatchControl();
    
    void setBatchControl(final BatchControl p0);
    
    PersistenceContext getPersistenceContext();
    
    void setPersistenceContext(final PersistenceContext p0);
    
    Connection getInternalConnection();
}
