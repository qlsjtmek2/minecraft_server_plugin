// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.sql.SQLException;

public class ScalarTypeLong extends ScalarTypeBase<Long>
{
    public ScalarTypeLong() {
        super(Long.class, true, -5);
    }
    
    public void bind(final DataBind b, final Long value) throws SQLException {
        if (value == null) {
            b.setNull(-5);
        }
        else {
            b.setLong(value);
        }
    }
    
    public Long read(final DataReader dataReader) throws SQLException {
        return dataReader.getLong();
    }
    
    public Object toJdbcType(final Object value) {
        return BasicTypeConverter.toLong(value);
    }
    
    public Long toBeanType(final Object value) {
        return BasicTypeConverter.toLong(value);
    }
    
    public String formatValue(final Long t) {
        return t.toString();
    }
    
    public Long parse(final String value) {
        return Long.valueOf(value);
    }
    
    public Long parseDateTime(final long systemTimeMillis) {
        return systemTimeMillis;
    }
    
    public boolean isDateTimeCapable() {
        return true;
    }
    
    public int getLuceneType() {
        return 2;
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
        final long val = dataInput.readLong();
        return val;
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        final Long value = (Long)v;
        if (value == null) {
            dataOutput.writeBoolean(false);
        }
        else {
            dataOutput.writeBoolean(true);
            dataOutput.writeLong(value);
        }
    }
}
