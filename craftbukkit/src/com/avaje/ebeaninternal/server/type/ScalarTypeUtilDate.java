// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import com.avaje.ebeaninternal.server.core.BasicTypeConverter;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class ScalarTypeUtilDate
{
    public static class TimestampType extends ScalarTypeBaseDateTime<Date>
    {
        public TimestampType() {
            super(Date.class, false, 93);
        }
        
        public Date read(final DataReader dataReader) throws SQLException {
            final Timestamp timestamp = dataReader.getTimestamp();
            if (timestamp == null) {
                return null;
            }
            return new Date(timestamp.getTime());
        }
        
        public void bind(final DataBind b, final Date value) throws SQLException {
            if (value == null) {
                b.setNull(93);
            }
            else {
                final Timestamp timestamp = new Timestamp(value.getTime());
                b.setTimestamp(timestamp);
            }
        }
        
        public Object toJdbcType(final Object value) {
            return BasicTypeConverter.toTimestamp(value);
        }
        
        public Date toBeanType(final Object value) {
            return BasicTypeConverter.toUtilDate(value);
        }
        
        public Date convertFromTimestamp(final Timestamp ts) {
            return new Date(ts.getTime());
        }
        
        public Timestamp convertToTimestamp(final Date t) {
            return new Timestamp(t.getTime());
        }
        
        public Date parseDateTime(final long systemTimeMillis) {
            return new Date(systemTimeMillis);
        }
        
        public Object luceneFromIndexValue(final Object value) {
            final Long l = (Long)value;
            return new Date(l);
        }
        
        public Object luceneToIndexValue(final Object value) {
            return ((Date)value).getTime();
        }
    }
    
    public static class DateType extends ScalarTypeBaseDate<Date>
    {
        public DateType() {
            super(Date.class, false, 91);
        }
        
        public Date convertFromDate(final java.sql.Date ts) {
            return new Date(ts.getTime());
        }
        
        public java.sql.Date convertToDate(final Date t) {
            return new java.sql.Date(t.getTime());
        }
        
        public Object toJdbcType(final Object value) {
            return BasicTypeConverter.toDate(value);
        }
        
        public Date toBeanType(final Object value) {
            return BasicTypeConverter.toUtilDate(value);
        }
    }
}
