// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.net.URL;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Ref;
import java.sql.Date;
import java.sql.Clob;
import java.io.Reader;
import java.sql.Blob;
import java.math.BigDecimal;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Array;

public interface ParameterBindings
{
    Array getArray(final int p0) throws SQLException;
    
    InputStream getAsciiStream(final int p0) throws SQLException;
    
    BigDecimal getBigDecimal(final int p0) throws SQLException;
    
    InputStream getBinaryStream(final int p0) throws SQLException;
    
    Blob getBlob(final int p0) throws SQLException;
    
    boolean getBoolean(final int p0) throws SQLException;
    
    byte getByte(final int p0) throws SQLException;
    
    byte[] getBytes(final int p0) throws SQLException;
    
    Reader getCharacterStream(final int p0) throws SQLException;
    
    Clob getClob(final int p0) throws SQLException;
    
    Date getDate(final int p0) throws SQLException;
    
    double getDouble(final int p0) throws SQLException;
    
    float getFloat(final int p0) throws SQLException;
    
    int getInt(final int p0) throws SQLException;
    
    long getLong(final int p0) throws SQLException;
    
    Reader getNCharacterStream(final int p0) throws SQLException;
    
    Reader getNClob(final int p0) throws SQLException;
    
    Object getObject(final int p0) throws SQLException;
    
    Ref getRef(final int p0) throws SQLException;
    
    short getShort(final int p0) throws SQLException;
    
    String getString(final int p0) throws SQLException;
    
    Time getTime(final int p0) throws SQLException;
    
    Timestamp getTimestamp(final int p0) throws SQLException;
    
    URL getURL(final int p0) throws SQLException;
    
    boolean isNull(final int p0) throws SQLException;
}
