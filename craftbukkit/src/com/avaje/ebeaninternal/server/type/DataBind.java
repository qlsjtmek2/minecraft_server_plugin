// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.Reader;
import java.io.StringReader;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Date;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.PreparedStatement;

public class DataBind
{
    private final PreparedStatement pstmt;
    private int pos;
    
    public DataBind(final PreparedStatement pstmt) {
        this.pstmt = pstmt;
    }
    
    public void close() throws SQLException {
        this.pstmt.close();
    }
    
    public int currentPos() {
        return this.pos;
    }
    
    public void resetPos() {
        this.pos = 0;
    }
    
    public void setObject(final Object value) throws SQLException {
        this.pstmt.setObject(++this.pos, value);
    }
    
    public void setObject(final Object value, final int sqlType) throws SQLException {
        this.pstmt.setObject(++this.pos, value, sqlType);
    }
    
    public void setNull(final int jdbcType) throws SQLException {
        this.pstmt.setNull(++this.pos, jdbcType);
    }
    
    public int nextPos() {
        return ++this.pos;
    }
    
    public int decrementPos() {
        return ++this.pos;
    }
    
    public int executeUpdate() throws SQLException {
        return this.pstmt.executeUpdate();
    }
    
    public PreparedStatement getPstmt() {
        return this.pstmt;
    }
    
    public void setString(final String s) throws SQLException {
        this.pstmt.setString(++this.pos, s);
    }
    
    public void setInt(final int i) throws SQLException {
        this.pstmt.setInt(++this.pos, i);
    }
    
    public void setLong(final long i) throws SQLException {
        this.pstmt.setLong(++this.pos, i);
    }
    
    public void setShort(final short i) throws SQLException {
        this.pstmt.setShort(++this.pos, i);
    }
    
    public void setFloat(final float i) throws SQLException {
        this.pstmt.setFloat(++this.pos, i);
    }
    
    public void setDouble(final double i) throws SQLException {
        this.pstmt.setDouble(++this.pos, i);
    }
    
    public void setBigDecimal(final BigDecimal v) throws SQLException {
        this.pstmt.setBigDecimal(++this.pos, v);
    }
    
    public void setDate(final Date v) throws SQLException {
        this.pstmt.setDate(++this.pos, v);
    }
    
    public void setTimestamp(final Timestamp v) throws SQLException {
        this.pstmt.setTimestamp(++this.pos, v);
    }
    
    public void setTime(final Time v) throws SQLException {
        this.pstmt.setTime(++this.pos, v);
    }
    
    public void setBoolean(final boolean v) throws SQLException {
        this.pstmt.setBoolean(++this.pos, v);
    }
    
    public void setBytes(final byte[] v) throws SQLException {
        this.pstmt.setBytes(++this.pos, v);
    }
    
    public void setByte(final byte v) throws SQLException {
        this.pstmt.setByte(++this.pos, v);
    }
    
    public void setChar(final char v) throws SQLException {
        this.pstmt.setString(++this.pos, String.valueOf(v));
    }
    
    public void setBlob(final byte[] bytes) throws SQLException {
        final ByteArrayInputStream is = new ByteArrayInputStream(bytes);
        this.pstmt.setBinaryStream(++this.pos, is, bytes.length);
    }
    
    public void setClob(final String content) throws SQLException {
        final Reader reader = new StringReader(content);
        this.pstmt.setCharacterStream(++this.pos, reader, content.length());
    }
}
