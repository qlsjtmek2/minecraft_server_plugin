// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

public class Constants
{
    public static final byte[] EMPTY_BYTE_ARRAY;
    public static final String MILLIS_I18N;
    public static final byte[] SLASH_STAR_SPACE_AS_BYTES;
    public static final byte[] SPACE_STAR_SLASH_SPACE_AS_BYTES;
    private static final Character[] CHARACTER_CACHE;
    private static final int BYTE_CACHE_OFFSET = 128;
    private static final Byte[] BYTE_CACHE;
    private static final int INTEGER_CACHE_OFFSET = 128;
    private static final Integer[] INTEGER_CACHE;
    private static final int SHORT_CACHE_OFFSET = 128;
    private static final Short[] SHORT_CACHE;
    private static final Long[] LONG_CACHE;
    private static final int LONG_CACHE_OFFSET = 128;
    
    public static Character characterValueOf(final char c) {
        if (c <= '\u007f') {
            return Constants.CHARACTER_CACHE[c];
        }
        return new Character(c);
    }
    
    public static final Byte byteValueOf(final byte b) {
        return Constants.BYTE_CACHE[b + 128];
    }
    
    public static final Integer integerValueOf(final int i) {
        if (i >= -128 && i <= 127) {
            return Constants.INTEGER_CACHE[i + 128];
        }
        return new Integer(i);
    }
    
    public static Short shortValueOf(final short s) {
        if (s >= -128 && s <= 127) {
            return Constants.SHORT_CACHE[s + 128];
        }
        return new Short(s);
    }
    
    public static final Long longValueOf(final long l) {
        if (l >= -128L && l <= 127L) {
            return Constants.LONG_CACHE[(int)l + 128];
        }
        return new Long(l);
    }
    
    static {
        EMPTY_BYTE_ARRAY = new byte[0];
        MILLIS_I18N = Messages.getString("Milliseconds");
        SLASH_STAR_SPACE_AS_BYTES = new byte[] { 47, 42, 32 };
        SPACE_STAR_SLASH_SPACE_AS_BYTES = new byte[] { 32, 42, 47, 32 };
        CHARACTER_CACHE = new Character[128];
        BYTE_CACHE = new Byte[256];
        INTEGER_CACHE = new Integer[256];
        SHORT_CACHE = new Short[256];
        LONG_CACHE = new Long[256];
        for (int i = 0; i < Constants.CHARACTER_CACHE.length; ++i) {
            Constants.CHARACTER_CACHE[i] = new Character((char)i);
        }
        for (int i = 0; i < Constants.INTEGER_CACHE.length; ++i) {
            Constants.INTEGER_CACHE[i] = new Integer(i - 128);
        }
        for (int i = 0; i < Constants.SHORT_CACHE.length; ++i) {
            Constants.SHORT_CACHE[i] = new Short((short)(i - 128));
        }
        for (int i = 0; i < Constants.LONG_CACHE.length; ++i) {
            Constants.LONG_CACHE[i] = new Long(i - 128);
        }
        for (int i = 0; i < Constants.BYTE_CACHE.length; ++i) {
            Constants.BYTE_CACHE[i] = new Byte((byte)(i - 128));
        }
    }
}
