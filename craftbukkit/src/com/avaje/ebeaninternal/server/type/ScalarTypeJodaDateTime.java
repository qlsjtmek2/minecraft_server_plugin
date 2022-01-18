// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.util.Date;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.sql.Timestamp;
import org.joda.time.DateTime;

public class ScalarTypeJodaDateTime extends ScalarTypeBaseDateTime<DateTime>
{
    public ScalarTypeJodaDateTime() {
        super(DateTime.class, false, 93);
    }
    
    public DateTime convertFromTimestamp(final Timestamp ts) {
        return new DateTime(ts.getTime());
    }
    
    public Timestamp convertToTimestamp(final DateTime t) {
        return new Timestamp(t.getMillis());
    }
    
    public Object toJdbcType(final Object value) {
        if (value instanceof DateTime) {
            return new Timestamp(((DateTime)value).getMillis());
        }
        return BasicTypeConverter.toTimestamp(value);
    }
    
    public DateTime toBeanType(final Object value) {
        if (value instanceof Date) {
            return new DateTime(((Date)value).getTime());
        }
        return (DateTime)value;
    }
}
