// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.sql.SQLException;
import java.sql.Date;

public class ScalarTypeDate extends ScalarTypeBaseDate<Date>
{
    public ScalarTypeDate() {
        super(Date.class, true, 91);
    }
    
    public Date convertFromDate(final Date date) {
        return date;
    }
    
    public Date convertToDate(final Date t) {
        return t;
    }
    
    public void bind(final DataBind b, final Date value) throws SQLException {
        if (value == null) {
            b.setNull(91);
        }
        else {
            b.setDate(value);
        }
    }
    
    public Date read(final DataReader dataReader) throws SQLException {
        return dataReader.getDate();
    }
    
    public Object toJdbcType(final Object value) {
        return BasicTypeConverter.toDate(value);
    }
    
    public Date toBeanType(final Object value) {
        return BasicTypeConverter.toDate(value);
    }
}
