// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.sql.Array;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;
import java.sql.SQLException;

public interface DataReader
{
    void close() throws SQLException;
    
    boolean next() throws SQLException;
    
    void resetColumnPosition();
    
    void incrementPos(final int p0);
    
    byte[] getBinaryBytes() throws SQLException;
    
    byte[] getBlobBytes() throws SQLException;
    
    String getStringFromStream() throws SQLException;
    
    String getStringClob() throws SQLException;
    
    String getString() throws SQLException;
    
    Boolean getBoolean() throws SQLException;
    
    Byte getByte() throws SQLException;
    
    Short getShort() throws SQLException;
    
    Integer getInt() throws SQLException;
    
    Long getLong() throws SQLException;
    
    Float getFloat() throws SQLException;
    
    Double getDouble() throws SQLException;
    
    byte[] getBytes() throws SQLException;
    
    Date getDate() throws SQLException;
    
    Time getTime() throws SQLException;
    
    Timestamp getTimestamp() throws SQLException;
    
    BigDecimal getBigDecimal() throws SQLException;
    
    Array getArray() throws SQLException;
}
