// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.sql.SQLException;
import java.sql.Time;

public class ScalarTypeTime extends ScalarTypeBase<Time>
{
    public ScalarTypeTime() {
        super(Time.class, true, 92);
    }
    
    public void bind(final DataBind b, final Time value) throws SQLException {
        if (value == null) {
            b.setNull(92);
        }
        else {
            b.setTime(value);
        }
    }
    
    public Time read(final DataReader dataReader) throws SQLException {
        return dataReader.getTime();
    }
    
    public Object toJdbcType(final Object value) {
        return BasicTypeConverter.toTime(value);
    }
    
    public Time toBeanType(final Object value) {
        return BasicTypeConverter.toTime(value);
    }
    
    public String formatValue(final Time v) {
        return v.toString();
    }
    
    public Time parse(final String value) {
        return Time.valueOf(value);
    }
    
    public Time parseDateTime(final long systemTimeMillis) {
        return new Time(systemTimeMillis);
    }
    
    public boolean isDateTimeCapable() {
        return true;
    }
    
    public int getLuceneType() {
        return 0;
    }
    
    public Object luceneFromIndexValue(final Object value) {
        return this.parse((String)value);
    }
    
    public Object luceneToIndexValue(final Object value) {
        return this.format(value);
    }
    
    public Object readData(final DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        final String val = dataInput.readUTF();
        return this.parse(val);
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        final Time value = (Time)v;
        if (value == null) {
            dataOutput.writeBoolean(false);
        }
        else {
            dataOutput.writeBoolean(true);
            dataOutput.writeUTF(this.format(value));
        }
    }
}
