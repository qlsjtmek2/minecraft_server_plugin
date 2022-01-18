// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.io.DataOutput;
import java.io.IOException;
import java.io.DataInput;
import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.sql.SQLException;

public class ScalarTypeString extends ScalarTypeBase<String>
{
    public ScalarTypeString() {
        super(String.class, true, 12);
    }
    
    public void bind(final DataBind b, final String value) throws SQLException {
        if (value == null) {
            b.setNull(12);
        }
        else {
            b.setString(value);
        }
    }
    
    public String read(final DataReader dataReader) throws SQLException {
        return dataReader.getString();
    }
    
    public Object toJdbcType(final Object value) {
        return BasicTypeConverter.toString(value);
    }
    
    public String toBeanType(final Object value) {
        return BasicTypeConverter.toString(value);
    }
    
    public String formatValue(final String t) {
        return t;
    }
    
    public String parse(final String value) {
        return value;
    }
    
    public String parseDateTime(final long systemTimeMillis) {
        return String.valueOf(systemTimeMillis);
    }
    
    public boolean isDateTimeCapable() {
        return true;
    }
    
    public String jsonFromString(final String value, final JsonValueAdapter ctx) {
        return value;
    }
    
    public String jsonToString(final String value, final JsonValueAdapter ctx) {
        return EscapeJson.escapeQuote(value);
    }
    
    public int getLuceneType() {
        return 0;
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
        return dataInput.readUTF();
    }
    
    public void writeData(final DataOutput dataOutput, final Object v) throws IOException {
        final String value = (String)v;
        if (value == null) {
            dataOutput.writeBoolean(false);
        }
        else {
            dataOutput.writeBoolean(true);
            dataOutput.writeUTF(value);
        }
    }
}
