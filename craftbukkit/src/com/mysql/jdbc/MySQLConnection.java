// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.List;
import java.util.Properties;
import java.sql.Statement;
import com.mysql.jdbc.log.Log;
import java.util.TimeZone;
import java.util.Timer;
import java.util.Calendar;
import java.sql.SQLException;

public interface MySQLConnection extends Connection, ConnectionProperties
{
    boolean isProxySet();
    
    void abortInternal() throws SQLException;
    
    void checkClosed() throws SQLException;
    
    void createNewIO(final boolean p0) throws SQLException;
    
    void dumpTestcaseQuery(final String p0);
    
    Connection duplicate() throws SQLException;
    
    ResultSetInternalMethods execSQL(final StatementImpl p0, final String p1, final int p2, final Buffer p3, final int p4, final int p5, final boolean p6, final String p7, final Field[] p8) throws SQLException;
    
    ResultSetInternalMethods execSQL(final StatementImpl p0, final String p1, final int p2, final Buffer p3, final int p4, final int p5, final boolean p6, final String p7, final Field[] p8, final boolean p9) throws SQLException;
    
    String extractSqlFromPacket(final String p0, final Buffer p1, final int p2) throws SQLException;
    
    StringBuffer generateConnectionCommentBlock(final StringBuffer p0);
    
    int getActiveStatementCount();
    
    int getAutoIncrementIncrement();
    
    CachedResultSetMetaData getCachedMetaData(final String p0);
    
    Calendar getCalendarInstanceForSessionOrNew();
    
    Timer getCancelTimer();
    
    String getCharacterSetMetadata();
    
    SingleByteCharsetConverter getCharsetConverter(final String p0) throws SQLException;
    
    String getCharsetNameForIndex(final int p0) throws SQLException;
    
    TimeZone getDefaultTimeZone();
    
    String getErrorMessageEncoding();
    
    ExceptionInterceptor getExceptionInterceptor();
    
    String getHost();
    
    long getId();
    
    long getIdleFor();
    
    MysqlIO getIO() throws SQLException;
    
    Log getLog() throws SQLException;
    
    int getMaxBytesPerChar(final String p0) throws SQLException;
    
    Statement getMetadataSafeStatement() throws SQLException;
    
    Object getMutex() throws SQLException;
    
    int getNetBufferLength();
    
    Properties getProperties();
    
    boolean getRequiresEscapingEncoder();
    
    String getServerCharacterEncoding();
    
    int getServerMajorVersion();
    
    int getServerMinorVersion();
    
    int getServerSubMinorVersion();
    
    TimeZone getServerTimezoneTZ();
    
    String getServerVariable(final String p0);
    
    String getServerVersion();
    
    Calendar getSessionLockedCalendar();
    
    String getStatementComment();
    
    List getStatementInterceptorsInstances();
    
    String getURL();
    
    String getUser();
    
    Calendar getUtcCalendar();
    
    void incrementNumberOfPreparedExecutes();
    
    void incrementNumberOfPrepares();
    
    void incrementNumberOfResultSetsCreated();
    
    void initializeResultsMetadataFromCache(final String p0, final CachedResultSetMetaData p1, final ResultSetInternalMethods p2) throws SQLException;
    
    void initializeSafeStatementInterceptors() throws SQLException;
    
    boolean isAbonormallyLongQuery(final long p0);
    
    boolean isClientTzUTC();
    
    boolean isCursorFetchEnabled() throws SQLException;
    
    boolean isReadInfoMsgEnabled();
    
    boolean isReadOnly() throws SQLException;
    
    boolean isRunningOnJDK13();
    
    boolean isServerTzUTC();
    
    boolean lowerCaseTableNames();
    
    void maxRowsChanged(final com.mysql.jdbc.Statement p0);
    
    void pingInternal(final boolean p0, final int p1) throws SQLException;
    
    void realClose(final boolean p0, final boolean p1, final boolean p2, final Throwable p3) throws SQLException;
    
    void recachePreparedStatement(final ServerPreparedStatement p0) throws SQLException;
    
    void registerQueryExecutionTime(final long p0);
    
    void registerStatement(final com.mysql.jdbc.Statement p0);
    
    void reportNumberOfTablesAccessed(final int p0);
    
    boolean serverSupportsConvertFn() throws SQLException;
    
    void setProxy(final MySQLConnection p0);
    
    void setReadInfoMsgEnabled(final boolean p0);
    
    void setReadOnlyInternal(final boolean p0) throws SQLException;
    
    void shutdownServer() throws SQLException;
    
    boolean storesLowerCaseTableName();
    
    void throwConnectionClosedException() throws SQLException;
    
    void transactionBegun() throws SQLException;
    
    void transactionCompleted() throws SQLException;
    
    void unregisterStatement(final com.mysql.jdbc.Statement p0);
    
    void unSafeStatementInterceptors() throws SQLException;
    
    void unsetMaxRows(final com.mysql.jdbc.Statement p0) throws SQLException;
    
    boolean useAnsiQuotedIdentifiers();
    
    boolean useMaxRows();
    
    MySQLConnection getLoadBalanceSafeProxy();
}
