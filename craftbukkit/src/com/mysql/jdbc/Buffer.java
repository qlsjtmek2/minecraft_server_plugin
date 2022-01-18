// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.sql.SQLException;

public class Buffer
{
    static final int MAX_BYTES_TO_DUMP = 512;
    static final int NO_LENGTH_LIMIT = -1;
    static final long NULL_LENGTH = -1L;
    private int bufLength;
    private byte[] byteBuffer;
    private int position;
    protected boolean wasMultiPacket;
    
    Buffer(final byte[] buf) {
        this.bufLength = 0;
        this.position = 0;
        this.wasMultiPacket = false;
        this.byteBuffer = buf;
        this.setBufLength(buf.length);
    }
    
    Buffer(final int size) {
        this.bufLength = 0;
        this.position = 0;
        this.wasMultiPacket = false;
        this.byteBuffer = new byte[size];
        this.setBufLength(this.byteBuffer.length);
        this.position = 4;
    }
    
    final void clear() {
        this.position = 4;
    }
    
    final void dump() {
        this.dump(this.getBufLength());
    }
    
    final String dump(final int numBytes) {
        return StringUtils.dumpAsHex(this.getBytes(0, (numBytes > this.getBufLength()) ? this.getBufLength() : numBytes), (numBytes > this.getBufLength()) ? this.getBufLength() : numBytes);
    }
    
    final String dumpClampedBytes(final int numBytes) {
        final int numBytesToDump = (numBytes < 512) ? numBytes : 512;
        final String dumped = StringUtils.dumpAsHex(this.getBytes(0, (numBytesToDump > this.getBufLength()) ? this.getBufLength() : numBytesToDump), (numBytesToDump > this.getBufLength()) ? this.getBufLength() : numBytesToDump);
        if (numBytesToDump < numBytes) {
            return dumped + " ....(packet exceeds max. dump length)";
        }
        return dumped;
    }
    
    final void dumpHeader() {
        for (int i = 0; i < 4; ++i) {
            String hexVal = Integer.toHexString(this.readByte(i) & 0xFF);
            if (hexVal.length() == 1) {
                hexVal = "0" + hexVal;
            }
            System.out.print(hexVal + " ");
        }
    }
    
    final void dumpNBytes(final int start, final int nBytes) {
        final StringBuffer asciiBuf = new StringBuffer();
        for (int i = start; i < start + nBytes && i < this.getBufLength(); ++i) {
            String hexVal = Integer.toHexString(this.readByte(i) & 0xFF);
            if (hexVal.length() == 1) {
                hexVal = "0" + hexVal;
            }
            System.out.print(hexVal + " ");
            if (this.readByte(i) > 32 && this.readByte(i) < 127) {
                asciiBuf.append((char)this.readByte(i));
            }
            else {
                asciiBuf.append(".");
            }
            asciiBuf.append(" ");
        }
        System.out.println("    " + asciiBuf.toString());
    }
    
    final void ensureCapacity(final int additionalData) throws SQLException {
        if (this.position + additionalData > this.getBufLength()) {
            if (this.position + additionalData < this.byteBuffer.length) {
                this.setBufLength(this.byteBuffer.length);
            }
            else {
                int newLength = (int)(this.byteBuffer.length * 1.25);
                if (newLength < this.byteBuffer.length + additionalData) {
                    newLength = this.byteBuffer.length + (int)(additionalData * 1.25);
                }
                if (newLength < this.byteBuffer.length) {
                    newLength = this.byteBuffer.length + additionalData;
                }
                final byte[] newBytes = new byte[newLength];
                System.arraycopy(this.byteBuffer, 0, newBytes, 0, this.byteBuffer.length);
                this.byteBuffer = newBytes;
                this.setBufLength(this.byteBuffer.length);
            }
        }
    }
    
    public int fastSkipLenString() {
        final long len = this.readFieldLength();
        this.position += (int)len;
        return (int)len;
    }
    
    public void fastSkipLenByteArray() {
        final long len = this.readFieldLength();
        if (len == -1L || len == 0L) {
            return;
        }
        this.position += (int)len;
    }
    
    protected final byte[] getBufferSource() {
        return this.byteBuffer;
    }
    
    int getBufLength() {
        return this.bufLength;
    }
    
    public byte[] getByteBuffer() {
        return this.byteBuffer;
    }
    
    final byte[] getBytes(final int len) {
        final byte[] b = new byte[len];
        System.arraycopy(this.byteBuffer, this.position, b, 0, len);
        this.position += len;
        return b;
    }
    
    byte[] getBytes(final int offset, final int len) {
        final byte[] dest = new byte[len];
        System.arraycopy(this.byteBuffer, offset, dest, 0, len);
        return dest;
    }
    
    int getCapacity() {
        return this.byteBuffer.length;
    }
    
    public ByteBuffer getNioBuffer() {
        throw new IllegalArgumentException(Messages.getString("ByteArrayBuffer.0"));
    }
    
    public int getPosition() {
        return this.position;
    }
    
    final boolean isLastDataPacket() {
        return this.getBufLength() < 9 && (this.byteBuffer[0] & 0xFF) == 0xFE;
    }
    
    final long newReadLength() {
        final int sw = this.byteBuffer[this.position++] & 0xFF;
        switch (sw) {
            case 251: {
                return 0L;
            }
            case 252: {
                return this.readInt();
            }
            case 253: {
                return this.readLongInt();
            }
            case 254: {
                return this.readLongLong();
            }
            default: {
                return sw;
            }
        }
    }
    
    final byte readByte() {
        return this.byteBuffer[this.position++];
    }
    
    final byte readByte(final int readAt) {
        return this.byteBuffer[readAt];
    }
    
    final long readFieldLength() {
        final int sw = this.byteBuffer[this.position++] & 0xFF;
        switch (sw) {
            case 251: {
                return -1L;
            }
            case 252: {
                return this.readInt();
            }
            case 253: {
                return this.readLongInt();
            }
            case 254: {
                return this.readLongLong();
            }
            default: {
                return sw;
            }
        }
    }
    
    final int readInt() {
        final byte[] b = this.byteBuffer;
        return (b[this.position++] & 0xFF) | (b[this.position++] & 0xFF) << 8;
    }
    
    final int readIntAsLong() {
        final byte[] b = this.byteBuffer;
        return (b[this.position++] & 0xFF) | (b[this.position++] & 0xFF) << 8 | (b[this.position++] & 0xFF) << 16 | (b[this.position++] & 0xFF) << 24;
    }
    
    final byte[] readLenByteArray(final int offset) {
        final long len = this.readFieldLength();
        if (len == -1L) {
            return null;
        }
        if (len == 0L) {
            return Constants.EMPTY_BYTE_ARRAY;
        }
        this.position += offset;
        return this.getBytes((int)len);
    }
    
    final long readLength() {
        final int sw = this.byteBuffer[this.position++] & 0xFF;
        switch (sw) {
            case 251: {
                return 0L;
            }
            case 252: {
                return this.readInt();
            }
            case 253: {
                return this.readLongInt();
            }
            case 254: {
                return this.readLong();
            }
            default: {
                return sw;
            }
        }
    }
    
    final long readLong() {
        final byte[] b = this.byteBuffer;
        return (b[this.position++] & 0xFFL) | (b[this.position++] & 0xFFL) << 8 | (b[this.position++] & 0xFF) << 16 | (b[this.position++] & 0xFF) << 24;
    }
    
    final int readLongInt() {
        final byte[] b = this.byteBuffer;
        return (b[this.position++] & 0xFF) | (b[this.position++] & 0xFF) << 8 | (b[this.position++] & 0xFF) << 16;
    }
    
    final long readLongLong() {
        final byte[] b = this.byteBuffer;
        return (b[this.position++] & 0xFF) | (b[this.position++] & 0xFF) << 8 | (b[this.position++] & 0xFF) << 16 | (b[this.position++] & 0xFF) << 24 | (b[this.position++] & 0xFF) << 32 | (b[this.position++] & 0xFF) << 40 | (b[this.position++] & 0xFF) << 48 | (b[this.position++] & 0xFF) << 56;
    }
    
    final int readnBytes() {
        final int sw = this.byteBuffer[this.position++] & 0xFF;
        switch (sw) {
            case 1: {
                return this.byteBuffer[this.position++] & 0xFF;
            }
            case 2: {
                return this.readInt();
            }
            case 3: {
                return this.readLongInt();
            }
            case 4: {
                return (int)this.readLong();
            }
            default: {
                return 255;
            }
        }
    }
    
    final String readString() {
        int i = this.position;
        int len = 0;
        for (int maxLen = this.getBufLength(); i < maxLen && this.byteBuffer[i] != 0; ++i) {
            ++len;
        }
        final String s = new String(this.byteBuffer, this.position, len);
        this.position += len + 1;
        return s;
    }
    
    final String readString(final String encoding, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        int i = this.position;
        int len = 0;
        for (int maxLen = this.getBufLength(); i < maxLen && this.byteBuffer[i] != 0; ++i) {
            ++len;
        }
        try {
            return new String(this.byteBuffer, this.position, len, encoding);
        }
        catch (UnsupportedEncodingException uEE) {
            throw SQLError.createSQLException(Messages.getString("ByteArrayBuffer.1") + encoding + "'", "S1009", exceptionInterceptor);
        }
        finally {
            this.position += len + 1;
        }
    }
    
    void setBufLength(final int bufLengthToSet) {
        this.bufLength = bufLengthToSet;
    }
    
    public void setByteBuffer(final byte[] byteBufferToSet) {
        this.byteBuffer = byteBufferToSet;
    }
    
    public void setPosition(final int positionToSet) {
        this.position = positionToSet;
    }
    
    public void setWasMultiPacket(final boolean flag) {
        this.wasMultiPacket = flag;
    }
    
    public String toString() {
        return this.dumpClampedBytes(this.getPosition());
    }
    
    public String toSuperString() {
        return super.toString();
    }
    
    public boolean wasMultiPacket() {
        return this.wasMultiPacket;
    }
    
    final void writeByte(final byte b) throws SQLException {
        this.ensureCapacity(1);
        this.byteBuffer[this.position++] = b;
    }
    
    final void writeBytesNoNull(final byte[] bytes) throws SQLException {
        final int len = bytes.length;
        this.ensureCapacity(len);
        System.arraycopy(bytes, 0, this.byteBuffer, this.position, len);
        this.position += len;
    }
    
    final void writeBytesNoNull(final byte[] bytes, final int offset, final int length) throws SQLException {
        this.ensureCapacity(length);
        System.arraycopy(bytes, offset, this.byteBuffer, this.position, length);
        this.position += length;
    }
    
    final void writeDouble(final double d) throws SQLException {
        final long l = Double.doubleToLongBits(d);
        this.writeLongLong(l);
    }
    
    final void writeFieldLength(final long length) throws SQLException {
        if (length < 251L) {
            this.writeByte((byte)length);
        }
        else if (length < 65536L) {
            this.ensureCapacity(3);
            this.writeByte((byte)(-4));
            this.writeInt((int)length);
        }
        else if (length < 16777216L) {
            this.ensureCapacity(4);
            this.writeByte((byte)(-3));
            this.writeLongInt((int)length);
        }
        else {
            this.ensureCapacity(9);
            this.writeByte((byte)(-2));
            this.writeLongLong(length);
        }
    }
    
    final void writeFloat(final float f) throws SQLException {
        this.ensureCapacity(4);
        final int i = Float.floatToIntBits(f);
        final byte[] b = this.byteBuffer;
        b[this.position++] = (byte)(i & 0xFF);
        b[this.position++] = (byte)(i >>> 8);
        b[this.position++] = (byte)(i >>> 16);
        b[this.position++] = (byte)(i >>> 24);
    }
    
    final void writeInt(final int i) throws SQLException {
        this.ensureCapacity(2);
        final byte[] b = this.byteBuffer;
        b[this.position++] = (byte)(i & 0xFF);
        b[this.position++] = (byte)(i >>> 8);
    }
    
    final void writeLenBytes(final byte[] b) throws SQLException {
        final int len = b.length;
        this.ensureCapacity(len + 9);
        this.writeFieldLength(len);
        System.arraycopy(b, 0, this.byteBuffer, this.position, len);
        this.position += len;
    }
    
    final void writeLenString(final String s, final String encoding, final String serverEncoding, final SingleByteCharsetConverter converter, final boolean parserKnowsUnicode, final MySQLConnection conn) throws UnsupportedEncodingException, SQLException {
        byte[] b = null;
        if (converter != null) {
            b = converter.toBytes(s);
        }
        else {
            b = StringUtils.getBytes(s, encoding, serverEncoding, parserKnowsUnicode, conn, conn.getExceptionInterceptor());
        }
        final int len = b.length;
        this.ensureCapacity(len + 9);
        this.writeFieldLength(len);
        System.arraycopy(b, 0, this.byteBuffer, this.position, len);
        this.position += len;
    }
    
    final void writeLong(final long i) throws SQLException {
        this.ensureCapacity(4);
        final byte[] b = this.byteBuffer;
        b[this.position++] = (byte)(i & 0xFFL);
        b[this.position++] = (byte)(i >>> 8);
        b[this.position++] = (byte)(i >>> 16);
        b[this.position++] = (byte)(i >>> 24);
    }
    
    final void writeLongInt(final int i) throws SQLException {
        this.ensureCapacity(3);
        final byte[] b = this.byteBuffer;
        b[this.position++] = (byte)(i & 0xFF);
        b[this.position++] = (byte)(i >>> 8);
        b[this.position++] = (byte)(i >>> 16);
    }
    
    final void writeLongLong(final long i) throws SQLException {
        this.ensureCapacity(8);
        final byte[] b = this.byteBuffer;
        b[this.position++] = (byte)(i & 0xFFL);
        b[this.position++] = (byte)(i >>> 8);
        b[this.position++] = (byte)(i >>> 16);
        b[this.position++] = (byte)(i >>> 24);
        b[this.position++] = (byte)(i >>> 32);
        b[this.position++] = (byte)(i >>> 40);
        b[this.position++] = (byte)(i >>> 48);
        b[this.position++] = (byte)(i >>> 56);
    }
    
    final void writeString(final String s) throws SQLException {
        this.ensureCapacity(s.length() * 2 + 1);
        this.writeStringNoNull(s);
        this.byteBuffer[this.position++] = 0;
    }
    
    final void writeString(final String s, final String encoding, final MySQLConnection conn) throws SQLException {
        this.ensureCapacity(s.length() * 2 + 1);
        try {
            this.writeStringNoNull(s, encoding, encoding, false, conn);
        }
        catch (UnsupportedEncodingException ue) {
            throw new SQLException(ue.toString(), "S1000");
        }
        this.byteBuffer[this.position++] = 0;
    }
    
    final void writeStringNoNull(final String s) throws SQLException {
        final int len = s.length();
        this.ensureCapacity(len * 2);
        System.arraycopy(s.getBytes(), 0, this.byteBuffer, this.position, len);
        this.position += len;
    }
    
    final void writeStringNoNull(final String s, final String encoding, final String serverEncoding, final boolean parserKnowsUnicode, final MySQLConnection conn) throws UnsupportedEncodingException, SQLException {
        final byte[] b = StringUtils.getBytes(s, encoding, serverEncoding, parserKnowsUnicode, conn, conn.getExceptionInterceptor());
        final int len = b.length;
        this.ensureCapacity(len);
        System.arraycopy(b, 0, this.byteBuffer, this.position, len);
        this.position += len;
    }
}
