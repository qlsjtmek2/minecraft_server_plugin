// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.util.concurrent.FutureTask;
import com.avaje.ebean.Query;
import com.avaje.ebean.FutureList;
import java.util.List;

public class QueryFutureList<T> extends BaseFuture<List<T>> implements FutureList<T>
{
    private final Query<T> query;
    
    public QueryFutureList(final Query<T> query, final FutureTask<List<T>> futureTask) {
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
