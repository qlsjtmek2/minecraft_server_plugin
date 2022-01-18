// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.io.IOException;
import java.util.GregorianCalendar;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.TimeZone;
import java.sql.Ref;
import java.sql.Clob;
import java.math.BigDecimal;
import java.sql.Array;
import java.io.Reader;
import java.sql.Blob;
import com.mysql.jdbc.profiler.ProfilerEvent;
import java.sql.ParameterMetaData;
import java.sql.ResultSetMetaData;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.BatchUpdateException;
import com.mysql.jdbc.exceptions.MySQLStatementCancelledException;
import com.mysql.jdbc.exceptions.MySQLTimeoutException;
import java.util.TimerTask;
import java.util.ArrayList;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.sql.Time;
import java.util.Calendar;
import java.sql.SQLException;
import java.lang.reflect.Constructor;

public class ServerPreparedStatement extends PreparedStatement
{
    private static final Constructor JDBC_4_SPS_CTOR;
    protected static final int BLOB_STREAM_READ_BUF_SIZE = 8192;
    private static final byte MAX_DATE_REP_LENGTH = 5;
    private static final byte MAX_DATETIME_REP_LENGTH = 12;
    private static final byte MAX_TIME_REP_LENGTH = 13;
    private boolean hasOnDuplicateKeyUpdate;
    private boolean detectedLongParameterSwitch;
    private int fieldCount;
    private boolean invalid;
    private SQLException invalidationException;
    private boolean isSelectQuery;
    private Buffer outByteBuffer;
    private BindValue[] parameterBindings;
    private Field[] parameterFields;
    private Field[] resultFields;
    private boolean sendTypesToServer;
    private long serverStatementId;
    private int stringTypeCode;
    private boolean serverNeedsResetBeforeEachExecution;
    protected boolean isCached;
    private boolean useAutoSlowLog;
    private Calendar serverTzCalendar;
    private Calendar defaultTzCalendar;
    private boolean hasCheckedRewrite;
    private boolean canRewrite;
    private int locationOfOnDuplicateKeyUpdate;
    
    private void storeTime(final Buffer intoBuf, final Time tm) throws SQLException {
        intoBuf.ensureCapacity(9);
        intoBuf.writeByte((byte)8);
        intoBuf.writeByte((byte)0);
        intoBuf.writeLong(0L);
        final Calendar sessionCalendar = this.getCalendarInstanceForSessionOrNew();
        synchronized (sessionCalendar) {
            final Date oldTime = sessionCalendar.getTime();
            try {
                sessionCalendar.setTime(tm);
                intoBuf.writeByte((byte)sessionCalendar.get(11));
                intoBuf.writeByte((byte)sessionCalendar.get(12));
                intoBuf.writeByte((byte)sessionCalendar.get(13));
            }
            finally {
                sessionCalendar.setTime(oldTime);
            }
        }
    }
    
    protected static ServerPreparedStatement getInstance(final MySQLConnection conn, final String sql, final String catalog, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        if (!Util.isJdbc4()) {
            return new ServerPreparedStatement(conn, sql, catalog, resultSetType, resultSetConcurrency);
        }
        try {
            return ServerPreparedStatement.JDBC_4_SPS_CTOR.newInstance(conn, sql, catalog, Constants.integerValueOf(resultSetType), Constants.integerValueOf(resultSetConcurrency));
        }
        catch (IllegalArgumentException e) {
            throw new SQLException(e.toString(), "S1000");
        }
        catch (InstantiationException e2) {
            throw new SQLException(e2.toString(), "S1000");
        }
        catch (IllegalAccessException e3) {
            throw new SQLException(e3.toString(), "S1000");
        }
        catch (InvocationTargetException e4) {
            final Throwable target = e4.getTargetException();
            if (target instanceof SQLException) {
                throw (SQLException)target;
            }
            throw new SQLException(target.toString(), "S1000");
        }
    }
    
    protected ServerPreparedStatement(final MySQLConnection conn, final String sql, final String catalog, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        super(conn, catalog);
        this.hasOnDuplicateKeyUpdate = false;
        this.detectedLongParameterSwitch = false;
        this.invalid = false;
        this.sendTypesToServer = false;
        this.stringTypeCode = 254;
        this.isCached = false;
        this.hasCheckedRewrite = false;
        this.canRewrite = false;
        this.locationOfOnDuplicateKeyUpdate = -2;
        this.checkNullOrEmptyQuery(sql);
        this.hasOnDuplicateKeyUpdate = this.containsOnDuplicateKeyInString(sql);
        final int startOfStatement = this.findStartOfStatement(sql);
        this.firstCharOfStmt = StringUtils.firstAlphaCharUc(sql, startOfStatement);
        this.isSelectQuery = ('S' == this.firstCharOfStmt);
        if (this.connection.versionMeetsMinimum(5, 0, 0)) {
            this.serverNeedsResetBeforeEachExecution = !this.connection.versionMeetsMinimum(5, 0, 3);
        }
        else {
            this.serverNeedsResetBeforeEachExecution = !this.connection.versionMeetsMinimum(4, 1, 10);
        }
        this.useAutoSlowLog = this.connection.getAutoSlowLog();
        this.useTrueBoolean = this.connection.versionMeetsMinimum(3, 21, 23);
        this.hasLimitClause = (StringUtils.indexOfIgnoreCase(sql, "LIMIT") != -1);
        final String statementComment = this.connection.getStatementComment();
        this.originalSql = ((statementComment == null) ? sql : ("/* " + statementComment + " */ " + sql));
        if (this.connection.versionMeetsMinimum(4, 1, 2)) {
            this.stringTypeCode = 253;
        }
        else {
            this.stringTypeCode = 254;
        }
        try {
            this.serverPrepare(sql);
        }
        catch (SQLException sqlEx) {
            this.realClose(false, true);
            throw sqlEx;
        }
        catch (Exception ex) {
            this.realClose(false, true);
            final SQLException sqlEx2 = SQLError.createSQLException(ex.toString(), "S1000", this.getExceptionInterceptor());
            sqlEx2.initCause(ex);
            throw sqlEx2;
        }
        this.setResultSetType(resultSetType);
        this.setResultSetConcurrency(resultSetConcurrency);
        this.parameterTypes = new int[this.parameterCount];
    }
    
    public synchronized void addBatch() throws SQLException {
        this.checkClosed();
        if (this.batchedArgs == null) {
            this.batchedArgs = new ArrayList();
        }
        this.batchedArgs.add(new BatchedBindValues(this.parameterBindings));
    }
    
    protected String asSql(final boolean quoteStreamsAndUnknowns) throws SQLException {
        if (this.isClosed) {
            return "statement has been closed, no further internal information available";
        }
        PreparedStatement pStmtForSub = null;
        try {
            pStmtForSub = PreparedStatement.getInstance(this.connection, this.originalSql, this.currentCatalog);
            for (int numParameters = pStmtForSub.parameterCount, ourNumParameters = this.parameterCount, i = 0; i < numParameters && i < ourNumParameters; ++i) {
                if (this.parameterBindings[i] != null) {
                    if (this.parameterBindings[i].isNull) {
                        pStmtForSub.setNull(i + 1, 0);
                    }
                    else {
                        final BindValue bindValue = this.parameterBindings[i];
                        switch (bindValue.bufferType) {
                            case 0: {
                                pStmtForSub.setByte(i + 1, bindValue.byteBinding);
                                break;
                            }
                            case 1: {
                                pStmtForSub.setShort(i + 1, bindValue.shortBinding);
                                break;
                            }
                            case 2: {
                                pStmtForSub.setInt(i + 1, bindValue.intBinding);
                                break;
                            }
                            case 7: {
                                pStmtForSub.setLong(i + 1, bindValue.longBinding);
                                break;
                            }
                            case 3: {
                                pStmtForSub.setFloat(i + 1, bindValue.floatBinding);
                                break;
                            }
                            case 4: {
                                pStmtForSub.setDouble(i + 1, bindValue.doubleBinding);
                                break;
                            }
                            default: {
                                pStmtForSub.setObject(i + 1, this.parameterBindings[i].value);
                                break;
                            }
                        }
                    }
                }
            }
            return pStmtForSub.asSql(quoteStreamsAndUnknowns);
        }
        finally {
            if (pStmtForSub != null) {
                try {
                    pStmtForSub.close();
                }
                catch (SQLException ex) {}
            }
        }
    }
    
    protected void checkClosed() throws SQLException {
        if (this.invalid) {
            throw this.invalidationException;
        }
        super.checkClosed();
    }
    
    public void clearParameters() throws SQLException {
        this.checkClosed();
        this.clearParametersInternal(true);
    }
    
    private void clearParametersInternal(final boolean clearServerParameters) throws SQLException {
        boolean hadLongData = false;
        if (this.parameterBindings != null) {
            for (int i = 0; i < this.parameterCount; ++i) {
                if (this.parameterBindings[i] != null && this.parameterBindings[i].isLongData) {
                    hadLongData = true;
                }
                this.parameterBindings[i].reset();
            }
        }
        if (clearServerParameters && hadLongData) {
            this.serverResetStatement();
            this.detectedLongParameterSwitch = false;
        }
    }
    
    protected void setClosed(final boolean flag) {
        this.isClosed = flag;
    }
    
    public synchronized void close() throws SQLException {
        if (this.isCached && !this.isClosed) {
            this.clearParameters();
            this.isClosed = true;
            this.connection.recachePreparedStatement(this);
            return;
        }
        this.realClose(true, true);
    }
    
    private void dumpCloseForTestcase() {
        final StringBuffer buf = new StringBuffer();
        this.connection.generateConnectionCommentBlock(buf);
        buf.append("DEALLOCATE PREPARE debug_stmt_");
        buf.append(this.statementId);
        buf.append(";\n");
        this.connection.dumpTestcaseQuery(buf.toString());
    }
    
    private void dumpExecuteForTestcase() throws SQLException {
        final StringBuffer buf = new StringBuffer();
        for (int i = 0; i < this.parameterCount; ++i) {
            this.connection.generateConnectionCommentBlock(buf);
            buf.append("SET @debug_stmt_param");
            buf.append(this.statementId);
            buf.append("_");
            buf.append(i);
            buf.append("=");
            if (this.parameterBindings[i].isNull) {
                buf.append("NULL");
            }
            else {
                buf.append(this.parameterBindings[i].toString(true));
            }
            buf.append(";\n");
        }
        this.connection.generateConnectionCommentBlock(buf);
        buf.append("EXECUTE debug_stmt_");
        buf.append(this.statementId);
        if (this.parameterCount > 0) {
            buf.append(" USING ");
            for (int i = 0; i < this.parameterCount; ++i) {
                if (i > 0) {
                    buf.append(", ");
                }
                buf.append("@debug_stmt_param");
                buf.append(this.statementId);
                buf.append("_");
                buf.append(i);
            }
        }
        buf.append(";\n");
        this.connection.dumpTestcaseQuery(buf.toString());
    }
    
    private void dumpPrepareForTestcase() throws SQLException {
        final StringBuffer buf = new StringBuffer(this.originalSql.length() + 64);
        this.connection.generateConnectionCommentBlock(buf);
        buf.append("PREPARE debug_stmt_");
        buf.append(this.statementId);
        buf.append(" FROM \"");
        buf.append(this.originalSql);
        buf.append("\";\n");
        this.connection.dumpTestcaseQuery(buf.toString());
    }
    
    protected int[] executeBatchSerially(final int batchTimeout) throws SQLException {
        final MySQLConnection locallyScopedConn = this.connection;
        if (locallyScopedConn == null) {
            this.checkClosed();
        }
        if (locallyScopedConn.isReadOnly()) {
            throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.2") + Messages.getString("ServerPreparedStatement.3"), "S1009", this.getExceptionInterceptor());
        }
        this.checkClosed();
        synchronized (locallyScopedConn.getMutex()) {
            this.clearWarnings();
            final BindValue[] oldBindValues = this.parameterBindings;
            try {
                int[] updateCounts = null;
                if (this.batchedArgs != null) {
                    final int nbrCommands = this.batchedArgs.size();
                    updateCounts = new int[nbrCommands];
                    if (this.retrieveGeneratedKeys) {
                        this.batchedGeneratedKeys = new ArrayList(nbrCommands);
                    }
                    for (int i = 0; i < nbrCommands; ++i) {
                        updateCounts[i] = -3;
                    }
                    SQLException sqlEx = null;
                    int commandIndex = 0;
                    BindValue[] previousBindValuesForBatch = null;
                    CancelTask timeoutTask = null;
                    try {
                        if (locallyScopedConn.getEnableQueryTimeouts() && batchTimeout != 0 && locallyScopedConn.versionMeetsMinimum(5, 0, 0)) {
                            timeoutTask = new CancelTask(this, this);
                            locallyScopedConn.getCancelTimer().schedule(timeoutTask, batchTimeout);
                        }
                        for (commandIndex = 0; commandIndex < nbrCommands; ++commandIndex) {
                            final Object arg = this.batchedArgs.get(commandIndex);
                            if (arg instanceof String) {
                                updateCounts[commandIndex] = this.executeUpdate((String)arg);
                            }
                            else {
                                this.parameterBindings = ((BatchedBindValues)arg).batchedParameterValues;
                                try {
                                    if (previousBindValuesForBatch != null) {
                                        for (int j = 0; j < this.parameterBindings.length; ++j) {
                                            if (this.parameterBindings[j].bufferType != previousBindValuesForBatch[j].bufferType) {
                                                this.sendTypesToServer = true;
                                                break;
                                            }
                                        }
                                    }
                                    try {
                                        updateCounts[commandIndex] = this.executeUpdate(false, true);
                                    }
                                    finally {
                                        previousBindValuesForBatch = this.parameterBindings;
                                    }
                                    if (this.retrieveGeneratedKeys) {
                                        ResultSet rs = null;
                                        try {
                                            rs = this.getGeneratedKeysInternal();
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
                                    updateCounts[commandIndex] = -3;
                                    if (!this.continueBatchOnError || ex instanceof MySQLTimeoutException || ex instanceof MySQLStatementCancelledException || this.hasDeadlockOrTimeoutRolledBackTx(ex)) {
                                        final int[] newUpdateCounts = new int[commandIndex];
                                        System.arraycopy(updateCounts, 0, newUpdateCounts, 0, commandIndex);
                                        throw new BatchUpdateException(ex.getMessage(), ex.getSQLState(), ex.getErrorCode(), newUpdateCounts);
                                    }
                                    sqlEx = ex;
                                }
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
                    if (sqlEx != null) {
                        throw new BatchUpdateException(sqlEx.getMessage(), sqlEx.getSQLState(), sqlEx.getErrorCode(), updateCounts);
                    }
                }
                return (updateCounts != null) ? updateCounts : new int[0];
            }
            finally {
                this.parameterBindings = oldBindValues;
                this.sendTypesToServer = true;
                this.clearBatch();
            }
        }
    }
    
    protected ResultSetInternalMethods executeInternal(final int maxRowsToRetrieve, final Buffer sendPacket, final boolean createStreamingResultSet, final boolean queryIsSelectOnly, final Field[] metadataFromCache, final boolean isBatch) throws SQLException {
        ++this.numberOfExecutions;
        try {
            return this.serverExecute(maxRowsToRetrieve, createStreamingResultSet, metadataFromCache);
        }
        catch (SQLException sqlEx) {
            if (this.connection.getEnablePacketDebug()) {
                this.connection.getIO().dumpPacketRingBuffer();
            }
            if (this.connection.getDumpQueriesOnException()) {
                final String extractedSql = this.toString();
                final StringBuffer messageBuf = new StringBuffer(extractedSql.length() + 32);
                messageBuf.append("\n\nQuery being executed when exception was thrown:\n");
                messageBuf.append(extractedSql);
                messageBuf.append("\n\n");
                sqlEx = ConnectionImpl.appendMessageToException(sqlEx, messageBuf.toString(), this.getExceptionInterceptor());
            }
            throw sqlEx;
        }
        catch (Exception ex) {
            if (this.connection.getEnablePacketDebug()) {
                this.connection.getIO().dumpPacketRingBuffer();
            }
            SQLException sqlEx2 = SQLError.createSQLException(ex.toString(), "S1000", this.getExceptionInterceptor());
            if (this.connection.getDumpQueriesOnException()) {
                final String extractedSql2 = this.toString();
                final StringBuffer messageBuf2 = new StringBuffer(extractedSql2.length() + 32);
                messageBuf2.append("\n\nQuery being executed when exception was thrown:\n");
                messageBuf2.append(extractedSql2);
                messageBuf2.append("\n\n");
                sqlEx2 = ConnectionImpl.appendMessageToException(sqlEx2, messageBuf2.toString(), this.getExceptionInterceptor());
            }
            sqlEx2.initCause(ex);
            throw sqlEx2;
        }
    }
    
    protected Buffer fillSendPacket() throws SQLException {
        return null;
    }
    
    protected Buffer fillSendPacket(final byte[][] batchedParameterStrings, final InputStream[] batchedParameterStreams, final boolean[] batchedIsStream, final int[] batchedStreamLengths) throws SQLException {
        return null;
    }
    
    protected BindValue getBinding(int parameterIndex, final boolean forLongData) throws SQLException {
        this.checkClosed();
        if (this.parameterBindings.length == 0) {
            throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.8"), "S1009", this.getExceptionInterceptor());
        }
        if (--parameterIndex < 0 || parameterIndex >= this.parameterBindings.length) {
            throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.9") + (parameterIndex + 1) + Messages.getString("ServerPreparedStatement.10") + this.parameterBindings.length, "S1009", this.getExceptionInterceptor());
        }
        if (this.parameterBindings[parameterIndex] == null) {
            this.parameterBindings[parameterIndex] = new BindValue();
        }
        else if (this.parameterBindings[parameterIndex].isLongData && !forLongData) {
            this.detectedLongParameterSwitch = true;
        }
        this.parameterBindings[parameterIndex].isSet = true;
        this.parameterBindings[parameterIndex].boundBeforeExecutionNum = this.numberOfExecutions;
        return this.parameterBindings[parameterIndex];
    }
    
    byte[] getBytes(final int parameterIndex) throws SQLException {
        final BindValue bindValue = this.getBinding(parameterIndex, false);
        if (bindValue.isNull) {
            return null;
        }
        if (bindValue.isLongData) {
            throw SQLError.notImplemented();
        }
        if (this.outByteBuffer == null) {
            this.outByteBuffer = new Buffer(this.connection.getNetBufferLength());
        }
        this.outByteBuffer.clear();
        final int originalPosition = this.outByteBuffer.getPosition();
        this.storeBinding(this.outByteBuffer, bindValue, this.connection.getIO());
        final int newPosition = this.outByteBuffer.getPosition();
        final int length = newPosition - originalPosition;
        final byte[] valueAsBytes = new byte[length];
        System.arraycopy(this.outByteBuffer.getByteBuffer(), originalPosition, valueAsBytes, 0, length);
        return valueAsBytes;
    }
    
    public ResultSetMetaData getMetaData() throws SQLException {
        this.checkClosed();
        if (this.resultFields == null) {
            return null;
        }
        return new com.mysql.jdbc.ResultSetMetaData(this.resultFields, this.connection.getUseOldAliasMetadataBehavior(), this.getExceptionInterceptor());
    }
    
    public ParameterMetaData getParameterMetaData() throws SQLException {
        this.checkClosed();
        if (this.parameterMetaData == null) {
            this.parameterMetaData = new MysqlParameterMetadata(this.parameterFields, this.parameterCount, this.getExceptionInterceptor());
        }
        return this.parameterMetaData;
    }
    
    boolean isNull(final int paramIndex) {
        throw new IllegalArgumentException(Messages.getString("ServerPreparedStatement.7"));
    }
    
    protected void realClose(final boolean calledExplicitly, final boolean closeOpenResults) throws SQLException {
        if (this.isClosed) {
            return;
        }
        if (this.connection != null) {
            if (this.connection.getAutoGenerateTestcaseScript()) {
                this.dumpCloseForTestcase();
            }
            SQLException exceptionDuringClose = null;
            if (calledExplicitly && !this.connection.isClosed()) {
                synchronized (this.connection.getMutex()) {
                    try {
                        final MysqlIO mysql = this.connection.getIO();
                        final Buffer packet = mysql.getSharedSendPacket();
                        packet.writeByte((byte)25);
                        packet.writeLong(this.serverStatementId);
                        mysql.sendCommand(25, null, packet, true, null, 0);
                    }
                    catch (SQLException sqlEx) {
                        exceptionDuringClose = sqlEx;
                    }
                }
            }
            super.realClose(calledExplicitly, closeOpenResults);
            this.clearParametersInternal(false);
            this.parameterBindings = null;
            this.parameterFields = null;
            this.resultFields = null;
            if (exceptionDuringClose != null) {
                throw exceptionDuringClose;
            }
        }
    }
    
    protected void rePrepare() throws SQLException {
        this.invalidationException = null;
        try {
            this.serverPrepare(this.originalSql);
        }
        catch (SQLException sqlEx) {
            this.invalidationException = sqlEx;
        }
        catch (Exception ex) {
            (this.invalidationException = SQLError.createSQLException(ex.toString(), "S1000", this.getExceptionInterceptor())).initCause(ex);
        }
        if (this.invalidationException != null) {
            this.invalid = true;
            this.parameterBindings = null;
            this.parameterFields = null;
            this.resultFields = null;
            if (this.results != null) {
                try {
                    this.results.close();
                }
                catch (Exception ex2) {}
            }
            if (this.connection != null) {
                if (this.maxRowsChanged) {
                    this.connection.unsetMaxRows(this);
                }
                if (!this.connection.getDontTrackOpenResources()) {
                    this.connection.unregisterStatement(this);
                }
            }
        }
    }
    
    private ResultSetInternalMethods serverExecute(final int maxRowsToRetrieve, final boolean createStreamingResultSet, final Field[] metadataFromCache) throws SQLException {
        synchronized (this.connection.getMutex()) {
            final MysqlIO mysql = this.connection.getIO();
            if (mysql.shouldIntercept()) {
                final ResultSetInternalMethods interceptedResults = mysql.invokeStatementInterceptorsPre(this.originalSql, this, true);
                if (interceptedResults != null) {
                    return interceptedResults;
                }
            }
            if (this.detectedLongParameterSwitch) {
                boolean firstFound = false;
                long boundTimeToCheck = 0L;
                for (int i = 0; i < this.parameterCount - 1; ++i) {
                    if (this.parameterBindings[i].isLongData) {
                        if (firstFound && boundTimeToCheck != this.parameterBindings[i].boundBeforeExecutionNum) {
                            throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.11") + Messages.getString("ServerPreparedStatement.12"), "S1C00", this.getExceptionInterceptor());
                        }
                        firstFound = true;
                        boundTimeToCheck = this.parameterBindings[i].boundBeforeExecutionNum;
                    }
                }
                this.serverResetStatement();
            }
            for (int j = 0; j < this.parameterCount; ++j) {
                if (!this.parameterBindings[j].isSet) {
                    throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.13") + (j + 1) + Messages.getString("ServerPreparedStatement.14"), "S1009", this.getExceptionInterceptor());
                }
            }
            for (int j = 0; j < this.parameterCount; ++j) {
                if (this.parameterBindings[j].isLongData) {
                    this.serverLongData(j, this.parameterBindings[j]);
                }
            }
            if (this.connection.getAutoGenerateTestcaseScript()) {
                this.dumpExecuteForTestcase();
            }
            final Buffer packet = mysql.getSharedSendPacket();
            packet.clear();
            packet.writeByte((byte)23);
            packet.writeLong(this.serverStatementId);
            boolean usingCursor = false;
            if (this.connection.versionMeetsMinimum(4, 1, 2)) {
                if (this.resultFields != null && this.connection.isCursorFetchEnabled() && this.getResultSetType() == 1003 && this.getResultSetConcurrency() == 1007 && this.getFetchSize() > 0) {
                    packet.writeByte((byte)1);
                    usingCursor = true;
                }
                else {
                    packet.writeByte((byte)0);
                }
                packet.writeLong(1L);
            }
            final int nullCount = (this.parameterCount + 7) / 8;
            final int nullBitsPosition = packet.getPosition();
            for (int k = 0; k < nullCount; ++k) {
                packet.writeByte((byte)0);
            }
            final byte[] nullBitsBuffer = new byte[nullCount];
            packet.writeByte((byte)(this.sendTypesToServer ? 1 : 0));
            if (this.sendTypesToServer) {
                for (int l = 0; l < this.parameterCount; ++l) {
                    packet.writeInt(this.parameterBindings[l].bufferType);
                }
            }
            for (int l = 0; l < this.parameterCount; ++l) {
                if (!this.parameterBindings[l].isLongData) {
                    if (!this.parameterBindings[l].isNull) {
                        this.storeBinding(packet, this.parameterBindings[l], mysql);
                    }
                    else {
                        final byte[] array = nullBitsBuffer;
                        final int n = l / 8;
                        array[n] |= (byte)(1 << (l & 0x7));
                    }
                }
            }
            final int endPosition = packet.getPosition();
            packet.setPosition(nullBitsPosition);
            packet.writeBytesNoNull(nullBitsBuffer);
            packet.setPosition(endPosition);
            long begin = 0L;
            final boolean logSlowQueries = this.connection.getLogSlowQueries();
            final boolean gatherPerformanceMetrics = this.connection.getGatherPerformanceMetrics();
            if (this.profileSQL || logSlowQueries || gatherPerformanceMetrics) {
                begin = mysql.getCurrentTimeNanosOrMillis();
            }
            this.resetCancelledState();
            CancelTask timeoutTask = null;
            try {
                if (this.connection.getEnableQueryTimeouts() && this.timeoutInMillis != 0 && this.connection.versionMeetsMinimum(5, 0, 0)) {
                    timeoutTask = new CancelTask(this, this);
                    this.connection.getCancelTimer().schedule(timeoutTask, this.timeoutInMillis);
                }
                final Buffer resultPacket = mysql.sendCommand(23, null, packet, false, null, 0);
                long queryEndTime = 0L;
                if (logSlowQueries || gatherPerformanceMetrics || this.profileSQL) {
                    queryEndTime = mysql.getCurrentTimeNanosOrMillis();
                }
                if (timeoutTask != null) {
                    timeoutTask.cancel();
                    this.connection.getCancelTimer().purge();
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
                boolean queryWasSlow = false;
                if (logSlowQueries || gatherPerformanceMetrics) {
                    final long elapsedTime = queryEndTime - begin;
                    if (logSlowQueries) {
                        if (this.useAutoSlowLog) {
                            queryWasSlow = (elapsedTime > this.connection.getSlowQueryThresholdMillis());
                        }
                        else {
                            queryWasSlow = this.connection.isAbonormallyLongQuery(elapsedTime);
                            this.connection.reportQueryTime(elapsedTime);
                        }
                    }
                    if (queryWasSlow) {
                        final StringBuffer mesgBuf = new StringBuffer(48 + this.originalSql.length());
                        mesgBuf.append(Messages.getString("ServerPreparedStatement.15"));
                        mesgBuf.append(mysql.getSlowQueryThreshold());
                        mesgBuf.append(Messages.getString("ServerPreparedStatement.15a"));
                        mesgBuf.append(elapsedTime);
                        mesgBuf.append(Messages.getString("ServerPreparedStatement.16"));
                        mesgBuf.append("as prepared: ");
                        mesgBuf.append(this.originalSql);
                        mesgBuf.append("\n\n with parameters bound:\n\n");
                        mesgBuf.append(this.asSql(true));
                        this.eventSink.consumeEvent(new ProfilerEvent((byte)6, "", this.currentCatalog, this.connection.getId(), this.getId(), 0, System.currentTimeMillis(), elapsedTime, mysql.getQueryTimingUnits(), null, new Throwable(), mesgBuf.toString()));
                    }
                    if (gatherPerformanceMetrics) {
                        this.connection.registerQueryExecutionTime(elapsedTime);
                    }
                }
                this.connection.incrementNumberOfPreparedExecutes();
                if (this.profileSQL) {
                    (this.eventSink = ProfilerEventHandlerFactory.getInstance(this.connection)).consumeEvent(new ProfilerEvent((byte)4, "", this.currentCatalog, this.connectionId, this.statementId, -1, System.currentTimeMillis(), (int)(mysql.getCurrentTimeNanosOrMillis() - begin), mysql.getQueryTimingUnits(), null, new Throwable(), this.truncateQueryToLog(this.asSql(true))));
                }
                ResultSetInternalMethods rs = mysql.readAllResults(this, maxRowsToRetrieve, this.resultSetType, this.resultSetConcurrency, createStreamingResultSet, this.currentCatalog, resultPacket, true, this.fieldCount, metadataFromCache);
                if (mysql.shouldIntercept()) {
                    final ResultSetInternalMethods interceptedResults2 = mysql.invokeStatementInterceptorsPost(this.originalSql, this, rs, true, null);
                    if (interceptedResults2 != null) {
                        rs = interceptedResults2;
                    }
                }
                if (this.profileSQL) {
                    final long fetchEndTime = mysql.getCurrentTimeNanosOrMillis();
                    this.eventSink.consumeEvent(new ProfilerEvent((byte)5, "", this.currentCatalog, this.connection.getId(), this.getId(), 0, System.currentTimeMillis(), fetchEndTime - queryEndTime, mysql.getQueryTimingUnits(), null, new Throwable(), null));
                }
                if (queryWasSlow && this.connection.getExplainSlowQueries()) {
                    final String queryAsString = this.asSql(true);
                    mysql.explainSlowQuery(queryAsString.getBytes(), queryAsString);
                }
                if (!createStreamingResultSet && this.serverNeedsResetBeforeEachExecution) {
                    this.serverResetStatement();
                }
                this.sendTypesToServer = false;
                this.results = rs;
                if (mysql.hadWarnings()) {
                    mysql.scanForAndThrowDataTruncation();
                }
                return rs;
            }
            catch (SQLException sqlEx) {
                if (mysql.shouldIntercept()) {
                    mysql.invokeStatementInterceptorsPost(this.originalSql, this, null, true, sqlEx);
                }
                throw sqlEx;
            }
            finally {
                if (timeoutTask != null) {
                    timeoutTask.cancel();
                    this.connection.getCancelTimer().purge();
                }
            }
        }
    }
    
    private void serverLongData(final int parameterIndex, final BindValue longData) throws SQLException {
        synchronized (this.connection.getMutex()) {
            final MysqlIO mysql = this.connection.getIO();
            final Buffer packet = mysql.getSharedSendPacket();
            final Object value = longData.value;
            if (value instanceof byte[]) {
                packet.clear();
                packet.writeByte((byte)24);
                packet.writeLong(this.serverStatementId);
                packet.writeInt(parameterIndex);
                packet.writeBytesNoNull((byte[])longData.value);
                mysql.sendCommand(24, null, packet, true, null, 0);
            }
            else if (value instanceof InputStream) {
                this.storeStream(mysql, parameterIndex, packet, (InputStream)value);
            }
            else if (value instanceof Blob) {
                this.storeStream(mysql, parameterIndex, packet, ((Blob)value).getBinaryStream());
            }
            else {
                if (!(value instanceof Reader)) {
                    throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.18") + value.getClass().getName() + "'", "S1009", this.getExceptionInterceptor());
                }
                this.storeReader(mysql, parameterIndex, packet, (Reader)value);
            }
        }
    }
    
    private void serverPrepare(final String sql) throws SQLException {
        synchronized (this.connection.getMutex()) {
            final MysqlIO mysql = this.connection.getIO();
            if (this.connection.getAutoGenerateTestcaseScript()) {
                this.dumpPrepareForTestcase();
            }
            try {
                long begin = 0L;
                if (StringUtils.startsWithIgnoreCaseAndWs(sql, "LOAD DATA")) {
                    this.isLoadDataQuery = true;
                }
                else {
                    this.isLoadDataQuery = false;
                }
                if (this.connection.getProfileSql()) {
                    begin = System.currentTimeMillis();
                }
                String characterEncoding = null;
                final String connectionEncoding = this.connection.getEncoding();
                if (!this.isLoadDataQuery && this.connection.getUseUnicode() && connectionEncoding != null) {
                    characterEncoding = connectionEncoding;
                }
                final Buffer prepareResultPacket = mysql.sendCommand(22, sql, null, false, characterEncoding, 0);
                if (this.connection.versionMeetsMinimum(4, 1, 1)) {
                    prepareResultPacket.setPosition(1);
                }
                else {
                    prepareResultPacket.setPosition(0);
                }
                this.serverStatementId = prepareResultPacket.readLong();
                this.fieldCount = prepareResultPacket.readInt();
                this.parameterCount = prepareResultPacket.readInt();
                this.parameterBindings = new BindValue[this.parameterCount];
                for (int i = 0; i < this.parameterCount; ++i) {
                    this.parameterBindings[i] = new BindValue();
                }
                this.connection.incrementNumberOfPrepares();
                if (this.profileSQL) {
                    this.eventSink.consumeEvent(new ProfilerEvent((byte)2, "", this.currentCatalog, this.connectionId, this.statementId, -1, System.currentTimeMillis(), mysql.getCurrentTimeNanosOrMillis() - begin, mysql.getQueryTimingUnits(), null, new Throwable(), this.truncateQueryToLog(sql)));
                }
                if (this.parameterCount > 0 && this.connection.versionMeetsMinimum(4, 1, 2) && !mysql.isVersion(5, 0, 0)) {
                    this.parameterFields = new Field[this.parameterCount];
                    Buffer metaDataPacket = mysql.readPacket();
                    for (int j = 0; !metaDataPacket.isLastDataPacket() && j < this.parameterCount; this.parameterFields[j++] = mysql.unpackField(metaDataPacket, false), metaDataPacket = mysql.readPacket()) {}
                }
                if (this.fieldCount > 0) {
                    this.resultFields = new Field[this.fieldCount];
                    Buffer fieldPacket = mysql.readPacket();
                    for (int j = 0; !fieldPacket.isLastDataPacket() && j < this.fieldCount; this.resultFields[j++] = mysql.unpackField(fieldPacket, false), fieldPacket = mysql.readPacket()) {}
                }
            }
            catch (SQLException sqlEx) {
                if (this.connection.getDumpQueriesOnException()) {
                    final StringBuffer messageBuf = new StringBuffer(this.originalSql.length() + 32);
                    messageBuf.append("\n\nQuery being prepared when exception was thrown:\n\n");
                    messageBuf.append(this.originalSql);
                    sqlEx = ConnectionImpl.appendMessageToException(sqlEx, messageBuf.toString(), this.getExceptionInterceptor());
                }
                throw sqlEx;
            }
            finally {
                this.connection.getIO().clearInputStream();
            }
        }
    }
    
    private String truncateQueryToLog(final String sql) {
        String query = null;
        if (sql.length() > this.connection.getMaxQuerySizeToLog()) {
            final StringBuffer queryBuf = new StringBuffer(this.connection.getMaxQuerySizeToLog() + 12);
            queryBuf.append(sql.substring(0, this.connection.getMaxQuerySizeToLog()));
            queryBuf.append(Messages.getString("MysqlIO.25"));
            query = queryBuf.toString();
        }
        else {
            query = sql;
        }
        return query;
    }
    
    private void serverResetStatement() throws SQLException {
        synchronized (this.connection.getMutex()) {
            final MysqlIO mysql = this.connection.getIO();
            final Buffer packet = mysql.getSharedSendPacket();
            packet.clear();
            packet.writeByte((byte)26);
            packet.writeLong(this.serverStatementId);
            try {
                mysql.sendCommand(26, null, packet, !this.connection.versionMeetsMinimum(4, 1, 2), null, 0);
            }
            catch (SQLException sqlEx) {
                throw sqlEx;
            }
            catch (Exception ex) {
                final SQLException sqlEx2 = SQLError.createSQLException(ex.toString(), "S1000", this.getExceptionInterceptor());
                sqlEx2.initCause(ex);
                throw sqlEx2;
            }
            finally {
                mysql.clearInputStream();
            }
        }
    }
    
    public void setArray(final int i, final Array x) throws SQLException {
        throw SQLError.notImplemented();
    }
    
    public void setAsciiStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        this.checkClosed();
        if (x == null) {
            this.setNull(parameterIndex, -2);
        }
        else {
            final BindValue binding = this.getBinding(parameterIndex, true);
            this.setType(binding, 252);
            binding.value = x;
            binding.isNull = false;
            binding.isLongData = true;
            if (this.connection.getUseStreamLengthsInPrepStmts()) {
                binding.bindLength = length;
            }
            else {
                binding.bindLength = -1L;
            }
        }
    }
    
    public void setBigDecimal(final int parameterIndex, final BigDecimal x) throws SQLException {
        this.checkClosed();
        if (x == null) {
            this.setNull(parameterIndex, 3);
        }
        else {
            final BindValue binding = this.getBinding(parameterIndex, false);
            if (this.connection.versionMeetsMinimum(5, 0, 3)) {
                this.setType(binding, 246);
            }
            else {
                this.setType(binding, this.stringTypeCode);
            }
            binding.value = StringUtils.fixDecimalExponent(StringUtils.consistentToString(x));
            binding.isNull = false;
            binding.isLongData = false;
        }
    }
    
    public void setBinaryStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        this.checkClosed();
        if (x == null) {
            this.setNull(parameterIndex, -2);
        }
        else {
            final BindValue binding = this.getBinding(parameterIndex, true);
            this.setType(binding, 252);
            binding.value = x;
            binding.isNull = false;
            binding.isLongData = true;
            if (this.connection.getUseStreamLengthsInPrepStmts()) {
                binding.bindLength = length;
            }
            else {
                binding.bindLength = -1L;
            }
        }
    }
    
    public void setBlob(final int parameterIndex, final Blob x) throws SQLException {
        this.checkClosed();
        if (x == null) {
            this.setNull(parameterIndex, -2);
        }
        else {
            final BindValue binding = this.getBinding(parameterIndex, true);
            this.setType(binding, 252);
            binding.value = x;
            binding.isNull = false;
            binding.isLongData = true;
            if (this.connection.getUseStreamLengthsInPrepStmts()) {
                binding.bindLength = x.length();
            }
            else {
                binding.bindLength = -1L;
            }
        }
    }
    
    public void setBoolean(final int parameterIndex, final boolean x) throws SQLException {
        this.setByte(parameterIndex, (byte)(x ? 1 : 0));
    }
    
    public void setByte(final int parameterIndex, final byte x) throws SQLException {
        this.checkClosed();
        final BindValue binding = this.getBinding(parameterIndex, false);
        this.setType(binding, 1);
        binding.value = null;
        binding.byteBinding = x;
        binding.isNull = false;
        binding.isLongData = false;
    }
    
    public void setBytes(final int parameterIndex, final byte[] x) throws SQLException {
        this.checkClosed();
        if (x == null) {
            this.setNull(parameterIndex, -2);
        }
        else {
            final BindValue binding = this.getBinding(parameterIndex, false);
            this.setType(binding, 253);
            binding.value = x;
            binding.isNull = false;
            binding.isLongData = false;
        }
    }
    
    public void setCharacterStream(final int parameterIndex, final Reader reader, final int length) throws SQLException {
        this.checkClosed();
        if (reader == null) {
            this.setNull(parameterIndex, -2);
        }
        else {
            final BindValue binding = this.getBinding(parameterIndex, true);
            this.setType(binding, 252);
            binding.value = reader;
            binding.isNull = false;
            binding.isLongData = true;
            if (this.connection.getUseStreamLengthsInPrepStmts()) {
                binding.bindLength = length;
            }
            else {
                binding.bindLength = -1L;
            }
        }
    }
    
    public void setClob(final int parameterIndex, final Clob x) throws SQLException {
        this.checkClosed();
        if (x == null) {
            this.setNull(parameterIndex, -2);
        }
        else {
            final BindValue binding = this.getBinding(parameterIndex, true);
            this.setType(binding, 252);
            binding.value = x.getCharacterStream();
            binding.isNull = false;
            binding.isLongData = true;
            if (this.connection.getUseStreamLengthsInPrepStmts()) {
                binding.bindLength = x.length();
            }
            else {
                binding.bindLength = -1L;
            }
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
            final BindValue binding = this.getBinding(parameterIndex, false);
            this.setType(binding, 10);
            binding.value = x;
            binding.isNull = false;
            binding.isLongData = false;
        }
    }
    
    public void setDouble(final int parameterIndex, final double x) throws SQLException {
        this.checkClosed();
        if (!this.connection.getAllowNanAndInf() && (x == Double.POSITIVE_INFINITY || x == Double.NEGATIVE_INFINITY || Double.isNaN(x))) {
            throw SQLError.createSQLException("'" + x + "' is not a valid numeric or approximate numeric value", "S1009", this.getExceptionInterceptor());
        }
        final BindValue binding = this.getBinding(parameterIndex, false);
        this.setType(binding, 5);
        binding.value = null;
        binding.doubleBinding = x;
        binding.isNull = false;
        binding.isLongData = false;
    }
    
    public void setFloat(final int parameterIndex, final float x) throws SQLException {
        this.checkClosed();
        final BindValue binding = this.getBinding(parameterIndex, false);
        this.setType(binding, 4);
        binding.value = null;
        binding.floatBinding = x;
        binding.isNull = false;
        binding.isLongData = false;
    }
    
    public void setInt(final int parameterIndex, final int x) throws SQLException {
        this.checkClosed();
        final BindValue binding = this.getBinding(parameterIndex, false);
        this.setType(binding, 3);
        binding.value = null;
        binding.intBinding = x;
        binding.isNull = false;
        binding.isLongData = false;
    }
    
    public void setLong(final int parameterIndex, final long x) throws SQLException {
        this.checkClosed();
        final BindValue binding = this.getBinding(parameterIndex, false);
        this.setType(binding, 8);
        binding.value = null;
        binding.longBinding = x;
        binding.isNull = false;
        binding.isLongData = false;
    }
    
    public void setNull(final int parameterIndex, final int sqlType) throws SQLException {
        this.checkClosed();
        final BindValue binding = this.getBinding(parameterIndex, false);
        if (binding.bufferType == 0) {
            this.setType(binding, 6);
        }
        binding.value = null;
        binding.isNull = true;
        binding.isLongData = false;
    }
    
    public void setNull(final int parameterIndex, final int sqlType, final String typeName) throws SQLException {
        this.checkClosed();
        final BindValue binding = this.getBinding(parameterIndex, false);
        if (binding.bufferType == 0) {
            this.setType(binding, 6);
        }
        binding.value = null;
        binding.isNull = true;
        binding.isLongData = false;
    }
    
    public void setRef(final int i, final Ref x) throws SQLException {
        throw SQLError.notImplemented();
    }
    
    public void setShort(final int parameterIndex, final short x) throws SQLException {
        this.checkClosed();
        final BindValue binding = this.getBinding(parameterIndex, false);
        this.setType(binding, 2);
        binding.value = null;
        binding.shortBinding = x;
        binding.isNull = false;
        binding.isLongData = false;
    }
    
    public void setString(final int parameterIndex, final String x) throws SQLException {
        this.checkClosed();
        if (x == null) {
            this.setNull(parameterIndex, 1);
        }
        else {
            final BindValue binding = this.getBinding(parameterIndex, false);
            this.setType(binding, this.stringTypeCode);
            binding.value = x;
            binding.isNull = false;
            binding.isLongData = false;
        }
    }
    
    public void setTime(final int parameterIndex, final Time x) throws SQLException {
        this.setTimeInternal(parameterIndex, x, null, this.connection.getDefaultTimeZone(), false);
    }
    
    public void setTime(final int parameterIndex, final Time x, final Calendar cal) throws SQLException {
        this.setTimeInternal(parameterIndex, x, cal, cal.getTimeZone(), true);
    }
    
    public void setTimeInternal(final int parameterIndex, final Time x, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 92);
        }
        else {
            final BindValue binding = this.getBinding(parameterIndex, false);
            this.setType(binding, 11);
            if (!this.useLegacyDatetimeCode) {
                binding.value = x;
            }
            else {
                final Calendar sessionCalendar = this.getCalendarInstanceForSessionOrNew();
                synchronized (sessionCalendar) {
                    binding.value = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, x, tz, this.connection.getServerTimezoneTZ(), rollForward);
                }
            }
            binding.isNull = false;
            binding.isLongData = false;
        }
    }
    
    public void setTimestamp(final int parameterIndex, final Timestamp x) throws SQLException {
        this.setTimestampInternal(parameterIndex, x, null, this.connection.getDefaultTimeZone(), false);
    }
    
    public void setTimestamp(final int parameterIndex, final Timestamp x, final Calendar cal) throws SQLException {
        this.setTimestampInternal(parameterIndex, x, cal, cal.getTimeZone(), true);
    }
    
    protected void setTimestampInternal(final int parameterIndex, final Timestamp x, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward) throws SQLException {
        if (x == null) {
            this.setNull(parameterIndex, 93);
        }
        else {
            final BindValue binding = this.getBinding(parameterIndex, false);
            this.setType(binding, 12);
            if (!this.useLegacyDatetimeCode) {
                binding.value = x;
            }
            else {
                final Calendar sessionCalendar = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : this.getCalendarInstanceForSessionOrNew();
                synchronized (sessionCalendar) {
                    binding.value = TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, x, tz, this.connection.getServerTimezoneTZ(), rollForward);
                }
                binding.isNull = false;
                binding.isLongData = false;
            }
        }
    }
    
    protected void setType(final BindValue oldValue, final int bufferType) {
        if (oldValue.bufferType != bufferType) {
            this.sendTypesToServer = true;
        }
        oldValue.bufferType = bufferType;
    }
    
    public void setUnicodeStream(final int parameterIndex, final InputStream x, final int length) throws SQLException {
        this.checkClosed();
        throw SQLError.notImplemented();
    }
    
    public void setURL(final int parameterIndex, final URL x) throws SQLException {
        this.checkClosed();
        this.setString(parameterIndex, x.toString());
    }
    
    private void storeBinding(final Buffer packet, final BindValue bindValue, final MysqlIO mysql) throws SQLException {
        try {
            final Object value = bindValue.value;
            switch (bindValue.bufferType) {
                case 1: {
                    packet.writeByte(bindValue.byteBinding);
                }
                case 2: {
                    packet.ensureCapacity(2);
                    packet.writeInt(bindValue.shortBinding);
                }
                case 3: {
                    packet.ensureCapacity(4);
                    packet.writeLong(bindValue.intBinding);
                }
                case 8: {
                    packet.ensureCapacity(8);
                    packet.writeLongLong(bindValue.longBinding);
                }
                case 4: {
                    packet.ensureCapacity(4);
                    packet.writeFloat(bindValue.floatBinding);
                }
                case 5: {
                    packet.ensureCapacity(8);
                    packet.writeDouble(bindValue.doubleBinding);
                }
                case 11: {
                    this.storeTime(packet, (Time)value);
                }
                case 7:
                case 10:
                case 12: {
                    this.storeDateTime(packet, (Date)value, mysql, bindValue.bufferType);
                }
                case 0:
                case 15:
                case 246:
                case 253:
                case 254: {
                    if (value instanceof byte[]) {
                        packet.writeLenBytes((byte[])value);
                    }
                    else if (!this.isLoadDataQuery) {
                        packet.writeLenString((String)value, this.charEncoding, this.connection.getServerCharacterEncoding(), this.charConverter, this.connection.parserKnowsUnicode(), this.connection);
                    }
                    else {
                        packet.writeLenBytes(((String)value).getBytes());
                    }
                }
            }
        }
        catch (UnsupportedEncodingException uEE) {
            throw SQLError.createSQLException(Messages.getString("ServerPreparedStatement.22") + this.connection.getEncoding() + "'", "S1000", this.getExceptionInterceptor());
        }
    }
    
    private void storeDateTime412AndOlder(final Buffer intoBuf, final Date dt, final int bufferType) throws SQLException {
        Calendar sessionCalendar = null;
        if (!this.useLegacyDatetimeCode) {
            if (bufferType == 10) {
                sessionCalendar = this.getDefaultTzCalendar();
            }
            else {
                sessionCalendar = this.getServerTzCalendar();
            }
        }
        else {
            sessionCalendar = ((dt instanceof Timestamp && this.connection.getUseJDBCCompliantTimezoneShift()) ? this.connection.getUtcCalendar() : this.getCalendarInstanceForSessionOrNew());
        }
        synchronized (sessionCalendar) {
            final Date oldTime = sessionCalendar.getTime();
            try {
                intoBuf.ensureCapacity(8);
                intoBuf.writeByte((byte)7);
                sessionCalendar.setTime(dt);
                final int year = sessionCalendar.get(1);
                final int month = sessionCalendar.get(2) + 1;
                final int date = sessionCalendar.get(5);
                intoBuf.writeInt(year);
                intoBuf.writeByte((byte)month);
                intoBuf.writeByte((byte)date);
                if (dt instanceof java.sql.Date) {
                    intoBuf.writeByte((byte)0);
                    intoBuf.writeByte((byte)0);
                    intoBuf.writeByte((byte)0);
                }
                else {
                    intoBuf.writeByte((byte)sessionCalendar.get(11));
                    intoBuf.writeByte((byte)sessionCalendar.get(12));
                    intoBuf.writeByte((byte)sessionCalendar.get(13));
                }
            }
            finally {
                sessionCalendar.setTime(oldTime);
            }
        }
    }
    
    private void storeDateTime(final Buffer intoBuf, final Date dt, final MysqlIO mysql, final int bufferType) throws SQLException {
        if (this.connection.versionMeetsMinimum(4, 1, 3)) {
            this.storeDateTime413AndNewer(intoBuf, dt, bufferType);
        }
        else {
            this.storeDateTime412AndOlder(intoBuf, dt, bufferType);
        }
    }
    
    private void storeDateTime413AndNewer(final Buffer intoBuf, final Date dt, final int bufferType) throws SQLException {
        Calendar sessionCalendar = null;
        if (!this.useLegacyDatetimeCode) {
            if (bufferType == 10) {
                sessionCalendar = this.getDefaultTzCalendar();
            }
            else {
                sessionCalendar = this.getServerTzCalendar();
            }
        }
        else {
            sessionCalendar = ((dt instanceof Timestamp && this.connection.getUseJDBCCompliantTimezoneShift()) ? this.connection.getUtcCalendar() : this.getCalendarInstanceForSessionOrNew());
        }
        synchronized (sessionCalendar) {
            final Date oldTime = sessionCalendar.getTime();
            try {
                sessionCalendar.setTime(dt);
                if (dt instanceof java.sql.Date) {
                    sessionCalendar.set(11, 0);
                    sessionCalendar.set(12, 0);
                    sessionCalendar.set(13, 0);
                }
                byte length = 7;
                if (dt instanceof Timestamp) {
                    length = 11;
                }
                intoBuf.ensureCapacity(length);
                intoBuf.writeByte(length);
                final int year = sessionCalendar.get(1);
                final int month = sessionCalendar.get(2) + 1;
                final int date = sessionCalendar.get(5);
                intoBuf.writeInt(year);
                intoBuf.writeByte((byte)month);
                intoBuf.writeByte((byte)date);
                if (dt instanceof java.sql.Date) {
                    intoBuf.writeByte((byte)0);
                    intoBuf.writeByte((byte)0);
                    intoBuf.writeByte((byte)0);
                }
                else {
                    intoBuf.writeByte((byte)sessionCalendar.get(11));
                    intoBuf.writeByte((byte)sessionCalendar.get(12));
                    intoBuf.writeByte((byte)sessionCalendar.get(13));
                }
                if (length == 11) {
                    intoBuf.writeLong(((Timestamp)dt).getNanos() / 1000);
                }
            }
            finally {
                sessionCalendar.setTime(oldTime);
            }
        }
    }
    
    private Calendar getServerTzCalendar() {
        synchronized (this) {
            if (this.serverTzCalendar == null) {
                this.serverTzCalendar = new GregorianCalendar(this.connection.getServerTimezoneTZ());
            }
            return this.serverTzCalendar;
        }
    }
    
    private Calendar getDefaultTzCalendar() {
        synchronized (this) {
            if (this.defaultTzCalendar == null) {
                this.defaultTzCalendar = new GregorianCalendar(TimeZone.getDefault());
            }
            return this.defaultTzCalendar;
        }
    }
    
    private void storeReader(final MysqlIO mysql, final int parameterIndex, final Buffer packet, final Reader inStream) throws SQLException {
        final String forcedEncoding = this.connection.getClobCharacterEncoding();
        final String clobEncoding = (forcedEncoding == null) ? this.connection.getEncoding() : forcedEncoding;
        int maxBytesChar = 2;
        if (clobEncoding != null) {
            if (!clobEncoding.equals("UTF-16")) {
                maxBytesChar = this.connection.getMaxBytesPerChar(clobEncoding);
                if (maxBytesChar == 1) {
                    maxBytesChar = 2;
                }
            }
            else {
                maxBytesChar = 4;
            }
        }
        final char[] buf = new char[8192 / maxBytesChar];
        int numRead = 0;
        int bytesInPacket = 0;
        int totalBytesRead = 0;
        int bytesReadAtLastSend = 0;
        final int packetIsFullAt = this.connection.getBlobSendChunkSize();
        try {
            packet.clear();
            packet.writeByte((byte)24);
            packet.writeLong(this.serverStatementId);
            packet.writeInt(parameterIndex);
            boolean readAny = false;
            while ((numRead = inStream.read(buf)) != -1) {
                readAny = true;
                final byte[] valueAsBytes = StringUtils.getBytes(buf, null, clobEncoding, this.connection.getServerCharacterEncoding(), 0, numRead, this.connection.parserKnowsUnicode(), this.getExceptionInterceptor());
                packet.writeBytesNoNull(valueAsBytes, 0, valueAsBytes.length);
                bytesInPacket += valueAsBytes.length;
                totalBytesRead += valueAsBytes.length;
                if (bytesInPacket >= packetIsFullAt) {
                    bytesReadAtLastSend = totalBytesRead;
                    mysql.sendCommand(24, null, packet, true, null, 0);
                    bytesInPacket = 0;
                    packet.clear();
                    packet.writeByte((byte)24);
                    packet.writeLong(this.serverStatementId);
                    packet.writeInt(parameterIndex);
                }
            }
            if (totalBytesRead != bytesReadAtLastSend) {
                mysql.sendCommand(24, null, packet, true, null, 0);
            }
            if (!readAny) {
                mysql.sendCommand(24, null, packet, true, null, 0);
            }
        }
        catch (IOException ioEx) {
            final SQLException sqlEx = SQLError.createSQLException(Messages.getString("ServerPreparedStatement.24") + ioEx.toString(), "S1000", this.getExceptionInterceptor());
            sqlEx.initCause(ioEx);
            throw sqlEx;
        }
        finally {
            if (this.connection.getAutoClosePStmtStreams() && inStream != null) {
                try {
                    inStream.close();
                }
                catch (IOException ex) {}
            }
        }
    }
    
    private void storeStream(final MysqlIO mysql, final int parameterIndex, final Buffer packet, final InputStream inStream) throws SQLException {
        final byte[] buf = new byte[8192];
        int numRead = 0;
        try {
            int bytesInPacket = 0;
            int totalBytesRead = 0;
            int bytesReadAtLastSend = 0;
            final int packetIsFullAt = this.connection.getBlobSendChunkSize();
            packet.clear();
            packet.writeByte((byte)24);
            packet.writeLong(this.serverStatementId);
            packet.writeInt(parameterIndex);
            boolean readAny = false;
            while ((numRead = inStream.read(buf)) != -1) {
                readAny = true;
                packet.writeBytesNoNull(buf, 0, numRead);
                bytesInPacket += numRead;
                totalBytesRead += numRead;
                if (bytesInPacket >= packetIsFullAt) {
                    bytesReadAtLastSend = totalBytesRead;
                    mysql.sendCommand(24, null, packet, true, null, 0);
                    bytesInPacket = 0;
                    packet.clear();
                    packet.writeByte((byte)24);
                    packet.writeLong(this.serverStatementId);
                    packet.writeInt(parameterIndex);
                }
            }
            if (totalBytesRead != bytesReadAtLastSend) {
                mysql.sendCommand(24, null, packet, true, null, 0);
            }
            if (!readAny) {
                mysql.sendCommand(24, null, packet, true, null, 0);
            }
        }
        catch (IOException ioEx) {
            final SQLException sqlEx = SQLError.createSQLException(Messages.getString("ServerPreparedStatement.25") + ioEx.toString(), "S1000", this.getExceptionInterceptor());
            sqlEx.initCause(ioEx);
            throw sqlEx;
        }
        finally {
            if (this.connection.getAutoClosePStmtStreams() && inStream != null) {
                try {
                    inStream.close();
                }
                catch (IOException ex) {}
            }
        }
    }
    
    public String toString() {
        final StringBuffer toStringBuf = new StringBuffer();
        toStringBuf.append("com.mysql.jdbc.ServerPreparedStatement[");
        toStringBuf.append(this.serverStatementId);
        toStringBuf.append("] - ");
        try {
            toStringBuf.append(this.asSql());
        }
        catch (SQLException sqlEx) {
            toStringBuf.append(Messages.getString("ServerPreparedStatement.6"));
            toStringBuf.append(sqlEx);
        }
        return toStringBuf.toString();
    }
    
    protected long getServerStatementId() {
        return this.serverStatementId;
    }
    
    public synchronized boolean canRewriteAsMultiValueInsertAtSqlLevel() throws SQLException {
        if (!this.hasCheckedRewrite) {
            this.hasCheckedRewrite = true;
            this.canRewrite = PreparedStatement.canRewrite(this.originalSql, this.isOnDuplicateKeyUpdate(), this.getLocationOfOnDuplicateKeyUpdate(), 0);
            this.parseInfo = new ParseInfo(this, this.originalSql, this.connection, this.connection.getMetaData(), this.charEncoding, this.charConverter);
        }
        return this.canRewrite;
    }
    
    public synchronized boolean canRewriteAsMultivalueInsertStatement() throws SQLException {
        if (!this.canRewriteAsMultiValueInsertAtSqlLevel()) {
            return false;
        }
        BindValue[] currentBindValues = null;
        final BindValue[] previousBindValues = null;
        for (int nbrCommands = this.batchedArgs.size(), commandIndex = 0; commandIndex < nbrCommands; ++commandIndex) {
            final Object arg = this.batchedArgs.get(commandIndex);
            if (!(arg instanceof String)) {
                currentBindValues = ((BatchedBindValues)arg).batchedParameterValues;
                if (previousBindValues != null) {
                    for (int j = 0; j < this.parameterBindings.length; ++j) {
                        if (currentBindValues[j].bufferType != previousBindValues[j].bufferType) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    
    protected synchronized int getLocationOfOnDuplicateKeyUpdate() {
        if (this.locationOfOnDuplicateKeyUpdate == -2) {
            this.locationOfOnDuplicateKeyUpdate = this.getOnDuplicateKeyLocation(this.originalSql);
        }
        return this.locationOfOnDuplicateKeyUpdate;
    }
    
    protected synchronized boolean isOnDuplicateKeyUpdate() {
        return this.getLocationOfOnDuplicateKeyUpdate() != -1;
    }
    
    protected long[] computeMaxParameterSetSizeAndBatchSize(final int numBatchedArgs) {
        long sizeOfEntireBatch = 10L;
        long maxSizeOfParameterSet = 0L;
        for (int i = 0; i < numBatchedArgs; ++i) {
            final BindValue[] paramArg = this.batchedArgs.get(i).batchedParameterValues;
            long sizeOfParameterSet = 0L;
            sizeOfParameterSet += (this.parameterCount + 7) / 8;
            sizeOfParameterSet += this.parameterCount * 2;
            for (int j = 0; j < this.parameterBindings.length; ++j) {
                if (!paramArg[j].isNull) {
                    final long size = paramArg[j].getBoundLength();
                    if (paramArg[j].isLongData) {
                        if (size != -1L) {
                            sizeOfParameterSet += size;
                        }
                    }
                    else {
                        sizeOfParameterSet += size;
                    }
                }
            }
            sizeOfEntireBatch += sizeOfParameterSet;
            if (sizeOfParameterSet > maxSizeOfParameterSet) {
                maxSizeOfParameterSet = sizeOfParameterSet;
            }
        }
        return new long[] { maxSizeOfParameterSet, sizeOfEntireBatch };
    }
    
    protected int setOneBatchedParameterSet(final java.sql.PreparedStatement batchedStatement, int batchedParamIndex, final Object paramSet) throws SQLException {
        final BindValue[] paramArg = ((BatchedBindValues)paramSet).batchedParameterValues;
        for (int j = 0; j < paramArg.length; ++j) {
            if (paramArg[j].isNull) {
                batchedStatement.setNull(batchedParamIndex++, 0);
            }
            else if (paramArg[j].isLongData) {
                final Object value = paramArg[j].value;
                if (value instanceof InputStream) {
                    batchedStatement.setBinaryStream(batchedParamIndex++, (InputStream)value, (int)paramArg[j].bindLength);
                }
                else {
                    batchedStatement.setCharacterStream(batchedParamIndex++, (Reader)value, (int)paramArg[j].bindLength);
                }
            }
            else {
                switch (paramArg[j].bufferType) {
                    case 1: {
                        batchedStatement.setByte(batchedParamIndex++, paramArg[j].byteBinding);
                        break;
                    }
                    case 2: {
                        batchedStatement.setShort(batchedParamIndex++, paramArg[j].shortBinding);
                        break;
                    }
                    case 3: {
                        batchedStatement.setInt(batchedParamIndex++, paramArg[j].intBinding);
                        break;
                    }
                    case 8: {
                        batchedStatement.setLong(batchedParamIndex++, paramArg[j].longBinding);
                        break;
                    }
                    case 4: {
                        batchedStatement.setFloat(batchedParamIndex++, paramArg[j].floatBinding);
                        break;
                    }
                    case 5: {
                        batchedStatement.setDouble(batchedParamIndex++, paramArg[j].doubleBinding);
                        break;
                    }
                    case 11: {
                        batchedStatement.setTime(batchedParamIndex++, (Time)paramArg[j].value);
                        break;
                    }
                    case 10: {
                        batchedStatement.setDate(batchedParamIndex++, (java.sql.Date)paramArg[j].value);
                        break;
                    }
                    case 7:
                    case 12: {
                        batchedStatement.setTimestamp(batchedParamIndex++, (Timestamp)paramArg[j].value);
                        break;
                    }
                    case 0:
                    case 15:
                    case 246:
                    case 253:
                    case 254: {
                        final Object value = paramArg[j].value;
                        if (value instanceof byte[]) {
                            batchedStatement.setBytes(batchedParamIndex, (byte[])value);
                        }
                        else {
                            batchedStatement.setString(batchedParamIndex, (String)value);
                        }
                        if (batchedStatement instanceof ServerPreparedStatement) {
                            final BindValue asBound = ((ServerPreparedStatement)batchedStatement).getBinding(batchedParamIndex, false);
                            asBound.bufferType = paramArg[j].bufferType;
                        }
                        ++batchedParamIndex;
                        break;
                    }
                    default: {
                        throw new IllegalArgumentException("Unknown type when re-binding parameter into batched statement for parameter index " + batchedParamIndex);
                    }
                }
            }
        }
        return batchedParamIndex;
    }
    
    protected boolean containsOnDuplicateKeyUpdateInSQL() {
        return this.hasOnDuplicateKeyUpdate;
    }
    
    protected PreparedStatement prepareBatchedInsertSQL(final MySQLConnection localConn, final int numBatches) throws SQLException {
        try {
            final PreparedStatement pstmt = new ServerPreparedStatement(localConn, this.parseInfo.getSqlForBatch(numBatches), this.currentCatalog, this.resultSetConcurrency, this.resultSetType);
            pstmt.setRetrieveGeneratedKeys(this.retrieveGeneratedKeys);
            return pstmt;
        }
        catch (UnsupportedEncodingException e) {
            final SQLException sqlEx = SQLError.createSQLException("Unable to prepare batch statement", "S1000", this.getExceptionInterceptor());
            sqlEx.initCause(e);
            throw sqlEx;
        }
    }
    
    static {
        if (Util.isJdbc4()) {
            try {
                JDBC_4_SPS_CTOR = Class.forName("com.mysql.jdbc.JDBC4ServerPreparedStatement").getConstructor(MySQLConnection.class, String.class, String.class, Integer.TYPE, Integer.TYPE);
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
        JDBC_4_SPS_CTOR = null;
    }
    
    static class BatchedBindValues
    {
        BindValue[] batchedParameterValues;
        
        BatchedBindValues(final BindValue[] paramVals) {
            final int numParams = paramVals.length;
            this.batchedParameterValues = new BindValue[numParams];
            for (int i = 0; i < numParams; ++i) {
                this.batchedParameterValues[i] = new BindValue(paramVals[i]);
            }
        }
    }
    
    public static class BindValue
    {
        long boundBeforeExecutionNum;
        public long bindLength;
        int bufferType;
        byte byteBinding;
        double doubleBinding;
        float floatBinding;
        int intBinding;
        public boolean isLongData;
        public boolean isNull;
        boolean isSet;
        long longBinding;
        short shortBinding;
        public Object value;
        
        BindValue() {
            this.boundBeforeExecutionNum = 0L;
            this.isSet = false;
        }
        
        BindValue(final BindValue copyMe) {
            this.boundBeforeExecutionNum = 0L;
            this.isSet = false;
            this.value = copyMe.value;
            this.isSet = copyMe.isSet;
            this.isLongData = copyMe.isLongData;
            this.isNull = copyMe.isNull;
            this.bufferType = copyMe.bufferType;
            this.bindLength = copyMe.bindLength;
            this.byteBinding = copyMe.byteBinding;
            this.shortBinding = copyMe.shortBinding;
            this.intBinding = copyMe.intBinding;
            this.longBinding = copyMe.longBinding;
            this.floatBinding = copyMe.floatBinding;
            this.doubleBinding = copyMe.doubleBinding;
        }
        
        void reset() {
            this.isSet = false;
            this.value = null;
            this.isLongData = false;
            this.byteBinding = 0;
            this.shortBinding = 0;
            this.intBinding = 0;
            this.longBinding = 0L;
            this.floatBinding = 0.0f;
            this.doubleBinding = 0.0;
        }
        
        public String toString() {
            return this.toString(false);
        }
        
        public String toString(final boolean quoteIfNeeded) {
            if (this.isLongData) {
                return "' STREAM DATA '";
            }
            switch (this.bufferType) {
                case 1: {
                    return String.valueOf(this.byteBinding);
                }
                case 2: {
                    return String.valueOf(this.shortBinding);
                }
                case 3: {
                    return String.valueOf(this.intBinding);
                }
                case 8: {
                    return String.valueOf(this.longBinding);
                }
                case 4: {
                    return String.valueOf(this.floatBinding);
                }
                case 5: {
                    return String.valueOf(this.doubleBinding);
                }
                case 7:
                case 10:
                case 11:
                case 12:
                case 15:
                case 253:
                case 254: {
                    if (quoteIfNeeded) {
                        return "'" + String.valueOf(this.value) + "'";
                    }
                    return String.valueOf(this.value);
                }
                default: {
                    if (this.value instanceof byte[]) {
                        return "byte data";
                    }
                    if (quoteIfNeeded) {
                        return "'" + String.valueOf(this.value) + "'";
                    }
                    return String.valueOf(this.value);
                }
            }
        }
        
        long getBoundLength() {
            if (this.isNull) {
                return 0L;
            }
            if (this.isLongData) {
                return this.bindLength;
            }
            switch (this.bufferType) {
                case 1: {
                    return 1L;
                }
                case 2: {
                    return 2L;
                }
                case 3: {
                    return 4L;
                }
                case 8: {
                    return 8L;
                }
                case 4: {
                    return 4L;
                }
                case 5: {
                    return 8L;
                }
                case 11: {
                    return 9L;
                }
                case 10: {
                    return 7L;
                }
                case 7:
                case 12: {
                    return 11L;
                }
                case 0:
                case 15:
                case 246:
                case 253:
                case 254: {
                    if (this.value instanceof byte[]) {
                        return ((byte[])this.value).length;
                    }
                    return ((String)this.value).length();
                }
                default: {
                    return 0L;
                }
            }
        }
    }
}
