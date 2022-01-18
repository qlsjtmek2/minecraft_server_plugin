// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.jdbc2.optional;

import com.mysql.jdbc.ConnectionImpl;
import java.sql.SQLException;
import java.sql.Connection;
import javax.sql.XAConnection;
import javax.sql.XADataSource;

public class MysqlXADataSource extends MysqlDataSource implements XADataSource
{
    public XAConnection getXAConnection() throws SQLException {
        final Connection conn = this.getConnection();
        return this.wrapConnection(conn);
    }
    
    public XAConnection getXAConnection(final String u, final String p) throws SQLException {
        final Connection conn = this.getConnection(u, p);
        return this.wrapConnection(conn);
    }
    
    private XAConnection wrapConnection(final Connection conn) throws SQLException {
        if (this.getPinGlobalTxToPhysicalConnection() || ((com.mysql.jdbc.Connection)conn).getPinGlobalTxToPhysicalConnection()) {
            return SuspendableXAConnection.getInstance((ConnectionImpl)conn);
        }
        return MysqlXAConnection.getInstance((ConnectionImpl)conn, this.getLogXaCommands());
    }
}
