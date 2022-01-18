// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.jdbc2.optional;

import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import javax.sql.PooledConnection;
import javax.sql.ConnectionPoolDataSource;

public class MysqlConnectionPoolDataSource extends MysqlDataSource implements ConnectionPoolDataSource
{
    public synchronized PooledConnection getPooledConnection() throws SQLException {
        final java.sql.Connection connection = this.getConnection();
        final MysqlPooledConnection mysqlPooledConnection = MysqlPooledConnection.getInstance((Connection)connection);
        return mysqlPooledConnection;
    }
    
    public synchronized PooledConnection getPooledConnection(final String s, final String s1) throws SQLException {
        final java.sql.Connection connection = this.getConnection(s, s1);
        final MysqlPooledConnection mysqlPooledConnection = MysqlPooledConnection.getInstance((Connection)connection);
        return mysqlPooledConnection;
    }
}
