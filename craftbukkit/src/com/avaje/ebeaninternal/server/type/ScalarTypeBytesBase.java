// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebean.text.TextException;
import java.sql.SQLException;

public abstract class ScalarTypeBytesBase extends ScalarTypeBase<byte[]>
{
    protected ScalarTypeBytesBase(final boolean jdbcNative, final int jdbcType) {
        super(byte[].class, jdbcNative, jdbcType);
    }
    
    public Object convertFromBytes(final byte[] bytes) {
        return bytes;
    }
    
    public byte[] convertToBytes(final Object value) {
        return (byte[])value;
    }
    
    public void bind(final DataBind b, final byte[] value) throws SQLException {
        if (value == null) {
            b.setNull(this.jdbcType);
        }
        else {
            b.setBytes(value);
        }
    }
    
    public Object toJdbcType(final Object value) {
        return value;
    }
    
    public byte[] toBeanType(final Object value) {
        return (byte[])value;
    }
    
    public String formatValue(final byte[] t) {
        throw new TextException("Not supported");
    }
    
    public byte[] parse(final String value) {
        throw new TextException("Not supported");
    }
    
    public byte[] parseDateTime(final long systemTimeMillis) {
        throw new TextException("Not supported");
    }
    
    public boolean isDateTimeCapable() {
        return false;
    }
    
    public int getLuceneType() {
        return 7;
    }
    
    public Object luceneFromIndexValue(final Object value) {
        return value;
    }
    
    public Object luceneToIndexValue(final Object value) {
        return value;
    }
    
    public Object readData(final DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        final int len = dataInput.readInt();
        final byte[] buf = new byte[len];
        dataInput.readFully(buf, 0, buf.length);
        return buf;
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        if (v == null) {
            dataOutput.writeBoolean(false);
        }
        else {
            final byte[] bytes = this.convertToBytes(v);
            dataOutput.writeInt(bytes.length);
            dataOutput.write(bytes);
        }
    }
}
