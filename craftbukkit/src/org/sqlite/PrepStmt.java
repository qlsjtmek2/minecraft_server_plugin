// 
// Decompiled by Procyon v0.5.30
// 

package org.sqlite;

import java.sql.ResultSetMetaData;
import java.util.Calendar;
import java.io.IOException;
import java.io.Reader;
import java.sql.Timestamp;
import java.sql.Time;
import java.util.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;

final class PrepStmt extends Stmt implements PreparedStatement, ParameterMetaData, Codes
{
    private int columnCount;
    private int paramCount;
    
    PrepStmt(final Conn conn, final String sql) throws SQLException {
        super(conn);
        this.sql = sql;
        this.db.prepare(this);
        this.rs.colsMeta = this.db.column_names(this.pointer);
        this.columnCount = this.db.column_count(this.pointer);
        this.paramCount = this.db.bind_parameter_count(this.pointer);
        this.batch = new Object[this.paramCount];
        this.batchPos = 0;
    }
    
    public void clearParameters() throws SQLException {
        this.checkOpen();
        this.db.reset(this.pointer);
        this.clearBatch();
    }
    
    protected void finalize() throws SQLException {
        this.close();
    }
    
    public boolean execute() throws SQLException {
        this.checkOpen();
        this.rs.close();
        this.db.reset(this.pointer);
        this.resultsWaiting = this.db.execute(this, this.batch);
        return this.columnCount != 0;
    }
    
    public ResultSet executeQuery() throws SQLException {
        this.checkOpen();
        if (this.columnCount == 0) {
            throw new SQLException("query does not return results");
        }
        this.rs.close();
        this.db.reset(this.pointer);
        this.resultsWaiting = this.db.execute(this, this.batch);
        return this.getResultSet();
    }
    
    public int executeUpdate() throws SQLException {
        this.checkOpen();
        if (this.columnCount != 0) {
            throw new SQLException("query returns results");
        }
        this.rs.close();
        this.db.reset(this.pointer);
        return this.db.executeUpdate(this, this.batch);
    }
    
    public int[] executeBatch() throws SQLException {
        if (this.batchPos == 0) {
            return new int[0];
        }
        try {
            return this.db.executeBatch(this.pointer, this.batchPos / this.paramCount, this.batch);
        }
        finally {
            this.clearBatch();
        }
    }
    
    public int getUpdateCount() throws SQLException {
        this.checkOpen();
        if (this.pointer == 0L || this.resultsWaiting) {
            return -1;
        }
        return this.db.changes();
    }
    
    public void addBatch() throws SQLException {
        this.checkOpen();
        this.batchPos += this.paramCount;
        if (this.batchPos + this.paramCount > this.batch.length) {
            final Object[] nb = new Object[this.batch.length * 2];
            System.arraycopy(this.batch, 0, nb, 0, this.batch.length);
            this.batch = nb;
        }
        System.arraycopy(this.batch, this.batchPos - this.paramCount, this.batch, this.batchPos, this.paramCount);
    }
    
    public ParameterMetaData getParameterMetaData() {
        return this;
    }
    
    public int getParameterCount() throws SQLException {
        this.checkOpen();
        return this.paramCount;
    }
    
    public String getParameterClassName(final int param) throws SQLException {
        this.checkOpen();
        return "java.lang.String";
    }
    
    public String getParameterTypeName(final int pos) {
        return "VARCHAR";
    }
    
    public int getParameterType(final int pos) {
        return 12;
    }
    
    public int getParameterMode(final int pos) {
        return 1;
    }
    
    public int getPrecision(final int pos) {
        return 0;
    }
    
    public int getScale(final int pos) {
        return 0;
    }
    
    public int isNullable(final int pos) {
        return 1;
    }
    
    public boolean isSigned(final int pos) {
        return true;
    }
    
    public Statement getStatement() {
        return this;
    }
    
    private void batch(final int pos, final Object value) throws SQLException {
        this.checkOpen();
        if (this.batch == null) {
            this.batch = new Object[this.paramCount];
        }
        this.batch[this.batchPos + pos - 1] = value;
    }
    
    public void setBoolean(final int pos, final boolean value) throws SQLException {
        this.setInt(pos, value ? 1 : 0);
    }
    
    public void setByte(final int pos, final byte value) throws SQLException {
        this.setInt(pos, value);
    }
    
    public void setBytes(final int pos, final byte[] value) throws SQLException {
        this.batch(pos, value);
    }
    
    public void setDouble(final int pos, final double value) throws SQLException {
        this.batch(pos, new Double(value));
    }
    
    public void setFloat(final int pos, final float value) throws SQLException {
        this.batch(pos, new Float(value));
    }
    
    public void setInt(final int pos, final int value) throws SQLException {
        this.batch(pos, new Integer(value));
    }
    
    public void setLong(final int pos, final long value) throws SQLException {
        this.batch(pos, new Long(value));
    }
    
    public void setNull(final int pos, final int u1) throws SQLException {
        this.setNull(pos, u1, null);
    }
    
    public void setNull(final int pos, final int u1, final String u2) throws SQLException {
        this.batch(pos, null);
    }
    
    public void setObject(final int pos, final Object value) throws SQLException {
        if (value == null) {
            this.batch(pos, null);
        }
        else if (value instanceof Date) {
            this.batch(pos, new Long(((Date)value).getTime()));
        }
        else if (value instanceof java.sql.Date) {
            this.batch(pos, new Long(((java.sql.Date)value).getTime()));
        }
        else if (value instanceof Time) {
            this.batch(pos, new Long(((Time)value).getTime()));
        }
        else if (value instanceof Timestamp) {
            this.batch(pos, new Long(((Timestamp)value).getTime()));
        }
        else if (value instanceof Long) {
            this.batch(pos, value);
        }
        else if (value instanceof Integer) {
            this.batch(pos, value);
        }
        else if (value instanceof Short) {
            this.batch(pos, new Integer((int)value));
        }
        else if (value instanceof Float) {
            this.batch(pos, value);
        }
        else if (value instanceof Double) {
            this.batch(pos, value);
        }
        else if (value instanceof Boolean) {
            this.setBoolean(pos, (boolean)value);
        }
        else if (value instanceof byte[]) {
            this.batch(pos, value);
        }
        else {
            this.batch(pos, value.toString());
        }
    }
    
    public void setObject(final int p, final Object v, final int t) throws SQLException {
        this.setObject(p, v);
    }
    
    public void setObject(final int p, final Object v, final int t, final int s) throws SQLException {
        this.setObject(p, v);
    }
    
    public void setShort(final int pos, final short value) throws SQLException {
        this.setInt(pos, value);
    }
    
    public void setString(final int pos, final String value) throws SQLException {
        this.batch(pos, value);
    }
    
    public void setCharacterStream(final int pos, final Reader reader, final int length) throws SQLException {
        try {
            final StringBuffer sb = new StringBuffer();
            final char[] cbuf = new char[8192];
            int cnt;
            while ((cnt = reader.read(cbuf)) > 0) {
                sb.append(cbuf, 0, cnt);
            }
            this.setString(pos, sb.toString());
        }
        catch (IOException e) {
            throw new SQLException("Cannot read from character stream, exception message: " + e.getMessage());
        }
    }
    
    public void setDate(final int pos, final java.sql.Date x) throws SQLException {
        this.setObject(pos, x);
    }
    
    public void setDate(final int pos, final java.sql.Date x, final Calendar cal) throws SQLException {
        this.setObject(pos, x);
    }
    
    public void setTime(final int pos, final Time x) throws SQLException {
        this.setObject(pos, x);
    }
    
    public void setTime(final int pos, final Time x, final Calendar cal) throws SQLException {
        this.setObject(pos, x);
    }
    
    public void setTimestamp(final int pos, final Timestamp x) throws SQLException {
        this.setObject(pos, x);
    }
    
    public void setTimestamp(final int pos, final Timestamp x, final Calendar cal) throws SQLException {
        this.setObject(pos, x);
    }
    
    public ResultSetMetaData getMetaData() throws SQLException {
        this.checkOpen();
        return this.rs;
    }
    
    public boolean execute(final String sql) throws SQLException {
        throw this.unused();
    }
    
    public int executeUpdate(final String sql) throws SQLException {
        throw this.unused();
    }
    
    public ResultSet executeQuery(final String sql) throws SQLException {
        throw this.unused();
    }
    
    public void addBatch(final String sql) throws SQLException {
        throw this.unused();
    }
    
    private SQLException unused() {
        return new SQLException("not supported by PreparedStatment");
    }
}
