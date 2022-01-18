// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.EbeanServer;
import java.util.Map;
import java.util.Set;
import com.avaje.ebean.SqlRow;
import java.util.List;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.Query;
import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.api.SpiEbeanServer;
import com.avaje.ebeaninternal.api.SpiSqlQuery;

public final class RelationalQueryRequest
{
    private final SpiSqlQuery query;
    private final RelationalQueryEngine queryEngine;
    private final SpiEbeanServer ebeanServer;
    private SpiTransaction trans;
    private boolean createdTransaction;
    private Query.Type queryType;
    
    public RelationalQueryRequest(final SpiEbeanServer server, final RelationalQueryEngine engine, final SqlQuery q, final Transaction t) {
        this.ebeanServer = server;
        this.queryEngine = engine;
        this.query = (SpiSqlQuery)q;
        this.trans = (SpiTransaction)t;
    }
    
    public void rollbackTransIfRequired() {
        if (this.createdTransaction) {
            this.trans.rollback();
        }
    }
    
    public void initTransIfRequired() {
        if (this.trans == null) {
            this.trans = this.ebeanServer.getCurrentServerTransaction();
            if (this.trans == null || !this.trans.isActive()) {
                this.trans = this.ebeanServer.createServerTransaction(false, -1);
                this.createdTransaction = true;
            }
        }
    }
    
    public void endTransIfRequired() {
        if (this.createdTransaction) {
            this.trans.rollback();
        }
    }
    
    public List<SqlRow> findList() {
        this.queryType = Query.Type.LIST;
        return (List<SqlRow>)this.queryEngine.findMany(this);
    }
    
    public Set<SqlRow> findSet() {
        this.queryType = Query.Type.SET;
        return (Set<SqlRow>)this.queryEngine.findMany(this);
    }
    
    public Map<?, SqlRow> findMap() {
        this.queryType = Query.Type.MAP;
        return (Map<?, SqlRow>)this.queryEngine.findMany(this);
    }
    
    public SpiSqlQuery getQuery() {
        return this.query;
    }
    
    public Query.Type getQueryType() {
        return this.queryType;
    }
    
    public EbeanServer getEbeanServer() {
        return this.ebeanServer;
    }
    
    public SpiTransaction getTransaction() {
        return this.trans;
    }
    
    public boolean isLogSql() {
        return this.trans.isLogSql();
    }
    
    public boolean isLogSummary() {
        return this.trans.isLogSummary();
    }
}
