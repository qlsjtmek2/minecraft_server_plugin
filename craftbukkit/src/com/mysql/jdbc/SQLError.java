// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.Hashtable;
import java.net.BindException;
import com.mysql.jdbc.exceptions.MySQLTransactionRollbackException;
import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import com.mysql.jdbc.exceptions.MySQLDataException;
import com.mysql.jdbc.exceptions.MySQLNonTransientConnectionException;
import com.mysql.jdbc.exceptions.MySQLTransientConnectionException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.HashMap;
import java.util.TreeMap;
import java.sql.DataTruncation;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.util.Map;

public class SQLError
{
    static final int ER_WARNING_NOT_COMPLETE_ROLLBACK = 1196;
    private static Map mysqlToSql99State;
    private static Map mysqlToSqlState;
    public static final String SQL_STATE_BASE_TABLE_NOT_FOUND = "S0002";
    public static final String SQL_STATE_BASE_TABLE_OR_VIEW_ALREADY_EXISTS = "S0001";
    public static final String SQL_STATE_BASE_TABLE_OR_VIEW_NOT_FOUND = "42S02";
    public static final String SQL_STATE_COLUMN_ALREADY_EXISTS = "S0021";
    public static final String SQL_STATE_COLUMN_NOT_FOUND = "S0022";
    public static final String SQL_STATE_COMMUNICATION_LINK_FAILURE = "08S01";
    public static final String SQL_STATE_CONNECTION_FAIL_DURING_TX = "08007";
    public static final String SQL_STATE_CONNECTION_IN_USE = "08002";
    public static final String SQL_STATE_CONNECTION_NOT_OPEN = "08003";
    public static final String SQL_STATE_CONNECTION_REJECTED = "08004";
    public static final String SQL_STATE_DATE_TRUNCATED = "01004";
    public static final String SQL_STATE_DATETIME_FIELD_OVERFLOW = "22008";
    public static final String SQL_STATE_DEADLOCK = "41000";
    public static final String SQL_STATE_DISCONNECT_ERROR = "01002";
    public static final String SQL_STATE_DIVISION_BY_ZERO = "22012";
    public static final String SQL_STATE_DRIVER_NOT_CAPABLE = "S1C00";
    public static final String SQL_STATE_ERROR_IN_ROW = "01S01";
    public static final String SQL_STATE_GENERAL_ERROR = "S1000";
    public static final String SQL_STATE_ILLEGAL_ARGUMENT = "S1009";
    public static final String SQL_STATE_INDEX_ALREADY_EXISTS = "S0011";
    public static final String SQL_STATE_INDEX_NOT_FOUND = "S0012";
    public static final String SQL_STATE_INSERT_VALUE_LIST_NO_MATCH_COL_LIST = "21S01";
    public static final String SQL_STATE_INVALID_AUTH_SPEC = "28000";
    public static final String SQL_STATE_INVALID_CHARACTER_VALUE_FOR_CAST = "22018";
    public static final String SQL_STATE_INVALID_COLUMN_NUMBER = "S1002";
    public static final String SQL_STATE_INVALID_CONNECTION_ATTRIBUTE = "01S00";
    public static final String SQL_STATE_MEMORY_ALLOCATION_FAILURE = "S1001";
    public static final String SQL_STATE_MORE_THAN_ONE_ROW_UPDATED_OR_DELETED = "01S04";
    public static final String SQL_STATE_NO_DEFAULT_FOR_COLUMN = "S0023";
    public static final String SQL_STATE_NO_ROWS_UPDATED_OR_DELETED = "01S03";
    public static final String SQL_STATE_NUMERIC_VALUE_OUT_OF_RANGE = "22003";
    public static final String SQL_STATE_PRIVILEGE_NOT_REVOKED = "01006";
    public static final String SQL_STATE_SYNTAX_ERROR = "42000";
    public static final String SQL_STATE_TIMEOUT_EXPIRED = "S1T00";
    public static final String SQL_STATE_TRANSACTION_RESOLUTION_UNKNOWN = "08007";
    public static final String SQL_STATE_UNABLE_TO_CONNECT_TO_DATASOURCE = "08001";
    public static final String SQL_STATE_WRONG_NO_OF_PARAMETERS = "07001";
    public static final String SQL_STATE_INVALID_TRANSACTION_TERMINATION = "2D000";
    private static Map sqlStateMessages;
    private static final long DEFAULT_WAIT_TIMEOUT_SECONDS = 28800L;
    private static final int DUE_TO_TIMEOUT_FALSE = 0;
    private static final int DUE_TO_TIMEOUT_MAYBE = 2;
    private static final int DUE_TO_TIMEOUT_TRUE = 1;
    private static final Constructor JDBC_4_COMMUNICATIONS_EXCEPTION_CTOR;
    private static Method THROWABLE_INIT_CAUSE_METHOD;
    
    static SQLWarning convertShowWarningsToSQLWarnings(final Connection connection) throws SQLException {
        return convertShowWarningsToSQLWarnings(connection, 0, false);
    }
    
    static SQLWarning convertShowWarningsToSQLWarnings(final Connection connection, final int warningCountIfKnown, final boolean forTruncationOnly) throws SQLException {
        Statement stmt = null;
        ResultSet warnRs = null;
        SQLWarning currentWarning = null;
        try {
            if (warningCountIfKnown < 100) {
                stmt = connection.createStatement();
                if (stmt.getMaxRows() != 0) {
                    stmt.setMaxRows(0);
                }
            }
            else {
                stmt = connection.createStatement(1003, 1007);
                stmt.setFetchSize(Integer.MIN_VALUE);
            }
            warnRs = stmt.executeQuery("SHOW WARNINGS");
            while (warnRs.next()) {
                final int code = warnRs.getInt("Code");
                if (forTruncationOnly) {
                    if (code != 1265 && code != 1264) {
                        continue;
                    }
                    final DataTruncation newTruncation = new MysqlDataTruncation(warnRs.getString("Message"), 0, false, false, 0, 0, code);
                    if (currentWarning == null) {
                        currentWarning = newTruncation;
                    }
                    else {
                        currentWarning.setNextWarning(newTruncation);
                    }
                }
                else {
                    final String level = warnRs.getString("Level");
                    final String message = warnRs.getString("Message");
                    final SQLWarning newWarning = new SQLWarning(message, mysqlToSqlState(code, connection.getUseSqlStateCodes()), code);
                    if (currentWarning == null) {
                        currentWarning = newWarning;
                    }
                    else {
                        currentWarning.setNextWarning(newWarning);
                    }
                }
            }
            if (forTruncationOnly && currentWarning != null) {
                throw currentWarning;
            }
            return currentWarning;
        }
        finally {
            SQLException reThrow = null;
            if (warnRs != null) {
                try {
                    warnRs.close();
                }
                catch (SQLException sqlEx) {
                    reThrow = sqlEx;
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException sqlEx) {
                    reThrow = sqlEx;
                }
            }
            if (reThrow != null) {
                throw reThrow;
            }
        }
    }
    
    public static void dumpSqlStatesMappingsAsXml() throws Exception {
        final TreeMap allErrorNumbers = new TreeMap();
        final Map mysqlErrorNumbersToNames = new HashMap();
        Integer errorNumber = null;
        Iterator mysqlErrorNumbers = SQLError.mysqlToSql99State.keySet().iterator();
        while (mysqlErrorNumbers.hasNext()) {
            errorNumber = mysqlErrorNumbers.next();
            allErrorNumbers.put(errorNumber, errorNumber);
        }
        mysqlErrorNumbers = SQLError.mysqlToSqlState.keySet().iterator();
        while (mysqlErrorNumbers.hasNext()) {
            errorNumber = mysqlErrorNumbers.next();
            allErrorNumbers.put(errorNumber, errorNumber);
        }
        final Field[] possibleFields = MysqlErrorNumbers.class.getDeclaredFields();
        for (int i = 0; i < possibleFields.length; ++i) {
            final String fieldName = possibleFields[i].getName();
            if (fieldName.startsWith("ER_")) {
                mysqlErrorNumbersToNames.put(possibleFields[i].get(null), fieldName);
            }
        }
        System.out.println("<ErrorMappings>");
        final Iterator allErrorNumbersIter = allErrorNumbers.keySet().iterator();
        while (allErrorNumbersIter.hasNext()) {
            errorNumber = allErrorNumbersIter.next();
            final String sql92State = mysqlToSql99(errorNumber);
            final String oldSqlState = mysqlToXOpen(errorNumber);
            System.out.println("   <ErrorMapping mysqlErrorNumber=\"" + errorNumber + "\" mysqlErrorName=\"" + mysqlErrorNumbersToNames.get(errorNumber) + "\" legacySqlState=\"" + ((oldSqlState == null) ? "" : oldSqlState) + "\" sql92SqlState=\"" + ((sql92State == null) ? "" : sql92State) + "\"/>");
        }
        System.out.println("</ErrorMappings>");
    }
    
    static String get(final String stateCode) {
        return SQLError.sqlStateMessages.get(stateCode);
    }
    
    private static String mysqlToSql99(final int errno) {
        final Integer err = Constants.integerValueOf(errno);
        if (SQLError.mysqlToSql99State.containsKey(err)) {
            return SQLError.mysqlToSql99State.get(err);
        }
        return "HY000";
    }
    
    static String mysqlToSqlState(final int errno, final boolean useSql92States) {
        if (useSql92States) {
            return mysqlToSql99(errno);
        }
        return mysqlToXOpen(errno);
    }
    
    private static String mysqlToXOpen(final int errno) {
        final Integer err = Constants.integerValueOf(errno);
        if (SQLError.mysqlToSqlState.containsKey(err)) {
            return SQLError.mysqlToSqlState.get(err);
        }
        return "S1000";
    }
    
    public static SQLException createSQLException(final String message, final String sqlState, final ExceptionInterceptor interceptor) {
        return createSQLException(message, sqlState, 0, interceptor);
    }
    
    public static SQLException createSQLException(final String message, final ExceptionInterceptor interceptor) {
        return createSQLException(message, interceptor, null);
    }
    
    public static SQLException createSQLException(final String message, final ExceptionInterceptor interceptor, final Connection conn) {
        final SQLException sqlEx = new SQLException(message);
        if (interceptor != null) {
            final SQLException interceptedEx = interceptor.interceptException(sqlEx, conn);
            if (interceptedEx != null) {
                return interceptedEx;
            }
        }
        return sqlEx;
    }
    
    public static SQLException createSQLException(final String message, final String sqlState, final Throwable cause, final ExceptionInterceptor interceptor) {
        return createSQLException(message, sqlState, cause, interceptor, null);
    }
    
    public static SQLException createSQLException(String message, final String sqlState, final Throwable cause, final ExceptionInterceptor interceptor, final Connection conn) {
        if (SQLError.THROWABLE_INIT_CAUSE_METHOD == null && cause != null) {
            message = message + " due to " + cause.toString();
        }
        final SQLException sqlEx = createSQLException(message, sqlState, interceptor);
        if (cause != null && SQLError.THROWABLE_INIT_CAUSE_METHOD != null) {
            try {
                SQLError.THROWABLE_INIT_CAUSE_METHOD.invoke(sqlEx, cause);
            }
            catch (Throwable t) {}
        }
        if (interceptor != null) {
            final SQLException interceptedEx = interceptor.interceptException(sqlEx, conn);
            if (interceptedEx != null) {
                return interceptedEx;
            }
        }
        return sqlEx;
    }
    
    public static SQLException createSQLException(final String message, final String sqlState, final int vendorErrorCode, final ExceptionInterceptor interceptor) {
        return createSQLException(message, sqlState, vendorErrorCode, false, interceptor);
    }
    
    public static SQLException createSQLException(final String message, final String sqlState, final int vendorErrorCode, final boolean isTransient, final ExceptionInterceptor interceptor) {
        return createSQLException(message, sqlState, vendorErrorCode, false, interceptor, null);
    }
    
    public static SQLException createSQLException(final String message, final String sqlState, final int vendorErrorCode, final boolean isTransient, final ExceptionInterceptor interceptor, final Connection conn) {
        try {
            SQLException sqlEx = null;
            if (sqlState != null) {
                if (sqlState.startsWith("08")) {
                    if (isTransient) {
                        if (!Util.isJdbc4()) {
                            sqlEx = new MySQLTransientConnectionException(message, sqlState, vendorErrorCode);
                        }
                        else {
                            sqlEx = (SQLException)Util.getInstance("com.mysql.jdbc.exceptions.jdbc4.MySQLTransientConnectionException", new Class[] { String.class, String.class, Integer.TYPE }, new Object[] { message, sqlState, Constants.integerValueOf(vendorErrorCode) }, interceptor);
                        }
                    }
                    else if (!Util.isJdbc4()) {
                        sqlEx = new MySQLNonTransientConnectionException(message, sqlState, vendorErrorCode);
                    }
                    else {
                        sqlEx = (SQLException)Util.getInstance("com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException", new Class[] { String.class, String.class, Integer.TYPE }, new Object[] { message, sqlState, Constants.integerValueOf(vendorErrorCode) }, interceptor);
                    }
                }
                else if (sqlState.startsWith("22")) {
                    if (!Util.isJdbc4()) {
                        sqlEx = new MySQLDataException(message, sqlState, vendorErrorCode);
                    }
                    else {
                        sqlEx = (SQLException)Util.getInstance("com.mysql.jdbc.exceptions.jdbc4.MySQLDataException", new Class[] { String.class, String.class, Integer.TYPE }, new Object[] { message, sqlState, Constants.integerValueOf(vendorErrorCode) }, interceptor);
                    }
                }
                else if (sqlState.startsWith("23")) {
                    if (!Util.isJdbc4()) {
                        sqlEx = new MySQLIntegrityConstraintViolationException(message, sqlState, vendorErrorCode);
                    }
                    else {
                        sqlEx = (SQLException)Util.getInstance("com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException", new Class[] { String.class, String.class, Integer.TYPE }, new Object[] { message, sqlState, Constants.integerValueOf(vendorErrorCode) }, interceptor);
                    }
                }
                else if (sqlState.startsWith("42")) {
                    if (!Util.isJdbc4()) {
                        sqlEx = new MySQLSyntaxErrorException(message, sqlState, vendorErrorCode);
                    }
                    else {
                        sqlEx = (SQLException)Util.getInstance("com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException", new Class[] { String.class, String.class, Integer.TYPE }, new Object[] { message, sqlState, Constants.integerValueOf(vendorErrorCode) }, interceptor);
                    }
                }
                else if (sqlState.startsWith("40")) {
                    if (!Util.isJdbc4()) {
                        sqlEx = new MySQLTransactionRollbackException(message, sqlState, vendorErrorCode);
                    }
                    else {
                        sqlEx = (SQLException)Util.getInstance("com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException", new Class[] { String.class, String.class, Integer.TYPE }, new Object[] { message, sqlState, Constants.integerValueOf(vendorErrorCode) }, interceptor);
                    }
                }
                else {
                    sqlEx = new SQLException(message, sqlState, vendorErrorCode);
                }
            }
            else {
                sqlEx = new SQLException(message, sqlState, vendorErrorCode);
            }
            if (interceptor != null) {
                final SQLException interceptedEx = interceptor.interceptException(sqlEx, conn);
                if (interceptedEx != null) {
                    return interceptedEx;
                }
            }
            return sqlEx;
        }
        catch (SQLException sqlEx) {
            final SQLException unexpectedEx = new SQLException("Unable to create correct SQLException class instance, error class/codes may be incorrect. Reason: " + Util.stackTraceToString(sqlEx), "S1000");
            if (interceptor != null) {
                final SQLException interceptedEx2 = interceptor.interceptException(unexpectedEx, conn);
                if (interceptedEx2 != null) {
                    return interceptedEx2;
                }
            }
            return unexpectedEx;
        }
    }
    
    public static SQLException createCommunicationsException(final MySQLConnection conn, final long lastPacketSentTimeMs, final long lastPacketReceivedTimeMs, final Exception underlyingException, final ExceptionInterceptor interceptor) {
        SQLException exToReturn = null;
        if (!Util.isJdbc4()) {
            exToReturn = new CommunicationsException(conn, lastPacketSentTimeMs, lastPacketReceivedTimeMs, underlyingException);
        }
        else {
            try {
                exToReturn = (SQLException)Util.handleNewInstance(SQLError.JDBC_4_COMMUNICATIONS_EXCEPTION_CTOR, new Object[] { conn, Constants.longValueOf(lastPacketSentTimeMs), Constants.longValueOf(lastPacketReceivedTimeMs), underlyingException }, interceptor);
            }
            catch (SQLException sqlEx) {
                return sqlEx;
            }
        }
        if (SQLError.THROWABLE_INIT_CAUSE_METHOD != null && underlyingException != null) {
            try {
                SQLError.THROWABLE_INIT_CAUSE_METHOD.invoke(exToReturn, underlyingException);
            }
            catch (Throwable t) {}
        }
        if (interceptor != null) {
            final SQLException interceptedEx = interceptor.interceptException(exToReturn, conn);
            if (interceptedEx != null) {
                return interceptedEx;
            }
        }
        return exToReturn;
    }
    
    public static String createLinkFailureMessageBasedOnHeuristics(final MySQLConnection conn, long lastPacketSentTimeMs, final long lastPacketReceivedTimeMs, final Exception underlyingException, final boolean streamingResultSetInPlay) {
        long serverTimeoutSeconds = 0L;
        boolean isInteractiveClient = false;
        if (conn != null) {
            isInteractiveClient = conn.getInteractiveClient();
            String serverTimeoutSecondsStr = null;
            if (isInteractiveClient) {
                serverTimeoutSecondsStr = conn.getServerVariable("interactive_timeout");
            }
            else {
                serverTimeoutSecondsStr = conn.getServerVariable("wait_timeout");
            }
            if (serverTimeoutSecondsStr != null) {
                try {
                    serverTimeoutSeconds = Long.parseLong(serverTimeoutSecondsStr);
                }
                catch (NumberFormatException nfe) {
                    serverTimeoutSeconds = 0L;
                }
            }
        }
        final StringBuffer exceptionMessageBuf = new StringBuffer();
        if (lastPacketSentTimeMs == 0L) {
            lastPacketSentTimeMs = System.currentTimeMillis();
        }
        final long timeSinceLastPacket = (System.currentTimeMillis() - lastPacketSentTimeMs) / 1000L;
        final long timeSinceLastPacketMs = System.currentTimeMillis() - lastPacketSentTimeMs;
        final long timeSinceLastPacketReceivedMs = System.currentTimeMillis() - lastPacketReceivedTimeMs;
        int dueToTimeout = 0;
        StringBuffer timeoutMessageBuf = null;
        if (streamingResultSetInPlay) {
            exceptionMessageBuf.append(Messages.getString("CommunicationsException.ClientWasStreaming"));
        }
        else {
            if (serverTimeoutSeconds != 0L) {
                if (timeSinceLastPacket > serverTimeoutSeconds) {
                    dueToTimeout = 1;
                    timeoutMessageBuf = new StringBuffer();
                    timeoutMessageBuf.append(Messages.getString("CommunicationsException.2"));
                    if (!isInteractiveClient) {
                        timeoutMessageBuf.append(Messages.getString("CommunicationsException.3"));
                    }
                    else {
                        timeoutMessageBuf.append(Messages.getString("CommunicationsException.4"));
                    }
                }
            }
            else if (timeSinceLastPacket > 28800L) {
                dueToTimeout = 2;
                timeoutMessageBuf = new StringBuffer();
                timeoutMessageBuf.append(Messages.getString("CommunicationsException.5"));
                timeoutMessageBuf.append(Messages.getString("CommunicationsException.6"));
                timeoutMessageBuf.append(Messages.getString("CommunicationsException.7"));
                timeoutMessageBuf.append(Messages.getString("CommunicationsException.8"));
            }
            if (dueToTimeout == 1 || dueToTimeout == 2) {
                if (lastPacketReceivedTimeMs != 0L) {
                    final Object[] timingInfo = { new Long(timeSinceLastPacketReceivedMs), new Long(timeSinceLastPacketMs) };
                    exceptionMessageBuf.append(Messages.getString("CommunicationsException.ServerPacketTimingInfo", timingInfo));
                }
                else {
                    exceptionMessageBuf.append(Messages.getString("CommunicationsException.ServerPacketTimingInfoNoRecv", new Object[] { new Long(timeSinceLastPacketMs) }));
                }
                if (timeoutMessageBuf != null) {
                    exceptionMessageBuf.append(timeoutMessageBuf);
                }
                exceptionMessageBuf.append(Messages.getString("CommunicationsException.11"));
                exceptionMessageBuf.append(Messages.getString("CommunicationsException.12"));
                exceptionMessageBuf.append(Messages.getString("CommunicationsException.13"));
            }
            else if (underlyingException instanceof BindException) {
                if (conn.getLocalSocketAddress() != null && !Util.interfaceExists(conn.getLocalSocketAddress())) {
                    exceptionMessageBuf.append(Messages.getString("CommunicationsException.LocalSocketAddressNotAvailable"));
                }
                else {
                    exceptionMessageBuf.append(Messages.getString("CommunicationsException.TooManyClientConnections"));
                }
            }
        }
        if (exceptionMessageBuf.length() == 0) {
            exceptionMessageBuf.append(Messages.getString("CommunicationsException.20"));
            if (SQLError.THROWABLE_INIT_CAUSE_METHOD == null && underlyingException != null) {
                exceptionMessageBuf.append(Messages.getString("CommunicationsException.21"));
                exceptionMessageBuf.append(Util.stackTraceToString(underlyingException));
            }
            if (conn != null && conn.getMaintainTimeStats() && !conn.getParanoid()) {
                exceptionMessageBuf.append("\n\n");
                if (lastPacketReceivedTimeMs != 0L) {
                    final Object[] timingInfo = { new Long(timeSinceLastPacketReceivedMs), new Long(timeSinceLastPacketMs) };
                    exceptionMessageBuf.append(Messages.getString("CommunicationsException.ServerPacketTimingInfo", timingInfo));
                }
                else {
                    exceptionMessageBuf.append(Messages.getString("CommunicationsException.ServerPacketTimingInfoNoRecv", new Object[] { new Long(timeSinceLastPacketMs) }));
                }
            }
        }
        return exceptionMessageBuf.toString();
    }
    
    public static SQLException notImplemented() {
        if (Util.isJdbc4()) {
            try {
                return (SQLException)Class.forName("java.sql.SQLFeatureNotSupportedException").newInstance();
            }
            catch (Throwable t) {}
        }
        return new NotImplemented();
    }
    
    static {
        Label_0082: {
            if (Util.isJdbc4()) {
                try {
                    JDBC_4_COMMUNICATIONS_EXCEPTION_CTOR = Class.forName("com.mysql.jdbc.exceptions.jdbc4.CommunicationsException").getConstructor(MySQLConnection.class, Long.TYPE, Long.TYPE, Exception.class);
                    break Label_0082;
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
            JDBC_4_COMMUNICATIONS_EXCEPTION_CTOR = null;
            try {
                SQLError.THROWABLE_INIT_CAUSE_METHOD = Throwable.class.getMethod("initCause", Throwable.class);
            }
            catch (Throwable t) {
                SQLError.THROWABLE_INIT_CAUSE_METHOD = null;
            }
        }
        (SQLError.sqlStateMessages = new HashMap()).put("01002", Messages.getString("SQLError.35"));
        SQLError.sqlStateMessages.put("01004", Messages.getString("SQLError.36"));
        SQLError.sqlStateMessages.put("01006", Messages.getString("SQLError.37"));
        SQLError.sqlStateMessages.put("01S00", Messages.getString("SQLError.38"));
        SQLError.sqlStateMessages.put("01S01", Messages.getString("SQLError.39"));
        SQLError.sqlStateMessages.put("01S03", Messages.getString("SQLError.40"));
        SQLError.sqlStateMessages.put("01S04", Messages.getString("SQLError.41"));
        SQLError.sqlStateMessages.put("07001", Messages.getString("SQLError.42"));
        SQLError.sqlStateMessages.put("08001", Messages.getString("SQLError.43"));
        SQLError.sqlStateMessages.put("08002", Messages.getString("SQLError.44"));
        SQLError.sqlStateMessages.put("08003", Messages.getString("SQLError.45"));
        SQLError.sqlStateMessages.put("08004", Messages.getString("SQLError.46"));
        SQLError.sqlStateMessages.put("08007", Messages.getString("SQLError.47"));
        SQLError.sqlStateMessages.put("08S01", Messages.getString("SQLError.48"));
        SQLError.sqlStateMessages.put("21S01", Messages.getString("SQLError.49"));
        SQLError.sqlStateMessages.put("22003", Messages.getString("SQLError.50"));
        SQLError.sqlStateMessages.put("22008", Messages.getString("SQLError.51"));
        SQLError.sqlStateMessages.put("22012", Messages.getString("SQLError.52"));
        SQLError.sqlStateMessages.put("41000", Messages.getString("SQLError.53"));
        SQLError.sqlStateMessages.put("28000", Messages.getString("SQLError.54"));
        SQLError.sqlStateMessages.put("42000", Messages.getString("SQLError.55"));
        SQLError.sqlStateMessages.put("42S02", Messages.getString("SQLError.56"));
        SQLError.sqlStateMessages.put("S0001", Messages.getString("SQLError.57"));
        SQLError.sqlStateMessages.put("S0002", Messages.getString("SQLError.58"));
        SQLError.sqlStateMessages.put("S0011", Messages.getString("SQLError.59"));
        SQLError.sqlStateMessages.put("S0012", Messages.getString("SQLError.60"));
        SQLError.sqlStateMessages.put("S0021", Messages.getString("SQLError.61"));
        SQLError.sqlStateMessages.put("S0022", Messages.getString("SQLError.62"));
        SQLError.sqlStateMessages.put("S0023", Messages.getString("SQLError.63"));
        SQLError.sqlStateMessages.put("S1000", Messages.getString("SQLError.64"));
        SQLError.sqlStateMessages.put("S1001", Messages.getString("SQLError.65"));
        SQLError.sqlStateMessages.put("S1002", Messages.getString("SQLError.66"));
        SQLError.sqlStateMessages.put("S1009", Messages.getString("SQLError.67"));
        SQLError.sqlStateMessages.put("S1C00", Messages.getString("SQLError.68"));
        SQLError.sqlStateMessages.put("S1T00", Messages.getString("SQLError.69"));
        (SQLError.mysqlToSqlState = new Hashtable()).put(Constants.integerValueOf(1040), "08004");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1042), "08004");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1043), "08004");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1047), "08S01");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1081), "08S01");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1129), "08004");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1130), "08004");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1045), "28000");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1037), "S1001");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1038), "S1001");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1064), "42000");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1065), "42000");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1055), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1056), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1057), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1059), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1060), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1061), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1062), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1063), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1066), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1067), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1068), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1069), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1070), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1071), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1072), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1073), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1074), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1075), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1082), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1083), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1084), "S1009");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1058), "21S01");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1051), "42S02");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1054), "S0022");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1205), "41000");
        SQLError.mysqlToSqlState.put(Constants.integerValueOf(1213), "41000");
        (SQLError.mysqlToSql99State = new HashMap()).put(Constants.integerValueOf(1205), "41000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1213), "41000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1022), "23000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1037), "HY001");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1038), "HY001");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1040), "08004");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1042), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1043), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1044), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1045), "28000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1050), "42S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1051), "42S02");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1052), "23000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1053), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1054), "42S22");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1055), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1056), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1057), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1058), "21S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1059), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1060), "42S21");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1061), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1062), "23000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1063), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1064), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1065), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1066), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1067), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1068), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1069), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1070), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1071), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1072), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1073), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1074), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1075), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1080), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1081), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1082), "42S12");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1083), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1084), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1090), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1091), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1101), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1102), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1103), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1104), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1106), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1107), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1109), "42S02");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1110), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1112), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1113), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1115), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1118), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1120), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1121), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1131), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1132), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1133), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1136), "21S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1138), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1139), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1140), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1141), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1142), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1143), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1144), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1145), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1146), "42S02");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1147), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1148), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1149), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1152), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1153), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1154), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1155), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1156), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1157), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1158), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1159), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1160), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1161), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1162), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1163), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1164), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1166), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1167), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1169), "23000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1170), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1171), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1172), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1173), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1177), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1178), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1179), "25000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1184), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1189), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1190), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1203), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1207), "25000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1211), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1213), "40001");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1216), "23000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1217), "23000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1218), "08S01");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1222), "21000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1226), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1230), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1231), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1232), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1234), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1235), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1239), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1241), "21000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1242), "21000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1247), "42S22");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1248), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1249), "01000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1250), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1251), "08004");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1252), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1253), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1261), "01000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1262), "01000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1263), "01000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1264), "01000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1265), "01000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1280), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1281), "42000");
        SQLError.mysqlToSql99State.put(Constants.integerValueOf(1286), "42000");
    }
}
