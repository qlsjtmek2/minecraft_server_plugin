// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebean.text.json.JsonValueAdapter;
import java.sql.SQLException;
import java.sql.Date;

public abstract class ScalarTypeBaseDate<T> extends ScalarTypeBase<T>
{
    public ScalarTypeBaseDate(final Class<T> type, final boolean jdbcNative, final int jdbcType) {
        super(type, jdbcNative, jdbcType);
    }
    
    public abstract Date convertToDate(final T p0);
    
    public abstract T convertFromDate(final Date p0);
    
    public void bind(final DataBind b, final T value) throws SQLException {
        if (value == null) {
            b.setNull(91);
        }
        else {
            final Date date = this.convertToDate(value);
            b.setDate(date);
        }
    }
    
    public T read(final DataReader dataReader) throws SQLException {
        final Date ts = dataReader.getDate();
        if (ts == null) {
            return null;
        }
        return this.convertFromDate(ts);
    }
    
    public String formatValue(final T t) {
        final Date date = this.convertToDate(t);
        return date.toString();
    }
    
    public T parse(final String value) {
        final Date date = Date.valueOf(value);
        return this.convertFromDate(date);
    }
    
    public T parseDateTime(final long systemTimeMillis) {
        final Date ts = new Date(systemTimeMillis);
        return this.convertFromDate(ts);
    }
    
    public boolean isDateTimeCapable() {
        return true;
    }
    
    public String jsonToString(final T value, final JsonValueAdapter ctx) {
        final Date date = this.convertToDate(value);
        return ctx.jsonFromDate(date);
    }
    
    public T jsonFromString(final String value, final JsonValueAdapter ctx) {
        final Date ts = ctx.jsonToDate(value);
        return this.convertFromDate(ts);
    }
    
    public int getLuceneType() {
        return 5;
    }
    
    public Object luceneFromIndexValue(final Object value) {
        final Long l = (Long)value;
        final Date date = new Date(l);
        return this.convertFromDate(date);
    }
    
    public Object luceneToIndexValue(final Object value) {
        final Date date = this.convertToDate(value);
        return date.getTime();
    }
    
    public Object readData(final DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        final long val = dataInput.readLong();
        final Date date = new Date(val);
        return this.convertFromDate(date);
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        if (v == null) {
            dataOutput.writeBoolean(false);
        }
        else {
            dataOutput.writeBoolean(true);
            final Date date = this.convertToDate(v);
            dataOutput.writeLong(date.getTime());
        }
    }
}
