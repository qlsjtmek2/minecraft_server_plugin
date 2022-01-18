// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.type;

import java.util.TimeZone;

public class ScalarTypeTimeZone extends ScalarTypeBaseVarchar<TimeZone>
{
    public ScalarTypeTimeZone() {
        super(TimeZone.class);
    }
    
    public int getLength() {
        return 20;
    }
    
    public TimeZone convertFromDbString(final String dbValue) {
        return TimeZone.getTimeZone(dbValue);
    }
    
    public String convertToDbString(final TimeZone beanValue) {
        return beanValue.getID();
    }
    
    public String formatValue(final TimeZone v) {
        return v.toString();
    }
    
    public TimeZone parse(final String value) {
        return TimeZone.getTimeZone(value);
    }
}
