// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.primitives;

import java.security.AccessController;
import java.lang.reflect.Field;
import java.security.PrivilegedAction;
import java.nio.ByteOrder;
import sun.misc.Unsafe;
import com.google.common.annotations.VisibleForTesting;
import java.util.Comparator;
import com.google.common.base.Preconditions;

public final class UnsignedBytes
{
    public static final byte MAX_POWER_OF_TWO = Byte.MIN_VALUE;
    
    public static int toInt(final byte value) {
        return value & 0xFF;
    }
    
    public static byte checkedCast(final long value) {
        Preconditions.checkArgument(value >> 8 == 0L, "out of range: %s", value);
        return (byte)value;
    }
    
    public static byte saturatedCast(final long value) {
        if (value > 255L) {
            return -1;
        }
        if (value < 0L) {
            return 0;
        }
        return (byte)value;
    }
    
    public static int compare(final byte a, final byte b) {
        return toInt(a) - toInt(b);
    }
    
    public static byte min(final byte... array) {
        Preconditions.checkArgument(array.length > 0);
        int min = toInt(array[0]);
        for (int i = 1; i < array.length; ++i) {
            final int next = toInt(array[i]);
            if (next < min) {
                min = next;
            }
        }
        return (byte)min;
    }
    
    public static byte max(final byte... array) {
        Preconditions.checkArgument(array.length > 0);
        int max = toInt(array[0]);
        for (int i = 1; i < array.length; ++i) {
            final int next = toInt(array[i]);
            if (next > max) {
                max = next;
            }
        }
        return (byte)max;
    }
    
    public static String join(final String separator, final byte... array) {
        Preconditions.checkNotNull(separator);
        if (array.length == 0) {
            return "";
        }
        final StringBuilder builder = new StringBuilder(array.length * 5);
        builder.append(toInt(array[0]));
        for (int i = 1; i < array.length; ++i) {
            builder.append(separator).append(toInt(array[i]));
        }
        return builder.toString();
    }
    
    public static Comparator<byte[]> lexicographicalComparator() {
        return LexicographicalComparatorHolder.BEST_COMPARATOR;
    }
    
    @VisibleForTesting
    static Comparator<byte[]> lexicographicalComparatorJavaImpl() {
        return LexicographicalComparatorHolder.PureJavaComparator.INSTANCE;
    }
    
    @VisibleForTesting
    static class LexicographicalComparatorHolder
    {
        static final String UNSAFE_COMPARATOR_NAME;
        static final Comparator<byte[]> BEST_COMPARATOR;
        
        static Comparator<byte[]> getBestComparator() {
            try {
                final Class<?> theClass = Class.forName(LexicographicalComparatorHolder.UNSAFE_COMPARATOR_NAME);
                final Comparator<byte[]> comparator = (Comparator<byte[]>)theClass.getEnumConstants()[0];
                return comparator;
            }
            catch (Throwable t) {
                return UnsignedBytes.lexicographicalComparatorJavaImpl();
            }
        }
        
        static {
            UNSAFE_COMPARATOR_NAME = LexicographicalComparatorHolder.class.getName() + "$UnsafeComparator";
            BEST_COMPARATOR = getBestComparator();
        }
        
        @VisibleForTesting
        enum UnsafeComparator implements Comparator<byte[]>
        {
            INSTANCE;
            
            static final boolean littleEndian;
            static final Unsafe theUnsafe;
            static final int BYTE_ARRAY_BASE_OFFSET;
            
            public int compare(final byte[] left, final byte[] right) {
                final int minLength = Math.min(left.length, right.length);
                final int minWords = minLength / 8;
                int i = 0;
                while (i < minWords * 8) {
                    final long lw = UnsafeComparator.theUnsafe.getLong(left, UnsafeComparator.BYTE_ARRAY_BASE_OFFSET + i);
                    final long rw = UnsafeComparator.theUnsafe.getLong(right, UnsafeComparator.BYTE_ARRAY_BASE_OFFSET + i);
                    final long diff = lw ^ rw;
                    if (diff != 0L) {
                        if (!UnsafeComparator.littleEndian) {
                            return UnsignedLongs.compare(lw, rw);
                        }
                        int n = 0;
                        int x = (int)diff;
                        if (x == 0) {
                            x = (int)(diff >>> 32);
                            n = 32;
                        }
                        int y = x << 16;
                        if (y == 0) {
                            n += 16;
                        }
                        else {
                            x = y;
                        }
                        y = x << 8;
                        if (y == 0) {
                            n += 8;
                        }
                        return (int)((lw >>> n & 0xFFL) - (rw >>> n & 0xFFL));
                    }
                    else {
                        i += 8;
                    }
                }
                for (i = minWords * 8; i < minLength; ++i) {
                    final int result = UnsignedBytes.compare(left[i], right[i]);
                    if (result != 0) {
                        return result;
                    }
                }
                return left.length - right.length;
            }
            
            static {
                littleEndian = ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN);
                theUnsafe = AccessController.doPrivileged((PrivilegedAction<Unsafe>)new PrivilegedAction<Object>() {
                    public Object run() {
                        try {
                            final Field f = Unsafe.class.getDeclaredField("theUnsafe");
                            f.setAccessible(true);
                            return f.get(null);
                        }
                        catch (NoSuchFieldException e) {
                            throw new Error();
                        }
                        catch (IllegalAccessException e2) {
                            throw new Error();
                        }
                    }
                });
                BYTE_ARRAY_BASE_OFFSET = UnsafeComparator.theUnsafe.arrayBaseOffset(byte[].class);
                if (UnsafeComparator.theUnsafe.arrayIndexScale(byte[].class) != 1) {
                    throw new AssertionError();
                }
            }
        }
        
        enum PureJavaComparator implements Comparator<byte[]>
        {
            INSTANCE;
            
            public int compare(final byte[] left, final byte[] right) {
                for (int minLength = Math.min(left.length, right.length), i = 0; i < minLength; ++i) {
                    final int result = UnsignedBytes.compare(left[i], right[i]);
                    if (result != 0) {
                        return result;
                    }
                }
                return left.length - right.length;
            }
        }
    }
}
