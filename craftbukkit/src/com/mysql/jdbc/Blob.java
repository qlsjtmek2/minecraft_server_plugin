// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class Blob implements java.sql.Blob, OutputStreamWatcher
{
    private byte[] binaryData;
    private boolean isClosed;
    private ExceptionInterceptor exceptionInterceptor;
    
    Blob(final ExceptionInterceptor exceptionInterceptor) {
        this.binaryData = null;
        this.isClosed = false;
        this.setBinaryData(Constants.EMPTY_BYTE_ARRAY);
        this.exceptionInterceptor = exceptionInterceptor;
    }
    
    Blob(final byte[] data, final ExceptionInterceptor exceptionInterceptor) {
        this.binaryData = null;
        this.isClosed = false;
        this.setBinaryData(data);
        this.exceptionInterceptor = exceptionInterceptor;
    }
    
    Blob(final byte[] data, final ResultSetInternalMethods creatorResultSetToSet, final int columnIndexToSet) {
        this.binaryData = null;
        this.isClosed = false;
        this.setBinaryData(data);
    }
    
    private synchronized byte[] getBinaryData() {
        return this.binaryData;
    }
    
    public synchronized InputStream getBinaryStream() throws SQLException {
        this.checkClosed();
        return new ByteArrayInputStream(this.getBinaryData());
    }
    
    public synchronized byte[] getBytes(long pos, final int length) throws SQLException {
        this.checkClosed();
        if (pos < 1L) {
            throw SQLError.createSQLException(Messages.getString("Blob.2"), "S1009", this.exceptionInterceptor);
        }
        --pos;
        if (pos > this.binaryData.length) {
            throw SQLError.createSQLException("\"pos\" argument can not be larger than the BLOB's length.", "S1009", this.exceptionInterceptor);
        }
        if (pos + length > this.binaryData.length) {
            throw SQLError.createSQLException("\"pos\" + \"length\" arguments can not be larger than the BLOB's length.", "S1009", this.exceptionInterceptor);
        }
        final byte[] newData = new byte[length];
        System.arraycopy(this.getBinaryData(), (int)pos, newData, 0, length);
        return newData;
    }
    
    public synchronized long length() throws SQLException {
        this.checkClosed();
        return this.getBinaryData().length;
    }
    
    public synchronized long position(final byte[] pattern, final long start) throws SQLException {
        throw SQLError.createSQLException("Not implemented", this.exceptionInterceptor);
    }
    
    public synchronized long position(final java.sql.Blob pattern, final long start) throws SQLException {
        this.checkClosed();
        return this.position(pattern.getBytes(0L, (int)pattern.length()), start);
    }
    
    private synchronized void setBinaryData(final byte[] newBinaryData) {
        this.binaryData = newBinaryData;
    }
    
    public synchronized OutputStream setBinaryStream(final long indexToWriteAt) throws SQLException {
        this.checkClosed();
        if (indexToWriteAt < 1L) {
            throw SQLError.createSQLException(Messages.getString("Blob.0"), "S1009", this.exceptionInterceptor);
        }
        final WatchableOutputStream bytesOut = new WatchableOutputStream();
        bytesOut.setWatcher(this);
        if (indexToWriteAt > 0L) {
            bytesOut.write(this.binaryData, 0, (int)(indexToWriteAt - 1L));
        }
        return bytesOut;
    }
    
    public synchronized int setBytes(final long writeAt, final byte[] bytes) throws SQLException {
        this.checkClosed();
        return this.setBytes(writeAt, bytes, 0, bytes.length);
    }
    
    public synchronized int setBytes(final long writeAt, final byte[] bytes, final int offset, final int length) throws SQLException {
        this.checkClosed();
        final OutputStream bytesOut = this.setBinaryStream(writeAt);
        try {
            bytesOut.write(bytes, offset, length);
        }
        catch (IOException ioEx) {
            final SQLException sqlEx = SQLError.createSQLException(Messages.getString("Blob.1"), "S1000", this.exceptionInterceptor);
            sqlEx.initCause(ioEx);
            throw sqlEx;
        }
        finally {
            try {
                bytesOut.close();
            }
            catch (IOException ex) {}
        }
        return length;
    }
    
    public synchronized void streamClosed(final byte[] byteData) {
        this.binaryData = byteData;
    }
    
    public synchronized void streamClosed(final WatchableOutputStream out) {
        final int streamSize = out.size();
        if (streamSize < this.binaryData.length) {
            out.write(this.binaryData, streamSize, this.binaryData.length - streamSize);
        }
        this.binaryData = out.toByteArray();
    }
    
    public synchronized void truncate(final long len) throws SQLException {
        this.checkClosed();
        if (len < 0L) {
            throw SQLError.createSQLException("\"len\" argument can not be < 1.", "S1009", this.exceptionInterceptor);
        }
        if (len > this.binaryData.length) {
            throw SQLError.createSQLException("\"len\" argument can not be larger than the BLOB's length.", "S1009", this.exceptionInterceptor);
        }
        final byte[] newData = new byte[(int)len];
        System.arraycopy(this.getBinaryData(), 0, newData, 0, (int)len);
        this.binaryData = newData;
    }
    
    public synchronized void free() throws SQLException {
        this.binaryData = null;
        this.isClosed = true;
    }
    
    public synchronized InputStream getBinaryStream(long pos, final long length) throws SQLException {
        this.checkClosed();
        if (pos < 1L) {
            throw SQLError.createSQLException("\"pos\" argument can not be < 1.", "S1009", this.exceptionInterceptor);
        }
        --pos;
        if (pos > this.binaryData.length) {
            throw SQLError.createSQLException("\"pos\" argument can not be larger than the BLOB's length.", "S1009", this.exceptionInterceptor);
        }
        if (pos + length > this.binaryData.length) {
            throw SQLError.createSQLException("\"pos\" + \"length\" arguments can not be larger than the BLOB's length.", "S1009", this.exceptionInterceptor);
        }
        return new ByteArrayInputStream(this.getBinaryData(), (int)pos, (int)length);
    }
    
    private synchronized void checkClosed() throws SQLException {
        if (this.isClosed) {
            throw SQLError.createSQLException("Invalid operation on closed BLOB", "S1009", this.exceptionInterceptor);
        }
    }
}
