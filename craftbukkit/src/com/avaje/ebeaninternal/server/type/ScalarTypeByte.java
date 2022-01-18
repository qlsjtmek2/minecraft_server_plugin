// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebean.text.TextException;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.sql.SQLException;

public class ScalarTypeByte extends ScalarTypeBase<Byte>
{
    public ScalarTypeByte() {
        super(Byte.class, true, -6);
    }
    
    public void bind(final DataBind b, final Byte value) throws SQLException {
        if (value == null) {
            b.setNull(-6);
        }
        else {
            b.setByte(value);
        }
    }
    
    public Byte read(final DataReader dataReader) throws SQLException {
        return dataReader.getByte();
    }
    
    public Object toJdbcType(final Object value) {
        return BasicTypeConverter.toByte(value);
    }
    
    public Byte toBeanType(final Object value) {
        return BasicTypeConverter.toByte(value);
    }
    
    public String formatValue(final Byte t) {
        return t.toString();
    }
    
    public Byte parse(final String value) {
        throw new TextException("Not supported");
    }
    
    public Byte parseDateTime(final long systemTimeMillis) {
        throw new TextException("Not Supported");
    }
    
    public boolean isDateTimeCapable() {
        return false;
    }
    
    public int getLuceneType() {
        return 7;
    }
    
    public Object luceneFromIndexValue(final Object value) {
        final byte[] ba = { (byte)value };
        return ba;
    }
    
    public Object luceneToIndexValue(final Object value) {
        return ((byte[])value)[0];
    }
    
    public Object readData(final DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        final byte val = dataInput.readByte();
        return val;
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        final Byte val = (Byte)v;
        if (val == null) {
            dataOutput.writeBoolean(false);
        }
        else {
            dataOutput.writeBoolean(true);
            dataOutput.writeByte(val);
        }
    }
}
