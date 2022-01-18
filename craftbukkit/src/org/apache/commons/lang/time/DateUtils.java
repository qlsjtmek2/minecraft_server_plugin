// 
// Decompiled by Procyon v0.5.30
// 

package org.apache.commons.lang.time;

import java.util.NoSuchElementException;
import java.util.Iterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils
{
    public static final TimeZone UTC_TIME_ZONE;
    public static final long MILLIS_PER_SECOND = 1000L;
    public static final long MILLIS_PER_MINUTE = 60000L;
    public static final long MILLIS_PER_HOUR = 3600000L;
    public static final long MILLIS_PER_DAY = 86400000L;
    public static final int SEMI_MONTH = 1001;
    private static final int[][] fields;
    public static final int RANGE_WEEK_SUNDAY = 1;
    public static final int RANGE_WEEK_MONDAY = 2;
    public static final int RANGE_WEEK_RELATIVE = 3;
    public static final int RANGE_WEEK_CENTER = 4;
    public static final int RANGE_MONTH_SUNDAY = 5;
    public static final int RANGE_MONTH_MONDAY = 6;
    public static final int MILLIS_IN_SECOND = 1000;
    public static final int MILLIS_IN_MINUTE = 60000;
    public static final int MILLIS_IN_HOUR = 3600000;
    public static final int MILLIS_IN_DAY = 86400000;
    
    public static boolean isSameDay(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }
    
    public static boolean isSameDay(final Calendar cal1, final Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6);
    }
    
    public static boolean isSameInstant(final Date date1, final Date date2) {
        if (date1 == null || date2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return date1.getTime() == date2.getTime();
    }
    
    public static boolean isSameInstant(final Calendar cal1, final Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return cal1.getTime().getTime() == cal2.getTime().getTime();
    }
    
    public static boolean isSameLocalTime(final Calendar cal1, final Calendar cal2) {
        if (cal1 == null || cal2 == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        return cal1.get(14) == cal2.get(14) && cal1.get(13) == cal2.get(13) && cal1.get(12) == cal2.get(12) && cal1.get(10) == cal2.get(10) && cal1.get(6) == cal2.get(6) && cal1.get(1) == cal2.get(1) && cal1.get(0) == cal2.get(0) && cal1.getClass() == cal2.getClass();
    }
    
    public static Date parseDate(final String str, final String[] parsePatterns) throws ParseException {
        if (str == null || parsePatterns == null) {
            throw new IllegalArgumentException("Date and Patterns must not be null");
        }
        SimpleDateFormat parser = null;
        final ParsePosition pos = new ParsePosition(0);
        for (int i = 0; i < parsePatterns.length; ++i) {
            if (i == 0) {
                parser = new SimpleDateFormat(parsePatterns[0]);
            }
            else {
                parser.applyPattern(parsePatterns[i]);
            }
            pos.setIndex(0);
            final Date date = parser.parse(str, pos);
            if (date != null && pos.getIndex() == str.length()) {
                return date;
            }
        }
        throw new ParseException("Unable to parse the date: " + str, -1);
    }
    
    public static Date addYears(final Date date, final int amount) {
        return add(date, 1, amount);
    }
    
    public static Date addMonths(final Date date, final int amount) {
        return add(date, 2, amount);
    }
    
    public static Date addWeeks(final Date date, final int amount) {
        return add(date, 3, amount);
    }
    
    public static Date addDays(final Date date, final int amount) {
        return add(date, 5, amount);
    }
    
    public static Date addHours(final Date date, final int amount) {
        return add(date, 11, amount);
    }
    
    public static Date addMinutes(final Date date, final int amount) {
        return add(date, 12, amount);
    }
    
    public static Date addSeconds(final Date date, final int amount) {
        return add(date, 13, amount);
    }
    
    public static Date addMilliseconds(final Date date, final int amount) {
        return add(date, 14, amount);
    }
    
    public static Date add(final Date date, final int calendarField, final int amount) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(calendarField, amount);
        return c.getTime();
    }
    
    public static Date round(final Date date, final int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar gval = Calendar.getInstance();
        gval.setTime(date);
        modify(gval, field, true);
        return gval.getTime();
    }
    
    public static Calendar round(final Calendar date, final int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar rounded = (Calendar)date.clone();
        modify(rounded, field, true);
        return rounded;
    }
    
    public static Date round(final Object date, final int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (date instanceof Date) {
            return round((Date)date, field);
        }
        if (date instanceof Calendar) {
            return round((Calendar)date, field).getTime();
        }
        throw new ClassCastException("Could not round " + date);
    }
    
    public static Date truncate(final Date date, final int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar gval = Calendar.getInstance();
        gval.setTime(date);
        modify(gval, field, false);
        return gval.getTime();
    }
    
    public static Calendar truncate(final Calendar date, final int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar truncated = (Calendar)date.clone();
        modify(truncated, field, false);
        return truncated;
    }
    
    public static Date truncate(final Object date, final int field) {
        if (date == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (date instanceof Date) {
            return truncate((Date)date, field);
        }
        if (date instanceof Calendar) {
            return truncate((Calendar)date, field).getTime();
        }
        throw new ClassCastException("Could not truncate " + date);
    }
    
    private static void modify(final Calendar val, final int field, final boolean round) {
        if (val.get(1) > 280000000) {
            throw new ArithmeticException("Calendar value too large for accurate calculations");
        }
        if (field == 14) {
            return;
        }
        final Date date = val.getTime();
        long time = date.getTime();
        boolean done = false;
        final int millisecs = val.get(14);
        if (!round || millisecs < 500) {
            time -= millisecs;
            if (field == 13) {
                done = true;
            }
        }
        final int seconds = val.get(13);
        if (!done && (!round || seconds < 30)) {
            time -= seconds * 1000L;
            if (field == 12) {
                done = true;
            }
        }
        final int minutes = val.get(12);
        if (!done && (!round || minutes < 30)) {
            time -= minutes * 60000L;
        }
        if (date.getTime() != time) {
            date.setTime(time);
            val.setTime(date);
        }
        boolean roundUp = false;
        for (int i = 0; i < DateUtils.fields.length; ++i) {
            for (int j = 0; j < DateUtils.fields[i].length; ++j) {
                if (DateUtils.fields[i][j] == field) {
                    if (round && roundUp) {
                        if (field == 1001) {
                            if (val.get(5) == 1) {
                                val.add(5, 15);
                            }
                            else {
                                val.add(5, -15);
                                val.add(2, 1);
                            }
                        }
                        else {
                            val.add(DateUtils.fields[i][0], 1);
                        }
                    }
                    return;
                }
            }
            int offset = 0;
            boolean offsetSet = false;
            switch (field) {
                case 1001: {
                    if (DateUtils.fields[i][0] == 5) {
                        offset = val.get(5) - 1;
                        if (offset >= 15) {
                            offset -= 15;
                        }
                        roundUp = (offset > 7);
                        offsetSet = true;
                        break;
                    }
                    break;
                }
                case 9: {
                    if (DateUtils.fields[i][0] == 11) {
                        offset = val.get(11);
                        if (offset >= 12) {
                            offset -= 12;
                        }
                        roundUp = (offset > 6);
                        offsetSet = true;
                        break;
                    }
                    break;
                }
            }
            if (!offsetSet) {
                final int min = val.getActualMinimum(DateUtils.fields[i][0]);
                final int max = val.getActualMaximum(DateUtils.fields[i][0]);
                offset = val.get(DateUtils.fields[i][0]) - min;
                roundUp = (offset > (max - min) / 2);
            }
            if (offset != 0) {
                val.set(DateUtils.fields[i][0], val.get(DateUtils.fields[i][0]) - offset);
            }
        }
        throw new IllegalArgumentException("The field " + field + " is not supported");
    }
    
    public static Iterator iterator(final Date focus, final int rangeStyle) {
        if (focus == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        final Calendar gval = Calendar.getInstance();
        gval.setTime(focus);
        return iterator(gval, rangeStyle);
    }
    
    public static Iterator iterator(final Calendar focus, final int rangeStyle) {
        if (focus == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        Calendar start = null;
        Calendar end = null;
        int startCutoff = 1;
        int endCutoff = 7;
        switch (rangeStyle) {
            case 5:
            case 6: {
                start = truncate(focus, 2);
                end = (Calendar)start.clone();
                end.add(2, 1);
                end.add(5, -1);
                if (rangeStyle == 6) {
                    startCutoff = 2;
                    endCutoff = 1;
                    break;
                }
                break;
            }
            case 1:
            case 2:
            case 3:
            case 4: {
                start = truncate(focus, 5);
                end = truncate(focus, 5);
                switch (rangeStyle) {
                    case 2: {
                        startCutoff = 2;
                        endCutoff = 1;
                        break;
                    }
                    case 3: {
                        startCutoff = focus.get(7);
                        endCutoff = startCutoff - 1;
                        break;
                    }
                    case 4: {
                        startCutoff = focus.get(7) - 3;
                        endCutoff = focus.get(7) + 3;
                        break;
                    }
                }
                break;
            }
            default: {
                throw new IllegalArgumentException("The range style " + rangeStyle + " is not valid.");
            }
        }
        if (startCutoff < 1) {
            startCutoff += 7;
        }
        if (startCutoff > 7) {
            startCutoff -= 7;
        }
        if (endCutoff < 1) {
            endCutoff += 7;
        }
        if (endCutoff > 7) {
            endCutoff -= 7;
        }
        while (start.get(7) != startCutoff) {
            start.add(5, -1);
        }
        while (end.get(7) != endCutoff) {
            end.add(5, 1);
        }
        return new DateIterator(start, end);
    }
    
    public static Iterator iterator(final Object focus, final int rangeStyle) {
        if (focus == null) {
            throw new IllegalArgumentException("The date must not be null");
        }
        if (focus instanceof Date) {
            return iterator((Date)focus, rangeStyle);
        }
        if (focus instanceof Calendar) {
            return iterator((Calendar)focus, rangeStyle);
        }
        throw new ClassCastException("Could not iterate based on " + focus);
    }
    
    static {
        UTC_TIME_ZONE = TimeZone.getTimeZone("GMT");
        fields = new int[][] { { 14 }, { 13 }, { 12 }, { 11, 10 }, { 5, 5, 9 }, { 2, 1001 }, { 1 }, { 0 } };
    }
    
    static class DateIterator implements Iterator
    {
        private final Calendar endFinal;
        private final Calendar spot;
        
        DateIterator(final Calendar startFinal, final Calendar endFinal) {
            this.endFinal = endFinal;
            (this.spot = startFinal).add(5, -1);
        }
        
        public boolean hasNext() {
            return this.spot.before(this.endFinal);
        }
        
        public Object next() {
            if (this.spot.equals(this.endFinal)) {
                throw new NoSuchElementException();
            }
            this.spot.add(5, 1);
            return this.spot.clone();
        }
        
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
