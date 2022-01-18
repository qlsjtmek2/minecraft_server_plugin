// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.Transaction;
import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import java.util.concurrent.Callable;

public class CallableQueryRowCount<T> extends CallableQuery<T> implements Callable<Integer>
{
    public CallableQueryRowCount(final SpiEbeanServer server, final Query<T> query, final Transaction t) {
        super(server, query, t);
    }
    
    public Integer call() throws Exception {
        return this.server.findRowCountWithCopy(this.query, this.t);
    }
}
