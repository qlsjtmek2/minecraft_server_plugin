// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.sql.SQLException;

public class ScalarTypeFloat extends ScalarTypeBase<Float>
{
    public ScalarTypeFloat() {
        super(Float.class, true, 7);
    }
    
    public void bind(final DataBind b, final Float value) throws SQLException {
        if (value == null) {
            b.setNull(7);
        }
        else {
            b.setFloat(value);
        }
    }
    
    public Float read(final DataReader dataReader) throws SQLException {
        return dataReader.getFloat();
    }
    
    public Object toJdbcType(final Object value) {
        return BasicTypeConverter.toFloat(value);
    }
    
    public Float toBeanType(final Object value) {
        return BasicTypeConverter.toFloat(value);
    }
    
    public String formatValue(final Float t) {
        return t.toString();
    }
    
    public Float parse(final String value) {
        return Float.valueOf(value);
    }
    
    public Float parseDateTime(final long systemTimeMillis) {
        return (float)systemTimeMillis;
    }
    
    public boolean isDateTimeCapable() {
        return true;
    }
    
    public String toJsonString(final Float value) {
        if (value.isInfinite() || value.isNaN()) {
            return "null";
        }
        return value.toString();
    }
    
    public int getLuceneType() {
        return 4;
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
        final float val = dataInput.readFloat();
        return val;
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        final Float value = (Float)v;
        if (value == null) {
            dataOutput.writeBoolean(false);
        }
        else {
            dataOutput.writeBoolean(true);
            dataOutput.writeFloat(value);
        }
    }
}
