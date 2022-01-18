// 
// Decompiled by Procyon v0.5.30
// 

package org.sqlite;

import java.sql.Struct;
import java.sql.Savepoint;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.sql.SQLWarning;
import java.sql.DatabaseMetaData;
import java.util.Map;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Connection;

class Conn implements Connection
{
    private final String url;
    private String fileName;
    private DB db;
    private MetaData meta;
    private boolean autoCommit;
    private int transactionIsolation;
    private int timeout;
    private static final String RESOURCE_NAME_PREFIX = ":resource:";
    
    public Conn(final String url, final String fileName) throws SQLException {
        this(url, fileName, new Properties());
    }
    
    public Conn(final String url, final String fileName, final Properties prop) throws SQLException {
        this.db = null;
        this.meta = null;
        this.autoCommit = true;
        this.transactionIsolation = 8;
        this.timeout = 0;
        this.url = url;
        this.fileName = fileName;
        final SQLiteConfig config = new SQLiteConfig(prop);
        this.open(config.getOpenModeFlags());
        final boolean enableSharedCache = config.isEnabledSharedCache();
        final boolean enableLoadExtension = config.isEnabledLoadExtension();
        this.db.shared_cache(enableSharedCache);
        this.db.enable_load_extension(enableLoadExtension);
        config.apply(this);
    }
    
    private void open(final int openModeFlags) throws SQLException {
        if (!":memory:".equals(this.fileName)) {
            if (this.fileName.startsWith(":resource:")) {
                final String resourceName = this.fileName.substring(":resource:".length());
                final ClassLoader contextCL = Thread.currentThread().getContextClassLoader();
                URL resourceAddr = contextCL.getResource(resourceName);
                if (resourceAddr == null) {
                    try {
                        resourceAddr = new URL(resourceName);
                    }
                    catch (MalformedURLException e) {
                        throw new SQLException(String.format("resource %s not found: %s", resourceName, e));
                    }
                }
                try {
                    this.fileName = this.extractResource(resourceAddr).getAbsolutePath();
                }
                catch (IOException e2) {
                    throw new SQLException(String.format("failed to load %s: %s", resourceName, e2));
                }
            }
            else {
                final File file = new File(this.fileName).getAbsoluteFile();
                File parent = file.getParentFile();
                if (parent != null && !parent.exists()) {
                    for (File up = parent; up != null && !up.exists(); up = up.getParentFile()) {
                        parent = up;
                    }
                    throw new SQLException("path to '" + this.fileName + "': '" + parent + "' does not exist");
                }
                try {
                    if (!file.exists() && file.createNewFile()) {
                        file.delete();
                    }
                }
                catch (Exception e3) {
                    throw new SQLException("opening db: '" + this.fileName + "': " + e3.getMessage());
                }
                this.fileName = file.getAbsolutePath();
            }
        }
        try {
            final Class<?> nativedb = Class.forName("org.sqlite.NativeDB");
            if (nativedb.getDeclaredMethod("load", (Class<?>[])null).invoke(null, (Object[])null)) {
                this.db = (DB)nativedb.newInstance();
            }
        }
        catch (Exception ex) {}
        if (this.db == null) {
            try {
                this.db = (DB)Class.forName("org.sqlite.NestedDB").newInstance();
            }
            catch (Exception e4) {
                throw new SQLException("no SQLite library found");
            }
        }
        this.db.open(this, this.fileName, openModeFlags);
        this.setTimeout(3000);
    }
    
    private File extractResource(final URL resourceAddr) throws IOException {
        if (resourceAddr.getProtocol().equals("file")) {
            try {
                return new File(resourceAddr.toURI());
            }
            catch (URISyntaxException e) {
                throw new IOException(e.getMessage());
            }
        }
        final String tempFolder = new File(System.getProperty("java.io.tmpdir")).getAbsolutePath();
        final String dbFileName = String.format("sqlite-jdbc-tmp-%d.db", resourceAddr.hashCode());
        final File dbFile = new File(tempFolder, dbFileName);
        if (dbFile.exists()) {
            final long resourceLastModified = resourceAddr.openConnection().getLastModified();
            final long tmpFileLastModified = dbFile.lastModified();
            if (resourceLastModified < tmpFileLastModified) {
                return dbFile;
            }
            final boolean deletionSucceeded = dbFile.delete();
            if (!deletionSucceeded) {
                throw new IOException("failed to remove existing DB file: " + dbFile.getAbsolutePath());
            }
        }
        final byte[] buffer = new byte[8192];
        final FileOutputStream writer = new FileOutputStream(dbFile);
        final InputStream reader = resourceAddr.openStream();
        try {
            int bytesRead = 0;
            while ((bytesRead = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, bytesRead);
            }
            return dbFile;
        }
        finally {
            writer.close();
            reader.close();
        }
    }
    
    int getTimeout() {
        return this.timeout;
    }
    
    void setTimeout(final int ms) throws SQLException {
        this.timeout = ms;
        this.db.busy_timeout(ms);
    }
    
    String url() {
        return this.url;
    }
    
    String libversion() throws SQLException {
        return this.db.libversion();
    }
    
    DB db() {
        return this.db;
    }
    
    private void checkOpen() throws SQLException {
        if (this.db == null) {
            throw new SQLException("database connection closed");
        }
    }
    
    private void checkCursor(final int rst, final int rsc, final int rsh) throws SQLException {
        if (rst != 1003) {
            throw new SQLException("SQLite only supports TYPE_FORWARD_ONLY cursors");
        }
        if (rsc != 1007) {
            throw new SQLException("SQLite only supports CONCUR_READ_ONLY cursors");
        }
        if (rsh != 2) {
            throw new SQLException("SQLite only supports closing cursors at commit");
        }
    }
    
    public void finalize() throws SQLException {
        this.close();
    }
    
    public void close() throws SQLException {
        if (this.db == null) {
            return;
        }
        if (this.meta != null) {
            this.meta.close();
        }
        this.db.close();
        this.db = null;
    }
    
    public boolean isClosed() throws SQLException {
        return this.db == null;
    }
    
    public String getCatalog() throws SQLException {
        this.checkOpen();
        return null;
    }
    
    public void setCatalog(final String catalog) throws SQLException {
        this.checkOpen();
    }
    
    public int getHoldability() throws SQLException {
        this.checkOpen();
        return 2;
    }
    
    public void setHoldability(final int h) throws SQLException {
        this.checkOpen();
        if (h != 2) {
            throw new SQLException("SQLite only supports CLOSE_CURSORS_AT_COMMIT");
        }
    }
    
    public int getTransactionIsolation() {
        return this.transactionIsolation;
    }
    
    public void setTransactionIsolation(final int level) throws SQLException {
        switch (level) {
            case 8: {
                this.db.exec("PRAGMA read_uncommitted = false;");
                break;
            }
            case 1: {
                this.db.exec("PRAGMA read_uncommitted = true;");
                break;
            }
            default: {
                throw new SQLException("SQLite supports only TRANSACTION_SERIALIZABLE and TRANSACTION_READ_UNCOMMITTED.");
            }
        }
        this.transactionIsolation = level;
    }
    
    public Map getTypeMap() throws SQLException {
        throw new SQLException("not yet implemented");
    }
    
    public void setTypeMap(final Map map) throws SQLException {
        throw new SQLException("not yet implemented");
    }
    
    public boolean isReadOnly() throws SQLException {
        return false;
    }
    
    public void setReadOnly(final boolean ro) throws SQLException {
    }
    
    public DatabaseMetaData getMetaData() {
        if (this.meta == null) {
            this.meta = new MetaData(this);
        }
        return this.meta;
    }
    
    public String nativeSQL(final String sql) {
        return sql;
    }
    
    public void clearWarnings() throws SQLException {
    }
    
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }
    
    public boolean getAutoCommit() throws SQLException {
        this.checkOpen();
        return this.autoCommit;
    }
    
    public void setAutoCommit(final boolean ac) throws SQLException {
        this.checkOpen();
        if (this.autoCommit == ac) {
            return;
        }
        this.autoCommit = ac;
        this.db.exec(this.autoCommit ? "commit;" : "begin;");
    }
    
    public void commit() throws SQLException {
        this.checkOpen();
        if (this.autoCommit) {
            throw new SQLException("database in auto-commit mode");
        }
        this.db.exec("commit;");
        this.db.exec("begin;");
    }
    
    public void rollback() throws SQLException {
        this.checkOpen();
        if (this.autoCommit) {
            throw new SQLException("database in auto-commit mode");
        }
        this.db.exec("rollback;");
        this.db.exec("begin;");
    }
    
    public Statement createStatement() throws SQLException {
        return this.createStatement(1003, 1007, 2);
    }
    
    public Statement createStatement(final int rsType, final int rsConcurr) throws SQLException {
        return this.createStatement(rsType, rsConcurr, 2);
    }
    
    public Statement createStatement(final int rst, final int rsc, final int rsh) throws SQLException {
        this.checkCursor(rst, rsc, rsh);
        return new Stmt(this);
    }
    
    public CallableStatement prepareCall(final String sql) throws SQLException {
        return this.prepareCall(sql, 1003, 1007, 2);
    }
    
    public CallableStatement prepareCall(final String sql, final int rst, final int rsc) throws SQLException {
        return this.prepareCall(sql, rst, rsc, 2);
    }
    
    public CallableStatement prepareCall(final String sql, final int rst, final int rsc, final int rsh) throws SQLException {
        throw new SQLException("SQLite does not support Stored Procedures");
    }
    
    public PreparedStatement prepareStatement(final String sql) throws SQLException {
        return this.prepareStatement(sql, 1003, 1007);
    }
    
    public PreparedStatement prepareStatement(final String sql, final int autoC) throws SQLException {
        return this.prepareStatement(sql);
    }
    
    public PreparedStatement prepareStatement(final String sql, final int[] colInds) throws SQLException {
        return this.prepareStatement(sql);
    }
    
    public PreparedStatement prepareStatement(final String sql, final String[] colNames) throws SQLException {
        return this.prepareStatement(sql);
    }
    
    public PreparedStatement prepareStatement(final String sql, final int rst, final int rsc) throws SQLException {
        return this.prepareStatement(sql, rst, rsc, 2);
    }
    
    public PreparedStatement prepareStatement(final String sql, final int rst, final int rsc, final int rsh) throws SQLException {
        this.checkCursor(rst, rsc, rsh);
        return new PrepStmt(this, sql);
    }
    
    String getDriverVersion() {
        if (this.db != null) {
            final String dbname = this.db.getClass().getName();
            if (dbname.indexOf("NestedDB") >= 0) {
                return "pure";
            }
            if (dbname.indexOf("NativeDB") >= 0) {
                return "native";
            }
        }
        return "unloaded";
    }
    
    public Savepoint setSavepoint() throws SQLException {
        throw new SQLException("unsupported by SQLite: savepoints");
    }
    
    public Savepoint setSavepoint(final String name) throws SQLException {
        throw new SQLException("unsupported by SQLite: savepoints");
    }
    
    public void releaseSavepoint(final Savepoint savepoint) throws SQLException {
        throw new SQLException("unsupported by SQLite: savepoints");
    }
    
    public void rollback(final Savepoint savepoint) throws SQLException {
        throw new SQLException("unsupported by SQLite: savepoints");
    }
    
    public Struct createStruct(final String t, final Object[] attr) throws SQLException {
        throw new SQLException("unsupported by SQLite");
    }
}
