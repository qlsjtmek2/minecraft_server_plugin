// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.api.SpiTransactionScopeManager;

public abstract class TransactionScopeManager implements SpiTransactionScopeManager
{
    protected final TransactionManager transactionManager;
    protected final String serverName;
    
    public TransactionScopeManager(final TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
        this.serverName = transactionManager.getServerName();
    }
    
    public abstract SpiTransaction get();
    
    public abstract void set(final SpiTransaction p0);
    
    public abstract void commit();
    
    public abstract void rollback();
    
    public abstract void end();
    
    public abstract void replace(final SpiTransaction p0);
}
