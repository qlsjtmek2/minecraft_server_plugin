// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.query;

import com.avaje.ebean.Transaction;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import java.util.List;
import java.util.concurrent.Callable;

public class CallableSqlQueryList implements Callable<List<SqlRow>>
{
    private final SqlQuery query;
    private final EbeanServer server;
    private final Transaction t;
    
    public CallableSqlQueryList(final EbeanServer server, final SqlQuery query, final Transaction t) {
        this.server = server;
        this.query = query;
        this.t = t;
    }
    
    public List<SqlRow> call() throws Exception {
        return this.server.findList(this.query, this.t);
    }
}
