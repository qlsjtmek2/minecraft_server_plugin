// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.Query;
import java.util.concurrent.FutureTask;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebean.FutureIds;
import java.util.List;

public class QueryFutureIds<T> extends BaseFuture<List<Object>> implements FutureIds<T>
{
    private final SpiQuery<T> query;
    
    public QueryFutureIds(final SpiQuery<T> query, final FutureTask<List<Object>> futureTask) {
        super(futureTask);
        this.query = query;
    }
    
    public Query<T> getQuery() {
        return this.query;
    }
    
    public List<Object> getPartialIds() {
        return this.query.getIdList();
    }
    
    public boolean cancel(final boolean mayInterruptIfRunning) {
        this.query.cancel();
        return super.cancel(mayInterruptIfRunning);
    }
}
