// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.TreeMap;
import java.sql.Connection;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Locale;
import java.sql.ResultSetMetaData;
import java.io.UnsupportedEncodingException;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.List;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;
import java.lang.reflect.Constructor;

public class DatabaseMetaData implements java.sql.DatabaseMetaData
{
    private static String mysqlKeywordsThatArentSQL92;
    protected static final int MAX_IDENTIFIER_LENGTH = 64;
    private static final int DEFERRABILITY = 13;
    private static final int DELETE_RULE = 10;
    private static final int FK_NAME = 11;
    private static final int FKCOLUMN_NAME = 7;
    private static final int FKTABLE_CAT = 4;
    private static final int FKTABLE_NAME = 6;
    private static final int FKTABLE_SCHEM = 5;
    private static final int KEY_SEQ = 8;
    private static final int PK_NAME = 12;
    private static final int PKCOLUMN_NAME = 3;
    private static final int PKTABLE_CAT = 0;
    private static final int PKTABLE_NAME = 2;
    private static final int PKTABLE_SCHEM = 1;
    private static final String SUPPORTS_FK = "SUPPORTS_FK";
    private static final byte[] TABLE_AS_BYTES;
    private static final byte[] SYSTEM_TABLE_AS_BYTES;
    private static final int UPDATE_RULE = 9;
    private static final byte[] VIEW_AS_BYTES;
    private static final Constructor JDBC_4_DBMD_SHOW_CTOR;
    private static final Constructor JDBC_4_DBMD_IS_CTOR;
    protected MySQLConnection conn;
    protected String database;
    protected String quotedId;
    private ExceptionInterceptor exceptionInterceptor;
    
    protected static DatabaseMetaData getInstance(final MySQLConnection connToSet, final String databaseToSet, final boolean checkForInfoSchema) throws SQLException {
        if (!Util.isJdbc4()) {
            if (checkForInfoSchema && connToSet != null && connToSet.getUseInformationSchema() && connToSet.versionMeetsMinimum(5, 0, 7)) {
                return new DatabaseMetaDataUsingInfoSchema(connToSet, databaseToSet);
            }
            return new DatabaseMetaData(connToSet, databaseToSet);
        }
        else {
            if (checkForInfoSchema && connToSet != null && connToSet.getUseInformationSchema() && connToSet.versionMeetsMinimum(5, 0, 7)) {
                return (DatabaseMetaData)Util.handleNewInstance(DatabaseMetaData.JDBC_4_DBMD_IS_CTOR, new Object[] { connToSet, databaseToSet }, connToSet.getExceptionInterceptor());
            }
            return (DatabaseMetaData)Util.handleNewInstance(DatabaseMetaData.JDBC_4_DBMD_SHOW_CTOR, new Object[] { connToSet, databaseToSet }, connToSet.getExceptionInterceptor());
        }
    }
    
    protected DatabaseMetaData(final MySQLConnection connToSet, final String databaseToSet) {
        this.database = null;
        this.quotedId = null;
        this.conn = connToSet;
        this.database = databaseToSet;
        this.exceptionInterceptor = this.conn.getExceptionInterceptor();
        try {
            this.quotedId = (this.conn.supportsQuotedIdentifiers() ? this.getIdentifierQuoteString() : "");
        }
        catch (SQLException sqlEx) {
            AssertionFailedException.shouldNotHappen(sqlEx);
        }
    }
    
    public boolean allProceduresAreCallable() throws SQLException {
        return false;
    }
    
    public boolean allTablesAreSelectable() throws SQLException {
        return false;
    }
    
    private ResultSet buildResultSet(final Field[] fields, final ArrayList rows) throws SQLException {
        return buildResultSet(fields, rows, this.conn);
    }
    
    static ResultSet buildResultSet(final Field[] fields, final ArrayList rows, final MySQLConnection c) throws SQLException {
        for (int fieldsLength = fields.length, i = 0; i < fieldsLength; ++i) {
            final int jdbcType = fields[i].getSQLType();
            switch (jdbcType) {
                case -1:
                case 1:
                case 12: {
                    fields[i].setCharacterSet(c.getCharacterSetMetadata());
                    break;
                }
            }
            fields[i].setConnection(c);
            fields[i].setUseOldNameMetadata(true);
        }
        return ResultSetImpl.getInstance(c.getCatalog(), fields, new RowDataStatic(rows), c, null, false);
    }
    
    private void convertToJdbcFunctionList(final String catalog, final ResultSet proceduresRs, final boolean needsClientFiltering, final String db, final Map procedureRowsOrderedByName, final int nameIndex, final Field[] fields) throws SQLException {
        while (proceduresRs.next()) {
            boolean shouldAdd = true;
            if (needsClientFiltering) {
                shouldAdd = false;
                final String procDb = proceduresRs.getString(1);
                if (db == null && procDb == null) {
                    shouldAdd = true;
                }
                else if (db != null && db.equals(procDb)) {
                    shouldAdd = true;
                }
            }
            if (shouldAdd) {
                final String functionName = proceduresRs.getString(nameIndex);
                byte[][] rowData = null;
                if (fields != null && fields.length == 9) {
                    rowData = new byte[][] { (catalog == null) ? null : this.s2b(catalog), null, this.s2b(functionName), null, null, null, this.s2b(proceduresRs.getString("comment")), this.s2b(Integer.toString(2)), this.s2b(functionName) };
                }
                else {
                    rowData = new byte[][] { (catalog == null) ? null : this.s2b(catalog), null, this.s2b(functionName), this.s2b(proceduresRs.getString("comment")), this.s2b(Integer.toString(this.getJDBC4FunctionNoTableConstant())), this.s2b(functionName) };
                }
                procedureRowsOrderedByName.put(functionName, new ByteArrayRow(rowData, this.getExceptionInterceptor()));
            }
        }
    }
    
    protected int getJDBC4FunctionNoTableConstant() {
        return 0;
    }
    
    private void convertToJdbcProcedureList(final boolean fromSelect, final String catalog, final ResultSet proceduresRs, final boolean needsClientFiltering, final String db, final Map procedureRowsOrderedByName, final int nameIndex) throws SQLException {
        while (proceduresRs.next()) {
            boolean shouldAdd = true;
            if (needsClientFiltering) {
                shouldAdd = false;
                final String procDb = proceduresRs.getString(1);
                if (db == null && procDb == null) {
                    shouldAdd = true;
                }
                else if (db != null && db.equals(procDb)) {
                    shouldAdd = true;
                }
            }
            if (shouldAdd) {
                final String procedureName = proceduresRs.getString(nameIndex);
                final byte[][] rowData = { (catalog == null) ? null : this.s2b(catalog), null, this.s2b(procedureName), null, null, null, null, null, null };
                final boolean isFunction = fromSelect && "FUNCTION".equalsIgnoreCase(proceduresRs.getString("type"));
                rowData[7] = this.s2b(isFunction ? Integer.toString(2) : Integer.toString(0));
                rowData[8] = this.s2b(procedureName);
                procedureRowsOrderedByName.put(procedureName, new ByteArrayRow(rowData, this.getExceptionInterceptor()));
            }
        }
    }
    
    private ResultSetRow convertTypeDescriptorToProcedureRow(final byte[] procNameAsBytes, final byte[] procCatAsBytes, final String paramName, final boolean isOutParam, final boolean isInParam, final boolean isReturnParam, final TypeDescriptor typeDesc, final boolean forGetFunctionColumns, final int ordinal) throws SQLException {
        final byte[][] row = forGetFunctionColumns ? new byte[17][] : new byte[14][];
        row[0] = procCatAsBytes;
        row[1] = null;
        row[2] = procNameAsBytes;
        row[3] = this.s2b(paramName);
        if (isInParam && isOutParam) {
            row[4] = this.s2b(String.valueOf(2));
        }
        else if (isInParam) {
            row[4] = this.s2b(String.valueOf(1));
        }
        else if (isOutParam) {
            row[4] = this.s2b(String.valueOf(4));
        }
        else if (isReturnParam) {
            row[4] = this.s2b(String.valueOf(5));
        }
        else {
            row[4] = this.s2b(String.valueOf(0));
        }
        row[5] = this.s2b(Short.toString(typeDesc.dataType));
        row[6] = this.s2b(typeDesc.typeName);
        row[8] = (row[7] = (byte[])((typeDesc.columnSize == null) ? null : this.s2b(typeDesc.columnSize.toString())));
        row[9] = (byte[])((typeDesc.decimalDigits == null) ? null : this.s2b(typeDesc.decimalDigits.toString()));
        row[10] = this.s2b(Integer.toString(typeDesc.numPrecRadix));
        switch (typeDesc.nullability) {
            case 0: {
                row[11] = this.s2b(String.valueOf(0));
                break;
            }
            case 1: {
                row[11] = this.s2b(String.valueOf(1));
                break;
            }
            case 2: {
                row[11] = this.s2b(String.valueOf(2));
                break;
            }
            default: {
                throw SQLError.createSQLException("Internal error while parsing callable statement metadata (unknown nullability value fount)", "S1000", this.getExceptionInterceptor());
            }
        }
        row[12] = null;
        if (forGetFunctionColumns) {
            row[13] = null;
            row[14] = this.s2b(String.valueOf(ordinal));
            row[15] = Constants.EMPTY_BYTE_ARRAY;
            row[16] = this.s2b(paramName);
        }
        return new ByteArrayRow(row, this.getExceptionInterceptor());
    }
    
    protected ExceptionInterceptor getExceptionInterceptor() {
        return this.exceptionInterceptor;
    }
    
    public boolean dataDefinitionCausesTransactionCommit() throws SQLException {
        return true;
    }
    
    public boolean dataDefinitionIgnoredInTransactions() throws SQLException {
        return false;
    }
    
    public boolean deletesAreDetected(final int type) throws SQLException {
        return false;
    }
    
    public boolean doesMaxRowSizeIncludeBlobs() throws SQLException {
        return true;
    }
    
    public List extractForeignKeyForTable(final ArrayList rows, final ResultSet rs, final String catalog) throws SQLException {
        final byte[][] row = { rs.getBytes(1), this.s2b("SUPPORTS_FK"), null };
        final String createTableString = rs.getString(2);
        final StringTokenizer lineTokenizer = new StringTokenizer(createTableString, "\n");
        final StringBuffer commentBuf = new StringBuffer("comment; ");
        boolean firstTime = true;
        String quoteChar = this.getIdentifierQuoteString();
        if (quoteChar == null) {
            quoteChar = "`";
        }
        while (lineTokenizer.hasMoreTokens()) {
            String line = lineTokenizer.nextToken().trim();
            String constraintName = null;
            if (StringUtils.startsWithIgnoreCase(line, "CONSTRAINT")) {
                boolean usingBackTicks = true;
                int beginPos = line.indexOf(quoteChar);
                if (beginPos == -1) {
                    beginPos = line.indexOf("\"");
                    usingBackTicks = false;
                }
                if (beginPos != -1) {
                    int endPos = -1;
                    if (usingBackTicks) {
                        endPos = line.indexOf(quoteChar, beginPos + 1);
                    }
                    else {
                        endPos = line.indexOf("\"", beginPos + 1);
                    }
                    if (endPos != -1) {
                        constraintName = line.substring(beginPos + 1, endPos);
                        line = line.substring(endPos + 1, line.length()).trim();
                    }
                }
            }
            if (line.startsWith("FOREIGN KEY")) {
                if (line.endsWith(",")) {
                    line = line.substring(0, line.length() - 1);
                }
                final char quote = this.quotedId.charAt(0);
                final int indexOfFK = line.indexOf("FOREIGN KEY");
                String localColumnName = null;
                String referencedCatalogName = this.quotedId + catalog + this.quotedId;
                String referencedTableName = null;
                String referencedColumnName = null;
                if (indexOfFK != -1) {
                    final int afterFk = indexOfFK + "FOREIGN KEY".length();
                    final int indexOfRef = StringUtils.indexOfIgnoreCaseRespectQuotes(afterFk, line, "REFERENCES", quote, true);
                    if (indexOfRef != -1) {
                        final int indexOfParenOpen = line.indexOf(40, afterFk);
                        final int indexOfParenClose = StringUtils.indexOfIgnoreCaseRespectQuotes(indexOfParenOpen, line, ")", quote, true);
                        if (indexOfParenOpen == -1 || indexOfParenClose == -1) {}
                        localColumnName = line.substring(indexOfParenOpen + 1, indexOfParenClose);
                        final int afterRef = indexOfRef + "REFERENCES".length();
                        final int referencedColumnBegin = StringUtils.indexOfIgnoreCaseRespectQuotes(afterRef, line, "(", quote, true);
                        if (referencedColumnBegin != -1) {
                            referencedTableName = line.substring(afterRef, referencedColumnBegin);
                            final int referencedColumnEnd = StringUtils.indexOfIgnoreCaseRespectQuotes(referencedColumnBegin + 1, line, ")", quote, true);
                            if (referencedColumnEnd != -1) {
                                referencedColumnName = line.substring(referencedColumnBegin + 1, referencedColumnEnd);
                            }
                            final int indexOfCatalogSep = StringUtils.indexOfIgnoreCaseRespectQuotes(0, referencedTableName, ".", quote, true);
                            if (indexOfCatalogSep != -1) {
                                referencedCatalogName = referencedTableName.substring(0, indexOfCatalogSep);
                                referencedTableName = referencedTableName.substring(indexOfCatalogSep + 1);
                            }
                        }
                    }
                }
                if (!firstTime) {
                    commentBuf.append("; ");
                }
                else {
                    firstTime = false;
                }
                if (constraintName != null) {
                    commentBuf.append(constraintName);
                }
                else {
                    commentBuf.append("not_available");
                }
                commentBuf.append("(");
                commentBuf.append(localColumnName);
                commentBuf.append(") REFER ");
                commentBuf.append(referencedCatalogName);
                commentBuf.append("/");
                commentBuf.append(referencedTableName);
                commentBuf.append("(");
                commentBuf.append(referencedColumnName);
                commentBuf.append(")");
                final int lastParenIndex = line.lastIndexOf(")");
                if (lastParenIndex == line.length() - 1) {
                    continue;
                }
                final String cascadeOptions = line.substring(lastParenIndex + 1);
                commentBuf.append(" ");
                commentBuf.append(cascadeOptions);
            }
        }
        row[2] = this.s2b(commentBuf.toString());
        rows.add(new ByteArrayRow(row, this.getExceptionInterceptor()));
        return rows;
    }
    
    public ResultSet extractForeignKeyFromCreateTable(final String catalog, final String tableName) throws SQLException {
        final ArrayList tableList = new ArrayList();
        ResultSet rs = null;
        Statement stmt = null;
        if (tableName != null) {
            tableList.add(tableName);
        }
        else {
            try {
                rs = this.getTables(catalog, "", "%", new String[] { "TABLE" });
                while (rs.next()) {
                    tableList.add(rs.getString("TABLE_NAME"));
                }
            }
            finally {
                if (rs != null) {
                    rs.close();
                }
                rs = null;
            }
        }
        final ArrayList rows = new ArrayList();
        final Field[] fields = { new Field("", "Name", 1, Integer.MAX_VALUE), new Field("", "Type", 1, 255), new Field("", "Comment", 1, Integer.MAX_VALUE) };
        final int numTables = tableList.size();
        stmt = this.conn.getMetadataSafeStatement();
        String quoteChar = this.getIdentifierQuoteString();
        if (quoteChar == null) {
            quoteChar = "`";
        }
        try {
            for (int i = 0; i < numTables; ++i) {
                String tableToExtract = tableList.get(i);
                if (tableToExtract.indexOf(quoteChar) > 0) {
                    tableToExtract = StringUtils.escapeQuote(tableToExtract, quoteChar);
                }
                final String query = new StringBuffer("SHOW CREATE TABLE ").append(quoteChar).append(catalog).append(quoteChar).append(".").append(quoteChar).append(tableToExtract).append(quoteChar).toString();
                try {
                    rs = stmt.executeQuery(query);
                }
                catch (SQLException sqlEx) {
                    final String sqlState = sqlEx.getSQLState();
                    if (!"42S02".equals(sqlState) && sqlEx.getErrorCode() != 1146) {
                        throw sqlEx;
                    }
                    continue;
                }
                while (rs.next()) {
                    this.extractForeignKeyForTable(rows, rs, catalog);
                }
            }
        }
        finally {
            if (rs != null) {
                rs.close();
            }
            rs = null;
            if (stmt != null) {
                stmt.close();
            }
            stmt = null;
        }
        return this.buildResultSet(fields, rows);
    }
    
    public ResultSet getAttributes(final String arg0, final String arg1, final String arg2, final String arg3) throws SQLException {
        final Field[] fields = { new Field("", "TYPE_CAT", 1, 32), new Field("", "TYPE_SCHEM", 1, 32), new Field("", "TYPE_NAME", 1, 32), new Field("", "ATTR_NAME", 1, 32), new Field("", "DATA_TYPE", 5, 32), new Field("", "ATTR_TYPE_NAME", 1, 32), new Field("", "ATTR_SIZE", 4, 32), new Field("", "DECIMAL_DIGITS", 4, 32), new Field("", "NUM_PREC_RADIX", 4, 32), new Field("", "NULLABLE ", 4, 32), new Field("", "REMARKS", 1, 32), new Field("", "ATTR_DEF", 1, 32), new Field("", "SQL_DATA_TYPE", 4, 32), new Field("", "SQL_DATETIME_SUB", 4, 32), new Field("", "CHAR_OCTET_LENGTH", 4, 32), new Field("", "ORDINAL_POSITION", 4, 32), new Field("", "IS_NULLABLE", 1, 32), new Field("", "SCOPE_CATALOG", 1, 32), new Field("", "SCOPE_SCHEMA", 1, 32), new Field("", "SCOPE_TABLE", 1, 32), new Field("", "SOURCE_DATA_TYPE", 5, 32) };
        return this.buildResultSet(fields, new ArrayList());
    }
    
    public ResultSet getBestRowIdentifier(final String catalog, final String schema, final String table, final int scope, final boolean nullable) throws SQLException {
        if (table == null) {
            throw SQLError.createSQLException("Table not specified.", "S1009", this.getExceptionInterceptor());
        }
        final Field[] fields = { new Field("", "SCOPE", 5, 5), new Field("", "COLUMN_NAME", 1, 32), new Field("", "DATA_TYPE", 4, 32), new Field("", "TYPE_NAME", 1, 32), new Field("", "COLUMN_SIZE", 4, 10), new Field("", "BUFFER_LENGTH", 4, 10), new Field("", "DECIMAL_DIGITS", 5, 10), new Field("", "PSEUDO_COLUMN", 5, 5) };
        final ArrayList rows = new ArrayList();
        final Statement stmt = this.conn.getMetadataSafeStatement();
        try {
            new IterateBlock(this.getCatalogIterator(catalog)) {
                void forEach(final Object catalogStr) throws SQLException {
                    ResultSet results = null;
                    try {
                        final StringBuffer queryBuf = new StringBuffer("SHOW COLUMNS FROM ");
                        queryBuf.append(DatabaseMetaData.this.quotedId);
                        queryBuf.append(table);
                        queryBuf.append(DatabaseMetaData.this.quotedId);
                        queryBuf.append(" FROM ");
                        queryBuf.append(DatabaseMetaData.this.quotedId);
                        queryBuf.append(catalogStr.toString());
                        queryBuf.append(DatabaseMetaData.this.quotedId);
                        results = stmt.executeQuery(queryBuf.toString());
                        while (results.next()) {
                            final String keyType = results.getString("Key");
                            if (keyType != null && StringUtils.startsWithIgnoreCase(keyType, "PRI")) {
                                final byte[][] rowVal = new byte[8][];
                                rowVal[0] = Integer.toString(2).getBytes();
                                rowVal[1] = results.getBytes("Field");
                                String type = results.getString("Type");
                                int size = MysqlIO.getMaxBuf();
                                int decimals = 0;
                                if (type.indexOf("enum") != -1) {
                                    final String temp = type.substring(type.indexOf("("), type.indexOf(")"));
                                    final StringTokenizer tokenizer = new StringTokenizer(temp, ",");
                                    int maxLength = 0;
                                    while (tokenizer.hasMoreTokens()) {
                                        maxLength = Math.max(maxLength, tokenizer.nextToken().length() - 2);
                                    }
                                    size = maxLength;
                                    decimals = 0;
                                    type = "enum";
                                }
                                else if (type.indexOf("(") != -1) {
                                    if (type.indexOf(",") != -1) {
                                        size = Integer.parseInt(type.substring(type.indexOf("(") + 1, type.indexOf(",")));
                                        decimals = Integer.parseInt(type.substring(type.indexOf(",") + 1, type.indexOf(")")));
                                    }
                                    else {
                                        size = Integer.parseInt(type.substring(type.indexOf("(") + 1, type.indexOf(")")));
                                    }
                                    type = type.substring(0, type.indexOf("("));
                                }
                                rowVal[2] = DatabaseMetaData.this.s2b(String.valueOf(MysqlDefs.mysqlToJavaType(type)));
                                rowVal[3] = DatabaseMetaData.this.s2b(type);
                                rowVal[4] = Integer.toString(size + decimals).getBytes();
                                rowVal[5] = Integer.toString(size + decimals).getBytes();
                                rowVal[6] = Integer.toString(decimals).getBytes();
                                rowVal[7] = Integer.toString(1).getBytes();
                                rows.add(new ByteArrayRow(rowVal, DatabaseMetaData.this.getExceptionInterceptor()));
                            }
                        }
                    }
                    catch (SQLException sqlEx) {
                        if (!"42S02".equals(sqlEx.getSQLState())) {
                            throw sqlEx;
                        }
                    }
                    finally {
                        if (results != null) {
                            try {
                                results.close();
                            }
                            catch (Exception ex) {}
                            results = null;
                        }
                    }
                }
            }.doForAll();
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        final ResultSet results = this.buildResultSet(fields, rows);
        return results;
    }
    
    private void getCallStmtParameterTypes(final String catalog, final String procName, final String parameterNamePattern, final List resultRows) throws SQLException {
        this.getCallStmtParameterTypes(catalog, procName, parameterNamePattern, resultRows, false);
    }
    
    private void getCallStmtParameterTypes(String catalog, String procName, String parameterNamePattern, final List resultRows, final boolean forGetFunctionColumns) throws SQLException {
        Statement paramRetrievalStmt = null;
        ResultSet paramRetrievalRs = null;
        if (parameterNamePattern == null) {
            if (!this.conn.getNullNamePatternMatchesAll()) {
                throw SQLError.createSQLException("Parameter/Column name pattern can not be NULL or empty.", "S1009", this.getExceptionInterceptor());
            }
            parameterNamePattern = "%";
        }
        final String quoteChar = this.getIdentifierQuoteString();
        String parameterDef = null;
        byte[] procNameAsBytes = null;
        byte[] procCatAsBytes = null;
        boolean isProcedureInAnsiMode = false;
        String storageDefnDelims = null;
        String storageDefnClosures = null;
        try {
            paramRetrievalStmt = this.conn.getMetadataSafeStatement();
            if (this.conn.lowerCaseTableNames() && catalog != null && catalog.length() != 0) {
                final String oldCatalog = this.conn.getCatalog();
                ResultSet rs = null;
                try {
                    this.conn.setCatalog(catalog.replaceAll(quoteChar, ""));
                    rs = paramRetrievalStmt.executeQuery("SELECT DATABASE()");
                    rs.next();
                    catalog = rs.getString(1);
                }
                finally {
                    this.conn.setCatalog(oldCatalog);
                    if (rs != null) {
                        rs.close();
                    }
                }
            }
            if (paramRetrievalStmt.getMaxRows() != 0) {
                paramRetrievalStmt.setMaxRows(0);
            }
            int dotIndex = -1;
            if (!" ".equals(quoteChar)) {
                dotIndex = StringUtils.indexOfIgnoreCaseRespectQuotes(0, procName, ".", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
            }
            else {
                dotIndex = procName.indexOf(".");
            }
            String dbName = null;
            if (dotIndex != -1 && dotIndex + 1 < procName.length()) {
                dbName = procName.substring(0, dotIndex);
                procName = procName.substring(dotIndex + 1);
            }
            else {
                dbName = catalog;
            }
            String tmpProcName = procName;
            tmpProcName = tmpProcName.replaceAll(quoteChar, "");
            try {
                procNameAsBytes = tmpProcName.getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException ueEx) {
                procNameAsBytes = this.s2b(tmpProcName);
            }
            tmpProcName = dbName;
            tmpProcName = tmpProcName.replaceAll(quoteChar, "");
            try {
                procCatAsBytes = tmpProcName.getBytes("UTF-8");
            }
            catch (UnsupportedEncodingException ueEx) {
                procCatAsBytes = this.s2b(tmpProcName);
            }
            final StringBuffer procNameBuf = new StringBuffer();
            if (dbName != null) {
                if (!" ".equals(quoteChar) && !dbName.startsWith(quoteChar)) {
                    procNameBuf.append(quoteChar);
                }
                procNameBuf.append(dbName);
                if (!" ".equals(quoteChar) && !dbName.startsWith(quoteChar)) {
                    procNameBuf.append(quoteChar);
                }
                procNameBuf.append(".");
            }
            final boolean procNameIsNotQuoted = !procName.startsWith(quoteChar);
            if (!" ".equals(quoteChar) && procNameIsNotQuoted) {
                procNameBuf.append(quoteChar);
            }
            procNameBuf.append(procName);
            if (!" ".equals(quoteChar) && procNameIsNotQuoted) {
                procNameBuf.append(quoteChar);
            }
            boolean parsingFunction = false;
            try {
                paramRetrievalRs = paramRetrievalStmt.executeQuery("SHOW CREATE PROCEDURE " + procNameBuf.toString());
                parsingFunction = false;
            }
            catch (SQLException sqlEx2) {
                paramRetrievalRs = paramRetrievalStmt.executeQuery("SHOW CREATE FUNCTION " + procNameBuf.toString());
                parsingFunction = true;
            }
            if (paramRetrievalRs.next()) {
                String procedureDef = parsingFunction ? paramRetrievalRs.getString("Create Function") : paramRetrievalRs.getString("Create Procedure");
                if (procedureDef == null || procedureDef.length() == 0) {
                    throw SQLError.createSQLException("User does not have access to metadata required to determine stored procedure parameter types. If rights can not be granted, configure connection with \"noAccessToProcedureBodies=true\" to have driver generate parameters that represent INOUT strings irregardless of actual parameter types.", "S1000", this.getExceptionInterceptor());
                }
                try {
                    final String sqlMode = paramRetrievalRs.getString("sql_mode");
                    if (StringUtils.indexOfIgnoreCase(sqlMode, "ANSI") != -1) {
                        isProcedureInAnsiMode = true;
                    }
                }
                catch (SQLException ex) {}
                final String identifierMarkers = isProcedureInAnsiMode ? "`\"" : "`";
                final String identifierAndStringMarkers = "'" + identifierMarkers;
                storageDefnDelims = "(" + identifierMarkers;
                storageDefnClosures = ")" + identifierMarkers;
                procedureDef = StringUtils.stripComments(procedureDef, identifierAndStringMarkers, identifierAndStringMarkers, true, false, true, true);
                final int openParenIndex = StringUtils.indexOfIgnoreCaseRespectQuotes(0, procedureDef, "(", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
                int endOfParamDeclarationIndex = 0;
                endOfParamDeclarationIndex = this.endPositionOfParameterDeclaration(openParenIndex, procedureDef, quoteChar);
                if (parsingFunction) {
                    final int returnsIndex = StringUtils.indexOfIgnoreCaseRespectQuotes(0, procedureDef, " RETURNS ", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
                    final int endReturnsDef = this.findEndOfReturnsClause(procedureDef, quoteChar, returnsIndex);
                    int declarationStart;
                    for (declarationStart = returnsIndex + "RETURNS ".length(); declarationStart < procedureDef.length() && Character.isWhitespace(procedureDef.charAt(declarationStart)); ++declarationStart) {}
                    final String returnsDefn = procedureDef.substring(declarationStart, endReturnsDef).trim();
                    final TypeDescriptor returnDescriptor = new TypeDescriptor(returnsDefn, null);
                    resultRows.add(this.convertTypeDescriptorToProcedureRow(procNameAsBytes, procCatAsBytes, "", false, false, true, returnDescriptor, forGetFunctionColumns, 0));
                }
                if (openParenIndex == -1 || endOfParamDeclarationIndex == -1) {
                    throw SQLError.createSQLException("Internal error when parsing callable statement metadata", "S1000", this.getExceptionInterceptor());
                }
                parameterDef = procedureDef.substring(openParenIndex + 1, endOfParamDeclarationIndex);
            }
        }
        finally {
            SQLException sqlExRethrow = null;
            if (paramRetrievalRs != null) {
                try {
                    paramRetrievalRs.close();
                }
                catch (SQLException sqlEx) {
                    sqlExRethrow = sqlEx;
                }
                paramRetrievalRs = null;
            }
            if (paramRetrievalStmt != null) {
                try {
                    paramRetrievalStmt.close();
                }
                catch (SQLException sqlEx) {
                    sqlExRethrow = sqlEx;
                }
                paramRetrievalStmt = null;
            }
            if (sqlExRethrow != null) {
                throw sqlExRethrow;
            }
        }
        if (parameterDef != null) {
            int ordinal = 1;
            final List parseList = StringUtils.split(parameterDef, ",", storageDefnDelims, storageDefnClosures, true);
            for (int parseListLen = parseList.size(), i = 0; i < parseListLen; ++i) {
                String declaration = parseList.get(i);
                if (declaration.trim().length() == 0) {
                    break;
                }
                declaration = declaration.replaceAll("[\\t\\n\\x0B\\f\\r]", " ");
                final StringTokenizer declarationTok = new StringTokenizer(declaration, " \t");
                String paramName = null;
                boolean isOutParam = false;
                boolean isInParam = false;
                if (!declarationTok.hasMoreTokens()) {
                    throw SQLError.createSQLException("Internal error when parsing callable statement metadata (unknown output from 'SHOW CREATE PROCEDURE')", "S1000", this.getExceptionInterceptor());
                }
                final String possibleParamName = declarationTok.nextToken();
                if (possibleParamName.equalsIgnoreCase("OUT")) {
                    isOutParam = true;
                    if (!declarationTok.hasMoreTokens()) {
                        throw SQLError.createSQLException("Internal error when parsing callable statement metadata (missing parameter name)", "S1000", this.getExceptionInterceptor());
                    }
                    paramName = declarationTok.nextToken();
                }
                else if (possibleParamName.equalsIgnoreCase("INOUT")) {
                    isOutParam = true;
                    isInParam = true;
                    if (!declarationTok.hasMoreTokens()) {
                        throw SQLError.createSQLException("Internal error when parsing callable statement metadata (missing parameter name)", "S1000", this.getExceptionInterceptor());
                    }
                    paramName = declarationTok.nextToken();
                }
                else if (possibleParamName.equalsIgnoreCase("IN")) {
                    isOutParam = false;
                    isInParam = true;
                    if (!declarationTok.hasMoreTokens()) {
                        throw SQLError.createSQLException("Internal error when parsing callable statement metadata (missing parameter name)", "S1000", this.getExceptionInterceptor());
                    }
                    paramName = declarationTok.nextToken();
                }
                else {
                    isOutParam = false;
                    isInParam = true;
                    paramName = possibleParamName;
                }
                TypeDescriptor typeDesc = null;
                if (!declarationTok.hasMoreTokens()) {
                    throw SQLError.createSQLException("Internal error when parsing callable statement metadata (missing parameter type)", "S1000", this.getExceptionInterceptor());
                }
                final StringBuffer typeInfoBuf = new StringBuffer(declarationTok.nextToken());
                while (declarationTok.hasMoreTokens()) {
                    typeInfoBuf.append(" ");
                    typeInfoBuf.append(declarationTok.nextToken());
                }
                final String typeInfo = typeInfoBuf.toString();
                typeDesc = new TypeDescriptor(typeInfo, null);
                if ((paramName.startsWith("`") && paramName.endsWith("`")) || (isProcedureInAnsiMode && paramName.startsWith("\"") && paramName.endsWith("\""))) {
                    paramName = paramName.substring(1, paramName.length() - 1);
                }
                final int wildCompareRes = StringUtils.wildCompare(paramName, parameterNamePattern);
                if (wildCompareRes != -1) {
                    final ResultSetRow row = this.convertTypeDescriptorToProcedureRow(procNameAsBytes, procCatAsBytes, paramName, isOutParam, isInParam, false, typeDesc, forGetFunctionColumns, ordinal++);
                    resultRows.add(row);
                }
            }
        }
    }
    
    private int endPositionOfParameterDeclaration(final int beginIndex, final String procedureDef, final String quoteChar) throws SQLException {
        int currentPos = beginIndex + 1;
        int parenDepth = 1;
        while (parenDepth > 0 && currentPos < procedureDef.length()) {
            final int closedParenIndex = StringUtils.indexOfIgnoreCaseRespectQuotes(currentPos, procedureDef, ")", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
            if (closedParenIndex == -1) {
                throw SQLError.createSQLException("Internal error when parsing callable statement metadata", "S1000", this.getExceptionInterceptor());
            }
            final int nextOpenParenIndex = StringUtils.indexOfIgnoreCaseRespectQuotes(currentPos, procedureDef, "(", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
            if (nextOpenParenIndex != -1 && nextOpenParenIndex < closedParenIndex) {
                ++parenDepth;
                currentPos = closedParenIndex + 1;
            }
            else {
                --parenDepth;
                currentPos = closedParenIndex;
            }
        }
        return currentPos;
    }
    
    private int findEndOfReturnsClause(final String procedureDefn, final String quoteChar, final int positionOfReturnKeyword) throws SQLException {
        final String[] tokens = { "LANGUAGE", "NOT", "DETERMINISTIC", "CONTAINS", "NO", "READ", "MODIFIES", "SQL", "COMMENT", "BEGIN", "RETURN" };
        final int startLookingAt = positionOfReturnKeyword + "RETURNS".length() + 1;
        int endOfReturn = -1;
        for (int i = 0; i < tokens.length; ++i) {
            final int nextEndOfReturn = StringUtils.indexOfIgnoreCaseRespectQuotes(startLookingAt, procedureDefn, tokens[i], quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
            if (nextEndOfReturn != -1 && (endOfReturn == -1 || nextEndOfReturn < endOfReturn)) {
                endOfReturn = nextEndOfReturn;
            }
        }
        if (endOfReturn != -1) {
            return endOfReturn;
        }
        endOfReturn = StringUtils.indexOfIgnoreCaseRespectQuotes(startLookingAt, procedureDefn, ":", quoteChar.charAt(0), !this.conn.isNoBackslashEscapesSet());
        if (endOfReturn != -1) {
            for (int i = endOfReturn; i > 0; --i) {
                if (Character.isWhitespace(procedureDefn.charAt(i))) {
                    return i;
                }
            }
        }
        throw SQLError.createSQLException("Internal error when parsing callable statement metadata", "S1000", this.getExceptionInterceptor());
    }
    
    private int getCascadeDeleteOption(final String cascadeOptions) {
        final int onDeletePos = cascadeOptions.indexOf("ON DELETE");
        if (onDeletePos != -1) {
            final String deleteOptions = cascadeOptions.substring(onDeletePos, cascadeOptions.length());
            if (deleteOptions.startsWith("ON DELETE CASCADE")) {
                return 0;
            }
            if (deleteOptions.startsWith("ON DELETE SET NULL")) {
                return 2;
            }
            if (deleteOptions.startsWith("ON DELETE RESTRICT")) {
                return 1;
            }
            if (deleteOptions.startsWith("ON DELETE NO ACTION")) {
                return 3;
            }
        }
        return 3;
    }
    
    private int getCascadeUpdateOption(final String cascadeOptions) {
        final int onUpdatePos = cascadeOptions.indexOf("ON UPDATE");
        if (onUpdatePos != -1) {
            final String updateOptions = cascadeOptions.substring(onUpdatePos, cascadeOptions.length());
            if (updateOptions.startsWith("ON UPDATE CASCADE")) {
                return 0;
            }
            if (updateOptions.startsWith("ON UPDATE SET NULL")) {
                return 2;
            }
            if (updateOptions.startsWith("ON UPDATE RESTRICT")) {
                return 1;
            }
            if (updateOptions.startsWith("ON UPDATE NO ACTION")) {
                return 3;
            }
        }
        return 3;
    }
    
    protected IteratorWithCleanup getCatalogIterator(final String catalogSpec) throws SQLException {
        IteratorWithCleanup allCatalogsIter;
        if (catalogSpec != null) {
            if (!catalogSpec.equals("")) {
                allCatalogsIter = new SingleStringIterator(catalogSpec);
            }
            else {
                allCatalogsIter = new SingleStringIterator(this.database);
            }
        }
        else if (this.conn.getNullCatalogMeansCurrent()) {
            allCatalogsIter = new SingleStringIterator(this.database);
        }
        else {
            allCatalogsIter = new ResultSetIterator(this.getCatalogs(), 1);
        }
        return allCatalogsIter;
    }
    
    public ResultSet getCatalogs() throws SQLException {
        ResultSet results = null;
        Statement stmt = null;
        try {
            stmt = this.conn.createStatement();
            stmt.setEscapeProcessing(false);
            results = stmt.executeQuery("SHOW DATABASES");
            final ResultSetMetaData resultsMD = results.getMetaData();
            final Field[] fields = { new Field("", "TABLE_CAT", 12, resultsMD.getColumnDisplaySize(1)) };
            final ArrayList tuples = new ArrayList();
            while (results.next()) {
                final byte[][] rowVal = { results.getBytes(1) };
                tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
            }
            return this.buildResultSet(fields, tuples);
        }
        finally {
            if (results != null) {
                try {
                    results.close();
                }
                catch (SQLException sqlEx) {
                    AssertionFailedException.shouldNotHappen(sqlEx);
                }
                results = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException sqlEx) {
                    AssertionFailedException.shouldNotHappen(sqlEx);
                }
                stmt = null;
            }
        }
    }
    
    public String getCatalogSeparator() throws SQLException {
        return ".";
    }
    
    public String getCatalogTerm() throws SQLException {
        return "database";
    }
    
    public ResultSet getColumnPrivileges(final String catalog, final String schema, final String table, final String columnNamePattern) throws SQLException {
        final Field[] fields = { new Field("", "TABLE_CAT", 1, 64), new Field("", "TABLE_SCHEM", 1, 1), new Field("", "TABLE_NAME", 1, 64), new Field("", "COLUMN_NAME", 1, 64), new Field("", "GRANTOR", 1, 77), new Field("", "GRANTEE", 1, 77), new Field("", "PRIVILEGE", 1, 64), new Field("", "IS_GRANTABLE", 1, 3) };
        final StringBuffer grantQuery = new StringBuffer("SELECT c.host, c.db, t.grantor, c.user, c.table_name, c.column_name, c.column_priv from mysql.columns_priv c, mysql.tables_priv t where c.host = t.host and c.db = t.db and c.table_name = t.table_name ");
        if (catalog != null && catalog.length() != 0) {
            grantQuery.append(" AND c.db='");
            grantQuery.append(catalog);
            grantQuery.append("' ");
        }
        grantQuery.append(" AND c.table_name ='");
        grantQuery.append(table);
        grantQuery.append("' AND c.column_name like '");
        grantQuery.append(columnNamePattern);
        grantQuery.append("'");
        Statement stmt = null;
        ResultSet results = null;
        final ArrayList grantRows = new ArrayList();
        try {
            stmt = this.conn.createStatement();
            stmt.setEscapeProcessing(false);
            results = stmt.executeQuery(grantQuery.toString());
            while (results.next()) {
                final String host = results.getString(1);
                final String db = results.getString(2);
                final String grantor = results.getString(3);
                String user = results.getString(4);
                if (user == null || user.length() == 0) {
                    user = "%";
                }
                final StringBuffer fullUser = new StringBuffer(user);
                if (host != null && this.conn.getUseHostsInPrivileges()) {
                    fullUser.append("@");
                    fullUser.append(host);
                }
                final String columnName = results.getString(6);
                String allPrivileges = results.getString(7);
                if (allPrivileges != null) {
                    allPrivileges = allPrivileges.toUpperCase(Locale.ENGLISH);
                    final StringTokenizer st = new StringTokenizer(allPrivileges, ",");
                    while (st.hasMoreTokens()) {
                        final String privilege = st.nextToken().trim();
                        final byte[][] tuple = { this.s2b(db), null, this.s2b(table), this.s2b(columnName), null, null, null, null };
                        if (grantor != null) {
                            tuple[4] = this.s2b(grantor);
                        }
                        else {
                            tuple[4] = null;
                        }
                        tuple[5] = this.s2b(fullUser.toString());
                        tuple[6] = this.s2b(privilege);
                        tuple[7] = null;
                        grantRows.add(new ByteArrayRow(tuple, this.getExceptionInterceptor()));
                    }
                }
            }
        }
        finally {
            if (results != null) {
                try {
                    results.close();
                }
                catch (Exception ex) {}
                results = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (Exception ex2) {}
                stmt = null;
            }
        }
        return this.buildResultSet(fields, grantRows);
    }
    
    public ResultSet getColumns(final String catalog, final String schemaPattern, final String tableNamePattern, String columnNamePattern) throws SQLException {
        if (columnNamePattern == null) {
            if (!this.conn.getNullNamePatternMatchesAll()) {
                throw SQLError.createSQLException("Column name pattern can not be NULL or empty.", "S1009", this.getExceptionInterceptor());
            }
            columnNamePattern = "%";
        }
        final String colPattern = columnNamePattern;
        final Field[] fields = this.createColumnsFields();
        final ArrayList rows = new ArrayList();
        final Statement stmt = this.conn.getMetadataSafeStatement();
        try {
            new IterateBlock(this.getCatalogIterator(catalog)) {
                void forEach(final Object catalogStr) throws SQLException {
                    final ArrayList tableNameList = new ArrayList();
                    if (tableNamePattern == null) {
                        ResultSet tables = null;
                        try {
                            tables = DatabaseMetaData.this.getTables((String)catalogStr, schemaPattern, "%", new String[0]);
                            while (tables.next()) {
                                final String tableNameFromList = tables.getString("TABLE_NAME");
                                tableNameList.add(tableNameFromList);
                            }
                        }
                        finally {
                            if (tables != null) {
                                try {
                                    tables.close();
                                }
                                catch (Exception sqlEx) {
                                    AssertionFailedException.shouldNotHappen(sqlEx);
                                }
                                tables = null;
                            }
                        }
                    }
                    else {
                        ResultSet tables = null;
                        try {
                            tables = DatabaseMetaData.this.getTables((String)catalogStr, schemaPattern, tableNamePattern, new String[0]);
                            while (tables.next()) {
                                final String tableNameFromList = tables.getString("TABLE_NAME");
                                tableNameList.add(tableNameFromList);
                            }
                        }
                        finally {
                            if (tables != null) {
                                try {
                                    tables.close();
                                }
                                catch (SQLException sqlEx2) {
                                    AssertionFailedException.shouldNotHappen(sqlEx2);
                                }
                                tables = null;
                            }
                        }
                    }
                    for (final String tableName : tableNameList) {
                        ResultSet results = null;
                        try {
                            final StringBuffer queryBuf = new StringBuffer("SHOW ");
                            if (DatabaseMetaData.this.conn.versionMeetsMinimum(4, 1, 0)) {
                                queryBuf.append("FULL ");
                            }
                            queryBuf.append("COLUMNS FROM ");
                            queryBuf.append(DatabaseMetaData.this.quotedId);
                            queryBuf.append(tableName);
                            queryBuf.append(DatabaseMetaData.this.quotedId);
                            queryBuf.append(" FROM ");
                            queryBuf.append(DatabaseMetaData.this.quotedId);
                            queryBuf.append((String)catalogStr);
                            queryBuf.append(DatabaseMetaData.this.quotedId);
                            queryBuf.append(" LIKE '");
                            queryBuf.append(colPattern);
                            queryBuf.append("'");
                            boolean fixUpOrdinalsRequired = false;
                            Map ordinalFixUpMap = null;
                            if (!colPattern.equals("%")) {
                                fixUpOrdinalsRequired = true;
                                final StringBuffer fullColumnQueryBuf = new StringBuffer("SHOW ");
                                if (DatabaseMetaData.this.conn.versionMeetsMinimum(4, 1, 0)) {
                                    fullColumnQueryBuf.append("FULL ");
                                }
                                fullColumnQueryBuf.append("COLUMNS FROM ");
                                fullColumnQueryBuf.append(DatabaseMetaData.this.quotedId);
                                fullColumnQueryBuf.append(tableName);
                                fullColumnQueryBuf.append(DatabaseMetaData.this.quotedId);
                                fullColumnQueryBuf.append(" FROM ");
                                fullColumnQueryBuf.append(DatabaseMetaData.this.quotedId);
                                fullColumnQueryBuf.append((String)catalogStr);
                                fullColumnQueryBuf.append(DatabaseMetaData.this.quotedId);
                                results = stmt.executeQuery(fullColumnQueryBuf.toString());
                                ordinalFixUpMap = new HashMap();
                                int fullOrdinalPos = 1;
                                while (results.next()) {
                                    final String fullOrdColName = results.getString("Field");
                                    ordinalFixUpMap.put(fullOrdColName, Constants.integerValueOf(fullOrdinalPos++));
                                }
                            }
                            results = stmt.executeQuery(queryBuf.toString());
                            int ordPos = 1;
                            while (results.next()) {
                                final byte[][] rowVal = new byte[23][];
                                rowVal[0] = DatabaseMetaData.this.s2b((String)catalogStr);
                                rowVal[1] = null;
                                rowVal[2] = DatabaseMetaData.this.s2b(tableName);
                                rowVal[3] = results.getBytes("Field");
                                final TypeDescriptor typeDesc = new TypeDescriptor(results.getString("Type"), results.getString("Null"));
                                rowVal[4] = Short.toString(typeDesc.dataType).getBytes();
                                rowVal[5] = DatabaseMetaData.this.s2b(typeDesc.typeName);
                                rowVal[6] = (byte[])((typeDesc.columnSize == null) ? null : DatabaseMetaData.this.s2b(typeDesc.columnSize.toString()));
                                rowVal[7] = DatabaseMetaData.this.s2b(Integer.toString(typeDesc.bufferLength));
                                rowVal[8] = (byte[])((typeDesc.decimalDigits == null) ? null : DatabaseMetaData.this.s2b(typeDesc.decimalDigits.toString()));
                                rowVal[9] = DatabaseMetaData.this.s2b(Integer.toString(typeDesc.numPrecRadix));
                                rowVal[10] = DatabaseMetaData.this.s2b(Integer.toString(typeDesc.nullability));
                                try {
                                    if (DatabaseMetaData.this.conn.versionMeetsMinimum(4, 1, 0)) {
                                        rowVal[11] = results.getBytes("Comment");
                                    }
                                    else {
                                        rowVal[11] = results.getBytes("Extra");
                                    }
                                }
                                catch (Exception E) {
                                    rowVal[11] = new byte[0];
                                }
                                rowVal[12] = results.getBytes("Default");
                                rowVal[13] = new byte[] { 48 };
                                rowVal[14] = new byte[] { 48 };
                                if (StringUtils.indexOfIgnoreCase(typeDesc.typeName, "CHAR") != -1 || StringUtils.indexOfIgnoreCase(typeDesc.typeName, "BLOB") != -1 || StringUtils.indexOfIgnoreCase(typeDesc.typeName, "TEXT") != -1 || StringUtils.indexOfIgnoreCase(typeDesc.typeName, "BINARY") != -1) {
                                    rowVal[15] = rowVal[6];
                                }
                                else {
                                    rowVal[15] = null;
                                }
                                if (!fixUpOrdinalsRequired) {
                                    rowVal[16] = Integer.toString(ordPos++).getBytes();
                                }
                                else {
                                    final String origColName = results.getString("Field");
                                    final Integer realOrdinal = ordinalFixUpMap.get(origColName);
                                    if (realOrdinal == null) {
                                        throw SQLError.createSQLException("Can not find column in full column list to determine true ordinal position.", "S1000", DatabaseMetaData.this.getExceptionInterceptor());
                                    }
                                    rowVal[16] = realOrdinal.toString().getBytes();
                                }
                                rowVal[17] = DatabaseMetaData.this.s2b(typeDesc.isNullable);
                                rowVal[19] = (rowVal[18] = null);
                                rowVal[21] = (rowVal[20] = null);
                                rowVal[22] = DatabaseMetaData.this.s2b("");
                                final String extra = results.getString("Extra");
                                if (extra != null) {
                                    rowVal[22] = DatabaseMetaData.this.s2b((StringUtils.indexOfIgnoreCase(extra, "auto_increment") != -1) ? "YES" : "NO");
                                }
                                rows.add(new ByteArrayRow(rowVal, DatabaseMetaData.this.getExceptionInterceptor()));
                            }
                        }
                        finally {
                            if (results != null) {
                                try {
                                    results.close();
                                }
                                catch (Exception ex) {}
                                results = null;
                            }
                        }
                    }
                }
            }.doForAll();
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        final ResultSet results = this.buildResultSet(fields, rows);
        return results;
    }
    
    protected Field[] createColumnsFields() {
        final Field[] fields = { new Field("", "TABLE_CAT", 1, 255), new Field("", "TABLE_SCHEM", 1, 0), new Field("", "TABLE_NAME", 1, 255), new Field("", "COLUMN_NAME", 1, 32), new Field("", "DATA_TYPE", 4, 5), new Field("", "TYPE_NAME", 1, 16), new Field("", "COLUMN_SIZE", 4, Integer.toString(Integer.MAX_VALUE).length()), new Field("", "BUFFER_LENGTH", 4, 10), new Field("", "DECIMAL_DIGITS", 4, 10), new Field("", "NUM_PREC_RADIX", 4, 10), new Field("", "NULLABLE", 4, 10), new Field("", "REMARKS", 1, 0), new Field("", "COLUMN_DEF", 1, 0), new Field("", "SQL_DATA_TYPE", 4, 10), new Field("", "SQL_DATETIME_SUB", 4, 10), new Field("", "CHAR_OCTET_LENGTH", 4, Integer.toString(Integer.MAX_VALUE).length()), new Field("", "ORDINAL_POSITION", 4, 10), new Field("", "IS_NULLABLE", 1, 3), new Field("", "SCOPE_CATALOG", 1, 255), new Field("", "SCOPE_SCHEMA", 1, 255), new Field("", "SCOPE_TABLE", 1, 255), new Field("", "SOURCE_DATA_TYPE", 5, 10), new Field("", "IS_AUTOINCREMENT", 1, 3) };
        return fields;
    }
    
    public Connection getConnection() throws SQLException {
        return this.conn;
    }
    
    public ResultSet getCrossReference(final String primaryCatalog, final String primarySchema, final String primaryTable, final String foreignCatalog, final String foreignSchema, final String foreignTable) throws SQLException {
        if (primaryTable == null) {
            throw SQLError.createSQLException("Table not specified.", "S1009", this.getExceptionInterceptor());
        }
        final Field[] fields = this.createFkMetadataFields();
        final ArrayList tuples = new ArrayList();
        if (this.conn.versionMeetsMinimum(3, 23, 0)) {
            final Statement stmt = this.conn.getMetadataSafeStatement();
            try {
                new IterateBlock(this.getCatalogIterator(foreignCatalog)) {
                    void forEach(final Object catalogStr) throws SQLException {
                        ResultSet fkresults = null;
                        try {
                            if (DatabaseMetaData.this.conn.versionMeetsMinimum(3, 23, 50)) {
                                fkresults = DatabaseMetaData.this.extractForeignKeyFromCreateTable(catalogStr.toString(), null);
                            }
                            else {
                                final StringBuffer queryBuf = new StringBuffer("SHOW TABLE STATUS FROM ");
                                queryBuf.append(DatabaseMetaData.this.quotedId);
                                queryBuf.append(catalogStr.toString());
                                queryBuf.append(DatabaseMetaData.this.quotedId);
                                fkresults = stmt.executeQuery(queryBuf.toString());
                            }
                            final String foreignTableWithCase = DatabaseMetaData.this.getTableNameWithCase(foreignTable);
                            final String primaryTableWithCase = DatabaseMetaData.this.getTableNameWithCase(primaryTable);
                            while (fkresults.next()) {
                                final String tableType = fkresults.getString("Type");
                                if (tableType != null && (tableType.equalsIgnoreCase("innodb") || tableType.equalsIgnoreCase("SUPPORTS_FK"))) {
                                    final String comment = fkresults.getString("Comment").trim();
                                    if (comment == null) {
                                        continue;
                                    }
                                    final StringTokenizer commentTokens = new StringTokenizer(comment, ";", false);
                                    if (commentTokens.hasMoreTokens()) {
                                        commentTokens.nextToken();
                                    }
                                    while (commentTokens.hasMoreTokens()) {
                                        final String keys = commentTokens.nextToken();
                                        final LocalAndReferencedColumns parsedInfo = DatabaseMetaData.this.parseTableStatusIntoLocalAndReferencedColumns(keys);
                                        int keySeq = 0;
                                        final Iterator referencingColumns = parsedInfo.localColumnsList.iterator();
                                        final Iterator referencedColumns = parsedInfo.referencedColumnsList.iterator();
                                        while (referencingColumns.hasNext()) {
                                            final String referencingColumn = DatabaseMetaData.this.removeQuotedId(referencingColumns.next().toString());
                                            final byte[][] tuple = new byte[14][];
                                            tuple[4] = (byte[])((foreignCatalog == null) ? null : DatabaseMetaData.this.s2b(foreignCatalog));
                                            tuple[5] = (byte[])((foreignSchema == null) ? null : DatabaseMetaData.this.s2b(foreignSchema));
                                            final String dummy = fkresults.getString("Name");
                                            if (dummy.compareTo(foreignTableWithCase) != 0) {
                                                continue;
                                            }
                                            tuple[6] = DatabaseMetaData.this.s2b(dummy);
                                            tuple[7] = DatabaseMetaData.this.s2b(referencingColumn);
                                            tuple[0] = (byte[])((primaryCatalog == null) ? null : DatabaseMetaData.this.s2b(primaryCatalog));
                                            tuple[1] = (byte[])((primarySchema == null) ? null : DatabaseMetaData.this.s2b(primarySchema));
                                            if (parsedInfo.referencedTable.compareTo(primaryTableWithCase) != 0) {
                                                continue;
                                            }
                                            tuple[2] = DatabaseMetaData.this.s2b(parsedInfo.referencedTable);
                                            tuple[3] = DatabaseMetaData.this.s2b(DatabaseMetaData.this.removeQuotedId(referencedColumns.next().toString()));
                                            tuple[8] = Integer.toString(keySeq).getBytes();
                                            final int[] actions = DatabaseMetaData.this.getForeignKeyActions(keys);
                                            tuple[9] = Integer.toString(actions[1]).getBytes();
                                            tuple[10] = Integer.toString(actions[0]).getBytes();
                                            tuple[12] = (tuple[11] = null);
                                            tuple[13] = Integer.toString(7).getBytes();
                                            tuples.add(new ByteArrayRow(tuple, DatabaseMetaData.this.getExceptionInterceptor()));
                                            ++keySeq;
                                        }
                                    }
                                }
                            }
                        }
                        finally {
                            if (fkresults != null) {
                                try {
                                    fkresults.close();
                                }
                                catch (Exception sqlEx) {
                                    AssertionFailedException.shouldNotHappen(sqlEx);
                                }
                                fkresults = null;
                            }
                        }
                    }
                }.doForAll();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        final ResultSet results = this.buildResultSet(fields, tuples);
        return results;
    }
    
    protected Field[] createFkMetadataFields() {
        final Field[] fields = { new Field("", "PKTABLE_CAT", 1, 255), new Field("", "PKTABLE_SCHEM", 1, 0), new Field("", "PKTABLE_NAME", 1, 255), new Field("", "PKCOLUMN_NAME", 1, 32), new Field("", "FKTABLE_CAT", 1, 255), new Field("", "FKTABLE_SCHEM", 1, 0), new Field("", "FKTABLE_NAME", 1, 255), new Field("", "FKCOLUMN_NAME", 1, 32), new Field("", "KEY_SEQ", 5, 2), new Field("", "UPDATE_RULE", 5, 2), new Field("", "DELETE_RULE", 5, 2), new Field("", "FK_NAME", 1, 0), new Field("", "PK_NAME", 1, 0), new Field("", "DEFERRABILITY", 5, 2) };
        return fields;
    }
    
    public int getDatabaseMajorVersion() throws SQLException {
        return this.conn.getServerMajorVersion();
    }
    
    public int getDatabaseMinorVersion() throws SQLException {
        return this.conn.getServerMinorVersion();
    }
    
    public String getDatabaseProductName() throws SQLException {
        return "MySQL";
    }
    
    public String getDatabaseProductVersion() throws SQLException {
        return this.conn.getServerVersion();
    }
    
    public int getDefaultTransactionIsolation() throws SQLException {
        if (this.conn.supportsIsolationLevel()) {
            return 2;
        }
        return 0;
    }
    
    public int getDriverMajorVersion() {
        return NonRegisteringDriver.getMajorVersionInternal();
    }
    
    public int getDriverMinorVersion() {
        return NonRegisteringDriver.getMinorVersionInternal();
    }
    
    public String getDriverName() throws SQLException {
        return "MySQL-AB JDBC Driver";
    }
    
    public String getDriverVersion() throws SQLException {
        return "mysql-connector-java-5.1.14 ( Revision: ${bzr.revision-id} )";
    }
    
    public ResultSet getExportedKeys(final String catalog, final String schema, final String table) throws SQLException {
        if (table == null) {
            throw SQLError.createSQLException("Table not specified.", "S1009", this.getExceptionInterceptor());
        }
        final Field[] fields = this.createFkMetadataFields();
        final ArrayList rows = new ArrayList();
        if (this.conn.versionMeetsMinimum(3, 23, 0)) {
            final Statement stmt = this.conn.getMetadataSafeStatement();
            try {
                new IterateBlock(this.getCatalogIterator(catalog)) {
                    void forEach(final Object catalogStr) throws SQLException {
                        ResultSet fkresults = null;
                        try {
                            if (DatabaseMetaData.this.conn.versionMeetsMinimum(3, 23, 50)) {
                                fkresults = DatabaseMetaData.this.extractForeignKeyFromCreateTable(catalogStr.toString(), null);
                            }
                            else {
                                final StringBuffer queryBuf = new StringBuffer("SHOW TABLE STATUS FROM ");
                                queryBuf.append(DatabaseMetaData.this.quotedId);
                                queryBuf.append(catalogStr.toString());
                                queryBuf.append(DatabaseMetaData.this.quotedId);
                                fkresults = stmt.executeQuery(queryBuf.toString());
                            }
                            final String tableNameWithCase = DatabaseMetaData.this.getTableNameWithCase(table);
                            while (fkresults.next()) {
                                final String tableType = fkresults.getString("Type");
                                if (tableType != null && (tableType.equalsIgnoreCase("innodb") || tableType.equalsIgnoreCase("SUPPORTS_FK"))) {
                                    final String comment = fkresults.getString("Comment").trim();
                                    if (comment == null) {
                                        continue;
                                    }
                                    final StringTokenizer commentTokens = new StringTokenizer(comment, ";", false);
                                    if (!commentTokens.hasMoreTokens()) {
                                        continue;
                                    }
                                    commentTokens.nextToken();
                                    while (commentTokens.hasMoreTokens()) {
                                        final String keys = commentTokens.nextToken();
                                        DatabaseMetaData.this.getExportKeyResults(catalogStr.toString(), tableNameWithCase, keys, rows, fkresults.getString("Name"));
                                    }
                                }
                            }
                        }
                        finally {
                            if (fkresults != null) {
                                try {
                                    fkresults.close();
                                }
                                catch (SQLException sqlEx) {
                                    AssertionFailedException.shouldNotHappen(sqlEx);
                                }
                                fkresults = null;
                            }
                        }
                    }
                }.doForAll();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        final ResultSet results = this.buildResultSet(fields, rows);
        return results;
    }
    
    private void getExportKeyResults(final String catalog, final String exportingTable, final String keysComment, final List tuples, final String fkTableName) throws SQLException {
        this.getResultsImpl(catalog, exportingTable, keysComment, tuples, fkTableName, true);
    }
    
    public String getExtraNameCharacters() throws SQLException {
        return "#@";
    }
    
    private int[] getForeignKeyActions(final String commentString) {
        final int[] actions = { 3, 3 };
        final int lastParenIndex = commentString.lastIndexOf(")");
        if (lastParenIndex != commentString.length() - 1) {
            final String cascadeOptions = commentString.substring(lastParenIndex + 1).trim().toUpperCase(Locale.ENGLISH);
            actions[0] = this.getCascadeDeleteOption(cascadeOptions);
            actions[1] = this.getCascadeUpdateOption(cascadeOptions);
        }
        return actions;
    }
    
    public String getIdentifierQuoteString() throws SQLException {
        if (!this.conn.supportsQuotedIdentifiers()) {
            return " ";
        }
        if (!this.conn.useAnsiQuotedIdentifiers()) {
            return "`";
        }
        return "\"";
    }
    
    public ResultSet getImportedKeys(final String catalog, final String schema, final String table) throws SQLException {
        if (table == null) {
            throw SQLError.createSQLException("Table not specified.", "S1009", this.getExceptionInterceptor());
        }
        final Field[] fields = this.createFkMetadataFields();
        final ArrayList rows = new ArrayList();
        if (this.conn.versionMeetsMinimum(3, 23, 0)) {
            final Statement stmt = this.conn.getMetadataSafeStatement();
            try {
                new IterateBlock(this.getCatalogIterator(catalog)) {
                    void forEach(final Object catalogStr) throws SQLException {
                        ResultSet fkresults = null;
                        try {
                            if (DatabaseMetaData.this.conn.versionMeetsMinimum(3, 23, 50)) {
                                fkresults = DatabaseMetaData.this.extractForeignKeyFromCreateTable(catalogStr.toString(), table);
                            }
                            else {
                                final StringBuffer queryBuf = new StringBuffer("SHOW TABLE STATUS ");
                                queryBuf.append(" FROM ");
                                queryBuf.append(DatabaseMetaData.this.quotedId);
                                queryBuf.append(catalogStr.toString());
                                queryBuf.append(DatabaseMetaData.this.quotedId);
                                queryBuf.append(" LIKE '");
                                queryBuf.append(table);
                                queryBuf.append("'");
                                fkresults = stmt.executeQuery(queryBuf.toString());
                            }
                            while (fkresults.next()) {
                                final String tableType = fkresults.getString("Type");
                                if (tableType != null && (tableType.equalsIgnoreCase("innodb") || tableType.equalsIgnoreCase("SUPPORTS_FK"))) {
                                    final String comment = fkresults.getString("Comment").trim();
                                    if (comment == null) {
                                        continue;
                                    }
                                    final StringTokenizer commentTokens = new StringTokenizer(comment, ";", false);
                                    if (!commentTokens.hasMoreTokens()) {
                                        continue;
                                    }
                                    commentTokens.nextToken();
                                    while (commentTokens.hasMoreTokens()) {
                                        final String keys = commentTokens.nextToken();
                                        DatabaseMetaData.this.getImportKeyResults(catalogStr.toString(), table, keys, rows);
                                    }
                                }
                            }
                        }
                        finally {
                            if (fkresults != null) {
                                try {
                                    fkresults.close();
                                }
                                catch (SQLException sqlEx) {
                                    AssertionFailedException.shouldNotHappen(sqlEx);
                                }
                                fkresults = null;
                            }
                        }
                    }
                }.doForAll();
            }
            finally {
                if (stmt != null) {
                    stmt.close();
                }
            }
        }
        final ResultSet results = this.buildResultSet(fields, rows);
        return results;
    }
    
    private void getImportKeyResults(final String catalog, final String importingTable, final String keysComment, final List tuples) throws SQLException {
        this.getResultsImpl(catalog, importingTable, keysComment, tuples, null, false);
    }
    
    public ResultSet getIndexInfo(final String catalog, final String schema, final String table, final boolean unique, final boolean approximate) throws SQLException {
        final Field[] fields = this.createIndexInfoFields();
        final ArrayList rows = new ArrayList();
        final Statement stmt = this.conn.getMetadataSafeStatement();
        try {
            new IterateBlock(this.getCatalogIterator(catalog)) {
                void forEach(final Object catalogStr) throws SQLException {
                    ResultSet results = null;
                    try {
                        final StringBuffer queryBuf = new StringBuffer("SHOW INDEX FROM ");
                        queryBuf.append(DatabaseMetaData.this.quotedId);
                        queryBuf.append(table);
                        queryBuf.append(DatabaseMetaData.this.quotedId);
                        queryBuf.append(" FROM ");
                        queryBuf.append(DatabaseMetaData.this.quotedId);
                        queryBuf.append(catalogStr.toString());
                        queryBuf.append(DatabaseMetaData.this.quotedId);
                        try {
                            results = stmt.executeQuery(queryBuf.toString());
                        }
                        catch (SQLException sqlEx) {
                            final int errorCode = sqlEx.getErrorCode();
                            if (!"42S02".equals(sqlEx.getSQLState()) && errorCode != 1146) {
                                throw sqlEx;
                            }
                        }
                        while (results != null && results.next()) {
                            final byte[][] row = new byte[14][];
                            row[0] = ((catalogStr.toString() == null) ? new byte[0] : DatabaseMetaData.this.s2b(catalogStr.toString()));
                            row[1] = null;
                            row[2] = results.getBytes("Table");
                            final boolean indexIsUnique = results.getInt("Non_unique") == 0;
                            row[3] = (indexIsUnique ? DatabaseMetaData.this.s2b("false") : DatabaseMetaData.this.s2b("true"));
                            row[4] = new byte[0];
                            row[5] = results.getBytes("Key_name");
                            row[6] = Integer.toString(3).getBytes();
                            row[7] = results.getBytes("Seq_in_index");
                            row[8] = results.getBytes("Column_name");
                            row[9] = results.getBytes("Collation");
                            long cardinality = results.getLong("Cardinality");
                            if (cardinality > 2147483647L) {
                                cardinality = 2147483647L;
                            }
                            row[10] = DatabaseMetaData.this.s2b(String.valueOf(cardinality));
                            row[11] = DatabaseMetaData.this.s2b("0");
                            row[12] = null;
                            if (unique) {
                                if (!indexIsUnique) {
                                    continue;
                                }
                                rows.add(new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                            }
                            else {
                                rows.add(new ByteArrayRow(row, DatabaseMetaData.this.getExceptionInterceptor()));
                            }
                        }
                    }
                    finally {
                        if (results != null) {
                            try {
                                results.close();
                            }
                            catch (Exception ex) {}
                            results = null;
                        }
                    }
                }
            }.doForAll();
            final ResultSet indexInfo = this.buildResultSet(fields, rows);
            return indexInfo;
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }
    
    protected Field[] createIndexInfoFields() {
        final Field[] fields = { new Field("", "TABLE_CAT", 1, 255), new Field("", "TABLE_SCHEM", 1, 0), new Field("", "TABLE_NAME", 1, 255), new Field("", "NON_UNIQUE", 16, 4), new Field("", "INDEX_QUALIFIER", 1, 1), new Field("", "INDEX_NAME", 1, 32), new Field("", "TYPE", 5, 32), new Field("", "ORDINAL_POSITION", 5, 5), new Field("", "COLUMN_NAME", 1, 32), new Field("", "ASC_OR_DESC", 1, 1), new Field("", "CARDINALITY", 4, 20), new Field("", "PAGES", 4, 10), new Field("", "FILTER_CONDITION", 1, 32) };
        return fields;
    }
    
    public int getJDBCMajorVersion() throws SQLException {
        return 4;
    }
    
    public int getJDBCMinorVersion() throws SQLException {
        return 0;
    }
    
    public int getMaxBinaryLiteralLength() throws SQLException {
        return 16777208;
    }
    
    public int getMaxCatalogNameLength() throws SQLException {
        return 32;
    }
    
    public int getMaxCharLiteralLength() throws SQLException {
        return 16777208;
    }
    
    public int getMaxColumnNameLength() throws SQLException {
        return 64;
    }
    
    public int getMaxColumnsInGroupBy() throws SQLException {
        return 64;
    }
    
    public int getMaxColumnsInIndex() throws SQLException {
        return 16;
    }
    
    public int getMaxColumnsInOrderBy() throws SQLException {
        return 64;
    }
    
    public int getMaxColumnsInSelect() throws SQLException {
        return 256;
    }
    
    public int getMaxColumnsInTable() throws SQLException {
        return 512;
    }
    
    public int getMaxConnections() throws SQLException {
        return 0;
    }
    
    public int getMaxCursorNameLength() throws SQLException {
        return 64;
    }
    
    public int getMaxIndexLength() throws SQLException {
        return 256;
    }
    
    public int getMaxProcedureNameLength() throws SQLException {
        return 0;
    }
    
    public int getMaxRowSize() throws SQLException {
        return 2147483639;
    }
    
    public int getMaxSchemaNameLength() throws SQLException {
        return 0;
    }
    
    public int getMaxStatementLength() throws SQLException {
        return MysqlIO.getMaxBuf() - 4;
    }
    
    public int getMaxStatements() throws SQLException {
        return 0;
    }
    
    public int getMaxTableNameLength() throws SQLException {
        return 64;
    }
    
    public int getMaxTablesInSelect() throws SQLException {
        return 256;
    }
    
    public int getMaxUserNameLength() throws SQLException {
        return 16;
    }
    
    public String getNumericFunctions() throws SQLException {
        return "ABS,ACOS,ASIN,ATAN,ATAN2,BIT_COUNT,CEILING,COS,COT,DEGREES,EXP,FLOOR,LOG,LOG10,MAX,MIN,MOD,PI,POW,POWER,RADIANS,RAND,ROUND,SIN,SQRT,TAN,TRUNCATE";
    }
    
    public ResultSet getPrimaryKeys(final String catalog, final String schema, final String table) throws SQLException {
        final Field[] fields = { new Field("", "TABLE_CAT", 1, 255), new Field("", "TABLE_SCHEM", 1, 0), new Field("", "TABLE_NAME", 1, 255), new Field("", "COLUMN_NAME", 1, 32), new Field("", "KEY_SEQ", 5, 5), new Field("", "PK_NAME", 1, 32) };
        if (table == null) {
            throw SQLError.createSQLException("Table not specified.", "S1009", this.getExceptionInterceptor());
        }
        final ArrayList rows = new ArrayList();
        final Statement stmt = this.conn.getMetadataSafeStatement();
        try {
            new IterateBlock(this.getCatalogIterator(catalog)) {
                void forEach(final Object catalogStr) throws SQLException {
                    ResultSet rs = null;
                    try {
                        final StringBuffer queryBuf = new StringBuffer("SHOW KEYS FROM ");
                        queryBuf.append(DatabaseMetaData.this.quotedId);
                        queryBuf.append(table);
                        queryBuf.append(DatabaseMetaData.this.quotedId);
                        queryBuf.append(" FROM ");
                        queryBuf.append(DatabaseMetaData.this.quotedId);
                        queryBuf.append(catalogStr.toString());
                        queryBuf.append(DatabaseMetaData.this.quotedId);
                        rs = stmt.executeQuery(queryBuf.toString());
                        final TreeMap sortMap = new TreeMap();
                        while (rs.next()) {
                            final String keyType = rs.getString("Key_name");
                            if (keyType != null && (keyType.equalsIgnoreCase("PRIMARY") || keyType.equalsIgnoreCase("PRI"))) {
                                final byte[][] tuple = { (catalogStr.toString() == null) ? new byte[0] : DatabaseMetaData.this.s2b(catalogStr.toString()), null, DatabaseMetaData.this.s2b(table), null, null, null };
                                final String columnName = rs.getString("Column_name");
                                tuple[3] = DatabaseMetaData.this.s2b(columnName);
                                tuple[4] = DatabaseMetaData.this.s2b(rs.getString("Seq_in_index"));
                                tuple[5] = DatabaseMetaData.this.s2b(keyType);
                                sortMap.put(columnName, tuple);
                            }
                        }
                        final Iterator sortedIterator = sortMap.values().iterator();
                        while (sortedIterator.hasNext()) {
                            rows.add(new ByteArrayRow(sortedIterator.next(), DatabaseMetaData.this.getExceptionInterceptor()));
                        }
                    }
                    finally {
                        if (rs != null) {
                            try {
                                rs.close();
                            }
                            catch (Exception ex) {}
                            rs = null;
                        }
                    }
                }
            }.doForAll();
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        final ResultSet results = this.buildResultSet(fields, rows);
        return results;
    }
    
    public ResultSet getProcedureColumns(final String catalog, final String schemaPattern, final String procedureNamePattern, final String columnNamePattern) throws SQLException {
        final Field[] fields = this.createProcedureColumnsFields();
        return this.getProcedureOrFunctionColumns(fields, catalog, schemaPattern, procedureNamePattern, columnNamePattern, true, true);
    }
    
    protected Field[] createProcedureColumnsFields() {
        final Field[] fields = { new Field("", "PROCEDURE_CAT", 1, 512), new Field("", "PROCEDURE_SCHEM", 1, 512), new Field("", "PROCEDURE_NAME", 1, 512), new Field("", "COLUMN_NAME", 1, 512), new Field("", "COLUMN_TYPE", 1, 64), new Field("", "DATA_TYPE", 5, 6), new Field("", "TYPE_NAME", 1, 64), new Field("", "PRECISION", 4, 12), new Field("", "LENGTH", 4, 12), new Field("", "SCALE", 5, 12), new Field("", "RADIX", 5, 6), new Field("", "NULLABLE", 5, 6), new Field("", "REMARKS", 1, 512) };
        return fields;
    }
    
    protected ResultSet getProcedureOrFunctionColumns(final Field[] fields, String catalog, final String schemaPattern, final String procedureOrFunctionNamePattern, final String columnNamePattern, final boolean returnProcedures, final boolean returnFunctions) throws SQLException {
        final List proceduresToExtractList = new ArrayList();
        ResultSet procedureNameRs = null;
        if (this.supportsStoredProcedures()) {
            try {
                String tmpProcedureOrFunctionNamePattern = null;
                if (procedureOrFunctionNamePattern != null && procedureOrFunctionNamePattern != "%") {
                    tmpProcedureOrFunctionNamePattern = StringUtils.sanitizeProcOrFuncName(procedureOrFunctionNamePattern);
                }
                if (tmpProcedureOrFunctionNamePattern == null) {
                    tmpProcedureOrFunctionNamePattern = procedureOrFunctionNamePattern;
                }
                else {
                    String tmpCatalog = catalog;
                    final List parseList = StringUtils.splitDBdotName(tmpProcedureOrFunctionNamePattern, tmpCatalog, this.quotedId, this.conn.isNoBackslashEscapesSet());
                    if (parseList.size() == 2) {
                        tmpCatalog = parseList.get(0);
                        tmpProcedureOrFunctionNamePattern = parseList.get(1);
                    }
                }
                procedureNameRs = this.getProceduresAndOrFunctions(this.createFieldMetadataForGetProcedures(), catalog, schemaPattern, tmpProcedureOrFunctionNamePattern, returnProcedures, returnFunctions);
                String tmpstrPNameRs = null;
                String tmpstrCatNameRs = null;
                boolean hasResults = false;
                while (procedureNameRs.next()) {
                    tmpstrCatNameRs = procedureNameRs.getString(1);
                    tmpstrPNameRs = procedureNameRs.getString(3);
                    if ((!tmpstrCatNameRs.startsWith(this.quotedId) || !tmpstrCatNameRs.endsWith(this.quotedId)) && (!tmpstrCatNameRs.startsWith("\"") || !tmpstrCatNameRs.endsWith("\""))) {
                        tmpstrCatNameRs = this.quotedId + tmpstrCatNameRs + this.quotedId;
                    }
                    if ((!tmpstrPNameRs.startsWith(this.quotedId) || !tmpstrPNameRs.endsWith(this.quotedId)) && (!tmpstrPNameRs.startsWith("\"") || !tmpstrPNameRs.endsWith("\""))) {
                        tmpstrPNameRs = this.quotedId + tmpstrPNameRs + this.quotedId;
                    }
                    if (proceduresToExtractList.indexOf(tmpstrCatNameRs + "." + tmpstrPNameRs) < 0) {
                        proceduresToExtractList.add(tmpstrCatNameRs + "." + tmpstrPNameRs);
                    }
                    hasResults = true;
                }
                if (hasResults) {
                    Collections.sort((List<Comparable>)proceduresToExtractList);
                }
            }
            finally {
                SQLException rethrowSqlEx = null;
                if (procedureNameRs != null) {
                    try {
                        procedureNameRs.close();
                    }
                    catch (SQLException sqlEx) {
                        rethrowSqlEx = sqlEx;
                    }
                }
                if (rethrowSqlEx != null) {
                    throw rethrowSqlEx;
                }
            }
        }
        final ArrayList resultRows = new ArrayList();
        int idx = 0;
        String procNameToCall = "";
        for (final String procName : proceduresToExtractList) {
            if (!" ".equals(this.quotedId)) {
                idx = StringUtils.indexOfIgnoreCaseRespectQuotes(0, procName, ".", this.quotedId.charAt(0), !this.conn.isNoBackslashEscapesSet());
            }
            else {
                idx = procName.indexOf(".");
            }
            if (idx > 0) {
                catalog = procName.substring(0, idx);
                if (this.quotedId != " " && catalog.startsWith(this.quotedId) && catalog.endsWith(this.quotedId)) {
                    catalog = procName.substring(1, catalog.length() - 1);
                }
                procNameToCall = procName;
            }
            else {
                procNameToCall = procName;
            }
            this.getCallStmtParameterTypes(catalog, procNameToCall, columnNamePattern, resultRows, fields.length == 17);
        }
        return this.buildResultSet(fields, resultRows);
    }
    
    public ResultSet getProcedures(final String catalog, final String schemaPattern, final String procedureNamePattern) throws SQLException {
        final Field[] fields = this.createFieldMetadataForGetProcedures();
        return this.getProceduresAndOrFunctions(fields, catalog, schemaPattern, procedureNamePattern, true, true);
    }
    
    private Field[] createFieldMetadataForGetProcedures() {
        final Field[] fields = { new Field("", "PROCEDURE_CAT", 1, 255), new Field("", "PROCEDURE_SCHEM", 1, 255), new Field("", "PROCEDURE_NAME", 1, 255), new Field("", "reserved1", 1, 0), new Field("", "reserved2", 1, 0), new Field("", "reserved3", 1, 0), new Field("", "REMARKS", 1, 255), new Field("", "PROCEDURE_TYPE", 5, 6), new Field("", "SPECIFIC_NAME", 1, 255) };
        return fields;
    }
    
    protected ResultSet getProceduresAndOrFunctions(final Field[] fields, final String catalog, final String schemaPattern, String procedureNamePattern, final boolean returnProcedures, final boolean returnFunctions) throws SQLException {
        if (procedureNamePattern == null || procedureNamePattern.length() == 0) {
            if (!this.conn.getNullNamePatternMatchesAll()) {
                throw SQLError.createSQLException("Procedure name pattern can not be NULL or empty.", "S1009", this.getExceptionInterceptor());
            }
            procedureNamePattern = "%";
        }
        final ArrayList procedureRows = new ArrayList();
        if (this.supportsStoredProcedures()) {
            final String procNamePattern = procedureNamePattern;
            final Map procedureRowsOrderedByName = new TreeMap();
            new IterateBlock(this.getCatalogIterator(catalog)) {
                void forEach(final Object catalogStr) throws SQLException {
                    String db = catalogStr.toString();
                    boolean fromSelect = false;
                    ResultSet proceduresRs = null;
                    boolean needsClientFiltering = true;
                    PreparedStatement proceduresStmt = DatabaseMetaData.this.conn.clientPrepareStatement("SELECT name, type, comment FROM mysql.proc WHERE name like ? and db <=> ? ORDER BY name");
                    try {
                        boolean hasTypeColumn = false;
                        if (db != null) {
                            if (DatabaseMetaData.this.conn.lowerCaseTableNames()) {
                                db = db.toLowerCase();
                            }
                            proceduresStmt.setString(2, db);
                        }
                        else {
                            proceduresStmt.setNull(2, 12);
                        }
                        int nameIndex = 1;
                        if (proceduresStmt.getMaxRows() != 0) {
                            proceduresStmt.setMaxRows(0);
                        }
                        proceduresStmt.setString(1, procNamePattern);
                        try {
                            proceduresRs = proceduresStmt.executeQuery();
                            fromSelect = true;
                            needsClientFiltering = false;
                            hasTypeColumn = true;
                        }
                        catch (SQLException sqlEx2) {
                            proceduresStmt.close();
                            fromSelect = false;
                            if (DatabaseMetaData.this.conn.versionMeetsMinimum(5, 0, 1)) {
                                nameIndex = 2;
                            }
                            else {
                                nameIndex = 1;
                            }
                            proceduresStmt = DatabaseMetaData.this.conn.clientPrepareStatement("SHOW PROCEDURE STATUS LIKE ?");
                            if (proceduresStmt.getMaxRows() != 0) {
                                proceduresStmt.setMaxRows(0);
                            }
                            proceduresStmt.setString(1, procNamePattern);
                            proceduresRs = proceduresStmt.executeQuery();
                        }
                        if (returnProcedures) {
                            DatabaseMetaData.this.convertToJdbcProcedureList(fromSelect, db, proceduresRs, needsClientFiltering, db, procedureRowsOrderedByName, nameIndex);
                        }
                        if (!hasTypeColumn) {
                            if (proceduresStmt != null) {
                                proceduresStmt.close();
                            }
                            proceduresStmt = DatabaseMetaData.this.conn.clientPrepareStatement("SHOW FUNCTION STATUS LIKE ?");
                            if (proceduresStmt.getMaxRows() != 0) {
                                proceduresStmt.setMaxRows(0);
                            }
                            proceduresStmt.setString(1, procNamePattern);
                            proceduresRs = proceduresStmt.executeQuery();
                        }
                        if (returnFunctions) {
                            DatabaseMetaData.this.convertToJdbcFunctionList(db, proceduresRs, needsClientFiltering, db, procedureRowsOrderedByName, nameIndex, fields);
                        }
                        final Iterator proceduresIter = procedureRowsOrderedByName.values().iterator();
                        while (proceduresIter.hasNext()) {
                            procedureRows.add(proceduresIter.next());
                        }
                    }
                    finally {
                        SQLException rethrowSqlEx = null;
                        if (proceduresRs != null) {
                            try {
                                proceduresRs.close();
                            }
                            catch (SQLException sqlEx) {
                                rethrowSqlEx = sqlEx;
                            }
                        }
                        if (proceduresStmt != null) {
                            try {
                                proceduresStmt.close();
                            }
                            catch (SQLException sqlEx) {
                                rethrowSqlEx = sqlEx;
                            }
                        }
                        if (rethrowSqlEx != null) {
                            throw rethrowSqlEx;
                        }
                    }
                }
            }.doForAll();
        }
        return this.buildResultSet(fields, procedureRows);
    }
    
    public String getProcedureTerm() throws SQLException {
        return "PROCEDURE";
    }
    
    public int getResultSetHoldability() throws SQLException {
        return 1;
    }
    
    private void getResultsImpl(final String catalog, final String table, final String keysComment, final List tuples, final String fkTableName, final boolean isExport) throws SQLException {
        final LocalAndReferencedColumns parsedInfo = this.parseTableStatusIntoLocalAndReferencedColumns(keysComment);
        if (isExport && !parsedInfo.referencedTable.equals(table)) {
            return;
        }
        if (parsedInfo.localColumnsList.size() != parsedInfo.referencedColumnsList.size()) {
            throw SQLError.createSQLException("Error parsing foreign keys definition,number of local and referenced columns is not the same.", "S1000", this.getExceptionInterceptor());
        }
        final Iterator localColumnNames = parsedInfo.localColumnsList.iterator();
        final Iterator referColumnNames = parsedInfo.referencedColumnsList.iterator();
        int keySeqIndex = 1;
        while (localColumnNames.hasNext()) {
            final byte[][] tuple = new byte[14][];
            final String lColumnName = this.removeQuotedId(localColumnNames.next().toString());
            final String rColumnName = this.removeQuotedId(referColumnNames.next().toString());
            tuple[4] = ((catalog == null) ? new byte[0] : this.s2b(catalog));
            tuple[5] = null;
            tuple[6] = this.s2b(isExport ? fkTableName : table);
            tuple[7] = this.s2b(lColumnName);
            tuple[0] = this.s2b(parsedInfo.referencedCatalog);
            tuple[1] = null;
            tuple[2] = this.s2b(isExport ? table : parsedInfo.referencedTable);
            tuple[3] = this.s2b(rColumnName);
            tuple[8] = this.s2b(Integer.toString(keySeqIndex++));
            final int[] actions = this.getForeignKeyActions(keysComment);
            tuple[9] = this.s2b(Integer.toString(actions[1]));
            tuple[10] = this.s2b(Integer.toString(actions[0]));
            tuple[11] = this.s2b(parsedInfo.constraintName);
            tuple[12] = null;
            tuple[13] = this.s2b(Integer.toString(7));
            tuples.add(new ByteArrayRow(tuple, this.getExceptionInterceptor()));
        }
    }
    
    public ResultSet getSchemas() throws SQLException {
        final Field[] fields = { new Field("", "TABLE_SCHEM", 1, 0), new Field("", "TABLE_CATALOG", 1, 0) };
        final ArrayList tuples = new ArrayList();
        final ResultSet results = this.buildResultSet(fields, tuples);
        return results;
    }
    
    public String getSchemaTerm() throws SQLException {
        return "";
    }
    
    public String getSearchStringEscape() throws SQLException {
        return "\\";
    }
    
    public String getSQLKeywords() throws SQLException {
        return DatabaseMetaData.mysqlKeywordsThatArentSQL92;
    }
    
    public int getSQLStateType() throws SQLException {
        if (this.conn.versionMeetsMinimum(4, 1, 0)) {
            return 2;
        }
        if (this.conn.getUseSqlStateCodes()) {
            return 2;
        }
        return 1;
    }
    
    public String getStringFunctions() throws SQLException {
        return "ASCII,BIN,BIT_LENGTH,CHAR,CHARACTER_LENGTH,CHAR_LENGTH,CONCAT,CONCAT_WS,CONV,ELT,EXPORT_SET,FIELD,FIND_IN_SET,HEX,INSERT,INSTR,LCASE,LEFT,LENGTH,LOAD_FILE,LOCATE,LOCATE,LOWER,LPAD,LTRIM,MAKE_SET,MATCH,MID,OCT,OCTET_LENGTH,ORD,POSITION,QUOTE,REPEAT,REPLACE,REVERSE,RIGHT,RPAD,RTRIM,SOUNDEX,SPACE,STRCMP,SUBSTRING,SUBSTRING,SUBSTRING,SUBSTRING,SUBSTRING_INDEX,TRIM,UCASE,UPPER";
    }
    
    public ResultSet getSuperTables(final String arg0, final String arg1, final String arg2) throws SQLException {
        final Field[] fields = { new Field("", "TABLE_CAT", 1, 32), new Field("", "TABLE_SCHEM", 1, 32), new Field("", "TABLE_NAME", 1, 32), new Field("", "SUPERTABLE_NAME", 1, 32) };
        return this.buildResultSet(fields, new ArrayList());
    }
    
    public ResultSet getSuperTypes(final String arg0, final String arg1, final String arg2) throws SQLException {
        final Field[] fields = { new Field("", "TYPE_CAT", 1, 32), new Field("", "TYPE_SCHEM", 1, 32), new Field("", "TYPE_NAME", 1, 32), new Field("", "SUPERTYPE_CAT", 1, 32), new Field("", "SUPERTYPE_SCHEM", 1, 32), new Field("", "SUPERTYPE_NAME", 1, 32) };
        return this.buildResultSet(fields, new ArrayList());
    }
    
    public String getSystemFunctions() throws SQLException {
        return "DATABASE,USER,SYSTEM_USER,SESSION_USER,PASSWORD,ENCRYPT,LAST_INSERT_ID,VERSION";
    }
    
    private String getTableNameWithCase(final String table) {
        final String tableNameWithCase = this.conn.lowerCaseTableNames() ? table.toLowerCase() : table;
        return tableNameWithCase;
    }
    
    public ResultSet getTablePrivileges(final String catalog, final String schemaPattern, String tableNamePattern) throws SQLException {
        if (tableNamePattern == null) {
            if (!this.conn.getNullNamePatternMatchesAll()) {
                throw SQLError.createSQLException("Table name pattern can not be NULL or empty.", "S1009", this.getExceptionInterceptor());
            }
            tableNamePattern = "%";
        }
        final Field[] fields = { new Field("", "TABLE_CAT", 1, 64), new Field("", "TABLE_SCHEM", 1, 1), new Field("", "TABLE_NAME", 1, 64), new Field("", "GRANTOR", 1, 77), new Field("", "GRANTEE", 1, 77), new Field("", "PRIVILEGE", 1, 64), new Field("", "IS_GRANTABLE", 1, 3) };
        final StringBuffer grantQuery = new StringBuffer("SELECT host,db,table_name,grantor,user,table_priv from mysql.tables_priv ");
        grantQuery.append(" WHERE ");
        if (catalog != null && catalog.length() != 0) {
            grantQuery.append(" db='");
            grantQuery.append(catalog);
            grantQuery.append("' AND ");
        }
        grantQuery.append("table_name like '");
        grantQuery.append(tableNamePattern);
        grantQuery.append("'");
        ResultSet results = null;
        final ArrayList grantRows = new ArrayList();
        Statement stmt = null;
        try {
            stmt = this.conn.createStatement();
            stmt.setEscapeProcessing(false);
            results = stmt.executeQuery(grantQuery.toString());
            while (results.next()) {
                final String host = results.getString(1);
                final String db = results.getString(2);
                final String table = results.getString(3);
                final String grantor = results.getString(4);
                String user = results.getString(5);
                if (user == null || user.length() == 0) {
                    user = "%";
                }
                final StringBuffer fullUser = new StringBuffer(user);
                if (host != null && this.conn.getUseHostsInPrivileges()) {
                    fullUser.append("@");
                    fullUser.append(host);
                }
                String allPrivileges = results.getString(6);
                if (allPrivileges != null) {
                    allPrivileges = allPrivileges.toUpperCase(Locale.ENGLISH);
                    final StringTokenizer st = new StringTokenizer(allPrivileges, ",");
                    while (st.hasMoreTokens()) {
                        final String privilege = st.nextToken().trim();
                        ResultSet columnResults = null;
                        try {
                            columnResults = this.getColumns(catalog, schemaPattern, table, "%");
                            while (columnResults.next()) {
                                final byte[][] tuple = new byte[8][];
                                tuple[0] = this.s2b(db);
                                tuple[1] = null;
                                tuple[2] = this.s2b(table);
                                if (grantor != null) {
                                    tuple[3] = this.s2b(grantor);
                                }
                                else {
                                    tuple[3] = null;
                                }
                                tuple[4] = this.s2b(fullUser.toString());
                                tuple[5] = this.s2b(privilege);
                                tuple[6] = null;
                                grantRows.add(new ByteArrayRow(tuple, this.getExceptionInterceptor()));
                            }
                        }
                        finally {
                            if (columnResults != null) {
                                try {
                                    columnResults.close();
                                }
                                catch (Exception ex) {}
                            }
                        }
                    }
                }
            }
        }
        finally {
            if (results != null) {
                try {
                    results.close();
                }
                catch (Exception ex2) {}
                results = null;
            }
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (Exception ex3) {}
                stmt = null;
            }
        }
        return this.buildResultSet(fields, grantRows);
    }
    
    public ResultSet getTables(final String catalog, final String schemaPattern, String tableNamePattern, final String[] types) throws SQLException {
        if (tableNamePattern == null) {
            if (!this.conn.getNullNamePatternMatchesAll()) {
                throw SQLError.createSQLException("Table name pattern can not be NULL or empty.", "S1009", this.getExceptionInterceptor());
            }
            tableNamePattern = "%";
        }
        final Field[] fields = { new Field("", "TABLE_CAT", 12, 255), new Field("", "TABLE_SCHEM", 12, 0), new Field("", "TABLE_NAME", 12, 255), new Field("", "TABLE_TYPE", 12, 5), new Field("", "REMARKS", 12, 0) };
        final ArrayList tuples = new ArrayList();
        final Statement stmt = this.conn.getMetadataSafeStatement();
        final List parseList = StringUtils.splitDBdotName(tableNamePattern, "", this.quotedId, this.conn.isNoBackslashEscapesSet());
        String tableNamePat;
        if (parseList.size() == 2) {
            tableNamePat = parseList.get(1);
        }
        else {
            tableNamePat = tableNamePattern;
        }
        final boolean operatingOnInformationSchema = "information_schema".equalsIgnoreCase(catalog);
        try {
            new IterateBlock(this.getCatalogIterator(catalog)) {
                void forEach(final Object catalogStr) throws SQLException {
                    ResultSet results = null;
                    try {
                        Label_0236: {
                            if (!DatabaseMetaData.this.conn.versionMeetsMinimum(5, 0, 2)) {
                                try {
                                    results = stmt.executeQuery("SHOW TABLES FROM " + DatabaseMetaData.this.quotedId + catalogStr.toString() + DatabaseMetaData.this.quotedId + " LIKE '" + tableNamePat + "'");
                                    break Label_0236;
                                }
                                catch (SQLException sqlEx) {
                                    if ("08S01".equals(sqlEx.getSQLState())) {
                                        throw sqlEx;
                                    }
                                    return;
                                }
                            }
                            try {
                                results = stmt.executeQuery("SHOW FULL TABLES FROM " + DatabaseMetaData.this.quotedId + catalogStr.toString() + DatabaseMetaData.this.quotedId + " LIKE '" + tableNamePat + "'");
                            }
                            catch (SQLException sqlEx) {
                                if ("08S01".equals(sqlEx.getSQLState())) {
                                    throw sqlEx;
                                }
                                return;
                            }
                        }
                        boolean shouldReportTables = false;
                        boolean shouldReportViews = false;
                        boolean shouldReportSystemTables = false;
                        if (types == null || types.length == 0) {
                            shouldReportTables = true;
                            shouldReportViews = true;
                            shouldReportSystemTables = true;
                        }
                        else {
                            for (int i = 0; i < types.length; ++i) {
                                if ("TABLE".equalsIgnoreCase(types[i])) {
                                    shouldReportTables = true;
                                }
                                if ("VIEW".equalsIgnoreCase(types[i])) {
                                    shouldReportViews = true;
                                }
                                if ("SYSTEM TABLE".equalsIgnoreCase(types[i])) {
                                    shouldReportSystemTables = true;
                                }
                            }
                        }
                        int typeColumnIndex = 0;
                        boolean hasTableTypes = false;
                        if (DatabaseMetaData.this.conn.versionMeetsMinimum(5, 0, 2)) {
                            try {
                                typeColumnIndex = results.findColumn("table_type");
                                hasTableTypes = true;
                            }
                            catch (SQLException sqlEx2) {
                                try {
                                    typeColumnIndex = results.findColumn("Type");
                                    hasTableTypes = true;
                                }
                                catch (SQLException sqlEx3) {
                                    hasTableTypes = false;
                                }
                            }
                        }
                        TreeMap tablesOrderedByName = null;
                        TreeMap viewsOrderedByName = null;
                        while (results.next()) {
                            final byte[][] row = { (catalogStr.toString() == null) ? null : DatabaseMetaData.this.s2b(catalogStr.toString()), null, results.getBytes(1), null, new byte[0] };
                            if (hasTableTypes) {
                                final String tableType = results.getString(typeColumnIndex);
                                if (("table".equalsIgnoreCase(tableType) || "base table".equalsIgnoreCase(tableType)) && shouldReportTables) {
                                    boolean reportTable = false;
                                    if (!operatingOnInformationSchema && shouldReportTables) {
                                        row[3] = DatabaseMetaData.TABLE_AS_BYTES;
                                        reportTable = true;
                                    }
                                    else if (operatingOnInformationSchema && shouldReportSystemTables) {
                                        row[3] = DatabaseMetaData.SYSTEM_TABLE_AS_BYTES;
                                        reportTable = true;
                                    }
                                    if (!reportTable) {
                                        continue;
                                    }
                                    if (tablesOrderedByName == null) {
                                        tablesOrderedByName = new TreeMap();
                                    }
                                    tablesOrderedByName.put(results.getString(1), row);
                                }
                                else if ("system view".equalsIgnoreCase(tableType) && shouldReportSystemTables) {
                                    row[3] = DatabaseMetaData.SYSTEM_TABLE_AS_BYTES;
                                    if (tablesOrderedByName == null) {
                                        tablesOrderedByName = new TreeMap();
                                    }
                                    tablesOrderedByName.put(results.getString(1), row);
                                }
                                else if ("view".equalsIgnoreCase(tableType) && shouldReportViews) {
                                    row[3] = DatabaseMetaData.VIEW_AS_BYTES;
                                    if (viewsOrderedByName == null) {
                                        viewsOrderedByName = new TreeMap();
                                    }
                                    viewsOrderedByName.put(results.getString(1), row);
                                }
                                else {
                                    if (hasTableTypes) {
                                        continue;
                                    }
                                    row[3] = DatabaseMetaData.TABLE_AS_BYTES;
                                    if (tablesOrderedByName == null) {
                                        tablesOrderedByName = new TreeMap();
                                    }
                                    tablesOrderedByName.put(results.getString(1), row);
                                }
                            }
                            else {
                                if (!shouldReportTables) {
                                    continue;
                                }
                                row[3] = DatabaseMetaData.TABLE_AS_BYTES;
                                if (tablesOrderedByName == null) {
                                    tablesOrderedByName = new TreeMap();
                                }
                                tablesOrderedByName.put(results.getString(1), row);
                            }
                        }
                        if (tablesOrderedByName != null) {
                            final Iterator tablesIter = tablesOrderedByName.values().iterator();
                            while (tablesIter.hasNext()) {
                                tuples.add(new ByteArrayRow(tablesIter.next(), DatabaseMetaData.this.getExceptionInterceptor()));
                            }
                        }
                        if (viewsOrderedByName != null) {
                            final Iterator viewsIter = viewsOrderedByName.values().iterator();
                            while (viewsIter.hasNext()) {
                                tuples.add(new ByteArrayRow(viewsIter.next(), DatabaseMetaData.this.getExceptionInterceptor()));
                            }
                        }
                    }
                    finally {
                        if (results != null) {
                            try {
                                results.close();
                            }
                            catch (Exception ex) {}
                            results = null;
                        }
                    }
                }
            }.doForAll();
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
        }
        final ResultSet tables = this.buildResultSet(fields, tuples);
        return tables;
    }
    
    public ResultSet getTableTypes() throws SQLException {
        final ArrayList tuples = new ArrayList();
        final Field[] fields = { new Field("", "TABLE_TYPE", 12, 5) };
        final byte[][] tableTypeRow = { DatabaseMetaData.TABLE_AS_BYTES };
        tuples.add(new ByteArrayRow(tableTypeRow, this.getExceptionInterceptor()));
        if (this.conn.versionMeetsMinimum(5, 0, 1)) {
            final byte[][] viewTypeRow = { DatabaseMetaData.VIEW_AS_BYTES };
            tuples.add(new ByteArrayRow(viewTypeRow, this.getExceptionInterceptor()));
        }
        final byte[][] tempTypeRow = { this.s2b("LOCAL TEMPORARY") };
        tuples.add(new ByteArrayRow(tempTypeRow, this.getExceptionInterceptor()));
        return this.buildResultSet(fields, tuples);
    }
    
    public String getTimeDateFunctions() throws SQLException {
        return "DAYOFWEEK,WEEKDAY,DAYOFMONTH,DAYOFYEAR,MONTH,DAYNAME,MONTHNAME,QUARTER,WEEK,YEAR,HOUR,MINUTE,SECOND,PERIOD_ADD,PERIOD_DIFF,TO_DAYS,FROM_DAYS,DATE_FORMAT,TIME_FORMAT,CURDATE,CURRENT_DATE,CURTIME,CURRENT_TIME,NOW,SYSDATE,CURRENT_TIMESTAMP,UNIX_TIMESTAMP,FROM_UNIXTIME,SEC_TO_TIME,TIME_TO_SEC";
    }
    
    public ResultSet getTypeInfo() throws SQLException {
        final Field[] fields = { new Field("", "TYPE_NAME", 1, 32), new Field("", "DATA_TYPE", 4, 5), new Field("", "PRECISION", 4, 10), new Field("", "LITERAL_PREFIX", 1, 4), new Field("", "LITERAL_SUFFIX", 1, 4), new Field("", "CREATE_PARAMS", 1, 32), new Field("", "NULLABLE", 5, 5), new Field("", "CASE_SENSITIVE", 16, 3), new Field("", "SEARCHABLE", 5, 3), new Field("", "UNSIGNED_ATTRIBUTE", 16, 3), new Field("", "FIXED_PREC_SCALE", 16, 3), new Field("", "AUTO_INCREMENT", 16, 3), new Field("", "LOCAL_TYPE_NAME", 1, 32), new Field("", "MINIMUM_SCALE", 5, 5), new Field("", "MAXIMUM_SCALE", 5, 5), new Field("", "SQL_DATA_TYPE", 4, 10), new Field("", "SQL_DATETIME_SUB", 4, 10), new Field("", "NUM_PREC_RADIX", 4, 10) };
        byte[][] rowVal = null;
        final ArrayList tuples = new ArrayList();
        rowVal = new byte[][] { this.s2b("BIT"), Integer.toString(-7).getBytes(), this.s2b("1"), this.s2b(""), this.s2b(""), this.s2b(""), Integer.toString(1).getBytes(), this.s2b("true"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("BIT"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("BOOL"), Integer.toString(-7).getBytes(), this.s2b("1"), this.s2b(""), this.s2b(""), this.s2b(""), Integer.toString(1).getBytes(), this.s2b("true"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("BOOL"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("TINYINT"), Integer.toString(-6).getBytes(), this.s2b("3"), this.s2b(""), this.s2b(""), this.s2b("[(M)] [UNSIGNED] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("true"), this.s2b("false"), this.s2b("true"), this.s2b("TINYINT"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("TINYINT UNSIGNED"), Integer.toString(-6).getBytes(), this.s2b("3"), this.s2b(""), this.s2b(""), this.s2b("[(M)] [UNSIGNED] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("true"), this.s2b("false"), this.s2b("true"), this.s2b("TINYINT UNSIGNED"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("BIGINT"), Integer.toString(-5).getBytes(), this.s2b("19"), this.s2b(""), this.s2b(""), this.s2b("[(M)] [UNSIGNED] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("true"), this.s2b("false"), this.s2b("true"), this.s2b("BIGINT"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("BIGINT UNSIGNED"), Integer.toString(-5).getBytes(), this.s2b("20"), this.s2b(""), this.s2b(""), this.s2b("[(M)] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("true"), this.s2b("false"), this.s2b("true"), this.s2b("BIGINT UNSIGNED"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("LONG VARBINARY"), Integer.toString(-4).getBytes(), this.s2b("16777215"), this.s2b("'"), this.s2b("'"), this.s2b(""), Integer.toString(1).getBytes(), this.s2b("true"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("LONG VARBINARY"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("MEDIUMBLOB"), Integer.toString(-4).getBytes(), this.s2b("16777215"), this.s2b("'"), this.s2b("'"), this.s2b(""), Integer.toString(1).getBytes(), this.s2b("true"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("MEDIUMBLOB"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("LONGBLOB"), Integer.toString(-4).getBytes(), Integer.toString(Integer.MAX_VALUE).getBytes(), this.s2b("'"), this.s2b("'"), this.s2b(""), Integer.toString(1).getBytes(), this.s2b("true"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("LONGBLOB"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("BLOB"), Integer.toString(-4).getBytes(), this.s2b("65535"), this.s2b("'"), this.s2b("'"), this.s2b(""), Integer.toString(1).getBytes(), this.s2b("true"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("BLOB"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("TINYBLOB"), Integer.toString(-4).getBytes(), this.s2b("255"), this.s2b("'"), this.s2b("'"), this.s2b(""), Integer.toString(1).getBytes(), this.s2b("true"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("TINYBLOB"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("VARBINARY"), Integer.toString(-3).getBytes(), this.s2b("255"), this.s2b("'"), this.s2b("'"), this.s2b("(M)"), Integer.toString(1).getBytes(), this.s2b("true"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("VARBINARY"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("BINARY"), Integer.toString(-2).getBytes(), this.s2b("255"), this.s2b("'"), this.s2b("'"), this.s2b("(M)"), Integer.toString(1).getBytes(), this.s2b("true"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("BINARY"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("LONG VARCHAR"), Integer.toString(-1).getBytes(), this.s2b("16777215"), this.s2b("'"), this.s2b("'"), this.s2b(""), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("LONG VARCHAR"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("MEDIUMTEXT"), Integer.toString(-1).getBytes(), this.s2b("16777215"), this.s2b("'"), this.s2b("'"), this.s2b(""), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("MEDIUMTEXT"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("LONGTEXT"), Integer.toString(-1).getBytes(), Integer.toString(Integer.MAX_VALUE).getBytes(), this.s2b("'"), this.s2b("'"), this.s2b(""), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("LONGTEXT"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("TEXT"), Integer.toString(-1).getBytes(), this.s2b("65535"), this.s2b("'"), this.s2b("'"), this.s2b(""), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("TEXT"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("TINYTEXT"), Integer.toString(-1).getBytes(), this.s2b("255"), this.s2b("'"), this.s2b("'"), this.s2b(""), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("TINYTEXT"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("CHAR"), Integer.toString(1).getBytes(), this.s2b("255"), this.s2b("'"), this.s2b("'"), this.s2b("(M)"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("CHAR"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        int decimalPrecision = 254;
        if (this.conn.versionMeetsMinimum(5, 0, 3)) {
            if (this.conn.versionMeetsMinimum(5, 0, 6)) {
                decimalPrecision = 65;
            }
            else {
                decimalPrecision = 64;
            }
        }
        rowVal = new byte[][] { this.s2b("NUMERIC"), Integer.toString(2).getBytes(), this.s2b(String.valueOf(decimalPrecision)), this.s2b(""), this.s2b(""), this.s2b("[(M[,D])] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("true"), this.s2b("NUMERIC"), this.s2b("-308"), this.s2b("308"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("DECIMAL"), Integer.toString(3).getBytes(), this.s2b(String.valueOf(decimalPrecision)), this.s2b(""), this.s2b(""), this.s2b("[(M[,D])] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("true"), this.s2b("DECIMAL"), this.s2b("-308"), this.s2b("308"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("INTEGER"), Integer.toString(4).getBytes(), this.s2b("10"), this.s2b(""), this.s2b(""), this.s2b("[(M)] [UNSIGNED] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("true"), this.s2b("false"), this.s2b("true"), this.s2b("INTEGER"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("INTEGER UNSIGNED"), Integer.toString(4).getBytes(), this.s2b("10"), this.s2b(""), this.s2b(""), this.s2b("[(M)] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("true"), this.s2b("false"), this.s2b("true"), this.s2b("INTEGER UNSIGNED"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("INT"), Integer.toString(4).getBytes(), this.s2b("10"), this.s2b(""), this.s2b(""), this.s2b("[(M)] [UNSIGNED] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("true"), this.s2b("false"), this.s2b("true"), this.s2b("INT"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("INT UNSIGNED"), Integer.toString(4).getBytes(), this.s2b("10"), this.s2b(""), this.s2b(""), this.s2b("[(M)] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("true"), this.s2b("false"), this.s2b("true"), this.s2b("INT UNSIGNED"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("MEDIUMINT"), Integer.toString(4).getBytes(), this.s2b("7"), this.s2b(""), this.s2b(""), this.s2b("[(M)] [UNSIGNED] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("true"), this.s2b("false"), this.s2b("true"), this.s2b("MEDIUMINT"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("MEDIUMINT UNSIGNED"), Integer.toString(4).getBytes(), this.s2b("8"), this.s2b(""), this.s2b(""), this.s2b("[(M)] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("true"), this.s2b("false"), this.s2b("true"), this.s2b("MEDIUMINT UNSIGNED"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("SMALLINT"), Integer.toString(5).getBytes(), this.s2b("5"), this.s2b(""), this.s2b(""), this.s2b("[(M)] [UNSIGNED] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("true"), this.s2b("false"), this.s2b("true"), this.s2b("SMALLINT"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("SMALLINT UNSIGNED"), Integer.toString(5).getBytes(), this.s2b("5"), this.s2b(""), this.s2b(""), this.s2b("[(M)] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("true"), this.s2b("false"), this.s2b("true"), this.s2b("SMALLINT UNSIGNED"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("FLOAT"), Integer.toString(7).getBytes(), this.s2b("10"), this.s2b(""), this.s2b(""), this.s2b("[(M,D)] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("true"), this.s2b("FLOAT"), this.s2b("-38"), this.s2b("38"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("DOUBLE"), Integer.toString(8).getBytes(), this.s2b("17"), this.s2b(""), this.s2b(""), this.s2b("[(M,D)] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("true"), this.s2b("DOUBLE"), this.s2b("-308"), this.s2b("308"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("DOUBLE PRECISION"), Integer.toString(8).getBytes(), this.s2b("17"), this.s2b(""), this.s2b(""), this.s2b("[(M,D)] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("true"), this.s2b("DOUBLE PRECISION"), this.s2b("-308"), this.s2b("308"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("REAL"), Integer.toString(8).getBytes(), this.s2b("17"), this.s2b(""), this.s2b(""), this.s2b("[(M,D)] [ZEROFILL]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("true"), this.s2b("REAL"), this.s2b("-308"), this.s2b("308"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("VARCHAR"), Integer.toString(12).getBytes(), this.s2b("255"), this.s2b("'"), this.s2b("'"), this.s2b("(M)"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("VARCHAR"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("ENUM"), Integer.toString(12).getBytes(), this.s2b("65535"), this.s2b("'"), this.s2b("'"), this.s2b(""), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("ENUM"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("SET"), Integer.toString(12).getBytes(), this.s2b("64"), this.s2b("'"), this.s2b("'"), this.s2b(""), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("SET"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("DATE"), Integer.toString(91).getBytes(), this.s2b("0"), this.s2b("'"), this.s2b("'"), this.s2b(""), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("DATE"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("TIME"), Integer.toString(92).getBytes(), this.s2b("0"), this.s2b("'"), this.s2b("'"), this.s2b(""), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("TIME"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("DATETIME"), Integer.toString(93).getBytes(), this.s2b("0"), this.s2b("'"), this.s2b("'"), this.s2b(""), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("DATETIME"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        rowVal = new byte[][] { this.s2b("TIMESTAMP"), Integer.toString(93).getBytes(), this.s2b("0"), this.s2b("'"), this.s2b("'"), this.s2b("[(M)]"), Integer.toString(1).getBytes(), this.s2b("false"), Integer.toString(3).getBytes(), this.s2b("false"), this.s2b("false"), this.s2b("false"), this.s2b("TIMESTAMP"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("0"), this.s2b("10") };
        tuples.add(new ByteArrayRow(rowVal, this.getExceptionInterceptor()));
        return this.buildResultSet(fields, tuples);
    }
    
    public ResultSet getUDTs(final String catalog, final String schemaPattern, final String typeNamePattern, final int[] types) throws SQLException {
        final Field[] fields = { new Field("", "TYPE_CAT", 12, 32), new Field("", "TYPE_SCHEM", 12, 32), new Field("", "TYPE_NAME", 12, 32), new Field("", "CLASS_NAME", 12, 32), new Field("", "DATA_TYPE", 12, 32), new Field("", "REMARKS", 12, 32) };
        final ArrayList tuples = new ArrayList();
        return this.buildResultSet(fields, tuples);
    }
    
    public String getURL() throws SQLException {
        return this.conn.getURL();
    }
    
    public String getUserName() throws SQLException {
        if (this.conn.getUseHostsInPrivileges()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                stmt = this.conn.createStatement();
                stmt.setEscapeProcessing(false);
                rs = stmt.executeQuery("SELECT USER()");
                rs.next();
                return rs.getString(1);
            }
            finally {
                if (rs != null) {
                    try {
                        rs.close();
                    }
                    catch (Exception ex) {
                        AssertionFailedException.shouldNotHappen(ex);
                    }
                    rs = null;
                }
                if (stmt != null) {
                    try {
                        stmt.close();
                    }
                    catch (Exception ex) {
                        AssertionFailedException.shouldNotHappen(ex);
                    }
                    stmt = null;
                }
            }
        }
        return this.conn.getUser();
    }
    
    public ResultSet getVersionColumns(final String catalog, final String schema, final String table) throws SQLException {
        final Field[] fields = { new Field("", "SCOPE", 5, 5), new Field("", "COLUMN_NAME", 1, 32), new Field("", "DATA_TYPE", 4, 5), new Field("", "TYPE_NAME", 1, 16), new Field("", "COLUMN_SIZE", 4, 16), new Field("", "BUFFER_LENGTH", 4, 16), new Field("", "DECIMAL_DIGITS", 5, 16), new Field("", "PSEUDO_COLUMN", 5, 5) };
        return this.buildResultSet(fields, new ArrayList());
    }
    
    public boolean insertsAreDetected(final int type) throws SQLException {
        return false;
    }
    
    public boolean isCatalogAtStart() throws SQLException {
        return true;
    }
    
    public boolean isReadOnly() throws SQLException {
        return false;
    }
    
    public boolean locatorsUpdateCopy() throws SQLException {
        return !this.conn.getEmulateLocators();
    }
    
    public boolean nullPlusNonNullIsNull() throws SQLException {
        return true;
    }
    
    public boolean nullsAreSortedAtEnd() throws SQLException {
        return false;
    }
    
    public boolean nullsAreSortedAtStart() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 0, 2) && !this.conn.versionMeetsMinimum(4, 0, 11);
    }
    
    public boolean nullsAreSortedHigh() throws SQLException {
        return false;
    }
    
    public boolean nullsAreSortedLow() throws SQLException {
        return !this.nullsAreSortedHigh();
    }
    
    public boolean othersDeletesAreVisible(final int type) throws SQLException {
        return false;
    }
    
    public boolean othersInsertsAreVisible(final int type) throws SQLException {
        return false;
    }
    
    public boolean othersUpdatesAreVisible(final int type) throws SQLException {
        return false;
    }
    
    public boolean ownDeletesAreVisible(final int type) throws SQLException {
        return false;
    }
    
    public boolean ownInsertsAreVisible(final int type) throws SQLException {
        return false;
    }
    
    public boolean ownUpdatesAreVisible(final int type) throws SQLException {
        return false;
    }
    
    private LocalAndReferencedColumns parseTableStatusIntoLocalAndReferencedColumns(String keysComment) throws SQLException {
        final String columnsDelimitter = ",";
        final char quoteChar = (this.quotedId.length() == 0) ? '\0' : this.quotedId.charAt(0);
        final int indexOfOpenParenLocalColumns = StringUtils.indexOfIgnoreCaseRespectQuotes(0, keysComment, "(", quoteChar, true);
        if (indexOfOpenParenLocalColumns == -1) {
            throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find start of local columns list.", "S1000", this.getExceptionInterceptor());
        }
        final String constraintName = this.removeQuotedId(keysComment.substring(0, indexOfOpenParenLocalColumns).trim());
        keysComment = keysComment.substring(indexOfOpenParenLocalColumns, keysComment.length());
        final String keysCommentTrimmed = keysComment.trim();
        final int indexOfCloseParenLocalColumns = StringUtils.indexOfIgnoreCaseRespectQuotes(0, keysCommentTrimmed, ")", quoteChar, true);
        if (indexOfCloseParenLocalColumns == -1) {
            throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find end of local columns list.", "S1000", this.getExceptionInterceptor());
        }
        final String localColumnNamesString = keysCommentTrimmed.substring(1, indexOfCloseParenLocalColumns);
        final int indexOfRefer = StringUtils.indexOfIgnoreCaseRespectQuotes(0, keysCommentTrimmed, "REFER ", this.quotedId.charAt(0), true);
        if (indexOfRefer == -1) {
            throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find start of referenced tables list.", "S1000", this.getExceptionInterceptor());
        }
        final int indexOfOpenParenReferCol = StringUtils.indexOfIgnoreCaseRespectQuotes(indexOfRefer, keysCommentTrimmed, "(", quoteChar, false);
        if (indexOfOpenParenReferCol == -1) {
            throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find start of referenced columns list.", "S1000", this.getExceptionInterceptor());
        }
        final String referCatalogTableString = keysCommentTrimmed.substring(indexOfRefer + "REFER ".length(), indexOfOpenParenReferCol);
        final int indexOfSlash = StringUtils.indexOfIgnoreCaseRespectQuotes(0, referCatalogTableString, "/", this.quotedId.charAt(0), false);
        if (indexOfSlash == -1) {
            throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find name of referenced catalog.", "S1000", this.getExceptionInterceptor());
        }
        final String referCatalog = this.removeQuotedId(referCatalogTableString.substring(0, indexOfSlash));
        final String referTable = this.removeQuotedId(referCatalogTableString.substring(indexOfSlash + 1).trim());
        final int indexOfCloseParenRefer = StringUtils.indexOfIgnoreCaseRespectQuotes(indexOfOpenParenReferCol, keysCommentTrimmed, ")", quoteChar, true);
        if (indexOfCloseParenRefer == -1) {
            throw SQLError.createSQLException("Error parsing foreign keys definition, couldn't find end of referenced columns list.", "S1000", this.getExceptionInterceptor());
        }
        final String referColumnNamesString = keysCommentTrimmed.substring(indexOfOpenParenReferCol + 1, indexOfCloseParenRefer);
        final List referColumnsList = StringUtils.split(referColumnNamesString, columnsDelimitter, this.quotedId, this.quotedId, false);
        final List localColumnsList = StringUtils.split(localColumnNamesString, columnsDelimitter, this.quotedId, this.quotedId, false);
        return new LocalAndReferencedColumns(localColumnsList, referColumnsList, constraintName, referCatalog, referTable);
    }
    
    private String removeQuotedId(String s) {
        if (s == null) {
            return null;
        }
        if (this.quotedId.equals("")) {
            return s;
        }
        s = s.trim();
        int frontOffset = 0;
        int backOffset = s.length();
        final int quoteLength = this.quotedId.length();
        if (s.startsWith(this.quotedId)) {
            frontOffset = quoteLength;
        }
        if (s.endsWith(this.quotedId)) {
            backOffset -= quoteLength;
        }
        return s.substring(frontOffset, backOffset);
    }
    
    protected byte[] s2b(final String s) throws SQLException {
        if (s == null) {
            return null;
        }
        return StringUtils.getBytes(s, this.conn.getCharacterSetMetadata(), this.conn.getServerCharacterEncoding(), this.conn.parserKnowsUnicode(), this.conn, this.getExceptionInterceptor());
    }
    
    public boolean storesLowerCaseIdentifiers() throws SQLException {
        return this.conn.storesLowerCaseTableName();
    }
    
    public boolean storesLowerCaseQuotedIdentifiers() throws SQLException {
        return this.conn.storesLowerCaseTableName();
    }
    
    public boolean storesMixedCaseIdentifiers() throws SQLException {
        return !this.conn.storesLowerCaseTableName();
    }
    
    public boolean storesMixedCaseQuotedIdentifiers() throws SQLException {
        return !this.conn.storesLowerCaseTableName();
    }
    
    public boolean storesUpperCaseIdentifiers() throws SQLException {
        return false;
    }
    
    public boolean storesUpperCaseQuotedIdentifiers() throws SQLException {
        return true;
    }
    
    public boolean supportsAlterTableWithAddColumn() throws SQLException {
        return true;
    }
    
    public boolean supportsAlterTableWithDropColumn() throws SQLException {
        return true;
    }
    
    public boolean supportsANSI92EntryLevelSQL() throws SQLException {
        return true;
    }
    
    public boolean supportsANSI92FullSQL() throws SQLException {
        return false;
    }
    
    public boolean supportsANSI92IntermediateSQL() throws SQLException {
        return false;
    }
    
    public boolean supportsBatchUpdates() throws SQLException {
        return true;
    }
    
    public boolean supportsCatalogsInDataManipulation() throws SQLException {
        return this.conn.versionMeetsMinimum(3, 22, 0);
    }
    
    public boolean supportsCatalogsInIndexDefinitions() throws SQLException {
        return this.conn.versionMeetsMinimum(3, 22, 0);
    }
    
    public boolean supportsCatalogsInPrivilegeDefinitions() throws SQLException {
        return this.conn.versionMeetsMinimum(3, 22, 0);
    }
    
    public boolean supportsCatalogsInProcedureCalls() throws SQLException {
        return this.conn.versionMeetsMinimum(3, 22, 0);
    }
    
    public boolean supportsCatalogsInTableDefinitions() throws SQLException {
        return this.conn.versionMeetsMinimum(3, 22, 0);
    }
    
    public boolean supportsColumnAliasing() throws SQLException {
        return true;
    }
    
    public boolean supportsConvert() throws SQLException {
        return false;
    }
    
    public boolean supportsConvert(final int fromType, final int toType) throws SQLException {
        switch (fromType) {
            case -4:
            case -3:
            case -2:
            case -1:
            case 1:
            case 12: {
                switch (toType) {
                    case -6:
                    case -5:
                    case -4:
                    case -3:
                    case -2:
                    case -1:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 12:
                    case 91:
                    case 92:
                    case 93:
                    case 1111: {
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            case -7: {
                return false;
            }
            case -6:
            case -5:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8: {
                switch (toType) {
                    case -6:
                    case -5:
                    case -4:
                    case -3:
                    case -2:
                    case -1:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 12: {
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            case 0: {
                return false;
            }
            case 1111: {
                switch (toType) {
                    case -4:
                    case -3:
                    case -2:
                    case -1:
                    case 1:
                    case 12: {
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            case 91: {
                switch (toType) {
                    case -4:
                    case -3:
                    case -2:
                    case -1:
                    case 1:
                    case 12: {
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            case 92: {
                switch (toType) {
                    case -4:
                    case -3:
                    case -2:
                    case -1:
                    case 1:
                    case 12: {
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            case 93: {
                switch (toType) {
                    case -4:
                    case -3:
                    case -2:
                    case -1:
                    case 1:
                    case 12:
                    case 91:
                    case 92: {
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
                break;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean supportsCoreSQLGrammar() throws SQLException {
        return true;
    }
    
    public boolean supportsCorrelatedSubqueries() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 1, 0);
    }
    
    public boolean supportsDataDefinitionAndDataManipulationTransactions() throws SQLException {
        return false;
    }
    
    public boolean supportsDataManipulationTransactionsOnly() throws SQLException {
        return false;
    }
    
    public boolean supportsDifferentTableCorrelationNames() throws SQLException {
        return true;
    }
    
    public boolean supportsExpressionsInOrderBy() throws SQLException {
        return true;
    }
    
    public boolean supportsExtendedSQLGrammar() throws SQLException {
        return false;
    }
    
    public boolean supportsFullOuterJoins() throws SQLException {
        return false;
    }
    
    public boolean supportsGetGeneratedKeys() {
        return true;
    }
    
    public boolean supportsGroupBy() throws SQLException {
        return true;
    }
    
    public boolean supportsGroupByBeyondSelect() throws SQLException {
        return true;
    }
    
    public boolean supportsGroupByUnrelated() throws SQLException {
        return true;
    }
    
    public boolean supportsIntegrityEnhancementFacility() throws SQLException {
        return this.conn.getOverrideSupportsIntegrityEnhancementFacility();
    }
    
    public boolean supportsLikeEscapeClause() throws SQLException {
        return true;
    }
    
    public boolean supportsLimitedOuterJoins() throws SQLException {
        return true;
    }
    
    public boolean supportsMinimumSQLGrammar() throws SQLException {
        return true;
    }
    
    public boolean supportsMixedCaseIdentifiers() throws SQLException {
        return !this.conn.lowerCaseTableNames();
    }
    
    public boolean supportsMixedCaseQuotedIdentifiers() throws SQLException {
        return !this.conn.lowerCaseTableNames();
    }
    
    public boolean supportsMultipleOpenResults() throws SQLException {
        return true;
    }
    
    public boolean supportsMultipleResultSets() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 1, 0);
    }
    
    public boolean supportsMultipleTransactions() throws SQLException {
        return true;
    }
    
    public boolean supportsNamedParameters() throws SQLException {
        return false;
    }
    
    public boolean supportsNonNullableColumns() throws SQLException {
        return true;
    }
    
    public boolean supportsOpenCursorsAcrossCommit() throws SQLException {
        return false;
    }
    
    public boolean supportsOpenCursorsAcrossRollback() throws SQLException {
        return false;
    }
    
    public boolean supportsOpenStatementsAcrossCommit() throws SQLException {
        return false;
    }
    
    public boolean supportsOpenStatementsAcrossRollback() throws SQLException {
        return false;
    }
    
    public boolean supportsOrderByUnrelated() throws SQLException {
        return false;
    }
    
    public boolean supportsOuterJoins() throws SQLException {
        return true;
    }
    
    public boolean supportsPositionedDelete() throws SQLException {
        return false;
    }
    
    public boolean supportsPositionedUpdate() throws SQLException {
        return false;
    }
    
    public boolean supportsResultSetConcurrency(final int type, final int concurrency) throws SQLException {
        switch (type) {
            case 1004: {
                if (concurrency == 1007 || concurrency == 1008) {
                    return true;
                }
                throw SQLError.createSQLException("Illegal arguments to supportsResultSetConcurrency()", "S1009", this.getExceptionInterceptor());
            }
            case 1003: {
                if (concurrency == 1007 || concurrency == 1008) {
                    return true;
                }
                throw SQLError.createSQLException("Illegal arguments to supportsResultSetConcurrency()", "S1009", this.getExceptionInterceptor());
            }
            case 1005: {
                return false;
            }
            default: {
                throw SQLError.createSQLException("Illegal arguments to supportsResultSetConcurrency()", "S1009", this.getExceptionInterceptor());
            }
        }
    }
    
    public boolean supportsResultSetHoldability(final int holdability) throws SQLException {
        return holdability == 1;
    }
    
    public boolean supportsResultSetType(final int type) throws SQLException {
        return type == 1004;
    }
    
    public boolean supportsSavepoints() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 0, 14) || this.conn.versionMeetsMinimum(4, 1, 1);
    }
    
    public boolean supportsSchemasInDataManipulation() throws SQLException {
        return false;
    }
    
    public boolean supportsSchemasInIndexDefinitions() throws SQLException {
        return false;
    }
    
    public boolean supportsSchemasInPrivilegeDefinitions() throws SQLException {
        return false;
    }
    
    public boolean supportsSchemasInProcedureCalls() throws SQLException {
        return false;
    }
    
    public boolean supportsSchemasInTableDefinitions() throws SQLException {
        return false;
    }
    
    public boolean supportsSelectForUpdate() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 0, 0);
    }
    
    public boolean supportsStatementPooling() throws SQLException {
        return false;
    }
    
    public boolean supportsStoredProcedures() throws SQLException {
        return this.conn.versionMeetsMinimum(5, 0, 0);
    }
    
    public boolean supportsSubqueriesInComparisons() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 1, 0);
    }
    
    public boolean supportsSubqueriesInExists() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 1, 0);
    }
    
    public boolean supportsSubqueriesInIns() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 1, 0);
    }
    
    public boolean supportsSubqueriesInQuantifieds() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 1, 0);
    }
    
    public boolean supportsTableCorrelationNames() throws SQLException {
        return true;
    }
    
    public boolean supportsTransactionIsolationLevel(final int level) throws SQLException {
        if (!this.conn.supportsIsolationLevel()) {
            return false;
        }
        switch (level) {
            case 1:
            case 2:
            case 4:
            case 8: {
                return true;
            }
            default: {
                return false;
            }
        }
    }
    
    public boolean supportsTransactions() throws SQLException {
        return this.conn.supportsTransactions();
    }
    
    public boolean supportsUnion() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 0, 0);
    }
    
    public boolean supportsUnionAll() throws SQLException {
        return this.conn.versionMeetsMinimum(4, 0, 0);
    }
    
    public boolean updatesAreDetected(final int type) throws SQLException {
        return false;
    }
    
    public boolean usesLocalFilePerTable() throws SQLException {
        return false;
    }
    
    public boolean usesLocalFiles() throws SQLException {
        return false;
    }
    
    public ResultSet getFunctionColumns(final String catalog, final String schemaPattern, final String functionNamePattern, final String columnNamePattern) throws SQLException {
        final Field[] fields = this.createFunctionColumnsFields();
        return this.getProcedureOrFunctionColumns(fields, catalog, schemaPattern, functionNamePattern, columnNamePattern, false, true);
    }
    
    protected Field[] createFunctionColumnsFields() {
        final Field[] fields = { new Field("", "FUNCTION_CAT", 12, 512), new Field("", "FUNCTION_SCHEM", 12, 512), new Field("", "FUNCTION_NAME", 12, 512), new Field("", "COLUMN_NAME", 12, 512), new Field("", "COLUMN_TYPE", 12, 64), new Field("", "DATA_TYPE", 5, 6), new Field("", "TYPE_NAME", 12, 64), new Field("", "PRECISION", 4, 12), new Field("", "LENGTH", 4, 12), new Field("", "SCALE", 5, 12), new Field("", "RADIX", 5, 6), new Field("", "NULLABLE", 5, 6), new Field("", "REMARKS", 12, 512), new Field("", "CHAR_OCTET_LENGTH", 4, 32), new Field("", "ORDINAL_POSITION", 4, 32), new Field("", "IS_NULLABLE", 12, 12), new Field("", "SPECIFIC_NAME", 12, 64) };
        return fields;
    }
    
    public boolean providesQueryObjectGenerator() throws SQLException {
        return false;
    }
    
    public ResultSet getSchemas(final String catalog, final String schemaPattern) throws SQLException {
        final Field[] fields = { new Field("", "TABLE_SCHEM", 12, 255), new Field("", "TABLE_CATALOG", 12, 255) };
        return this.buildResultSet(fields, new ArrayList());
    }
    
    public boolean supportsStoredFunctionsUsingCallSyntax() throws SQLException {
        return true;
    }
    
    protected PreparedStatement prepareMetaDataSafeStatement(final String sql) throws SQLException {
        final PreparedStatement pStmt = this.conn.clientPrepareStatement(sql);
        if (pStmt.getMaxRows() != 0) {
            pStmt.setMaxRows(0);
        }
        ((com.mysql.jdbc.Statement)pStmt).setHoldResultsOpenOverClose(true);
        return pStmt;
    }
    
    static {
        TABLE_AS_BYTES = "TABLE".getBytes();
        SYSTEM_TABLE_AS_BYTES = "SYSTEM TABLE".getBytes();
        VIEW_AS_BYTES = "VIEW".getBytes();
        Label_0126: {
            if (Util.isJdbc4()) {
                try {
                    JDBC_4_DBMD_SHOW_CTOR = Class.forName("com.mysql.jdbc.JDBC4DatabaseMetaData").getConstructor(MySQLConnection.class, String.class);
                    JDBC_4_DBMD_IS_CTOR = Class.forName("com.mysql.jdbc.JDBC4DatabaseMetaDataUsingInfoSchema").getConstructor(MySQLConnection.class, String.class);
                    break Label_0126;
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
            JDBC_4_DBMD_IS_CTOR = null;
            JDBC_4_DBMD_SHOW_CTOR = null;
        }
        final String[] allMySQLKeywords = { "ACCESSIBLE", "ADD", "ALL", "ALTER", "ANALYZE", "AND", "AS", "ASC", "ASENSITIVE", "BEFORE", "BETWEEN", "BIGINT", "BINARY", "BLOB", "BOTH", "BY", "CALL", "CASCADE", "CASE", "CHANGE", "CHAR", "CHARACTER", "CHECK", "COLLATE", "COLUMN", "CONDITION", "CONNECTION", "CONSTRAINT", "CONTINUE", "CONVERT", "CREATE", "CROSS", "CURRENT_DATE", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", "CURSOR", "DATABASE", "DATABASES", "DAY_HOUR", "DAY_MICROSECOND", "DAY_MINUTE", "DAY_SECOND", "DEC", "DECIMAL", "DECLARE", "DEFAULT", "DELAYED", "DELETE", "DESC", "DESCRIBE", "DETERMINISTIC", "DISTINCT", "DISTINCTROW", "DIV", "DOUBLE", "DROP", "DUAL", "EACH", "ELSE", "ELSEIF", "ENCLOSED", "ESCAPED", "EXISTS", "EXIT", "EXPLAIN", "FALSE", "FETCH", "FLOAT", "FLOAT4", "FLOAT8", "FOR", "FORCE", "FOREIGN", "FROM", "FULLTEXT", "GRANT", "GROUP", "HAVING", "HIGH_PRIORITY", "HOUR_MICROSECOND", "HOUR_MINUTE", "HOUR_SECOND", "IF", "IGNORE", "IN", "INDEX", "INFILE", "INNER", "INOUT", "INSENSITIVE", "INSERT", "INT", "INT1", "INT2", "INT3", "INT4", "INT8", "INTEGER", "INTERVAL", "INTO", "IS", "ITERATE", "JOIN", "KEY", "KEYS", "KILL", "LEADING", "LEAVE", "LEFT", "LIKE", "LIMIT", "LINEAR", "LINES", "LOAD", "LOCALTIME", "LOCALTIMESTAMP", "LOCK", "LONG", "LONGBLOB", "LONGTEXT", "LOOP", "LOW_PRIORITY", "MATCH", "MEDIUMBLOB", "MEDIUMINT", "MEDIUMTEXT", "MIDDLEINT", "MINUTE_MICROSECOND", "MINUTE_SECOND", "MOD", "MODIFIES", "NATURAL", "NOT", "NO_WRITE_TO_BINLOG", "NULL", "NUMERIC", "ON", "OPTIMIZE", "OPTION", "OPTIONALLY", "OR", "ORDER", "OUT", "OUTER", "OUTFILE", "PRECISION", "PRIMARY", "PROCEDURE", "PURGE", "RANGE", "READ", "READS", "READ_ONLY", "READ_WRITE", "REAL", "REFERENCES", "REGEXP", "RELEASE", "RENAME", "REPEAT", "REPLACE", "REQUIRE", "RESTRICT", "RETURN", "REVOKE", "RIGHT", "RLIKE", "SCHEMA", "SCHEMAS", "SECOND_MICROSECOND", "SELECT", "SENSITIVE", "SEPARATOR", "SET", "SHOW", "SMALLINT", "SPATIAL", "SPECIFIC", "SQL", "SQLEXCEPTION", "SQLSTATE", "SQLWARNING", "SQL_BIG_RESULT", "SQL_CALC_FOUND_ROWS", "SQL_SMALL_RESULT", "SSL", "STARTING", "STRAIGHT_JOIN", "TABLE", "TERMINATED", "THEN", "TINYBLOB", "TINYINT", "TINYTEXT", "TO", "TRAILING", "TRIGGER", "TRUE", "UNDO", "UNION", "UNIQUE", "UNLOCK", "UNSIGNED", "UPDATE", "USAGE", "USE", "USING", "UTC_DATE", "UTC_TIME", "UTC_TIMESTAMP", "VALUES", "VARBINARY", "VARCHAR", "VARCHARACTER", "VARYING", "WHEN", "WHERE", "WHILE", "WITH", "WRITE", "X509", "XOR", "YEAR_MONTH", "ZEROFILL" };
        final String[] sql92Keywords = { "ABSOLUTE", "EXEC", "OVERLAPS", "ACTION", "EXECUTE", "PAD", "ADA", "EXISTS", "PARTIAL", "ADD", "EXTERNAL", "PASCAL", "ALL", "EXTRACT", "POSITION", "ALLOCATE", "FALSE", "PRECISION", "ALTER", "FETCH", "PREPARE", "AND", "FIRST", "PRESERVE", "ANY", "FLOAT", "PRIMARY", "ARE", "FOR", "PRIOR", "AS", "FOREIGN", "PRIVILEGES", "ASC", "FORTRAN", "PROCEDURE", "ASSERTION", "FOUND", "PUBLIC", "AT", "FROM", "READ", "AUTHORIZATION", "FULL", "REAL", "AVG", "GET", "REFERENCES", "BEGIN", "GLOBAL", "RELATIVE", "BETWEEN", "GO", "RESTRICT", "BIT", "GOTO", "REVOKE", "BIT_LENGTH", "GRANT", "RIGHT", "BOTH", "GROUP", "ROLLBACK", "BY", "HAVING", "ROWS", "CASCADE", "HOUR", "SCHEMA", "CASCADED", "IDENTITY", "SCROLL", "CASE", "IMMEDIATE", "SECOND", "CAST", "IN", "SECTION", "CATALOG", "INCLUDE", "SELECT", "CHAR", "INDEX", "SESSION", "CHAR_LENGTH", "INDICATOR", "SESSION_USER", "CHARACTER", "INITIALLY", "SET", "CHARACTER_LENGTH", "INNER", "SIZE", "CHECK", "INPUT", "SMALLINT", "CLOSE", "INSENSITIVE", "SOME", "COALESCE", "INSERT", "SPACE", "COLLATE", "INT", "SQL", "COLLATION", "INTEGER", "SQLCA", "COLUMN", "INTERSECT", "SQLCODE", "COMMIT", "INTERVAL", "SQLERROR", "CONNECT", "INTO", "SQLSTATE", "CONNECTION", "IS", "SQLWARNING", "CONSTRAINT", "ISOLATION", "SUBSTRING", "CONSTRAINTS", "JOIN", "SUM", "CONTINUE", "KEY", "SYSTEM_USER", "CONVERT", "LANGUAGE", "TABLE", "CORRESPONDING", "LAST", "TEMPORARY", "COUNT", "LEADING", "THEN", "CREATE", "LEFT", "TIME", "CROSS", "LEVEL", "TIMESTAMP", "CURRENT", "LIKE", "TIMEZONE_HOUR", "CURRENT_DATE", "LOCAL", "TIMEZONE_MINUTE", "CURRENT_TIME", "LOWER", "TO", "CURRENT_TIMESTAMP", "MATCH", "TRAILING", "CURRENT_USER", "MAX", "TRANSACTION", "CURSOR", "MIN", "TRANSLATE", "DATE", "MINUTE", "TRANSLATION", "DAY", "MODULE", "TRIM", "DEALLOCATE", "MONTH", "TRUE", "DEC", "NAMES", "UNION", "DECIMAL", "NATIONAL", "UNIQUE", "DECLARE", "NATURAL", "UNKNOWN", "DEFAULT", "NCHAR", "UPDATE", "DEFERRABLE", "NEXT", "UPPER", "DEFERRED", "NO", "USAGE", "DELETE", "NONE", "USER", "DESC", "NOT", "USING", "DESCRIBE", "NULL", "VALUE", "DESCRIPTOR", "NULLIF", "VALUES", "DIAGNOSTICS", "NUMERIC", "VARCHAR", "DISCONNECT", "OCTET_LENGTH", "VARYING", "DISTINCT", "OF", "VIEW", "DOMAIN", "ON", "WHEN", "DOUBLE", "ONLY", "WHENEVER", "DROP", "OPEN", "WHERE", "ELSE", "OPTION", "WITH", "END", "OR", "WORK", "END-EXEC", "ORDER", "WRITE", "ESCAPE", "OUTER", "YEAR", "EXCEPT", "OUTPUT", "ZONE", "EXCEPTION" };
        final TreeMap mySQLKeywordMap = new TreeMap();
        for (int i = 0; i < allMySQLKeywords.length; ++i) {
            mySQLKeywordMap.put(allMySQLKeywords[i], null);
        }
        final HashMap sql92KeywordMap = new HashMap(sql92Keywords.length);
        for (int j = 0; j < sql92Keywords.length; ++j) {
            sql92KeywordMap.put(sql92Keywords[j], null);
        }
        Iterator it = sql92KeywordMap.keySet().iterator();
        while (it.hasNext()) {
            mySQLKeywordMap.remove(it.next());
        }
        final StringBuffer keywordBuf = new StringBuffer();
        it = mySQLKeywordMap.keySet().iterator();
        if (it.hasNext()) {
            keywordBuf.append(it.next().toString());
        }
        while (it.hasNext()) {
            keywordBuf.append(",");
            keywordBuf.append(it.next().toString());
        }
        DatabaseMetaData.mysqlKeywordsThatArentSQL92 = keywordBuf.toString();
    }
    
    protected abstract class IteratorWithCleanup
    {
        abstract void close() throws SQLException;
        
        abstract boolean hasNext() throws SQLException;
        
        abstract Object next() throws SQLException;
    }
    
    class LocalAndReferencedColumns
    {
        String constraintName;
        List localColumnsList;
        String referencedCatalog;
        List referencedColumnsList;
        String referencedTable;
        
        LocalAndReferencedColumns(final List localColumns, final List refColumns, final String constName, final String refCatalog, final String refTable) {
            this.localColumnsList = localColumns;
            this.referencedColumnsList = refColumns;
            this.constraintName = constName;
            this.referencedTable = refTable;
            this.referencedCatalog = refCatalog;
        }
    }
    
    protected class ResultSetIterator extends IteratorWithCleanup
    {
        int colIndex;
        ResultSet resultSet;
        
        ResultSetIterator(final ResultSet rs, final int index) {
            this.resultSet = rs;
            this.colIndex = index;
        }
        
        void close() throws SQLException {
            this.resultSet.close();
        }
        
        boolean hasNext() throws SQLException {
            return this.resultSet.next();
        }
        
        Object next() throws SQLException {
            return this.resultSet.getObject(this.colIndex);
        }
    }
    
    protected class SingleStringIterator extends IteratorWithCleanup
    {
        boolean onFirst;
        String value;
        
        SingleStringIterator(final String s) {
            this.onFirst = true;
            this.value = s;
        }
        
        void close() throws SQLException {
        }
        
        boolean hasNext() throws SQLException {
            return this.onFirst;
        }
        
        Object next() throws SQLException {
            this.onFirst = false;
            return this.value;
        }
    }
    
    class TypeDescriptor
    {
        int bufferLength;
        int charOctetLength;
        Integer columnSize;
        short dataType;
        Integer decimalDigits;
        String isNullable;
        int nullability;
        int numPrecRadix;
        String typeName;
        
        TypeDescriptor(final String typeInfo, final String nullabilityInfo) throws SQLException {
            this.numPrecRadix = 10;
            if (typeInfo == null) {
                throw SQLError.createSQLException("NULL typeinfo not supported.", "S1009", DatabaseMetaData.this.getExceptionInterceptor());
            }
            String mysqlType = "";
            String fullMysqlType = null;
            if (typeInfo.indexOf("(") != -1) {
                mysqlType = typeInfo.substring(0, typeInfo.indexOf("("));
            }
            else {
                mysqlType = typeInfo;
            }
            final int indexOfUnsignedInMysqlType = StringUtils.indexOfIgnoreCase(mysqlType, "unsigned");
            if (indexOfUnsignedInMysqlType != -1) {
                mysqlType = mysqlType.substring(0, indexOfUnsignedInMysqlType - 1);
            }
            boolean isUnsigned = false;
            if (StringUtils.indexOfIgnoreCase(typeInfo, "unsigned") != -1) {
                fullMysqlType = mysqlType + " unsigned";
                isUnsigned = true;
            }
            else {
                fullMysqlType = mysqlType;
            }
            if (DatabaseMetaData.this.conn.getCapitalizeTypeNames()) {
                fullMysqlType = fullMysqlType.toUpperCase(Locale.ENGLISH);
            }
            this.dataType = (short)MysqlDefs.mysqlToJavaType(mysqlType);
            this.typeName = fullMysqlType;
            if (StringUtils.startsWithIgnoreCase(typeInfo, "enum")) {
                final String temp = typeInfo.substring(typeInfo.indexOf("("), typeInfo.lastIndexOf(")"));
                final StringTokenizer tokenizer = new StringTokenizer(temp, ",");
                int maxLength = 0;
                while (tokenizer.hasMoreTokens()) {
                    maxLength = Math.max(maxLength, tokenizer.nextToken().length() - 2);
                }
                this.columnSize = Constants.integerValueOf(maxLength);
                this.decimalDigits = null;
            }
            else if (StringUtils.startsWithIgnoreCase(typeInfo, "set")) {
                final String temp = typeInfo.substring(typeInfo.indexOf("(") + 1, typeInfo.lastIndexOf(")"));
                final StringTokenizer tokenizer = new StringTokenizer(temp, ",");
                int maxLength = 0;
                final int numElements = tokenizer.countTokens();
                if (numElements > 0) {
                    maxLength += numElements - 1;
                }
                while (tokenizer.hasMoreTokens()) {
                    final String setMember = tokenizer.nextToken().trim();
                    if (setMember.startsWith("'") && setMember.endsWith("'")) {
                        maxLength += setMember.length() - 2;
                    }
                    else {
                        maxLength += setMember.length();
                    }
                }
                this.columnSize = Constants.integerValueOf(maxLength);
                this.decimalDigits = null;
            }
            else if (typeInfo.indexOf(",") != -1) {
                this.columnSize = Integer.valueOf(typeInfo.substring(typeInfo.indexOf("(") + 1, typeInfo.indexOf(",")).trim());
                this.decimalDigits = Integer.valueOf(typeInfo.substring(typeInfo.indexOf(",") + 1, typeInfo.indexOf(")")).trim());
            }
            else {
                this.columnSize = null;
                this.decimalDigits = null;
                if ((StringUtils.indexOfIgnoreCase(typeInfo, "char") != -1 || StringUtils.indexOfIgnoreCase(typeInfo, "text") != -1 || StringUtils.indexOfIgnoreCase(typeInfo, "blob") != -1 || StringUtils.indexOfIgnoreCase(typeInfo, "binary") != -1 || StringUtils.indexOfIgnoreCase(typeInfo, "bit") != -1) && typeInfo.indexOf("(") != -1) {
                    int endParenIndex = typeInfo.indexOf(")");
                    if (endParenIndex == -1) {
                        endParenIndex = typeInfo.length();
                    }
                    this.columnSize = Integer.valueOf(typeInfo.substring(typeInfo.indexOf("(") + 1, endParenIndex).trim());
                    if (DatabaseMetaData.this.conn.getTinyInt1isBit() && this.columnSize == 1 && StringUtils.startsWithIgnoreCase(typeInfo, 0, "tinyint")) {
                        if (DatabaseMetaData.this.conn.getTransformedBitIsBoolean()) {
                            this.dataType = 16;
                            this.typeName = "BOOLEAN";
                        }
                        else {
                            this.dataType = -7;
                            this.typeName = "BIT";
                        }
                    }
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "tinyint")) {
                    if (DatabaseMetaData.this.conn.getTinyInt1isBit() && typeInfo.indexOf("(1)") != -1) {
                        if (DatabaseMetaData.this.conn.getTransformedBitIsBoolean()) {
                            this.dataType = 16;
                            this.typeName = "BOOLEAN";
                        }
                        else {
                            this.dataType = -7;
                            this.typeName = "BIT";
                        }
                    }
                    else {
                        this.columnSize = Constants.integerValueOf(3);
                        this.decimalDigits = Constants.integerValueOf(0);
                    }
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "smallint")) {
                    this.columnSize = Constants.integerValueOf(5);
                    this.decimalDigits = Constants.integerValueOf(0);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "mediumint")) {
                    this.columnSize = Constants.integerValueOf(isUnsigned ? 8 : 7);
                    this.decimalDigits = Constants.integerValueOf(0);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "int")) {
                    this.columnSize = Constants.integerValueOf(10);
                    this.decimalDigits = Constants.integerValueOf(0);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "integer")) {
                    this.columnSize = Constants.integerValueOf(10);
                    this.decimalDigits = Constants.integerValueOf(0);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "bigint")) {
                    this.columnSize = Constants.integerValueOf(isUnsigned ? 20 : 19);
                    this.decimalDigits = Constants.integerValueOf(0);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "int24")) {
                    this.columnSize = Constants.integerValueOf(19);
                    this.decimalDigits = Constants.integerValueOf(0);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "real")) {
                    this.columnSize = Constants.integerValueOf(12);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "float")) {
                    this.columnSize = Constants.integerValueOf(12);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "decimal")) {
                    this.columnSize = Constants.integerValueOf(12);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "numeric")) {
                    this.columnSize = Constants.integerValueOf(12);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "double")) {
                    this.columnSize = Constants.integerValueOf(22);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "char")) {
                    this.columnSize = Constants.integerValueOf(1);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "varchar")) {
                    this.columnSize = Constants.integerValueOf(255);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "timestamp")) {
                    this.columnSize = Constants.integerValueOf(19);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "datetime")) {
                    this.columnSize = Constants.integerValueOf(19);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "date")) {
                    this.columnSize = Constants.integerValueOf(10);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "time")) {
                    this.columnSize = Constants.integerValueOf(8);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "tinyblob")) {
                    this.columnSize = Constants.integerValueOf(255);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "blob")) {
                    this.columnSize = Constants.integerValueOf(65535);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "mediumblob")) {
                    this.columnSize = Constants.integerValueOf(16777215);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "longblob")) {
                    this.columnSize = Constants.integerValueOf(Integer.MAX_VALUE);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "tinytext")) {
                    this.columnSize = Constants.integerValueOf(255);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "text")) {
                    this.columnSize = Constants.integerValueOf(65535);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "mediumtext")) {
                    this.columnSize = Constants.integerValueOf(16777215);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "longtext")) {
                    this.columnSize = Constants.integerValueOf(Integer.MAX_VALUE);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "enum")) {
                    this.columnSize = Constants.integerValueOf(255);
                }
                else if (StringUtils.startsWithIgnoreCaseAndWs(typeInfo, "set")) {
                    this.columnSize = Constants.integerValueOf(255);
                }
            }
            this.bufferLength = MysqlIO.getMaxBuf();
            this.numPrecRadix = 10;
            if (nullabilityInfo != null) {
                if (nullabilityInfo.equals("YES")) {
                    this.nullability = 1;
                    this.isNullable = "YES";
                }
                else {
                    this.nullability = 0;
                    this.isNullable = "NO";
                }
            }
            else {
                this.nullability = 0;
                this.isNullable = "NO";
            }
        }
    }
}
