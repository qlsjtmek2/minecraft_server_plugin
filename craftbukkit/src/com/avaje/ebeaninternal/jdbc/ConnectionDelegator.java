// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.jdbc;

import java.sql.Savepoint;
import java.util.Map;
import java.sql.SQLWarning;
import java.sql.DatabaseMetaData;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class ConnectionDelegator implements Connection
{
    private final Connection delegate;
    
    public ConnectionDelegator(final Connection delegate) {
        this.delegate = delegate;
    }
    
    public Statement createStatement() throws SQLException {
        return this.delegate.createStatement();
    }
    
    public PreparedStatement prepareStatement(final String sql) throws SQLException {
        return this.delegate.prepareStatement(sql);
    }
    
    public CallableStatement prepareCall(final String sql) throws SQLException {
        return this.delegate.prepareCall(sql);
    }
    
    public String nativeSQL(final String sql) throws SQLException {
        return this.delegate.nativeSQL(sql);
    }
    
    public void setAutoCommit(final boolean autoCommit) throws SQLException {
        this.delegate.setAutoCommit(autoCommit);
    }
    
    public boolean getAutoCommit() throws SQLException {
        return this.delegate.getAutoCommit();
    }
    
    public void commit() throws SQLException {
        this.delegate.commit();
    }
    
    public void rollback() throws SQLException {
        this.delegate.rollback();
    }
    
    public void close() throws SQLException {
        this.delegate.close();
    }
    
    public boolean isClosed() throws SQLException {
        return this.delegate.isClosed();
    }
    
    public DatabaseMetaData getMetaData() throws SQLException {
        return this.delegate.getMetaData();
    }
    
    public void setReadOnly(final boolean readOnly) throws SQLException {
        this.delegate.setReadOnly(readOnly);
    }
    
    public boolean isReadOnly() throws SQLException {
        return this.delegate.isReadOnly();
    }
    
    public void setCatalog(final String catalog) throws SQLException {
        this.delegate.setCatalog(catalog);
    }
    
    public String getCatalog() throws SQLException {
        return this.delegate.getCatalog();
    }
    
    public void setTransactionIsolation(final int level) throws SQLException {
        this.delegate.setTransactionIsolation(level);
    }
    
    public int getTransactionIsolation() throws SQLException {
        return this.delegate.getTransactionIsolation();
    }
    
    public SQLWarning getWarnings() throws SQLException {
        return this.delegate.getWarnings();
    }
    
    public void clearWarnings() throws SQLException {
        this.delegate.clearWarnings();
    }
    
    public Statement createStatement(final int resultSetType, final int resultSetConcurrency) throws SQLException {
        return this.delegate.createStatement(resultSetType, resultSetConcurrency);
    }
    
    public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        return this.delegate.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }
    
    public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        return this.delegate.prepareCall(sql, resultSetType, resultSetConcurrency);
    }
    
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        return this.delegate.getTypeMap();
    }
    
    public void setTypeMap(final Map<String, Class<?>> map) throws SQLException {
        this.delegate.setTypeMap(map);
    }
    
    public void setHoldability(final int holdability) throws SQLException {
        this.delegate.setHoldability(holdability);
    }
    
    public int getHoldability() throws SQLException {
        return this.delegate.getHoldability();
    }
    
    public Savepoint setSavepoint() throws SQLException {
        return this.delegate.setSavepoint();
    }
    
    public Savepoint setSavepoint(final String name) throws SQLException {
        return this.delegate.setSavepoint(name);
    }
    
    public void rollback(final Savepoint savepoint) throws SQLException {
        this.delegate.rollback(savepoint);
    }
    
    public void releaseSavepoint(final Savepoint savepoint) throws SQLException {
        this.delegate.releaseSavepoint(savepoint);
    }
    
    public Statement createStatement(final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        return this.delegate.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }
    
    public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        return this.delegate.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }
    
    public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        return this.delegate.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }
    
    public PreparedStatement prepareStatement(final String sql, final int autoGeneratedKeys) throws SQLException {
        return this.delegate.prepareStatement(sql, autoGeneratedKeys);
    }
    
    public PreparedStatement prepareStatement(final String sql, final int[] columnIndexes) throws SQLException {
        return this.delegate.prepareStatement(sql, columnIndexes);
    }
    
    public PreparedStatement prepareStatement(final String sql, final String[] columnNames) throws SQLException {
        return this.delegate.prepareStatement(sql, columnNames);
    }
}
