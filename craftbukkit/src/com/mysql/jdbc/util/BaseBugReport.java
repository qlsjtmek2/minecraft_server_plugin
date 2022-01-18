// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.util;

import java.util.Properties;
import java.sql.SQLException;
import com.mysql.jdbc.Driver;
import java.sql.Connection;

public abstract class BaseBugReport
{
    private Connection conn;
    private Driver driver;
    
    public BaseBugReport() {
        try {
            this.driver = new Driver();
        }
        catch (SQLException ex) {
            throw new RuntimeException(ex.toString());
        }
    }
    
    public abstract void setUp() throws Exception;
    
    public abstract void tearDown() throws Exception;
    
    public abstract void runTest() throws Exception;
    
    public final void run() throws Exception {
        try {
            this.setUp();
            this.runTest();
        }
        finally {
            this.tearDown();
        }
    }
    
    protected final void assertTrue(final String message, final boolean condition) throws Exception {
        if (!condition) {
            throw new Exception("Assertion failed: " + message);
        }
    }
    
    protected final void assertTrue(final boolean condition) throws Exception {
        this.assertTrue("(no message given)", condition);
    }
    
    public String getUrl() {
        return "jdbc:mysql:///test";
    }
    
    public final synchronized Connection getConnection() throws SQLException {
        if (this.conn == null || this.conn.isClosed()) {
            this.conn = this.getNewConnection();
        }
        return this.conn;
    }
    
    public final synchronized Connection getNewConnection() throws SQLException {
        return this.getConnection(this.getUrl());
    }
    
    public final synchronized Connection getConnection(final String url) throws SQLException {
        return this.getConnection(url, null);
    }
    
    public final synchronized Connection getConnection(final String url, final Properties props) throws SQLException {
        return this.driver.connect(url, props);
    }
}
