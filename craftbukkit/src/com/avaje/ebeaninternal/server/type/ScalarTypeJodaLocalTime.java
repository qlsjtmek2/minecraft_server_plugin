// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import java.util.Date;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import org.joda.time.DateTimeZone;
import java.sql.SQLException;
import java.sql.Time;
import org.joda.time.LocalTime;

public class ScalarTypeJodaLocalTime extends ScalarTypeBase<LocalTime>
{
    public ScalarTypeJodaLocalTime() {
        super(LocalTime.class, false, 92);
    }
    
    public void bind(final DataBind b, final LocalTime value) throws SQLException {
        if (value == null) {
            b.setNull(92);
        }
        else {
            final Time sqlTime = new Time(value.getMillisOfDay());
            b.setTime(sqlTime);
        }
    }
    
    public LocalTime read(final DataReader dataReader) throws SQLException {
        final Time sqlTime = dataReader.getTime();
        if (sqlTime == null) {
            return null;
        }
        return new LocalTime((Object)sqlTime, DateTimeZone.UTC);
    }
    
    public Object toJdbcType(final Object value) {
        if (value instanceof LocalTime) {
            return new Time(((LocalTime)value).getMillisOfDay());
        }
        return BasicTypeConverter.toTime(value);
    }
    
    public LocalTime toBeanType(final Object value) {
        if (value instanceof Date) {
            return new LocalTime(value, DateTimeZone.UTC);
        }
        return (LocalTime)value;
    }
    
    public String formatValue(final LocalTime v) {
        return v.toString();
    }
    
    public LocalTime parse(final String value) {
        return new LocalTime((Object)value);
    }
    
    public LocalTime parseDateTime(final long systemTimeMillis) {
        return new LocalTime(systemTimeMillis);
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
