// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import java.sql.SQLException;
import java.sql.CallableStatement;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.CallableSql;
import com.avaje.ebeaninternal.api.BindParams;
import com.avaje.ebeaninternal.api.TransactionEventTable;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebeaninternal.api.SpiCallableSql;
import java.io.Serializable;

public class DefaultCallableSql implements Serializable, SpiCallableSql
{
    private static final long serialVersionUID = 8984272253185424701L;
    private final transient EbeanServer server;
    private String sql;
    private String label;
    private int timeout;
    private TransactionEventTable transactionEvent;
    private BindParams bindParameters;
    
    public DefaultCallableSql(final EbeanServer server, final String sql) {
        this.transactionEvent = new TransactionEventTable();
        this.bindParameters = new BindParams();
        this.server = server;
        this.sql = sql;
    }
    
    public void execute() {
        this.server.execute(this, null);
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public CallableSql setLabel(final String label) {
        this.label = label;
        return this;
    }
    
    public int getTimeout() {
        return this.timeout;
    }
    
    public String getSql() {
        return this.sql;
    }
    
    public CallableSql setTimeout(final int secs) {
        this.timeout = secs;
        return this;
    }
    
    public CallableSql setSql(final String sql) {
        this.sql = sql;
        return this;
    }
    
    public CallableSql bind(final int position, final Object value) {
        this.bindParameters.setParameter(position, value);
        return this;
    }
    
    public CallableSql setParameter(final int position, final Object value) {
        this.bindParameters.setParameter(position, value);
        return this;
    }
    
    public CallableSql registerOut(final int position, final int type) {
        this.bindParameters.registerOut(position, type);
        return this;
    }
    
    public Object getObject(final int position) {
        final BindParams.Param p = this.bindParameters.getParameter(position);
        return p.getOutValue();
    }
    
    public boolean executeOverride(final CallableStatement cstmt) throws SQLException {
        return false;
    }
    
    public CallableSql addModification(final String tableName, final boolean inserts, final boolean updates, final boolean deletes) {
        this.transactionEvent.add(tableName, inserts, updates, deletes);
        return this;
    }
    
    public TransactionEventTable getTransactionEventTable() {
        return this.transactionEvent;
    }
    
    public BindParams getBindParams() {
        return this.bindParameters;
    }
}
