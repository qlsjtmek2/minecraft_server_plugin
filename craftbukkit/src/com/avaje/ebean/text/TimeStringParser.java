// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean.text;

import java.sql.Time;

public final class TimeStringParser implements StringParser
{
    private static final TimeStringParser SHARED;
    
    public static TimeStringParser get() {
        return TimeStringParser.SHARED;
    }
    
    public Object parse(final String value) {
        if (value == null || value.trim().length() == 0) {
            return null;
        }
        final String s = value.trim();
        final int firstColon = s.indexOf(58);
        final int secondColon = s.indexOf(58, firstColon + 1);
        if (firstColon == -1) {
            throw new IllegalArgumentException("No ':' in value [" + s + "]");
        }
        try {
            final int hour = Integer.parseInt(s.substring(0, firstColon));
            int minute;
            int second;
            if (secondColon == -1) {
                minute = Integer.parseInt(s.substring(firstColon + 1, s.length()));
                second = 0;
            }
            else {
                minute = Integer.parseInt(s.substring(firstColon + 1, secondColon));
                second = Integer.parseInt(s.substring(secondColon + 1));
            }
            return new Time(hour, minute, second);
        }
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Number format Error parsing time [" + s + "] " + e.getMessage(), e);
        }
    }
    
    static {
        SHARED = new TimeStringParser();
    }
}
