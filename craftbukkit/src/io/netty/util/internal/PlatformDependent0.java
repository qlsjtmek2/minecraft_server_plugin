// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.internal;

import java.lang.reflect.Method;
import java.nio.Buffer;
import java.lang.reflect.Field;
import sun.misc.Cleaner;
import java.nio.ByteBuffer;
import sun.misc.Unsafe;

final class PlatformDependent0
{
    private static final Unsafe UNSAFE;
    private static final long CLEANER_FIELD_OFFSET;
    private static final long ADDRESS_FIELD_OFFSET;
    private static final boolean UNALIGNED;
    
    static boolean hasUnsafe() {
        return PlatformDependent0.UNSAFE != null;
    }
    
    static void throwException(final Throwable t) {
        PlatformDependent0.UNSAFE.throwException(t);
    }
    
    static void freeDirectBuffer(final ByteBuffer buffer) {
        try {
            final Cleaner cleaner = (Cleaner)getObject(buffer, PlatformDependent0.CLEANER_FIELD_OFFSET);
            if (cleaner == null) {
                throw new IllegalArgumentException("attempted to deallocate the buffer which was allocated via JNIEnv->NewDirectByteBuffer()");
            }
            cleaner.clean();
        }
        catch (Throwable t) {}
    }
    
    static long directBufferAddress(final ByteBuffer buffer) {
        return getLong(buffer, PlatformDependent0.ADDRESS_FIELD_OFFSET);
    }
    
    static long arrayBaseOffset() {
        return PlatformDependent0.UNSAFE.arrayBaseOffset(byte[].class);
    }
    
    static Object getObject(final Object object, final long fieldOffset) {
        return PlatformDependent0.UNSAFE.getObject(object, fieldOffset);
    }
    
    static int getInt(final Object object, final long fieldOffset) {
        return PlatformDependent0.UNSAFE.getInt(object, fieldOffset);
    }
    
    private static long getLong(final Object object, final long fieldOffset) {
        return PlatformDependent0.UNSAFE.getLong(object, fieldOffset);
    }
    
    static long objectFieldOffset(final Field field) {
        return PlatformDependent0.UNSAFE.objectFieldOffset(field);
    }
    
    static byte getByte(final long address) {
        return PlatformDependent0.UNSAFE.getByte(address);
    }
    
    static short getShort(final long address) {
        if (PlatformDependent0.UNALIGNED) {
            return PlatformDependent0.UNSAFE.getShort(address);
        }
        return (short)(getByte(address) << 8 | (getByte(address + 1L) & 0xFF));
    }
    
    static int getInt(final long address) {
        if (PlatformDependent0.UNALIGNED) {
            return PlatformDependent0.UNSAFE.getInt(address);
        }
        return getByte(address) << 24 | (getByte(address + 1L) & 0xFF) << 16 | (getByte(address + 2L) & 0xFF) << 8 | (getByte(address + 3L) & 0xFF);
    }
    
    static long getLong(final long address) {
        if (PlatformDependent0.UNALIGNED) {
            return PlatformDependent0.UNSAFE.getLong(address);
        }
        return getByte(address) << 56 | (getByte(address + 1L) & 0xFFL) << 48 | (getByte(address + 2L) & 0xFFL) << 40 | (getByte(address + 3L) & 0xFFL) << 32 | (getByte(address + 4L) & 0xFFL) << 24 | (getByte(address + 5L) & 0xFFL) << 16 | (getByte(address + 6L) & 0xFFL) << 8 | (getByte(address + 7L) & 0xFFL);
    }
    
    static void putByte(final long address, final byte value) {
        PlatformDependent0.UNSAFE.putByte(address, value);
    }
    
    static void putShort(final long address, final short value) {
        if (PlatformDependent0.UNALIGNED) {
            PlatformDependent0.UNSAFE.putShort(address, value);
        }
        else {
            putByte(address, (byte)(value >>> 8));
            putByte(address + 1L, (byte)value);
        }
    }
    
    static void putInt(final long address, final int value) {
        if (PlatformDependent0.UNALIGNED) {
            PlatformDependent0.UNSAFE.putInt(address, value);
        }
        else {
            putByte(address, (byte)(value >>> 24));
            putByte(address + 1L, (byte)(value >>> 16));
            putByte(address + 2L, (byte)(value >>> 8));
            putByte(address + 3L, (byte)value);
        }
    }
    
    static void putLong(final long address, final long value) {
        if (PlatformDependent0.UNALIGNED) {
            PlatformDependent0.UNSAFE.putLong(address, value);
        }
        else {
            putByte(address, (byte)(value >>> 56));
            putByte(address + 1L, (byte)(value >>> 48));
            putByte(address + 2L, (byte)(value >>> 40));
            putByte(address + 3L, (byte)(value >>> 32));
            putByte(address + 4L, (byte)(value >>> 24));
            putByte(address + 5L, (byte)(value >>> 16));
            putByte(address + 6L, (byte)(value >>> 8));
            putByte(address + 7L, (byte)value);
        }
    }
    
    static void copyMemory(final long srcAddr, final long dstAddr, final long length) {
        PlatformDependent0.UNSAFE.copyMemory(srcAddr, dstAddr, length);
    }
    
    static void copyMemory(final Object src, final long srcOffset, final Object dst, final long dstOffset, final long length) {
        PlatformDependent0.UNSAFE.copyMemory(src, srcOffset, dst, dstOffset, length);
    }
    
    static {
        ByteBuffer direct = ByteBuffer.allocateDirect(1);
        Field cleanerField;
        try {
            cleanerField = direct.getClass().getDeclaredField("cleaner");
            cleanerField.setAccessible(true);
            final Cleaner cleaner = (Cleaner)cleanerField.get(direct);
            cleaner.clean();
        }
        catch (Throwable t) {
            cleanerField = null;
        }
        Field addressField;
        try {
            addressField = Buffer.class.getDeclaredField("address");
            addressField.setAccessible(true);
            if (addressField.getLong(ByteBuffer.allocate(1)) != 0L) {
                addressField = null;
            }
            else {
                direct = ByteBuffer.allocateDirect(1);
                if (addressField.getLong(direct) == 0L) {
                    addressField = null;
                }
                final Cleaner cleaner2 = (Cleaner)cleanerField.get(direct);
                cleaner2.clean();
            }
        }
        catch (Throwable t2) {
            addressField = null;
        }
        Unsafe unsafe;
        if (addressField != null && cleanerField != null) {
            try {
                final Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
                unsafeField.setAccessible(true);
                unsafe = (Unsafe)unsafeField.get(null);
                unsafe.getClass().getDeclaredMethod("copyMemory", Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE);
            }
            catch (Throwable cause) {
                unsafe = null;
            }
        }
        else {
            unsafe = null;
        }
        UNSAFE = unsafe;
        if (unsafe == null) {
            CLEANER_FIELD_OFFSET = -1L;
            ADDRESS_FIELD_OFFSET = -1L;
            UNALIGNED = false;
        }
        else {
            ADDRESS_FIELD_OFFSET = objectFieldOffset(addressField);
            CLEANER_FIELD_OFFSET = objectFieldOffset(cleanerField);
            boolean unaligned;
            try {
                final Class<?> bitsClass = Class.forName("java.nio.Bits", false, ClassLoader.getSystemClassLoader());
                final Method unalignedMethod = bitsClass.getDeclaredMethod("unaligned", (Class<?>[])new Class[0]);
                unalignedMethod.setAccessible(true);
                unaligned = Boolean.TRUE.equals(unalignedMethod.invoke(null, new Object[0]));
            }
            catch (Throwable t3) {
                final String arch = SystemPropertyUtil.get("os.arch", "");
                unaligned = arch.matches("^(i[3-6]86|x86(_64)?|x64|amd64)$");
            }
            UNALIGNED = unaligned;
        }
    }
}
