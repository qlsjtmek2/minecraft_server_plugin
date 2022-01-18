// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;

public interface JDBC4ClientInfoProvider
{
    void initialize(final Connection p0, final Properties p1) throws SQLException;
    
    void destroy() throws SQLException;
    
    Properties getClientInfo(final Connection p0) throws SQLException;
    
    String getClientInfo(final Connection p0, final String p1) throws SQLException;
    
    void setClientInfo(final Connection p0, final Properties p1) throws SQLClientInfoException;
    
    void setClientInfo(final Connection p0, final String p1, final String p2) throws SQLClientInfoException;
}
