// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Driver;

public class ReplicationDriver extends NonRegisteringReplicationDriver implements Driver
{
    static {
        try {
            DriverManager.registerDriver(new NonRegisteringReplicationDriver());
        }
        catch (SQLException E) {
            throw new RuntimeException("Can't register driver!");
        }
    }
}
