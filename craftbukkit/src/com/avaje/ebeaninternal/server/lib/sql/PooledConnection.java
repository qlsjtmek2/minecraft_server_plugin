// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.sql;

import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.sql.Savepoint;
import java.sql.CallableStatement;
import java.sql.SQLWarning;
import java.util.Map;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Iterator;
import java.util.logging.Level;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.logging.Logger;
import com.avaje.ebeaninternal.jdbc.ConnectionDelegator;

public class PooledConnection extends ConnectionDelegator
{
    private static final Logger logger;
    private static String IDLE_CONNECTION_ACCESSED_ERROR;
    static final int STATUS_IDLE = 88;
    static final int STATUS_ACTIVE = 89;
    static final int STATUS_ENDED = 87;
    final String name;
    final DataSourcePool pool;
    final Connection connection;
    final long creationTime;
    final PstmtCache pstmtCache;
    final Object pstmtMonitor;
    int status;
    boolean longRunning;
    boolean hadErrors;
    long startUseTime;
    long lastUseTime;
    String lastStatement;
    int pstmtHitCounter;
    int pstmtMissCounter;
    String createdByMethod;
    StackTraceElement[] stackTrace;
    int maxStackTrace;
    int slotId;
    boolean resetIsolationReadOnlyRequired;
    
    public PooledConnection(final DataSourcePool pool, final int uniqueId, final Connection connection) throws SQLException {
        super(connection);
        this.pstmtMonitor = new Object();
        this.status = 88;
        this.resetIsolationReadOnlyRequired = false;
        this.pool = pool;
        this.connection = connection;
        this.name = pool.getName() + "." + uniqueId;
        this.pstmtCache = new PstmtCache(this.name, pool.getPstmtCacheSize());
        this.maxStackTrace = pool.getMaxStackTraceSize();
        this.creationTime = System.currentTimeMillis();
        this.lastUseTime = this.creationTime;
    }
    
    protected PooledConnection(final String name) {
        super(null);
        this.pstmtMonitor = new Object();
        this.status = 88;
        this.resetIsolationReadOnlyRequired = false;
        this.name = name;
        this.pool = null;
        this.connection = null;
        this.pstmtCache = null;
        this.maxStackTrace = 0;
        this.creationTime = System.currentTimeMillis();
        this.lastUseTime = this.creationTime;
    }
    
    public int getSlotId() {
        return this.slotId;
    }
    
    public void setSlotId(final int slotId) {
        this.slotId = slotId;
    }
    
    public DataSourcePool getDataSourcePool() {
        return this.pool;
    }
    
    public long getCreationTime() {
        return this.creationTime;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String toString() {
        return this.name;
    }
    
    public String getDescription() {
        return "name[" + this.name + "] startTime[" + this.getStartUseTime() + "] stmt[" + this.getLastStatement() + "] createdBy[" + this.getCreatedByMethod() + "]";
    }
    
    public String getStatistics() {
        return "name[" + this.name + "] startTime[" + this.getStartUseTime() + "] pstmtHits[" + this.pstmtHitCounter + "] pstmtMiss[" + this.pstmtMissCounter + "] " + this.pstmtCache.getDescription();
    }
    
    public boolean isLongRunning() {
        return this.longRunning;
    }
    
    public void setLongRunning(final boolean longRunning) {
        this.longRunning = longRunning;
    }
    
    public void closeConnectionFully(final boolean logErrors) {
        String msg = "Closing Connection[" + this.getName() + "]" + " psReuse[" + this.pstmtHitCounter + "] psCreate[" + this.pstmtMissCounter + "] psSize[" + this.pstmtCache.size() + "]";
        PooledConnection.logger.info(msg);
        try {
            if (this.connection.isClosed()) {
                msg = "Closing Connection[" + this.getName() + "] that is already closed?";
                PooledConnection.logger.log(Level.SEVERE, msg);
                return;
            }
        }
        catch (SQLException ex) {
            if (logErrors) {
                msg = "Error when fully closing connection [" + this.getName() + "]";
                PooledConnection.logger.log(Level.SEVERE, msg, ex);
            }
        }
        try {
            for (final ExtendedPreparedStatement ps : ((LinkedHashMap<K, ExtendedPreparedStatement>)this.pstmtCache).values()) {
                ps.closeDestroy();
            }
        }
        catch (SQLException ex) {
            if (logErrors) {
                PooledConnection.logger.log(Level.WARNING, "Error when closing connection Statements", ex);
            }
        }
        try {
            this.connection.close();
        }
        catch (SQLException ex) {
            if (logErrors) {
                msg = "Error when fully closing connection [" + this.getName() + "]";
                PooledConnection.logger.log(Level.SEVERE, msg, ex);
            }
        }
    }
    
    public PstmtCache getPstmtCache() {
        return this.pstmtCache;
    }
    
    public Statement createStatement() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "createStatement()");
        }
        try {
            return this.connection.createStatement();
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    public Statement createStatement(final int resultSetType, final int resultSetConcurreny) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "createStatement()");
        }
        try {
            return this.connection.createStatement(resultSetType, resultSetConcurreny);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    protected void returnPreparedStatement(final ExtendedPreparedStatement pstmt) {
        synchronized (this.pstmtMonitor) {
            final ExtendedPreparedStatement alreadyInCache = this.pstmtCache.get(pstmt.getCacheKey());
            if (alreadyInCache == null) {
                this.pstmtCache.put(pstmt.getCacheKey(), pstmt);
            }
            else {
                try {
                    pstmt.closeDestroy();
                }
                catch (SQLException e) {
                    PooledConnection.logger.log(Level.SEVERE, "Error closing Pstmt", e);
                }
            }
        }
    }
    
    public PreparedStatement prepareStatement(final String sql, final int returnKeysFlag) throws SQLException {
        final String cacheKey = sql + returnKeysFlag;
        return this.prepareStatement(sql, true, returnKeysFlag, cacheKey);
    }
    
    public PreparedStatement prepareStatement(final String sql) throws SQLException {
        return this.prepareStatement(sql, false, 0, sql);
    }
    
    private PreparedStatement prepareStatement(final String sql, final boolean useFlag, final int flag, final String cacheKey) throws SQLException {
        if (this.status == 88) {
            final String m = PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "prepareStatement()";
            throw new SQLException(m);
        }
        try {
            synchronized (this.pstmtMonitor) {
                this.lastStatement = sql;
                final ExtendedPreparedStatement pstmt = this.pstmtCache.remove(cacheKey);
                if (pstmt != null) {
                    ++this.pstmtHitCounter;
                    return pstmt;
                }
                ++this.pstmtMissCounter;
                PreparedStatement actualPstmt;
                if (useFlag) {
                    actualPstmt = this.connection.prepareStatement(sql, flag);
                }
                else {
                    actualPstmt = this.connection.prepareStatement(sql);
                }
                return new ExtendedPreparedStatement(this, actualPstmt, sql, cacheKey);
            }
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurreny) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "prepareStatement()");
        }
        try {
            ++this.pstmtMissCounter;
            this.lastStatement = sql;
            return this.connection.prepareStatement(sql, resultSetType, resultSetConcurreny);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    protected void resetForUse() {
        this.status = 89;
        this.startUseTime = System.currentTimeMillis();
        this.createdByMethod = null;
        this.lastStatement = null;
        this.hadErrors = false;
        this.longRunning = false;
    }
    
    public void addError(final Throwable e) {
        this.hadErrors = true;
    }
    
    public boolean hadErrors() {
        return this.hadErrors;
    }
    
    public void close() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "close()");
        }
        if (this.hadErrors && !this.pool.validateConnection(this)) {
            this.closeConnectionFully(false);
            this.pool.checkDataSource();
            return;
        }
        try {
            if (this.connection.getAutoCommit() != this.pool.getAutoCommit()) {
                this.connection.setAutoCommit(this.pool.getAutoCommit());
            }
            if (this.resetIsolationReadOnlyRequired) {
                this.resetIsolationReadOnly();
                this.resetIsolationReadOnlyRequired = false;
            }
            this.lastUseTime = System.currentTimeMillis();
            this.status = 88;
            this.pool.returnConnection(this);
        }
        catch (Exception ex) {
            this.closeConnectionFully(false);
            this.pool.checkDataSource();
        }
    }
    
    private void resetIsolationReadOnly() throws SQLException {
        if (this.connection.getTransactionIsolation() != this.pool.getTransactionIsolation()) {
            this.connection.setTransactionIsolation(this.pool.getTransactionIsolation());
        }
        if (this.connection.isReadOnly()) {
            this.connection.setReadOnly(false);
        }
    }
    
    protected void finalize() throws Throwable {
        try {
            if (this.connection != null && !this.connection.isClosed()) {
                final String msg = "Closing Connection[" + this.getName() + "] on finalize().";
                PooledConnection.logger.warning(msg);
                this.closeConnectionFully(false);
            }
        }
        catch (Exception e) {
            PooledConnection.logger.log(Level.SEVERE, null, e);
        }
        super.finalize();
    }
    
    public long getStartUseTime() {
        return this.startUseTime;
    }
    
    public long getLastUsedTime() {
        return this.lastUseTime;
    }
    
    public String getLastStatement() {
        return this.lastStatement;
    }
    
    protected void setLastStatement(final String lastStatement) {
        this.lastStatement = lastStatement;
        if (PooledConnection.logger.isLoggable(Level.FINER)) {
            PooledConnection.logger.finer(".setLastStatement[" + lastStatement + "]");
        }
    }
    
    public void setReadOnly(final boolean readOnly) throws SQLException {
        this.resetIsolationReadOnlyRequired = true;
        this.connection.setReadOnly(readOnly);
    }
    
    public void setTransactionIsolation(final int level) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "setTransactionIsolation()");
        }
        try {
            this.resetIsolationReadOnlyRequired = true;
            this.connection.setTransactionIsolation(level);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    public void clearWarnings() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "clearWarnings()");
        }
        this.connection.clearWarnings();
    }
    
    public void commit() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "commit()");
        }
        try {
            this.status = 87;
            this.connection.commit();
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    public boolean getAutoCommit() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "getAutoCommit()");
        }
        return this.connection.getAutoCommit();
    }
    
    public String getCatalog() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "getCatalog()");
        }
        return this.connection.getCatalog();
    }
    
    public DatabaseMetaData getMetaData() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "getMetaData()");
        }
        return this.connection.getMetaData();
    }
    
    public int getTransactionIsolation() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "getTransactionIsolation()");
        }
        return this.connection.getTransactionIsolation();
    }
    
    public Map<String, Class<?>> getTypeMap() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "getTypeMap()");
        }
        return this.connection.getTypeMap();
    }
    
    public SQLWarning getWarnings() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "getWarnings()");
        }
        return this.connection.getWarnings();
    }
    
    public boolean isClosed() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "isClosed()");
        }
        return this.connection.isClosed();
    }
    
    public boolean isReadOnly() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "isReadOnly()");
        }
        return this.connection.isReadOnly();
    }
    
    public String nativeSQL(final String sql) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "nativeSQL()");
        }
        this.lastStatement = sql;
        return this.connection.nativeSQL(sql);
    }
    
    public CallableStatement prepareCall(final String sql) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "prepareCall()");
        }
        this.lastStatement = sql;
        return this.connection.prepareCall(sql);
    }
    
    public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurreny) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "prepareCall()");
        }
        this.lastStatement = sql;
        return this.connection.prepareCall(sql, resultSetType, resultSetConcurreny);
    }
    
    public void rollback() throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "rollback()");
        }
        try {
            this.status = 87;
            this.connection.rollback();
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    public void setAutoCommit(final boolean autoCommit) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "setAutoCommit()");
        }
        try {
            this.connection.setAutoCommit(autoCommit);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    public void setCatalog(final String catalog) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "setCatalog()");
        }
        this.connection.setCatalog(catalog);
    }
    
    public void setTypeMap(final Map<String, Class<?>> map) throws SQLException {
        if (this.status == 88) {
            throw new SQLException(PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR + "setTypeMap()");
        }
        this.connection.setTypeMap(map);
    }
    
    public Savepoint setSavepoint() throws SQLException {
        try {
            return this.connection.setSavepoint();
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    public Savepoint setSavepoint(final String savepointName) throws SQLException {
        try {
            return this.connection.setSavepoint(savepointName);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    public void rollback(final Savepoint sp) throws SQLException {
        try {
            this.connection.rollback(sp);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    public void releaseSavepoint(final Savepoint sp) throws SQLException {
        try {
            this.connection.releaseSavepoint(sp);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    public void setHoldability(final int i) throws SQLException {
        try {
            this.connection.setHoldability(i);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    public int getHoldability() throws SQLException {
        try {
            return this.connection.getHoldability();
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    public Statement createStatement(final int i, final int x, final int y) throws SQLException {
        try {
            return this.connection.createStatement(i, x, y);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    public PreparedStatement prepareStatement(final String s, final int i, final int x, final int y) throws SQLException {
        try {
            return this.connection.prepareStatement(s, i, x, y);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    public PreparedStatement prepareStatement(final String s, final int[] i) throws SQLException {
        try {
            return this.connection.prepareStatement(s, i);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    public PreparedStatement prepareStatement(final String s, final String[] s2) throws SQLException {
        try {
            return this.connection.prepareStatement(s, s2);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    public CallableStatement prepareCall(final String s, final int i, final int x, final int y) throws SQLException {
        try {
            return this.connection.prepareCall(s, i, x, y);
        }
        catch (SQLException ex) {
            this.addError(ex);
            throw ex;
        }
    }
    
    public String getCreatedByMethod() {
        if (this.createdByMethod != null) {
            return this.createdByMethod;
        }
        if (this.stackTrace == null) {
            return null;
        }
        for (int j = 0; j < this.stackTrace.length; ++j) {
            final String methodLine = this.stackTrace[j].toString();
            if (!this.skipElement(methodLine)) {
                return this.createdByMethod = methodLine;
            }
        }
        return null;
    }
    
    private boolean skipElement(final String methodLine) {
        return methodLine.startsWith("java.lang.") || methodLine.startsWith("java.util.") || methodLine.startsWith("com.avaje.ebeaninternal.server.query.CallableQuery.<init>") || (!methodLine.startsWith("com.avaje.ebeaninternal.server.query.Callable") && methodLine.startsWith("com.avaje.ebeaninternal"));
    }
    
    protected void setStackTrace(final StackTraceElement[] stackTrace) {
        this.stackTrace = stackTrace;
    }
    
    public StackTraceElement[] getStackTrace() {
        if (this.stackTrace == null) {
            return null;
        }
        final ArrayList<StackTraceElement> filteredList = new ArrayList<StackTraceElement>();
        boolean include = false;
        for (int i = 0; i < this.stackTrace.length; ++i) {
            if (!include && !this.skipElement(this.stackTrace[i].toString())) {
                include = true;
            }
            if (include && filteredList.size() < this.maxStackTrace) {
                filteredList.add(this.stackTrace[i]);
            }
        }
        return filteredList.toArray(new StackTraceElement[filteredList.size()]);
    }
    
    static {
        logger = Logger.getLogger(PooledConnection.class.getName());
        PooledConnection.IDLE_CONNECTION_ACCESSED_ERROR = "Pooled Connection has been accessed whilst idle in the pool, via method: ";
    }
}
