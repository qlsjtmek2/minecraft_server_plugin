// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.sql;

import java.sql.SQLWarning;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import com.avaje.ebeaninternal.jdbc.PreparedStatementDelegator;

public abstract class ExtendedStatement extends PreparedStatementDelegator
{
    protected final PooledConnection pooledConnection;
    protected final PreparedStatement pstmt;
    
    public ExtendedStatement(final PooledConnection pooledConnection, final PreparedStatement pstmt) {
        super(pstmt);
        this.pooledConnection = pooledConnection;
        this.pstmt = pstmt;
    }
    
    public abstract void close() throws SQLException;
    
    public Connection getConnection() throws SQLException {
        try {
            return this.pstmt.getConnection();
        }
        catch (SQLException e) {
            this.pooledConnection.addError(e);
            throw e;
        }
    }
    
    public void addBatch(final String sql) throws SQLException {
        try {
            this.pooledConnection.setLastStatement(sql);
            this.pstmt.addBatch(sql);
        }
        catch (SQLException e) {
            this.pooledConnection.addError(e);
            throw e;
        }
    }
    
    public boolean execute(final String sql) throws SQLException {
        try {
            this.pooledConnection.setLastStatement(sql);
            return this.pstmt.execute(sql);
        }
        catch (SQLException e) {
            this.pooledConnection.addError(e);
            throw e;
        }
    }
    
    public ResultSet executeQuery(final String sql) throws SQLException {
        try {
            this.pooledConnection.setLastStatement(sql);
            return this.pstmt.executeQuery(sql);
        }
        catch (SQLException e) {
            this.pooledConnection.addError(e);
            throw e;
        }
    }
    
    public int executeUpdate(final String sql) throws SQLException {
        try {
            this.pooledConnection.setLastStatement(sql);
            return this.pstmt.executeUpdate(sql);
        }
        catch (SQLException e) {
            this.pooledConnection.addError(e);
            throw e;
        }
    }
    
    public int[] executeBatch() throws SQLException {
        return this.pstmt.executeBatch();
    }
    
    public void cancel() throws SQLException {
        this.pstmt.cancel();
    }
    
    public void clearBatch() throws SQLException {
        this.pstmt.clearBatch();
    }
    
    public void clearWarnings() throws SQLException {
        this.pstmt.clearWarnings();
    }
    
    public int getFetchDirection() throws SQLException {
        return this.pstmt.getFetchDirection();
    }
    
    public int getFetchSize() throws SQLException {
        return this.pstmt.getFetchSize();
    }
    
    public int getMaxFieldSize() throws SQLException {
        return this.pstmt.getMaxFieldSize();
    }
    
    public int getMaxRows() throws SQLException {
        return this.pstmt.getMaxRows();
    }
    
    public boolean getMoreResults() throws SQLException {
        return this.pstmt.getMoreResults();
    }
    
    public int getQueryTimeout() throws SQLException {
        return this.pstmt.getQueryTimeout();
    }
    
    public ResultSet getResultSet() throws SQLException {
        return this.pstmt.getResultSet();
    }
    
    public int getResultSetConcurrency() throws SQLException {
        return this.pstmt.getResultSetConcurrency();
    }
    
    public int getResultSetType() throws SQLException {
        return this.pstmt.getResultSetType();
    }
    
    public int getUpdateCount() throws SQLException {
        return this.pstmt.getUpdateCount();
    }
    
    public SQLWarning getWarnings() throws SQLException {
        return this.pstmt.getWarnings();
    }
    
    public void setCursorName(final String name) throws SQLException {
        this.pstmt.setCursorName(name);
    }
    
    public void setEscapeProcessing(final boolean enable) throws SQLException {
        this.pstmt.setEscapeProcessing(enable);
    }
    
    public void setFetchDirection(final int direction) throws SQLException {
        this.pstmt.setFetchDirection(direction);
    }
    
    public void setFetchSize(final int rows) throws SQLException {
        this.pstmt.setFetchSize(rows);
    }
    
    public void setMaxFieldSize(final int max) throws SQLException {
        this.pstmt.setMaxFieldSize(max);
    }
    
    public void setMaxRows(final int max) throws SQLException {
        this.pstmt.setMaxRows(max);
    }
    
    public void setQueryTimeout(final int seconds) throws SQLException {
        this.pstmt.setQueryTimeout(seconds);
    }
    
    public boolean getMoreResults(final int i) throws SQLException {
        return this.pstmt.getMoreResults(i);
    }
    
    public ResultSet getGeneratedKeys() throws SQLException {
        return this.pstmt.getGeneratedKeys();
    }
    
    public int executeUpdate(final String s, final int i) throws SQLException {
        return this.pstmt.executeUpdate(s, i);
    }
    
    public int executeUpdate(final String s, final int[] i) throws SQLException {
        return this.pstmt.executeUpdate(s, i);
    }
    
    public int executeUpdate(final String s, final String[] i) throws SQLException {
        return this.pstmt.executeUpdate(s, i);
    }
    
    public boolean execute(final String s, final int i) throws SQLException {
        return this.pstmt.execute(s, i);
    }
    
    public boolean execute(final String s, final int[] i) throws SQLException {
        return this.pstmt.execute(s, i);
    }
    
    public boolean execute(final String s, final String[] i) throws SQLException {
        return this.pstmt.execute(s, i);
    }
    
    public int getResultSetHoldability() throws SQLException {
        return this.pstmt.getResultSetHoldability();
    }
}
