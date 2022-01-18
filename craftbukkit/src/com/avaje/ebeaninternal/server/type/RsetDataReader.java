// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.io.IOException;
import com.avaje.ebeaninternal.server.core.Message;
import java.sql.Clob;
import java.io.Reader;
import java.sql.Timestamp;
import java.sql.Time;
import java.sql.Ref;
import java.sql.Date;
import java.math.BigDecimal;
import java.io.InputStream;
import java.sql.Array;
import java.sql.SQLException;
import java.sql.ResultSet;

public class RsetDataReader implements DataReader
{
    private static final int bufferSize = 512;
    static final int clobBufferSize = 512;
    static final int stringInitialSize = 512;
    private final ResultSet rset;
    protected int pos;
    
    public RsetDataReader(final ResultSet rset) {
        this.rset = rset;
    }
    
    public void close() throws SQLException {
        this.rset.close();
    }
    
    public boolean next() throws SQLException {
        return this.rset.next();
    }
    
    public void resetColumnPosition() {
        this.pos = 0;
    }
    
    public void incrementPos(final int increment) {
        this.pos += increment;
    }
    
    protected int pos() {
        return ++this.pos;
    }
    
    public Array getArray() throws SQLException {
        return this.rset.getArray(this.pos());
    }
    
    public InputStream getAsciiStream() throws SQLException {
        return this.rset.getAsciiStream(this.pos());
    }
    
    public BigDecimal getBigDecimal() throws SQLException {
        return this.rset.getBigDecimal(this.pos());
    }
    
    public InputStream getBinaryStream() throws SQLException {
        return this.rset.getBinaryStream(this.pos());
    }
    
    public Boolean getBoolean() throws SQLException {
        final boolean v = this.rset.getBoolean(this.pos());
        if (this.rset.wasNull()) {
            return null;
        }
        return v;
    }
    
    public Byte getByte() throws SQLException {
        final byte v = this.rset.getByte(this.pos());
        if (this.rset.wasNull()) {
            return null;
        }
        return v;
    }
    
    public byte[] getBytes() throws SQLException {
        return this.rset.getBytes(this.pos());
    }
    
    public Date getDate() throws SQLException {
        return this.rset.getDate(this.pos());
    }
    
    public Double getDouble() throws SQLException {
        final double v = this.rset.getDouble(this.pos());
        if (this.rset.wasNull()) {
            return null;
        }
        return v;
    }
    
    public Float getFloat() throws SQLException {
        final float v = this.rset.getFloat(this.pos());
        if (this.rset.wasNull()) {
            return null;
        }
        return v;
    }
    
    public Integer getInt() throws SQLException {
        final int v = this.rset.getInt(this.pos());
        if (this.rset.wasNull()) {
            return null;
        }
        return v;
    }
    
    public Long getLong() throws SQLException {
        final long v = this.rset.getLong(this.pos());
        if (this.rset.wasNull()) {
            return null;
        }
        return v;
    }
    
    public Ref getRef() throws SQLException {
        return this.rset.getRef(this.pos());
    }
    
    public Short getShort() throws SQLException {
        final short s = this.rset.getShort(this.pos());
        if (this.rset.wasNull()) {
            return null;
        }
        return s;
    }
    
    public String getString() throws SQLException {
        return this.rset.getString(this.pos());
    }
    
    public Time getTime() throws SQLException {
        return this.rset.getTime(this.pos());
    }
    
    public Timestamp getTimestamp() throws SQLException {
        return this.rset.getTimestamp(this.pos());
    }
    
    public String getStringFromStream() throws SQLException {
        final Reader reader = this.rset.getCharacterStream(this.pos());
        if (reader == null) {
            return null;
        }
        return this.readStringLob(reader);
    }
    
    public String getStringClob() throws SQLException {
        final Clob clob = this.rset.getClob(this.pos());
        if (clob == null) {
            return null;
        }
        final Reader reader = clob.getCharacterStream();
        if (reader == null) {
            return null;
        }
        return this.readStringLob(reader);
    }
    
    protected String readStringLob(final Reader reader) throws SQLException {
        final char[] buffer = new char[512];
        int readLength = 0;
        final StringBuilder out = new StringBuilder(512);
        try {
            while ((readLength = reader.read(buffer)) != -1) {
                out.append(buffer, 0, readLength);
            }
            reader.close();
        }
        catch (IOException e) {
            throw new SQLException(Message.msg("persist.clob.io", e.getMessage()));
        }
        return out.toString();
    }
    
    public byte[] getBinaryBytes() throws SQLException {
        final InputStream in = this.rset.getBinaryStream(this.pos());
        return this.getBinaryLob(in);
    }
    
    public byte[] getBlobBytes() throws SQLException {
        final Blob blob = this.rset.getBlob(this.pos());
        if (blob == null) {
            return null;
        }
        final InputStream in = blob.getBinaryStream();
        return this.getBinaryLob(in);
    }
    
    protected byte[] getBinaryLob(final InputStream in) throws SQLException {
        try {
            if (in == null) {
                return null;
            }
            final ByteArrayOutputStream out = new ByteArrayOutputStream();
            final byte[] buf = new byte[512];
            int len;
            while ((len = in.read(buf, 0, buf.length)) != -1) {
                out.write(buf, 0, len);
            }
            byte[] data = out.toByteArray();
            if (data.length == 0) {
                data = null;
            }
            in.close();
            out.close();
            return data;
        }
        catch (IOException e) {
            throw new SQLException(e.getClass().getName() + ":" + e.getMessage());
        }
    }
}
