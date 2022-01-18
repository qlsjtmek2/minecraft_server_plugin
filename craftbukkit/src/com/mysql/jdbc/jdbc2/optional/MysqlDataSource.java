// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.jdbc2.optional;

import java.util.Hashtable;
import java.util.Iterator;
import javax.naming.NamingException;
import javax.naming.RefAddr;
import javax.naming.StringRefAddr;
import javax.naming.Reference;
import java.util.Properties;
import java.sql.SQLException;
import java.sql.Connection;
import java.io.PrintWriter;
import com.mysql.jdbc.NonRegisteringDriver;
import java.io.Serializable;
import javax.naming.Referenceable;
import javax.sql.DataSource;
import com.mysql.jdbc.ConnectionPropertiesImpl;

public class MysqlDataSource extends ConnectionPropertiesImpl implements DataSource, Referenceable, Serializable
{
    protected static NonRegisteringDriver mysqlDriver;
    protected PrintWriter logWriter;
    protected String databaseName;
    protected String encoding;
    protected String hostName;
    protected String password;
    protected String profileSql;
    protected String url;
    protected String user;
    protected boolean explicitUrl;
    protected int port;
    
    public MysqlDataSource() {
        this.logWriter = null;
        this.databaseName = null;
        this.encoding = null;
        this.hostName = null;
        this.password = null;
        this.profileSql = "false";
        this.url = null;
        this.user = null;
        this.explicitUrl = false;
        this.port = 3306;
    }
    
    public Connection getConnection() throws SQLException {
        return this.getConnection(this.user, this.password);
    }
    
    public Connection getConnection(final String userID, final String pass) throws SQLException {
        final Properties props = new Properties();
        if (userID != null) {
            props.setProperty("user", userID);
        }
        if (pass != null) {
            props.setProperty("password", pass);
        }
        this.exposeAsProperties(props);
        return this.getConnection(props);
    }
    
    public void setDatabaseName(final String dbName) {
        this.databaseName = dbName;
    }
    
    public String getDatabaseName() {
        return (this.databaseName != null) ? this.databaseName : "";
    }
    
    public void setLogWriter(final PrintWriter output) throws SQLException {
        this.logWriter = output;
    }
    
    public PrintWriter getLogWriter() {
        return this.logWriter;
    }
    
    public void setLoginTimeout(final int seconds) throws SQLException {
    }
    
    public int getLoginTimeout() {
        return 0;
    }
    
    public void setPassword(final String pass) {
        this.password = pass;
    }
    
    public void setPort(final int p) {
        this.port = p;
    }
    
    public int getPort() {
        return this.port;
    }
    
    public void setPortNumber(final int p) {
        this.setPort(p);
    }
    
    public int getPortNumber() {
        return this.getPort();
    }
    
    public void setPropertiesViaRef(final Reference ref) throws SQLException {
        super.initializeFromRef(ref);
    }
    
    public Reference getReference() throws NamingException {
        final String factoryName = "com.mysql.jdbc.jdbc2.optional.MysqlDataSourceFactory";
        final Reference ref = new Reference(this.getClass().getName(), factoryName, null);
        ref.add(new StringRefAddr("user", this.getUser()));
        ref.add(new StringRefAddr("password", this.password));
        ref.add(new StringRefAddr("serverName", this.getServerName()));
        ref.add(new StringRefAddr("port", "" + this.getPort()));
        ref.add(new StringRefAddr("databaseName", this.getDatabaseName()));
        ref.add(new StringRefAddr("url", this.getUrl()));
        ref.add(new StringRefAddr("explicitUrl", String.valueOf(this.explicitUrl)));
        try {
            this.storeToRef(ref);
        }
        catch (SQLException sqlEx) {
            throw new NamingException(sqlEx.getMessage());
        }
        return ref;
    }
    
    public void setServerName(final String serverName) {
        this.hostName = serverName;
    }
    
    public String getServerName() {
        return (this.hostName != null) ? this.hostName : "";
    }
    
    public void setURL(final String url) {
        this.setUrl(url);
    }
    
    public String getURL() {
        return this.getUrl();
    }
    
    public void setUrl(final String url) {
        this.url = url;
        this.explicitUrl = true;
    }
    
    public String getUrl() {
        if (!this.explicitUrl) {
            String builtUrl = "jdbc:mysql://";
            builtUrl = builtUrl + this.getServerName() + ":" + this.getPort() + "/" + this.getDatabaseName();
            return builtUrl;
        }
        return this.url;
    }
    
    public void setUser(final String userID) {
        this.user = userID;
    }
    
    public String getUser() {
        return this.user;
    }
    
    protected Connection getConnection(final Properties props) throws SQLException {
        String jdbcUrlToUse = null;
        if (!this.explicitUrl) {
            final StringBuffer jdbcUrl = new StringBuffer("jdbc:mysql://");
            if (this.hostName != null) {
                jdbcUrl.append(this.hostName);
            }
            jdbcUrl.append(":");
            jdbcUrl.append(this.port);
            jdbcUrl.append("/");
            if (this.databaseName != null) {
                jdbcUrl.append(this.databaseName);
            }
            jdbcUrlToUse = jdbcUrl.toString();
        }
        else {
            jdbcUrlToUse = this.url;
        }
        final Properties urlProps = MysqlDataSource.mysqlDriver.parseURL(jdbcUrlToUse, null);
        urlProps.remove("DBNAME");
        urlProps.remove("HOST");
        urlProps.remove("PORT");
        for (final String key : ((Hashtable<Object, V>)urlProps).keySet()) {
            props.setProperty(key, urlProps.getProperty(key));
        }
        return MysqlDataSource.mysqlDriver.connect(jdbcUrlToUse, props);
    }
    
    static {
        MysqlDataSource.mysqlDriver = null;
        try {
            MysqlDataSource.mysqlDriver = new NonRegisteringDriver();
        }
        catch (Exception E) {
            throw new RuntimeException("Can not load Driver class com.mysql.jdbc.Driver");
        }
    }
}
