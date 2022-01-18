// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Date;
import java.sql.Clob;
import java.io.Reader;
import java.sql.Blob;
import java.math.BigDecimal;
import java.io.InputStream;
import com.mysql.jdbc.profiler.ProfilerEvent;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;
import java.util.Iterator;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.sql.SQLException;
import java.util.Map;
import java.util.List;

public class UpdatableResultSet extends ResultSetImpl
{
    protected static final byte[] STREAM_DATA_MARKER;
    protected SingleByteCharsetConverter charConverter;
    private String charEncoding;
    private byte[][] defaultColumnValue;
    private PreparedStatement deleter;
    private String deleteSQL;
    private boolean initializedCharConverter;
    protected PreparedStatement inserter;
    private String insertSQL;
    private boolean isUpdatable;
    private String notUpdatableReason;
    private List primaryKeyIndicies;
    private String qualifiedAndQuotedTableName;
    private String quotedIdChar;
    private PreparedStatement refresher;
    private String refreshSQL;
    private ResultSetRow savedCurrentRow;
    protected PreparedStatement updater;
    private String updateSQL;
    private boolean populateInserterWithDefaultValues;
    private Map databasesUsedToTablesUsed;
    
    protected UpdatableResultSet(final String catalog, final Field[] fields, final RowData tuples, final MySQLConnection conn, final StatementImpl creatorStmt) throws SQLException {
        super(catalog, fields, tuples, conn, creatorStmt);
        this.deleter = null;
        this.deleteSQL = null;
        this.initializedCharConverter = false;
        this.inserter = null;
        this.insertSQL = null;
        this.isUpdatable = false;
        this.notUpdatableReason = null;
        this.primaryKeyIndicies = null;
        this.quotedIdChar = null;
        this.refreshSQL = null;
        this.updater = null;
        this.updateSQL = null;
        this.populateInserterWithDefaultValues = false;
        this.databasesUsedToTablesUsed = null;
        this.checkUpdatability();
        this.populateInserterWithDefaultValues = this.connection.getPopulateInsertRowWithDefaultValues();
    }
    
    public synchronized boolean absolute(final int row) throws SQLException {
        return super.absolute(row);
    }
    
    public synchronized void afterLast() throws SQLException {
        super.afterLast();
    }
    
    public synchronized void beforeFirst() throws SQLException {
        super.beforeFirst();
    }
    
    public synchronized void cancelRowUpdates() throws SQLException {
        this.checkClosed();
        if (this.doingUpdates) {
            this.doingUpdates = false;
            this.updater.clearParameters();
        }
    }
    
    protected void checkRowPos() throws SQLException {
        this.checkClosed();
        if (!this.onInsertRow) {
            super.checkRowPos();
        }
    }
    
    protected void checkUpdatability() throws SQLException {
        try {
            if (this.fields == null) {
                return;
            }
            String singleTableName = null;
            String catalogName = null;
            int primaryKeyCount = 0;
            if (this.catalog == null || this.catalog.length() == 0) {
                this.catalog = this.fields[0].getDatabaseName();
                if (this.catalog == null || this.catalog.length() == 0) {
                    throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.43"), "S1009", this.getExceptionInterceptor());
                }
            }
            if (this.fields.length <= 0) {
                this.isUpdatable = false;
                this.notUpdatableReason = Messages.getString("NotUpdatableReason.3");
                return;
            }
            singleTableName = this.fields[0].getOriginalTableName();
            catalogName = this.fields[0].getDatabaseName();
            if (singleTableName == null) {
                singleTableName = this.fields[0].getTableName();
                catalogName = this.catalog;
            }
            if (singleTableName != null && singleTableName.length() == 0) {
                this.isUpdatable = false;
                this.notUpdatableReason = Messages.getString("NotUpdatableReason.3");
                return;
            }
            if (this.fields[0].isPrimaryKey()) {
                ++primaryKeyCount;
            }
            for (int i = 1; i < this.fields.length; ++i) {
                String otherTableName = this.fields[i].getOriginalTableName();
                String otherCatalogName = this.fields[i].getDatabaseName();
                if (otherTableName == null) {
                    otherTableName = this.fields[i].getTableName();
                    otherCatalogName = this.catalog;
                }
                if (otherTableName != null && otherTableName.length() == 0) {
                    this.isUpdatable = false;
                    this.notUpdatableReason = Messages.getString("NotUpdatableReason.3");
                    return;
                }
                if (singleTableName == null || !otherTableName.equals(singleTableName)) {
                    this.isUpdatable = false;
                    this.notUpdatableReason = Messages.getString("NotUpdatableReason.0");
                    return;
                }
                if (catalogName == null || !otherCatalogName.equals(catalogName)) {
                    this.isUpdatable = false;
                    this.notUpdatableReason = Messages.getString("NotUpdatableReason.1");
                    return;
                }
                if (this.fields[i].isPrimaryKey()) {
                    ++primaryKeyCount;
                }
            }
            if (singleTableName == null || singleTableName.length() == 0) {
                this.isUpdatable = false;
                this.notUpdatableReason = Messages.getString("NotUpdatableReason.2");
                return;
            }
            if (this.connection.getStrictUpdates()) {
                final DatabaseMetaData dbmd = this.connection.getMetaData();
                ResultSet rs = null;
                final HashMap primaryKeyNames = new HashMap();
                try {
                    rs = dbmd.getPrimaryKeys(catalogName, null, singleTableName);
                    while (rs.next()) {
                        String keyName = rs.getString(4);
                        keyName = keyName.toUpperCase();
                        primaryKeyNames.put(keyName, keyName);
                    }
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
                }
                final int existingPrimaryKeysCount = primaryKeyNames.size();
                if (existingPrimaryKeysCount == 0) {
                    this.isUpdatable = false;
                    this.notUpdatableReason = Messages.getString("NotUpdatableReason.5");
                    return;
                }
                for (int j = 0; j < this.fields.length; ++j) {
                    if (this.fields[j].isPrimaryKey()) {
                        final String columnNameUC = this.fields[j].getName().toUpperCase();
                        if (primaryKeyNames.remove(columnNameUC) == null) {
                            final String originalName = this.fields[j].getOriginalName();
                            if (originalName != null && primaryKeyNames.remove(originalName.toUpperCase()) == null) {
                                this.isUpdatable = false;
                                this.notUpdatableReason = Messages.getString("NotUpdatableReason.6", new Object[] { originalName });
                                return;
                            }
                        }
                    }
                }
                if (!(this.isUpdatable = primaryKeyNames.isEmpty())) {
                    if (existingPrimaryKeysCount > 1) {
                        this.notUpdatableReason = Messages.getString("NotUpdatableReason.7");
                    }
                    else {
                        this.notUpdatableReason = Messages.getString("NotUpdatableReason.4");
                    }
                    return;
                }
            }
            if (primaryKeyCount == 0) {
                this.isUpdatable = false;
                this.notUpdatableReason = Messages.getString("NotUpdatableReason.4");
                return;
            }
            this.isUpdatable = true;
            this.notUpdatableReason = null;
        }
        catch (SQLException sqlEx) {
            this.isUpdatable = false;
            this.notUpdatableReason = sqlEx.getMessage();
        }
    }
    
    public synchronized void deleteRow() throws SQLException {
        this.checkClosed();
        if (!this.isUpdatable) {
            throw new NotUpdatable(this.notUpdatableReason);
        }
        if (this.onInsertRow) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.1"), this.getExceptionInterceptor());
        }
        if (this.rowData.size() == 0) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.2"), this.getExceptionInterceptor());
        }
        if (this.isBeforeFirst()) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.3"), this.getExceptionInterceptor());
        }
        if (this.isAfterLast()) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.4"), this.getExceptionInterceptor());
        }
        if (this.deleter == null) {
            if (this.deleteSQL == null) {
                this.generateStatements();
            }
            this.deleter = (PreparedStatement)this.connection.clientPrepareStatement(this.deleteSQL);
        }
        this.deleter.clearParameters();
        String characterEncoding = null;
        if (this.connection.getUseUnicode()) {
            characterEncoding = this.connection.getEncoding();
        }
        final int numKeys = this.primaryKeyIndicies.size();
        if (numKeys == 1) {
            final int index = this.primaryKeyIndicies.get(0);
            this.setParamValue(this.deleter, 1, this.thisRow, index, this.fields[index].getSQLType());
        }
        else {
            for (int i = 0; i < numKeys; ++i) {
                final int index2 = this.primaryKeyIndicies.get(i);
                this.setParamValue(this.deleter, i + 1, this.thisRow, index2, this.fields[index2].getSQLType());
            }
        }
        this.deleter.executeUpdate();
        this.rowData.removeRow(this.rowData.getCurrentRowNumber());
        this.previous();
    }
    
    private synchronized void setParamValue(final PreparedStatement ps, final int psIdx, final ResultSetRow row, final int rsIdx, final int sqlType) throws SQLException {
        final byte[] val = row.getColumnValue(rsIdx);
        if (val == null) {
            ps.setNull(psIdx, 0);
            return;
        }
        switch (sqlType) {
            case 0: {
                ps.setNull(psIdx, 0);
                break;
            }
            case -6:
            case 4:
            case 5: {
                ps.setInt(psIdx, row.getInt(rsIdx));
                break;
            }
            case -5: {
                ps.setLong(psIdx, row.getLong(rsIdx));
                break;
            }
            case -1:
            case 1:
            case 2:
            case 3:
            case 12: {
                ps.setString(psIdx, row.getString(rsIdx, this.charEncoding, this.connection));
                break;
            }
            case 91: {
                ps.setDate(psIdx, row.getDateFast(rsIdx, this.connection, this, this.fastDateCal), this.fastDateCal);
                break;
            }
            case 93: {
                ps.setTimestamp(psIdx, row.getTimestampFast(rsIdx, this.fastDateCal, this.defaultTimeZone, false, this.connection, this));
                break;
            }
            case 92: {
                ps.setTime(psIdx, row.getTimeFast(rsIdx, this.fastDateCal, this.defaultTimeZone, false, this.connection, this));
                break;
            }
            case 6:
            case 7:
            case 8:
            case 16: {
                ps.setBytesNoEscapeNoQuotes(psIdx, val);
                break;
            }
            default: {
                ps.setBytes(psIdx, val);
                break;
            }
        }
    }
    
    private synchronized void extractDefaultValues() throws SQLException {
        final DatabaseMetaData dbmd = this.connection.getMetaData();
        this.defaultColumnValue = new byte[this.fields.length][];
        ResultSet columnsResultSet = null;
        for (final Map.Entry dbEntry : this.databasesUsedToTablesUsed.entrySet()) {
            final String databaseName = dbEntry.getKey().toString();
            for (final Map.Entry tableEntry : dbEntry.getValue().entrySet()) {
                final String tableName = tableEntry.getKey().toString();
                final Map columnNamesToIndices = tableEntry.getValue();
                try {
                    columnsResultSet = dbmd.getColumns(this.catalog, null, tableName, "%");
                    while (columnsResultSet.next()) {
                        final String columnName = columnsResultSet.getString("COLUMN_NAME");
                        final byte[] defaultValue = columnsResultSet.getBytes("COLUMN_DEF");
                        if (columnNamesToIndices.containsKey(columnName)) {
                            final int localColumnIndex = columnNamesToIndices.get(columnName);
                            this.defaultColumnValue[localColumnIndex] = defaultValue;
                        }
                    }
                }
                finally {
                    if (columnsResultSet != null) {
                        columnsResultSet.close();
                        columnsResultSet = null;
                    }
                }
            }
        }
    }
    
    public synchronized boolean first() throws SQLException {
        return super.first();
    }
    
    protected synchronized void generateStatements() throws SQLException {
        if (!this.isUpdatable) {
            this.doingUpdates = false;
            this.onInsertRow = false;
            throw new NotUpdatable(this.notUpdatableReason);
        }
        final String quotedId = this.getQuotedIdChar();
        Map tableNamesSoFar = null;
        if (this.connection.lowerCaseTableNames()) {
            tableNamesSoFar = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            this.databasesUsedToTablesUsed = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        }
        else {
            tableNamesSoFar = new TreeMap();
            this.databasesUsedToTablesUsed = new TreeMap();
        }
        this.primaryKeyIndicies = new ArrayList();
        final StringBuffer fieldValues = new StringBuffer();
        final StringBuffer keyValues = new StringBuffer();
        final StringBuffer columnNames = new StringBuffer();
        final StringBuffer insertPlaceHolders = new StringBuffer();
        final StringBuffer allTablesBuf = new StringBuffer();
        final Map columnIndicesToTable = new HashMap();
        boolean firstTime = true;
        boolean keysFirstTime = true;
        final String equalsStr = this.connection.versionMeetsMinimum(3, 23, 0) ? "<=>" : "=";
        for (int i = 0; i < this.fields.length; ++i) {
            final StringBuffer tableNameBuffer = new StringBuffer();
            Map updColumnNameToIndex = null;
            if (this.fields[i].getOriginalTableName() != null) {
                final String databaseName = this.fields[i].getDatabaseName();
                if (databaseName != null && databaseName.length() > 0) {
                    tableNameBuffer.append(quotedId);
                    tableNameBuffer.append(databaseName);
                    tableNameBuffer.append(quotedId);
                    tableNameBuffer.append('.');
                }
                final String tableOnlyName = this.fields[i].getOriginalTableName();
                tableNameBuffer.append(quotedId);
                tableNameBuffer.append(tableOnlyName);
                tableNameBuffer.append(quotedId);
                final String fqTableName = tableNameBuffer.toString();
                if (!tableNamesSoFar.containsKey(fqTableName)) {
                    if (!tableNamesSoFar.isEmpty()) {
                        allTablesBuf.append(',');
                    }
                    allTablesBuf.append(fqTableName);
                    tableNamesSoFar.put(fqTableName, fqTableName);
                }
                columnIndicesToTable.put(new Integer(i), fqTableName);
                updColumnNameToIndex = this.getColumnsToIndexMapForTableAndDB(databaseName, tableOnlyName);
            }
            else {
                final String tableOnlyName2 = this.fields[i].getTableName();
                if (tableOnlyName2 != null) {
                    tableNameBuffer.append(quotedId);
                    tableNameBuffer.append(tableOnlyName2);
                    tableNameBuffer.append(quotedId);
                    final String fqTableName2 = tableNameBuffer.toString();
                    if (!tableNamesSoFar.containsKey(fqTableName2)) {
                        if (!tableNamesSoFar.isEmpty()) {
                            allTablesBuf.append(',');
                        }
                        allTablesBuf.append(fqTableName2);
                        tableNamesSoFar.put(fqTableName2, fqTableName2);
                    }
                    columnIndicesToTable.put(new Integer(i), fqTableName2);
                    updColumnNameToIndex = this.getColumnsToIndexMapForTableAndDB(this.catalog, tableOnlyName2);
                }
            }
            final String originalColumnName = this.fields[i].getOriginalName();
            String columnName = null;
            if (this.connection.getIO().hasLongColumnInfo() && originalColumnName != null && originalColumnName.length() > 0) {
                columnName = originalColumnName;
            }
            else {
                columnName = this.fields[i].getName();
            }
            if (updColumnNameToIndex != null && columnName != null) {
                updColumnNameToIndex.put(columnName, new Integer(i));
            }
            final String originalTableName = this.fields[i].getOriginalTableName();
            String tableName = null;
            if (this.connection.getIO().hasLongColumnInfo() && originalTableName != null && originalTableName.length() > 0) {
                tableName = originalTableName;
            }
            else {
                tableName = this.fields[i].getTableName();
            }
            final StringBuffer fqcnBuf = new StringBuffer();
            final String databaseName2 = this.fields[i].getDatabaseName();
            if (databaseName2 != null && databaseName2.length() > 0) {
                fqcnBuf.append(quotedId);
                fqcnBuf.append(databaseName2);
                fqcnBuf.append(quotedId);
                fqcnBuf.append('.');
            }
            fqcnBuf.append(quotedId);
            fqcnBuf.append(tableName);
            fqcnBuf.append(quotedId);
            fqcnBuf.append('.');
            fqcnBuf.append(quotedId);
            fqcnBuf.append(columnName);
            fqcnBuf.append(quotedId);
            final String qualifiedColumnName = fqcnBuf.toString();
            if (this.fields[i].isPrimaryKey()) {
                this.primaryKeyIndicies.add(Constants.integerValueOf(i));
                if (!keysFirstTime) {
                    keyValues.append(" AND ");
                }
                else {
                    keysFirstTime = false;
                }
                keyValues.append(qualifiedColumnName);
                keyValues.append(equalsStr);
                keyValues.append("?");
            }
            if (firstTime) {
                firstTime = false;
                fieldValues.append("SET ");
            }
            else {
                fieldValues.append(",");
                columnNames.append(",");
                insertPlaceHolders.append(",");
            }
            insertPlaceHolders.append("?");
            columnNames.append(qualifiedColumnName);
            fieldValues.append(qualifiedColumnName);
            fieldValues.append("=?");
        }
        this.qualifiedAndQuotedTableName = allTablesBuf.toString();
        this.updateSQL = "UPDATE " + this.qualifiedAndQuotedTableName + " " + fieldValues.toString() + " WHERE " + keyValues.toString();
        this.insertSQL = "INSERT INTO " + this.qualifiedAndQuotedTableName + " (" + columnNames.toString() + ") VALUES (" + insertPlaceHolders.toString() + ")";
        this.refreshSQL = "SELECT " + columnNames.toString() + " FROM " + this.qualifiedAndQuotedTableName + " WHERE " + keyValues.toString();
        this.deleteSQL = "DELETE FROM " + this.qualifiedAndQuotedTableName + " WHERE " + keyValues.toString();
    }
    
    private Map getColumnsToIndexMapForTableAndDB(final String databaseName, final String tableName) {
        Map tablesUsedToColumnsMap = this.databasesUsedToTablesUsed.get(databaseName);
        if (tablesUsedToColumnsMap == null) {
            if (this.connection.lowerCaseTableNames()) {
                tablesUsedToColumnsMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
            }
            else {
                tablesUsedToColumnsMap = new TreeMap();
            }
            this.databasesUsedToTablesUsed.put(databaseName, tablesUsedToColumnsMap);
        }
        Map nameToIndex = tablesUsedToColumnsMap.get(tableName);
        if (nameToIndex == null) {
            nameToIndex = new HashMap();
            tablesUsedToColumnsMap.put(tableName, nameToIndex);
        }
        return nameToIndex;
    }
    
    private synchronized SingleByteCharsetConverter getCharConverter() throws SQLException {
        if (!this.initializedCharConverter) {
            this.initializedCharConverter = true;
            if (this.connection.getUseUnicode()) {
                this.charEncoding = this.connection.getEncoding();
                this.charConverter = this.connection.getCharsetConverter(this.charEncoding);
            }
        }
        return this.charConverter;
    }
    
    public int getConcurrency() throws SQLException {
        return this.isUpdatable ? 1008 : 1007;
    }
    
    private synchronized String getQuotedIdChar() throws SQLException {
        if (this.quotedIdChar == null) {
            final boolean useQuotedIdentifiers = this.connection.supportsQuotedIdentifiers();
            if (useQuotedIdentifiers) {
                final DatabaseMetaData dbmd = this.connection.getMetaData();
                this.quotedIdChar = dbmd.getIdentifierQuoteString();
            }
            else {
                this.quotedIdChar = "";
            }
        }
        return this.quotedIdChar;
    }
    
    public synchronized void insertRow() throws SQLException {
        this.checkClosed();
        if (!this.onInsertRow) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.7"), this.getExceptionInterceptor());
        }
        this.inserter.executeUpdate();
        final long autoIncrementId = this.inserter.getLastInsertID();
        final int numFields = this.fields.length;
        final byte[][] newRow = new byte[numFields][];
        for (int i = 0; i < numFields; ++i) {
            if (this.inserter.isNull(i)) {
                newRow[i] = null;
            }
            else {
                newRow[i] = this.inserter.getBytesRepresentation(i);
            }
            if (this.fields[i].isAutoIncrement() && autoIncrementId > 0L) {
                newRow[i] = String.valueOf(autoIncrementId).getBytes();
                this.inserter.setBytesNoEscapeNoQuotes(i + 1, newRow[i]);
            }
        }
        final ResultSetRow resultSetRow = new ByteArrayRow(newRow, this.getExceptionInterceptor());
        this.refreshRow(this.inserter, resultSetRow);
        this.rowData.addRow(resultSetRow);
        this.resetInserter();
    }
    
    public synchronized boolean isAfterLast() throws SQLException {
        return super.isAfterLast();
    }
    
    public synchronized boolean isBeforeFirst() throws SQLException {
        return super.isBeforeFirst();
    }
    
    public synchronized boolean isFirst() throws SQLException {
        return super.isFirst();
    }
    
    public synchronized boolean isLast() throws SQLException {
        return super.isLast();
    }
    
    boolean isUpdatable() {
        return this.isUpdatable;
    }
    
    public synchronized boolean last() throws SQLException {
        return super.last();
    }
    
    public synchronized void moveToCurrentRow() throws SQLException {
        this.checkClosed();
        if (!this.isUpdatable) {
            throw new NotUpdatable(this.notUpdatableReason);
        }
        if (this.onInsertRow) {
            this.onInsertRow = false;
            this.thisRow = this.savedCurrentRow;
        }
    }
    
    public synchronized void moveToInsertRow() throws SQLException {
        this.checkClosed();
        if (!this.isUpdatable) {
            throw new NotUpdatable(this.notUpdatableReason);
        }
        if (this.inserter == null) {
            if (this.insertSQL == null) {
                this.generateStatements();
            }
            this.inserter = (PreparedStatement)this.connection.clientPrepareStatement(this.insertSQL);
            if (this.populateInserterWithDefaultValues) {
                this.extractDefaultValues();
            }
            this.resetInserter();
        }
        else {
            this.resetInserter();
        }
        final int numFields = this.fields.length;
        this.onInsertRow = true;
        this.doingUpdates = false;
        this.savedCurrentRow = this.thisRow;
        byte[][] newRowData = new byte[numFields][];
        this.thisRow = new ByteArrayRow(newRowData, this.getExceptionInterceptor());
        for (int i = 0; i < numFields; ++i) {
            if (!this.populateInserterWithDefaultValues) {
                this.inserter.setBytesNoEscapeNoQuotes(i + 1, "DEFAULT".getBytes());
                newRowData = null;
            }
            else if (this.defaultColumnValue[i] != null) {
                final Field f = this.fields[i];
                Label_0391: {
                    switch (f.getMysqlType()) {
                        case 7:
                        case 10:
                        case 11:
                        case 12:
                        case 14: {
                            if (this.defaultColumnValue[i].length > 7 && this.defaultColumnValue[i][0] == 67 && this.defaultColumnValue[i][1] == 85 && this.defaultColumnValue[i][2] == 82 && this.defaultColumnValue[i][3] == 82 && this.defaultColumnValue[i][4] == 69 && this.defaultColumnValue[i][5] == 78 && this.defaultColumnValue[i][6] == 84 && this.defaultColumnValue[i][7] == 95) {
                                this.inserter.setBytesNoEscapeNoQuotes(i + 1, this.defaultColumnValue[i]);
                                break Label_0391;
                            }
                            break;
                        }
                    }
                    this.inserter.setBytes(i + 1, this.defaultColumnValue[i], false, false);
                }
                final byte[] defaultValueCopy = new byte[this.defaultColumnValue[i].length];
                System.arraycopy(this.defaultColumnValue[i], 0, defaultValueCopy, 0, defaultValueCopy.length);
                newRowData[i] = defaultValueCopy;
            }
            else {
                this.inserter.setNull(i + 1, 0);
                newRowData[i] = null;
            }
        }
    }
    
    public synchronized boolean next() throws SQLException {
        return super.next();
    }
    
    public synchronized boolean prev() throws SQLException {
        return super.prev();
    }
    
    public synchronized boolean previous() throws SQLException {
        return super.previous();
    }
    
    public void realClose(final boolean calledExplicitly) throws SQLException {
        if (this.isClosed) {
            return;
        }
        SQLException sqlEx = null;
        if (this.useUsageAdvisor && this.deleter == null && this.inserter == null && this.refresher == null && this.updater == null) {
            this.eventSink = ProfilerEventHandlerFactory.getInstance(this.connection);
            final String message = Messages.getString("UpdatableResultSet.34");
            this.eventSink.consumeEvent(new ProfilerEvent((byte)0, "", (this.owningStatement == null) ? "N/A" : this.owningStatement.currentCatalog, this.connectionId, (this.owningStatement == null) ? -1 : this.owningStatement.getId(), this.resultId, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, this.pointOfOrigin, message));
        }
        try {
            if (this.deleter != null) {
                this.deleter.close();
            }
        }
        catch (SQLException ex) {
            sqlEx = ex;
        }
        try {
            if (this.inserter != null) {
                this.inserter.close();
            }
        }
        catch (SQLException ex) {
            sqlEx = ex;
        }
        try {
            if (this.refresher != null) {
                this.refresher.close();
            }
        }
        catch (SQLException ex) {
            sqlEx = ex;
        }
        try {
            if (this.updater != null) {
                this.updater.close();
            }
        }
        catch (SQLException ex) {
            sqlEx = ex;
        }
        super.realClose(calledExplicitly);
        if (sqlEx != null) {
            throw sqlEx;
        }
    }
    
    public synchronized void refreshRow() throws SQLException {
        this.checkClosed();
        if (!this.isUpdatable) {
            throw new NotUpdatable();
        }
        if (this.onInsertRow) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.8"), this.getExceptionInterceptor());
        }
        if (this.rowData.size() == 0) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.9"), this.getExceptionInterceptor());
        }
        if (this.isBeforeFirst()) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.10"), this.getExceptionInterceptor());
        }
        if (this.isAfterLast()) {
            throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.11"), this.getExceptionInterceptor());
        }
        this.refreshRow(this.updater, this.thisRow);
    }
    
    private synchronized void refreshRow(final PreparedStatement updateInsertStmt, final ResultSetRow rowToRefresh) throws SQLException {
        if (this.refresher == null) {
            if (this.refreshSQL == null) {
                this.generateStatements();
            }
            this.refresher = (PreparedStatement)this.connection.clientPrepareStatement(this.refreshSQL);
        }
        this.refresher.clearParameters();
        final int numKeys = this.primaryKeyIndicies.size();
        if (numKeys == 1) {
            byte[] dataFrom = null;
            final int index = this.primaryKeyIndicies.get(0);
            if (!this.doingUpdates && !this.onInsertRow) {
                dataFrom = rowToRefresh.getColumnValue(index);
            }
            else {
                dataFrom = updateInsertStmt.getBytesRepresentation(index);
                if (updateInsertStmt.isNull(index) || dataFrom.length == 0) {
                    dataFrom = rowToRefresh.getColumnValue(index);
                }
                else {
                    dataFrom = this.stripBinaryPrefix(dataFrom);
                }
            }
            if (this.fields[index].getvalueNeedsQuoting()) {
                this.refresher.setBytesNoEscape(1, dataFrom);
            }
            else {
                this.refresher.setBytesNoEscapeNoQuotes(1, dataFrom);
            }
        }
        else {
            for (int i = 0; i < numKeys; ++i) {
                byte[] dataFrom2 = null;
                final int index2 = this.primaryKeyIndicies.get(i);
                if (!this.doingUpdates && !this.onInsertRow) {
                    dataFrom2 = rowToRefresh.getColumnValue(index2);
                }
                else {
                    dataFrom2 = updateInsertStmt.getBytesRepresentation(index2);
                    if (updateInsertStmt.isNull(index2) || dataFrom2.length == 0) {
                        dataFrom2 = rowToRefresh.getColumnValue(index2);
                    }
                    else {
                        dataFrom2 = this.stripBinaryPrefix(dataFrom2);
                    }
                }
                this.refresher.setBytesNoEscape(i + 1, dataFrom2);
            }
        }
        ResultSet rs = null;
        try {
            rs = this.refresher.executeQuery();
            final int numCols = rs.getMetaData().getColumnCount();
            if (!rs.next()) {
                throw SQLError.createSQLException(Messages.getString("UpdatableResultSet.12"), "S1000", this.getExceptionInterceptor());
            }
            for (int j = 0; j < numCols; ++j) {
                final byte[] val = rs.getBytes(j + 1);
                if (val == null || rs.wasNull()) {
                    rowToRefresh.setColumnValue(j, null);
                }
                else {
                    rowToRefresh.setColumnValue(j, rs.getBytes(j + 1));
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
        }
    }
    
    public synchronized boolean relative(final int rows) throws SQLException {
        return super.relative(rows);
    }
    
    private void resetInserter() throws SQLException {
        this.inserter.clearParameters();
        for (int i = 0; i < this.fields.length; ++i) {
            this.inserter.setNull(i + 1, 0);
        }
    }
    
    public synchronized boolean rowDeleted() throws SQLException {
        throw SQLError.notImplemented();
    }
    
    public synchronized boolean rowInserted() throws SQLException {
        throw SQLError.notImplemented();
    }
    
    public synchronized boolean rowUpdated() throws SQLException {
        throw SQLError.notImplemented();
    }
    
    protected void setResultSetConcurrency(final int concurrencyFlag) {
        super.setResultSetConcurrency(concurrencyFlag);
    }
    
    private byte[] stripBinaryPrefix(final byte[] dataFrom) {
        return StringUtils.stripEnclosure(dataFrom, "_binary'", "'");
    }
    
    protected synchronized void syncUpdate() throws SQLException {
        if (this.updater == null) {
            if (this.updateSQL == null) {
                this.generateStatements();
            }
            this.updater = (PreparedStatement)this.connection.clientPrepareStatement(this.updateSQL);
        }
        final int numFields = this.fields.length;
        this.updater.clearParameters();
        for (int i = 0; i < numFields; ++i) {
            if (this.thisRow.getColumnValue(i) != null) {
                if (this.fields[i].getvalueNeedsQuoting()) {
                    this.updater.setBytes(i + 1, this.thisRow.getColumnValue(i), this.fields[i].isBinary(), false);
                }
                else {
                    this.updater.setBytesNoEscapeNoQuotes(i + 1, this.thisRow.getColumnValue(i));
                }
            }
            else {
                this.updater.setNull(i + 1, 0);
            }
        }
        final int numKeys = this.primaryKeyIndicies.size();
        if (numKeys == 1) {
            final int index = this.primaryKeyIndicies.get(0);
            this.setParamValue(this.updater, numFields + 1, this.thisRow, index, this.fields[index].getSQLType());
        }
        else {
            for (int j = 0; j < numKeys; ++j) {
                final int idx = this.primaryKeyIndicies.get(j);
                this.setParamValue(this.updater, numFields + j + 1, this.thisRow, idx, this.fields[idx].getSQLType());
            }
        }
    }
    
    public synchronized void updateAsciiStream(final int columnIndex, final InputStream x, final int length) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setAsciiStream(columnIndex, x, length);
        }
        else {
            this.inserter.setAsciiStream(columnIndex, x, length);
            this.thisRow.setColumnValue(columnIndex - 1, UpdatableResultSet.STREAM_DATA_MARKER);
        }
    }
    
    public synchronized void updateAsciiStream(final String columnName, final InputStream x, final int length) throws SQLException {
        this.updateAsciiStream(this.findColumn(columnName), x, length);
    }
    
    public synchronized void updateBigDecimal(final int columnIndex, final BigDecimal x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setBigDecimal(columnIndex, x);
        }
        else {
            this.inserter.setBigDecimal(columnIndex, x);
            if (x == null) {
                this.thisRow.setColumnValue(columnIndex - 1, null);
            }
            else {
                this.thisRow.setColumnValue(columnIndex - 1, x.toString().getBytes());
            }
        }
    }
    
    public synchronized void updateBigDecimal(final String columnName, final BigDecimal x) throws SQLException {
        this.updateBigDecimal(this.findColumn(columnName), x);
    }
    
    public synchronized void updateBinaryStream(final int columnIndex, final InputStream x, final int length) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setBinaryStream(columnIndex, x, length);
        }
        else {
            this.inserter.setBinaryStream(columnIndex, x, length);
            if (x == null) {
                this.thisRow.setColumnValue(columnIndex - 1, null);
            }
            else {
                this.thisRow.setColumnValue(columnIndex - 1, UpdatableResultSet.STREAM_DATA_MARKER);
            }
        }
    }
    
    public synchronized void updateBinaryStream(final String columnName, final InputStream x, final int length) throws SQLException {
        this.updateBinaryStream(this.findColumn(columnName), x, length);
    }
    
    public synchronized void updateBlob(final int columnIndex, final Blob blob) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setBlob(columnIndex, blob);
        }
        else {
            this.inserter.setBlob(columnIndex, blob);
            if (blob == null) {
                this.thisRow.setColumnValue(columnIndex - 1, null);
            }
            else {
                this.thisRow.setColumnValue(columnIndex - 1, UpdatableResultSet.STREAM_DATA_MARKER);
            }
        }
    }
    
    public synchronized void updateBlob(final String columnName, final Blob blob) throws SQLException {
        this.updateBlob(this.findColumn(columnName), blob);
    }
    
    public synchronized void updateBoolean(final int columnIndex, final boolean x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setBoolean(columnIndex, x);
        }
        else {
            this.inserter.setBoolean(columnIndex, x);
            this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
        }
    }
    
    public synchronized void updateBoolean(final String columnName, final boolean x) throws SQLException {
        this.updateBoolean(this.findColumn(columnName), x);
    }
    
    public synchronized void updateByte(final int columnIndex, final byte x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setByte(columnIndex, x);
        }
        else {
            this.inserter.setByte(columnIndex, x);
            this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
        }
    }
    
    public synchronized void updateByte(final String columnName, final byte x) throws SQLException {
        this.updateByte(this.findColumn(columnName), x);
    }
    
    public synchronized void updateBytes(final int columnIndex, final byte[] x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setBytes(columnIndex, x);
        }
        else {
            this.inserter.setBytes(columnIndex, x);
            this.thisRow.setColumnValue(columnIndex - 1, x);
        }
    }
    
    public synchronized void updateBytes(final String columnName, final byte[] x) throws SQLException {
        this.updateBytes(this.findColumn(columnName), x);
    }
    
    public synchronized void updateCharacterStream(final int columnIndex, final Reader x, final int length) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setCharacterStream(columnIndex, x, length);
        }
        else {
            this.inserter.setCharacterStream(columnIndex, x, length);
            if (x == null) {
                this.thisRow.setColumnValue(columnIndex - 1, null);
            }
            else {
                this.thisRow.setColumnValue(columnIndex - 1, UpdatableResultSet.STREAM_DATA_MARKER);
            }
        }
    }
    
    public synchronized void updateCharacterStream(final String columnName, final Reader reader, final int length) throws SQLException {
        this.updateCharacterStream(this.findColumn(columnName), reader, length);
    }
    
    public void updateClob(final int columnIndex, final Clob clob) throws SQLException {
        if (clob == null) {
            this.updateNull(columnIndex);
        }
        else {
            this.updateCharacterStream(columnIndex, clob.getCharacterStream(), (int)clob.length());
        }
    }
    
    public synchronized void updateDate(final int columnIndex, final Date x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setDate(columnIndex, x);
        }
        else {
            this.inserter.setDate(columnIndex, x);
            this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
        }
    }
    
    public synchronized void updateDate(final String columnName, final Date x) throws SQLException {
        this.updateDate(this.findColumn(columnName), x);
    }
    
    public synchronized void updateDouble(final int columnIndex, final double x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setDouble(columnIndex, x);
        }
        else {
            this.inserter.setDouble(columnIndex, x);
            this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
        }
    }
    
    public synchronized void updateDouble(final String columnName, final double x) throws SQLException {
        this.updateDouble(this.findColumn(columnName), x);
    }
    
    public synchronized void updateFloat(final int columnIndex, final float x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setFloat(columnIndex, x);
        }
        else {
            this.inserter.setFloat(columnIndex, x);
            this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
        }
    }
    
    public synchronized void updateFloat(final String columnName, final float x) throws SQLException {
        this.updateFloat(this.findColumn(columnName), x);
    }
    
    public synchronized void updateInt(final int columnIndex, final int x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setInt(columnIndex, x);
        }
        else {
            this.inserter.setInt(columnIndex, x);
            this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
        }
    }
    
    public synchronized void updateInt(final String columnName, final int x) throws SQLException {
        this.updateInt(this.findColumn(columnName), x);
    }
    
    public synchronized void updateLong(final int columnIndex, final long x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setLong(columnIndex, x);
        }
        else {
            this.inserter.setLong(columnIndex, x);
            this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
        }
    }
    
    public synchronized void updateLong(final String columnName, final long x) throws SQLException {
        this.updateLong(this.findColumn(columnName), x);
    }
    
    public synchronized void updateNull(final int columnIndex) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setNull(columnIndex, 0);
        }
        else {
            this.inserter.setNull(columnIndex, 0);
            this.thisRow.setColumnValue(columnIndex - 1, null);
        }
    }
    
    public synchronized void updateNull(final String columnName) throws SQLException {
        this.updateNull(this.findColumn(columnName));
    }
    
    public synchronized void updateObject(final int columnIndex, final Object x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setObject(columnIndex, x);
        }
        else {
            this.inserter.setObject(columnIndex, x);
            this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
        }
    }
    
    public synchronized void updateObject(final int columnIndex, final Object x, final int scale) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setObject(columnIndex, x);
        }
        else {
            this.inserter.setObject(columnIndex, x);
            this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
        }
    }
    
    public synchronized void updateObject(final String columnName, final Object x) throws SQLException {
        this.updateObject(this.findColumn(columnName), x);
    }
    
    public synchronized void updateObject(final String columnName, final Object x, final int scale) throws SQLException {
        this.updateObject(this.findColumn(columnName), x);
    }
    
    public synchronized void updateRow() throws SQLException {
        if (!this.isUpdatable) {
            throw new NotUpdatable(this.notUpdatableReason);
        }
        if (this.doingUpdates) {
            this.updater.executeUpdate();
            this.refreshRow();
            this.doingUpdates = false;
        }
        this.syncUpdate();
    }
    
    public synchronized void updateShort(final int columnIndex, final short x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setShort(columnIndex, x);
        }
        else {
            this.inserter.setShort(columnIndex, x);
            this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
        }
    }
    
    public synchronized void updateShort(final String columnName, final short x) throws SQLException {
        this.updateShort(this.findColumn(columnName), x);
    }
    
    public synchronized void updateString(final int columnIndex, final String x) throws SQLException {
        this.checkClosed();
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setString(columnIndex, x);
        }
        else {
            this.inserter.setString(columnIndex, x);
            if (x == null) {
                this.thisRow.setColumnValue(columnIndex - 1, null);
            }
            else if (this.getCharConverter() != null) {
                this.thisRow.setColumnValue(columnIndex - 1, StringUtils.getBytes(x, this.charConverter, this.charEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), this.getExceptionInterceptor()));
            }
            else {
                this.thisRow.setColumnValue(columnIndex - 1, x.getBytes());
            }
        }
    }
    
    public synchronized void updateString(final String columnName, final String x) throws SQLException {
        this.updateString(this.findColumn(columnName), x);
    }
    
    public synchronized void updateTime(final int columnIndex, final Time x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setTime(columnIndex, x);
        }
        else {
            this.inserter.setTime(columnIndex, x);
            this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
        }
    }
    
    public synchronized void updateTime(final String columnName, final Time x) throws SQLException {
        this.updateTime(this.findColumn(columnName), x);
    }
    
    public synchronized void updateTimestamp(final int columnIndex, final Timestamp x) throws SQLException {
        if (!this.onInsertRow) {
            if (!this.doingUpdates) {
                this.doingUpdates = true;
                this.syncUpdate();
            }
            this.updater.setTimestamp(columnIndex, x);
        }
        else {
            this.inserter.setTimestamp(columnIndex, x);
            this.thisRow.setColumnValue(columnIndex - 1, this.inserter.getBytesRepresentation(columnIndex - 1));
        }
    }
    
    public synchronized void updateTimestamp(final String columnName, final Timestamp x) throws SQLException {
        this.updateTimestamp(this.findColumn(columnName), x);
    }
    
    static {
        STREAM_DATA_MARKER = "** STREAM DATA **".getBytes();
    }
}
