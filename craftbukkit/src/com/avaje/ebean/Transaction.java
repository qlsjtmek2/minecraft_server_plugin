// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.sql.Connection;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

public interface Transaction
{
    public static final int READ_COMMITTED = 2;
    public static final int READ_UNCOMMITTED = 1;
    public static final int REPEATABLE_READ = 4;
    public static final int SERIALIZABLE = 8;
    
    void waitForIndexUpdates();
    
    boolean isReadOnly();
    
    void setReadOnly(final boolean p0);
    
    void log(final String p0);
    
    void setLogLevel(final LogLevel p0);
    
    LogLevel getLogLevel();
    
    void setLoggingOn(final boolean p0);
    
    void commit() throws RollbackException;
    
    void rollback() throws PersistenceException;
    
    void rollback(final Throwable p0) throws PersistenceException;
    
    void end() throws PersistenceException;
    
    boolean isActive();
    
    void setPersistCascade(final boolean p0);
    
    void setBatchMode(final boolean p0);
    
    void setBatchSize(final int p0);
    
    void setBatchGetGeneratedKeys(final boolean p0);
    
    void setBatchFlushOnMixed(final boolean p0);
    
    void setBatchFlushOnQuery(final boolean p0);
    
    boolean isBatchFlushOnQuery();
    
    void flushBatch() throws PersistenceException, OptimisticLockException;
    
    void batchFlush() throws PersistenceException, OptimisticLockException;
    
    Connection getConnection();
    
    void addModification(final String p0, final boolean p1, final boolean p2, final boolean p3);
}
