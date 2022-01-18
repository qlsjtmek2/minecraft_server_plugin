// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import com.mysql.jdbc.log.NullLogger;
import java.sql.Savepoint;
import com.mysql.jdbc.profiler.ProfilerEvent;
import java.sql.SQLWarning;
import java.io.IOException;
import java.util.Stack;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.UnsupportedCharsetException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.sql.PreparedStatement;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Enumeration;
import com.mysql.jdbc.log.LogFactory;
import java.util.GregorianCalendar;
import java.sql.Blob;
import java.util.HashMap;
import java.lang.reflect.Method;
import java.lang.reflect.Array;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;
import com.mysql.jdbc.util.LRUCache;
import com.mysql.jdbc.profiler.ProfilerEventHandler;
import java.util.TimeZone;
import java.sql.DatabaseMetaData;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Timer;
import com.mysql.jdbc.log.Log;
import java.util.Map;

public class ConnectionImpl extends ConnectionPropertiesImpl implements MySQLConnection
{
    private static final String JDBC_LOCAL_CHARACTER_SET_RESULTS = "jdbc.local.character_set_results";
    private MySQLConnection proxy;
    private static final Object CHARSET_CONVERTER_NOT_AVAILABLE_MARKER;
    public static Map charsetMap;
    protected static final String DEFAULT_LOGGER_CLASS = "com.mysql.jdbc.log.StandardLogger";
    private static final int HISTOGRAM_BUCKETS = 20;
    private static final String LOGGER_INSTANCE_NAME = "MySQL";
    private static Map mapTransIsolationNameToValue;
    private static final Log NULL_LOGGER;
    private static Map roundRobinStatsMap;
    private static final Map serverCollationByUrl;
    private static final Map serverConfigByUrl;
    private long queryTimeCount;
    private double queryTimeSum;
    private double queryTimeSumSquares;
    private double queryTimeMean;
    private Timer cancelTimer;
    private List connectionLifecycleInterceptors;
    private static final Constructor JDBC_4_CONNECTION_CTOR;
    private static final int DEFAULT_RESULT_SET_TYPE = 1003;
    private static final int DEFAULT_RESULT_SET_CONCURRENCY = 1007;
    private boolean autoCommit;
    private Map cachedPreparedStatementParams;
    private String characterSetMetadata;
    private String characterSetResultsOnServer;
    private Map charsetConverterMap;
    private Map charsetToNumBytesMap;
    private long connectionCreationTimeMillis;
    private long connectionId;
    private String database;
    private DatabaseMetaData dbmd;
    private TimeZone defaultTimeZone;
    private ProfilerEventHandler eventSink;
    private Throwable forceClosedReason;
    private Throwable forcedClosedLocation;
    private boolean hasIsolationLevels;
    private boolean hasQuotedIdentifiers;
    private String host;
    private String[] indexToCharsetMapping;
    private MysqlIO io;
    private boolean isClientTzUTC;
    private boolean isClosed;
    private boolean isInGlobalTx;
    private boolean isRunningOnJDK13;
    private int isolationLevel;
    private boolean isServerTzUTC;
    private long lastQueryFinishedTime;
    private Log log;
    private long longestQueryTimeMs;
    private boolean lowerCaseTableNames;
    private long masterFailTimeMillis;
    private long maximumNumberTablesAccessed;
    private boolean maxRowsChanged;
    private long metricsLastReportedMs;
    private long minimumNumberTablesAccessed;
    private final Object mutex;
    private String myURL;
    private boolean needsPing;
    private int netBufferLength;
    private boolean noBackslashEscapes;
    private long numberOfPreparedExecutes;
    private long numberOfPrepares;
    private long numberOfQueriesIssued;
    private long numberOfResultSetsCreated;
    private long[] numTablesMetricsHistBreakpoints;
    private int[] numTablesMetricsHistCounts;
    private long[] oldHistBreakpoints;
    private int[] oldHistCounts;
    private Map openStatements;
    private LRUCache parsedCallableStatementCache;
    private boolean parserKnowsUnicode;
    private String password;
    private long[] perfMetricsHistBreakpoints;
    private int[] perfMetricsHistCounts;
    private Throwable pointOfOrigin;
    private int port;
    protected Properties props;
    private boolean readInfoMsg;
    private boolean readOnly;
    protected LRUCache resultSetMetadataCache;
    private TimeZone serverTimezoneTZ;
    private Map serverVariables;
    private long shortestQueryTimeMs;
    private Map statementsUsingMaxRows;
    private double totalQueryTimeMs;
    private boolean transactionsSupported;
    private Map typeMap;
    private boolean useAnsiQuotes;
    private String user;
    private boolean useServerPreparedStmts;
    private LRUCache serverSideStatementCheckCache;
    private LRUCache serverSideStatementCache;
    private Calendar sessionCalendar;
    private Calendar utcCalendar;
    private String origHostToConnectTo;
    private int origPortToConnectTo;
    private String origDatabaseToConnectTo;
    private String errorMessageEncoding;
    private boolean usePlatformCharsetConverters;
    private boolean hasTriedMasterFlag;
    private String statementComment;
    private boolean storesLowerCaseTableName;
    private List statementInterceptors;
    private boolean requiresEscapingEncoder;
    private String hostPortPair;
    private boolean usingCachedConfig;
    private int autoIncrementIncrement;
    private ExceptionInterceptor exceptionInterceptor;
    
    public String getHost() {
        return this.host;
    }
    
    public boolean isProxySet() {
        return this.proxy != null;
    }
    
    public void setProxy(final MySQLConnection proxy) {
        this.proxy = proxy;
    }
    
    private MySQLConnection getProxy() {
        return (this.proxy != null) ? this.proxy : this;
    }
    
    public MySQLConnection getLoadBalanceSafeProxy() {
        return this.getProxy();
    }
    
    protected static SQLException appendMessageToException(final SQLException sqlEx, final String messageToAppend, final ExceptionInterceptor interceptor) {
        final String origMessage = sqlEx.getMessage();
        final String sqlState = sqlEx.getSQLState();
        final int vendorErrorCode = sqlEx.getErrorCode();
        final StringBuffer messageBuf = new StringBuffer(origMessage.length() + messageToAppend.length());
        messageBuf.append(origMessage);
        messageBuf.append(messageToAppend);
        final SQLException sqlExceptionWithNewMessage = SQLError.createSQLException(messageBuf.toString(), sqlState, vendorErrorCode, interceptor);
        try {
            Method getStackTraceMethod = null;
            Method setStackTraceMethod = null;
            Object theStackTraceAsObject = null;
            final Class stackTraceElementClass = Class.forName("java.lang.StackTraceElement");
            final Class stackTraceElementArrayClass = Array.newInstance(stackTraceElementClass, new int[] { 0 }).getClass();
            getStackTraceMethod = Throwable.class.getMethod("getStackTrace", (Class<?>[])new Class[0]);
            setStackTraceMethod = Throwable.class.getMethod("setStackTrace", stackTraceElementArrayClass);
            if (getStackTraceMethod != null && setStackTraceMethod != null) {
                theStackTraceAsObject = getStackTraceMethod.invoke(sqlEx, new Object[0]);
                setStackTraceMethod.invoke(sqlExceptionWithNewMessage, theStackTraceAsObject);
            }
        }
        catch (NoClassDefFoundError noClassDefFound) {}
        catch (NoSuchMethodException noSuchMethodEx) {}
        catch (Throwable t) {}
        return sqlExceptionWithNewMessage;
    }
    
    public synchronized Timer getCancelTimer() {
        if (this.cancelTimer == null) {
            boolean createdNamedTimer = false;
            try {
                final Constructor ctr = Timer.class.getConstructor(String.class, Boolean.TYPE);
                this.cancelTimer = ctr.newInstance("MySQL Statement Cancellation Timer", Boolean.TRUE);
                createdNamedTimer = true;
            }
            catch (Throwable t) {
                createdNamedTimer = false;
            }
            if (!createdNamedTimer) {
                this.cancelTimer = new Timer(true);
            }
        }
        return this.cancelTimer;
    }
    
    protected static Connection getInstance(final String hostToConnectTo, final int portToConnectTo, final Properties info, final String databaseToConnectTo, final String url) throws SQLException {
        if (!Util.isJdbc4()) {
            return new ConnectionImpl(hostToConnectTo, portToConnectTo, info, databaseToConnectTo, url);
        }
        return (Connection)Util.handleNewInstance(ConnectionImpl.JDBC_4_CONNECTION_CTOR, new Object[] { hostToConnectTo, Constants.integerValueOf(portToConnectTo), info, databaseToConnectTo, url }, null);
    }
    
    private static synchronized int getNextRoundRobinHostIndex(final String url, final List hostList) {
        final int indexRange = hostList.size();
        final int index = (int)(Math.random() * indexRange);
        return index;
    }
    
    private static boolean nullSafeCompare(final String s1, final String s2) {
        return (s1 == null && s2 == null) || ((s1 != null || s2 == null) && s1.equals(s2));
    }
    
    protected ConnectionImpl() {
        this.proxy = null;
        this.autoCommit = true;
        this.characterSetMetadata = null;
        this.characterSetResultsOnServer = null;
        this.charsetConverterMap = new HashMap(CharsetMapping.getNumberOfCharsetsConfigured());
        this.connectionCreationTimeMillis = 0L;
        this.database = null;
        this.dbmd = null;
        this.hasIsolationLevels = false;
        this.hasQuotedIdentifiers = false;
        this.host = null;
        this.indexToCharsetMapping = CharsetMapping.INDEX_TO_CHARSET;
        this.io = null;
        this.isClientTzUTC = false;
        this.isClosed = true;
        this.isInGlobalTx = false;
        this.isRunningOnJDK13 = false;
        this.isolationLevel = 2;
        this.isServerTzUTC = false;
        this.lastQueryFinishedTime = 0L;
        this.log = ConnectionImpl.NULL_LOGGER;
        this.longestQueryTimeMs = 0L;
        this.lowerCaseTableNames = false;
        this.masterFailTimeMillis = 0L;
        this.maximumNumberTablesAccessed = 0L;
        this.maxRowsChanged = false;
        this.minimumNumberTablesAccessed = Long.MAX_VALUE;
        this.mutex = new Object();
        this.myURL = null;
        this.needsPing = false;
        this.netBufferLength = 16384;
        this.noBackslashEscapes = false;
        this.numberOfPreparedExecutes = 0L;
        this.numberOfPrepares = 0L;
        this.numberOfQueriesIssued = 0L;
        this.numberOfResultSetsCreated = 0L;
        this.oldHistBreakpoints = null;
        this.oldHistCounts = null;
        this.parserKnowsUnicode = false;
        this.password = null;
        this.port = 3306;
        this.props = null;
        this.readInfoMsg = false;
        this.readOnly = false;
        this.serverTimezoneTZ = null;
        this.serverVariables = null;
        this.shortestQueryTimeMs = Long.MAX_VALUE;
        this.totalQueryTimeMs = 0.0;
        this.transactionsSupported = false;
        this.useAnsiQuotes = false;
        this.user = null;
        this.useServerPreparedStmts = false;
        this.errorMessageEncoding = "Cp1252";
        this.hasTriedMasterFlag = false;
        this.statementComment = null;
        this.usingCachedConfig = false;
        this.autoIncrementIncrement = 0;
    }
    
    protected ConnectionImpl(final String hostToConnectTo, final int portToConnectTo, final Properties info, String databaseToConnectTo, final String url) throws SQLException {
        this.proxy = null;
        this.autoCommit = true;
        this.characterSetMetadata = null;
        this.characterSetResultsOnServer = null;
        this.charsetConverterMap = new HashMap(CharsetMapping.getNumberOfCharsetsConfigured());
        this.connectionCreationTimeMillis = 0L;
        this.database = null;
        this.dbmd = null;
        this.hasIsolationLevels = false;
        this.hasQuotedIdentifiers = false;
        this.host = null;
        this.indexToCharsetMapping = CharsetMapping.INDEX_TO_CHARSET;
        this.io = null;
        this.isClientTzUTC = false;
        this.isClosed = true;
        this.isInGlobalTx = false;
        this.isRunningOnJDK13 = false;
        this.isolationLevel = 2;
        this.isServerTzUTC = false;
        this.lastQueryFinishedTime = 0L;
        this.log = ConnectionImpl.NULL_LOGGER;
        this.longestQueryTimeMs = 0L;
        this.lowerCaseTableNames = false;
        this.masterFailTimeMillis = 0L;
        this.maximumNumberTablesAccessed = 0L;
        this.maxRowsChanged = false;
        this.minimumNumberTablesAccessed = Long.MAX_VALUE;
        this.mutex = new Object();
        this.myURL = null;
        this.needsPing = false;
        this.netBufferLength = 16384;
        this.noBackslashEscapes = false;
        this.numberOfPreparedExecutes = 0L;
        this.numberOfPrepares = 0L;
        this.numberOfQueriesIssued = 0L;
        this.numberOfResultSetsCreated = 0L;
        this.oldHistBreakpoints = null;
        this.oldHistCounts = null;
        this.parserKnowsUnicode = false;
        this.password = null;
        this.port = 3306;
        this.props = null;
        this.readInfoMsg = false;
        this.readOnly = false;
        this.serverTimezoneTZ = null;
        this.serverVariables = null;
        this.shortestQueryTimeMs = Long.MAX_VALUE;
        this.totalQueryTimeMs = 0.0;
        this.transactionsSupported = false;
        this.useAnsiQuotes = false;
        this.user = null;
        this.useServerPreparedStmts = false;
        this.errorMessageEncoding = "Cp1252";
        this.hasTriedMasterFlag = false;
        this.statementComment = null;
        this.usingCachedConfig = false;
        this.autoIncrementIncrement = 0;
        this.charsetToNumBytesMap = new HashMap();
        this.connectionCreationTimeMillis = System.currentTimeMillis();
        this.pointOfOrigin = new Throwable();
        if (databaseToConnectTo == null) {
            databaseToConnectTo = "";
        }
        this.origHostToConnectTo = hostToConnectTo;
        this.origPortToConnectTo = portToConnectTo;
        this.origDatabaseToConnectTo = databaseToConnectTo;
        try {
            Blob.class.getMethod("truncate", Long.TYPE);
            this.isRunningOnJDK13 = false;
        }
        catch (NoSuchMethodException nsme) {
            this.isRunningOnJDK13 = true;
        }
        this.sessionCalendar = new GregorianCalendar();
        (this.utcCalendar = new GregorianCalendar()).setTimeZone(TimeZone.getTimeZone("GMT"));
        this.log = LogFactory.getLogger(this.getLogger(), "MySQL", this.getExceptionInterceptor());
        this.defaultTimeZone = Util.getDefaultTimeZone();
        if ("GMT".equalsIgnoreCase(this.defaultTimeZone.getID())) {
            this.isClientTzUTC = true;
        }
        else {
            this.isClientTzUTC = false;
        }
        this.openStatements = new HashMap();
        this.serverVariables = new HashMap();
        if (NonRegisteringDriver.isHostPropertiesList(hostToConnectTo)) {
            final Properties hostSpecificProps = NonRegisteringDriver.expandHostKeyValues(hostToConnectTo);
            final Enumeration<?> propertyNames = hostSpecificProps.propertyNames();
            while (propertyNames.hasMoreElements()) {
                final String propertyName = propertyNames.nextElement().toString();
                final String propertyValue = hostSpecificProps.getProperty(propertyName);
                info.setProperty(propertyName, propertyValue);
            }
        }
        else if (hostToConnectTo == null) {
            this.host = "localhost";
            this.hostPortPair = this.host + ":" + portToConnectTo;
        }
        else {
            this.host = hostToConnectTo;
            if (hostToConnectTo.indexOf(":") == -1) {
                this.hostPortPair = this.host + ":" + portToConnectTo;
            }
            else {
                this.hostPortPair = this.host;
            }
        }
        this.port = portToConnectTo;
        this.database = databaseToConnectTo;
        this.myURL = url;
        this.user = info.getProperty("user");
        this.password = info.getProperty("password");
        if (this.user == null || this.user.equals("")) {
            this.user = "";
        }
        if (this.password == null) {
            this.password = "";
        }
        this.initializeDriverProperties(this.props = info);
        try {
            this.dbmd = this.getMetaData(false, false);
            this.initializeSafeStatementInterceptors();
            this.createNewIO(false);
            this.unSafeStatementInterceptors();
        }
        catch (SQLException ex) {
            this.cleanup(ex);
            throw ex;
        }
        catch (Exception ex2) {
            this.cleanup(ex2);
            final StringBuffer mesg = new StringBuffer(128);
            if (!this.getParanoid()) {
                mesg.append("Cannot connect to MySQL server on ");
                mesg.append(this.host);
                mesg.append(":");
                mesg.append(this.port);
                mesg.append(".\n\n");
                mesg.append("Make sure that there is a MySQL server ");
                mesg.append("running on the machine/port you are trying ");
                mesg.append("to connect to and that the machine this software is running on ");
                mesg.append("is able to connect to this host/port (i.e. not firewalled). ");
                mesg.append("Also make sure that the server has not been started with the --skip-networking ");
                mesg.append("flag.\n\n");
            }
            else {
                mesg.append("Unable to connect to database.");
            }
            final SQLException sqlEx = SQLError.createSQLException(mesg.toString(), "08S01", this.getExceptionInterceptor());
            sqlEx.initCause(ex2);
            throw sqlEx;
        }
    }
    
    public void unSafeStatementInterceptors() throws SQLException {
        final ArrayList unSafedStatementInterceptors = new ArrayList(this.statementInterceptors.size());
        for (int i = 0; i < this.statementInterceptors.size(); ++i) {
            final NoSubInterceptorWrapper wrappedInterceptor = this.statementInterceptors.get(i);
            unSafedStatementInterceptors.add(wrappedInterceptor.getUnderlyingInterceptor());
        }
        this.statementInterceptors = unSafedStatementInterceptors;
        if (this.io != null) {
            this.io.setStatementInterceptors(this.statementInterceptors);
        }
    }
    
    public void initializeSafeStatementInterceptors() throws SQLException {
        this.isClosed = false;
        final List unwrappedInterceptors = Util.loadExtensions(this, this.props, this.getStatementInterceptors(), "MysqlIo.BadStatementInterceptor", this.getExceptionInterceptor());
        this.statementInterceptors = new ArrayList(unwrappedInterceptors.size());
        for (int i = 0; i < unwrappedInterceptors.size(); ++i) {
            final Object interceptor = unwrappedInterceptors.get(i);
            if (interceptor instanceof StatementInterceptor) {
                if (ReflectiveStatementInterceptorAdapter.getV2PostProcessMethod(interceptor.getClass()) != null) {
                    this.statementInterceptors.add(new NoSubInterceptorWrapper(new ReflectiveStatementInterceptorAdapter((StatementInterceptor)interceptor)));
                }
                else {
                    this.statementInterceptors.add(new NoSubInterceptorWrapper(new V1toV2StatementInterceptorAdapter((StatementInterceptor)interceptor)));
                }
            }
            else {
                this.statementInterceptors.add(new NoSubInterceptorWrapper((StatementInterceptorV2)interceptor));
            }
        }
    }
    
    public List getStatementInterceptorsInstances() {
        return this.statementInterceptors;
    }
    
    private void addToHistogram(final int[] histogramCounts, final long[] histogramBreakpoints, final long value, final int numberOfTimes, final long currentLowerBound, final long currentUpperBound) {
        if (histogramCounts == null) {
            this.createInitialHistogram(histogramBreakpoints, currentLowerBound, currentUpperBound);
        }
        else {
            for (int i = 0; i < 20; ++i) {
                if (histogramBreakpoints[i] >= value) {
                    final int n = i;
                    histogramCounts[n] += numberOfTimes;
                    break;
                }
            }
        }
    }
    
    private void addToPerformanceHistogram(final long value, final int numberOfTimes) {
        this.checkAndCreatePerformanceHistogram();
        this.addToHistogram(this.perfMetricsHistCounts, this.perfMetricsHistBreakpoints, value, numberOfTimes, (this.shortestQueryTimeMs == Long.MAX_VALUE) ? 0L : this.shortestQueryTimeMs, this.longestQueryTimeMs);
    }
    
    private void addToTablesAccessedHistogram(final long value, final int numberOfTimes) {
        this.checkAndCreateTablesAccessedHistogram();
        this.addToHistogram(this.numTablesMetricsHistCounts, this.numTablesMetricsHistBreakpoints, value, numberOfTimes, (this.minimumNumberTablesAccessed == Long.MAX_VALUE) ? 0L : this.minimumNumberTablesAccessed, this.maximumNumberTablesAccessed);
    }
    
    private void buildCollationMapping() throws SQLException {
        if (this.versionMeetsMinimum(4, 1, 0)) {
            TreeMap sortedCollationMap = null;
            if (this.getCacheServerConfiguration()) {
                synchronized (ConnectionImpl.serverConfigByUrl) {
                    sortedCollationMap = ConnectionImpl.serverCollationByUrl.get(this.getURL());
                }
            }
            Statement stmt = null;
            ResultSet results = null;
            try {
                if (sortedCollationMap == null) {
                    sortedCollationMap = new TreeMap();
                    stmt = this.getMetadataSafeStatement();
                    results = stmt.executeQuery("SHOW COLLATION");
                    while (results.next()) {
                        final String charsetName = results.getString(2);
                        final Integer charsetIndex = Constants.integerValueOf(results.getInt(3));
                        sortedCollationMap.put(charsetIndex, charsetName);
                    }
                    if (this.getCacheServerConfiguration()) {
                        synchronized (ConnectionImpl.serverConfigByUrl) {
                            ConnectionImpl.serverCollationByUrl.put(this.getURL(), sortedCollationMap);
                        }
                    }
                }
                int highestIndex = sortedCollationMap.lastKey();
                if (CharsetMapping.INDEX_TO_CHARSET.length > highestIndex) {
                    highestIndex = CharsetMapping.INDEX_TO_CHARSET.length;
                }
                this.indexToCharsetMapping = new String[highestIndex + 1];
                for (int i = 0; i < CharsetMapping.INDEX_TO_CHARSET.length; ++i) {
                    this.indexToCharsetMapping[i] = CharsetMapping.INDEX_TO_CHARSET[i];
                }
                for (final Map.Entry indexEntry : sortedCollationMap.entrySet()) {
                    final String mysqlCharsetName = indexEntry.getValue();
                    this.indexToCharsetMapping[indexEntry.getKey()] = CharsetMapping.getJavaEncodingForMysqlEncoding(mysqlCharsetName, this);
                }
            }
            catch (SQLException e) {
                throw e;
            }
            finally {
                if (results != null) {
                    try {
                        results.close();
                    }
                    catch (SQLException ex) {}
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    }
                    catch (SQLException ex2) {}
                }
            }
        }
        else {
            this.indexToCharsetMapping = CharsetMapping.INDEX_TO_CHARSET;
        }
    }
    
    private boolean canHandleAsServerPreparedStatement(final String sql) throws SQLException {
        if (sql == null || sql.length() == 0) {
            return true;
        }
        if (!this.useServerPreparedStmts) {
            return false;
        }
        if (this.getCachePreparedStatements()) {
            synchronized (this.serverSideStatementCheckCache) {
                final Boolean flag = this.serverSideStatementCheckCache.get(sql);
                if (flag != null) {
                    return flag;
                }
                final boolean canHandle = this.canHandleAsServerPreparedStatementNoCache(sql);
                if (sql.length() < this.getPreparedStatementCacheSqlLimit()) {
                    this.serverSideStatementCheckCache.put(sql, canHandle ? Boolean.TRUE : Boolean.FALSE);
                }
                return canHandle;
            }
        }
        return this.canHandleAsServerPreparedStatementNoCache(sql);
    }
    
    private boolean canHandleAsServerPreparedStatementNoCache(final String sql) throws SQLException {
        if (StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "CALL")) {
            return false;
        }
        boolean canHandleAsStatement = true;
        if (!this.versionMeetsMinimum(5, 0, 7) && (StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "SELECT") || StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "DELETE") || StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "INSERT") || StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "UPDATE") || StringUtils.startsWithIgnoreCaseAndNonAlphaNumeric(sql, "REPLACE"))) {
            int currentPos = 0;
            final int statementLength = sql.length();
            final int lastPosToLook = statementLength - 7;
            final boolean allowBackslashEscapes = !this.noBackslashEscapes;
            final char quoteChar = this.useAnsiQuotes ? '\"' : '\'';
            boolean foundLimitWithPlaceholder = false;
            while (currentPos < lastPosToLook) {
                final int limitStart = StringUtils.indexOfIgnoreCaseRespectQuotes(currentPos, sql, "LIMIT ", quoteChar, allowBackslashEscapes);
                if (limitStart == -1) {
                    break;
                }
                for (currentPos = limitStart + 7; currentPos < statementLength; ++currentPos) {
                    final char c = sql.charAt(currentPos);
                    if (!Character.isDigit(c) && !Character.isWhitespace(c) && c != ',' && c != '?') {
                        break;
                    }
                    if (c == '?') {
                        foundLimitWithPlaceholder = true;
                        break;
                    }
                }
            }
            canHandleAsStatement = !foundLimitWithPlaceholder;
        }
        else if (StringUtils.startsWithIgnoreCaseAndWs(sql, "CREATE TABLE")) {
            canHandleAsStatement = false;
        }
        else if (StringUtils.startsWithIgnoreCaseAndWs(sql, "DO")) {
            canHandleAsStatement = false;
        }
        else if (StringUtils.startsWithIgnoreCaseAndWs(sql, "SET")) {
            canHandleAsStatement = false;
        }
        return canHandleAsStatement;
    }
    
    public void changeUser(String userName, String newPassword) throws SQLException {
        if (userName == null || userName.equals("")) {
            userName = "";
        }
        if (newPassword == null) {
            newPassword = "";
        }
        this.io.changeUser(userName, newPassword, this.database);
        this.user = userName;
        this.password = newPassword;
        if (this.versionMeetsMinimum(4, 1, 0)) {
            this.configureClientCharacterSet(true);
        }
        this.setSessionVariables();
        this.setupServerForTruncationChecks();
    }
    
    private boolean characterSetNamesMatches(final String mysqlEncodingName) {
        return mysqlEncodingName != null && mysqlEncodingName.equalsIgnoreCase(this.serverVariables.get("character_set_client")) && mysqlEncodingName.equalsIgnoreCase(this.serverVariables.get("character_set_connection"));
    }
    
    private void checkAndCreatePerformanceHistogram() {
        if (this.perfMetricsHistCounts == null) {
            this.perfMetricsHistCounts = new int[20];
        }
        if (this.perfMetricsHistBreakpoints == null) {
            this.perfMetricsHistBreakpoints = new long[20];
        }
    }
    
    private void checkAndCreateTablesAccessedHistogram() {
        if (this.numTablesMetricsHistCounts == null) {
            this.numTablesMetricsHistCounts = new int[20];
        }
        if (this.numTablesMetricsHistBreakpoints == null) {
            this.numTablesMetricsHistBreakpoints = new long[20];
        }
    }
    
    public void checkClosed() throws SQLException {
        if (this.isClosed) {
            this.throwConnectionClosedException();
        }
    }
    
    public void throwConnectionClosedException() throws SQLException {
        final StringBuffer messageBuf = new StringBuffer("No operations allowed after connection closed.");
        if (this.forcedClosedLocation != null || this.forceClosedReason != null) {
            messageBuf.append("Connection was implicitly closed by the driver.");
        }
        final SQLException ex = SQLError.createSQLException(messageBuf.toString(), "08003", this.getExceptionInterceptor());
        if (this.forceClosedReason != null) {
            ex.initCause(this.forceClosedReason);
        }
        throw ex;
    }
    
    private void checkServerEncoding() throws SQLException {
        if (this.getUseUnicode() && this.getEncoding() != null) {
            return;
        }
        String serverEncoding = this.serverVariables.get("character_set");
        if (serverEncoding == null) {
            serverEncoding = this.serverVariables.get("character_set_server");
        }
        String mappedServerEncoding = null;
        if (serverEncoding != null) {
            mappedServerEncoding = CharsetMapping.getJavaEncodingForMysqlEncoding(serverEncoding.toUpperCase(Locale.ENGLISH), this);
        }
        if (!this.getUseUnicode() && mappedServerEncoding != null) {
            final SingleByteCharsetConverter converter = this.getCharsetConverter(mappedServerEncoding);
            if (converter != null) {
                this.setUseUnicode(true);
                this.setEncoding(mappedServerEncoding);
                return;
            }
        }
        if (serverEncoding != null) {
            if (mappedServerEncoding == null && Character.isLowerCase(serverEncoding.charAt(0))) {
                final char[] ach = serverEncoding.toCharArray();
                ach[0] = Character.toUpperCase(serverEncoding.charAt(0));
                this.setEncoding(new String(ach));
            }
            if (mappedServerEncoding == null) {
                throw SQLError.createSQLException("Unknown character encoding on server '" + serverEncoding + "', use 'characterEncoding=' property " + " to provide correct mapping", "01S00", this.getExceptionInterceptor());
            }
            try {
                "abc".getBytes(mappedServerEncoding);
                this.setEncoding(mappedServerEncoding);
                this.setUseUnicode(true);
            }
            catch (UnsupportedEncodingException UE) {
                throw SQLError.createSQLException("The driver can not map the character encoding '" + this.getEncoding() + "' that your server is using " + "to a character encoding your JVM understands. You " + "can specify this mapping manually by adding \"useUnicode=true\" " + "as well as \"characterEncoding=[an_encoding_your_jvm_understands]\" " + "to your JDBC URL.", "0S100", this.getExceptionInterceptor());
            }
        }
    }
    
    private void checkTransactionIsolationLevel() throws SQLException {
        String txIsolationName = null;
        if (this.versionMeetsMinimum(4, 0, 3)) {
            txIsolationName = "tx_isolation";
        }
        else {
            txIsolationName = "transaction_isolation";
        }
        final String s = this.serverVariables.get(txIsolationName);
        if (s != null) {
            final Integer intTI = ConnectionImpl.mapTransIsolationNameToValue.get(s);
            if (intTI != null) {
                this.isolationLevel = intTI;
            }
        }
    }
    
    public void abortInternal() throws SQLException {
        if (this.io != null) {
            try {
                this.io.forceClose();
            }
            catch (Throwable t) {}
            this.io = null;
        }
        this.isClosed = true;
    }
    
    private void cleanup(final Throwable whyCleanedUp) {
        try {
            if (this.io != null && !this.isClosed()) {
                this.realClose(false, false, false, whyCleanedUp);
            }
            else if (this.io != null) {
                this.io.forceClose();
            }
        }
        catch (SQLException ex) {}
        this.isClosed = true;
    }
    
    public void clearHasTriedMaster() {
        this.hasTriedMasterFlag = false;
    }
    
    public void clearWarnings() throws SQLException {
    }
    
    public PreparedStatement clientPrepareStatement(final String sql) throws SQLException {
        return this.clientPrepareStatement(sql, 1003, 1007);
    }
    
    public PreparedStatement clientPrepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        final PreparedStatement pStmt = this.clientPrepareStatement(sql);
        ((com.mysql.jdbc.PreparedStatement)pStmt).setRetrieveGeneratedKeys(autoGenKeyIndex == 1);
        return pStmt;
    }
    
    public PreparedStatement clientPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        return this.clientPrepareStatement(sql, resultSetType, resultSetConcurrency, true);
    }
    
    public PreparedStatement clientPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final boolean processEscapeCodesIfNeeded) throws SQLException {
        this.checkClosed();
        final String nativeSql = (processEscapeCodesIfNeeded && this.getProcessEscapeCodesForPrepStmts()) ? this.nativeSQL(sql) : sql;
        com.mysql.jdbc.PreparedStatement pStmt = null;
        if (this.getCachePreparedStatements()) {
            synchronized (this.cachedPreparedStatementParams) {
                final com.mysql.jdbc.PreparedStatement.ParseInfo pStmtInfo = this.cachedPreparedStatementParams.get(nativeSql);
                if (pStmtInfo == null) {
                    pStmt = com.mysql.jdbc.PreparedStatement.getInstance(this.getLoadBalanceSafeProxy(), nativeSql, this.database);
                    final com.mysql.jdbc.PreparedStatement.ParseInfo parseInfo = pStmt.getParseInfo();
                    if (parseInfo.statementLength < this.getPreparedStatementCacheSqlLimit()) {
                        if (this.cachedPreparedStatementParams.size() >= this.getPreparedStatementCacheSize()) {
                            final Iterator oldestIter = this.cachedPreparedStatementParams.keySet().iterator();
                            long lruTime = Long.MAX_VALUE;
                            String oldestSql = null;
                            while (oldestIter.hasNext()) {
                                final String sqlKey = oldestIter.next();
                                final com.mysql.jdbc.PreparedStatement.ParseInfo lruInfo = this.cachedPreparedStatementParams.get(sqlKey);
                                if (lruInfo.lastUsed < lruTime) {
                                    lruTime = lruInfo.lastUsed;
                                    oldestSql = sqlKey;
                                }
                            }
                            if (oldestSql != null) {
                                this.cachedPreparedStatementParams.remove(oldestSql);
                            }
                        }
                        this.cachedPreparedStatementParams.put(nativeSql, pStmt.getParseInfo());
                    }
                }
                else {
                    pStmtInfo.lastUsed = System.currentTimeMillis();
                    pStmt = new com.mysql.jdbc.PreparedStatement(this.getLoadBalanceSafeProxy(), nativeSql, this.database, pStmtInfo);
                }
            }
        }
        else {
            pStmt = com.mysql.jdbc.PreparedStatement.getInstance(this.getLoadBalanceSafeProxy(), nativeSql, this.database);
        }
        pStmt.setResultSetType(resultSetType);
        pStmt.setResultSetConcurrency(resultSetConcurrency);
        return pStmt;
    }
    
    public PreparedStatement clientPrepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        final com.mysql.jdbc.PreparedStatement pStmt = (com.mysql.jdbc.PreparedStatement)this.clientPrepareStatement(sql);
        pStmt.setRetrieveGeneratedKeys(autoGenKeyIndexes != null && autoGenKeyIndexes.length > 0);
        return pStmt;
    }
    
    public PreparedStatement clientPrepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        final com.mysql.jdbc.PreparedStatement pStmt = (com.mysql.jdbc.PreparedStatement)this.clientPrepareStatement(sql);
        pStmt.setRetrieveGeneratedKeys(autoGenKeyColNames != null && autoGenKeyColNames.length > 0);
        return pStmt;
    }
    
    public PreparedStatement clientPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        return this.clientPrepareStatement(sql, resultSetType, resultSetConcurrency, true);
    }
    
    public synchronized void close() throws SQLException {
        if (this.connectionLifecycleInterceptors != null) {
            new IterateBlock(this.connectionLifecycleInterceptors.iterator()) {
                void forEach(final Object each) throws SQLException {
                    ((ConnectionLifecycleInterceptor)each).close();
                }
            }.doForAll();
        }
        this.realClose(true, true, false, null);
    }
    
    private void closeAllOpenStatements() throws SQLException {
        SQLException postponedException = null;
        if (this.openStatements != null) {
            final List currentlyOpenStatements = new ArrayList();
            final Iterator iter = this.openStatements.keySet().iterator();
            while (iter.hasNext()) {
                currentlyOpenStatements.add(iter.next());
            }
            for (int numStmts = currentlyOpenStatements.size(), i = 0; i < numStmts; ++i) {
                final StatementImpl stmt = currentlyOpenStatements.get(i);
                try {
                    stmt.realClose(false, true);
                }
                catch (SQLException sqlEx) {
                    postponedException = sqlEx;
                }
            }
            if (postponedException != null) {
                throw postponedException;
            }
        }
    }
    
    private void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            }
            catch (SQLException ex) {}
            stmt = null;
        }
    }
    
    public void commit() throws SQLException {
        synchronized (this.getMutex()) {
            this.checkClosed();
            try {
                if (this.connectionLifecycleInterceptors != null) {
                    final IterateBlock iter = new IterateBlock(this.connectionLifecycleInterceptors.iterator()) {
                        void forEach(final Object each) throws SQLException {
                            if (!((ConnectionLifecycleInterceptor)each).commit()) {
                                this.stopIterating = true;
                            }
                        }
                    };
                    iter.doForAll();
                    if (!iter.fullIteration()) {
                        return;
                    }
                }
                if (this.autoCommit && !this.getRelaxAutoCommit()) {
                    throw SQLError.createSQLException("Can't call commit when autocommit=true", this.getExceptionInterceptor());
                }
                if (this.transactionsSupported) {
                    if (this.getUseLocalTransactionState() && this.versionMeetsMinimum(5, 0, 0) && !this.io.inTransactionOnServer()) {
                        return;
                    }
                    this.execSQL(null, "commit", -1, null, 1003, 1007, false, this.database, null, false);
                }
            }
            catch (SQLException sqlException) {
                if ("08S01".equals(sqlException.getSQLState())) {
                    throw SQLError.createSQLException("Communications link failure during commit(). Transaction resolution unknown.", "08007", this.getExceptionInterceptor());
                }
                throw sqlException;
            }
            finally {
                this.needsPing = this.getReconnectAtTxEnd();
            }
        }
    }
    
    private void configureCharsetProperties() throws SQLException {
        if (this.getEncoding() != null) {
            try {
                final String testString = "abc";
                testString.getBytes(this.getEncoding());
            }
            catch (UnsupportedEncodingException UE) {
                final String oldEncoding = this.getEncoding();
                this.setEncoding(CharsetMapping.getJavaEncodingForMysqlEncoding(oldEncoding, this));
                if (this.getEncoding() == null) {
                    throw SQLError.createSQLException("Java does not support the MySQL character encoding  encoding '" + oldEncoding + "'.", "01S00", this.getExceptionInterceptor());
                }
                try {
                    final String testString2 = "abc";
                    testString2.getBytes(this.getEncoding());
                }
                catch (UnsupportedEncodingException encodingEx) {
                    throw SQLError.createSQLException("Unsupported character encoding '" + this.getEncoding() + "'.", "01S00", this.getExceptionInterceptor());
                }
            }
        }
    }
    
    private boolean configureClientCharacterSet(final boolean dontCheckServerMatch) throws SQLException {
        String realJavaEncoding = this.getEncoding();
        boolean characterSetAlreadyConfigured = false;
        try {
            if (this.versionMeetsMinimum(4, 1, 0)) {
                characterSetAlreadyConfigured = true;
                this.setUseUnicode(true);
                this.configureCharsetProperties();
                realJavaEncoding = this.getEncoding();
                try {
                    if (this.props != null && this.props.getProperty("com.mysql.jdbc.faultInjection.serverCharsetIndex") != null) {
                        this.io.serverCharsetIndex = Integer.parseInt(this.props.getProperty("com.mysql.jdbc.faultInjection.serverCharsetIndex"));
                    }
                    String serverEncodingToSet = CharsetMapping.INDEX_TO_CHARSET[this.io.serverCharsetIndex];
                    if (serverEncodingToSet == null || serverEncodingToSet.length() == 0) {
                        if (realJavaEncoding == null) {
                            throw SQLError.createSQLException("Unknown initial character set index '" + this.io.serverCharsetIndex + "' received from server. Initial client character set can be forced via the 'characterEncoding' property.", "S1000", this.getExceptionInterceptor());
                        }
                        this.setEncoding(realJavaEncoding);
                    }
                    if (this.versionMeetsMinimum(4, 1, 0) && "ISO8859_1".equalsIgnoreCase(serverEncodingToSet)) {
                        serverEncodingToSet = "Cp1252";
                    }
                    this.setEncoding(serverEncodingToSet);
                }
                catch (ArrayIndexOutOfBoundsException outOfBoundsEx) {
                    if (realJavaEncoding == null) {
                        throw SQLError.createSQLException("Unknown initial character set index '" + this.io.serverCharsetIndex + "' received from server. Initial client character set can be forced via the 'characterEncoding' property.", "S1000", this.getExceptionInterceptor());
                    }
                    this.setEncoding(realJavaEncoding);
                }
                if (this.getEncoding() == null) {
                    this.setEncoding("ISO8859_1");
                }
                if (this.getUseUnicode()) {
                    if (realJavaEncoding != null) {
                        if (realJavaEncoding.equalsIgnoreCase("UTF-8") || realJavaEncoding.equalsIgnoreCase("UTF8")) {
                            final boolean utf8mb4Supported = this.versionMeetsMinimum(5, 5, 2);
                            boolean useutf8mb4 = false;
                            if (utf8mb4Supported) {
                                useutf8mb4 = (this.io.serverCharsetIndex == 45 && this.io.serverCharsetIndex == 45);
                            }
                            if (!this.getUseOldUTF8Behavior()) {
                                if (dontCheckServerMatch || !this.characterSetNamesMatches("utf8") || (utf8mb4Supported && !this.characterSetNamesMatches("utf8mb4"))) {
                                    this.execSQL(null, "SET NAMES " + (useutf8mb4 ? "utf8mb4" : "utf8"), -1, null, 1003, 1007, false, this.database, null, false);
                                }
                            }
                            else {
                                this.execSQL(null, "SET NAMES latin1", -1, null, 1003, 1007, false, this.database, null, false);
                            }
                            this.setEncoding(realJavaEncoding);
                        }
                        else {
                            final String mysqlEncodingName = CharsetMapping.getMysqlEncodingForJavaEncoding(realJavaEncoding.toUpperCase(Locale.ENGLISH), this);
                            if (mysqlEncodingName != null && (dontCheckServerMatch || !this.characterSetNamesMatches(mysqlEncodingName))) {
                                this.execSQL(null, "SET NAMES " + mysqlEncodingName, -1, null, 1003, 1007, false, this.database, null, false);
                            }
                            this.setEncoding(realJavaEncoding);
                        }
                    }
                    else if (this.getEncoding() != null) {
                        String mysqlEncodingName = CharsetMapping.getMysqlEncodingForJavaEncoding(this.getEncoding().toUpperCase(Locale.ENGLISH), this);
                        if (this.getUseOldUTF8Behavior()) {
                            mysqlEncodingName = "latin1";
                        }
                        if (dontCheckServerMatch || !this.characterSetNamesMatches(mysqlEncodingName)) {
                            this.execSQL(null, "SET NAMES " + mysqlEncodingName, -1, null, 1003, 1007, false, this.database, null, false);
                        }
                        realJavaEncoding = this.getEncoding();
                    }
                }
                String onServer = null;
                boolean isNullOnServer = false;
                if (this.serverVariables != null) {
                    onServer = this.serverVariables.get("character_set_results");
                    isNullOnServer = (onServer == null || "NULL".equalsIgnoreCase(onServer) || onServer.length() == 0);
                }
                if (this.getCharacterSetResults() == null) {
                    if (!isNullOnServer) {
                        this.execSQL(null, "SET character_set_results = NULL", -1, null, 1003, 1007, false, this.database, null, false);
                        if (!this.usingCachedConfig) {
                            this.serverVariables.put("jdbc.local.character_set_results", null);
                        }
                    }
                    else if (!this.usingCachedConfig) {
                        this.serverVariables.put("jdbc.local.character_set_results", onServer);
                    }
                }
                else {
                    if (this.getUseOldUTF8Behavior()) {
                        this.execSQL(null, "SET NAMES latin1", -1, null, 1003, 1007, false, this.database, null, false);
                    }
                    final String charsetResults = this.getCharacterSetResults();
                    String mysqlEncodingName2 = null;
                    if ("UTF-8".equalsIgnoreCase(charsetResults) || "UTF8".equalsIgnoreCase(charsetResults)) {
                        mysqlEncodingName2 = "utf8";
                    }
                    else {
                        mysqlEncodingName2 = CharsetMapping.getMysqlEncodingForJavaEncoding(charsetResults.toUpperCase(Locale.ENGLISH), this);
                    }
                    if (!mysqlEncodingName2.equalsIgnoreCase(this.serverVariables.get("character_set_results"))) {
                        final StringBuffer setBuf = new StringBuffer("SET character_set_results = ".length() + mysqlEncodingName2.length());
                        setBuf.append("SET character_set_results = ").append(mysqlEncodingName2);
                        this.execSQL(null, setBuf.toString(), -1, null, 1003, 1007, false, this.database, null, false);
                        if (!this.usingCachedConfig) {
                            this.serverVariables.put("jdbc.local.character_set_results", mysqlEncodingName2);
                        }
                    }
                    else if (!this.usingCachedConfig) {
                        this.serverVariables.put("jdbc.local.character_set_results", onServer);
                    }
                }
                if (this.getConnectionCollation() != null) {
                    final StringBuffer setBuf2 = new StringBuffer("SET collation_connection = ".length() + this.getConnectionCollation().length());
                    setBuf2.append("SET collation_connection = ").append(this.getConnectionCollation());
                    this.execSQL(null, setBuf2.toString(), -1, null, 1003, 1007, false, this.database, null, false);
                }
            }
            else {
                realJavaEncoding = this.getEncoding();
            }
        }
        finally {
            this.setEncoding(realJavaEncoding);
        }
        try {
            final CharsetEncoder enc = Charset.forName(this.getEncoding()).newEncoder();
            final CharBuffer cbuf = CharBuffer.allocate(1);
            final ByteBuffer bbuf = ByteBuffer.allocate(1);
            cbuf.put("¥");
            cbuf.position(0);
            enc.encode(cbuf, bbuf, true);
            if (bbuf.get(0) == 92) {
                this.requiresEscapingEncoder = true;
            }
            else {
                cbuf.clear();
                bbuf.clear();
                cbuf.put("\u20a9");
                cbuf.position(0);
                enc.encode(cbuf, bbuf, true);
                if (bbuf.get(0) == 92) {
                    this.requiresEscapingEncoder = true;
                }
            }
        }
        catch (UnsupportedCharsetException ucex) {
            try {
                byte[] bbuf2 = new String("¥").getBytes(this.getEncoding());
                if (bbuf2[0] == 92) {
                    this.requiresEscapingEncoder = true;
                }
                else {
                    bbuf2 = new String("\u20a9").getBytes(this.getEncoding());
                    if (bbuf2[0] == 92) {
                        this.requiresEscapingEncoder = true;
                    }
                }
            }
            catch (UnsupportedEncodingException ueex) {
                throw SQLError.createSQLException("Unable to use encoding: " + this.getEncoding(), "S1000", ueex, this.getExceptionInterceptor());
            }
        }
        return characterSetAlreadyConfigured;
    }
    
    private void configureTimezone() throws SQLException {
        String configuredTimeZoneOnServer = this.serverVariables.get("timezone");
        if (configuredTimeZoneOnServer == null) {
            configuredTimeZoneOnServer = this.serverVariables.get("time_zone");
            if ("SYSTEM".equalsIgnoreCase(configuredTimeZoneOnServer)) {
                configuredTimeZoneOnServer = this.serverVariables.get("system_time_zone");
            }
        }
        String canoncicalTimezone = this.getServerTimezone();
        Label_0176: {
            if ((this.getUseTimezone() || !this.getUseLegacyDatetimeCode()) && configuredTimeZoneOnServer != null) {
                if (canoncicalTimezone != null) {
                    if (!StringUtils.isEmptyOrWhitespaceOnly(canoncicalTimezone)) {
                        break Label_0176;
                    }
                }
                try {
                    canoncicalTimezone = TimeUtil.getCanoncialTimezone(configuredTimeZoneOnServer, this.getExceptionInterceptor());
                    if (canoncicalTimezone == null) {
                        throw SQLError.createSQLException("Can't map timezone '" + configuredTimeZoneOnServer + "' to " + " canonical timezone.", "S1009", this.getExceptionInterceptor());
                    }
                    break Label_0176;
                }
                catch (IllegalArgumentException iae) {
                    throw SQLError.createSQLException(iae.getMessage(), "S1000", this.getExceptionInterceptor());
                }
            }
            canoncicalTimezone = this.getServerTimezone();
        }
        if (canoncicalTimezone != null && canoncicalTimezone.length() > 0) {
            this.serverTimezoneTZ = TimeZone.getTimeZone(canoncicalTimezone);
            if (!canoncicalTimezone.equalsIgnoreCase("GMT") && this.serverTimezoneTZ.getID().equals("GMT")) {
                throw SQLError.createSQLException("No timezone mapping entry for '" + canoncicalTimezone + "'", "S1009", this.getExceptionInterceptor());
            }
            if ("GMT".equalsIgnoreCase(this.serverTimezoneTZ.getID())) {
                this.isServerTzUTC = true;
            }
            else {
                this.isServerTzUTC = false;
            }
        }
    }
    
    private void createInitialHistogram(final long[] breakpoints, long lowerBound, final long upperBound) {
        double bucketSize = (upperBound - lowerBound) / 20.0 * 1.25;
        if (bucketSize < 1.0) {
            bucketSize = 1.0;
        }
        for (int i = 0; i < 20; ++i) {
            breakpoints[i] = lowerBound;
            lowerBound += (long)bucketSize;
        }
    }
    
    public void createNewIO(final boolean isForReconnect) throws SQLException {
        synchronized (this.mutex) {
            final Properties mergedProps = this.exposeAsProperties(this.props);
            if (!this.getHighAvailability()) {
                this.connectOneTryOnly(isForReconnect, mergedProps);
                return;
            }
            this.connectWithRetries(isForReconnect, mergedProps);
        }
    }
    
    private void connectWithRetries(final boolean isForReconnect, final Properties mergedProps) throws SQLException {
        final double timeout = this.getInitialTimeout();
        boolean connectionGood = false;
        Exception connectionException = null;
        int attemptCount = 0;
        while (attemptCount < this.getMaxReconnects() && !connectionGood) {
            try {
                if (this.io != null) {
                    this.io.forceClose();
                }
                this.coreConnect(mergedProps);
                this.pingInternal(false, 0);
                this.connectionId = this.io.getThreadId();
                this.isClosed = false;
                final boolean oldAutoCommit = this.getAutoCommit();
                final int oldIsolationLevel = this.isolationLevel;
                final boolean oldReadOnly = this.isReadOnly();
                final String oldCatalog = this.getCatalog();
                this.io.setStatementInterceptors(this.statementInterceptors);
                this.initializePropsFromServer();
                if (isForReconnect) {
                    this.setAutoCommit(oldAutoCommit);
                    if (this.hasIsolationLevels) {
                        this.setTransactionIsolation(oldIsolationLevel);
                    }
                    this.setCatalog(oldCatalog);
                    this.setReadOnly(oldReadOnly);
                }
                connectionGood = true;
            }
            catch (Exception EEE) {
                connectionException = EEE;
                connectionGood = false;
                if (!connectionGood) {
                    if (attemptCount > 0) {
                        try {
                            Thread.sleep((long)timeout * 1000L);
                        }
                        catch (InterruptedException ex) {}
                    }
                    ++attemptCount;
                    continue;
                }
            }
            break;
        }
        if (!connectionGood) {
            final SQLException chainedEx = SQLError.createSQLException(Messages.getString("Connection.UnableToConnectWithRetries", new Object[] { new Integer(this.getMaxReconnects()) }), "08001", this.getExceptionInterceptor());
            chainedEx.initCause(connectionException);
            throw chainedEx;
        }
        if (this.getParanoid() && !this.getHighAvailability()) {
            this.password = null;
            this.user = null;
        }
        if (isForReconnect) {
            final Iterator statementIter = this.openStatements.values().iterator();
            Stack serverPreparedStatements = null;
            while (statementIter.hasNext()) {
                final Object statementObj = statementIter.next();
                if (statementObj instanceof ServerPreparedStatement) {
                    if (serverPreparedStatements == null) {
                        serverPreparedStatements = new Stack();
                    }
                    serverPreparedStatements.add(statementObj);
                }
            }
            if (serverPreparedStatements != null) {
                while (!serverPreparedStatements.isEmpty()) {
                    serverPreparedStatements.pop().rePrepare();
                }
            }
        }
    }
    
    private void coreConnect(final Properties mergedProps) throws SQLException, IOException {
        int newPort = 3306;
        String newHost = "localhost";
        final String protocol = mergedProps.getProperty("PROTOCOL");
        if (protocol != null) {
            if ("tcp".equalsIgnoreCase(protocol)) {
                newHost = this.normalizeHost(mergedProps.getProperty("HOST"));
                newPort = this.parsePortNumber(mergedProps.getProperty("PORT", "3306"));
            }
            else if ("pipe".equalsIgnoreCase(protocol)) {
                this.setSocketFactoryClassName(NamedPipeSocketFactory.class.getName());
                final String path = mergedProps.getProperty("PATH");
                if (path != null) {
                    mergedProps.setProperty("namedPipePath", path);
                }
            }
            else {
                newHost = this.normalizeHost(mergedProps.getProperty("HOST"));
                newPort = this.parsePortNumber(mergedProps.getProperty("PORT", "3306"));
            }
        }
        else {
            final String[] parsedHostPortPair = NonRegisteringDriver.parseHostPortPair(this.hostPortPair);
            newHost = parsedHostPortPair[0];
            newHost = this.normalizeHost(newHost);
            if (parsedHostPortPair[1] != null) {
                newPort = this.parsePortNumber(parsedHostPortPair[1]);
            }
        }
        this.port = newPort;
        this.host = newHost;
        (this.io = new MysqlIO(newHost, newPort, mergedProps, this.getSocketFactoryClassName(), this.getProxy(), this.getSocketTimeout(), this.largeRowSizeThreshold.getValueAsInt())).doHandshake(this.user, this.password, this.database);
    }
    
    private String normalizeHost(final String host) {
        if (host == null || StringUtils.isEmptyOrWhitespaceOnly(host)) {
            return "localhost";
        }
        return host;
    }
    
    private int parsePortNumber(final String portAsString) throws SQLException {
        int portNumber = 3306;
        try {
            portNumber = Integer.parseInt(portAsString);
        }
        catch (NumberFormatException nfe) {
            throw SQLError.createSQLException("Illegal connection port value '" + portAsString + "'", "01S00", this.getExceptionInterceptor());
        }
        return portNumber;
    }
    
    private void connectOneTryOnly(final boolean isForReconnect, final Properties mergedProps) throws SQLException {
        Exception connectionNotEstablishedBecause = null;
        try {
            this.coreConnect(mergedProps);
            this.connectionId = this.io.getThreadId();
            this.isClosed = false;
            final boolean oldAutoCommit = this.getAutoCommit();
            final int oldIsolationLevel = this.isolationLevel;
            final boolean oldReadOnly = this.isReadOnly();
            final String oldCatalog = this.getCatalog();
            this.io.setStatementInterceptors(this.statementInterceptors);
            this.initializePropsFromServer();
            if (isForReconnect) {
                this.setAutoCommit(oldAutoCommit);
                if (this.hasIsolationLevels) {
                    this.setTransactionIsolation(oldIsolationLevel);
                }
                this.setCatalog(oldCatalog);
                this.setReadOnly(oldReadOnly);
            }
        }
        catch (Exception EEE) {
            if (this.io != null) {
                this.io.forceClose();
            }
            connectionNotEstablishedBecause = EEE;
            if (EEE instanceof SQLException) {
                throw (SQLException)EEE;
            }
            final SQLException chainedEx = SQLError.createSQLException(Messages.getString("Connection.UnableToConnect"), "08001", this.getExceptionInterceptor());
            chainedEx.initCause(connectionNotEstablishedBecause);
            throw chainedEx;
        }
    }
    
    private void createPreparedStatementCaches() {
        final int cacheSize = this.getPreparedStatementCacheSize();
        this.cachedPreparedStatementParams = new HashMap(cacheSize);
        if (this.getUseServerPreparedStmts()) {
            this.serverSideStatementCheckCache = new LRUCache(cacheSize);
            this.serverSideStatementCache = new LRUCache(cacheSize) {
                protected boolean removeEldestEntry(final Map.Entry eldest) {
                    if (this.maxElements <= 1) {
                        return false;
                    }
                    final boolean removeIt = super.removeEldestEntry(eldest);
                    if (removeIt) {
                        final ServerPreparedStatement ps = eldest.getValue();
                        ps.setClosed(ps.isCached = false);
                        try {
                            ps.close();
                        }
                        catch (SQLException ex) {}
                    }
                    return removeIt;
                }
            };
        }
    }
    
    public Statement createStatement() throws SQLException {
        return this.createStatement(1003, 1007);
    }
    
    public Statement createStatement(final int resultSetType, final int resultSetConcurrency) throws SQLException {
        this.checkClosed();
        final StatementImpl stmt = new StatementImpl(this.getLoadBalanceSafeProxy(), this.database);
        stmt.setResultSetType(resultSetType);
        stmt.setResultSetConcurrency(resultSetConcurrency);
        return stmt;
    }
    
    public Statement createStatement(final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        if (this.getPedantic() && resultSetHoldability != 1) {
            throw SQLError.createSQLException("HOLD_CUSRORS_OVER_COMMIT is only supported holdability level", "S1009", this.getExceptionInterceptor());
        }
        return this.createStatement(resultSetType, resultSetConcurrency);
    }
    
    public void dumpTestcaseQuery(final String query) {
        System.err.println(query);
    }
    
    public Connection duplicate() throws SQLException {
        return new ConnectionImpl(this.origHostToConnectTo, this.origPortToConnectTo, this.props, this.origDatabaseToConnectTo, this.myURL);
    }
    
    public ResultSetInternalMethods execSQL(final StatementImpl callingStatement, final String sql, final int maxRows, final Buffer packet, final int resultSetType, final int resultSetConcurrency, final boolean streamResults, final String catalog, final Field[] cachedMetadata) throws SQLException {
        return this.execSQL(callingStatement, sql, maxRows, packet, resultSetType, resultSetConcurrency, streamResults, catalog, cachedMetadata, false);
    }
    
    public ResultSetInternalMethods execSQL(final StatementImpl callingStatement, final String sql, final int maxRows, final Buffer packet, final int resultSetType, final int resultSetConcurrency, final boolean streamResults, final String catalog, final Field[] cachedMetadata, final boolean isBatch) throws SQLException {
        synchronized (this.mutex) {
            long queryStartTime = 0L;
            int endOfQueryPacketPosition = 0;
            if (packet != null) {
                endOfQueryPacketPosition = packet.getPosition();
            }
            if (this.getGatherPerformanceMetrics()) {
                queryStartTime = System.currentTimeMillis();
            }
            this.lastQueryFinishedTime = 0L;
            Label_0097: {
                if (!this.getHighAvailability() || (!this.autoCommit && !this.getAutoReconnectForPools()) || !this.needsPing || isBatch) {
                    break Label_0097;
                }
                try {
                    this.pingInternal(false, 0);
                    this.needsPing = false;
                }
                catch (Exception Ex) {
                    this.createNewIO(true);
                }
                try {
                    if (packet == null) {
                        String encoding = null;
                        if (this.getUseUnicode()) {
                            encoding = this.getEncoding();
                        }
                        return this.io.sqlQueryDirect(callingStatement, sql, encoding, null, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, cachedMetadata);
                    }
                    return this.io.sqlQueryDirect(callingStatement, null, null, packet, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, cachedMetadata);
                }
                catch (SQLException sqlE) {
                    if (this.getDumpQueriesOnException()) {
                        final String extractedSql = this.extractSqlFromPacket(sql, packet, endOfQueryPacketPosition);
                        final StringBuffer messageBuf = new StringBuffer(extractedSql.length() + 32);
                        messageBuf.append("\n\nQuery being executed when exception was thrown:\n");
                        messageBuf.append(extractedSql);
                        messageBuf.append("\n\n");
                        sqlE = appendMessageToException(sqlE, messageBuf.toString(), this.getExceptionInterceptor());
                    }
                    if (this.getHighAvailability()) {
                        this.needsPing = true;
                    }
                    else {
                        final String sqlState = sqlE.getSQLState();
                        if (sqlState != null && sqlState.equals("08S01")) {
                            this.cleanup(sqlE);
                        }
                    }
                    throw sqlE;
                }
                catch (Exception ex) {
                    if (this.getHighAvailability()) {
                        this.needsPing = true;
                    }
                    else if (ex instanceof IOException) {
                        this.cleanup(ex);
                    }
                    final SQLException sqlEx = SQLError.createSQLException(Messages.getString("Connection.UnexpectedException"), "S1000", this.getExceptionInterceptor());
                    sqlEx.initCause(ex);
                    throw sqlEx;
                }
                finally {
                    if (this.getMaintainTimeStats()) {
                        this.lastQueryFinishedTime = System.currentTimeMillis();
                    }
                    if (this.getGatherPerformanceMetrics()) {
                        final long queryTime = System.currentTimeMillis() - queryStartTime;
                        this.registerQueryExecutionTime(queryTime);
                    }
                }
            }
        }
    }
    
    public String extractSqlFromPacket(final String possibleSqlQuery, final Buffer queryPacket, final int endOfQueryPacketPosition) throws SQLException {
        String extractedSql = null;
        if (possibleSqlQuery != null) {
            if (possibleSqlQuery.length() > this.getMaxQuerySizeToLog()) {
                final StringBuffer truncatedQueryBuf = new StringBuffer(possibleSqlQuery.substring(0, this.getMaxQuerySizeToLog()));
                truncatedQueryBuf.append(Messages.getString("MysqlIO.25"));
                extractedSql = truncatedQueryBuf.toString();
            }
            else {
                extractedSql = possibleSqlQuery;
            }
        }
        if (extractedSql == null) {
            int extractPosition = endOfQueryPacketPosition;
            boolean truncated = false;
            if (endOfQueryPacketPosition > this.getMaxQuerySizeToLog()) {
                extractPosition = this.getMaxQuerySizeToLog();
                truncated = true;
            }
            extractedSql = new String(queryPacket.getByteBuffer(), 5, extractPosition - 5);
            if (truncated) {
                extractedSql += Messages.getString("MysqlIO.25");
            }
        }
        return extractedSql;
    }
    
    public void finalize() throws Throwable {
        this.cleanup(null);
        super.finalize();
    }
    
    public StringBuffer generateConnectionCommentBlock(final StringBuffer buf) {
        buf.append("/* conn id ");
        buf.append(this.getId());
        buf.append(" clock: ");
        buf.append(System.currentTimeMillis());
        buf.append(" */ ");
        return buf;
    }
    
    public int getActiveStatementCount() {
        if (this.openStatements != null) {
            synchronized (this.openStatements) {
                return this.openStatements.size();
            }
        }
        return 0;
    }
    
    public boolean getAutoCommit() throws SQLException {
        return this.autoCommit;
    }
    
    public Calendar getCalendarInstanceForSessionOrNew() {
        if (this.getDynamicCalendars()) {
            return Calendar.getInstance();
        }
        return this.getSessionLockedCalendar();
    }
    
    public String getCatalog() throws SQLException {
        return this.database;
    }
    
    public String getCharacterSetMetadata() {
        return this.characterSetMetadata;
    }
    
    public SingleByteCharsetConverter getCharsetConverter(final String javaEncodingName) throws SQLException {
        if (javaEncodingName == null) {
            return null;
        }
        if (this.usePlatformCharsetConverters) {
            return null;
        }
        SingleByteCharsetConverter converter = null;
        synchronized (this.charsetConverterMap) {
            final Object asObject = this.charsetConverterMap.get(javaEncodingName);
            if (asObject == ConnectionImpl.CHARSET_CONVERTER_NOT_AVAILABLE_MARKER) {
                return null;
            }
            converter = (SingleByteCharsetConverter)asObject;
            if (converter == null) {
                try {
                    converter = SingleByteCharsetConverter.getInstance(javaEncodingName, this);
                    if (converter == null) {
                        this.charsetConverterMap.put(javaEncodingName, ConnectionImpl.CHARSET_CONVERTER_NOT_AVAILABLE_MARKER);
                    }
                    else {
                        this.charsetConverterMap.put(javaEncodingName, converter);
                    }
                }
                catch (UnsupportedEncodingException unsupEncEx) {
                    this.charsetConverterMap.put(javaEncodingName, ConnectionImpl.CHARSET_CONVERTER_NOT_AVAILABLE_MARKER);
                    converter = null;
                }
            }
        }
        return converter;
    }
    
    public String getCharsetNameForIndex(final int charsetIndex) throws SQLException {
        String charsetName = null;
        if (this.getUseOldUTF8Behavior()) {
            return this.getEncoding();
        }
        if (charsetIndex != -1) {
            try {
                charsetName = this.indexToCharsetMapping[charsetIndex];
                if (("sjis".equalsIgnoreCase(charsetName) || "MS932".equalsIgnoreCase(charsetName)) && CharsetMapping.isAliasForSjis(this.getEncoding())) {
                    charsetName = this.getEncoding();
                }
            }
            catch (ArrayIndexOutOfBoundsException outOfBoundsEx) {
                throw SQLError.createSQLException("Unknown character set index for field '" + charsetIndex + "' received from server.", "S1000", this.getExceptionInterceptor());
            }
            if (charsetName == null) {
                charsetName = this.getEncoding();
            }
        }
        else {
            charsetName = this.getEncoding();
        }
        return charsetName;
    }
    
    public TimeZone getDefaultTimeZone() {
        return this.defaultTimeZone;
    }
    
    public String getErrorMessageEncoding() {
        return this.errorMessageEncoding;
    }
    
    public int getHoldability() throws SQLException {
        return 2;
    }
    
    public long getId() {
        return this.connectionId;
    }
    
    public long getIdleFor() {
        if (this.lastQueryFinishedTime == 0L) {
            return 0L;
        }
        final long now = System.currentTimeMillis();
        final long idleTime = now - this.lastQueryFinishedTime;
        return idleTime;
    }
    
    public MysqlIO getIO() throws SQLException {
        if (this.io == null || this.isClosed) {
            throw SQLError.createSQLException("Operation not allowed on closed connection", "08003", this.getExceptionInterceptor());
        }
        return this.io;
    }
    
    public Log getLog() throws SQLException {
        return this.log;
    }
    
    public int getMaxBytesPerChar(final String javaCharsetName) throws SQLException {
        String charset = CharsetMapping.getMysqlEncodingForJavaEncoding(javaCharsetName, this);
        if (this.io.serverCharsetIndex == 33 && this.versionMeetsMinimum(5, 5, 3) && javaCharsetName.equalsIgnoreCase("UTF-8")) {
            charset = "utf8";
        }
        if (!this.versionMeetsMinimum(4, 1, 0)) {
            return 1;
        }
        Map mapToCheck = null;
        if (!this.getUseDynamicCharsetInfo()) {
            mapToCheck = CharsetMapping.STATIC_CHARSET_TO_NUM_BYTES_MAP;
        }
        else {
            mapToCheck = this.charsetToNumBytesMap;
            synchronized (this.charsetToNumBytesMap) {
                if (this.charsetToNumBytesMap.isEmpty()) {
                    Statement stmt = null;
                    ResultSet rs = null;
                    try {
                        stmt = this.getMetadataSafeStatement();
                        rs = stmt.executeQuery("SHOW CHARACTER SET");
                        while (rs.next()) {
                            this.charsetToNumBytesMap.put(rs.getString("Charset"), Constants.integerValueOf(rs.getInt("Maxlen")));
                        }
                        rs.close();
                        rs = null;
                        stmt.close();
                        stmt = null;
                    }
                    finally {
                        if (rs != null) {
                            rs.close();
                            rs = null;
                        }
                        if (stmt != null) {
                            stmt.close();
                            stmt = null;
                        }
                    }
                }
            }
        }
        final Integer mbPerChar = mapToCheck.get(charset);
        if (mbPerChar != null) {
            return mbPerChar;
        }
        return 1;
    }
    
    public DatabaseMetaData getMetaData() throws SQLException {
        return this.getMetaData(true, true);
    }
    
    private DatabaseMetaData getMetaData(final boolean checkClosed, final boolean checkForInfoSchema) throws SQLException {
        if (checkClosed) {
            this.checkClosed();
        }
        return com.mysql.jdbc.DatabaseMetaData.getInstance(this.getLoadBalanceSafeProxy(), this.database, checkForInfoSchema);
    }
    
    public Statement getMetadataSafeStatement() throws SQLException {
        final Statement stmt = this.createStatement();
        if (stmt.getMaxRows() != 0) {
            stmt.setMaxRows(0);
        }
        stmt.setEscapeProcessing(false);
        if (stmt.getFetchSize() != 0) {
            stmt.setFetchSize(0);
        }
        return stmt;
    }
    
    public Object getMutex() throws SQLException {
        if (this.io == null) {
            this.throwConnectionClosedException();
        }
        this.reportMetricsIfNeeded();
        return this.mutex;
    }
    
    public int getNetBufferLength() {
        return this.netBufferLength;
    }
    
    public String getServerCharacterEncoding() {
        if (this.io.versionMeetsMinimum(4, 1, 0)) {
            return this.serverVariables.get("character_set_server");
        }
        return this.serverVariables.get("character_set");
    }
    
    public int getServerMajorVersion() {
        return this.io.getServerMajorVersion();
    }
    
    public int getServerMinorVersion() {
        return this.io.getServerMinorVersion();
    }
    
    public int getServerSubMinorVersion() {
        return this.io.getServerSubMinorVersion();
    }
    
    public TimeZone getServerTimezoneTZ() {
        return this.serverTimezoneTZ;
    }
    
    public String getServerVariable(final String variableName) {
        if (this.serverVariables != null) {
            return this.serverVariables.get(variableName);
        }
        return null;
    }
    
    public String getServerVersion() {
        return this.io.getServerVersion();
    }
    
    public Calendar getSessionLockedCalendar() {
        return this.sessionCalendar;
    }
    
    public int getTransactionIsolation() throws SQLException {
        if (this.hasIsolationLevels && !this.getUseLocalSessionState()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = this.getMetadataSafeStatement();
                String query = null;
                int offset = 0;
                if (this.versionMeetsMinimum(4, 0, 3)) {
                    query = "SELECT @@session.tx_isolation";
                    offset = 1;
                }
                else {
                    query = "SHOW VARIABLES LIKE 'transaction_isolation'";
                    offset = 2;
                }
                rs = stmt.executeQuery(query);
                if (rs.next()) {
                    final String s = rs.getString(offset);
                    if (s != null) {
                        final Integer intTI = ConnectionImpl.mapTransIsolationNameToValue.get(s);
                        if (intTI != null) {
                            return intTI;
                        }
                    }
                    throw SQLError.createSQLException("Could not map transaction isolation '" + s + " to a valid JDBC level.", "S1000", this.getExceptionInterceptor());
                }
                throw SQLError.createSQLException("Could not retrieve transaction isolation level from server", "S1000", this.getExceptionInterceptor());
            }
            finally {
                if (rs != null) {
                    try {
                        rs.close();
                    }
                    catch (Exception ex) {}
                    rs = null;
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    }
                    catch (Exception ex2) {}
                    stmt = null;
                }
            }
        }
        return this.isolationLevel;
    }
    
    public synchronized Map getTypeMap() throws SQLException {
        if (this.typeMap == null) {
            this.typeMap = new HashMap();
        }
        return this.typeMap;
    }
    
    public String getURL() {
        return this.myURL;
    }
    
    public String getUser() {
        return this.user;
    }
    
    public Calendar getUtcCalendar() {
        return this.utcCalendar;
    }
    
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }
    
    public boolean hasSameProperties(final Connection c) {
        return this.props.equals(c.getProperties());
    }
    
    public Properties getProperties() {
        return this.props;
    }
    
    public boolean hasTriedMaster() {
        return this.hasTriedMasterFlag;
    }
    
    public void incrementNumberOfPreparedExecutes() {
        if (this.getGatherPerformanceMetrics()) {
            ++this.numberOfPreparedExecutes;
            ++this.numberOfQueriesIssued;
        }
    }
    
    public void incrementNumberOfPrepares() {
        if (this.getGatherPerformanceMetrics()) {
            ++this.numberOfPrepares;
        }
    }
    
    public void incrementNumberOfResultSetsCreated() {
        if (this.getGatherPerformanceMetrics()) {
            ++this.numberOfResultSetsCreated;
        }
    }
    
    private void initializeDriverProperties(final Properties info) throws SQLException {
        this.initializeProperties(info);
        final String exceptionInterceptorClasses = this.getExceptionInterceptors();
        if (exceptionInterceptorClasses != null && !"".equals(exceptionInterceptorClasses)) {
            (this.exceptionInterceptor = new ExceptionInterceptorChain(exceptionInterceptorClasses)).init(this, info);
        }
        this.usePlatformCharsetConverters = this.getUseJvmCharsetConverters();
        this.log = LogFactory.getLogger(this.getLogger(), "MySQL", this.getExceptionInterceptor());
        if (this.getProfileSql() || this.getUseUsageAdvisor()) {
            this.eventSink = ProfilerEventHandlerFactory.getInstance(this.getLoadBalanceSafeProxy());
        }
        if (this.getCachePreparedStatements()) {
            this.createPreparedStatementCaches();
        }
        if (this.getNoDatetimeStringSync() && this.getUseTimezone()) {
            throw SQLError.createSQLException("Can't enable noDatetimeSync and useTimezone configuration properties at the same time", "01S00", this.getExceptionInterceptor());
        }
        if (this.getCacheCallableStatements()) {
            this.parsedCallableStatementCache = new LRUCache(this.getCallableStatementCacheSize());
        }
        if (this.getAllowMultiQueries()) {
            this.setCacheResultSetMetadata(false);
        }
        if (this.getCacheResultSetMetadata()) {
            this.resultSetMetadataCache = new LRUCache(this.getMetadataCacheSize());
        }
    }
    
    private void initializePropsFromServer() throws SQLException {
        final String connectionInterceptorClasses = this.getConnectionLifecycleInterceptors();
        this.connectionLifecycleInterceptors = null;
        if (connectionInterceptorClasses != null) {
            this.connectionLifecycleInterceptors = Util.loadExtensions(this, this.props, connectionInterceptorClasses, "Connection.badLifecycleInterceptor", this.getExceptionInterceptor());
        }
        this.setSessionVariables();
        if (!this.versionMeetsMinimum(4, 1, 0)) {
            this.setTransformedBitIsBoolean(false);
        }
        this.parserKnowsUnicode = this.versionMeetsMinimum(4, 1, 0);
        if (this.getUseServerPreparedStmts() && this.versionMeetsMinimum(4, 1, 0)) {
            this.useServerPreparedStmts = true;
            if (this.versionMeetsMinimum(5, 0, 0) && !this.versionMeetsMinimum(5, 0, 3)) {
                this.useServerPreparedStmts = false;
            }
        }
        this.serverVariables.clear();
        if (this.versionMeetsMinimum(3, 21, 22)) {
            this.loadServerVariables();
            if (this.versionMeetsMinimum(5, 0, 2)) {
                this.autoIncrementIncrement = this.getServerVariableAsInt("auto_increment_increment", 1);
            }
            else {
                this.autoIncrementIncrement = 1;
            }
            this.buildCollationMapping();
            LicenseConfiguration.checkLicenseType(this.serverVariables);
            final String lowerCaseTables = this.serverVariables.get("lower_case_table_names");
            this.lowerCaseTableNames = ("on".equalsIgnoreCase(lowerCaseTables) || "1".equalsIgnoreCase(lowerCaseTables) || "2".equalsIgnoreCase(lowerCaseTables));
            this.storesLowerCaseTableName = ("1".equalsIgnoreCase(lowerCaseTables) || "on".equalsIgnoreCase(lowerCaseTables));
            this.configureTimezone();
            if (this.serverVariables.containsKey("max_allowed_packet")) {
                final int serverMaxAllowedPacket = this.getServerVariableAsInt("max_allowed_packet", -1);
                if (serverMaxAllowedPacket != -1 && (serverMaxAllowedPacket < this.getMaxAllowedPacket() || this.getMaxAllowedPacket() <= 0)) {
                    this.setMaxAllowedPacket(serverMaxAllowedPacket);
                }
                else if (serverMaxAllowedPacket == -1 && this.getMaxAllowedPacket() == -1) {
                    this.setMaxAllowedPacket(65535);
                }
                final int preferredBlobSendChunkSize = this.getBlobSendChunkSize();
                final int allowedBlobSendChunkSize = Math.min(preferredBlobSendChunkSize, this.getMaxAllowedPacket()) - 8192 - 11;
                this.setBlobSendChunkSize(String.valueOf(allowedBlobSendChunkSize));
            }
            if (this.serverVariables.containsKey("net_buffer_length")) {
                this.netBufferLength = this.getServerVariableAsInt("net_buffer_length", 16384);
            }
            this.checkTransactionIsolationLevel();
            if (!this.versionMeetsMinimum(4, 1, 0)) {
                this.checkServerEncoding();
            }
            this.io.checkForCharsetMismatch();
            if (this.serverVariables.containsKey("sql_mode")) {
                int sqlMode = 0;
                final String sqlModeAsString = this.serverVariables.get("sql_mode");
                try {
                    sqlMode = Integer.parseInt(sqlModeAsString);
                }
                catch (NumberFormatException nfe) {
                    sqlMode = 0;
                    if (sqlModeAsString != null) {
                        if (sqlModeAsString.indexOf("ANSI_QUOTES") != -1) {
                            sqlMode |= 0x4;
                        }
                        if (sqlModeAsString.indexOf("NO_BACKSLASH_ESCAPES") != -1) {
                            this.noBackslashEscapes = true;
                        }
                    }
                }
                if ((sqlMode & 0x4) > 0) {
                    this.useAnsiQuotes = true;
                }
                else {
                    this.useAnsiQuotes = false;
                }
            }
        }
        this.errorMessageEncoding = CharsetMapping.getCharacterEncodingForErrorMessages(this);
        final boolean overrideDefaultAutocommit = this.isAutoCommitNonDefaultOnServer();
        this.configureClientCharacterSet(false);
        if (this.versionMeetsMinimum(3, 23, 15)) {
            this.transactionsSupported = true;
            if (!overrideDefaultAutocommit) {
                this.setAutoCommit(true);
            }
        }
        else {
            this.transactionsSupported = false;
        }
        if (this.versionMeetsMinimum(3, 23, 36)) {
            this.hasIsolationLevels = true;
        }
        else {
            this.hasIsolationLevels = false;
        }
        this.hasQuotedIdentifiers = this.versionMeetsMinimum(3, 23, 6);
        this.io.resetMaxBuf();
        if (this.io.versionMeetsMinimum(4, 1, 0)) {
            final String characterSetResultsOnServerMysql = this.serverVariables.get("jdbc.local.character_set_results");
            if (characterSetResultsOnServerMysql == null || StringUtils.startsWithIgnoreCaseAndWs(characterSetResultsOnServerMysql, "NULL") || characterSetResultsOnServerMysql.length() == 0) {
                final String defaultMetadataCharsetMysql = this.serverVariables.get("character_set_system");
                String defaultMetadataCharset = null;
                if (defaultMetadataCharsetMysql != null) {
                    defaultMetadataCharset = CharsetMapping.getJavaEncodingForMysqlEncoding(defaultMetadataCharsetMysql, this);
                }
                else {
                    defaultMetadataCharset = "UTF-8";
                }
                this.characterSetMetadata = defaultMetadataCharset;
            }
            else {
                this.characterSetResultsOnServer = CharsetMapping.getJavaEncodingForMysqlEncoding(characterSetResultsOnServerMysql, this);
                this.characterSetMetadata = this.characterSetResultsOnServer;
            }
        }
        else {
            this.characterSetMetadata = this.getEncoding();
        }
        if (this.versionMeetsMinimum(4, 1, 0) && !this.versionMeetsMinimum(4, 1, 10) && this.getAllowMultiQueries() && this.isQueryCacheEnabled()) {
            this.setAllowMultiQueries(false);
        }
        if (this.versionMeetsMinimum(5, 0, 0) && (this.getUseLocalTransactionState() || this.getElideSetAutoCommits()) && this.isQueryCacheEnabled() && !this.versionMeetsMinimum(6, 0, 10)) {
            this.setUseLocalTransactionState(false);
            this.setElideSetAutoCommits(false);
        }
        this.setupServerForTruncationChecks();
    }
    
    private boolean isQueryCacheEnabled() {
        return "ON".equalsIgnoreCase(this.serverVariables.get("query_cache_type")) && !"0".equalsIgnoreCase(this.serverVariables.get("query_cache_size"));
    }
    
    private int getServerVariableAsInt(final String variableName, final int fallbackValue) throws SQLException {
        try {
            return Integer.parseInt(this.serverVariables.get(variableName));
        }
        catch (NumberFormatException nfe) {
            this.getLog().logWarn(Messages.getString("Connection.BadValueInServerVariables", new Object[] { variableName, this.serverVariables.get(variableName), new Integer(fallbackValue) }));
            return fallbackValue;
        }
    }
    
    private boolean isAutoCommitNonDefaultOnServer() throws SQLException {
        boolean overrideDefaultAutocommit = false;
        final String initConnectValue = this.serverVariables.get("init_connect");
        if (this.versionMeetsMinimum(4, 1, 2) && initConnectValue != null && initConnectValue.length() > 0) {
            if (!this.getElideSetAutoCommits()) {
                ResultSet rs = null;
                Statement stmt = null;
                try {
                    stmt = this.getMetadataSafeStatement();
                    rs = stmt.executeQuery("SELECT @@session.autocommit");
                    if (rs.next()) {
                        this.autoCommit = rs.getBoolean(1);
                        if (!this.autoCommit) {
                            overrideDefaultAutocommit = true;
                        }
                    }
                }
                finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        }
                        catch (SQLException ex) {}
                    }
                    if (stmt != null) {
                        try {
                            stmt.close();
                        }
                        catch (SQLException ex2) {}
                    }
                }
            }
            else if (this.getIO().isSetNeededForAutoCommitMode(true)) {
                this.autoCommit = false;
                overrideDefaultAutocommit = true;
            }
        }
        return overrideDefaultAutocommit;
    }
    
    public boolean isClientTzUTC() {
        return this.isClientTzUTC;
    }
    
    public boolean isClosed() {
        return this.isClosed;
    }
    
    public boolean isCursorFetchEnabled() throws SQLException {
        return this.versionMeetsMinimum(5, 0, 2) && this.getUseCursorFetch();
    }
    
    public boolean isInGlobalTx() {
        return this.isInGlobalTx;
    }
    
    public synchronized boolean isMasterConnection() {
        return false;
    }
    
    public boolean isNoBackslashEscapesSet() {
        return this.noBackslashEscapes;
    }
    
    public boolean isReadInfoMsgEnabled() {
        return this.readInfoMsg;
    }
    
    public boolean isReadOnly() throws SQLException {
        return this.readOnly;
    }
    
    public boolean isRunningOnJDK13() {
        return this.isRunningOnJDK13;
    }
    
    public synchronized boolean isSameResource(final Connection otherConnection) {
        if (otherConnection == null) {
            return false;
        }
        boolean directCompare = true;
        final String otherHost = ((ConnectionImpl)otherConnection).origHostToConnectTo;
        final String otherOrigDatabase = ((ConnectionImpl)otherConnection).origDatabaseToConnectTo;
        final String otherCurrentCatalog = ((ConnectionImpl)otherConnection).database;
        if (!nullSafeCompare(otherHost, this.origHostToConnectTo)) {
            directCompare = false;
        }
        else if (otherHost != null && otherHost.indexOf(44) == -1 && otherHost.indexOf(58) == -1) {
            directCompare = (((ConnectionImpl)otherConnection).origPortToConnectTo == this.origPortToConnectTo);
        }
        if (directCompare) {
            if (!nullSafeCompare(otherOrigDatabase, this.origDatabaseToConnectTo)) {
                directCompare = false;
                directCompare = false;
            }
            else if (!nullSafeCompare(otherCurrentCatalog, this.database)) {
                directCompare = false;
            }
        }
        if (directCompare) {
            return true;
        }
        final String otherResourceId = ((ConnectionImpl)otherConnection).getResourceId();
        final String myResourceId = this.getResourceId();
        if (otherResourceId != null || myResourceId != null) {
            directCompare = nullSafeCompare(otherResourceId, myResourceId);
            if (directCompare) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isServerTzUTC() {
        return this.isServerTzUTC;
    }
    
    private void loadServerVariables() throws SQLException {
        if (this.getCacheServerConfiguration()) {
            synchronized (ConnectionImpl.serverConfigByUrl) {
                final Map cachedVariableMap = ConnectionImpl.serverConfigByUrl.get(this.getURL());
                if (cachedVariableMap != null) {
                    this.serverVariables = cachedVariableMap;
                    this.usingCachedConfig = true;
                    return;
                }
            }
        }
        Statement stmt = null;
        ResultSet results = null;
        try {
            stmt = this.getMetadataSafeStatement();
            String version = this.dbmd.getDriverVersion();
            if (version != null && version.indexOf(42) != -1) {
                final StringBuffer buf = new StringBuffer(version.length() + 10);
                for (int i = 0; i < version.length(); ++i) {
                    final char c = version.charAt(i);
                    if (c == '*') {
                        buf.append("[star]");
                    }
                    else {
                        buf.append(c);
                    }
                }
                version = buf.toString();
            }
            final String versionComment = (this.getParanoid() || version == null) ? "" : ("/* " + version + " */");
            String query = versionComment + "SHOW VARIABLES";
            if (this.versionMeetsMinimum(5, 0, 3)) {
                query = versionComment + "SHOW VARIABLES WHERE Variable_name ='language'" + " OR Variable_name = 'net_write_timeout'" + " OR Variable_name = 'interactive_timeout'" + " OR Variable_name = 'wait_timeout'" + " OR Variable_name = 'character_set_client'" + " OR Variable_name = 'character_set_connection'" + " OR Variable_name = 'character_set'" + " OR Variable_name = 'character_set_server'" + " OR Variable_name = 'tx_isolation'" + " OR Variable_name = 'transaction_isolation'" + " OR Variable_name = 'character_set_results'" + " OR Variable_name = 'timezone'" + " OR Variable_name = 'time_zone'" + " OR Variable_name = 'system_time_zone'" + " OR Variable_name = 'lower_case_table_names'" + " OR Variable_name = 'max_allowed_packet'" + " OR Variable_name = 'net_buffer_length'" + " OR Variable_name = 'sql_mode'" + " OR Variable_name = 'query_cache_type'" + " OR Variable_name = 'query_cache_size'" + " OR Variable_name = 'init_connect'";
            }
            results = stmt.executeQuery(query);
            while (results.next()) {
                this.serverVariables.put(results.getString(1), results.getString(2));
            }
            if (this.versionMeetsMinimum(5, 0, 2)) {
                results = stmt.executeQuery(versionComment + "SELECT @@session.auto_increment_increment");
                if (results.next()) {
                    this.serverVariables.put("auto_increment_increment", results.getString(1));
                }
            }
            if (this.getCacheServerConfiguration()) {
                synchronized (ConnectionImpl.serverConfigByUrl) {
                    ConnectionImpl.serverConfigByUrl.put(this.getURL(), this.serverVariables);
                }
            }
        }
        catch (SQLException e) {
            throw e;
        }
        finally {
            if (results != null) {
                try {
                    results.close();
                }
                catch (SQLException ex) {}
            }
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException ex2) {}
            }
        }
    }
    
    public int getAutoIncrementIncrement() {
        return this.autoIncrementIncrement;
    }
    
    public boolean lowerCaseTableNames() {
        return this.lowerCaseTableNames;
    }
    
    public void maxRowsChanged(final com.mysql.jdbc.Statement stmt) {
        synchronized (this.mutex) {
            if (this.statementsUsingMaxRows == null) {
                this.statementsUsingMaxRows = new HashMap();
            }
            this.statementsUsingMaxRows.put(stmt, stmt);
            this.maxRowsChanged = true;
        }
    }
    
    public String nativeSQL(final String sql) throws SQLException {
        if (sql == null) {
            return null;
        }
        final Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, this.serverSupportsConvertFn(), this.getLoadBalanceSafeProxy());
        if (escapedSqlResult instanceof String) {
            return (String)escapedSqlResult;
        }
        return ((EscapeProcessorResult)escapedSqlResult).escapedSql;
    }
    
    private CallableStatement parseCallableStatement(final String sql) throws SQLException {
        final Object escapedSqlResult = EscapeProcessor.escapeSQL(sql, this.serverSupportsConvertFn(), this.getLoadBalanceSafeProxy());
        boolean isFunctionCall = false;
        String parsedSql = null;
        if (escapedSqlResult instanceof EscapeProcessorResult) {
            parsedSql = ((EscapeProcessorResult)escapedSqlResult).escapedSql;
            isFunctionCall = ((EscapeProcessorResult)escapedSqlResult).callingStoredFunction;
        }
        else {
            parsedSql = (String)escapedSqlResult;
            isFunctionCall = false;
        }
        return CallableStatement.getInstance(this.getLoadBalanceSafeProxy(), parsedSql, this.database, isFunctionCall);
    }
    
    public boolean parserKnowsUnicode() {
        return this.parserKnowsUnicode;
    }
    
    public void ping() throws SQLException {
        this.pingInternal(true, 0);
    }
    
    public void pingInternal(final boolean checkForClosedConnection, final int timeoutMillis) throws SQLException {
        if (checkForClosedConnection) {
            this.checkClosed();
        }
        final long pingMillisLifetime = this.getSelfDestructOnPingSecondsLifetime();
        final int pingMaxOperations = this.getSelfDestructOnPingMaxOperations();
        if ((pingMillisLifetime > 0L && System.currentTimeMillis() - this.connectionCreationTimeMillis > pingMillisLifetime) || (pingMaxOperations > 0 && pingMaxOperations <= this.io.getCommandCount())) {
            this.close();
            throw SQLError.createSQLException(Messages.getString("Connection.exceededConnectionLifetime"), "08S01", this.getExceptionInterceptor());
        }
        this.io.sendCommand(14, null, null, false, null, timeoutMillis);
    }
    
    public java.sql.CallableStatement prepareCall(final String sql) throws SQLException {
        return this.prepareCall(sql, 1003, 1007);
    }
    
    public java.sql.CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        if (this.versionMeetsMinimum(5, 0, 0)) {
            CallableStatement cStmt = null;
            if (!this.getCacheCallableStatements()) {
                cStmt = this.parseCallableStatement(sql);
            }
            else {
                synchronized (this.parsedCallableStatementCache) {
                    final CompoundCacheKey key = new CompoundCacheKey(this.getCatalog(), sql);
                    CallableStatement.CallableStatementParamInfo cachedParamInfo = this.parsedCallableStatementCache.get(key);
                    if (cachedParamInfo != null) {
                        cStmt = CallableStatement.getInstance(this.getLoadBalanceSafeProxy(), cachedParamInfo);
                    }
                    else {
                        cStmt = this.parseCallableStatement(sql);
                        cachedParamInfo = cStmt.paramInfo;
                        this.parsedCallableStatementCache.put(key, cachedParamInfo);
                    }
                }
            }
            cStmt.setResultSetType(resultSetType);
            cStmt.setResultSetConcurrency(resultSetConcurrency);
            return cStmt;
        }
        throw SQLError.createSQLException("Callable statements not supported.", "S1C00", this.getExceptionInterceptor());
    }
    
    public java.sql.CallableStatement prepareCall(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        if (this.getPedantic() && resultSetHoldability != 1) {
            throw SQLError.createSQLException("HOLD_CUSRORS_OVER_COMMIT is only supported holdability level", "S1009", this.getExceptionInterceptor());
        }
        final CallableStatement cStmt = (CallableStatement)this.prepareCall(sql, resultSetType, resultSetConcurrency);
        return cStmt;
    }
    
    public PreparedStatement prepareStatement(final String sql) throws SQLException {
        return this.prepareStatement(sql, 1003, 1007);
    }
    
    public PreparedStatement prepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        final PreparedStatement pStmt = this.prepareStatement(sql);
        ((com.mysql.jdbc.PreparedStatement)pStmt).setRetrieveGeneratedKeys(autoGenKeyIndex == 1);
        return pStmt;
    }
    
    public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        this.checkClosed();
        com.mysql.jdbc.PreparedStatement pStmt = null;
        boolean canServerPrepare = true;
        final String nativeSql = this.getProcessEscapeCodesForPrepStmts() ? this.nativeSQL(sql) : sql;
        if (this.useServerPreparedStmts && this.getEmulateUnsupportedPstmts()) {
            canServerPrepare = this.canHandleAsServerPreparedStatement(nativeSql);
        }
        if (this.useServerPreparedStmts && canServerPrepare) {
            if (this.getCachePreparedStatements()) {
                synchronized (this.serverSideStatementCache) {
                    pStmt = this.serverSideStatementCache.remove(sql);
                    if (pStmt != null) {
                        ((ServerPreparedStatement)pStmt).setClosed(false);
                        pStmt.clearParameters();
                    }
                    if (pStmt == null) {
                        try {
                            pStmt = ServerPreparedStatement.getInstance(this.getLoadBalanceSafeProxy(), nativeSql, this.database, resultSetType, resultSetConcurrency);
                            if (sql.length() < this.getPreparedStatementCacheSqlLimit()) {
                                ((ServerPreparedStatement)pStmt).isCached = true;
                            }
                            pStmt.setResultSetType(resultSetType);
                            pStmt.setResultSetConcurrency(resultSetConcurrency);
                        }
                        catch (SQLException sqlEx) {
                            if (!this.getEmulateUnsupportedPstmts()) {
                                throw sqlEx;
                            }
                            pStmt = (com.mysql.jdbc.PreparedStatement)this.clientPrepareStatement(nativeSql, resultSetType, resultSetConcurrency, false);
                            if (sql.length() < this.getPreparedStatementCacheSqlLimit()) {
                                this.serverSideStatementCheckCache.put(sql, Boolean.FALSE);
                            }
                        }
                    }
                }
            }
            else {
                try {
                    pStmt = ServerPreparedStatement.getInstance(this.getLoadBalanceSafeProxy(), nativeSql, this.database, resultSetType, resultSetConcurrency);
                    pStmt.setResultSetType(resultSetType);
                    pStmt.setResultSetConcurrency(resultSetConcurrency);
                }
                catch (SQLException sqlEx2) {
                    if (!this.getEmulateUnsupportedPstmts()) {
                        throw sqlEx2;
                    }
                    pStmt = (com.mysql.jdbc.PreparedStatement)this.clientPrepareStatement(nativeSql, resultSetType, resultSetConcurrency, false);
                }
            }
        }
        else {
            pStmt = (com.mysql.jdbc.PreparedStatement)this.clientPrepareStatement(nativeSql, resultSetType, resultSetConcurrency, false);
        }
        return pStmt;
    }
    
    public PreparedStatement prepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        if (this.getPedantic() && resultSetHoldability != 1) {
            throw SQLError.createSQLException("HOLD_CUSRORS_OVER_COMMIT is only supported holdability level", "S1009", this.getExceptionInterceptor());
        }
        return this.prepareStatement(sql, resultSetType, resultSetConcurrency);
    }
    
    public PreparedStatement prepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        final PreparedStatement pStmt = this.prepareStatement(sql);
        ((com.mysql.jdbc.PreparedStatement)pStmt).setRetrieveGeneratedKeys(autoGenKeyIndexes != null && autoGenKeyIndexes.length > 0);
        return pStmt;
    }
    
    public PreparedStatement prepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        final PreparedStatement pStmt = this.prepareStatement(sql);
        ((com.mysql.jdbc.PreparedStatement)pStmt).setRetrieveGeneratedKeys(autoGenKeyColNames != null && autoGenKeyColNames.length > 0);
        return pStmt;
    }
    
    public void realClose(final boolean calledExplicitly, final boolean issueRollback, final boolean skipLocalTeardown, final Throwable reason) throws SQLException {
        SQLException sqlEx = null;
        if (this.isClosed()) {
            return;
        }
        this.forceClosedReason = reason;
        try {
            if (!skipLocalTeardown) {
                if (!this.getAutoCommit() && issueRollback) {
                    try {
                        this.rollback();
                    }
                    catch (SQLException ex) {
                        sqlEx = ex;
                    }
                }
                this.reportMetrics();
                if (this.getUseUsageAdvisor()) {
                    if (!calledExplicitly) {
                        final String message = "Connection implicitly closed by Driver. You should call Connection.close() from your code to free resources more efficiently and avoid resource leaks.";
                        this.eventSink.consumeEvent(new ProfilerEvent((byte)0, "", this.getCatalog(), this.getId(), -1, -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
                    }
                    final long connectionLifeTime = System.currentTimeMillis() - this.connectionCreationTimeMillis;
                    if (connectionLifeTime < 500L) {
                        final String message2 = "Connection lifetime of < .5 seconds. You might be un-necessarily creating short-lived connections and should investigate connection pooling to be more efficient.";
                        this.eventSink.consumeEvent(new ProfilerEvent((byte)0, "", this.getCatalog(), this.getId(), -1, -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message2));
                    }
                }
                try {
                    this.closeAllOpenStatements();
                }
                catch (SQLException ex) {
                    sqlEx = ex;
                }
                if (this.io != null) {
                    try {
                        this.io.quit();
                    }
                    catch (Exception e) {}
                }
            }
            else {
                this.io.forceClose();
            }
            if (this.statementInterceptors != null) {
                for (int i = 0; i < this.statementInterceptors.size(); ++i) {
                    this.statementInterceptors.get(i).destroy();
                }
            }
            if (this.exceptionInterceptor != null) {
                this.exceptionInterceptor.destroy();
            }
        }
        finally {
            this.openStatements = null;
            this.io = null;
            this.statementInterceptors = null;
            this.exceptionInterceptor = null;
            ProfilerEventHandlerFactory.removeInstance(this);
            synchronized (this) {
                if (this.cancelTimer != null) {
                    this.cancelTimer.cancel();
                }
            }
            this.isClosed = true;
        }
        if (sqlEx != null) {
            throw sqlEx;
        }
    }
    
    public void recachePreparedStatement(final ServerPreparedStatement pstmt) throws SQLException {
        if (pstmt.isPoolable()) {
            synchronized (this.serverSideStatementCache) {
                this.serverSideStatementCache.put(pstmt.originalSql, pstmt);
            }
        }
    }
    
    public void registerQueryExecutionTime(final long queryTimeMs) {
        if (queryTimeMs > this.longestQueryTimeMs) {
            this.longestQueryTimeMs = queryTimeMs;
            this.repartitionPerformanceHistogram();
        }
        this.addToPerformanceHistogram(queryTimeMs, 1);
        if (queryTimeMs < this.shortestQueryTimeMs) {
            this.shortestQueryTimeMs = ((queryTimeMs == 0L) ? 1L : queryTimeMs);
        }
        ++this.numberOfQueriesIssued;
        this.totalQueryTimeMs += queryTimeMs;
    }
    
    public void registerStatement(final com.mysql.jdbc.Statement stmt) {
        synchronized (this.openStatements) {
            this.openStatements.put(stmt, stmt);
        }
    }
    
    public void releaseSavepoint(final Savepoint arg0) throws SQLException {
    }
    
    private void repartitionHistogram(final int[] histCounts, final long[] histBreakpoints, final long currentLowerBound, final long currentUpperBound) {
        if (this.oldHistCounts == null) {
            this.oldHistCounts = new int[histCounts.length];
            this.oldHistBreakpoints = new long[histBreakpoints.length];
        }
        System.arraycopy(histCounts, 0, this.oldHistCounts, 0, histCounts.length);
        System.arraycopy(histBreakpoints, 0, this.oldHistBreakpoints, 0, histBreakpoints.length);
        this.createInitialHistogram(histBreakpoints, currentLowerBound, currentUpperBound);
        for (int i = 0; i < 20; ++i) {
            this.addToHistogram(histCounts, histBreakpoints, this.oldHistBreakpoints[i], this.oldHistCounts[i], currentLowerBound, currentUpperBound);
        }
    }
    
    private void repartitionPerformanceHistogram() {
        this.checkAndCreatePerformanceHistogram();
        this.repartitionHistogram(this.perfMetricsHistCounts, this.perfMetricsHistBreakpoints, (this.shortestQueryTimeMs == Long.MAX_VALUE) ? 0L : this.shortestQueryTimeMs, this.longestQueryTimeMs);
    }
    
    private void repartitionTablesAccessedHistogram() {
        this.checkAndCreateTablesAccessedHistogram();
        this.repartitionHistogram(this.numTablesMetricsHistCounts, this.numTablesMetricsHistBreakpoints, (this.minimumNumberTablesAccessed == Long.MAX_VALUE) ? 0L : this.minimumNumberTablesAccessed, this.maximumNumberTablesAccessed);
    }
    
    private void reportMetrics() {
        if (this.getGatherPerformanceMetrics()) {
            final StringBuffer logMessage = new StringBuffer(256);
            logMessage.append("** Performance Metrics Report **\n");
            logMessage.append("\nLongest reported query: " + this.longestQueryTimeMs + " ms");
            logMessage.append("\nShortest reported query: " + this.shortestQueryTimeMs + " ms");
            logMessage.append("\nAverage query execution time: " + this.totalQueryTimeMs / this.numberOfQueriesIssued + " ms");
            logMessage.append("\nNumber of statements executed: " + this.numberOfQueriesIssued);
            logMessage.append("\nNumber of result sets created: " + this.numberOfResultSetsCreated);
            logMessage.append("\nNumber of statements prepared: " + this.numberOfPrepares);
            logMessage.append("\nNumber of prepared statement executions: " + this.numberOfPreparedExecutes);
            if (this.perfMetricsHistBreakpoints != null) {
                logMessage.append("\n\n\tTiming Histogram:\n");
                final int maxNumPoints = 20;
                int highestCount = Integer.MIN_VALUE;
                for (int i = 0; i < 20; ++i) {
                    if (this.perfMetricsHistCounts[i] > highestCount) {
                        highestCount = this.perfMetricsHistCounts[i];
                    }
                }
                if (highestCount == 0) {
                    highestCount = 1;
                }
                for (int i = 0; i < 19; ++i) {
                    if (i == 0) {
                        logMessage.append("\n\tless than " + this.perfMetricsHistBreakpoints[i + 1] + " ms: \t" + this.perfMetricsHistCounts[i]);
                    }
                    else {
                        logMessage.append("\n\tbetween " + this.perfMetricsHistBreakpoints[i] + " and " + this.perfMetricsHistBreakpoints[i + 1] + " ms: \t" + this.perfMetricsHistCounts[i]);
                    }
                    logMessage.append("\t");
                    for (int numPointsToGraph = maxNumPoints * (this.perfMetricsHistCounts[i] / highestCount), j = 0; j < numPointsToGraph; ++j) {
                        logMessage.append("*");
                    }
                    if (this.longestQueryTimeMs < this.perfMetricsHistCounts[i + 1]) {
                        break;
                    }
                }
                if (this.perfMetricsHistBreakpoints[18] < this.longestQueryTimeMs) {
                    logMessage.append("\n\tbetween ");
                    logMessage.append(this.perfMetricsHistBreakpoints[18]);
                    logMessage.append(" and ");
                    logMessage.append(this.perfMetricsHistBreakpoints[19]);
                    logMessage.append(" ms: \t");
                    logMessage.append(this.perfMetricsHistCounts[19]);
                }
            }
            if (this.numTablesMetricsHistBreakpoints != null) {
                logMessage.append("\n\n\tTable Join Histogram:\n");
                final int maxNumPoints = 20;
                int highestCount = Integer.MIN_VALUE;
                for (int i = 0; i < 20; ++i) {
                    if (this.numTablesMetricsHistCounts[i] > highestCount) {
                        highestCount = this.numTablesMetricsHistCounts[i];
                    }
                }
                if (highestCount == 0) {
                    highestCount = 1;
                }
                for (int i = 0; i < 19; ++i) {
                    if (i == 0) {
                        logMessage.append("\n\t" + this.numTablesMetricsHistBreakpoints[i + 1] + " tables or less: \t\t" + this.numTablesMetricsHistCounts[i]);
                    }
                    else {
                        logMessage.append("\n\tbetween " + this.numTablesMetricsHistBreakpoints[i] + " and " + this.numTablesMetricsHistBreakpoints[i + 1] + " tables: \t" + this.numTablesMetricsHistCounts[i]);
                    }
                    logMessage.append("\t");
                    for (int numPointsToGraph = maxNumPoints * (this.numTablesMetricsHistCounts[i] / highestCount), j = 0; j < numPointsToGraph; ++j) {
                        logMessage.append("*");
                    }
                    if (this.maximumNumberTablesAccessed < this.numTablesMetricsHistBreakpoints[i + 1]) {
                        break;
                    }
                }
                if (this.numTablesMetricsHistBreakpoints[18] < this.maximumNumberTablesAccessed) {
                    logMessage.append("\n\tbetween ");
                    logMessage.append(this.numTablesMetricsHistBreakpoints[18]);
                    logMessage.append(" and ");
                    logMessage.append(this.numTablesMetricsHistBreakpoints[19]);
                    logMessage.append(" tables: ");
                    logMessage.append(this.numTablesMetricsHistCounts[19]);
                }
            }
            this.log.logInfo(logMessage);
            this.metricsLastReportedMs = System.currentTimeMillis();
        }
    }
    
    private void reportMetricsIfNeeded() {
        if (this.getGatherPerformanceMetrics() && System.currentTimeMillis() - this.metricsLastReportedMs > this.getReportMetricsIntervalMillis()) {
            this.reportMetrics();
        }
    }
    
    public void reportNumberOfTablesAccessed(final int numTablesAccessed) {
        if (numTablesAccessed < this.minimumNumberTablesAccessed) {
            this.minimumNumberTablesAccessed = numTablesAccessed;
        }
        if (numTablesAccessed > this.maximumNumberTablesAccessed) {
            this.maximumNumberTablesAccessed = numTablesAccessed;
            this.repartitionTablesAccessedHistogram();
        }
        this.addToTablesAccessedHistogram(numTablesAccessed, 1);
    }
    
    public void resetServerState() throws SQLException {
        if (!this.getParanoid() && this.io != null && this.versionMeetsMinimum(4, 0, 6)) {
            this.changeUser(this.user, this.password);
        }
    }
    
    public void rollback() throws SQLException {
        synchronized (this.getMutex()) {
            this.checkClosed();
            try {
                if (this.connectionLifecycleInterceptors != null) {
                    final IterateBlock iter = new IterateBlock(this.connectionLifecycleInterceptors.iterator()) {
                        void forEach(final Object each) throws SQLException {
                            if (!((ConnectionLifecycleInterceptor)each).rollback()) {
                                this.stopIterating = true;
                            }
                        }
                    };
                    iter.doForAll();
                    if (!iter.fullIteration()) {
                        return;
                    }
                }
                if (this.autoCommit && !this.getRelaxAutoCommit()) {
                    throw SQLError.createSQLException("Can't call rollback when autocommit=true", "08003", this.getExceptionInterceptor());
                }
                if (this.transactionsSupported) {
                    try {
                        this.rollbackNoChecks();
                    }
                    catch (SQLException sqlEx) {
                        if (this.getIgnoreNonTxTables() && sqlEx.getErrorCode() == 1196) {
                            return;
                        }
                        throw sqlEx;
                    }
                }
            }
            catch (SQLException sqlException) {
                if ("08S01".equals(sqlException.getSQLState())) {
                    throw SQLError.createSQLException("Communications link failure during rollback(). Transaction resolution unknown.", "08007", this.getExceptionInterceptor());
                }
                throw sqlException;
            }
            finally {
                this.needsPing = this.getReconnectAtTxEnd();
            }
        }
    }
    
    public void rollback(final Savepoint savepoint) throws SQLException {
        if (this.versionMeetsMinimum(4, 0, 14) || this.versionMeetsMinimum(4, 1, 1)) {
            synchronized (this.getMutex()) {
                this.checkClosed();
                try {
                    if (this.connectionLifecycleInterceptors != null) {
                        final IterateBlock iter = new IterateBlock(this.connectionLifecycleInterceptors.iterator()) {
                            void forEach(final Object each) throws SQLException {
                                if (!((ConnectionLifecycleInterceptor)each).rollback(savepoint)) {
                                    this.stopIterating = true;
                                }
                            }
                        };
                        iter.doForAll();
                        if (!iter.fullIteration()) {
                            return;
                        }
                    }
                    final StringBuffer rollbackQuery = new StringBuffer("ROLLBACK TO SAVEPOINT ");
                    rollbackQuery.append('`');
                    rollbackQuery.append(savepoint.getSavepointName());
                    rollbackQuery.append('`');
                    Statement stmt = null;
                    try {
                        stmt = this.getMetadataSafeStatement();
                        stmt.executeUpdate(rollbackQuery.toString());
                    }
                    catch (SQLException sqlEx) {
                        final int errno = sqlEx.getErrorCode();
                        if (errno == 1181) {
                            final String msg = sqlEx.getMessage();
                            if (msg != null) {
                                final int indexOfError153 = msg.indexOf("153");
                                if (indexOfError153 != -1) {
                                    throw SQLError.createSQLException("Savepoint '" + savepoint.getSavepointName() + "' does not exist", "S1009", errno, this.getExceptionInterceptor());
                                }
                            }
                        }
                        if (this.getIgnoreNonTxTables() && sqlEx.getErrorCode() != 1196) {
                            throw sqlEx;
                        }
                        if ("08S01".equals(sqlEx.getSQLState())) {
                            throw SQLError.createSQLException("Communications link failure during rollback(). Transaction resolution unknown.", "08007", this.getExceptionInterceptor());
                        }
                        throw sqlEx;
                    }
                    finally {
                        this.closeStatement(stmt);
                    }
                }
                finally {
                    this.needsPing = this.getReconnectAtTxEnd();
                }
            }
            return;
        }
        throw SQLError.notImplemented();
    }
    
    private void rollbackNoChecks() throws SQLException {
        if (this.getUseLocalTransactionState() && this.versionMeetsMinimum(5, 0, 0) && !this.io.inTransactionOnServer()) {
            return;
        }
        this.execSQL(null, "rollback", -1, null, 1003, 1007, false, this.database, null, false);
    }
    
    public PreparedStatement serverPrepareStatement(final String sql) throws SQLException {
        final String nativeSql = this.getProcessEscapeCodesForPrepStmts() ? this.nativeSQL(sql) : sql;
        return ServerPreparedStatement.getInstance(this.getLoadBalanceSafeProxy(), nativeSql, this.getCatalog(), 1003, 1007);
    }
    
    public PreparedStatement serverPrepareStatement(final String sql, final int autoGenKeyIndex) throws SQLException {
        final String nativeSql = this.getProcessEscapeCodesForPrepStmts() ? this.nativeSQL(sql) : sql;
        final com.mysql.jdbc.PreparedStatement pStmt = ServerPreparedStatement.getInstance(this.getLoadBalanceSafeProxy(), nativeSql, this.getCatalog(), 1003, 1007);
        pStmt.setRetrieveGeneratedKeys(autoGenKeyIndex == 1);
        return pStmt;
    }
    
    public PreparedStatement serverPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency) throws SQLException {
        final String nativeSql = this.getProcessEscapeCodesForPrepStmts() ? this.nativeSQL(sql) : sql;
        return ServerPreparedStatement.getInstance(this.getLoadBalanceSafeProxy(), nativeSql, this.getCatalog(), resultSetType, resultSetConcurrency);
    }
    
    public PreparedStatement serverPrepareStatement(final String sql, final int resultSetType, final int resultSetConcurrency, final int resultSetHoldability) throws SQLException {
        if (this.getPedantic() && resultSetHoldability != 1) {
            throw SQLError.createSQLException("HOLD_CUSRORS_OVER_COMMIT is only supported holdability level", "S1009", this.getExceptionInterceptor());
        }
        return this.serverPrepareStatement(sql, resultSetType, resultSetConcurrency);
    }
    
    public PreparedStatement serverPrepareStatement(final String sql, final int[] autoGenKeyIndexes) throws SQLException {
        final com.mysql.jdbc.PreparedStatement pStmt = (com.mysql.jdbc.PreparedStatement)this.serverPrepareStatement(sql);
        pStmt.setRetrieveGeneratedKeys(autoGenKeyIndexes != null && autoGenKeyIndexes.length > 0);
        return pStmt;
    }
    
    public PreparedStatement serverPrepareStatement(final String sql, final String[] autoGenKeyColNames) throws SQLException {
        final com.mysql.jdbc.PreparedStatement pStmt = (com.mysql.jdbc.PreparedStatement)this.serverPrepareStatement(sql);
        pStmt.setRetrieveGeneratedKeys(autoGenKeyColNames != null && autoGenKeyColNames.length > 0);
        return pStmt;
    }
    
    public boolean serverSupportsConvertFn() throws SQLException {
        return this.versionMeetsMinimum(4, 0, 2);
    }
    
    public void setAutoCommit(final boolean autoCommitFlag) throws SQLException {
        synchronized (this.getMutex()) {
            this.checkClosed();
            if (this.connectionLifecycleInterceptors != null) {
                final IterateBlock iter = new IterateBlock(this.connectionLifecycleInterceptors.iterator()) {
                    void forEach(final Object each) throws SQLException {
                        if (!((ConnectionLifecycleInterceptor)each).setAutoCommit(autoCommitFlag)) {
                            this.stopIterating = true;
                        }
                    }
                };
                iter.doForAll();
                if (!iter.fullIteration()) {
                    return;
                }
            }
            if (this.getAutoReconnectForPools()) {
                this.setHighAvailability(true);
            }
            try {
                if (this.transactionsSupported) {
                    boolean needsSetOnServer = true;
                    if (this.getUseLocalSessionState() && this.autoCommit == autoCommitFlag) {
                        needsSetOnServer = false;
                    }
                    else if (!this.getHighAvailability()) {
                        needsSetOnServer = this.getIO().isSetNeededForAutoCommitMode(autoCommitFlag);
                    }
                    this.autoCommit = autoCommitFlag;
                    if (needsSetOnServer) {
                        this.execSQL(null, autoCommitFlag ? "SET autocommit=1" : "SET autocommit=0", -1, null, 1003, 1007, false, this.database, null, false);
                    }
                }
                else {
                    if (!autoCommitFlag && !this.getRelaxAutoCommit()) {
                        throw SQLError.createSQLException("MySQL Versions Older than 3.23.15 do not support transactions", "08003", this.getExceptionInterceptor());
                    }
                    this.autoCommit = autoCommitFlag;
                }
            }
            finally {
                if (this.getAutoReconnectForPools()) {
                    this.setHighAvailability(false);
                }
            }
        }
    }
    
    public void setCatalog(final String catalog) throws SQLException {
        synchronized (this.getMutex()) {
            this.checkClosed();
            if (catalog == null) {
                throw SQLError.createSQLException("Catalog can not be null", "S1009", this.getExceptionInterceptor());
            }
            if (this.connectionLifecycleInterceptors != null) {
                final IterateBlock iter = new IterateBlock(this.connectionLifecycleInterceptors.iterator()) {
                    void forEach(final Object each) throws SQLException {
                        if (!((ConnectionLifecycleInterceptor)each).setCatalog(catalog)) {
                            this.stopIterating = true;
                        }
                    }
                };
                iter.doForAll();
                if (!iter.fullIteration()) {
                    return;
                }
            }
            if (this.getUseLocalSessionState()) {
                if (this.lowerCaseTableNames) {
                    if (this.database.equalsIgnoreCase(catalog)) {
                        return;
                    }
                }
                else if (this.database.equals(catalog)) {
                    return;
                }
            }
            String quotedId = this.dbmd.getIdentifierQuoteString();
            if (quotedId == null || quotedId.equals(" ")) {
                quotedId = "";
            }
            final StringBuffer query = new StringBuffer("USE ");
            query.append(quotedId);
            query.append(catalog);
            query.append(quotedId);
            this.execSQL(null, query.toString(), -1, null, 1003, 1007, false, this.database, null, false);
            this.database = catalog;
        }
    }
    
    public synchronized void setFailedOver(final boolean flag) {
    }
    
    public void setHoldability(final int arg0) throws SQLException {
    }
    
    public void setInGlobalTx(final boolean flag) {
        this.isInGlobalTx = flag;
    }
    
    public void setPreferSlaveDuringFailover(final boolean flag) {
    }
    
    public void setReadInfoMsgEnabled(final boolean flag) {
        this.readInfoMsg = flag;
    }
    
    public void setReadOnly(final boolean readOnlyFlag) throws SQLException {
        this.checkClosed();
        this.setReadOnlyInternal(readOnlyFlag);
    }
    
    public void setReadOnlyInternal(final boolean readOnlyFlag) throws SQLException {
        this.readOnly = readOnlyFlag;
    }
    
    public Savepoint setSavepoint() throws SQLException {
        final MysqlSavepoint savepoint = new MysqlSavepoint(this.getExceptionInterceptor());
        this.setSavepoint(savepoint);
        return savepoint;
    }
    
    private void setSavepoint(final MysqlSavepoint savepoint) throws SQLException {
        if (this.versionMeetsMinimum(4, 0, 14) || this.versionMeetsMinimum(4, 1, 1)) {
            synchronized (this.getMutex()) {
                this.checkClosed();
                final StringBuffer savePointQuery = new StringBuffer("SAVEPOINT ");
                savePointQuery.append('`');
                savePointQuery.append(savepoint.getSavepointName());
                savePointQuery.append('`');
                Statement stmt = null;
                try {
                    stmt = this.getMetadataSafeStatement();
                    stmt.executeUpdate(savePointQuery.toString());
                }
                finally {
                    this.closeStatement(stmt);
                }
            }
            return;
        }
        throw SQLError.notImplemented();
    }
    
    public synchronized Savepoint setSavepoint(final String name) throws SQLException {
        final MysqlSavepoint savepoint = new MysqlSavepoint(name, this.getExceptionInterceptor());
        this.setSavepoint(savepoint);
        return savepoint;
    }
    
    private void setSessionVariables() throws SQLException {
        if (this.versionMeetsMinimum(4, 0, 0) && this.getSessionVariables() != null) {
            final List variablesToSet = StringUtils.split(this.getSessionVariables(), ",", "\"'", "\"'", false);
            final int numVariablesToSet = variablesToSet.size();
            Statement stmt = null;
            try {
                stmt = this.getMetadataSafeStatement();
                for (int i = 0; i < numVariablesToSet; ++i) {
                    final String variableValuePair = variablesToSet.get(i);
                    if (variableValuePair.startsWith("@")) {
                        stmt.executeUpdate("SET " + variableValuePair);
                    }
                    else {
                        stmt.executeUpdate("SET SESSION " + variableValuePair);
                    }
                }
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
    }
    
    public synchronized void setTransactionIsolation(final int level) throws SQLException {
        this.checkClosed();
        if (this.hasIsolationLevels) {
            String sql = null;
            boolean shouldSendSet = false;
            if (this.getAlwaysSendSetIsolation()) {
                shouldSendSet = true;
            }
            else if (level != this.isolationLevel) {
                shouldSendSet = true;
            }
            if (this.getUseLocalSessionState()) {
                shouldSendSet = (this.isolationLevel != level);
            }
            if (shouldSendSet) {
                switch (level) {
                    case 0: {
                        throw SQLError.createSQLException("Transaction isolation level NONE not supported by MySQL", this.getExceptionInterceptor());
                    }
                    case 2: {
                        sql = "SET SESSION TRANSACTION ISOLATION LEVEL READ COMMITTED";
                        break;
                    }
                    case 1: {
                        sql = "SET SESSION TRANSACTION ISOLATION LEVEL READ UNCOMMITTED";
                        break;
                    }
                    case 4: {
                        sql = "SET SESSION TRANSACTION ISOLATION LEVEL REPEATABLE READ";
                        break;
                    }
                    case 8: {
                        sql = "SET SESSION TRANSACTION ISOLATION LEVEL SERIALIZABLE";
                        break;
                    }
                    default: {
                        throw SQLError.createSQLException("Unsupported transaction isolation level '" + level + "'", "S1C00", this.getExceptionInterceptor());
                    }
                }
                this.execSQL(null, sql, -1, null, 1003, 1007, false, this.database, null, false);
                this.isolationLevel = level;
            }
            return;
        }
        throw SQLError.createSQLException("Transaction Isolation Levels are not supported on MySQL versions older than 3.23.36.", "S1C00", this.getExceptionInterceptor());
    }
    
    public synchronized void setTypeMap(final Map map) throws SQLException {
        this.typeMap = map;
    }
    
    private void setupServerForTruncationChecks() throws SQLException {
        if (this.getJdbcCompliantTruncation() && this.versionMeetsMinimum(5, 0, 2)) {
            final String currentSqlMode = this.serverVariables.get("sql_mode");
            final boolean strictTransTablesIsSet = StringUtils.indexOfIgnoreCase(currentSqlMode, "STRICT_TRANS_TABLES") != -1;
            if (currentSqlMode == null || currentSqlMode.length() == 0 || !strictTransTablesIsSet) {
                final StringBuffer commandBuf = new StringBuffer("SET sql_mode='");
                if (currentSqlMode != null && currentSqlMode.length() > 0) {
                    commandBuf.append(currentSqlMode);
                    commandBuf.append(",");
                }
                commandBuf.append("STRICT_TRANS_TABLES'");
                this.execSQL(null, commandBuf.toString(), -1, null, 1003, 1007, false, this.database, null, false);
                this.setJdbcCompliantTruncation(false);
            }
            else if (strictTransTablesIsSet) {
                this.setJdbcCompliantTruncation(false);
            }
        }
    }
    
    public void shutdownServer() throws SQLException {
        try {
            this.io.sendCommand(8, null, null, false, null, 0);
        }
        catch (Exception ex) {
            final SQLException sqlEx = SQLError.createSQLException(Messages.getString("Connection.UnhandledExceptionDuringShutdown"), "S1000", this.getExceptionInterceptor());
            sqlEx.initCause(ex);
            throw sqlEx;
        }
    }
    
    public boolean supportsIsolationLevel() {
        return this.hasIsolationLevels;
    }
    
    public boolean supportsQuotedIdentifiers() {
        return this.hasQuotedIdentifiers;
    }
    
    public boolean supportsTransactions() {
        return this.transactionsSupported;
    }
    
    public void unregisterStatement(final com.mysql.jdbc.Statement stmt) {
        if (this.openStatements != null) {
            synchronized (this.openStatements) {
                this.openStatements.remove(stmt);
            }
        }
    }
    
    public void unsetMaxRows(final com.mysql.jdbc.Statement stmt) throws SQLException {
        synchronized (this.mutex) {
            if (this.statementsUsingMaxRows != null) {
                final Object found = this.statementsUsingMaxRows.remove(stmt);
                if (found != null && this.statementsUsingMaxRows.size() == 0) {
                    this.execSQL(null, "SET OPTION SQL_SELECT_LIMIT=DEFAULT", -1, null, 1003, 1007, false, this.database, null, false);
                    this.maxRowsChanged = false;
                }
            }
        }
    }
    
    public boolean useAnsiQuotedIdentifiers() {
        return this.useAnsiQuotes;
    }
    
    public boolean useMaxRows() {
        synchronized (this.mutex) {
            return this.maxRowsChanged;
        }
    }
    
    public boolean versionMeetsMinimum(final int major, final int minor, final int subminor) throws SQLException {
        this.checkClosed();
        return this.io.versionMeetsMinimum(major, minor, subminor);
    }
    
    public CachedResultSetMetaData getCachedMetaData(final String sql) {
        if (this.resultSetMetadataCache != null) {
            synchronized (this.resultSetMetadataCache) {
                return this.resultSetMetadataCache.get(sql);
            }
        }
        return null;
    }
    
    public void initializeResultsMetadataFromCache(final String sql, CachedResultSetMetaData cachedMetaData, final ResultSetInternalMethods resultSet) throws SQLException {
        if (cachedMetaData == null) {
            cachedMetaData = new CachedResultSetMetaData();
            resultSet.buildIndexMapping();
            resultSet.initializeWithMetadata();
            if (resultSet instanceof UpdatableResultSet) {
                ((UpdatableResultSet)resultSet).checkUpdatability();
            }
            resultSet.populateCachedMetaData(cachedMetaData);
            this.resultSetMetadataCache.put(sql, cachedMetaData);
        }
        else {
            resultSet.initializeFromCachedMetaData(cachedMetaData);
            resultSet.initializeWithMetadata();
            if (resultSet instanceof UpdatableResultSet) {
                ((UpdatableResultSet)resultSet).checkUpdatability();
            }
        }
    }
    
    public String getStatementComment() {
        return this.statementComment;
    }
    
    public void setStatementComment(final String comment) {
        this.statementComment = comment;
    }
    
    public synchronized void reportQueryTime(final long millisOrNanos) {
        ++this.queryTimeCount;
        this.queryTimeSum += millisOrNanos;
        this.queryTimeSumSquares += millisOrNanos * millisOrNanos;
        this.queryTimeMean = (this.queryTimeMean * (this.queryTimeCount - 1L) + millisOrNanos) / this.queryTimeCount;
    }
    
    public synchronized boolean isAbonormallyLongQuery(final long millisOrNanos) {
        if (this.queryTimeCount < 15L) {
            return false;
        }
        final double stddev = Math.sqrt((this.queryTimeSumSquares - this.queryTimeSum * this.queryTimeSum / this.queryTimeCount) / (this.queryTimeCount - 1L));
        return millisOrNanos > this.queryTimeMean + 5.0 * stddev;
    }
    
    public void initializeExtension(final Extension ex) throws SQLException {
        ex.init(this, this.props);
    }
    
    public void transactionBegun() throws SQLException {
        if (this.connectionLifecycleInterceptors != null) {
            final IterateBlock iter = new IterateBlock(this.connectionLifecycleInterceptors.iterator()) {
                void forEach(final Object each) throws SQLException {
                    ((ConnectionLifecycleInterceptor)each).transactionBegun();
                }
            };
            iter.doForAll();
        }
    }
    
    public void transactionCompleted() throws SQLException {
        if (this.connectionLifecycleInterceptors != null) {
            final IterateBlock iter = new IterateBlock(this.connectionLifecycleInterceptors.iterator()) {
                void forEach(final Object each) throws SQLException {
                    ((ConnectionLifecycleInterceptor)each).transactionCompleted();
                }
            };
            iter.doForAll();
        }
    }
    
    public boolean storesLowerCaseTableName() {
        return this.storesLowerCaseTableName;
    }
    
    public ExceptionInterceptor getExceptionInterceptor() {
        return this.exceptionInterceptor;
    }
    
    public boolean getRequiresEscapingEncoder() {
        return this.requiresEscapingEncoder;
    }
    
    static {
        CHARSET_CONVERTER_NOT_AVAILABLE_MARKER = new Object();
        ConnectionImpl.mapTransIsolationNameToValue = null;
        NULL_LOGGER = new NullLogger("MySQL");
        serverCollationByUrl = new HashMap();
        serverConfigByUrl = new HashMap();
        (ConnectionImpl.mapTransIsolationNameToValue = new HashMap(8)).put("READ-UNCOMMITED", Constants.integerValueOf(1));
        ConnectionImpl.mapTransIsolationNameToValue.put("READ-UNCOMMITTED", Constants.integerValueOf(1));
        ConnectionImpl.mapTransIsolationNameToValue.put("READ-COMMITTED", Constants.integerValueOf(2));
        ConnectionImpl.mapTransIsolationNameToValue.put("REPEATABLE-READ", Constants.integerValueOf(4));
        ConnectionImpl.mapTransIsolationNameToValue.put("SERIALIZABLE", Constants.integerValueOf(8));
        if (Util.isJdbc4()) {
            try {
                JDBC_4_CONNECTION_CTOR = Class.forName("com.mysql.jdbc.JDBC4Connection").getConstructor(String.class, Integer.TYPE, Properties.class, String.class, String.class);
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
        JDBC_4_CONNECTION_CTOR = null;
    }
    
    class ExceptionInterceptorChain implements ExceptionInterceptor
    {
        List interceptors;
        
        ExceptionInterceptorChain(final String interceptorClasses) throws SQLException {
            this.interceptors = Util.loadExtensions(ConnectionImpl.this, ConnectionImpl.this.props, interceptorClasses, "Connection.BadExceptionInterceptor", this);
        }
        
        public SQLException interceptException(SQLException sqlEx, final Connection conn) {
            if (this.interceptors != null) {
                final Iterator iter = this.interceptors.iterator();
                while (iter.hasNext()) {
                    sqlEx = iter.next().interceptException(sqlEx, ConnectionImpl.this);
                }
            }
            return sqlEx;
        }
        
        public void destroy() {
            if (this.interceptors != null) {
                final Iterator iter = this.interceptors.iterator();
                while (iter.hasNext()) {
                    iter.next().destroy();
                }
            }
        }
        
        public void init(final Connection conn, final Properties props) throws SQLException {
            if (this.interceptors != null) {
                final Iterator iter = this.interceptors.iterator();
                while (iter.hasNext()) {
                    iter.next().init(conn, props);
                }
            }
        }
    }
    
    class CompoundCacheKey
    {
        String componentOne;
        String componentTwo;
        int hashCode;
        
        CompoundCacheKey(final String partOne, final String partTwo) {
            this.componentOne = partOne;
            this.componentTwo = partTwo;
            this.hashCode = (((this.componentOne != null) ? this.componentOne : "") + this.componentTwo).hashCode();
        }
        
        public boolean equals(final Object obj) {
            if (obj instanceof CompoundCacheKey) {
                final CompoundCacheKey another = (CompoundCacheKey)obj;
                boolean firstPartEqual = false;
                if (this.componentOne == null) {
                    firstPartEqual = (another.componentOne == null);
                }
                else {
                    firstPartEqual = this.componentOne.equals(another.componentOne);
                }
                return firstPartEqual && this.componentTwo.equals(another.componentTwo);
            }
            return false;
        }
        
        public int hashCode() {
            return this.hashCode;
        }
    }
}
