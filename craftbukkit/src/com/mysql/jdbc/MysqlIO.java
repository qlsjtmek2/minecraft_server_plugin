// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.io.EOFException;
import com.mysql.jdbc.profiler.ProfilerEventHandler;
import com.mysql.jdbc.exceptions.MySQLStatementCancelledException;
import com.mysql.jdbc.exceptions.MySQLTimeoutException;
import com.mysql.jdbc.profiler.ProfilerEvent;
import java.net.SocketException;
import java.sql.Statement;
import java.sql.ResultSet;
import com.mysql.jdbc.util.ResultSetUtil;
import java.util.Iterator;
import java.sql.SQLException;
import java.io.IOException;
import java.io.BufferedInputStream;
import com.mysql.jdbc.util.ReadAheadInputStream;
import java.util.Properties;
import java.util.List;
import java.util.Calendar;
import java.lang.ref.SoftReference;
import java.net.Socket;
import java.util.LinkedList;
import java.io.InputStream;
import java.util.zip.Deflater;
import java.io.BufferedOutputStream;

public class MysqlIO
{
    private static final int UTF8_CHARSET_INDEX = 33;
    private static final String CODE_PAGE_1252 = "Cp1252";
    protected static final int NULL_LENGTH = -1;
    protected static final int COMP_HEADER_LENGTH = 3;
    protected static final int MIN_COMPRESS_LEN = 50;
    protected static final int HEADER_LENGTH = 4;
    protected static final int AUTH_411_OVERHEAD = 33;
    private static int maxBufferSize;
    private static final int CLIENT_COMPRESS = 32;
    protected static final int CLIENT_CONNECT_WITH_DB = 8;
    private static final int CLIENT_FOUND_ROWS = 2;
    private static final int CLIENT_LOCAL_FILES = 128;
    private static final int CLIENT_LONG_FLAG = 4;
    private static final int CLIENT_LONG_PASSWORD = 1;
    private static final int CLIENT_PROTOCOL_41 = 512;
    private static final int CLIENT_INTERACTIVE = 1024;
    protected static final int CLIENT_SSL = 2048;
    private static final int CLIENT_TRANSACTIONS = 8192;
    protected static final int CLIENT_RESERVED = 16384;
    protected static final int CLIENT_SECURE_CONNECTION = 32768;
    private static final int CLIENT_MULTI_QUERIES = 65536;
    private static final int CLIENT_MULTI_RESULTS = 131072;
    private static final int SERVER_STATUS_IN_TRANS = 1;
    private static final int SERVER_STATUS_AUTOCOMMIT = 2;
    static final int SERVER_MORE_RESULTS_EXISTS = 8;
    private static final int SERVER_QUERY_NO_GOOD_INDEX_USED = 16;
    private static final int SERVER_QUERY_NO_INDEX_USED = 32;
    private static final int SERVER_QUERY_WAS_SLOW = 2048;
    private static final int SERVER_STATUS_CURSOR_EXISTS = 64;
    private static final String FALSE_SCRAMBLE = "xxxxxxxx";
    protected static final int MAX_QUERY_SIZE_TO_LOG = 1024;
    protected static final int MAX_QUERY_SIZE_TO_EXPLAIN = 1048576;
    protected static final int INITIAL_PACKET_SIZE = 1024;
    private static String jvmPlatformCharset;
    protected static final String ZERO_DATE_VALUE_MARKER = "0000-00-00";
    protected static final String ZERO_DATETIME_VALUE_MARKER = "0000-00-00 00:00:00";
    private static final int MAX_PACKET_DUMP_LENGTH = 1024;
    private boolean packetSequenceReset;
    protected int serverCharsetIndex;
    private Buffer reusablePacket;
    private Buffer sendPacket;
    private Buffer sharedSendPacket;
    protected BufferedOutputStream mysqlOutput;
    protected MySQLConnection connection;
    private Deflater deflater;
    protected InputStream mysqlInput;
    private LinkedList packetDebugRingBuffer;
    private RowData streamingData;
    protected Socket mysqlConnection;
    private SocketFactory socketFactory;
    private SoftReference loadFileBufRef;
    private SoftReference splitBufRef;
    protected String host;
    protected String seed;
    private String serverVersion;
    private String socketFactoryClassName;
    private byte[] packetHeaderBuf;
    private boolean colDecimalNeedsBump;
    private boolean hadWarnings;
    private boolean has41NewNewProt;
    private boolean hasLongColumnInfo;
    private boolean isInteractiveClient;
    private boolean logSlowQueries;
    private boolean platformDbCharsetMatches;
    private boolean profileSql;
    private boolean queryBadIndexUsed;
    private boolean queryNoIndexUsed;
    private boolean serverQueryWasSlow;
    private boolean use41Extensions;
    private boolean useCompression;
    private boolean useNewLargePackets;
    private boolean useNewUpdateCounts;
    private byte packetSequence;
    private byte readPacketSequence;
    private boolean checkPacketSequence;
    private byte protocolVersion;
    private int maxAllowedPacket;
    protected int maxThreeBytes;
    protected int port;
    protected int serverCapabilities;
    private int serverMajorVersion;
    private int serverMinorVersion;
    private int oldServerStatus;
    private int serverStatus;
    private int serverSubMinorVersion;
    private int warningCount;
    protected long clientParam;
    protected long lastPacketSentTimeMs;
    protected long lastPacketReceivedTimeMs;
    private boolean traceProtocol;
    private boolean enablePacketDebug;
    private Calendar sessionCalendar;
    private boolean useConnectWithDb;
    private boolean needToGrabQueryFromPacket;
    private boolean autoGenerateTestcaseScript;
    private long threadId;
    private boolean useNanosForElapsedTime;
    private long slowQueryThreshold;
    private String queryTimingUnits;
    private boolean useDirectRowUnpack;
    private int useBufferRowSizeThreshold;
    private int commandCount;
    private List statementInterceptors;
    private ExceptionInterceptor exceptionInterceptor;
    private int statementExecutionDepth;
    private boolean useAutoSlowLog;
    
    public MysqlIO(final String host, final int port, final Properties props, final String socketFactoryClassName, final MySQLConnection conn, final int socketTimeout, final int useBufferRowSizeThreshold) throws IOException, SQLException {
        this.packetSequenceReset = false;
        this.reusablePacket = null;
        this.sendPacket = null;
        this.sharedSendPacket = null;
        this.mysqlOutput = null;
        this.deflater = null;
        this.mysqlInput = null;
        this.packetDebugRingBuffer = null;
        this.streamingData = null;
        this.mysqlConnection = null;
        this.socketFactory = null;
        this.host = null;
        this.serverVersion = null;
        this.socketFactoryClassName = null;
        this.packetHeaderBuf = new byte[4];
        this.colDecimalNeedsBump = false;
        this.hadWarnings = false;
        this.has41NewNewProt = false;
        this.hasLongColumnInfo = false;
        this.isInteractiveClient = false;
        this.logSlowQueries = false;
        this.platformDbCharsetMatches = true;
        this.profileSql = false;
        this.queryBadIndexUsed = false;
        this.queryNoIndexUsed = false;
        this.serverQueryWasSlow = false;
        this.use41Extensions = false;
        this.useCompression = false;
        this.useNewLargePackets = false;
        this.useNewUpdateCounts = false;
        this.packetSequence = 0;
        this.readPacketSequence = -1;
        this.checkPacketSequence = false;
        this.protocolVersion = 0;
        this.maxAllowedPacket = 1048576;
        this.maxThreeBytes = 16581375;
        this.port = 3306;
        this.serverMajorVersion = 0;
        this.serverMinorVersion = 0;
        this.oldServerStatus = 0;
        this.serverStatus = 0;
        this.serverSubMinorVersion = 0;
        this.warningCount = 0;
        this.clientParam = 0L;
        this.lastPacketSentTimeMs = 0L;
        this.lastPacketReceivedTimeMs = 0L;
        this.traceProtocol = false;
        this.enablePacketDebug = false;
        this.useDirectRowUnpack = true;
        this.commandCount = 0;
        this.statementExecutionDepth = 0;
        this.connection = conn;
        if (this.connection.getEnablePacketDebug()) {
            this.packetDebugRingBuffer = new LinkedList();
        }
        this.traceProtocol = this.connection.getTraceProtocol();
        this.useAutoSlowLog = this.connection.getAutoSlowLog();
        this.useBufferRowSizeThreshold = useBufferRowSizeThreshold;
        this.useDirectRowUnpack = this.connection.getUseDirectRowUnpack();
        this.logSlowQueries = this.connection.getLogSlowQueries();
        this.reusablePacket = new Buffer(1024);
        this.sendPacket = new Buffer(1024);
        this.port = port;
        this.host = host;
        this.socketFactoryClassName = socketFactoryClassName;
        this.socketFactory = this.createSocketFactory();
        this.exceptionInterceptor = this.connection.getExceptionInterceptor();
        try {
            this.mysqlConnection = this.socketFactory.connect(this.host, this.port, props);
            if (socketTimeout != 0) {
                try {
                    this.mysqlConnection.setSoTimeout(socketTimeout);
                }
                catch (Exception ex) {}
            }
            this.mysqlConnection = this.socketFactory.beforeHandshake();
            if (this.connection.getUseReadAheadInput()) {
                this.mysqlInput = new ReadAheadInputStream(this.mysqlConnection.getInputStream(), 16384, this.connection.getTraceProtocol(), this.connection.getLog());
            }
            else if (this.connection.useUnbufferedInput()) {
                this.mysqlInput = this.mysqlConnection.getInputStream();
            }
            else {
                this.mysqlInput = new BufferedInputStream(this.mysqlConnection.getInputStream(), 16384);
            }
            this.mysqlOutput = new BufferedOutputStream(this.mysqlConnection.getOutputStream(), 16384);
            this.isInteractiveClient = this.connection.getInteractiveClient();
            this.profileSql = this.connection.getProfileSql();
            this.sessionCalendar = Calendar.getInstance();
            this.autoGenerateTestcaseScript = this.connection.getAutoGenerateTestcaseScript();
            this.needToGrabQueryFromPacket = (this.profileSql || this.logSlowQueries || this.autoGenerateTestcaseScript);
            if (this.connection.getUseNanosForElapsedTime() && Util.nanoTimeAvailable()) {
                this.useNanosForElapsedTime = true;
                this.queryTimingUnits = Messages.getString("Nanoseconds");
            }
            else {
                this.queryTimingUnits = Messages.getString("Milliseconds");
            }
            if (this.connection.getLogSlowQueries()) {
                this.calculateSlowQueryThreshold();
            }
        }
        catch (IOException ioEx) {
            throw SQLError.createCommunicationsException(this.connection, 0L, 0L, ioEx, this.getExceptionInterceptor());
        }
    }
    
    public boolean hasLongColumnInfo() {
        return this.hasLongColumnInfo;
    }
    
    protected boolean isDataAvailable() throws SQLException {
        try {
            return this.mysqlInput.available() > 0;
        }
        catch (IOException ioEx) {
            throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, this.getExceptionInterceptor());
        }
    }
    
    protected long getLastPacketSentTimeMs() {
        return this.lastPacketSentTimeMs;
    }
    
    protected long getLastPacketReceivedTimeMs() {
        return this.lastPacketReceivedTimeMs;
    }
    
    protected ResultSetImpl getResultSet(final StatementImpl callingStatement, final long columnCount, final int maxRows, final int resultSetType, final int resultSetConcurrency, final boolean streamResults, final String catalog, final boolean isBinaryEncoded, final Field[] metadataFromCache) throws SQLException {
        Field[] fields = null;
        if (metadataFromCache == null) {
            fields = new Field[(int)columnCount];
            for (int i = 0; i < columnCount; ++i) {
                Buffer fieldPacket = null;
                fieldPacket = this.readPacket();
                fields[i] = this.unpackField(fieldPacket, false);
            }
        }
        else {
            for (int i = 0; i < columnCount; ++i) {
                this.skipPacket();
            }
        }
        final Buffer packet = this.reuseAndReadPacket(this.reusablePacket);
        this.readServerStatusForResultSets(packet);
        if (this.connection.versionMeetsMinimum(5, 0, 2) && this.connection.getUseCursorFetch() && isBinaryEncoded && callingStatement != null && callingStatement.getFetchSize() != 0 && callingStatement.getResultSetType() == 1003) {
            final ServerPreparedStatement prepStmt = (ServerPreparedStatement)callingStatement;
            boolean usingCursor = true;
            if (this.connection.versionMeetsMinimum(5, 0, 5)) {
                usingCursor = ((this.serverStatus & 0x40) != 0x0);
            }
            if (usingCursor) {
                final RowData rows = new RowDataCursor(this, prepStmt, fields);
                final ResultSetImpl rs = this.buildResultSetWithRows(callingStatement, catalog, fields, rows, resultSetType, resultSetConcurrency, isBinaryEncoded);
                if (usingCursor) {
                    rs.setFetchSize(callingStatement.getFetchSize());
                }
                return rs;
            }
        }
        RowData rowData = null;
        if (!streamResults) {
            rowData = this.readSingleRowSet(columnCount, maxRows, resultSetConcurrency, isBinaryEncoded, (metadataFromCache == null) ? fields : metadataFromCache);
        }
        else {
            rowData = new RowDataDynamic(this, (int)columnCount, (metadataFromCache == null) ? fields : metadataFromCache, isBinaryEncoded);
            this.streamingData = rowData;
        }
        final ResultSetImpl rs2 = this.buildResultSetWithRows(callingStatement, catalog, (metadataFromCache == null) ? fields : metadataFromCache, rowData, resultSetType, resultSetConcurrency, isBinaryEncoded);
        return rs2;
    }
    
    protected final void forceClose() {
        try {
            try {
                if (this.mysqlInput != null) {
                    this.mysqlInput.close();
                }
            }
            finally {
                if (!this.mysqlConnection.isClosed() && !this.mysqlConnection.isInputShutdown()) {
                    this.mysqlConnection.shutdownInput();
                }
            }
        }
        catch (IOException ioEx) {
            this.mysqlInput = null;
        }
        try {
            try {
                if (this.mysqlOutput != null) {
                    this.mysqlOutput.close();
                }
            }
            finally {
                if (!this.mysqlConnection.isClosed() && !this.mysqlConnection.isOutputShutdown()) {
                    this.mysqlConnection.shutdownOutput();
                }
            }
        }
        catch (IOException ioEx) {
            this.mysqlOutput = null;
        }
        try {
            if (this.mysqlConnection != null) {
                this.mysqlConnection.close();
            }
        }
        catch (IOException ioEx) {
            this.mysqlConnection = null;
        }
    }
    
    protected final void skipPacket() throws SQLException {
        try {
            final int lengthRead = this.readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
            if (lengthRead < 4) {
                this.forceClose();
                throw new IOException(Messages.getString("MysqlIO.1"));
            }
            final int packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
            if (this.traceProtocol) {
                final StringBuffer traceMessageBuf = new StringBuffer();
                traceMessageBuf.append(Messages.getString("MysqlIO.2"));
                traceMessageBuf.append(packetLength);
                traceMessageBuf.append(Messages.getString("MysqlIO.3"));
                traceMessageBuf.append(StringUtils.dumpAsHex(this.packetHeaderBuf, 4));
                this.connection.getLog().logTrace(traceMessageBuf.toString());
            }
            final byte multiPacketSeq = this.packetHeaderBuf[3];
            if (!this.packetSequenceReset) {
                if (this.enablePacketDebug && this.checkPacketSequence) {
                    this.checkPacketSequencing(multiPacketSeq);
                }
            }
            else {
                this.packetSequenceReset = false;
            }
            this.readPacketSequence = multiPacketSeq;
            this.skipFully(this.mysqlInput, packetLength);
        }
        catch (IOException ioEx) {
            throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, this.getExceptionInterceptor());
        }
        catch (OutOfMemoryError oom) {
            try {
                this.connection.realClose(false, false, true, oom);
            }
            finally {
                throw oom;
            }
        }
    }
    
    protected final Buffer readPacket() throws SQLException {
        try {
            final int lengthRead = this.readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
            if (lengthRead < 4) {
                this.forceClose();
                throw new IOException(Messages.getString("MysqlIO.1"));
            }
            final int packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
            if (packetLength > this.maxAllowedPacket) {
                throw new PacketTooBigException(packetLength, this.maxAllowedPacket);
            }
            if (this.traceProtocol) {
                final StringBuffer traceMessageBuf = new StringBuffer();
                traceMessageBuf.append(Messages.getString("MysqlIO.2"));
                traceMessageBuf.append(packetLength);
                traceMessageBuf.append(Messages.getString("MysqlIO.3"));
                traceMessageBuf.append(StringUtils.dumpAsHex(this.packetHeaderBuf, 4));
                this.connection.getLog().logTrace(traceMessageBuf.toString());
            }
            final byte multiPacketSeq = this.packetHeaderBuf[3];
            if (!this.packetSequenceReset) {
                if (this.enablePacketDebug && this.checkPacketSequence) {
                    this.checkPacketSequencing(multiPacketSeq);
                }
            }
            else {
                this.packetSequenceReset = false;
            }
            this.readPacketSequence = multiPacketSeq;
            final byte[] buffer = new byte[packetLength + 1];
            final int numBytesRead = this.readFully(this.mysqlInput, buffer, 0, packetLength);
            if (numBytesRead != packetLength) {
                throw new IOException("Short read, expected " + packetLength + " bytes, only read " + numBytesRead);
            }
            buffer[packetLength] = 0;
            final Buffer packet = new Buffer(buffer);
            packet.setBufLength(packetLength + 1);
            if (this.traceProtocol) {
                final StringBuffer traceMessageBuf2 = new StringBuffer();
                traceMessageBuf2.append(Messages.getString("MysqlIO.4"));
                traceMessageBuf2.append(getPacketDumpToLog(packet, packetLength));
                this.connection.getLog().logTrace(traceMessageBuf2.toString());
            }
            if (this.enablePacketDebug) {
                this.enqueuePacketForDebugging(false, false, 0, this.packetHeaderBuf, packet);
            }
            if (this.connection.getMaintainTimeStats()) {
                this.lastPacketReceivedTimeMs = System.currentTimeMillis();
            }
            return packet;
        }
        catch (IOException ioEx) {
            throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, this.getExceptionInterceptor());
        }
        catch (OutOfMemoryError oom) {
            try {
                this.connection.realClose(false, false, true, oom);
            }
            finally {
                throw oom;
            }
        }
    }
    
    protected final Field unpackField(final Buffer packet, final boolean extractDefaultValues) throws SQLException {
        if (this.use41Extensions) {
            if (this.has41NewNewProt) {
                int catalogNameStart = packet.getPosition() + 1;
                final int catalogNameLength = packet.fastSkipLenString();
                catalogNameStart = this.adjustStartForFieldLength(catalogNameStart, catalogNameLength);
            }
            int databaseNameStart = packet.getPosition() + 1;
            final int databaseNameLength = packet.fastSkipLenString();
            databaseNameStart = this.adjustStartForFieldLength(databaseNameStart, databaseNameLength);
            int tableNameStart = packet.getPosition() + 1;
            final int tableNameLength = packet.fastSkipLenString();
            tableNameStart = this.adjustStartForFieldLength(tableNameStart, tableNameLength);
            int originalTableNameStart = packet.getPosition() + 1;
            final int originalTableNameLength = packet.fastSkipLenString();
            originalTableNameStart = this.adjustStartForFieldLength(originalTableNameStart, originalTableNameLength);
            int nameStart = packet.getPosition() + 1;
            final int nameLength = packet.fastSkipLenString();
            nameStart = this.adjustStartForFieldLength(nameStart, nameLength);
            int originalColumnNameStart = packet.getPosition() + 1;
            final int originalColumnNameLength = packet.fastSkipLenString();
            originalColumnNameStart = this.adjustStartForFieldLength(originalColumnNameStart, originalColumnNameLength);
            packet.readByte();
            final short charSetNumber = (short)packet.readInt();
            long colLength = 0L;
            if (this.has41NewNewProt) {
                colLength = packet.readLong();
            }
            else {
                colLength = packet.readLongInt();
            }
            final int colType = packet.readByte() & 0xFF;
            short colFlag = 0;
            if (this.hasLongColumnInfo) {
                colFlag = (short)packet.readInt();
            }
            else {
                colFlag = (short)(packet.readByte() & 0xFF);
            }
            final int colDecimals = packet.readByte() & 0xFF;
            int defaultValueStart = -1;
            int defaultValueLength = -1;
            if (extractDefaultValues) {
                defaultValueStart = packet.getPosition() + 1;
                defaultValueLength = packet.fastSkipLenString();
            }
            final Field field = new Field(this.connection, packet.getByteBuffer(), databaseNameStart, databaseNameLength, tableNameStart, tableNameLength, originalTableNameStart, originalTableNameLength, nameStart, nameLength, originalColumnNameStart, originalColumnNameLength, colLength, colType, colFlag, colDecimals, defaultValueStart, defaultValueLength, charSetNumber);
            return field;
        }
        int tableNameStart2 = packet.getPosition() + 1;
        final int tableNameLength2 = packet.fastSkipLenString();
        tableNameStart2 = this.adjustStartForFieldLength(tableNameStart2, tableNameLength2);
        int nameStart2 = packet.getPosition() + 1;
        final int nameLength2 = packet.fastSkipLenString();
        nameStart2 = this.adjustStartForFieldLength(nameStart2, nameLength2);
        final int colLength2 = packet.readnBytes();
        final int colType2 = packet.readnBytes();
        packet.readByte();
        short colFlag2 = 0;
        if (this.hasLongColumnInfo) {
            colFlag2 = (short)packet.readInt();
        }
        else {
            colFlag2 = (short)(packet.readByte() & 0xFF);
        }
        int colDecimals2 = packet.readByte() & 0xFF;
        if (this.colDecimalNeedsBump) {
            ++colDecimals2;
        }
        final Field field2 = new Field(this.connection, packet.getByteBuffer(), nameStart2, nameLength2, tableNameStart2, tableNameLength2, colLength2, colType2, colFlag2, colDecimals2);
        return field2;
    }
    
    private int adjustStartForFieldLength(final int nameStart, final int nameLength) {
        if (nameLength < 251) {
            return nameStart;
        }
        if (nameLength >= 251 && nameLength < 65536) {
            return nameStart + 2;
        }
        if (nameLength >= 65536 && nameLength < 16777216) {
            return nameStart + 3;
        }
        return nameStart + 8;
    }
    
    protected boolean isSetNeededForAutoCommitMode(final boolean autoCommitFlag) {
        if (!this.use41Extensions || !this.connection.getElideSetAutoCommits()) {
            return true;
        }
        final boolean autoCommitModeOnServer = (this.serverStatus & 0x2) != 0x0;
        if (!autoCommitFlag && this.versionMeetsMinimum(5, 0, 0)) {
            final boolean inTransactionOnServer = (this.serverStatus & 0x1) != 0x0;
            return !inTransactionOnServer;
        }
        return autoCommitModeOnServer != autoCommitFlag;
    }
    
    protected boolean inTransactionOnServer() {
        return (this.serverStatus & 0x1) != 0x0;
    }
    
    protected void changeUser(final String userName, final String password, final String database) throws SQLException {
        this.packetSequence = -1;
        final int passwordLength = 16;
        final int userLength = (userName != null) ? userName.length() : 0;
        final int databaseLength = (database != null) ? database.length() : 0;
        final int packLength = (userLength + passwordLength + databaseLength) * 2 + 7 + 4 + 33;
        if ((this.serverCapabilities & 0x8000) != 0x0) {
            final Buffer changeUserPacket = new Buffer(packLength + 1);
            changeUserPacket.writeByte((byte)17);
            if (this.versionMeetsMinimum(4, 1, 1)) {
                this.secureAuth411(changeUserPacket, packLength, userName, password, database, false);
            }
            else {
                this.secureAuth(changeUserPacket, packLength, userName, password, database, false);
            }
        }
        else {
            final Buffer packet = new Buffer(packLength);
            packet.writeByte((byte)17);
            packet.writeString(userName);
            if (this.protocolVersion > 9) {
                packet.writeString(Util.newCrypt(password, this.seed));
            }
            else {
                packet.writeString(Util.oldCrypt(password, this.seed));
            }
            final boolean localUseConnectWithDb = this.useConnectWithDb && database != null && database.length() > 0;
            if (localUseConnectWithDb) {
                packet.writeString(database);
            }
            this.send(packet, packet.getPosition());
            this.checkErrorPacket();
            if (!localUseConnectWithDb) {
                this.changeDatabaseTo(database);
            }
        }
    }
    
    protected Buffer checkErrorPacket() throws SQLException {
        return this.checkErrorPacket(-1);
    }
    
    protected void checkForCharsetMismatch() {
        if (this.connection.getUseUnicode() && this.connection.getEncoding() != null) {
            String encodingToCheck = MysqlIO.jvmPlatformCharset;
            if (encodingToCheck == null) {
                encodingToCheck = System.getProperty("file.encoding");
            }
            if (encodingToCheck == null) {
                this.platformDbCharsetMatches = false;
            }
            else {
                this.platformDbCharsetMatches = encodingToCheck.equals(this.connection.getEncoding());
            }
        }
    }
    
    protected void clearInputStream() throws SQLException {
        try {
            for (int len = this.mysqlInput.available(); len > 0; len = this.mysqlInput.available()) {
                this.mysqlInput.skip(len);
            }
        }
        catch (IOException ioEx) {
            throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, this.getExceptionInterceptor());
        }
    }
    
    protected void resetReadPacketSequence() {
        this.readPacketSequence = 0;
    }
    
    protected void dumpPacketRingBuffer() throws SQLException {
        if (this.packetDebugRingBuffer != null && this.connection.getEnablePacketDebug()) {
            final StringBuffer dumpBuffer = new StringBuffer();
            dumpBuffer.append("Last " + this.packetDebugRingBuffer.size() + " packets received from server, from oldest->newest:\n");
            dumpBuffer.append("\n");
            final Iterator ringBufIter = this.packetDebugRingBuffer.iterator();
            while (ringBufIter.hasNext()) {
                dumpBuffer.append(ringBufIter.next());
                dumpBuffer.append("\n");
            }
            this.connection.getLog().logTrace(dumpBuffer.toString());
        }
    }
    
    protected void explainSlowQuery(final byte[] querySQL, final String truncatedQuery) throws SQLException {
        if (StringUtils.startsWithIgnoreCaseAndWs(truncatedQuery, "SELECT")) {
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                stmt = (PreparedStatement)this.connection.clientPrepareStatement("EXPLAIN ?");
                stmt.setBytesNoEscapeNoQuotes(1, querySQL);
                rs = stmt.executeQuery();
                final StringBuffer explainResults = new StringBuffer(Messages.getString("MysqlIO.8") + truncatedQuery + Messages.getString("MysqlIO.9"));
                ResultSetUtil.appendResultSetSlashGStyle(explainResults, rs);
                this.connection.getLog().logWarn(explainResults.toString());
            }
            catch (SQLException sqlEx) {}
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
    }
    
    static int getMaxBuf() {
        return MysqlIO.maxBufferSize;
    }
    
    final int getServerMajorVersion() {
        return this.serverMajorVersion;
    }
    
    final int getServerMinorVersion() {
        return this.serverMinorVersion;
    }
    
    final int getServerSubMinorVersion() {
        return this.serverSubMinorVersion;
    }
    
    String getServerVersion() {
        return this.serverVersion;
    }
    
    void doHandshake(final String user, final String password, final String database) throws SQLException {
        this.checkPacketSequence = false;
        this.readPacketSequence = 0;
        final Buffer buf = this.readPacket();
        this.protocolVersion = buf.readByte();
        if (this.protocolVersion == -1) {
            try {
                this.mysqlConnection.close();
            }
            catch (Exception ex) {}
            int errno = 2000;
            errno = buf.readInt();
            final String serverErrorMessage = buf.readString("ASCII", this.getExceptionInterceptor());
            final StringBuffer errorBuf = new StringBuffer(Messages.getString("MysqlIO.10"));
            errorBuf.append(serverErrorMessage);
            errorBuf.append("\"");
            final String xOpen = SQLError.mysqlToSqlState(errno, this.connection.getUseSqlStateCodes());
            throw SQLError.createSQLException(SQLError.get(xOpen) + ", " + errorBuf.toString(), xOpen, errno, this.getExceptionInterceptor());
        }
        this.serverVersion = buf.readString("ASCII", this.getExceptionInterceptor());
        int point = this.serverVersion.indexOf(46);
        if (point != -1) {
            try {
                final int n = Integer.parseInt(this.serverVersion.substring(0, point));
                this.serverMajorVersion = n;
            }
            catch (NumberFormatException ex2) {}
            String remaining = this.serverVersion.substring(point + 1, this.serverVersion.length());
            point = remaining.indexOf(46);
            if (point != -1) {
                try {
                    final int n2 = Integer.parseInt(remaining.substring(0, point));
                    this.serverMinorVersion = n2;
                }
                catch (NumberFormatException ex3) {}
                int pos;
                for (remaining = remaining.substring(point + 1, remaining.length()), pos = 0; pos < remaining.length() && remaining.charAt(pos) >= '0'; ++pos) {
                    if (remaining.charAt(pos) > '9') {
                        break;
                    }
                }
                try {
                    final int n3 = Integer.parseInt(remaining.substring(0, pos));
                    this.serverSubMinorVersion = n3;
                }
                catch (NumberFormatException ex4) {}
            }
        }
        if (this.versionMeetsMinimum(4, 0, 8)) {
            this.maxThreeBytes = 16777215;
            this.useNewLargePackets = true;
        }
        else {
            this.maxThreeBytes = 16581375;
            this.useNewLargePackets = false;
        }
        this.colDecimalNeedsBump = this.versionMeetsMinimum(3, 23, 0);
        this.colDecimalNeedsBump = !this.versionMeetsMinimum(3, 23, 15);
        this.useNewUpdateCounts = this.versionMeetsMinimum(3, 22, 5);
        this.threadId = buf.readLong();
        this.seed = buf.readString("ASCII", this.getExceptionInterceptor());
        this.serverCapabilities = 0;
        if (buf.getPosition() < buf.getBufLength()) {
            this.serverCapabilities = buf.readInt();
        }
        if (this.versionMeetsMinimum(4, 1, 1) || (this.protocolVersion > 9 && (this.serverCapabilities & 0x200) != 0x0)) {
            final int position = buf.getPosition();
            this.serverCharsetIndex = (buf.readByte() & 0xFF);
            this.serverStatus = buf.readInt();
            this.checkTransactionState(0);
            buf.setPosition(position + 16);
            final String seedPart2 = buf.readString("ASCII", this.getExceptionInterceptor());
            final StringBuffer newSeed = new StringBuffer(20);
            newSeed.append(this.seed);
            newSeed.append(seedPart2);
            this.seed = newSeed.toString();
        }
        if ((this.serverCapabilities & 0x20) != 0x0 && this.connection.getUseCompression()) {
            this.clientParam |= 0x20L;
        }
        this.useConnectWithDb = (database != null && database.length() > 0 && !this.connection.getCreateDatabaseIfNotExist());
        if (this.useConnectWithDb) {
            this.clientParam |= 0x8L;
        }
        if ((this.serverCapabilities & 0x800) == 0x0 && this.connection.getUseSSL()) {
            if (this.connection.getRequireSSL()) {
                this.connection.close();
                this.forceClose();
                throw SQLError.createSQLException(Messages.getString("MysqlIO.15"), "08001", this.getExceptionInterceptor());
            }
            this.connection.setUseSSL(false);
        }
        if ((this.serverCapabilities & 0x4) != 0x0) {
            this.clientParam |= 0x4L;
            this.hasLongColumnInfo = true;
        }
        if (!this.connection.getUseAffectedRows()) {
            this.clientParam |= 0x2L;
        }
        if (this.connection.getAllowLoadLocalInfile()) {
            this.clientParam |= 0x80L;
        }
        if (this.isInteractiveClient) {
            this.clientParam |= 0x400L;
        }
        if (this.protocolVersion > 9) {
            this.clientParam |= 0x1L;
        }
        else {
            this.clientParam &= 0xFFFFFFFFFFFFFFFEL;
        }
        if (this.versionMeetsMinimum(4, 1, 0) || (this.protocolVersion > 9 && (this.serverCapabilities & 0x4000) != 0x0)) {
            if (this.versionMeetsMinimum(4, 1, 1) || (this.protocolVersion > 9 && (this.serverCapabilities & 0x200) != 0x0)) {
                this.clientParam |= 0x200L;
                this.has41NewNewProt = true;
                this.clientParam |= 0x2000L;
                this.clientParam |= 0x20000L;
                if (this.connection.getAllowMultiQueries()) {
                    this.clientParam |= 0x10000L;
                }
            }
            else {
                this.clientParam |= 0x4000L;
                this.has41NewNewProt = false;
            }
            this.use41Extensions = true;
        }
        final int passwordLength = 16;
        final int userLength = (user != null) ? user.length() : 0;
        final int databaseLength = (database != null) ? database.length() : 0;
        final int packLength = (userLength + passwordLength + databaseLength) * 2 + 7 + 4 + 33;
        Buffer packet = null;
        if (!this.connection.getUseSSL()) {
            if ((this.serverCapabilities & 0x8000) != 0x0) {
                this.clientParam |= 0x8000L;
                if (this.versionMeetsMinimum(4, 1, 1) || (this.protocolVersion > 9 && (this.serverCapabilities & 0x200) != 0x0)) {
                    this.secureAuth411(null, packLength, user, password, database, true);
                }
                else {
                    this.secureAuth(null, packLength, user, password, database, true);
                }
            }
            else {
                packet = new Buffer(packLength);
                if ((this.clientParam & 0x4000L) != 0x0L) {
                    if (this.versionMeetsMinimum(4, 1, 1) || (this.protocolVersion > 9 && (this.serverCapabilities & 0x200) != 0x0)) {
                        packet.writeLong(this.clientParam);
                        packet.writeLong(this.maxThreeBytes);
                        packet.writeByte((byte)8);
                        packet.writeBytesNoNull(new byte[23]);
                    }
                    else {
                        packet.writeLong(this.clientParam);
                        packet.writeLong(this.maxThreeBytes);
                    }
                }
                else {
                    packet.writeInt((int)this.clientParam);
                    packet.writeLongInt(this.maxThreeBytes);
                }
                packet.writeString(user, "Cp1252", this.connection);
                if (this.protocolVersion > 9) {
                    packet.writeString(Util.newCrypt(password, this.seed), "Cp1252", this.connection);
                }
                else {
                    packet.writeString(Util.oldCrypt(password, this.seed), "Cp1252", this.connection);
                }
                if (this.useConnectWithDb) {
                    packet.writeString(database, "Cp1252", this.connection);
                }
                this.send(packet, packet.getPosition());
            }
        }
        else {
            this.negotiateSSLConnection(user, password, database, packLength);
        }
        if (!this.versionMeetsMinimum(4, 1, 1) && this.protocolVersion > 9 && (this.serverCapabilities & 0x200) != 0x0) {
            this.checkErrorPacket();
        }
        if ((this.serverCapabilities & 0x20) != 0x0 && this.connection.getUseCompression()) {
            this.deflater = new Deflater();
            this.useCompression = true;
            this.mysqlInput = new CompressedInputStream(this.connection, this.mysqlInput);
        }
        if (!this.useConnectWithDb) {
            this.changeDatabaseTo(database);
        }
        try {
            this.mysqlConnection = this.socketFactory.afterHandshake();
        }
        catch (IOException ioEx) {
            throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, this.getExceptionInterceptor());
        }
    }
    
    private void changeDatabaseTo(final String database) throws SQLException {
        if (database == null || database.length() == 0) {
            return;
        }
        try {
            this.sendCommand(2, database, null, false, null, 0);
        }
        catch (Exception ex) {
            if (!this.connection.getCreateDatabaseIfNotExist()) {
                throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ex, this.getExceptionInterceptor());
            }
            this.sendCommand(3, "CREATE DATABASE IF NOT EXISTS " + database, null, false, null, 0);
            this.sendCommand(2, database, null, false, null, 0);
        }
    }
    
    final ResultSetRow nextRow(final Field[] fields, final int columnCount, final boolean isBinaryEncoded, final int resultSetConcurrency, final boolean useBufferRowIfPossible, boolean useBufferRowExplicit, final boolean canReuseRowPacketForBufferRow, final Buffer existingRowPacket) throws SQLException {
        if (this.useDirectRowUnpack && existingRowPacket == null && !isBinaryEncoded && !useBufferRowIfPossible && !useBufferRowExplicit) {
            return this.nextRowFast(fields, columnCount, isBinaryEncoded, resultSetConcurrency, useBufferRowIfPossible, useBufferRowExplicit, canReuseRowPacketForBufferRow);
        }
        Buffer rowPacket = null;
        if (existingRowPacket == null) {
            rowPacket = this.checkErrorPacket();
            if (!useBufferRowExplicit && useBufferRowIfPossible && rowPacket.getBufLength() > this.useBufferRowSizeThreshold) {
                useBufferRowExplicit = true;
            }
        }
        else {
            rowPacket = existingRowPacket;
            this.checkErrorPacket(existingRowPacket);
        }
        if (!isBinaryEncoded) {
            rowPacket.setPosition(rowPacket.getPosition() - 1);
            if (rowPacket.isLastDataPacket()) {
                this.readServerStatusForResultSets(rowPacket);
                return null;
            }
            if (resultSetConcurrency == 1008 || (!useBufferRowIfPossible && !useBufferRowExplicit)) {
                final byte[][] rowData = new byte[columnCount][];
                for (int i = 0; i < columnCount; ++i) {
                    rowData[i] = rowPacket.readLenByteArray(0);
                }
                return new ByteArrayRow(rowData, this.getExceptionInterceptor());
            }
            if (!canReuseRowPacketForBufferRow) {
                this.reusablePacket = new Buffer(rowPacket.getBufLength());
            }
            return new BufferRow(rowPacket, fields, false, this.getExceptionInterceptor());
        }
        else {
            if (rowPacket.isLastDataPacket()) {
                rowPacket.setPosition(rowPacket.getPosition() - 1);
                this.readServerStatusForResultSets(rowPacket);
                return null;
            }
            if (resultSetConcurrency == 1008 || (!useBufferRowIfPossible && !useBufferRowExplicit)) {
                return this.unpackBinaryResultSetRow(fields, rowPacket, resultSetConcurrency);
            }
            if (!canReuseRowPacketForBufferRow) {
                this.reusablePacket = new Buffer(rowPacket.getBufLength());
            }
            return new BufferRow(rowPacket, fields, true, this.getExceptionInterceptor());
        }
    }
    
    final ResultSetRow nextRowFast(final Field[] fields, final int columnCount, final boolean isBinaryEncoded, final int resultSetConcurrency, final boolean useBufferRowIfPossible, final boolean useBufferRowExplicit, final boolean canReuseRowPacket) throws SQLException {
        try {
            final int lengthRead = this.readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
            if (lengthRead < 4) {
                this.forceClose();
                throw new RuntimeException(Messages.getString("MysqlIO.43"));
            }
            final int packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
            if (packetLength == this.maxThreeBytes) {
                this.reuseAndReadPacket(this.reusablePacket, packetLength);
                return this.nextRow(fields, columnCount, isBinaryEncoded, resultSetConcurrency, useBufferRowIfPossible, useBufferRowExplicit, canReuseRowPacket, this.reusablePacket);
            }
            if (packetLength > this.useBufferRowSizeThreshold) {
                this.reuseAndReadPacket(this.reusablePacket, packetLength);
                return this.nextRow(fields, columnCount, isBinaryEncoded, resultSetConcurrency, true, true, false, this.reusablePacket);
            }
            int remaining = packetLength;
            boolean firstTime = true;
            byte[][] rowData = null;
            for (int i = 0; i < columnCount; ++i) {
                final int sw = this.mysqlInput.read() & 0xFF;
                --remaining;
                if (firstTime) {
                    if (sw == 255) {
                        final Buffer errorPacket = new Buffer(packetLength + 4);
                        errorPacket.setPosition(0);
                        errorPacket.writeByte(this.packetHeaderBuf[0]);
                        errorPacket.writeByte(this.packetHeaderBuf[1]);
                        errorPacket.writeByte(this.packetHeaderBuf[2]);
                        errorPacket.writeByte((byte)1);
                        errorPacket.writeByte((byte)sw);
                        this.readFully(this.mysqlInput, errorPacket.getByteBuffer(), 5, packetLength - 1);
                        errorPacket.setPosition(4);
                        this.checkErrorPacket(errorPacket);
                    }
                    if (sw == 254 && packetLength < 9) {
                        if (this.use41Extensions) {
                            this.warningCount = ((this.mysqlInput.read() & 0xFF) | (this.mysqlInput.read() & 0xFF) << 8);
                            remaining -= 2;
                            if (this.warningCount > 0) {
                                this.hadWarnings = true;
                            }
                            this.oldServerStatus = this.serverStatus;
                            this.serverStatus = ((this.mysqlInput.read() & 0xFF) | (this.mysqlInput.read() & 0xFF) << 8);
                            this.checkTransactionState(this.oldServerStatus);
                            remaining -= 2;
                            if (remaining > 0) {
                                this.skipFully(this.mysqlInput, remaining);
                            }
                        }
                        return null;
                    }
                    rowData = new byte[columnCount][];
                    firstTime = false;
                }
                int len = 0;
                switch (sw) {
                    case 251: {
                        len = -1;
                        break;
                    }
                    case 252: {
                        len = ((this.mysqlInput.read() & 0xFF) | (this.mysqlInput.read() & 0xFF) << 8);
                        remaining -= 2;
                        break;
                    }
                    case 253: {
                        len = ((this.mysqlInput.read() & 0xFF) | (this.mysqlInput.read() & 0xFF) << 8 | (this.mysqlInput.read() & 0xFF) << 16);
                        remaining -= 3;
                        break;
                    }
                    case 254: {
                        len = ((this.mysqlInput.read() & 0xFF) | (this.mysqlInput.read() & 0xFF) << 8 | (this.mysqlInput.read() & 0xFF) << 16 | (this.mysqlInput.read() & 0xFF) << 24 | (this.mysqlInput.read() & 0xFF) << 32 | (this.mysqlInput.read() & 0xFF) << 40 | (this.mysqlInput.read() & 0xFF) << 48 | (this.mysqlInput.read() & 0xFF) << 56);
                        remaining -= 8;
                        break;
                    }
                    default: {
                        len = sw;
                        break;
                    }
                }
                if (len == -1) {
                    rowData[i] = null;
                }
                else if (len == 0) {
                    rowData[i] = Constants.EMPTY_BYTE_ARRAY;
                }
                else {
                    rowData[i] = new byte[len];
                    final int bytesRead = this.readFully(this.mysqlInput, rowData[i], 0, len);
                    if (bytesRead != len) {
                        throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, new IOException(Messages.getString("MysqlIO.43")), this.getExceptionInterceptor());
                    }
                    remaining -= bytesRead;
                }
            }
            if (remaining > 0) {
                this.skipFully(this.mysqlInput, remaining);
            }
            return new ByteArrayRow(rowData, this.getExceptionInterceptor());
        }
        catch (IOException ioEx) {
            throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, this.getExceptionInterceptor());
        }
    }
    
    final void quit() throws SQLException {
        try {
            try {
                if (!this.mysqlConnection.isClosed()) {
                    this.mysqlConnection.shutdownInput();
                }
            }
            catch (IOException ioEx) {
                this.connection.getLog().logWarn("Caught while disconnecting...", ioEx);
            }
            final Buffer packet = new Buffer(6);
            this.packetSequence = -1;
            packet.writeByte((byte)1);
            this.send(packet, packet.getPosition());
        }
        finally {
            this.forceClose();
        }
    }
    
    Buffer getSharedSendPacket() {
        if (this.sharedSendPacket == null) {
            this.sharedSendPacket = new Buffer(1024);
        }
        return this.sharedSendPacket;
    }
    
    void closeStreamer(final RowData streamer) throws SQLException {
        if (this.streamingData == null) {
            throw SQLError.createSQLException(Messages.getString("MysqlIO.17") + streamer + Messages.getString("MysqlIO.18"), this.getExceptionInterceptor());
        }
        if (streamer != this.streamingData) {
            throw SQLError.createSQLException(Messages.getString("MysqlIO.19") + streamer + Messages.getString("MysqlIO.20") + Messages.getString("MysqlIO.21") + Messages.getString("MysqlIO.22"), this.getExceptionInterceptor());
        }
        this.streamingData = null;
    }
    
    boolean tackOnMoreStreamingResults(final ResultSetImpl addingTo) throws SQLException {
        if ((this.serverStatus & 0x8) != 0x0) {
            boolean moreRowSetsExist = true;
            ResultSetImpl currentResultSet = addingTo;
            boolean firstTime = true;
            while (moreRowSetsExist && (firstTime || !currentResultSet.reallyResult())) {
                firstTime = false;
                final Buffer fieldPacket = this.checkErrorPacket();
                fieldPacket.setPosition(0);
                final Statement owningStatement = addingTo.getStatement();
                final int maxRows = owningStatement.getMaxRows();
                final ResultSetImpl newResultSet = this.readResultsForQueryOrUpdate((StatementImpl)owningStatement, maxRows, owningStatement.getResultSetType(), owningStatement.getResultSetConcurrency(), true, owningStatement.getConnection().getCatalog(), fieldPacket, addingTo.isBinaryEncoded, -1L, null);
                currentResultSet.setNextResultSet(newResultSet);
                currentResultSet = newResultSet;
                moreRowSetsExist = ((this.serverStatus & 0x8) != 0x0);
                if (!currentResultSet.reallyResult() && !moreRowSetsExist) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
    
    ResultSetImpl readAllResults(final StatementImpl callingStatement, final int maxRows, final int resultSetType, final int resultSetConcurrency, final boolean streamResults, final String catalog, final Buffer resultPacket, final boolean isBinaryEncoded, final long preSentColumnCount, final Field[] metadataFromCache) throws SQLException {
        resultPacket.setPosition(resultPacket.getPosition() - 1);
        ResultSetImpl currentResultSet;
        final ResultSetImpl topLevelResultSet = currentResultSet = this.readResultsForQueryOrUpdate(callingStatement, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, resultPacket, isBinaryEncoded, preSentColumnCount, metadataFromCache);
        final boolean checkForMoreResults = (this.clientParam & 0x20000L) != 0x0L;
        final boolean serverHasMoreResults = (this.serverStatus & 0x8) != 0x0;
        if (serverHasMoreResults && streamResults) {
            if (topLevelResultSet.getUpdateCount() != -1L) {
                this.tackOnMoreStreamingResults(topLevelResultSet);
            }
            this.reclaimLargeReusablePacket();
            return topLevelResultSet;
        }
        for (boolean moreRowSetsExist = checkForMoreResults & serverHasMoreResults; moreRowSetsExist; moreRowSetsExist = ((this.serverStatus & 0x8) != 0x0)) {
            final Buffer fieldPacket = this.checkErrorPacket();
            fieldPacket.setPosition(0);
            final ResultSetImpl newResultSet = this.readResultsForQueryOrUpdate(callingStatement, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, fieldPacket, isBinaryEncoded, preSentColumnCount, metadataFromCache);
            currentResultSet.setNextResultSet(newResultSet);
            currentResultSet = newResultSet;
        }
        if (!streamResults) {
            this.clearInputStream();
        }
        this.reclaimLargeReusablePacket();
        return topLevelResultSet;
    }
    
    void resetMaxBuf() {
        this.maxAllowedPacket = this.connection.getMaxAllowedPacket();
    }
    
    final Buffer sendCommand(final int command, final String extraData, final Buffer queryPacket, final boolean skipCheck, final String extraDataCharEncoding, final int timeoutMillis) throws SQLException {
        ++this.commandCount;
        this.enablePacketDebug = this.connection.getEnablePacketDebug();
        this.readPacketSequence = 0;
        int oldTimeout = 0;
        if (timeoutMillis != 0) {
            try {
                oldTimeout = this.mysqlConnection.getSoTimeout();
                this.mysqlConnection.setSoTimeout(timeoutMillis);
            }
            catch (SocketException e) {
                throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, e, this.getExceptionInterceptor());
            }
        }
        try {
            this.checkForOutstandingStreamingData();
            this.oldServerStatus = this.serverStatus;
            this.serverStatus = 0;
            this.hadWarnings = false;
            this.warningCount = 0;
            this.queryNoIndexUsed = false;
            this.queryBadIndexUsed = false;
            this.serverQueryWasSlow = false;
            if (this.useCompression) {
                final int bytesLeft = this.mysqlInput.available();
                if (bytesLeft > 0) {
                    this.mysqlInput.skip(bytesLeft);
                }
            }
            try {
                this.clearInputStream();
                if (queryPacket == null) {
                    final int packLength = 8 + ((extraData != null) ? extraData.length() : 0) + 2;
                    if (this.sendPacket == null) {
                        this.sendPacket = new Buffer(packLength);
                    }
                    this.packetSequence = -1;
                    this.readPacketSequence = 0;
                    this.checkPacketSequence = true;
                    this.sendPacket.clear();
                    this.sendPacket.writeByte((byte)command);
                    if (command == 2 || command == 5 || command == 6 || command == 3 || command == 22) {
                        if (extraDataCharEncoding == null) {
                            this.sendPacket.writeStringNoNull(extraData);
                        }
                        else {
                            this.sendPacket.writeStringNoNull(extraData, extraDataCharEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.connection);
                        }
                    }
                    else if (command == 12) {
                        final long id = Long.parseLong(extraData);
                        this.sendPacket.writeLong(id);
                    }
                    this.send(this.sendPacket, this.sendPacket.getPosition());
                }
                else {
                    this.packetSequence = -1;
                    this.send(queryPacket, queryPacket.getPosition());
                }
            }
            catch (SQLException sqlEx) {
                throw sqlEx;
            }
            catch (Exception ex) {
                throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ex, this.getExceptionInterceptor());
            }
            Buffer returnPacket = null;
            if (!skipCheck) {
                if (command == 23 || command == 26) {
                    this.readPacketSequence = 0;
                    this.packetSequenceReset = true;
                }
                returnPacket = this.checkErrorPacket(command);
            }
            return returnPacket;
        }
        catch (IOException ioEx) {
            throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, this.getExceptionInterceptor());
        }
        finally {
            if (timeoutMillis != 0) {
                try {
                    this.mysqlConnection.setSoTimeout(oldTimeout);
                }
                catch (SocketException e2) {
                    throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, e2, this.getExceptionInterceptor());
                }
            }
        }
    }
    
    protected boolean shouldIntercept() {
        return this.statementInterceptors != null;
    }
    
    final ResultSetInternalMethods sqlQueryDirect(final StatementImpl callingStatement, final String query, final String characterEncoding, Buffer queryPacket, final int maxRows, final int resultSetType, final int resultSetConcurrency, final boolean streamResults, final String catalog, final Field[] cachedMetadata) throws Exception {
        ++this.statementExecutionDepth;
        try {
            if (this.statementInterceptors != null) {
                final ResultSetInternalMethods interceptedResults = this.invokeStatementInterceptorsPre(query, callingStatement, false);
                if (interceptedResults != null) {
                    return interceptedResults;
                }
            }
            long queryStartTime = 0L;
            long queryEndTime = 0L;
            if (query != null) {
                int packLength = 5 + query.length() * 2 + 2;
                final String statementComment = this.connection.getStatementComment();
                byte[] commentAsBytes = null;
                if (statementComment != null) {
                    commentAsBytes = StringUtils.getBytes(statementComment, null, characterEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.getExceptionInterceptor());
                    packLength += commentAsBytes.length;
                    packLength += 6;
                }
                if (this.sendPacket == null) {
                    this.sendPacket = new Buffer(packLength);
                }
                else {
                    this.sendPacket.clear();
                }
                this.sendPacket.writeByte((byte)3);
                if (commentAsBytes != null) {
                    this.sendPacket.writeBytesNoNull(Constants.SLASH_STAR_SPACE_AS_BYTES);
                    this.sendPacket.writeBytesNoNull(commentAsBytes);
                    this.sendPacket.writeBytesNoNull(Constants.SPACE_STAR_SLASH_SPACE_AS_BYTES);
                }
                if (characterEncoding != null) {
                    if (this.platformDbCharsetMatches) {
                        this.sendPacket.writeStringNoNull(query, characterEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.connection);
                    }
                    else if (StringUtils.startsWithIgnoreCaseAndWs(query, "LOAD DATA")) {
                        this.sendPacket.writeBytesNoNull(query.getBytes());
                    }
                    else {
                        this.sendPacket.writeStringNoNull(query, characterEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.connection);
                    }
                }
                else {
                    this.sendPacket.writeStringNoNull(query);
                }
                queryPacket = this.sendPacket;
            }
            byte[] queryBuf = null;
            int oldPacketPosition = 0;
            if (this.needToGrabQueryFromPacket) {
                queryBuf = queryPacket.getByteBuffer();
                oldPacketPosition = queryPacket.getPosition();
                queryStartTime = this.getCurrentTimeNanosOrMillis();
            }
            if (this.autoGenerateTestcaseScript) {
                String testcaseQuery = null;
                if (query != null) {
                    testcaseQuery = query;
                }
                else {
                    testcaseQuery = new String(queryBuf, 5, oldPacketPosition - 5);
                }
                final StringBuffer debugBuf = new StringBuffer(testcaseQuery.length() + 32);
                this.connection.generateConnectionCommentBlock(debugBuf);
                debugBuf.append(testcaseQuery);
                debugBuf.append(';');
                this.connection.dumpTestcaseQuery(debugBuf.toString());
            }
            final Buffer resultPacket = this.sendCommand(3, null, queryPacket, false, null, 0);
            long fetchBeginTime = 0L;
            long fetchEndTime = 0L;
            String profileQueryToLog = null;
            boolean queryWasSlow = false;
            if (this.profileSql || this.logSlowQueries) {
                queryEndTime = System.currentTimeMillis();
                boolean shouldExtractQuery = false;
                if (this.profileSql) {
                    shouldExtractQuery = true;
                }
                else if (this.logSlowQueries) {
                    final long queryTime = queryEndTime - queryStartTime;
                    boolean logSlow = false;
                    if (this.useAutoSlowLog) {
                        logSlow = (queryTime > this.connection.getSlowQueryThresholdMillis());
                    }
                    else {
                        logSlow = this.connection.isAbonormallyLongQuery(queryTime);
                        this.connection.reportQueryTime(queryTime);
                    }
                    if (logSlow) {
                        shouldExtractQuery = true;
                        queryWasSlow = true;
                    }
                }
                if (shouldExtractQuery) {
                    boolean truncated = false;
                    int extractPosition;
                    if ((extractPosition = oldPacketPosition) > this.connection.getMaxQuerySizeToLog()) {
                        extractPosition = this.connection.getMaxQuerySizeToLog() + 5;
                        truncated = true;
                    }
                    profileQueryToLog = new String(queryBuf, 5, extractPosition - 5);
                    if (truncated) {
                        profileQueryToLog += Messages.getString("MysqlIO.25");
                    }
                }
                fetchBeginTime = queryEndTime;
            }
            ResultSetInternalMethods rs = this.readAllResults(callingStatement, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, resultPacket, false, -1L, cachedMetadata);
            if (queryWasSlow && !this.serverQueryWasSlow) {
                final StringBuffer mesgBuf = new StringBuffer(48 + profileQueryToLog.length());
                mesgBuf.append(Messages.getString("MysqlIO.SlowQuery", new Object[] { new Long(this.slowQueryThreshold), this.queryTimingUnits, new Long(queryEndTime - queryStartTime) }));
                mesgBuf.append(profileQueryToLog);
                final ProfilerEventHandler eventSink = ProfilerEventHandlerFactory.getInstance(this.connection);
                eventSink.consumeEvent(new ProfilerEvent((byte)6, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), (int)(queryEndTime - queryStartTime), this.queryTimingUnits, null, new Throwable(), mesgBuf.toString()));
                if (this.connection.getExplainSlowQueries()) {
                    if (oldPacketPosition < 1048576) {
                        this.explainSlowQuery(queryPacket.getBytes(5, oldPacketPosition - 5), profileQueryToLog);
                    }
                    else {
                        this.connection.getLog().logWarn(Messages.getString("MysqlIO.28") + 1048576 + Messages.getString("MysqlIO.29"));
                    }
                }
            }
            if (this.logSlowQueries) {
                final ProfilerEventHandler eventSink2 = ProfilerEventHandlerFactory.getInstance(this.connection);
                if (this.queryBadIndexUsed && this.profileSql) {
                    eventSink2.consumeEvent(new ProfilerEvent((byte)6, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, null, new Throwable(), Messages.getString("MysqlIO.33") + profileQueryToLog));
                }
                if (this.queryNoIndexUsed && this.profileSql) {
                    eventSink2.consumeEvent(new ProfilerEvent((byte)6, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, null, new Throwable(), Messages.getString("MysqlIO.35") + profileQueryToLog));
                }
                if (this.serverQueryWasSlow && this.profileSql) {
                    eventSink2.consumeEvent(new ProfilerEvent((byte)6, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, null, new Throwable(), Messages.getString("MysqlIO.ServerSlowQuery") + profileQueryToLog));
                }
            }
            if (this.profileSql) {
                fetchEndTime = this.getCurrentTimeNanosOrMillis();
                final ProfilerEventHandler eventSink2 = ProfilerEventHandlerFactory.getInstance(this.connection);
                eventSink2.consumeEvent(new ProfilerEvent((byte)3, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), queryEndTime - queryStartTime, this.queryTimingUnits, null, new Throwable(), profileQueryToLog));
                eventSink2.consumeEvent(new ProfilerEvent((byte)5, "", catalog, this.connection.getId(), (callingStatement != null) ? callingStatement.getId() : 999, ((ResultSetImpl)rs).resultId, System.currentTimeMillis(), fetchEndTime - fetchBeginTime, this.queryTimingUnits, null, new Throwable(), null));
            }
            if (this.hadWarnings) {
                this.scanForAndThrowDataTruncation();
            }
            if (this.statementInterceptors != null) {
                final ResultSetInternalMethods interceptedResults2 = this.invokeStatementInterceptorsPost(query, callingStatement, rs, false, null);
                if (interceptedResults2 != null) {
                    rs = interceptedResults2;
                }
            }
            return rs;
        }
        catch (SQLException sqlEx) {
            if (this.statementInterceptors != null) {
                this.invokeStatementInterceptorsPost(query, callingStatement, null, false, sqlEx);
            }
            if (callingStatement != null) {
                synchronized (callingStatement.cancelTimeoutMutex) {
                    if (callingStatement.wasCancelled) {
                        SQLException cause = null;
                        if (callingStatement.wasCancelledByTimeout) {
                            cause = new MySQLTimeoutException();
                        }
                        else {
                            cause = new MySQLStatementCancelledException();
                        }
                        callingStatement.resetCancelledState();
                        throw cause;
                    }
                }
            }
            throw sqlEx;
        }
        finally {
            --this.statementExecutionDepth;
        }
    }
    
    ResultSetInternalMethods invokeStatementInterceptorsPre(final String sql, final com.mysql.jdbc.Statement interceptedStatement, final boolean forceExecute) throws SQLException {
        ResultSetInternalMethods previousResultSet = null;
        for (final StatementInterceptorV2 interceptor : this.statementInterceptors) {
            final boolean executeTopLevelOnly = interceptor.executeTopLevelOnly();
            final boolean shouldExecute = (executeTopLevelOnly && (this.statementExecutionDepth == 1 || forceExecute)) || !executeTopLevelOnly;
            if (shouldExecute) {
                final ResultSetInternalMethods interceptedResultSet = interceptor.preProcess(sql, interceptedStatement, this.connection);
                if (interceptedResultSet == null) {
                    continue;
                }
                previousResultSet = interceptedResultSet;
            }
        }
        return previousResultSet;
    }
    
    ResultSetInternalMethods invokeStatementInterceptorsPost(final String sql, final com.mysql.jdbc.Statement interceptedStatement, ResultSetInternalMethods originalResultSet, final boolean forceExecute, final SQLException statementException) throws SQLException {
        for (final StatementInterceptorV2 interceptor : this.statementInterceptors) {
            final boolean executeTopLevelOnly = interceptor.executeTopLevelOnly();
            final boolean shouldExecute = (executeTopLevelOnly && (this.statementExecutionDepth == 1 || forceExecute)) || !executeTopLevelOnly;
            if (shouldExecute) {
                final ResultSetInternalMethods interceptedResultSet = interceptor.postProcess(sql, interceptedStatement, originalResultSet, this.connection, this.warningCount, this.queryNoIndexUsed, this.queryBadIndexUsed, statementException);
                if (interceptedResultSet == null) {
                    continue;
                }
                originalResultSet = interceptedResultSet;
            }
        }
        return originalResultSet;
    }
    
    private void calculateSlowQueryThreshold() {
        this.slowQueryThreshold = this.connection.getSlowQueryThresholdMillis();
        if (this.connection.getUseNanosForElapsedTime()) {
            final long nanosThreshold = this.connection.getSlowQueryThresholdNanos();
            if (nanosThreshold != 0L) {
                this.slowQueryThreshold = nanosThreshold;
            }
            else {
                this.slowQueryThreshold *= 1000000L;
            }
        }
    }
    
    protected long getCurrentTimeNanosOrMillis() {
        if (this.useNanosForElapsedTime) {
            return Util.getCurrentTimeNanosOrMillis();
        }
        return System.currentTimeMillis();
    }
    
    String getHost() {
        return this.host;
    }
    
    boolean isVersion(final int major, final int minor, final int subminor) {
        return major == this.getServerMajorVersion() && minor == this.getServerMinorVersion() && subminor == this.getServerSubMinorVersion();
    }
    
    boolean versionMeetsMinimum(final int major, final int minor, final int subminor) {
        return this.getServerMajorVersion() >= major && (this.getServerMajorVersion() != major || (this.getServerMinorVersion() >= minor && (this.getServerMinorVersion() != minor || this.getServerSubMinorVersion() >= subminor)));
    }
    
    private static final String getPacketDumpToLog(final Buffer packetToDump, final int packetLength) {
        if (packetLength < 1024) {
            return packetToDump.dump(packetLength);
        }
        final StringBuffer packetDumpBuf = new StringBuffer(4096);
        packetDumpBuf.append(packetToDump.dump(1024));
        packetDumpBuf.append(Messages.getString("MysqlIO.36"));
        packetDumpBuf.append(1024);
        packetDumpBuf.append(Messages.getString("MysqlIO.37"));
        return packetDumpBuf.toString();
    }
    
    private final int readFully(final InputStream in, final byte[] b, final int off, final int len) throws IOException {
        if (len < 0) {
            throw new IndexOutOfBoundsException();
        }
        int n;
        int count;
        for (n = 0; n < len; n += count) {
            count = in.read(b, off + n, len - n);
            if (count < 0) {
                throw new EOFException(Messages.getString("MysqlIO.EOF", new Object[] { new Integer(len), new Integer(n) }));
            }
        }
        return n;
    }
    
    private final long skipFully(final InputStream in, final long len) throws IOException {
        if (len < 0L) {
            throw new IOException("Negative skip length not allowed");
        }
        long n;
        long count;
        for (n = 0L; n < len; n += count) {
            count = in.skip(len - n);
            if (count < 0L) {
                throw new EOFException(Messages.getString("MysqlIO.EOF", new Object[] { new Long(len), new Long(n) }));
            }
        }
        return n;
    }
    
    protected final ResultSetImpl readResultsForQueryOrUpdate(final StatementImpl callingStatement, final int maxRows, final int resultSetType, final int resultSetConcurrency, final boolean streamResults, final String catalog, final Buffer resultPacket, final boolean isBinaryEncoded, final long preSentColumnCount, final Field[] metadataFromCache) throws SQLException {
        final long columnCount = resultPacket.readFieldLength();
        if (columnCount == 0L) {
            return this.buildResultSetWithUpdates(callingStatement, resultPacket);
        }
        if (columnCount == -1L) {
            String charEncoding = null;
            if (this.connection.getUseUnicode()) {
                charEncoding = this.connection.getEncoding();
            }
            String fileName = null;
            if (this.platformDbCharsetMatches) {
                fileName = ((charEncoding != null) ? resultPacket.readString(charEncoding, this.getExceptionInterceptor()) : resultPacket.readString());
            }
            else {
                fileName = resultPacket.readString();
            }
            return this.sendFileToServer(callingStatement, fileName);
        }
        final ResultSetImpl results = this.getResultSet(callingStatement, columnCount, maxRows, resultSetType, resultSetConcurrency, streamResults, catalog, isBinaryEncoded, metadataFromCache);
        return results;
    }
    
    private int alignPacketSize(final int a, final int l) {
        return a + l - 1 & ~(l - 1);
    }
    
    private ResultSetImpl buildResultSetWithRows(final StatementImpl callingStatement, final String catalog, final Field[] fields, final RowData rows, final int resultSetType, final int resultSetConcurrency, final boolean isBinaryEncoded) throws SQLException {
        ResultSetImpl rs = null;
        switch (resultSetConcurrency) {
            case 1007: {
                rs = ResultSetImpl.getInstance(catalog, fields, rows, this.connection, callingStatement, false);
                if (isBinaryEncoded) {
                    rs.setBinaryEncoded();
                    break;
                }
                break;
            }
            case 1008: {
                rs = ResultSetImpl.getInstance(catalog, fields, rows, this.connection, callingStatement, true);
                break;
            }
            default: {
                return ResultSetImpl.getInstance(catalog, fields, rows, this.connection, callingStatement, false);
            }
        }
        rs.setResultSetType(resultSetType);
        rs.setResultSetConcurrency(resultSetConcurrency);
        return rs;
    }
    
    private ResultSetImpl buildResultSetWithUpdates(final StatementImpl callingStatement, final Buffer resultPacket) throws SQLException {
        long updateCount = -1L;
        long updateID = -1L;
        String info = null;
        try {
            if (this.useNewUpdateCounts) {
                updateCount = resultPacket.newReadLength();
                updateID = resultPacket.newReadLength();
            }
            else {
                updateCount = resultPacket.readLength();
                updateID = resultPacket.readLength();
            }
            if (this.use41Extensions) {
                this.serverStatus = resultPacket.readInt();
                this.checkTransactionState(this.oldServerStatus);
                this.warningCount = resultPacket.readInt();
                if (this.warningCount > 0) {
                    this.hadWarnings = true;
                }
                resultPacket.readByte();
                this.setServerSlowQueryFlags();
            }
            if (this.connection.isReadInfoMsgEnabled()) {
                info = resultPacket.readString(this.connection.getErrorMessageEncoding(), this.getExceptionInterceptor());
            }
        }
        catch (Exception ex) {
            final SQLException sqlEx = SQLError.createSQLException(SQLError.get("S1000"), "S1000", -1, this.getExceptionInterceptor());
            sqlEx.initCause(ex);
            throw sqlEx;
        }
        final ResultSetInternalMethods updateRs = ResultSetImpl.getInstance(updateCount, updateID, this.connection, callingStatement);
        if (info != null) {
            ((ResultSetImpl)updateRs).setServerInfo(info);
        }
        return (ResultSetImpl)updateRs;
    }
    
    private void setServerSlowQueryFlags() {
        this.queryBadIndexUsed = ((this.serverStatus & 0x10) != 0x0);
        this.queryNoIndexUsed = ((this.serverStatus & 0x20) != 0x0);
        this.serverQueryWasSlow = ((this.serverStatus & 0x800) != 0x0);
    }
    
    private void checkForOutstandingStreamingData() throws SQLException {
        if (this.streamingData != null) {
            final boolean shouldClobber = this.connection.getClobberStreamingResults();
            if (!shouldClobber) {
                throw SQLError.createSQLException(Messages.getString("MysqlIO.39") + this.streamingData + Messages.getString("MysqlIO.40") + Messages.getString("MysqlIO.41") + Messages.getString("MysqlIO.42"), this.getExceptionInterceptor());
            }
            this.streamingData.getOwner().realClose(false);
            this.clearInputStream();
        }
    }
    
    private Buffer compressPacket(final Buffer packet, final int offset, final int packetLen, int headerLength) throws SQLException {
        packet.writeLongInt(packetLen - headerLength);
        packet.writeByte((byte)0);
        int lengthToWrite = 0;
        int compressedLength = 0;
        final byte[] bytesToCompress = packet.getByteBuffer();
        byte[] compressedBytes = null;
        int offsetWrite = 0;
        if (packetLen < 50) {
            lengthToWrite = packetLen;
            compressedBytes = packet.getByteBuffer();
            compressedLength = 0;
            offsetWrite = offset;
        }
        else {
            compressedBytes = new byte[bytesToCompress.length * 2];
            this.deflater.reset();
            this.deflater.setInput(bytesToCompress, offset, packetLen);
            this.deflater.finish();
            final int compLen = this.deflater.deflate(compressedBytes);
            if (compLen > packetLen) {
                lengthToWrite = packetLen;
                compressedBytes = packet.getByteBuffer();
                compressedLength = 0;
                offsetWrite = offset;
            }
            else {
                lengthToWrite = compLen;
                headerLength += 3;
                compressedLength = packetLen;
            }
        }
        final Buffer compressedPacket = new Buffer(packetLen + headerLength);
        compressedPacket.setPosition(0);
        compressedPacket.writeLongInt(lengthToWrite);
        compressedPacket.writeByte(this.packetSequence);
        compressedPacket.writeLongInt(compressedLength);
        compressedPacket.writeBytesNoNull(compressedBytes, offsetWrite, lengthToWrite);
        return compressedPacket;
    }
    
    private final void readServerStatusForResultSets(final Buffer rowPacket) throws SQLException {
        if (this.use41Extensions) {
            rowPacket.readByte();
            this.warningCount = rowPacket.readInt();
            if (this.warningCount > 0) {
                this.hadWarnings = true;
            }
            this.oldServerStatus = this.serverStatus;
            this.serverStatus = rowPacket.readInt();
            this.checkTransactionState(this.oldServerStatus);
            this.setServerSlowQueryFlags();
        }
    }
    
    private SocketFactory createSocketFactory() throws SQLException {
        try {
            if (this.socketFactoryClassName == null) {
                throw SQLError.createSQLException(Messages.getString("MysqlIO.75"), "08001", this.getExceptionInterceptor());
            }
            return (SocketFactory)Class.forName(this.socketFactoryClassName).newInstance();
        }
        catch (Exception ex) {
            final SQLException sqlEx = SQLError.createSQLException(Messages.getString("MysqlIO.76") + this.socketFactoryClassName + Messages.getString("MysqlIO.77"), "08001", this.getExceptionInterceptor());
            sqlEx.initCause(ex);
            throw sqlEx;
        }
    }
    
    private void enqueuePacketForDebugging(final boolean isPacketBeingSent, final boolean isPacketReused, final int sendLength, final byte[] header, final Buffer packet) throws SQLException {
        if (this.packetDebugRingBuffer.size() + 1 > this.connection.getPacketDebugBufferSize()) {
            this.packetDebugRingBuffer.removeFirst();
        }
        StringBuffer packetDump = null;
        if (!isPacketBeingSent) {
            final int bytesToDump = Math.min(1024, packet.getBufLength());
            final Buffer packetToDump = new Buffer(4 + bytesToDump);
            packetToDump.setPosition(0);
            packetToDump.writeBytesNoNull(header);
            packetToDump.writeBytesNoNull(packet.getBytes(0, bytesToDump));
            final String packetPayload = packetToDump.dump(bytesToDump);
            packetDump = new StringBuffer(96 + packetPayload.length());
            packetDump.append("Server ");
            if (isPacketReused) {
                packetDump.append("(re-used)");
            }
            else {
                packetDump.append("(new)");
            }
            packetDump.append(" ");
            packetDump.append(packet.toSuperString());
            packetDump.append(" --------------------> Client\n");
            packetDump.append("\nPacket payload:\n\n");
            packetDump.append(packetPayload);
            if (bytesToDump == 1024) {
                packetDump.append("\nNote: Packet of " + packet.getBufLength() + " bytes truncated to " + 1024 + " bytes.\n");
            }
        }
        else {
            final int bytesToDump = Math.min(1024, sendLength);
            final String packetPayload2 = packet.dump(bytesToDump);
            packetDump = new StringBuffer(68 + packetPayload2.length());
            packetDump.append("Client ");
            packetDump.append(packet.toSuperString());
            packetDump.append("--------------------> Server\n");
            packetDump.append("\nPacket payload:\n\n");
            packetDump.append(packetPayload2);
            if (bytesToDump == 1024) {
                packetDump.append("\nNote: Packet of " + sendLength + " bytes truncated to " + 1024 + " bytes.\n");
            }
        }
        this.packetDebugRingBuffer.addLast(packetDump);
    }
    
    private RowData readSingleRowSet(final long columnCount, final int maxRows, final int resultSetConcurrency, final boolean isBinaryEncoded, final Field[] fields) throws SQLException {
        final ArrayList rows = new ArrayList();
        final boolean useBufferRowExplicit = useBufferRowExplicit(fields);
        ResultSetRow row = this.nextRow(fields, (int)columnCount, isBinaryEncoded, resultSetConcurrency, false, useBufferRowExplicit, false, null);
        int rowCount = 0;
        if (row != null) {
            rows.add(row);
            rowCount = 1;
        }
        while (row != null) {
            row = this.nextRow(fields, (int)columnCount, isBinaryEncoded, resultSetConcurrency, false, useBufferRowExplicit, false, null);
            if (row != null && (maxRows == -1 || rowCount < maxRows)) {
                rows.add(row);
                ++rowCount;
            }
        }
        final RowData rowData = new RowDataStatic(rows);
        return rowData;
    }
    
    public static boolean useBufferRowExplicit(final Field[] fields) {
        if (fields == null) {
            return false;
        }
        int i = 0;
        while (i < fields.length) {
            switch (fields[i].getSQLType()) {
                case -4:
                case -1:
                case 2004:
                case 2005: {
                    return true;
                }
                default: {
                    ++i;
                    continue;
                }
            }
        }
        return false;
    }
    
    private void reclaimLargeReusablePacket() {
        if (this.reusablePacket != null && this.reusablePacket.getCapacity() > 1048576) {
            this.reusablePacket = new Buffer(1024);
        }
    }
    
    private final Buffer reuseAndReadPacket(final Buffer reuse) throws SQLException {
        return this.reuseAndReadPacket(reuse, -1);
    }
    
    private final Buffer reuseAndReadPacket(final Buffer reuse, final int existingPacketLength) throws SQLException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     0: aload_1         /* reuse */
        //     1: iconst_0       
        //     2: invokevirtual   com/mysql/jdbc/Buffer.setWasMultiPacket:(Z)V
        //     5: iconst_0       
        //     6: istore_3        /* packetLength */
        //     7: iload_2         /* existingPacketLength */
        //     8: iconst_m1      
        //     9: if_icmpne       94
        //    12: aload_0         /* this */
        //    13: aload_0         /* this */
        //    14: getfield        com/mysql/jdbc/MysqlIO.mysqlInput:Ljava/io/InputStream;
        //    17: aload_0         /* this */
        //    18: getfield        com/mysql/jdbc/MysqlIO.packetHeaderBuf:[B
        //    21: iconst_0       
        //    22: iconst_4       
        //    23: invokespecial   com/mysql/jdbc/MysqlIO.readFully:(Ljava/io/InputStream;[BII)I
        //    26: istore          lengthRead
        //    28: iload           lengthRead
        //    30: iconst_4       
        //    31: if_icmpge       52
        //    34: aload_0         /* this */
        //    35: invokevirtual   com/mysql/jdbc/MysqlIO.forceClose:()V
        //    38: new             Ljava/io/IOException;
        //    41: dup            
        //    42: ldc_w           "MysqlIO.43"
        //    45: invokestatic    com/mysql/jdbc/Messages.getString:(Ljava/lang/String;)Ljava/lang/String;
        //    48: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //    51: athrow         
        //    52: aload_0         /* this */
        //    53: getfield        com/mysql/jdbc/MysqlIO.packetHeaderBuf:[B
        //    56: iconst_0       
        //    57: baload         
        //    58: sipush          255
        //    61: iand           
        //    62: aload_0         /* this */
        //    63: getfield        com/mysql/jdbc/MysqlIO.packetHeaderBuf:[B
        //    66: iconst_1       
        //    67: baload         
        //    68: sipush          255
        //    71: iand           
        //    72: bipush          8
        //    74: ishl           
        //    75: iadd           
        //    76: aload_0         /* this */
        //    77: getfield        com/mysql/jdbc/MysqlIO.packetHeaderBuf:[B
        //    80: iconst_2       
        //    81: baload         
        //    82: sipush          255
        //    85: iand           
        //    86: bipush          16
        //    88: ishl           
        //    89: iadd           
        //    90: istore_3        /* packetLength */
        //    91: goto            96
        //    94: iload_2         /* existingPacketLength */
        //    95: istore_3        /* packetLength */
        //    96: aload_0         /* this */
        //    97: getfield        com/mysql/jdbc/MysqlIO.traceProtocol:Z
        //   100: ifeq            176
        //   103: new             Ljava/lang/StringBuffer;
        //   106: dup            
        //   107: invokespecial   java/lang/StringBuffer.<init>:()V
        //   110: astore          traceMessageBuf
        //   112: aload           traceMessageBuf
        //   114: ldc_w           "MysqlIO.44"
        //   117: invokestatic    com/mysql/jdbc/Messages.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   120: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   123: pop            
        //   124: aload           traceMessageBuf
        //   126: iload_3         /* packetLength */
        //   127: invokevirtual   java/lang/StringBuffer.append:(I)Ljava/lang/StringBuffer;
        //   130: pop            
        //   131: aload           traceMessageBuf
        //   133: ldc_w           "MysqlIO.45"
        //   136: invokestatic    com/mysql/jdbc/Messages.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   139: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   142: pop            
        //   143: aload           traceMessageBuf
        //   145: aload_0         /* this */
        //   146: getfield        com/mysql/jdbc/MysqlIO.packetHeaderBuf:[B
        //   149: iconst_4       
        //   150: invokestatic    com/mysql/jdbc/StringUtils.dumpAsHex:([BI)Ljava/lang/String;
        //   153: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   156: pop            
        //   157: aload_0         /* this */
        //   158: getfield        com/mysql/jdbc/MysqlIO.connection:Lcom/mysql/jdbc/MySQLConnection;
        //   161: invokeinterface com/mysql/jdbc/MySQLConnection.getLog:()Lcom/mysql/jdbc/log/Log;
        //   166: aload           traceMessageBuf
        //   168: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   171: invokeinterface com/mysql/jdbc/log/Log.logTrace:(Ljava/lang/Object;)V
        //   176: aload_0         /* this */
        //   177: getfield        com/mysql/jdbc/MysqlIO.packetHeaderBuf:[B
        //   180: iconst_3       
        //   181: baload         
        //   182: istore          multiPacketSeq
        //   184: aload_0         /* this */
        //   185: getfield        com/mysql/jdbc/MysqlIO.packetSequenceReset:Z
        //   188: ifne            214
        //   191: aload_0         /* this */
        //   192: getfield        com/mysql/jdbc/MysqlIO.enablePacketDebug:Z
        //   195: ifeq            219
        //   198: aload_0         /* this */
        //   199: getfield        com/mysql/jdbc/MysqlIO.checkPacketSequence:Z
        //   202: ifeq            219
        //   205: aload_0         /* this */
        //   206: iload           multiPacketSeq
        //   208: invokespecial   com/mysql/jdbc/MysqlIO.checkPacketSequencing:(B)V
        //   211: goto            219
        //   214: aload_0         /* this */
        //   215: iconst_0       
        //   216: putfield        com/mysql/jdbc/MysqlIO.packetSequenceReset:Z
        //   219: aload_0         /* this */
        //   220: iload           multiPacketSeq
        //   222: putfield        com/mysql/jdbc/MysqlIO.readPacketSequence:B
        //   225: aload_1         /* reuse */
        //   226: iconst_0       
        //   227: invokevirtual   com/mysql/jdbc/Buffer.setPosition:(I)V
        //   230: aload_1         /* reuse */
        //   231: invokevirtual   com/mysql/jdbc/Buffer.getByteBuffer:()[B
        //   234: arraylength    
        //   235: iload_3         /* packetLength */
        //   236: if_icmpgt       248
        //   239: aload_1         /* reuse */
        //   240: iload_3         /* packetLength */
        //   241: iconst_1       
        //   242: iadd           
        //   243: newarray        B
        //   245: invokevirtual   com/mysql/jdbc/Buffer.setByteBuffer:([B)V
        //   248: aload_1         /* reuse */
        //   249: iload_3         /* packetLength */
        //   250: invokevirtual   com/mysql/jdbc/Buffer.setBufLength:(I)V
        //   253: aload_0         /* this */
        //   254: aload_0         /* this */
        //   255: getfield        com/mysql/jdbc/MysqlIO.mysqlInput:Ljava/io/InputStream;
        //   258: aload_1         /* reuse */
        //   259: invokevirtual   com/mysql/jdbc/Buffer.getByteBuffer:()[B
        //   262: iconst_0       
        //   263: iload_3         /* packetLength */
        //   264: invokespecial   com/mysql/jdbc/MysqlIO.readFully:(Ljava/io/InputStream;[BII)I
        //   267: istore          numBytesRead
        //   269: iload           numBytesRead
        //   271: iload_3         /* packetLength */
        //   272: if_icmpeq       314
        //   275: new             Ljava/io/IOException;
        //   278: dup            
        //   279: new             Ljava/lang/StringBuilder;
        //   282: dup            
        //   283: invokespecial   java/lang/StringBuilder.<init>:()V
        //   286: ldc_w           "Short read, expected "
        //   289: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   292: iload_3         /* packetLength */
        //   293: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   296: ldc_w           " bytes, only read "
        //   299: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   302: iload           numBytesRead
        //   304: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   307: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   310: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
        //   313: athrow         
        //   314: aload_0         /* this */
        //   315: getfield        com/mysql/jdbc/MysqlIO.traceProtocol:Z
        //   318: ifeq            372
        //   321: new             Ljava/lang/StringBuffer;
        //   324: dup            
        //   325: invokespecial   java/lang/StringBuffer.<init>:()V
        //   328: astore          traceMessageBuf
        //   330: aload           traceMessageBuf
        //   332: ldc_w           "MysqlIO.46"
        //   335: invokestatic    com/mysql/jdbc/Messages.getString:(Ljava/lang/String;)Ljava/lang/String;
        //   338: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   341: pop            
        //   342: aload           traceMessageBuf
        //   344: aload_1         /* reuse */
        //   345: iload_3         /* packetLength */
        //   346: invokestatic    com/mysql/jdbc/MysqlIO.getPacketDumpToLog:(Lcom/mysql/jdbc/Buffer;I)Ljava/lang/String;
        //   349: invokevirtual   java/lang/StringBuffer.append:(Ljava/lang/String;)Ljava/lang/StringBuffer;
        //   352: pop            
        //   353: aload_0         /* this */
        //   354: getfield        com/mysql/jdbc/MysqlIO.connection:Lcom/mysql/jdbc/MySQLConnection;
        //   357: invokeinterface com/mysql/jdbc/MySQLConnection.getLog:()Lcom/mysql/jdbc/log/Log;
        //   362: aload           traceMessageBuf
        //   364: invokevirtual   java/lang/StringBuffer.toString:()Ljava/lang/String;
        //   367: invokeinterface com/mysql/jdbc/log/Log.logTrace:(Ljava/lang/Object;)V
        //   372: aload_0         /* this */
        //   373: getfield        com/mysql/jdbc/MysqlIO.enablePacketDebug:Z
        //   376: ifeq            391
        //   379: aload_0         /* this */
        //   380: iconst_0       
        //   381: iconst_1       
        //   382: iconst_0       
        //   383: aload_0         /* this */
        //   384: getfield        com/mysql/jdbc/MysqlIO.packetHeaderBuf:[B
        //   387: aload_1         /* reuse */
        //   388: invokespecial   com/mysql/jdbc/MysqlIO.enqueuePacketForDebugging:(ZZI[BLcom/mysql/jdbc/Buffer;)V
        //   391: iconst_0       
        //   392: istore          isMultiPacket
        //   394: iload_3         /* packetLength */
        //   395: aload_0         /* this */
        //   396: getfield        com/mysql/jdbc/MysqlIO.maxThreeBytes:I
        //   399: if_icmpne       426
        //   402: aload_1         /* reuse */
        //   403: aload_0         /* this */
        //   404: getfield        com/mysql/jdbc/MysqlIO.maxThreeBytes:I
        //   407: invokevirtual   com/mysql/jdbc/Buffer.setPosition:(I)V
        //   410: iload_3         /* packetLength */
        //   411: istore          packetEndPoint
        //   413: iconst_1       
        //   414: istore          isMultiPacket
        //   416: aload_0         /* this */
        //   417: aload_1         /* reuse */
        //   418: iload           multiPacketSeq
        //   420: iload           packetEndPoint
        //   422: invokespecial   com/mysql/jdbc/MysqlIO.readRemainingMultiPackets:(Lcom/mysql/jdbc/Buffer;BI)I
        //   425: istore_3        /* packetLength */
        //   426: iload           isMultiPacket
        //   428: ifne            438
        //   431: aload_1         /* reuse */
        //   432: invokevirtual   com/mysql/jdbc/Buffer.getByteBuffer:()[B
        //   435: iload_3         /* packetLength */
        //   436: iconst_0       
        //   437: bastore        
        //   438: aload_0         /* this */
        //   439: getfield        com/mysql/jdbc/MysqlIO.connection:Lcom/mysql/jdbc/MySQLConnection;
        //   442: invokeinterface com/mysql/jdbc/MySQLConnection.getMaintainTimeStats:()Z
        //   447: ifeq            457
        //   450: aload_0         /* this */
        //   451: invokestatic    java/lang/System.currentTimeMillis:()J
        //   454: putfield        com/mysql/jdbc/MysqlIO.lastPacketReceivedTimeMs:J
        //   457: aload_1         /* reuse */
        //   458: areturn        
        //   459: astore_3        /* packetLength */
        //   460: aload_0         /* this */
        //   461: getfield        com/mysql/jdbc/MysqlIO.connection:Lcom/mysql/jdbc/MySQLConnection;
        //   464: aload_0         /* this */
        //   465: getfield        com/mysql/jdbc/MysqlIO.lastPacketSentTimeMs:J
        //   468: aload_0         /* this */
        //   469: getfield        com/mysql/jdbc/MysqlIO.lastPacketReceivedTimeMs:J
        //   472: aload_3         /* ioEx */
        //   473: aload_0         /* this */
        //   474: invokevirtual   com/mysql/jdbc/MysqlIO.getExceptionInterceptor:()Lcom/mysql/jdbc/ExceptionInterceptor;
        //   477: invokestatic    com/mysql/jdbc/SQLError.createCommunicationsException:(Lcom/mysql/jdbc/MySQLConnection;JJLjava/lang/Exception;Lcom/mysql/jdbc/ExceptionInterceptor;)Ljava/sql/SQLException;
        //   480: athrow         
        //   481: astore_3        /* ioEx */
        //   482: aload_0         /* this */
        //   483: invokevirtual   com/mysql/jdbc/MysqlIO.clearInputStream:()V
        //   486: jsr             500
        //   489: goto            535
        //   492: astore          8
        //   494: jsr             500
        //   497: aload           8
        //   499: athrow         
        //   500: astore          9
        //   502: aload_0         /* this */
        //   503: getfield        com/mysql/jdbc/MysqlIO.connection:Lcom/mysql/jdbc/MySQLConnection;
        //   506: iconst_0       
        //   507: iconst_0       
        //   508: iconst_1       
        //   509: aload_3         /* oom */
        //   510: invokeinterface com/mysql/jdbc/MySQLConnection.realClose:(ZZZLjava/lang/Throwable;)V
        //   515: jsr             529
        //   518: goto            533
        //   521: astore          10
        //   523: jsr             529
        //   526: aload           10
        //   528: athrow         
        //   529: astore          11
        //   531: aload_3         /* oom */
        //   532: athrow         
        //   533: ret             9
        //   535: goto            535
        //    Exceptions:
        //  throws java.sql.SQLException
        //    LocalVariableTable:
        //  Start  Length  Slot  Name                  Signature
        //  -----  ------  ----  --------------------  ----------------------------
        //  28     63      4     lengthRead            I
        //  112    64      4     traceMessageBuf       Ljava/lang/StringBuffer;
        //  330    42      6     traceMessageBuf       Ljava/lang/StringBuffer;
        //  413    13      7     packetEndPoint        I
        //  7      452     3     packetLength          I
        //  184    275     4     multiPacketSeq        B
        //  269    190     5     numBytesRead          I
        //  394    65      6     isMultiPacket         Z
        //  460    21      3     ioEx                  Ljava/io/IOException;
        //  482    53      3     oom                   Ljava/lang/OutOfMemoryError;
        //  0      538     0     this                  Lcom/mysql/jdbc/MysqlIO;
        //  0      538     1     reuse                 Lcom/mysql/jdbc/Buffer;
        //  0      538     2     existingPacketLength  I
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                        
        //  -----  -----  -----  -----  ----------------------------
        //  0      458    459    481    Ljava/io/IOException;
        //  0      458    481    538    Ljava/lang/OutOfMemoryError;
        //  482    489    492    500    Any
        //  492    497    492    500    Any
        //  502    518    521    529    Any
        //  521    526    521    529    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2162)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:317)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:238)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:123)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    private int readRemainingMultiPackets(final Buffer reuse, byte multiPacketSeq, int packetEndPoint) throws IOException, SQLException {
        int lengthRead = this.readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
        if (lengthRead < 4) {
            this.forceClose();
            throw new IOException(Messages.getString("MysqlIO.47"));
        }
        int packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
        final Buffer multiPacket = new Buffer(packetLength);
        boolean firstMultiPkt = true;
        while (true) {
            if (!firstMultiPkt) {
                lengthRead = this.readFully(this.mysqlInput, this.packetHeaderBuf, 0, 4);
                if (lengthRead < 4) {
                    this.forceClose();
                    throw new IOException(Messages.getString("MysqlIO.48"));
                }
                packetLength = (this.packetHeaderBuf[0] & 0xFF) + ((this.packetHeaderBuf[1] & 0xFF) << 8) + ((this.packetHeaderBuf[2] & 0xFF) << 16);
            }
            else {
                firstMultiPkt = false;
            }
            if (!this.useNewLargePackets && packetLength == 1) {
                this.clearInputStream();
                break;
            }
            if (packetLength < this.maxThreeBytes) {
                final byte newPacketSeq = this.packetHeaderBuf[3];
                if (newPacketSeq != multiPacketSeq + 1) {
                    throw new IOException(Messages.getString("MysqlIO.49"));
                }
                multiPacketSeq = newPacketSeq;
                multiPacket.setPosition(0);
                multiPacket.setBufLength(packetLength);
                final byte[] byteBuf = multiPacket.getByteBuffer();
                final int lengthToWrite = packetLength;
                final int bytesRead = this.readFully(this.mysqlInput, byteBuf, 0, packetLength);
                if (bytesRead != lengthToWrite) {
                    throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, SQLError.createSQLException(Messages.getString("MysqlIO.50") + lengthToWrite + Messages.getString("MysqlIO.51") + bytesRead + ".", this.getExceptionInterceptor()), this.getExceptionInterceptor());
                }
                reuse.writeBytesNoNull(byteBuf, 0, lengthToWrite);
                packetEndPoint += lengthToWrite;
                break;
            }
            else {
                final byte newPacketSeq = this.packetHeaderBuf[3];
                if (newPacketSeq != multiPacketSeq + 1) {
                    throw new IOException(Messages.getString("MysqlIO.53"));
                }
                multiPacketSeq = newPacketSeq;
                multiPacket.setPosition(0);
                multiPacket.setBufLength(packetLength);
                final byte[] byteBuf = multiPacket.getByteBuffer();
                final int lengthToWrite = packetLength;
                final int bytesRead = this.readFully(this.mysqlInput, byteBuf, 0, packetLength);
                if (bytesRead != lengthToWrite) {
                    throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, SQLError.createSQLException(Messages.getString("MysqlIO.54") + lengthToWrite + Messages.getString("MysqlIO.55") + bytesRead + ".", this.getExceptionInterceptor()), this.getExceptionInterceptor());
                }
                reuse.writeBytesNoNull(byteBuf, 0, lengthToWrite);
                packetEndPoint += lengthToWrite;
            }
        }
        reuse.setPosition(0);
        reuse.setWasMultiPacket(true);
        return packetLength;
    }
    
    private void checkPacketSequencing(final byte multiPacketSeq) throws SQLException {
        if (multiPacketSeq == -128 && this.readPacketSequence != 127) {
            throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, new IOException("Packets out of order, expected packet # -128, but received packet # " + multiPacketSeq), this.getExceptionInterceptor());
        }
        if (this.readPacketSequence == -1 && multiPacketSeq != 0) {
            throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, new IOException("Packets out of order, expected packet # -1, but received packet # " + multiPacketSeq), this.getExceptionInterceptor());
        }
        if (multiPacketSeq != -128 && this.readPacketSequence != -1 && multiPacketSeq != this.readPacketSequence + 1) {
            throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, new IOException("Packets out of order, expected packet # " + (this.readPacketSequence + 1) + ", but received packet # " + multiPacketSeq), this.getExceptionInterceptor());
        }
    }
    
    void enableMultiQueries() throws SQLException {
        final Buffer buf = this.getSharedSendPacket();
        buf.clear();
        buf.writeByte((byte)27);
        buf.writeInt(0);
        this.sendCommand(27, null, buf, false, null, 0);
    }
    
    void disableMultiQueries() throws SQLException {
        final Buffer buf = this.getSharedSendPacket();
        buf.clear();
        buf.writeByte((byte)27);
        buf.writeInt(1);
        this.sendCommand(27, null, buf, false, null, 0);
    }
    
    private final void send(final Buffer packet, int packetLen) throws SQLException {
        try {
            if (this.maxAllowedPacket > 0 && packetLen > this.maxAllowedPacket) {
                throw new PacketTooBigException(packetLen, this.maxAllowedPacket);
            }
            if (this.serverMajorVersion >= 4 && packetLen >= this.maxThreeBytes) {
                this.sendSplitPackets(packet);
            }
            else {
                ++this.packetSequence;
                Buffer packetToSend = packet;
                packetToSend.setPosition(0);
                if (this.useCompression) {
                    final int originalPacketLen = packetLen;
                    packetToSend = this.compressPacket(packet, 0, packetLen, 4);
                    packetLen = packetToSend.getPosition();
                    if (this.traceProtocol) {
                        final StringBuffer traceMessageBuf = new StringBuffer();
                        traceMessageBuf.append(Messages.getString("MysqlIO.57"));
                        traceMessageBuf.append(getPacketDumpToLog(packetToSend, packetLen));
                        traceMessageBuf.append(Messages.getString("MysqlIO.58"));
                        traceMessageBuf.append(getPacketDumpToLog(packet, originalPacketLen));
                        this.connection.getLog().logTrace(traceMessageBuf.toString());
                    }
                }
                else {
                    packetToSend.writeLongInt(packetLen - 4);
                    packetToSend.writeByte(this.packetSequence);
                    if (this.traceProtocol) {
                        final StringBuffer traceMessageBuf2 = new StringBuffer();
                        traceMessageBuf2.append(Messages.getString("MysqlIO.59"));
                        traceMessageBuf2.append("host: '");
                        traceMessageBuf2.append(this.host);
                        traceMessageBuf2.append("' threadId: '");
                        traceMessageBuf2.append(this.threadId);
                        traceMessageBuf2.append("'\n");
                        traceMessageBuf2.append(packetToSend.dump(packetLen));
                        this.connection.getLog().logTrace(traceMessageBuf2.toString());
                    }
                }
                this.mysqlOutput.write(packetToSend.getByteBuffer(), 0, packetLen);
                this.mysqlOutput.flush();
            }
            if (this.enablePacketDebug) {
                this.enqueuePacketForDebugging(true, false, packetLen + 5, this.packetHeaderBuf, packet);
            }
            if (packet == this.sharedSendPacket) {
                this.reclaimLargeSharedSendPacket();
            }
            if (this.connection.getMaintainTimeStats()) {
                this.lastPacketSentTimeMs = System.currentTimeMillis();
            }
        }
        catch (IOException ioEx) {
            throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, this.getExceptionInterceptor());
        }
    }
    
    private final ResultSetImpl sendFileToServer(final StatementImpl callingStatement, final String fileName) throws SQLException {
        Buffer filePacket = (this.loadFileBufRef == null) ? null : this.loadFileBufRef.get();
        final int bigPacketLength = Math.min(this.connection.getMaxAllowedPacket() - 12, this.alignPacketSize(this.connection.getMaxAllowedPacket() - 16, 4096) - 12);
        final int oneMeg = 1048576;
        final int smallerPacketSizeAligned = Math.min(oneMeg - 12, this.alignPacketSize(oneMeg - 16, 4096) - 12);
        final int packetLength = Math.min(smallerPacketSizeAligned, bigPacketLength);
        if (filePacket == null) {
            try {
                filePacket = new Buffer(packetLength + 4);
                this.loadFileBufRef = new SoftReference((T)filePacket);
            }
            catch (OutOfMemoryError oom) {
                throw SQLError.createSQLException("Could not allocate packet of " + packetLength + " bytes required for LOAD DATA LOCAL INFILE operation." + " Try increasing max heap allocation for JVM or decreasing server variable " + "'max_allowed_packet'", "S1001", this.getExceptionInterceptor());
            }
        }
        filePacket.clear();
        this.send(filePacket, 0);
        final byte[] fileBuf = new byte[packetLength];
        BufferedInputStream fileIn = null;
        try {
            if (!this.connection.getAllowLoadLocalInfile()) {
                throw SQLError.createSQLException(Messages.getString("MysqlIO.LoadDataLocalNotAllowed"), "S1000", this.getExceptionInterceptor());
            }
            InputStream hookedStream = null;
            if (callingStatement != null) {
                hookedStream = callingStatement.getLocalInfileInputStream();
            }
            if (hookedStream != null) {
                fileIn = new BufferedInputStream(hookedStream);
            }
            else if (!this.connection.getAllowUrlInLocalInfile()) {
                fileIn = new BufferedInputStream(new FileInputStream(fileName));
            }
            else if (fileName.indexOf(58) != -1) {
                try {
                    final URL urlFromFileName = new URL(fileName);
                    fileIn = new BufferedInputStream(urlFromFileName.openStream());
                }
                catch (MalformedURLException badUrlEx) {
                    fileIn = new BufferedInputStream(new FileInputStream(fileName));
                }
            }
            else {
                fileIn = new BufferedInputStream(new FileInputStream(fileName));
            }
            int bytesRead = 0;
            while ((bytesRead = fileIn.read(fileBuf)) != -1) {
                filePacket.clear();
                filePacket.writeBytesNoNull(fileBuf, 0, bytesRead);
                this.send(filePacket, filePacket.getPosition());
            }
        }
        catch (IOException ioEx) {
            final StringBuffer messageBuf = new StringBuffer(Messages.getString("MysqlIO.60"));
            if (!this.connection.getParanoid()) {
                messageBuf.append("'");
                if (fileName != null) {
                    messageBuf.append(fileName);
                }
                messageBuf.append("'");
            }
            messageBuf.append(Messages.getString("MysqlIO.63"));
            if (!this.connection.getParanoid()) {
                messageBuf.append(Messages.getString("MysqlIO.64"));
                messageBuf.append(Util.stackTraceToString(ioEx));
            }
            throw SQLError.createSQLException(messageBuf.toString(), "S1009", this.getExceptionInterceptor());
        }
        finally {
            if (fileIn != null) {
                try {
                    fileIn.close();
                }
                catch (Exception ex) {
                    final SQLException sqlEx = SQLError.createSQLException(Messages.getString("MysqlIO.65"), "S1000", this.getExceptionInterceptor());
                    sqlEx.initCause(ex);
                    throw sqlEx;
                }
                fileIn = null;
            }
            else {
                filePacket.clear();
                this.send(filePacket, filePacket.getPosition());
                this.checkErrorPacket();
            }
        }
        filePacket.clear();
        this.send(filePacket, filePacket.getPosition());
        final Buffer resultPacket = this.checkErrorPacket();
        return this.buildResultSetWithUpdates(callingStatement, resultPacket);
    }
    
    private Buffer checkErrorPacket(final int command) throws SQLException {
        final int statusCode = 0;
        Buffer resultPacket = null;
        this.serverStatus = 0;
        try {
            resultPacket = this.reuseAndReadPacket(this.reusablePacket);
        }
        catch (SQLException sqlEx) {
            throw sqlEx;
        }
        catch (Exception fallThru) {
            throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, fallThru, this.getExceptionInterceptor());
        }
        this.checkErrorPacket(resultPacket);
        return resultPacket;
    }
    
    private void checkErrorPacket(final Buffer resultPacket) throws SQLException {
        final int statusCode = resultPacket.readByte();
        if (statusCode != -1) {
            return;
        }
        int errno = 2000;
        if (this.protocolVersion > 9) {
            errno = resultPacket.readInt();
            String xOpen = null;
            String serverErrorMessage = resultPacket.readString(this.connection.getErrorMessageEncoding(), this.getExceptionInterceptor());
            if (serverErrorMessage.charAt(0) == '#') {
                if (serverErrorMessage.length() > 6) {
                    xOpen = serverErrorMessage.substring(1, 6);
                    serverErrorMessage = serverErrorMessage.substring(6);
                    if (xOpen.equals("HY000")) {
                        xOpen = SQLError.mysqlToSqlState(errno, this.connection.getUseSqlStateCodes());
                    }
                }
                else {
                    xOpen = SQLError.mysqlToSqlState(errno, this.connection.getUseSqlStateCodes());
                }
            }
            else {
                xOpen = SQLError.mysqlToSqlState(errno, this.connection.getUseSqlStateCodes());
            }
            this.clearInputStream();
            final StringBuffer errorBuf = new StringBuffer();
            final String xOpenErrorMessage = SQLError.get(xOpen);
            if (!this.connection.getUseOnlyServerErrorMessages() && xOpenErrorMessage != null) {
                errorBuf.append(xOpenErrorMessage);
                errorBuf.append(Messages.getString("MysqlIO.68"));
            }
            errorBuf.append(serverErrorMessage);
            if (!this.connection.getUseOnlyServerErrorMessages() && xOpenErrorMessage != null) {
                errorBuf.append("\"");
            }
            this.appendInnodbStatusInformation(xOpen, errorBuf);
            if (xOpen != null && xOpen.startsWith("22")) {
                throw new MysqlDataTruncation(errorBuf.toString(), 0, true, false, 0, 0, errno);
            }
            throw SQLError.createSQLException(errorBuf.toString(), xOpen, errno, false, this.getExceptionInterceptor(), this.connection);
        }
        else {
            final String serverErrorMessage = resultPacket.readString(this.connection.getErrorMessageEncoding(), this.getExceptionInterceptor());
            this.clearInputStream();
            if (serverErrorMessage.indexOf(Messages.getString("MysqlIO.70")) != -1) {
                throw SQLError.createSQLException(SQLError.get("S0022") + ", " + serverErrorMessage, "S0022", -1, false, this.getExceptionInterceptor(), this.connection);
            }
            final StringBuffer errorBuf2 = new StringBuffer(Messages.getString("MysqlIO.72"));
            errorBuf2.append(serverErrorMessage);
            errorBuf2.append("\"");
            throw SQLError.createSQLException(SQLError.get("S1000") + ", " + errorBuf2.toString(), "S1000", -1, false, this.getExceptionInterceptor(), this.connection);
        }
    }
    
    private void appendInnodbStatusInformation(final String xOpen, final StringBuffer errorBuf) throws SQLException {
        if (this.connection.getIncludeInnodbStatusInDeadlockExceptions() && xOpen != null && (xOpen.startsWith("40") || xOpen.startsWith("41")) && this.streamingData == null) {
            ResultSet rs = null;
            try {
                rs = this.sqlQueryDirect(null, "SHOW ENGINE INNODB STATUS", this.connection.getEncoding(), null, -1, 1003, 1007, false, this.connection.getCatalog(), null);
                if (rs.next()) {
                    errorBuf.append("\n\n");
                    errorBuf.append(rs.getString("Status"));
                }
                else {
                    errorBuf.append("\n\n");
                    errorBuf.append(Messages.getString("MysqlIO.NoInnoDBStatusFound"));
                }
            }
            catch (Exception ex) {
                errorBuf.append("\n\n");
                errorBuf.append(Messages.getString("MysqlIO.InnoDBStatusFailed"));
                errorBuf.append("\n\n");
                errorBuf.append(Util.stackTraceToString(ex));
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
            }
        }
    }
    
    private final void sendSplitPackets(final Buffer packet) throws SQLException {
        try {
            Buffer headerPacket = (this.splitBufRef == null) ? null : this.splitBufRef.get();
            if (headerPacket == null) {
                headerPacket = new Buffer(this.maxThreeBytes + 4);
                this.splitBufRef = new SoftReference((T)headerPacket);
            }
            int len = packet.getPosition();
            final int splitSize = this.maxThreeBytes;
            int originalPacketPos = 4;
            final byte[] origPacketBytes = packet.getByteBuffer();
            final byte[] headerPacketBytes = headerPacket.getByteBuffer();
            while (len >= this.maxThreeBytes) {
                ++this.packetSequence;
                headerPacket.setPosition(0);
                headerPacket.writeLongInt(splitSize);
                headerPacket.writeByte(this.packetSequence);
                System.arraycopy(origPacketBytes, originalPacketPos, headerPacketBytes, 4, splitSize);
                int packetLen = splitSize + 4;
                if (!this.useCompression) {
                    this.mysqlOutput.write(headerPacketBytes, 0, splitSize + 4);
                    this.mysqlOutput.flush();
                }
                else {
                    headerPacket.setPosition(0);
                    final Buffer packetToSend = this.compressPacket(headerPacket, 4, splitSize, 4);
                    packetLen = packetToSend.getPosition();
                    this.mysqlOutput.write(packetToSend.getByteBuffer(), 0, packetLen);
                    this.mysqlOutput.flush();
                }
                originalPacketPos += splitSize;
                len -= splitSize;
            }
            headerPacket.clear();
            headerPacket.setPosition(0);
            headerPacket.writeLongInt(len - 4);
            headerPacket.writeByte(++this.packetSequence);
            if (len != 0) {
                System.arraycopy(origPacketBytes, originalPacketPos, headerPacketBytes, 4, len - 4);
            }
            int packetLen = len - 4;
            if (!this.useCompression) {
                this.mysqlOutput.write(headerPacket.getByteBuffer(), 0, len);
                this.mysqlOutput.flush();
            }
            else {
                headerPacket.setPosition(0);
                final Buffer packetToSend = this.compressPacket(headerPacket, 4, packetLen, 4);
                packetLen = packetToSend.getPosition();
                this.mysqlOutput.write(packetToSend.getByteBuffer(), 0, packetLen);
                this.mysqlOutput.flush();
            }
        }
        catch (IOException ioEx) {
            throw SQLError.createCommunicationsException(this.connection, this.lastPacketSentTimeMs, this.lastPacketReceivedTimeMs, ioEx, this.getExceptionInterceptor());
        }
    }
    
    private void reclaimLargeSharedSendPacket() {
        if (this.sharedSendPacket != null && this.sharedSendPacket.getCapacity() > 1048576) {
            this.sharedSendPacket = new Buffer(1024);
        }
    }
    
    boolean hadWarnings() {
        return this.hadWarnings;
    }
    
    void scanForAndThrowDataTruncation() throws SQLException {
        if (this.streamingData == null && this.versionMeetsMinimum(4, 1, 0) && this.connection.getJdbcCompliantTruncation() && this.warningCount > 0) {
            SQLError.convertShowWarningsToSQLWarnings(this.connection, this.warningCount, true);
        }
    }
    
    private void secureAuth(Buffer packet, final int packLength, final String user, final String password, final String database, final boolean writeClientParams) throws SQLException {
        if (packet == null) {
            packet = new Buffer(packLength);
        }
        if (writeClientParams) {
            if (this.use41Extensions) {
                if (this.versionMeetsMinimum(4, 1, 1)) {
                    packet.writeLong(this.clientParam);
                    packet.writeLong(this.maxThreeBytes);
                    packet.writeByte((byte)8);
                    packet.writeBytesNoNull(new byte[23]);
                }
                else {
                    packet.writeLong(this.clientParam);
                    packet.writeLong(this.maxThreeBytes);
                }
            }
            else {
                packet.writeInt((int)this.clientParam);
                packet.writeLongInt(this.maxThreeBytes);
            }
        }
        packet.writeString(user, "Cp1252", this.connection);
        if (password.length() != 0) {
            packet.writeString("xxxxxxxx", "Cp1252", this.connection);
        }
        else {
            packet.writeString("", "Cp1252", this.connection);
        }
        if (this.useConnectWithDb) {
            packet.writeString(database, "Cp1252", this.connection);
        }
        this.send(packet, packet.getPosition());
        if (password.length() > 0) {
            final Buffer b = this.readPacket();
            b.setPosition(0);
            final byte[] replyAsBytes = b.getByteBuffer();
            if (replyAsBytes.length == 25 && replyAsBytes[0] != 0) {
                if (replyAsBytes[0] != 42) {
                    try {
                        final byte[] buff = Security.passwordHashStage1(password);
                        byte[] passwordHash = new byte[buff.length];
                        System.arraycopy(buff, 0, passwordHash, 0, buff.length);
                        passwordHash = Security.passwordHashStage2(passwordHash, replyAsBytes);
                        final byte[] packetDataAfterSalt = new byte[replyAsBytes.length - 5];
                        System.arraycopy(replyAsBytes, 4, packetDataAfterSalt, 0, replyAsBytes.length - 5);
                        final byte[] mysqlScrambleBuff = new byte[20];
                        Security.passwordCrypt(packetDataAfterSalt, mysqlScrambleBuff, passwordHash, 20);
                        Security.passwordCrypt(mysqlScrambleBuff, buff, buff, 20);
                        final Buffer packet2 = new Buffer(25);
                        packet2.writeBytesNoNull(buff);
                        ++this.packetSequence;
                        this.send(packet2, 24);
                        return;
                    }
                    catch (NoSuchAlgorithmException nse) {
                        throw SQLError.createSQLException(Messages.getString("MysqlIO.91") + Messages.getString("MysqlIO.92"), "S1000", this.getExceptionInterceptor());
                    }
                }
                try {
                    final byte[] passwordHash2 = Security.createKeyFromOldPassword(password);
                    final byte[] netReadPos4 = new byte[replyAsBytes.length - 5];
                    System.arraycopy(replyAsBytes, 4, netReadPos4, 0, replyAsBytes.length - 5);
                    final byte[] mysqlScrambleBuff2 = new byte[20];
                    Security.passwordCrypt(netReadPos4, mysqlScrambleBuff2, passwordHash2, 20);
                    final String scrambledPassword = Util.scramble(new String(mysqlScrambleBuff2), password);
                    final Buffer packet2 = new Buffer(packLength);
                    packet2.writeString(scrambledPassword, "Cp1252", this.connection);
                    ++this.packetSequence;
                    this.send(packet2, 24);
                }
                catch (NoSuchAlgorithmException nse) {
                    throw SQLError.createSQLException(Messages.getString("MysqlIO.93") + Messages.getString("MysqlIO.94"), "S1000", this.getExceptionInterceptor());
                }
            }
        }
    }
    
    void secureAuth411(Buffer packet, final int packLength, final String user, final String password, final String database, final boolean writeClientParams) throws SQLException {
        if (packet == null) {
            packet = new Buffer(packLength);
        }
        if (writeClientParams) {
            if (this.use41Extensions) {
                if (this.versionMeetsMinimum(4, 1, 1)) {
                    packet.writeLong(this.clientParam);
                    packet.writeLong(this.maxThreeBytes);
                    packet.writeByte((byte)33);
                    packet.writeBytesNoNull(new byte[23]);
                }
                else {
                    packet.writeLong(this.clientParam);
                    packet.writeLong(this.maxThreeBytes);
                }
            }
            else {
                packet.writeInt((int)this.clientParam);
                packet.writeLongInt(this.maxThreeBytes);
            }
        }
        packet.writeString(user, "utf-8", this.connection);
        Label_0239: {
            if (password.length() != 0) {
                packet.writeByte((byte)20);
                try {
                    packet.writeBytesNoNull(Security.scramble411(password, this.seed, this.connection));
                    break Label_0239;
                }
                catch (NoSuchAlgorithmException nse) {
                    throw SQLError.createSQLException(Messages.getString("MysqlIO.95") + Messages.getString("MysqlIO.96"), "S1000", this.getExceptionInterceptor());
                }
                catch (UnsupportedEncodingException e) {
                    throw SQLError.createSQLException(Messages.getString("MysqlIO.95") + Messages.getString("MysqlIO.96"), "S1000", this.getExceptionInterceptor());
                }
            }
            packet.writeByte((byte)0);
        }
        if (this.useConnectWithDb) {
            packet.writeString(database, "utf-8", this.connection);
        }
        this.send(packet, packet.getPosition());
        final byte packetSequence = this.packetSequence;
        this.packetSequence = (byte)(packetSequence + 1);
        byte savePacketSequence = packetSequence;
        final Buffer reply = this.checkErrorPacket();
        if (reply.isLastDataPacket()) {
            ++savePacketSequence;
            this.packetSequence = savePacketSequence;
            packet.clear();
            final String seed323 = this.seed.substring(0, 8);
            packet.writeString(Util.newCrypt(password, seed323));
            this.send(packet, packet.getPosition());
            this.checkErrorPacket();
        }
    }
    
    private final ResultSetRow unpackBinaryResultSetRow(final Field[] fields, final Buffer binaryData, final int resultSetConcurrency) throws SQLException {
        final int numFields = fields.length;
        final byte[][] unpackedRowData = new byte[numFields][];
        final int nullCount = (numFields + 9) / 8;
        final byte[] nullBitMask = new byte[nullCount];
        for (int i = 0; i < nullCount; ++i) {
            nullBitMask[i] = binaryData.readByte();
        }
        int nullMaskPos = 0;
        int bit = 4;
        for (int j = 0; j < numFields; ++j) {
            if ((nullBitMask[nullMaskPos] & bit) != 0x0) {
                unpackedRowData[j] = null;
            }
            else if (resultSetConcurrency != 1008) {
                this.extractNativeEncodedColumn(binaryData, fields, j, unpackedRowData);
            }
            else {
                this.unpackNativeEncodedColumn(binaryData, fields, j, unpackedRowData);
            }
            if (((bit <<= 1) & 0xFF) == 0x0) {
                bit = 1;
                ++nullMaskPos;
            }
        }
        return new ByteArrayRow(unpackedRowData, this.getExceptionInterceptor());
    }
    
    private final void extractNativeEncodedColumn(final Buffer binaryData, final Field[] fields, final int columnIndex, final byte[][] unpackedRowData) throws SQLException {
        final Field curField = fields[columnIndex];
        switch (curField.getMysqlType()) {
            case 6: {
                break;
            }
            case 1: {
                unpackedRowData[columnIndex] = new byte[] { binaryData.readByte() };
                break;
            }
            case 2:
            case 13: {
                unpackedRowData[columnIndex] = binaryData.getBytes(2);
                break;
            }
            case 3:
            case 9: {
                unpackedRowData[columnIndex] = binaryData.getBytes(4);
                break;
            }
            case 8: {
                unpackedRowData[columnIndex] = binaryData.getBytes(8);
                break;
            }
            case 4: {
                unpackedRowData[columnIndex] = binaryData.getBytes(4);
                break;
            }
            case 5: {
                unpackedRowData[columnIndex] = binaryData.getBytes(8);
                break;
            }
            case 11: {
                final int length = (int)binaryData.readFieldLength();
                unpackedRowData[columnIndex] = binaryData.getBytes(length);
                break;
            }
            case 10: {
                final int length = (int)binaryData.readFieldLength();
                unpackedRowData[columnIndex] = binaryData.getBytes(length);
                break;
            }
            case 7:
            case 12: {
                final int length = (int)binaryData.readFieldLength();
                unpackedRowData[columnIndex] = binaryData.getBytes(length);
                break;
            }
            case 0:
            case 15:
            case 246:
            case 249:
            case 250:
            case 251:
            case 252:
            case 253:
            case 254:
            case 255: {
                unpackedRowData[columnIndex] = binaryData.readLenByteArray(0);
                break;
            }
            case 16: {
                unpackedRowData[columnIndex] = binaryData.readLenByteArray(0);
                break;
            }
            default: {
                throw SQLError.createSQLException(Messages.getString("MysqlIO.97") + curField.getMysqlType() + Messages.getString("MysqlIO.98") + columnIndex + Messages.getString("MysqlIO.99") + fields.length + Messages.getString("MysqlIO.100"), "S1000", this.getExceptionInterceptor());
            }
        }
    }
    
    private final void unpackNativeEncodedColumn(final Buffer binaryData, final Field[] fields, final int columnIndex, final byte[][] unpackedRowData) throws SQLException {
        final Field curField = fields[columnIndex];
        switch (curField.getMysqlType()) {
            case 6: {
                break;
            }
            case 1: {
                final byte tinyVal = binaryData.readByte();
                if (!curField.isUnsigned()) {
                    unpackedRowData[columnIndex] = String.valueOf(tinyVal).getBytes();
                    break;
                }
                final short unsignedTinyVal = (short)(tinyVal & 0xFF);
                unpackedRowData[columnIndex] = String.valueOf(unsignedTinyVal).getBytes();
                break;
            }
            case 2:
            case 13: {
                final short shortVal = (short)binaryData.readInt();
                if (!curField.isUnsigned()) {
                    unpackedRowData[columnIndex] = String.valueOf(shortVal).getBytes();
                    break;
                }
                final int unsignedShortVal = shortVal & 0xFFFF;
                unpackedRowData[columnIndex] = String.valueOf(unsignedShortVal).getBytes();
                break;
            }
            case 3:
            case 9: {
                final int intVal = (int)binaryData.readLong();
                if (!curField.isUnsigned()) {
                    unpackedRowData[columnIndex] = String.valueOf(intVal).getBytes();
                    break;
                }
                final long longVal = intVal & 0xFFFFFFFFL;
                unpackedRowData[columnIndex] = String.valueOf(longVal).getBytes();
                break;
            }
            case 8: {
                final long longVal = binaryData.readLongLong();
                if (!curField.isUnsigned()) {
                    unpackedRowData[columnIndex] = String.valueOf(longVal).getBytes();
                    break;
                }
                final BigInteger asBigInteger = ResultSetImpl.convertLongToUlong(longVal);
                unpackedRowData[columnIndex] = asBigInteger.toString().getBytes();
                break;
            }
            case 4: {
                final float floatVal = Float.intBitsToFloat(binaryData.readIntAsLong());
                unpackedRowData[columnIndex] = String.valueOf(floatVal).getBytes();
                break;
            }
            case 5: {
                final double doubleVal = Double.longBitsToDouble(binaryData.readLongLong());
                unpackedRowData[columnIndex] = String.valueOf(doubleVal).getBytes();
                break;
            }
            case 11: {
                final int length = (int)binaryData.readFieldLength();
                int hour = 0;
                int minute = 0;
                int seconds = 0;
                if (length != 0) {
                    binaryData.readByte();
                    binaryData.readLong();
                    hour = binaryData.readByte();
                    minute = binaryData.readByte();
                    seconds = binaryData.readByte();
                    if (length > 8) {
                        binaryData.readLong();
                    }
                }
                final byte[] timeAsBytes = { (byte)Character.forDigit(hour / 10, 10), (byte)Character.forDigit(hour % 10, 10), 58, (byte)Character.forDigit(minute / 10, 10), (byte)Character.forDigit(minute % 10, 10), 58, (byte)Character.forDigit(seconds / 10, 10), (byte)Character.forDigit(seconds % 10, 10) };
                unpackedRowData[columnIndex] = timeAsBytes;
                break;
            }
            case 10: {
                final int length = (int)binaryData.readFieldLength();
                int year = 0;
                int month = 0;
                int day = 0;
                final int hour = 0;
                final int minute = 0;
                final int seconds = 0;
                if (length != 0) {
                    year = binaryData.readInt();
                    month = binaryData.readByte();
                    day = binaryData.readByte();
                }
                if (year == 0 && month == 0 && day == 0) {
                    if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                        unpackedRowData[columnIndex] = null;
                        break;
                    }
                    if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                        throw SQLError.createSQLException("Value '0000-00-00' can not be represented as java.sql.Date", "S1009", this.getExceptionInterceptor());
                    }
                    year = 1;
                    month = 1;
                    day = 1;
                }
                final byte[] dateAsBytes = new byte[10];
                dateAsBytes[0] = (byte)Character.forDigit(year / 1000, 10);
                final int after1000 = year % 1000;
                dateAsBytes[1] = (byte)Character.forDigit(after1000 / 100, 10);
                final int after1001 = after1000 % 100;
                dateAsBytes[2] = (byte)Character.forDigit(after1001 / 10, 10);
                dateAsBytes[3] = (byte)Character.forDigit(after1001 % 10, 10);
                dateAsBytes[4] = 45;
                dateAsBytes[5] = (byte)Character.forDigit(month / 10, 10);
                dateAsBytes[6] = (byte)Character.forDigit(month % 10, 10);
                dateAsBytes[7] = 45;
                dateAsBytes[8] = (byte)Character.forDigit(day / 10, 10);
                dateAsBytes[9] = (byte)Character.forDigit(day % 10, 10);
                unpackedRowData[columnIndex] = dateAsBytes;
                break;
            }
            case 7:
            case 12: {
                final int length = (int)binaryData.readFieldLength();
                int year = 0;
                int month = 0;
                int day = 0;
                int hour = 0;
                int minute = 0;
                int seconds = 0;
                final int nanos = 0;
                if (length != 0) {
                    year = binaryData.readInt();
                    month = binaryData.readByte();
                    day = binaryData.readByte();
                    if (length > 4) {
                        hour = binaryData.readByte();
                        minute = binaryData.readByte();
                        seconds = binaryData.readByte();
                    }
                }
                if (year == 0 && month == 0 && day == 0) {
                    if ("convertToNull".equals(this.connection.getZeroDateTimeBehavior())) {
                        unpackedRowData[columnIndex] = null;
                        break;
                    }
                    if ("exception".equals(this.connection.getZeroDateTimeBehavior())) {
                        throw SQLError.createSQLException("Value '0000-00-00' can not be represented as java.sql.Timestamp", "S1009", this.getExceptionInterceptor());
                    }
                    year = 1;
                    month = 1;
                    day = 1;
                }
                int stringLength = 19;
                final byte[] nanosAsBytes = Integer.toString(nanos).getBytes();
                stringLength += 1 + nanosAsBytes.length;
                final byte[] datetimeAsBytes = new byte[stringLength];
                datetimeAsBytes[0] = (byte)Character.forDigit(year / 1000, 10);
                final int after1000 = year % 1000;
                datetimeAsBytes[1] = (byte)Character.forDigit(after1000 / 100, 10);
                final int after1001 = after1000 % 100;
                datetimeAsBytes[2] = (byte)Character.forDigit(after1001 / 10, 10);
                datetimeAsBytes[3] = (byte)Character.forDigit(after1001 % 10, 10);
                datetimeAsBytes[4] = 45;
                datetimeAsBytes[5] = (byte)Character.forDigit(month / 10, 10);
                datetimeAsBytes[6] = (byte)Character.forDigit(month % 10, 10);
                datetimeAsBytes[7] = 45;
                datetimeAsBytes[8] = (byte)Character.forDigit(day / 10, 10);
                datetimeAsBytes[9] = (byte)Character.forDigit(day % 10, 10);
                datetimeAsBytes[10] = 32;
                datetimeAsBytes[11] = (byte)Character.forDigit(hour / 10, 10);
                datetimeAsBytes[12] = (byte)Character.forDigit(hour % 10, 10);
                datetimeAsBytes[13] = 58;
                datetimeAsBytes[14] = (byte)Character.forDigit(minute / 10, 10);
                datetimeAsBytes[15] = (byte)Character.forDigit(minute % 10, 10);
                datetimeAsBytes[16] = 58;
                datetimeAsBytes[17] = (byte)Character.forDigit(seconds / 10, 10);
                datetimeAsBytes[18] = (byte)Character.forDigit(seconds % 10, 10);
                datetimeAsBytes[19] = 46;
                final int nanosOffset = 20;
                for (int j = 0; j < nanosAsBytes.length; ++j) {
                    datetimeAsBytes[nanosOffset + j] = nanosAsBytes[j];
                }
                unpackedRowData[columnIndex] = datetimeAsBytes;
                break;
            }
            case 0:
            case 15:
            case 16:
            case 246:
            case 249:
            case 250:
            case 251:
            case 252:
            case 253:
            case 254: {
                unpackedRowData[columnIndex] = binaryData.readLenByteArray(0);
                break;
            }
            default: {
                throw SQLError.createSQLException(Messages.getString("MysqlIO.97") + curField.getMysqlType() + Messages.getString("MysqlIO.98") + columnIndex + Messages.getString("MysqlIO.99") + fields.length + Messages.getString("MysqlIO.100"), "S1000", this.getExceptionInterceptor());
            }
        }
    }
    
    private void negotiateSSLConnection(final String user, final String password, final String database, final int packLength) throws SQLException {
        if (!ExportControlled.enabled()) {
            throw new ConnectionFeatureNotAvailableException(this.connection, this.lastPacketSentTimeMs, null);
        }
        boolean doSecureAuth = false;
        if ((this.serverCapabilities & 0x8000) != 0x0) {
            this.clientParam |= 0x8000L;
            doSecureAuth = true;
        }
        this.clientParam |= 0x800L;
        final Buffer packet = new Buffer(packLength);
        if (this.use41Extensions) {
            packet.writeLong(this.clientParam);
        }
        else {
            packet.writeInt((int)this.clientParam);
        }
        this.send(packet, packet.getPosition());
        ExportControlled.transformSocketToSSLSocket(this);
        packet.clear();
        if (doSecureAuth) {
            if (this.versionMeetsMinimum(4, 1, 1)) {
                this.secureAuth411(null, packLength, user, password, database, true);
            }
            else {
                this.secureAuth411(null, packLength, user, password, database, true);
            }
        }
        else {
            if (this.use41Extensions) {
                packet.writeLong(this.clientParam);
                packet.writeLong(this.maxThreeBytes);
            }
            else {
                packet.writeInt((int)this.clientParam);
                packet.writeLongInt(this.maxThreeBytes);
            }
            packet.writeString(user);
            if (this.protocolVersion > 9) {
                packet.writeString(Util.newCrypt(password, this.seed));
            }
            else {
                packet.writeString(Util.oldCrypt(password, this.seed));
            }
            if ((this.serverCapabilities & 0x8) != 0x0 && database != null && database.length() > 0) {
                packet.writeString(database);
            }
            this.send(packet, packet.getPosition());
        }
    }
    
    protected int getServerStatus() {
        return this.serverStatus;
    }
    
    protected List fetchRowsViaCursor(List fetchedRows, final long statementId, final Field[] columnTypes, final int fetchSize, final boolean useBufferRowExplicit) throws SQLException {
        if (fetchedRows == null) {
            fetchedRows = new ArrayList<ResultSetRow>(fetchSize);
        }
        else {
            fetchedRows.clear();
        }
        this.sharedSendPacket.clear();
        this.sharedSendPacket.writeByte((byte)28);
        this.sharedSendPacket.writeLong(statementId);
        this.sharedSendPacket.writeLong(fetchSize);
        this.sendCommand(28, null, this.sharedSendPacket, true, null, 0);
        ResultSetRow row = null;
        while ((row = this.nextRow(columnTypes, columnTypes.length, true, 1007, false, useBufferRowExplicit, false, null)) != null) {
            fetchedRows.add(row);
        }
        return fetchedRows;
    }
    
    protected long getThreadId() {
        return this.threadId;
    }
    
    protected boolean useNanosForElapsedTime() {
        return this.useNanosForElapsedTime;
    }
    
    protected long getSlowQueryThreshold() {
        return this.slowQueryThreshold;
    }
    
    protected String getQueryTimingUnits() {
        return this.queryTimingUnits;
    }
    
    protected int getCommandCount() {
        return this.commandCount;
    }
    
    private void checkTransactionState(final int oldStatus) throws SQLException {
        final boolean previouslyInTrans = (oldStatus & 0x1) != 0x0;
        final boolean currentlyInTrans = (this.serverStatus & 0x1) != 0x0;
        if (previouslyInTrans && !currentlyInTrans) {
            this.connection.transactionCompleted();
        }
        else if (!previouslyInTrans && currentlyInTrans) {
            this.connection.transactionBegun();
        }
    }
    
    protected void setStatementInterceptors(final List statementInterceptors) {
        this.statementInterceptors = statementInterceptors;
    }
    
    protected ExceptionInterceptor getExceptionInterceptor() {
        return this.exceptionInterceptor;
    }
    
    static {
        MysqlIO.maxBufferSize = 65535;
        MysqlIO.jvmPlatformCharset = null;
        OutputStreamWriter outWriter = null;
        try {
            outWriter = new OutputStreamWriter(new ByteArrayOutputStream());
            MysqlIO.jvmPlatformCharset = outWriter.getEncoding();
        }
        finally {
            try {
                if (outWriter != null) {
                    outWriter.close();
                }
            }
            catch (IOException ex) {}
        }
    }
}
