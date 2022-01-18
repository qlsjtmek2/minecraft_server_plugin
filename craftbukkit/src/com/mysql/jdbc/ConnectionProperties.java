// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.SQLException;

public interface ConnectionProperties
{
    String exposeAsXml() throws SQLException;
    
    boolean getAllowLoadLocalInfile();
    
    boolean getAllowMultiQueries();
    
    boolean getAllowNanAndInf();
    
    boolean getAllowUrlInLocalInfile();
    
    boolean getAlwaysSendSetIsolation();
    
    boolean getAutoDeserialize();
    
    boolean getAutoGenerateTestcaseScript();
    
    boolean getAutoReconnectForPools();
    
    int getBlobSendChunkSize();
    
    boolean getCacheCallableStatements();
    
    boolean getCachePreparedStatements();
    
    boolean getCacheResultSetMetadata();
    
    boolean getCacheServerConfiguration();
    
    int getCallableStatementCacheSize();
    
    boolean getCapitalizeTypeNames();
    
    String getCharacterSetResults();
    
    boolean getClobberStreamingResults();
    
    String getClobCharacterEncoding();
    
    String getConnectionCollation();
    
    int getConnectTimeout();
    
    boolean getContinueBatchOnError();
    
    boolean getCreateDatabaseIfNotExist();
    
    int getDefaultFetchSize();
    
    boolean getDontTrackOpenResources();
    
    boolean getDumpQueriesOnException();
    
    boolean getDynamicCalendars();
    
    boolean getElideSetAutoCommits();
    
    boolean getEmptyStringsConvertToZero();
    
    boolean getEmulateLocators();
    
    boolean getEmulateUnsupportedPstmts();
    
    boolean getEnablePacketDebug();
    
    String getEncoding();
    
    boolean getExplainSlowQueries();
    
    boolean getFailOverReadOnly();
    
    boolean getGatherPerformanceMetrics();
    
    boolean getHoldResultsOpenOverStatementClose();
    
    boolean getIgnoreNonTxTables();
    
    int getInitialTimeout();
    
    boolean getInteractiveClient();
    
    boolean getIsInteractiveClient();
    
    boolean getJdbcCompliantTruncation();
    
    int getLocatorFetchBufferSize();
    
    String getLogger();
    
    String getLoggerClassName();
    
    boolean getLogSlowQueries();
    
    boolean getMaintainTimeStats();
    
    int getMaxQuerySizeToLog();
    
    int getMaxReconnects();
    
    int getMaxRows();
    
    int getMetadataCacheSize();
    
    boolean getNoDatetimeStringSync();
    
    boolean getNullCatalogMeansCurrent();
    
    boolean getNullNamePatternMatchesAll();
    
    int getPacketDebugBufferSize();
    
    boolean getParanoid();
    
    boolean getPedantic();
    
    int getPreparedStatementCacheSize();
    
    int getPreparedStatementCacheSqlLimit();
    
    boolean getProfileSql();
    
    boolean getProfileSQL();
    
    String getPropertiesTransform();
    
    int getQueriesBeforeRetryMaster();
    
    boolean getReconnectAtTxEnd();
    
    boolean getRelaxAutoCommit();
    
    int getReportMetricsIntervalMillis();
    
    boolean getRequireSSL();
    
    boolean getRollbackOnPooledClose();
    
    boolean getRoundRobinLoadBalance();
    
    boolean getRunningCTS13();
    
    int getSecondsBeforeRetryMaster();
    
    String getServerTimezone();
    
    String getSessionVariables();
    
    int getSlowQueryThresholdMillis();
    
    String getSocketFactoryClassName();
    
    int getSocketTimeout();
    
    boolean getStrictFloatingPoint();
    
    boolean getStrictUpdates();
    
    boolean getTinyInt1isBit();
    
    boolean getTraceProtocol();
    
    boolean getTransformedBitIsBoolean();
    
    boolean getUseCompression();
    
    boolean getUseFastIntParsing();
    
    boolean getUseHostsInPrivileges();
    
    boolean getUseInformationSchema();
    
    boolean getUseLocalSessionState();
    
    boolean getUseOldUTF8Behavior();
    
    boolean getUseOnlyServerErrorMessages();
    
    boolean getUseReadAheadInput();
    
    boolean getUseServerPreparedStmts();
    
    boolean getUseSqlStateCodes();
    
    boolean getUseSSL();
    
    boolean getUseStreamLengthsInPrepStmts();
    
    boolean getUseTimezone();
    
    boolean getUseUltraDevWorkAround();
    
    boolean getUseUnbufferedInput();
    
    boolean getUseUnicode();
    
    boolean getUseUsageAdvisor();
    
    boolean getYearIsDateType();
    
    String getZeroDateTimeBehavior();
    
    void setAllowLoadLocalInfile(final boolean p0);
    
    void setAllowMultiQueries(final boolean p0);
    
    void setAllowNanAndInf(final boolean p0);
    
    void setAllowUrlInLocalInfile(final boolean p0);
    
    void setAlwaysSendSetIsolation(final boolean p0);
    
    void setAutoDeserialize(final boolean p0);
    
    void setAutoGenerateTestcaseScript(final boolean p0);
    
    void setAutoReconnect(final boolean p0);
    
    void setAutoReconnectForConnectionPools(final boolean p0);
    
    void setAutoReconnectForPools(final boolean p0);
    
    void setBlobSendChunkSize(final String p0) throws SQLException;
    
    void setCacheCallableStatements(final boolean p0);
    
    void setCachePreparedStatements(final boolean p0);
    
    void setCacheResultSetMetadata(final boolean p0);
    
    void setCacheServerConfiguration(final boolean p0);
    
    void setCallableStatementCacheSize(final int p0);
    
    void setCapitalizeDBMDTypes(final boolean p0);
    
    void setCapitalizeTypeNames(final boolean p0);
    
    void setCharacterEncoding(final String p0);
    
    void setCharacterSetResults(final String p0);
    
    void setClobberStreamingResults(final boolean p0);
    
    void setClobCharacterEncoding(final String p0);
    
    void setConnectionCollation(final String p0);
    
    void setConnectTimeout(final int p0);
    
    void setContinueBatchOnError(final boolean p0);
    
    void setCreateDatabaseIfNotExist(final boolean p0);
    
    void setDefaultFetchSize(final int p0);
    
    void setDetectServerPreparedStmts(final boolean p0);
    
    void setDontTrackOpenResources(final boolean p0);
    
    void setDumpQueriesOnException(final boolean p0);
    
    void setDynamicCalendars(final boolean p0);
    
    void setElideSetAutoCommits(final boolean p0);
    
    void setEmptyStringsConvertToZero(final boolean p0);
    
    void setEmulateLocators(final boolean p0);
    
    void setEmulateUnsupportedPstmts(final boolean p0);
    
    void setEnablePacketDebug(final boolean p0);
    
    void setEncoding(final String p0);
    
    void setExplainSlowQueries(final boolean p0);
    
    void setFailOverReadOnly(final boolean p0);
    
    void setGatherPerformanceMetrics(final boolean p0);
    
    void setHoldResultsOpenOverStatementClose(final boolean p0);
    
    void setIgnoreNonTxTables(final boolean p0);
    
    void setInitialTimeout(final int p0);
    
    void setIsInteractiveClient(final boolean p0);
    
    void setJdbcCompliantTruncation(final boolean p0);
    
    void setLocatorFetchBufferSize(final String p0) throws SQLException;
    
    void setLogger(final String p0);
    
    void setLoggerClassName(final String p0);
    
    void setLogSlowQueries(final boolean p0);
    
    void setMaintainTimeStats(final boolean p0);
    
    void setMaxQuerySizeToLog(final int p0);
    
    void setMaxReconnects(final int p0);
    
    void setMaxRows(final int p0);
    
    void setMetadataCacheSize(final int p0);
    
    void setNoDatetimeStringSync(final boolean p0);
    
    void setNullCatalogMeansCurrent(final boolean p0);
    
    void setNullNamePatternMatchesAll(final boolean p0);
    
    void setPacketDebugBufferSize(final int p0);
    
    void setParanoid(final boolean p0);
    
    void setPedantic(final boolean p0);
    
    void setPreparedStatementCacheSize(final int p0);
    
    void setPreparedStatementCacheSqlLimit(final int p0);
    
    void setProfileSql(final boolean p0);
    
    void setProfileSQL(final boolean p0);
    
    void setPropertiesTransform(final String p0);
    
    void setQueriesBeforeRetryMaster(final int p0);
    
    void setReconnectAtTxEnd(final boolean p0);
    
    void setRelaxAutoCommit(final boolean p0);
    
    void setReportMetricsIntervalMillis(final int p0);
    
    void setRequireSSL(final boolean p0);
    
    void setRetainStatementAfterResultSetClose(final boolean p0);
    
    void setRollbackOnPooledClose(final boolean p0);
    
    void setRoundRobinLoadBalance(final boolean p0);
    
    void setRunningCTS13(final boolean p0);
    
    void setSecondsBeforeRetryMaster(final int p0);
    
    void setServerTimezone(final String p0);
    
    void setSessionVariables(final String p0);
    
    void setSlowQueryThresholdMillis(final int p0);
    
    void setSocketFactoryClassName(final String p0);
    
    void setSocketTimeout(final int p0);
    
    void setStrictFloatingPoint(final boolean p0);
    
    void setStrictUpdates(final boolean p0);
    
    void setTinyInt1isBit(final boolean p0);
    
    void setTraceProtocol(final boolean p0);
    
    void setTransformedBitIsBoolean(final boolean p0);
    
    void setUseCompression(final boolean p0);
    
    void setUseFastIntParsing(final boolean p0);
    
    void setUseHostsInPrivileges(final boolean p0);
    
    void setUseInformationSchema(final boolean p0);
    
    void setUseLocalSessionState(final boolean p0);
    
    void setUseOldUTF8Behavior(final boolean p0);
    
    void setUseOnlyServerErrorMessages(final boolean p0);
    
    void setUseReadAheadInput(final boolean p0);
    
    void setUseServerPreparedStmts(final boolean p0);
    
    void setUseSqlStateCodes(final boolean p0);
    
    void setUseSSL(final boolean p0);
    
    void setUseStreamLengthsInPrepStmts(final boolean p0);
    
    void setUseTimezone(final boolean p0);
    
    void setUseUltraDevWorkAround(final boolean p0);
    
    void setUseUnbufferedInput(final boolean p0);
    
    void setUseUnicode(final boolean p0);
    
    void setUseUsageAdvisor(final boolean p0);
    
    void setYearIsDateType(final boolean p0);
    
    void setZeroDateTimeBehavior(final String p0);
    
    boolean useUnbufferedInput();
    
    boolean getUseCursorFetch();
    
    void setUseCursorFetch(final boolean p0);
    
    boolean getOverrideSupportsIntegrityEnhancementFacility();
    
    void setOverrideSupportsIntegrityEnhancementFacility(final boolean p0);
    
    boolean getNoTimezoneConversionForTimeType();
    
    void setNoTimezoneConversionForTimeType(final boolean p0);
    
    boolean getUseJDBCCompliantTimezoneShift();
    
    void setUseJDBCCompliantTimezoneShift(final boolean p0);
    
    boolean getAutoClosePStmtStreams();
    
    void setAutoClosePStmtStreams(final boolean p0);
    
    boolean getProcessEscapeCodesForPrepStmts();
    
    void setProcessEscapeCodesForPrepStmts(final boolean p0);
    
    boolean getUseGmtMillisForDatetimes();
    
    void setUseGmtMillisForDatetimes(final boolean p0);
    
    boolean getDumpMetadataOnColumnNotFound();
    
    void setDumpMetadataOnColumnNotFound(final boolean p0);
    
    String getResourceId();
    
    void setResourceId(final String p0);
    
    boolean getRewriteBatchedStatements();
    
    void setRewriteBatchedStatements(final boolean p0);
    
    boolean getJdbcCompliantTruncationForReads();
    
    void setJdbcCompliantTruncationForReads(final boolean p0);
    
    boolean getUseJvmCharsetConverters();
    
    void setUseJvmCharsetConverters(final boolean p0);
    
    boolean getPinGlobalTxToPhysicalConnection();
    
    void setPinGlobalTxToPhysicalConnection(final boolean p0);
    
    void setGatherPerfMetrics(final boolean p0);
    
    boolean getGatherPerfMetrics();
    
    void setUltraDevHack(final boolean p0);
    
    boolean getUltraDevHack();
    
    void setInteractiveClient(final boolean p0);
    
    void setSocketFactory(final String p0);
    
    String getSocketFactory();
    
    void setUseServerPrepStmts(final boolean p0);
    
    boolean getUseServerPrepStmts();
    
    void setCacheCallableStmts(final boolean p0);
    
    boolean getCacheCallableStmts();
    
    void setCachePrepStmts(final boolean p0);
    
    boolean getCachePrepStmts();
    
    void setCallableStmtCacheSize(final int p0);
    
    int getCallableStmtCacheSize();
    
    void setPrepStmtCacheSize(final int p0);
    
    int getPrepStmtCacheSize();
    
    void setPrepStmtCacheSqlLimit(final int p0);
    
    int getPrepStmtCacheSqlLimit();
    
    boolean getNoAccessToProcedureBodies();
    
    void setNoAccessToProcedureBodies(final boolean p0);
    
    boolean getUseOldAliasMetadataBehavior();
    
    void setUseOldAliasMetadataBehavior(final boolean p0);
    
    String getClientCertificateKeyStorePassword();
    
    void setClientCertificateKeyStorePassword(final String p0);
    
    String getClientCertificateKeyStoreType();
    
    void setClientCertificateKeyStoreType(final String p0);
    
    String getClientCertificateKeyStoreUrl();
    
    void setClientCertificateKeyStoreUrl(final String p0);
    
    String getTrustCertificateKeyStorePassword();
    
    void setTrustCertificateKeyStorePassword(final String p0);
    
    String getTrustCertificateKeyStoreType();
    
    void setTrustCertificateKeyStoreType(final String p0);
    
    String getTrustCertificateKeyStoreUrl();
    
    void setTrustCertificateKeyStoreUrl(final String p0);
    
    boolean getUseSSPSCompatibleTimezoneShift();
    
    void setUseSSPSCompatibleTimezoneShift(final boolean p0);
    
    boolean getTreatUtilDateAsTimestamp();
    
    void setTreatUtilDateAsTimestamp(final boolean p0);
    
    boolean getUseFastDateParsing();
    
    void setUseFastDateParsing(final boolean p0);
    
    String getLocalSocketAddress();
    
    void setLocalSocketAddress(final String p0);
    
    void setUseConfigs(final String p0);
    
    String getUseConfigs();
    
    boolean getGenerateSimpleParameterMetadata();
    
    void setGenerateSimpleParameterMetadata(final boolean p0);
    
    boolean getLogXaCommands();
    
    void setLogXaCommands(final boolean p0);
    
    int getResultSetSizeThreshold();
    
    void setResultSetSizeThreshold(final int p0);
    
    int getNetTimeoutForStreamingResults();
    
    void setNetTimeoutForStreamingResults(final int p0);
    
    boolean getEnableQueryTimeouts();
    
    void setEnableQueryTimeouts(final boolean p0);
    
    boolean getPadCharsWithSpace();
    
    void setPadCharsWithSpace(final boolean p0);
    
    boolean getUseDynamicCharsetInfo();
    
    void setUseDynamicCharsetInfo(final boolean p0);
    
    String getClientInfoProvider();
    
    void setClientInfoProvider(final String p0);
    
    boolean getPopulateInsertRowWithDefaultValues();
    
    void setPopulateInsertRowWithDefaultValues(final boolean p0);
    
    String getLoadBalanceStrategy();
    
    void setLoadBalanceStrategy(final String p0);
    
    boolean getTcpNoDelay();
    
    void setTcpNoDelay(final boolean p0);
    
    boolean getTcpKeepAlive();
    
    void setTcpKeepAlive(final boolean p0);
    
    int getTcpRcvBuf();
    
    void setTcpRcvBuf(final int p0);
    
    int getTcpSndBuf();
    
    void setTcpSndBuf(final int p0);
    
    int getTcpTrafficClass();
    
    void setTcpTrafficClass(final int p0);
    
    boolean getUseNanosForElapsedTime();
    
    void setUseNanosForElapsedTime(final boolean p0);
    
    long getSlowQueryThresholdNanos();
    
    void setSlowQueryThresholdNanos(final long p0);
    
    String getStatementInterceptors();
    
    void setStatementInterceptors(final String p0);
    
    boolean getUseDirectRowUnpack();
    
    void setUseDirectRowUnpack(final boolean p0);
    
    String getLargeRowSizeThreshold();
    
    void setLargeRowSizeThreshold(final String p0);
    
    boolean getUseBlobToStoreUTF8OutsideBMP();
    
    void setUseBlobToStoreUTF8OutsideBMP(final boolean p0);
    
    String getUtf8OutsideBmpExcludedColumnNamePattern();
    
    void setUtf8OutsideBmpExcludedColumnNamePattern(final String p0);
    
    String getUtf8OutsideBmpIncludedColumnNamePattern();
    
    void setUtf8OutsideBmpIncludedColumnNamePattern(final String p0);
    
    boolean getIncludeInnodbStatusInDeadlockExceptions();
    
    void setIncludeInnodbStatusInDeadlockExceptions(final boolean p0);
    
    boolean getBlobsAreStrings();
    
    void setBlobsAreStrings(final boolean p0);
    
    boolean getFunctionsNeverReturnBlobs();
    
    void setFunctionsNeverReturnBlobs(final boolean p0);
    
    boolean getAutoSlowLog();
    
    void setAutoSlowLog(final boolean p0);
    
    String getConnectionLifecycleInterceptors();
    
    void setConnectionLifecycleInterceptors(final String p0);
    
    String getProfilerEventHandler();
    
    void setProfilerEventHandler(final String p0);
    
    boolean getVerifyServerCertificate();
    
    void setVerifyServerCertificate(final boolean p0);
    
    boolean getUseLegacyDatetimeCode();
    
    void setUseLegacyDatetimeCode(final boolean p0);
    
    int getSelfDestructOnPingSecondsLifetime();
    
    void setSelfDestructOnPingSecondsLifetime(final int p0);
    
    int getSelfDestructOnPingMaxOperations();
    
    void setSelfDestructOnPingMaxOperations(final int p0);
    
    boolean getUseColumnNamesInFindColumn();
    
    void setUseColumnNamesInFindColumn(final boolean p0);
    
    boolean getUseLocalTransactionState();
    
    void setUseLocalTransactionState(final boolean p0);
    
    boolean getCompensateOnDuplicateKeyUpdateCounts();
    
    void setCompensateOnDuplicateKeyUpdateCounts(final boolean p0);
    
    void setUseAffectedRows(final boolean p0);
    
    boolean getUseAffectedRows();
    
    void setPasswordCharacterEncoding(final String p0);
    
    String getPasswordCharacterEncoding();
    
    int getLoadBalanceBlacklistTimeout();
    
    void setLoadBalanceBlacklistTimeout(final int p0);
    
    void setRetriesAllDown(final int p0);
    
    int getRetriesAllDown();
    
    ExceptionInterceptor getExceptionInterceptor();
    
    void setExceptionInterceptors(final String p0);
    
    String getExceptionInterceptors();
    
    boolean getQueryTimeoutKillsConnection();
    
    void setQueryTimeoutKillsConnection(final boolean p0);
    
    int getMaxAllowedPacket();
    
    boolean getRetainStatementAfterResultSetClose();
    
    int getLoadBalancePingTimeout();
    
    void setLoadBalancePingTimeout(final int p0);
    
    boolean getLoadBalanceValidateConnectionOnSwapServer();
    
    void setLoadBalanceValidateConnectionOnSwapServer(final boolean p0);
    
    String getLoadBalanceConnectionGroup();
    
    void setLoadBalanceConnectionGroup(final String p0);
    
    String getLoadBalanceExceptionChecker();
    
    void setLoadBalanceExceptionChecker(final String p0);
    
    String getLoadBalanceSQLStateFailover();
    
    void setLoadBalanceSQLStateFailover(final String p0);
    
    String getLoadBalanceSQLExceptionSubclassFailover();
    
    void setLoadBalanceSQLExceptionSubclassFailover(final String p0);
    
    boolean getLoadBalanceEnableJMX();
    
    void setLoadBalanceEnableJMX(final boolean p0);
    
    void setLoadBalanceAutoCommitStatementThreshold(final int p0);
    
    int getLoadBalanceAutoCommitStatementThreshold();
    
    void setLoadBalanceAutoCommitStatementRegex(final String p0);
    
    String getLoadBalanceAutoCommitStatementRegex();
}
