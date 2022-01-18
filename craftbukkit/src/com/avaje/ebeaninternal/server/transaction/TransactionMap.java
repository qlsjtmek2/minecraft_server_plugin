// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction;

import javax.persistence.PersistenceException;
import com.avaje.ebeaninternal.api.SpiTransaction;
import java.util.HashMap;

public class TransactionMap
{
    HashMap<String, State> map;
    
    public TransactionMap() {
        this.map = new HashMap<String, State>();
    }
    
    public State getState(final String serverName) {
        State state = this.map.get(serverName);
        if (state == null) {
            state = new State();
            this.map.put(serverName, state);
        }
        return state;
    }
    
    public static class State
    {
        SpiTransaction transaction;
        
        public SpiTransaction get() {
            return this.transaction;
        }
        
        public void set(final SpiTransaction trans) {
            if (this.transaction != null && this.transaction.isActive()) {
                final String m = "The existing transaction is still active?";
                throw new PersistenceException(m);
            }
            this.transaction = trans;
        }
        
        public void commit() {
            this.transaction.commit();
            this.transaction = null;
        }
        
        public void rollback() {
            this.transaction.rollback();
            this.transaction = null;
        }
        
        public void end() {
            if (this.transaction != null) {
                this.transaction.end();
                this.transaction = null;
            }
        }
        
        public void replace(final SpiTransaction trans) {
            this.transaction = trans;
        }
    }
}
