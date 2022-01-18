// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.querydefn;

import java.sql.SQLException;
import javax.persistence.PersistenceException;
import com.avaje.ebean.SqlFutureList;
import java.util.Map;
import java.util.Set;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import java.util.List;
import com.avaje.ebeaninternal.api.BindParams;
import java.sql.PreparedStatement;
import com.avaje.ebean.SqlQueryListener;
import com.avaje.ebean.EbeanServer;
import com.avaje.ebeaninternal.api.SpiSqlQuery;

public class DefaultRelationalQuery implements SpiSqlQuery
{
    private static final long serialVersionUID = -1098305779779591068L;
    private transient EbeanServer server;
    private transient SqlQueryListener queryListener;
    private String query;
    private int firstRow;
    private int maxRows;
    private int timeout;
    private boolean futureFetch;
    private boolean cancelled;
    private transient PreparedStatement pstmt;
    private int backgroundFetchAfter;
    private int bufferFetchSizeHint;
    private String mapKey;
    private BindParams bindParams;
    
    public DefaultRelationalQuery(final EbeanServer server, final String query) {
        this.bindParams = new BindParams();
        this.server = server;
        this.query = query;
    }
    
    public DefaultRelationalQuery setQuery(final String query) {
        this.query = query;
        return this;
    }
    
    public List<SqlRow> findList() {
        return this.server.findList(this, null);
    }
    
    public Set<SqlRow> findSet() {
        return this.server.findSet(this, null);
    }
    
    public Map<?, SqlRow> findMap() {
        return this.server.findMap(this, null);
    }
    
    public SqlRow findUnique() {
        return this.server.findUnique(this, null);
    }
    
    public SqlFutureList findFutureList() {
        return this.server.findFutureList(this, null);
    }
    
    public DefaultRelationalQuery setParameter(final int position, final Object value) {
        this.bindParams.setParameter(position, value);
        return this;
    }
    
    public DefaultRelationalQuery setParameter(final String paramName, final Object value) {
        this.bindParams.setParameter(paramName, value);
        return this;
    }
    
    public SqlQueryListener getListener() {
        return this.queryListener;
    }
    
    public DefaultRelationalQuery setListener(final SqlQueryListener queryListener) {
        this.queryListener = queryListener;
        return this;
    }
    
    public String toString() {
        return "SqlQuery [" + this.query + "]";
    }
    
    public int getFirstRow() {
        return this.firstRow;
    }
    
    public DefaultRelationalQuery setFirstRow(final int firstRow) {
        this.firstRow = firstRow;
        return this;
    }
    
    public int getMaxRows() {
        return this.maxRows;
    }
    
    public DefaultRelationalQuery setMaxRows(final int maxRows) {
        this.maxRows = maxRows;
        return this;
    }
    
    public String getMapKey() {
        return this.mapKey;
    }
    
    public DefaultRelationalQuery setMapKey(final String mapKey) {
        this.mapKey = mapKey;
        return this;
    }
    
    public int getBackgroundFetchAfter() {
        return this.backgroundFetchAfter;
    }
    
    public DefaultRelationalQuery setBackgroundFetchAfter(final int backgroundFetchAfter) {
        this.backgroundFetchAfter = backgroundFetchAfter;
        return this;
    }
    
    public int getTimeout() {
        return this.timeout;
    }
    
    public DefaultRelationalQuery setTimeout(final int secs) {
        this.timeout = secs;
        return this;
    }
    
    public BindParams getBindParams() {
        return this.bindParams;
    }
    
    public DefaultRelationalQuery setBufferFetchSizeHint(final int bufferFetchSizeHint) {
        this.bufferFetchSizeHint = bufferFetchSizeHint;
        return this;
    }
    
    public int getBufferFetchSizeHint() {
        return this.bufferFetchSizeHint;
    }
    
    public String getQuery() {
        return this.query;
    }
    
    public boolean isFutureFetch() {
        return this.futureFetch;
    }
    
    public void setFutureFetch(final boolean futureFetch) {
        this.futureFetch = futureFetch;
    }
    
    public void setPreparedStatement(final PreparedStatement pstmt) {
        synchronized (this) {
            this.pstmt = pstmt;
        }
    }
    
    public void cancel() {
        synchronized (this) {
            this.cancelled = true;
            if (this.pstmt != null) {
                try {
                    this.pstmt.cancel();
                }
                catch (SQLException e) {
                    final String msg = "Error cancelling query";
                    throw new PersistenceException(msg, e);
                }
            }
        }
    }
    
    public boolean isCancelled() {
        synchronized (this) {
            return this.cancelled;
        }
    }
}
