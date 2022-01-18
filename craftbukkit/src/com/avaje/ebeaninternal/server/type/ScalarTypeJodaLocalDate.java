// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.sql.Date;
import org.joda.time.LocalDate;

public class ScalarTypeJodaLocalDate extends ScalarTypeBaseDate<LocalDate>
{
    public ScalarTypeJodaLocalDate() {
        super(LocalDate.class, false, 91);
    }
    
    public LocalDate convertFromDate(final Date ts) {
        return new LocalDate(ts.getTime());
    }
    
    public Date convertToDate(final LocalDate t) {
        return new Date(t.toDateMidnight().getMillis());
    }
    
    public Object toJdbcType(final Object value) {
        if (value instanceof LocalDate) {
            return new Date(((LocalDate)value).toDateMidnight().getMillis());
        }
        return BasicTypeConverter.toDate(value);
    }
    
    public LocalDate toBeanType(final Object value) {
        if (value instanceof java.util.Date) {
            return new LocalDate(((java.util.Date)value).getTime());
        }
        return (LocalDate)value;
    }
    
    public LocalDate parseDateTime(final long systemTimeMillis) {
        return new LocalDate(systemTimeMillis);
    }
}
