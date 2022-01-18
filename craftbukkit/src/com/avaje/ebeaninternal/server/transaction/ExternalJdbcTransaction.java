// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import javax.persistence.RollbackException;
import javax.persistence.PersistenceException;
import com.avaje.ebean.LogLevel;
import java.sql.Connection;

public class ExternalJdbcTransaction extends JdbcTransaction
{
    public ExternalJdbcTransaction(final Connection connection) {
        super(null, true, LogLevel.NONE, connection, null);
    }
    
    public ExternalJdbcTransaction(final String id, final boolean explicit, final Connection connection, final TransactionManager manager) {
        super(id, explicit, manager.getTransactionLogLevel(), connection, manager);
    }
    
    public ExternalJdbcTransaction(final String id, final boolean explicit, final LogLevel logLevel, final Connection connection, final TransactionManager manager) {
        super(id, explicit, logLevel, connection, manager);
    }
    
    public void commit() throws RollbackException {
        throw new PersistenceException("This is an external transaction so must be committed externally");
    }
    
    public void end() throws PersistenceException {
        throw new PersistenceException("This is an external transaction so must be committed externally");
    }
    
    public void rollback() throws PersistenceException {
        throw new PersistenceException("This is an external transaction so must be rolled back externally");
    }
    
    public void rollback(final Throwable e) throws PersistenceException {
        throw new PersistenceException("This is an external transaction so must be rolled back externally");
    }
}
