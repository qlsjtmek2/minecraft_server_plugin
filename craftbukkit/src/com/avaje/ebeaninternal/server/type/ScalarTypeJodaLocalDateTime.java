// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.util.Date;
import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.sql.Timestamp;
import org.joda.time.LocalDateTime;

public class ScalarTypeJodaLocalDateTime extends ScalarTypeBaseDateTime<LocalDateTime>
{
    public ScalarTypeJodaLocalDateTime() {
        super(LocalDateTime.class, false, 93);
    }
    
    public LocalDateTime convertFromTimestamp(final Timestamp ts) {
        return new LocalDateTime(ts.getTime());
    }
    
    public Timestamp convertToTimestamp(final LocalDateTime t) {
        return new Timestamp(t.toDateTime().getMillis());
    }
    
    public Object toJdbcType(final Object value) {
        if (value instanceof LocalDateTime) {
            return new Timestamp(((LocalDateTime)value).toDateTime().getMillis());
        }
        return BasicTypeConverter.toTimestamp(value);
    }
    
    public LocalDateTime toBeanType(final Object value) {
        if (value instanceof Date) {
            return new LocalDateTime(((Date)value).getTime());
        }
        return (LocalDateTime)value;
    }
    
    public LocalDateTime parseDateTime(final long systemTimeMillis) {
        return new LocalDateTime(systemTimeMillis);
    }
}
