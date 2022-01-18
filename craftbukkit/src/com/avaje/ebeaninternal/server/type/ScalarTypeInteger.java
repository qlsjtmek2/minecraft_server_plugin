// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebean.text.TextException;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import java.sql.SQLException;

public class ScalarTypeInteger extends ScalarTypeBase<Integer>
{
    public ScalarTypeInteger() {
        super(Integer.class, true, 4);
    }
    
    public void bind(final DataBind b, final Integer value) throws SQLException {
        if (value == null) {
            b.setNull(4);
        }
        else {
            b.setInt(value);
        }
    }
    
    public Integer read(final DataReader dataReader) throws SQLException {
        return dataReader.getInt();
    }
    
    public Object readData(final DataInput dataInput) throws IOException {
        return dataInput.readInt();
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        dataOutput.writeInt((int)v);
    }
    
    public Object toJdbcType(final Object value) {
        return BasicTypeConverter.toInteger(value);
    }
    
    public Integer toBeanType(final Object value) {
        return BasicTypeConverter.toInteger(value);
    }
    
    public String formatValue(final Integer v) {
        return v.toString();
    }
    
    public Integer parse(final String value) {
        return Integer.valueOf(value);
    }
    
    public Integer parseDateTime(final long systemTimeMillis) {
        throw new TextException("Not Supported");
    }
    
    public boolean isDateTimeCapable() {
        return false;
    }
    
    public String jsonToString(final Integer value, final JsonValueAdapter ctx) {
        return value.toString();
    }
    
    public Integer jsonFromString(final String value, final JsonValueAdapter ctx) {
        return Integer.valueOf(value);
    }
    
    public int getLuceneType() {
        return 1;
    }
    
    public Object luceneFromIndexValue(final Object value) {
        return value;
    }
    
    public Object luceneToIndexValue(final Object value) {
        return value;
    }
}
