// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.NClob;
import java.sql.Clob;
import java.sql.Blob;
import java.sql.SQLClientInfoException;
import java.util.Properties;
import java.sql.Struct;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.SQLXML;

public interface JDBC4MySQLConnection extends MySQLConnection
{
    SQLXML createSQLXML() throws SQLException;
    
    Array createArrayOf(final String p0, final Object[] p1) throws SQLException;
    
    Struct createStruct(final String p0, final Object[] p1) throws SQLException;
    
    Properties getClientInfo() throws SQLException;
    
    String getClientInfo(final String p0) throws SQLException;
    
    boolean isValid(final int p0) throws SQLException;
    
    void setClientInfo(final Properties p0) throws SQLClientInfoException;
    
    void setClientInfo(final String p0, final String p1) throws SQLClientInfoException;
    
    boolean isWrapperFor(final Class<?> p0) throws SQLException;
    
     <T> T unwrap(final Class<T> p0) throws SQLException;
    
    Blob createBlob();
    
    Clob createClob();
    
    NClob createNClob();
}
