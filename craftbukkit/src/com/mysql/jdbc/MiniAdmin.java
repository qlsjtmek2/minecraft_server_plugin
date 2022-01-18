// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.Properties;
import java.sql.SQLException;

public class MiniAdmin
{
    private Connection conn;
    
    public MiniAdmin(final java.sql.Connection conn) throws SQLException {
        if (conn == null) {
            throw SQLError.createSQLException(Messages.getString("MiniAdmin.0"), "S1000", ((ConnectionImpl)conn).getExceptionInterceptor());
        }
        if (!(conn instanceof Connection)) {
            throw SQLError.createSQLException(Messages.getString("MiniAdmin.1"), "S1000", ((ConnectionImpl)conn).getExceptionInterceptor());
        }
        this.conn = (Connection)conn;
    }
    
    public MiniAdmin(final String jdbcUrl) throws SQLException {
        this(jdbcUrl, new Properties());
    }
    
    public MiniAdmin(final String jdbcUrl, final Properties props) throws SQLException {
        this.conn = (Connection)new Driver().connect(jdbcUrl, props);
    }
    
    public void shutdown() throws SQLException {
        this.conn.shutdownServer();
    }
}
