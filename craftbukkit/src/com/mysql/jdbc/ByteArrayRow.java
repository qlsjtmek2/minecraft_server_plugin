// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.Date;
import java.sql.Time;
import java.io.UnsupportedEncodingException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.TimeZone;
import java.util.Calendar;
import java.sql.SQLException;

public class ByteArrayRow extends ResultSetRow
{
    byte[][] internalRowData;
    
    public ByteArrayRow(final byte[][] internalRowData, final ExceptionInterceptor exceptionInterceptor) {
        super(exceptionInterceptor);
        this.internalRowData = internalRowData;
    }
    
    public byte[] getColumnValue(final int index) throws SQLException {
        return this.internalRowData[index];
    }
    
    public void setColumnValue(final int index, final byte[] value) throws SQLException {
        this.internalRowData[index] = value;
    }
    
    public String getString(final int index, final String encoding, final MySQLConnection conn) throws SQLException {
        final byte[] columnData = this.internalRowData[index];
        if (columnData == null) {
            return null;
        }
        return this.getString(encoding, conn, columnData, 0, columnData.length);
    }
    
    public boolean isNull(final int index) throws SQLException {
        return this.internalRowData[index] == null;
    }
    
    public boolean isFloatingPointNumber(final int index) throws SQLException {
        final byte[] numAsBytes = this.internalRowData[index];
        if (this.internalRowData[index] == null || this.internalRowData[index].length == 0) {
            return false;
        }
        for (int i = 0; i < numAsBytes.length; ++i) {
            if ((char)numAsBytes[i] == 'e' || (char)numAsBytes[i] == 'E') {
                return true;
            }
        }
        return false;
    }
    
    public long length(final int index) throws SQLException {
        if (this.internalRowData[index] == null) {
            return 0L;
        }
        return this.internalRowData[index].length;
    }
    
    public int getInt(final int columnIndex) {
        if (this.internalRowData[columnIndex] == null) {
            return 0;
        }
        return StringUtils.getInt(this.internalRowData[columnIndex]);
    }
    
    public long getLong(final int columnIndex) {
        if (this.internalRowData[columnIndex] == null) {
            return 0L;
        }
        return StringUtils.getLong(this.internalRowData[columnIndex]);
    }
    
    public Timestamp getTimestampFast(final int columnIndex, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward, final MySQLConnection conn, final ResultSetImpl rs) throws SQLException {
        final byte[] columnValue = this.internalRowData[columnIndex];
        if (columnValue == null) {
            return null;
        }
        return this.getTimestampFast(columnIndex, this.internalRowData[columnIndex], 0, columnValue.length, targetCalendar, tz, rollForward, conn, rs);
    }
    
    public double getNativeDouble(final int columnIndex) throws SQLException {
        if (this.internalRowData[columnIndex] == null) {
            return 0.0;
        }
        return this.getNativeDouble(this.internalRowData[columnIndex], 0);
    }
    
    public float getNativeFloat(final int columnIndex) throws SQLException {
        if (this.internalRowData[columnIndex] == null) {
            return 0.0f;
        }
        return this.getNativeFloat(this.internalRowData[columnIndex], 0);
    }
    
    public int getNativeInt(final int columnIndex) throws SQLException {
        if (this.internalRowData[columnIndex] == null) {
            return 0;
        }
        return this.getNativeInt(this.internalRowData[columnIndex], 0);
    }
    
    public long getNativeLong(final int columnIndex) throws SQLException {
        if (this.internalRowData[columnIndex] == null) {
            return 0L;
        }
        return this.getNativeLong(this.internalRowData[columnIndex], 0);
    }
    
    public short getNativeShort(final int columnIndex) throws SQLException {
        if (this.internalRowData[columnIndex] == null) {
            return 0;
        }
        return this.getNativeShort(this.internalRowData[columnIndex], 0);
    }
    
    public Timestamp getNativeTimestamp(final int columnIndex, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward, final MySQLConnection conn, final ResultSetImpl rs) throws SQLException {
        final byte[] bits = this.internalRowData[columnIndex];
        if (bits == null) {
            return null;
        }
        return this.getNativeTimestamp(bits, 0, bits.length, targetCalendar, tz, rollForward, conn, rs);
    }
    
    public void closeOpenStreams() {
    }
    
    public InputStream getBinaryInputStream(final int columnIndex) throws SQLException {
        if (this.internalRowData[columnIndex] == null) {
            return null;
        }
        return new ByteArrayInputStream(this.internalRowData[columnIndex]);
    }
    
    public Reader getReader(final int columnIndex) throws SQLException {
        final InputStream stream = this.getBinaryInputStream(columnIndex);
        if (stream == null) {
            return null;
        }
        try {
            return new InputStreamReader(stream, this.metadata[columnIndex].getCharacterSet());
        }
        catch (UnsupportedEncodingException e) {
            final SQLException sqlEx = SQLError.createSQLException("", this.exceptionInterceptor);
            sqlEx.initCause(e);
            throw sqlEx;
        }
    }
    
    public Time getTimeFast(final int columnIndex, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward, final MySQLConnection conn, final ResultSetImpl rs) throws SQLException {
        final byte[] columnValue = this.internalRowData[columnIndex];
        if (columnValue == null) {
            return null;
        }
        return this.getTimeFast(columnIndex, this.internalRowData[columnIndex], 0, columnValue.length, targetCalendar, tz, rollForward, conn, rs);
    }
    
    public Date getDateFast(final int columnIndex, final MySQLConnection conn, final ResultSetImpl rs, final Calendar targetCalendar) throws SQLException {
        final byte[] columnValue = this.internalRowData[columnIndex];
        if (columnValue == null) {
            return null;
        }
        return this.getDateFast(columnIndex, this.internalRowData[columnIndex], 0, columnValue.length, conn, rs, targetCalendar);
    }
    
    public Object getNativeDateTimeValue(final int columnIndex, final Calendar targetCalendar, final int jdbcType, final int mysqlType, final TimeZone tz, final boolean rollForward, final MySQLConnection conn, final ResultSetImpl rs) throws SQLException {
        final byte[] columnValue = this.internalRowData[columnIndex];
        if (columnValue == null) {
            return null;
        }
        return this.getNativeDateTimeValue(columnIndex, columnValue, 0, columnValue.length, targetCalendar, jdbcType, mysqlType, tz, rollForward, conn, rs);
    }
    
    public Date getNativeDate(final int columnIndex, final MySQLConnection conn, final ResultSetImpl rs, final Calendar cal) throws SQLException {
        final byte[] columnValue = this.internalRowData[columnIndex];
        if (columnValue == null) {
            return null;
        }
        return this.getNativeDate(columnIndex, columnValue, 0, columnValue.length, conn, rs, cal);
    }
    
    public Time getNativeTime(final int columnIndex, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward, final MySQLConnection conn, final ResultSetImpl rs) throws SQLException {
        final byte[] columnValue = this.internalRowData[columnIndex];
        if (columnValue == null) {
            return null;
        }
        return this.getNativeTime(columnIndex, columnValue, 0, columnValue.length, targetCalendar, tz, rollForward, conn, rs);
    }
    
    public int getBytesSize() {
        if (this.internalRowData == null) {
            return 0;
        }
        int bytesSize = 0;
        for (int i = 0; i < this.internalRowData.length; ++i) {
            if (this.internalRowData[i] != null) {
                bytesSize += this.internalRowData[i].length;
            }
        }
        return bytesSize;
    }
}
