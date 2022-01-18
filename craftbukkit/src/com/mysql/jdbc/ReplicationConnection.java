// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.TimeZone;
import com.mysql.jdbc.log.Log;
import java.sql.Savepoint;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.SQLWarning;
import java.util.Map;
import java.sql.DatabaseMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Properties;

public class ReplicationConnection implements Connection, PingTarget
{
    protected Connection currentConnection;
    protected Connection masterConnection;
    protected Connection slavesConnection;
    
    protected ReplicationConnection() {
    }
    
    public ReplicationConnection(final Properties masterProperties, final Properties slaveProperties) throws SQLException {
        final NonRegisteringDriver driver = new NonRegisteringDriver();
        final StringBuffer masterUrl = new StringBuffer("jdbc:mysql://");
        final StringBuffer slaveUrl = new StringBuffer("jdbc:mysql:loadbalance://");
        final String masterHost = masterProperties.getProperty("HOST");
        if (masterHost != null) {
            masterUrl.append(masterHost);
        }
        for (int numHosts = Integer.parseInt(slaveProperties.getProperty("NUM_HOSTS")), i = 1; i <= numHosts; ++i) {
            final String slaveHost = slaveProperties.getProperty("HOST." + i);
            if (slaveHost != null) {
                if (i > 1) {
                    slaveUrl.append(',');
                }
                slaveUrl.append(slaveHost);
            }
        }
        final String masterDb = masterProperties.getProperty("DBNAME");
        masterUrl.append("/");
        if (masterDb != null) {
            masterUrl.append(masterDb);
        }
        final String slaveDb = slaveProperties.getProperty("DBNAME");
        slaveUrl.append("/");
        if (slaveDb != null) {
            slaveUrl.append(slaveDb);
        }
        slaveProperties.setProperty("roundRobinLoadBalance", "true");
        this.masterConnection = (Connection)driver.connect(masterUrl.toString(), masterProperties);
        (this.slavesConnection = (Connection)driver.connect(slaveUrl.toString(), slaveProperties)).setReadOnly(true);
        this.currentConnection = this.masterConnection;
    }
    
    public synchronized void clearWarnings() throws SQLException {
        this.currentConnection.clearWarnings();
    }
    
    public synchronized void close() throws SQLException {
        this.masterConnection.close();
        this.slavesConnection.close();
    }
    
    public synchronized void commit() throws SQLException {
        this.currentConnection.commit();
    }
    
    public Statement createStatement() throws SQLException {
        final Statement stmt = this.currentConnection.createStatement();
        ((com.mysql.jdbc.Statement)stmt).setPingTarget(this);
        return stmt;
    }
    
    public synchronized Statement createStatement(final int resultSetType, final int resultSetConcurrency) throws SQLException {
        final Statement stmt = this.currentConnection.createStatement(resultSetType, resultSetConcurrency);
        ((com.mysql.jdbc.Statement)stmt).setPingTarget(this);
        return stmt;
    }
    
    public synchronized Statement createStatement(final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        final Statement stmt = this.currentConnection.createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
        ((com.mysql.jdbc.Statement)stmt).setPingTarget(this);
        return stmt;
    }
    
    public synchronized boolean getAutoCommit() throws SQLException {
        return this.currentConnection.getAutoCommit();
    }
    
    public synchronized String getCatalog() throws SQLException {
        return this.currentConnection.getCatalog();
    }
    
    public synchronized Connection getCurrentConnection() {
        return this.currentConnection;
    }
    
    public synchronized int getHoldability() throws SQLException {
        return this.currentConnection.getHoldability();
    }
    
    public synchronized Connection getMasterConnection() {
        return this.masterConnection;
    }
    
    public synchronized DatabaseMetaData getMetaData() throws SQLException {
        return this.currentConnection.getMetaData();
    }
    
    public synchronized Connection getSlavesConnection() {
        return this.slavesConnection;
    }
    
    public synchronized int getTransactionIsolation() throws SQLException {
        return this.currentConnection.getTransactionIsolation();
    }
    
    public synchronized Map getTypeMap() throws SQLException {
        return this.currentConnection.getTypeMap();
    }
    
    public synchronized SQLWarning getWarnings() throws SQLException {
        return this.currentConnection.getWarnings();
    }
    
    public synchronized boolean isClosed() throws SQLException {
        return this.currentConnection.isClosed();
    }
    
    public synchronized boolean isReadOnly() throws SQLException {
        return this.currentConnection == this.slavesConnection;
    }
    
    public synchronized String nativeSQL(final String sql) throws SQLException {
        return this.currentConnection.nativeSQL(sql);
    }
    
    public CallableStatement prepareCall(final String sql) throws SQLException {
        return this.currentConnection.prepareCall(sql);
    }
    
    public synchronized CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        return this.currentConnection.prepareCall(sql, resultSetType, resultSetConcurrency);
    }
    
    public synchronized CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        return this.currentConnection.prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }
    
    public PreparedStatement prepareStatement(final String sql) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.prepareStatement(sql);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized PreparedStatement prepareStatement(final String sql, final int autoGeneratedKeys) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.prepareStatement(sql, autoGeneratedKeys);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.prepareStatement(sql, resultSetType, resultSetConcurrency);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized PreparedStatement prepareStatement(final String sql, final int[] columnIndexes) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.prepareStatement(sql, columnIndexes);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized PreparedStatement prepareStatement(final String sql, final String[] columnNames) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.prepareStatement(sql, columnNames);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized void releaseSavepoint(final Savepoint savepoint) throws SQLException {
        this.currentConnection.releaseSavepoint(savepoint);
    }
    
    public synchronized void rollback() throws SQLException {
        this.currentConnection.rollback();
    }
    
    public synchronized void rollback(final Savepoint savepoint) throws SQLException {
        this.currentConnection.rollback(savepoint);
    }
    
    public synchronized void setAutoCommit(final boolean autoCommit) throws SQLException {
        this.currentConnection.setAutoCommit(autoCommit);
    }
    
    public synchronized void setCatalog(final String catalog) throws SQLException {
        this.currentConnection.setCatalog(catalog);
    }
    
    public synchronized void setHoldability(final int holdability) throws SQLException {
        this.currentConnection.setHoldability(holdability);
    }
    
    public synchronized void setReadOnly(final boolean readOnly) throws SQLException {
        if (readOnly) {
            if (this.currentConnection != this.slavesConnection) {
                this.switchToSlavesConnection();
            }
        }
        else if (this.currentConnection != this.masterConnection) {
            this.switchToMasterConnection();
        }
    }
    
    public synchronized Savepoint setSavepoint() throws SQLException {
        return this.currentConnection.setSavepoint();
    }
    
    public synchronized Savepoint setSavepoint(final String name) throws SQLException {
        return this.currentConnection.setSavepoint(name);
    }
    
    public synchronized void setTransactionIsolation(final int level) throws SQLException {
        this.currentConnection.setTransactionIsolation(level);
    }
    
    public synchronized void setTypeMap(final Map arg0) throws SQLException {
        this.currentConnection.setTypeMap(arg0);
    }
    
    private synchronized void switchToMasterConnection() throws SQLException {
        this.swapConnections(this.masterConnection, this.slavesConnection);
    }
    
    private synchronized void switchToSlavesConnection() throws SQLException {
        this.swapConnections(this.slavesConnection, this.masterConnection);
    }
    
    private synchronized void swapConnections(final Connection switchToConnection, final Connection switchFromConnection) throws SQLException {
        final String switchFromCatalog = switchFromConnection.getCatalog();
        final String switchToCatalog = switchToConnection.getCatalog();
        if (switchToCatalog != null && !switchToCatalog.equals(switchFromCatalog)) {
            switchToConnection.setCatalog(switchFromCatalog);
        }
        else if (switchFromCatalog != null) {
            switchToConnection.setCatalog(switchFromCatalog);
        }
        final boolean switchToAutoCommit = switchToConnection.getAutoCommit();
        final boolean switchFromConnectionAutoCommit = switchFromConnection.getAutoCommit();
        if (switchFromConnectionAutoCommit != switchToAutoCommit) {
            switchToConnection.setAutoCommit(switchFromConnectionAutoCommit);
        }
        final int switchToIsolation = switchToConnection.getTransactionIsolation();
        final int switchFromIsolation = switchFromConnection.getTransactionIsolation();
        if (switchFromIsolation != switchToIsolation) {
            switchToConnection.setTransactionIsolation(switchFromIsolation);
        }
        this.currentConnection = switchToConnection;
    }
    
    public synchronized void doPing() throws SQLException {
        if (this.masterConnection != null) {
            this.masterConnection.ping();
        }
        if (this.slavesConnection != null) {
            this.slavesConnection.ping();
        }
    }
    
    public synchronized void changeUser(final String userName, final String newPassword) throws SQLException {
        this.masterConnection.changeUser(userName, newPassword);
        this.slavesConnection.changeUser(userName, newPassword);
    }
    
    public synchronized void clearHasTriedMaster() {
        this.masterConnection.clearHasTriedMaster();
        this.slavesConnection.clearHasTriedMaster();
    }
    
    public synchronized PreparedStatement clientPrepareStatement(final String sql) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.clientPrepareStatement(sql);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized PreparedStatement clientPrepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.clientPrepareStatement(sql, autoGenKeyIndex);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized PreparedStatement clientPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.clientPrepareStatement(sql, resultSetType, resultSetConcurrency);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized PreparedStatement clientPrepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.clientPrepareStatement(sql, autoGenKeyIndexes);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized PreparedStatement clientPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.clientPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized PreparedStatement clientPrepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.clientPrepareStatement(sql, autoGenKeyColNames);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized int getActiveStatementCount() {
        return this.currentConnection.getActiveStatementCount();
    }
    
    public synchronized long getIdleFor() {
        return this.currentConnection.getIdleFor();
    }
    
    public synchronized Log getLog() throws SQLException {
        return this.currentConnection.getLog();
    }
    
    public synchronized String getServerCharacterEncoding() {
        return this.currentConnection.getServerCharacterEncoding();
    }
    
    public synchronized TimeZone getServerTimezoneTZ() {
        return this.currentConnection.getServerTimezoneTZ();
    }
    
    public synchronized String getStatementComment() {
        return this.currentConnection.getStatementComment();
    }
    
    public synchronized boolean hasTriedMaster() {
        return this.currentConnection.hasTriedMaster();
    }
    
    public synchronized void initializeExtension(final Extension ex) throws SQLException {
        this.currentConnection.initializeExtension(ex);
    }
    
    public synchronized boolean isAbonormallyLongQuery(final long millisOrNanos) {
        return this.currentConnection.isAbonormallyLongQuery(millisOrNanos);
    }
    
    public synchronized boolean isInGlobalTx() {
        return this.currentConnection.isInGlobalTx();
    }
    
    public synchronized boolean isMasterConnection() {
        return this.currentConnection.isMasterConnection();
    }
    
    public synchronized boolean isNoBackslashEscapesSet() {
        return this.currentConnection.isNoBackslashEscapesSet();
    }
    
    public synchronized boolean lowerCaseTableNames() {
        return this.currentConnection.lowerCaseTableNames();
    }
    
    public synchronized boolean parserKnowsUnicode() {
        return this.currentConnection.parserKnowsUnicode();
    }
    
    public synchronized void ping() throws SQLException {
        this.masterConnection.ping();
        this.slavesConnection.ping();
    }
    
    public synchronized void reportQueryTime(final long millisOrNanos) {
        this.currentConnection.reportQueryTime(millisOrNanos);
    }
    
    public synchronized void resetServerState() throws SQLException {
        this.currentConnection.resetServerState();
    }
    
    public synchronized PreparedStatement serverPrepareStatement(final String sql) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.serverPrepareStatement(sql);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized PreparedStatement serverPrepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.serverPrepareStatement(sql, autoGenKeyIndex);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized PreparedStatement serverPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.serverPrepareStatement(sql, resultSetType, resultSetConcurrency);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized PreparedStatement serverPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.serverPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized PreparedStatement serverPrepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.serverPrepareStatement(sql, autoGenKeyIndexes);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized PreparedStatement serverPrepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        final PreparedStatement pstmt = this.currentConnection.serverPrepareStatement(sql, autoGenKeyColNames);
        ((com.mysql.jdbc.Statement)pstmt).setPingTarget(this);
        return pstmt;
    }
    
    public synchronized void setFailedOver(final boolean flag) {
        this.currentConnection.setFailedOver(flag);
    }
    
    public synchronized void setPreferSlaveDuringFailover(final boolean flag) {
        this.currentConnection.setPreferSlaveDuringFailover(flag);
    }
    
    public synchronized void setStatementComment(final String comment) {
        this.masterConnection.setStatementComment(comment);
        this.slavesConnection.setStatementComment(comment);
    }
    
    public synchronized void shutdownServer() throws SQLException {
        this.currentConnection.shutdownServer();
    }
    
    public synchronized boolean supportsIsolationLevel() {
        return this.currentConnection.supportsIsolationLevel();
    }
    
    public synchronized boolean supportsQuotedIdentifiers() {
        return this.currentConnection.supportsQuotedIdentifiers();
    }
    
    public synchronized boolean supportsTransactions() {
        return this.currentConnection.supportsTransactions();
    }
    
    public synchronized boolean versionMeetsMinimum(final int major, final int minor, final int subminor) throws SQLException {
        return this.currentConnection.versionMeetsMinimum(major, minor, subminor);
    }
    
    public synchronized String exposeAsXml() throws SQLException {
        return this.currentConnection.exposeAsXml();
    }
    
    public synchronized boolean getAllowLoadLocalInfile() {
        return this.currentConnection.getAllowLoadLocalInfile();
    }
    
    public synchronized boolean getAllowMultiQueries() {
        return this.currentConnection.getAllowMultiQueries();
    }
    
    public synchronized boolean getAllowNanAndInf() {
        return this.currentConnection.getAllowNanAndInf();
    }
    
    public synchronized boolean getAllowUrlInLocalInfile() {
        return this.currentConnection.getAllowUrlInLocalInfile();
    }
    
    public synchronized boolean getAlwaysSendSetIsolation() {
        return this.currentConnection.getAlwaysSendSetIsolation();
    }
    
    public synchronized boolean getAutoClosePStmtStreams() {
        return this.currentConnection.getAutoClosePStmtStreams();
    }
    
    public synchronized boolean getAutoDeserialize() {
        return this.currentConnection.getAutoDeserialize();
    }
    
    public synchronized boolean getAutoGenerateTestcaseScript() {
        return this.currentConnection.getAutoGenerateTestcaseScript();
    }
    
    public synchronized boolean getAutoReconnectForPools() {
        return this.currentConnection.getAutoReconnectForPools();
    }
    
    public synchronized boolean getAutoSlowLog() {
        return this.currentConnection.getAutoSlowLog();
    }
    
    public synchronized int getBlobSendChunkSize() {
        return this.currentConnection.getBlobSendChunkSize();
    }
    
    public synchronized boolean getBlobsAreStrings() {
        return this.currentConnection.getBlobsAreStrings();
    }
    
    public synchronized boolean getCacheCallableStatements() {
        return this.currentConnection.getCacheCallableStatements();
    }
    
    public synchronized boolean getCacheCallableStmts() {
        return this.currentConnection.getCacheCallableStmts();
    }
    
    public synchronized boolean getCachePrepStmts() {
        return this.currentConnection.getCachePrepStmts();
    }
    
    public synchronized boolean getCachePreparedStatements() {
        return this.currentConnection.getCachePreparedStatements();
    }
    
    public synchronized boolean getCacheResultSetMetadata() {
        return this.currentConnection.getCacheResultSetMetadata();
    }
    
    public synchronized boolean getCacheServerConfiguration() {
        return this.currentConnection.getCacheServerConfiguration();
    }
    
    public synchronized int getCallableStatementCacheSize() {
        return this.currentConnection.getCallableStatementCacheSize();
    }
    
    public synchronized int getCallableStmtCacheSize() {
        return this.currentConnection.getCallableStmtCacheSize();
    }
    
    public synchronized boolean getCapitalizeTypeNames() {
        return this.currentConnection.getCapitalizeTypeNames();
    }
    
    public synchronized String getCharacterSetResults() {
        return this.currentConnection.getCharacterSetResults();
    }
    
    public synchronized String getClientCertificateKeyStorePassword() {
        return this.currentConnection.getClientCertificateKeyStorePassword();
    }
    
    public synchronized String getClientCertificateKeyStoreType() {
        return this.currentConnection.getClientCertificateKeyStoreType();
    }
    
    public synchronized String getClientCertificateKeyStoreUrl() {
        return this.currentConnection.getClientCertificateKeyStoreUrl();
    }
    
    public synchronized String getClientInfoProvider() {
        return this.currentConnection.getClientInfoProvider();
    }
    
    public synchronized String getClobCharacterEncoding() {
        return this.currentConnection.getClobCharacterEncoding();
    }
    
    public synchronized boolean getClobberStreamingResults() {
        return this.currentConnection.getClobberStreamingResults();
    }
    
    public synchronized int getConnectTimeout() {
        return this.currentConnection.getConnectTimeout();
    }
    
    public synchronized String getConnectionCollation() {
        return this.currentConnection.getConnectionCollation();
    }
    
    public synchronized String getConnectionLifecycleInterceptors() {
        return this.currentConnection.getConnectionLifecycleInterceptors();
    }
    
    public synchronized boolean getContinueBatchOnError() {
        return this.currentConnection.getContinueBatchOnError();
    }
    
    public synchronized boolean getCreateDatabaseIfNotExist() {
        return this.currentConnection.getCreateDatabaseIfNotExist();
    }
    
    public synchronized int getDefaultFetchSize() {
        return this.currentConnection.getDefaultFetchSize();
    }
    
    public synchronized boolean getDontTrackOpenResources() {
        return this.currentConnection.getDontTrackOpenResources();
    }
    
    public synchronized boolean getDumpMetadataOnColumnNotFound() {
        return this.currentConnection.getDumpMetadataOnColumnNotFound();
    }
    
    public synchronized boolean getDumpQueriesOnException() {
        return this.currentConnection.getDumpQueriesOnException();
    }
    
    public synchronized boolean getDynamicCalendars() {
        return this.currentConnection.getDynamicCalendars();
    }
    
    public synchronized boolean getElideSetAutoCommits() {
        return this.currentConnection.getElideSetAutoCommits();
    }
    
    public synchronized boolean getEmptyStringsConvertToZero() {
        return this.currentConnection.getEmptyStringsConvertToZero();
    }
    
    public synchronized boolean getEmulateLocators() {
        return this.currentConnection.getEmulateLocators();
    }
    
    public synchronized boolean getEmulateUnsupportedPstmts() {
        return this.currentConnection.getEmulateUnsupportedPstmts();
    }
    
    public synchronized boolean getEnablePacketDebug() {
        return this.currentConnection.getEnablePacketDebug();
    }
    
    public synchronized boolean getEnableQueryTimeouts() {
        return this.currentConnection.getEnableQueryTimeouts();
    }
    
    public synchronized String getEncoding() {
        return this.currentConnection.getEncoding();
    }
    
    public synchronized boolean getExplainSlowQueries() {
        return this.currentConnection.getExplainSlowQueries();
    }
    
    public synchronized boolean getFailOverReadOnly() {
        return this.currentConnection.getFailOverReadOnly();
    }
    
    public synchronized boolean getFunctionsNeverReturnBlobs() {
        return this.currentConnection.getFunctionsNeverReturnBlobs();
    }
    
    public synchronized boolean getGatherPerfMetrics() {
        return this.currentConnection.getGatherPerfMetrics();
    }
    
    public synchronized boolean getGatherPerformanceMetrics() {
        return this.currentConnection.getGatherPerformanceMetrics();
    }
    
    public synchronized boolean getGenerateSimpleParameterMetadata() {
        return this.currentConnection.getGenerateSimpleParameterMetadata();
    }
    
    public synchronized boolean getHoldResultsOpenOverStatementClose() {
        return this.currentConnection.getHoldResultsOpenOverStatementClose();
    }
    
    public synchronized boolean getIgnoreNonTxTables() {
        return this.currentConnection.getIgnoreNonTxTables();
    }
    
    public synchronized boolean getIncludeInnodbStatusInDeadlockExceptions() {
        return this.currentConnection.getIncludeInnodbStatusInDeadlockExceptions();
    }
    
    public synchronized int getInitialTimeout() {
        return this.currentConnection.getInitialTimeout();
    }
    
    public synchronized boolean getInteractiveClient() {
        return this.currentConnection.getInteractiveClient();
    }
    
    public synchronized boolean getIsInteractiveClient() {
        return this.currentConnection.getIsInteractiveClient();
    }
    
    public synchronized boolean getJdbcCompliantTruncation() {
        return this.currentConnection.getJdbcCompliantTruncation();
    }
    
    public synchronized boolean getJdbcCompliantTruncationForReads() {
        return this.currentConnection.getJdbcCompliantTruncationForReads();
    }
    
    public synchronized String getLargeRowSizeThreshold() {
        return this.currentConnection.getLargeRowSizeThreshold();
    }
    
    public synchronized String getLoadBalanceStrategy() {
        return this.currentConnection.getLoadBalanceStrategy();
    }
    
    public synchronized String getLocalSocketAddress() {
        return this.currentConnection.getLocalSocketAddress();
    }
    
    public synchronized int getLocatorFetchBufferSize() {
        return this.currentConnection.getLocatorFetchBufferSize();
    }
    
    public synchronized boolean getLogSlowQueries() {
        return this.currentConnection.getLogSlowQueries();
    }
    
    public synchronized boolean getLogXaCommands() {
        return this.currentConnection.getLogXaCommands();
    }
    
    public synchronized String getLogger() {
        return this.currentConnection.getLogger();
    }
    
    public synchronized String getLoggerClassName() {
        return this.currentConnection.getLoggerClassName();
    }
    
    public synchronized boolean getMaintainTimeStats() {
        return this.currentConnection.getMaintainTimeStats();
    }
    
    public synchronized int getMaxQuerySizeToLog() {
        return this.currentConnection.getMaxQuerySizeToLog();
    }
    
    public synchronized int getMaxReconnects() {
        return this.currentConnection.getMaxReconnects();
    }
    
    public synchronized int getMaxRows() {
        return this.currentConnection.getMaxRows();
    }
    
    public synchronized int getMetadataCacheSize() {
        return this.currentConnection.getMetadataCacheSize();
    }
    
    public synchronized int getNetTimeoutForStreamingResults() {
        return this.currentConnection.getNetTimeoutForStreamingResults();
    }
    
    public synchronized boolean getNoAccessToProcedureBodies() {
        return this.currentConnection.getNoAccessToProcedureBodies();
    }
    
    public synchronized boolean getNoDatetimeStringSync() {
        return this.currentConnection.getNoDatetimeStringSync();
    }
    
    public synchronized boolean getNoTimezoneConversionForTimeType() {
        return this.currentConnection.getNoTimezoneConversionForTimeType();
    }
    
    public synchronized boolean getNullCatalogMeansCurrent() {
        return this.currentConnection.getNullCatalogMeansCurrent();
    }
    
    public synchronized boolean getNullNamePatternMatchesAll() {
        return this.currentConnection.getNullNamePatternMatchesAll();
    }
    
    public synchronized boolean getOverrideSupportsIntegrityEnhancementFacility() {
        return this.currentConnection.getOverrideSupportsIntegrityEnhancementFacility();
    }
    
    public synchronized int getPacketDebugBufferSize() {
        return this.currentConnection.getPacketDebugBufferSize();
    }
    
    public synchronized boolean getPadCharsWithSpace() {
        return this.currentConnection.getPadCharsWithSpace();
    }
    
    public synchronized boolean getParanoid() {
        return this.currentConnection.getParanoid();
    }
    
    public synchronized boolean getPedantic() {
        return this.currentConnection.getPedantic();
    }
    
    public synchronized boolean getPinGlobalTxToPhysicalConnection() {
        return this.currentConnection.getPinGlobalTxToPhysicalConnection();
    }
    
    public synchronized boolean getPopulateInsertRowWithDefaultValues() {
        return this.currentConnection.getPopulateInsertRowWithDefaultValues();
    }
    
    public synchronized int getPrepStmtCacheSize() {
        return this.currentConnection.getPrepStmtCacheSize();
    }
    
    public synchronized int getPrepStmtCacheSqlLimit() {
        return this.currentConnection.getPrepStmtCacheSqlLimit();
    }
    
    public synchronized int getPreparedStatementCacheSize() {
        return this.currentConnection.getPreparedStatementCacheSize();
    }
    
    public synchronized int getPreparedStatementCacheSqlLimit() {
        return this.currentConnection.getPreparedStatementCacheSqlLimit();
    }
    
    public synchronized boolean getProcessEscapeCodesForPrepStmts() {
        return this.currentConnection.getProcessEscapeCodesForPrepStmts();
    }
    
    public synchronized boolean getProfileSQL() {
        return this.currentConnection.getProfileSQL();
    }
    
    public synchronized boolean getProfileSql() {
        return this.currentConnection.getProfileSql();
    }
    
    public synchronized String getProfilerEventHandler() {
        return this.currentConnection.getProfilerEventHandler();
    }
    
    public synchronized String getPropertiesTransform() {
        return this.currentConnection.getPropertiesTransform();
    }
    
    public synchronized int getQueriesBeforeRetryMaster() {
        return this.currentConnection.getQueriesBeforeRetryMaster();
    }
    
    public synchronized boolean getReconnectAtTxEnd() {
        return this.currentConnection.getReconnectAtTxEnd();
    }
    
    public synchronized boolean getRelaxAutoCommit() {
        return this.currentConnection.getRelaxAutoCommit();
    }
    
    public synchronized int getReportMetricsIntervalMillis() {
        return this.currentConnection.getReportMetricsIntervalMillis();
    }
    
    public synchronized boolean getRequireSSL() {
        return this.currentConnection.getRequireSSL();
    }
    
    public synchronized String getResourceId() {
        return this.currentConnection.getResourceId();
    }
    
    public synchronized int getResultSetSizeThreshold() {
        return this.currentConnection.getResultSetSizeThreshold();
    }
    
    public synchronized boolean getRewriteBatchedStatements() {
        return this.currentConnection.getRewriteBatchedStatements();
    }
    
    public synchronized boolean getRollbackOnPooledClose() {
        return this.currentConnection.getRollbackOnPooledClose();
    }
    
    public synchronized boolean getRoundRobinLoadBalance() {
        return this.currentConnection.getRoundRobinLoadBalance();
    }
    
    public synchronized boolean getRunningCTS13() {
        return this.currentConnection.getRunningCTS13();
    }
    
    public synchronized int getSecondsBeforeRetryMaster() {
        return this.currentConnection.getSecondsBeforeRetryMaster();
    }
    
    public synchronized int getSelfDestructOnPingMaxOperations() {
        return this.currentConnection.getSelfDestructOnPingMaxOperations();
    }
    
    public synchronized int getSelfDestructOnPingSecondsLifetime() {
        return this.currentConnection.getSelfDestructOnPingSecondsLifetime();
    }
    
    public synchronized String getServerTimezone() {
        return this.currentConnection.getServerTimezone();
    }
    
    public synchronized String getSessionVariables() {
        return this.currentConnection.getSessionVariables();
    }
    
    public synchronized int getSlowQueryThresholdMillis() {
        return this.currentConnection.getSlowQueryThresholdMillis();
    }
    
    public synchronized long getSlowQueryThresholdNanos() {
        return this.currentConnection.getSlowQueryThresholdNanos();
    }
    
    public synchronized String getSocketFactory() {
        return this.currentConnection.getSocketFactory();
    }
    
    public synchronized String getSocketFactoryClassName() {
        return this.currentConnection.getSocketFactoryClassName();
    }
    
    public synchronized int getSocketTimeout() {
        return this.currentConnection.getSocketTimeout();
    }
    
    public synchronized String getStatementInterceptors() {
        return this.currentConnection.getStatementInterceptors();
    }
    
    public synchronized boolean getStrictFloatingPoint() {
        return this.currentConnection.getStrictFloatingPoint();
    }
    
    public synchronized boolean getStrictUpdates() {
        return this.currentConnection.getStrictUpdates();
    }
    
    public synchronized boolean getTcpKeepAlive() {
        return this.currentConnection.getTcpKeepAlive();
    }
    
    public synchronized boolean getTcpNoDelay() {
        return this.currentConnection.getTcpNoDelay();
    }
    
    public synchronized int getTcpRcvBuf() {
        return this.currentConnection.getTcpRcvBuf();
    }
    
    public synchronized int getTcpSndBuf() {
        return this.currentConnection.getTcpSndBuf();
    }
    
    public synchronized int getTcpTrafficClass() {
        return this.currentConnection.getTcpTrafficClass();
    }
    
    public synchronized boolean getTinyInt1isBit() {
        return this.currentConnection.getTinyInt1isBit();
    }
    
    public synchronized boolean getTraceProtocol() {
        return this.currentConnection.getTraceProtocol();
    }
    
    public synchronized boolean getTransformedBitIsBoolean() {
        return this.currentConnection.getTransformedBitIsBoolean();
    }
    
    public synchronized boolean getTreatUtilDateAsTimestamp() {
        return this.currentConnection.getTreatUtilDateAsTimestamp();
    }
    
    public synchronized String getTrustCertificateKeyStorePassword() {
        return this.currentConnection.getTrustCertificateKeyStorePassword();
    }
    
    public synchronized String getTrustCertificateKeyStoreType() {
        return this.currentConnection.getTrustCertificateKeyStoreType();
    }
    
    public synchronized String getTrustCertificateKeyStoreUrl() {
        return this.currentConnection.getTrustCertificateKeyStoreUrl();
    }
    
    public synchronized boolean getUltraDevHack() {
        return this.currentConnection.getUltraDevHack();
    }
    
    public synchronized boolean getUseBlobToStoreUTF8OutsideBMP() {
        return this.currentConnection.getUseBlobToStoreUTF8OutsideBMP();
    }
    
    public synchronized boolean getUseCompression() {
        return this.currentConnection.getUseCompression();
    }
    
    public synchronized String getUseConfigs() {
        return this.currentConnection.getUseConfigs();
    }
    
    public synchronized boolean getUseCursorFetch() {
        return this.currentConnection.getUseCursorFetch();
    }
    
    public synchronized boolean getUseDirectRowUnpack() {
        return this.currentConnection.getUseDirectRowUnpack();
    }
    
    public synchronized boolean getUseDynamicCharsetInfo() {
        return this.currentConnection.getUseDynamicCharsetInfo();
    }
    
    public synchronized boolean getUseFastDateParsing() {
        return this.currentConnection.getUseFastDateParsing();
    }
    
    public synchronized boolean getUseFastIntParsing() {
        return this.currentConnection.getUseFastIntParsing();
    }
    
    public synchronized boolean getUseGmtMillisForDatetimes() {
        return this.currentConnection.getUseGmtMillisForDatetimes();
    }
    
    public synchronized boolean getUseHostsInPrivileges() {
        return this.currentConnection.getUseHostsInPrivileges();
    }
    
    public synchronized boolean getUseInformationSchema() {
        return this.currentConnection.getUseInformationSchema();
    }
    
    public synchronized boolean getUseJDBCCompliantTimezoneShift() {
        return this.currentConnection.getUseJDBCCompliantTimezoneShift();
    }
    
    public synchronized boolean getUseJvmCharsetConverters() {
        return this.currentConnection.getUseJvmCharsetConverters();
    }
    
    public synchronized boolean getUseLegacyDatetimeCode() {
        return this.currentConnection.getUseLegacyDatetimeCode();
    }
    
    public synchronized boolean getUseLocalSessionState() {
        return this.currentConnection.getUseLocalSessionState();
    }
    
    public synchronized boolean getUseNanosForElapsedTime() {
        return this.currentConnection.getUseNanosForElapsedTime();
    }
    
    public synchronized boolean getUseOldAliasMetadataBehavior() {
        return this.currentConnection.getUseOldAliasMetadataBehavior();
    }
    
    public synchronized boolean getUseOldUTF8Behavior() {
        return this.currentConnection.getUseOldUTF8Behavior();
    }
    
    public synchronized boolean getUseOnlyServerErrorMessages() {
        return this.currentConnection.getUseOnlyServerErrorMessages();
    }
    
    public synchronized boolean getUseReadAheadInput() {
        return this.currentConnection.getUseReadAheadInput();
    }
    
    public synchronized boolean getUseSSL() {
        return this.currentConnection.getUseSSL();
    }
    
    public synchronized boolean getUseSSPSCompatibleTimezoneShift() {
        return this.currentConnection.getUseSSPSCompatibleTimezoneShift();
    }
    
    public synchronized boolean getUseServerPrepStmts() {
        return this.currentConnection.getUseServerPrepStmts();
    }
    
    public synchronized boolean getUseServerPreparedStmts() {
        return this.currentConnection.getUseServerPreparedStmts();
    }
    
    public synchronized boolean getUseSqlStateCodes() {
        return this.currentConnection.getUseSqlStateCodes();
    }
    
    public synchronized boolean getUseStreamLengthsInPrepStmts() {
        return this.currentConnection.getUseStreamLengthsInPrepStmts();
    }
    
    public synchronized boolean getUseTimezone() {
        return this.currentConnection.getUseTimezone();
    }
    
    public synchronized boolean getUseUltraDevWorkAround() {
        return this.currentConnection.getUseUltraDevWorkAround();
    }
    
    public synchronized boolean getUseUnbufferedInput() {
        return this.currentConnection.getUseUnbufferedInput();
    }
    
    public synchronized boolean getUseUnicode() {
        return this.currentConnection.getUseUnicode();
    }
    
    public synchronized boolean getUseUsageAdvisor() {
        return this.currentConnection.getUseUsageAdvisor();
    }
    
    public synchronized String getUtf8OutsideBmpExcludedColumnNamePattern() {
        return this.currentConnection.getUtf8OutsideBmpExcludedColumnNamePattern();
    }
    
    public synchronized String getUtf8OutsideBmpIncludedColumnNamePattern() {
        return this.currentConnection.getUtf8OutsideBmpIncludedColumnNamePattern();
    }
    
    public synchronized boolean getVerifyServerCertificate() {
        return this.currentConnection.getVerifyServerCertificate();
    }
    
    public synchronized boolean getYearIsDateType() {
        return this.currentConnection.getYearIsDateType();
    }
    
    public synchronized String getZeroDateTimeBehavior() {
        return this.currentConnection.getZeroDateTimeBehavior();
    }
    
    public synchronized void setAllowLoadLocalInfile(final boolean property) {
    }
    
    public synchronized void setAllowMultiQueries(final boolean property) {
    }
    
    public synchronized void setAllowNanAndInf(final boolean flag) {
    }
    
    public synchronized void setAllowUrlInLocalInfile(final boolean flag) {
    }
    
    public synchronized void setAlwaysSendSetIsolation(final boolean flag) {
    }
    
    public synchronized void setAutoClosePStmtStreams(final boolean flag) {
    }
    
    public synchronized void setAutoDeserialize(final boolean flag) {
    }
    
    public synchronized void setAutoGenerateTestcaseScript(final boolean flag) {
    }
    
    public synchronized void setAutoReconnect(final boolean flag) {
    }
    
    public synchronized void setAutoReconnectForConnectionPools(final boolean property) {
    }
    
    public synchronized void setAutoReconnectForPools(final boolean flag) {
    }
    
    public synchronized void setAutoSlowLog(final boolean flag) {
    }
    
    public synchronized void setBlobSendChunkSize(final String value) throws SQLException {
    }
    
    public synchronized void setBlobsAreStrings(final boolean flag) {
    }
    
    public synchronized void setCacheCallableStatements(final boolean flag) {
    }
    
    public synchronized void setCacheCallableStmts(final boolean flag) {
    }
    
    public synchronized void setCachePrepStmts(final boolean flag) {
    }
    
    public synchronized void setCachePreparedStatements(final boolean flag) {
    }
    
    public synchronized void setCacheResultSetMetadata(final boolean property) {
    }
    
    public synchronized void setCacheServerConfiguration(final boolean flag) {
    }
    
    public synchronized void setCallableStatementCacheSize(final int size) {
    }
    
    public synchronized void setCallableStmtCacheSize(final int cacheSize) {
    }
    
    public synchronized void setCapitalizeDBMDTypes(final boolean property) {
    }
    
    public synchronized void setCapitalizeTypeNames(final boolean flag) {
    }
    
    public synchronized void setCharacterEncoding(final String encoding) {
    }
    
    public synchronized void setCharacterSetResults(final String characterSet) {
    }
    
    public synchronized void setClientCertificateKeyStorePassword(final String value) {
    }
    
    public synchronized void setClientCertificateKeyStoreType(final String value) {
    }
    
    public synchronized void setClientCertificateKeyStoreUrl(final String value) {
    }
    
    public synchronized void setClientInfoProvider(final String classname) {
    }
    
    public synchronized void setClobCharacterEncoding(final String encoding) {
    }
    
    public synchronized void setClobberStreamingResults(final boolean flag) {
    }
    
    public synchronized void setConnectTimeout(final int timeoutMs) {
    }
    
    public synchronized void setConnectionCollation(final String collation) {
    }
    
    public synchronized void setConnectionLifecycleInterceptors(final String interceptors) {
    }
    
    public synchronized void setContinueBatchOnError(final boolean property) {
    }
    
    public synchronized void setCreateDatabaseIfNotExist(final boolean flag) {
    }
    
    public synchronized void setDefaultFetchSize(final int n) {
    }
    
    public synchronized void setDetectServerPreparedStmts(final boolean property) {
    }
    
    public synchronized void setDontTrackOpenResources(final boolean flag) {
    }
    
    public synchronized void setDumpMetadataOnColumnNotFound(final boolean flag) {
    }
    
    public synchronized void setDumpQueriesOnException(final boolean flag) {
    }
    
    public synchronized void setDynamicCalendars(final boolean flag) {
    }
    
    public synchronized void setElideSetAutoCommits(final boolean flag) {
    }
    
    public synchronized void setEmptyStringsConvertToZero(final boolean flag) {
    }
    
    public synchronized void setEmulateLocators(final boolean property) {
    }
    
    public synchronized void setEmulateUnsupportedPstmts(final boolean flag) {
    }
    
    public synchronized void setEnablePacketDebug(final boolean flag) {
    }
    
    public synchronized void setEnableQueryTimeouts(final boolean flag) {
    }
    
    public synchronized void setEncoding(final String property) {
    }
    
    public synchronized void setExplainSlowQueries(final boolean flag) {
    }
    
    public synchronized void setFailOverReadOnly(final boolean flag) {
    }
    
    public synchronized void setFunctionsNeverReturnBlobs(final boolean flag) {
    }
    
    public synchronized void setGatherPerfMetrics(final boolean flag) {
    }
    
    public synchronized void setGatherPerformanceMetrics(final boolean flag) {
    }
    
    public synchronized void setGenerateSimpleParameterMetadata(final boolean flag) {
    }
    
    public synchronized void setHoldResultsOpenOverStatementClose(final boolean flag) {
    }
    
    public synchronized void setIgnoreNonTxTables(final boolean property) {
    }
    
    public synchronized void setIncludeInnodbStatusInDeadlockExceptions(final boolean flag) {
    }
    
    public synchronized void setInitialTimeout(final int property) {
    }
    
    public synchronized void setInteractiveClient(final boolean property) {
    }
    
    public synchronized void setIsInteractiveClient(final boolean property) {
    }
    
    public synchronized void setJdbcCompliantTruncation(final boolean flag) {
    }
    
    public synchronized void setJdbcCompliantTruncationForReads(final boolean jdbcCompliantTruncationForReads) {
    }
    
    public synchronized void setLargeRowSizeThreshold(final String value) {
    }
    
    public synchronized void setLoadBalanceStrategy(final String strategy) {
    }
    
    public synchronized void setLocalSocketAddress(final String address) {
    }
    
    public synchronized void setLocatorFetchBufferSize(final String value) throws SQLException {
    }
    
    public synchronized void setLogSlowQueries(final boolean flag) {
    }
    
    public synchronized void setLogXaCommands(final boolean flag) {
    }
    
    public synchronized void setLogger(final String property) {
    }
    
    public synchronized void setLoggerClassName(final String className) {
    }
    
    public synchronized void setMaintainTimeStats(final boolean flag) {
    }
    
    public synchronized void setMaxQuerySizeToLog(final int sizeInBytes) {
    }
    
    public synchronized void setMaxReconnects(final int property) {
    }
    
    public synchronized void setMaxRows(final int property) {
    }
    
    public synchronized void setMetadataCacheSize(final int value) {
    }
    
    public synchronized void setNetTimeoutForStreamingResults(final int value) {
    }
    
    public synchronized void setNoAccessToProcedureBodies(final boolean flag) {
    }
    
    public synchronized void setNoDatetimeStringSync(final boolean flag) {
    }
    
    public synchronized void setNoTimezoneConversionForTimeType(final boolean flag) {
    }
    
    public synchronized void setNullCatalogMeansCurrent(final boolean value) {
    }
    
    public synchronized void setNullNamePatternMatchesAll(final boolean value) {
    }
    
    public synchronized void setOverrideSupportsIntegrityEnhancementFacility(final boolean flag) {
    }
    
    public synchronized void setPacketDebugBufferSize(final int size) {
    }
    
    public synchronized void setPadCharsWithSpace(final boolean flag) {
    }
    
    public synchronized void setParanoid(final boolean property) {
    }
    
    public synchronized void setPedantic(final boolean property) {
    }
    
    public synchronized void setPinGlobalTxToPhysicalConnection(final boolean flag) {
    }
    
    public synchronized void setPopulateInsertRowWithDefaultValues(final boolean flag) {
    }
    
    public synchronized void setPrepStmtCacheSize(final int cacheSize) {
    }
    
    public synchronized void setPrepStmtCacheSqlLimit(final int sqlLimit) {
    }
    
    public synchronized void setPreparedStatementCacheSize(final int cacheSize) {
    }
    
    public synchronized void setPreparedStatementCacheSqlLimit(final int cacheSqlLimit) {
    }
    
    public synchronized void setProcessEscapeCodesForPrepStmts(final boolean flag) {
    }
    
    public synchronized void setProfileSQL(final boolean flag) {
    }
    
    public synchronized void setProfileSql(final boolean property) {
    }
    
    public synchronized void setProfilerEventHandler(final String handler) {
    }
    
    public synchronized void setPropertiesTransform(final String value) {
    }
    
    public synchronized void setQueriesBeforeRetryMaster(final int property) {
    }
    
    public synchronized void setReconnectAtTxEnd(final boolean property) {
    }
    
    public synchronized void setRelaxAutoCommit(final boolean property) {
    }
    
    public synchronized void setReportMetricsIntervalMillis(final int millis) {
    }
    
    public synchronized void setRequireSSL(final boolean property) {
    }
    
    public synchronized void setResourceId(final String resourceId) {
    }
    
    public synchronized void setResultSetSizeThreshold(final int threshold) {
    }
    
    public synchronized void setRetainStatementAfterResultSetClose(final boolean flag) {
    }
    
    public synchronized void setRewriteBatchedStatements(final boolean flag) {
    }
    
    public synchronized void setRollbackOnPooledClose(final boolean flag) {
    }
    
    public synchronized void setRoundRobinLoadBalance(final boolean flag) {
    }
    
    public synchronized void setRunningCTS13(final boolean flag) {
    }
    
    public synchronized void setSecondsBeforeRetryMaster(final int property) {
    }
    
    public synchronized void setSelfDestructOnPingMaxOperations(final int maxOperations) {
    }
    
    public synchronized void setSelfDestructOnPingSecondsLifetime(final int seconds) {
    }
    
    public synchronized void setServerTimezone(final String property) {
    }
    
    public synchronized void setSessionVariables(final String variables) {
    }
    
    public synchronized void setSlowQueryThresholdMillis(final int millis) {
    }
    
    public synchronized void setSlowQueryThresholdNanos(final long nanos) {
    }
    
    public synchronized void setSocketFactory(final String name) {
    }
    
    public synchronized void setSocketFactoryClassName(final String property) {
    }
    
    public synchronized void setSocketTimeout(final int property) {
    }
    
    public synchronized void setStatementInterceptors(final String value) {
    }
    
    public synchronized void setStrictFloatingPoint(final boolean property) {
    }
    
    public synchronized void setStrictUpdates(final boolean property) {
    }
    
    public synchronized void setTcpKeepAlive(final boolean flag) {
    }
    
    public synchronized void setTcpNoDelay(final boolean flag) {
    }
    
    public synchronized void setTcpRcvBuf(final int bufSize) {
    }
    
    public synchronized void setTcpSndBuf(final int bufSize) {
    }
    
    public synchronized void setTcpTrafficClass(final int classFlags) {
    }
    
    public synchronized void setTinyInt1isBit(final boolean flag) {
    }
    
    public synchronized void setTraceProtocol(final boolean flag) {
    }
    
    public synchronized void setTransformedBitIsBoolean(final boolean flag) {
    }
    
    public synchronized void setTreatUtilDateAsTimestamp(final boolean flag) {
    }
    
    public synchronized void setTrustCertificateKeyStorePassword(final String value) {
    }
    
    public synchronized void setTrustCertificateKeyStoreType(final String value) {
    }
    
    public synchronized void setTrustCertificateKeyStoreUrl(final String value) {
    }
    
    public synchronized void setUltraDevHack(final boolean flag) {
    }
    
    public synchronized void setUseBlobToStoreUTF8OutsideBMP(final boolean flag) {
    }
    
    public synchronized void setUseCompression(final boolean property) {
    }
    
    public synchronized void setUseConfigs(final String configs) {
    }
    
    public synchronized void setUseCursorFetch(final boolean flag) {
    }
    
    public synchronized void setUseDirectRowUnpack(final boolean flag) {
    }
    
    public synchronized void setUseDynamicCharsetInfo(final boolean flag) {
    }
    
    public synchronized void setUseFastDateParsing(final boolean flag) {
    }
    
    public synchronized void setUseFastIntParsing(final boolean flag) {
    }
    
    public synchronized void setUseGmtMillisForDatetimes(final boolean flag) {
    }
    
    public synchronized void setUseHostsInPrivileges(final boolean property) {
    }
    
    public synchronized void setUseInformationSchema(final boolean flag) {
    }
    
    public synchronized void setUseJDBCCompliantTimezoneShift(final boolean flag) {
    }
    
    public synchronized void setUseJvmCharsetConverters(final boolean flag) {
    }
    
    public synchronized void setUseLegacyDatetimeCode(final boolean flag) {
    }
    
    public synchronized void setUseLocalSessionState(final boolean flag) {
    }
    
    public synchronized void setUseNanosForElapsedTime(final boolean flag) {
    }
    
    public synchronized void setUseOldAliasMetadataBehavior(final boolean flag) {
    }
    
    public synchronized void setUseOldUTF8Behavior(final boolean flag) {
    }
    
    public synchronized void setUseOnlyServerErrorMessages(final boolean flag) {
    }
    
    public synchronized void setUseReadAheadInput(final boolean flag) {
    }
    
    public synchronized void setUseSSL(final boolean property) {
    }
    
    public synchronized void setUseSSPSCompatibleTimezoneShift(final boolean flag) {
    }
    
    public synchronized void setUseServerPrepStmts(final boolean flag) {
    }
    
    public synchronized void setUseServerPreparedStmts(final boolean flag) {
    }
    
    public synchronized void setUseSqlStateCodes(final boolean flag) {
    }
    
    public synchronized void setUseStreamLengthsInPrepStmts(final boolean property) {
    }
    
    public synchronized void setUseTimezone(final boolean property) {
    }
    
    public synchronized void setUseUltraDevWorkAround(final boolean property) {
    }
    
    public synchronized void setUseUnbufferedInput(final boolean flag) {
    }
    
    public synchronized void setUseUnicode(final boolean flag) {
    }
    
    public synchronized void setUseUsageAdvisor(final boolean useUsageAdvisorFlag) {
    }
    
    public synchronized void setUtf8OutsideBmpExcludedColumnNamePattern(final String regexPattern) {
    }
    
    public synchronized void setUtf8OutsideBmpIncludedColumnNamePattern(final String regexPattern) {
    }
    
    public synchronized void setVerifyServerCertificate(final boolean flag) {
    }
    
    public synchronized void setYearIsDateType(final boolean flag) {
    }
    
    public synchronized void setZeroDateTimeBehavior(final String behavior) {
    }
    
    public synchronized boolean useUnbufferedInput() {
        return this.currentConnection.useUnbufferedInput();
    }
    
    public synchronized boolean isSameResource(final Connection c) {
        return this.currentConnection.isSameResource(c);
    }
    
    public void setInGlobalTx(final boolean flag) {
        this.currentConnection.setInGlobalTx(flag);
    }
    
    public boolean getUseColumnNamesInFindColumn() {
        return this.currentConnection.getUseColumnNamesInFindColumn();
    }
    
    public void setUseColumnNamesInFindColumn(final boolean flag) {
    }
    
    public boolean getUseLocalTransactionState() {
        return this.currentConnection.getUseLocalTransactionState();
    }
    
    public void setUseLocalTransactionState(final boolean flag) {
    }
    
    public boolean getCompensateOnDuplicateKeyUpdateCounts() {
        return this.currentConnection.getCompensateOnDuplicateKeyUpdateCounts();
    }
    
    public void setCompensateOnDuplicateKeyUpdateCounts(final boolean flag) {
    }
    
    public boolean getUseAffectedRows() {
        return this.currentConnection.getUseAffectedRows();
    }
    
    public void setUseAffectedRows(final boolean flag) {
    }
    
    public String getPasswordCharacterEncoding() {
        return this.currentConnection.getPasswordCharacterEncoding();
    }
    
    public void setPasswordCharacterEncoding(final String characterSet) {
        this.currentConnection.setPasswordCharacterEncoding(characterSet);
    }
    
    public int getAutoIncrementIncrement() {
        return this.currentConnection.getAutoIncrementIncrement();
    }
    
    public int getLoadBalanceBlacklistTimeout() {
        return this.currentConnection.getLoadBalanceBlacklistTimeout();
    }
    
    public void setLoadBalanceBlacklistTimeout(final int loadBalanceBlacklistTimeout) {
        this.currentConnection.setLoadBalanceBlacklistTimeout(loadBalanceBlacklistTimeout);
    }
    
    public int getLoadBalancePingTimeout() {
        return this.currentConnection.getLoadBalancePingTimeout();
    }
    
    public void setLoadBalancePingTimeout(final int loadBalancePingTimeout) {
        this.currentConnection.setLoadBalancePingTimeout(loadBalancePingTimeout);
    }
    
    public boolean getLoadBalanceValidateConnectionOnSwapServer() {
        return this.currentConnection.getLoadBalanceValidateConnectionOnSwapServer();
    }
    
    public void setLoadBalanceValidateConnectionOnSwapServer(final boolean loadBalanceValidateConnectionOnSwapServer) {
        this.currentConnection.setLoadBalanceValidateConnectionOnSwapServer(loadBalanceValidateConnectionOnSwapServer);
    }
    
    public int getRetriesAllDown() {
        return this.currentConnection.getRetriesAllDown();
    }
    
    public void setRetriesAllDown(final int retriesAllDown) {
        this.currentConnection.setRetriesAllDown(retriesAllDown);
    }
    
    public ExceptionInterceptor getExceptionInterceptor() {
        return this.currentConnection.getExceptionInterceptor();
    }
    
    public String getExceptionInterceptors() {
        return this.currentConnection.getExceptionInterceptors();
    }
    
    public void setExceptionInterceptors(final String exceptionInterceptors) {
        this.currentConnection.setExceptionInterceptors(exceptionInterceptors);
    }
    
    public boolean getQueryTimeoutKillsConnection() {
        return this.currentConnection.getQueryTimeoutKillsConnection();
    }
    
    public void setQueryTimeoutKillsConnection(final boolean queryTimeoutKillsConnection) {
        this.currentConnection.setQueryTimeoutKillsConnection(queryTimeoutKillsConnection);
    }
    
    public boolean hasSameProperties(final Connection c) {
        return this.masterConnection.hasSameProperties(c) && this.slavesConnection.hasSameProperties(c);
    }
    
    public Properties getProperties() {
        final Properties props = new Properties();
        props.putAll(this.masterConnection.getProperties());
        props.putAll(this.slavesConnection.getProperties());
        return props;
    }
    
    public String getHost() {
        return this.currentConnection.getHost();
    }
    
    public void setProxy(final MySQLConnection proxy) {
        this.currentConnection.setProxy(proxy);
    }
    
    public boolean getRetainStatementAfterResultSetClose() {
        return this.currentConnection.getRetainStatementAfterResultSetClose();
    }
    
    public int getMaxAllowedPacket() {
        return this.currentConnection.getMaxAllowedPacket();
    }
    
    public String getLoadBalanceConnectionGroup() {
        return this.currentConnection.getLoadBalanceConnectionGroup();
    }
    
    public boolean getLoadBalanceEnableJMX() {
        return this.currentConnection.getLoadBalanceEnableJMX();
    }
    
    public String getLoadBalanceExceptionChecker() {
        return this.currentConnection.getLoadBalanceExceptionChecker();
    }
    
    public String getLoadBalanceSQLExceptionSubclassFailover() {
        return this.currentConnection.getLoadBalanceSQLExceptionSubclassFailover();
    }
    
    public String getLoadBalanceSQLStateFailover() {
        return this.currentConnection.getLoadBalanceSQLStateFailover();
    }
    
    public void setLoadBalanceConnectionGroup(final String loadBalanceConnectionGroup) {
        this.currentConnection.setLoadBalanceConnectionGroup(loadBalanceConnectionGroup);
    }
    
    public void setLoadBalanceEnableJMX(final boolean loadBalanceEnableJMX) {
        this.currentConnection.setLoadBalanceEnableJMX(loadBalanceEnableJMX);
    }
    
    public void setLoadBalanceExceptionChecker(final String loadBalanceExceptionChecker) {
        this.currentConnection.setLoadBalanceExceptionChecker(loadBalanceExceptionChecker);
    }
    
    public void setLoadBalanceSQLExceptionSubclassFailover(final String loadBalanceSQLExceptionSubclassFailover) {
        this.currentConnection.setLoadBalanceSQLExceptionSubclassFailover(loadBalanceSQLExceptionSubclassFailover);
    }
    
    public void setLoadBalanceSQLStateFailover(final String loadBalanceSQLStateFailover) {
        this.currentConnection.setLoadBalanceSQLStateFailover(loadBalanceSQLStateFailover);
    }
    
    public String getLoadBalanceAutoCommitStatementRegex() {
        return this.currentConnection.getLoadBalanceAutoCommitStatementRegex();
    }
    
    public int getLoadBalanceAutoCommitStatementThreshold() {
        return this.currentConnection.getLoadBalanceAutoCommitStatementThreshold();
    }
    
    public void setLoadBalanceAutoCommitStatementRegex(final String loadBalanceAutoCommitStatementRegex) {
        this.currentConnection.setLoadBalanceAutoCommitStatementRegex(loadBalanceAutoCommitStatementRegex);
    }
    
    public void setLoadBalanceAutoCommitStatementThreshold(final int loadBalanceAutoCommitStatementThreshold) {
        this.currentConnection.setLoadBalanceAutoCommitStatementThreshold(loadBalanceAutoCommitStatementThreshold);
    }
}
