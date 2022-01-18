// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.transaction.log;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class LogTime
{
    private static final String[] sep;
    private static LogTime day;
    private final String ymd;
    private final long startMidnight;
    private final long startTomorrow;
    
    public static LogTime get() {
        return LogTime.day;
    }
    
    public static LogTime nextDay() {
        final LogTime d = new LogTime();
        return LogTime.day = d;
    }
    
    public static LogTime getWithCheck() {
        final LogTime d = LogTime.day;
        if (d.isNextDay()) {
            return nextDay();
        }
        return d;
    }
    
    private LogTime() {
        final GregorianCalendar now = new GregorianCalendar();
        now.set(11, 0);
        now.set(12, 0);
        now.set(13, 0);
        now.set(14, 0);
        this.startMidnight = now.getTime().getTime();
        this.ymd = this.getDayDerived(now);
        now.add(5, 1);
        this.startTomorrow = now.getTime().getTime();
    }
    
    public boolean isNextDay() {
        return System.currentTimeMillis() >= this.startTomorrow;
    }
    
    public String getYMD() {
        return this.ymd;
    }
    
    public String getNow(final String[] separators) {
        return this.getTimestamp(System.currentTimeMillis(), separators);
    }
    
    public String getTimestamp(final long systime) {
        final StringBuilder sb = new StringBuilder();
        this.getTime(sb, systime, this.startMidnight, LogTime.sep);
        return sb.toString();
    }
    
    public String getTimestamp(final long systime, final String[] separators) {
        final StringBuilder sb = new StringBuilder();
        this.getTime(sb, systime, this.startMidnight, separators);
        return sb.toString();
    }
    
    public String getNow() {
        return this.getNow(LogTime.sep);
    }
    
    private String getDayDerived(final Calendar now) {
        final int nowyear = now.get(1);
        int nowmonth = now.get(2);
        final int nowday = now.get(5);
        ++nowmonth;
        final StringBuilder sb = new StringBuilder();
        this.format(sb, nowyear, 4);
        this.format(sb, nowmonth, 2);
        this.format(sb, nowday, 2);
        return sb.toString();
    }
    
    private void getTime(final StringBuilder sb, final long time, final long midnight, final String[] separator) {
        long rem = time - midnight;
        final long millis = rem % 1000L;
        rem /= 1000L;
        final long secs = rem % 60L;
        rem /= 60L;
        final long mins = rem % 60L;
        final long hrs;
        rem = (hrs = rem / 60L);
        this.format(sb, hrs, 2);
        sb.append(separator[0]);
        this.format(sb, mins, 2);
        sb.append(separator[0]);
        this.format(sb, secs, 2);
        sb.append(separator[1]);
        this.format(sb, millis, 3);
    }
    
    private void format(final StringBuilder sb, final long value, final int places) {
        final String format = Long.toString(value);
        for (int pad = places - format.length(), i = 0; i < pad; ++i) {
            sb.append("0");
        }
        sb.append(format);
    }
    
    static {
        sep = new String[] { ":", "." };
        LogTime.day = new LogTime();
    }
}
