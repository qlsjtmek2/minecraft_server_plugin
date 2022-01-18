// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import java.sql.Time;
import java.sql.Timestamp;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.math.BigDecimal;
import java.util.UUID;
import java.io.Serializable;

public final class BasicTypeConverter implements Serializable
{
    private static final long serialVersionUID = 7691463236204070311L;
    public static final int UTIL_CALENDAR = -999998986;
    public static final int UTIL_DATE = -999998988;
    public static final int MATH_BIGINTEGER = -999998987;
    public static final int ENUM = -999998989;
    
    public static Object convert(final Object value, final int toDataType) {
        try {
            switch (toDataType) {
                case -999998988: {
                    return toUtilDate(value);
                }
                case -999998986: {
                    return toCalendar(value);
                }
                case -5: {
                    return toLong(value);
                }
                case 4: {
                    return toInteger(value);
                }
                case -7: {
                    return toBoolean(value);
                }
                case -6: {
                    return toByte(value);
                }
                case 5: {
                    return toShort(value);
                }
                case 2: {
                    return toBigDecimal(value);
                }
                case 3: {
                    return toBigDecimal(value);
                }
                case 7: {
                    return toFloat(value);
                }
                case 8: {
                    return toDouble(value);
                }
                case 6: {
                    return toDouble(value);
                }
                case 16: {
                    return toBoolean(value);
                }
                case 93: {
                    return toTimestamp(value);
                }
                case 91: {
                    return toDate(value);
                }
                case 12: {
                    return toString(value);
                }
                case 1: {
                    return toString(value);
                }
                case 1111: {
                    return value;
                }
                case 2000: {
                    return value;
                }
                case -4:
                case -2:
                case 2004: {
                    return value;
                }
                case -1:
                case 2005: {
                    return value;
                }
                default: {
                    final String msg = "Unhandled data type [" + toDataType + "] converting [" + value + "]";
                    throw new RuntimeException(msg);
                }
            }
        }
        catch (ClassCastException e) {
            final String m = "ClassCastException converting to data type [" + toDataType + "] value [" + value + "]";
            throw new RuntimeException(m);
        }
    }
    
    public static String toString(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String)value;
        }
        if (value instanceof char[]) {
            return String.valueOf((char[])value);
        }
        return value.toString();
    }
    
    public static Boolean toBoolean(final Object value, final String dbTrueValue) {
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean) {
            return (Boolean)value;
        }
        final String s = value.toString();
        return s.equalsIgnoreCase(dbTrueValue);
    }
    
    public static Boolean toBoolean(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean) {
            return (Boolean)value;
        }
        return Boolean.valueOf(value.toString());
    }
    
    public static UUID toUUID(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return UUID.fromString((String)value);
        }
        return (UUID)value;
    }
    
    public static BigDecimal toBigDecimal(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal)value;
        }
        return new BigDecimal(value.toString());
    }
    
    public static Float toFloat(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Float) {
            return (Float)value;
        }
        if (value instanceof Number) {
            return ((Number)value).floatValue();
        }
        return Float.valueOf(value.toString());
    }
    
    public static Short toShort(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Short) {
            return (Short)value;
        }
        if (value instanceof Number) {
            return ((Number)value).shortValue();
        }
        return Short.valueOf(value.toString());
    }
    
    public static Byte toByte(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Byte) {
            return (Byte)value;
        }
        return Byte.valueOf(value.toString());
    }
    
    public static Integer toInteger(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return (Integer)value;
        }
        if (value instanceof Number) {
            return ((Number)value).intValue();
        }
        return Integer.valueOf(value.toString());
    }
    
    public static Long toLong(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Long) {
            return (Long)value;
        }
        if (value instanceof String) {
            return Long.valueOf((String)value);
        }
        if (value instanceof Number) {
            return ((Number)value).longValue();
        }
        if (value instanceof Date) {
            return ((Date)value).getTime();
        }
        if (value instanceof Calendar) {
            return ((Calendar)value).getTime().getTime();
        }
        return Long.valueOf(value.toString());
    }
    
    public static BigInteger toMathBigInteger(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigInteger) {
            return (BigInteger)value;
        }
        return new BigInteger(value.toString());
    }
    
    public static Double toDouble(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Double) {
            return (Double)value;
        }
        if (value instanceof Number) {
            return ((Number)value).doubleValue();
        }
        return Double.valueOf(value.toString());
    }
    
    public static Timestamp toTimestamp(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Timestamp) {
            return (Timestamp)value;
        }
        if (value instanceof Date) {
            return new Timestamp(((Date)value).getTime());
        }
        if (value instanceof Calendar) {
            return new Timestamp(((Calendar)value).getTime().getTime());
        }
        if (value instanceof String) {
            return Timestamp.valueOf((String)value);
        }
        if (value instanceof Number) {
            return new Timestamp(((Number)value).longValue());
        }
        final String msg = "Unable to convert [" + value.getClass().getName() + "] into a Timestamp.";
        throw new RuntimeException(msg);
    }
    
    public static Time toTime(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Time) {
            return (Time)value;
        }
        if (value instanceof String) {
            return Time.valueOf((String)value);
        }
        final String m = "Unable to convert [" + value.getClass().getName() + "] into a java.sql.Date.";
        throw new RuntimeException(m);
    }
    
    public static java.sql.Date toDate(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof java.sql.Date) {
            return (java.sql.Date)value;
        }
        if (value instanceof Date) {
            return new java.sql.Date(((Date)value).getTime());
        }
        if (value instanceof Calendar) {
            return new java.sql.Date(((Calendar)value).getTime().getTime());
        }
        if (value instanceof String) {
            return java.sql.Date.valueOf((String)value);
        }
        if (value instanceof Number) {
            return new java.sql.Date(((Number)value).longValue());
        }
        final String m = "Unable to convert [" + value.getClass().getName() + "] into a java.sql.Date.";
        throw new RuntimeException(m);
    }
    
    public static Date toUtilDate(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Timestamp) {
            return new Date(((Timestamp)value).getTime());
        }
        if (value instanceof java.sql.Date) {
            return new Date(((java.sql.Date)value).getTime());
        }
        if (value instanceof Date) {
            return (Date)value;
        }
        if (value instanceof Calendar) {
            return ((Calendar)value).getTime();
        }
        if (value instanceof String) {
            return new Date(Timestamp.valueOf((String)value).getTime());
        }
        if (value instanceof Number) {
            return new Date(((Number)value).longValue());
        }
        throw new RuntimeException("Unable to convert [" + value.getClass().getName() + "] into a java.util.Date");
    }
    
    public static Calendar toCalendar(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Calendar) {
            return (Calendar)value;
        }
        if (value instanceof Date) {
            final Date date = (Date)value;
            return toCalendarFromDate(date);
        }
        if (value instanceof String) {
            final Date date = toUtilDate(value);
            return toCalendarFromDate(date);
        }
        if (value instanceof Number) {
            final long timeMillis = ((Number)value).longValue();
            final Date date2 = new Date(timeMillis);
            return toCalendarFromDate(date2);
        }
        final String m = "Unable to convert [" + value.getClass().getName() + "] into a java.util.Date";
        throw new RuntimeException(m);
    }
    
    private static Calendar toCalendarFromDate(final Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
