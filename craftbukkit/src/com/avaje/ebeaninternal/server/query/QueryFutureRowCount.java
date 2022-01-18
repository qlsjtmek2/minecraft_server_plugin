// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.util.concurrent.FutureTask;
import com.avaje.ebean.Query;
import com.avaje.ebean.FutureRowCount;

public class QueryFutureRowCount<T> extends BaseFuture<Integer> implements FutureRowCount<T>
{
    private final Query<T> query;
    
    public QueryFutureRowCount(final Query<T> query, final FutureTask<Integer> futureTask) {
        super(futureTask);
        this.query = query;
    }
    
    public Query<T> getQuery() {
        return this.query;
    }
    
    public boolean cancel(final boolean mayInterruptIfRunning) {
        this.query.cancel();
        return super.cancel(mayInterruptIfRunning);
    }
}
