// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.config.dbplatform.HsqldbPlatform;
import com.avaje.ebean.config.dbplatform.H2Platform;
import java.sql.DatabaseMetaData;
import java.sql.Connection;
import java.util.logging.Level;
import javax.sql.DataSource;
import java.sql.SQLException;
import com.avaje.ebean.config.dbplatform.SQLitePlatform;
import com.avaje.ebean.config.dbplatform.MySqlPlatform;
import com.avaje.ebean.config.dbplatform.MsSqlServer2000Platform;
import com.avaje.ebean.config.dbplatform.MsSqlServer2005Platform;
import com.avaje.ebean.config.dbplatform.Oracle10Platform;
import com.avaje.ebean.config.dbplatform.Oracle9Platform;
import com.avaje.ebean.config.dbplatform.PostgresPlatform;
import javax.persistence.PersistenceException;
import com.avaje.ebean.config.dbplatform.DatabasePlatform;
import com.avaje.ebean.config.ServerConfig;
import java.util.logging.Logger;

public class DatabasePlatformFactory
{
    private static final Logger logger;
    
    public DatabasePlatform create(final ServerConfig serverConfig) {
        try {
            if (serverConfig.getDatabasePlatformName() != null) {
                return this.byDatabaseName(serverConfig.getDatabasePlatformName());
            }
            if (serverConfig.getDataSourceConfig().isOffline()) {
                final String m = "You must specify a DatabasePlatformName when you are offline";
                throw new PersistenceException(m);
            }
            return this.byDataSource(serverConfig.getDataSource());
        }
        catch (Exception ex) {
            throw new PersistenceException(ex);
        }
    }
    
    private DatabasePlatform byDatabaseName(String dbName) throws SQLException {
        dbName = dbName.toLowerCase();
        if (dbName.equals("postgres83")) {
            return new PostgresPlatform();
        }
        if (dbName.equals("oracle9")) {
            return new Oracle9Platform();
        }
        if (dbName.equals("oracle10")) {
            return new Oracle10Platform();
        }
        if (dbName.equals("oracle")) {
            return new Oracle10Platform();
        }
        if (dbName.equals("sqlserver2005")) {
            return new MsSqlServer2005Platform();
        }
        if (dbName.equals("sqlserver2000")) {
            return new MsSqlServer2000Platform();
        }
        if (dbName.equals("mysql")) {
            return new MySqlPlatform();
        }
        if (dbName.equals("sqlite")) {
            return new SQLitePlatform();
        }
        throw new RuntimeException("database platform " + dbName + " is not known?");
    }
    
    private DatabasePlatform byDataSource(final DataSource dataSource) {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
            final DatabaseMetaData metaData = conn.getMetaData();
            return this.byDatabaseMeta(metaData);
        }
        catch (SQLException ex) {
            throw new PersistenceException(ex);
        }
        finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            }
            catch (SQLException ex2) {
                DatabasePlatformFactory.logger.log(Level.SEVERE, null, ex2);
            }
        }
    }
    
    private DatabasePlatform byDatabaseMeta(final DatabaseMetaData metaData) throws SQLException {
        String dbProductName = metaData.getDatabaseProductName();
        dbProductName = dbProductName.toLowerCase();
        final int majorVersion = metaData.getDatabaseMajorVersion();
        if (dbProductName.indexOf("oracle") > -1) {
            if (majorVersion > 9) {
                return new Oracle10Platform();
            }
            return new Oracle9Platform();
        }
        else if (dbProductName.indexOf("microsoft") > -1) {
            if (majorVersion > 8) {
                return new MsSqlServer2005Platform();
            }
            return new MsSqlServer2000Platform();
        }
        else {
            if (dbProductName.indexOf("mysql") > -1) {
                return new MySqlPlatform();
            }
            if (dbProductName.indexOf("h2") > -1) {
                return new H2Platform();
            }
            if (dbProductName.indexOf("hsql database engine") > -1) {
                return new HsqldbPlatform();
            }
            if (dbProductName.indexOf("postgres") > -1) {
                return new PostgresPlatform();
            }
            if (dbProductName.indexOf("sqlite") > -1) {
                return new SQLitePlatform();
            }
            return new DatabasePlatform();
        }
    }
    
    static {
        logger = Logger.getLogger(DatabasePlatformFactory.class.getName());
    }
}
