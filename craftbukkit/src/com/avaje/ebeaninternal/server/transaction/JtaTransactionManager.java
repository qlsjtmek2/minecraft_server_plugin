// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import javax.transaction.HeuristicRollbackException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.NotSupportedException;
import javax.transaction.Synchronization;
import com.avaje.ebean.LogLevel;
import java.util.logging.Level;
import com.avaje.ebeaninternal.api.SpiTransaction;
import javax.transaction.UserTransaction;
import javax.naming.NamingException;
import javax.persistence.PersistenceException;
import javax.naming.InitialContext;
import javax.transaction.TransactionSynchronizationRegistry;
import javax.sql.DataSource;
import java.util.logging.Logger;
import com.avaje.ebean.config.ExternalTransactionManager;

public class JtaTransactionManager implements ExternalTransactionManager
{
    private static final Logger logger;
    private static final String EBEAN_TXN_RESOURCE = "EBEAN_TXN_RESOURCE";
    private DataSource dataSource;
    private TransactionManager transactionManager;
    private String serverName;
    
    public void setTransactionManager(final Object txnMgr) {
        this.transactionManager = (TransactionManager)txnMgr;
        this.dataSource = this.transactionManager.getDataSource();
        this.serverName = this.transactionManager.getServerName();
    }
    
    private TransactionSynchronizationRegistry getSyncRegistry() {
        try {
            final InitialContext ctx = new InitialContext();
            return (TransactionSynchronizationRegistry)ctx.lookup("java:comp/TransactionSynchronizationRegistry");
        }
        catch (NamingException e) {
            throw new PersistenceException(e);
        }
    }
    
    private UserTransaction getUserTransaction() {
        try {
            final InitialContext ctx = new InitialContext();
            return (UserTransaction)ctx.lookup("java:comp/UserTransaction");
        }
        catch (NamingException e) {
            return (UserTransaction)new DummyUserTransaction();
        }
    }
    
    public Object getCurrentTransaction() {
        final TransactionSynchronizationRegistry syncRegistry = this.getSyncRegistry();
        final SpiTransaction t = (SpiTransaction)syncRegistry.getResource((Object)"EBEAN_TXN_RESOURCE");
        if (t != null) {
            return t;
        }
        final SpiTransaction currentEbeanTransaction = DefaultTransactionThreadLocal.get(this.serverName);
        if (currentEbeanTransaction != null) {
            final String msg = "JTA Transaction - no current txn BUT using current Ebean one " + currentEbeanTransaction.getId();
            JtaTransactionManager.logger.log(Level.WARNING, msg);
            return currentEbeanTransaction;
        }
        final UserTransaction ut = this.getUserTransaction();
        if (ut == null) {
            if (JtaTransactionManager.logger.isLoggable(Level.FINE)) {
                JtaTransactionManager.logger.fine("JTA Transaction - no current txn");
            }
            return null;
        }
        final String txnId = String.valueOf(System.currentTimeMillis());
        final JtaTransaction newTrans = new JtaTransaction(txnId, true, LogLevel.NONE, ut, this.dataSource, this.transactionManager);
        final JtaTxnListener txnListener = this.createJtaTxnListener(newTrans);
        syncRegistry.putResource((Object)"EBEAN_TXN_RESOURCE", (Object)newTrans);
        syncRegistry.registerInterposedSynchronization((Synchronization)txnListener);
        DefaultTransactionThreadLocal.set(this.serverName, newTrans);
        return newTrans;
    }
    
    private JtaTxnListener createJtaTxnListener(final SpiTransaction t) {
        return new JtaTxnListener(this.transactionManager, t);
    }
    
    static {
        logger = Logger.getLogger(JtaTransactionManager.class.getName());
    }
    
    private static class DummyUserTransaction implements UserTransaction
    {
        public void begin() throws NotSupportedException, SystemException {
        }
        
        public void commit() throws RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {
        }
        
        public int getStatus() throws SystemException {
            return 0;
        }
        
        public void rollback() throws IllegalStateException, SecurityException, SystemException {
        }
        
        public void setRollbackOnly() throws IllegalStateException, SystemException {
        }
        
        public void setTransactionTimeout(final int seconds) throws SystemException {
        }
    }
    
    private static class JtaTxnListener implements Synchronization
    {
        private final TransactionManager transactionManager;
        private final SpiTransaction transaction;
        private final String serverName;
        
        private JtaTxnListener(final TransactionManager transactionManager, final SpiTransaction t) {
            this.transactionManager = transactionManager;
            this.transaction = t;
            this.serverName = transactionManager.getServerName();
        }
        
        public void beforeCompletion() {
        }
        
        public void afterCompletion(final int status) {
            switch (status) {
                case 3: {
                    if (JtaTransactionManager.logger.isLoggable(Level.FINE)) {
                        JtaTransactionManager.logger.fine("Jta Txn [" + this.transaction.getId() + "] committed");
                    }
                    this.transactionManager.notifyOfCommit(this.transaction);
                    DefaultTransactionThreadLocal.replace(this.serverName, null);
                    break;
                }
                case 4: {
                    if (JtaTransactionManager.logger.isLoggable(Level.FINE)) {
                        JtaTransactionManager.logger.fine("Jta Txn [" + this.transaction.getId() + "] rollback");
                    }
                    this.transactionManager.notifyOfRollback(this.transaction, null);
                    DefaultTransactionThreadLocal.replace(this.serverName, null);
                    break;
                }
                default: {
                    JtaTransactionManager.logger.fine("Jta Txn [" + this.transaction.getId() + "] status:" + status);
                    break;
                }
            }
        }
    }
}
