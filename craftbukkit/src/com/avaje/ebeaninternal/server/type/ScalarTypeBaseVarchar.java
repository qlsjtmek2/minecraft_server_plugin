// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebean.text.TextException;
import java.sql.SQLException;

public abstract class ScalarTypeBaseVarchar<T> extends ScalarTypeBase<T>
{
    public ScalarTypeBaseVarchar(final Class<T> type) {
        super(type, false, 12);
    }
    
    public ScalarTypeBaseVarchar(final Class<T> type, final boolean jdbcNative, final int jdbcType) {
        super(type, jdbcNative, jdbcType);
    }
    
    public abstract String formatValue(final T p0);
    
    public abstract T parse(final String p0);
    
    public abstract T convertFromDbString(final String p0);
    
    public abstract String convertToDbString(final T p0);
    
    public void bind(final DataBind b, final T value) throws SQLException {
        if (value == null) {
            b.setNull(12);
        }
        else {
            final String s = this.convertToDbString(value);
            b.setString(s);
        }
    }
    
    public T read(final DataReader dataReader) throws SQLException {
        final String s = dataReader.getString();
        if (s == null) {
            return null;
        }
        return this.convertFromDbString(s);
    }
    
    public T toBeanType(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return this.parse((String)value);
        }
        return (T)value;
    }
    
    public Object toJdbcType(final Object value) {
        if (value instanceof String) {
            return this.parse((String)value);
        }
        return value;
    }
    
    public T parseDateTime(final long systemTimeMillis) {
        throw new TextException("Not Supported");
    }
    
    public boolean isDateTimeCapable() {
        return false;
    }
    
    public String format(final Object v) {
        return this.formatValue(v);
    }
    
    public T jsonFromString(final String value, final JsonValueAdapter ctx) {
        return this.parse(value);
    }
    
    public String toJsonString(final Object value, final JsonValueAdapter ctx) {
        final String s = this.format(value);
        return EscapeJson.escapeQuote(s);
    }
    
    public Object luceneFromIndexValue(final Object value) {
        final String v = (String)value;
        return this.convertFromDbString(v);
    }
    
    public Object luceneToIndexValue(final Object value) {
        return this.convertToDbString(value);
    }
    
    public int getLuceneType() {
        return 0;
    }
    
    public Object readData(final DataInput dataInput) throws IOException {
        if (!dataInput.readBoolean()) {
            return null;
        }
        final String val = dataInput.readUTF();
        return this.convertFromDbString(val);
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        if (v == null) {
            dataOutput.writeBoolean(false);
        }
        else {
            dataOutput.writeBoolean(true);
            final String s = this.convertToDbString(v);
            dataOutput.writeUTF(s);
        }
    }
}
