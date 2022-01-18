// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebeaninternal.api.SpiTransaction;

public class DefaultTransactionScopeManager extends TransactionScopeManager
{
    public DefaultTransactionScopeManager(final TransactionManager transactionManager) {
        super(transactionManager);
    }
    
    public void commit() {
        DefaultTransactionThreadLocal.commit(this.serverName);
    }
    
    public void end() {
        DefaultTransactionThreadLocal.end(this.serverName);
    }
    
    public SpiTransaction get() {
        final SpiTransaction t = DefaultTransactionThreadLocal.get(this.serverName);
        if (t == null || !t.isActive()) {
            return null;
        }
        return t;
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
