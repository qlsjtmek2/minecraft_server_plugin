// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.Savepoint;
import java.sql.CallableStatement;
import java.sql.SQLWarning;
import java.util.Map;
import java.util.List;
import java.util.Properties;
import java.sql.DatabaseMetaData;
import com.mysql.jdbc.log.Log;
import java.util.TimeZone;
import java.util.Timer;
import java.util.Calendar;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoadBalancedMySQLConnection implements MySQLConnection
{
    protected LoadBalancingConnectionProxy proxy;
    
    public LoadBalancingConnectionProxy getProxy() {
        return this.proxy;
    }
    
    protected MySQLConnection getActiveMySQLConnection() {
        return this.proxy.currentConn;
    }
    
    public LoadBalancedMySQLConnection(final LoadBalancingConnectionProxy proxy) {
        this.proxy = proxy;
    }
    
    public void abortInternal() throws SQLException {
        this.getActiveMySQLConnection().abortInternal();
    }
    
    public void changeUser(final String userName, final String newPassword) throws SQLException {
        this.getActiveMySQLConnection().changeUser(userName, newPassword);
    }
    
    public void checkClosed() throws SQLException {
        this.getActiveMySQLConnection().checkClosed();
    }
    
    public void clearHasTriedMaster() {
        this.getActiveMySQLConnection().clearHasTriedMaster();
    }
    
    public void clearWarnings() throws SQLException {
        this.getActiveMySQLConnection().clearWarnings();
    }
    
    public PreparedStatement clientPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        return this.getActiveMySQLConnection().clientPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }
    
    public PreparedStatement clientPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        return this.getActiveMySQLConnection().clientPrepareStatement(sql, resultSetType, resultSetConcurrency);
    }
    
    public PreparedStatement clientPrepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        return this.getActiveMySQLConnection().clientPrepareStatement(sql, autoGenKeyIndex);
    }
    
    public PreparedStatement clientPrepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        return this.getActiveMySQLConnection().clientPrepareStatement(sql, autoGenKeyIndexes);
    }
    
    public PreparedStatement clientPrepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        return this.getActiveMySQLConnection().clientPrepareStatement(sql, autoGenKeyColNames);
    }
    
    public PreparedStatement clientPrepareStatement(final String sql) throws SQLException {
        return this.getActiveMySQLConnection().clientPrepareStatement(sql);
    }
    
    public synchronized void close() throws SQLException {
        this.getActiveMySQLConnection().close();
    }
    
    public void commit() throws SQLException {
        this.getActiveMySQLConnection().commit();
    }
    
    public void createNewIO(final boolean isForReconnect) throws SQLException {
        this.getActiveMySQLConnection().createNewIO(isForReconnect);
    }
    
    public Statement createStatement() throws SQLException {
        return this.getActiveMySQLConnection().createStatement();
    }
    
    public Statement createStatement(final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        return this.getActiveMySQLConnection().createStatement(resultSetType, resultSetConcurrency, resultSetHoldability);
    }
    
    public Statement createStatement(final int resultSetType, final int resultSetConcurrency) throws SQLException {
        return this.getActiveMySQLConnection().createStatement(resultSetType, resultSetConcurrency);
    }
    
    public void dumpTestcaseQuery(final String query) {
        this.getActiveMySQLConnection().dumpTestcaseQuery(query);
    }
    
    public Connection duplicate() throws SQLException {
        return this.getActiveMySQLConnection().duplicate();
    }
    
    public ResultSetInternalMethods execSQL(final StatementImpl callingStatement, final String sql, final int maxRows, final Buffer packet, final int resultSetType, final int resultSetConcurrency, final boolean streamResults, final String catalog, final Field[] cachedMetadata, final boolean isBatch) throws SQLException {
        return this.getActiveMySQLConnection().execSQL(callingStatement, sql, maxRows, packet, resultSetType, resultSetConcurrency, streamResults, catalog, cachedMetadata, isBatch);
    }
    
    public ResultSetInternalMethods execSQL(final StatementImpl callingStatement, final String sql, final int maxRows, final Buffer packet, final int resultSetType, final int resultSetConcurrency, final boolean streamResults, final String catalog, final Field[] cachedMetadata) throws SQLException {
        return this.getActiveMySQLConnection().execSQL(callingStatement, sql, maxRows, packet, resultSetType, resultSetConcurrency, streamResults, catalog, cachedMetadata);
    }
    
    public String extractSqlFromPacket(final String possibleSqlQuery, final Buffer queryPacket, final int endOfQueryPacketPosition) throws SQLException {
        return this.getActiveMySQLConnection().extractSqlFromPacket(possibleSqlQuery, queryPacket, endOfQueryPacketPosition);
    }
    
    public String exposeAsXml() throws SQLException {
        return this.getActiveMySQLConnection().exposeAsXml();
    }
    
    public boolean getAllowLoadLocalInfile() {
        return this.getActiveMySQLConnection().getAllowLoadLocalInfile();
    }
    
    public boolean getAllowMultiQueries() {
        return this.getActiveMySQLConnection().getAllowMultiQueries();
    }
    
    public boolean getAllowNanAndInf() {
        return this.getActiveMySQLConnection().getAllowNanAndInf();
    }
    
    public boolean getAllowUrlInLocalInfile() {
        return this.getActiveMySQLConnection().getAllowUrlInLocalInfile();
    }
    
    public boolean getAlwaysSendSetIsolation() {
        return this.getActiveMySQLConnection().getAlwaysSendSetIsolation();
    }
    
    public boolean getAutoClosePStmtStreams() {
        return this.getActiveMySQLConnection().getAutoClosePStmtStreams();
    }
    
    public boolean getAutoDeserialize() {
        return this.getActiveMySQLConnection().getAutoDeserialize();
    }
    
    public boolean getAutoGenerateTestcaseScript() {
        return this.getActiveMySQLConnection().getAutoGenerateTestcaseScript();
    }
    
    public boolean getAutoReconnectForPools() {
        return this.getActiveMySQLConnection().getAutoReconnectForPools();
    }
    
    public boolean getAutoSlowLog() {
        return this.getActiveMySQLConnection().getAutoSlowLog();
    }
    
    public int getBlobSendChunkSize() {
        return this.getActiveMySQLConnection().getBlobSendChunkSize();
    }
    
    public boolean getBlobsAreStrings() {
        return this.getActiveMySQLConnection().getBlobsAreStrings();
    }
    
    public boolean getCacheCallableStatements() {
        return this.getActiveMySQLConnection().getCacheCallableStatements();
    }
    
    public boolean getCacheCallableStmts() {
        return this.getActiveMySQLConnection().getCacheCallableStmts();
    }
    
    public boolean getCachePrepStmts() {
        return this.getActiveMySQLConnection().getCachePrepStmts();
    }
    
    public boolean getCachePreparedStatements() {
        return this.getActiveMySQLConnection().getCachePreparedStatements();
    }
    
    public boolean getCacheResultSetMetadata() {
        return this.getActiveMySQLConnection().getCacheResultSetMetadata();
    }
    
    public boolean getCacheServerConfiguration() {
        return this.getActiveMySQLConnection().getCacheServerConfiguration();
    }
    
    public int getCallableStatementCacheSize() {
        return this.getActiveMySQLConnection().getCallableStatementCacheSize();
    }
    
    public int getCallableStmtCacheSize() {
        return this.getActiveMySQLConnection().getCallableStmtCacheSize();
    }
    
    public boolean getCapitalizeTypeNames() {
        return this.getActiveMySQLConnection().getCapitalizeTypeNames();
    }
    
    public String getCharacterSetResults() {
        return this.getActiveMySQLConnection().getCharacterSetResults();
    }
    
    public String getClientCertificateKeyStorePassword() {
        return this.getActiveMySQLConnection().getClientCertificateKeyStorePassword();
    }
    
    public String getClientCertificateKeyStoreType() {
        return this.getActiveMySQLConnection().getClientCertificateKeyStoreType();
    }
    
    public String getClientCertificateKeyStoreUrl() {
        return this.getActiveMySQLConnection().getClientCertificateKeyStoreUrl();
    }
    
    public String getClientInfoProvider() {
        return this.getActiveMySQLConnection().getClientInfoProvider();
    }
    
    public String getClobCharacterEncoding() {
        return this.getActiveMySQLConnection().getClobCharacterEncoding();
    }
    
    public boolean getClobberStreamingResults() {
        return this.getActiveMySQLConnection().getClobberStreamingResults();
    }
    
    public boolean getCompensateOnDuplicateKeyUpdateCounts() {
        return this.getActiveMySQLConnection().getCompensateOnDuplicateKeyUpdateCounts();
    }
    
    public int getConnectTimeout() {
        return this.getActiveMySQLConnection().getConnectTimeout();
    }
    
    public String getConnectionCollation() {
        return this.getActiveMySQLConnection().getConnectionCollation();
    }
    
    public String getConnectionLifecycleInterceptors() {
        return this.getActiveMySQLConnection().getConnectionLifecycleInterceptors();
    }
    
    public boolean getContinueBatchOnError() {
        return this.getActiveMySQLConnection().getContinueBatchOnError();
    }
    
    public boolean getCreateDatabaseIfNotExist() {
        return this.getActiveMySQLConnection().getCreateDatabaseIfNotExist();
    }
    
    public int getDefaultFetchSize() {
        return this.getActiveMySQLConnection().getDefaultFetchSize();
    }
    
    public boolean getDontTrackOpenResources() {
        return this.getActiveMySQLConnection().getDontTrackOpenResources();
    }
    
    public boolean getDumpMetadataOnColumnNotFound() {
        return this.getActiveMySQLConnection().getDumpMetadataOnColumnNotFound();
    }
    
    public boolean getDumpQueriesOnException() {
        return this.getActiveMySQLConnection().getDumpQueriesOnException();
    }
    
    public boolean getDynamicCalendars() {
        return this.getActiveMySQLConnection().getDynamicCalendars();
    }
    
    public boolean getElideSetAutoCommits() {
        return this.getActiveMySQLConnection().getElideSetAutoCommits();
    }
    
    public boolean getEmptyStringsConvertToZero() {
        return this.getActiveMySQLConnection().getEmptyStringsConvertToZero();
    }
    
    public boolean getEmulateLocators() {
        return this.getActiveMySQLConnection().getEmulateLocators();
    }
    
    public boolean getEmulateUnsupportedPstmts() {
        return this.getActiveMySQLConnection().getEmulateUnsupportedPstmts();
    }
    
    public boolean getEnablePacketDebug() {
        return this.getActiveMySQLConnection().getEnablePacketDebug();
    }
    
    public boolean getEnableQueryTimeouts() {
        return this.getActiveMySQLConnection().getEnableQueryTimeouts();
    }
    
    public String getEncoding() {
        return this.getActiveMySQLConnection().getEncoding();
    }
    
    public String getExceptionInterceptors() {
        return this.getActiveMySQLConnection().getExceptionInterceptors();
    }
    
    public boolean getExplainSlowQueries() {
        return this.getActiveMySQLConnection().getExplainSlowQueries();
    }
    
    public boolean getFailOverReadOnly() {
        return this.getActiveMySQLConnection().getFailOverReadOnly();
    }
    
    public boolean getFunctionsNeverReturnBlobs() {
        return this.getActiveMySQLConnection().getFunctionsNeverReturnBlobs();
    }
    
    public boolean getGatherPerfMetrics() {
        return this.getActiveMySQLConnection().getGatherPerfMetrics();
    }
    
    public boolean getGatherPerformanceMetrics() {
        return this.getActiveMySQLConnection().getGatherPerformanceMetrics();
    }
    
    public boolean getGenerateSimpleParameterMetadata() {
        return this.getActiveMySQLConnection().getGenerateSimpleParameterMetadata();
    }
    
    public boolean getIgnoreNonTxTables() {
        return this.getActiveMySQLConnection().getIgnoreNonTxTables();
    }
    
    public boolean getIncludeInnodbStatusInDeadlockExceptions() {
        return this.getActiveMySQLConnection().getIncludeInnodbStatusInDeadlockExceptions();
    }
    
    public int getInitialTimeout() {
        return this.getActiveMySQLConnection().getInitialTimeout();
    }
    
    public boolean getInteractiveClient() {
        return this.getActiveMySQLConnection().getInteractiveClient();
    }
    
    public boolean getIsInteractiveClient() {
        return this.getActiveMySQLConnection().getIsInteractiveClient();
    }
    
    public boolean getJdbcCompliantTruncation() {
        return this.getActiveMySQLConnection().getJdbcCompliantTruncation();
    }
    
    public boolean getJdbcCompliantTruncationForReads() {
        return this.getActiveMySQLConnection().getJdbcCompliantTruncationForReads();
    }
    
    public String getLargeRowSizeThreshold() {
        return this.getActiveMySQLConnection().getLargeRowSizeThreshold();
    }
    
    public int getLoadBalanceBlacklistTimeout() {
        return this.getActiveMySQLConnection().getLoadBalanceBlacklistTimeout();
    }
    
    public int getLoadBalancePingTimeout() {
        return this.getActiveMySQLConnection().getLoadBalancePingTimeout();
    }
    
    public String getLoadBalanceStrategy() {
        return this.getActiveMySQLConnection().getLoadBalanceStrategy();
    }
    
    public boolean getLoadBalanceValidateConnectionOnSwapServer() {
        return this.getActiveMySQLConnection().getLoadBalanceValidateConnectionOnSwapServer();
    }
    
    public String getLocalSocketAddress() {
        return this.getActiveMySQLConnection().getLocalSocketAddress();
    }
    
    public int getLocatorFetchBufferSize() {
        return this.getActiveMySQLConnection().getLocatorFetchBufferSize();
    }
    
    public boolean getLogSlowQueries() {
        return this.getActiveMySQLConnection().getLogSlowQueries();
    }
    
    public boolean getLogXaCommands() {
        return this.getActiveMySQLConnection().getLogXaCommands();
    }
    
    public String getLogger() {
        return this.getActiveMySQLConnection().getLogger();
    }
    
    public String getLoggerClassName() {
        return this.getActiveMySQLConnection().getLoggerClassName();
    }
    
    public boolean getMaintainTimeStats() {
        return this.getActiveMySQLConnection().getMaintainTimeStats();
    }
    
    public int getMaxAllowedPacket() {
        return this.getActiveMySQLConnection().getMaxAllowedPacket();
    }
    
    public int getMaxQuerySizeToLog() {
        return this.getActiveMySQLConnection().getMaxQuerySizeToLog();
    }
    
    public int getMaxReconnects() {
        return this.getActiveMySQLConnection().getMaxReconnects();
    }
    
    public int getMaxRows() {
        return this.getActiveMySQLConnection().getMaxRows();
    }
    
    public int getMetadataCacheSize() {
        return this.getActiveMySQLConnection().getMetadataCacheSize();
    }
    
    public int getNetTimeoutForStreamingResults() {
        return this.getActiveMySQLConnection().getNetTimeoutForStreamingResults();
    }
    
    public boolean getNoAccessToProcedureBodies() {
        return this.getActiveMySQLConnection().getNoAccessToProcedureBodies();
    }
    
    public boolean getNoDatetimeStringSync() {
        return this.getActiveMySQLConnection().getNoDatetimeStringSync();
    }
    
    public boolean getNoTimezoneConversionForTimeType() {
        return this.getActiveMySQLConnection().getNoTimezoneConversionForTimeType();
    }
    
    public boolean getNullCatalogMeansCurrent() {
        return this.getActiveMySQLConnection().getNullCatalogMeansCurrent();
    }
    
    public boolean getNullNamePatternMatchesAll() {
        return this.getActiveMySQLConnection().getNullNamePatternMatchesAll();
    }
    
    public boolean getOverrideSupportsIntegrityEnhancementFacility() {
        return this.getActiveMySQLConnection().getOverrideSupportsIntegrityEnhancementFacility();
    }
    
    public int getPacketDebugBufferSize() {
        return this.getActiveMySQLConnection().getPacketDebugBufferSize();
    }
    
    public boolean getPadCharsWithSpace() {
        return this.getActiveMySQLConnection().getPadCharsWithSpace();
    }
    
    public boolean getParanoid() {
        return this.getActiveMySQLConnection().getParanoid();
    }
    
    public String getPasswordCharacterEncoding() {
        return this.getActiveMySQLConnection().getPasswordCharacterEncoding();
    }
    
    public boolean getPedantic() {
        return this.getActiveMySQLConnection().getPedantic();
    }
    
    public boolean getPinGlobalTxToPhysicalConnection() {
        return this.getActiveMySQLConnection().getPinGlobalTxToPhysicalConnection();
    }
    
    public boolean getPopulateInsertRowWithDefaultValues() {
        return this.getActiveMySQLConnection().getPopulateInsertRowWithDefaultValues();
    }
    
    public int getPrepStmtCacheSize() {
        return this.getActiveMySQLConnection().getPrepStmtCacheSize();
    }
    
    public int getPrepStmtCacheSqlLimit() {
        return this.getActiveMySQLConnection().getPrepStmtCacheSqlLimit();
    }
    
    public int getPreparedStatementCacheSize() {
        return this.getActiveMySQLConnection().getPreparedStatementCacheSize();
    }
    
    public int getPreparedStatementCacheSqlLimit() {
        return this.getActiveMySQLConnection().getPreparedStatementCacheSqlLimit();
    }
    
    public boolean getProcessEscapeCodesForPrepStmts() {
        return this.getActiveMySQLConnection().getProcessEscapeCodesForPrepStmts();
    }
    
    public boolean getProfileSQL() {
        return this.getActiveMySQLConnection().getProfileSQL();
    }
    
    public boolean getProfileSql() {
        return this.getActiveMySQLConnection().getProfileSql();
    }
    
    public String getProfilerEventHandler() {
        return this.getActiveMySQLConnection().getProfilerEventHandler();
    }
    
    public String getPropertiesTransform() {
        return this.getActiveMySQLConnection().getPropertiesTransform();
    }
    
    public int getQueriesBeforeRetryMaster() {
        return this.getActiveMySQLConnection().getQueriesBeforeRetryMaster();
    }
    
    public boolean getQueryTimeoutKillsConnection() {
        return this.getActiveMySQLConnection().getQueryTimeoutKillsConnection();
    }
    
    public boolean getReconnectAtTxEnd() {
        return this.getActiveMySQLConnection().getReconnectAtTxEnd();
    }
    
    public boolean getRelaxAutoCommit() {
        return this.getActiveMySQLConnection().getRelaxAutoCommit();
    }
    
    public int getReportMetricsIntervalMillis() {
        return this.getActiveMySQLConnection().getReportMetricsIntervalMillis();
    }
    
    public boolean getRequireSSL() {
        return this.getActiveMySQLConnection().getRequireSSL();
    }
    
    public String getResourceId() {
        return this.getActiveMySQLConnection().getResourceId();
    }
    
    public int getResultSetSizeThreshold() {
        return this.getActiveMySQLConnection().getResultSetSizeThreshold();
    }
    
    public boolean getRetainStatementAfterResultSetClose() {
        return this.getActiveMySQLConnection().getRetainStatementAfterResultSetClose();
    }
    
    public int getRetriesAllDown() {
        return this.getActiveMySQLConnection().getRetriesAllDown();
    }
    
    public boolean getRewriteBatchedStatements() {
        return this.getActiveMySQLConnection().getRewriteBatchedStatements();
    }
    
    public boolean getRollbackOnPooledClose() {
        return this.getActiveMySQLConnection().getRollbackOnPooledClose();
    }
    
    public boolean getRoundRobinLoadBalance() {
        return this.getActiveMySQLConnection().getRoundRobinLoadBalance();
    }
    
    public boolean getRunningCTS13() {
        return this.getActiveMySQLConnection().getRunningCTS13();
    }
    
    public int getSecondsBeforeRetryMaster() {
        return this.getActiveMySQLConnection().getSecondsBeforeRetryMaster();
    }
    
    public int getSelfDestructOnPingMaxOperations() {
        return this.getActiveMySQLConnection().getSelfDestructOnPingMaxOperations();
    }
    
    public int getSelfDestructOnPingSecondsLifetime() {
        return this.getActiveMySQLConnection().getSelfDestructOnPingSecondsLifetime();
    }
    
    public String getServerTimezone() {
        return this.getActiveMySQLConnection().getServerTimezone();
    }
    
    public String getSessionVariables() {
        return this.getActiveMySQLConnection().getSessionVariables();
    }
    
    public int getSlowQueryThresholdMillis() {
        return this.getActiveMySQLConnection().getSlowQueryThresholdMillis();
    }
    
    public long getSlowQueryThresholdNanos() {
        return this.getActiveMySQLConnection().getSlowQueryThresholdNanos();
    }
    
    public String getSocketFactory() {
        return this.getActiveMySQLConnection().getSocketFactory();
    }
    
    public String getSocketFactoryClassName() {
        return this.getActiveMySQLConnection().getSocketFactoryClassName();
    }
    
    public int getSocketTimeout() {
        return this.getActiveMySQLConnection().getSocketTimeout();
    }
    
    public String getStatementInterceptors() {
        return this.getActiveMySQLConnection().getStatementInterceptors();
    }
    
    public boolean getStrictFloatingPoint() {
        return this.getActiveMySQLConnection().getStrictFloatingPoint();
    }
    
    public boolean getStrictUpdates() {
        return this.getActiveMySQLConnection().getStrictUpdates();
    }
    
    public boolean getTcpKeepAlive() {
        return this.getActiveMySQLConnection().getTcpKeepAlive();
    }
    
    public boolean getTcpNoDelay() {
        return this.getActiveMySQLConnection().getTcpNoDelay();
    }
    
    public int getTcpRcvBuf() {
        return this.getActiveMySQLConnection().getTcpRcvBuf();
    }
    
    public int getTcpSndBuf() {
        return this.getActiveMySQLConnection().getTcpSndBuf();
    }
    
    public int getTcpTrafficClass() {
        return this.getActiveMySQLConnection().getTcpTrafficClass();
    }
    
    public boolean getTinyInt1isBit() {
        return this.getActiveMySQLConnection().getTinyInt1isBit();
    }
    
    public boolean getTraceProtocol() {
        return this.getActiveMySQLConnection().getTraceProtocol();
    }
    
    public boolean getTransformedBitIsBoolean() {
        return this.getActiveMySQLConnection().getTransformedBitIsBoolean();
    }
    
    public boolean getTreatUtilDateAsTimestamp() {
        return this.getActiveMySQLConnection().getTreatUtilDateAsTimestamp();
    }
    
    public String getTrustCertificateKeyStorePassword() {
        return this.getActiveMySQLConnection().getTrustCertificateKeyStorePassword();
    }
    
    public String getTrustCertificateKeyStoreType() {
        return this.getActiveMySQLConnection().getTrustCertificateKeyStoreType();
    }
    
    public String getTrustCertificateKeyStoreUrl() {
        return this.getActiveMySQLConnection().getTrustCertificateKeyStoreUrl();
    }
    
    public boolean getUltraDevHack() {
        return this.getActiveMySQLConnection().getUltraDevHack();
    }
    
    public boolean getUseAffectedRows() {
        return this.getActiveMySQLConnection().getUseAffectedRows();
    }
    
    public boolean getUseBlobToStoreUTF8OutsideBMP() {
        return this.getActiveMySQLConnection().getUseBlobToStoreUTF8OutsideBMP();
    }
    
    public boolean getUseColumnNamesInFindColumn() {
        return this.getActiveMySQLConnection().getUseColumnNamesInFindColumn();
    }
    
    public boolean getUseCompression() {
        return this.getActiveMySQLConnection().getUseCompression();
    }
    
    public String getUseConfigs() {
        return this.getActiveMySQLConnection().getUseConfigs();
    }
    
    public boolean getUseCursorFetch() {
        return this.getActiveMySQLConnection().getUseCursorFetch();
    }
    
    public boolean getUseDirectRowUnpack() {
        return this.getActiveMySQLConnection().getUseDirectRowUnpack();
    }
    
    public boolean getUseDynamicCharsetInfo() {
        return this.getActiveMySQLConnection().getUseDynamicCharsetInfo();
    }
    
    public boolean getUseFastDateParsing() {
        return this.getActiveMySQLConnection().getUseFastDateParsing();
    }
    
    public boolean getUseFastIntParsing() {
        return this.getActiveMySQLConnection().getUseFastIntParsing();
    }
    
    public boolean getUseGmtMillisForDatetimes() {
        return this.getActiveMySQLConnection().getUseGmtMillisForDatetimes();
    }
    
    public boolean getUseHostsInPrivileges() {
        return this.getActiveMySQLConnection().getUseHostsInPrivileges();
    }
    
    public boolean getUseInformationSchema() {
        return this.getActiveMySQLConnection().getUseInformationSchema();
    }
    
    public boolean getUseJDBCCompliantTimezoneShift() {
        return this.getActiveMySQLConnection().getUseJDBCCompliantTimezoneShift();
    }
    
    public boolean getUseJvmCharsetConverters() {
        return this.getActiveMySQLConnection().getUseJvmCharsetConverters();
    }
    
    public boolean getUseLegacyDatetimeCode() {
        return this.getActiveMySQLConnection().getUseLegacyDatetimeCode();
    }
    
    public boolean getUseLocalSessionState() {
        return this.getActiveMySQLConnection().getUseLocalSessionState();
    }
    
    public boolean getUseLocalTransactionState() {
        return this.getActiveMySQLConnection().getUseLocalTransactionState();
    }
    
    public boolean getUseNanosForElapsedTime() {
        return this.getActiveMySQLConnection().getUseNanosForElapsedTime();
    }
    
    public boolean getUseOldAliasMetadataBehavior() {
        return this.getActiveMySQLConnection().getUseOldAliasMetadataBehavior();
    }
    
    public boolean getUseOldUTF8Behavior() {
        return this.getActiveMySQLConnection().getUseOldUTF8Behavior();
    }
    
    public boolean getUseOnlyServerErrorMessages() {
        return this.getActiveMySQLConnection().getUseOnlyServerErrorMessages();
    }
    
    public boolean getUseReadAheadInput() {
        return this.getActiveMySQLConnection().getUseReadAheadInput();
    }
    
    public boolean getUseSSL() {
        return this.getActiveMySQLConnection().getUseSSL();
    }
    
    public boolean getUseSSPSCompatibleTimezoneShift() {
        return this.getActiveMySQLConnection().getUseSSPSCompatibleTimezoneShift();
    }
    
    public boolean getUseServerPrepStmts() {
        return this.getActiveMySQLConnection().getUseServerPrepStmts();
    }
    
    public boolean getUseServerPreparedStmts() {
        return this.getActiveMySQLConnection().getUseServerPreparedStmts();
    }
    
    public boolean getUseSqlStateCodes() {
        return this.getActiveMySQLConnection().getUseSqlStateCodes();
    }
    
    public boolean getUseStreamLengthsInPrepStmts() {
        return this.getActiveMySQLConnection().getUseStreamLengthsInPrepStmts();
    }
    
    public boolean getUseTimezone() {
        return this.getActiveMySQLConnection().getUseTimezone();
    }
    
    public boolean getUseUltraDevWorkAround() {
        return this.getActiveMySQLConnection().getUseUltraDevWorkAround();
    }
    
    public boolean getUseUnbufferedInput() {
        return this.getActiveMySQLConnection().getUseUnbufferedInput();
    }
    
    public boolean getUseUnicode() {
        return this.getActiveMySQLConnection().getUseUnicode();
    }
    
    public boolean getUseUsageAdvisor() {
        return this.getActiveMySQLConnection().getUseUsageAdvisor();
    }
    
    public String getUtf8OutsideBmpExcludedColumnNamePattern() {
        return this.getActiveMySQLConnection().getUtf8OutsideBmpExcludedColumnNamePattern();
    }
    
    public String getUtf8OutsideBmpIncludedColumnNamePattern() {
        return this.getActiveMySQLConnection().getUtf8OutsideBmpIncludedColumnNamePattern();
    }
    
    public boolean getVerifyServerCertificate() {
        return this.getActiveMySQLConnection().getVerifyServerCertificate();
    }
    
    public boolean getYearIsDateType() {
        return this.getActiveMySQLConnection().getYearIsDateType();
    }
    
    public String getZeroDateTimeBehavior() {
        return this.getActiveMySQLConnection().getZeroDateTimeBehavior();
    }
    
    public void setAllowLoadLocalInfile(final boolean property) {
        this.getActiveMySQLConnection().setAllowLoadLocalInfile(property);
    }
    
    public void setAllowMultiQueries(final boolean property) {
        this.getActiveMySQLConnection().setAllowMultiQueries(property);
    }
    
    public void setAllowNanAndInf(final boolean flag) {
        this.getActiveMySQLConnection().setAllowNanAndInf(flag);
    }
    
    public void setAllowUrlInLocalInfile(final boolean flag) {
        this.getActiveMySQLConnection().setAllowUrlInLocalInfile(flag);
    }
    
    public void setAlwaysSendSetIsolation(final boolean flag) {
        this.getActiveMySQLConnection().setAlwaysSendSetIsolation(flag);
    }
    
    public void setAutoClosePStmtStreams(final boolean flag) {
        this.getActiveMySQLConnection().setAutoClosePStmtStreams(flag);
    }
    
    public void setAutoDeserialize(final boolean flag) {
        this.getActiveMySQLConnection().setAutoDeserialize(flag);
    }
    
    public void setAutoGenerateTestcaseScript(final boolean flag) {
        this.getActiveMySQLConnection().setAutoGenerateTestcaseScript(flag);
    }
    
    public void setAutoReconnect(final boolean flag) {
        this.getActiveMySQLConnection().setAutoReconnect(flag);
    }
    
    public void setAutoReconnectForConnectionPools(final boolean property) {
        this.getActiveMySQLConnection().setAutoReconnectForConnectionPools(property);
    }
    
    public void setAutoReconnectForPools(final boolean flag) {
        this.getActiveMySQLConnection().setAutoReconnectForPools(flag);
    }
    
    public void setAutoSlowLog(final boolean flag) {
        this.getActiveMySQLConnection().setAutoSlowLog(flag);
    }
    
    public void setBlobSendChunkSize(final String value) throws SQLException {
        this.getActiveMySQLConnection().setBlobSendChunkSize(value);
    }
    
    public void setBlobsAreStrings(final boolean flag) {
        this.getActiveMySQLConnection().setBlobsAreStrings(flag);
    }
    
    public void setCacheCallableStatements(final boolean flag) {
        this.getActiveMySQLConnection().setCacheCallableStatements(flag);
    }
    
    public void setCacheCallableStmts(final boolean flag) {
        this.getActiveMySQLConnection().setCacheCallableStmts(flag);
    }
    
    public void setCachePrepStmts(final boolean flag) {
        this.getActiveMySQLConnection().setCachePrepStmts(flag);
    }
    
    public void setCachePreparedStatements(final boolean flag) {
        this.getActiveMySQLConnection().setCachePreparedStatements(flag);
    }
    
    public void setCacheResultSetMetadata(final boolean property) {
        this.getActiveMySQLConnection().setCacheResultSetMetadata(property);
    }
    
    public void setCacheServerConfiguration(final boolean flag) {
        this.getActiveMySQLConnection().setCacheServerConfiguration(flag);
    }
    
    public void setCallableStatementCacheSize(final int size) {
        this.getActiveMySQLConnection().setCallableStatementCacheSize(size);
    }
    
    public void setCallableStmtCacheSize(final int cacheSize) {
        this.getActiveMySQLConnection().setCallableStmtCacheSize(cacheSize);
    }
    
    public void setCapitalizeDBMDTypes(final boolean property) {
        this.getActiveMySQLConnection().setCapitalizeDBMDTypes(property);
    }
    
    public void setCapitalizeTypeNames(final boolean flag) {
        this.getActiveMySQLConnection().setCapitalizeTypeNames(flag);
    }
    
    public void setCharacterEncoding(final String encoding) {
        this.getActiveMySQLConnection().setCharacterEncoding(encoding);
    }
    
    public void setCharacterSetResults(final String characterSet) {
        this.getActiveMySQLConnection().setCharacterSetResults(characterSet);
    }
    
    public void setClientCertificateKeyStorePassword(final String value) {
        this.getActiveMySQLConnection().setClientCertificateKeyStorePassword(value);
    }
    
    public void setClientCertificateKeyStoreType(final String value) {
        this.getActiveMySQLConnection().setClientCertificateKeyStoreType(value);
    }
    
    public void setClientCertificateKeyStoreUrl(final String value) {
        this.getActiveMySQLConnection().setClientCertificateKeyStoreUrl(value);
    }
    
    public void setClientInfoProvider(final String classname) {
        this.getActiveMySQLConnection().setClientInfoProvider(classname);
    }
    
    public void setClobCharacterEncoding(final String encoding) {
        this.getActiveMySQLConnection().setClobCharacterEncoding(encoding);
    }
    
    public void setClobberStreamingResults(final boolean flag) {
        this.getActiveMySQLConnection().setClobberStreamingResults(flag);
    }
    
    public void setCompensateOnDuplicateKeyUpdateCounts(final boolean flag) {
        this.getActiveMySQLConnection().setCompensateOnDuplicateKeyUpdateCounts(flag);
    }
    
    public void setConnectTimeout(final int timeoutMs) {
        this.getActiveMySQLConnection().setConnectTimeout(timeoutMs);
    }
    
    public void setConnectionCollation(final String collation) {
        this.getActiveMySQLConnection().setConnectionCollation(collation);
    }
    
    public void setConnectionLifecycleInterceptors(final String interceptors) {
        this.getActiveMySQLConnection().setConnectionLifecycleInterceptors(interceptors);
    }
    
    public void setContinueBatchOnError(final boolean property) {
        this.getActiveMySQLConnection().setContinueBatchOnError(property);
    }
    
    public void setCreateDatabaseIfNotExist(final boolean flag) {
        this.getActiveMySQLConnection().setCreateDatabaseIfNotExist(flag);
    }
    
    public void setDefaultFetchSize(final int n) {
        this.getActiveMySQLConnection().setDefaultFetchSize(n);
    }
    
    public void setDetectServerPreparedStmts(final boolean property) {
        this.getActiveMySQLConnection().setDetectServerPreparedStmts(property);
    }
    
    public void setDontTrackOpenResources(final boolean flag) {
        this.getActiveMySQLConnection().setDontTrackOpenResources(flag);
    }
    
    public void setDumpMetadataOnColumnNotFound(final boolean flag) {
        this.getActiveMySQLConnection().setDumpMetadataOnColumnNotFound(flag);
    }
    
    public void setDumpQueriesOnException(final boolean flag) {
        this.getActiveMySQLConnection().setDumpQueriesOnException(flag);
    }
    
    public void setDynamicCalendars(final boolean flag) {
        this.getActiveMySQLConnection().setDynamicCalendars(flag);
    }
    
    public void setElideSetAutoCommits(final boolean flag) {
        this.getActiveMySQLConnection().setElideSetAutoCommits(flag);
    }
    
    public void setEmptyStringsConvertToZero(final boolean flag) {
        this.getActiveMySQLConnection().setEmptyStringsConvertToZero(flag);
    }
    
    public void setEmulateLocators(final boolean property) {
        this.getActiveMySQLConnection().setEmulateLocators(property);
    }
    
    public void setEmulateUnsupportedPstmts(final boolean flag) {
        this.getActiveMySQLConnection().setEmulateUnsupportedPstmts(flag);
    }
    
    public void setEnablePacketDebug(final boolean flag) {
        this.getActiveMySQLConnection().setEnablePacketDebug(flag);
    }
    
    public void setEnableQueryTimeouts(final boolean flag) {
        this.getActiveMySQLConnection().setEnableQueryTimeouts(flag);
    }
    
    public void setEncoding(final String property) {
        this.getActiveMySQLConnection().setEncoding(property);
    }
    
    public void setExceptionInterceptors(final String exceptionInterceptors) {
        this.getActiveMySQLConnection().setExceptionInterceptors(exceptionInterceptors);
    }
    
    public void setExplainSlowQueries(final boolean flag) {
        this.getActiveMySQLConnection().setExplainSlowQueries(flag);
    }
    
    public void setFailOverReadOnly(final boolean flag) {
        this.getActiveMySQLConnection().setFailOverReadOnly(flag);
    }
    
    public void setFunctionsNeverReturnBlobs(final boolean flag) {
        this.getActiveMySQLConnection().setFunctionsNeverReturnBlobs(flag);
    }
    
    public void setGatherPerfMetrics(final boolean flag) {
        this.getActiveMySQLConnection().setGatherPerfMetrics(flag);
    }
    
    public void setGatherPerformanceMetrics(final boolean flag) {
        this.getActiveMySQLConnection().setGatherPerformanceMetrics(flag);
    }
    
    public void setGenerateSimpleParameterMetadata(final boolean flag) {
        this.getActiveMySQLConnection().setGenerateSimpleParameterMetadata(flag);
    }
    
    public void setHoldResultsOpenOverStatementClose(final boolean flag) {
        this.getActiveMySQLConnection().setHoldResultsOpenOverStatementClose(flag);
    }
    
    public void setIgnoreNonTxTables(final boolean property) {
        this.getActiveMySQLConnection().setIgnoreNonTxTables(property);
    }
    
    public void setIncludeInnodbStatusInDeadlockExceptions(final boolean flag) {
        this.getActiveMySQLConnection().setIncludeInnodbStatusInDeadlockExceptions(flag);
    }
    
    public void setInitialTimeout(final int property) {
        this.getActiveMySQLConnection().setInitialTimeout(property);
    }
    
    public void setInteractiveClient(final boolean property) {
        this.getActiveMySQLConnection().setInteractiveClient(property);
    }
    
    public void setIsInteractiveClient(final boolean property) {
        this.getActiveMySQLConnection().setIsInteractiveClient(property);
    }
    
    public void setJdbcCompliantTruncation(final boolean flag) {
        this.getActiveMySQLConnection().setJdbcCompliantTruncation(flag);
    }
    
    public void setJdbcCompliantTruncationForReads(final boolean jdbcCompliantTruncationForReads) {
        this.getActiveMySQLConnection().setJdbcCompliantTruncationForReads(jdbcCompliantTruncationForReads);
    }
    
    public void setLargeRowSizeThreshold(final String value) {
        this.getActiveMySQLConnection().setLargeRowSizeThreshold(value);
    }
    
    public void setLoadBalanceBlacklistTimeout(final int loadBalanceBlacklistTimeout) {
        this.getActiveMySQLConnection().setLoadBalanceBlacklistTimeout(loadBalanceBlacklistTimeout);
    }
    
    public void setLoadBalancePingTimeout(final int loadBalancePingTimeout) {
        this.getActiveMySQLConnection().setLoadBalancePingTimeout(loadBalancePingTimeout);
    }
    
    public void setLoadBalanceStrategy(final String strategy) {
        this.getActiveMySQLConnection().setLoadBalanceStrategy(strategy);
    }
    
    public void setLoadBalanceValidateConnectionOnSwapServer(final boolean loadBalanceValidateConnectionOnSwapServer) {
        this.getActiveMySQLConnection().setLoadBalanceValidateConnectionOnSwapServer(loadBalanceValidateConnectionOnSwapServer);
    }
    
    public void setLocalSocketAddress(final String address) {
        this.getActiveMySQLConnection().setLocalSocketAddress(address);
    }
    
    public void setLocatorFetchBufferSize(final String value) throws SQLException {
        this.getActiveMySQLConnection().setLocatorFetchBufferSize(value);
    }
    
    public void setLogSlowQueries(final boolean flag) {
        this.getActiveMySQLConnection().setLogSlowQueries(flag);
    }
    
    public void setLogXaCommands(final boolean flag) {
        this.getActiveMySQLConnection().setLogXaCommands(flag);
    }
    
    public void setLogger(final String property) {
        this.getActiveMySQLConnection().setLogger(property);
    }
    
    public void setLoggerClassName(final String className) {
        this.getActiveMySQLConnection().setLoggerClassName(className);
    }
    
    public void setMaintainTimeStats(final boolean flag) {
        this.getActiveMySQLConnection().setMaintainTimeStats(flag);
    }
    
    public void setMaxQuerySizeToLog(final int sizeInBytes) {
        this.getActiveMySQLConnection().setMaxQuerySizeToLog(sizeInBytes);
    }
    
    public void setMaxReconnects(final int property) {
        this.getActiveMySQLConnection().setMaxReconnects(property);
    }
    
    public void setMaxRows(final int property) {
        this.getActiveMySQLConnection().setMaxRows(property);
    }
    
    public void setMetadataCacheSize(final int value) {
        this.getActiveMySQLConnection().setMetadataCacheSize(value);
    }
    
    public void setNetTimeoutForStreamingResults(final int value) {
        this.getActiveMySQLConnection().setNetTimeoutForStreamingResults(value);
    }
    
    public void setNoAccessToProcedureBodies(final boolean flag) {
        this.getActiveMySQLConnection().setNoAccessToProcedureBodies(flag);
    }
    
    public void setNoDatetimeStringSync(final boolean flag) {
        this.getActiveMySQLConnection().setNoDatetimeStringSync(flag);
    }
    
    public void setNoTimezoneConversionForTimeType(final boolean flag) {
        this.getActiveMySQLConnection().setNoTimezoneConversionForTimeType(flag);
    }
    
    public void setNullCatalogMeansCurrent(final boolean value) {
        this.getActiveMySQLConnection().setNullCatalogMeansCurrent(value);
    }
    
    public void setNullNamePatternMatchesAll(final boolean value) {
        this.getActiveMySQLConnection().setNullNamePatternMatchesAll(value);
    }
    
    public void setOverrideSupportsIntegrityEnhancementFacility(final boolean flag) {
        this.getActiveMySQLConnection().setOverrideSupportsIntegrityEnhancementFacility(flag);
    }
    
    public void setPacketDebugBufferSize(final int size) {
        this.getActiveMySQLConnection().setPacketDebugBufferSize(size);
    }
    
    public void setPadCharsWithSpace(final boolean flag) {
        this.getActiveMySQLConnection().setPadCharsWithSpace(flag);
    }
    
    public void setParanoid(final boolean property) {
        this.getActiveMySQLConnection().setParanoid(property);
    }
    
    public void setPasswordCharacterEncoding(final String characterSet) {
        this.getActiveMySQLConnection().setPasswordCharacterEncoding(characterSet);
    }
    
    public void setPedantic(final boolean property) {
        this.getActiveMySQLConnection().setPedantic(property);
    }
    
    public void setPinGlobalTxToPhysicalConnection(final boolean flag) {
        this.getActiveMySQLConnection().setPinGlobalTxToPhysicalConnection(flag);
    }
    
    public void setPopulateInsertRowWithDefaultValues(final boolean flag) {
        this.getActiveMySQLConnection().setPopulateInsertRowWithDefaultValues(flag);
    }
    
    public void setPrepStmtCacheSize(final int cacheSize) {
        this.getActiveMySQLConnection().setPrepStmtCacheSize(cacheSize);
    }
    
    public void setPrepStmtCacheSqlLimit(final int sqlLimit) {
        this.getActiveMySQLConnection().setPrepStmtCacheSqlLimit(sqlLimit);
    }
    
    public void setPreparedStatementCacheSize(final int cacheSize) {
        this.getActiveMySQLConnection().setPreparedStatementCacheSize(cacheSize);
    }
    
    public void setPreparedStatementCacheSqlLimit(final int cacheSqlLimit) {
        this.getActiveMySQLConnection().setPreparedStatementCacheSqlLimit(cacheSqlLimit);
    }
    
    public void setProcessEscapeCodesForPrepStmts(final boolean flag) {
        this.getActiveMySQLConnection().setProcessEscapeCodesForPrepStmts(flag);
    }
    
    public void setProfileSQL(final boolean flag) {
        this.getActiveMySQLConnection().setProfileSQL(flag);
    }
    
    public void setProfileSql(final boolean property) {
        this.getActiveMySQLConnection().setProfileSql(property);
    }
    
    public void setProfilerEventHandler(final String handler) {
        this.getActiveMySQLConnection().setProfilerEventHandler(handler);
    }
    
    public void setPropertiesTransform(final String value) {
        this.getActiveMySQLConnection().setPropertiesTransform(value);
    }
    
    public void setQueriesBeforeRetryMaster(final int property) {
        this.getActiveMySQLConnection().setQueriesBeforeRetryMaster(property);
    }
    
    public void setQueryTimeoutKillsConnection(final boolean queryTimeoutKillsConnection) {
        this.getActiveMySQLConnection().setQueryTimeoutKillsConnection(queryTimeoutKillsConnection);
    }
    
    public void setReconnectAtTxEnd(final boolean property) {
        this.getActiveMySQLConnection().setReconnectAtTxEnd(property);
    }
    
    public void setRelaxAutoCommit(final boolean property) {
        this.getActiveMySQLConnection().setRelaxAutoCommit(property);
    }
    
    public void setReportMetricsIntervalMillis(final int millis) {
        this.getActiveMySQLConnection().setReportMetricsIntervalMillis(millis);
    }
    
    public void setRequireSSL(final boolean property) {
        this.getActiveMySQLConnection().setRequireSSL(property);
    }
    
    public void setResourceId(final String resourceId) {
        this.getActiveMySQLConnection().setResourceId(resourceId);
    }
    
    public void setResultSetSizeThreshold(final int threshold) {
        this.getActiveMySQLConnection().setResultSetSizeThreshold(threshold);
    }
    
    public void setRetainStatementAfterResultSetClose(final boolean flag) {
        this.getActiveMySQLConnection().setRetainStatementAfterResultSetClose(flag);
    }
    
    public void setRetriesAllDown(final int retriesAllDown) {
        this.getActiveMySQLConnection().setRetriesAllDown(retriesAllDown);
    }
    
    public void setRewriteBatchedStatements(final boolean flag) {
        this.getActiveMySQLConnection().setRewriteBatchedStatements(flag);
    }
    
    public void setRollbackOnPooledClose(final boolean flag) {
        this.getActiveMySQLConnection().setRollbackOnPooledClose(flag);
    }
    
    public void setRoundRobinLoadBalance(final boolean flag) {
        this.getActiveMySQLConnection().setRoundRobinLoadBalance(flag);
    }
    
    public void setRunningCTS13(final boolean flag) {
        this.getActiveMySQLConnection().setRunningCTS13(flag);
    }
    
    public void setSecondsBeforeRetryMaster(final int property) {
        this.getActiveMySQLConnection().setSecondsBeforeRetryMaster(property);
    }
    
    public void setSelfDestructOnPingMaxOperations(final int maxOperations) {
        this.getActiveMySQLConnection().setSelfDestructOnPingMaxOperations(maxOperations);
    }
    
    public void setSelfDestructOnPingSecondsLifetime(final int seconds) {
        this.getActiveMySQLConnection().setSelfDestructOnPingSecondsLifetime(seconds);
    }
    
    public void setServerTimezone(final String property) {
        this.getActiveMySQLConnection().setServerTimezone(property);
    }
    
    public void setSessionVariables(final String variables) {
        this.getActiveMySQLConnection().setSessionVariables(variables);
    }
    
    public void setSlowQueryThresholdMillis(final int millis) {
        this.getActiveMySQLConnection().setSlowQueryThresholdMillis(millis);
    }
    
    public void setSlowQueryThresholdNanos(final long nanos) {
        this.getActiveMySQLConnection().setSlowQueryThresholdNanos(nanos);
    }
    
    public void setSocketFactory(final String name) {
        this.getActiveMySQLConnection().setSocketFactory(name);
    }
    
    public void setSocketFactoryClassName(final String property) {
        this.getActiveMySQLConnection().setSocketFactoryClassName(property);
    }
    
    public void setSocketTimeout(final int property) {
        this.getActiveMySQLConnection().setSocketTimeout(property);
    }
    
    public void setStatementInterceptors(final String value) {
        this.getActiveMySQLConnection().setStatementInterceptors(value);
    }
    
    public void setStrictFloatingPoint(final boolean property) {
        this.getActiveMySQLConnection().setStrictFloatingPoint(property);
    }
    
    public void setStrictUpdates(final boolean property) {
        this.getActiveMySQLConnection().setStrictUpdates(property);
    }
    
    public void setTcpKeepAlive(final boolean flag) {
        this.getActiveMySQLConnection().setTcpKeepAlive(flag);
    }
    
    public void setTcpNoDelay(final boolean flag) {
        this.getActiveMySQLConnection().setTcpNoDelay(flag);
    }
    
    public void setTcpRcvBuf(final int bufSize) {
        this.getActiveMySQLConnection().setTcpRcvBuf(bufSize);
    }
    
    public void setTcpSndBuf(final int bufSize) {
        this.getActiveMySQLConnection().setTcpSndBuf(bufSize);
    }
    
    public void setTcpTrafficClass(final int classFlags) {
        this.getActiveMySQLConnection().setTcpTrafficClass(classFlags);
    }
    
    public void setTinyInt1isBit(final boolean flag) {
        this.getActiveMySQLConnection().setTinyInt1isBit(flag);
    }
    
    public void setTraceProtocol(final boolean flag) {
        this.getActiveMySQLConnection().setTraceProtocol(flag);
    }
    
    public void setTransformedBitIsBoolean(final boolean flag) {
        this.getActiveMySQLConnection().setTransformedBitIsBoolean(flag);
    }
    
    public void setTreatUtilDateAsTimestamp(final boolean flag) {
        this.getActiveMySQLConnection().setTreatUtilDateAsTimestamp(flag);
    }
    
    public void setTrustCertificateKeyStorePassword(final String value) {
        this.getActiveMySQLConnection().setTrustCertificateKeyStorePassword(value);
    }
    
    public void setTrustCertificateKeyStoreType(final String value) {
        this.getActiveMySQLConnection().setTrustCertificateKeyStoreType(value);
    }
    
    public void setTrustCertificateKeyStoreUrl(final String value) {
        this.getActiveMySQLConnection().setTrustCertificateKeyStoreUrl(value);
    }
    
    public void setUltraDevHack(final boolean flag) {
        this.getActiveMySQLConnection().setUltraDevHack(flag);
    }
    
    public void setUseAffectedRows(final boolean flag) {
        this.getActiveMySQLConnection().setUseAffectedRows(flag);
    }
    
    public void setUseBlobToStoreUTF8OutsideBMP(final boolean flag) {
        this.getActiveMySQLConnection().setUseBlobToStoreUTF8OutsideBMP(flag);
    }
    
    public void setUseColumnNamesInFindColumn(final boolean flag) {
        this.getActiveMySQLConnection().setUseColumnNamesInFindColumn(flag);
    }
    
    public void setUseCompression(final boolean property) {
        this.getActiveMySQLConnection().setUseCompression(property);
    }
    
    public void setUseConfigs(final String configs) {
        this.getActiveMySQLConnection().setUseConfigs(configs);
    }
    
    public void setUseCursorFetch(final boolean flag) {
        this.getActiveMySQLConnection().setUseCursorFetch(flag);
    }
    
    public void setUseDirectRowUnpack(final boolean flag) {
        this.getActiveMySQLConnection().setUseDirectRowUnpack(flag);
    }
    
    public void setUseDynamicCharsetInfo(final boolean flag) {
        this.getActiveMySQLConnection().setUseDynamicCharsetInfo(flag);
    }
    
    public void setUseFastDateParsing(final boolean flag) {
        this.getActiveMySQLConnection().setUseFastDateParsing(flag);
    }
    
    public void setUseFastIntParsing(final boolean flag) {
        this.getActiveMySQLConnection().setUseFastIntParsing(flag);
    }
    
    public void setUseGmtMillisForDatetimes(final boolean flag) {
        this.getActiveMySQLConnection().setUseGmtMillisForDatetimes(flag);
    }
    
    public void setUseHostsInPrivileges(final boolean property) {
        this.getActiveMySQLConnection().setUseHostsInPrivileges(property);
    }
    
    public void setUseInformationSchema(final boolean flag) {
        this.getActiveMySQLConnection().setUseInformationSchema(flag);
    }
    
    public void setUseJDBCCompliantTimezoneShift(final boolean flag) {
        this.getActiveMySQLConnection().setUseJDBCCompliantTimezoneShift(flag);
    }
    
    public void setUseJvmCharsetConverters(final boolean flag) {
        this.getActiveMySQLConnection().setUseJvmCharsetConverters(flag);
    }
    
    public void setUseLegacyDatetimeCode(final boolean flag) {
        this.getActiveMySQLConnection().setUseLegacyDatetimeCode(flag);
    }
    
    public void setUseLocalSessionState(final boolean flag) {
        this.getActiveMySQLConnection().setUseLocalSessionState(flag);
    }
    
    public void setUseLocalTransactionState(final boolean flag) {
        this.getActiveMySQLConnection().setUseLocalTransactionState(flag);
    }
    
    public void setUseNanosForElapsedTime(final boolean flag) {
        this.getActiveMySQLConnection().setUseNanosForElapsedTime(flag);
    }
    
    public void setUseOldAliasMetadataBehavior(final boolean flag) {
        this.getActiveMySQLConnection().setUseOldAliasMetadataBehavior(flag);
    }
    
    public void setUseOldUTF8Behavior(final boolean flag) {
        this.getActiveMySQLConnection().setUseOldUTF8Behavior(flag);
    }
    
    public void setUseOnlyServerErrorMessages(final boolean flag) {
        this.getActiveMySQLConnection().setUseOnlyServerErrorMessages(flag);
    }
    
    public void setUseReadAheadInput(final boolean flag) {
        this.getActiveMySQLConnection().setUseReadAheadInput(flag);
    }
    
    public void setUseSSL(final boolean property) {
        this.getActiveMySQLConnection().setUseSSL(property);
    }
    
    public void setUseSSPSCompatibleTimezoneShift(final boolean flag) {
        this.getActiveMySQLConnection().setUseSSPSCompatibleTimezoneShift(flag);
    }
    
    public void setUseServerPrepStmts(final boolean flag) {
        this.getActiveMySQLConnection().setUseServerPrepStmts(flag);
    }
    
    public void setUseServerPreparedStmts(final boolean flag) {
        this.getActiveMySQLConnection().setUseServerPreparedStmts(flag);
    }
    
    public void setUseSqlStateCodes(final boolean flag) {
        this.getActiveMySQLConnection().setUseSqlStateCodes(flag);
    }
    
    public void setUseStreamLengthsInPrepStmts(final boolean property) {
        this.getActiveMySQLConnection().setUseStreamLengthsInPrepStmts(property);
    }
    
    public void setUseTimezone(final boolean property) {
        this.getActiveMySQLConnection().setUseTimezone(property);
    }
    
    public void setUseUltraDevWorkAround(final boolean property) {
        this.getActiveMySQLConnection().setUseUltraDevWorkAround(property);
    }
    
    public void setUseUnbufferedInput(final boolean flag) {
        this.getActiveMySQLConnection().setUseUnbufferedInput(flag);
    }
    
    public void setUseUnicode(final boolean flag) {
        this.getActiveMySQLConnection().setUseUnicode(flag);
    }
    
    public void setUseUsageAdvisor(final boolean useUsageAdvisorFlag) {
        this.getActiveMySQLConnection().setUseUsageAdvisor(useUsageAdvisorFlag);
    }
    
    public void setUtf8OutsideBmpExcludedColumnNamePattern(final String regexPattern) {
        this.getActiveMySQLConnection().setUtf8OutsideBmpExcludedColumnNamePattern(regexPattern);
    }
    
    public void setUtf8OutsideBmpIncludedColumnNamePattern(final String regexPattern) {
        this.getActiveMySQLConnection().setUtf8OutsideBmpIncludedColumnNamePattern(regexPattern);
    }
    
    public void setVerifyServerCertificate(final boolean flag) {
        this.getActiveMySQLConnection().setVerifyServerCertificate(flag);
    }
    
    public void setYearIsDateType(final boolean flag) {
        this.getActiveMySQLConnection().setYearIsDateType(flag);
    }
    
    public void setZeroDateTimeBehavior(final String behavior) {
        this.getActiveMySQLConnection().setZeroDateTimeBehavior(behavior);
    }
    
    public boolean useUnbufferedInput() {
        return this.getActiveMySQLConnection().useUnbufferedInput();
    }
    
    public StringBuffer generateConnectionCommentBlock(final StringBuffer buf) {
        return this.getActiveMySQLConnection().generateConnectionCommentBlock(buf);
    }
    
    public int getActiveStatementCount() {
        return this.getActiveMySQLConnection().getActiveStatementCount();
    }
    
    public boolean getAutoCommit() throws SQLException {
        return this.getActiveMySQLConnection().getAutoCommit();
    }
    
    public int getAutoIncrementIncrement() {
        return this.getActiveMySQLConnection().getAutoIncrementIncrement();
    }
    
    public CachedResultSetMetaData getCachedMetaData(final String sql) {
        return this.getActiveMySQLConnection().getCachedMetaData(sql);
    }
    
    public Calendar getCalendarInstanceForSessionOrNew() {
        return this.getActiveMySQLConnection().getCalendarInstanceForSessionOrNew();
    }
    
    public synchronized Timer getCancelTimer() {
        return this.getActiveMySQLConnection().getCancelTimer();
    }
    
    public String getCatalog() throws SQLException {
        return this.getActiveMySQLConnection().getCatalog();
    }
    
    public String getCharacterSetMetadata() {
        return this.getActiveMySQLConnection().getCharacterSetMetadata();
    }
    
    public SingleByteCharsetConverter getCharsetConverter(final String javaEncodingName) throws SQLException {
        return this.getActiveMySQLConnection().getCharsetConverter(javaEncodingName);
    }
    
    public String getCharsetNameForIndex(final int charsetIndex) throws SQLException {
        return this.getActiveMySQLConnection().getCharsetNameForIndex(charsetIndex);
    }
    
    public TimeZone getDefaultTimeZone() {
        return this.getActiveMySQLConnection().getDefaultTimeZone();
    }
    
    public String getErrorMessageEncoding() {
        return this.getActiveMySQLConnection().getErrorMessageEncoding();
    }
    
    public ExceptionInterceptor getExceptionInterceptor() {
        return this.getActiveMySQLConnection().getExceptionInterceptor();
    }
    
    public int getHoldability() throws SQLException {
        return this.getActiveMySQLConnection().getHoldability();
    }
    
    public String getHost() {
        return this.getActiveMySQLConnection().getHost();
    }
    
    public long getId() {
        return this.getActiveMySQLConnection().getId();
    }
    
    public long getIdleFor() {
        return this.getActiveMySQLConnection().getIdleFor();
    }
    
    public MysqlIO getIO() throws SQLException {
        return this.getActiveMySQLConnection().getIO();
    }
    
    public MySQLConnection getLoadBalanceSafeProxy() {
        return this.getActiveMySQLConnection().getLoadBalanceSafeProxy();
    }
    
    public Log getLog() throws SQLException {
        return this.getActiveMySQLConnection().getLog();
    }
    
    public int getMaxBytesPerChar(final String javaCharsetName) throws SQLException {
        return this.getActiveMySQLConnection().getMaxBytesPerChar(javaCharsetName);
    }
    
    public DatabaseMetaData getMetaData() throws SQLException {
        return this.getActiveMySQLConnection().getMetaData();
    }
    
    public Statement getMetadataSafeStatement() throws SQLException {
        return this.getActiveMySQLConnection().getMetadataSafeStatement();
    }
    
    public Object getMutex() throws SQLException {
        return this.getActiveMySQLConnection().getMutex();
    }
    
    public int getNetBufferLength() {
        return this.getActiveMySQLConnection().getNetBufferLength();
    }
    
    public Properties getProperties() {
        return this.getActiveMySQLConnection().getProperties();
    }
    
    public boolean getRequiresEscapingEncoder() {
        return this.getActiveMySQLConnection().getRequiresEscapingEncoder();
    }
    
    public String getServerCharacterEncoding() {
        return this.getActiveMySQLConnection().getServerCharacterEncoding();
    }
    
    public int getServerMajorVersion() {
        return this.getActiveMySQLConnection().getServerMajorVersion();
    }
    
    public int getServerMinorVersion() {
        return this.getActiveMySQLConnection().getServerMinorVersion();
    }
    
    public int getServerSubMinorVersion() {
        return this.getActiveMySQLConnection().getServerSubMinorVersion();
    }
    
    public TimeZone getServerTimezoneTZ() {
        return this.getActiveMySQLConnection().getServerTimezoneTZ();
    }
    
    public String getServerVariable(final String variableName) {
        return this.getActiveMySQLConnection().getServerVariable(variableName);
    }
    
    public String getServerVersion() {
        return this.getActiveMySQLConnection().getServerVersion();
    }
    
    public Calendar getSessionLockedCalendar() {
        return this.getActiveMySQLConnection().getSessionLockedCalendar();
    }
    
    public String getStatementComment() {
        return this.getActiveMySQLConnection().getStatementComment();
    }
    
    public List getStatementInterceptorsInstances() {
        return this.getActiveMySQLConnection().getStatementInterceptorsInstances();
    }
    
    public int getTransactionIsolation() throws SQLException {
        return this.getActiveMySQLConnection().getTransactionIsolation();
    }
    
    public synchronized Map getTypeMap() throws SQLException {
        return this.getActiveMySQLConnection().getTypeMap();
    }
    
    public String getURL() {
        return this.getActiveMySQLConnection().getURL();
    }
    
    public String getUser() {
        return this.getActiveMySQLConnection().getUser();
    }
    
    public Calendar getUtcCalendar() {
        return this.getActiveMySQLConnection().getUtcCalendar();
    }
    
    public SQLWarning getWarnings() throws SQLException {
        return this.getActiveMySQLConnection().getWarnings();
    }
    
    public boolean hasSameProperties(final Connection c) {
        return this.getActiveMySQLConnection().hasSameProperties(c);
    }
    
    public boolean hasTriedMaster() {
        return this.getActiveMySQLConnection().hasTriedMaster();
    }
    
    public void incrementNumberOfPreparedExecutes() {
        this.getActiveMySQLConnection().incrementNumberOfPreparedExecutes();
    }
    
    public void incrementNumberOfPrepares() {
        this.getActiveMySQLConnection().incrementNumberOfPrepares();
    }
    
    public void incrementNumberOfResultSetsCreated() {
        this.getActiveMySQLConnection().incrementNumberOfResultSetsCreated();
    }
    
    public void initializeExtension(final Extension ex) throws SQLException {
        this.getActiveMySQLConnection().initializeExtension(ex);
    }
    
    public void initializeResultsMetadataFromCache(final String sql, final CachedResultSetMetaData cachedMetaData, final ResultSetInternalMethods resultSet) throws SQLException {
        this.getActiveMySQLConnection().initializeResultsMetadataFromCache(sql, cachedMetaData, resultSet);
    }
    
    public void initializeSafeStatementInterceptors() throws SQLException {
        this.getActiveMySQLConnection().initializeSafeStatementInterceptors();
    }
    
    public synchronized boolean isAbonormallyLongQuery(final long millisOrNanos) {
        return this.getActiveMySQLConnection().isAbonormallyLongQuery(millisOrNanos);
    }
    
    public boolean isClientTzUTC() {
        return this.getActiveMySQLConnection().isClientTzUTC();
    }
    
    public boolean isCursorFetchEnabled() throws SQLException {
        return this.getActiveMySQLConnection().isCursorFetchEnabled();
    }
    
    public boolean isInGlobalTx() {
        return this.getActiveMySQLConnection().isInGlobalTx();
    }
    
    public synchronized boolean isMasterConnection() {
        return this.getActiveMySQLConnection().isMasterConnection();
    }
    
    public boolean isNoBackslashEscapesSet() {
        return this.getActiveMySQLConnection().isNoBackslashEscapesSet();
    }
    
    public boolean isReadInfoMsgEnabled() {
        return this.getActiveMySQLConnection().isReadInfoMsgEnabled();
    }
    
    public boolean isReadOnly() throws SQLException {
        return this.getActiveMySQLConnection().isReadOnly();
    }
    
    public boolean isRunningOnJDK13() {
        return this.getActiveMySQLConnection().isRunningOnJDK13();
    }
    
    public synchronized boolean isSameResource(final Connection otherConnection) {
        return this.getActiveMySQLConnection().isSameResource(otherConnection);
    }
    
    public boolean isServerTzUTC() {
        return this.getActiveMySQLConnection().isServerTzUTC();
    }
    
    public boolean lowerCaseTableNames() {
        return this.getActiveMySQLConnection().lowerCaseTableNames();
    }
    
    public void maxRowsChanged(final com.mysql.jdbc.Statement stmt) {
        this.getActiveMySQLConnection().maxRowsChanged(stmt);
    }
    
    public String nativeSQL(final String sql) throws SQLException {
        return this.getActiveMySQLConnection().nativeSQL(sql);
    }
    
    public boolean parserKnowsUnicode() {
        return this.getActiveMySQLConnection().parserKnowsUnicode();
    }
    
    public void ping() throws SQLException {
        this.getActiveMySQLConnection().ping();
    }
    
    public void pingInternal(final boolean checkForClosedConnection, final int timeoutMillis) throws SQLException {
        this.getActiveMySQLConnection().pingInternal(checkForClosedConnection, timeoutMillis);
    }
    
    public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        return this.getActiveMySQLConnection().prepareCall(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }
    
    public CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        return this.getActiveMySQLConnection().prepareCall(sql, resultSetType, resultSetConcurrency);
    }
    
    public CallableStatement prepareCall(final String sql) throws SQLException {
        return this.getActiveMySQLConnection().prepareCall(sql);
    }
    
    public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        return this.getActiveMySQLConnection().prepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }
    
    public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        return this.getActiveMySQLConnection().prepareStatement(sql, resultSetType, resultSetConcurrency);
    }
    
    public PreparedStatement prepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        return this.getActiveMySQLConnection().prepareStatement(sql, autoGenKeyIndex);
    }
    
    public PreparedStatement prepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        return this.getActiveMySQLConnection().prepareStatement(sql, autoGenKeyIndexes);
    }
    
    public PreparedStatement prepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        return this.getActiveMySQLConnection().prepareStatement(sql, autoGenKeyColNames);
    }
    
    public PreparedStatement prepareStatement(final String sql) throws SQLException {
        return this.getActiveMySQLConnection().prepareStatement(sql);
    }
    
    public void realClose(final boolean calledExplicitly, final boolean issueRollback, final boolean skipLocalTeardown, final Throwable reason) throws SQLException {
        this.getActiveMySQLConnection().realClose(calledExplicitly, issueRollback, skipLocalTeardown, reason);
    }
    
    public void recachePreparedStatement(final ServerPreparedStatement pstmt) throws SQLException {
        this.getActiveMySQLConnection().recachePreparedStatement(pstmt);
    }
    
    public void registerQueryExecutionTime(final long queryTimeMs) {
        this.getActiveMySQLConnection().registerQueryExecutionTime(queryTimeMs);
    }
    
    public void registerStatement(final com.mysql.jdbc.Statement stmt) {
        this.getActiveMySQLConnection().registerStatement(stmt);
    }
    
    public void releaseSavepoint(final Savepoint arg0) throws SQLException {
        this.getActiveMySQLConnection().releaseSavepoint(arg0);
    }
    
    public void reportNumberOfTablesAccessed(final int numTablesAccessed) {
        this.getActiveMySQLConnection().reportNumberOfTablesAccessed(numTablesAccessed);
    }
    
    public synchronized void reportQueryTime(final long millisOrNanos) {
        this.getActiveMySQLConnection().reportQueryTime(millisOrNanos);
    }
    
    public void resetServerState() throws SQLException {
        this.getActiveMySQLConnection().resetServerState();
    }
    
    public void rollback() throws SQLException {
        this.getActiveMySQLConnection().rollback();
    }
    
    public void rollback(final Savepoint savepoint) throws SQLException {
        this.getActiveMySQLConnection().rollback(savepoint);
    }
    
    public PreparedStatement serverPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        return this.getActiveMySQLConnection().serverPrepareStatement(sql, resultSetType, resultSetConcurrency, resultSetHoldability);
    }
    
    public PreparedStatement serverPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        return this.getActiveMySQLConnection().serverPrepareStatement(sql, resultSetType, resultSetConcurrency);
    }
    
    public PreparedStatement serverPrepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        return this.getActiveMySQLConnection().serverPrepareStatement(sql, autoGenKeyIndex);
    }
    
    public PreparedStatement serverPrepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        return this.getActiveMySQLConnection().serverPrepareStatement(sql, autoGenKeyIndexes);
    }
    
    public PreparedStatement serverPrepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        return this.getActiveMySQLConnection().serverPrepareStatement(sql, autoGenKeyColNames);
    }
    
    public PreparedStatement serverPrepareStatement(final String sql) throws SQLException {
        return this.getActiveMySQLConnection().serverPrepareStatement(sql);
    }
    
    public boolean serverSupportsConvertFn() throws SQLException {
        return this.getActiveMySQLConnection().serverSupportsConvertFn();
    }
    
    public void setAutoCommit(final boolean autoCommitFlag) throws SQLException {
        this.getActiveMySQLConnection().setAutoCommit(autoCommitFlag);
    }
    
    public void setCatalog(final String catalog) throws SQLException {
        this.getActiveMySQLConnection().setCatalog(catalog);
    }
    
    public synchronized void setFailedOver(final boolean flag) {
        this.getActiveMySQLConnection().setFailedOver(flag);
    }
    
    public void setHoldability(final int arg0) throws SQLException {
        this.getActiveMySQLConnection().setHoldability(arg0);
    }
    
    public void setInGlobalTx(final boolean flag) {
        this.getActiveMySQLConnection().setInGlobalTx(flag);
    }
    
    public void setPreferSlaveDuringFailover(final boolean flag) {
        this.getActiveMySQLConnection().setPreferSlaveDuringFailover(flag);
    }
    
    public void setProxy(final MySQLConnection proxy) {
        this.getActiveMySQLConnection().setProxy(proxy);
    }
    
    public void setReadInfoMsgEnabled(final boolean flag) {
        this.getActiveMySQLConnection().setReadInfoMsgEnabled(flag);
    }
    
    public void setReadOnly(final boolean readOnlyFlag) throws SQLException {
        this.getActiveMySQLConnection().setReadOnly(readOnlyFlag);
    }
    
    public void setReadOnlyInternal(final boolean readOnlyFlag) throws SQLException {
        this.getActiveMySQLConnection().setReadOnlyInternal(readOnlyFlag);
    }
    
    public Savepoint setSavepoint() throws SQLException {
        return this.getActiveMySQLConnection().setSavepoint();
    }
    
    public synchronized Savepoint setSavepoint(final String name) throws SQLException {
        return this.getActiveMySQLConnection().setSavepoint(name);
    }
    
    public void setStatementComment(final String comment) {
        this.getActiveMySQLConnection().setStatementComment(comment);
    }
    
    public synchronized void setTransactionIsolation(final int level) throws SQLException {
        this.getActiveMySQLConnection().setTransactionIsolation(level);
    }
    
    public void shutdownServer() throws SQLException {
        this.getActiveMySQLConnection().shutdownServer();
    }
    
    public boolean storesLowerCaseTableName() {
        return this.getActiveMySQLConnection().storesLowerCaseTableName();
    }
    
    public boolean supportsIsolationLevel() {
        return this.getActiveMySQLConnection().supportsIsolationLevel();
    }
    
    public boolean supportsQuotedIdentifiers() {
        return this.getActiveMySQLConnection().supportsQuotedIdentifiers();
    }
    
    public boolean supportsTransactions() {
        return this.getActiveMySQLConnection().supportsTransactions();
    }
    
    public void throwConnectionClosedException() throws SQLException {
        this.getActiveMySQLConnection().throwConnectionClosedException();
    }
    
    public void transactionBegun() throws SQLException {
        this.getActiveMySQLConnection().transactionBegun();
    }
    
    public void transactionCompleted() throws SQLException {
        this.getActiveMySQLConnection().transactionCompleted();
    }
    
    public void unregisterStatement(final com.mysql.jdbc.Statement stmt) {
        this.getActiveMySQLConnection().unregisterStatement(stmt);
    }
    
    public void unSafeStatementInterceptors() throws SQLException {
        this.getActiveMySQLConnection().unSafeStatementInterceptors();
    }
    
    public void unsetMaxRows(final com.mysql.jdbc.Statement stmt) throws SQLException {
        this.getActiveMySQLConnection().unsetMaxRows(stmt);
    }
    
    public boolean useAnsiQuotedIdentifiers() {
        return this.getActiveMySQLConnection().useAnsiQuotedIdentifiers();
    }
    
    public boolean useMaxRows() {
        return this.getActiveMySQLConnection().useMaxRows();
    }
    
    public boolean versionMeetsMinimum(final int major, final int minor, final int subminor) throws SQLException {
        return this.getActiveMySQLConnection().versionMeetsMinimum(major, minor, subminor);
    }
    
    public boolean isClosed() throws SQLException {
        return this.getActiveMySQLConnection().isClosed();
    }
    
    public boolean getHoldResultsOpenOverStatementClose() {
        return this.getActiveMySQLConnection().getHoldResultsOpenOverStatementClose();
    }
    
    public String getLoadBalanceConnectionGroup() {
        return this.getActiveMySQLConnection().getLoadBalanceConnectionGroup();
    }
    
    public boolean getLoadBalanceEnableJMX() {
        return this.getActiveMySQLConnection().getLoadBalanceEnableJMX();
    }
    
    public String getLoadBalanceExceptionChecker() {
        return this.getActiveMySQLConnection().getLoadBalanceExceptionChecker();
    }
    
    public String getLoadBalanceSQLExceptionSubclassFailover() {
        return this.getActiveMySQLConnection().getLoadBalanceSQLExceptionSubclassFailover();
    }
    
    public String getLoadBalanceSQLStateFailover() {
        return this.getActiveMySQLConnection().getLoadBalanceSQLStateFailover();
    }
    
    public void setLoadBalanceConnectionGroup(final String loadBalanceConnectionGroup) {
        this.getActiveMySQLConnection().setLoadBalanceConnectionGroup(loadBalanceConnectionGroup);
    }
    
    public void setLoadBalanceEnableJMX(final boolean loadBalanceEnableJMX) {
        this.getActiveMySQLConnection().setLoadBalanceEnableJMX(loadBalanceEnableJMX);
    }
    
    public void setLoadBalanceExceptionChecker(final String loadBalanceExceptionChecker) {
        this.getActiveMySQLConnection().setLoadBalanceExceptionChecker(loadBalanceExceptionChecker);
    }
    
    public void setLoadBalanceSQLExceptionSubclassFailover(final String loadBalanceSQLExceptionSubclassFailover) {
        this.getActiveMySQLConnection().setLoadBalanceSQLExceptionSubclassFailover(loadBalanceSQLExceptionSubclassFailover);
    }
    
    public void setLoadBalanceSQLStateFailover(final String loadBalanceSQLStateFailover) {
        this.getActiveMySQLConnection().setLoadBalanceSQLStateFailover(loadBalanceSQLStateFailover);
    }
    
    public boolean shouldExecutionTriggerServerSwapAfter(final String SQL) {
        return false;
    }
    
    public boolean isProxySet() {
        return this.getActiveMySQLConnection().isProxySet();
    }
    
    public String getLoadBalanceAutoCommitStatementRegex() {
        return this.getActiveMySQLConnection().getLoadBalanceAutoCommitStatementRegex();
    }
    
    public int getLoadBalanceAutoCommitStatementThreshold() {
        return this.getActiveMySQLConnection().getLoadBalanceAutoCommitStatementThreshold();
    }
    
    public void setLoadBalanceAutoCommitStatementRegex(final String loadBalanceAutoCommitStatementRegex) {
        this.getActiveMySQLConnection().setLoadBalanceAutoCommitStatementRegex(loadBalanceAutoCommitStatementRegex);
    }
    
    public void setLoadBalanceAutoCommitStatementThreshold(final int loadBalanceAutoCommitStatementThreshold) {
        this.getActiveMySQLConnection().setLoadBalanceAutoCommitStatementThreshold(loadBalanceAutoCommitStatementThreshold);
    }
    
    public synchronized void setTypeMap(final Map map) throws SQLException {
        this.getActiveMySQLConnection().setTypeMap(map);
    }
}
