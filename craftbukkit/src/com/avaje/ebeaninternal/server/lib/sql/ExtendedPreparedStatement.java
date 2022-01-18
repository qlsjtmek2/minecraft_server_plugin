// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.sql;

import java.net.URL;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Ref;
import java.util.Calendar;
import java.sql.Date;
import java.sql.Clob;
import java.io.Reader;
import java.sql.Blob;
import java.math.BigDecimal;
import java.io.InputStream;
import java.sql.Array;
import java.sql.ParameterMetaData;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class ExtendedPreparedStatement extends ExtendedStatement implements PreparedStatement
{
    final String sql;
    final String cacheKey;
    
    public ExtendedPreparedStatement(final PooledConnection pooledConnection, final PreparedStatement pstmt, final String sql, final String cacheKey) {
        super(pooledConnection, pstmt);
        this.sql = sql;
        this.cacheKey = cacheKey;
    }
    
    public PreparedStatement getDelegate() {
        return this.pstmt;
    }
    
    public String getCacheKey() {
        return this.cacheKey;
    }
    
    public String getSql() {
        return this.sql;
    }
    
    public void closeDestroy() throws SQLException {
        this.pstmt.close();
    }
    
    public void close() throws SQLException {
        this.pooledConnection.returnPreparedStatement(this);
    }
    
    public void addBatch() throws SQLException {
        try {
            this.pstmt.addBatch();
        }
        catch (SQLException e) {
            this.pooledConnection.addError(e);
            throw e;
        }
    }
    
    public void clearParameters() throws SQLException {
        try {
            this.pstmt.clearParameters();
        }
        catch (SQLException e) {
            this.pooledConnection.addError(e);
            throw e;
        }
    }
    
    public boolean execute() throws SQLException {
        try {
            return this.pstmt.execute();
        }
        catch (SQLException e) {
            this.pooledConnection.addError(e);
            throw e;
        }
    }
    
    public ResultSet executeQuery() throws SQLException {
        try {
            return this.pstmt.executeQuery();
        }
        catch (SQLException e) {
            this.pooledConnection.addError(e);
            throw e;
        }
    }
    
    public int executeUpdate() throws SQLException {
        try {
            return this.pstmt.executeUpdate();
        }
        catch (SQLException e) {
            this.pooledConnection.addError(e);
            throw e;
        }
    }
    
    public ResultSetMetaData getMetaData() throws SQLException {
        try {
            return this.pstmt.getMetaData();
        }
        catch (SQLException e) {
            this.pooledConnection.addError(e);
            throw e;
        }
    }
    
    public ParameterMetaData getParameterMetaData() throws SQLException {
        return this.pstmt.getParameterMetaData();
    }
    
    public void setArray(final int i, final Array x) throws SQLException {
        this.pstmt.setArray(i, x);
    }
    
    public void setAsciiStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        this.pstmt.setAsciiStream(parameterIndex, x, length);
    }
    
    public void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException {
        this.pstmt.setBigDecimal(parameterIndex, x);
    }
    
    public void setBinaryStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        this.pstmt.setBinaryStream(parameterIndex, x, length);
    }
    
    public void setBlob(final int i, final Blob x) throws SQLException {
        this.pstmt.setBlob(i, x);
    }
    
    public void setBoolean(final int parameterIndex, final boolean x) throws SQLException {
        this.pstmt.setBoolean(parameterIndex, x);
    }
    
    public void setByte(final int parameterIndex, final byte x) throws SQLException {
        this.pstmt.setByte(parameterIndex, x);
    }
    
    public void setBytes(final int parameterIndex, final byte[] x) throws SQLException {
        this.pstmt.setBytes(parameterIndex, x);
    }
    
    public void setCharacterStream(final int parameterIndex, final Reader reader, final int length) throws SQLException {
        this.pstmt.setCharacterStream(parameterIndex, reader, length);
    }
    
    public void setClob(final int i, final Clob x) throws SQLException {
        this.pstmt.setClob(i, x);
    }
    
    public void setDate(final int parameterIndex, final Date x) throws SQLException {
        this.pstmt.setDate(parameterIndex, x);
    }
    
    public void setDate(final int parameterIndex, final Date x, final Calendar cal) throws SQLException {
        this.pstmt.setDate(parameterIndex, x, cal);
    }
    
    public void setDouble(final int parameterIndex, final double x) throws SQLException {
        this.pstmt.setDouble(parameterIndex, x);
    }
    
    public void setFloat(final int parameterIndex, final float x) throws SQLException {
        this.pstmt.setFloat(parameterIndex, x);
    }
    
    public void setInt(final int parameterIndex, final int x) throws SQLException {
        this.pstmt.setInt(parameterIndex, x);
    }
    
    public void setLong(final int parameterIndex, final long x) throws SQLException {
        this.pstmt.setLong(parameterIndex, x);
    }
    
    public void setNull(final int parameterIndex, final int sqlType) throws SQLException {
        this.pstmt.setNull(parameterIndex, sqlType);
    }
    
    public void setNull(final int paramIndex, final int sqlType, final String typeName) throws SQLException {
        this.pstmt.setNull(paramIndex, sqlType, typeName);
    }
    
    public void setObject(final int parameterIndex, final Object x) throws SQLException {
        this.pstmt.setObject(parameterIndex, x);
    }
    
    public void setObject(final int parameterIndex, final Object x, final int targetSqlType) throws SQLException {
        this.pstmt.setObject(parameterIndex, x, targetSqlType);
    }
    
    public void setObject(final int parameterIndex, final Object x, final int targetSqlType, final int scale) throws SQLException {
        this.pstmt.setObject(parameterIndex, x, targetSqlType, scale);
    }
    
    public void setRef(final int i, final Ref x) throws SQLException {
        this.pstmt.setRef(i, x);
    }
    
    public void setShort(final int parameterIndex, final short x) throws SQLException {
        this.pstmt.setShort(parameterIndex, x);
    }
    
    public void setString(final int parameterIndex, final String x) throws SQLException {
        this.pstmt.setString(parameterIndex, x);
    }
    
    public void setTime(final int parameterIndex, final Time x) throws SQLException {
        this.pstmt.setTime(parameterIndex, x);
    }
    
    public void setTime(final int parameterIndex, final Time x, final Calendar cal) throws SQLException {
        this.pstmt.setTime(parameterIndex, x, cal);
    }
    
    public void setTimestamp(final int parameterIndex, final Timestamp x) throws SQLException {
        this.pstmt.setTimestamp(parameterIndex, x);
    }
    
    public void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal) throws SQLException {
        this.pstmt.setTimestamp(parameterIndex, x, cal);
    }
    
    public void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        this.pstmt.setUnicodeStream(parameterIndex, x, length);
    }
    
    public void setURL(final int parameterIndex, final URL x) throws SQLException {
        this.pstmt.setURL(parameterIndex, x);
    }
}
