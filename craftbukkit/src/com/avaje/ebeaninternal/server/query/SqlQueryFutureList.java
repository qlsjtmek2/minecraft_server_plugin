// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import java.util.concurrent.FutureTask;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlFutureList;
import com.avaje.ebean.SqlRow;
import java.util.List;

public class SqlQueryFutureList extends BaseFuture<List<SqlRow>> implements SqlFutureList
{
    private final SqlQuery query;
    
    public SqlQueryFutureList(final SqlQuery query, final FutureTask<List<SqlRow>> futureTask) {
        super(futureTask);
        this.query = query;
    }
    
    public SqlQuery getQuery() {
        return this.query;
    }
    
    public boolean cancel(final boolean mayInterruptIfRunning) {
        this.query.cancel();
        return super.cancel(mayInterruptIfRunning);
    }
}
