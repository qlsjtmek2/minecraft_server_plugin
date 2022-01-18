// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.jdbc;

import java.sql.Connection;
import java.sql.SQLWarning;
import java.sql.ParameterMetaData;
import java.net.URL;
import java.util.Calendar;
import java.sql.ResultSetMetaData;
import java.sql.Array;
import java.sql.Clob;
import java.sql.Blob;
import java.sql.Ref;
import java.io.Reader;
import java.io.InputStream;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class PreparedStatementDelegator implements PreparedStatement
{
    private final PreparedStatement delegate;
    
    public PreparedStatementDelegator(final PreparedStatement delegate) {
        this.delegate = delegate;
    }
    
    public ResultSet executeQuery() throws SQLException {
        return this.delegate.executeQuery();
    }
    
    public int executeUpdate() throws SQLException {
        return this.delegate.executeUpdate();
    }
    
    public void setNull(final int parameterIndex, final int sqlType) throws SQLException {
        this.delegate.setNull(parameterIndex, sqlType);
    }
    
    public void setBoolean(final int parameterIndex, final boolean x) throws SQLException {
        this.delegate.setBoolean(parameterIndex, x);
    }
    
    public void setByte(final int parameterIndex, final byte x) throws SQLException {
        this.delegate.setByte(parameterIndex, x);
    }
    
    public void setShort(final int parameterIndex, final short x) throws SQLException {
        this.delegate.setShort(parameterIndex, x);
    }
    
    public void setInt(final int parameterIndex, final int x) throws SQLException {
        this.delegate.setInt(parameterIndex, x);
    }
    
    public void setLong(final int parameterIndex, final long x) throws SQLException {
        this.delegate.setLong(parameterIndex, x);
    }
    
    public void setFloat(final int parameterIndex, final float x) throws SQLException {
        this.delegate.setFloat(parameterIndex, x);
    }
    
    public void setDouble(final int parameterIndex, final double x) throws SQLException {
        this.delegate.setDouble(parameterIndex, x);
    }
    
    public void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException {
        this.delegate.setBigDecimal(parameterIndex, x);
    }
    
    public void setString(final int parameterIndex, final String x) throws SQLException {
        this.delegate.setString(parameterIndex, x);
    }
    
    public void setBytes(final int parameterIndex, final byte[] x) throws SQLException {
        this.delegate.setBytes(parameterIndex, x);
    }
    
    public void setDate(final int parameterIndex, final Date x) throws SQLException {
        this.delegate.setDate(parameterIndex, x);
    }
    
    public void setTime(final int parameterIndex, final Time x) throws SQLException {
        this.delegate.setTime(parameterIndex, x);
    }
    
    public void setTimestamp(final int parameterIndex, final Timestamp x) throws SQLException {
        this.delegate.setTimestamp(parameterIndex, x);
    }
    
    public void setAsciiStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        this.delegate.setAsciiStream(parameterIndex, x, length);
    }
    
    public void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        this.delegate.setUnicodeStream(parameterIndex, x, length);
    }
    
    public void setBinaryStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        this.delegate.setBinaryStream(parameterIndex, x, length);
    }
    
    public void clearParameters() throws SQLException {
        this.delegate.clearParameters();
    }
    
    public void setObject(final int i, final Object o, final int i1, final int i2) throws SQLException {
        this.delegate.setObject(i, o, i1, i2);
    }
    
    public void setObject(final int parameterIndex, final Object x, final int targetSqlType) throws SQLException {
        this.delegate.setObject(parameterIndex, x, targetSqlType);
    }
    
    public void setObject(final int parameterIndex, final Object x) throws SQLException {
        this.delegate.setObject(parameterIndex, x);
    }
    
    public boolean execute() throws SQLException {
        return this.delegate.execute();
    }
    
    public void addBatch() throws SQLException {
        this.delegate.addBatch();
    }
    
    public void setCharacterStream(final int parameterIndex, final Reader reader, final int length) throws SQLException {
        this.delegate.setCharacterStream(parameterIndex, reader, length);
    }
    
    public void setRef(final int parameterIndex, final Ref x) throws SQLException {
        this.delegate.setRef(parameterIndex, x);
    }
    
    public void setBlob(final int parameterIndex, final Blob x) throws SQLException {
        this.delegate.setBlob(parameterIndex, x);
    }
    
    public void setClob(final int parameterIndex, final Clob x) throws SQLException {
        this.delegate.setClob(parameterIndex, x);
    }
    
    public void setArray(final int parameterIndex, final Array x) throws SQLException {
        this.delegate.setArray(parameterIndex, x);
    }
    
    public ResultSetMetaData getMetaData() throws SQLException {
        return this.delegate.getMetaData();
    }
    
    public void setDate(final int parameterIndex, final Date x, final Calendar cal) throws SQLException {
        this.delegate.setDate(parameterIndex, x, cal);
    }
    
    public void setTime(final int parameterIndex, final Time x, final Calendar cal) throws SQLException {
        this.delegate.setTime(parameterIndex, x, cal);
    }
    
    public void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal) throws SQLException {
        this.delegate.setTimestamp(parameterIndex, x, cal);
    }
    
    public void setNull(final int parameterIndex, final int sqlType, final String typeName) throws SQLException {
        this.delegate.setNull(parameterIndex, sqlType, typeName);
    }
    
    public void setURL(final int parameterIndex, final URL x) throws SQLException {
        this.delegate.setURL(parameterIndex, x);
    }
    
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return this.delegate.getParameterMetaData();
    }
    
    public ResultSet executeQuery(final String sql) throws SQLException {
        return this.delegate.executeQuery(sql);
    }
    
    public int executeUpdate(final String sql) throws SQLException {
        return this.delegate.executeUpdate(sql);
    }
    
    public void close() throws SQLException {
        this.delegate.close();
    }
    
    public int getMaxFieldSize() throws SQLException {
        return this.delegate.getMaxFieldSize();
    }
    
    public void setMaxFieldSize(final int max) throws SQLException {
        this.delegate.setMaxFieldSize(max);
    }
    
    public int getMaxRows() throws SQLException {
        return this.delegate.getMaxRows();
    }
    
    public void setMaxRows(final int max) throws SQLException {
        this.delegate.setMaxRows(max);
    }
    
    public void setEscapeProcessing(final boolean enable) throws SQLException {
        this.delegate.setEscapeProcessing(enable);
    }
    
    public int getQueryTimeout() throws SQLException {
        return this.delegate.getQueryTimeout();
    }
    
    public void setQueryTimeout(final int seconds) throws SQLException {
        this.delegate.setQueryTimeout(seconds);
    }
    
    public void cancel() throws SQLException {
        this.delegate.cancel();
    }
    
    public SQLWarning getWarnings() throws SQLException {
        return this.delegate.getWarnings();
    }
    
    public void clearWarnings() throws SQLException {
        this.delegate.clearWarnings();
    }
    
    public void setCursorName(final String name) throws SQLException {
        this.delegate.setCursorName(name);
    }
    
    public boolean execute(final String sql) throws SQLException {
        return this.delegate.execute(sql);
    }
    
    public ResultSet getResultSet() throws SQLException {
        return this.delegate.getResultSet();
    }
    
    public int getUpdateCount() throws SQLException {
        return this.delegate.getUpdateCount();
    }
    
    public boolean getMoreResults() throws SQLException {
        return this.delegate.getMoreResults();
    }
    
    public void setFetchDirection(final int direction) throws SQLException {
        this.delegate.setFetchDirection(direction);
    }
    
    public int getFetchDirection() throws SQLException {
        return this.delegate.getFetchDirection();
    }
    
    public void setFetchSize(final int rows) throws SQLException {
        this.delegate.setFetchSize(rows);
    }
    
    public int getFetchSize() throws SQLException {
        return this.delegate.getFetchSize();
    }
    
    public int getResultSetConcurrency() throws SQLException {
        return this.delegate.getResultSetConcurrency();
    }
    
    public int getResultSetType() throws SQLException {
        return this.delegate.getResultSetType();
    }
    
    public void addBatch(final String sql) throws SQLException {
        this.delegate.addBatch(sql);
    }
    
    public void clearBatch() throws SQLException {
        this.delegate.clearBatch();
    }
    
    public int[] executeBatch() throws SQLException {
        return this.delegate.executeBatch();
    }
    
    public Connection getConnection() throws SQLException {
        return this.delegate.getConnection();
    }
    
    public boolean getMoreResults(final int current) throws SQLException {
        return this.delegate.getMoreResults(current);
    }
    
    public ResultSet getGeneratedKeys() throws SQLException {
        return this.delegate.getGeneratedKeys();
    }
    
    public int executeUpdate(final String sql, final int autoGeneratedKeys) throws SQLException {
        return this.delegate.executeUpdate(sql, autoGeneratedKeys);
    }
    
    public int executeUpdate(final String sql, final int[] columnIndexes) throws SQLException {
        return this.delegate.executeUpdate(sql, columnIndexes);
    }
    
    public int executeUpdate(final String sql, final String[] columnNames) throws SQLException {
        return this.delegate.executeUpdate(sql, columnNames);
    }
    
    public boolean execute(final String sql, final int autoGeneratedKeys) throws SQLException {
        return this.delegate.execute(sql, autoGeneratedKeys);
    }
    
    public boolean execute(final String sql, final int[] columnIndexes) throws SQLException {
        return this.delegate.execute(sql, columnIndexes);
    }
    
    public boolean execute(final String sql, final String[] columnNames) throws SQLException {
        return this.delegate.execute(sql, columnNames);
    }
    
    public int getResultSetHoldability() throws SQLException {
        return this.delegate.getResultSetHoldability();
    }
}
