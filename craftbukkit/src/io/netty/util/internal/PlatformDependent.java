// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.internal;

import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.concurrent.BlockingQueue;
import java.net.SocketAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.regex.Pattern;
import java.util.Locale;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import io.netty.util.internal.chmv8.ConcurrentHashMapV8;
import java.util.concurrent.ConcurrentMap;

public final class PlatformDependent
{
    private static final boolean IS_ANDROID;
    private static final boolean IS_WINDOWS;
    private static final boolean IS_ROOT;
    private static final int JAVA_VERSION;
    private static final boolean CAN_ENABLE_TCP_NODELAY_BY_DEFAULT;
    private static final boolean HAS_UNSAFE;
    private static final boolean CAN_USE_CHM_V8;
    private static final boolean DIRECT_BUFFER_PREFERRED;
    private static final long ARRAY_BASE_OFFSET;
    private static final boolean HAS_JAVASSIST;
    
    public static boolean isAndroid() {
        return PlatformDependent.IS_ANDROID;
    }
    
    public static boolean isWindows() {
        return PlatformDependent.IS_WINDOWS;
    }
    
    public static boolean isRoot() {
        return PlatformDependent.IS_ROOT;
    }
    
    public static int javaVersion() {
        return PlatformDependent.JAVA_VERSION;
    }
    
    public static boolean canEnableTcpNoDelayByDefault() {
        return PlatformDependent.CAN_ENABLE_TCP_NODELAY_BY_DEFAULT;
    }
    
    public static boolean hasUnsafe() {
        return PlatformDependent.HAS_UNSAFE;
    }
    
    public static boolean directBufferPreferred() {
        return PlatformDependent.DIRECT_BUFFER_PREFERRED;
    }
    
    public static boolean hasJavassist() {
        return PlatformDependent.HAS_JAVASSIST;
    }
    
    public static void throwException(final Throwable t) {
        if (hasUnsafe()) {
            PlatformDependent0.throwException(t);
        }
        else {
            throwException0(t);
        }
    }
    
    private static <E extends Throwable> void throwException0(final Throwable t) throws E, Throwable {
        throw t;
    }
    
    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap() {
        if (PlatformDependent.CAN_USE_CHM_V8) {
            return new ConcurrentHashMapV8<K, V>();
        }
        return new ConcurrentHashMap<K, V>();
    }
    
    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(final int initialCapacity) {
        if (PlatformDependent.CAN_USE_CHM_V8) {
            return new ConcurrentHashMapV8<K, V>(initialCapacity);
        }
        return new ConcurrentHashMap<K, V>(initialCapacity);
    }
    
    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(final int initialCapacity, final float loadFactor) {
        if (PlatformDependent.CAN_USE_CHM_V8) {
            return new ConcurrentHashMapV8<K, V>(initialCapacity, loadFactor);
        }
        return new ConcurrentHashMap<K, V>(initialCapacity, loadFactor);
    }
    
    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(final int initialCapacity, final float loadFactor, final int concurrencyLevel) {
        if (PlatformDependent.CAN_USE_CHM_V8) {
            return new ConcurrentHashMapV8<K, V>(initialCapacity, loadFactor, concurrencyLevel);
        }
        return new ConcurrentHashMap<K, V>(initialCapacity, loadFactor, concurrencyLevel);
    }
    
    public static <K, V> ConcurrentMap<K, V> newConcurrentHashMap(final Map<? extends K, ? extends V> map) {
        if (PlatformDependent.CAN_USE_CHM_V8) {
            return new ConcurrentHashMapV8<K, V>(map);
        }
        return new ConcurrentHashMap<K, V>(map);
    }
    
    public static void freeDirectBuffer(final ByteBuffer buffer) {
        if (buffer.isDirect()) {
            PlatformDependent0.freeDirectBuffer(buffer);
        }
    }
    
    public static long directBufferAddress(final ByteBuffer buffer) {
        return PlatformDependent0.directBufferAddress(buffer);
    }
    
    public static Object getObject(final Object object, final long fieldOffset) {
        return PlatformDependent0.getObject(object, fieldOffset);
    }
    
    public static int getInt(final Object object, final long fieldOffset) {
        return PlatformDependent0.getInt(object, fieldOffset);
    }
    
    public static long objectFieldOffset(final Field field) {
        return PlatformDependent0.objectFieldOffset(field);
    }
    
    public static byte getByte(final long address) {
        return PlatformDependent0.getByte(address);
    }
    
    public static short getShort(final long address) {
        return PlatformDependent0.getShort(address);
    }
    
    public static int getInt(final long address) {
        return PlatformDependent0.getInt(address);
    }
    
    public static long getLong(final long address) {
        return PlatformDependent0.getLong(address);
    }
    
    public static void putByte(final long address, final byte value) {
        PlatformDependent0.putByte(address, value);
    }
    
    public static void putShort(final long address, final short value) {
        PlatformDependent0.putShort(address, value);
    }
    
    public static void putInt(final long address, final int value) {
        PlatformDependent0.putInt(address, value);
    }
    
    public static void putLong(final long address, final long value) {
        PlatformDependent0.putLong(address, value);
    }
    
    public static void copyMemory(final long srcAddr, final long dstAddr, final long length) {
        PlatformDependent0.copyMemory(srcAddr, dstAddr, length);
    }
    
    public static void copyMemory(final byte[] src, final int srcIndex, final long dstAddr, final long length) {
        PlatformDependent0.copyMemory(src, PlatformDependent.ARRAY_BASE_OFFSET + srcIndex, null, dstAddr, length);
    }
    
    public static void copyMemory(final long srcAddr, final byte[] dst, final int dstIndex, final long length) {
        PlatformDependent0.copyMemory(null, srcAddr, dst, PlatformDependent.ARRAY_BASE_OFFSET + dstIndex, length);
    }
    
    private static boolean isAndroid0() {
        boolean android;
        try {
            Class.forName("android.app.Application", false, ClassLoader.getSystemClassLoader());
            android = true;
        }
        catch (Exception e) {
            android = false;
        }
        return android;
    }
    
    private static boolean isWindows0() {
        return SystemPropertyUtil.get("os.name", "").toLowerCase(Locale.US).contains("win");
    }
    
    private static boolean isRoot0() {
        final Pattern PERMISSION_DENIED = Pattern.compile(".*permission.*denied.*");
        boolean root = false;
        if (!isWindows()) {
            for (int i = 1023; i > 0; --i) {
                ServerSocket ss = null;
                try {
                    ss = new ServerSocket();
                    ss.setReuseAddress(true);
                    ss.bind(new InetSocketAddress(i));
                    root = true;
                }
                catch (Exception e) {
                    String message = e.getMessage();
                    if (message == null) {
                        message = "";
                    }
                    message = message.toLowerCase();
                    if (PERMISSION_DENIED.matcher(message).matches()) {}
                }
                finally {
                    if (ss != null) {
                        try {
                            ss.close();
                        }
                        catch (Exception ex) {}
                    }
                }
            }
        }
        return root;
    }
    
    private static int javaVersion0() {
        if (isAndroid()) {
            return 6;
        }
        try {
            Class.forName("java.time.Clock", false, Object.class.getClassLoader());
            return 8;
        }
        catch (Exception e) {
            try {
                Class.forName("java.util.concurrent.LinkedTransferQueue", false, BlockingQueue.class.getClassLoader());
                return 7;
            }
            catch (Exception e) {
                return 6;
            }
        }
    }
    
    private static boolean hasUnsafe0() {
        if (isAndroid()) {
            return false;
        }
        final boolean noUnsafe = SystemPropertyUtil.getBoolean("io.netty.noUnsafe", false);
        if (noUnsafe) {
            return false;
        }
        boolean tryUnsafe;
        if (SystemPropertyUtil.contains("io.netty.tryUnsafe")) {
            tryUnsafe = SystemPropertyUtil.getBoolean("io.netty.tryUnsafe", true);
        }
        else {
            tryUnsafe = SystemPropertyUtil.getBoolean("org.jboss.netty.tryUnsafe", true);
        }
        if (!tryUnsafe) {
            return false;
        }
        try {
            return PlatformDependent0.hasUnsafe();
        }
        catch (Throwable t) {
            return false;
        }
    }
    
    private static long arrayBaseOffset0() {
        if (!hasUnsafe()) {
            return -1L;
        }
        return PlatformDependent0.arrayBaseOffset();
    }
    
    private static boolean hasJavassist0() {
        final boolean noJavassist = SystemPropertyUtil.getBoolean("io.netty.noJavassist", false);
        if (noJavassist) {
            return false;
        }
        try {
            JavassistTypeParameterMatcherGenerator.generate(Object.class, PlatformDependent.class.getClassLoader());
            return true;
        }
        catch (Throwable t) {
            return false;
        }
    }
    
    static {
        IS_ANDROID = isAndroid0();
        IS_WINDOWS = isWindows0();
        IS_ROOT = isRoot0();
        JAVA_VERSION = javaVersion0();
        CAN_ENABLE_TCP_NODELAY_BY_DEFAULT = !isAndroid();
        HAS_UNSAFE = hasUnsafe0();
        CAN_USE_CHM_V8 = (PlatformDependent.HAS_UNSAFE && PlatformDependent.JAVA_VERSION < 8);
        DIRECT_BUFFER_PREFERRED = (PlatformDependent.HAS_UNSAFE && SystemPropertyUtil.getBoolean("io.netty.preferDirect", false));
        ARRAY_BASE_OFFSET = arrayBaseOffset0();
        HAS_JAVASSIST = hasJavassist0();
        final InternalLogger logger = InternalLoggerFactory.getInstance(PlatformDependent.class);
        if (!hasUnsafe()) {
            logger.warn("Your platform does not provide complete low-level API for accessing direct buffers reliably. Unless explicitly requested, heap buffer will always be preferred to avoid potential risk of getting OutOfMemoryError.");
        }
    }
}
