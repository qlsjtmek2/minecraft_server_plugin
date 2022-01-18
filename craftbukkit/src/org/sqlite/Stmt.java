// 
// Decompiled by Procyon v0.5.30
// 

package org.sqlite;

import java.sql.Connection;
import java.sql.SQLWarning;
import java.sql.BatchUpdateException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class Stmt extends Unused implements Statement, Codes
{
    final Conn conn;
    final DB db;
    final RS rs;
    long pointer;
    String sql;
    int batchPos;
    Object[] batch;
    boolean resultsWaiting;
    
    Stmt(final Conn c) {
        this.sql = null;
        this.batch = null;
        this.resultsWaiting = false;
        this.conn = c;
        this.db = this.conn.db();
        this.rs = new RS(this);
    }
    
    protected final void checkOpen() throws SQLException {
        if (this.pointer == 0L) {
            throw new SQLException("statement is not executing");
        }
    }
    
    boolean isOpen() throws SQLException {
        return this.pointer != 0L;
    }
    
    protected boolean exec() throws SQLException {
        if (this.sql == null) {
            throw new SQLException("SQLiteJDBC internal error: sql==null");
        }
        if (this.rs.isOpen()) {
            throw new SQLException("SQLite JDBC internal error: rs.isOpen() on exec.");
        }
        boolean rc = false;
        try {
            rc = this.db.execute(this, null);
        }
        finally {
            this.resultsWaiting = rc;
        }
        return this.db.column_count(this.pointer) != 0;
    }
    
    protected boolean exec(final String sql) throws SQLException {
        if (sql == null) {
            throw new SQLException("SQLiteJDBC internal error: sql==null");
        }
        if (this.rs.isOpen()) {
            throw new SQLException("SQLite JDBC internal error: rs.isOpen() on exec.");
        }
        boolean rc = false;
        try {
            rc = this.db.execute(sql);
        }
        finally {
            this.resultsWaiting = rc;
        }
        return this.db.column_count(this.pointer) != 0;
    }
    
    public void close() throws SQLException {
        if (this.pointer == 0L) {
            return;
        }
        this.rs.close();
        this.batch = null;
        this.batchPos = 0;
        final int resp = this.db.finalize(this);
        if (resp != 0 && resp != 21) {
            this.db.throwex();
        }
    }
    
    protected void finalize() throws SQLException {
        this.close();
    }
    
    public boolean execute(final String sql) throws SQLException {
        this.close();
        this.sql = sql;
        this.db.prepare(this);
        return this.exec();
    }
    
    public ResultSet executeQuery(final String sql) throws SQLException {
        this.close();
        this.sql = sql;
        this.db.prepare(this);
        if (!this.exec()) {
            this.close();
            throw new SQLException("query does not return ResultSet");
        }
        return this.getResultSet();
    }
    
    public int executeUpdate(final String sql) throws SQLException {
        this.close();
        this.sql = sql;
        int changes = 0;
        final ExtendedCommand.SQLExtension ext = ExtendedCommand.parse(sql);
        if (ext != null) {
            ext.execute(this.db);
        }
        else {
            try {
                final int statusCode = this.db._exec(sql);
                if (statusCode != 0) {
                    throw DB.newSQLException(statusCode, "");
                }
                changes = this.db.changes();
            }
            finally {
                this.close();
            }
        }
        return changes;
    }
    
    public ResultSet getResultSet() throws SQLException {
        this.checkOpen();
        if (this.rs.isOpen()) {
            throw new SQLException("ResultSet already requested");
        }
        if (this.db.column_count(this.pointer) == 0) {
            throw new SQLException("no ResultSet available");
        }
        if (this.rs.colsMeta == null) {
            this.rs.colsMeta = this.db.column_names(this.pointer);
        }
        this.rs.cols = this.rs.colsMeta;
        this.rs.open = this.resultsWaiting;
        this.resultsWaiting = false;
        return this.rs;
    }
    
    public int getUpdateCount() throws SQLException {
        if (this.pointer != 0L && !this.rs.isOpen() && !this.resultsWaiting && this.db.column_count(this.pointer) == 0) {
            return this.db.changes();
        }
        return -1;
    }
    
    public void addBatch(final String sql) throws SQLException {
        this.close();
        if (this.batch == null || this.batchPos + 1 >= this.batch.length) {
            final Object[] nb = new Object[Math.max(10, this.batchPos * 2)];
            if (this.batch != null) {
                System.arraycopy(this.batch, 0, nb, 0, this.batch.length);
            }
            this.batch = nb;
        }
        this.batch[this.batchPos++] = sql;
    }
    
    public void clearBatch() throws SQLException {
        this.batchPos = 0;
        if (this.batch != null) {
            for (int i = 0; i < this.batch.length; ++i) {
                this.batch[i] = null;
            }
        }
    }
    
    public int[] executeBatch() throws SQLException {
        this.close();
        if (this.batch == null || this.batchPos == 0) {
            return new int[0];
        }
        final int[] changes = new int[this.batchPos];
        synchronized (this.db) {
            try {
                for (int i = 0; i < changes.length; ++i) {
                    try {
                        this.sql = (String)this.batch[i];
                        this.db.prepare(this);
                        changes[i] = this.db.executeUpdate(this, null);
                    }
                    catch (SQLException e) {
                        throw new BatchUpdateException("batch entry " + i + ": " + e.getMessage(), changes);
                    }
                    finally {
                        this.db.finalize(this);
                    }
                }
            }
            finally {
                this.clearBatch();
            }
        }
        return changes;
    }
    
    public void setCursorName(final String name) {
    }
    
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }
    
    public void clearWarnings() throws SQLException {
    }
    
    public Connection getConnection() throws SQLException {
        return this.conn;
    }
    
    public void cancel() throws SQLException {
        this.rs.checkOpen();
        this.db.interrupt();
    }
    
    public int getQueryTimeout() throws SQLException {
        return this.conn.getTimeout();
    }
    
    public void setQueryTimeout(final int seconds) throws SQLException {
        if (seconds < 0) {
            throw new SQLException("query timeout must be >= 0");
        }
        this.conn.setTimeout(1000 * seconds);
    }
    
    public int getMaxRows() throws SQLException {
        return this.rs.maxRows;
    }
    
    public void setMaxRows(final int max) throws SQLException {
        if (max < 0) {
            throw new SQLException("max row count must be >= 0");
        }
        this.rs.maxRows = max;
    }
    
    public int getMaxFieldSize() throws SQLException {
        return 0;
    }
    
    public void setMaxFieldSize(final int max) throws SQLException {
        if (max < 0) {
            throw new SQLException("max field size " + max + " cannot be negative");
        }
    }
    
    public int getFetchSize() throws SQLException {
        return this.rs.getFetchSize();
    }
    
    public void setFetchSize(final int r) throws SQLException {
        this.rs.setFetchSize(r);
    }
    
    public int getFetchDirection() throws SQLException {
        return this.rs.getFetchDirection();
    }
    
    public void setFetchDirection(final int d) throws SQLException {
        this.rs.setFetchDirection(d);
    }
    
    public ResultSet getGeneratedKeys() throws SQLException {
        return ((MetaData)this.conn.getMetaData()).getGeneratedKeys();
    }
    
    public boolean getMoreResults() throws SQLException {
        return this.getMoreResults(0);
    }
    
    public boolean getMoreResults(final int c) throws SQLException {
        this.checkOpen();
        this.close();
        return false;
    }
    
    public int getResultSetConcurrency() throws SQLException {
        return 1007;
    }
    
    public int getResultSetHoldability() throws SQLException {
        return 2;
    }
    
    public int getResultSetType() throws SQLException {
        return 1003;
    }
    
    static class BackupObserver implements DB.ProgressObserver
    {
        public void progress(final int remaining, final int pageCount) {
            System.out.println(String.format("remaining:%d, page count:%d", remaining, pageCount));
        }
    }
}
