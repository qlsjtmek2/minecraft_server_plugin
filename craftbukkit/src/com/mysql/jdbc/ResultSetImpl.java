// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.ResultSet;
import com.mysql.jdbc.profiler.ProfilerEvent;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Ref;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.sql.ResultSetMetaData;
import java.util.StringTokenizer;
import java.sql.Clob;
import java.io.StringReader;
import java.io.Reader;
import java.sql.Blob;
import java.math.BigDecimal;
import java.io.InputStream;
import java.sql.Array;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.HashMap;
import java.sql.SQLException;
import java.math.BigInteger;
import java.sql.Statement;
import java.sql.SQLWarning;
import java.util.Calendar;
import com.mysql.jdbc.profiler.ProfilerEventHandler;
import java.util.TimeZone;
import java.util.Map;
import java.lang.reflect.Constructor;

public class ResultSetImpl implements ResultSetInternalMethods
{
    private static final Constructor JDBC_4_RS_4_ARG_CTOR;
    private static final Constructor JDBC_4_RS_6_ARG_CTOR;
    private static final Constructor JDBC_4_UPD_RS_6_ARG_CTOR;
    protected static final double MIN_DIFF_PREC;
    protected static final double MAX_DIFF_PREC;
    protected static int resultCounter;
    protected String catalog;
    protected Map columnLabelToIndex;
    protected Map columnToIndexCache;
    protected boolean[] columnUsed;
    protected MySQLConnection connection;
    protected long connectionId;
    protected int currentRow;
    TimeZone defaultTimeZone;
    protected boolean doingUpdates;
    protected ProfilerEventHandler eventSink;
    Calendar fastDateCal;
    protected int fetchDirection;
    protected int fetchSize;
    protected Field[] fields;
    protected char firstCharOfQuery;
    protected Map fullColumnNameToIndex;
    protected Map columnNameToIndex;
    protected boolean hasBuiltIndexMapping;
    protected boolean isBinaryEncoded;
    protected boolean isClosed;
    protected ResultSetInternalMethods nextResultSet;
    protected boolean onInsertRow;
    protected StatementImpl owningStatement;
    protected Throwable pointOfOrigin;
    protected boolean profileSql;
    protected boolean reallyResult;
    protected int resultId;
    protected int resultSetConcurrency;
    protected int resultSetType;
    protected RowData rowData;
    protected String serverInfo;
    PreparedStatement statementUsedForFetchingRows;
    protected ResultSetRow thisRow;
    protected long updateCount;
    protected long updateId;
    private boolean useStrictFloatingPoint;
    protected boolean useUsageAdvisor;
    protected SQLWarning warningChain;
    protected boolean wasNullFlag;
    protected Statement wrapperStatement;
    protected boolean retainOwningStatement;
    protected Calendar gmtCalendar;
    protected boolean useFastDateParsing;
    private boolean padCharsWithSpace;
    private boolean jdbcCompliantTruncationForReads;
    private boolean useFastIntParsing;
    private boolean useColumnNamesInFindColumn;
    private ExceptionInterceptor exceptionInterceptor;
    protected static final char[] EMPTY_SPACE;
    private boolean onValidRow;
    private String invalidRowReason;
    protected boolean useLegacyDatetimeCode;
    private TimeZone serverTimeZoneTz;
    
    protected static BigInteger convertLongToUlong(final long longVal) {
        final byte[] asBytes = { (byte)(longVal >>> 56), (byte)(longVal >>> 48), (byte)(longVal >>> 40), (byte)(longVal >>> 32), (byte)(longVal >>> 24), (byte)(longVal >>> 16), (byte)(longVal >>> 8), (byte)(longVal & 0xFFL) };
        return new BigInteger(1, asBytes);
    }
    
    protected static ResultSetImpl getInstance(final long updateCount, final long updateID, final MySQLConnection conn, final StatementImpl creatorStmt) throws SQLException {
        if (!Util.isJdbc4()) {
            return new ResultSetImpl(updateCount, updateID, conn, creatorStmt);
        }
        return (ResultSetImpl)Util.handleNewInstance(ResultSetImpl.JDBC_4_RS_4_ARG_CTOR, new Object[] { Constants.longValueOf(updateCount), Constants.longValueOf(updateID), conn, creatorStmt }, conn.getExceptionInterceptor());
    }
    
    protected static ResultSetImpl getInstance(final String catalog, final Field[] fields, final RowData tuples, final MySQLConnection conn, final StatementImpl creatorStmt, final boolean isUpdatable) throws SQLException {
        if (!Util.isJdbc4()) {
            if (!isUpdatable) {
                return new ResultSetImpl(catalog, fields, tuples, conn, creatorStmt);
            }
            return new UpdatableResultSet(catalog, fields, tuples, conn, creatorStmt);
        }
        else {
            if (!isUpdatable) {
                return (ResultSetImpl)Util.handleNewInstance(ResultSetImpl.JDBC_4_RS_6_ARG_CTOR, new Object[] { catalog, fields, tuples, conn, creatorStmt }, conn.getExceptionInterceptor());
            }
            return (ResultSetImpl)Util.handleNewInstance(ResultSetImpl.JDBC_4_UPD_RS_6_ARG_CTOR, new Object[] { catalog, fields, tuples, conn, creatorStmt }, conn.getExceptionInterceptor());
        }
    }
    
    public ResultSetImpl(final long updateCount, final long updateID, final MySQLConnection conn, final StatementImpl creatorStmt) {
        this.catalog = null;
        this.columnLabelToIndex = null;
        this.columnToIndexCache = null;
        this.columnUsed = null;
        this.connectionId = 0L;
        this.currentRow = -1;
        this.doingUpdates = false;
        this.eventSink = null;
        this.fastDateCal = null;
        this.fetchDirection = 1000;
        this.fetchSize = 0;
        this.fullColumnNameToIndex = null;
        this.columnNameToIndex = null;
        this.hasBuiltIndexMapping = false;
        this.isBinaryEncoded = false;
        this.isClosed = false;
        this.nextResultSet = null;
        this.onInsertRow = false;
        this.profileSql = false;
        this.reallyResult = false;
        this.resultSetConcurrency = 0;
        this.resultSetType = 0;
        this.serverInfo = null;
        this.thisRow = null;
        this.updateId = -1L;
        this.useStrictFloatingPoint = false;
        this.useUsageAdvisor = false;
        this.warningChain = null;
        this.wasNullFlag = false;
        this.gmtCalendar = null;
        this.useFastDateParsing = false;
        this.padCharsWithSpace = false;
        this.useFastIntParsing = true;
        this.onValidRow = false;
        this.invalidRowReason = null;
        this.updateCount = updateCount;
        this.updateId = updateID;
        this.reallyResult = false;
        this.fields = new Field[0];
        this.connection = conn;
        this.owningStatement = creatorStmt;
        this.exceptionInterceptor = this.connection.getExceptionInterceptor();
        this.retainOwningStatement = false;
        if (this.connection != null) {
            this.retainOwningStatement = this.connection.getRetainStatementAfterResultSetClose();
            this.connectionId = this.connection.getId();
            this.serverTimeZoneTz = this.connection.getServerTimezoneTZ();
            this.padCharsWithSpace = this.connection.getPadCharsWithSpace();
        }
        this.useLegacyDatetimeCode = this.connection.getUseLegacyDatetimeCode();
    }
    
    public ResultSetImpl(final String catalog, final Field[] fields, final RowData tuples, final MySQLConnection conn, final StatementImpl creatorStmt) throws SQLException {
        this.catalog = null;
        this.columnLabelToIndex = null;
        this.columnToIndexCache = null;
        this.columnUsed = null;
        this.connectionId = 0L;
        this.currentRow = -1;
        this.doingUpdates = false;
        this.eventSink = null;
        this.fastDateCal = null;
        this.fetchDirection = 1000;
        this.fetchSize = 0;
        this.fullColumnNameToIndex = null;
        this.columnNameToIndex = null;
        this.hasBuiltIndexMapping = false;
        this.isBinaryEncoded = false;
        this.isClosed = false;
        this.nextResultSet = null;
        this.onInsertRow = false;
        this.profileSql = false;
        this.reallyResult = false;
        this.resultSetConcurrency = 0;
        this.resultSetType = 0;
        this.serverInfo = null;
        this.thisRow = null;
        this.updateId = -1L;
        this.useStrictFloatingPoint = false;
        this.useUsageAdvisor = false;
        this.warningChain = null;
        this.wasNullFlag = false;
        this.gmtCalendar = null;
        this.useFastDateParsing = false;
        this.padCharsWithSpace = false;
        this.useFastIntParsing = true;
        this.onValidRow = false;
        this.invalidRowReason = null;
        this.connection = conn;
        this.retainOwningStatement = false;
        if (this.connection != null) {
            this.useStrictFloatingPoint = this.connection.getStrictFloatingPoint();
            this.setDefaultTimeZone(this.connection.getDefaultTimeZone());
            this.connectionId = this.connection.getId();
            this.useFastDateParsing = this.connection.getUseFastDateParsing();
            this.profileSql = this.connection.getProfileSql();
            this.retainOwningStatement = this.connection.getRetainStatementAfterResultSetClose();
            this.jdbcCompliantTruncationForReads = this.connection.getJdbcCompliantTruncationForReads();
            this.useFastIntParsing = this.connection.getUseFastIntParsing();
            this.serverTimeZoneTz = this.connection.getServerTimezoneTZ();
            this.padCharsWithSpace = this.connection.getPadCharsWithSpace();
        }
        this.owningStatement = creatorStmt;
        this.catalog = catalog;
        this.fields = fields;
        this.rowData = tuples;
        this.updateCount = this.rowData.size();
        this.reallyResult = true;
        if (this.rowData.size() > 0) {
            if (this.updateCount == 1L && this.thisRow == null) {
                this.rowData.close();
                this.updateCount = -1L;
            }
        }
        else {
            this.thisRow = null;
        }
        this.rowData.setOwner(this);
        if (this.fields != null) {
            this.initializeWithMetadata();
        }
        this.useLegacyDatetimeCode = this.connection.getUseLegacyDatetimeCode();
        this.useColumnNamesInFindColumn = this.connection.getUseColumnNamesInFindColumn();
        this.setRowPositionValidity();
    }
    
    public void initializeWithMetadata() throws SQLException {
        this.rowData.setMetadata(this.fields);
        this.columnToIndexCache = new HashMap();
        if (this.profileSql || this.connection.getUseUsageAdvisor()) {
            this.columnUsed = new boolean[this.fields.length];
            this.pointOfOrigin = new Throwable();
            this.resultId = ResultSetImpl.resultCounter++;
            this.useUsageAdvisor = this.connection.getUseUsageAdvisor();
            this.eventSink = ProfilerEventHandlerFactory.getInstance(this.connection);
        }
        if (this.connection.getGatherPerformanceMetrics()) {
            this.connection.incrementNumberOfResultSetsCreated();
            final Map tableNamesMap = new HashMap();
            for (int i = 0; i < this.fields.length; ++i) {
                final Field f = this.fields[i];
                String tableName = f.getOriginalTableName();
                if (tableName == null) {
                    tableName = f.getTableName();
                }
                if (tableName != null) {
                    if (this.connection.lowerCaseTableNames()) {
                        tableName = tableName.toLowerCase();
                    }
                    tableNamesMap.put(tableName, null);
                }
            }
            this.connection.reportNumberOfTablesAccessed(tableNamesMap.size());
        }
    }
    
    private synchronized void createCalendarIfNeeded() {
        if (this.fastDateCal == null) {
            (this.fastDateCal = new GregorianCalendar(Locale.US)).setTimeZone(this.getDefaultTimeZone());
        }
    }
    
    public boolean absolute(int row) throws SQLException {
        this.checkClosed();
        boolean b;
        if (this.rowData.size() == 0) {
            b = false;
        }
        else {
            if (row == 0) {
                throw SQLError.createSQLException(Messages.getString("ResultSet.Cannot_absolute_position_to_row_0_110"), "S1009", this.getExceptionInterceptor());
            }
            if (this.onInsertRow) {
                this.onInsertRow = false;
            }
            if (this.doingUpdates) {
                this.doingUpdates = false;
            }
            if (this.thisRow != null) {
                this.thisRow.closeOpenStreams();
            }
            if (row == 1) {
                b = this.first();
            }
            else if (row == -1) {
                b = this.last();
            }
            else if (row > this.rowData.size()) {
                this.afterLast();
                b = false;
            }
            else if (row < 0) {
                final int newRowPosition = this.rowData.size() + row + 1;
                if (newRowPosition <= 0) {
                    this.beforeFirst();
                    b = false;
                }
                else {
                    b = this.absolute(newRowPosition);
                }
            }
            else {
                --row;
                this.rowData.setCurrentRow(row);
                this.thisRow = this.rowData.getAt(row);
                b = true;
            }
        }
        this.setRowPositionValidity();
        return b;
    }
    
    public void afterLast() throws SQLException {
        this.checkClosed();
        if (this.onInsertRow) {
            this.onInsertRow = false;
        }
        if (this.doingUpdates) {
            this.doingUpdates = false;
        }
        if (this.thisRow != null) {
            this.thisRow.closeOpenStreams();
        }
        if (this.rowData.size() != 0) {
            this.rowData.afterLast();
            this.thisRow = null;
        }
        this.setRowPositionValidity();
    }
    
    public void beforeFirst() throws SQLException {
        this.checkClosed();
        if (this.onInsertRow) {
            this.onInsertRow = false;
        }
        if (this.doingUpdates) {
            this.doingUpdates = false;
        }
        if (this.rowData.size() == 0) {
            return;
        }
        if (this.thisRow != null) {
            this.thisRow.closeOpenStreams();
        }
        this.rowData.beforeFirst();
        this.thisRow = null;
        this.setRowPositionValidity();
    }
    
    public void buildIndexMapping() throws SQLException {
        final int numFields = this.fields.length;
        this.columnLabelToIndex = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        this.fullColumnNameToIndex = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        this.columnNameToIndex = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        for (int i = numFields - 1; i >= 0; --i) {
            final Integer index = Constants.integerValueOf(i);
            final String columnName = this.fields[i].getOriginalName();
            final String columnLabel = this.fields[i].getName();
            final String fullColumnName = this.fields[i].getFullName();
            if (columnLabel != null) {
                this.columnLabelToIndex.put(columnLabel, index);
            }
            if (fullColumnName != null) {
                this.fullColumnNameToIndex.put(fullColumnName, index);
            }
            if (columnName != null) {
                this.columnNameToIndex.put(columnName, index);
            }
        }
        this.hasBuiltIndexMapping = true;
    }
    
    public void cancelRowUpdates() throws SQLException {
        throw new NotUpdatable();
    }
    
    protected final void checkClosed() throws SQLException {
        if (this.isClosed) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Operation_not_allowed_after_ResultSet_closed_144"), "S1000", this.getExceptionInterceptor());
        }
    }
    
    protected final void checkColumnBounds(final int columnIndex) throws SQLException {
        if (columnIndex < 1) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Column_Index_out_of_range_low", new Object[] { Constants.integerValueOf(columnIndex), Constants.integerValueOf(this.fields.length) }), "S1009", this.getExceptionInterceptor());
        }
        if (columnIndex > this.fields.length) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Column_Index_out_of_range_high", new Object[] { Constants.integerValueOf(columnIndex), Constants.integerValueOf(this.fields.length) }), "S1009", this.getExceptionInterceptor());
        }
        if (this.profileSql || this.useUsageAdvisor) {
            this.columnUsed[columnIndex - 1] = true;
        }
    }
    
    protected void checkRowPos() throws SQLException {
        this.checkClosed();
        if (!this.onValidRow) {
            throw SQLError.createSQLException(this.invalidRowReason, "S1000", this.getExceptionInterceptor());
        }
    }
    
    private void setRowPositionValidity() throws SQLException {
        if (!this.rowData.isDynamic() && this.rowData.size() == 0) {
            this.invalidRowReason = Messages.getString("ResultSet.Illegal_operation_on_empty_result_set");
            this.onValidRow = false;
        }
        else if (this.rowData.isBeforeFirst()) {
            this.invalidRowReason = Messages.getString("ResultSet.Before_start_of_result_set_146");
            this.onValidRow = false;
        }
        else if (this.rowData.isAfterLast()) {
            this.invalidRowReason = Messages.getString("ResultSet.After_end_of_result_set_148");
            this.onValidRow = false;
        }
        else {
            this.onValidRow = true;
            this.invalidRowReason = null;
        }
    }
    
    public void clearNextResult() {
        this.nextResultSet = null;
    }
    
    public void clearWarnings() throws SQLException {
        this.warningChain = null;
    }
    
    public void close() throws SQLException {
        this.realClose(true);
    }
    
    private int convertToZeroWithEmptyCheck() throws SQLException {
        if (this.connection.getEmptyStringsConvertToZero()) {
            return 0;
        }
        throw SQLError.createSQLException("Can't convert empty string ('') to numeric", "22018", this.getExceptionInterceptor());
    }
    
    private String convertToZeroLiteralStringWithEmptyCheck() throws SQLException {
        if (this.connection.getEmptyStringsConvertToZero()) {
            return "0";
        }
        throw SQLError.createSQLException("Can't convert empty string ('') to numeric", "22018", this.getExceptionInterceptor());
    }
    
    public ResultSetInternalMethods copy() throws SQLException {
        final ResultSetInternalMethods rs = getInstance(this.catalog, this.fields, this.rowData, this.connection, this.owningStatement, false);
        return rs;
    }
    
    public void redefineFieldsForDBMD(final Field[] f) {
        this.fields = f;
        for (int i = 0; i < this.fields.length; ++i) {
            this.fields[i].setUseOldNameMetadata(true);
            this.fields[i].setConnection(this.connection);
        }
    }
    
    public void populateCachedMetaData(final CachedResultSetMetaData cachedMetaData) throws SQLException {
        cachedMetaData.fields = this.fields;
        cachedMetaData.columnNameToIndex = this.columnLabelToIndex;
        cachedMetaData.fullColumnNameToIndex = this.fullColumnNameToIndex;
        cachedMetaData.metadata = this.getMetaData();
    }
    
    public void initializeFromCachedMetaData(final CachedResultSetMetaData cachedMetaData) {
        this.fields = cachedMetaData.fields;
        this.columnLabelToIndex = cachedMetaData.columnNameToIndex;
        this.fullColumnNameToIndex = cachedMetaData.fullColumnNameToIndex;
        this.hasBuiltIndexMapping = true;
    }
    
    public void deleteRow() throws SQLException {
        throw new NotUpdatable();
    }
    
    private String extractStringFromNativeColumn(final int columnIndex, final int mysqlType) throws SQLException {
        final int columnIndexMinusOne = columnIndex - 1;
        this.wasNullFlag = false;
        if (this.thisRow.isNull(columnIndexMinusOne)) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        final String encoding = this.fields[columnIndexMinusOne].getCharacterSet();
        return this.thisRow.getString(columnIndex - 1, encoding, this.connection);
    }
    
    protected synchronized Date fastDateCreate(Calendar cal, final int year, final int month, final int day) {
        if (this.useLegacyDatetimeCode) {
            return TimeUtil.fastDateCreate(year, month, day, cal);
        }
        if (cal == null) {
            this.createCalendarIfNeeded();
            cal = this.fastDateCal;
        }
        final boolean useGmtMillis = this.connection.getUseGmtMillisForDatetimes();
        return TimeUtil.fastDateCreate(useGmtMillis, useGmtMillis ? this.getGmtCalendar() : cal, cal, year, month, day);
    }
    
    protected synchronized Time fastTimeCreate(Calendar cal, final int hour, final int minute, final int second) throws SQLException {
        if (!this.useLegacyDatetimeCode) {
            return TimeUtil.fastTimeCreate(hour, minute, second, cal, this.getExceptionInterceptor());
        }
        if (cal == null) {
            this.createCalendarIfNeeded();
            cal = this.fastDateCal;
        }
        return TimeUtil.fastTimeCreate(cal, hour, minute, second, this.getExceptionInterceptor());
    }
    
    protected synchronized Timestamp fastTimestampCreate(Calendar cal, final int year, final int month, final int day, final int hour, final int minute, final int seconds, final int secondsPart) {
        if (!this.useLegacyDatetimeCode) {
            return TimeUtil.fastTimestampCreate(cal.getTimeZone(), year, month, day, hour, minute, seconds, secondsPart);
        }
        if (cal == null) {
            this.createCalendarIfNeeded();
            cal = this.fastDateCal;
        }
        final boolean useGmtMillis = this.connection.getUseGmtMillisForDatetimes();
        return TimeUtil.fastTimestampCreate(useGmtMillis, useGmtMillis ? this.getGmtCalendar() : null, cal, year, month, day, hour, minute, seconds, secondsPart);
    }
    
    public synchronized int findColumn(final String columnName) throws SQLException {
        this.checkClosed();
        if (!this.hasBuiltIndexMapping) {
            this.buildIndexMapping();
        }
        Integer index = this.columnToIndexCache.get(columnName);
        if (index != null) {
            return index + 1;
        }
        index = this.columnLabelToIndex.get(columnName);
        if (index == null && this.useColumnNamesInFindColumn) {
            index = this.columnNameToIndex.get(columnName);
        }
        if (index == null) {
            index = this.fullColumnNameToIndex.get(columnName);
        }
        if (index != null) {
            this.columnToIndexCache.put(columnName, index);
            return index + 1;
        }
        for (int i = 0; i < this.fields.length; ++i) {
            if (this.fields[i].getName().equalsIgnoreCase(columnName)) {
                return i + 1;
            }
            if (this.fields[i].getFullName().equalsIgnoreCase(columnName)) {
                return i + 1;
            }
        }
        throw SQLError.createSQLException(Messages.getString("ResultSet.Column____112") + columnName + Messages.getString("ResultSet.___not_found._113"), "S0022", this.getExceptionInterceptor());
    }
    
    public boolean first() throws SQLException {
        this.checkClosed();
        boolean b = true;
        if (this.rowData.isEmpty()) {
            b = false;
        }
        else {
            if (this.onInsertRow) {
                this.onInsertRow = false;
            }
            if (this.doingUpdates) {
                this.doingUpdates = false;
            }
            this.rowData.beforeFirst();
            this.thisRow = this.rowData.next();
        }
        this.setRowPositionValidity();
        return b;
    }
    
    public Array getArray(final int i) throws SQLException {
        this.checkColumnBounds(i);
        throw SQLError.notImplemented();
    }
    
    public Array getArray(final String colName) throws SQLException {
        return this.getArray(this.findColumn(colName));
    }
    
    public InputStream getAsciiStream(final int columnIndex) throws SQLException {
        this.checkRowPos();
        if (!this.isBinaryEncoded) {
            return this.getBinaryStream(columnIndex);
        }
        return this.getNativeBinaryStream(columnIndex);
    }
    
    public InputStream getAsciiStream(final String columnName) throws SQLException {
        return this.getAsciiStream(this.findColumn(columnName));
    }
    
    public BigDecimal getBigDecimal(final int columnIndex) throws SQLException {
        if (!this.isBinaryEncoded) {
            final String stringVal = this.getString(columnIndex);
            if (stringVal != null) {
                if (stringVal.length() == 0) {
                    final BigDecimal val = new BigDecimal(this.convertToZeroLiteralStringWithEmptyCheck());
                    return val;
                }
                try {
                    final BigDecimal val = new BigDecimal(stringVal);
                    return val;
                }
                catch (NumberFormatException ex) {
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009", this.getExceptionInterceptor());
                }
            }
            return null;
        }
        return this.getNativeBigDecimal(columnIndex);
    }
    
    public BigDecimal getBigDecimal(final int columnIndex, final int scale) throws SQLException {
        if (!this.isBinaryEncoded) {
            final String stringVal = this.getString(columnIndex);
            if (stringVal != null) {
                if (stringVal.length() == 0) {
                    final BigDecimal val = new BigDecimal(this.convertToZeroLiteralStringWithEmptyCheck());
                    try {
                        return val.setScale(scale);
                    }
                    catch (ArithmeticException ex) {
                        try {
                            return val.setScale(scale, 4);
                        }
                        catch (ArithmeticException arEx) {
                            throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, new Integer(columnIndex) }), "S1009", this.getExceptionInterceptor());
                        }
                    }
                }
                BigDecimal val;
                try {
                    val = new BigDecimal(stringVal);
                }
                catch (NumberFormatException ex2) {
                    if (this.fields[columnIndex - 1].getMysqlType() != 16) {
                        throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { Constants.integerValueOf(columnIndex), stringVal }), "S1009", this.getExceptionInterceptor());
                    }
                    final long valueAsLong = this.getNumericRepresentationOfSQLBitType(columnIndex);
                    val = new BigDecimal(valueAsLong);
                }
                try {
                    return val.setScale(scale);
                }
                catch (ArithmeticException ex) {
                    try {
                        return val.setScale(scale, 4);
                    }
                    catch (ArithmeticException arithEx) {
                        throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { Constants.integerValueOf(columnIndex), stringVal }), "S1009", this.getExceptionInterceptor());
                    }
                }
            }
            return null;
        }
        return this.getNativeBigDecimal(columnIndex, scale);
    }
    
    public BigDecimal getBigDecimal(final String columnName) throws SQLException {
        return this.getBigDecimal(this.findColumn(columnName));
    }
    
    public BigDecimal getBigDecimal(final String columnName, final int scale) throws SQLException {
        return this.getBigDecimal(this.findColumn(columnName), scale);
    }
    
    private final BigDecimal getBigDecimalFromString(final String stringVal, final int columnIndex, final int scale) throws SQLException {
        if (stringVal != null) {
            Label_0075: {
                if (stringVal.length() != 0) {
                    break Label_0075;
                }
                final BigDecimal bdVal = new BigDecimal(this.convertToZeroLiteralStringWithEmptyCheck());
                try {
                    return bdVal.setScale(scale);
                }
                catch (ArithmeticException ex) {
                    try {
                        return bdVal.setScale(scale, 4);
                    }
                    catch (ArithmeticException arEx) {
                        throw new SQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009");
                    }
                }
                try {
                    try {
                        return new BigDecimal(stringVal).setScale(scale);
                    }
                    catch (ArithmeticException ex) {
                        try {
                            return new BigDecimal(stringVal).setScale(scale, 4);
                        }
                        catch (ArithmeticException arEx) {
                            throw new SQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009");
                        }
                    }
                }
                catch (NumberFormatException ex2) {
                    if (this.fields[columnIndex - 1].getMysqlType() == 16) {
                        final long valueAsLong = this.getNumericRepresentationOfSQLBitType(columnIndex);
                        try {
                            return new BigDecimal(valueAsLong).setScale(scale);
                        }
                        catch (ArithmeticException arEx2) {
                            try {
                                return new BigDecimal(valueAsLong).setScale(scale, 4);
                            }
                            catch (ArithmeticException arEx3) {
                                throw new SQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009");
                            }
                        }
                    }
                    if (this.fields[columnIndex - 1].getMysqlType() == 1 && this.connection.getTinyInt1isBit() && this.fields[columnIndex - 1].getLength() == 1L) {
                        return new BigDecimal(stringVal.equalsIgnoreCase("true") ? 1 : 0).setScale(scale);
                    }
                    throw new SQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009");
                }
            }
        }
        return null;
    }
    
    public InputStream getBinaryStream(final int columnIndex) throws SQLException {
        this.checkRowPos();
        if (this.isBinaryEncoded) {
            return this.getNativeBinaryStream(columnIndex);
        }
        this.checkColumnBounds(columnIndex);
        final int columnIndexMinusOne = columnIndex - 1;
        if (this.thisRow.isNull(columnIndexMinusOne)) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        return this.thisRow.getBinaryInputStream(columnIndexMinusOne);
    }
    
    public InputStream getBinaryStream(final String columnName) throws SQLException {
        return this.getBinaryStream(this.findColumn(columnName));
    }
    
    public Blob getBlob(final int columnIndex) throws SQLException {
        if (this.isBinaryEncoded) {
            return this.getNativeBlob(columnIndex);
        }
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        final int columnIndexMinusOne = columnIndex - 1;
        if (this.thisRow.isNull(columnIndexMinusOne)) {
            this.wasNullFlag = true;
        }
        else {
            this.wasNullFlag = false;
        }
        if (this.wasNullFlag) {
            return null;
        }
        if (!this.connection.getEmulateLocators()) {
            return new com.mysql.jdbc.Blob(this.thisRow.getColumnValue(columnIndexMinusOne), this.getExceptionInterceptor());
        }
        return new BlobFromLocator(this, columnIndex, this.getExceptionInterceptor());
    }
    
    public Blob getBlob(final String colName) throws SQLException {
        return this.getBlob(this.findColumn(colName));
    }
    
    public boolean getBoolean(final int columnIndex) throws SQLException {
        this.checkColumnBounds(columnIndex);
        final int columnIndexMinusOne = columnIndex - 1;
        final Field field = this.fields[columnIndexMinusOne];
        if (field.getMysqlType() == 16) {
            return this.byteArrayToBoolean(columnIndexMinusOne);
        }
        this.wasNullFlag = false;
        final int sqlType = field.getSQLType();
        switch (sqlType) {
            case 16: {
                if (field.getMysqlType() == -1) {
                    final String stringVal = this.getString(columnIndex);
                    return this.getBooleanFromString(stringVal, columnIndex);
                }
                final long boolVal = this.getLong(columnIndex, false);
                return boolVal == -1L || boolVal > 0L;
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
                final long boolVal = this.getLong(columnIndex, false);
                return boolVal == -1L || boolVal > 0L;
            }
            default: {
                if (this.connection.getPedantic()) {
                    switch (sqlType) {
                        case -4:
                        case -3:
                        case -2:
                        case 70:
                        case 91:
                        case 92:
                        case 93:
                        case 2000:
                        case 2002:
                        case 2003:
                        case 2004:
                        case 2005:
                        case 2006: {
                            throw SQLError.createSQLException("Required type conversion not allowed", "22018", this.getExceptionInterceptor());
                        }
                    }
                }
                if (sqlType == -2 || sqlType == -3 || sqlType == -4 || sqlType == 2004) {
                    return this.byteArrayToBoolean(columnIndexMinusOne);
                }
                if (this.useUsageAdvisor) {
                    this.issueConversionViaParsingWarning("getBoolean()", columnIndex, this.thisRow.getColumnValue(columnIndexMinusOne), this.fields[columnIndex], new int[] { 16, 5, 1, 2, 3, 8, 4 });
                }
                final String stringVal2 = this.getString(columnIndex);
                return this.getBooleanFromString(stringVal2, columnIndex);
            }
        }
    }
    
    private boolean byteArrayToBoolean(final int columnIndexMinusOne) throws SQLException {
        final Object value = this.thisRow.getColumnValue(columnIndexMinusOne);
        if (value == null) {
            this.wasNullFlag = true;
            return false;
        }
        this.wasNullFlag = false;
        if (((byte[])value).length == 0) {
            return false;
        }
        final byte boolVal = ((byte[])value)[0];
        return boolVal == 49 || (boolVal != 48 && (boolVal == -1 || boolVal > 0));
    }
    
    public boolean getBoolean(final String columnName) throws SQLException {
        return this.getBoolean(this.findColumn(columnName));
    }
    
    private final boolean getBooleanFromString(final String stringVal, final int columnIndex) throws SQLException {
        if (stringVal != null && stringVal.length() > 0) {
            final int c = Character.toLowerCase(stringVal.charAt(0));
            return c == 116 || c == 121 || c == 49 || stringVal.equals("-1");
        }
        return false;
    }
    
    public byte getByte(final int columnIndex) throws SQLException {
        if (this.isBinaryEncoded) {
            return this.getNativeByte(columnIndex);
        }
        final String stringVal = this.getString(columnIndex);
        if (this.wasNullFlag || stringVal == null) {
            return 0;
        }
        return this.getByteFromString(stringVal, columnIndex);
    }
    
    public byte getByte(final String columnName) throws SQLException {
        return this.getByte(this.findColumn(columnName));
    }
    
    private final byte getByteFromString(String stringVal, final int columnIndex) throws SQLException {
        if (stringVal != null && stringVal.length() == 0) {
            return (byte)this.convertToZeroWithEmptyCheck();
        }
        if (stringVal == null) {
            return 0;
        }
        stringVal = stringVal.trim();
        try {
            final int decimalIndex = stringVal.indexOf(".");
            if (decimalIndex != -1) {
                final double valueAsDouble = Double.parseDouble(stringVal);
                if (this.jdbcCompliantTruncationForReads && (valueAsDouble < -128.0 || valueAsDouble > 127.0)) {
                    this.throwRangeException(stringVal, columnIndex, -6);
                }
                return (byte)valueAsDouble;
            }
            final long valueAsLong = Long.parseLong(stringVal);
            if (this.jdbcCompliantTruncationForReads && (valueAsLong < -128L || valueAsLong > 127L)) {
                this.throwRangeException(String.valueOf(valueAsLong), columnIndex, -6);
            }
            return (byte)valueAsLong;
        }
        catch (NumberFormatException NFE) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Value____173") + stringVal + Messages.getString("ResultSet.___is_out_of_range_[-127,127]_174"), "S1009", this.getExceptionInterceptor());
        }
    }
    
    public byte[] getBytes(final int columnIndex) throws SQLException {
        return this.getBytes(columnIndex, false);
    }
    
    protected byte[] getBytes(final int columnIndex, final boolean noConversion) throws SQLException {
        if (this.isBinaryEncoded) {
            return this.getNativeBytes(columnIndex, noConversion);
        }
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        final int columnIndexMinusOne = columnIndex - 1;
        if (this.thisRow.isNull(columnIndexMinusOne)) {
            this.wasNullFlag = true;
        }
        else {
            this.wasNullFlag = false;
        }
        if (this.wasNullFlag) {
            return null;
        }
        return this.thisRow.getColumnValue(columnIndexMinusOne);
    }
    
    public byte[] getBytes(final String columnName) throws SQLException {
        return this.getBytes(this.findColumn(columnName));
    }
    
    private final byte[] getBytesFromString(final String stringVal, final int columnIndex) throws SQLException {
        if (stringVal != null) {
            return StringUtils.getBytes(stringVal, this.connection.getEncoding(), this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.connection, this.getExceptionInterceptor());
        }
        return null;
    }
    
    public int getBytesSize() throws SQLException {
        final RowData localRowData = this.rowData;
        this.checkClosed();
        if (localRowData instanceof RowDataStatic) {
            int bytesSize = 0;
            for (int numRows = localRowData.size(), i = 0; i < numRows; ++i) {
                bytesSize += localRowData.getAt(i).getBytesSize();
            }
            return bytesSize;
        }
        return -1;
    }
    
    protected Calendar getCalendarInstanceForSessionOrNew() {
        if (this.connection != null) {
            return this.connection.getCalendarInstanceForSessionOrNew();
        }
        return new GregorianCalendar();
    }
    
    public Reader getCharacterStream(final int columnIndex) throws SQLException {
        if (this.isBinaryEncoded) {
            return this.getNativeCharacterStream(columnIndex);
        }
        this.checkColumnBounds(columnIndex);
        final int columnIndexMinusOne = columnIndex - 1;
        if (this.thisRow.isNull(columnIndexMinusOne)) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        return this.thisRow.getReader(columnIndexMinusOne);
    }
    
    public Reader getCharacterStream(final String columnName) throws SQLException {
        return this.getCharacterStream(this.findColumn(columnName));
    }
    
    private final Reader getCharacterStreamFromString(final String stringVal, final int columnIndex) throws SQLException {
        if (stringVal != null) {
            return new StringReader(stringVal);
        }
        return null;
    }
    
    public Clob getClob(final int i) throws SQLException {
        if (this.isBinaryEncoded) {
            return this.getNativeClob(i);
        }
        final String asString = this.getStringForClob(i);
        if (asString == null) {
            return null;
        }
        return new com.mysql.jdbc.Clob(asString, this.getExceptionInterceptor());
    }
    
    public Clob getClob(final String colName) throws SQLException {
        return this.getClob(this.findColumn(colName));
    }
    
    private final Clob getClobFromString(final String stringVal, final int columnIndex) throws SQLException {
        return new com.mysql.jdbc.Clob(stringVal, this.getExceptionInterceptor());
    }
    
    public int getConcurrency() throws SQLException {
        return 1007;
    }
    
    public String getCursorName() throws SQLException {
        throw SQLError.createSQLException(Messages.getString("ResultSet.Positioned_Update_not_supported"), "S1C00", this.getExceptionInterceptor());
    }
    
    public Date getDate(final int columnIndex) throws SQLException {
        return this.getDate(columnIndex, null);
    }
    
    public Date getDate(final int columnIndex, final Calendar cal) throws SQLException {
        if (this.isBinaryEncoded) {
            return this.getNativeDate(columnIndex, cal);
        }
        if (!this.useFastDateParsing) {
            final String stringVal = this.getStringInternal(columnIndex, false);
            if (stringVal == null) {
                return null;
            }
            return this.getDateFromString(stringVal, columnIndex, cal);
        }
        else {
            this.checkColumnBounds(columnIndex);
            final int columnIndexMinusOne = columnIndex - 1;
            if (this.thisRow.isNull(columnIndexMinusOne)) {
                this.wasNullFlag = true;
                return null;
            }
            this.wasNullFlag = false;
            return this.thisRow.getDateFast(columnIndexMinusOne, this.connection, this, cal);
        }
    }
    
    public Date getDate(final String columnName) throws SQLException {
        return this.getDate(this.findColumn(columnName));
    }
    
    public Date getDate(final String columnName, final Calendar cal) throws SQLException {
        return this.getDate(this.findColumn(columnName), cal);
    }
    
    private final Date getDateFromString(String stringVal, final int columnIndex, final Calendar targetCalendar) throws SQLException {
        int year = 0;
        int month = 0;
        int day = 0;
        try {
            this.wasNullFlag = false;
            if (stringVal == null) {
                this.wasNullFlag = true;
                return null;
            }
            stringVal = stringVal.trim();
            if (stringVal.equals("0") || stringVal.equals("0000-00-00") || stringVal.equals("0000-00-00 00:00:00") || stringVal.equals("00000000000000") || stringVal.equals("0")) {
                if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                    this.wasNullFlag = true;
                    return null;
                }
                if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                    throw SQLError.createSQLException("Value '" + stringVal + "' can not be represented as java.sql.Date", "S1009", this.getExceptionInterceptor());
                }
                return this.fastDateCreate(targetCalendar, 1, 1, 1);
            }
            else if (this.fields[columnIndex - 1].getMysqlType() == 7) {
                switch (stringVal.length()) {
                    case 19:
                    case 21: {
                        year = Integer.parseInt(stringVal.substring(0, 4));
                        month = Integer.parseInt(stringVal.substring(5, 7));
                        day = Integer.parseInt(stringVal.substring(8, 10));
                        return this.fastDateCreate(targetCalendar, year, month, day);
                    }
                    case 8:
                    case 14: {
                        year = Integer.parseInt(stringVal.substring(0, 4));
                        month = Integer.parseInt(stringVal.substring(4, 6));
                        day = Integer.parseInt(stringVal.substring(6, 8));
                        return this.fastDateCreate(targetCalendar, year, month, day);
                    }
                    case 6:
                    case 10:
                    case 12: {
                        year = Integer.parseInt(stringVal.substring(0, 2));
                        if (year <= 69) {
                            year += 100;
                        }
                        month = Integer.parseInt(stringVal.substring(2, 4));
                        day = Integer.parseInt(stringVal.substring(4, 6));
                        return this.fastDateCreate(targetCalendar, year + 1900, month, day);
                    }
                    case 4: {
                        year = Integer.parseInt(stringVal.substring(0, 4));
                        if (year <= 69) {
                            year += 100;
                        }
                        month = Integer.parseInt(stringVal.substring(2, 4));
                        return this.fastDateCreate(targetCalendar, year + 1900, month, 1);
                    }
                    case 2: {
                        year = Integer.parseInt(stringVal.substring(0, 2));
                        if (year <= 69) {
                            year += 100;
                        }
                        return this.fastDateCreate(targetCalendar, year + 1900, 1, 1);
                    }
                    default: {
                        throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009", this.getExceptionInterceptor());
                    }
                }
            }
            else {
                if (this.fields[columnIndex - 1].getMysqlType() == 13) {
                    if (stringVal.length() == 2 || stringVal.length() == 1) {
                        year = Integer.parseInt(stringVal);
                        if (year <= 69) {
                            year += 100;
                        }
                        year += 1900;
                    }
                    else {
                        year = Integer.parseInt(stringVal.substring(0, 4));
                    }
                    return this.fastDateCreate(targetCalendar, year, 1, 1);
                }
                if (this.fields[columnIndex - 1].getMysqlType() == 11) {
                    return this.fastDateCreate(targetCalendar, 1970, 1, 1);
                }
                if (stringVal.length() >= 10) {
                    if (stringVal.length() != 18) {
                        year = Integer.parseInt(stringVal.substring(0, 4));
                        month = Integer.parseInt(stringVal.substring(5, 7));
                        day = Integer.parseInt(stringVal.substring(8, 10));
                    }
                    else {
                        final StringTokenizer st = new StringTokenizer(stringVal, "- ");
                        year = Integer.parseInt(st.nextToken());
                        month = Integer.parseInt(st.nextToken());
                        day = Integer.parseInt(st.nextToken());
                    }
                    return this.fastDateCreate(targetCalendar, year, month, day);
                }
                if (stringVal.length() == 8) {
                    return this.fastDateCreate(targetCalendar, 1970, 1, 1);
                }
                throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009", this.getExceptionInterceptor());
            }
        }
        catch (SQLException sqlEx) {
            throw sqlEx;
        }
        catch (Exception e) {
            final SQLException sqlEx2 = SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Date", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009", this.getExceptionInterceptor());
            sqlEx2.initCause(e);
            throw sqlEx2;
        }
    }
    
    private TimeZone getDefaultTimeZone() {
        if (!this.useLegacyDatetimeCode && this.connection != null) {
            return this.serverTimeZoneTz;
        }
        return this.connection.getDefaultTimeZone();
    }
    
    public double getDouble(final int columnIndex) throws SQLException {
        if (!this.isBinaryEncoded) {
            return this.getDoubleInternal(columnIndex);
        }
        return this.getNativeDouble(columnIndex);
    }
    
    public double getDouble(final String columnName) throws SQLException {
        return this.getDouble(this.findColumn(columnName));
    }
    
    private final double getDoubleFromString(final String stringVal, final int columnIndex) throws SQLException {
        return this.getDoubleInternal(stringVal, columnIndex);
    }
    
    protected double getDoubleInternal(final int colIndex) throws SQLException {
        return this.getDoubleInternal(this.getString(colIndex), colIndex);
    }
    
    protected double getDoubleInternal(final String stringVal, final int colIndex) throws SQLException {
        try {
            if (stringVal == null) {
                return 0.0;
            }
            if (stringVal.length() == 0) {
                return this.convertToZeroWithEmptyCheck();
            }
            double d = Double.parseDouble(stringVal);
            if (this.useStrictFloatingPoint) {
                if (d == 2.147483648E9) {
                    d = 2.147483647E9;
                }
                else if (d == 1.0000000036275E-15) {
                    d = 1.0E-15;
                }
                else if (d == 9.999999869911E14) {
                    d = 9.99999999999999E14;
                }
                else if (d == 1.4012984643248E-45) {
                    d = 1.4E-45;
                }
                else if (d == 1.4013E-45) {
                    d = 1.4E-45;
                }
                else if (d == 3.4028234663853E37) {
                    d = 3.4028235E37;
                }
                else if (d == -2.14748E9) {
                    d = -2.147483648E9;
                }
                else if (d == 3.40282E37) {
                    d = 3.4028235E37;
                }
            }
            return d;
        }
        catch (NumberFormatException e) {
            if (this.fields[colIndex - 1].getMysqlType() == 16) {
                final long valueAsLong = this.getNumericRepresentationOfSQLBitType(colIndex);
                return valueAsLong;
            }
            throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_number", new Object[] { stringVal, Constants.integerValueOf(colIndex) }), "S1009", this.getExceptionInterceptor());
        }
    }
    
    public int getFetchDirection() throws SQLException {
        return this.fetchDirection;
    }
    
    public int getFetchSize() throws SQLException {
        return this.fetchSize;
    }
    
    public char getFirstCharOfQuery() {
        return this.firstCharOfQuery;
    }
    
    public float getFloat(final int columnIndex) throws SQLException {
        if (!this.isBinaryEncoded) {
            String val = null;
            val = this.getString(columnIndex);
            return this.getFloatFromString(val, columnIndex);
        }
        return this.getNativeFloat(columnIndex);
    }
    
    public float getFloat(final String columnName) throws SQLException {
        return this.getFloat(this.findColumn(columnName));
    }
    
    private final float getFloatFromString(final String val, final int columnIndex) throws SQLException {
        try {
            if (val == null) {
                return 0.0f;
            }
            if (val.length() == 0) {
                return this.convertToZeroWithEmptyCheck();
            }
            final float f = Float.parseFloat(val);
            if (this.jdbcCompliantTruncationForReads && (f == Float.MIN_VALUE || f == Float.MAX_VALUE)) {
                final double valAsDouble = Double.parseDouble(val);
                if (valAsDouble < 1.401298464324817E-45 - ResultSetImpl.MIN_DIFF_PREC || valAsDouble > 3.4028234663852886E38 - ResultSetImpl.MAX_DIFF_PREC) {
                    this.throwRangeException(String.valueOf(valAsDouble), columnIndex, 6);
                }
            }
            return f;
        }
        catch (NumberFormatException nfe) {
            try {
                final Double valueAsDouble = new Double(val);
                final float valueAsFloat = (float)(Object)valueAsDouble;
                if (this.jdbcCompliantTruncationForReads && ((this.jdbcCompliantTruncationForReads && valueAsFloat == Float.NEGATIVE_INFINITY) || valueAsFloat == Float.POSITIVE_INFINITY)) {
                    this.throwRangeException(valueAsDouble.toString(), columnIndex, 6);
                }
                return valueAsFloat;
            }
            catch (NumberFormatException newNfe) {
                throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getFloat()_-____200") + val + Messages.getString("ResultSet.___in_column__201") + columnIndex, "S1009", this.getExceptionInterceptor());
            }
        }
    }
    
    public int getInt(final int columnIndex) throws SQLException {
        this.checkRowPos();
        if (!this.isBinaryEncoded) {
            final int columnIndexMinusOne = columnIndex - 1;
            if (this.useFastIntParsing) {
                this.checkColumnBounds(columnIndex);
                if (this.thisRow.isNull(columnIndexMinusOne)) {
                    this.wasNullFlag = true;
                }
                else {
                    this.wasNullFlag = false;
                }
                if (this.wasNullFlag) {
                    return 0;
                }
                if (this.thisRow.length(columnIndexMinusOne) == 0L) {
                    return this.convertToZeroWithEmptyCheck();
                }
                final boolean needsFullParse = this.thisRow.isFloatingPointNumber(columnIndexMinusOne);
                if (!needsFullParse) {
                    try {
                        return this.getIntWithOverflowCheck(columnIndexMinusOne);
                    }
                    catch (NumberFormatException nfe) {
                        try {
                            return this.parseIntAsDouble(columnIndex, this.thisRow.getString(columnIndexMinusOne, this.fields[columnIndexMinusOne].getCharacterSet(), this.connection));
                        }
                        catch (NumberFormatException newNfe) {
                            if (this.fields[columnIndexMinusOne].getMysqlType() == 16) {
                                final long valueAsLong = this.getNumericRepresentationOfSQLBitType(columnIndex);
                                if (this.connection.getJdbcCompliantTruncationForReads() && (valueAsLong < -2147483648L || valueAsLong > 2147483647L)) {
                                    this.throwRangeException(String.valueOf(valueAsLong), columnIndex, 4);
                                }
                                return (int)valueAsLong;
                            }
                            throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getInt()_-____74") + this.thisRow.getString(columnIndexMinusOne, this.fields[columnIndexMinusOne].getCharacterSet(), this.connection) + "'", "S1009", this.getExceptionInterceptor());
                        }
                    }
                }
            }
            String val = null;
            try {
                val = this.getString(columnIndex);
                if (val == null) {
                    return 0;
                }
                if (val.length() == 0) {
                    return this.convertToZeroWithEmptyCheck();
                }
                if (val.indexOf("e") == -1 && val.indexOf("E") == -1 && val.indexOf(".") == -1) {
                    final int intVal = Integer.parseInt(val);
                    this.checkForIntegerTruncation(columnIndexMinusOne, null, intVal);
                    return intVal;
                }
                final int intVal = this.parseIntAsDouble(columnIndex, val);
                this.checkForIntegerTruncation(columnIndex, null, intVal);
                return intVal;
            }
            catch (NumberFormatException nfe) {
                try {
                    return this.parseIntAsDouble(columnIndex, val);
                }
                catch (NumberFormatException newNfe) {
                    if (this.fields[columnIndexMinusOne].getMysqlType() == 16) {
                        final long valueAsLong = this.getNumericRepresentationOfSQLBitType(columnIndex);
                        if (this.jdbcCompliantTruncationForReads && (valueAsLong < -2147483648L || valueAsLong > 2147483647L)) {
                            this.throwRangeException(String.valueOf(valueAsLong), columnIndex, 4);
                        }
                        return (int)valueAsLong;
                    }
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getInt()_-____74") + val + "'", "S1009", this.getExceptionInterceptor());
                }
            }
        }
        return this.getNativeInt(columnIndex);
    }
    
    public int getInt(final String columnName) throws SQLException {
        return this.getInt(this.findColumn(columnName));
    }
    
    private final int getIntFromString(String val, final int columnIndex) throws SQLException {
        try {
            if (val == null) {
                return 0;
            }
            if (val.length() == 0) {
                return this.convertToZeroWithEmptyCheck();
            }
            if (val.indexOf("e") == -1 && val.indexOf("E") == -1 && val.indexOf(".") == -1) {
                val = val.trim();
                final int valueAsInt = Integer.parseInt(val);
                if (this.jdbcCompliantTruncationForReads && (valueAsInt == Integer.MIN_VALUE || valueAsInt == Integer.MAX_VALUE)) {
                    final long valueAsLong = Long.parseLong(val);
                    if (valueAsLong < -2147483648L || valueAsLong > 2147483647L) {
                        this.throwRangeException(String.valueOf(valueAsLong), columnIndex, 4);
                    }
                }
                return valueAsInt;
            }
            final double valueAsDouble = Double.parseDouble(val);
            if (this.jdbcCompliantTruncationForReads && (valueAsDouble < -2.147483648E9 || valueAsDouble > 2.147483647E9)) {
                this.throwRangeException(String.valueOf(valueAsDouble), columnIndex, 4);
            }
            return (int)valueAsDouble;
        }
        catch (NumberFormatException nfe) {
            try {
                final double valueAsDouble2 = Double.parseDouble(val);
                if (this.jdbcCompliantTruncationForReads && (valueAsDouble2 < -2.147483648E9 || valueAsDouble2 > 2.147483647E9)) {
                    this.throwRangeException(String.valueOf(valueAsDouble2), columnIndex, 4);
                }
                return (int)valueAsDouble2;
            }
            catch (NumberFormatException newNfe) {
                throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getInt()_-____206") + val + Messages.getString("ResultSet.___in_column__207") + columnIndex, "S1009", this.getExceptionInterceptor());
            }
        }
    }
    
    public long getLong(final int columnIndex) throws SQLException {
        return this.getLong(columnIndex, true);
    }
    
    private long getLong(final int columnIndex, final boolean overflowCheck) throws SQLException {
        if (!this.isBinaryEncoded) {
            this.checkRowPos();
            final int columnIndexMinusOne = columnIndex - 1;
            if (this.useFastIntParsing) {
                this.checkColumnBounds(columnIndex);
                if (this.thisRow.isNull(columnIndexMinusOne)) {
                    this.wasNullFlag = true;
                }
                else {
                    this.wasNullFlag = false;
                }
                if (this.wasNullFlag) {
                    return 0L;
                }
                if (this.thisRow.length(columnIndexMinusOne) == 0L) {
                    return this.convertToZeroWithEmptyCheck();
                }
                final boolean needsFullParse = this.thisRow.isFloatingPointNumber(columnIndexMinusOne);
                if (!needsFullParse) {
                    try {
                        return this.getLongWithOverflowCheck(columnIndexMinusOne, overflowCheck);
                    }
                    catch (NumberFormatException nfe) {
                        try {
                            return this.parseLongAsDouble(columnIndexMinusOne, this.thisRow.getString(columnIndexMinusOne, this.fields[columnIndexMinusOne].getCharacterSet(), this.connection));
                        }
                        catch (NumberFormatException newNfe) {
                            if (this.fields[columnIndexMinusOne].getMysqlType() == 16) {
                                return this.getNumericRepresentationOfSQLBitType(columnIndex);
                            }
                            throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getLong()_-____79") + this.thisRow.getString(columnIndexMinusOne, this.fields[columnIndexMinusOne].getCharacterSet(), this.connection) + "'", "S1009", this.getExceptionInterceptor());
                        }
                    }
                }
            }
            String val = null;
            try {
                val = this.getString(columnIndex);
                if (val == null) {
                    return 0L;
                }
                if (val.length() == 0) {
                    return this.convertToZeroWithEmptyCheck();
                }
                if (val.indexOf("e") == -1 && val.indexOf("E") == -1) {
                    return this.parseLongWithOverflowCheck(columnIndexMinusOne, null, val, overflowCheck);
                }
                return this.parseLongAsDouble(columnIndexMinusOne, val);
            }
            catch (NumberFormatException nfe) {
                try {
                    return this.parseLongAsDouble(columnIndexMinusOne, val);
                }
                catch (NumberFormatException newNfe) {
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getLong()_-____79") + val + "'", "S1009", this.getExceptionInterceptor());
                }
            }
        }
        return this.getNativeLong(columnIndex, overflowCheck, true);
    }
    
    public long getLong(final String columnName) throws SQLException {
        return this.getLong(this.findColumn(columnName));
    }
    
    private final long getLongFromString(final String val, final int columnIndexZeroBased) throws SQLException {
        try {
            if (val == null) {
                return 0L;
            }
            if (val.length() == 0) {
                return this.convertToZeroWithEmptyCheck();
            }
            if (val.indexOf("e") == -1 && val.indexOf("E") == -1) {
                return this.parseLongWithOverflowCheck(columnIndexZeroBased, null, val, true);
            }
            return this.parseLongAsDouble(columnIndexZeroBased, val);
        }
        catch (NumberFormatException nfe) {
            try {
                return this.parseLongAsDouble(columnIndexZeroBased, val);
            }
            catch (NumberFormatException newNfe) {
                throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getLong()_-____211") + val + Messages.getString("ResultSet.___in_column__212") + (columnIndexZeroBased + 1), "S1009", this.getExceptionInterceptor());
            }
        }
    }
    
    public ResultSetMetaData getMetaData() throws SQLException {
        this.checkClosed();
        return new com.mysql.jdbc.ResultSetMetaData(this.fields, this.connection.getUseOldAliasMetadataBehavior(), this.getExceptionInterceptor());
    }
    
    protected Array getNativeArray(final int i) throws SQLException {
        throw SQLError.notImplemented();
    }
    
    protected InputStream getNativeAsciiStream(final int columnIndex) throws SQLException {
        this.checkRowPos();
        return this.getNativeBinaryStream(columnIndex);
    }
    
    protected BigDecimal getNativeBigDecimal(final int columnIndex) throws SQLException {
        this.checkColumnBounds(columnIndex);
        final int scale = this.fields[columnIndex - 1].getDecimals();
        return this.getNativeBigDecimal(columnIndex, scale);
    }
    
    protected BigDecimal getNativeBigDecimal(final int columnIndex, final int scale) throws SQLException {
        this.checkColumnBounds(columnIndex);
        String stringVal = null;
        final Field f = this.fields[columnIndex - 1];
        final Object value = this.thisRow.getColumnValue(columnIndex - 1);
        if (value == null) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        switch (f.getSQLType()) {
            case 2:
            case 3: {
                stringVal = StringUtils.toAsciiString((byte[])value);
                break;
            }
            default: {
                stringVal = this.getNativeString(columnIndex);
                break;
            }
        }
        return this.getBigDecimalFromString(stringVal, columnIndex, scale);
    }
    
    protected InputStream getNativeBinaryStream(final int columnIndex) throws SQLException {
        this.checkRowPos();
        final int columnIndexMinusOne = columnIndex - 1;
        if (this.thisRow.isNull(columnIndexMinusOne)) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        switch (this.fields[columnIndexMinusOne].getSQLType()) {
            case -7:
            case -4:
            case -3:
            case -2:
            case 2004: {
                return this.thisRow.getBinaryInputStream(columnIndexMinusOne);
            }
            default: {
                final byte[] b = this.getNativeBytes(columnIndex, false);
                if (b != null) {
                    return new ByteArrayInputStream(b);
                }
                return null;
            }
        }
    }
    
    protected Blob getNativeBlob(final int columnIndex) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        final Object value = this.thisRow.getColumnValue(columnIndex - 1);
        if (value == null) {
            this.wasNullFlag = true;
        }
        else {
            this.wasNullFlag = false;
        }
        if (this.wasNullFlag) {
            return null;
        }
        final int mysqlType = this.fields[columnIndex - 1].getMysqlType();
        byte[] dataAsBytes = null;
        switch (mysqlType) {
            case 249:
            case 250:
            case 251:
            case 252: {
                dataAsBytes = (byte[])value;
                break;
            }
            default: {
                dataAsBytes = this.getNativeBytes(columnIndex, false);
                break;
            }
        }
        if (!this.connection.getEmulateLocators()) {
            return new com.mysql.jdbc.Blob(dataAsBytes, this.getExceptionInterceptor());
        }
        return new BlobFromLocator(this, columnIndex, this.getExceptionInterceptor());
    }
    
    public static boolean arraysEqual(final byte[] left, final byte[] right) {
        if (left == null) {
            return right == null;
        }
        if (right == null) {
            return false;
        }
        if (left.length != right.length) {
            return false;
        }
        for (int i = 0; i < left.length; ++i) {
            if (left[i] != right[i]) {
                return false;
            }
        }
        return true;
    }
    
    protected byte getNativeByte(final int columnIndex) throws SQLException {
        return this.getNativeByte(columnIndex, true);
    }
    
    protected byte getNativeByte(int columnIndex, final boolean overflowCheck) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        final Object value = this.thisRow.getColumnValue(columnIndex - 1);
        if (value == null) {
            this.wasNullFlag = true;
            return 0;
        }
        if (value == null) {
            this.wasNullFlag = true;
        }
        else {
            this.wasNullFlag = false;
        }
        if (this.wasNullFlag) {
            return 0;
        }
        --columnIndex;
        final Field field = this.fields[columnIndex];
        switch (field.getMysqlType()) {
            case 16: {
                final long valueAsLong = this.getNumericRepresentationOfSQLBitType(columnIndex + 1);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && (valueAsLong < -128L || valueAsLong > 127L)) {
                    this.throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, -6);
                }
                return (byte)valueAsLong;
            }
            case 1: {
                final byte valueAsByte = ((byte[])value)[0];
                if (!field.isUnsigned()) {
                    return valueAsByte;
                }
                final short valueAsShort = (valueAsByte >= 0) ? valueAsByte : ((short)(valueAsByte + 256));
                if (overflowCheck && this.jdbcCompliantTruncationForReads && valueAsShort > 127) {
                    this.throwRangeException(String.valueOf(valueAsShort), columnIndex + 1, -6);
                }
                return (byte)valueAsShort;
            }
            case 2:
            case 13: {
                final short valueAsShort = this.getNativeShort(columnIndex + 1);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && (valueAsShort < -128 || valueAsShort > 127)) {
                    this.throwRangeException(String.valueOf(valueAsShort), columnIndex + 1, -6);
                }
                return (byte)valueAsShort;
            }
            case 3:
            case 9: {
                final int valueAsInt = this.getNativeInt(columnIndex + 1, false);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && (valueAsInt < -128 || valueAsInt > 127)) {
                    this.throwRangeException(String.valueOf(valueAsInt), columnIndex + 1, -6);
                }
                return (byte)valueAsInt;
            }
            case 4: {
                final float valueAsFloat = this.getNativeFloat(columnIndex + 1);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && (valueAsFloat < -128.0f || valueAsFloat > 127.0f)) {
                    this.throwRangeException(String.valueOf(valueAsFloat), columnIndex + 1, -6);
                }
                return (byte)valueAsFloat;
            }
            case 5: {
                final double valueAsDouble = this.getNativeDouble(columnIndex + 1);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && (valueAsDouble < -128.0 || valueAsDouble > 127.0)) {
                    this.throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, -6);
                }
                return (byte)valueAsDouble;
            }
            case 8: {
                final long valueAsLong = this.getNativeLong(columnIndex + 1, false, true);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && (valueAsLong < -128L || valueAsLong > 127L)) {
                    this.throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, -6);
                }
                return (byte)valueAsLong;
            }
            default: {
                if (this.useUsageAdvisor) {
                    this.issueConversionViaParsingWarning("getByte()", columnIndex, this.thisRow.getColumnValue(columnIndex - 1), this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
                }
                return this.getByteFromString(this.getNativeString(columnIndex + 1), columnIndex + 1);
            }
        }
    }
    
    protected byte[] getNativeBytes(final int columnIndex, final boolean noConversion) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        final Object value = this.thisRow.getColumnValue(columnIndex - 1);
        if (value == null) {
            this.wasNullFlag = true;
        }
        else {
            this.wasNullFlag = false;
        }
        if (this.wasNullFlag) {
            return null;
        }
        final Field field = this.fields[columnIndex - 1];
        int mysqlType = field.getMysqlType();
        if (noConversion) {
            mysqlType = 252;
        }
        switch (mysqlType) {
            case 16:
            case 249:
            case 250:
            case 251:
            case 252: {
                return (byte[])value;
            }
            case 15:
            case 253:
            case 254: {
                if (value instanceof byte[]) {
                    return (byte[])value;
                }
                break;
            }
        }
        final int sqlType = field.getSQLType();
        if (sqlType == -3 || sqlType == -2) {
            return (byte[])value;
        }
        return this.getBytesFromString(this.getNativeString(columnIndex), columnIndex);
    }
    
    protected Reader getNativeCharacterStream(final int columnIndex) throws SQLException {
        final int columnIndexMinusOne = columnIndex - 1;
        switch (this.fields[columnIndexMinusOne].getSQLType()) {
            case -1:
            case 1:
            case 12:
            case 2005: {
                if (this.thisRow.isNull(columnIndexMinusOne)) {
                    this.wasNullFlag = true;
                    return null;
                }
                this.wasNullFlag = false;
                return this.thisRow.getReader(columnIndexMinusOne);
            }
            default: {
                String asString = null;
                asString = this.getStringForClob(columnIndex);
                if (asString == null) {
                    return null;
                }
                return this.getCharacterStreamFromString(asString, columnIndex);
            }
        }
    }
    
    protected Clob getNativeClob(final int columnIndex) throws SQLException {
        final String stringVal = this.getStringForClob(columnIndex);
        if (stringVal == null) {
            return null;
        }
        return this.getClobFromString(stringVal, columnIndex);
    }
    
    private String getNativeConvertToString(final int columnIndex, final Field field) throws SQLException {
        final int sqlType = field.getSQLType();
        final int mysqlType = field.getMysqlType();
        switch (sqlType) {
            case -7: {
                return String.valueOf(this.getNumericRepresentationOfSQLBitType(columnIndex));
            }
            case 16: {
                final boolean booleanVal = this.getBoolean(columnIndex);
                if (this.wasNullFlag) {
                    return null;
                }
                return String.valueOf(booleanVal);
            }
            case -6: {
                final byte tinyintVal = this.getNativeByte(columnIndex, false);
                if (this.wasNullFlag) {
                    return null;
                }
                if (!field.isUnsigned() || tinyintVal >= 0) {
                    return String.valueOf(tinyintVal);
                }
                final short unsignedTinyVal = (short)(tinyintVal & 0xFF);
                return String.valueOf(unsignedTinyVal);
            }
            case 5: {
                int intVal = this.getNativeInt(columnIndex, false);
                if (this.wasNullFlag) {
                    return null;
                }
                if (!field.isUnsigned() || intVal >= 0) {
                    return String.valueOf(intVal);
                }
                intVal &= 0xFFFF;
                return String.valueOf(intVal);
            }
            case 4: {
                final int intVal = this.getNativeInt(columnIndex, false);
                if (this.wasNullFlag) {
                    return null;
                }
                if (!field.isUnsigned() || intVal >= 0 || field.getMysqlType() == 9) {
                    return String.valueOf(intVal);
                }
                final long longVal = intVal & 0xFFFFFFFFL;
                return String.valueOf(longVal);
            }
            case -5: {
                if (!field.isUnsigned()) {
                    final long longVal = this.getNativeLong(columnIndex, false, true);
                    if (this.wasNullFlag) {
                        return null;
                    }
                    return String.valueOf(longVal);
                }
                else {
                    final long longVal = this.getNativeLong(columnIndex, false, false);
                    if (this.wasNullFlag) {
                        return null;
                    }
                    return String.valueOf(convertLongToUlong(longVal));
                }
                break;
            }
            case 7: {
                final float floatVal = this.getNativeFloat(columnIndex);
                if (this.wasNullFlag) {
                    return null;
                }
                return String.valueOf(floatVal);
            }
            case 6:
            case 8: {
                final double doubleVal = this.getNativeDouble(columnIndex);
                if (this.wasNullFlag) {
                    return null;
                }
                return String.valueOf(doubleVal);
            }
            case 2:
            case 3: {
                final String stringVal = StringUtils.toAsciiString(this.thisRow.getColumnValue(columnIndex - 1));
                if (stringVal == null) {
                    this.wasNullFlag = true;
                    return null;
                }
                this.wasNullFlag = false;
                if (stringVal.length() == 0) {
                    final BigDecimal val = new BigDecimal(0);
                    return val.toString();
                }
                BigDecimal val;
                try {
                    val = new BigDecimal(stringVal);
                }
                catch (NumberFormatException ex) {
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, Constants.integerValueOf(columnIndex) }), "S1009", this.getExceptionInterceptor());
                }
                return val.toString();
            }
            case -1:
            case 1:
            case 12: {
                return this.extractStringFromNativeColumn(columnIndex, mysqlType);
            }
            case -4:
            case -3:
            case -2: {
                if (!field.isBlob()) {
                    return this.extractStringFromNativeColumn(columnIndex, mysqlType);
                }
                if (!field.isBinary()) {
                    return this.extractStringFromNativeColumn(columnIndex, mysqlType);
                }
                Object obj;
                final byte[] data = (byte[])(obj = this.getBytes(columnIndex));
                if (data != null && data.length >= 2) {
                    if (data[0] == -84 && data[1] == -19) {
                        try {
                            final ByteArrayInputStream bytesIn = new ByteArrayInputStream(data);
                            final ObjectInputStream objIn = new ObjectInputStream(bytesIn);
                            obj = objIn.readObject();
                            objIn.close();
                            bytesIn.close();
                        }
                        catch (ClassNotFoundException cnfe) {
                            throw SQLError.createSQLException(Messages.getString("ResultSet.Class_not_found___91") + cnfe.toString() + Messages.getString("ResultSet._while_reading_serialized_object_92"), this.getExceptionInterceptor());
                        }
                        catch (IOException ex2) {
                            obj = data;
                        }
                    }
                    return obj.toString();
                }
                return this.extractStringFromNativeColumn(columnIndex, mysqlType);
            }
            case 91: {
                if (mysqlType == 13) {
                    short shortVal = this.getNativeShort(columnIndex);
                    if (this.connection.getYearIsDateType()) {
                        if (field.getLength() == 2L) {
                            if (shortVal <= 69) {
                                shortVal += 100;
                            }
                            shortVal += 1900;
                        }
                        return this.fastDateCreate(null, shortVal, 1, 1).toString();
                    }
                    if (this.wasNullFlag) {
                        return null;
                    }
                    return String.valueOf(shortVal);
                }
                else {
                    if (this.connection.getNoDatetimeStringSync()) {
                        final byte[] asBytes = this.getNativeBytes(columnIndex, true);
                        if (asBytes == null) {
                            return null;
                        }
                        if (asBytes.length == 0) {
                            return "0000-00-00";
                        }
                        final int year = (asBytes[0] & 0xFF) | (asBytes[1] & 0xFF) << 8;
                        final int month = asBytes[2];
                        final int day = asBytes[3];
                        if (year == 0 && month == 0 && day == 0) {
                            return "0000-00-00";
                        }
                    }
                    final Date dt = this.getNativeDate(columnIndex);
                    if (dt == null) {
                        return null;
                    }
                    return String.valueOf(dt);
                }
                break;
            }
            case 92: {
                final Time tm = this.getNativeTime(columnIndex, null, this.defaultTimeZone, false);
                if (tm == null) {
                    return null;
                }
                return String.valueOf(tm);
            }
            case 93: {
                if (this.connection.getNoDatetimeStringSync()) {
                    final byte[] asBytes2 = this.getNativeBytes(columnIndex, true);
                    if (asBytes2 == null) {
                        return null;
                    }
                    if (asBytes2.length == 0) {
                        return "0000-00-00 00:00:00";
                    }
                    final int year2 = (asBytes2[0] & 0xFF) | (asBytes2[1] & 0xFF) << 8;
                    final int month2 = asBytes2[2];
                    final int day2 = asBytes2[3];
                    if (year2 == 0 && month2 == 0 && day2 == 0) {
                        return "0000-00-00 00:00:00";
                    }
                }
                final Timestamp tstamp = this.getNativeTimestamp(columnIndex, null, this.defaultTimeZone, false);
                if (tstamp == null) {
                    return null;
                }
                final String result = String.valueOf(tstamp);
                if (!this.connection.getNoDatetimeStringSync()) {
                    return result;
                }
                if (result.endsWith(".0")) {
                    return result.substring(0, result.length() - 2);
                }
                break;
            }
        }
        return this.extractStringFromNativeColumn(columnIndex, mysqlType);
    }
    
    protected Date getNativeDate(final int columnIndex) throws SQLException {
        return this.getNativeDate(columnIndex, null);
    }
    
    protected Date getNativeDate(final int columnIndex, final Calendar cal) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        final int columnIndexMinusOne = columnIndex - 1;
        final int mysqlType = this.fields[columnIndexMinusOne].getMysqlType();
        Date dateToReturn = null;
        if (mysqlType == 10) {
            dateToReturn = this.thisRow.getNativeDate(columnIndexMinusOne, this.connection, this, cal);
        }
        else {
            final TimeZone tz = (cal != null) ? cal.getTimeZone() : this.getDefaultTimeZone();
            final boolean rollForward = tz != null && !tz.equals(this.getDefaultTimeZone());
            dateToReturn = (Date)this.thisRow.getNativeDateTimeValue(columnIndexMinusOne, null, 91, mysqlType, tz, rollForward, this.connection, this);
        }
        if (dateToReturn == null) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        return dateToReturn;
    }
    
    Date getNativeDateViaParseConversion(final int columnIndex) throws SQLException {
        if (this.useUsageAdvisor) {
            this.issueConversionViaParsingWarning("getDate()", columnIndex, this.thisRow.getColumnValue(columnIndex - 1), this.fields[columnIndex - 1], new int[] { 10 });
        }
        final String stringVal = this.getNativeString(columnIndex);
        return this.getDateFromString(stringVal, columnIndex, null);
    }
    
    protected double getNativeDouble(int columnIndex) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        --columnIndex;
        if (this.thisRow.isNull(columnIndex)) {
            this.wasNullFlag = true;
            return 0.0;
        }
        this.wasNullFlag = false;
        final Field f = this.fields[columnIndex];
        switch (f.getMysqlType()) {
            case 5: {
                return this.thisRow.getNativeDouble(columnIndex);
            }
            case 1: {
                if (!f.isUnsigned()) {
                    return this.getNativeByte(columnIndex + 1);
                }
                return this.getNativeShort(columnIndex + 1);
            }
            case 2:
            case 13: {
                if (!f.isUnsigned()) {
                    return this.getNativeShort(columnIndex + 1);
                }
                return this.getNativeInt(columnIndex + 1);
            }
            case 3:
            case 9: {
                if (!f.isUnsigned()) {
                    return this.getNativeInt(columnIndex + 1);
                }
                return this.getNativeLong(columnIndex + 1);
            }
            case 8: {
                final long valueAsLong = this.getNativeLong(columnIndex + 1);
                if (!f.isUnsigned()) {
                    return valueAsLong;
                }
                final BigInteger asBigInt = convertLongToUlong(valueAsLong);
                return asBigInt.doubleValue();
            }
            case 4: {
                return this.getNativeFloat(columnIndex + 1);
            }
            case 16: {
                return this.getNumericRepresentationOfSQLBitType(columnIndex + 1);
            }
            default: {
                final String stringVal = this.getNativeString(columnIndex + 1);
                if (this.useUsageAdvisor) {
                    this.issueConversionViaParsingWarning("getDouble()", columnIndex, stringVal, this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
                }
                return this.getDoubleFromString(stringVal, columnIndex + 1);
            }
        }
    }
    
    protected float getNativeFloat(int columnIndex) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        --columnIndex;
        if (this.thisRow.isNull(columnIndex)) {
            this.wasNullFlag = true;
            return 0.0f;
        }
        this.wasNullFlag = false;
        final Field f = this.fields[columnIndex];
        switch (f.getMysqlType()) {
            case 16: {
                final long valueAsLong = this.getNumericRepresentationOfSQLBitType(columnIndex + 1);
                return valueAsLong;
            }
            case 5: {
                final Double valueAsDouble = new Double(this.getNativeDouble(columnIndex + 1));
                final float valueAsFloat = (float)(Object)valueAsDouble;
                if ((this.jdbcCompliantTruncationForReads && valueAsFloat == Float.NEGATIVE_INFINITY) || valueAsFloat == Float.POSITIVE_INFINITY) {
                    this.throwRangeException(valueAsDouble.toString(), columnIndex + 1, 6);
                }
                return (float)this.getNativeDouble(columnIndex + 1);
            }
            case 1: {
                if (!f.isUnsigned()) {
                    return this.getNativeByte(columnIndex + 1);
                }
                return this.getNativeShort(columnIndex + 1);
            }
            case 2:
            case 13: {
                if (!f.isUnsigned()) {
                    return this.getNativeShort(columnIndex + 1);
                }
                return this.getNativeInt(columnIndex + 1);
            }
            case 3:
            case 9: {
                if (!f.isUnsigned()) {
                    return this.getNativeInt(columnIndex + 1);
                }
                return this.getNativeLong(columnIndex + 1);
            }
            case 8: {
                final long valueAsLong = this.getNativeLong(columnIndex + 1);
                if (!f.isUnsigned()) {
                    return valueAsLong;
                }
                final BigInteger asBigInt = convertLongToUlong(valueAsLong);
                return asBigInt.floatValue();
            }
            case 4: {
                return this.thisRow.getNativeFloat(columnIndex);
            }
            default: {
                final String stringVal = this.getNativeString(columnIndex + 1);
                if (this.useUsageAdvisor) {
                    this.issueConversionViaParsingWarning("getFloat()", columnIndex, stringVal, this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
                }
                return this.getFloatFromString(stringVal, columnIndex + 1);
            }
        }
    }
    
    protected int getNativeInt(final int columnIndex) throws SQLException {
        return this.getNativeInt(columnIndex, true);
    }
    
    protected int getNativeInt(int columnIndex, final boolean overflowCheck) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        --columnIndex;
        if (this.thisRow.isNull(columnIndex)) {
            this.wasNullFlag = true;
            return 0;
        }
        this.wasNullFlag = false;
        final Field f = this.fields[columnIndex];
        switch (f.getMysqlType()) {
            case 16: {
                final long valueAsLong = this.getNumericRepresentationOfSQLBitType(columnIndex + 1);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && (valueAsLong < -2147483648L || valueAsLong > 2147483647L)) {
                    this.throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 4);
                }
                return (short)valueAsLong;
            }
            case 1: {
                final byte tinyintVal = this.getNativeByte(columnIndex + 1, false);
                if (!f.isUnsigned() || tinyintVal >= 0) {
                    return tinyintVal;
                }
                return tinyintVal + 256;
            }
            case 2:
            case 13: {
                final short asShort = this.getNativeShort(columnIndex + 1, false);
                if (!f.isUnsigned() || asShort >= 0) {
                    return asShort;
                }
                return asShort + 65536;
            }
            case 3:
            case 9: {
                final int valueAsInt = this.thisRow.getNativeInt(columnIndex);
                if (!f.isUnsigned()) {
                    return valueAsInt;
                }
                final long valueAsLong = (valueAsInt >= 0) ? valueAsInt : (valueAsInt + 4294967296L);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && valueAsLong > 2147483647L) {
                    this.throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 4);
                }
                return (int)valueAsLong;
            }
            case 8: {
                final long valueAsLong = this.getNativeLong(columnIndex + 1, false, true);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && (valueAsLong < -2147483648L || valueAsLong > 2147483647L)) {
                    this.throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 4);
                }
                return (int)valueAsLong;
            }
            case 5: {
                final double valueAsDouble = this.getNativeDouble(columnIndex + 1);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && (valueAsDouble < -2.147483648E9 || valueAsDouble > 2.147483647E9)) {
                    this.throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, 4);
                }
                return (int)valueAsDouble;
            }
            case 4: {
                final double valueAsDouble = this.getNativeFloat(columnIndex + 1);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && (valueAsDouble < -2.147483648E9 || valueAsDouble > 2.147483647E9)) {
                    this.throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, 4);
                }
                return (int)valueAsDouble;
            }
            default: {
                final String stringVal = this.getNativeString(columnIndex + 1);
                if (this.useUsageAdvisor) {
                    this.issueConversionViaParsingWarning("getInt()", columnIndex, stringVal, this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
                }
                return this.getIntFromString(stringVal, columnIndex + 1);
            }
        }
    }
    
    protected long getNativeLong(final int columnIndex) throws SQLException {
        return this.getNativeLong(columnIndex, true, true);
    }
    
    protected long getNativeLong(int columnIndex, final boolean overflowCheck, final boolean expandUnsignedLong) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        --columnIndex;
        if (this.thisRow.isNull(columnIndex)) {
            this.wasNullFlag = true;
            return 0L;
        }
        this.wasNullFlag = false;
        final Field f = this.fields[columnIndex];
        switch (f.getMysqlType()) {
            case 16: {
                return this.getNumericRepresentationOfSQLBitType(columnIndex + 1);
            }
            case 1: {
                if (!f.isUnsigned()) {
                    return this.getNativeByte(columnIndex + 1);
                }
                return this.getNativeInt(columnIndex + 1);
            }
            case 2: {
                if (!f.isUnsigned()) {
                    return this.getNativeShort(columnIndex + 1);
                }
                return this.getNativeInt(columnIndex + 1, false);
            }
            case 13: {
                return this.getNativeShort(columnIndex + 1);
            }
            case 3:
            case 9: {
                final int asInt = this.getNativeInt(columnIndex + 1, false);
                if (!f.isUnsigned() || asInt >= 0) {
                    return asInt;
                }
                return asInt + 4294967296L;
            }
            case 8: {
                final long valueAsLong = this.thisRow.getNativeLong(columnIndex);
                if (!f.isUnsigned() || !expandUnsignedLong) {
                    return valueAsLong;
                }
                final BigInteger asBigInt = convertLongToUlong(valueAsLong);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && (asBigInt.compareTo(new BigInteger(String.valueOf(Long.MAX_VALUE))) > 0 || asBigInt.compareTo(new BigInteger(String.valueOf(Long.MIN_VALUE))) < 0)) {
                    this.throwRangeException(asBigInt.toString(), columnIndex + 1, -5);
                }
                return this.getLongFromString(asBigInt.toString(), columnIndex);
            }
            case 5: {
                final double valueAsDouble = this.getNativeDouble(columnIndex + 1);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && (valueAsDouble < -9.223372036854776E18 || valueAsDouble > 9.223372036854776E18)) {
                    this.throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, -5);
                }
                return (long)valueAsDouble;
            }
            case 4: {
                final double valueAsDouble = this.getNativeFloat(columnIndex + 1);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && (valueAsDouble < -9.223372036854776E18 || valueAsDouble > 9.223372036854776E18)) {
                    this.throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, -5);
                }
                return (long)valueAsDouble;
            }
            default: {
                final String stringVal = this.getNativeString(columnIndex + 1);
                if (this.useUsageAdvisor) {
                    this.issueConversionViaParsingWarning("getLong()", columnIndex, stringVal, this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
                }
                return this.getLongFromString(stringVal, columnIndex + 1);
            }
        }
    }
    
    protected Ref getNativeRef(final int i) throws SQLException {
        throw SQLError.notImplemented();
    }
    
    protected short getNativeShort(final int columnIndex) throws SQLException {
        return this.getNativeShort(columnIndex, true);
    }
    
    protected short getNativeShort(int columnIndex, final boolean overflowCheck) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        --columnIndex;
        if (this.thisRow.isNull(columnIndex)) {
            this.wasNullFlag = true;
            return 0;
        }
        this.wasNullFlag = false;
        final Field f = this.fields[columnIndex];
        switch (f.getMysqlType()) {
            case 1: {
                final byte tinyintVal = this.getNativeByte(columnIndex + 1, false);
                if (!f.isUnsigned() || tinyintVal >= 0) {
                    return tinyintVal;
                }
                return (short)(tinyintVal + 256);
            }
            case 2:
            case 13: {
                final short asShort = this.thisRow.getNativeShort(columnIndex);
                if (!f.isUnsigned()) {
                    return asShort;
                }
                final int valueAsInt = asShort & 0xFFFF;
                if (overflowCheck && this.jdbcCompliantTruncationForReads && valueAsInt > 32767) {
                    this.throwRangeException(String.valueOf(valueAsInt), columnIndex + 1, 5);
                }
                return (short)valueAsInt;
            }
            case 3:
            case 9: {
                if (!f.isUnsigned()) {
                    final int valueAsInt = this.getNativeInt(columnIndex + 1, false);
                    if ((overflowCheck && this.jdbcCompliantTruncationForReads && valueAsInt > 32767) || valueAsInt < -32768) {
                        this.throwRangeException(String.valueOf(valueAsInt), columnIndex + 1, 5);
                    }
                    return (short)valueAsInt;
                }
                final long valueAsLong = this.getNativeLong(columnIndex + 1, false, true);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && valueAsLong > 32767L) {
                    this.throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 5);
                }
                return (short)valueAsLong;
            }
            case 8: {
                final long valueAsLong = this.getNativeLong(columnIndex + 1, false, false);
                if (!f.isUnsigned()) {
                    if (overflowCheck && this.jdbcCompliantTruncationForReads && (valueAsLong < -32768L || valueAsLong > 32767L)) {
                        this.throwRangeException(String.valueOf(valueAsLong), columnIndex + 1, 5);
                    }
                    return (short)valueAsLong;
                }
                final BigInteger asBigInt = convertLongToUlong(valueAsLong);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && (asBigInt.compareTo(new BigInteger(String.valueOf(32767))) > 0 || asBigInt.compareTo(new BigInteger(String.valueOf(-32768))) < 0)) {
                    this.throwRangeException(asBigInt.toString(), columnIndex + 1, 5);
                }
                return (short)this.getIntFromString(asBigInt.toString(), columnIndex + 1);
            }
            case 5: {
                final double valueAsDouble = this.getNativeDouble(columnIndex + 1);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && (valueAsDouble < -32768.0 || valueAsDouble > 32767.0)) {
                    this.throwRangeException(String.valueOf(valueAsDouble), columnIndex + 1, 5);
                }
                return (short)valueAsDouble;
            }
            case 4: {
                final float valueAsFloat = this.getNativeFloat(columnIndex + 1);
                if (overflowCheck && this.jdbcCompliantTruncationForReads && (valueAsFloat < -32768.0f || valueAsFloat > 32767.0f)) {
                    this.throwRangeException(String.valueOf(valueAsFloat), columnIndex + 1, 5);
                }
                return (short)valueAsFloat;
            }
            default: {
                final String stringVal = this.getNativeString(columnIndex + 1);
                if (this.useUsageAdvisor) {
                    this.issueConversionViaParsingWarning("getShort()", columnIndex, stringVal, this.fields[columnIndex], new int[] { 5, 1, 2, 3, 8, 4 });
                }
                return this.getShortFromString(stringVal, columnIndex + 1);
            }
        }
    }
    
    protected String getNativeString(final int columnIndex) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        if (this.fields == null) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Query_generated_no_fields_for_ResultSet_133"), "S1002", this.getExceptionInterceptor());
        }
        if (this.thisRow.isNull(columnIndex - 1)) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        String stringVal = null;
        final Field field = this.fields[columnIndex - 1];
        stringVal = this.getNativeConvertToString(columnIndex, field);
        final int mysqlType = field.getMysqlType();
        if (mysqlType != 7 && mysqlType != 10 && field.isZeroFill() && stringVal != null) {
            final int origLength = stringVal.length();
            final StringBuffer zeroFillBuf = new StringBuffer(origLength);
            for (long numZeros = field.getLength() - origLength, i = 0L; i < numZeros; ++i) {
                zeroFillBuf.append('0');
            }
            zeroFillBuf.append(stringVal);
            stringVal = zeroFillBuf.toString();
        }
        return stringVal;
    }
    
    private Time getNativeTime(final int columnIndex, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        final int columnIndexMinusOne = columnIndex - 1;
        final int mysqlType = this.fields[columnIndexMinusOne].getMysqlType();
        Time timeVal = null;
        if (mysqlType == 11) {
            timeVal = this.thisRow.getNativeTime(columnIndexMinusOne, targetCalendar, tz, rollForward, this.connection, this);
        }
        else {
            timeVal = (Time)this.thisRow.getNativeDateTimeValue(columnIndexMinusOne, null, 92, mysqlType, tz, rollForward, this.connection, this);
        }
        if (timeVal == null) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        return timeVal;
    }
    
    Time getNativeTimeViaParseConversion(final int columnIndex, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward) throws SQLException {
        if (this.useUsageAdvisor) {
            this.issueConversionViaParsingWarning("getTime()", columnIndex, this.thisRow.getColumnValue(columnIndex - 1), this.fields[columnIndex - 1], new int[] { 11 });
        }
        final String strTime = this.getNativeString(columnIndex);
        return this.getTimeFromString(strTime, targetCalendar, columnIndex, tz, rollForward);
    }
    
    private Timestamp getNativeTimestamp(final int columnIndex, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        final int columnIndexMinusOne = columnIndex - 1;
        Timestamp tsVal = null;
        final int mysqlType = this.fields[columnIndexMinusOne].getMysqlType();
        switch (mysqlType) {
            case 7:
            case 12: {
                tsVal = this.thisRow.getNativeTimestamp(columnIndexMinusOne, targetCalendar, tz, rollForward, this.connection, this);
                break;
            }
            default: {
                tsVal = (Timestamp)this.thisRow.getNativeDateTimeValue(columnIndexMinusOne, null, 93, mysqlType, tz, rollForward, this.connection, this);
                break;
            }
        }
        if (tsVal == null) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        return tsVal;
    }
    
    Timestamp getNativeTimestampViaParseConversion(final int columnIndex, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward) throws SQLException {
        if (this.useUsageAdvisor) {
            this.issueConversionViaParsingWarning("getTimestamp()", columnIndex, this.thisRow.getColumnValue(columnIndex - 1), this.fields[columnIndex - 1], new int[] { 7, 12 });
        }
        final String strTimestamp = this.getNativeString(columnIndex);
        return this.getTimestampFromString(columnIndex, targetCalendar, strTimestamp, tz, rollForward);
    }
    
    protected InputStream getNativeUnicodeStream(final int columnIndex) throws SQLException {
        this.checkRowPos();
        return this.getBinaryStream(columnIndex);
    }
    
    protected URL getNativeURL(final int colIndex) throws SQLException {
        final String val = this.getString(colIndex);
        if (val == null) {
            return null;
        }
        try {
            return new URL(val);
        }
        catch (MalformedURLException mfe) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Malformed_URL____141") + val + "'", "S1009", this.getExceptionInterceptor());
        }
    }
    
    public ResultSetInternalMethods getNextResultSet() {
        return this.nextResultSet;
    }
    
    public Object getObject(final int columnIndex) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        final int columnIndexMinusOne = columnIndex - 1;
        if (this.thisRow.isNull(columnIndexMinusOne)) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        final Field field = this.fields[columnIndexMinusOne];
        switch (field.getSQLType()) {
            case -7:
            case 16: {
                if (field.getMysqlType() == 16 && !field.isSingleBit()) {
                    return this.getBytes(columnIndex);
                }
                return this.getBoolean(columnIndex);
            }
            case -6: {
                if (!field.isUnsigned()) {
                    return Constants.integerValueOf(this.getByte(columnIndex));
                }
                return Constants.integerValueOf(this.getInt(columnIndex));
            }
            case 5: {
                return Constants.integerValueOf(this.getInt(columnIndex));
            }
            case 4: {
                if (!field.isUnsigned() || field.getMysqlType() == 9) {
                    return Constants.integerValueOf(this.getInt(columnIndex));
                }
                return Constants.longValueOf(this.getLong(columnIndex));
            }
            case -5: {
                if (!field.isUnsigned()) {
                    return Constants.longValueOf(this.getLong(columnIndex));
                }
                final String stringVal = this.getString(columnIndex);
                if (stringVal == null) {
                    return null;
                }
                try {
                    return new BigInteger(stringVal);
                }
                catch (NumberFormatException nfe) {
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigInteger", new Object[] { Constants.integerValueOf(columnIndex), stringVal }), "S1009", this.getExceptionInterceptor());
                }
            }
            case 2:
            case 3: {
                final String stringVal = this.getString(columnIndex);
                if (stringVal == null) {
                    return null;
                }
                if (stringVal.length() == 0) {
                    final BigDecimal val = new BigDecimal(0);
                    return val;
                }
                BigDecimal val;
                try {
                    val = new BigDecimal(stringVal);
                }
                catch (NumberFormatException ex) {
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, new Integer(columnIndex) }), "S1009", this.getExceptionInterceptor());
                }
                return val;
            }
            case 7: {
                return new Float(this.getFloat(columnIndex));
            }
            case 6:
            case 8: {
                return new Double(this.getDouble(columnIndex));
            }
            case 1:
            case 12: {
                if (!field.isOpaqueBinary()) {
                    return this.getString(columnIndex);
                }
                return this.getBytes(columnIndex);
            }
            case -1: {
                if (!field.isOpaqueBinary()) {
                    return this.getStringForClob(columnIndex);
                }
                return this.getBytes(columnIndex);
            }
            case -4:
            case -3:
            case -2: {
                if (field.getMysqlType() == 255) {
                    return this.getBytes(columnIndex);
                }
                if (!field.isBinary() && !field.isBlob()) {
                    return this.getBytes(columnIndex);
                }
                final byte[] data = this.getBytes(columnIndex);
                if (this.connection.getAutoDeserialize()) {
                    Object obj = data;
                    if (data != null && data.length >= 2) {
                        if (data[0] == -84 && data[1] == -19) {
                            try {
                                final ByteArrayInputStream bytesIn = new ByteArrayInputStream(data);
                                final ObjectInputStream objIn = new ObjectInputStream(bytesIn);
                                obj = objIn.readObject();
                                objIn.close();
                                bytesIn.close();
                                return obj;
                            }
                            catch (ClassNotFoundException cnfe) {
                                throw SQLError.createSQLException(Messages.getString("ResultSet.Class_not_found___91") + cnfe.toString() + Messages.getString("ResultSet._while_reading_serialized_object_92"), this.getExceptionInterceptor());
                            }
                            catch (IOException ex2) {
                                obj = data;
                                return obj;
                            }
                        }
                        return this.getString(columnIndex);
                    }
                    return obj;
                }
                return data;
            }
            case 91: {
                if (field.getMysqlType() == 13 && !this.connection.getYearIsDateType()) {
                    return Constants.shortValueOf(this.getShort(columnIndex));
                }
                return this.getDate(columnIndex);
            }
            case 92: {
                return this.getTime(columnIndex);
            }
            case 93: {
                return this.getTimestamp(columnIndex);
            }
            default: {
                return this.getString(columnIndex);
            }
        }
    }
    
    public Object getObject(final int i, final Map map) throws SQLException {
        return this.getObject(i);
    }
    
    public Object getObject(final String columnName) throws SQLException {
        return this.getObject(this.findColumn(columnName));
    }
    
    public Object getObject(final String colName, final Map map) throws SQLException {
        return this.getObject(this.findColumn(colName), map);
    }
    
    public Object getObjectStoredProc(final int columnIndex, final int desiredSqlType) throws SQLException {
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        final Object value = this.thisRow.getColumnValue(columnIndex - 1);
        if (value == null) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        final Field field = this.fields[columnIndex - 1];
        switch (desiredSqlType) {
            case -7:
            case 16: {
                return this.getBoolean(columnIndex);
            }
            case -6: {
                return Constants.integerValueOf(this.getInt(columnIndex));
            }
            case 5: {
                return Constants.integerValueOf(this.getInt(columnIndex));
            }
            case 4: {
                if (!field.isUnsigned() || field.getMysqlType() == 9) {
                    return Constants.integerValueOf(this.getInt(columnIndex));
                }
                return Constants.longValueOf(this.getLong(columnIndex));
            }
            case -5: {
                if (field.isUnsigned()) {
                    return this.getBigDecimal(columnIndex);
                }
                return Constants.longValueOf(this.getLong(columnIndex));
            }
            case 2:
            case 3: {
                final String stringVal = this.getString(columnIndex);
                if (stringVal == null) {
                    return null;
                }
                if (stringVal.length() == 0) {
                    final BigDecimal val = new BigDecimal(0);
                    return val;
                }
                BigDecimal val;
                try {
                    val = new BigDecimal(stringVal);
                }
                catch (NumberFormatException ex) {
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_BigDecimal", new Object[] { stringVal, new Integer(columnIndex) }), "S1009", this.getExceptionInterceptor());
                }
                return val;
            }
            case 7: {
                return new Float(this.getFloat(columnIndex));
            }
            case 6: {
                if (!this.connection.getRunningCTS13()) {
                    return new Double(this.getFloat(columnIndex));
                }
                return new Float(this.getFloat(columnIndex));
            }
            case 8: {
                return new Double(this.getDouble(columnIndex));
            }
            case 1:
            case 12: {
                return this.getString(columnIndex);
            }
            case -1: {
                return this.getStringForClob(columnIndex);
            }
            case -4:
            case -3:
            case -2: {
                return this.getBytes(columnIndex);
            }
            case 91: {
                if (field.getMysqlType() == 13 && !this.connection.getYearIsDateType()) {
                    return Constants.shortValueOf(this.getShort(columnIndex));
                }
                return this.getDate(columnIndex);
            }
            case 92: {
                return this.getTime(columnIndex);
            }
            case 93: {
                return this.getTimestamp(columnIndex);
            }
            default: {
                return this.getString(columnIndex);
            }
        }
    }
    
    public Object getObjectStoredProc(final int i, final Map map, final int desiredSqlType) throws SQLException {
        return this.getObjectStoredProc(i, desiredSqlType);
    }
    
    public Object getObjectStoredProc(final String columnName, final int desiredSqlType) throws SQLException {
        return this.getObjectStoredProc(this.findColumn(columnName), desiredSqlType);
    }
    
    public Object getObjectStoredProc(final String colName, final Map map, final int desiredSqlType) throws SQLException {
        return this.getObjectStoredProc(this.findColumn(colName), map, desiredSqlType);
    }
    
    public Ref getRef(final int i) throws SQLException {
        this.checkColumnBounds(i);
        throw SQLError.notImplemented();
    }
    
    public Ref getRef(final String colName) throws SQLException {
        return this.getRef(this.findColumn(colName));
    }
    
    public int getRow() throws SQLException {
        this.checkClosed();
        final int currentRowNumber = this.rowData.getCurrentRowNumber();
        int row = 0;
        if (!this.rowData.isDynamic()) {
            if (currentRowNumber < 0 || this.rowData.isAfterLast() || this.rowData.isEmpty()) {
                row = 0;
            }
            else {
                row = currentRowNumber + 1;
            }
        }
        else {
            row = currentRowNumber + 1;
        }
        return row;
    }
    
    public String getServerInfo() {
        return this.serverInfo;
    }
    
    private long getNumericRepresentationOfSQLBitType(final int columnIndex) throws SQLException {
        final Object value = this.thisRow.getColumnValue(columnIndex - 1);
        if (this.fields[columnIndex - 1].isSingleBit() || ((byte[])value).length == 1) {
            return ((byte[])value)[0];
        }
        final byte[] asBytes = (byte[])value;
        int shift = 0;
        final long[] steps = new long[asBytes.length];
        for (int i = asBytes.length - 1; i >= 0; --i) {
            steps[i] = (asBytes[i] & 0xFF) << shift;
            shift += 8;
        }
        long valueAsLong = 0L;
        for (int j = 0; j < asBytes.length; ++j) {
            valueAsLong |= steps[j];
        }
        return valueAsLong;
    }
    
    public short getShort(final int columnIndex) throws SQLException {
        if (!this.isBinaryEncoded) {
            this.checkRowPos();
            if (this.useFastIntParsing) {
                this.checkColumnBounds(columnIndex);
                final Object value = this.thisRow.getColumnValue(columnIndex - 1);
                if (value == null) {
                    this.wasNullFlag = true;
                }
                else {
                    this.wasNullFlag = false;
                }
                if (this.wasNullFlag) {
                    return 0;
                }
                final byte[] shortAsBytes = (byte[])value;
                if (shortAsBytes.length == 0) {
                    return (short)this.convertToZeroWithEmptyCheck();
                }
                boolean needsFullParse = false;
                for (int i = 0; i < shortAsBytes.length; ++i) {
                    if ((char)shortAsBytes[i] == 'e' || (char)shortAsBytes[i] == 'E') {
                        needsFullParse = true;
                        break;
                    }
                }
                if (!needsFullParse) {
                    try {
                        return this.parseShortWithOverflowCheck(columnIndex, shortAsBytes, null);
                    }
                    catch (NumberFormatException nfe) {
                        try {
                            return this.parseShortAsDouble(columnIndex, new String(shortAsBytes));
                        }
                        catch (NumberFormatException newNfe) {
                            if (this.fields[columnIndex - 1].getMysqlType() == 16) {
                                final long valueAsLong = this.getNumericRepresentationOfSQLBitType(columnIndex);
                                if (this.jdbcCompliantTruncationForReads && (valueAsLong < -32768L || valueAsLong > 32767L)) {
                                    this.throwRangeException(String.valueOf(valueAsLong), columnIndex, 5);
                                }
                                return (short)valueAsLong;
                            }
                            throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getShort()_-____96") + new String(shortAsBytes) + "'", "S1009", this.getExceptionInterceptor());
                        }
                    }
                }
            }
            String val = null;
            try {
                val = this.getString(columnIndex);
                if (val == null) {
                    return 0;
                }
                if (val.length() == 0) {
                    return (short)this.convertToZeroWithEmptyCheck();
                }
                if (val.indexOf("e") == -1 && val.indexOf("E") == -1 && val.indexOf(".") == -1) {
                    return this.parseShortWithOverflowCheck(columnIndex, null, val);
                }
                return this.parseShortAsDouble(columnIndex, val);
            }
            catch (NumberFormatException nfe2) {
                try {
                    return this.parseShortAsDouble(columnIndex, val);
                }
                catch (NumberFormatException newNfe2) {
                    if (this.fields[columnIndex - 1].getMysqlType() == 16) {
                        final long valueAsLong2 = this.getNumericRepresentationOfSQLBitType(columnIndex);
                        if (this.jdbcCompliantTruncationForReads && (valueAsLong2 < -32768L || valueAsLong2 > 32767L)) {
                            this.throwRangeException(String.valueOf(valueAsLong2), columnIndex, 5);
                        }
                        return (short)valueAsLong2;
                    }
                    throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getShort()_-____96") + val + "'", "S1009", this.getExceptionInterceptor());
                }
            }
        }
        return this.getNativeShort(columnIndex);
    }
    
    public short getShort(final String columnName) throws SQLException {
        return this.getShort(this.findColumn(columnName));
    }
    
    private final short getShortFromString(final String val, final int columnIndex) throws SQLException {
        try {
            if (val == null) {
                return 0;
            }
            if (val.length() == 0) {
                return (short)this.convertToZeroWithEmptyCheck();
            }
            if (val.indexOf("e") == -1 && val.indexOf("E") == -1 && val.indexOf(".") == -1) {
                return this.parseShortWithOverflowCheck(columnIndex, null, val);
            }
            return this.parseShortAsDouble(columnIndex, val);
        }
        catch (NumberFormatException nfe) {
            try {
                return this.parseShortAsDouble(columnIndex, val);
            }
            catch (NumberFormatException newNfe) {
                throw SQLError.createSQLException(Messages.getString("ResultSet.Invalid_value_for_getShort()_-____217") + val + Messages.getString("ResultSet.___in_column__218") + columnIndex, "S1009", this.getExceptionInterceptor());
            }
        }
    }
    
    public Statement getStatement() throws SQLException {
        if (this.isClosed && !this.retainOwningStatement) {
            throw SQLError.createSQLException("Operation not allowed on closed ResultSet. Statements can be retained over result set closure by setting the connection property \"retainStatementAfterResultSetClose\" to \"true\".", "S1000", this.getExceptionInterceptor());
        }
        if (this.wrapperStatement != null) {
            return this.wrapperStatement;
        }
        return this.owningStatement;
    }
    
    public String getString(final int columnIndex) throws SQLException {
        String stringVal = this.getStringInternal(columnIndex, true);
        if (this.padCharsWithSpace && stringVal != null) {
            final Field f = this.fields[columnIndex - 1];
            if (f.getMysqlType() == 254) {
                final int fieldLength = (int)f.getLength() / f.getMaxBytesPerCharacter();
                final int currentLength = stringVal.length();
                if (currentLength < fieldLength) {
                    final StringBuffer paddedBuf = new StringBuffer(fieldLength);
                    paddedBuf.append(stringVal);
                    final int difference = fieldLength - currentLength;
                    paddedBuf.append(ResultSetImpl.EMPTY_SPACE, 0, difference);
                    stringVal = paddedBuf.toString();
                }
            }
        }
        return stringVal;
    }
    
    public String getString(final String columnName) throws SQLException {
        return this.getString(this.findColumn(columnName));
    }
    
    private String getStringForClob(final int columnIndex) throws SQLException {
        String asString = null;
        final String forcedEncoding = this.connection.getClobCharacterEncoding();
        if (forcedEncoding == null) {
            if (!this.isBinaryEncoded) {
                asString = this.getString(columnIndex);
            }
            else {
                asString = this.getNativeString(columnIndex);
            }
        }
        else {
            try {
                byte[] asBytes = null;
                if (!this.isBinaryEncoded) {
                    asBytes = this.getBytes(columnIndex);
                }
                else {
                    asBytes = this.getNativeBytes(columnIndex, true);
                }
                if (asBytes != null) {
                    asString = new String(asBytes, forcedEncoding);
                }
            }
            catch (UnsupportedEncodingException uee) {
                throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", this.getExceptionInterceptor());
            }
        }
        return asString;
    }
    
    protected String getStringInternal(final int columnIndex, final boolean checkDateTypes) throws SQLException {
        if (this.isBinaryEncoded) {
            return this.getNativeString(columnIndex);
        }
        this.checkRowPos();
        this.checkColumnBounds(columnIndex);
        if (this.fields == null) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Query_generated_no_fields_for_ResultSet_99"), "S1002", this.getExceptionInterceptor());
        }
        final int internalColumnIndex = columnIndex - 1;
        if (this.thisRow.isNull(internalColumnIndex)) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        final Field metadata = this.fields[internalColumnIndex];
        String stringVal = null;
        if (metadata.getMysqlType() == 16) {
            if (!metadata.isSingleBit()) {
                return String.valueOf(this.getNumericRepresentationOfSQLBitType(columnIndex));
            }
            final byte[] value = this.thisRow.getColumnValue(internalColumnIndex);
            if (value.length == 0) {
                return String.valueOf(this.convertToZeroWithEmptyCheck());
            }
            return String.valueOf(value[0]);
        }
        else {
            final String encoding = metadata.getCharacterSet();
            stringVal = this.thisRow.getString(internalColumnIndex, encoding, this.connection);
            if (metadata.getMysqlType() != 13) {
                if (checkDateTypes && !this.connection.getNoDatetimeStringSync()) {
                    switch (metadata.getSQLType()) {
                        case 92: {
                            final Time tm = this.getTimeFromString(stringVal, null, columnIndex, this.getDefaultTimeZone(), false);
                            if (tm == null) {
                                this.wasNullFlag = true;
                                return null;
                            }
                            this.wasNullFlag = false;
                            return tm.toString();
                        }
                        case 91: {
                            final Date dt = this.getDateFromString(stringVal, columnIndex, null);
                            if (dt == null) {
                                this.wasNullFlag = true;
                                return null;
                            }
                            this.wasNullFlag = false;
                            return dt.toString();
                        }
                        case 93: {
                            final Timestamp ts = this.getTimestampFromString(columnIndex, null, stringVal, this.getDefaultTimeZone(), false);
                            if (ts == null) {
                                this.wasNullFlag = true;
                                return null;
                            }
                            this.wasNullFlag = false;
                            return ts.toString();
                        }
                    }
                }
                return stringVal;
            }
            if (!this.connection.getYearIsDateType()) {
                return stringVal;
            }
            final Date dt2 = this.getDateFromString(stringVal, columnIndex, null);
            if (dt2 == null) {
                this.wasNullFlag = true;
                return null;
            }
            this.wasNullFlag = false;
            return dt2.toString();
        }
    }
    
    public Time getTime(final int columnIndex) throws SQLException {
        return this.getTimeInternal(columnIndex, null, this.getDefaultTimeZone(), false);
    }
    
    public Time getTime(final int columnIndex, final Calendar cal) throws SQLException {
        return this.getTimeInternal(columnIndex, cal, cal.getTimeZone(), true);
    }
    
    public Time getTime(final String columnName) throws SQLException {
        return this.getTime(this.findColumn(columnName));
    }
    
    public Time getTime(final String columnName, final Calendar cal) throws SQLException {
        return this.getTime(this.findColumn(columnName), cal);
    }
    
    private Time getTimeFromString(String timeAsString, final Calendar targetCalendar, final int columnIndex, final TimeZone tz, final boolean rollForward) throws SQLException {
        int hr = 0;
        int min = 0;
        int sec = 0;
        try {
            if (timeAsString == null) {
                this.wasNullFlag = true;
                return null;
            }
            timeAsString = timeAsString.trim();
            if (timeAsString.equals("0") || timeAsString.equals("0000-00-00") || timeAsString.equals("0000-00-00 00:00:00") || timeAsString.equals("00000000000000")) {
                if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                    this.wasNullFlag = true;
                    return null;
                }
                if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                    throw SQLError.createSQLException("Value '" + timeAsString + "' can not be represented as java.sql.Time", "S1009", this.getExceptionInterceptor());
                }
                return this.fastTimeCreate(targetCalendar, 0, 0, 0);
            }
            else {
                this.wasNullFlag = false;
                final Field timeColField = this.fields[columnIndex - 1];
                if (timeColField.getMysqlType() == 7) {
                    final int length = timeAsString.length();
                    switch (length) {
                        case 19: {
                            hr = Integer.parseInt(timeAsString.substring(length - 8, length - 6));
                            min = Integer.parseInt(timeAsString.substring(length - 5, length - 3));
                            sec = Integer.parseInt(timeAsString.substring(length - 2, length));
                            break;
                        }
                        case 12:
                        case 14: {
                            hr = Integer.parseInt(timeAsString.substring(length - 6, length - 4));
                            min = Integer.parseInt(timeAsString.substring(length - 4, length - 2));
                            sec = Integer.parseInt(timeAsString.substring(length - 2, length));
                            break;
                        }
                        case 10: {
                            hr = Integer.parseInt(timeAsString.substring(6, 8));
                            min = Integer.parseInt(timeAsString.substring(8, 10));
                            sec = 0;
                            break;
                        }
                        default: {
                            throw SQLError.createSQLException(Messages.getString("ResultSet.Timestamp_too_small_to_convert_to_Time_value_in_column__257") + columnIndex + "(" + this.fields[columnIndex - 1] + ").", "S1009", this.getExceptionInterceptor());
                        }
                    }
                    final SQLWarning precisionLost = new SQLWarning(Messages.getString("ResultSet.Precision_lost_converting_TIMESTAMP_to_Time_with_getTime()_on_column__261") + columnIndex + "(" + this.fields[columnIndex - 1] + ").");
                    if (this.warningChain == null) {
                        this.warningChain = precisionLost;
                    }
                    else {
                        this.warningChain.setNextWarning(precisionLost);
                    }
                }
                else if (timeColField.getMysqlType() == 12) {
                    hr = Integer.parseInt(timeAsString.substring(11, 13));
                    min = Integer.parseInt(timeAsString.substring(14, 16));
                    sec = Integer.parseInt(timeAsString.substring(17, 19));
                    final SQLWarning precisionLost2 = new SQLWarning(Messages.getString("ResultSet.Precision_lost_converting_DATETIME_to_Time_with_getTime()_on_column__264") + columnIndex + "(" + this.fields[columnIndex - 1] + ").");
                    if (this.warningChain == null) {
                        this.warningChain = precisionLost2;
                    }
                    else {
                        this.warningChain.setNextWarning(precisionLost2);
                    }
                }
                else {
                    if (timeColField.getMysqlType() == 10) {
                        return this.fastTimeCreate(targetCalendar, 0, 0, 0);
                    }
                    if (timeAsString.length() != 5 && timeAsString.length() != 8) {
                        throw SQLError.createSQLException(Messages.getString("ResultSet.Bad_format_for_Time____267") + timeAsString + Messages.getString("ResultSet.___in_column__268") + columnIndex, "S1009", this.getExceptionInterceptor());
                    }
                    hr = Integer.parseInt(timeAsString.substring(0, 2));
                    min = Integer.parseInt(timeAsString.substring(3, 5));
                    sec = ((timeAsString.length() == 5) ? 0 : Integer.parseInt(timeAsString.substring(6)));
                }
                final Calendar sessionCalendar = this.getCalendarInstanceForSessionOrNew();
                synchronized (sessionCalendar) {
                    return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimeCreate(sessionCalendar, hr, min, sec), this.connection.getServerTimezoneTZ(), tz, rollForward);
                }
            }
        }
        catch (Exception ex) {
            final SQLException sqlEx = SQLError.createSQLException(ex.toString(), "S1009", this.getExceptionInterceptor());
            sqlEx.initCause(ex);
            throw sqlEx;
        }
    }
    
    private Time getTimeInternal(final int columnIndex, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward) throws SQLException {
        this.checkRowPos();
        if (this.isBinaryEncoded) {
            return this.getNativeTime(columnIndex, targetCalendar, tz, rollForward);
        }
        if (!this.useFastDateParsing) {
            final String timeAsString = this.getStringInternal(columnIndex, false);
            return this.getTimeFromString(timeAsString, targetCalendar, columnIndex, tz, rollForward);
        }
        this.checkColumnBounds(columnIndex);
        final int columnIndexMinusOne = columnIndex - 1;
        if (this.thisRow.isNull(columnIndexMinusOne)) {
            this.wasNullFlag = true;
            return null;
        }
        this.wasNullFlag = false;
        return this.thisRow.getTimeFast(columnIndexMinusOne, targetCalendar, tz, rollForward, this.connection, this);
    }
    
    public Timestamp getTimestamp(final int columnIndex) throws SQLException {
        return this.getTimestampInternal(columnIndex, null, this.getDefaultTimeZone(), false);
    }
    
    public Timestamp getTimestamp(final int columnIndex, final Calendar cal) throws SQLException {
        return this.getTimestampInternal(columnIndex, cal, cal.getTimeZone(), true);
    }
    
    public Timestamp getTimestamp(final String columnName) throws SQLException {
        return this.getTimestamp(this.findColumn(columnName));
    }
    
    public Timestamp getTimestamp(final String columnName, final Calendar cal) throws SQLException {
        return this.getTimestamp(this.findColumn(columnName), cal);
    }
    
    private Timestamp getTimestampFromString(final int columnIndex, final Calendar targetCalendar, String timestampValue, final TimeZone tz, final boolean rollForward) throws SQLException {
        try {
            this.wasNullFlag = false;
            if (timestampValue == null) {
                this.wasNullFlag = true;
                return null;
            }
            timestampValue = timestampValue.trim();
            final int length = timestampValue.length();
            final Calendar sessionCalendar = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : this.getCalendarInstanceForSessionOrNew();
            synchronized (sessionCalendar) {
                if (length > 0 && timestampValue.charAt(0) == '0' && (timestampValue.equals("0000-00-00") || timestampValue.equals("0000-00-00 00:00:00") || timestampValue.equals("00000000000000") || timestampValue.equals("0"))) {
                    if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                        this.wasNullFlag = true;
                        return null;
                    }
                    if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                        throw SQLError.createSQLException("Value '" + timestampValue + "' can not be represented as java.sql.Timestamp", "S1009", this.getExceptionInterceptor());
                    }
                    return this.fastTimestampCreate(null, 1, 1, 1, 0, 0, 0, 0);
                }
                else if (this.fields[columnIndex - 1].getMysqlType() == 13) {
                    if (!this.useLegacyDatetimeCode) {
                        return TimeUtil.fastTimestampCreate(tz, Integer.parseInt(timestampValue.substring(0, 4)), 1, 1, 0, 0, 0, 0);
                    }
                    return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, Integer.parseInt(timestampValue.substring(0, 4)), 1, 1, 0, 0, 0, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                }
                else {
                    if (timestampValue.endsWith(".")) {
                        timestampValue = timestampValue.substring(0, timestampValue.length() - 1);
                    }
                    int year = 0;
                    int month = 0;
                    int day = 0;
                    int hour = 0;
                    int minutes = 0;
                    int seconds = 0;
                    int nanos = 0;
                    switch (length) {
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26: {
                            year = Integer.parseInt(timestampValue.substring(0, 4));
                            month = Integer.parseInt(timestampValue.substring(5, 7));
                            day = Integer.parseInt(timestampValue.substring(8, 10));
                            hour = Integer.parseInt(timestampValue.substring(11, 13));
                            minutes = Integer.parseInt(timestampValue.substring(14, 16));
                            seconds = Integer.parseInt(timestampValue.substring(17, 19));
                            nanos = 0;
                            if (length > 19) {
                                final int decimalIndex = timestampValue.lastIndexOf(46);
                                if (decimalIndex != -1) {
                                    if (decimalIndex + 2 > length) {
                                        throw new IllegalArgumentException();
                                    }
                                    nanos = Integer.parseInt(timestampValue.substring(decimalIndex + 1));
                                    final int numDigits = length - (decimalIndex + 1);
                                    if (numDigits < 9) {
                                        final int factor = (int)Math.pow(10.0, 9 - numDigits);
                                        nanos *= factor;
                                    }
                                }
                                break;
                            }
                            break;
                        }
                        case 14: {
                            year = Integer.parseInt(timestampValue.substring(0, 4));
                            month = Integer.parseInt(timestampValue.substring(4, 6));
                            day = Integer.parseInt(timestampValue.substring(6, 8));
                            hour = Integer.parseInt(timestampValue.substring(8, 10));
                            minutes = Integer.parseInt(timestampValue.substring(10, 12));
                            seconds = Integer.parseInt(timestampValue.substring(12, 14));
                            break;
                        }
                        case 12: {
                            year = Integer.parseInt(timestampValue.substring(0, 2));
                            if (year <= 69) {
                                year += 100;
                            }
                            year += 1900;
                            month = Integer.parseInt(timestampValue.substring(2, 4));
                            day = Integer.parseInt(timestampValue.substring(4, 6));
                            hour = Integer.parseInt(timestampValue.substring(6, 8));
                            minutes = Integer.parseInt(timestampValue.substring(8, 10));
                            seconds = Integer.parseInt(timestampValue.substring(10, 12));
                            break;
                        }
                        case 10: {
                            if (this.fields[columnIndex - 1].getMysqlType() == 10 || timestampValue.indexOf("-") != -1) {
                                year = Integer.parseInt(timestampValue.substring(0, 4));
                                month = Integer.parseInt(timestampValue.substring(5, 7));
                                day = Integer.parseInt(timestampValue.substring(8, 10));
                                hour = 0;
                                minutes = 0;
                                break;
                            }
                            year = Integer.parseInt(timestampValue.substring(0, 2));
                            if (year <= 69) {
                                year += 100;
                            }
                            month = Integer.parseInt(timestampValue.substring(2, 4));
                            day = Integer.parseInt(timestampValue.substring(4, 6));
                            hour = Integer.parseInt(timestampValue.substring(6, 8));
                            minutes = Integer.parseInt(timestampValue.substring(8, 10));
                            year += 1900;
                            break;
                        }
                        case 8: {
                            if (timestampValue.indexOf(":") != -1) {
                                hour = Integer.parseInt(timestampValue.substring(0, 2));
                                minutes = Integer.parseInt(timestampValue.substring(3, 5));
                                seconds = Integer.parseInt(timestampValue.substring(6, 8));
                                year = 1970;
                                month = 1;
                                day = 1;
                                break;
                            }
                            year = Integer.parseInt(timestampValue.substring(0, 4));
                            month = Integer.parseInt(timestampValue.substring(4, 6));
                            day = Integer.parseInt(timestampValue.substring(6, 8));
                            year -= 1900;
                            --month;
                            break;
                        }
                        case 6: {
                            year = Integer.parseInt(timestampValue.substring(0, 2));
                            if (year <= 69) {
                                year += 100;
                            }
                            year += 1900;
                            month = Integer.parseInt(timestampValue.substring(2, 4));
                            day = Integer.parseInt(timestampValue.substring(4, 6));
                            break;
                        }
                        case 4: {
                            year = Integer.parseInt(timestampValue.substring(0, 2));
                            if (year <= 69) {
                                year += 100;
                            }
                            year += 1900;
                            month = Integer.parseInt(timestampValue.substring(2, 4));
                            day = 1;
                            break;
                        }
                        case 2: {
                            year = Integer.parseInt(timestampValue.substring(0, 2));
                            if (year <= 69) {
                                year += 100;
                            }
                            year += 1900;
                            month = 1;
                            day = 1;
                            break;
                        }
                        default: {
                            throw new SQLException("Bad format for Timestamp '" + timestampValue + "' in column " + columnIndex + ".", "S1009");
                        }
                    }
                    if (!this.useLegacyDatetimeCode) {
                        return TimeUtil.fastTimestampCreate(tz, year, month, day, hour, minutes, seconds, nanos);
                    }
                    return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, year, month, day, hour, minutes, seconds, nanos), this.connection.getServerTimezoneTZ(), tz, rollForward);
                }
            }
        }
        catch (Exception e) {
            final SQLException sqlEx = SQLError.createSQLException("Cannot convert value '" + timestampValue + "' from column " + columnIndex + " to TIMESTAMP.", "S1009", this.getExceptionInterceptor());
            sqlEx.initCause(e);
            throw sqlEx;
        }
    }
    
    private Timestamp getTimestampFromBytes(final int columnIndex, final Calendar targetCalendar, final byte[] timestampAsBytes, final TimeZone tz, final boolean rollForward) throws SQLException {
        this.checkColumnBounds(columnIndex);
        try {
            this.wasNullFlag = false;
            if (timestampAsBytes == null) {
                this.wasNullFlag = true;
                return null;
            }
            int length = timestampAsBytes.length;
            final Calendar sessionCalendar = this.connection.getUseJDBCCompliantTimezoneShift() ? this.connection.getUtcCalendar() : this.getCalendarInstanceForSessionOrNew();
            synchronized (sessionCalendar) {
                boolean allZeroTimestamp = true;
                boolean onlyTimePresent = StringUtils.indexOf(timestampAsBytes, ':') != -1;
                for (final byte b : timestampAsBytes) {
                    if (b == 32 || b == 45 || b == 47) {
                        onlyTimePresent = false;
                    }
                    if (b != 48 && b != 32 && b != 58 && b != 45 && b != 47 && b != 46) {
                        allZeroTimestamp = false;
                        break;
                    }
                }
                if (!onlyTimePresent && allZeroTimestamp) {
                    if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                        this.wasNullFlag = true;
                        return null;
                    }
                    if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                        throw SQLError.createSQLException("Value '" + timestampAsBytes + "' can not be represented as java.sql.Timestamp", "S1009", this.getExceptionInterceptor());
                    }
                    if (!this.useLegacyDatetimeCode) {
                        return TimeUtil.fastTimestampCreate(tz, 1, 1, 1, 0, 0, 0, 0);
                    }
                    return this.fastTimestampCreate(null, 1, 1, 1, 0, 0, 0, 0);
                }
                else if (this.fields[columnIndex - 1].getMysqlType() == 13) {
                    if (!this.useLegacyDatetimeCode) {
                        return TimeUtil.fastTimestampCreate(tz, StringUtils.getInt(timestampAsBytes, 0, 4), 1, 1, 0, 0, 0, 0);
                    }
                    return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, StringUtils.getInt(timestampAsBytes, 0, 4), 1, 1, 0, 0, 0, 0), this.connection.getServerTimezoneTZ(), tz, rollForward);
                }
                else {
                    if (timestampAsBytes[length - 1] == 46) {
                        --length;
                    }
                    int year = 0;
                    int month = 0;
                    int day = 0;
                    int hour = 0;
                    int minutes = 0;
                    int seconds = 0;
                    int nanos = 0;
                    switch (length) {
                        case 19:
                        case 20:
                        case 21:
                        case 22:
                        case 23:
                        case 24:
                        case 25:
                        case 26: {
                            year = StringUtils.getInt(timestampAsBytes, 0, 4);
                            month = StringUtils.getInt(timestampAsBytes, 5, 7);
                            day = StringUtils.getInt(timestampAsBytes, 8, 10);
                            hour = StringUtils.getInt(timestampAsBytes, 11, 13);
                            minutes = StringUtils.getInt(timestampAsBytes, 14, 16);
                            seconds = StringUtils.getInt(timestampAsBytes, 17, 19);
                            nanos = 0;
                            if (length > 19) {
                                final int decimalIndex = StringUtils.lastIndexOf(timestampAsBytes, '.');
                                if (decimalIndex != -1) {
                                    if (decimalIndex + 2 > length) {
                                        throw new IllegalArgumentException();
                                    }
                                    nanos = StringUtils.getInt(timestampAsBytes, decimalIndex + 1, length);
                                }
                                break;
                            }
                            break;
                        }
                        case 14: {
                            year = StringUtils.getInt(timestampAsBytes, 0, 4);
                            month = StringUtils.getInt(timestampAsBytes, 4, 6);
                            day = StringUtils.getInt(timestampAsBytes, 6, 8);
                            hour = StringUtils.getInt(timestampAsBytes, 8, 10);
                            minutes = StringUtils.getInt(timestampAsBytes, 10, 12);
                            seconds = StringUtils.getInt(timestampAsBytes, 12, 14);
                            break;
                        }
                        case 12: {
                            year = StringUtils.getInt(timestampAsBytes, 0, 2);
                            if (year <= 69) {
                                year += 100;
                            }
                            year += 1900;
                            month = StringUtils.getInt(timestampAsBytes, 2, 4);
                            day = StringUtils.getInt(timestampAsBytes, 4, 6);
                            hour = StringUtils.getInt(timestampAsBytes, 6, 8);
                            minutes = StringUtils.getInt(timestampAsBytes, 8, 10);
                            seconds = StringUtils.getInt(timestampAsBytes, 10, 12);
                            break;
                        }
                        case 10: {
                            if (this.fields[columnIndex - 1].getMysqlType() == 10 || StringUtils.indexOf(timestampAsBytes, '-') != -1) {
                                year = StringUtils.getInt(timestampAsBytes, 0, 4);
                                month = StringUtils.getInt(timestampAsBytes, 5, 7);
                                day = StringUtils.getInt(timestampAsBytes, 8, 10);
                                hour = 0;
                                minutes = 0;
                                break;
                            }
                            year = StringUtils.getInt(timestampAsBytes, 0, 2);
                            if (year <= 69) {
                                year += 100;
                            }
                            month = StringUtils.getInt(timestampAsBytes, 2, 4);
                            day = StringUtils.getInt(timestampAsBytes, 4, 6);
                            hour = StringUtils.getInt(timestampAsBytes, 6, 8);
                            minutes = StringUtils.getInt(timestampAsBytes, 8, 10);
                            year += 1900;
                            break;
                        }
                        case 8: {
                            if (StringUtils.indexOf(timestampAsBytes, ':') != -1) {
                                hour = StringUtils.getInt(timestampAsBytes, 0, 2);
                                minutes = StringUtils.getInt(timestampAsBytes, 3, 5);
                                seconds = StringUtils.getInt(timestampAsBytes, 6, 8);
                                year = 1970;
                                month = 1;
                                day = 1;
                                break;
                            }
                            year = StringUtils.getInt(timestampAsBytes, 0, 4);
                            month = StringUtils.getInt(timestampAsBytes, 4, 6);
                            day = StringUtils.getInt(timestampAsBytes, 6, 8);
                            year -= 1900;
                            --month;
                            break;
                        }
                        case 6: {
                            year = StringUtils.getInt(timestampAsBytes, 0, 2);
                            if (year <= 69) {
                                year += 100;
                            }
                            year += 1900;
                            month = StringUtils.getInt(timestampAsBytes, 2, 4);
                            day = StringUtils.getInt(timestampAsBytes, 4, 6);
                            break;
                        }
                        case 4: {
                            year = StringUtils.getInt(timestampAsBytes, 0, 2);
                            if (year <= 69) {
                                year += 100;
                            }
                            year += 1900;
                            month = StringUtils.getInt(timestampAsBytes, 2, 4);
                            day = 1;
                            break;
                        }
                        case 2: {
                            year = StringUtils.getInt(timestampAsBytes, 0, 2);
                            if (year <= 69) {
                                year += 100;
                            }
                            year += 1900;
                            month = 1;
                            day = 1;
                            break;
                        }
                        default: {
                            throw new SQLException("Bad format for Timestamp '" + new String(timestampAsBytes) + "' in column " + columnIndex + ".", "S1009");
                        }
                    }
                    if (!this.useLegacyDatetimeCode) {
                        return TimeUtil.fastTimestampCreate(tz, year, month, day, hour, minutes, seconds, nanos);
                    }
                    return TimeUtil.changeTimezone(this.connection, sessionCalendar, targetCalendar, this.fastTimestampCreate(sessionCalendar, year, month, day, hour, minutes, seconds, nanos), this.connection.getServerTimezoneTZ(), tz, rollForward);
                }
            }
        }
        catch (Exception e) {
            final SQLException sqlEx = SQLError.createSQLException("Cannot convert value '" + new String(timestampAsBytes) + "' from column " + columnIndex + " to TIMESTAMP.", "S1009", this.getExceptionInterceptor());
            sqlEx.initCause(e);
            throw sqlEx;
        }
    }
    
    private Timestamp getTimestampInternal(final int columnIndex, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward) throws SQLException {
        if (this.isBinaryEncoded) {
            return this.getNativeTimestamp(columnIndex, targetCalendar, tz, rollForward);
        }
        Timestamp tsVal = null;
        if (!this.useFastDateParsing) {
            final String timestampValue = this.getStringInternal(columnIndex, false);
            tsVal = this.getTimestampFromString(columnIndex, targetCalendar, timestampValue, tz, rollForward);
        }
        else {
            this.checkClosed();
            this.checkRowPos();
            this.checkColumnBounds(columnIndex);
            tsVal = this.thisRow.getTimestampFast(columnIndex - 1, targetCalendar, tz, rollForward, this.connection, this);
        }
        if (tsVal == null) {
            this.wasNullFlag = true;
        }
        else {
            this.wasNullFlag = false;
        }
        return tsVal;
    }
    
    public int getType() throws SQLException {
        return this.resultSetType;
    }
    
    public InputStream getUnicodeStream(final int columnIndex) throws SQLException {
        if (!this.isBinaryEncoded) {
            this.checkRowPos();
            return this.getBinaryStream(columnIndex);
        }
        return this.getNativeBinaryStream(columnIndex);
    }
    
    public InputStream getUnicodeStream(final String columnName) throws SQLException {
        return this.getUnicodeStream(this.findColumn(columnName));
    }
    
    public long getUpdateCount() {
        return this.updateCount;
    }
    
    public long getUpdateID() {
        return this.updateId;
    }
    
    public URL getURL(final int colIndex) throws SQLException {
        final String val = this.getString(colIndex);
        if (val == null) {
            return null;
        }
        try {
            return new URL(val);
        }
        catch (MalformedURLException mfe) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Malformed_URL____104") + val + "'", "S1009", this.getExceptionInterceptor());
        }
    }
    
    public URL getURL(final String colName) throws SQLException {
        final String val = this.getString(colName);
        if (val == null) {
            return null;
        }
        try {
            return new URL(val);
        }
        catch (MalformedURLException mfe) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Malformed_URL____107") + val + "'", "S1009", this.getExceptionInterceptor());
        }
    }
    
    public SQLWarning getWarnings() throws SQLException {
        return this.warningChain;
    }
    
    public void insertRow() throws SQLException {
        throw new NotUpdatable();
    }
    
    public boolean isAfterLast() throws SQLException {
        this.checkClosed();
        final boolean b = this.rowData.isAfterLast();
        return b;
    }
    
    public boolean isBeforeFirst() throws SQLException {
        this.checkClosed();
        return this.rowData.isBeforeFirst();
    }
    
    public boolean isFirst() throws SQLException {
        this.checkClosed();
        return this.rowData.isFirst();
    }
    
    public boolean isLast() throws SQLException {
        this.checkClosed();
        return this.rowData.isLast();
    }
    
    private void issueConversionViaParsingWarning(final String methodName, final int columnIndex, final Object value, final Field fieldInfo, final int[] typesWithNoParseConversion) throws SQLException {
        final StringBuffer originalQueryBuf = new StringBuffer();
        if (this.owningStatement != null && this.owningStatement instanceof PreparedStatement) {
            originalQueryBuf.append(Messages.getString("ResultSet.CostlyConversionCreatedFromQuery"));
            originalQueryBuf.append(((PreparedStatement)this.owningStatement).originalSql);
            originalQueryBuf.append("\n\n");
        }
        else {
            originalQueryBuf.append(".");
        }
        final StringBuffer convertibleTypesBuf = new StringBuffer();
        for (int i = 0; i < typesWithNoParseConversion.length; ++i) {
            convertibleTypesBuf.append(MysqlDefs.typeToName(typesWithNoParseConversion[i]));
            convertibleTypesBuf.append("\n");
        }
        final String message = Messages.getString("ResultSet.CostlyConversion", new Object[] { methodName, new Integer(columnIndex + 1), fieldInfo.getOriginalName(), fieldInfo.getOriginalTableName(), originalQueryBuf.toString(), (value != null) ? value.getClass().getName() : com.mysql.jdbc.ResultSetMetaData.getClassNameForJavaType(fieldInfo.getSQLType(), fieldInfo.isUnsigned(), fieldInfo.getMysqlType(), fieldInfo.isBinary() || fieldInfo.isBlob(), fieldInfo.isOpaqueBinary()), MysqlDefs.typeToName(fieldInfo.getMysqlType()), convertibleTypesBuf.toString() });
        this.eventSink.consumeEvent(new ProfilerEvent((byte)0, "", (this.owningStatement == null) ? "N/A" : this.owningStatement.currentCatalog, this.connectionId, (this.owningStatement == null) ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
    }
    
    public boolean last() throws SQLException {
        this.checkClosed();
        boolean b = true;
        if (this.rowData.size() == 0) {
            b = false;
        }
        else {
            if (this.onInsertRow) {
                this.onInsertRow = false;
            }
            if (this.doingUpdates) {
                this.doingUpdates = false;
            }
            if (this.thisRow != null) {
                this.thisRow.closeOpenStreams();
            }
            this.rowData.beforeLast();
            this.thisRow = this.rowData.next();
        }
        this.setRowPositionValidity();
        return b;
    }
    
    public void moveToCurrentRow() throws SQLException {
        throw new NotUpdatable();
    }
    
    public void moveToInsertRow() throws SQLException {
        throw new NotUpdatable();
    }
    
    public boolean next() throws SQLException {
        this.checkClosed();
        if (this.onInsertRow) {
            this.onInsertRow = false;
        }
        if (this.doingUpdates) {
            this.doingUpdates = false;
        }
        if (!this.reallyResult()) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.ResultSet_is_from_UPDATE._No_Data_115"), "S1000", this.getExceptionInterceptor());
        }
        if (this.thisRow != null) {
            this.thisRow.closeOpenStreams();
        }
        boolean b;
        if (this.rowData.size() == 0) {
            b = false;
        }
        else {
            this.thisRow = this.rowData.next();
            if (this.thisRow == null) {
                b = false;
            }
            else {
                this.clearWarnings();
                b = true;
            }
        }
        this.setRowPositionValidity();
        return b;
    }
    
    private int parseIntAsDouble(final int columnIndex, final String val) throws NumberFormatException, SQLException {
        if (val == null) {
            return 0;
        }
        final double valueAsDouble = Double.parseDouble(val);
        if (this.jdbcCompliantTruncationForReads && (valueAsDouble < -2.147483648E9 || valueAsDouble > 2.147483647E9)) {
            this.throwRangeException(String.valueOf(valueAsDouble), columnIndex, 4);
        }
        return (int)valueAsDouble;
    }
    
    private int getIntWithOverflowCheck(final int columnIndex) throws SQLException {
        final int intValue = this.thisRow.getInt(columnIndex);
        this.checkForIntegerTruncation(columnIndex, null, intValue);
        return intValue;
    }
    
    private void checkForIntegerTruncation(final int columnIndex, final byte[] valueAsBytes, final int intValue) throws SQLException {
        if (this.jdbcCompliantTruncationForReads && (intValue == Integer.MIN_VALUE || intValue == Integer.MAX_VALUE)) {
            String valueAsString = null;
            if (valueAsBytes == null) {
                valueAsString = this.thisRow.getString(columnIndex, this.fields[columnIndex].getCharacterSet(), this.connection);
            }
            final long valueAsLong = Long.parseLong((valueAsString == null) ? new String(valueAsBytes) : valueAsString);
            if (valueAsLong < -2147483648L || valueAsLong > 2147483647L) {
                this.throwRangeException((valueAsString == null) ? new String(valueAsBytes) : valueAsString, columnIndex + 1, 4);
            }
        }
    }
    
    private long parseLongAsDouble(final int columnIndexZeroBased, final String val) throws NumberFormatException, SQLException {
        if (val == null) {
            return 0L;
        }
        final double valueAsDouble = Double.parseDouble(val);
        if (this.jdbcCompliantTruncationForReads && (valueAsDouble < -9.223372036854776E18 || valueAsDouble > 9.223372036854776E18)) {
            this.throwRangeException(val, columnIndexZeroBased + 1, -5);
        }
        return (long)valueAsDouble;
    }
    
    private long getLongWithOverflowCheck(final int columnIndexZeroBased, final boolean doOverflowCheck) throws SQLException {
        final long longValue = this.thisRow.getLong(columnIndexZeroBased);
        if (doOverflowCheck) {
            this.checkForLongTruncation(columnIndexZeroBased, null, longValue);
        }
        return longValue;
    }
    
    private long parseLongWithOverflowCheck(final int columnIndexZeroBased, final byte[] valueAsBytes, String valueAsString, final boolean doCheck) throws NumberFormatException, SQLException {
        long longValue = 0L;
        if (valueAsBytes == null && valueAsString == null) {
            return 0L;
        }
        if (valueAsBytes != null) {
            longValue = StringUtils.getLong(valueAsBytes);
        }
        else {
            valueAsString = valueAsString.trim();
            longValue = Long.parseLong(valueAsString);
        }
        if (doCheck && this.jdbcCompliantTruncationForReads) {
            this.checkForLongTruncation(columnIndexZeroBased, valueAsBytes, longValue);
        }
        return longValue;
    }
    
    private void checkForLongTruncation(final int columnIndexZeroBased, final byte[] valueAsBytes, final long longValue) throws SQLException {
        if (longValue == Long.MIN_VALUE || longValue == Long.MAX_VALUE) {
            String valueAsString = null;
            if (valueAsBytes == null) {
                valueAsString = this.thisRow.getString(columnIndexZeroBased, this.fields[columnIndexZeroBased].getCharacterSet(), this.connection);
            }
            final double valueAsDouble = Double.parseDouble((valueAsString == null) ? new String(valueAsBytes) : valueAsString);
            if (valueAsDouble < -9.223372036854776E18 || valueAsDouble > 9.223372036854776E18) {
                this.throwRangeException((valueAsString == null) ? new String(valueAsBytes) : valueAsString, columnIndexZeroBased + 1, -5);
            }
        }
    }
    
    private short parseShortAsDouble(final int columnIndex, final String val) throws NumberFormatException, SQLException {
        if (val == null) {
            return 0;
        }
        final double valueAsDouble = Double.parseDouble(val);
        if (this.jdbcCompliantTruncationForReads && (valueAsDouble < -32768.0 || valueAsDouble > 32767.0)) {
            this.throwRangeException(String.valueOf(valueAsDouble), columnIndex, 5);
        }
        return (short)valueAsDouble;
    }
    
    private short parseShortWithOverflowCheck(final int columnIndex, final byte[] valueAsBytes, String valueAsString) throws NumberFormatException, SQLException {
        short shortValue = 0;
        if (valueAsBytes == null && valueAsString == null) {
            return 0;
        }
        if (valueAsBytes != null) {
            shortValue = StringUtils.getShort(valueAsBytes);
        }
        else {
            valueAsString = valueAsString.trim();
            shortValue = Short.parseShort(valueAsString);
        }
        if (this.jdbcCompliantTruncationForReads && (shortValue == -32768 || shortValue == 32767)) {
            final long valueAsLong = Long.parseLong((valueAsString == null) ? new String(valueAsBytes) : valueAsString);
            if (valueAsLong < -32768L || valueAsLong > 32767L) {
                this.throwRangeException((valueAsString == null) ? new String(valueAsBytes) : valueAsString, columnIndex, 5);
            }
        }
        return shortValue;
    }
    
    public boolean prev() throws SQLException {
        this.checkClosed();
        int rowIndex = this.rowData.getCurrentRowNumber();
        if (this.thisRow != null) {
            this.thisRow.closeOpenStreams();
        }
        boolean b = true;
        if (rowIndex - 1 >= 0) {
            --rowIndex;
            this.rowData.setCurrentRow(rowIndex);
            this.thisRow = this.rowData.getAt(rowIndex);
            b = true;
        }
        else if (rowIndex - 1 == -1) {
            --rowIndex;
            this.rowData.setCurrentRow(rowIndex);
            this.thisRow = null;
            b = false;
        }
        else {
            b = false;
        }
        this.setRowPositionValidity();
        return b;
    }
    
    public boolean previous() throws SQLException {
        if (this.onInsertRow) {
            this.onInsertRow = false;
        }
        if (this.doingUpdates) {
            this.doingUpdates = false;
        }
        return this.prev();
    }
    
    public void realClose(final boolean calledExplicitly) throws SQLException {
        if (this.isClosed) {
            return;
        }
        try {
            if (this.useUsageAdvisor) {
                if (!calledExplicitly) {
                    this.eventSink.consumeEvent(new ProfilerEvent((byte)0, "", (this.owningStatement == null) ? "N/A" : this.owningStatement.currentCatalog, this.connectionId, (this.owningStatement == null) ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, Messages.getString("ResultSet.ResultSet_implicitly_closed_by_driver")));
                }
                if (this.rowData instanceof RowDataStatic) {
                    if (this.rowData.size() > this.connection.getResultSetSizeThreshold()) {
                        this.eventSink.consumeEvent(new ProfilerEvent((byte)0, "", (this.owningStatement == null) ? Messages.getString("ResultSet.N/A_159") : this.owningStatement.currentCatalog, this.connectionId, (this.owningStatement == null) ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, Messages.getString("ResultSet.Too_Large_Result_Set", new Object[] { new Integer(this.rowData.size()), new Integer(this.connection.getResultSetSizeThreshold()) })));
                    }
                    if (!this.isLast() && !this.isAfterLast() && this.rowData.size() != 0) {
                        this.eventSink.consumeEvent(new ProfilerEvent((byte)0, "", (this.owningStatement == null) ? Messages.getString("ResultSet.N/A_159") : this.owningStatement.currentCatalog, this.connectionId, (this.owningStatement == null) ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, Messages.getString("ResultSet.Possible_incomplete_traversal_of_result_set", new Object[] { new Integer(this.getRow()), new Integer(this.rowData.size()) })));
                    }
                }
                if (this.columnUsed.length > 0 && !this.rowData.wasEmpty()) {
                    final StringBuffer buf = new StringBuffer(Messages.getString("ResultSet.The_following_columns_were_never_referenced"));
                    boolean issueWarn = false;
                    for (int i = 0; i < this.columnUsed.length; ++i) {
                        if (!this.columnUsed[i]) {
                            if (!issueWarn) {
                                issueWarn = true;
                            }
                            else {
                                buf.append(", ");
                            }
                            buf.append(this.fields[i].getFullName());
                        }
                    }
                    if (issueWarn) {
                        this.eventSink.consumeEvent(new ProfilerEvent((byte)0, "", (this.owningStatement == null) ? "N/A" : this.owningStatement.currentCatalog, this.connectionId, (this.owningStatement == null) ? -1 : this.owningStatement.getId(), 0, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, buf.toString()));
                    }
                }
            }
        }
        finally {
            if (this.owningStatement != null && calledExplicitly) {
                this.owningStatement.removeOpenResultSet(this);
            }
            SQLException exceptionDuringClose = null;
            if (this.rowData != null) {
                try {
                    this.rowData.close();
                }
                catch (SQLException sqlEx) {
                    exceptionDuringClose = sqlEx;
                }
            }
            if (this.statementUsedForFetchingRows != null) {
                try {
                    this.statementUsedForFetchingRows.realClose(true, false);
                }
                catch (SQLException sqlEx) {
                    if (exceptionDuringClose != null) {
                        exceptionDuringClose.setNextException(sqlEx);
                    }
                    else {
                        exceptionDuringClose = sqlEx;
                    }
                }
            }
            this.rowData = null;
            this.defaultTimeZone = null;
            this.fields = null;
            this.columnLabelToIndex = null;
            this.fullColumnNameToIndex = null;
            this.columnToIndexCache = null;
            this.eventSink = null;
            this.warningChain = null;
            if (!this.retainOwningStatement) {
                this.owningStatement = null;
            }
            this.catalog = null;
            this.serverInfo = null;
            this.thisRow = null;
            this.fastDateCal = null;
            this.connection = null;
            this.isClosed = true;
            if (exceptionDuringClose != null) {
                throw exceptionDuringClose;
            }
        }
    }
    
    public boolean reallyResult() {
        return this.rowData != null || this.reallyResult;
    }
    
    public void refreshRow() throws SQLException {
        throw new NotUpdatable();
    }
    
    public boolean relative(final int rows) throws SQLException {
        this.checkClosed();
        if (this.rowData.size() == 0) {
            this.setRowPositionValidity();
            return false;
        }
        if (this.thisRow != null) {
            this.thisRow.closeOpenStreams();
        }
        this.rowData.moveRowRelative(rows);
        this.thisRow = this.rowData.getAt(this.rowData.getCurrentRowNumber());
        this.setRowPositionValidity();
        return !this.rowData.isAfterLast() && !this.rowData.isBeforeFirst();
    }
    
    public boolean rowDeleted() throws SQLException {
        throw SQLError.notImplemented();
    }
    
    public boolean rowInserted() throws SQLException {
        throw SQLError.notImplemented();
    }
    
    public boolean rowUpdated() throws SQLException {
        throw SQLError.notImplemented();
    }
    
    protected void setBinaryEncoded() {
        this.isBinaryEncoded = true;
    }
    
    private void setDefaultTimeZone(final TimeZone defaultTimeZone) {
        this.defaultTimeZone = defaultTimeZone;
    }
    
    public void setFetchDirection(final int direction) throws SQLException {
        if (direction != 1000 && direction != 1001 && direction != 1002) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Illegal_value_for_fetch_direction_64"), "S1009", this.getExceptionInterceptor());
        }
        this.fetchDirection = direction;
    }
    
    public void setFetchSize(final int rows) throws SQLException {
        if (rows < 0) {
            throw SQLError.createSQLException(Messages.getString("ResultSet.Value_must_be_between_0_and_getMaxRows()_66"), "S1009", this.getExceptionInterceptor());
        }
        this.fetchSize = rows;
    }
    
    public void setFirstCharOfQuery(final char c) {
        this.firstCharOfQuery = c;
    }
    
    protected void setNextResultSet(final ResultSetInternalMethods nextResultSet) {
        this.nextResultSet = nextResultSet;
    }
    
    public void setOwningStatement(final StatementImpl owningStatement) {
        this.owningStatement = owningStatement;
    }
    
    protected void setResultSetConcurrency(final int concurrencyFlag) {
        this.resultSetConcurrency = concurrencyFlag;
    }
    
    protected void setResultSetType(final int typeFlag) {
        this.resultSetType = typeFlag;
    }
    
    protected void setServerInfo(final String info) {
        this.serverInfo = info;
    }
    
    public void setStatementUsedForFetchingRows(final PreparedStatement stmt) {
        this.statementUsedForFetchingRows = stmt;
    }
    
    public void setWrapperStatement(final Statement wrapperStatement) {
        this.wrapperStatement = wrapperStatement;
    }
    
    private void throwRangeException(final String valueAsString, final int columnIndex, final int jdbcType) throws SQLException {
        String datatype = null;
        switch (jdbcType) {
            case -6: {
                datatype = "TINYINT";
                break;
            }
            case 5: {
                datatype = "SMALLINT";
                break;
            }
            case 4: {
                datatype = "INTEGER";
                break;
            }
            case -5: {
                datatype = "BIGINT";
                break;
            }
            case 7: {
                datatype = "REAL";
                break;
            }
            case 6: {
                datatype = "FLOAT";
                break;
            }
            case 8: {
                datatype = "DOUBLE";
                break;
            }
            case 3: {
                datatype = "DECIMAL";
                break;
            }
            default: {
                datatype = " (JDBC type '" + jdbcType + "')";
                break;
            }
        }
        throw SQLError.createSQLException("'" + valueAsString + "' in column '" + columnIndex + "' is outside valid range for the datatype " + datatype + ".", "22003", this.getExceptionInterceptor());
    }
    
    public String toString() {
        if (this.reallyResult) {
            return super.toString();
        }
        return "Result set representing update count of " + this.updateCount;
    }
    
    public void updateArray(final int arg0, final Array arg1) throws SQLException {
        throw SQLError.notImplemented();
    }
    
    public void updateArray(final String arg0, final Array arg1) throws SQLException {
        throw SQLError.notImplemented();
    }
    
    public void updateAsciiStream(final int columnIndex, final InputStream x, final int length) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateAsciiStream(final String columnName, final InputStream x, final int length) throws SQLException {
        this.updateAsciiStream(this.findColumn(columnName), x, length);
    }
    
    public void updateBigDecimal(final int columnIndex, final BigDecimal x) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateBigDecimal(final String columnName, final BigDecimal x) throws SQLException {
        this.updateBigDecimal(this.findColumn(columnName), x);
    }
    
    public void updateBinaryStream(final int columnIndex, final InputStream x, final int length) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateBinaryStream(final String columnName, final InputStream x, final int length) throws SQLException {
        this.updateBinaryStream(this.findColumn(columnName), x, length);
    }
    
    public void updateBlob(final int arg0, final Blob arg1) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateBlob(final String arg0, final Blob arg1) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateBoolean(final int columnIndex, final boolean x) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateBoolean(final String columnName, final boolean x) throws SQLException {
        this.updateBoolean(this.findColumn(columnName), x);
    }
    
    public void updateByte(final int columnIndex, final byte x) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateByte(final String columnName, final byte x) throws SQLException {
        this.updateByte(this.findColumn(columnName), x);
    }
    
    public void updateBytes(final int columnIndex, final byte[] x) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateBytes(final String columnName, final byte[] x) throws SQLException {
        this.updateBytes(this.findColumn(columnName), x);
    }
    
    public void updateCharacterStream(final int columnIndex, final Reader x, final int length) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateCharacterStream(final String columnName, final Reader reader, final int length) throws SQLException {
        this.updateCharacterStream(this.findColumn(columnName), reader, length);
    }
    
    public void updateClob(final int arg0, final Clob arg1) throws SQLException {
        throw SQLError.notImplemented();
    }
    
    public void updateClob(final String columnName, final Clob clob) throws SQLException {
        this.updateClob(this.findColumn(columnName), clob);
    }
    
    public void updateDate(final int columnIndex, final Date x) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateDate(final String columnName, final Date x) throws SQLException {
        this.updateDate(this.findColumn(columnName), x);
    }
    
    public void updateDouble(final int columnIndex, final double x) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateDouble(final String columnName, final double x) throws SQLException {
        this.updateDouble(this.findColumn(columnName), x);
    }
    
    public void updateFloat(final int columnIndex, final float x) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateFloat(final String columnName, final float x) throws SQLException {
        this.updateFloat(this.findColumn(columnName), x);
    }
    
    public void updateInt(final int columnIndex, final int x) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateInt(final String columnName, final int x) throws SQLException {
        this.updateInt(this.findColumn(columnName), x);
    }
    
    public void updateLong(final int columnIndex, final long x) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateLong(final String columnName, final long x) throws SQLException {
        this.updateLong(this.findColumn(columnName), x);
    }
    
    public void updateNull(final int columnIndex) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateNull(final String columnName) throws SQLException {
        this.updateNull(this.findColumn(columnName));
    }
    
    public void updateObject(final int columnIndex, final Object x) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateObject(final int columnIndex, final Object x, final int scale) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateObject(final String columnName, final Object x) throws SQLException {
        this.updateObject(this.findColumn(columnName), x);
    }
    
    public void updateObject(final String columnName, final Object x, final int scale) throws SQLException {
        this.updateObject(this.findColumn(columnName), x);
    }
    
    public void updateRef(final int arg0, final Ref arg1) throws SQLException {
        throw SQLError.notImplemented();
    }
    
    public void updateRef(final String arg0, final Ref arg1) throws SQLException {
        throw SQLError.notImplemented();
    }
    
    public void updateRow() throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateShort(final int columnIndex, final short x) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateShort(final String columnName, final short x) throws SQLException {
        this.updateShort(this.findColumn(columnName), x);
    }
    
    public void updateString(final int columnIndex, final String x) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateString(final String columnName, final String x) throws SQLException {
        this.updateString(this.findColumn(columnName), x);
    }
    
    public void updateTime(final int columnIndex, final Time x) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateTime(final String columnName, final Time x) throws SQLException {
        this.updateTime(this.findColumn(columnName), x);
    }
    
    public void updateTimestamp(final int columnIndex, final Timestamp x) throws SQLException {
        throw new NotUpdatable();
    }
    
    public void updateTimestamp(final String columnName, final Timestamp x) throws SQLException {
        this.updateTimestamp(this.findColumn(columnName), x);
    }
    
    public boolean wasNull() throws SQLException {
        return this.wasNullFlag;
    }
    
    protected Calendar getGmtCalendar() {
        if (this.gmtCalendar == null) {
            this.gmtCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        }
        return this.gmtCalendar;
    }
    
    protected ExceptionInterceptor getExceptionInterceptor() {
        return this.exceptionInterceptor;
    }
    
    static {
        Label_0180: {
            if (Util.isJdbc4()) {
                try {
                    JDBC_4_RS_4_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4ResultSet").getConstructor(Long.TYPE, Long.TYPE, MySQLConnection.class, StatementImpl.class);
                    JDBC_4_RS_6_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4ResultSet").getConstructor(String.class, Field[].class, RowData.class, MySQLConnection.class, StatementImpl.class);
                    JDBC_4_UPD_RS_6_ARG_CTOR = Class.forName("com.mysql.jdbc.JDBC4UpdatableResultSet").getConstructor(String.class, Field[].class, RowData.class, MySQLConnection.class, StatementImpl.class);
                    break Label_0180;
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
            JDBC_4_RS_4_ARG_CTOR = null;
            JDBC_4_RS_6_ARG_CTOR = null;
            JDBC_4_UPD_RS_6_ARG_CTOR = null;
        }
        MIN_DIFF_PREC = Float.parseFloat(Float.toString(Float.MIN_VALUE)) - Double.parseDouble(Float.toString(Float.MIN_VALUE));
        MAX_DIFF_PREC = Float.parseFloat(Float.toString(Float.MAX_VALUE)) - Double.parseDouble(Float.toString(Float.MAX_VALUE));
        ResultSetImpl.resultCounter = 1;
        EMPTY_SPACE = new char[255];
        for (int i = 0; i < ResultSetImpl.EMPTY_SPACE.length; ++i) {
            ResultSetImpl.EMPTY_SPACE[i] = ' ';
        }
    }
}
