// 
// Decompiled by Procyon v0.5.30
// 

package org.sqlite;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverPropertyInfo;
import java.util.Properties;
import java.sql.Driver;

public class JDBC implements Driver
{
    public static final String PREFIX = "jdbc:sqlite:";
    
    public int getMajorVersion() {
        return SQLiteJDBCLoader.getMajorVersion();
    }
    
    public int getMinorVersion() {
        return SQLiteJDBCLoader.getMinorVersion();
    }
    
    public boolean jdbcCompliant() {
        return false;
    }
    
    public boolean acceptsURL(final String url) {
        return isValidURL(url);
    }
    
    public static boolean isValidURL(final String url) {
        return url != null && url.toLowerCase().startsWith("jdbc:sqlite:");
    }
    
    public DriverPropertyInfo[] getPropertyInfo(final String url, final Properties info) throws SQLException {
        return SQLiteConfig.getDriverPropertyInfo();
    }
    
    public Connection connect(final String url, final Properties info) throws SQLException {
        return createConnection(url, info);
    }
    
    static String extractAddress(final String url) {
        return "jdbc:sqlite:".equalsIgnoreCase(url) ? ":memory:" : url.substring("jdbc:sqlite:".length());
    }
    
    public static Connection createConnection(String url, final Properties prop) throws SQLException {
        if (!isValidURL(url)) {
            throw new SQLException("invalid database address: " + url);
        }
        url = url.trim();
        return new Conn(url, extractAddress(url), prop);
    }
    
    static {
        try {
            DriverManager.registerDriver(new JDBC());
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
