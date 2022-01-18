// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.net.URL;
import java.util.TimeZone;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.io.ObjectOutputStream;
import java.sql.Ref;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.sql.Timestamp;
import java.sql.Time;
import java.math.BigInteger;
import java.util.Locale;
import java.util.Calendar;
import java.sql.Clob;
import java.sql.Blob;
import java.math.BigDecimal;
import java.sql.Array;
import com.mysql.jdbc.profiler.ProfilerEvent;
import java.sql.ParameterMetaData;
import java.io.StringReader;
import java.util.Date;
import java.sql.ResultSet;
import com.mysql.jdbc.exceptions.MySQLStatementCancelledException;
import com.mysql.jdbc.exceptions.MySQLTimeoutException;
import java.sql.Statement;
import java.sql.BatchUpdateException;
import java.util.TimerTask;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.CharsetEncoder;
import java.text.SimpleDateFormat;
import java.sql.ResultSetMetaData;
import java.io.InputStream;
import java.sql.DatabaseMetaData;
import java.lang.reflect.Constructor;

public class PreparedStatement extends StatementImpl implements java.sql.PreparedStatement
{
    private static final Constructor JDBC_4_PSTMT_2_ARG_CTOR;
    private static final Constructor JDBC_4_PSTMT_3_ARG_CTOR;
    private static final Constructor JDBC_4_PSTMT_4_ARG_CTOR;
    private static final byte[] HEX_DIGITS;
    protected boolean batchHasPlainStatements;
    private DatabaseMetaData dbmd;
    protected char firstCharOfStmt;
    protected boolean hasLimitClause;
    protected boolean isLoadDataQuery;
    private boolean[] isNull;
    private boolean[] isStream;
    protected int numberOfExecutions;
    protected String originalSql;
    protected int parameterCount;
    protected MysqlParameterMetadata parameterMetaData;
    private InputStream[] parameterStreams;
    private byte[][] parameterValues;
    protected int[] parameterTypes;
    protected ParseInfo parseInfo;
    private ResultSetMetaData pstmtResultMetaData;
    private byte[][] staticSqlStrings;
    private byte[] streamConvertBuf;
    private int[] streamLengths;
    private SimpleDateFormat tsdf;
    protected boolean useTrueBoolean;
    protected boolean usingAnsiMode;
    protected String batchedValuesClause;
    private boolean doPingInstead;
    private SimpleDateFormat ddf;
    private SimpleDateFormat tdf;
    private boolean compensateForOnDuplicateKeyUpdate;
    private CharsetEncoder charsetEncoder;
    private int batchCommandIndex;
    protected int rewrittenBatchSize;
    
    protected static int readFully(final Reader reader, final char[] buf, final int length) throws IOException {
        int numCharsRead;
        int count;
        for (numCharsRead = 0; numCharsRead < length; numCharsRead += count) {
            count = reader.read(buf, numCharsRead, length - numCharsRead);
            if (count < 0) {
                break;
            }
        }
        return numCharsRead;
    }
    
    protected static PreparedStatement getInstance(final MySQLConnection conn, final String catalog) throws SQLException {
        if (!Util.isJdbc4()) {
            return new PreparedStatement(conn, catalog);
        }
        return (PreparedStatement)Util.handleNewInstance(PreparedStatement.JDBC_4_PSTMT_2_ARG_CTOR, new Object[] { conn, catalog }, conn.getExceptionInterceptor());
    }
    
    protected static PreparedStatement getInstance(final MySQLConnection conn, final String sql, final String catalog) throws SQLException {
        if (!Util.isJdbc4()) {
            return new PreparedStatement(conn, sql, catalog);
        }
        return (PreparedStatement)Util.handleNewInstance(PreparedStatement.JDBC_4_PSTMT_3_ARG_CTOR, new Object[] { conn, sql, catalog }, conn.getExceptionInterceptor());
    }
    
    protected static PreparedStatement getInstance(final MySQLConnection conn, final String sql, final String catalog, final ParseInfo cachedParseInfo) throws SQLException {
        if (!Util.isJdbc4()) {
            return new PreparedStatement(conn, sql, catalog, cachedParseInfo);
        }
        return (PreparedStatement)Util.handleNewInstance(PreparedStatement.JDBC_4_PSTMT_4_ARG_CTOR, new Object[] { conn, sql, catalog, cachedParseInfo }, conn.getExceptionInterceptor());
    }
    
    public PreparedStatement(final MySQLConnection conn, final String catalog) throws SQLException {
        super(conn, catalog);
        this.batchHasPlainStatements = false;
        this.dbmd = null;
        this.firstCharOfStmt = '\0';
        this.hasLimitClause = false;
        this.isLoadDataQuery = false;
        this.isNull = null;
        this.isStream = null;
        this.numberOfExecutions = 0;
        this.originalSql = null;
        this.parameterStreams = null;
        this.parameterValues = null;
        this.parameterTypes = null;
        this.staticSqlStrings = null;
        this.streamConvertBuf = new byte[4096];
        this.streamLengths = null;
        this.tsdf = null;
        this.useTrueBoolean = false;
        this.compensateForOnDuplicateKeyUpdate = false;
        this.batchCommandIndex = -1;
        this.rewrittenBatchSize = 0;
        this.compensateForOnDuplicateKeyUpdate = this.connection.getCompensateOnDuplicateKeyUpdateCounts();
    }
    
    public PreparedStatement(final MySQLConnection conn, final String sql, final String catalog) throws SQLException {
        super(conn, catalog);
        this.batchHasPlainStatements = false;
        this.dbmd = null;
        this.firstCharOfStmt = '\0';
        this.hasLimitClause = false;
        this.isLoadDataQuery = false;
        this.isNull = null;
        this.isStream = null;
        this.numberOfExecutions = 0;
        this.originalSql = null;
        this.parameterStreams = null;
        this.parameterValues = null;
        this.parameterTypes = null;
        this.staticSqlStrings = null;
        this.streamConvertBuf = new byte[4096];
        this.streamLengths = null;
        this.tsdf = null;
        this.useTrueBoolean = false;
        this.compensateForOnDuplicateKeyUpdate = false;
        this.batchCommandIndex = -1;
        this.rewrittenBatchSize = 0;
        if (sql == null) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.0"), "S1009", this.getExceptionInterceptor());
        }
        this.originalSql = sql;
        if (this.originalSql.startsWith("/* ping */")) {
            this.doPingInstead = true;
        }
        else {
            this.doPingInstead = false;
        }
        this.dbmd = this.connection.getMetaData();
        this.useTrueBoolean = this.connection.versionMeetsMinimum(3, 21, 23);
        this.parseInfo = new ParseInfo(sql, this.connection, this.dbmd, this.charEncoding, this.charConverter);
        this.initializeFromParseInfo();
        this.compensateForOnDuplicateKeyUpdate = this.connection.getCompensateOnDuplicateKeyUpdateCounts();
        if (conn.getRequiresEscapingEncoder()) {
            this.charsetEncoder = Charset.forName(conn.getEncoding()).newEncoder();
        }
    }
    
    public PreparedStatement(final MySQLConnection conn, final String sql, final String catalog, final ParseInfo cachedParseInfo) throws SQLException {
        super(conn, catalog);
        this.batchHasPlainStatements = false;
        this.dbmd = null;
        this.firstCharOfStmt = '\0';
        this.hasLimitClause = false;
        this.isLoadDataQuery = false;
        this.isNull = null;
        this.isStream = null;
        this.numberOfExecutions = 0;
        this.originalSql = null;
        this.parameterStreams = null;
        this.parameterValues = null;
        this.parameterTypes = null;
        this.staticSqlStrings = null;
        this.streamConvertBuf = new byte[4096];
        this.streamLengths = null;
        this.tsdf = null;
        this.useTrueBoolean = false;
        this.compensateForOnDuplicateKeyUpdate = false;
        this.batchCommandIndex = -1;
        this.rewrittenBatchSize = 0;
        if (sql == null) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.1"), "S1009", this.getExceptionInterceptor());
        }
        this.originalSql = sql;
        this.dbmd = this.connection.getMetaData();
        this.useTrueBoolean = this.connection.versionMeetsMinimum(3, 21, 23);
        this.parseInfo = cachedParseInfo;
        this.usingAnsiMode = !this.connection.useAnsiQuotedIdentifiers();
        this.initializeFromParseInfo();
        this.compensateForOnDuplicateKeyUpdate = this.connection.getCompensateOnDuplicateKeyUpdateCounts();
        if (conn.getRequiresEscapingEncoder()) {
            this.charsetEncoder = Charset.forName(conn.getEncoding()).newEncoder();
        }
    }
    
    public void addBatch() throws SQLException {
        if (this.batchedArgs == null) {
            this.batchedArgs = new ArrayList();
        }
        for (int i = 0; i < this.parameterValues.length; ++i) {
            this.checkAllParametersSet(this.parameterValues[i], this.parameterStreams[i], i);
        }
        this.batchedArgs.add(new BatchParams(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths, this.isNull));
    }
    
    public synchronized void addBatch(final String sql) throws SQLException {
        this.batchHasPlainStatements = true;
        super.addBatch(sql);
    }
    
    protected String asSql() throws SQLException {
        return this.asSql(false);
    }
    
    protected String asSql(final boolean quoteStreamsAndUnknowns) throws SQLException {
        if (this.isClosed) {
            return "statement has been closed, no further internal information available";
        }
        final StringBuffer buf = new StringBuffer();
        try {
            final int realParameterCount = this.parameterCount + this.getParameterIndexOffset();
            Object batchArg = null;
            if (this.batchCommandIndex != -1) {
                batchArg = this.batchedArgs.get(this.batchCommandIndex);
            }
            for (int i = 0; i < realParameterCount; ++i) {
                if (this.charEncoding != null) {
                    buf.append(new String(this.staticSqlStrings[i], this.charEncoding));
                }
                else {
                    buf.append(new String(this.staticSqlStrings[i]));
                }
                byte[] val = null;
                if (batchArg != null && batchArg instanceof String) {
                    buf.append((String)batchArg);
                }
                else {
                    if (this.batchCommandIndex == -1) {
                        val = this.parameterValues[i];
                    }
                    else {
                        val = ((BatchParams)batchArg).parameterStrings[i];
                    }
                    boolean isStreamParam = false;
                    if (this.batchCommandIndex == -1) {
                        isStreamParam = this.isStream[i];
                    }
                    else {
                        isStreamParam = ((BatchParams)batchArg).isStream[i];
                    }
                    if (val == null && !isStreamParam) {
                        if (quoteStreamsAndUnknowns) {
                            buf.append("'");
                        }
                        buf.append("** NOT SPECIFIED **");
                        if (quoteStreamsAndUnknowns) {
                            buf.append("'");
                        }
                    }
                    else if (isStreamParam) {
                        if (quoteStreamsAndUnknowns) {
                            buf.append("'");
                        }
                        buf.append("** STREAM DATA **");
                        if (quoteStreamsAndUnknowns) {
                            buf.append("'");
                        }
                    }
                    else if (this.charConverter != null) {
                        buf.append(this.charConverter.toString(val));
                    }
                    else if (this.charEncoding != null) {
                        buf.append(new String(val, this.charEncoding));
                    }
                    else {
                        buf.append(StringUtils.toAsciiString(val));
                    }
                }
            }
            if (this.charEncoding != null) {
                buf.append(new String(this.staticSqlStrings[this.parameterCount + this.getParameterIndexOffset()], this.charEncoding));
            }
            else {
                buf.append(StringUtils.toAsciiString(this.staticSqlStrings[this.parameterCount + this.getParameterIndexOffset()]));
            }
        }
        catch (UnsupportedEncodingException uue) {
            throw new RuntimeException(Messages.getString("PreparedStatement.32") + this.charEncoding + Messages.getString("PreparedStatement.33"));
        }
        return buf.toString();
    }
    
    public synchronized void clearBatch() throws SQLException {
        this.batchHasPlainStatements = false;
        super.clearBatch();
    }
    
    public synchronized void clearParameters() throws SQLException {
        this.checkClosed();
        for (int i = 0; i < this.parameterValues.length; ++i) {
            this.parameterValues[i] = null;
            this.parameterStreams[i] = null;
            this.isStream[i] = false;
            this.isNull[i] = false;
            this.parameterTypes[i] = 0;
        }
    }
    
    public synchronized void close() throws SQLException {
        this.realClose(true, true);
    }
    
    private final void escapeblockFast(final byte[] buf, final Buffer packet, final int size) throws SQLException {
        int lastwritten = 0;
        for (int i = 0; i < size; ++i) {
            final byte b = buf[i];
            if (b == 0) {
                if (i > lastwritten) {
                    packet.writeBytesNoNull(buf, lastwritten, i - lastwritten);
                }
                packet.writeByte((byte)92);
                packet.writeByte((byte)48);
                lastwritten = i + 1;
            }
            else if (b == 92 || b == 39 || (!this.usingAnsiMode && b == 34)) {
                if (i > lastwritten) {
                    packet.writeBytesNoNull(buf, lastwritten, i - lastwritten);
                }
                packet.writeByte((byte)92);
                lastwritten = i;
            }
        }
        if (lastwritten < size) {
            packet.writeBytesNoNull(buf, lastwritten, size - lastwritten);
        }
    }
    
    private final void escapeblockFast(final byte[] buf, final ByteArrayOutputStream bytesOut, final int size) {
        int lastwritten = 0;
        for (int i = 0; i < size; ++i) {
            final byte b = buf[i];
            if (b == 0) {
                if (i > lastwritten) {
                    bytesOut.write(buf, lastwritten, i - lastwritten);
                }
                bytesOut.write(92);
                bytesOut.write(48);
                lastwritten = i + 1;
            }
            else if (b == 92 || b == 39 || (!this.usingAnsiMode && b == 34)) {
                if (i > lastwritten) {
                    bytesOut.write(buf, lastwritten, i - lastwritten);
                }
                bytesOut.write(92);
                lastwritten = i;
            }
        }
        if (lastwritten < size) {
            bytesOut.write(buf, lastwritten, size - lastwritten);
        }
    }
    
    protected boolean checkReadOnlySafeStatement() throws SQLException {
        return !this.connection.isReadOnly() || this.firstCharOfStmt == 'S';
    }
    
    public boolean execute() throws SQLException {
        this.checkClosed();
        final MySQLConnection locallyScopedConn = this.connection;
        if (!this.checkReadOnlySafeStatement()) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.20") + Messages.getString("PreparedStatement.21"), "S1009", this.getExceptionInterceptor());
        }
        ResultSetInternalMethods rs = null;
        CachedResultSetMetaData cachedMetadata = null;
        synchronized (locallyScopedConn.getMutex()) {
            this.lastQueryIsOnDupKeyUpdate = false;
            if (this.retrieveGeneratedKeys) {
                this.lastQueryIsOnDupKeyUpdate = this.containsOnDuplicateKeyUpdateInSQL();
            }
            final boolean doStreaming = this.createStreamingResultSet();
            this.clearWarnings();
            if (doStreaming && this.connection.getNetTimeoutForStreamingResults() > 0) {
                this.executeSimpleNonQuery(locallyScopedConn, "SET net_write_timeout=" + this.connection.getNetTimeoutForStreamingResults());
            }
            this.batchedGeneratedKeys = null;
            final Buffer sendPacket = this.fillSendPacket();
            String oldCatalog = null;
            if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
                oldCatalog = locallyScopedConn.getCatalog();
                locallyScopedConn.setCatalog(this.currentCatalog);
            }
            if (locallyScopedConn.getCacheResultSetMetadata()) {
                cachedMetadata = locallyScopedConn.getCachedMetaData(this.originalSql);
            }
            Field[] metadataFromCache = null;
            if (cachedMetadata != null) {
                metadataFromCache = cachedMetadata.fields;
            }
            boolean oldInfoMsgState = false;
            if (this.retrieveGeneratedKeys) {
                oldInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled();
                locallyScopedConn.setReadInfoMsgEnabled(true);
            }
            if (locallyScopedConn.useMaxRows()) {
                int rowLimit = -1;
                if (this.firstCharOfStmt == 'S') {
                    if (this.hasLimitClause) {
                        rowLimit = this.maxRows;
                    }
                    else if (this.maxRows <= 0) {
                        this.executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT");
                    }
                    else {
                        this.executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=" + this.maxRows);
                    }
                }
                else {
                    this.executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT");
                }
                rs = this.executeInternal(rowLimit, sendPacket, doStreaming, this.firstCharOfStmt == 'S', metadataFromCache, false);
            }
            else {
                rs = this.executeInternal(-1, sendPacket, doStreaming, this.firstCharOfStmt == 'S', metadataFromCache, false);
            }
            if (cachedMetadata != null) {
                locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, cachedMetadata, this.results);
            }
            else if (rs.reallyResult() && locallyScopedConn.getCacheResultSetMetadata()) {
                locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, null, rs);
            }
            if (this.retrieveGeneratedKeys) {
                locallyScopedConn.setReadInfoMsgEnabled(oldInfoMsgState);
                rs.setFirstCharOfQuery(this.firstCharOfStmt);
            }
            if (oldCatalog != null) {
                locallyScopedConn.setCatalog(oldCatalog);
            }
            if (rs != null) {
                this.lastInsertId = rs.getUpdateID();
                this.results = rs;
            }
        }
        return rs != null && rs.reallyResult();
    }
    
    public int[] executeBatch() throws SQLException {
        this.checkClosed();
        if (this.connection.isReadOnly()) {
            throw new SQLException(Messages.getString("PreparedStatement.25") + Messages.getString("PreparedStatement.26"), "S1009");
        }
        synchronized (this.connection.getMutex()) {
            if (this.batchedArgs == null || this.batchedArgs.size() == 0) {
                return new int[0];
            }
            final int batchTimeout = this.timeoutInMillis;
            this.timeoutInMillis = 0;
            this.resetCancelledState();
            try {
                this.clearWarnings();
                if (!this.batchHasPlainStatements && this.connection.getRewriteBatchedStatements()) {
                    if (this.canRewriteAsMultiValueInsertAtSqlLevel()) {
                        return this.executeBatchedInserts(batchTimeout);
                    }
                    if (this.connection.versionMeetsMinimum(4, 1, 0) && !this.batchHasPlainStatements && this.batchedArgs != null && this.batchedArgs.size() > 3) {
                        return this.executePreparedBatchAsMultiStatement(batchTimeout);
                    }
                }
                return this.executeBatchSerially(batchTimeout);
            }
            finally {
                this.clearBatch();
            }
        }
    }
    
    public boolean canRewriteAsMultiValueInsertAtSqlLevel() throws SQLException {
        return this.parseInfo.canRewriteAsMultiValueInsert;
    }
    
    protected int getLocationOfOnDuplicateKeyUpdate() {
        return this.parseInfo.locationOfOnDuplicateKeyUpdate;
    }
    
    protected int[] executePreparedBatchAsMultiStatement(final int batchTimeout) throws SQLException {
        synchronized (this.connection.getMutex()) {
            if (this.batchedValuesClause == null) {
                this.batchedValuesClause = this.originalSql + ";";
            }
            final MySQLConnection locallyScopedConn = this.connection;
            final boolean multiQueriesEnabled = locallyScopedConn.getAllowMultiQueries();
            CancelTask timeoutTask = null;
            try {
                this.clearWarnings();
                final int numBatchedArgs = this.batchedArgs.size();
                if (this.retrieveGeneratedKeys) {
                    this.batchedGeneratedKeys = new ArrayList(numBatchedArgs);
                }
                int numValuesPerBatch = this.computeBatchSize(numBatchedArgs);
                if (numBatchedArgs < numValuesPerBatch) {
                    numValuesPerBatch = numBatchedArgs;
                }
                java.sql.PreparedStatement batchedStatement = null;
                int batchedParamIndex = 1;
                int numberToExecuteAsMultiValue = 0;
                int batchCounter = 0;
                int updateCountCounter = 0;
                final int[] updateCounts = new int[numBatchedArgs];
                SQLException sqlEx = null;
                try {
                    if (!multiQueriesEnabled) {
                        locallyScopedConn.getIO().enableMultiQueries();
                    }
                    if (this.retrieveGeneratedKeys) {
                        batchedStatement = locallyScopedConn.prepareStatement(this.generateMultiStatementForBatch(numValuesPerBatch), 1);
                    }
                    else {
                        batchedStatement = locallyScopedConn.prepareStatement(this.generateMultiStatementForBatch(numValuesPerBatch));
                    }
                    if (locallyScopedConn.getEnableQueryTimeouts() && batchTimeout != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) {
                        timeoutTask = new CancelTask(this, (StatementImpl)batchedStatement);
                        locallyScopedConn.getCancelTimer().schedule(timeoutTask, batchTimeout);
                    }
                    if (numBatchedArgs < numValuesPerBatch) {
                        numberToExecuteAsMultiValue = numBatchedArgs;
                    }
                    else {
                        numberToExecuteAsMultiValue = numBatchedArgs / numValuesPerBatch;
                    }
                    for (int numberArgsToExecute = numberToExecuteAsMultiValue * numValuesPerBatch, i = 0; i < numberArgsToExecute; ++i) {
                        if (i != 0 && i % numValuesPerBatch == 0) {
                            try {
                                batchedStatement.execute();
                            }
                            catch (SQLException ex) {
                                sqlEx = this.handleExceptionForBatch(batchCounter, numValuesPerBatch, updateCounts, ex);
                            }
                            updateCountCounter = this.processMultiCountsAndKeys((StatementImpl)batchedStatement, updateCountCounter, updateCounts);
                            batchedStatement.clearParameters();
                            batchedParamIndex = 1;
                        }
                        batchedParamIndex = this.setOneBatchedParameterSet(batchedStatement, batchedParamIndex, this.batchedArgs.get(batchCounter++));
                    }
                    try {
                        batchedStatement.execute();
                    }
                    catch (SQLException ex2) {
                        sqlEx = this.handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, ex2);
                    }
                    updateCountCounter = this.processMultiCountsAndKeys((StatementImpl)batchedStatement, updateCountCounter, updateCounts);
                    batchedStatement.clearParameters();
                    numValuesPerBatch = numBatchedArgs - batchCounter;
                }
                finally {
                    if (batchedStatement != null) {
                        batchedStatement.close();
                    }
                }
                try {
                    if (numValuesPerBatch > 0) {
                        if (this.retrieveGeneratedKeys) {
                            batchedStatement = locallyScopedConn.prepareStatement(this.generateMultiStatementForBatch(numValuesPerBatch), 1);
                        }
                        else {
                            batchedStatement = locallyScopedConn.prepareStatement(this.generateMultiStatementForBatch(numValuesPerBatch));
                        }
                        if (timeoutTask != null) {
                            timeoutTask.toCancel = (StatementImpl)batchedStatement;
                        }
                        for (batchedParamIndex = 1; batchCounter < numBatchedArgs; batchedParamIndex = this.setOneBatchedParameterSet(batchedStatement, batchedParamIndex, this.batchedArgs.get(batchCounter++))) {}
                        try {
                            batchedStatement.execute();
                        }
                        catch (SQLException ex3) {
                            sqlEx = this.handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, ex3);
                        }
                        updateCountCounter = this.processMultiCountsAndKeys((StatementImpl)batchedStatement, updateCountCounter, updateCounts);
                        batchedStatement.clearParameters();
                    }
                    if (timeoutTask != null) {
                        if (timeoutTask.caughtWhileCancelling != null) {
                            throw timeoutTask.caughtWhileCancelling;
                        }
                        timeoutTask.cancel();
                        locallyScopedConn.getCancelTimer().purge();
                        timeoutTask = null;
                    }
                    if (sqlEx != null) {
                        throw new BatchUpdateException(sqlEx.getMessage(), sqlEx.getSQLState(), sqlEx.getErrorCode(), updateCounts);
                    }
                    return updateCounts;
                }
                finally {
                    if (batchedStatement != null) {
                        batchedStatement.close();
                    }
                }
            }
            finally {
                if (timeoutTask != null) {
                    timeoutTask.cancel();
                    locallyScopedConn.getCancelTimer().purge();
                }
                this.resetCancelledState();
                if (!multiQueriesEnabled) {
                    locallyScopedConn.getIO().disableMultiQueries();
                }
                this.clearBatch();
            }
        }
    }
    
    private String generateMultiStatementForBatch(final int numBatches) {
        final StringBuffer newStatementSql = new StringBuffer((this.originalSql.length() + 1) * numBatches);
        newStatementSql.append(this.originalSql);
        for (int i = 0; i < numBatches - 1; ++i) {
            newStatementSql.append(';');
            newStatementSql.append(this.originalSql);
        }
        return newStatementSql.toString();
    }
    
    protected int[] executeBatchedInserts(final int batchTimeout) throws SQLException {
        final String valuesClause = this.getValuesClause();
        final MySQLConnection locallyScopedConn = this.connection;
        if (valuesClause == null) {
            return this.executeBatchSerially(batchTimeout);
        }
        final int numBatchedArgs = this.batchedArgs.size();
        if (this.retrieveGeneratedKeys) {
            this.batchedGeneratedKeys = new ArrayList(numBatchedArgs);
        }
        int numValuesPerBatch = this.computeBatchSize(numBatchedArgs);
        if (numBatchedArgs < numValuesPerBatch) {
            numValuesPerBatch = numBatchedArgs;
        }
        java.sql.PreparedStatement batchedStatement = null;
        int batchedParamIndex = 1;
        int updateCountRunningTotal = 0;
        int numberToExecuteAsMultiValue = 0;
        int batchCounter = 0;
        CancelTask timeoutTask = null;
        SQLException sqlEx = null;
        final int[] updateCounts = new int[numBatchedArgs];
        for (int i = 0; i < this.batchedArgs.size(); ++i) {
            updateCounts[i] = 1;
        }
        try {
            try {
                batchedStatement = this.prepareBatchedInsertSQL(locallyScopedConn, numValuesPerBatch);
                if (locallyScopedConn.getEnableQueryTimeouts() && batchTimeout != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) {
                    timeoutTask = new CancelTask(this, (StatementImpl)batchedStatement);
                    locallyScopedConn.getCancelTimer().schedule(timeoutTask, batchTimeout);
                }
                if (numBatchedArgs < numValuesPerBatch) {
                    numberToExecuteAsMultiValue = numBatchedArgs;
                }
                else {
                    numberToExecuteAsMultiValue = numBatchedArgs / numValuesPerBatch;
                }
                for (int numberArgsToExecute = numberToExecuteAsMultiValue * numValuesPerBatch, j = 0; j < numberArgsToExecute; ++j) {
                    if (j != 0 && j % numValuesPerBatch == 0) {
                        try {
                            updateCountRunningTotal += batchedStatement.executeUpdate();
                        }
                        catch (SQLException ex) {
                            sqlEx = this.handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, ex);
                        }
                        this.getBatchedGeneratedKeys(batchedStatement);
                        batchedStatement.clearParameters();
                        batchedParamIndex = 1;
                    }
                    batchedParamIndex = this.setOneBatchedParameterSet(batchedStatement, batchedParamIndex, this.batchedArgs.get(batchCounter++));
                }
                try {
                    updateCountRunningTotal += batchedStatement.executeUpdate();
                }
                catch (SQLException ex2) {
                    sqlEx = this.handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, ex2);
                }
                this.getBatchedGeneratedKeys(batchedStatement);
                numValuesPerBatch = numBatchedArgs - batchCounter;
            }
            finally {
                if (batchedStatement != null) {
                    batchedStatement.close();
                }
            }
            try {
                if (numValuesPerBatch > 0) {
                    batchedStatement = this.prepareBatchedInsertSQL(locallyScopedConn, numValuesPerBatch);
                    if (timeoutTask != null) {
                        timeoutTask.toCancel = (StatementImpl)batchedStatement;
                    }
                    for (batchedParamIndex = 1; batchCounter < numBatchedArgs; batchedParamIndex = this.setOneBatchedParameterSet(batchedStatement, batchedParamIndex, this.batchedArgs.get(batchCounter++))) {}
                    try {
                        updateCountRunningTotal += batchedStatement.executeUpdate();
                    }
                    catch (SQLException ex3) {
                        sqlEx = this.handleExceptionForBatch(batchCounter - 1, numValuesPerBatch, updateCounts, ex3);
                    }
                    this.getBatchedGeneratedKeys(batchedStatement);
                }
                if (sqlEx != null) {
                    throw new BatchUpdateException(sqlEx.getMessage(), sqlEx.getSQLState(), sqlEx.getErrorCode(), updateCounts);
                }
                return updateCounts;
            }
            finally {
                if (batchedStatement != null) {
                    batchedStatement.close();
                }
            }
        }
        finally {
            if (timeoutTask != null) {
                timeoutTask.cancel();
                locallyScopedConn.getCancelTimer().purge();
            }
            this.resetCancelledState();
        }
    }
    
    protected String getValuesClause() throws SQLException {
        return this.parseInfo.valuesClause;
    }
    
    protected int computeBatchSize(final int numBatchedArgs) throws SQLException {
        final long[] combinedValues = this.computeMaxParameterSetSizeAndBatchSize(numBatchedArgs);
        final long maxSizeOfParameterSet = combinedValues[0];
        final long sizeOfEntireBatch = combinedValues[1];
        final int maxAllowedPacket = this.connection.getMaxAllowedPacket();
        if (sizeOfEntireBatch < maxAllowedPacket - this.originalSql.length()) {
            return numBatchedArgs;
        }
        return (int)Math.max(1L, (maxAllowedPacket - this.originalSql.length()) / maxSizeOfParameterSet);
    }
    
    protected long[] computeMaxParameterSetSizeAndBatchSize(final int numBatchedArgs) throws SQLException {
        long sizeOfEntireBatch = 0L;
        long maxSizeOfParameterSet = 0L;
        for (int i = 0; i < numBatchedArgs; ++i) {
            final BatchParams paramArg = this.batchedArgs.get(i);
            final boolean[] isNullBatch = paramArg.isNull;
            final boolean[] isStreamBatch = paramArg.isStream;
            long sizeOfParameterSet = 0L;
            for (int j = 0; j < isNullBatch.length; ++j) {
                if (!isNullBatch[j]) {
                    if (isStreamBatch[j]) {
                        final int streamLength = paramArg.streamLengths[j];
                        if (streamLength != -1) {
                            sizeOfParameterSet += streamLength * 2;
                        }
                        else {
                            final int paramLength = paramArg.parameterStrings[j].length;
                            sizeOfParameterSet += paramLength;
                        }
                    }
                    else {
                        sizeOfParameterSet += paramArg.parameterStrings[j].length;
                    }
                }
                else {
                    sizeOfParameterSet += 4L;
                }
            }
            if (this.getValuesClause() != null) {
                sizeOfParameterSet += this.getValuesClause().length() + 1;
            }
            else {
                sizeOfParameterSet += this.originalSql.length() + 1;
            }
            sizeOfEntireBatch += sizeOfParameterSet;
            if (sizeOfParameterSet > maxSizeOfParameterSet) {
                maxSizeOfParameterSet = sizeOfParameterSet;
            }
        }
        return new long[] { maxSizeOfParameterSet, sizeOfEntireBatch };
    }
    
    protected int[] executeBatchSerially(final int batchTimeout) throws SQLException {
        final MySQLConnection locallyScopedConn = this.connection;
        if (locallyScopedConn == null) {
            this.checkClosed();
        }
        int[] updateCounts = null;
        if (this.batchedArgs != null) {
            final int nbrCommands = this.batchedArgs.size();
            updateCounts = new int[nbrCommands];
            for (int i = 0; i < nbrCommands; ++i) {
                updateCounts[i] = -3;
            }
            SQLException sqlEx = null;
            CancelTask timeoutTask = null;
            try {
                if (locallyScopedConn.getEnableQueryTimeouts() && batchTimeout != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) {
                    timeoutTask = new CancelTask(this, this);
                    locallyScopedConn.getCancelTimer().schedule(timeoutTask, batchTimeout);
                }
                if (this.retrieveGeneratedKeys) {
                    this.batchedGeneratedKeys = new ArrayList(nbrCommands);
                }
                this.batchCommandIndex = 0;
                while (this.batchCommandIndex < nbrCommands) {
                    final Object arg = this.batchedArgs.get(this.batchCommandIndex);
                    if (arg instanceof String) {
                        updateCounts[this.batchCommandIndex] = this.executeUpdate((String)arg);
                    }
                    else {
                        final BatchParams paramArg = (BatchParams)arg;
                        try {
                            updateCounts[this.batchCommandIndex] = this.executeUpdate(paramArg.parameterStrings, paramArg.parameterStreams, paramArg.isStream, paramArg.streamLengths, paramArg.isNull, true);
                            if (this.retrieveGeneratedKeys) {
                                ResultSet rs = null;
                                try {
                                    if (this.containsOnDuplicateKeyUpdateInSQL()) {
                                        rs = this.getGeneratedKeysInternal(1);
                                    }
                                    else {
                                        rs = this.getGeneratedKeysInternal();
                                    }
                                    while (rs.next()) {
                                        this.batchedGeneratedKeys.add(new ByteArrayRow(new byte[][] { rs.getBytes(1) }, this.getExceptionInterceptor()));
                                    }
                                }
                                finally {
                                    if (rs != null) {
                                        rs.close();
                                    }
                                }
                            }
                        }
                        catch (SQLException ex) {
                            updateCounts[this.batchCommandIndex] = -3;
                            if (!this.continueBatchOnError || ex instanceof MySQLTimeoutException || ex instanceof MySQLStatementCancelledException || this.hasDeadlockOrTimeoutRolledBackTx(ex)) {
                                final int[] newUpdateCounts = new int[this.batchCommandIndex];
                                System.arraycopy(updateCounts, 0, newUpdateCounts, 0, this.batchCommandIndex);
                                throw new BatchUpdateException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), newUpdateCounts);
                            }
                            sqlEx = ex;
                        }
                    }
                    ++this.batchCommandIndex;
                }
                if (sqlEx != null) {
                    throw new BatchUpdateException(sqlEx.getMessage(), sqlEx.getSQLState(), sqlEx.getErrorCode(), updateCounts);
                }
            }
            catch (NullPointerException npe) {
                try {
                    this.checkClosed();
                }
                catch (SQLException connectionClosedEx) {
                    updateCounts[this.batchCommandIndex] = -3;
                    final int[] newUpdateCounts2 = new int[this.batchCommandIndex];
                    System.arraycopy(updateCounts, 0, newUpdateCounts2, 0, this.batchCommandIndex);
                    throw new BatchUpdateException(connectionClosedEx.getMessage(), connectionClosedEx.getSQLState(), connectionClosedEx.getErrorCode(), newUpdateCounts2);
                }
                throw npe;
            }
            finally {
                this.batchCommandIndex = -1;
                if (timeoutTask != null) {
                    timeoutTask.cancel();
                    locallyScopedConn.getCancelTimer().purge();
                }
                this.resetCancelledState();
            }
        }
        return (updateCounts != null) ? updateCounts : new int[0];
    }
    
    public String getDateTime(final String pattern) {
        final SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }
    
    protected ResultSetInternalMethods executeInternal(final int maxRowsToRetrieve, final Buffer sendPacket, final boolean createStreamingResultSet, final boolean queryIsSelectOnly, final Field[] metadataFromCache, final boolean isBatch) throws SQLException {
        try {
            this.resetCancelledState();
            final MySQLConnection locallyScopedConnection = this.connection;
            ++this.numberOfExecutions;
            if (this.doPingInstead) {
                this.doPingInstead();
                return this.results;
            }
            CancelTask timeoutTask = null;
            ResultSetInternalMethods rs;
            try {
                if (locallyScopedConnection.getEnableQueryTimeouts() && this.timeoutInMillis != 0 && locallyScopedConnection.versionMeetsMinimum(5, 0, 0)) {
                    timeoutTask = new CancelTask(this, this);
                    locallyScopedConnection.getCancelTimer().schedule(timeoutTask, this.timeoutInMillis);
                }
                rs = locallyScopedConnection.execSQL(this, null, maxRowsToRetrieve, sendPacket, this.resultSetType, this.resultSetConcurrency, createStreamingResultSet, this.currentCatalog, metadataFromCache, isBatch);
                if (timeoutTask != null) {
                    timeoutTask.cancel();
                    locallyScopedConnection.getCancelTimer().purge();
                    if (timeoutTask.caughtWhileCancelling != null) {
                        throw timeoutTask.caughtWhileCancelling;
                    }
                    timeoutTask = null;
                }
                synchronized (this.cancelTimeoutMutex) {
                    if (this.wasCancelled) {
                        SQLException cause = null;
                        if (this.wasCancelledByTimeout) {
                            cause = new MySQLTimeoutException();
                        }
                        else {
                            cause = new MySQLStatementCancelledException();
                        }
                        this.resetCancelledState();
                        throw cause;
                    }
                }
            }
            finally {
                if (timeoutTask != null) {
                    timeoutTask.cancel();
                    locallyScopedConnection.getCancelTimer().purge();
                }
            }
            return rs;
        }
        catch (NullPointerException npe) {
            this.checkClosed();
            throw npe;
        }
    }
    
    public ResultSet executeQuery() throws SQLException {
        this.checkClosed();
        final MySQLConnection locallyScopedConn = this.connection;
        this.checkForDml(this.originalSql, this.firstCharOfStmt);
        CachedResultSetMetaData cachedMetadata = null;
        synchronized (locallyScopedConn.getMutex()) {
            this.clearWarnings();
            final boolean doStreaming = this.createStreamingResultSet();
            this.batchedGeneratedKeys = null;
            if (doStreaming && this.connection.getNetTimeoutForStreamingResults() > 0) {
                Statement stmt = null;
                try {
                    stmt = this.connection.createStatement();
                    ((StatementImpl)stmt).executeSimpleNonQuery(this.connection, "SET net_write_timeout=" + this.connection.getNetTimeoutForStreamingResults());
                }
                finally {
                    if (stmt != null) {
                        stmt.close();
                    }
                }
            }
            final Buffer sendPacket = this.fillSendPacket();
            if (this.results != null && !this.connection.getHoldResultsOpenOverStatementClose() && !this.holdResultsOpenOverClose) {
                this.results.realClose(false);
            }
            String oldCatalog = null;
            if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
                oldCatalog = locallyScopedConn.getCatalog();
                locallyScopedConn.setCatalog(this.currentCatalog);
            }
            if (locallyScopedConn.getCacheResultSetMetadata()) {
                cachedMetadata = locallyScopedConn.getCachedMetaData(this.originalSql);
            }
            Field[] metadataFromCache = null;
            if (cachedMetadata != null) {
                metadataFromCache = cachedMetadata.fields;
            }
            if (locallyScopedConn.useMaxRows()) {
                if (this.hasLimitClause) {
                    this.results = this.executeInternal(this.maxRows, sendPacket, this.createStreamingResultSet(), true, metadataFromCache, false);
                }
                else {
                    if (this.maxRows <= 0) {
                        this.executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT");
                    }
                    else {
                        this.executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=" + this.maxRows);
                    }
                    this.results = this.executeInternal(-1, sendPacket, doStreaming, true, metadataFromCache, false);
                    if (oldCatalog != null) {
                        this.connection.setCatalog(oldCatalog);
                    }
                }
            }
            else {
                this.results = this.executeInternal(-1, sendPacket, doStreaming, true, metadataFromCache, false);
            }
            if (oldCatalog != null) {
                locallyScopedConn.setCatalog(oldCatalog);
            }
            if (cachedMetadata != null) {
                locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, cachedMetadata, this.results);
            }
            else if (locallyScopedConn.getCacheResultSetMetadata()) {
                locallyScopedConn.initializeResultsMetadataFromCache(this.originalSql, null, this.results);
            }
        }
        this.lastInsertId = this.results.getUpdateID();
        return this.results;
    }
    
    public int executeUpdate() throws SQLException {
        return this.executeUpdate(true, false);
    }
    
    protected int executeUpdate(final boolean clearBatchedGeneratedKeysAndWarnings, final boolean isBatch) throws SQLException {
        if (clearBatchedGeneratedKeysAndWarnings) {
            this.clearWarnings();
            this.batchedGeneratedKeys = null;
        }
        return this.executeUpdate(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths, this.isNull, isBatch);
    }
    
    protected int executeUpdate(final byte[][] batchedParameterStrings, final InputStream[] batchedParameterStreams, final boolean[] batchedIsStream, final int[] batchedStreamLengths, final boolean[] batchedIsNull, final boolean isReallyBatch) throws SQLException {
        this.checkClosed();
        final MySQLConnection locallyScopedConn = this.connection;
        if (locallyScopedConn.isReadOnly()) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.34") + Messages.getString("PreparedStatement.35"), "S1009", this.getExceptionInterceptor());
        }
        if (this.firstCharOfStmt == 'S' && this.isSelectQuery()) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.37"), "01S03", this.getExceptionInterceptor());
        }
        if (this.results != null && !locallyScopedConn.getHoldResultsOpenOverStatementClose()) {
            this.results.realClose(false);
        }
        ResultSetInternalMethods rs = null;
        synchronized (locallyScopedConn.getMutex()) {
            final Buffer sendPacket = this.fillSendPacket(batchedParameterStrings, batchedParameterStreams, batchedIsStream, batchedStreamLengths);
            String oldCatalog = null;
            if (!locallyScopedConn.getCatalog().equals(this.currentCatalog)) {
                oldCatalog = locallyScopedConn.getCatalog();
                locallyScopedConn.setCatalog(this.currentCatalog);
            }
            if (locallyScopedConn.useMaxRows()) {
                this.executeSimpleNonQuery(locallyScopedConn, "SET OPTION SQL_SELECT_LIMIT=DEFAULT");
            }
            boolean oldInfoMsgState = false;
            if (this.retrieveGeneratedKeys) {
                oldInfoMsgState = locallyScopedConn.isReadInfoMsgEnabled();
                locallyScopedConn.setReadInfoMsgEnabled(true);
            }
            rs = this.executeInternal(-1, sendPacket, false, false, null, isReallyBatch);
            if (this.retrieveGeneratedKeys) {
                locallyScopedConn.setReadInfoMsgEnabled(oldInfoMsgState);
                rs.setFirstCharOfQuery(this.firstCharOfStmt);
            }
            if (oldCatalog != null) {
                locallyScopedConn.setCatalog(oldCatalog);
            }
        }
        this.results = rs;
        this.updateCount = rs.getUpdateCount();
        if (this.containsOnDuplicateKeyUpdateInSQL() && this.compensateForOnDuplicateKeyUpdate && (this.updateCount == 2L || this.updateCount == 0L)) {
            this.updateCount = 1L;
        }
        int truncatedUpdateCount = 0;
        if (this.updateCount > 2147483647L) {
            truncatedUpdateCount = Integer.MAX_VALUE;
        }
        else {
            truncatedUpdateCount = (int)this.updateCount;
        }
        this.lastInsertId = rs.getUpdateID();
        return truncatedUpdateCount;
    }
    
    protected boolean containsOnDuplicateKeyUpdateInSQL() {
        return this.parseInfo.isOnDuplicateKeyUpdate;
    }
    
    protected Buffer fillSendPacket() throws SQLException {
        return this.fillSendPacket(this.parameterValues, this.parameterStreams, this.isStream, this.streamLengths);
    }
    
    protected Buffer fillSendPacket(final byte[][] batchedParameterStrings, final InputStream[] batchedParameterStreams, final boolean[] batchedIsStream, final int[] batchedStreamLengths) throws SQLException {
        final Buffer sendPacket = this.connection.getIO().getSharedSendPacket();
        sendPacket.clear();
        sendPacket.writeByte((byte)3);
        final boolean useStreamLengths = this.connection.getUseStreamLengthsInPrepStmts();
        int ensurePacketSize = 0;
        final String statementComment = this.connection.getStatementComment();
        byte[] commentAsBytes = null;
        if (statementComment != null) {
            if (this.charConverter != null) {
                commentAsBytes = this.charConverter.toBytes(statementComment);
            }
            else {
                commentAsBytes = StringUtils.getBytes(statementComment, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.getExceptionInterceptor());
            }
            ensurePacketSize += commentAsBytes.length;
            ensurePacketSize += 6;
        }
        for (int i = 0; i < batchedParameterStrings.length; ++i) {
            if (batchedIsStream[i] && useStreamLengths) {
                ensurePacketSize += batchedStreamLengths[i];
            }
        }
        if (ensurePacketSize != 0) {
            sendPacket.ensureCapacity(ensurePacketSize);
        }
        if (commentAsBytes != null) {
            sendPacket.writeBytesNoNull(Constants.SLASH_STAR_SPACE_AS_BYTES);
            sendPacket.writeBytesNoNull(commentAsBytes);
            sendPacket.writeBytesNoNull(Constants.SPACE_STAR_SLASH_SPACE_AS_BYTES);
        }
        for (int i = 0; i < batchedParameterStrings.length; ++i) {
            this.checkAllParametersSet(batchedParameterStrings[i], batchedParameterStreams[i], i);
            sendPacket.writeBytesNoNull(this.staticSqlStrings[i]);
            if (batchedIsStream[i]) {
                this.streamToBytes(sendPacket, batchedParameterStreams[i], true, batchedStreamLengths[i], useStreamLengths);
            }
            else {
                sendPacket.writeBytesNoNull(batchedParameterStrings[i]);
            }
        }
        sendPacket.writeBytesNoNull(this.staticSqlStrings[batchedParameterStrings.length]);
        return sendPacket;
    }
    
    private void checkAllParametersSet(final byte[] parameterString, final InputStream parameterStream, final int columnIndex) throws SQLException {
        if (parameterString == null && parameterStream == null) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.40") + (columnIndex + 1), "07001", this.getExceptionInterceptor());
        }
    }
    
    protected PreparedStatement prepareBatchedInsertSQL(final MySQLConnection localConn, final int numBatches) throws SQLException {
        final PreparedStatement pstmt = new PreparedStatement(localConn, "Rewritten batch of: " + this.originalSql, this.currentCatalog, this.parseInfo.getParseInfoForBatch(numBatches));
        pstmt.setRetrieveGeneratedKeys(this.retrieveGeneratedKeys);
        pstmt.rewrittenBatchSize = numBatches;
        return pstmt;
    }
    
    public int getRewrittenBatchSize() {
        return this.rewrittenBatchSize;
    }
    
    public String getNonRewrittenSql() {
        final int indexOfBatch = this.originalSql.indexOf(" of: ");
        if (indexOfBatch != -1) {
            return this.originalSql.substring(indexOfBatch + 5);
        }
        return this.originalSql;
    }
    
    public byte[] getBytesRepresentation(final int parameterIndex) throws SQLException {
        if (this.isStream[parameterIndex]) {
            return this.streamToBytes(this.parameterStreams[parameterIndex], false, this.streamLengths[parameterIndex], this.connection.getUseStreamLengthsInPrepStmts());
        }
        final byte[] parameterVal = this.parameterValues[parameterIndex];
        if (parameterVal == null) {
            return null;
        }
        if (parameterVal[0] == 39 && parameterVal[parameterVal.length - 1] == 39) {
            final byte[] valNoQuotes = new byte[parameterVal.length - 2];
            System.arraycopy(parameterVal, 1, valNoQuotes, 0, parameterVal.length - 2);
            return valNoQuotes;
        }
        return parameterVal;
    }
    
    protected byte[] getBytesRepresentationForBatch(final int parameterIndex, final int commandIndex) throws SQLException {
        final Object batchedArg = this.batchedArgs.get(commandIndex);
        if (batchedArg instanceof String) {
            try {
                return ((String)batchedArg).getBytes(this.charEncoding);
            }
            catch (UnsupportedEncodingException uue) {
                throw new RuntimeException(Messages.getString("PreparedStatement.32") + this.charEncoding + Messages.getString("PreparedStatement.33"));
            }
        }
        final BatchParams params = (BatchParams)batchedArg;
        if (params.isStream[parameterIndex]) {
            return this.streamToBytes(params.parameterStreams[parameterIndex], false, params.streamLengths[parameterIndex], this.connection.getUseStreamLengthsInPrepStmts());
        }
        final byte[] parameterVal = params.parameterStrings[parameterIndex];
        if (parameterVal == null) {
            return null;
        }
        if (parameterVal[0] == 39 && parameterVal[parameterVal.length - 1] == 39) {
            final byte[] valNoQuotes = new byte[parameterVal.length - 2];
            System.arraycopy(parameterVal, 1, valNoQuotes, 0, parameterVal.length - 2);
            return valNoQuotes;
        }
        return parameterVal;
    }
    
    private final String getDateTimePattern(final String dt, final boolean toTime) throws Exception {
        final int dtLength = (dt != null) ? dt.length() : 0;
        if (dtLength >= 8 && dtLength <= 10) {
            int dashCount = 0;
            boolean isDateOnly = true;
            for (int i = 0; i < dtLength; ++i) {
                final char c = dt.charAt(i);
                if (!Character.isDigit(c) && c != '-') {
                    isDateOnly = false;
                    break;
                }
                if (c == '-') {
                    ++dashCount;
                }
            }
            if (isDateOnly && dashCount == 2) {
                return "yyyy-MM-dd";
            }
        }
        boolean colonsOnly = true;
        for (int j = 0; j < dtLength; ++j) {
            final char c2 = dt.charAt(j);
            if (!Character.isDigit(c2) && c2 != ':') {
                colonsOnly = false;
                break;
            }
        }
        if (colonsOnly) {
            return "HH:mm:ss";
        }
        final StringReader reader = new StringReader(dt + " ");
        final ArrayList vec = new ArrayList();
        final ArrayList vecRemovelist = new ArrayList();
        Object[] nv = { Constants.characterValueOf('y'), new StringBuffer(), Constants.integerValueOf(0) };
        vec.add(nv);
        if (toTime) {
            nv = new Object[] { Constants.characterValueOf('h'), new StringBuffer(), Constants.integerValueOf(0) };
            vec.add(nv);
        }
        int z;
        while ((z = reader.read()) != -1) {
            final char separator = (char)z;
            for (int maxvecs = vec.size(), count = 0; count < maxvecs; ++count) {
                final Object[] v = vec.get(count);
                final int n = (int)v[2];
                char c3 = this.getSuccessor((char)v[0], n);
                if (!Character.isLetterOrDigit(separator)) {
                    if (c3 == (char)v[0] && c3 != 'S') {
                        vecRemovelist.add(v);
                    }
                    else {
                        ((StringBuffer)v[1]).append(separator);
                        if (c3 == 'X' || c3 == 'Y') {
                            v[2] = Constants.integerValueOf(4);
                        }
                    }
                }
                else {
                    if (c3 == 'X') {
                        c3 = 'y';
                        nv = new Object[] { Constants.characterValueOf('M'), new StringBuffer(((StringBuffer)v[1]).toString()).append('M'), Constants.integerValueOf(1) };
                        vec.add(nv);
                    }
                    else if (c3 == 'Y') {
                        c3 = 'M';
                        nv = new Object[] { Constants.characterValueOf('d'), new StringBuffer(((StringBuffer)v[1]).toString()).append('d'), Constants.integerValueOf(1) };
                        vec.add(nv);
                    }
                    ((StringBuffer)v[1]).append(c3);
                    if (c3 == (char)v[0]) {
                        v[2] = Constants.integerValueOf(n + 1);
                    }
                    else {
                        v[0] = Constants.characterValueOf(c3);
                        v[2] = Constants.integerValueOf(1);
                    }
                }
            }
            for (int size = vecRemovelist.size(), k = 0; k < size; ++k) {
                final Object[] v = vecRemovelist.get(k);
                vec.remove(v);
            }
            vecRemovelist.clear();
        }
        for (int size = vec.size(), k = 0; k < size; ++k) {
            final Object[] v = vec.get(k);
            final char c3 = (char)v[0];
            final int n = (int)v[2];
            final boolean bk = this.getSuccessor(c3, n) != c3;
            final boolean atEnd = (c3 == 's' || c3 == 'm' || (c3 == 'h' && toTime)) && bk;
            final boolean finishesAtDate = bk && c3 == 'd' && !toTime;
            final boolean containsEnd = ((StringBuffer)v[1]).toString().indexOf(87) != -1;
            if ((!atEnd && !finishesAtDate) || containsEnd) {
                vecRemovelist.add(v);
            }
        }
        for (int size = vecRemovelist.size(), k = 0; k < size; ++k) {
            vec.remove(vecRemovelist.get(k));
        }
        vecRemovelist.clear();
        final Object[] v = vec.get(0);
        final StringBuffer format = (StringBuffer)v[1];
        format.setLength(format.length() - 1);
        return format.toString();
    }
    
    public ResultSetMetaData getMetaData() throws SQLException {
        if (!this.isSelectQuery()) {
            return null;
        }
        PreparedStatement mdStmt = null;
        ResultSet mdRs = null;
        if (this.pstmtResultMetaData == null) {
            try {
                mdStmt = new PreparedStatement(this.connection, this.originalSql, this.currentCatalog, this.parseInfo);
                mdStmt.setMaxRows(0);
                for (int paramCount = this.parameterValues.length, i = 1; i <= paramCount; ++i) {
                    mdStmt.setString(i, "");
                }
                final boolean hadResults = mdStmt.execute();
                if (hadResults) {
                    mdRs = mdStmt.getResultSet();
                    this.pstmtResultMetaData = mdRs.getMetaData();
                }
                else {
                    this.pstmtResultMetaData = new com.mysql.jdbc.ResultSetMetaData(new Field[0], this.connection.getUseOldAliasMetadataBehavior(), this.getExceptionInterceptor());
                }
            }
            finally {
                SQLException sqlExRethrow = null;
                if (mdRs != null) {
                    try {
                        mdRs.close();
                    }
                    catch (SQLException sqlEx) {
                        sqlExRethrow = sqlEx;
                    }
                    mdRs = null;
                }
                if (mdStmt != null) {
                    try {
                        mdStmt.close();
                    }
                    catch (SQLException sqlEx) {
                        sqlExRethrow = sqlEx;
                    }
                    mdStmt = null;
                }
                if (sqlExRethrow != null) {
                    throw sqlExRethrow;
                }
            }
        }
        return this.pstmtResultMetaData;
    }
    
    protected boolean isSelectQuery() {
        return StringUtils.startsWithIgnoreCaseAndWs(StringUtils.stripComments(this.originalSql, "'\"", "'\"", true, false, true, true), "SELECT");
    }
    
    public ParameterMetaData getParameterMetaData() throws SQLException {
        if (this.parameterMetaData == null) {
            if (this.connection.getGenerateSimpleParameterMetadata()) {
                this.parameterMetaData = new MysqlParameterMetadata(this.parameterCount);
            }
            else {
                this.parameterMetaData = new MysqlParameterMetadata(null, this.parameterCount, this.getExceptionInterceptor());
            }
        }
        return this.parameterMetaData;
    }
    
    ParseInfo getParseInfo() {
        return this.parseInfo;
    }
    
    private final char getSuccessor(final char c, final int n) {
        return (c == 'y' && n == 2) ? 'X' : ((c == 'y' && n < 4) ? 'y' : ((c == 'y') ? 'M' : ((c == 'M' && n == 2) ? 'Y' : ((c == 'M' && n < 3) ? 'M' : ((c == 'M') ? 'd' : ((c == 'd' && n < 2) ? 'd' : ((c == 'd') ? 'H' : ((c == 'H' && n < 2) ? 'H' : ((c == 'H') ? 'm' : ((c == 'm' && n < 2) ? 'm' : ((c == 'm') ? 's' : ((c == 's' && n < 2) ? 's' : 'W'))))))))))));
    }
    
    private final void hexEscapeBlock(final byte[] buf, final Buffer packet, final int size) throws SQLException {
        for (final byte b : buf) {
            final int lowBits = (b & 0xFF) / 16;
            final int highBits = (b & 0xFF) % 16;
            packet.writeByte(PreparedStatement.HEX_DIGITS[lowBits]);
            packet.writeByte(PreparedStatement.HEX_DIGITS[highBits]);
        }
    }
    
    private void initializeFromParseInfo() throws SQLException {
        this.staticSqlStrings = this.parseInfo.staticSql;
        this.hasLimitClause = this.parseInfo.foundLimitClause;
        this.isLoadDataQuery = this.parseInfo.foundLoadData;
        this.firstCharOfStmt = this.parseInfo.firstStmtChar;
        this.parameterCount = this.staticSqlStrings.length - 1;
        this.parameterValues = new byte[this.parameterCount][];
        this.parameterStreams = new InputStream[this.parameterCount];
        this.isStream = new boolean[this.parameterCount];
        this.streamLengths = new int[this.parameterCount];
        this.isNull = new boolean[this.parameterCount];
        this.parameterTypes = new int[this.parameterCount];
        this.clearParameters();
        for (int j = 0; j < this.parameterCount; ++j) {
            this.isStream[j] = false;
        }
    }
    
    boolean isNull(final int paramIndex) {
        return this.isNull[paramIndex];
    }
    
    private final int readblock(final InputStream i, final byte[] b) throws SQLException {
        try {
            return i.read(b);
        }
        catch (Throwable ex) {
            final SQLException sqlEx = SQLError.createSQLException(Messages.getString("PreparedStatement.56") + ex.getClass().getName(), "S1000", this.getExceptionInterceptor());
            sqlEx.initCause(ex);
            throw sqlEx;
        }
    }
    
    private final int readblock(final InputStream i, final byte[] b, final int length) throws SQLException {
        try {
            int lengthToRead = length;
            if (lengthToRead > b.length) {
                lengthToRead = b.length;
            }
            return i.read(b, 0, lengthToRead);
        }
        catch (Throwable ex) {
            final SQLException sqlEx = SQLError.createSQLException(Messages.getString("PreparedStatement.56") + ex.getClass().getName(), "S1000", this.getExceptionInterceptor());
            sqlEx.initCause(ex);
            throw sqlEx;
        }
    }
    
    protected void realClose(final boolean calledExplicitly, final boolean closeOpenResults) throws SQLException {
        if (this.useUsageAdvisor && this.numberOfExecutions <= 1) {
            final String message = Messages.getString("PreparedStatement.43");
            this.eventSink.consumeEvent(new ProfilerEvent((byte)0, "", this.currentCatalog, this.connectionId, this.getId(), -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
        }
        super.realClose(calledExplicitly, closeOpenResults);
        this.dbmd = null;
        this.originalSql = null;
        this.staticSqlStrings = null;
        this.parameterValues = null;
        this.parameterStreams = null;
        this.isStream = null;
        this.streamLengths = null;
        this.isNull = null;
        this.streamConvertBuf = null;
        this.parameterTypes = null;
    }
    
    public void setArray(final int i, final Array x) throws SQLException {
        throw SQLError.notImplemented();
    }
    
    public void setAsciiStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 12);
        }
        else {
            this.setBinaryStream(parameterIndex, x, length);
        }
    }
    
    public void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 3);
        }
        else {
            this.setInternal(parameterIndex, StringUtils.fixDecimalExponent(StringUtils.consistentToString(x)));
            this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 3;
        }
    }
    
    public void setBinaryStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, -2);
        }
        else {
            final int parameterIndexOffset = this.getParameterIndexOffset();
            if (parameterIndex < 1 || parameterIndex > this.staticSqlStrings.length) {
                throw SQLError.createSQLException(Messages.getString("PreparedStatement.2") + parameterIndex + Messages.getString("PreparedStatement.3") + this.staticSqlStrings.length + Messages.getString("PreparedStatement.4"), "S1009", this.getExceptionInterceptor());
            }
            if (parameterIndexOffset == -1 && parameterIndex == 1) {
                throw SQLError.createSQLException("Can't set IN parameter for return value of stored function call.", "S1009", this.getExceptionInterceptor());
            }
            this.parameterStreams[parameterIndex - 1 + parameterIndexOffset] = x;
            this.isStream[parameterIndex - 1 + parameterIndexOffset] = true;
            this.streamLengths[parameterIndex - 1 + parameterIndexOffset] = length;
            this.isNull[parameterIndex - 1 + parameterIndexOffset] = false;
            this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 2004;
        }
    }
    
    public void setBlob(final int parameterIndex, final InputStream inputStream, final long length) throws SQLException {
        this.setBinaryStream(parameterIndex, inputStream, (int)length);
    }
    
    public void setBlob(final int i, final Blob x) throws SQLException {
        if (x == null) {
            this.setNull(i, 2004);
        }
        else {
            final ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            bytesOut.write(39);
            this.escapeblockFast(x.getBytes(1L, (int)x.length()), bytesOut, (int)x.length());
            bytesOut.write(39);
            this.setInternal(i, bytesOut.toByteArray());
            this.parameterTypes[i - 1 + this.getParameterIndexOffset()] = 2004;
        }
    }
    
    public void setBoolean(final int parameterIndex, final boolean x) throws SQLException {
        if (this.useTrueBoolean) {
            this.setInternal(parameterIndex, x ? "1" : "0");
        }
        else {
            this.setInternal(parameterIndex, x ? "'t'" : "'f'");
            this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 16;
        }
    }
    
    public void setByte(final int parameterIndex, final byte x) throws SQLException {
        this.setInternal(parameterIndex, String.valueOf(x));
        this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = -6;
    }
    
    public void setBytes(final int parameterIndex, final byte[] x) throws SQLException {
        this.setBytes(parameterIndex, x, true, true);
        if (x != null) {
            this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = -2;
        }
    }
    
    protected void setBytes(final int parameterIndex, final byte[] x, final boolean checkForIntroducer, final boolean escapeForMBChars) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, -2);
        }
        else {
            final String connectionEncoding = this.connection.getEncoding();
            if (this.connection.isNoBackslashEscapesSet() || (escapeForMBChars && this.connection.getUseUnicode() && connectionEncoding != null && CharsetMapping.isMultibyteCharset(connectionEncoding))) {
                final ByteArrayOutputStream bOut = new ByteArrayOutputStream(x.length * 2 + 3);
                bOut.write(120);
                bOut.write(39);
                for (int i = 0; i < x.length; ++i) {
                    final int lowBits = (x[i] & 0xFF) / 16;
                    final int highBits = (x[i] & 0xFF) % 16;
                    bOut.write(PreparedStatement.HEX_DIGITS[lowBits]);
                    bOut.write(PreparedStatement.HEX_DIGITS[highBits]);
                }
                bOut.write(39);
                this.setInternal(parameterIndex, bOut.toByteArray());
                return;
            }
            final int numBytes = x.length;
            int pad = 2;
            final boolean needsIntroducer = checkForIntroducer && this.connection.versionMeetsMinimum(4, 1, 0);
            if (needsIntroducer) {
                pad += 7;
            }
            final ByteArrayOutputStream bOut2 = new ByteArrayOutputStream(numBytes + pad);
            if (needsIntroducer) {
                bOut2.write(95);
                bOut2.write(98);
                bOut2.write(105);
                bOut2.write(110);
                bOut2.write(97);
                bOut2.write(114);
                bOut2.write(121);
            }
            bOut2.write(39);
            for (final byte b : x) {
                switch (b) {
                    case 0: {
                        bOut2.write(92);
                        bOut2.write(48);
                        break;
                    }
                    case 10: {
                        bOut2.write(92);
                        bOut2.write(110);
                        break;
                    }
                    case 13: {
                        bOut2.write(92);
                        bOut2.write(114);
                        break;
                    }
                    case 92: {
                        bOut2.write(92);
                        bOut2.write(92);
                        break;
                    }
                    case 39: {
                        bOut2.write(92);
                        bOut2.write(39);
                        break;
                    }
                    case 34: {
                        bOut2.write(92);
                        bOut2.write(34);
                        break;
                    }
                    case 26: {
                        bOut2.write(92);
                        bOut2.write(90);
                        break;
                    }
                    default: {
                        bOut2.write(b);
                        break;
                    }
                }
            }
            bOut2.write(39);
            this.setInternal(parameterIndex, bOut2.toByteArray());
        }
    }
    
    protected void setBytesNoEscape(final int parameterIndex, final byte[] parameterAsBytes) throws SQLException {
        final byte[] parameterWithQuotes = new byte[parameterAsBytes.length + 2];
        parameterWithQuotes[0] = 39;
        System.arraycopy(parameterAsBytes, 0, parameterWithQuotes, 1, parameterAsBytes.length);
        parameterWithQuotes[parameterAsBytes.length + 1] = 39;
        this.setInternal(parameterIndex, parameterWithQuotes);
    }
    
    protected void setBytesNoEscapeNoQuotes(final int parameterIndex, final byte[] parameterAsBytes) throws SQLException {
        this.setInternal(parameterIndex, parameterAsBytes);
    }
    
    public void setCharacterStream(final int parameterIndex, final Reader reader, final int length) throws SQLException {
        try {
            if (reader == null) {
                this.setNull(parameterIndex, -1);
            }
            else {
                char[] c = null;
                int len = 0;
                final boolean useLength = this.connection.getUseStreamLengthsInPrepStmts();
                final String forcedEncoding = this.connection.getClobCharacterEncoding();
                if (useLength && length != -1) {
                    c = new char[length];
                    final int numCharsRead = readFully(reader, c, length);
                    if (forcedEncoding == null) {
                        this.setString(parameterIndex, new String(c, 0, numCharsRead));
                    }
                    else {
                        try {
                            this.setBytes(parameterIndex, new String(c, 0, numCharsRead).getBytes(forcedEncoding));
                        }
                        catch (UnsupportedEncodingException uee) {
                            throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", this.getExceptionInterceptor());
                        }
                    }
                }
                else {
                    c = new char[4096];
                    final StringBuffer buf = new StringBuffer();
                    while ((len = reader.read(c)) != -1) {
                        buf.append(c, 0, len);
                    }
                    if (forcedEncoding == null) {
                        this.setString(parameterIndex, buf.toString());
                    }
                    else {
                        try {
                            this.setBytes(parameterIndex, buf.toString().getBytes(forcedEncoding));
                        }
                        catch (UnsupportedEncodingException uee) {
                            throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", this.getExceptionInterceptor());
                        }
                    }
                }
                this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 2005;
            }
        }
        catch (IOException ioEx) {
            throw SQLError.createSQLException(ioEx.toString(), "S1000", this.getExceptionInterceptor());
        }
    }
    
    public void setClob(final int i, final Clob x) throws SQLException {
        if (x == null) {
            this.setNull(i, 2005);
        }
        else {
            final String forcedEncoding = this.connection.getClobCharacterEncoding();
            if (forcedEncoding == null) {
                this.setString(i, x.getSubString(1L, (int)x.length()));
            }
            else {
                try {
                    this.setBytes(i, x.getSubString(1L, (int)x.length()).getBytes(forcedEncoding));
                }
                catch (UnsupportedEncodingException uee) {
                    throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", this.getExceptionInterceptor());
                }
            }
            this.parameterTypes[i - 1 + this.getParameterIndexOffset()] = 2005;
        }
    }
    
    public void setDate(final int parameterIndex, final java.sql.Date x) throws SQLException {
        this.setDate(parameterIndex, x, null);
    }
    
    public void setDate(final int parameterIndex, final java.sql.Date x, final Calendar cal) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 91);
        }
        else {
            this.checkClosed();
            if (!this.useLegacyDatetimeCode) {
                this.newSetDateInternal(parameterIndex, x, cal);
            }
            else {
                final SimpleDateFormat dateFormatter = new SimpleDateFormat("''yyyy-MM-dd''", Locale.US);
                this.setInternal(parameterIndex, dateFormatter.format(x));
                this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 91;
            }
        }
    }
    
    public void setDouble(final int parameterIndex, final double x) throws SQLException {
        if (!this.connection.getAllowNanAndInf() && (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY || Double.isNaN(x))) {
            throw SQLError.createSQLException("'" + x + "' is not a valid numeric or approximate numeric value", "S1009", this.getExceptionInterceptor());
        }
        this.setInternal(parameterIndex, StringUtils.fixDecimalExponent(String.valueOf(x)));
        this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 8;
    }
    
    public void setFloat(final int parameterIndex, final float x) throws SQLException {
        this.setInternal(parameterIndex, StringUtils.fixDecimalExponent(String.valueOf(x)));
        this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 6;
    }
    
    public void setInt(final int parameterIndex, final int x) throws SQLException {
        this.setInternal(parameterIndex, String.valueOf(x));
        this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 4;
    }
    
    protected final void setInternal(final int paramIndex, final byte[] val) throws SQLException {
        if (this.isClosed) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.48"), "S1009", this.getExceptionInterceptor());
        }
        final int parameterIndexOffset = this.getParameterIndexOffset();
        this.checkBounds(paramIndex, parameterIndexOffset);
        this.isStream[paramIndex - 1 + parameterIndexOffset] = false;
        this.isNull[paramIndex - 1 + parameterIndexOffset] = false;
        this.parameterStreams[paramIndex - 1 + parameterIndexOffset] = null;
        this.parameterValues[paramIndex - 1 + parameterIndexOffset] = val;
    }
    
    private void checkBounds(final int paramIndex, final int parameterIndexOffset) throws SQLException {
        if (paramIndex < 1) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.49") + paramIndex + Messages.getString("PreparedStatement.50"), "S1009", this.getExceptionInterceptor());
        }
        if (paramIndex > this.parameterCount) {
            throw SQLError.createSQLException(Messages.getString("PreparedStatement.51") + paramIndex + Messages.getString("PreparedStatement.52") + this.parameterValues.length + Messages.getString("PreparedStatement.53"), "S1009", this.getExceptionInterceptor());
        }
        if (parameterIndexOffset == -1 && paramIndex == 1) {
            throw SQLError.createSQLException("Can't set IN parameter for return value of stored function call.", "S1009", this.getExceptionInterceptor());
        }
    }
    
    protected final void setInternal(final int paramIndex, final String val) throws SQLException {
        this.checkClosed();
        byte[] parameterAsBytes = null;
        if (this.charConverter != null) {
            parameterAsBytes = this.charConverter.toBytes(val);
        }
        else {
            parameterAsBytes = StringUtils.getBytes(val, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.getExceptionInterceptor());
        }
        this.setInternal(paramIndex, parameterAsBytes);
    }
    
    public void setLong(final int parameterIndex, final long x) throws SQLException {
        this.setInternal(parameterIndex, String.valueOf(x));
        this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = -5;
    }
    
    public void setNull(final int parameterIndex, final int sqlType) throws SQLException {
        this.setInternal(parameterIndex, "null");
        this.isNull[parameterIndex - 1 + this.getParameterIndexOffset()] = true;
        this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 0;
    }
    
    public void setNull(final int parameterIndex, final int sqlType, final String arg) throws SQLException {
        this.setNull(parameterIndex, sqlType);
        this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 0;
    }
    
    private void setNumericObject(final int parameterIndex, final Object parameterObj, final int targetSqlType, final int scale) throws SQLException {
        Number parameterAsNum;
        if (parameterObj instanceof Boolean) {
            parameterAsNum = (parameterObj ? Constants.integerValueOf(1) : Constants.integerValueOf(0));
        }
        else if (parameterObj instanceof String) {
            switch (targetSqlType) {
                case -7: {
                    if ("1".equals(parameterObj) || "0".equals(parameterObj)) {
                        parameterAsNum = Integer.valueOf((String)parameterObj);
                        break;
                    }
                    final boolean parameterAsBoolean = "true".equalsIgnoreCase((String)parameterObj);
                    parameterAsNum = (parameterAsBoolean ? Constants.integerValueOf(1) : Constants.integerValueOf(0));
                    break;
                }
                case -6:
                case 4:
                case 5: {
                    parameterAsNum = Integer.valueOf((String)parameterObj);
                    break;
                }
                case -5: {
                    parameterAsNum = Long.valueOf((String)parameterObj);
                    break;
                }
                case 7: {
                    parameterAsNum = Float.valueOf((String)parameterObj);
                    break;
                }
                case 6:
                case 8: {
                    parameterAsNum = Double.valueOf((String)parameterObj);
                    break;
                }
                default: {
                    parameterAsNum = new BigDecimal((String)parameterObj);
                    break;
                }
            }
        }
        else {
            parameterAsNum = (Number)parameterObj;
        }
        switch (targetSqlType) {
            case -7:
            case -6:
            case 4:
            case 5: {
                this.setInt(parameterIndex, parameterAsNum.intValue());
                break;
            }
            case -5: {
                this.setLong(parameterIndex, parameterAsNum.longValue());
                break;
            }
            case 7: {
                this.setFloat(parameterIndex, parameterAsNum.floatValue());
                break;
            }
            case 6:
            case 8: {
                this.setDouble(parameterIndex, parameterAsNum.doubleValue());
                break;
            }
            case 2:
            case 3: {
                if (parameterAsNum instanceof BigDecimal) {
                    BigDecimal scaledBigDecimal = null;
                    try {
                        scaledBigDecimal = ((BigDecimal)parameterAsNum).setScale(scale);
                    }
                    catch (ArithmeticException ex) {
                        try {
                            scaledBigDecimal = ((BigDecimal)parameterAsNum).setScale(scale, 4);
                        }
                        catch (ArithmeticException arEx) {
                            throw SQLError.createSQLException("Can't set scale of '" + scale + "' for DECIMAL argument '" + parameterAsNum + "'", "S1009", this.getExceptionInterceptor());
                        }
                    }
                    this.setBigDecimal(parameterIndex, scaledBigDecimal);
                    break;
                }
                if (parameterAsNum instanceof BigInteger) {
                    this.setBigDecimal(parameterIndex, new BigDecimal((BigInteger)parameterAsNum, scale));
                    break;
                }
                this.setBigDecimal(parameterIndex, new BigDecimal(parameterAsNum.doubleValue()));
                break;
            }
        }
    }
    
    public void setObject(final int parameterIndex, final Object parameterObj) throws SQLException {
        if (parameterObj == null) {
            this.setNull(parameterIndex, 1111);
        }
        else if (parameterObj instanceof Byte) {
            this.setInt(parameterIndex, (int)parameterObj);
        }
        else if (parameterObj instanceof String) {
            this.setString(parameterIndex, (String)parameterObj);
        }
        else if (parameterObj instanceof BigDecimal) {
            this.setBigDecimal(parameterIndex, (BigDecimal)parameterObj);
        }
        else if (parameterObj instanceof Short) {
            this.setShort(parameterIndex, (short)parameterObj);
        }
        else if (parameterObj instanceof Integer) {
            this.setInt(parameterIndex, (int)parameterObj);
        }
        else if (parameterObj instanceof Long) {
            this.setLong(parameterIndex, (long)parameterObj);
        }
        else if (parameterObj instanceof Float) {
            this.setFloat(parameterIndex, (float)parameterObj);
        }
        else if (parameterObj instanceof Double) {
            this.setDouble(parameterIndex, (double)parameterObj);
        }
        else if (parameterObj instanceof byte[]) {
            this.setBytes(parameterIndex, (byte[])parameterObj);
        }
        else if (parameterObj instanceof java.sql.Date) {
            this.setDate(parameterIndex, (java.sql.Date)parameterObj);
        }
        else if (parameterObj instanceof Time) {
            this.setTime(parameterIndex, (Time)parameterObj);
        }
        else if (parameterObj instanceof Timestamp) {
            this.setTimestamp(parameterIndex, (Timestamp)parameterObj);
        }
        else if (parameterObj instanceof Boolean) {
            this.setBoolean(parameterIndex, (boolean)parameterObj);
        }
        else if (parameterObj instanceof InputStream) {
            this.setBinaryStream(parameterIndex, (InputStream)parameterObj, -1);
        }
        else if (parameterObj instanceof Blob) {
            this.setBlob(parameterIndex, (Blob)parameterObj);
        }
        else if (parameterObj instanceof Clob) {
            this.setClob(parameterIndex, (Clob)parameterObj);
        }
        else if (this.connection.getTreatUtilDateAsTimestamp() && parameterObj instanceof Date) {
            this.setTimestamp(parameterIndex, new Timestamp(((Date)parameterObj).getTime()));
        }
        else if (parameterObj instanceof BigInteger) {
            this.setString(parameterIndex, parameterObj.toString());
        }
        else {
            this.setSerializableObject(parameterIndex, parameterObj);
        }
    }
    
    public void setObject(final int parameterIndex, final Object parameterObj, final int targetSqlType) throws SQLException {
        if (!(parameterObj instanceof BigDecimal)) {
            this.setObject(parameterIndex, parameterObj, targetSqlType, 0);
        }
        else {
            this.setObject(parameterIndex, parameterObj, targetSqlType, ((BigDecimal)parameterObj).scale());
        }
    }
    
    public void setObject(final int parameterIndex, final Object parameterObj, final int targetSqlType, final int scale) throws SQLException {
        if (parameterObj == null) {
            this.setNull(parameterIndex, 1111);
        }
        else {
            try {
                switch (targetSqlType) {
                    case 16: {
                        if (parameterObj instanceof Boolean) {
                            this.setBoolean(parameterIndex, (boolean)parameterObj);
                            break;
                        }
                        if (parameterObj instanceof String) {
                            this.setBoolean(parameterIndex, "true".equalsIgnoreCase((String)parameterObj) || !"0".equalsIgnoreCase((String)parameterObj));
                            break;
                        }
                        if (parameterObj instanceof Number) {
                            final int intValue = ((Number)parameterObj).intValue();
                            this.setBoolean(parameterIndex, intValue != 0);
                            break;
                        }
                        throw SQLError.createSQLException("No conversion from " + parameterObj.getClass().getName() + " to Types.BOOLEAN possible.", "S1009", this.getExceptionInterceptor());
                    }
                    case -7:
                    case -6:
                    case -5:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8: {
                        this.setNumericObject(parameterIndex, parameterObj, targetSqlType, scale);
                        break;
                    }
                    case -1:
                    case 1:
                    case 12: {
                        if (parameterObj instanceof BigDecimal) {
                            this.setString(parameterIndex, StringUtils.fixDecimalExponent(StringUtils.consistentToString((BigDecimal)parameterObj)));
                            break;
                        }
                        this.setString(parameterIndex, parameterObj.toString());
                        break;
                    }
                    case 2005: {
                        if (parameterObj instanceof Clob) {
                            this.setClob(parameterIndex, (Clob)parameterObj);
                            break;
                        }
                        this.setString(parameterIndex, parameterObj.toString());
                        break;
                    }
                    case -4:
                    case -3:
                    case -2:
                    case 2004: {
                        if (parameterObj instanceof byte[]) {
                            this.setBytes(parameterIndex, (byte[])parameterObj);
                            break;
                        }
                        if (parameterObj instanceof Blob) {
                            this.setBlob(parameterIndex, (Blob)parameterObj);
                            break;
                        }
                        this.setBytes(parameterIndex, StringUtils.getBytes(parameterObj.toString(), this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.getExceptionInterceptor()));
                        break;
                    }
                    case 91:
                    case 93: {
                        Date parameterAsDate;
                        if (parameterObj instanceof String) {
                            final ParsePosition pp = new ParsePosition(0);
                            final DateFormat sdf = new SimpleDateFormat(this.getDateTimePattern((String)parameterObj, false), Locale.US);
                            parameterAsDate = sdf.parse((String)parameterObj, pp);
                        }
                        else {
                            parameterAsDate = (Date)parameterObj;
                        }
                        switch (targetSqlType) {
                            case 91: {
                                if (parameterAsDate instanceof java.sql.Date) {
                                    this.setDate(parameterIndex, (java.sql.Date)parameterAsDate);
                                    break;
                                }
                                this.setDate(parameterIndex, new java.sql.Date(parameterAsDate.getTime()));
                                break;
                            }
                            case 93: {
                                if (parameterAsDate instanceof Timestamp) {
                                    this.setTimestamp(parameterIndex, (Timestamp)parameterAsDate);
                                    break;
                                }
                                this.setTimestamp(parameterIndex, new Timestamp(parameterAsDate.getTime()));
                                break;
                            }
                        }
                        break;
                    }
                    case 92: {
                        if (parameterObj instanceof String) {
                            final DateFormat sdf2 = new SimpleDateFormat(this.getDateTimePattern((String)parameterObj, true), Locale.US);
                            this.setTime(parameterIndex, new Time(sdf2.parse((String)parameterObj).getTime()));
                            break;
                        }
                        if (parameterObj instanceof Timestamp) {
                            final Timestamp xT = (Timestamp)parameterObj;
                            this.setTime(parameterIndex, new Time(xT.getTime()));
                            break;
                        }
                        this.setTime(parameterIndex, (Time)parameterObj);
                        break;
                    }
                    case 1111: {
                        this.setSerializableObject(parameterIndex, parameterObj);
                        break;
                    }
                    default: {
                        throw SQLError.createSQLException(Messages.getString("PreparedStatement.16"), "S1000", this.getExceptionInterceptor());
                    }
                }
            }
            catch (Exception ex) {
                if (ex instanceof SQLException) {
                    throw (SQLException)ex;
                }
                final SQLException sqlEx = SQLError.createSQLException(Messages.getString("PreparedStatement.17") + parameterObj.getClass().toString() + Messages.getString("PreparedStatement.18") + ex.getClass().getName() + Messages.getString("PreparedStatement.19") + ex.getMessage(), "S1000", this.getExceptionInterceptor());
                sqlEx.initCause(ex);
                throw sqlEx;
            }
        }
    }
    
    protected int setOneBatchedParameterSet(final java.sql.PreparedStatement batchedStatement, int batchedParamIndex, final Object paramSet) throws SQLException {
        final BatchParams paramArg = (BatchParams)paramSet;
        final boolean[] isNullBatch = paramArg.isNull;
        final boolean[] isStreamBatch = paramArg.isStream;
        for (int j = 0; j < isNullBatch.length; ++j) {
            if (isNullBatch[j]) {
                batchedStatement.setNull(batchedParamIndex++, 0);
            }
            else if (isStreamBatch[j]) {
                batchedStatement.setBinaryStream(batchedParamIndex++, paramArg.parameterStreams[j], paramArg.streamLengths[j]);
            }
            else {
                ((PreparedStatement)batchedStatement).setBytesNoEscapeNoQuotes(batchedParamIndex++, paramArg.parameterStrings[j]);
            }
        }
        return batchedParamIndex;
    }
    
    public void setRef(final int i, final Ref x) throws SQLException {
        throw SQLError.notImplemented();
    }
    
    void setResultSetConcurrency(final int concurrencyFlag) {
        this.resultSetConcurrency = concurrencyFlag;
    }
    
    void setResultSetType(final int typeFlag) {
        this.resultSetType = typeFlag;
    }
    
    protected void setRetrieveGeneratedKeys(final boolean retrieveGeneratedKeys) {
        this.retrieveGeneratedKeys = retrieveGeneratedKeys;
    }
    
    private final void setSerializableObject(final int parameterIndex, final Object parameterObj) throws SQLException {
        try {
            final ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            final ObjectOutputStream objectOut = new ObjectOutputStream(bytesOut);
            objectOut.writeObject(parameterObj);
            objectOut.flush();
            objectOut.close();
            bytesOut.flush();
            bytesOut.close();
            final byte[] buf = bytesOut.toByteArray();
            final ByteArrayInputStream bytesIn = new ByteArrayInputStream(buf);
            this.setBinaryStream(parameterIndex, bytesIn, buf.length);
            this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = -2;
        }
        catch (Exception ex) {
            final SQLException sqlEx = SQLError.createSQLException(Messages.getString("PreparedStatement.54") + ex.getClass().getName(), "S1009", this.getExceptionInterceptor());
            sqlEx.initCause(ex);
            throw sqlEx;
        }
    }
    
    public void setShort(final int parameterIndex, final short x) throws SQLException {
        this.setInternal(parameterIndex, String.valueOf(x));
        this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 5;
    }
    
    public void setString(final int parameterIndex, final String x) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 1);
        }
        else {
            this.checkClosed();
            final int stringLength = x.length();
            if (this.connection.isNoBackslashEscapesSet()) {
                final boolean needsHexEscape = this.isEscapeNeededForString(x, stringLength);
                if (!needsHexEscape) {
                    byte[] parameterAsBytes = null;
                    final StringBuffer quotedString = new StringBuffer(x.length() + 2);
                    quotedString.append('\'');
                    quotedString.append(x);
                    quotedString.append('\'');
                    if (!this.isLoadDataQuery) {
                        parameterAsBytes = StringUtils.getBytes(quotedString.toString(), this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.getExceptionInterceptor());
                    }
                    else {
                        parameterAsBytes = quotedString.toString().getBytes();
                    }
                    this.setInternal(parameterIndex, parameterAsBytes);
                }
                else {
                    byte[] parameterAsBytes = null;
                    if (!this.isLoadDataQuery) {
                        parameterAsBytes = StringUtils.getBytes(x, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.getExceptionInterceptor());
                    }
                    else {
                        parameterAsBytes = x.getBytes();
                    }
                    this.setBytes(parameterIndex, parameterAsBytes);
                }
                return;
            }
            String parameterAsString = x;
            boolean needsQuoted = true;
            if (this.isLoadDataQuery || this.isEscapeNeededForString(x, stringLength)) {
                needsQuoted = false;
                final StringBuffer buf = new StringBuffer((int)(x.length() * 1.1));
                buf.append('\'');
                for (int i = 0; i < stringLength; ++i) {
                    final char c = x.charAt(i);
                    switch (c) {
                        case '\0': {
                            buf.append('\\');
                            buf.append('0');
                            continue;
                        }
                        case '\n': {
                            buf.append('\\');
                            buf.append('n');
                            continue;
                        }
                        case '\r': {
                            buf.append('\\');
                            buf.append('r');
                            continue;
                        }
                        case '\\': {
                            buf.append('\\');
                            buf.append('\\');
                            continue;
                        }
                        case '\'': {
                            buf.append('\\');
                            buf.append('\'');
                            continue;
                        }
                        case '\"': {
                            if (this.usingAnsiMode) {
                                buf.append('\\');
                            }
                            buf.append('\"');
                            continue;
                        }
                        case '\u001a': {
                            buf.append('\\');
                            buf.append('Z');
                            continue;
                        }
                        case '¥':
                        case '\u20a9': {
                            if (this.charsetEncoder == null) {
                                break;
                            }
                            final CharBuffer cbuf = CharBuffer.allocate(1);
                            final ByteBuffer bbuf = ByteBuffer.allocate(1);
                            cbuf.put(c);
                            cbuf.position(0);
                            this.charsetEncoder.encode(cbuf, bbuf, true);
                            if (bbuf.get(0) == 92) {
                                buf.append('\\');
                                break;
                            }
                            break;
                        }
                    }
                    buf.append(c);
                }
                buf.append('\'');
                parameterAsString = buf.toString();
            }
            byte[] parameterAsBytes2 = null;
            if (!this.isLoadDataQuery) {
                if (needsQuoted) {
                    parameterAsBytes2 = StringUtils.getBytesWrapped(parameterAsString, '\'', '\'', this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.getExceptionInterceptor());
                }
                else {
                    parameterAsBytes2 = StringUtils.getBytes(parameterAsString, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.getExceptionInterceptor());
                }
            }
            else {
                parameterAsBytes2 = parameterAsString.getBytes();
            }
            this.setInternal(parameterIndex, parameterAsBytes2);
            this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 12;
        }
    }
    
    private boolean isEscapeNeededForString(final String x, final int stringLength) {
        boolean needsHexEscape = false;
        for (int i = 0; i < stringLength; ++i) {
            final char c = x.charAt(i);
            switch (c) {
                case '\0': {
                    needsHexEscape = true;
                    break;
                }
                case '\n': {
                    needsHexEscape = true;
                    break;
                }
                case '\r': {
                    needsHexEscape = true;
                    break;
                }
                case '\\': {
                    needsHexEscape = true;
                    break;
                }
                case '\'': {
                    needsHexEscape = true;
                    break;
                }
                case '\"': {
                    needsHexEscape = true;
                    break;
                }
                case '\u001a': {
                    needsHexEscape = true;
                    break;
                }
            }
            if (needsHexEscape) {
                break;
            }
        }
        return needsHexEscape;
    }
    
    public void setTime(final int parameterIndex, final Time x, final Calendar cal) throws SQLException {
        this.setTimeInternal(parameterIndex, x, cal, cal.getTimeZone(), true);
    }
    
    public void setTime(final int parameterIndex, final Time x) throws SQLException {
        this.setTimeInternal(parameterIndex, x, null, Util.getDefaultTimeZone(), false);
    }
    
    private void setTimeInternal(final int parameterIndex, Time x, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 92);
        }
        else {
            this.checkClosed();
            if (!this.useLegacyDatetimeCode) {
                this.newSetTimeInternal(parameterIndex, x, targetCalendar);
            }
            else {
                final Calendar sessionCalendar = this.getCalendarInstanceForSessionOrNew();
                synchronized (sessionCalendar) {
                    x = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, x, tz, this.connection.getServerTimezoneTZ(), rollForward);
                }
                this.setInternal(parameterIndex, "'" + x.toString() + "'");
            }
            this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 92;
        }
    }
    
    public void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal) throws SQLException {
        this.setTimestampInternal(parameterIndex, x, cal, cal.getTimeZone(), true);
    }
    
    public void setTimestamp(final int parameterIndex, final Timestamp x) throws SQLException {
        this.setTimestampInternal(parameterIndex, x, null, Util.getDefaultTimeZone(), false);
    }
    
    private void setTimestampInternal(final int parameterIndex, Timestamp x, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 93);
        }
        else {
            this.checkClosed();
            if (!this.useLegacyDatetimeCode) {
                this.newSetTimestampInternal(parameterIndex, x, targetCalendar);
            }
            else {
                String timestampString = null;
                final Calendar sessionCalendar = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : this.getCalendarInstanceForSessionOrNew();
                synchronized (sessionCalendar) {
                    x = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, x, tz, this.connection.getServerTimezoneTZ(), rollForward);
                }
                if (this.connection.getUseSSPSCompatibleTimezoneShift()) {
                    this.doSSPSCompatibleTimezoneShift(parameterIndex, x, sessionCalendar);
                }
                else {
                    synchronized (this) {
                        if (this.tsdf == null) {
                            this.tsdf = new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss''", Locale.US);
                        }
                        timestampString = this.tsdf.format(x);
                        this.setInternal(parameterIndex, timestampString);
                    }
                }
            }
            this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 93;
        }
    }
    
    private synchronized void newSetTimestampInternal(final int parameterIndex, final Timestamp x, final Calendar targetCalendar) throws SQLException {
        if (this.tsdf == null) {
            this.tsdf = new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss", Locale.US);
        }
        String timestampString = null;
        if (targetCalendar != null) {
            targetCalendar.setTime(x);
            this.tsdf.setTimeZone(targetCalendar.getTimeZone());
            timestampString = this.tsdf.format(x);
        }
        else {
            this.tsdf.setTimeZone(this.connection.getServerTimezoneTZ());
            timestampString = this.tsdf.format(x);
        }
        final StringBuffer buf = new StringBuffer();
        buf.append(timestampString);
        buf.append('.');
        buf.append(this.formatNanos(x.getNanos()));
        buf.append('\'');
        this.setInternal(parameterIndex, buf.toString());
    }
    
    private String formatNanos(final int nanos) {
        return "0";
    }
    
    private synchronized void newSetTimeInternal(final int parameterIndex, final Time x, final Calendar targetCalendar) throws SQLException {
        if (this.tdf == null) {
            this.tdf = new SimpleDateFormat("''HH:mm:ss''", Locale.US);
        }
        String timeString = null;
        if (targetCalendar != null) {
            targetCalendar.setTime(x);
            this.tdf.setTimeZone(targetCalendar.getTimeZone());
            timeString = this.tdf.format(x);
        }
        else {
            this.tdf.setTimeZone(this.connection.getServerTimezoneTZ());
            timeString = this.tdf.format(x);
        }
        this.setInternal(parameterIndex, timeString);
    }
    
    private synchronized void newSetDateInternal(final int parameterIndex, final java.sql.Date x, final Calendar targetCalendar) throws SQLException {
        if (this.ddf == null) {
            this.ddf = new SimpleDateFormat("''yyyy-MM-dd''", Locale.US);
        }
        String timeString = null;
        if (targetCalendar != null) {
            targetCalendar.setTime(x);
            this.ddf.setTimeZone(targetCalendar.getTimeZone());
            timeString = this.ddf.format(x);
        }
        else {
            this.ddf.setTimeZone(this.connection.getServerTimezoneTZ());
            timeString = this.ddf.format(x);
        }
        this.setInternal(parameterIndex, timeString);
    }
    
    private void doSSPSCompatibleTimezoneShift(final int parameterIndex, final Timestamp x, final Calendar sessionCalendar) throws SQLException {
        final Calendar sessionCalendar2 = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : this.getCalendarInstanceForSessionOrNew();
        synchronized (sessionCalendar2) {
            final Date oldTime = sessionCalendar2.getTime();
            try {
                sessionCalendar2.setTime(x);
                final int year = sessionCalendar2.get(1);
                final int month = sessionCalendar2.get(2) + 1;
                final int date = sessionCalendar2.get(5);
                final int hour = sessionCalendar2.get(11);
                final int minute = sessionCalendar2.get(12);
                final int seconds = sessionCalendar2.get(13);
                final StringBuffer tsBuf = new StringBuffer();
                tsBuf.append('\'');
                tsBuf.append(year);
                tsBuf.append("-");
                if (month < 10) {
                    tsBuf.append('0');
                }
                tsBuf.append(month);
                tsBuf.append('-');
                if (date < 10) {
                    tsBuf.append('0');
                }
                tsBuf.append(date);
                tsBuf.append(' ');
                if (hour < 10) {
                    tsBuf.append('0');
                }
                tsBuf.append(hour);
                tsBuf.append(':');
                if (minute < 10) {
                    tsBuf.append('0');
                }
                tsBuf.append(minute);
                tsBuf.append(':');
                if (seconds < 10) {
                    tsBuf.append('0');
                }
                tsBuf.append(seconds);
                tsBuf.append('.');
                tsBuf.append(this.formatNanos(x.getNanos()));
                tsBuf.append('\'');
                this.setInternal(parameterIndex, tsBuf.toString());
            }
            finally {
                sessionCalendar.setTime(oldTime);
            }
        }
    }
    
    public void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 12);
        }
        else {
            this.setBinaryStream(parameterIndex, x, length);
            this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 2005;
        }
    }
    
    public void setURL(final int parameterIndex, final URL arg) throws SQLException {
        if (arg != null) {
            this.setString(parameterIndex, arg.toString());
            this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 70;
        }
        else {
            this.setNull(parameterIndex, 1);
        }
    }
    
    private final void streamToBytes(final Buffer packet, InputStream in, final boolean escape, final int streamLength, boolean useLength) throws SQLException {
        try {
            final String connectionEncoding = this.connection.getEncoding();
            boolean hexEscape = false;
            if (this.connection.isNoBackslashEscapesSet() || (this.connection.getUseUnicode() && connectionEncoding != null && CharsetMapping.isMultibyteCharset(connectionEncoding) && !this.connection.parserKnowsUnicode())) {
                hexEscape = true;
            }
            if (streamLength == -1) {
                useLength = false;
            }
            int bc = -1;
            if (useLength) {
                bc = this.readblock(in, this.streamConvertBuf, streamLength);
            }
            else {
                bc = this.readblock(in, this.streamConvertBuf);
            }
            int lengthLeftToRead = streamLength - bc;
            if (hexEscape) {
                packet.writeStringNoNull("x");
            }
            else if (this.connection.getIO().versionMeetsMinimum(4, 1, 0)) {
                packet.writeStringNoNull("_binary");
            }
            if (escape) {
                packet.writeByte((byte)39);
            }
            while (bc > 0) {
                if (hexEscape) {
                    this.hexEscapeBlock(this.streamConvertBuf, packet, bc);
                }
                else if (escape) {
                    this.escapeblockFast(this.streamConvertBuf, packet, bc);
                }
                else {
                    packet.writeBytesNoNull(this.streamConvertBuf, 0, bc);
                }
                if (useLength) {
                    bc = this.readblock(in, this.streamConvertBuf, lengthLeftToRead);
                    if (bc <= 0) {
                        continue;
                    }
                    lengthLeftToRead -= bc;
                }
                else {
                    bc = this.readblock(in, this.streamConvertBuf);
                }
            }
            if (escape) {
                packet.writeByte((byte)39);
            }
        }
        finally {
            if (this.connection.getAutoClosePStmtStreams()) {
                try {
                    in.close();
                }
                catch (IOException ex) {}
                in = null;
            }
        }
    }
    
    private final byte[] streamToBytes(InputStream in, final boolean escape, final int streamLength, boolean useLength) throws SQLException {
        try {
            if (streamLength == -1) {
                useLength = false;
            }
            final ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            int bc = -1;
            if (useLength) {
                bc = this.readblock(in, this.streamConvertBuf, streamLength);
            }
            else {
                bc = this.readblock(in, this.streamConvertBuf);
            }
            int lengthLeftToRead = streamLength - bc;
            if (escape) {
                if (this.connection.versionMeetsMinimum(4, 1, 0)) {
                    bytesOut.write(95);
                    bytesOut.write(98);
                    bytesOut.write(105);
                    bytesOut.write(110);
                    bytesOut.write(97);
                    bytesOut.write(114);
                    bytesOut.write(121);
                }
                bytesOut.write(39);
            }
            while (bc > 0) {
                if (escape) {
                    this.escapeblockFast(this.streamConvertBuf, bytesOut, bc);
                }
                else {
                    bytesOut.write(this.streamConvertBuf, 0, bc);
                }
                if (useLength) {
                    bc = this.readblock(in, this.streamConvertBuf, lengthLeftToRead);
                    if (bc <= 0) {
                        continue;
                    }
                    lengthLeftToRead -= bc;
                }
                else {
                    bc = this.readblock(in, this.streamConvertBuf);
                }
            }
            if (escape) {
                bytesOut.write(39);
            }
            return bytesOut.toByteArray();
        }
        finally {
            if (this.connection.getAutoClosePStmtStreams()) {
                try {
                    in.close();
                }
                catch (IOException ex) {}
                in = null;
            }
        }
    }
    
    public String toString() {
        final StringBuffer buf = new StringBuffer();
        buf.append(super.toString());
        buf.append(": ");
        try {
            buf.append(this.asSql());
        }
        catch (SQLException sqlEx) {
            buf.append("EXCEPTION: " + sqlEx.toString());
        }
        return buf.toString();
    }
    
    public synchronized boolean isClosed() throws SQLException {
        return this.isClosed;
    }
    
    protected int getParameterIndexOffset() {
        return 0;
    }
    
    public void setAsciiStream(final int parameterIndex, final InputStream x) throws SQLException {
        this.setAsciiStream(parameterIndex, x, -1);
    }
    
    public void setAsciiStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
        this.setAsciiStream(parameterIndex, x, (int)length);
        this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 2005;
    }
    
    public void setBinaryStream(final int parameterIndex, final InputStream x) throws SQLException {
        this.setBinaryStream(parameterIndex, x, -1);
    }
    
    public void setBinaryStream(final int parameterIndex, final InputStream x, final long length) throws SQLException {
        this.setBinaryStream(parameterIndex, x, (int)length);
    }
    
    public void setBlob(final int parameterIndex, final InputStream inputStream) throws SQLException {
        this.setBinaryStream(parameterIndex, inputStream);
    }
    
    public void setCharacterStream(final int parameterIndex, final Reader reader) throws SQLException {
        this.setCharacterStream(parameterIndex, reader, -1);
    }
    
    public void setCharacterStream(final int parameterIndex, final Reader reader, final long length) throws SQLException {
        this.setCharacterStream(parameterIndex, reader, (int)length);
    }
    
    public void setClob(final int parameterIndex, final Reader reader) throws SQLException {
        this.setCharacterStream(parameterIndex, reader);
    }
    
    public void setClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
        this.setCharacterStream(parameterIndex, reader, length);
    }
    
    public void setNCharacterStream(final int parameterIndex, final Reader value) throws SQLException {
        this.setNCharacterStream(parameterIndex, value, -1L);
    }
    
    public void setNString(final int parameterIndex, final String x) throws SQLException {
        if (this.charEncoding.equalsIgnoreCase("UTF-8") || this.charEncoding.equalsIgnoreCase("utf8")) {
            this.setString(parameterIndex, x);
            return;
        }
        if (x == null) {
            this.setNull(parameterIndex, 1);
        }
        else {
            final int stringLength = x.length();
            final StringBuffer buf = new StringBuffer((int)(x.length() * 1.1 + 4.0));
            buf.append("_utf8");
            buf.append('\'');
            for (int i = 0; i < stringLength; ++i) {
                final char c = x.charAt(i);
                switch (c) {
                    case '\0': {
                        buf.append('\\');
                        buf.append('0');
                        break;
                    }
                    case '\n': {
                        buf.append('\\');
                        buf.append('n');
                        break;
                    }
                    case '\r': {
                        buf.append('\\');
                        buf.append('r');
                        break;
                    }
                    case '\\': {
                        buf.append('\\');
                        buf.append('\\');
                        break;
                    }
                    case '\'': {
                        buf.append('\\');
                        buf.append('\'');
                        break;
                    }
                    case '\"': {
                        if (this.usingAnsiMode) {
                            buf.append('\\');
                        }
                        buf.append('\"');
                        break;
                    }
                    case '\u001a': {
                        buf.append('\\');
                        buf.append('Z');
                        break;
                    }
                    default: {
                        buf.append(c);
                        break;
                    }
                }
            }
            buf.append('\'');
            final String parameterAsString = buf.toString();
            byte[] parameterAsBytes = null;
            if (!this.isLoadDataQuery) {
                parameterAsBytes = StringUtils.getBytes(parameterAsString, this.connection.getCharsetConverter("UTF-8"), "UTF-8", this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.getExceptionInterceptor());
            }
            else {
                parameterAsBytes = parameterAsString.getBytes();
            }
            this.setInternal(parameterIndex, parameterAsBytes);
            this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = -9;
        }
    }
    
    public void setNCharacterStream(final int parameterIndex, final Reader reader, final long length) throws SQLException {
        try {
            if (reader == null) {
                this.setNull(parameterIndex, -1);
            }
            else {
                char[] c = null;
                int len = 0;
                final boolean useLength = this.connection.getUseStreamLengthsInPrepStmts();
                if (useLength && length != -1L) {
                    c = new char[(int)length];
                    final int numCharsRead = readFully(reader, c, (int)length);
                    this.setNString(parameterIndex, new String(c, 0, numCharsRead));
                }
                else {
                    c = new char[4096];
                    final StringBuffer buf = new StringBuffer();
                    while ((len = reader.read(c)) != -1) {
                        buf.append(c, 0, len);
                    }
                    this.setNString(parameterIndex, buf.toString());
                }
                this.parameterTypes[parameterIndex - 1 + this.getParameterIndexOffset()] = 2011;
            }
        }
        catch (IOException ioEx) {
            throw SQLError.createSQLException(ioEx.toString(), "S1000", this.getExceptionInterceptor());
        }
    }
    
    public void setNClob(final int parameterIndex, final Reader reader) throws SQLException {
        this.setNCharacterStream(parameterIndex, reader);
    }
    
    public void setNClob(final int parameterIndex, final Reader reader, final long length) throws SQLException {
        if (reader == null) {
            this.setNull(parameterIndex, -1);
        }
        else {
            this.setNCharacterStream(parameterIndex, reader, length);
        }
    }
    
    public ParameterBindings getParameterBindings() throws SQLException {
        return new EmulatedPreparedStatementBindings();
    }
    
    public String getPreparedSql() {
        if (this.rewrittenBatchSize == 0) {
            return this.originalSql;
        }
        try {
            return this.parseInfo.getSqlForBatch(this.parseInfo);
        }
        catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
    
    public int getUpdateCount() throws SQLException {
        int count = super.getUpdateCount();
        if (this.containsOnDuplicateKeyUpdateInSQL() && this.compensateForOnDuplicateKeyUpdate && (count == 2 || count == 0)) {
            count = 1;
        }
        return count;
    }
    
    protected static boolean canRewrite(final String sql, final boolean isOnDuplicateKeyUpdate, final int locationOfOnDuplicateKeyUpdate, final int statementStartPos) {
        boolean rewritableOdku = true;
        if (isOnDuplicateKeyUpdate) {
            final int updateClausePos = StringUtils.indexOfIgnoreCase(locationOfOnDuplicateKeyUpdate, sql, " UPDATE ");
            if (updateClausePos != -1) {
                rewritableOdku = (StringUtils.indexOfIgnoreCaseRespectMarker(updateClausePos, sql, "LAST_INSERT_ID", "\"'`", "\"'`", false) == -1);
            }
        }
        return StringUtils.startsWithIgnoreCaseAndWs(sql, "INSERT", statementStartPos) && StringUtils.indexOfIgnoreCaseRespectMarker(statementStartPos, sql, "SELECT", "\"'`", "\"'`", false) == -1 && rewritableOdku;
    }
    
    static {
        Label_0144: {
            if (Util.isJdbc4()) {
                try {
                    JDBC_4_PSTMT_2_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4PreparedStatement").getConstructor(MySQLConnection.class, String.class);
                    JDBC_4_PSTMT_3_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4PreparedStatement").getConstructor(MySQLConnection.class, String.class, String.class);
                    JDBC_4_PSTMT_4_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4PreparedStatement").getConstructor(MySQLConnection.class, String.class, String.class, ParseInfo.class);
                    break Label_0144;
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
            JDBC_4_PSTMT_2_ARG_CTOR = null;
            JDBC_4_PSTMT_3_ARG_CTOR = null;
            JDBC_4_PSTMT_4_ARG_CTOR = null;
        }
        HEX_DIGITS = new byte[] { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
    }
    
    class BatchParams
    {
        boolean[] isNull;
        boolean[] isStream;
        InputStream[] parameterStreams;
        byte[][] parameterStrings;
        int[] streamLengths;
        
        BatchParams(final byte[][] strings, final InputStream[] streams, final boolean[] isStreamFlags, final int[] lengths, final boolean[] isNullFlags) {
            this.isNull = null;
            this.isStream = null;
            this.parameterStreams = null;
            this.parameterStrings = null;
            this.streamLengths = null;
            this.parameterStrings = new byte[strings.length][];
            this.parameterStreams = new InputStream[streams.length];
            this.isStream = new boolean[isStreamFlags.length];
            this.streamLengths = new int[lengths.length];
            this.isNull = new boolean[isNullFlags.length];
            System.arraycopy(strings, 0, this.parameterStrings, 0, strings.length);
            System.arraycopy(streams, 0, this.parameterStreams, 0, streams.length);
            System.arraycopy(isStreamFlags, 0, this.isStream, 0, isStreamFlags.length);
            System.arraycopy(lengths, 0, this.streamLengths, 0, lengths.length);
            System.arraycopy(isNullFlags, 0, this.isNull, 0, isNullFlags.length);
        }
    }
    
    class EndPoint
    {
        int begin;
        int end;
        
        EndPoint(final int b, final int e) {
            this.begin = b;
            this.end = e;
        }
    }
    
    class ParseInfo
    {
        char firstStmtChar;
        boolean foundLimitClause;
        boolean foundLoadData;
        long lastUsed;
        int statementLength;
        int statementStartPos;
        boolean canRewriteAsMultiValueInsert;
        byte[][] staticSql;
        boolean isOnDuplicateKeyUpdate;
        int locationOfOnDuplicateKeyUpdate;
        String valuesClause;
        boolean parametersInDuplicateKeyClause;
        private ParseInfo batchHead;
        private ParseInfo batchValues;
        private ParseInfo batchODKUClause;
        
        ParseInfo(final PreparedStatement preparedStatement, final String sql, final MySQLConnection conn, final DatabaseMetaData dbmd, final String encoding, final SingleByteCharsetConverter converter) throws SQLException {
            this(preparedStatement, sql, conn, dbmd, encoding, converter, true);
        }
        
        public ParseInfo(final String sql, final MySQLConnection conn, final DatabaseMetaData dbmd, final String encoding, final SingleByteCharsetConverter converter, final boolean buildRewriteInfo) throws SQLException {
            this.firstStmtChar = '\0';
            this.foundLimitClause = false;
            this.foundLoadData = false;
            this.lastUsed = 0L;
            this.statementLength = 0;
            this.statementStartPos = 0;
            this.canRewriteAsMultiValueInsert = false;
            this.staticSql = null;
            this.isOnDuplicateKeyUpdate = false;
            this.locationOfOnDuplicateKeyUpdate = -1;
            this.parametersInDuplicateKeyClause = false;
            try {
                if (sql == null) {
                    throw SQLError.createSQLException(Messages.getString("PreparedStatement.61"), "S1009", PreparedStatement.this.getExceptionInterceptor());
                }
                this.locationOfOnDuplicateKeyUpdate = PreparedStatement.this.getOnDuplicateKeyLocation(sql);
                this.isOnDuplicateKeyUpdate = (this.locationOfOnDuplicateKeyUpdate != -1);
                this.lastUsed = System.currentTimeMillis();
                final String quotedIdentifierString = dbmd.getIdentifierQuoteString();
                char quotedIdentifierChar = '\0';
                if (quotedIdentifierString != null && !quotedIdentifierString.equals(" ") && quotedIdentifierString.length() > 0) {
                    quotedIdentifierChar = quotedIdentifierString.charAt(0);
                }
                this.statementLength = sql.length();
                final ArrayList endpointList = new ArrayList();
                boolean inQuotes = false;
                char quoteChar = '\0';
                boolean inQuotedId = false;
                int lastParmEnd = 0;
                final int stopLookingForLimitClause = this.statementLength - 5;
                this.foundLimitClause = false;
                final boolean noBackslashEscapes = PreparedStatement.this.connection.isNoBackslashEscapesSet();
                this.statementStartPos = PreparedStatement.this.findStartOfStatement(sql);
                for (int i = this.statementStartPos; i < this.statementLength; ++i) {
                    char c = sql.charAt(i);
                    if (this.firstStmtChar == '\0' && Character.isLetter(c)) {
                        this.firstStmtChar = Character.toUpperCase(c);
                    }
                    if (!noBackslashEscapes && c == '\\' && i < this.statementLength - 1) {
                        ++i;
                    }
                    else {
                        if (!inQuotes && quotedIdentifierChar != '\0' && c == quotedIdentifierChar) {
                            inQuotedId = !inQuotedId;
                        }
                        else if (!inQuotedId) {
                            if (inQuotes) {
                                if ((c == '\'' || c == '\"') && c == quoteChar) {
                                    if (i < this.statementLength - 1 && sql.charAt(i + 1) == quoteChar) {
                                        ++i;
                                        continue;
                                    }
                                    inQuotes = !inQuotes;
                                    quoteChar = '\0';
                                }
                                else if ((c == '\'' || c == '\"') && c == quoteChar) {
                                    inQuotes = !inQuotes;
                                    quoteChar = '\0';
                                }
                            }
                            else {
                                if (c == '#' || (c == '-' && i + 1 < this.statementLength && sql.charAt(i + 1) == '-')) {
                                    for (int endOfStmt = this.statementLength - 1; i < endOfStmt; ++i) {
                                        c = sql.charAt(i);
                                        if (c == '\r') {
                                            break;
                                        }
                                        if (c == '\n') {
                                            break;
                                        }
                                    }
                                    continue;
                                }
                                if (c == '/' && i + 1 < this.statementLength) {
                                    char cNext = sql.charAt(i + 1);
                                    if (cNext == '*') {
                                        i += 2;
                                        int j = i;
                                        while (j < this.statementLength) {
                                            ++i;
                                            cNext = sql.charAt(j);
                                            if (cNext == '*' && j + 1 < this.statementLength && sql.charAt(j + 1) == '/') {
                                                if (++i < this.statementLength) {
                                                    c = sql.charAt(i);
                                                    break;
                                                }
                                                break;
                                            }
                                            else {
                                                ++j;
                                            }
                                        }
                                    }
                                }
                                else if (c == '\'' || c == '\"') {
                                    inQuotes = true;
                                    quoteChar = c;
                                }
                            }
                        }
                        if (c == '?' && !inQuotes && !inQuotedId) {
                            endpointList.add(new int[] { lastParmEnd, i });
                            lastParmEnd = i + 1;
                            if (this.isOnDuplicateKeyUpdate && i > this.locationOfOnDuplicateKeyUpdate) {
                                this.parametersInDuplicateKeyClause = true;
                            }
                        }
                        if (!inQuotes && i < stopLookingForLimitClause && (c == 'L' || c == 'l')) {
                            final char posI1 = sql.charAt(i + 1);
                            if (posI1 == 'I' || posI1 == 'i') {
                                final char posM = sql.charAt(i + 2);
                                if (posM == 'M' || posM == 'm') {
                                    final char posI2 = sql.charAt(i + 3);
                                    if (posI2 == 'I' || posI2 == 'i') {
                                        final char posT = sql.charAt(i + 4);
                                        if (posT == 'T' || posT == 't') {
                                            this.foundLimitClause = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (this.firstStmtChar == 'L') {
                    if (StringUtils.startsWithIgnoreCaseAndWs(sql, "LOAD DATA")) {
                        this.foundLoadData = true;
                    }
                    else {
                        this.foundLoadData = false;
                    }
                }
                else {
                    this.foundLoadData = false;
                }
                endpointList.add(new int[] { lastParmEnd, this.statementLength });
                this.staticSql = new byte[endpointList.size()][];
                final char[] asCharArray = sql.toCharArray();
                for (int i = 0; i < this.staticSql.length; ++i) {
                    final int[] ep = endpointList.get(i);
                    final int end = ep[1];
                    final int begin = ep[0];
                    final int len = end - begin;
                    if (this.foundLoadData) {
                        final String temp = new String(asCharArray, begin, len);
                        this.staticSql[i] = temp.getBytes();
                    }
                    else if (encoding == null) {
                        final byte[] buf = new byte[len];
                        for (int k = 0; k < len; ++k) {
                            buf[k] = (byte)sql.charAt(begin + k);
                        }
                        this.staticSql[i] = buf;
                    }
                    else if (converter != null) {
                        this.staticSql[i] = StringUtils.getBytes(sql, converter, encoding, PreparedStatement.this.connection.getServerCharacterEncoding(), begin, len, PreparedStatement.this.connection.parserKnowsUnicode(), PreparedStatement.this.getExceptionInterceptor());
                    }
                    else {
                        final String temp = new String(asCharArray, begin, len);
                        this.staticSql[i] = StringUtils.getBytes(temp, encoding, PreparedStatement.this.connection.getServerCharacterEncoding(), PreparedStatement.this.connection.parserKnowsUnicode(), conn, PreparedStatement.this.getExceptionInterceptor());
                    }
                }
            }
            catch (StringIndexOutOfBoundsException oobEx) {
                final SQLException sqlEx = new SQLException("Parse error for " + sql);
                sqlEx.initCause(oobEx);
                throw sqlEx;
            }
            if (buildRewriteInfo) {
                this.canRewriteAsMultiValueInsert = (PreparedStatement.canRewrite(sql, this.isOnDuplicateKeyUpdate, this.locationOfOnDuplicateKeyUpdate, this.statementStartPos) && !this.parametersInDuplicateKeyClause);
                if (this.canRewriteAsMultiValueInsert && conn.getRewriteBatchedStatements()) {
                    this.buildRewriteBatchedParams(sql, conn, dbmd, encoding, converter);
                }
            }
        }
        
        private void buildRewriteBatchedParams(final String sql, final MySQLConnection conn, final DatabaseMetaData metadata, final String encoding, final SingleByteCharsetConverter converter) throws SQLException {
            this.valuesClause = this.extractValuesClause(sql);
            final String odkuClause = this.isOnDuplicateKeyUpdate ? sql.substring(this.locationOfOnDuplicateKeyUpdate) : null;
            String headSql = null;
            if (this.isOnDuplicateKeyUpdate) {
                headSql = sql.substring(0, this.locationOfOnDuplicateKeyUpdate);
            }
            else {
                headSql = sql;
            }
            this.batchHead = new ParseInfo(headSql, conn, metadata, encoding, converter, false);
            this.batchValues = new ParseInfo("," + this.valuesClause, conn, metadata, encoding, converter, false);
            this.batchODKUClause = null;
            if (odkuClause != null && odkuClause.length() > 0) {
                this.batchODKUClause = new ParseInfo("," + this.valuesClause + " " + odkuClause, conn, metadata, encoding, converter, false);
            }
        }
        
        private String extractValuesClause(final String sql) throws SQLException {
            final String quoteCharStr = PreparedStatement.this.connection.getMetaData().getIdentifierQuoteString();
            int indexOfValues = -1;
            int valuesSearchStart = this.statementStartPos;
            while (indexOfValues == -1) {
                if (quoteCharStr.length() > 0) {
                    indexOfValues = StringUtils.indexOfIgnoreCaseRespectQuotes(valuesSearchStart, PreparedStatement.this.originalSql, "VALUES", quoteCharStr.charAt(0), false);
                }
                else {
                    indexOfValues = StringUtils.indexOfIgnoreCase(valuesSearchStart, PreparedStatement.this.originalSql, "VALUES");
                }
                if (indexOfValues <= 0) {
                    break;
                }
                char c = PreparedStatement.this.originalSql.charAt(indexOfValues - 1);
                if (!Character.isWhitespace(c) && c != ')' && c != '`') {
                    valuesSearchStart = indexOfValues + 6;
                    indexOfValues = -1;
                }
                else {
                    c = PreparedStatement.this.originalSql.charAt(indexOfValues + 6);
                    if (Character.isWhitespace(c) || c == '(') {
                        continue;
                    }
                    valuesSearchStart = indexOfValues + 6;
                    indexOfValues = -1;
                }
            }
            if (indexOfValues == -1) {
                return null;
            }
            final int indexOfFirstParen = sql.indexOf(40, indexOfValues + 6);
            if (indexOfFirstParen == -1) {
                return null;
            }
            int endOfValuesClause = sql.lastIndexOf(41);
            if (endOfValuesClause == -1) {
                return null;
            }
            if (this.isOnDuplicateKeyUpdate) {
                endOfValuesClause = this.locationOfOnDuplicateKeyUpdate - 1;
            }
            return sql.substring(indexOfFirstParen, endOfValuesClause + 1);
        }
        
        synchronized ParseInfo getParseInfoForBatch(final int numBatch) {
            final AppendingBatchVisitor apv = new AppendingBatchVisitor();
            this.buildInfoForBatch(numBatch, apv);
            final ParseInfo batchParseInfo = new ParseInfo(apv.getStaticSqlStrings(), this.firstStmtChar, this.foundLimitClause, this.foundLoadData, this.isOnDuplicateKeyUpdate, this.locationOfOnDuplicateKeyUpdate, this.statementLength, this.statementStartPos);
            return batchParseInfo;
        }
        
        String getSqlForBatch(final int numBatch) throws UnsupportedEncodingException {
            final ParseInfo batchInfo = this.getParseInfoForBatch(numBatch);
            return this.getSqlForBatch(batchInfo);
        }
        
        String getSqlForBatch(final ParseInfo batchInfo) throws UnsupportedEncodingException {
            int size = 0;
            final byte[][] sqlStrings = batchInfo.staticSql;
            final int sqlStringsLength = sqlStrings.length;
            for (int i = 0; i < sqlStringsLength; ++i) {
                size += sqlStrings[i].length;
                ++size;
            }
            final StringBuffer buf = new StringBuffer(size);
            for (int j = 0; j < sqlStringsLength - 1; ++j) {
                buf.append(new String(sqlStrings[j], PreparedStatement.this.charEncoding));
                buf.append("?");
            }
            buf.append(new String(sqlStrings[sqlStringsLength - 1]));
            return buf.toString();
        }
        
        private void buildInfoForBatch(final int numBatch, final BatchVisitor visitor) {
            final byte[][] headStaticSql = this.batchHead.staticSql;
            final int headStaticSqlLength = headStaticSql.length;
            if (headStaticSqlLength > 1) {
                for (int i = 0; i < headStaticSqlLength - 1; ++i) {
                    visitor.append(headStaticSql[i]).increment();
                }
            }
            final byte[] endOfHead = headStaticSql[headStaticSqlLength - 1];
            final byte[][] valuesStaticSql = this.batchValues.staticSql;
            final byte[] beginOfValues = valuesStaticSql[0];
            visitor.merge(endOfHead, beginOfValues).increment();
            int numValueRepeats = numBatch - 1;
            if (this.batchODKUClause != null) {
                --numValueRepeats;
            }
            final int valuesStaticSqlLength = valuesStaticSql.length;
            final byte[] endOfValues = valuesStaticSql[valuesStaticSqlLength - 1];
            for (int j = 0; j < numValueRepeats; ++j) {
                for (int k = 1; k < valuesStaticSqlLength - 1; ++k) {
                    visitor.append(valuesStaticSql[k]).increment();
                }
                visitor.merge(endOfValues, beginOfValues).increment();
            }
            if (this.batchODKUClause != null) {
                final byte[][] batchOdkuStaticSql = this.batchODKUClause.staticSql;
                final byte[] beginOfOdku = batchOdkuStaticSql[0];
                visitor.decrement().merge(endOfValues, beginOfOdku).increment();
                final int batchOdkuStaticSqlLength = batchOdkuStaticSql.length;
                if (numBatch > 1) {
                    for (int l = 1; l < batchOdkuStaticSqlLength; ++l) {
                        visitor.append(batchOdkuStaticSql[l]).increment();
                    }
                }
                else {
                    visitor.decrement().append(batchOdkuStaticSql[batchOdkuStaticSqlLength - 1]);
                }
            }
            else {
                visitor.decrement().append(this.staticSql[this.staticSql.length - 1]);
            }
        }
        
        private ParseInfo(final byte[][] staticSql, final char firstStmtChar, final boolean foundLimitClause, final boolean foundLoadData, final boolean isOnDuplicateKeyUpdate, final int locationOfOnDuplicateKeyUpdate, final int statementLength, final int statementStartPos) {
            this.firstStmtChar = '\0';
            this.foundLimitClause = false;
            this.foundLoadData = false;
            this.lastUsed = 0L;
            this.statementLength = 0;
            this.statementStartPos = 0;
            this.canRewriteAsMultiValueInsert = false;
            this.staticSql = null;
            this.isOnDuplicateKeyUpdate = false;
            this.locationOfOnDuplicateKeyUpdate = -1;
            this.parametersInDuplicateKeyClause = false;
            this.firstStmtChar = firstStmtChar;
            this.foundLimitClause = foundLimitClause;
            this.foundLoadData = foundLoadData;
            this.isOnDuplicateKeyUpdate = isOnDuplicateKeyUpdate;
            this.locationOfOnDuplicateKeyUpdate = locationOfOnDuplicateKeyUpdate;
            this.statementLength = statementLength;
            this.statementStartPos = statementStartPos;
            this.staticSql = staticSql;
        }
    }
    
    class AppendingBatchVisitor implements BatchVisitor
    {
        LinkedList statementComponents;
        
        AppendingBatchVisitor() {
            this.statementComponents = new LinkedList();
        }
        
        public BatchVisitor append(final byte[] values) {
            this.statementComponents.addLast(values);
            return this;
        }
        
        public BatchVisitor increment() {
            return this;
        }
        
        public BatchVisitor decrement() {
            this.statementComponents.removeLast();
            return this;
        }
        
        public BatchVisitor merge(final byte[] front, final byte[] back) {
            final int mergedLength = front.length + back.length;
            final byte[] merged = new byte[mergedLength];
            System.arraycopy(front, 0, merged, 0, front.length);
            System.arraycopy(back, 0, merged, front.length, back.length);
            this.statementComponents.addLast(merged);
            return this;
        }
        
        public byte[][] getStaticSqlStrings() {
            final byte[][] asBytes = new byte[this.statementComponents.size()][];
            this.statementComponents.toArray(asBytes);
            return asBytes;
        }
        
        public String toString() {
            final StringBuffer buf = new StringBuffer();
            final Iterator iter = this.statementComponents.iterator();
            while (iter.hasNext()) {
                buf.append(new String(iter.next()));
            }
            return buf.toString();
        }
    }
    
    class EmulatedPreparedStatementBindings implements ParameterBindings
    {
        private ResultSetImpl bindingsAsRs;
        private boolean[] parameterIsNull;
        
        public EmulatedPreparedStatementBindings() throws SQLException {
            final List rows = new ArrayList();
            this.parameterIsNull = new boolean[PreparedStatement.this.parameterCount];
            System.arraycopy(PreparedStatement.this.isNull, 0, this.parameterIsNull, 0, PreparedStatement.this.parameterCount);
            final byte[][] rowData = new byte[PreparedStatement.this.parameterCount][];
            final Field[] typeMetadata = new Field[PreparedStatement.this.parameterCount];
            for (int i = 0; i < PreparedStatement.this.parameterCount; ++i) {
                if (PreparedStatement.this.batchCommandIndex == -1) {
                    rowData[i] = PreparedStatement.this.getBytesRepresentation(i);
                }
                else {
                    rowData[i] = PreparedStatement.this.getBytesRepresentationForBatch(i, PreparedStatement.this.batchCommandIndex);
                }
                int charsetIndex = 0;
                if (PreparedStatement.this.parameterTypes[i] == -2 || PreparedStatement.this.parameterTypes[i] == 2004) {
                    charsetIndex = 63;
                }
                else {
                    final String mysqlEncodingName = CharsetMapping.getMysqlEncodingForJavaEncoding(PreparedStatement.this.connection.getEncoding(), PreparedStatement.this.connection);
                    charsetIndex = CharsetMapping.getCharsetIndexForMysqlEncodingName(mysqlEncodingName);
                }
                final Field parameterMetadata = new Field(null, "parameter_" + (i + 1), charsetIndex, PreparedStatement.this.parameterTypes[i], rowData[i].length);
                parameterMetadata.setConnection(PreparedStatement.this.connection);
                typeMetadata[i] = parameterMetadata;
            }
            rows.add(new ByteArrayRow(rowData, PreparedStatement.this.getExceptionInterceptor()));
            (this.bindingsAsRs = new ResultSetImpl(PreparedStatement.this.connection.getCatalog(), typeMetadata, new RowDataStatic(rows), PreparedStatement.this.connection, null)).next();
        }
        
        public Array getArray(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getArray(parameterIndex);
        }
        
        public InputStream getAsciiStream(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getAsciiStream(parameterIndex);
        }
        
        public BigDecimal getBigDecimal(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getBigDecimal(parameterIndex);
        }
        
        public InputStream getBinaryStream(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getBinaryStream(parameterIndex);
        }
        
        public Blob getBlob(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getBlob(parameterIndex);
        }
        
        public boolean getBoolean(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getBoolean(parameterIndex);
        }
        
        public byte getByte(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getByte(parameterIndex);
        }
        
        public byte[] getBytes(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getBytes(parameterIndex);
        }
        
        public Reader getCharacterStream(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getCharacterStream(parameterIndex);
        }
        
        public Clob getClob(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getClob(parameterIndex);
        }
        
        public java.sql.Date getDate(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getDate(parameterIndex);
        }
        
        public double getDouble(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getDouble(parameterIndex);
        }
        
        public float getFloat(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getFloat(parameterIndex);
        }
        
        public int getInt(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getInt(parameterIndex);
        }
        
        public long getLong(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getLong(parameterIndex);
        }
        
        public Reader getNCharacterStream(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getCharacterStream(parameterIndex);
        }
        
        public Reader getNClob(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getCharacterStream(parameterIndex);
        }
        
        public Object getObject(final int parameterIndex) throws SQLException {
            PreparedStatement.this.checkBounds(parameterIndex, 0);
            if (this.parameterIsNull[parameterIndex - 1]) {
                return null;
            }
            switch (PreparedStatement.this.parameterTypes[parameterIndex - 1]) {
                case -6: {
                    return new Byte(this.getByte(parameterIndex));
                }
                case 5: {
                    return new Short(this.getShort(parameterIndex));
                }
                case 4: {
                    return new Integer(this.getInt(parameterIndex));
                }
                case -5: {
                    return new Long(this.getLong(parameterIndex));
                }
                case 6: {
                    return new Float(this.getFloat(parameterIndex));
                }
                case 8: {
                    return new Double(this.getDouble(parameterIndex));
                }
                default: {
                    return this.bindingsAsRs.getObject(parameterIndex);
                }
            }
        }
        
        public Ref getRef(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getRef(parameterIndex);
        }
        
        public short getShort(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getShort(parameterIndex);
        }
        
        public String getString(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getString(parameterIndex);
        }
        
        public Time getTime(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getTime(parameterIndex);
        }
        
        public Timestamp getTimestamp(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getTimestamp(parameterIndex);
        }
        
        public URL getURL(final int parameterIndex) throws SQLException {
            return this.bindingsAsRs.getURL(parameterIndex);
        }
        
        public boolean isNull(final int parameterIndex) throws SQLException {
            PreparedStatement.this.checkBounds(parameterIndex, 0);
            return this.parameterIsNull[parameterIndex - 1];
        }
    }
    
    interface BatchVisitor
    {
        BatchVisitor increment();
        
        BatchVisitor decrement();
        
        BatchVisitor append(final byte[] p0);
        
        BatchVisitor merge(final byte[] p0, final byte[] p1);
    }
}
