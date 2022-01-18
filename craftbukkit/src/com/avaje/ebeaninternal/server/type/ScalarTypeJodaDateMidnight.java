// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.sql.Date;
import org.joda.time.DateMidnight;

public class ScalarTypeJodaDateMidnight extends ScalarTypeBaseDate<DateMidnight>
{
    public ScalarTypeJodaDateMidnight() {
        super(DateMidnight.class, false, 91);
    }
    
    public DateMidnight convertFromDate(final Date ts) {
        return new DateMidnight(ts.getTime());
    }
    
    public Date convertToDate(final DateMidnight t) {
        return new Date(t.getMillis());
    }
    
    public Object toJdbcType(final Object value) {
        if (value instanceof DateMidnight) {
            return new Date(((DateMidnight)value).getMillis());
        }
        return BasicTypeConverter.toDate(value);
    }
    
    public DateMidnight toBeanType(final Object value) {
        if (value instanceof java.util.Date) {
            return new DateMidnight(((java.util.Date)value).getTime());
        }
        return (DateMidnight)value;
    }
}
