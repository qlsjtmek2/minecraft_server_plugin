// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.Transaction;
import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import java.util.List;
import java.util.concurrent.Callable;

public class CallableQueryIds<T> extends CallableQuery<T> implements Callable<List<Object>>
{
    public CallableQueryIds(final SpiEbeanServer server, final Query<T> query, final Transaction t) {
        super(server, query, t);
    }
    
    public List<Object> call() throws Exception {
        return this.server.findIdsWithCopy(this.query, this.t);
    }
}
