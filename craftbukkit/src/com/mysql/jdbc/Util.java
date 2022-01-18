// 
// Decompiled by Procyon v0.5.30
// 

package com.mysql.jdbc;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Iterator;
import java.math.BigInteger;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.lang.reflect.Constructor;
import java.io.Writer;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.ObjectInputStream;
import java.sql.ResultSet;
import java.util.TimeZone;
import java.lang.reflect.Method;

public class Util
{
    protected static Method systemNanoTimeMethod;
    private static Method CAST_METHOD;
    private static final TimeZone DEFAULT_TIMEZONE;
    private static Util enclosingInstance;
    private static boolean isJdbc4;
    private static boolean isColdFusion;
    
    public static boolean nanoTimeAvailable() {
        return Util.systemNanoTimeMethod != null;
    }
    
    static final TimeZone getDefaultTimeZone() {
        return (TimeZone)Util.DEFAULT_TIMEZONE.clone();
    }
    
    public static boolean isJdbc4() {
        return Util.isJdbc4;
    }
    
    public static boolean isColdFusion() {
        return Util.isColdFusion;
    }
    
    static String newCrypt(final String password, final String seed) {
        if (password == null || password.length() == 0) {
            return password;
        }
        final long[] pw = newHash(seed);
        final long[] msg = newHash(password);
        final long max = 1073741823L;
        long seed2 = (pw[0] ^ msg[0]) % max;
        long seed3 = (pw[1] ^ msg[1]) % max;
        final char[] chars = new char[seed.length()];
        for (int i = 0; i < seed.length(); ++i) {
            seed2 = (seed2 * 3L + seed3) % max;
            seed3 = (seed2 + seed3 + 33L) % max;
            final double d = seed2 / max;
            final byte b = (byte)Math.floor(d * 31.0 + 64.0);
            chars[i] = (char)b;
        }
        seed2 = (seed2 * 3L + seed3) % max;
        seed3 = (seed2 + seed3 + 33L) % max;
        final double d = seed2 / max;
        final byte b = (byte)Math.floor(d * 31.0);
        for (int i = 0; i < seed.length(); ++i) {
            final char[] array = chars;
            final int n = i;
            array[n] ^= (char)b;
        }
        return new String(chars);
    }
    
    static long[] newHash(final String password) {
        long nr = 1345345333L;
        long add = 7L;
        long nr2 = 305419889L;
        for (int i = 0; i < password.length(); ++i) {
            if (password.charAt(i) != ' ') {
                if (password.charAt(i) != '\t') {
                    final long tmp = '\u00ff' & password.charAt(i);
                    nr ^= ((nr & 0x3FL) + add) * tmp + (nr << 8);
                    nr2 += (nr2 << 8 ^ nr);
                    add += tmp;
                }
            }
        }
        final long[] result = { nr & 0x7FFFFFFFL, nr2 & 0x7FFFFFFFL };
        return result;
    }
    
    static String oldCrypt(final String password, final String seed) {
        final long max = 33554431L;
        if (password == null || password.length() == 0) {
            return password;
        }
        final long hp = oldHash(seed);
        final long hm = oldHash(password);
        long nr = hp ^ hm;
        long s1;
        nr = (s1 = nr % max);
        long s2 = nr / 2L;
        final char[] chars = new char[seed.length()];
        for (int i = 0; i < seed.length(); ++i) {
            s1 = (s1 * 3L + s2) % max;
            s2 = (s1 + s2 + 33L) % max;
            final double d = s1 / max;
            final byte b = (byte)Math.floor(d * 31.0 + 64.0);
            chars[i] = (char)b;
        }
        return new String(chars);
    }
    
    static long oldHash(final String password) {
        long nr = 1345345333L;
        long nr2 = 7L;
        for (int i = 0; i < password.length(); ++i) {
            if (password.charAt(i) != ' ') {
                if (password.charAt(i) != '\t') {
                    final long tmp = password.charAt(i);
                    nr ^= ((nr & 0x3FL) + nr2) * tmp + (nr << 8);
                    nr2 += tmp;
                }
            }
        }
        return nr & 0x7FFFFFFFL;
    }
    
    private static RandStructcture randomInit(final long seed1, final long seed2) {
        final RandStructcture randStruct = Util.enclosingInstance.new RandStructcture();
        randStruct.maxValue = 1073741823L;
        randStruct.maxValueDbl = randStruct.maxValue;
        randStruct.seed1 = seed1 % randStruct.maxValue;
        randStruct.seed2 = seed2 % randStruct.maxValue;
        return randStruct;
    }
    
    public static Object readObject(final ResultSet resultSet, final int index) throws Exception {
        final ObjectInputStream objIn = new ObjectInputStream(resultSet.getBinaryStream(index));
        final Object obj = objIn.readObject();
        objIn.close();
        return obj;
    }
    
    private static double rnd(final RandStructcture randStruct) {
        randStruct.seed1 = (randStruct.seed1 * 3L + randStruct.seed2) % randStruct.maxValue;
        randStruct.seed2 = (randStruct.seed1 + randStruct.seed2 + 33L) % randStruct.maxValue;
        return randStruct.seed1 / randStruct.maxValueDbl;
    }
    
    public static String scramble(String message, final String password) {
        final byte[] to = new byte[8];
        String val = "";
        message = message.substring(0, 8);
        if (password != null && password.length() > 0) {
            final long[] hashPass = newHash(password);
            final long[] hashMessage = newHash(message);
            final RandStructcture randStruct = randomInit(hashPass[0] ^ hashMessage[0], hashPass[1] ^ hashMessage[1]);
            int msgPos = 0;
            final int msgLength = message.length();
            int toPos = 0;
            while (msgPos++ < msgLength) {
                to[toPos++] = (byte)(Math.floor(rnd(randStruct) * 31.0) + 64.0);
            }
            final byte extra = (byte)Math.floor(rnd(randStruct) * 31.0);
            for (int i = 0; i < to.length; ++i) {
                final byte[] array = to;
                final int n = i;
                array[n] ^= extra;
            }
            val = new String(to);
        }
        return val;
    }
    
    public static String stackTraceToString(final Throwable ex) {
        final StringBuffer traceBuf = new StringBuffer();
        traceBuf.append(Messages.getString("Util.1"));
        if (ex != null) {
            traceBuf.append(ex.getClass().getName());
            final String message = ex.getMessage();
            if (message != null) {
                traceBuf.append(Messages.getString("Util.2"));
                traceBuf.append(message);
            }
            final StringWriter out = new StringWriter();
            final PrintWriter printOut = new PrintWriter(out);
            ex.printStackTrace(printOut);
            traceBuf.append(Messages.getString("Util.3"));
            traceBuf.append(out.toString());
        }
        traceBuf.append(Messages.getString("Util.4"));
        return traceBuf.toString();
    }
    
    public static Object getInstance(final String className, final Class[] argTypes, final Object[] args, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        try {
            return handleNewInstance(Class.forName(className).getConstructor((Class<?>[])argTypes), args, exceptionInterceptor);
        }
        catch (SecurityException e) {
            throw SQLError.createSQLException("Can't instantiate required class", "S1000", e, exceptionInterceptor);
        }
        catch (NoSuchMethodException e2) {
            throw SQLError.createSQLException("Can't instantiate required class", "S1000", e2, exceptionInterceptor);
        }
        catch (ClassNotFoundException e3) {
            throw SQLError.createSQLException("Can't instantiate required class", "S1000", e3, exceptionInterceptor);
        }
    }
    
    public static final Object handleNewInstance(final Constructor ctor, final Object[] args, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        try {
            return ctor.newInstance(args);
        }
        catch (IllegalArgumentException e) {
            throw SQLError.createSQLException("Can't instantiate required class", "S1000", e, exceptionInterceptor);
        }
        catch (InstantiationException e2) {
            throw SQLError.createSQLException("Can't instantiate required class", "S1000", e2, exceptionInterceptor);
        }
        catch (IllegalAccessException e3) {
            throw SQLError.createSQLException("Can't instantiate required class", "S1000", e3, exceptionInterceptor);
        }
        catch (InvocationTargetException e4) {
            Throwable target = e4.getTargetException();
            if (target instanceof SQLException) {
                throw (SQLException)target;
            }
            if (target instanceof ExceptionInInitializerError) {
                target = ((ExceptionInInitializerError)target).getException();
            }
            throw SQLError.createSQLException(target.toString(), "S1000", exceptionInterceptor);
        }
    }
    
    public static boolean interfaceExists(final String hostname) {
        try {
            final Class networkInterfaceClass = Class.forName("java.net.NetworkInterface");
            return networkInterfaceClass.getMethod("getByName", (Class[])null).invoke(networkInterfaceClass, hostname) != null;
        }
        catch (Throwable t) {
            return false;
        }
    }
    
    public static Object cast(final Object invokeOn, final Object toCast) {
        if (Util.CAST_METHOD != null) {
            try {
                return Util.CAST_METHOD.invoke(invokeOn, toCast);
            }
            catch (Throwable t) {
                return null;
            }
        }
        return null;
    }
    
    public static long getCurrentTimeNanosOrMillis() {
        if (Util.systemNanoTimeMethod != null) {
            try {
                return (long)Util.systemNanoTimeMethod.invoke(null, (Object[])null);
            }
            catch (IllegalArgumentException e) {}
            catch (IllegalAccessException e2) {}
            catch (InvocationTargetException ex) {}
        }
        return System.currentTimeMillis();
    }
    
    public static void resultSetToMap(final Map mappedValues, final ResultSet rs) throws SQLException {
        while (rs.next()) {
            mappedValues.put(rs.getObject(1), rs.getObject(2));
        }
    }
    
    public static Map calculateDifferences(final Map map1, final Map map2) {
        final Map diffMap = new HashMap();
        for (final Map.Entry entry : map1.entrySet()) {
            final Object key = entry.getKey();
            Number value1 = null;
            Number value2 = null;
            if (entry.getValue() instanceof Number) {
                value1 = entry.getValue();
                value2 = map2.get(key);
            }
            else {
                try {
                    value1 = new Double(entry.getValue().toString());
                    value2 = new Double(map2.get(key).toString());
                }
                catch (NumberFormatException nfe) {
                    continue;
                }
            }
            if (value1.equals(value2)) {
                continue;
            }
            if (value1 instanceof Byte) {
                diffMap.put(key, new Byte((byte)((byte)value2 - (byte)value1)));
            }
            else if (value1 instanceof Short) {
                diffMap.put(key, new Short((short)((short)value2 - (short)value1)));
            }
            else if (value1 instanceof Integer) {
                diffMap.put(key, new Integer((int)value2 - (int)value1));
            }
            else if (value1 instanceof Long) {
                diffMap.put(key, new Long((long)value2 - (long)value1));
            }
            else if (value1 instanceof Float) {
                diffMap.put(key, new Float((float)value2 - (float)value1));
            }
            else if (value1 instanceof Double) {
                diffMap.put(key, new Double((short)value2 - (short)value1));
            }
            else if (value1 instanceof BigDecimal) {
                diffMap.put(key, ((BigDecimal)value2).subtract((BigDecimal)value1));
            }
            else {
                if (!(value1 instanceof BigInteger)) {
                    continue;
                }
                diffMap.put(key, ((BigInteger)value2).subtract((BigInteger)value1));
            }
        }
        return diffMap;
    }
    
    public static List loadExtensions(final Connection conn, final Properties props, final String extensionClassNames, final String errorMessageKey, final ExceptionInterceptor exceptionInterceptor) throws SQLException {
        final List extensionList = new LinkedList();
        final List interceptorsToCreate = StringUtils.split(extensionClassNames, ",", true);
        final Iterator iter = interceptorsToCreate.iterator();
        String className = null;
        try {
            while (iter.hasNext()) {
                className = iter.next().toString();
                final Extension extensionInstance = (Extension)Class.forName(className).newInstance();
                extensionInstance.init(conn, props);
                extensionList.add(extensionInstance);
            }
        }
        catch (Throwable t) {
            final SQLException sqlEx = SQLError.createSQLException(Messages.getString(errorMessageKey, new Object[] { className }), exceptionInterceptor);
            sqlEx.initCause(t);
            throw sqlEx;
        }
        return extensionList;
    }
    
    static {
        try {
            Util.systemNanoTimeMethod = System.class.getMethod("nanoTime", (Class<?>[])null);
        }
        catch (SecurityException e) {
            Util.systemNanoTimeMethod = null;
        }
        catch (NoSuchMethodException e2) {
            Util.systemNanoTimeMethod = null;
        }
        DEFAULT_TIMEZONE = TimeZone.getDefault();
        Util.enclosingInstance = new Util();
        Util.isJdbc4 = false;
        Util.isColdFusion = false;
        try {
            Util.CAST_METHOD = Class.class.getMethod("cast", Object.class);
        }
        catch (Throwable t2) {}
        try {
            Class.forName("java.sql.NClob");
            Util.isJdbc4 = true;
        }
        catch (Throwable t) {
            Util.isJdbc4 = false;
        }
        final String loadedFrom = stackTraceToString(new Throwable());
        if (loadedFrom != null) {
            Util.isColdFusion = (loadedFrom.indexOf("coldfusion") != -1);
        }
        else {
            Util.isColdFusion = false;
        }
    }
    
    class RandStructcture
    {
        long maxValue;
        double maxValueDbl;
        long seed1;
        long seed2;
    }
}
