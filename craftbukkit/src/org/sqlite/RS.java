// 
// Decompiled by Procyon v0.5.30
// 

package org.sqlite;

import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Time;
import java.util.Calendar;
import java.sql.Date;
import java.io.StringReader;
import java.io.Reader;
import java.sql.SQLException;
import java.sql.ResultSetMetaData;
import java.sql.ResultSet;

final class RS extends Unused implements ResultSet, ResultSetMetaData, Codes
{
    private final Stmt stmt;
    private final DB db;
    boolean open;
    int maxRows;
    String[] cols;
    String[] colsMeta;
    boolean[][] meta;
    private int limitRows;
    private int row;
    private int lastCol;
    
    RS(final Stmt stmt) {
        this.open = false;
        this.cols = null;
        this.colsMeta = null;
        this.meta = null;
        this.row = 0;
        this.stmt = stmt;
        this.db = stmt.db;
    }
    
    boolean isOpen() {
        return this.open;
    }
    
    void checkOpen() throws SQLException {
        if (!this.open) {
            throw new SQLException("ResultSet closed");
        }
    }
    
    private int checkCol(int col) throws SQLException {
        if (this.colsMeta == null) {
            throw new IllegalStateException("SQLite JDBC: inconsistent internal state");
        }
        if (col < 1 || col > this.colsMeta.length) {
            throw new SQLException("column " + col + " out of bounds [1," + this.colsMeta.length + "]");
        }
        return --col;
    }
    
    private int markCol(int col) throws SQLException {
        this.checkOpen();
        this.checkCol(col);
        this.lastCol = col;
        return --col;
    }
    
    private void checkMeta() throws SQLException {
        this.checkCol(1);
        if (this.meta == null) {
            this.meta = this.db.column_metadata(this.stmt.pointer);
        }
    }
    
    public void close() throws SQLException {
        this.cols = null;
        this.colsMeta = null;
        this.meta = null;
        this.open = false;
        this.limitRows = 0;
        this.row = 0;
        this.lastCol = -1;
        if (this.stmt == null) {
            return;
        }
        if (this.stmt != null && this.stmt.pointer != 0L) {
            this.db.reset(this.stmt.pointer);
        }
    }
    
    public int findColumn(final String col) throws SQLException {
        this.checkOpen();
        int c = -1;
        for (int i = 0; i < this.cols.length; ++i) {
            if (col.equalsIgnoreCase(this.cols[i]) || (this.cols[i].toUpperCase().endsWith(col.toUpperCase()) && this.cols[i].charAt(this.cols[i].length() - col.length()) == '.')) {
                if (c != -1) {
                    throw new SQLException("ambiguous column: '" + col + "'");
                }
                c = i;
            }
        }
        if (c == -1) {
            throw new SQLException("no such column: '" + col + "'");
        }
        return c + 1;
    }
    
    public boolean next() throws SQLException {
        if (!this.open) {
            return false;
        }
        this.lastCol = -1;
        if (this.row == 0) {
            ++this.row;
            return true;
        }
        if (this.maxRows != 0 && this.row > this.maxRows) {
            return false;
        }
        final int statusCode = this.db.step(this.stmt.pointer);
        switch (statusCode) {
            case 101: {
                this.close();
                return false;
            }
            case 100: {
                ++this.row;
                return true;
            }
            default: {
                this.db.throwex(statusCode);
                return false;
            }
        }
    }
    
    public int getType() throws SQLException {
        return 1003;
    }
    
    public int getFetchSize() throws SQLException {
        return this.limitRows;
    }
    
    public void setFetchSize(final int rows) throws SQLException {
        if (0 > rows || (this.maxRows != 0 && rows > this.maxRows)) {
            throw new SQLException("fetch size " + rows + " out of bounds " + this.maxRows);
        }
        this.limitRows = rows;
    }
    
    public int getFetchDirection() throws SQLException {
        this.checkOpen();
        return 1000;
    }
    
    public void setFetchDirection(final int d) throws SQLException {
        this.checkOpen();
        if (d != 1000) {
            throw new SQLException("only FETCH_FORWARD direction supported");
        }
    }
    
    public boolean isAfterLast() throws SQLException {
        return !this.open;
    }
    
    public boolean isBeforeFirst() throws SQLException {
        return this.open && this.row == 0;
    }
    
    public boolean isFirst() throws SQLException {
        return this.row == 1;
    }
    
    public boolean isLast() throws SQLException {
        throw new SQLException("function not yet implemented for SQLite");
    }
    
    protected void finalize() throws SQLException {
        this.close();
    }
    
    public int getRow() throws SQLException {
        return this.row;
    }
    
    public boolean wasNull() throws SQLException {
        return this.db.column_type(this.stmt.pointer, this.markCol(this.lastCol)) == 5;
    }
    
    public boolean getBoolean(final int col) throws SQLException {
        return this.getInt(col) != 0;
    }
    
    public boolean getBoolean(final String col) throws SQLException {
        return this.getBoolean(this.findColumn(col));
    }
    
    public byte getByte(final int col) throws SQLException {
        return (byte)this.getInt(col);
    }
    
    public byte getByte(final String col) throws SQLException {
        return this.getByte(this.findColumn(col));
    }
    
    public byte[] getBytes(final int col) throws SQLException {
        return this.db.column_blob(this.stmt.pointer, this.markCol(col));
    }
    
    public byte[] getBytes(final String col) throws SQLException {
        return this.getBytes(this.findColumn(col));
    }
    
    public Reader getCharacterStream(final int col) throws SQLException {
        return new StringReader(this.getString(col));
    }
    
    public Reader getCharacterStream(final String col) throws SQLException {
        return this.getCharacterStream(this.findColumn(col));
    }
    
    public Date getDate(final int col) throws SQLException {
        if (this.db.column_type(this.stmt.pointer, this.markCol(col)) == 5) {
            return null;
        }
        return new Date(this.db.column_long(this.stmt.pointer, this.markCol(col)));
    }
    
    public Date getDate(final int col, final Calendar cal) throws SQLException {
        if (this.db.column_type(this.stmt.pointer, this.markCol(col)) == 5) {
            return null;
        }
        if (cal == null) {
            return this.getDate(col);
        }
        cal.setTimeInMillis(this.db.column_long(this.stmt.pointer, this.markCol(col)));
        return new Date(cal.getTime().getTime());
    }
    
    public Date getDate(final String col) throws SQLException {
        return this.getDate(this.findColumn(col), Calendar.getInstance());
    }
    
    public Date getDate(final String col, final Calendar cal) throws SQLException {
        return this.getDate(this.findColumn(col), cal);
    }
    
    public double getDouble(final int col) throws SQLException {
        if (this.db.column_type(this.stmt.pointer, this.markCol(col)) == 5) {
            return 0.0;
        }
        return this.db.column_double(this.stmt.pointer, this.markCol(col));
    }
    
    public double getDouble(final String col) throws SQLException {
        return this.getDouble(this.findColumn(col));
    }
    
    public float getFloat(final int col) throws SQLException {
        if (this.db.column_type(this.stmt.pointer, this.markCol(col)) == 5) {
            return 0.0f;
        }
        return (float)this.db.column_double(this.stmt.pointer, this.markCol(col));
    }
    
    public float getFloat(final String col) throws SQLException {
        return this.getFloat(this.findColumn(col));
    }
    
    public int getInt(final int col) throws SQLException {
        return this.db.column_int(this.stmt.pointer, this.markCol(col));
    }
    
    public int getInt(final String col) throws SQLException {
        return this.getInt(this.findColumn(col));
    }
    
    public long getLong(final int col) throws SQLException {
        return this.db.column_long(this.stmt.pointer, this.markCol(col));
    }
    
    public long getLong(final String col) throws SQLException {
        return this.getLong(this.findColumn(col));
    }
    
    public short getShort(final int col) throws SQLException {
        return (short)this.getInt(col);
    }
    
    public short getShort(final String col) throws SQLException {
        return this.getShort(this.findColumn(col));
    }
    
    public String getString(final int col) throws SQLException {
        return this.db.column_text(this.stmt.pointer, this.markCol(col));
    }
    
    public String getString(final String col) throws SQLException {
        return this.getString(this.findColumn(col));
    }
    
    public Time getTime(final int col) throws SQLException {
        if (this.db.column_type(this.stmt.pointer, this.markCol(col)) == 5) {
            return null;
        }
        return new Time(this.db.column_long(this.stmt.pointer, this.markCol(col)));
    }
    
    public Time getTime(final int col, final Calendar cal) throws SQLException {
        if (cal == null) {
            return this.getTime(col);
        }
        if (this.db.column_type(this.stmt.pointer, this.markCol(col)) == 5) {
            return null;
        }
        cal.setTimeInMillis(this.db.column_long(this.stmt.pointer, this.markCol(col)));
        return new Time(cal.getTime().getTime());
    }
    
    public Time getTime(final String col) throws SQLException {
        return this.getTime(this.findColumn(col));
    }
    
    public Time getTime(final String col, final Calendar cal) throws SQLException {
        return this.getTime(this.findColumn(col), cal);
    }
    
    public Timestamp getTimestamp(final int col) throws SQLException {
        if (this.db.column_type(this.stmt.pointer, this.markCol(col)) == 5) {
            return null;
        }
        return new Timestamp(this.db.column_long(this.stmt.pointer, this.markCol(col)));
    }
    
    public Timestamp getTimestamp(final int col, final Calendar cal) throws SQLException {
        if (cal == null) {
            return this.getTimestamp(col);
        }
        if (this.db.column_type(this.stmt.pointer, this.markCol(col)) == 5) {
            return null;
        }
        cal.setTimeInMillis(this.db.column_long(this.stmt.pointer, this.markCol(col)));
        return new Timestamp(cal.getTime().getTime());
    }
    
    public Timestamp getTimestamp(final String col) throws SQLException {
        return this.getTimestamp(this.findColumn(col));
    }
    
    public Timestamp getTimestamp(final String c, final Calendar ca) throws SQLException {
        return this.getTimestamp(this.findColumn(c), ca);
    }
    
    public Object getObject(final int col) throws SQLException {
        switch (this.db.column_type(this.stmt.pointer, this.checkCol(col))) {
            case 1: {
                final long val = this.getLong(col);
                if (val > 2147483647L || val < -2147483648L) {
                    return new Long(val);
                }
                return new Integer((int)val);
            }
            case 2: {
                return new Double(this.getDouble(col));
            }
            case 4: {
                return this.getBytes(col);
            }
            case 5: {
                return null;
            }
            default: {
                return this.getString(col);
            }
        }
    }
    
    public Object getObject(final String col) throws SQLException {
        return this.getObject(this.findColumn(col));
    }
    
    public Statement getStatement() {
        return this.stmt;
    }
    
    public String getCursorName() throws SQLException {
        return null;
    }
    
    public SQLWarning getWarnings() throws SQLException {
        return null;
    }
    
    public void clearWarnings() throws SQLException {
    }
    
    public ResultSetMetaData getMetaData() throws SQLException {
        return this;
    }
    
    public String getCatalogName(final int col) throws SQLException {
        return this.db.column_table_name(this.stmt.pointer, this.checkCol(col));
    }
    
    public String getColumnClassName(final int col) throws SQLException {
        this.checkCol(col);
        return "java.lang.Object";
    }
    
    public int getColumnCount() throws SQLException {
        this.checkCol(1);
        return this.colsMeta.length;
    }
    
    public int getColumnDisplaySize(final int col) throws SQLException {
        return Integer.MAX_VALUE;
    }
    
    public String getColumnLabel(final int col) throws SQLException {
        return this.getColumnName(col);
    }
    
    public String getColumnName(final int col) throws SQLException {
        return this.db.column_name(this.stmt.pointer, this.checkCol(col));
    }
    
    public int getColumnType(final int col) throws SQLException {
        switch (this.db.column_type(this.stmt.pointer, this.checkCol(col))) {
            case 1: {
                return 4;
            }
            case 2: {
                return 6;
            }
            case 4: {
                return 2004;
            }
            case 5: {
                return 0;
            }
            default: {
                return 12;
            }
        }
    }
    
    public String getColumnTypeName(final int col) throws SQLException {
        switch (this.db.column_type(this.stmt.pointer, this.checkCol(col))) {
            case 1: {
                return "integer";
            }
            case 2: {
                return "float";
            }
            case 4: {
                return "blob";
            }
            case 5: {
                return "null";
            }
            default: {
                return "text";
            }
        }
    }
    
    public int getPrecision(final int col) throws SQLException {
        return 0;
    }
    
    public int getScale(final int col) throws SQLException {
        return 0;
    }
    
    public String getSchemaName(final int col) throws SQLException {
        return "";
    }
    
    public String getTableName(final int col) throws SQLException {
        return this.db.column_table_name(this.stmt.pointer, this.checkCol(col));
    }
    
    public int isNullable(final int col) throws SQLException {
        this.checkMeta();
        return this.meta[this.checkCol(col)][1] ? 0 : 1;
    }
    
    public boolean isAutoIncrement(final int col) throws SQLException {
        this.checkMeta();
        return this.meta[this.checkCol(col)][2];
    }
    
    public boolean isCaseSensitive(final int col) throws SQLException {
        return true;
    }
    
    public boolean isCurrency(final int col) throws SQLException {
        return false;
    }
    
    public boolean isDefinitelyWritable(final int col) throws SQLException {
        return true;
    }
    
    public boolean isReadOnly(final int col) throws SQLException {
        return false;
    }
    
    public boolean isSearchable(final int col) throws SQLException {
        return true;
    }
    
    public boolean isSigned(final int col) throws SQLException {
        return false;
    }
    
    public boolean isWritable(final int col) throws SQLException {
        return true;
    }
    
    public int getConcurrency() throws SQLException {
        return 1007;
    }
    
    public boolean rowDeleted() throws SQLException {
        return false;
    }
    
    public boolean rowInserted() throws SQLException {
        return false;
    }
    
    public boolean rowUpdated() throws SQLException {
        return false;
    }
}
