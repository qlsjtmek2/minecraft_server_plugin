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

public class ScalarTypeShort extends ScalarTypeBase<Short>
{
    public ScalarTypeShort() {
        super(Short.class, true, 5);
    }
    
    public void bind(final DataBind b, final Short value) throws SQLException {
        if (value == null) {
            b.setNull(5);
        }
        else {
            b.setShort(value);
        }
    }
    
    public Short read(final DataReader dataReader) throws SQLException {
        return dataReader.getShort();
    }
    
    public Object toJdbcType(final Object value) {
        return BasicTypeConverter.toShort(value);
    }
    
    public Short toBeanType(final Object value) {
        return BasicTypeConverter.toShort(value);
    }
    
    public String formatValue(final Short v) {
        return v.toString();
    }
    
    public Short parse(final String value) {
        return Short.valueOf(value);
    }
    
    public Short parseDateTime(final long systemTimeMillis) {
        throw new TextException("Not Supported");
    }
    
    public boolean isDateTimeCapable() {
        return false;
    }
    
    public int getLuceneType() {
        return 1;
    }
    
    public Object luceneFromIndexValue(final Object value) {
        return Short.valueOf(value.toString());
    }
    
    public Object luceneToIndexValue(final Object value) {
        return value;
    }
    
    public Object readData(final DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        final short val = dataInput.readShort();
        return val;
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        final Short value = (Short)v;
        if (value == null) {
            dataOutput.writeBoolean(false);
        }
        else {
            dataOutput.writeBoolean(true);
            dataOutput.writeShort(value);
        }
    }
}
