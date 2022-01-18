// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import java.sql.SQLException;
import javax.persistence.PersistenceException;
import java.sql.Connection;
import com.avaje.ebean.LogLevel;
import javax.sql.DataSource;
import javax.transaction.UserTransaction;

public class JtaTransaction extends JdbcTransaction
{
    private UserTransaction userTransaction;
    private DataSource dataSource;
    private boolean commmitted;
    private boolean newTransaction;
    
    public JtaTransaction(final String id, final boolean explicit, final LogLevel logLevel, final UserTransaction utx, final DataSource ds, final TransactionManager manager) {
        super(id, explicit, logLevel, null, manager);
        this.commmitted = false;
        this.newTransaction = false;
        this.userTransaction = utx;
        this.dataSource = ds;
        try {
            this.newTransaction = (this.userTransaction.getStatus() == 6);
            if (this.newTransaction) {
                this.userTransaction.begin();
            }
        }
        catch (Exception e) {
            throw new PersistenceException(e);
        }
        try {
            this.connection = this.dataSource.getConnection();
            if (this.connection == null) {
                throw new PersistenceException("The DataSource returned a null connection.");
            }
            if (this.connection.getAutoCommit()) {
                this.connection.setAutoCommit(false);
            }
        }
        catch (SQLException e2) {
            throw new PersistenceException(e2);
        }
    }
    
    public void commit() {
        if (this.commmitted) {
            throw new PersistenceException("This transaction has already been committed.");
        }
        try {
            try {
                if (this.newTransaction) {
                    this.userTransaction.commit();
                }
                this.notifyCommit();
            }
            finally {
                this.close();
            }
        }
        catch (Exception e) {
            throw new PersistenceException(e);
        }
        this.commmitted = true;
    }
    
    public void rollback() {
        this.rollback(null);
    }
    
    public void rollback(final Throwable e) {
        if (!this.commmitted) {
            try {
                try {
                    if (this.userTransaction != null) {
                        if (this.newTransaction) {
                            this.userTransaction.rollback();
                        }
                        else {
                            this.userTransaction.setRollbackOnly();
                        }
                    }
                    this.notifyRollback(e);
                }
                finally {
                    this.close();
                }
            }
            catch (Exception ex) {
                throw new PersistenceException(ex);
            }
        }
    }
    
    private void close() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
            this.connection = null;
        }
    }
}
