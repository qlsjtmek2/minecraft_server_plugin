// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebean.text.json.JsonValueAdapter;
import java.sql.SQLException;
import java.sql.Timestamp;

public abstract class ScalarTypeBaseDateTime<T> extends ScalarTypeBase<T>
{
    public ScalarTypeBaseDateTime(final Class<T> type, final boolean jdbcNative, final int jdbcType) {
        super(type, jdbcNative, jdbcType);
    }
    
    public abstract Timestamp convertToTimestamp(final T p0);
    
    public abstract T convertFromTimestamp(final Timestamp p0);
    
    public void bind(final DataBind b, final T value) throws SQLException {
        if (value == null) {
            b.setNull(93);
        }
        else {
            final Timestamp ts = this.convertToTimestamp(value);
            b.setTimestamp(ts);
        }
    }
    
    public T read(final DataReader dataReader) throws SQLException {
        final Timestamp ts = dataReader.getTimestamp();
        if (ts == null) {
            return null;
        }
        return this.convertFromTimestamp(ts);
    }
    
    public String formatValue(final T t) {
        final Timestamp ts = this.convertToTimestamp(t);
        return ts.toString();
    }
    
    public T parse(final String value) {
        final Timestamp ts = Timestamp.valueOf(value);
        return this.convertFromTimestamp(ts);
    }
    
    public T parseDateTime(final long systemTimeMillis) {
        final Timestamp ts = new Timestamp(systemTimeMillis);
        return this.convertFromTimestamp(ts);
    }
    
    public boolean isDateTimeCapable() {
        return true;
    }
    
    public String jsonToString(final T value, final JsonValueAdapter ctx) {
        final Timestamp ts = this.convertToTimestamp(value);
        return ctx.jsonFromTimestamp(ts);
    }
    
    public T jsonFromString(final String value, final JsonValueAdapter ctx) {
        final Timestamp ts = ctx.jsonToTimestamp(value);
        return this.convertFromTimestamp(ts);
    }
    
    public int getLuceneType() {
        return 6;
    }
    
    public Object luceneFromIndexValue(final Object value) {
        final Long l = (Long)value;
        final Timestamp ts = new Timestamp(l);
        return this.convertFromTimestamp(ts);
    }
    
    public Object luceneToIndexValue(final Object value) {
        final Timestamp ts = this.convertToTimestamp(value);
        return ts.getTime();
    }
    
    public Object readData(final DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        final long val = dataInput.readLong();
        final Timestamp ts = new Timestamp(val);
        return this.convertFromTimestamp(ts);
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        if (v == null) {
            dataOutput.writeBoolean(false);
        }
        else {
            dataOutput.writeBoolean(true);
            final Timestamp ts = this.convertToTimestamp(v);
            dataOutput.writeLong(ts.getTime());
        }
    }
}
