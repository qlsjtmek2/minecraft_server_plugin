// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.util;

import java.lang.reflect.Array;

public class Java15Compat
{
    public static <T> T[] Arrays_copyOf(final T[] original, final int newLength) {
        if (0 <= newLength) {
            return org.bukkit.util.Java15Compat.Arrays_copyOfRange(original, 0, newLength);
        }
        throw new NegativeArraySizeException();
    }
    
    public static long[] Arrays_copyOf(final long[] original, final int newLength) {
        if (0 <= newLength) {
            return Arrays_copyOfRange(original, 0, newLength);
        }
        throw new NegativeArraySizeException();
    }
    
    private static long[] Arrays_copyOfRange(final long[] original, final int start, final int end) {
        if (original.length < start || 0 > start) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if (start <= end) {
            final int length = end - start;
            final int copyLength = Math.min(length, original.length - start);
            final long[] copy = (long[])Array.newInstance(original.getClass().getComponentType(), length);
            System.arraycopy(original, start, copy, 0, copyLength);
            return copy;
        }
        throw new IllegalArgumentException();
    }
}
