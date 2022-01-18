// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.Transaction;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebean.Query;

public abstract class CallableQuery<T>
{
    protected final Query<T> query;
    protected final SpiEbeanServer server;
    protected final Transaction t;
    
    public CallableQuery(final SpiEbeanServer server, final Query<T> query, final Transaction t) {
        this.server = server;
        this.query = query;
        this.t = t;
    }
}
