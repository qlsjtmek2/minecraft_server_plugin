// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebean.config.ExternalTransactionManager;

public class ExternalTransactionScopeManager extends TransactionScopeManager
{
    final ExternalTransactionManager externalManager;
    
    public ExternalTransactionScopeManager(final TransactionManager transactionManager, final ExternalTransactionManager externalManager) {
        super(transactionManager);
        this.externalManager = externalManager;
    }
    
    public void commit() {
        DefaultTransactionThreadLocal.commit(this.serverName);
    }
    
    public void end() {
        DefaultTransactionThreadLocal.end(this.serverName);
    }
    
    public SpiTransaction get() {
        return (SpiTransaction)this.externalManager.getCurrentTransaction();
    }
    
    public void replace(final SpiTransaction trans) {
        DefaultTransactionThreadLocal.replace(this.serverName, trans);
    }
    
    public void rollback() {
        DefaultTransactionThreadLocal.rollback(this.serverName);
    }
    
    public void set(final SpiTransaction trans) {
        DefaultTransactionThreadLocal.set(this.serverName, trans);
    }
}
