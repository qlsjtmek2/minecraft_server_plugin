// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

public class ScalarTypeCalendar extends ScalarTypeBaseDateTime<Calendar>
{
    public ScalarTypeCalendar(final int jdbcType) {
        super(Calendar.class, false, jdbcType);
    }
    
    public void bind(final DataBind b, final Calendar value) throws SQLException {
        if (value == null) {
            b.setNull(93);
        }
        else if (this.jdbcType == 93) {
            final Timestamp timestamp = new Timestamp(value.getTimeInMillis());
            b.setTimestamp(timestamp);
        }
        else {
            final Date d = new Date(value.getTimeInMillis());
            b.setDate(d);
        }
    }
    
    public Calendar convertFromTimestamp(final Timestamp ts) {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(ts.getTime());
        return calendar;
    }
    
    public Timestamp convertToTimestamp(final Calendar t) {
        return new Timestamp(t.getTimeInMillis());
    }
    
    public Object toJdbcType(final Object value) {
        return BasicTypeConverter.convert(value, this.jdbcType);
    }
    
    public Calendar toBeanType(final Object value) {
        return BasicTypeConverter.toCalendar(value);
    }
}
