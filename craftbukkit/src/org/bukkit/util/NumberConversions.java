// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.util;

public final class NumberConversions
{
    public static int floor(final double num) {
        final int floor = (int)num;
        return (floor == num) ? floor : (floor - (int)(Double.doubleToRawLongBits(num) >>> 63));
    }
    
    public static int ceil(final double num) {
        final int floor = (int)num;
        return (floor == num) ? floor : (floor + (int)(~Double.doubleToRawLongBits(num) >>> 63));
    }
    
    public static int round(final double num) {
        return floor(num + 0.5);
    }
    
    public static int toInt(final Object object) {
        if (object instanceof Number) {
            return ((Number)object).intValue();
        }
        try {
            return Integer.valueOf(object.toString());
        }
        catch (NumberFormatException e) {}
        catch (NullPointerException ex) {}
        return 0;
    }
    
    public static float toFloat(final Object object) {
        if (object instanceof Number) {
            return ((Number)object).floatValue();
        }
        try {
            return Float.valueOf(object.toString());
        }
        catch (NumberFormatException e) {}
        catch (NullPointerException ex) {}
        return 0.0f;
    }
    
    public static double toDouble(final Object object) {
        if (object instanceof Number) {
            return ((Number)object).doubleValue();
        }
        try {
            return Double.valueOf(object.toString());
        }
        catch (NumberFormatException e) {}
        catch (NullPointerException ex) {}
        return 0.0;
    }
    
    public static long toLong(final Object object) {
        if (object instanceof Number) {
            return ((Number)object).longValue();
        }
        try {
            return Long.valueOf(object.toString());
        }
        catch (NumberFormatException e) {}
        catch (NullPointerException ex) {}
        return 0L;
    }
    
    public static short toShort(final Object object) {
        if (object instanceof Number) {
            return ((Number)object).shortValue();
        }
        try {
            return Short.valueOf(object.toString());
        }
        catch (NumberFormatException e) {}
        catch (NullPointerException ex) {}
        return 0;
    }
    
    public static byte toByte(final Object object) {
        if (object instanceof Number) {
            return ((Number)object).byteValue();
        }
        try {
            return Byte.valueOf(object.toString());
        }
        catch (NumberFormatException e) {}
        catch (NullPointerException ex) {}
        return 0;
    }
}
