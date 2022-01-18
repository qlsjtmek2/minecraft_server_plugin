// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.sql.Date;
import java.sql.Time;
import java.io.UnsupportedEncodingException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Timestamp;
import java.util.TimeZone;
import java.util.Calendar;
import java.util.LinkedList;
import java.io.ByteArrayInputStream;
import java.util.Iterator;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class BufferRow extends ResultSetRow
{
    private Buffer rowFromServer;
    private int homePosition;
    private int preNullBitmaskHomePosition;
    private int lastRequestedIndex;
    private int lastRequestedPos;
    private Field[] metadata;
    private boolean isBinaryEncoded;
    private boolean[] isNull;
    private List openStreams;
    
    public BufferRow(final Buffer buf, final Field[] fields, final boolean isBinaryEncoded, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        super(exceptionInterceptor);
        this.homePosition = 0;
        this.preNullBitmaskHomePosition = 0;
        this.lastRequestedIndex = -1;
        this.rowFromServer = buf;
        this.metadata = fields;
        this.isBinaryEncoded = isBinaryEncoded;
        this.homePosition = this.rowFromServer.getPosition();
        this.preNullBitmaskHomePosition = this.homePosition;
        if (fields != null) {
            this.setMetadata(fields);
        }
    }
    
    public synchronized void closeOpenStreams() {
        if (this.openStreams != null) {
            final Iterator iter = this.openStreams.iterator();
            while (iter.hasNext()) {
                try {
                    iter.next().close();
                }
                catch (IOException e) {}
            }
            this.openStreams.clear();
        }
    }
    
    private int findAndSeekToOffset(final int index) throws SQLException {
        if (this.isBinaryEncoded) {
            return this.findAndSeekToOffsetForBinaryEncoding(index);
        }
        if (index == 0) {
            this.lastRequestedIndex = 0;
            this.lastRequestedPos = this.homePosition;
            this.rowFromServer.setPosition(this.homePosition);
            return 0;
        }
        if (index == this.lastRequestedIndex) {
            this.rowFromServer.setPosition(this.lastRequestedPos);
            return this.lastRequestedPos;
        }
        int startingIndex = 0;
        if (index > this.lastRequestedIndex) {
            if (this.lastRequestedIndex >= 0) {
                startingIndex = this.lastRequestedIndex;
            }
            else {
                startingIndex = 0;
            }
            this.rowFromServer.setPosition(this.lastRequestedPos);
        }
        else {
            this.rowFromServer.setPosition(this.homePosition);
        }
        for (int i = startingIndex; i < index; ++i) {
            this.rowFromServer.fastSkipLenByteArray();
        }
        this.lastRequestedIndex = index;
        return this.lastRequestedPos = this.rowFromServer.getPosition();
    }
    
    private int findAndSeekToOffsetForBinaryEncoding(final int index) throws SQLException {
        if (index == 0) {
            this.lastRequestedIndex = 0;
            this.lastRequestedPos = this.homePosition;
            this.rowFromServer.setPosition(this.homePosition);
            return 0;
        }
        if (index == this.lastRequestedIndex) {
            this.rowFromServer.setPosition(this.lastRequestedPos);
            return this.lastRequestedPos;
        }
        int startingIndex = 0;
        if (index > this.lastRequestedIndex) {
            if (this.lastRequestedIndex >= 0) {
                startingIndex = this.lastRequestedIndex;
            }
            else {
                startingIndex = 0;
                this.lastRequestedPos = this.homePosition;
            }
            this.rowFromServer.setPosition(this.lastRequestedPos);
        }
        else {
            this.rowFromServer.setPosition(this.homePosition);
        }
        for (int i = startingIndex; i < index; ++i) {
            if (!this.isNull[i]) {
                final int curPosition = this.rowFromServer.getPosition();
                switch (this.metadata[i].getMysqlType()) {
                    case 6: {
                        break;
                    }
                    case 1: {
                        this.rowFromServer.setPosition(curPosition + 1);
                        break;
                    }
                    case 2:
                    case 13: {
                        this.rowFromServer.setPosition(curPosition + 2);
                        break;
                    }
                    case 3:
                    case 9: {
                        this.rowFromServer.setPosition(curPosition + 4);
                        break;
                    }
                    case 8: {
                        this.rowFromServer.setPosition(curPosition + 8);
                        break;
                    }
                    case 4: {
                        this.rowFromServer.setPosition(curPosition + 4);
                        break;
                    }
                    case 5: {
                        this.rowFromServer.setPosition(curPosition + 8);
                        break;
                    }
                    case 11: {
                        this.rowFromServer.fastSkipLenByteArray();
                        break;
                    }
                    case 10: {
                        this.rowFromServer.fastSkipLenByteArray();
                        break;
                    }
                    case 7:
                    case 12: {
                        this.rowFromServer.fastSkipLenByteArray();
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
                    case 254:
                    case 255: {
                        this.rowFromServer.fastSkipLenByteArray();
                        break;
                    }
                    default: {
                        throw SQLError.createSQLException(Messages.getString("MysqlIO.97") + this.metadata[i].getMysqlType() + Messages.getString("MysqlIO.98") + (i + 1) + Messages.getString("MysqlIO.99") + this.metadata.length + Messages.getString("MysqlIO.100"), "S1000", this.exceptionInterceptor);
                    }
                }
            }
        }
        this.lastRequestedIndex = index;
        return this.lastRequestedPos = this.rowFromServer.getPosition();
    }
    
    public synchronized InputStream getBinaryInputStream(final int columnIndex) throws SQLException {
        if (this.isBinaryEncoded && this.isNull(columnIndex)) {
            return null;
        }
        this.findAndSeekToOffset(columnIndex);
        final long length = this.rowFromServer.readFieldLength();
        final int offset = this.rowFromServer.getPosition();
        if (length == -1L) {
            return null;
        }
        final InputStream stream = new ByteArrayInputStream(this.rowFromServer.getByteBuffer(), offset, (int)length);
        if (this.openStreams == null) {
            this.openStreams = new LinkedList();
        }
        return stream;
    }
    
    public byte[] getColumnValue(final int index) throws SQLException {
        this.findAndSeekToOffset(index);
        if (!this.isBinaryEncoded) {
            return this.rowFromServer.readLenByteArray(0);
        }
        if (this.isNull[index]) {
            return null;
        }
        switch (this.metadata[index].getMysqlType()) {
            case 6: {
                return null;
            }
            case 1: {
                return new byte[] { this.rowFromServer.readByte() };
            }
            case 2:
            case 13: {
                return this.rowFromServer.getBytes(2);
            }
            case 3:
            case 9: {
                return this.rowFromServer.getBytes(4);
            }
            case 8: {
                return this.rowFromServer.getBytes(8);
            }
            case 4: {
                return this.rowFromServer.getBytes(4);
            }
            case 5: {
                return this.rowFromServer.getBytes(8);
            }
            case 0:
            case 7:
            case 10:
            case 11:
            case 12:
            case 15:
            case 16:
            case 246:
            case 249:
            case 250:
            case 251:
            case 252:
            case 253:
            case 254:
            case 255: {
                return this.rowFromServer.readLenByteArray(0);
            }
            default: {
                throw SQLError.createSQLException(Messages.getString("MysqlIO.97") + this.metadata[index].getMysqlType() + Messages.getString("MysqlIO.98") + (index + 1) + Messages.getString("MysqlIO.99") + this.metadata.length + Messages.getString("MysqlIO.100"), "S1000", this.exceptionInterceptor);
            }
        }
    }
    
    public int getInt(final int columnIndex) throws SQLException {
        this.findAndSeekToOffset(columnIndex);
        final long length = this.rowFromServer.readFieldLength();
        final int offset = this.rowFromServer.getPosition();
        if (length == -1L) {
            return 0;
        }
        return StringUtils.getInt(this.rowFromServer.getByteBuffer(), offset, offset + (int)length);
    }
    
    public long getLong(final int columnIndex) throws SQLException {
        this.findAndSeekToOffset(columnIndex);
        final long length = this.rowFromServer.readFieldLength();
        final int offset = this.rowFromServer.getPosition();
        if (length == -1L) {
            return 0L;
        }
        return StringUtils.getLong(this.rowFromServer.getByteBuffer(), offset, offset + (int)length);
    }
    
    public double getNativeDouble(final int columnIndex) throws SQLException {
        if (this.isNull(columnIndex)) {
            return 0.0;
        }
        this.findAndSeekToOffset(columnIndex);
        final int offset = this.rowFromServer.getPosition();
        return this.getNativeDouble(this.rowFromServer.getByteBuffer(), offset);
    }
    
    public float getNativeFloat(final int columnIndex) throws SQLException {
        if (this.isNull(columnIndex)) {
            return 0.0f;
        }
        this.findAndSeekToOffset(columnIndex);
        final int offset = this.rowFromServer.getPosition();
        return this.getNativeFloat(this.rowFromServer.getByteBuffer(), offset);
    }
    
    public int getNativeInt(final int columnIndex) throws SQLException {
        if (this.isNull(columnIndex)) {
            return 0;
        }
        this.findAndSeekToOffset(columnIndex);
        final int offset = this.rowFromServer.getPosition();
        return this.getNativeInt(this.rowFromServer.getByteBuffer(), offset);
    }
    
    public long getNativeLong(final int columnIndex) throws SQLException {
        if (this.isNull(columnIndex)) {
            return 0L;
        }
        this.findAndSeekToOffset(columnIndex);
        final int offset = this.rowFromServer.getPosition();
        return this.getNativeLong(this.rowFromServer.getByteBuffer(), offset);
    }
    
    public short getNativeShort(final int columnIndex) throws SQLException {
        if (this.isNull(columnIndex)) {
            return 0;
        }
        this.findAndSeekToOffset(columnIndex);
        final int offset = this.rowFromServer.getPosition();
        return this.getNativeShort(this.rowFromServer.getByteBuffer(), offset);
    }
    
    public Timestamp getNativeTimestamp(final int columnIndex, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward, final MySQLConnection conn, final ResultSetImpl rs) throws SQLException {
        if (this.isNull(columnIndex)) {
            return null;
        }
        this.findAndSeekToOffset(columnIndex);
        final long length = this.rowFromServer.readFieldLength();
        final int offset = this.rowFromServer.getPosition();
        return this.getNativeTimestamp(this.rowFromServer.getByteBuffer(), offset, (int)length, targetCalendar, tz, rollForward, conn, rs);
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
    
    public String getString(final int columnIndex, final String encoding, final MySQLConnection conn) throws SQLException {
        if (this.isBinaryEncoded && this.isNull(columnIndex)) {
            return null;
        }
        this.findAndSeekToOffset(columnIndex);
        final long length = this.rowFromServer.readFieldLength();
        if (length == -1L) {
            return null;
        }
        if (length == 0L) {
            return "";
        }
        final int offset = this.rowFromServer.getPosition();
        return this.getString(encoding, conn, this.rowFromServer.getByteBuffer(), offset, (int)length);
    }
    
    public Time getTimeFast(final int columnIndex, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward, final MySQLConnection conn, final ResultSetImpl rs) throws SQLException {
        if (this.isNull(columnIndex)) {
            return null;
        }
        this.findAndSeekToOffset(columnIndex);
        final long length = this.rowFromServer.readFieldLength();
        final int offset = this.rowFromServer.getPosition();
        return this.getTimeFast(columnIndex, this.rowFromServer.getByteBuffer(), offset, (int)length, targetCalendar, tz, rollForward, conn, rs);
    }
    
    public Timestamp getTimestampFast(final int columnIndex, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward, final MySQLConnection conn, final ResultSetImpl rs) throws SQLException {
        if (this.isNull(columnIndex)) {
            return null;
        }
        this.findAndSeekToOffset(columnIndex);
        final long length = this.rowFromServer.readFieldLength();
        final int offset = this.rowFromServer.getPosition();
        return this.getTimestampFast(columnIndex, this.rowFromServer.getByteBuffer(), offset, (int)length, targetCalendar, tz, rollForward, conn, rs);
    }
    
    public boolean isFloatingPointNumber(final int index) throws SQLException {
        if (this.isBinaryEncoded) {
            switch (this.metadata[index].getSQLType()) {
                case 2:
                case 3:
                case 6:
                case 8: {
                    return true;
                }
                default: {
                    return false;
                }
            }
        }
        else {
            this.findAndSeekToOffset(index);
            final long length = this.rowFromServer.readFieldLength();
            if (length == -1L) {
                return false;
            }
            if (length == 0L) {
                return false;
            }
            final int offset = this.rowFromServer.getPosition();
            final byte[] buffer = this.rowFromServer.getByteBuffer();
            for (int i = 0; i < (int)length; ++i) {
                final char c = (char)buffer[offset + i];
                if (c == 'e' || c == 'E') {
                    return true;
                }
            }
            return false;
        }
    }
    
    public boolean isNull(final int index) throws SQLException {
        if (!this.isBinaryEncoded) {
            this.findAndSeekToOffset(index);
            return this.rowFromServer.readFieldLength() == -1L;
        }
        return this.isNull[index];
    }
    
    public long length(final int index) throws SQLException {
        this.findAndSeekToOffset(index);
        final long length = this.rowFromServer.readFieldLength();
        if (length == -1L) {
            return 0L;
        }
        return length;
    }
    
    public void setColumnValue(final int index, final byte[] value) throws SQLException {
        throw new OperationNotSupportedException();
    }
    
    public ResultSetRow setMetadata(final Field[] f) throws SQLException {
        super.setMetadata(f);
        if (this.isBinaryEncoded) {
            this.setupIsNullBitmask();
        }
        return this;
    }
    
    private void setupIsNullBitmask() throws SQLException {
        if (this.isNull != null) {
            return;
        }
        this.rowFromServer.setPosition(this.preNullBitmaskHomePosition);
        final int nullCount = (this.metadata.length + 9) / 8;
        final byte[] nullBitMask = new byte[nullCount];
        for (int i = 0; i < nullCount; ++i) {
            nullBitMask[i] = this.rowFromServer.readByte();
        }
        this.homePosition = this.rowFromServer.getPosition();
        this.isNull = new boolean[this.metadata.length];
        int nullMaskPos = 0;
        int bit = 4;
        for (int j = 0; j < this.metadata.length; ++j) {
            this.isNull[j] = ((nullBitMask[nullMaskPos] & bit) != 0x0);
            if (((bit <<= 1) & 0xFF) == 0x0) {
                bit = 1;
                ++nullMaskPos;
            }
        }
    }
    
    public Date getDateFast(final int columnIndex, final MySQLConnection conn, final ResultSetImpl rs, final Calendar targetCalendar) throws SQLException {
        if (this.isNull(columnIndex)) {
            return null;
        }
        this.findAndSeekToOffset(columnIndex);
        final long length = this.rowFromServer.readFieldLength();
        final int offset = this.rowFromServer.getPosition();
        return this.getDateFast(columnIndex, this.rowFromServer.getByteBuffer(), offset, (int)length, conn, rs, targetCalendar);
    }
    
    public Date getNativeDate(final int columnIndex, final MySQLConnection conn, final ResultSetImpl rs, final Calendar cal) throws SQLException {
        if (this.isNull(columnIndex)) {
            return null;
        }
        this.findAndSeekToOffset(columnIndex);
        final long length = this.rowFromServer.readFieldLength();
        final int offset = this.rowFromServer.getPosition();
        return this.getNativeDate(columnIndex, this.rowFromServer.getByteBuffer(), offset, (int)length, conn, rs, cal);
    }
    
    public Object getNativeDateTimeValue(final int columnIndex, final Calendar targetCalendar, final int jdbcType, final int mysqlType, final TimeZone tz, final boolean rollForward, final MySQLConnection conn, final ResultSetImpl rs) throws SQLException {
        if (this.isNull(columnIndex)) {
            return null;
        }
        this.findAndSeekToOffset(columnIndex);
        final long length = this.rowFromServer.readFieldLength();
        final int offset = this.rowFromServer.getPosition();
        return this.getNativeDateTimeValue(columnIndex, this.rowFromServer.getByteBuffer(), offset, (int)length, targetCalendar, jdbcType, mysqlType, tz, rollForward, conn, rs);
    }
    
    public Time getNativeTime(final int columnIndex, final Calendar targetCalendar, final TimeZone tz, final boolean rollForward, final MySQLConnection conn, final ResultSetImpl rs) throws SQLException {
        if (this.isNull(columnIndex)) {
            return null;
        }
        this.findAndSeekToOffset(columnIndex);
        final long length = this.rowFromServer.readFieldLength();
        final int offset = this.rowFromServer.getPosition();
        return this.getNativeTime(columnIndex, this.rowFromServer.getByteBuffer(), offset, (int)length, targetCalendar, tz, rollForward, conn, rs);
    }
    
    public int getBytesSize() {
        return this.rowFromServer.getBufLength();
    }
}
