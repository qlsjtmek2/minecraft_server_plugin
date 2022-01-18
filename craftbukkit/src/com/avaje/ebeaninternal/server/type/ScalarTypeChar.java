// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.json.JsonValueAdapter;
import java.sql.SQLException;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;

public class ScalarTypeChar extends ScalarTypeBaseVarchar<Character>
{
    public ScalarTypeChar() {
        super(Character.TYPE, false, 12);
    }
    
    public Character convertFromDbString(final String dbValue) {
        return dbValue.charAt(0);
    }
    
    public String convertToDbString(final Character beanValue) {
        return beanValue.toString();
    }
    
    public void bind(final DataBind b, final Character value) throws SQLException {
        if (value == null) {
            b.setNull(12);
        }
        else {
            final String s = BasicTypeConverter.toString(value);
            b.setString(s);
        }
    }
    
    public Character read(final DataReader dataReader) throws SQLException {
        final String string = dataReader.getString();
        if (string == null || string.length() == 0) {
            return null;
        }
        return string.charAt(0);
    }
    
    public Object toJdbcType(final Object value) {
        return BasicTypeConverter.toString(value);
    }
    
    public Character toBeanType(final Object value) {
        final String s = BasicTypeConverter.toString(value);
        return s.charAt(0);
    }
    
    public String formatValue(final Character t) {
        return t.toString();
    }
    
    public Character parse(final String value) {
        return value.charAt(0);
    }
    
    public Character jsonFromString(final String value, final JsonValueAdapter ctx) {
        return value.charAt(0);
    }
    
    public String jsonToString(final Character value, final JsonValueAdapter ctx) {
        return EscapeJson.escapeQuote(value.toString());
    }
}
