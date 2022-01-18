// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.sql.SQLException;

public class ScalarTypeDouble extends ScalarTypeBase<Double>
{
    public ScalarTypeDouble() {
        super(Double.class, true, 8);
    }
    
    public void bind(final DataBind b, final Double value) throws SQLException {
        if (value == null) {
            b.setNull(8);
        }
        else {
            b.setDouble(value);
        }
    }
    
    public Double read(final DataReader dataReader) throws SQLException {
        return dataReader.getDouble();
    }
    
    public Object toJdbcType(final Object value) {
        return BasicTypeConverter.toDouble(value);
    }
    
    public Double toBeanType(final Object value) {
        return BasicTypeConverter.toDouble(value);
    }
    
    public String formatValue(final Double t) {
        return t.toString();
    }
    
    public Double parse(final String value) {
        return Double.valueOf(value);
    }
    
    public Double parseDateTime(final long systemTimeMillis) {
        return (double)systemTimeMillis;
    }
    
    public boolean isDateTimeCapable() {
        return true;
    }
    
    public String toJsonString(final Double value) {
        if (value.isInfinite() || value.isNaN()) {
            return "null";
        }
        return value.toString();
    }
    
    public int getLuceneType() {
        return 3;
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
        final double val = dataInput.readDouble();
        return val;
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        final Double value = (Double)v;
        if (value == null) {
            dataOutput.writeBoolean(false);
        }
        else {
            dataOutput.writeBoolean(true);
            dataOutput.writeDouble(value);
        }
    }
}
