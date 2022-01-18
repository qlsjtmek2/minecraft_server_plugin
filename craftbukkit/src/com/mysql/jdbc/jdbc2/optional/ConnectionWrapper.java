// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc.jdbc2.optional;

import com.mysql.jdbc.MySQLConnection;
import java.util.Properties;
import com.mysql.jdbc.ExceptionInterceptor;
import com.mysql.jdbc.Extension;
import java.util.TimeZone;
import com.mysql.jdbc.log.Log;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.Statement;
import java.sql.SQLWarning;
import java.util.Map;
import java.sql.Savepoint;
import java.sql.DatabaseMetaData;
import com.mysql.jdbc.SQLError;
import java.sql.SQLException;
import com.mysql.jdbc.Util;
import java.lang.reflect.Constructor;
import com.mysql.jdbc.Connection;

public class ConnectionWrapper extends WrapperBase implements Connection
{
    protected Connection mc;
    private String invalidHandleStr;
    private boolean closed;
    private boolean isForXa;
    private static final Constructor JDBC_4_CONNECTION_WRAPPER_CTOR;
    
    protected static ConnectionWrapper getInstance(final MysqlPooledConnection mysqlPooledConnection, final Connection mysqlConnection, final boolean forXa) throws SQLException {
        if (!Util.isJdbc4()) {
            return new ConnectionWrapper(mysqlPooledConnection, mysqlConnection, forXa);
        }
        return (ConnectionWrapper)Util.handleNewInstance(ConnectionWrapper.JDBC_4_CONNECTION_WRAPPER_CTOR, new Object[] { mysqlPooledConnection, mysqlConnection, forXa }, mysqlPooledConnection.getExceptionInterceptor());
    }
    
    public ConnectionWrapper(final MysqlPooledConnection mysqlPooledConnection, final Connection mysqlConnection, final boolean forXa) throws SQLException {
        super(mysqlPooledConnection);
        this.mc = null;
        this.invalidHandleStr = "Logical handle no longer valid";
        this.mc = mysqlConnection;
        this.closed = false;
        this.isForXa = forXa;
        if (this.isForXa) {
            this.setInGlobalTx(false);
        }
    }
    
    public void setAutoCommit(final boolean autoCommit) throws SQLException {
        this.checkClosed();
        if (autoCommit && this.isInGlobalTx()) {
            throw SQLError.createSQLException("Can't set autocommit to 'true' on an XAConnection", "2D000", 1401, this.exceptionInterceptor);
        }
        try {
            this.mc.setAutoCommit(autoCommit);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }
    
    public boolean getAutoCommit() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.getAutoCommit();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return false;
        }
    }
    
    public void setCatalog(final String catalog) throws SQLException {
        this.checkClosed();
        try {
            this.mc.setCatalog(catalog);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }
    
    public String getCatalog() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.getCatalog();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public boolean isClosed() throws SQLException {
        return this.closed || this.mc.isClosed();
    }
    
    public boolean isMasterConnection() {
        return this.mc.isMasterConnection();
    }
    
    public void setHoldability(final int arg0) throws SQLException {
        this.checkClosed();
        try {
            this.mc.setHoldability(arg0);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }
    
    public int getHoldability() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.getHoldability();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return 1;
        }
    }
    
    public long getIdleFor() {
        return this.mc.getIdleFor();
    }
    
    public DatabaseMetaData getMetaData() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.getMetaData();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public void setReadOnly(final boolean readOnly) throws SQLException {
        this.checkClosed();
        try {
            this.mc.setReadOnly(readOnly);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }
    
    public boolean isReadOnly() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.isReadOnly();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return false;
        }
    }
    
    public Savepoint setSavepoint() throws SQLException {
        this.checkClosed();
        if (this.isInGlobalTx()) {
            throw SQLError.createSQLException("Can't set autocommit to 'true' on an XAConnection", "2D000", 1401, this.exceptionInterceptor);
        }
        try {
            return this.mc.setSavepoint();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public Savepoint setSavepoint(final String arg0) throws SQLException {
        this.checkClosed();
        if (this.isInGlobalTx()) {
            throw SQLError.createSQLException("Can't set autocommit to 'true' on an XAConnection", "2D000", 1401, this.exceptionInterceptor);
        }
        try {
            return this.mc.setSavepoint(arg0);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public void setTransactionIsolation(final int level) throws SQLException {
        this.checkClosed();
        try {
            this.mc.setTransactionIsolation(level);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }
    
    public int getTransactionIsolation() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.getTransactionIsolation();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return 4;
        }
    }
    
    public void setTypeMap(final Map map) throws SQLException {
        this.checkClosed();
        try {
            this.mc.setTypeMap(map);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }
    
    public Map getTypeMap() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.getTypeMap();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public SQLWarning getWarnings() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.getWarnings();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public void clearWarnings() throws SQLException {
        this.checkClosed();
        try {
            this.mc.clearWarnings();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }
    
    public void close() throws SQLException {
        this.close(true);
    }
    
    public void commit() throws SQLException {
        this.checkClosed();
        if (this.isInGlobalTx()) {
            throw SQLError.createSQLException("Can't call commit() on an XAConnection associated with a global transaction", "2D000", 1401, this.exceptionInterceptor);
        }
        try {
            this.mc.commit();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }
    
    public Statement createStatement() throws SQLException {
        this.checkClosed();
        try {
            return StatementWrapper.getInstance(this, this.pooledConnection, this.mc.createStatement());
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public Statement createStatement(final int resultSetType, final int resultSetConcurrency) throws SQLException {
        this.checkClosed();
        try {
            return StatementWrapper.getInstance(this, this.pooledConnection, this.mc.createStatement(resultSetType, resultSetConcurrency));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public Statement createStatement(final int arg0, final int arg1, final int arg2) throws SQLException {
        this.checkClosed();
        try {
            return StatementWrapper.getInstance(this, this.pooledConnection, this.mc.createStatement(arg0, arg1, arg2));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public String nativeSQL(final String sql) throws SQLException {
        this.checkClosed();
        try {
            return this.mc.nativeSQL(sql);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public CallableStatement prepareCall(final String sql) throws SQLException {
        this.checkClosed();
        try {
            return CallableStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareCall(sql));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        this.checkClosed();
        try {
            return CallableStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareCall(sql, resultSetType, resultSetConcurrency));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public CallableStatement prepareCall(final String arg0, final int arg1, final int arg2, final int arg3) throws SQLException {
        this.checkClosed();
        try {
            return CallableStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareCall(arg0, arg1, arg2, arg3));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement clientPrepare(final String sql) throws SQLException {
        this.checkClosed();
        try {
            return new PreparedStatementWrapper(this, this.pooledConnection, this.mc.clientPrepareStatement(sql));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement clientPrepare(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        this.checkClosed();
        try {
            return new PreparedStatementWrapper(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, resultSetType, resultSetConcurrency));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement prepareStatement(final String sql) throws SQLException {
        this.checkClosed();
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(sql));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        this.checkClosed();
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(sql, resultSetType, resultSetConcurrency));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement prepareStatement(final String arg0, final int arg1, final int arg2, final int arg3) throws SQLException {
        this.checkClosed();
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(arg0, arg1, arg2, arg3));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement prepareStatement(final String arg0, final int arg1) throws SQLException {
        this.checkClosed();
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(arg0, arg1));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement prepareStatement(final String arg0, final int[] arg1) throws SQLException {
        this.checkClosed();
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(arg0, arg1));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement prepareStatement(final String arg0, final String[] arg1) throws SQLException {
        this.checkClosed();
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.prepareStatement(arg0, arg1));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public void releaseSavepoint(final Savepoint arg0) throws SQLException {
        this.checkClosed();
        try {
            this.mc.releaseSavepoint(arg0);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }
    
    public void rollback() throws SQLException {
        this.checkClosed();
        if (this.isInGlobalTx()) {
            throw SQLError.createSQLException("Can't call rollback() on an XAConnection associated with a global transaction", "2D000", 1401, this.exceptionInterceptor);
        }
        try {
            this.mc.rollback();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }
    
    public void rollback(final Savepoint arg0) throws SQLException {
        this.checkClosed();
        if (this.isInGlobalTx()) {
            throw SQLError.createSQLException("Can't call rollback() on an XAConnection associated with a global transaction", "2D000", 1401, this.exceptionInterceptor);
        }
        try {
            this.mc.rollback(arg0);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }
    
    public boolean isSameResource(final Connection c) {
        if (c instanceof ConnectionWrapper) {
            return this.mc.isSameResource(((ConnectionWrapper)c).mc);
        }
        return c instanceof Connection && this.mc.isSameResource(c);
    }
    
    protected void close(final boolean fireClosedEvent) throws SQLException {
        synchronized (this.pooledConnection) {
            if (this.closed) {
                return;
            }
            if (!this.isInGlobalTx() && this.mc.getRollbackOnPooledClose() && !this.getAutoCommit()) {
                this.rollback();
            }
            if (fireClosedEvent) {
                this.pooledConnection.callConnectionEventListeners(2, null);
            }
            this.closed = true;
        }
    }
    
    protected void checkClosed() throws SQLException {
        if (this.closed) {
            throw SQLError.createSQLException(this.invalidHandleStr, this.exceptionInterceptor);
        }
    }
    
    public boolean isInGlobalTx() {
        return this.mc.isInGlobalTx();
    }
    
    public void setInGlobalTx(final boolean flag) {
        this.mc.setInGlobalTx(flag);
    }
    
    public void ping() throws SQLException {
        if (this.mc != null) {
            this.mc.ping();
        }
    }
    
    public void changeUser(final String userName, final String newPassword) throws SQLException {
        this.checkClosed();
        try {
            this.mc.changeUser(userName, newPassword);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }
    
    public void clearHasTriedMaster() {
        this.mc.clearHasTriedMaster();
    }
    
    public PreparedStatement clientPrepareStatement(final String sql) throws SQLException {
        this.checkClosed();
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement clientPrepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, autoGenKeyIndex));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement clientPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, resultSetType, resultSetConcurrency));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement clientPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement clientPrepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, autoGenKeyIndexes));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement clientPrepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.clientPrepareStatement(sql, autoGenKeyColNames));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public int getActiveStatementCount() {
        return this.mc.getActiveStatementCount();
    }
    
    public Log getLog() throws SQLException {
        return this.mc.getLog();
    }
    
    public String getServerCharacterEncoding() {
        return this.mc.getServerCharacterEncoding();
    }
    
    public TimeZone getServerTimezoneTZ() {
        return this.mc.getServerTimezoneTZ();
    }
    
    public String getStatementComment() {
        return this.mc.getStatementComment();
    }
    
    public boolean hasTriedMaster() {
        return this.mc.hasTriedMaster();
    }
    
    public boolean isAbonormallyLongQuery(final long millisOrNanos) {
        return this.mc.isAbonormallyLongQuery(millisOrNanos);
    }
    
    public boolean isNoBackslashEscapesSet() {
        return this.mc.isNoBackslashEscapesSet();
    }
    
    public boolean lowerCaseTableNames() {
        return this.mc.lowerCaseTableNames();
    }
    
    public boolean parserKnowsUnicode() {
        return this.mc.parserKnowsUnicode();
    }
    
    public void reportQueryTime(final long millisOrNanos) {
        this.mc.reportQueryTime(millisOrNanos);
    }
    
    public void resetServerState() throws SQLException {
        this.checkClosed();
        try {
            this.mc.resetServerState();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }
    
    public PreparedStatement serverPrepareStatement(final String sql) throws SQLException {
        this.checkClosed();
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement serverPrepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, autoGenKeyIndex));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement serverPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, resultSetType, resultSetConcurrency));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement serverPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement serverPrepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, autoGenKeyIndexes));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public PreparedStatement serverPrepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        try {
            return PreparedStatementWrapper.getInstance(this, this.pooledConnection, this.mc.serverPrepareStatement(sql, autoGenKeyColNames));
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public void setFailedOver(final boolean flag) {
        this.mc.setFailedOver(flag);
    }
    
    public void setPreferSlaveDuringFailover(final boolean flag) {
        this.mc.setPreferSlaveDuringFailover(flag);
    }
    
    public void setStatementComment(final String comment) {
        this.mc.setStatementComment(comment);
    }
    
    public void shutdownServer() throws SQLException {
        this.checkClosed();
        try {
            this.mc.shutdownServer();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
        }
    }
    
    public boolean supportsIsolationLevel() {
        return this.mc.supportsIsolationLevel();
    }
    
    public boolean supportsQuotedIdentifiers() {
        return this.mc.supportsQuotedIdentifiers();
    }
    
    public boolean supportsTransactions() {
        return this.mc.supportsTransactions();
    }
    
    public boolean versionMeetsMinimum(final int major, final int minor, final int subminor) throws SQLException {
        this.checkClosed();
        try {
            return this.mc.versionMeetsMinimum(major, minor, subminor);
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return false;
        }
    }
    
    public String exposeAsXml() throws SQLException {
        this.checkClosed();
        try {
            return this.mc.exposeAsXml();
        }
        catch (SQLException sqlException) {
            this.checkAndFireConnectionError(sqlException);
            return null;
        }
    }
    
    public boolean getAllowLoadLocalInfile() {
        return this.mc.getAllowLoadLocalInfile();
    }
    
    public boolean getAllowMultiQueries() {
        return this.mc.getAllowMultiQueries();
    }
    
    public boolean getAllowNanAndInf() {
        return this.mc.getAllowNanAndInf();
    }
    
    public boolean getAllowUrlInLocalInfile() {
        return this.mc.getAllowUrlInLocalInfile();
    }
    
    public boolean getAlwaysSendSetIsolation() {
        return this.mc.getAlwaysSendSetIsolation();
    }
    
    public boolean getAutoClosePStmtStreams() {
        return this.mc.getAutoClosePStmtStreams();
    }
    
    public boolean getAutoDeserialize() {
        return this.mc.getAutoDeserialize();
    }
    
    public boolean getAutoGenerateTestcaseScript() {
        return this.mc.getAutoGenerateTestcaseScript();
    }
    
    public boolean getAutoReconnectForPools() {
        return this.mc.getAutoReconnectForPools();
    }
    
    public boolean getAutoSlowLog() {
        return this.mc.getAutoSlowLog();
    }
    
    public int getBlobSendChunkSize() {
        return this.mc.getBlobSendChunkSize();
    }
    
    public boolean getBlobsAreStrings() {
        return this.mc.getBlobsAreStrings();
    }
    
    public boolean getCacheCallableStatements() {
        return this.mc.getCacheCallableStatements();
    }
    
    public boolean getCacheCallableStmts() {
        return this.mc.getCacheCallableStmts();
    }
    
    public boolean getCachePrepStmts() {
        return this.mc.getCachePrepStmts();
    }
    
    public boolean getCachePreparedStatements() {
        return this.mc.getCachePreparedStatements();
    }
    
    public boolean getCacheResultSetMetadata() {
        return this.mc.getCacheResultSetMetadata();
    }
    
    public boolean getCacheServerConfiguration() {
        return this.mc.getCacheServerConfiguration();
    }
    
    public int getCallableStatementCacheSize() {
        return this.mc.getCallableStatementCacheSize();
    }
    
    public int getCallableStmtCacheSize() {
        return this.mc.getCallableStmtCacheSize();
    }
    
    public boolean getCapitalizeTypeNames() {
        return this.mc.getCapitalizeTypeNames();
    }
    
    public String getCharacterSetResults() {
        return this.mc.getCharacterSetResults();
    }
    
    public String getClientCertificateKeyStorePassword() {
        return this.mc.getClientCertificateKeyStorePassword();
    }
    
    public String getClientCertificateKeyStoreType() {
        return this.mc.getClientCertificateKeyStoreType();
    }
    
    public String getClientCertificateKeyStoreUrl() {
        return this.mc.getClientCertificateKeyStoreUrl();
    }
    
    public String getClientInfoProvider() {
        return this.mc.getClientInfoProvider();
    }
    
    public String getClobCharacterEncoding() {
        return this.mc.getClobCharacterEncoding();
    }
    
    public boolean getClobberStreamingResults() {
        return this.mc.getClobberStreamingResults();
    }
    
    public int getConnectTimeout() {
        return this.mc.getConnectTimeout();
    }
    
    public String getConnectionCollation() {
        return this.mc.getConnectionCollation();
    }
    
    public String getConnectionLifecycleInterceptors() {
        return this.mc.getConnectionLifecycleInterceptors();
    }
    
    public boolean getContinueBatchOnError() {
        return this.mc.getContinueBatchOnError();
    }
    
    public boolean getCreateDatabaseIfNotExist() {
        return this.mc.getCreateDatabaseIfNotExist();
    }
    
    public int getDefaultFetchSize() {
        return this.mc.getDefaultFetchSize();
    }
    
    public boolean getDontTrackOpenResources() {
        return this.mc.getDontTrackOpenResources();
    }
    
    public boolean getDumpMetadataOnColumnNotFound() {
        return this.mc.getDumpMetadataOnColumnNotFound();
    }
    
    public boolean getDumpQueriesOnException() {
        return this.mc.getDumpQueriesOnException();
    }
    
    public boolean getDynamicCalendars() {
        return this.mc.getDynamicCalendars();
    }
    
    public boolean getElideSetAutoCommits() {
        return this.mc.getElideSetAutoCommits();
    }
    
    public boolean getEmptyStringsConvertToZero() {
        return this.mc.getEmptyStringsConvertToZero();
    }
    
    public boolean getEmulateLocators() {
        return this.mc.getEmulateLocators();
    }
    
    public boolean getEmulateUnsupportedPstmts() {
        return this.mc.getEmulateUnsupportedPstmts();
    }
    
    public boolean getEnablePacketDebug() {
        return this.mc.getEnablePacketDebug();
    }
    
    public boolean getEnableQueryTimeouts() {
        return this.mc.getEnableQueryTimeouts();
    }
    
    public String getEncoding() {
        return this.mc.getEncoding();
    }
    
    public boolean getExplainSlowQueries() {
        return this.mc.getExplainSlowQueries();
    }
    
    public boolean getFailOverReadOnly() {
        return this.mc.getFailOverReadOnly();
    }
    
    public boolean getFunctionsNeverReturnBlobs() {
        return this.mc.getFunctionsNeverReturnBlobs();
    }
    
    public boolean getGatherPerfMetrics() {
        return this.mc.getGatherPerfMetrics();
    }
    
    public boolean getGatherPerformanceMetrics() {
        return this.mc.getGatherPerformanceMetrics();
    }
    
    public boolean getGenerateSimpleParameterMetadata() {
        return this.mc.getGenerateSimpleParameterMetadata();
    }
    
    public boolean getHoldResultsOpenOverStatementClose() {
        return this.mc.getHoldResultsOpenOverStatementClose();
    }
    
    public boolean getIgnoreNonTxTables() {
        return this.mc.getIgnoreNonTxTables();
    }
    
    public boolean getIncludeInnodbStatusInDeadlockExceptions() {
        return this.mc.getIncludeInnodbStatusInDeadlockExceptions();
    }
    
    public int getInitialTimeout() {
        return this.mc.getInitialTimeout();
    }
    
    public boolean getInteractiveClient() {
        return this.mc.getInteractiveClient();
    }
    
    public boolean getIsInteractiveClient() {
        return this.mc.getIsInteractiveClient();
    }
    
    public boolean getJdbcCompliantTruncation() {
        return this.mc.getJdbcCompliantTruncation();
    }
    
    public boolean getJdbcCompliantTruncationForReads() {
        return this.mc.getJdbcCompliantTruncationForReads();
    }
    
    public String getLargeRowSizeThreshold() {
        return this.mc.getLargeRowSizeThreshold();
    }
    
    public String getLoadBalanceStrategy() {
        return this.mc.getLoadBalanceStrategy();
    }
    
    public String getLocalSocketAddress() {
        return this.mc.getLocalSocketAddress();
    }
    
    public int getLocatorFetchBufferSize() {
        return this.mc.getLocatorFetchBufferSize();
    }
    
    public boolean getLogSlowQueries() {
        return this.mc.getLogSlowQueries();
    }
    
    public boolean getLogXaCommands() {
        return this.mc.getLogXaCommands();
    }
    
    public String getLogger() {
        return this.mc.getLogger();
    }
    
    public String getLoggerClassName() {
        return this.mc.getLoggerClassName();
    }
    
    public boolean getMaintainTimeStats() {
        return this.mc.getMaintainTimeStats();
    }
    
    public int getMaxQuerySizeToLog() {
        return this.mc.getMaxQuerySizeToLog();
    }
    
    public int getMaxReconnects() {
        return this.mc.getMaxReconnects();
    }
    
    public int getMaxRows() {
        return this.mc.getMaxRows();
    }
    
    public int getMetadataCacheSize() {
        return this.mc.getMetadataCacheSize();
    }
    
    public int getNetTimeoutForStreamingResults() {
        return this.mc.getNetTimeoutForStreamingResults();
    }
    
    public boolean getNoAccessToProcedureBodies() {
        return this.mc.getNoAccessToProcedureBodies();
    }
    
    public boolean getNoDatetimeStringSync() {
        return this.mc.getNoDatetimeStringSync();
    }
    
    public boolean getNoTimezoneConversionForTimeType() {
        return this.mc.getNoTimezoneConversionForTimeType();
    }
    
    public boolean getNullCatalogMeansCurrent() {
        return this.mc.getNullCatalogMeansCurrent();
    }
    
    public boolean getNullNamePatternMatchesAll() {
        return this.mc.getNullNamePatternMatchesAll();
    }
    
    public boolean getOverrideSupportsIntegrityEnhancementFacility() {
        return this.mc.getOverrideSupportsIntegrityEnhancementFacility();
    }
    
    public int getPacketDebugBufferSize() {
        return this.mc.getPacketDebugBufferSize();
    }
    
    public boolean getPadCharsWithSpace() {
        return this.mc.getPadCharsWithSpace();
    }
    
    public boolean getParanoid() {
        return this.mc.getParanoid();
    }
    
    public boolean getPedantic() {
        return this.mc.getPedantic();
    }
    
    public boolean getPinGlobalTxToPhysicalConnection() {
        return this.mc.getPinGlobalTxToPhysicalConnection();
    }
    
    public boolean getPopulateInsertRowWithDefaultValues() {
        return this.mc.getPopulateInsertRowWithDefaultValues();
    }
    
    public int getPrepStmtCacheSize() {
        return this.mc.getPrepStmtCacheSize();
    }
    
    public int getPrepStmtCacheSqlLimit() {
        return this.mc.getPrepStmtCacheSqlLimit();
    }
    
    public int getPreparedStatementCacheSize() {
        return this.mc.getPreparedStatementCacheSize();
    }
    
    public int getPreparedStatementCacheSqlLimit() {
        return this.mc.getPreparedStatementCacheSqlLimit();
    }
    
    public boolean getProcessEscapeCodesForPrepStmts() {
        return this.mc.getProcessEscapeCodesForPrepStmts();
    }
    
    public boolean getProfileSQL() {
        return this.mc.getProfileSQL();
    }
    
    public boolean getProfileSql() {
        return this.mc.getProfileSql();
    }
    
    public String getPropertiesTransform() {
        return this.mc.getPropertiesTransform();
    }
    
    public int getQueriesBeforeRetryMaster() {
        return this.mc.getQueriesBeforeRetryMaster();
    }
    
    public boolean getReconnectAtTxEnd() {
        return this.mc.getReconnectAtTxEnd();
    }
    
    public boolean getRelaxAutoCommit() {
        return this.mc.getRelaxAutoCommit();
    }
    
    public int getReportMetricsIntervalMillis() {
        return this.mc.getReportMetricsIntervalMillis();
    }
    
    public boolean getRequireSSL() {
        return this.mc.getRequireSSL();
    }
    
    public String getResourceId() {
        return this.mc.getResourceId();
    }
    
    public int getResultSetSizeThreshold() {
        return this.mc.getResultSetSizeThreshold();
    }
    
    public boolean getRewriteBatchedStatements() {
        return this.mc.getRewriteBatchedStatements();
    }
    
    public boolean getRollbackOnPooledClose() {
        return this.mc.getRollbackOnPooledClose();
    }
    
    public boolean getRoundRobinLoadBalance() {
        return this.mc.getRoundRobinLoadBalance();
    }
    
    public boolean getRunningCTS13() {
        return this.mc.getRunningCTS13();
    }
    
    public int getSecondsBeforeRetryMaster() {
        return this.mc.getSecondsBeforeRetryMaster();
    }
    
    public String getServerTimezone() {
        return this.mc.getServerTimezone();
    }
    
    public String getSessionVariables() {
        return this.mc.getSessionVariables();
    }
    
    public int getSlowQueryThresholdMillis() {
        return this.mc.getSlowQueryThresholdMillis();
    }
    
    public long getSlowQueryThresholdNanos() {
        return this.mc.getSlowQueryThresholdNanos();
    }
    
    public String getSocketFactory() {
        return this.mc.getSocketFactory();
    }
    
    public String getSocketFactoryClassName() {
        return this.mc.getSocketFactoryClassName();
    }
    
    public int getSocketTimeout() {
        return this.mc.getSocketTimeout();
    }
    
    public String getStatementInterceptors() {
        return this.mc.getStatementInterceptors();
    }
    
    public boolean getStrictFloatingPoint() {
        return this.mc.getStrictFloatingPoint();
    }
    
    public boolean getStrictUpdates() {
        return this.mc.getStrictUpdates();
    }
    
    public boolean getTcpKeepAlive() {
        return this.mc.getTcpKeepAlive();
    }
    
    public boolean getTcpNoDelay() {
        return this.mc.getTcpNoDelay();
    }
    
    public int getTcpRcvBuf() {
        return this.mc.getTcpRcvBuf();
    }
    
    public int getTcpSndBuf() {
        return this.mc.getTcpSndBuf();
    }
    
    public int getTcpTrafficClass() {
        return this.mc.getTcpTrafficClass();
    }
    
    public boolean getTinyInt1isBit() {
        return this.mc.getTinyInt1isBit();
    }
    
    public boolean getTraceProtocol() {
        return this.mc.getTraceProtocol();
    }
    
    public boolean getTransformedBitIsBoolean() {
        return this.mc.getTransformedBitIsBoolean();
    }
    
    public boolean getTreatUtilDateAsTimestamp() {
        return this.mc.getTreatUtilDateAsTimestamp();
    }
    
    public String getTrustCertificateKeyStorePassword() {
        return this.mc.getTrustCertificateKeyStorePassword();
    }
    
    public String getTrustCertificateKeyStoreType() {
        return this.mc.getTrustCertificateKeyStoreType();
    }
    
    public String getTrustCertificateKeyStoreUrl() {
        return this.mc.getTrustCertificateKeyStoreUrl();
    }
    
    public boolean getUltraDevHack() {
        return this.mc.getUltraDevHack();
    }
    
    public boolean getUseBlobToStoreUTF8OutsideBMP() {
        return this.mc.getUseBlobToStoreUTF8OutsideBMP();
    }
    
    public boolean getUseCompression() {
        return this.mc.getUseCompression();
    }
    
    public String getUseConfigs() {
        return this.mc.getUseConfigs();
    }
    
    public boolean getUseCursorFetch() {
        return this.mc.getUseCursorFetch();
    }
    
    public boolean getUseDirectRowUnpack() {
        return this.mc.getUseDirectRowUnpack();
    }
    
    public boolean getUseDynamicCharsetInfo() {
        return this.mc.getUseDynamicCharsetInfo();
    }
    
    public boolean getUseFastDateParsing() {
        return this.mc.getUseFastDateParsing();
    }
    
    public boolean getUseFastIntParsing() {
        return this.mc.getUseFastIntParsing();
    }
    
    public boolean getUseGmtMillisForDatetimes() {
        return this.mc.getUseGmtMillisForDatetimes();
    }
    
    public boolean getUseHostsInPrivileges() {
        return this.mc.getUseHostsInPrivileges();
    }
    
    public boolean getUseInformationSchema() {
        return this.mc.getUseInformationSchema();
    }
    
    public boolean getUseJDBCCompliantTimezoneShift() {
        return this.mc.getUseJDBCCompliantTimezoneShift();
    }
    
    public boolean getUseJvmCharsetConverters() {
        return this.mc.getUseJvmCharsetConverters();
    }
    
    public boolean getUseLocalSessionState() {
        return this.mc.getUseLocalSessionState();
    }
    
    public boolean getUseNanosForElapsedTime() {
        return this.mc.getUseNanosForElapsedTime();
    }
    
    public boolean getUseOldAliasMetadataBehavior() {
        return this.mc.getUseOldAliasMetadataBehavior();
    }
    
    public boolean getUseOldUTF8Behavior() {
        return this.mc.getUseOldUTF8Behavior();
    }
    
    public boolean getUseOnlyServerErrorMessages() {
        return this.mc.getUseOnlyServerErrorMessages();
    }
    
    public boolean getUseReadAheadInput() {
        return this.mc.getUseReadAheadInput();
    }
    
    public boolean getUseSSL() {
        return this.mc.getUseSSL();
    }
    
    public boolean getUseSSPSCompatibleTimezoneShift() {
        return this.mc.getUseSSPSCompatibleTimezoneShift();
    }
    
    public boolean getUseServerPrepStmts() {
        return this.mc.getUseServerPrepStmts();
    }
    
    public boolean getUseServerPreparedStmts() {
        return this.mc.getUseServerPreparedStmts();
    }
    
    public boolean getUseSqlStateCodes() {
        return this.mc.getUseSqlStateCodes();
    }
    
    public boolean getUseStreamLengthsInPrepStmts() {
        return this.mc.getUseStreamLengthsInPrepStmts();
    }
    
    public boolean getUseTimezone() {
        return this.mc.getUseTimezone();
    }
    
    public boolean getUseUltraDevWorkAround() {
        return this.mc.getUseUltraDevWorkAround();
    }
    
    public boolean getUseUnbufferedInput() {
        return this.mc.getUseUnbufferedInput();
    }
    
    public boolean getUseUnicode() {
        return this.mc.getUseUnicode();
    }
    
    public boolean getUseUsageAdvisor() {
        return this.mc.getUseUsageAdvisor();
    }
    
    public String getUtf8OutsideBmpExcludedColumnNamePattern() {
        return this.mc.getUtf8OutsideBmpExcludedColumnNamePattern();
    }
    
    public String getUtf8OutsideBmpIncludedColumnNamePattern() {
        return this.mc.getUtf8OutsideBmpIncludedColumnNamePattern();
    }
    
    public boolean getYearIsDateType() {
        return this.mc.getYearIsDateType();
    }
    
    public String getZeroDateTimeBehavior() {
        return this.mc.getZeroDateTimeBehavior();
    }
    
    public void setAllowLoadLocalInfile(final boolean property) {
        this.mc.setAllowLoadLocalInfile(property);
    }
    
    public void setAllowMultiQueries(final boolean property) {
        this.mc.setAllowMultiQueries(property);
    }
    
    public void setAllowNanAndInf(final boolean flag) {
        this.mc.setAllowNanAndInf(flag);
    }
    
    public void setAllowUrlInLocalInfile(final boolean flag) {
        this.mc.setAllowUrlInLocalInfile(flag);
    }
    
    public void setAlwaysSendSetIsolation(final boolean flag) {
        this.mc.setAlwaysSendSetIsolation(flag);
    }
    
    public void setAutoClosePStmtStreams(final boolean flag) {
        this.mc.setAutoClosePStmtStreams(flag);
    }
    
    public void setAutoDeserialize(final boolean flag) {
        this.mc.setAutoDeserialize(flag);
    }
    
    public void setAutoGenerateTestcaseScript(final boolean flag) {
        this.mc.setAutoGenerateTestcaseScript(flag);
    }
    
    public void setAutoReconnect(final boolean flag) {
        this.mc.setAutoReconnect(flag);
    }
    
    public void setAutoReconnectForConnectionPools(final boolean property) {
        this.mc.setAutoReconnectForConnectionPools(property);
    }
    
    public void setAutoReconnectForPools(final boolean flag) {
        this.mc.setAutoReconnectForPools(flag);
    }
    
    public void setAutoSlowLog(final boolean flag) {
        this.mc.setAutoSlowLog(flag);
    }
    
    public void setBlobSendChunkSize(final String value) throws SQLException {
        this.mc.setBlobSendChunkSize(value);
    }
    
    public void setBlobsAreStrings(final boolean flag) {
        this.mc.setBlobsAreStrings(flag);
    }
    
    public void setCacheCallableStatements(final boolean flag) {
        this.mc.setCacheCallableStatements(flag);
    }
    
    public void setCacheCallableStmts(final boolean flag) {
        this.mc.setCacheCallableStmts(flag);
    }
    
    public void setCachePrepStmts(final boolean flag) {
        this.mc.setCachePrepStmts(flag);
    }
    
    public void setCachePreparedStatements(final boolean flag) {
        this.mc.setCachePreparedStatements(flag);
    }
    
    public void setCacheResultSetMetadata(final boolean property) {
        this.mc.setCacheResultSetMetadata(property);
    }
    
    public void setCacheServerConfiguration(final boolean flag) {
        this.mc.setCacheServerConfiguration(flag);
    }
    
    public void setCallableStatementCacheSize(final int size) {
        this.mc.setCallableStatementCacheSize(size);
    }
    
    public void setCallableStmtCacheSize(final int cacheSize) {
        this.mc.setCallableStmtCacheSize(cacheSize);
    }
    
    public void setCapitalizeDBMDTypes(final boolean property) {
        this.mc.setCapitalizeDBMDTypes(property);
    }
    
    public void setCapitalizeTypeNames(final boolean flag) {
        this.mc.setCapitalizeTypeNames(flag);
    }
    
    public void setCharacterEncoding(final String encoding) {
        this.mc.setCharacterEncoding(encoding);
    }
    
    public void setCharacterSetResults(final String characterSet) {
        this.mc.setCharacterSetResults(characterSet);
    }
    
    public void setClientCertificateKeyStorePassword(final String value) {
        this.mc.setClientCertificateKeyStorePassword(value);
    }
    
    public void setClientCertificateKeyStoreType(final String value) {
        this.mc.setClientCertificateKeyStoreType(value);
    }
    
    public void setClientCertificateKeyStoreUrl(final String value) {
        this.mc.setClientCertificateKeyStoreUrl(value);
    }
    
    public void setClientInfoProvider(final String classname) {
        this.mc.setClientInfoProvider(classname);
    }
    
    public void setClobCharacterEncoding(final String encoding) {
        this.mc.setClobCharacterEncoding(encoding);
    }
    
    public void setClobberStreamingResults(final boolean flag) {
        this.mc.setClobberStreamingResults(flag);
    }
    
    public void setConnectTimeout(final int timeoutMs) {
        this.mc.setConnectTimeout(timeoutMs);
    }
    
    public void setConnectionCollation(final String collation) {
        this.mc.setConnectionCollation(collation);
    }
    
    public void setConnectionLifecycleInterceptors(final String interceptors) {
        this.mc.setConnectionLifecycleInterceptors(interceptors);
    }
    
    public void setContinueBatchOnError(final boolean property) {
        this.mc.setContinueBatchOnError(property);
    }
    
    public void setCreateDatabaseIfNotExist(final boolean flag) {
        this.mc.setCreateDatabaseIfNotExist(flag);
    }
    
    public void setDefaultFetchSize(final int n) {
        this.mc.setDefaultFetchSize(n);
    }
    
    public void setDetectServerPreparedStmts(final boolean property) {
        this.mc.setDetectServerPreparedStmts(property);
    }
    
    public void setDontTrackOpenResources(final boolean flag) {
        this.mc.setDontTrackOpenResources(flag);
    }
    
    public void setDumpMetadataOnColumnNotFound(final boolean flag) {
        this.mc.setDumpMetadataOnColumnNotFound(flag);
    }
    
    public void setDumpQueriesOnException(final boolean flag) {
        this.mc.setDumpQueriesOnException(flag);
    }
    
    public void setDynamicCalendars(final boolean flag) {
        this.mc.setDynamicCalendars(flag);
    }
    
    public void setElideSetAutoCommits(final boolean flag) {
        this.mc.setElideSetAutoCommits(flag);
    }
    
    public void setEmptyStringsConvertToZero(final boolean flag) {
        this.mc.setEmptyStringsConvertToZero(flag);
    }
    
    public void setEmulateLocators(final boolean property) {
        this.mc.setEmulateLocators(property);
    }
    
    public void setEmulateUnsupportedPstmts(final boolean flag) {
        this.mc.setEmulateUnsupportedPstmts(flag);
    }
    
    public void setEnablePacketDebug(final boolean flag) {
        this.mc.setEnablePacketDebug(flag);
    }
    
    public void setEnableQueryTimeouts(final boolean flag) {
        this.mc.setEnableQueryTimeouts(flag);
    }
    
    public void setEncoding(final String property) {
        this.mc.setEncoding(property);
    }
    
    public void setExplainSlowQueries(final boolean flag) {
        this.mc.setExplainSlowQueries(flag);
    }
    
    public void setFailOverReadOnly(final boolean flag) {
        this.mc.setFailOverReadOnly(flag);
    }
    
    public void setFunctionsNeverReturnBlobs(final boolean flag) {
        this.mc.setFunctionsNeverReturnBlobs(flag);
    }
    
    public void setGatherPerfMetrics(final boolean flag) {
        this.mc.setGatherPerfMetrics(flag);
    }
    
    public void setGatherPerformanceMetrics(final boolean flag) {
        this.mc.setGatherPerformanceMetrics(flag);
    }
    
    public void setGenerateSimpleParameterMetadata(final boolean flag) {
        this.mc.setGenerateSimpleParameterMetadata(flag);
    }
    
    public void setHoldResultsOpenOverStatementClose(final boolean flag) {
        this.mc.setHoldResultsOpenOverStatementClose(flag);
    }
    
    public void setIgnoreNonTxTables(final boolean property) {
        this.mc.setIgnoreNonTxTables(property);
    }
    
    public void setIncludeInnodbStatusInDeadlockExceptions(final boolean flag) {
        this.mc.setIncludeInnodbStatusInDeadlockExceptions(flag);
    }
    
    public void setInitialTimeout(final int property) {
        this.mc.setInitialTimeout(property);
    }
    
    public void setInteractiveClient(final boolean property) {
        this.mc.setInteractiveClient(property);
    }
    
    public void setIsInteractiveClient(final boolean property) {
        this.mc.setIsInteractiveClient(property);
    }
    
    public void setJdbcCompliantTruncation(final boolean flag) {
        this.mc.setJdbcCompliantTruncation(flag);
    }
    
    public void setJdbcCompliantTruncationForReads(final boolean jdbcCompliantTruncationForReads) {
        this.mc.setJdbcCompliantTruncationForReads(jdbcCompliantTruncationForReads);
    }
    
    public void setLargeRowSizeThreshold(final String value) {
        this.mc.setLargeRowSizeThreshold(value);
    }
    
    public void setLoadBalanceStrategy(final String strategy) {
        this.mc.setLoadBalanceStrategy(strategy);
    }
    
    public void setLocalSocketAddress(final String address) {
        this.mc.setLocalSocketAddress(address);
    }
    
    public void setLocatorFetchBufferSize(final String value) throws SQLException {
        this.mc.setLocatorFetchBufferSize(value);
    }
    
    public void setLogSlowQueries(final boolean flag) {
        this.mc.setLogSlowQueries(flag);
    }
    
    public void setLogXaCommands(final boolean flag) {
        this.mc.setLogXaCommands(flag);
    }
    
    public void setLogger(final String property) {
        this.mc.setLogger(property);
    }
    
    public void setLoggerClassName(final String className) {
        this.mc.setLoggerClassName(className);
    }
    
    public void setMaintainTimeStats(final boolean flag) {
        this.mc.setMaintainTimeStats(flag);
    }
    
    public void setMaxQuerySizeToLog(final int sizeInBytes) {
        this.mc.setMaxQuerySizeToLog(sizeInBytes);
    }
    
    public void setMaxReconnects(final int property) {
        this.mc.setMaxReconnects(property);
    }
    
    public void setMaxRows(final int property) {
        this.mc.setMaxRows(property);
    }
    
    public void setMetadataCacheSize(final int value) {
        this.mc.setMetadataCacheSize(value);
    }
    
    public void setNetTimeoutForStreamingResults(final int value) {
        this.mc.setNetTimeoutForStreamingResults(value);
    }
    
    public void setNoAccessToProcedureBodies(final boolean flag) {
        this.mc.setNoAccessToProcedureBodies(flag);
    }
    
    public void setNoDatetimeStringSync(final boolean flag) {
        this.mc.setNoDatetimeStringSync(flag);
    }
    
    public void setNoTimezoneConversionForTimeType(final boolean flag) {
        this.mc.setNoTimezoneConversionForTimeType(flag);
    }
    
    public void setNullCatalogMeansCurrent(final boolean value) {
        this.mc.setNullCatalogMeansCurrent(value);
    }
    
    public void setNullNamePatternMatchesAll(final boolean value) {
        this.mc.setNullNamePatternMatchesAll(value);
    }
    
    public void setOverrideSupportsIntegrityEnhancementFacility(final boolean flag) {
        this.mc.setOverrideSupportsIntegrityEnhancementFacility(flag);
    }
    
    public void setPacketDebugBufferSize(final int size) {
        this.mc.setPacketDebugBufferSize(size);
    }
    
    public void setPadCharsWithSpace(final boolean flag) {
        this.mc.setPadCharsWithSpace(flag);
    }
    
    public void setParanoid(final boolean property) {
        this.mc.setParanoid(property);
    }
    
    public void setPedantic(final boolean property) {
        this.mc.setPedantic(property);
    }
    
    public void setPinGlobalTxToPhysicalConnection(final boolean flag) {
        this.mc.setPinGlobalTxToPhysicalConnection(flag);
    }
    
    public void setPopulateInsertRowWithDefaultValues(final boolean flag) {
        this.mc.setPopulateInsertRowWithDefaultValues(flag);
    }
    
    public void setPrepStmtCacheSize(final int cacheSize) {
        this.mc.setPrepStmtCacheSize(cacheSize);
    }
    
    public void setPrepStmtCacheSqlLimit(final int sqlLimit) {
        this.mc.setPrepStmtCacheSqlLimit(sqlLimit);
    }
    
    public void setPreparedStatementCacheSize(final int cacheSize) {
        this.mc.setPreparedStatementCacheSize(cacheSize);
    }
    
    public void setPreparedStatementCacheSqlLimit(final int cacheSqlLimit) {
        this.mc.setPreparedStatementCacheSqlLimit(cacheSqlLimit);
    }
    
    public void setProcessEscapeCodesForPrepStmts(final boolean flag) {
        this.mc.setProcessEscapeCodesForPrepStmts(flag);
    }
    
    public void setProfileSQL(final boolean flag) {
        this.mc.setProfileSQL(flag);
    }
    
    public void setProfileSql(final boolean property) {
        this.mc.setProfileSql(property);
    }
    
    public void setPropertiesTransform(final String value) {
        this.mc.setPropertiesTransform(value);
    }
    
    public void setQueriesBeforeRetryMaster(final int property) {
        this.mc.setQueriesBeforeRetryMaster(property);
    }
    
    public void setReconnectAtTxEnd(final boolean property) {
        this.mc.setReconnectAtTxEnd(property);
    }
    
    public void setRelaxAutoCommit(final boolean property) {
        this.mc.setRelaxAutoCommit(property);
    }
    
    public void setReportMetricsIntervalMillis(final int millis) {
        this.mc.setReportMetricsIntervalMillis(millis);
    }
    
    public void setRequireSSL(final boolean property) {
        this.mc.setRequireSSL(property);
    }
    
    public void setResourceId(final String resourceId) {
        this.mc.setResourceId(resourceId);
    }
    
    public void setResultSetSizeThreshold(final int threshold) {
        this.mc.setResultSetSizeThreshold(threshold);
    }
    
    public void setRetainStatementAfterResultSetClose(final boolean flag) {
        this.mc.setRetainStatementAfterResultSetClose(flag);
    }
    
    public void setRewriteBatchedStatements(final boolean flag) {
        this.mc.setRewriteBatchedStatements(flag);
    }
    
    public void setRollbackOnPooledClose(final boolean flag) {
        this.mc.setRollbackOnPooledClose(flag);
    }
    
    public void setRoundRobinLoadBalance(final boolean flag) {
        this.mc.setRoundRobinLoadBalance(flag);
    }
    
    public void setRunningCTS13(final boolean flag) {
        this.mc.setRunningCTS13(flag);
    }
    
    public void setSecondsBeforeRetryMaster(final int property) {
        this.mc.setSecondsBeforeRetryMaster(property);
    }
    
    public void setServerTimezone(final String property) {
        this.mc.setServerTimezone(property);
    }
    
    public void setSessionVariables(final String variables) {
        this.mc.setSessionVariables(variables);
    }
    
    public void setSlowQueryThresholdMillis(final int millis) {
        this.mc.setSlowQueryThresholdMillis(millis);
    }
    
    public void setSlowQueryThresholdNanos(final long nanos) {
        this.mc.setSlowQueryThresholdNanos(nanos);
    }
    
    public void setSocketFactory(final String name) {
        this.mc.setSocketFactory(name);
    }
    
    public void setSocketFactoryClassName(final String property) {
        this.mc.setSocketFactoryClassName(property);
    }
    
    public void setSocketTimeout(final int property) {
        this.mc.setSocketTimeout(property);
    }
    
    public void setStatementInterceptors(final String value) {
        this.mc.setStatementInterceptors(value);
    }
    
    public void setStrictFloatingPoint(final boolean property) {
        this.mc.setStrictFloatingPoint(property);
    }
    
    public void setStrictUpdates(final boolean property) {
        this.mc.setStrictUpdates(property);
    }
    
    public void setTcpKeepAlive(final boolean flag) {
        this.mc.setTcpKeepAlive(flag);
    }
    
    public void setTcpNoDelay(final boolean flag) {
        this.mc.setTcpNoDelay(flag);
    }
    
    public void setTcpRcvBuf(final int bufSize) {
        this.mc.setTcpRcvBuf(bufSize);
    }
    
    public void setTcpSndBuf(final int bufSize) {
        this.mc.setTcpSndBuf(bufSize);
    }
    
    public void setTcpTrafficClass(final int classFlags) {
        this.mc.setTcpTrafficClass(classFlags);
    }
    
    public void setTinyInt1isBit(final boolean flag) {
        this.mc.setTinyInt1isBit(flag);
    }
    
    public void setTraceProtocol(final boolean flag) {
        this.mc.setTraceProtocol(flag);
    }
    
    public void setTransformedBitIsBoolean(final boolean flag) {
        this.mc.setTransformedBitIsBoolean(flag);
    }
    
    public void setTreatUtilDateAsTimestamp(final boolean flag) {
        this.mc.setTreatUtilDateAsTimestamp(flag);
    }
    
    public void setTrustCertificateKeyStorePassword(final String value) {
        this.mc.setTrustCertificateKeyStorePassword(value);
    }
    
    public void setTrustCertificateKeyStoreType(final String value) {
        this.mc.setTrustCertificateKeyStoreType(value);
    }
    
    public void setTrustCertificateKeyStoreUrl(final String value) {
        this.mc.setTrustCertificateKeyStoreUrl(value);
    }
    
    public void setUltraDevHack(final boolean flag) {
        this.mc.setUltraDevHack(flag);
    }
    
    public void setUseBlobToStoreUTF8OutsideBMP(final boolean flag) {
        this.mc.setUseBlobToStoreUTF8OutsideBMP(flag);
    }
    
    public void setUseCompression(final boolean property) {
        this.mc.setUseCompression(property);
    }
    
    public void setUseConfigs(final String configs) {
        this.mc.setUseConfigs(configs);
    }
    
    public void setUseCursorFetch(final boolean flag) {
        this.mc.setUseCursorFetch(flag);
    }
    
    public void setUseDirectRowUnpack(final boolean flag) {
        this.mc.setUseDirectRowUnpack(flag);
    }
    
    public void setUseDynamicCharsetInfo(final boolean flag) {
        this.mc.setUseDynamicCharsetInfo(flag);
    }
    
    public void setUseFastDateParsing(final boolean flag) {
        this.mc.setUseFastDateParsing(flag);
    }
    
    public void setUseFastIntParsing(final boolean flag) {
        this.mc.setUseFastIntParsing(flag);
    }
    
    public void setUseGmtMillisForDatetimes(final boolean flag) {
        this.mc.setUseGmtMillisForDatetimes(flag);
    }
    
    public void setUseHostsInPrivileges(final boolean property) {
        this.mc.setUseHostsInPrivileges(property);
    }
    
    public void setUseInformationSchema(final boolean flag) {
        this.mc.setUseInformationSchema(flag);
    }
    
    public void setUseJDBCCompliantTimezoneShift(final boolean flag) {
        this.mc.setUseJDBCCompliantTimezoneShift(flag);
    }
    
    public void setUseJvmCharsetConverters(final boolean flag) {
        this.mc.setUseJvmCharsetConverters(flag);
    }
    
    public void setUseLocalSessionState(final boolean flag) {
        this.mc.setUseLocalSessionState(flag);
    }
    
    public void setUseNanosForElapsedTime(final boolean flag) {
        this.mc.setUseNanosForElapsedTime(flag);
    }
    
    public void setUseOldAliasMetadataBehavior(final boolean flag) {
        this.mc.setUseOldAliasMetadataBehavior(flag);
    }
    
    public void setUseOldUTF8Behavior(final boolean flag) {
        this.mc.setUseOldUTF8Behavior(flag);
    }
    
    public void setUseOnlyServerErrorMessages(final boolean flag) {
        this.mc.setUseOnlyServerErrorMessages(flag);
    }
    
    public void setUseReadAheadInput(final boolean flag) {
        this.mc.setUseReadAheadInput(flag);
    }
    
    public void setUseSSL(final boolean property) {
        this.mc.setUseSSL(property);
    }
    
    public void setUseSSPSCompatibleTimezoneShift(final boolean flag) {
        this.mc.setUseSSPSCompatibleTimezoneShift(flag);
    }
    
    public void setUseServerPrepStmts(final boolean flag) {
        this.mc.setUseServerPrepStmts(flag);
    }
    
    public void setUseServerPreparedStmts(final boolean flag) {
        this.mc.setUseServerPreparedStmts(flag);
    }
    
    public void setUseSqlStateCodes(final boolean flag) {
        this.mc.setUseSqlStateCodes(flag);
    }
    
    public void setUseStreamLengthsInPrepStmts(final boolean property) {
        this.mc.setUseStreamLengthsInPrepStmts(property);
    }
    
    public void setUseTimezone(final boolean property) {
        this.mc.setUseTimezone(property);
    }
    
    public void setUseUltraDevWorkAround(final boolean property) {
        this.mc.setUseUltraDevWorkAround(property);
    }
    
    public void setUseUnbufferedInput(final boolean flag) {
        this.mc.setUseUnbufferedInput(flag);
    }
    
    public void setUseUnicode(final boolean flag) {
        this.mc.setUseUnicode(flag);
    }
    
    public void setUseUsageAdvisor(final boolean useUsageAdvisorFlag) {
        this.mc.setUseUsageAdvisor(useUsageAdvisorFlag);
    }
    
    public void setUtf8OutsideBmpExcludedColumnNamePattern(final String regexPattern) {
        this.mc.setUtf8OutsideBmpExcludedColumnNamePattern(regexPattern);
    }
    
    public void setUtf8OutsideBmpIncludedColumnNamePattern(final String regexPattern) {
        this.mc.setUtf8OutsideBmpIncludedColumnNamePattern(regexPattern);
    }
    
    public void setYearIsDateType(final boolean flag) {
        this.mc.setYearIsDateType(flag);
    }
    
    public void setZeroDateTimeBehavior(final String behavior) {
        this.mc.setZeroDateTimeBehavior(behavior);
    }
    
    public boolean useUnbufferedInput() {
        return this.mc.useUnbufferedInput();
    }
    
    public void initializeExtension(final Extension ex) throws SQLException {
        this.mc.initializeExtension(ex);
    }
    
    public String getProfilerEventHandler() {
        return this.mc.getProfilerEventHandler();
    }
    
    public void setProfilerEventHandler(final String handler) {
        this.mc.setProfilerEventHandler(handler);
    }
    
    public boolean getVerifyServerCertificate() {
        return this.mc.getVerifyServerCertificate();
    }
    
    public void setVerifyServerCertificate(final boolean flag) {
        this.mc.setVerifyServerCertificate(flag);
    }
    
    public boolean getUseLegacyDatetimeCode() {
        return this.mc.getUseLegacyDatetimeCode();
    }
    
    public void setUseLegacyDatetimeCode(final boolean flag) {
        this.mc.setUseLegacyDatetimeCode(flag);
    }
    
    public int getSelfDestructOnPingMaxOperations() {
        return this.mc.getSelfDestructOnPingMaxOperations();
    }
    
    public int getSelfDestructOnPingSecondsLifetime() {
        return this.mc.getSelfDestructOnPingSecondsLifetime();
    }
    
    public void setSelfDestructOnPingMaxOperations(final int maxOperations) {
        this.mc.setSelfDestructOnPingMaxOperations(maxOperations);
    }
    
    public void setSelfDestructOnPingSecondsLifetime(final int seconds) {
        this.mc.setSelfDestructOnPingSecondsLifetime(seconds);
    }
    
    public boolean getUseColumnNamesInFindColumn() {
        return this.mc.getUseColumnNamesInFindColumn();
    }
    
    public void setUseColumnNamesInFindColumn(final boolean flag) {
        this.mc.setUseColumnNamesInFindColumn(flag);
    }
    
    public boolean getUseLocalTransactionState() {
        return this.mc.getUseLocalTransactionState();
    }
    
    public void setUseLocalTransactionState(final boolean flag) {
        this.mc.setUseLocalTransactionState(flag);
    }
    
    public boolean getCompensateOnDuplicateKeyUpdateCounts() {
        return this.mc.getCompensateOnDuplicateKeyUpdateCounts();
    }
    
    public void setCompensateOnDuplicateKeyUpdateCounts(final boolean flag) {
        this.mc.setCompensateOnDuplicateKeyUpdateCounts(flag);
    }
    
    public boolean getUseAffectedRows() {
        return this.mc.getUseAffectedRows();
    }
    
    public void setUseAffectedRows(final boolean flag) {
        this.mc.setUseAffectedRows(flag);
    }
    
    public String getPasswordCharacterEncoding() {
        return this.mc.getPasswordCharacterEncoding();
    }
    
    public void setPasswordCharacterEncoding(final String characterSet) {
        this.mc.setPasswordCharacterEncoding(characterSet);
    }
    
    public int getAutoIncrementIncrement() {
        return this.mc.getAutoIncrementIncrement();
    }
    
    public int getLoadBalanceBlacklistTimeout() {
        return this.mc.getLoadBalanceBlacklistTimeout();
    }
    
    public void setLoadBalanceBlacklistTimeout(final int loadBalanceBlacklistTimeout) {
        this.mc.setLoadBalanceBlacklistTimeout(loadBalanceBlacklistTimeout);
    }
    
    public int getLoadBalancePingTimeout() {
        return this.mc.getLoadBalancePingTimeout();
    }
    
    public void setLoadBalancePingTimeout(final int loadBalancePingTimeout) {
        this.mc.setLoadBalancePingTimeout(loadBalancePingTimeout);
    }
    
    public boolean getLoadBalanceValidateConnectionOnSwapServer() {
        return this.mc.getLoadBalanceValidateConnectionOnSwapServer();
    }
    
    public void setLoadBalanceValidateConnectionOnSwapServer(final boolean loadBalanceValidateConnectionOnSwapServer) {
        this.mc.setLoadBalanceValidateConnectionOnSwapServer(loadBalanceValidateConnectionOnSwapServer);
    }
    
    public void setRetriesAllDown(final int retriesAllDown) {
        this.mc.setRetriesAllDown(retriesAllDown);
    }
    
    public int getRetriesAllDown() {
        return this.mc.getRetriesAllDown();
    }
    
    public ExceptionInterceptor getExceptionInterceptor() {
        return this.pooledConnection.getExceptionInterceptor();
    }
    
    public String getExceptionInterceptors() {
        return this.mc.getExceptionInterceptors();
    }
    
    public void setExceptionInterceptors(final String exceptionInterceptors) {
        this.mc.setExceptionInterceptors(exceptionInterceptors);
    }
    
    public boolean getQueryTimeoutKillsConnection() {
        return this.mc.getQueryTimeoutKillsConnection();
    }
    
    public void setQueryTimeoutKillsConnection(final boolean queryTimeoutKillsConnection) {
        this.mc.setQueryTimeoutKillsConnection(queryTimeoutKillsConnection);
    }
    
    public boolean hasSameProperties(final Connection c) {
        return this.mc.hasSameProperties(c);
    }
    
    public Properties getProperties() {
        return this.mc.getProperties();
    }
    
    public String getHost() {
        return this.mc.getHost();
    }
    
    public void setProxy(final MySQLConnection conn) {
        this.mc.setProxy(conn);
    }
    
    public boolean getRetainStatementAfterResultSetClose() {
        return this.mc.getRetainStatementAfterResultSetClose();
    }
    
    public int getMaxAllowedPacket() {
        return this.mc.getMaxAllowedPacket();
    }
    
    public String getLoadBalanceConnectionGroup() {
        return this.mc.getLoadBalanceConnectionGroup();
    }
    
    public boolean getLoadBalanceEnableJMX() {
        return this.mc.getLoadBalanceEnableJMX();
    }
    
    public String getLoadBalanceExceptionChecker() {
        return this.mc.getLoadBalanceExceptionChecker();
    }
    
    public String getLoadBalanceSQLExceptionSubclassFailover() {
        return this.mc.getLoadBalanceSQLExceptionSubclassFailover();
    }
    
    public String getLoadBalanceSQLStateFailover() {
        return this.mc.getLoadBalanceSQLStateFailover();
    }
    
    public void setLoadBalanceConnectionGroup(final String loadBalanceConnectionGroup) {
        this.mc.setLoadBalanceConnectionGroup(loadBalanceConnectionGroup);
    }
    
    public void setLoadBalanceEnableJMX(final boolean loadBalanceEnableJMX) {
        this.mc.setLoadBalanceEnableJMX(loadBalanceEnableJMX);
    }
    
    public void setLoadBalanceExceptionChecker(final String loadBalanceExceptionChecker) {
        this.mc.setLoadBalanceExceptionChecker(loadBalanceExceptionChecker);
    }
    
    public void setLoadBalanceSQLExceptionSubclassFailover(final String loadBalanceSQLExceptionSubclassFailover) {
        this.mc.setLoadBalanceSQLExceptionSubclassFailover(loadBalanceSQLExceptionSubclassFailover);
    }
    
    public void setLoadBalanceSQLStateFailover(final String loadBalanceSQLStateFailover) {
        this.mc.setLoadBalanceSQLStateFailover(loadBalanceSQLStateFailover);
    }
    
    public String getLoadBalanceAutoCommitStatementRegex() {
        return this.mc.getLoadBalanceAutoCommitStatementRegex();
    }
    
    public int getLoadBalanceAutoCommitStatementThreshold() {
        return this.mc.getLoadBalanceAutoCommitStatementThreshold();
    }
    
    public void setLoadBalanceAutoCommitStatementRegex(final String loadBalanceAutoCommitStatementRegex) {
        this.mc.setLoadBalanceAutoCommitStatementRegex(loadBalanceAutoCommitStatementRegex);
    }
    
    public void setLoadBalanceAutoCommitStatementThreshold(final int loadBalanceAutoCommitStatementThreshold) {
        this.mc.setLoadBalanceAutoCommitStatementThreshold(loadBalanceAutoCommitStatementThreshold);
    }
    
    static {
        if (Util.isJdbc4()) {
            try {
                JDBC_4_CONNECTION_WRAPPER_CTOR = Class.forName("com.mysql.jdbc.jdbc2.optional.JDBC4ConnectionWrapper").getConstructor(MysqlPooledConnection.class, Connection.class, Boolean.TYPE);
                return;
            }
            catch (SecurityException e) {
                throw new RuntimeException(e);
            }
            catch (NoSuchMethodException e2) {
                throw new RuntimeException(e2);
            }
            catch (ClassNotFoundException e3) {
                throw new RuntimeException(e3);
            }
        }
        JDBC_4_CONNECTION_WRAPPER_CTOR = null;
    }
}
