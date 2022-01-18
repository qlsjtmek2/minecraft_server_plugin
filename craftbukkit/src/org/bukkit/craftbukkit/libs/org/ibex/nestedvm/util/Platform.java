// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util;

import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.io.FileInputStream;
import java.text.DateFormatSymbols;
import java.io.FileOutputStream;
import java.util.Locale;
import java.util.TimeZone;
import java.net.UnknownHostException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.Socket;
import java.io.RandomAccessFile;
import java.io.IOException;
import java.io.File;

public abstract class Platform
{
    private static final Platform p;
    static /* synthetic */ Class class$org$ibex$nestedvm$util$Platform;
    
    public static String getProperty(final String s) {
        try {
            return System.getProperty(s);
        }
        catch (SecurityException ex) {
            return null;
        }
    }
    
    abstract boolean _atomicCreateFile(final File p0) throws IOException;
    
    public static boolean atomicCreateFile(final File file) throws IOException {
        return Platform.p._atomicCreateFile(file);
    }
    
    abstract Seekable.Lock _lockFile(final Seekable p0, final RandomAccessFile p1, final long p2, final long p3, final boolean p4) throws IOException;
    
    public static Seekable.Lock lockFile(final Seekable seekable, final RandomAccessFile randomAccessFile, final long n, final long n2, final boolean b) throws IOException {
        return Platform.p._lockFile(seekable, randomAccessFile, n, n2, b);
    }
    
    abstract void _socketHalfClose(final Socket p0, final boolean p1) throws IOException;
    
    public static void socketHalfClose(final Socket socket, final boolean b) throws IOException {
        Platform.p._socketHalfClose(socket, b);
    }
    
    abstract void _socketSetKeepAlive(final Socket p0, final boolean p1) throws SocketException;
    
    public static void socketSetKeepAlive(final Socket socket, final boolean b) throws SocketException {
        Platform.p._socketSetKeepAlive(socket, b);
    }
    
    abstract InetAddress _inetAddressFromBytes(final byte[] p0) throws UnknownHostException;
    
    public static InetAddress inetAddressFromBytes(final byte[] array) throws UnknownHostException {
        return Platform.p._inetAddressFromBytes(array);
    }
    
    abstract String _timeZoneGetDisplayName(final TimeZone p0, final boolean p1, final boolean p2, final Locale p3);
    
    public static String timeZoneGetDisplayName(final TimeZone timeZone, final boolean b, final boolean b2, final Locale locale) {
        return Platform.p._timeZoneGetDisplayName(timeZone, b, b2, locale);
    }
    
    public static String timeZoneGetDisplayName(final TimeZone timeZone, final boolean b, final boolean b2) {
        return timeZoneGetDisplayName(timeZone, b, b2, Locale.getDefault());
    }
    
    abstract void _setFileLength(final RandomAccessFile p0, final int p1) throws IOException;
    
    public static void setFileLength(final RandomAccessFile randomAccessFile, final int n) throws IOException {
        Platform.p._setFileLength(randomAccessFile, n);
    }
    
    abstract File[] _listRoots();
    
    public static File[] listRoots() {
        return Platform.p._listRoots();
    }
    
    abstract File _getRoot(final File p0);
    
    public static File getRoot(final File file) {
        return Platform.p._getRoot(file);
    }
    
    static /* synthetic */ Class class$(final String s) {
        try {
            return Class.forName(s);
        }
        catch (ClassNotFoundException ex) {
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
    
    static {
        float floatValue;
        try {
            if (getProperty("java.vm.name").equals("SableVM")) {
                floatValue = 1.2f;
            }
            else {
                floatValue = Float.valueOf(getProperty("java.specification.version"));
            }
        }
        catch (Exception ex) {
            System.err.println("WARNING: " + ex + " while trying to find jvm version -  assuming 1.1");
            floatValue = 1.1f;
        }
        String s;
        if (floatValue >= 1.4f) {
            s = "Jdk14";
        }
        else if (floatValue >= 1.3f) {
            s = "Jdk13";
        }
        else if (floatValue >= 1.2f) {
            s = "Jdk12";
        }
        else {
            if (floatValue < 1.1f) {
                throw new Error("JVM Specification version: " + floatValue + " is too old. (see org.ibex.util.Platform to add support)");
            }
            s = "Jdk11";
        }
        try {
            p = (Platform)Class.forName(((Platform.class$org$ibex$nestedvm$util$Platform == null) ? (Platform.class$org$ibex$nestedvm$util$Platform = class$("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Platform")) : Platform.class$org$ibex$nestedvm$util$Platform).getName() + "$" + s).newInstance();
        }
        catch (Exception ex2) {
            ex2.printStackTrace();
            throw new Error("Error instansiating platform class");
        }
    }
    
    static class Jdk11 extends Platform
    {
        boolean _atomicCreateFile(final File file) throws IOException {
            if (file.exists()) {
                return false;
            }
            new FileOutputStream(file).close();
            return true;
        }
        
        Seekable.Lock _lockFile(final Seekable seekable, final RandomAccessFile randomAccessFile, final long n, final long n2, final boolean b) throws IOException {
            throw new IOException("file locking requires jdk 1.4+");
        }
        
        void _socketHalfClose(final Socket socket, final boolean b) throws IOException {
            throw new IOException("half closing sockets not supported");
        }
        
        InetAddress _inetAddressFromBytes(final byte[] array) throws UnknownHostException {
            if (array.length != 4) {
                throw new UnknownHostException("only ipv4 addrs supported");
            }
            return InetAddress.getByName("" + (array[0] & 0xFF) + "." + (array[1] & 0xFF) + "." + (array[2] & 0xFF) + "." + (array[3] & 0xFF));
        }
        
        void _socketSetKeepAlive(final Socket socket, final boolean b) throws SocketException {
            if (b) {
                throw new SocketException("keepalive not supported");
            }
        }
        
        String _timeZoneGetDisplayName(final TimeZone timeZone, final boolean b, final boolean b2, final Locale locale) {
            final String[][] zoneStrings = new DateFormatSymbols(locale).getZoneStrings();
            final String id = timeZone.getID();
            for (int i = 0; i < zoneStrings.length; ++i) {
                if (zoneStrings[i][0].equals(id)) {
                    return zoneStrings[i][b ? (b2 ? 3 : 4) : (b2 ? 1 : 2)];
                }
            }
            final StringBuffer sb = new StringBuffer("GMT");
            int n = timeZone.getRawOffset() / 1000;
            if (n < 0) {
                sb.append("-");
                n = -n;
            }
            else {
                sb.append("+");
            }
            sb.append(n / 3600);
            final int n2 = n % 3600;
            if (n2 > 0) {
                sb.append(":").append(n2 / 60);
            }
            final int n3 = n2 % 60;
            if (n3 > 0) {
                sb.append(":").append(n3);
            }
            return sb.toString();
        }
        
        void _setFileLength(final RandomAccessFile randomAccessFile, int i) throws IOException {
            final FileInputStream fileInputStream = new FileInputStream(randomAccessFile.getFD());
            final FileOutputStream fileOutputStream = new FileOutputStream(randomAccessFile.getFD());
            final byte[] array = new byte[1024];
            while (i > 0) {
                final int read = fileInputStream.read(array, 0, Math.min(i, array.length));
                if (read == -1) {
                    break;
                }
                fileOutputStream.write(array, 0, read);
                i -= read;
            }
            if (i == 0) {
                return;
            }
            for (int j = 0; j < array.length; ++j) {
                array[j] = 0;
            }
            while (i > 0) {
                fileOutputStream.write(array, 0, Math.min(i, array.length));
                i -= array.length;
            }
        }
        
        RandomAccessFile _truncatedRandomAccessFile(final File file, final String s) throws IOException {
            new FileOutputStream(file).close();
            return new RandomAccessFile(file, s);
        }
        
        File[] _listRoots() {
            final String[] array = { "java.home", "java.class.path", "java.library.path", "java.io.tmpdir", "java.ext.dirs", "user.home", "user.dir" };
            final Hashtable hashtable = new Hashtable<File, Boolean>();
            for (int i = 0; i < array.length; ++i) {
                String s = Platform.getProperty(array[i]);
                if (s != null) {
                    int index;
                    do {
                        String substring = s;
                        if ((index = s.indexOf(File.pathSeparatorChar)) != -1) {
                            substring = s.substring(0, index);
                            s = s.substring(index + 1);
                        }
                        hashtable.put(Platform.getRoot(new File(substring)), Boolean.TRUE);
                    } while (index != -1);
                }
            }
            final File[] array2 = new File[hashtable.size()];
            int n = 0;
            final Enumeration<File> keys = hashtable.keys();
            while (keys.hasMoreElements()) {
                array2[n++] = keys.nextElement();
            }
            return array2;
        }
        
        File _getRoot(File file) {
            if (!file.isAbsolute()) {
                file = new File(file.getAbsolutePath());
            }
            String parent;
            while ((parent = file.getParent()) != null) {
                file = new File(parent);
            }
            if (file.getPath().length() == 0) {
                file = new File("/");
            }
            return file;
        }
    }
    
    static class Jdk12 extends Jdk11
    {
        boolean _atomicCreateFile(final File file) throws IOException {
            return file.createNewFile();
        }
        
        String _timeZoneGetDisplayName(final TimeZone timeZone, final boolean b, final boolean b2, final Locale locale) {
            return timeZone.getDisplayName(b, b2 ? 1 : 0, locale);
        }
        
        void _setFileLength(final RandomAccessFile randomAccessFile, final int n) throws IOException {
            randomAccessFile.setLength(n);
        }
        
        File[] _listRoots() {
            return File.listRoots();
        }
    }
    
    static class Jdk13 extends Jdk12
    {
        void _socketHalfClose(final Socket socket, final boolean b) throws IOException {
            if (b) {
                socket.shutdownOutput();
            }
            else {
                socket.shutdownInput();
            }
        }
        
        void _socketSetKeepAlive(final Socket socket, final boolean keepAlive) throws SocketException {
            socket.setKeepAlive(keepAlive);
        }
    }
    
    static class Jdk14 extends Jdk13
    {
        InetAddress _inetAddressFromBytes(final byte[] array) throws UnknownHostException {
            return InetAddress.getByAddress(array);
        }
        
        Seekable.Lock _lockFile(final Seekable seekable, final RandomAccessFile randomAccessFile, final long n, final long n2, final boolean b) throws IOException {
            FileLock fileLock;
            try {
                fileLock = ((n == 0L && n2 == 0L) ? randomAccessFile.getChannel().lock() : randomAccessFile.getChannel().tryLock(n, n2, b));
            }
            catch (OverlappingFileLockException ex) {
                fileLock = null;
            }
            if (fileLock == null) {
                return null;
            }
            return new Jdk14FileLock(seekable, fileLock);
        }
    }
    
    private static final class Jdk14FileLock extends Seekable.Lock
    {
        private final Seekable s;
        private final FileLock l;
        
        Jdk14FileLock(final Seekable s, final FileLock l) {
            this.s = s;
            this.l = l;
        }
        
        public Seekable seekable() {
            return this.s;
        }
        
        public boolean isShared() {
            return this.l.isShared();
        }
        
        public boolean isValid() {
            return this.l.isValid();
        }
        
        public void release() throws IOException {
            this.l.release();
        }
        
        public long position() {
            return this.l.position();
        }
        
        public long size() {
            return this.l.size();
        }
        
        public String toString() {
            return this.l.toString();
        }
    }
}
