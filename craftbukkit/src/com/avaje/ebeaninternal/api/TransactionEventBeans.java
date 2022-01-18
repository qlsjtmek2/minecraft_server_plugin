// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.api;

import java.util.List;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import java.util.ArrayList;

public class TransactionEventBeans
{
    ArrayList<PersistRequestBean<?>> requests;
    
    public TransactionEventBeans() {
        this.requests = new ArrayList<PersistRequestBean<?>>();
    }
    
    public List<PersistRequestBean<?>> getRequests() {
        return this.requests;
    }
    
    public void add(final PersistRequestBean<?> request) {
        this.requests.add(request);
    }
    
    public void notifyCache() {
        for (int i = 0; i < this.requests.size(); ++i) {
            this.requests.get(i).notifyCache();
        }
    }
}
