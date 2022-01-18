// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.Transaction;
import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import java.util.List;
import java.util.concurrent.Callable;

public class CallableQueryList<T> extends CallableQuery<T> implements Callable<List<T>>
{
    public CallableQueryList(final SpiEbeanServer server, final Query<T> query, final Transaction t) {
        super(server, query, t);
    }
    
    public List<T> call() throws Exception {
        return this.server.findList(this.query, this.t);
    }
}
