// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.json.JsonValueAdapter;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;

public class ScalarTypeCharArray extends ScalarTypeBaseVarchar<char[]>
{
    public ScalarTypeCharArray() {
        super(char[].class, false, 12);
    }
    
    public char[] convertFromDbString(final String dbValue) {
        return dbValue.toCharArray();
    }
    
    public String convertToDbString(final char[] beanValue) {
        return new String(beanValue);
    }
    
    public void bind(final DataBind b, final char[] value) throws SQLException {
        if (value == null) {
            b.setNull(12);
        }
        else {
            final String s = BasicTypeConverter.toString(value);
            b.setString(s);
        }
    }
    
    public char[] read(final DataReader dataReader) throws SQLException {
        final String string = dataReader.getString();
        if (string == null) {
            return null;
        }
        return string.toCharArray();
    }
    
    public Object toJdbcType(final Object value) {
        return BasicTypeConverter.toString(value);
    }
    
    public char[] toBeanType(final Object value) {
        final String s = BasicTypeConverter.toString(value);
        return s.toCharArray();
    }
    
    public String formatValue(final char[] t) {
        return String.valueOf(t);
    }
    
    public char[] parse(final String value) {
        return value.toCharArray();
    }
    
    public char[] jsonFromString(final String value, final JsonValueAdapter ctx) {
        return value.toCharArray();
    }
    
    public String jsonToString(final char[] value, final JsonValueAdapter ctx) {
        return EscapeJson.escapeQuote(String.valueOf(value));
    }
}
