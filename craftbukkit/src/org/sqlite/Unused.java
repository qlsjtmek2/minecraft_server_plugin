// 
// Decompiled by Procyon v0.5.30
// 

package org.sqlite;

import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;
import java.io.Reader;
import java.util.Map;
import java.net.URL;
import java.sql.Ref;
import java.sql.Clob;
import java.sql.Blob;
import java.math.BigDecimal;
import java.io.InputStream;
import java.sql.Array;
import java.sql.SQLException;

abstract class Unused
{
    private SQLException unused() {
        return new SQLException("not implemented by SQLite JDBC driver");
    }
    
    public void setEscapeProcessing(final boolean enable) throws SQLException {
        throw this.unused();
    }
    
    public boolean execute(final String sql, final int[] colinds) throws SQLException {
        throw this.unused();
    }
    
    public boolean execute(final String sql, final String[] colnames) throws SQLException {
        throw this.unused();
    }
    
    public int executeUpdate(final String sql, final int autoKeys) throws SQLException {
        throw this.unused();
    }
    
    public int executeUpdate(final String sql, final int[] colinds) throws SQLException {
        throw this.unused();
    }
    
    public int executeUpdate(final String sql, final String[] cols) throws SQLException {
        throw this.unused();
    }
    
    public boolean execute(final String sql, final int autokeys) throws SQLException {
        throw this.unused();
    }
    
    public void setArray(final int i, final Array x) throws SQLException {
        throw this.unused();
    }
    
    public void setAsciiStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        throw this.unused();
    }
    
    public void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException {
        throw this.unused();
    }
    
    public void setBinaryStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        throw this.unused();
    }
    
    public void setBlob(final int i, final Blob x) throws SQLException {
        throw this.unused();
    }
    
    public void setClob(final int i, final Clob x) throws SQLException {
        throw this.unused();
    }
    
    public void setRef(final int i, final Ref x) throws SQLException {
        throw this.unused();
    }
    
    public void setUnicodeStream(final int pos, final InputStream x, final int length) throws SQLException {
        throw this.unused();
    }
    
    public void setURL(final int pos, final URL x) throws SQLException {
        throw this.unused();
    }
    
    public Array getArray(final int i) throws SQLException {
        throw this.unused();
    }
    
    public Array getArray(final String col) throws SQLException {
        throw this.unused();
    }
    
    public InputStream getAsciiStream(final int col) throws SQLException {
        throw this.unused();
    }
    
    public InputStream getAsciiStream(final String col) throws SQLException {
        throw this.unused();
    }
    
    public BigDecimal getBigDecimal(final int col) throws SQLException {
        throw this.unused();
    }
    
    public BigDecimal getBigDecimal(final int col, final int s) throws SQLException {
        throw this.unused();
    }
    
    public BigDecimal getBigDecimal(final String col) throws SQLException {
        throw this.unused();
    }
    
    public BigDecimal getBigDecimal(final String col, final int s) throws SQLException {
        throw this.unused();
    }
    
    public InputStream getBinaryStream(final int col) throws SQLException {
        throw this.unused();
    }
    
    public InputStream getBinaryStream(final String col) throws SQLException {
        throw this.unused();
    }
    
    public Blob getBlob(final int col) throws SQLException {
        throw this.unused();
    }
    
    public Blob getBlob(final String col) throws SQLException {
        throw this.unused();
    }
    
    public Clob getClob(final int col) throws SQLException {
        throw this.unused();
    }
    
    public Clob getClob(final String col) throws SQLException {
        throw this.unused();
    }
    
    public Object getObject(final int col, final Map map) throws SQLException {
        throw this.unused();
    }
    
    public Object getObject(final String col, final Map map) throws SQLException {
        throw this.unused();
    }
    
    public Ref getRef(final int i) throws SQLException {
        throw this.unused();
    }
    
    public Ref getRef(final String col) throws SQLException {
        throw this.unused();
    }
    
    public InputStream getUnicodeStream(final int col) throws SQLException {
        throw this.unused();
    }
    
    public InputStream getUnicodeStream(final String col) throws SQLException {
        throw this.unused();
    }
    
    public URL getURL(final int col) throws SQLException {
        throw this.unused();
    }
    
    public URL getURL(final String col) throws SQLException {
        throw this.unused();
    }
    
    public void insertRow() throws SQLException {
        throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
    }
    
    public void moveToCurrentRow() throws SQLException {
        throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
    }
    
    public void moveToInsertRow() throws SQLException {
        throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
    }
    
    public boolean last() throws SQLException {
        throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
    }
    
    public boolean previous() throws SQLException {
        throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
    }
    
    public boolean relative(final int rows) throws SQLException {
        throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
    }
    
    public boolean absolute(final int row) throws SQLException {
        throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
    }
    
    public void afterLast() throws SQLException {
        throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
    }
    
    public void beforeFirst() throws SQLException {
        throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
    }
    
    public boolean first() throws SQLException {
        throw new SQLException("ResultSet is TYPE_FORWARD_ONLY");
    }
    
    public void cancelRowUpdates() throws SQLException {
        throw this.unused();
    }
    
    public void deleteRow() throws SQLException {
        throw this.unused();
    }
    
    public void updateArray(final int col, final Array x) throws SQLException {
        throw this.unused();
    }
    
    public void updateArray(final String col, final Array x) throws SQLException {
        throw this.unused();
    }
    
    public void updateAsciiStream(final int col, final InputStream x, final int l) throws SQLException {
        throw this.unused();
    }
    
    public void updateAsciiStream(final String col, final InputStream x, final int l) throws SQLException {
        throw this.unused();
    }
    
    public void updateBigDecimal(final int col, final BigDecimal x) throws SQLException {
        throw this.unused();
    }
    
    public void updateBigDecimal(final String col, final BigDecimal x) throws SQLException {
        throw this.unused();
    }
    
    public void updateBinaryStream(final int c, final InputStream x, final int l) throws SQLException {
        throw this.unused();
    }
    
    public void updateBinaryStream(final String c, final InputStream x, final int l) throws SQLException {
        throw this.unused();
    }
    
    public void updateBlob(final int col, final Blob x) throws SQLException {
        throw this.unused();
    }
    
    public void updateBlob(final String col, final Blob x) throws SQLException {
        throw this.unused();
    }
    
    public void updateBoolean(final int col, final boolean x) throws SQLException {
        throw this.unused();
    }
    
    public void updateBoolean(final String col, final boolean x) throws SQLException {
        throw this.unused();
    }
    
    public void updateByte(final int col, final byte x) throws SQLException {
        throw this.unused();
    }
    
    public void updateByte(final String col, final byte x) throws SQLException {
        throw this.unused();
    }
    
    public void updateBytes(final int col, final byte[] x) throws SQLException {
        throw this.unused();
    }
    
    public void updateBytes(final String col, final byte[] x) throws SQLException {
        throw this.unused();
    }
    
    public void updateCharacterStream(final int c, final Reader x, final int l) throws SQLException {
        throw this.unused();
    }
    
    public void updateCharacterStream(final String c, final Reader r, final int l) throws SQLException {
        throw this.unused();
    }
    
    public void updateClob(final int col, final Clob x) throws SQLException {
        throw this.unused();
    }
    
    public void updateClob(final String col, final Clob x) throws SQLException {
        throw this.unused();
    }
    
    public void updateDate(final int col, final Date x) throws SQLException {
        throw this.unused();
    }
    
    public void updateDate(final String col, final Date x) throws SQLException {
        throw this.unused();
    }
    
    public void updateDouble(final int col, final double x) throws SQLException {
        throw this.unused();
    }
    
    public void updateDouble(final String col, final double x) throws SQLException {
        throw this.unused();
    }
    
    public void updateFloat(final int col, final float x) throws SQLException {
        throw this.unused();
    }
    
    public void updateFloat(final String col, final float x) throws SQLException {
        throw this.unused();
    }
    
    public void updateInt(final int col, final int x) throws SQLException {
        throw this.unused();
    }
    
    public void updateInt(final String col, final int x) throws SQLException {
        throw this.unused();
    }
    
    public void updateLong(final int col, final long x) throws SQLException {
        throw this.unused();
    }
    
    public void updateLong(final String col, final long x) throws SQLException {
        throw this.unused();
    }
    
    public void updateNull(final int col) throws SQLException {
        throw this.unused();
    }
    
    public void updateNull(final String col) throws SQLException {
        throw this.unused();
    }
    
    public void updateObject(final int c, final Object x) throws SQLException {
        throw this.unused();
    }
    
    public void updateObject(final int c, final Object x, final int s) throws SQLException {
        throw this.unused();
    }
    
    public void updateObject(final String col, final Object x) throws SQLException {
        throw this.unused();
    }
    
    public void updateObject(final String c, final Object x, final int s) throws SQLException {
        throw this.unused();
    }
    
    public void updateRef(final int col, final Ref x) throws SQLException {
        throw this.unused();
    }
    
    public void updateRef(final String c, final Ref x) throws SQLException {
        throw this.unused();
    }
    
    public void updateRow() throws SQLException {
        throw this.unused();
    }
    
    public void updateShort(final int c, final short x) throws SQLException {
        throw this.unused();
    }
    
    public void updateShort(final String c, final short x) throws SQLException {
        throw this.unused();
    }
    
    public void updateString(final int c, final String x) throws SQLException {
        throw this.unused();
    }
    
    public void updateString(final String c, final String x) throws SQLException {
        throw this.unused();
    }
    
    public void updateTime(final int c, final Time x) throws SQLException {
        throw this.unused();
    }
    
    public void updateTime(final String c, final Time x) throws SQLException {
        throw this.unused();
    }
    
    public void updateTimestamp(final int c, final Timestamp x) throws SQLException {
        throw this.unused();
    }
    
    public void updateTimestamp(final String c, final Timestamp x) throws SQLException {
        throw this.unused();
    }
    
    public void refreshRow() throws SQLException {
        throw this.unused();
    }
}
