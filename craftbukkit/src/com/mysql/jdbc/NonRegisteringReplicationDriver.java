// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.Connection;
import java.util.Properties;
import java.sql.SQLException;

public class NonRegisteringReplicationDriver extends NonRegisteringDriver
{
    public Connection connect(final String url, final Properties info) throws SQLException {
        return this.connectReplicationConnection(url, info);
    }
}
