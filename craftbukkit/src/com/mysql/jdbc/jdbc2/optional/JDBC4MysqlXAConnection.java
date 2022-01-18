// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.jdbc2.optional;

import java.util.Iterator;
import javax.sql.StatementEvent;
import java.sql.SQLException;
import java.util.HashMap;
import com.mysql.jdbc.ConnectionImpl;
import javax.sql.StatementEventListener;
import java.util.Map;

public class JDBC4MysqlXAConnection extends MysqlXAConnection
{
    private Map<StatementEventListener, StatementEventListener> statementEventListeners;
    
    public JDBC4MysqlXAConnection(final ConnectionImpl connection, final boolean logXaCommands) throws SQLException {
        super(connection, logXaCommands);
        this.statementEventListeners = new HashMap<StatementEventListener, StatementEventListener>();
    }
    
    @Override
    public synchronized void close() throws SQLException {
        super.close();
        if (this.statementEventListeners != null) {
            this.statementEventListeners.clear();
            this.statementEventListeners = null;
        }
    }
    
    @Override
    public void addStatementEventListener(final StatementEventListener listener) {
        synchronized (this.statementEventListeners) {
            this.statementEventListeners.put(listener, listener);
        }
    }
    
    @Override
    public void removeStatementEventListener(final StatementEventListener listener) {
        synchronized (this.statementEventListeners) {
            this.statementEventListeners.remove(listener);
        }
    }
    
    void fireStatementEvent(final StatementEvent event) throws SQLException {
        synchronized (this.statementEventListeners) {
            for (final StatementEventListener listener : this.statementEventListeners.keySet()) {
                listener.statementClosed(event);
            }
        }
    }
}
