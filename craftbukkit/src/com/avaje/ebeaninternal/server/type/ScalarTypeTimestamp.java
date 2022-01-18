// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.sql.SQLException;
import java.sql.Timestamp;

public class ScalarTypeTimestamp extends ScalarTypeBaseDateTime<Timestamp>
{
    public ScalarTypeTimestamp() {
        super(Timestamp.class, true, 93);
    }
    
    public Timestamp convertFromTimestamp(final Timestamp ts) {
        return ts;
    }
    
    public Timestamp convertToTimestamp(final Timestamp t) {
        return t;
    }
    
    public void bind(final DataBind b, final Timestamp value) throws SQLException {
        if (value == null) {
            b.setNull(93);
        }
        else {
            b.setTimestamp(value);
        }
    }
    
    public Timestamp read(final DataReader dataReader) throws SQLException {
        return dataReader.getTimestamp();
    }
    
    public Object toJdbcType(final Object value) {
        return BasicTypeConverter.toTimestamp(value);
    }
    
    public Timestamp toBeanType(final Object value) {
        return BasicTypeConverter.toTimestamp(value);
    }
}
