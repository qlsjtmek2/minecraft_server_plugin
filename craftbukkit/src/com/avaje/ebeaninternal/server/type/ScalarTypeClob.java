// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebean.text.json.JsonValueAdapter;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.sql.SQLException;

public class ScalarTypeClob extends ScalarTypeBaseVarchar<String>
{
    static final int clobBufferSize = 512;
    static final int stringInitialSize = 512;
    
    protected ScalarTypeClob(final boolean jdbcNative, final int jdbcType) {
        super(String.class, jdbcNative, jdbcType);
    }
    
    public ScalarTypeClob() {
        super(String.class, true, 2005);
    }
    
    public String convertFromDbString(final String dbValue) {
        return dbValue;
    }
    
    public String convertToDbString(final String beanValue) {
        return beanValue;
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
        return dataReader.getStringClob();
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
}
