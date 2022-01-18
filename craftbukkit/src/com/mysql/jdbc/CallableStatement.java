// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.HashMap;
import java.io.Reader;
import java.io.InputStream;
import java.sql.Statement;
import java.util.Iterator;
import java.net.URL;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Ref;
import java.sql.ParameterMetaData;
import java.util.Map;
import java.util.Calendar;
import java.sql.Date;
import java.sql.Clob;
import java.sql.Blob;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.sql.SQLException;
import java.lang.reflect.Constructor;

public class CallableStatement extends PreparedStatement implements java.sql.CallableStatement
{
    protected static final Constructor JDBC_4_CSTMT_2_ARGS_CTOR;
    protected static final Constructor JDBC_4_CSTMT_4_ARGS_CTOR;
    private static final int NOT_OUTPUT_PARAMETER_INDICATOR = Integer.MIN_VALUE;
    private static final String PARAMETER_NAMESPACE_PREFIX = "@com_mysql_jdbc_outparam_";
    private boolean callingStoredFunction;
    private ResultSetInternalMethods functionReturnValueResults;
    private boolean hasOutputParams;
    private ResultSetInternalMethods outputParameterResults;
    protected boolean outputParamWasNull;
    private int[] parameterIndexToRsIndex;
    protected CallableStatementParamInfo paramInfo;
    private CallableStatementParam returnValueParam;
    private int[] placeholderToParameterIndexMap;
    
    private static String mangleParameterName(final String origParameterName) {
        if (origParameterName == null) {
            return null;
        }
        int offset = 0;
        if (origParameterName.length() > 0 && origParameterName.charAt(0) == '@') {
            offset = 1;
        }
        final StringBuffer paramNameBuf = new StringBuffer("@com_mysql_jdbc_outparam_".length() + origParameterName.length());
        paramNameBuf.append("@com_mysql_jdbc_outparam_");
        paramNameBuf.append(origParameterName.substring(offset));
        return paramNameBuf.toString();
    }
    
    public CallableStatement(final MySQLConnection conn, final CallableStatementParamInfo paramInfo) throws SQLException {
        super(conn, paramInfo.nativeSql, paramInfo.catalogInUse);
        this.callingStoredFunction = false;
        this.hasOutputParams = false;
        this.outputParamWasNull = false;
        this.paramInfo = paramInfo;
        this.callingStoredFunction = this.paramInfo.isFunctionCall;
        if (this.callingStoredFunction) {
            ++this.parameterCount;
        }
        this.retrieveGeneratedKeys = true;
    }
    
    protected static CallableStatement getInstance(final MySQLConnection conn, final String sql, final String catalog, final boolean isFunctionCall) throws SQLException {
        if (!Util.isJdbc4()) {
            return new CallableStatement(conn, sql, catalog, isFunctionCall);
        }
        return (CallableStatement)Util.handleNewInstance(CallableStatement.JDBC_4_CSTMT_4_ARGS_CTOR, new Object[] { conn, sql, catalog, isFunctionCall }, conn.getExceptionInterceptor());
    }
    
    protected static CallableStatement getInstance(final MySQLConnection conn, final CallableStatementParamInfo paramInfo) throws SQLException {
        if (!Util.isJdbc4()) {
            return new CallableStatement(conn, paramInfo);
        }
        return (CallableStatement)Util.handleNewInstance(CallableStatement.JDBC_4_CSTMT_2_ARGS_CTOR, new Object[] { conn, paramInfo }, conn.getExceptionInterceptor());
    }
    
    private void generateParameterMap() throws SQLException {
        if (this.paramInfo == null) {
            return;
        }
        int parameterCountFromMetaData = this.paramInfo.getParameterCount();
        if (this.callingStoredFunction) {
            --parameterCountFromMetaData;
        }
        if (this.paramInfo != null && this.parameterCount != parameterCountFromMetaData) {
            this.placeholderToParameterIndexMap = new int[this.parameterCount];
            final int startPos = this.callingStoredFunction ? StringUtils.indexOfIgnoreCase(this.originalSql, "SELECT") : StringUtils.indexOfIgnoreCase(this.originalSql, "CALL");
            if (startPos != -1) {
                final int parenOpenPos = this.originalSql.indexOf(40, startPos + 4);
                if (parenOpenPos != -1) {
                    final int parenClosePos = StringUtils.indexOfIgnoreCaseRespectQuotes(parenOpenPos, this.originalSql, ")", '\'', true);
                    if (parenClosePos != -1) {
                        final List parsedParameters = StringUtils.split(this.originalSql.substring(parenOpenPos + 1, parenClosePos), ",", "'\"", "'\"", true);
                        final int numParsedParameters = parsedParameters.size();
                        if (numParsedParameters != this.parameterCount) {}
                        int placeholderCount = 0;
                        for (int i = 0; i < numParsedParameters; ++i) {
                            if (parsedParameters.get(i).equals("?")) {
                                this.placeholderToParameterIndexMap[placeholderCount++] = i;
                            }
                        }
                    }
                }
            }
        }
    }
    
    public CallableStatement(final MySQLConnection conn, final String sql, final String catalog, final boolean isFunctionCall) throws SQLException {
        super(conn, sql, catalog);
        this.callingStoredFunction = false;
        this.hasOutputParams = false;
        this.outputParamWasNull = false;
        if (!(this.callingStoredFunction = isFunctionCall)) {
            if (!StringUtils.startsWithIgnoreCaseAndWs(sql, "CALL")) {
                this.fakeParameterTypes(false);
            }
            else {
                this.determineParameterTypes();
            }
            this.generateParameterMap();
        }
        else {
            this.determineParameterTypes();
            this.generateParameterMap();
            ++this.parameterCount;
        }
        this.retrieveGeneratedKeys = true;
    }
    
    public void addBatch() throws SQLException {
        this.setOutParams();
        super.addBatch();
    }
    
    private CallableStatementParam checkIsOutputParam(int paramIndex) throws SQLException {
        if (this.callingStoredFunction) {
            if (paramIndex == 1) {
                if (this.returnValueParam == null) {
                    this.returnValueParam = new CallableStatementParam("", 0, false, true, 12, "VARCHAR", 0, 0, (short)2, 5);
                }
                return this.returnValueParam;
            }
            --paramIndex;
        }
        this.checkParameterIndexBounds(paramIndex);
        int localParamIndex = paramIndex - 1;
        if (this.placeholderToParameterIndexMap != null) {
            localParamIndex = this.placeholderToParameterIndexMap[localParamIndex];
        }
        final CallableStatementParam paramDescriptor = this.paramInfo.getParameter(localParamIndex);
        if (this.connection.getNoAccessToProcedureBodies()) {
            paramDescriptor.isOut = true;
            paramDescriptor.isIn = true;
            paramDescriptor.inOutModifier = 2;
        }
        else if (!paramDescriptor.isOut) {
            throw SQLError.createSQLException(Messages.getString("CallableStatement.9") + paramIndex + Messages.getString("CallableStatement.10"), "S1009", this.getExceptionInterceptor());
        }
        this.hasOutputParams = true;
        return paramDescriptor;
    }
    
    private void checkParameterIndexBounds(final int paramIndex) throws SQLException {
        this.paramInfo.checkBounds(paramIndex);
    }
    
    private void checkStreamability() throws SQLException {
        if (this.hasOutputParams && this.createStreamingResultSet()) {
            throw SQLError.createSQLException(Messages.getString("CallableStatement.14"), "S1C00", this.getExceptionInterceptor());
        }
    }
    
    public synchronized void clearParameters() throws SQLException {
        super.clearParameters();
        try {
            if (this.outputParameterResults != null) {
                this.outputParameterResults.close();
            }
        }
        finally {
            this.outputParameterResults = null;
        }
    }
    
    private void fakeParameterTypes(final boolean isReallyProcedure) throws SQLException {
        final Field[] fields = { new Field("", "PROCEDURE_CAT", 1, 0), new Field("", "PROCEDURE_SCHEM", 1, 0), new Field("", "PROCEDURE_NAME", 1, 0), new Field("", "COLUMN_NAME", 1, 0), new Field("", "COLUMN_TYPE", 1, 0), new Field("", "DATA_TYPE", 5, 0), new Field("", "TYPE_NAME", 1, 0), new Field("", "PRECISION", 4, 0), new Field("", "LENGTH", 4, 0), new Field("", "SCALE", 5, 0), new Field("", "RADIX", 5, 0), new Field("", "NULLABLE", 5, 0), new Field("", "REMARKS", 1, 0) };
        final String procName = isReallyProcedure ? this.extractProcedureName() : null;
        byte[] procNameAsBytes = null;
        try {
            procNameAsBytes = (byte[])((procName == null) ? null : procName.getBytes("UTF-8"));
        }
        catch (UnsupportedEncodingException ueEx) {
            procNameAsBytes = StringUtils.s2b(procName, this.connection);
        }
        final ArrayList resultRows = new ArrayList();
        for (int i = 0; i < this.parameterCount; ++i) {
            final byte[][] row = { null, null, procNameAsBytes, StringUtils.s2b(String.valueOf(i), this.connection), StringUtils.s2b(String.valueOf(1), this.connection), StringUtils.s2b(String.valueOf(12), this.connection), StringUtils.s2b("VARCHAR", this.connection), StringUtils.s2b(Integer.toString(65535), this.connection), StringUtils.s2b(Integer.toString(65535), this.connection), StringUtils.s2b(Integer.toString(0), this.connection), StringUtils.s2b(Integer.toString(10), this.connection), StringUtils.s2b(Integer.toString(2), this.connection), null };
            resultRows.add(new ByteArrayRow(row, this.getExceptionInterceptor()));
        }
        final ResultSet paramTypesRs = DatabaseMetaData.buildResultSet(fields, resultRows, this.connection);
        this.convertGetProcedureColumnsToInternalDescriptors(paramTypesRs);
    }
    
    private void determineParameterTypes() throws SQLException {
        ResultSet paramTypesRs = null;
        try {
            String procName = this.extractProcedureName();
            String quotedId = "";
            try {
                quotedId = (this.connection.supportsQuotedIdentifiers() ? this.connection.getMetaData().getIdentifierQuoteString() : "");
            }
            catch (SQLException sqlEx) {
                AssertionFailedException.shouldNotHappen(sqlEx);
            }
            final List parseList = StringUtils.splitDBdotName(procName, "", quotedId, this.connection.isNoBackslashEscapesSet());
            String tmpCatalog = "";
            if (parseList.size() == 2) {
                tmpCatalog = parseList.get(0);
                procName = parseList.get(1);
            }
            final java.sql.DatabaseMetaData dbmd = this.connection.getMetaData();
            boolean useCatalog = false;
            if (tmpCatalog.length() <= 0) {
                useCatalog = true;
            }
            paramTypesRs = dbmd.getProcedureColumns((this.connection.versionMeetsMinimum(5, 0, 2) && useCatalog) ? this.currentCatalog : tmpCatalog, null, procName, "%");
            boolean hasResults = false;
            try {
                if (paramTypesRs.next()) {
                    paramTypesRs.previous();
                    hasResults = true;
                }
            }
            catch (Exception ex) {}
            if (hasResults) {
                this.convertGetProcedureColumnsToInternalDescriptors(paramTypesRs);
            }
            else {
                this.fakeParameterTypes(true);
            }
        }
        finally {
            SQLException sqlExRethrow = null;
            if (paramTypesRs != null) {
                try {
                    paramTypesRs.close();
                }
                catch (SQLException sqlEx2) {
                    sqlExRethrow = sqlEx2;
                }
                paramTypesRs = null;
            }
            if (sqlExRethrow != null) {
                throw sqlExRethrow;
            }
        }
    }
    
    private void convertGetProcedureColumnsToInternalDescriptors(final ResultSet paramTypesRs) throws SQLException {
        if (!this.connection.isRunningOnJDK13()) {
            this.paramInfo = new CallableStatementParamInfoJDBC3(paramTypesRs);
        }
        else {
            this.paramInfo = new CallableStatementParamInfo(paramTypesRs);
        }
    }
    
    public boolean execute() throws SQLException {
        boolean returnVal = false;
        this.checkClosed();
        this.checkStreamability();
        synchronized (this.connection.getMutex()) {
            this.setInOutParamsOnServer();
            this.setOutParams();
            returnVal = super.execute();
            if (this.callingStoredFunction) {
                (this.functionReturnValueResults = this.results).next();
                this.results = null;
            }
            this.retrieveOutParams();
        }
        return !this.callingStoredFunction && returnVal;
    }
    
    public ResultSet executeQuery() throws SQLException {
        this.checkClosed();
        this.checkStreamability();
        ResultSet execResults = null;
        synchronized (this.connection.getMutex()) {
            this.setInOutParamsOnServer();
            this.setOutParams();
            execResults = super.executeQuery();
            this.retrieveOutParams();
        }
        return execResults;
    }
    
    public int executeUpdate() throws SQLException {
        int returnVal = -1;
        this.checkClosed();
        this.checkStreamability();
        if (this.callingStoredFunction) {
            this.execute();
            return -1;
        }
        synchronized (this.connection.getMutex()) {
            this.setInOutParamsOnServer();
            this.setOutParams();
            returnVal = super.executeUpdate();
            this.retrieveOutParams();
        }
        return returnVal;
    }
    
    private String extractProcedureName() throws SQLException {
        final String sanitizedSql = StringUtils.stripComments(this.originalSql, "`\"'", "`\"'", true, false, true, true);
        int endCallIndex = StringUtils.indexOfIgnoreCase(sanitizedSql, "CALL ");
        int offset = 5;
        if (endCallIndex == -1) {
            endCallIndex = StringUtils.indexOfIgnoreCase(sanitizedSql, "SELECT ");
            offset = 7;
        }
        if (endCallIndex != -1) {
            final StringBuffer nameBuf = new StringBuffer();
            final String trimmedStatement = sanitizedSql.substring(endCallIndex + offset).trim();
            for (int statementLength = trimmedStatement.length(), i = 0; i < statementLength; ++i) {
                final char c = trimmedStatement.charAt(i);
                if (Character.isWhitespace(c) || c == '(') {
                    break;
                }
                if (c == '?') {
                    break;
                }
                nameBuf.append(c);
            }
            return nameBuf.toString();
        }
        throw SQLError.createSQLException(Messages.getString("CallableStatement.1"), "S1000", this.getExceptionInterceptor());
    }
    
    protected String fixParameterName(String paramNameIn) throws SQLException {
        if ((paramNameIn == null || paramNameIn.length() == 0) && !this.hasParametersView()) {
            throw SQLError.createSQLException((Messages.getString("CallableStatement.0") + paramNameIn == null) ? Messages.getString("CallableStatement.15") : Messages.getString("CallableStatement.16"), "S1009", this.getExceptionInterceptor());
        }
        if (paramNameIn == null && this.hasParametersView()) {
            paramNameIn = "nullpn";
        }
        if (this.connection.getNoAccessToProcedureBodies()) {
            throw SQLError.createSQLException("No access to parameters by name when connection has been configured not to access procedure bodies", "S1009", this.getExceptionInterceptor());
        }
        return mangleParameterName(paramNameIn);
    }
    
    public synchronized Array getArray(final int i) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(i);
        final Array retValue = rs.getArray(this.mapOutputParameterIndexToRsIndex(i));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Array getArray(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final Array retValue = rs.getArray(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized BigDecimal getBigDecimal(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final BigDecimal retValue = rs.getBigDecimal(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized BigDecimal getBigDecimal(final int parameterIndex, final int scale) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final BigDecimal retValue = rs.getBigDecimal(this.mapOutputParameterIndexToRsIndex(parameterIndex), scale);
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized BigDecimal getBigDecimal(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final BigDecimal retValue = rs.getBigDecimal(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Blob getBlob(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final Blob retValue = rs.getBlob(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Blob getBlob(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final Blob retValue = rs.getBlob(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized boolean getBoolean(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final boolean retValue = rs.getBoolean(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized boolean getBoolean(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final boolean retValue = rs.getBoolean(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized byte getByte(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final byte retValue = rs.getByte(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized byte getByte(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final byte retValue = rs.getByte(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized byte[] getBytes(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final byte[] retValue = rs.getBytes(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized byte[] getBytes(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final byte[] retValue = rs.getBytes(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Clob getClob(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final Clob retValue = rs.getClob(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Clob getClob(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final Clob retValue = rs.getClob(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Date getDate(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final Date retValue = rs.getDate(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Date getDate(final int parameterIndex, final Calendar cal) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final Date retValue = rs.getDate(this.mapOutputParameterIndexToRsIndex(parameterIndex), cal);
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Date getDate(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final Date retValue = rs.getDate(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Date getDate(final String parameterName, final Calendar cal) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final Date retValue = rs.getDate(this.fixParameterName(parameterName), cal);
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized double getDouble(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final double retValue = rs.getDouble(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized double getDouble(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final double retValue = rs.getDouble(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized float getFloat(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final float retValue = rs.getFloat(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized float getFloat(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final float retValue = rs.getFloat(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized int getInt(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final int retValue = rs.getInt(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized int getInt(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final int retValue = rs.getInt(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized long getLong(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final long retValue = rs.getLong(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized long getLong(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final long retValue = rs.getLong(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    protected int getNamedParamIndex(final String paramName, final boolean forOut) throws SQLException {
        if (this.connection.getNoAccessToProcedureBodies()) {
            throw SQLError.createSQLException("No access to parameters by name when connection has been configured not to access procedure bodies", "S1009", this.getExceptionInterceptor());
        }
        if (paramName == null || paramName.length() == 0) {
            throw SQLError.createSQLException(Messages.getString("CallableStatement.2"), "S1009", this.getExceptionInterceptor());
        }
        if (this.paramInfo == null) {
            throw SQLError.createSQLException(Messages.getString("CallableStatement.3") + paramName + Messages.getString("CallableStatement.4"), "S1009", this.getExceptionInterceptor());
        }
        final CallableStatementParam namedParamInfo = this.paramInfo.getParameter(paramName);
        if (forOut && !namedParamInfo.isOut) {
            throw SQLError.createSQLException(Messages.getString("CallableStatement.5") + paramName + Messages.getString("CallableStatement.6"), "S1009", this.getExceptionInterceptor());
        }
        if (this.placeholderToParameterIndexMap == null) {
            return namedParamInfo.index + 1;
        }
        for (int i = 0; i < this.placeholderToParameterIndexMap.length; ++i) {
            if (this.placeholderToParameterIndexMap[i] == namedParamInfo.index) {
                return i + 1;
            }
        }
        throw SQLError.createSQLException("Can't find local placeholder mapping for parameter named \"" + paramName + "\".", "S1009", this.getExceptionInterceptor());
    }
    
    public synchronized Object getObject(final int parameterIndex) throws SQLException {
        final CallableStatementParam paramDescriptor = this.checkIsOutputParam(parameterIndex);
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final Object retVal = rs.getObjectStoredProc(this.mapOutputParameterIndexToRsIndex(parameterIndex), paramDescriptor.desiredJdbcType);
        this.outputParamWasNull = rs.wasNull();
        return retVal;
    }
    
    public synchronized Object getObject(final int parameterIndex, final Map map) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final Object retVal = rs.getObject(this.mapOutputParameterIndexToRsIndex(parameterIndex), map);
        this.outputParamWasNull = rs.wasNull();
        return retVal;
    }
    
    public synchronized Object getObject(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final Object retValue = rs.getObject(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Object getObject(final String parameterName, final Map map) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final Object retValue = rs.getObject(this.fixParameterName(parameterName), map);
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    protected ResultSetInternalMethods getOutputParameters(final int paramIndex) throws SQLException {
        this.outputParamWasNull = false;
        if (paramIndex == 1 && this.callingStoredFunction && this.returnValueParam != null) {
            return this.functionReturnValueResults;
        }
        if (this.outputParameterResults != null) {
            return this.outputParameterResults;
        }
        if (this.paramInfo.numberOfParameters() == 0) {
            throw SQLError.createSQLException(Messages.getString("CallableStatement.7"), "S1009", this.getExceptionInterceptor());
        }
        throw SQLError.createSQLException(Messages.getString("CallableStatement.8"), "S1000", this.getExceptionInterceptor());
    }
    
    public synchronized ParameterMetaData getParameterMetaData() throws SQLException {
        if (this.placeholderToParameterIndexMap == null) {
            return (CallableStatementParamInfoJDBC3)this.paramInfo;
        }
        return new CallableStatementParamInfoJDBC3(this.paramInfo);
    }
    
    public synchronized Ref getRef(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final Ref retValue = rs.getRef(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Ref getRef(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final Ref retValue = rs.getRef(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized short getShort(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final short retValue = rs.getShort(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized short getShort(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final short retValue = rs.getShort(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized String getString(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final String retValue = rs.getString(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized String getString(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final String retValue = rs.getString(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Time getTime(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final Time retValue = rs.getTime(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Time getTime(final int parameterIndex, final Calendar cal) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final Time retValue = rs.getTime(this.mapOutputParameterIndexToRsIndex(parameterIndex), cal);
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Time getTime(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final Time retValue = rs.getTime(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Time getTime(final String parameterName, final Calendar cal) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final Time retValue = rs.getTime(this.fixParameterName(parameterName), cal);
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Timestamp getTimestamp(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final Timestamp retValue = rs.getTimestamp(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Timestamp getTimestamp(final int parameterIndex, final Calendar cal) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final Timestamp retValue = rs.getTimestamp(this.mapOutputParameterIndexToRsIndex(parameterIndex), cal);
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Timestamp getTimestamp(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final Timestamp retValue = rs.getTimestamp(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized Timestamp getTimestamp(final String parameterName, final Calendar cal) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final Timestamp retValue = rs.getTimestamp(this.fixParameterName(parameterName), cal);
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized URL getURL(final int parameterIndex) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(parameterIndex);
        final URL retValue = rs.getURL(this.mapOutputParameterIndexToRsIndex(parameterIndex));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    public synchronized URL getURL(final String parameterName) throws SQLException {
        final ResultSetInternalMethods rs = this.getOutputParameters(0);
        final URL retValue = rs.getURL(this.fixParameterName(parameterName));
        this.outputParamWasNull = rs.wasNull();
        return retValue;
    }
    
    protected int mapOutputParameterIndexToRsIndex(final int paramIndex) throws SQLException {
        if (this.returnValueParam != null && paramIndex == 1) {
            return 1;
        }
        this.checkParameterIndexBounds(paramIndex);
        int localParamIndex = paramIndex - 1;
        if (this.placeholderToParameterIndexMap != null) {
            localParamIndex = this.placeholderToParameterIndexMap[localParamIndex];
        }
        final int rsIndex = this.parameterIndexToRsIndex[localParamIndex];
        if (rsIndex == Integer.MIN_VALUE) {
            throw SQLError.createSQLException(Messages.getString("CallableStatement.21") + paramIndex + Messages.getString("CallableStatement.22"), "S1009", this.getExceptionInterceptor());
        }
        return rsIndex + 1;
    }
    
    public void registerOutParameter(final int parameterIndex, final int sqlType) throws SQLException {
        final CallableStatementParam paramDescriptor = this.checkIsOutputParam(parameterIndex);
        paramDescriptor.desiredJdbcType = sqlType;
    }
    
    public void registerOutParameter(final int parameterIndex, final int sqlType, final int scale) throws SQLException {
        this.registerOutParameter(parameterIndex, sqlType);
    }
    
    public void registerOutParameter(final int parameterIndex, final int sqlType, final String typeName) throws SQLException {
        this.checkIsOutputParam(parameterIndex);
    }
    
    public synchronized void registerOutParameter(final String parameterName, final int sqlType) throws SQLException {
        this.registerOutParameter(this.getNamedParamIndex(parameterName, true), sqlType);
    }
    
    public void registerOutParameter(final String parameterName, final int sqlType, final int scale) throws SQLException {
        this.registerOutParameter(this.getNamedParamIndex(parameterName, true), sqlType);
    }
    
    public void registerOutParameter(final String parameterName, final int sqlType, final String typeName) throws SQLException {
        this.registerOutParameter(this.getNamedParamIndex(parameterName, true), sqlType, typeName);
    }
    
    private void retrieveOutParams() throws SQLException {
        final int numParameters = this.paramInfo.numberOfParameters();
        this.parameterIndexToRsIndex = new int[numParameters];
        for (int i = 0; i < numParameters; ++i) {
            this.parameterIndexToRsIndex[i] = Integer.MIN_VALUE;
        }
        int localParamIndex = 0;
        if (numParameters > 0) {
            final StringBuffer outParameterQuery = new StringBuffer("SELECT ");
            boolean firstParam = true;
            boolean hadOutputParams = false;
            for (final CallableStatementParam retrParamInfo : this.paramInfo) {
                if (retrParamInfo.isOut) {
                    hadOutputParams = true;
                    this.parameterIndexToRsIndex[retrParamInfo.index] = localParamIndex++;
                    if (retrParamInfo.paramName == null && this.hasParametersView()) {
                        retrParamInfo.paramName = "nullnp" + retrParamInfo.index;
                    }
                    final String outParameterName = mangleParameterName(retrParamInfo.paramName);
                    if (!firstParam) {
                        outParameterQuery.append(",");
                    }
                    else {
                        firstParam = false;
                    }
                    if (!outParameterName.startsWith("@")) {
                        outParameterQuery.append('@');
                    }
                    outParameterQuery.append(outParameterName);
                }
            }
            if (hadOutputParams) {
                Statement outParameterStmt = null;
                ResultSet outParamRs = null;
                try {
                    outParameterStmt = this.connection.createStatement();
                    outParamRs = outParameterStmt.executeQuery(outParameterQuery.toString());
                    this.outputParameterResults = ((ResultSetInternalMethods)outParamRs).copy();
                    if (!this.outputParameterResults.next()) {
                        this.outputParameterResults.close();
                        this.outputParameterResults = null;
                    }
                }
                finally {
                    if (outParameterStmt != null) {
                        outParameterStmt.close();
                    }
                }
            }
            else {
                this.outputParameterResults = null;
            }
        }
        else {
            this.outputParameterResults = null;
        }
    }
    
    public void setAsciiStream(final String parameterName, final InputStream x, final int length) throws SQLException {
        this.setAsciiStream(this.getNamedParamIndex(parameterName, false), x, length);
    }
    
    public void setBigDecimal(final String parameterName, final BigDecimal x) throws SQLException {
        this.setBigDecimal(this.getNamedParamIndex(parameterName, false), x);
    }
    
    public void setBinaryStream(final String parameterName, final InputStream x, final int length) throws SQLException {
        this.setBinaryStream(this.getNamedParamIndex(parameterName, false), x, length);
    }
    
    public void setBoolean(final String parameterName, final boolean x) throws SQLException {
        this.setBoolean(this.getNamedParamIndex(parameterName, false), x);
    }
    
    public void setByte(final String parameterName, final byte x) throws SQLException {
        this.setByte(this.getNamedParamIndex(parameterName, false), x);
    }
    
    public void setBytes(final String parameterName, final byte[] x) throws SQLException {
        this.setBytes(this.getNamedParamIndex(parameterName, false), x);
    }
    
    public void setCharacterStream(final String parameterName, final Reader reader, final int length) throws SQLException {
        this.setCharacterStream(this.getNamedParamIndex(parameterName, false), reader, length);
    }
    
    public void setDate(final String parameterName, final Date x) throws SQLException {
        this.setDate(this.getNamedParamIndex(parameterName, false), x);
    }
    
    public void setDate(final String parameterName, final Date x, final Calendar cal) throws SQLException {
        this.setDate(this.getNamedParamIndex(parameterName, false), x, cal);
    }
    
    public void setDouble(final String parameterName, final double x) throws SQLException {
        this.setDouble(this.getNamedParamIndex(parameterName, false), x);
    }
    
    public void setFloat(final String parameterName, final float x) throws SQLException {
        this.setFloat(this.getNamedParamIndex(parameterName, false), x);
    }
    
    private void setInOutParamsOnServer() throws SQLException {
        if (this.paramInfo.numParameters > 0) {
            int parameterIndex = 0;
            for (final CallableStatementParam inParamInfo : this.paramInfo) {
                if (inParamInfo.isOut && inParamInfo.isIn) {
                    if (inParamInfo.paramName == null && this.hasParametersView()) {
                        inParamInfo.paramName = "nullnp" + inParamInfo.index;
                    }
                    final String inOutParameterName = mangleParameterName(inParamInfo.paramName);
                    final StringBuffer queryBuf = new StringBuffer(4 + inOutParameterName.length() + 1 + 1);
                    queryBuf.append("SET ");
                    queryBuf.append(inOutParameterName);
                    queryBuf.append("=?");
                    PreparedStatement setPstmt = null;
                    try {
                        setPstmt = (PreparedStatement)this.connection.clientPrepareStatement(queryBuf.toString());
                        final byte[] parameterAsBytes = this.getBytesRepresentation(inParamInfo.index);
                        if (parameterAsBytes != null) {
                            if (parameterAsBytes.length > 8 && parameterAsBytes[0] == 95 && parameterAsBytes[1] == 98 && parameterAsBytes[2] == 105 && parameterAsBytes[3] == 110 && parameterAsBytes[4] == 97 && parameterAsBytes[5] == 114 && parameterAsBytes[6] == 121 && parameterAsBytes[7] == 39) {
                                setPstmt.setBytesNoEscapeNoQuotes(1, parameterAsBytes);
                            }
                            else {
                                final int sqlType = inParamInfo.desiredJdbcType;
                                switch (sqlType) {
                                    case -7:
                                    case -4:
                                    case -3:
                                    case -2:
                                    case 2000:
                                    case 2004: {
                                        setPstmt.setBytes(1, parameterAsBytes);
                                        break;
                                    }
                                    default: {
                                        setPstmt.setBytesNoEscape(1, parameterAsBytes);
                                        break;
                                    }
                                }
                            }
                        }
                        else {
                            setPstmt.setNull(1, 0);
                        }
                        setPstmt.executeUpdate();
                    }
                    finally {
                        if (setPstmt != null) {
                            setPstmt.close();
                        }
                    }
                }
                ++parameterIndex;
            }
        }
    }
    
    public void setInt(final String parameterName, final int x) throws SQLException {
        this.setInt(this.getNamedParamIndex(parameterName, false), x);
    }
    
    public void setLong(final String parameterName, final long x) throws SQLException {
        this.setLong(this.getNamedParamIndex(parameterName, false), x);
    }
    
    public void setNull(final String parameterName, final int sqlType) throws SQLException {
        this.setNull(this.getNamedParamIndex(parameterName, false), sqlType);
    }
    
    public void setNull(final String parameterName, final int sqlType, final String typeName) throws SQLException {
        this.setNull(this.getNamedParamIndex(parameterName, false), sqlType, typeName);
    }
    
    public void setObject(final String parameterName, final Object x) throws SQLException {
        this.setObject(this.getNamedParamIndex(parameterName, false), x);
    }
    
    public void setObject(final String parameterName, final Object x, final int targetSqlType) throws SQLException {
        this.setObject(this.getNamedParamIndex(parameterName, false), x, targetSqlType);
    }
    
    public void setObject(final String parameterName, final Object x, final int targetSqlType, final int scale) throws SQLException {
    }
    
    private void setOutParams() throws SQLException {
        if (this.paramInfo.numParameters > 0) {
            for (final CallableStatementParam outParamInfo : this.paramInfo) {
                if (!this.callingStoredFunction && outParamInfo.isOut) {
                    if (outParamInfo.paramName == null && this.hasParametersView()) {
                        outParamInfo.paramName = "nullnp" + outParamInfo.index;
                    }
                    final String outParameterName = mangleParameterName(outParamInfo.paramName);
                    int outParamIndex = 0;
                    if (this.placeholderToParameterIndexMap == null) {
                        outParamIndex = outParamInfo.index + 1;
                    }
                    else {
                        boolean found = false;
                        for (int i = 0; i < this.placeholderToParameterIndexMap.length; ++i) {
                            if (this.placeholderToParameterIndexMap[i] == outParamInfo.index) {
                                outParamIndex = i + 1;
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            throw SQLError.createSQLException("boo!", "S1000", this.connection.getExceptionInterceptor());
                        }
                    }
                    this.setBytesNoEscapeNoQuotes(outParamIndex, StringUtils.getBytes(outParameterName, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.getExceptionInterceptor()));
                }
            }
        }
    }
    
    public void setShort(final String parameterName, final short x) throws SQLException {
        this.setShort(this.getNamedParamIndex(parameterName, false), x);
    }
    
    public void setString(final String parameterName, final String x) throws SQLException {
        this.setString(this.getNamedParamIndex(parameterName, false), x);
    }
    
    public void setTime(final String parameterName, final Time x) throws SQLException {
        this.setTime(this.getNamedParamIndex(parameterName, false), x);
    }
    
    public void setTime(final String parameterName, final Time x, final Calendar cal) throws SQLException {
        this.setTime(this.getNamedParamIndex(parameterName, false), x, cal);
    }
    
    public void setTimestamp(final String parameterName, final Timestamp x) throws SQLException {
        this.setTimestamp(this.getNamedParamIndex(parameterName, false), x);
    }
    
    public void setTimestamp(final String parameterName, final Timestamp x, final Calendar cal) throws SQLException {
        this.setTimestamp(this.getNamedParamIndex(parameterName, false), x, cal);
    }
    
    public void setURL(final String parameterName, final URL val) throws SQLException {
        this.setURL(this.getNamedParamIndex(parameterName, false), val);
    }
    
    public synchronized boolean wasNull() throws SQLException {
        return this.outputParamWasNull;
    }
    
    public int[] executeBatch() throws SQLException {
        if (this.hasOutputParams) {
            throw SQLError.createSQLException("Can't call executeBatch() on CallableStatement with OUTPUT parameters", "S1009", this.getExceptionInterceptor());
        }
        return super.executeBatch();
    }
    
    protected int getParameterIndexOffset() {
        if (this.callingStoredFunction) {
            return -1;
        }
        return super.getParameterIndexOffset();
    }
    
    public void setAsciiStream(final String parameterName, final InputStream x) throws SQLException {
        this.setAsciiStream(this.getNamedParamIndex(parameterName, false), x);
    }
    
    public void setAsciiStream(final String parameterName, final InputStream x, final long length) throws SQLException {
        this.setAsciiStream(this.getNamedParamIndex(parameterName, false), x, length);
    }
    
    public void setBinaryStream(final String parameterName, final InputStream x) throws SQLException {
        this.setBinaryStream(this.getNamedParamIndex(parameterName, false), x);
    }
    
    public void setBinaryStream(final String parameterName, final InputStream x, final long length) throws SQLException {
        this.setBinaryStream(this.getNamedParamIndex(parameterName, false), x, length);
    }
    
    public void setBlob(final String parameterName, final Blob x) throws SQLException {
        this.setBlob(this.getNamedParamIndex(parameterName, false), x);
    }
    
    public void setBlob(final String parameterName, final InputStream inputStream) throws SQLException {
        this.setBlob(this.getNamedParamIndex(parameterName, false), inputStream);
    }
    
    public void setBlob(final String parameterName, final InputStream inputStream, final long length) throws SQLException {
        this.setBlob(this.getNamedParamIndex(parameterName, false), inputStream, length);
    }
    
    public void setCharacterStream(final String parameterName, final Reader reader) throws SQLException {
        this.setCharacterStream(this.getNamedParamIndex(parameterName, false), reader);
    }
    
    public void setCharacterStream(final String parameterName, final Reader reader, final long length) throws SQLException {
        this.setCharacterStream(this.getNamedParamIndex(parameterName, false), reader, length);
    }
    
    public void setClob(final String parameterName, final Clob x) throws SQLException {
        this.setClob(this.getNamedParamIndex(parameterName, false), x);
    }
    
    public void setClob(final String parameterName, final Reader reader) throws SQLException {
        this.setClob(this.getNamedParamIndex(parameterName, false), reader);
    }
    
    public void setClob(final String parameterName, final Reader reader, final long length) throws SQLException {
        this.setClob(this.getNamedParamIndex(parameterName, false), reader, length);
    }
    
    public void setNCharacterStream(final String parameterName, final Reader value) throws SQLException {
        this.setNCharacterStream(this.getNamedParamIndex(parameterName, false), value);
    }
    
    public void setNCharacterStream(final String parameterName, final Reader value, final long length) throws SQLException {
        this.setNCharacterStream(this.getNamedParamIndex(parameterName, false), value, length);
    }
    
    private boolean checkReadOnlyProcedure() throws SQLException {
        if (this.connection.getNoAccessToProcedureBodies()) {
            return false;
        }
        synchronized (this.paramInfo) {
            if (this.paramInfo.isReadOnlySafeChecked) {
                return this.paramInfo.isReadOnlySafeProcedure;
            }
            ResultSet rs = null;
            java.sql.PreparedStatement ps = null;
            try {
                String procName = this.extractProcedureName();
                String catalog = this.currentCatalog;
                if (procName.indexOf(".") != -1) {
                    catalog = procName.substring(0, procName.indexOf("."));
                    if (StringUtils.startsWithIgnoreCaseAndWs(catalog, "`") && catalog.trim().endsWith("`")) {
                        catalog = catalog.substring(1, catalog.length() - 1);
                    }
                    procName = procName.substring(procName.indexOf(".") + 1);
                    procName = new String(StringUtils.stripEnclosure(procName.getBytes(), "`", "`"));
                }
                ps = this.connection.prepareStatement("SELECT SQL_DATA_ACCESS FROM  information_schema.routines  WHERE routine_schema = ?  AND routine_name = ?");
                ps.setMaxRows(0);
                ps.setFetchSize(0);
                ps.setString(1, catalog);
                ps.setString(2, procName);
                rs = ps.executeQuery();
                if (rs.next()) {
                    final String sqlDataAccess = rs.getString(1);
                    if ("READS SQL DATA".equalsIgnoreCase(sqlDataAccess) || "NO SQL".equalsIgnoreCase(sqlDataAccess)) {
                        synchronized (this.paramInfo) {
                            this.paramInfo.isReadOnlySafeChecked = true;
                            this.paramInfo.isReadOnlySafeProcedure = true;
                        }
                        return true;
                    }
                }
            }
            catch (SQLException e) {}
            finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            }
            this.paramInfo.isReadOnlySafeChecked = false;
            this.paramInfo.isReadOnlySafeProcedure = false;
        }
        return false;
    }
    
    protected boolean checkReadOnlySafeStatement() throws SQLException {
        return super.checkReadOnlySafeStatement() || this.checkReadOnlyProcedure();
    }
    
    private boolean hasParametersView() throws SQLException {
        try {
            if (this.connection.versionMeetsMinimum(5, 5, 0)) {
                final java.sql.DatabaseMetaData dbmd1 = new DatabaseMetaDataUsingInfoSchema(this.connection, this.connection.getCatalog());
                return ((DatabaseMetaDataUsingInfoSchema)dbmd1).gethasParametersView();
            }
            return false;
        }
        catch (SQLException e) {
            return false;
        }
    }
    
    static {
        if (Util.isJdbc4()) {
            try {
                JDBC_4_CSTMT_2_ARGS_CTOR = Class.forName("com.mysql.jdbc.JDBC4CallableStatement").getConstructor(MySQLConnection.class, CallableStatementParamInfo.class);
                JDBC_4_CSTMT_4_ARGS_CTOR = Class.forName("com.mysql.jdbc.JDBC4CallableStatement").getConstructor(MySQLConnection.class, String.class, String.class, Boolean.TYPE);
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
        JDBC_4_CSTMT_4_ARGS_CTOR = null;
        JDBC_4_CSTMT_2_ARGS_CTOR = null;
    }
    
    protected class CallableStatementParam
    {
        int desiredJdbcType;
        int index;
        int inOutModifier;
        boolean isIn;
        boolean isOut;
        int jdbcType;
        short nullability;
        String paramName;
        int precision;
        int scale;
        String typeName;
        
        CallableStatementParam(final String name, final int idx, final boolean in, final boolean out, final int jdbcType, final String typeName, final int precision, final int scale, final short nullability, final int inOutModifier) {
            this.paramName = name;
            this.isIn = in;
            this.isOut = out;
            this.index = idx;
            this.jdbcType = jdbcType;
            this.typeName = typeName;
            this.precision = precision;
            this.scale = scale;
            this.nullability = nullability;
            this.inOutModifier = inOutModifier;
        }
        
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }
    
    protected class CallableStatementParamInfo
    {
        String catalogInUse;
        boolean isFunctionCall;
        String nativeSql;
        int numParameters;
        List parameterList;
        Map parameterMap;
        boolean isReadOnlySafeProcedure;
        boolean isReadOnlySafeChecked;
        
        CallableStatementParamInfo(final CallableStatementParamInfo fullParamInfo) {
            this.isReadOnlySafeProcedure = false;
            this.isReadOnlySafeChecked = false;
            this.nativeSql = CallableStatement.this.originalSql;
            this.catalogInUse = CallableStatement.this.currentCatalog;
            this.isFunctionCall = fullParamInfo.isFunctionCall;
            final int[] localParameterMap = CallableStatement.this.placeholderToParameterIndexMap;
            final int parameterMapLength = localParameterMap.length;
            this.isReadOnlySafeProcedure = fullParamInfo.isReadOnlySafeProcedure;
            this.isReadOnlySafeChecked = fullParamInfo.isReadOnlySafeChecked;
            this.parameterList = new ArrayList(fullParamInfo.numParameters);
            this.parameterMap = new HashMap(fullParamInfo.numParameters);
            if (this.isFunctionCall) {
                this.parameterList.add(fullParamInfo.parameterList.get(0));
            }
            final int offset = this.isFunctionCall ? 1 : 0;
            for (int i = 0; i < parameterMapLength; ++i) {
                if (localParameterMap[i] != 0) {
                    final CallableStatementParam param = fullParamInfo.parameterList.get(localParameterMap[i] + offset);
                    this.parameterList.add(param);
                    this.parameterMap.put(param.paramName, param);
                }
            }
            this.numParameters = this.parameterList.size();
        }
        
        CallableStatementParamInfo(final ResultSet paramTypesRs) throws SQLException {
            this.isReadOnlySafeProcedure = false;
            this.isReadOnlySafeChecked = false;
            final boolean hadRows = paramTypesRs.last();
            this.nativeSql = CallableStatement.this.originalSql;
            this.catalogInUse = CallableStatement.this.currentCatalog;
            this.isFunctionCall = CallableStatement.this.callingStoredFunction;
            if (hadRows) {
                this.numParameters = paramTypesRs.getRow();
                this.parameterList = new ArrayList(this.numParameters);
                this.parameterMap = new HashMap(this.numParameters);
                paramTypesRs.beforeFirst();
                this.addParametersFromDBMD(paramTypesRs);
            }
            else {
                this.numParameters = 0;
            }
            if (this.isFunctionCall) {
                ++this.numParameters;
            }
        }
        
        private void addParametersFromDBMD(final ResultSet paramTypesRs) throws SQLException {
            int i = 0;
            while (paramTypesRs.next()) {
                final String paramName = paramTypesRs.getString(4);
                final int inOutModifier = paramTypesRs.getInt(5);
                boolean isOutParameter = false;
                boolean isInParameter = false;
                if (i == 0 && this.isFunctionCall) {
                    isOutParameter = true;
                    isInParameter = false;
                }
                else if (inOutModifier == 2) {
                    isOutParameter = true;
                    isInParameter = true;
                }
                else if (inOutModifier == 1) {
                    isOutParameter = false;
                    isInParameter = true;
                }
                else if (inOutModifier == 4) {
                    isOutParameter = true;
                    isInParameter = false;
                }
                final int jdbcType = paramTypesRs.getInt(6);
                final String typeName = paramTypesRs.getString(7);
                final int precision = paramTypesRs.getInt(8);
                final int scale = paramTypesRs.getInt(10);
                final short nullability = paramTypesRs.getShort(12);
                final CallableStatementParam paramInfoToAdd = new CallableStatementParam(paramName, i++, isInParameter, isOutParameter, jdbcType, typeName, precision, scale, nullability, inOutModifier);
                this.parameterList.add(paramInfoToAdd);
                this.parameterMap.put(paramName, paramInfoToAdd);
            }
        }
        
        protected void checkBounds(final int paramIndex) throws SQLException {
            final int localParamIndex = paramIndex - 1;
            if (paramIndex < 0 || localParamIndex >= this.numParameters) {
                throw SQLError.createSQLException(Messages.getString("CallableStatement.11") + paramIndex + Messages.getString("CallableStatement.12") + this.numParameters + Messages.getString("CallableStatement.13"), "S1009", CallableStatement.this.getExceptionInterceptor());
            }
        }
        
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
        
        CallableStatementParam getParameter(final int index) {
            return this.parameterList.get(index);
        }
        
        CallableStatementParam getParameter(final String name) {
            return this.parameterMap.get(name);
        }
        
        public String getParameterClassName(final int arg0) throws SQLException {
            final String mysqlTypeName = this.getParameterTypeName(arg0);
            final boolean isBinaryOrBlob = StringUtils.indexOfIgnoreCase(mysqlTypeName, "BLOB") != -1 || StringUtils.indexOfIgnoreCase(mysqlTypeName, "BINARY") != -1;
            final boolean isUnsigned = StringUtils.indexOfIgnoreCase(mysqlTypeName, "UNSIGNED") != -1;
            int mysqlTypeIfKnown = 0;
            if (StringUtils.startsWithIgnoreCase(mysqlTypeName, "MEDIUMINT")) {
                mysqlTypeIfKnown = 9;
            }
            return ResultSetMetaData.getClassNameForJavaType(this.getParameterType(arg0), isUnsigned, mysqlTypeIfKnown, isBinaryOrBlob, false);
        }
        
        public int getParameterCount() throws SQLException {
            if (this.parameterList == null) {
                return 0;
            }
            return this.parameterList.size();
        }
        
        public int getParameterMode(final int arg0) throws SQLException {
            this.checkBounds(arg0);
            return this.getParameter(arg0 - 1).inOutModifier;
        }
        
        public int getParameterType(final int arg0) throws SQLException {
            this.checkBounds(arg0);
            return this.getParameter(arg0 - 1).jdbcType;
        }
        
        public String getParameterTypeName(final int arg0) throws SQLException {
            this.checkBounds(arg0);
            return this.getParameter(arg0 - 1).typeName;
        }
        
        public int getPrecision(final int arg0) throws SQLException {
            this.checkBounds(arg0);
            return this.getParameter(arg0 - 1).precision;
        }
        
        public int getScale(final int arg0) throws SQLException {
            this.checkBounds(arg0);
            return this.getParameter(arg0 - 1).scale;
        }
        
        public int isNullable(final int arg0) throws SQLException {
            this.checkBounds(arg0);
            return this.getParameter(arg0 - 1).nullability;
        }
        
        public boolean isSigned(final int arg0) throws SQLException {
            this.checkBounds(arg0);
            return false;
        }
        
        Iterator iterator() {
            return this.parameterList.iterator();
        }
        
        int numberOfParameters() {
            return this.numParameters;
        }
    }
    
    protected class CallableStatementParamInfoJDBC3 extends CallableStatementParamInfo implements ParameterMetaData
    {
        CallableStatementParamInfoJDBC3(final ResultSet paramTypesRs) throws SQLException {
            super(paramTypesRs);
        }
        
        public CallableStatementParamInfoJDBC3(final CallableStatementParamInfo paramInfo) {
            super(paramInfo);
        }
        
        public boolean isWrapperFor(final Class iface) throws SQLException {
            CallableStatement.this.checkClosed();
            return iface.isInstance(this);
        }
        
        public Object unwrap(final Class iface) throws SQLException {
            try {
                return Util.cast(iface, this);
            }
            catch (ClassCastException cce) {
                throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", CallableStatement.this.getExceptionInterceptor());
            }
        }
    }
}
