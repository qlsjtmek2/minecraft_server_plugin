// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import com.avaje.ebeaninternal.api.SpiTransaction;

final class TransWrapper
{
    final SpiTransaction transaction;
    private final boolean wasCreated;
    
    TransWrapper(final SpiTransaction t, final boolean created) {
        this.transaction = t;
        this.wasCreated = created;
    }
    
    void commitIfCreated() {
        if (this.wasCreated) {
            this.transaction.commit();
        }
    }
    
    void rollbackIfCreated() {
        if (this.wasCreated) {
            this.transaction.rollback();
        }
    }
    
    boolean wasCreated() {
        return this.wasCreated;
    }
}
