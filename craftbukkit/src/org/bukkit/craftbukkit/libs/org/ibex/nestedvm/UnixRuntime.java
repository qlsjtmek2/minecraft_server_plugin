// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.libs.org.ibex.nestedvm;

import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLConnection;
import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.InodeCache;
import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Sort;
import java.util.Hashtable;
import java.net.SocketException;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.io.FileInputStream;
import java.io.File;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable;
import java.util.Enumeration;
import java.util.TimeZone;
import org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Platform;
import java.lang.reflect.Method;
import java.util.Vector;

public abstract class UnixRuntime extends Runtime implements Cloneable
{
    private int pid;
    private UnixRuntime parent;
    private static final GlobalState defaultGS;
    private GlobalState gs;
    private String cwd;
    private UnixRuntime execedRuntime;
    private Object children;
    private Vector activeChildren;
    private Vector exitedChildren;
    private static final Method runtimeCompilerCompile;
    static /* synthetic */ Class class$org$ibex$nestedvm$util$Seekable;
    static /* synthetic */ Class class$java$lang$String;
    
    public final int getPid() {
        return this.pid;
    }
    
    public void setGlobalState(final GlobalState gs) {
        if (this.state != 1) {
            throw new IllegalStateException("can't change GlobalState when running");
        }
        if (gs == null) {
            throw new NullPointerException("gs is null");
        }
        this.gs = gs;
    }
    
    protected UnixRuntime(final int n, final int n2) {
        this(n, n2, false);
    }
    
    protected UnixRuntime(final int n, final int n2, final boolean b) {
        super(n, n2, b);
        if (!b) {
            this.gs = UnixRuntime.defaultGS;
            final String property = Platform.getProperty("user.dir");
            this.cwd = ((property == null) ? null : this.gs.mapHostPath(property));
            if (this.cwd == null) {
                this.cwd = "/";
            }
            this.cwd = this.cwd.substring(1);
        }
    }
    
    private static String posixTZ() {
        final StringBuffer sb = new StringBuffer();
        final TimeZone default1 = TimeZone.getDefault();
        int n = default1.getRawOffset() / 1000;
        sb.append(Platform.timeZoneGetDisplayName(default1, false, false));
        if (n > 0) {
            sb.append("-");
        }
        else {
            n = -n;
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
        if (default1.useDaylightTime()) {
            sb.append(Platform.timeZoneGetDisplayName(default1, true, false));
        }
        return sb.toString();
    }
    
    private static boolean envHas(final String s, final String[] array) {
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != null && array[i].startsWith(s + "=")) {
                return true;
            }
        }
        return false;
    }
    
    String[] createEnv(String[] array) {
        final String[] array2 = new String[7];
        int n = 0;
        if (array == null) {
            array = new String[0];
        }
        if (!envHas("USER", array) && Platform.getProperty("user.name") != null) {
            array2[n++] = "USER=" + Platform.getProperty("user.name");
        }
        final String property;
        final String mapHostPath;
        if (!envHas("HOME", array) && (property = Platform.getProperty("user.home")) != null && (mapHostPath = this.gs.mapHostPath(property)) != null) {
            array2[n++] = "HOME=" + mapHostPath;
        }
        final String property2;
        final String mapHostPath2;
        if (!envHas("TMPDIR", array) && (property2 = Platform.getProperty("java.io.tmpdir")) != null && (mapHostPath2 = this.gs.mapHostPath(property2)) != null) {
            array2[n++] = "TMPDIR=" + mapHostPath2;
        }
        if (!envHas("SHELL", array)) {
            array2[n++] = "SHELL=/bin/sh";
        }
        if (!envHas("TERM", array) && !UnixRuntime.win32Hacks) {
            array2[n++] = "TERM=vt100";
        }
        if (!envHas("TZ", array)) {
            array2[n++] = "TZ=" + posixTZ();
        }
        if (!envHas("PATH", array)) {
            array2[n++] = "PATH=/usr/local/bin:/usr/bin:/bin:/usr/local/sbin:/usr/sbin:/sbin";
        }
        final String[] array3 = new String[array.length + n];
        for (int i = 0; i < n; ++i) {
            array3[i] = array2[i];
        }
        for (int j = 0; j < array.length; ++j) {
            array3[n++] = array[j];
        }
        return array3;
    }
    
    void _started() {
        final UnixRuntime[] tasks = this.gs.tasks;
        synchronized (this.gs) {
            if (this.pid != 0) {
                final UnixRuntime unixRuntime = tasks[this.pid];
                if (unixRuntime == null || unixRuntime == this || unixRuntime.pid != this.pid || unixRuntime.parent != this.parent) {
                    throw new Error("should never happen");
                }
                synchronized (this.parent.children) {
                    final int index = this.parent.activeChildren.indexOf(unixRuntime);
                    if (index == -1) {
                        throw new Error("should never happen");
                    }
                    this.parent.activeChildren.setElementAt(this, index);
                }
            }
            else {
                int pid = -1;
                int i;
                int n;
                for (n = (i = this.gs.nextPID); i < tasks.length; ++i) {
                    if (tasks[i] == null) {
                        pid = i;
                        break;
                    }
                }
                if (pid == -1) {
                    for (int j = 1; j < n; ++j) {
                        if (tasks[j] == null) {
                            pid = j;
                            break;
                        }
                    }
                }
                if (pid == -1) {
                    throw new ProcessTableFullExn();
                }
                this.pid = pid;
                this.gs.nextPID = pid + 1;
            }
            tasks[this.pid] = this;
        }
    }
    
    int _syscall(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7) throws ErrnoException, FaultException {
        switch (n) {
            case 11: {
                return this.sys_kill(n2, n3);
            }
            case 25: {
                return this.sys_fork();
            }
            case 23: {
                return this.sys_pipe(n2);
            }
            case 24: {
                return this.sys_dup2(n2, n3);
            }
            case 39: {
                return this.sys_dup(n2);
            }
            case 26: {
                return this.sys_waitpid(n2, n3, n4);
            }
            case 14: {
                return this.sys_stat(n2, n3);
            }
            case 33: {
                return this.sys_lstat(n2, n3);
            }
            case 18: {
                return this.sys_mkdir(n2, n3);
            }
            case 27: {
                return this.sys_getcwd(n2, n3);
            }
            case 22: {
                return this.sys_chdir(n2);
            }
            case 28: {
                return this.sys_exec(n2, n3, n4);
            }
            case 36: {
                return this.sys_getdents(n2, n3, n4, n5);
            }
            case 20: {
                return this.sys_unlink(n2);
            }
            case 46: {
                return this.sys_getppid();
            }
            case 56: {
                return this.sys_socket(n2, n3, n4);
            }
            case 57: {
                return this.sys_connect(n2, n3, n4);
            }
            case 58: {
                return this.sys_resolve_hostname(n2, n3, n4);
            }
            case 60: {
                return this.sys_setsockopt(n2, n3, n4, n5, n6);
            }
            case 61: {
                return this.sys_getsockopt(n2, n3, n4, n5, n6);
            }
            case 63: {
                return this.sys_bind(n2, n3, n4);
            }
            case 62: {
                return this.sys_listen(n2, n3);
            }
            case 59: {
                return this.sys_accept(n2, n3, n4);
            }
            case 64: {
                return this.sys_shutdown(n2, n3);
            }
            case 53: {
                return this.sys_sysctl(n2, n3, n4, n5, n6, n7);
            }
            case 65: {
                return this.sys_sendto(n2, n3, n4, n5, n6, n7);
            }
            case 66: {
                return this.sys_recvfrom(n2, n3, n4, n5, n6, n7);
            }
            case 67: {
                return this.sys_select(n2, n3, n4, n5, n6);
            }
            case 78: {
                return this.sys_access(n2, n3);
            }
            case 52: {
                return this.sys_realpath(n2, n3);
            }
            case 76: {
                return this.sys_chown(n2, n3, n4);
            }
            case 43: {
                return this.sys_chown(n2, n3, n4);
            }
            case 77: {
                return this.sys_fchown(n2, n3, n4);
            }
            case 74: {
                return this.sys_chmod(n2, n3, n4);
            }
            case 75: {
                return this.sys_fchmod(n2, n3, n4);
            }
            case 29: {
                return this.sys_fcntl_lock(n2, n3, n4);
            }
            case 73: {
                return this.sys_umask(n2);
            }
            default: {
                return super._syscall(n, n2, n3, n4, n5, n6, n7);
            }
        }
    }
    
    FD _open(String normalizePath, final int n, final int n2) throws ErrnoException {
        normalizePath = this.normalizePath(normalizePath);
        final FD open = this.gs.open(this, normalizePath, n, n2);
        if (open != null && normalizePath != null) {
            open.setNormalizedPath(normalizePath);
        }
        return open;
    }
    
    private int sys_getppid() {
        return (this.parent == null) ? 1 : this.parent.pid;
    }
    
    private int sys_chown(final int n, final int n2, final int n3) {
        return 0;
    }
    
    private int sys_lchown(final int n, final int n2, final int n3) {
        return 0;
    }
    
    private int sys_fchown(final int n, final int n2, final int n3) {
        return 0;
    }
    
    private int sys_chmod(final int n, final int n2, final int n3) {
        return 0;
    }
    
    private int sys_fchmod(final int n, final int n2, final int n3) {
        return 0;
    }
    
    private int sys_umask(final int n) {
        return 0;
    }
    
    private int sys_access(final int n, final int n2) throws ErrnoException, ReadFaultException {
        return (this.gs.stat(this, this.cstring(n)) == null) ? -2 : 0;
    }
    
    private int sys_realpath(final int n, final int n2) throws FaultException {
        final byte[] nullTerminatedBytes = Runtime.getNullTerminatedBytes(this.normalizePath(this.cstring(n)));
        if (nullTerminatedBytes.length > 1024) {
            return -34;
        }
        this.copyout(nullTerminatedBytes, n2, nullTerminatedBytes.length);
        return 0;
    }
    
    private int sys_kill(final int n, final int n2) {
        if (n != n) {
            return -3;
        }
        if (n2 < 0 || n2 >= 32) {
            return -22;
        }
        switch (n2) {
            case 0: {
                return 0;
            }
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 28: {
                break;
            }
            default: {
                this.exit(128 + n2, true);
                break;
            }
        }
        return 0;
    }
    
    private int sys_waitpid(final int n, final int n2, final int n3) throws FaultException, ErrnoException {
        if ((n3 & 0xFFFFFFFE) != 0x0) {
            return -22;
        }
        if (n == 0 || n < -1) {
            System.err.println("WARNING: waitpid called with a pid of " + n);
            return -10;
        }
        final boolean b = (n3 & 0x1) == 0x0;
        if (n != -1 && (n <= 0 || n >= this.gs.tasks.length)) {
            return -10;
        }
        if (this.children == null) {
            return b ? -10 : 0;
        }
        UnixRuntime unixRuntime = null;
        synchronized (this.children) {
            while (true) {
                if (n == -1) {
                    if (this.exitedChildren.size() > 0) {
                        unixRuntime = this.exitedChildren.elementAt(this.exitedChildren.size() - 1);
                        this.exitedChildren.removeElementAt(this.exitedChildren.size() - 1);
                    }
                }
                else {
                    if (n <= 0) {
                        throw new Error("should never happen");
                    }
                    if (n >= this.gs.tasks.length) {
                        return -10;
                    }
                    final UnixRuntime unixRuntime2 = this.gs.tasks[n];
                    if (unixRuntime2.parent != this) {
                        return -10;
                    }
                    if (unixRuntime2.state == 4) {
                        if (!this.exitedChildren.removeElement(unixRuntime2)) {
                            throw new Error("should never happen");
                        }
                        unixRuntime = unixRuntime2;
                    }
                }
                if (unixRuntime != null) {
                    this.gs.tasks[unixRuntime.pid] = null;
                    break;
                }
                if (!b) {
                    return 0;
                }
                try {
                    this.children.wait();
                }
                catch (InterruptedException ex) {}
            }
        }
        if (n2 != 0) {
            this.memWrite(n2, unixRuntime.exitStatus() << 8);
        }
        return unixRuntime.pid;
    }
    
    void _exited() {
        if (this.children != null) {
            synchronized (this.children) {
                final Enumeration<UnixRuntime> elements = this.exitedChildren.elements();
                while (elements.hasMoreElements()) {
                    this.gs.tasks[elements.nextElement().pid] = null;
                }
                this.exitedChildren.removeAllElements();
                final Enumeration<UnixRuntime> elements2 = this.activeChildren.elements();
                while (elements2.hasMoreElements()) {
                    elements2.nextElement().parent = null;
                }
                this.activeChildren.removeAllElements();
            }
        }
        final UnixRuntime parent = this.parent;
        if (parent == null) {
            this.gs.tasks[this.pid] = null;
        }
        else {
            synchronized (parent.children) {
                if (this.parent == null) {
                    this.gs.tasks[this.pid] = null;
                }
                else {
                    if (!this.parent.activeChildren.removeElement(this)) {
                        throw new Error("should never happen _exited: pid: " + this.pid);
                    }
                    this.parent.exitedChildren.addElement(this);
                    this.parent.children.notify();
                }
            }
        }
    }
    
    protected Object clone() throws CloneNotSupportedException {
        final UnixRuntime unixRuntime = (UnixRuntime)super.clone();
        unixRuntime.pid = 0;
        unixRuntime.parent = null;
        unixRuntime.children = null;
        final UnixRuntime unixRuntime2 = unixRuntime;
        final UnixRuntime unixRuntime3 = unixRuntime;
        final Vector vector = null;
        unixRuntime3.exitedChildren = vector;
        unixRuntime2.activeChildren = vector;
        return unixRuntime;
    }
    
    private int sys_fork() {
        UnixRuntime unixRuntime;
        try {
            unixRuntime = (UnixRuntime)this.clone();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return -12;
        }
        unixRuntime.parent = this;
        try {
            unixRuntime._started();
        }
        catch (ProcessTableFullExn processTableFullExn) {
            return -12;
        }
        if (this.children == null) {
            this.children = new Object();
            this.activeChildren = new Vector();
            this.exitedChildren = new Vector();
        }
        this.activeChildren.addElement(unixRuntime);
        final CPUState cpuState = new CPUState();
        this.getCPUState(cpuState);
        cpuState.r[2] = 0;
        final CPUState cpuState2 = cpuState;
        cpuState2.pc += 4;
        unixRuntime.setCPUState(cpuState);
        unixRuntime.state = 2;
        new ForkedProcess(unixRuntime);
        return unixRuntime.pid;
    }
    
    public static int runAndExec(final UnixRuntime unixRuntime, final String s, final String[] array) {
        return runAndExec(unixRuntime, Runtime.concatArgv(s, array));
    }
    
    public static int runAndExec(final UnixRuntime unixRuntime, final String[] array) {
        unixRuntime.start(array);
        return executeAndExec(unixRuntime);
    }
    
    public static int executeAndExec(UnixRuntime execedRuntime) {
        while (true) {
            if (execedRuntime.execute()) {
                if (execedRuntime.state != 5) {
                    break;
                }
                execedRuntime = execedRuntime.execedRuntime;
            }
            else {
                System.err.println("WARNING: Pause requested while executing runAndExec()");
            }
        }
        return execedRuntime.exitStatus();
    }
    
    private String[] readStringArray(final int n) throws ReadFaultException {
        int n2 = 0;
        for (int n3 = n; this.memRead(n3) != 0; n3 += 4) {
            ++n2;
        }
        final String[] array = new String[n2];
        for (int i = 0, n4 = n; i < n2; ++i, n4 += 4) {
            array[i] = this.cstring(this.memRead(n4));
        }
        return array;
    }
    
    private int sys_exec(final int n, final int n2, final int n3) throws ErrnoException, FaultException {
        return this.exec(this.normalizePath(this.cstring(n)), this.readStringArray(n2), this.readStringArray(n3));
    }
    
    public Class runtimeCompile(final Seekable seekable, final String s) throws IOException {
        if (UnixRuntime.runtimeCompilerCompile == null) {
            System.err.println("WARNING: Exec attempted but RuntimeCompiler not found!");
            return null;
        }
        try {
            return (Class)UnixRuntime.runtimeCompilerCompile.invoke(null, seekable, "unixruntime,maxinsnpermethod=256,lessconstants", s);
        }
        catch (IllegalAccessException ex) {
            ex.printStackTrace();
            return null;
        }
        catch (InvocationTargetException ex2) {
            final Throwable targetException = ex2.getTargetException();
            if (targetException instanceof IOException) {
                throw (IOException)targetException;
            }
            if (targetException instanceof RuntimeException) {
                throw (RuntimeException)targetException;
            }
            if (targetException instanceof Error) {
                throw (Error)targetException;
            }
            targetException.printStackTrace();
            return null;
        }
    }
    
    private int exec(final String s, String[] array, final String[] array2) throws ErrnoException {
        if (array.length == 0) {
            array = new String[] { "" };
        }
        if (s.equals("bin/busybox") && this.getClass().getName().endsWith("BusyBox")) {
            return this.execClass(this.getClass(), array, array2);
        }
        final FStat stat = this.gs.stat(this, s);
        if (stat == null) {
            return -2;
        }
        final GlobalState.CacheEnt cacheEnt = this.gs.execCache.get(s);
        final long n = stat.mtime();
        final long n2 = stat.size();
        if (cacheEnt != null) {
            if (cacheEnt.time == n && cacheEnt.size == n2) {
                if (cacheEnt.o instanceof Class) {
                    return this.execClass((Class)cacheEnt.o, array, array2);
                }
                if (cacheEnt.o instanceof String[]) {
                    return this.execScript(s, (String[])cacheEnt.o, array, array2);
                }
                throw new Error("should never happen");
            }
            else {
                this.gs.execCache.remove(s);
            }
        }
        final FD open = this.gs.open(this, s, 0, 0);
        if (open == null) {
            throw new ErrnoException(2);
        }
        final Seekable seekable = open.seekable();
        if (seekable == null) {
            throw new ErrnoException(13);
        }
        final byte[] array3 = new byte[4096];
        try {
            int n3 = seekable.read(array3, 0, array3.length);
            if (n3 == -1) {
                throw new ErrnoException(8);
            }
            switch (array3[0]) {
                case Byte.MAX_VALUE: {
                    if (n3 < 4) {
                        seekable.tryReadFully(array3, n3, 4 - n3);
                    }
                    if (array3[1] != 69 || array3[2] != 76 || array3[3] != 70) {
                        return -8;
                    }
                    seekable.seek(0);
                    System.err.println("Running RuntimeCompiler for " + s);
                    final Class runtimeCompile = this.runtimeCompile(seekable, s);
                    System.err.println("RuntimeCompiler finished for " + s);
                    if (runtimeCompile == null) {
                        throw new ErrnoException(8);
                    }
                    this.gs.execCache.put(s, new GlobalState.CacheEnt(n, n2, runtimeCompile));
                    return this.execClass(runtimeCompile, array, array2);
                }
                case 35: {
                    if (n3 == 1) {
                        final int read = seekable.read(array3, 1, array3.length - 1);
                        if (read == -1) {
                            return -8;
                        }
                        n3 += read;
                    }
                    if (array3[1] != 33) {
                        return -8;
                    }
                    int n4 = 2;
                    n3 -= 2;
                Label_0656:
                    while (true) {
                        for (int i = n4; i < n4 + n3; ++i) {
                            if (array3[i] == 10) {
                                n4 = i;
                                break Label_0656;
                            }
                        }
                        n4 += n3;
                        if (n4 == array3.length) {
                            break;
                        }
                        n3 = seekable.read(array3, n4, array3.length - n4);
                    }
                    int n5;
                    for (n5 = 2; n5 < n4 && array3[n5] == 32; ++n5) {}
                    if (n5 == n4) {
                        throw new ErrnoException(8);
                    }
                    int n6;
                    for (n6 = n5; n6 < n4 && array3[n6] != 32; ++n6) {}
                    final int n7 = n6;
                    while (n6 < n4 && array3[n6] == 32) {
                        ++n6;
                    }
                    final String[] array4 = { new String(array3, n5, n7 - n5), (n6 < n4) ? new String(array3, n6, n4 - n6) : null };
                    this.gs.execCache.put(s, new GlobalState.CacheEnt(n, n2, array4));
                    return this.execScript(s, array4, array, array2);
                }
                default: {
                    return -8;
                }
            }
        }
        catch (IOException ex) {
            return -5;
        }
        finally {
            open.close();
        }
    }
    
    public int execScript(final String s, final String[] array, final String[] array2, final String[] array3) throws ErrnoException {
        final String[] array4 = new String[array2.length - 1 + ((array[1] != null) ? 3 : 2)];
        final int lastIndex = array[0].lastIndexOf(47);
        array4[0] = ((lastIndex == -1) ? array[0] : array[0].substring(lastIndex + 1));
        array4[1] = "/" + s;
        int n = 2;
        if (array[1] != null) {
            array4[n++] = array[1];
        }
        for (int i = 1; i < array2.length; ++i) {
            array4[n++] = array2[i];
        }
        if (n != array4.length) {
            throw new Error("p != newArgv.length");
        }
        System.err.println("Execing: " + array[0]);
        for (int j = 0; j < array4.length; ++j) {
            System.err.println("execing [" + j + "] " + array4[j]);
        }
        return this.exec(array[0], array4, array3);
    }
    
    public int execClass(final Class clazz, final String[] array, final String[] array2) {
        try {
            return this.exec(clazz.getDeclaredConstructor(Boolean.TYPE).newInstance(Boolean.TRUE), array, array2);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return -8;
        }
    }
    
    private int exec(final UnixRuntime execedRuntime, final String[] array, final String[] array2) {
        for (int i = 0; i < 64; ++i) {
            if (this.closeOnExec[i]) {
                this.closeFD(i);
            }
        }
        execedRuntime.fds = this.fds;
        execedRuntime.closeOnExec = this.closeOnExec;
        this.fds = null;
        this.closeOnExec = null;
        execedRuntime.gs = this.gs;
        execedRuntime.sm = this.sm;
        execedRuntime.cwd = this.cwd;
        execedRuntime.pid = this.pid;
        execedRuntime.parent = this.parent;
        execedRuntime.start(array, array2);
        this.state = 5;
        this.execedRuntime = execedRuntime;
        return 0;
    }
    
    private int sys_pipe(final int n) {
        final Pipe pipe = new Pipe();
        final int addFD = this.addFD(pipe.reader);
        if (addFD < 0) {
            return -23;
        }
        final int addFD2 = this.addFD(pipe.writer);
        if (addFD2 < 0) {
            this.closeFD(addFD);
            return -23;
        }
        try {
            this.memWrite(n, addFD);
            this.memWrite(n + 4, addFD2);
        }
        catch (FaultException ex) {
            this.closeFD(addFD);
            this.closeFD(addFD2);
            return -14;
        }
        return 0;
    }
    
    private int sys_dup2(final int n, final int n2) {
        if (n == n2) {
            return 0;
        }
        if (n < 0 || n >= 64) {
            return -81;
        }
        if (n2 < 0 || n2 >= 64) {
            return -81;
        }
        if (this.fds[n] == null) {
            return -81;
        }
        if (this.fds[n2] != null) {
            this.fds[n2].close();
        }
        this.fds[n2] = this.fds[n].dup();
        return 0;
    }
    
    private int sys_dup(final int n) {
        if (n < 0 || n >= 64) {
            return -81;
        }
        if (this.fds[n] == null) {
            return -81;
        }
        final FD dup = this.fds[n].dup();
        final int addFD = this.addFD(dup);
        if (addFD < 0) {
            dup.close();
            return -23;
        }
        return addFD;
    }
    
    private int sys_stat(final int n, final int n2) throws FaultException, ErrnoException {
        final FStat stat = this.gs.stat(this, this.normalizePath(this.cstring(n)));
        if (stat == null) {
            return -2;
        }
        return this.stat(stat, n2);
    }
    
    private int sys_lstat(final int n, final int n2) throws FaultException, ErrnoException {
        final FStat lstat = this.gs.lstat(this, this.normalizePath(this.cstring(n)));
        if (lstat == null) {
            return -2;
        }
        return this.stat(lstat, n2);
    }
    
    private int sys_mkdir(final int n, final int n2) throws FaultException, ErrnoException {
        this.gs.mkdir(this, this.normalizePath(this.cstring(n)), n2);
        return 0;
    }
    
    private int sys_unlink(final int n) throws FaultException, ErrnoException {
        this.gs.unlink(this, this.normalizePath(this.cstring(n)));
        return 0;
    }
    
    private int sys_getcwd(final int n, final int n2) throws FaultException, ErrnoException {
        final byte[] bytes = Runtime.getBytes(this.cwd);
        if (n2 == 0) {
            return -22;
        }
        if (n2 < bytes.length + 2) {
            return -34;
        }
        this.memset(n, 47, 1);
        this.copyout(bytes, n + 1, bytes.length);
        this.memset(n + bytes.length + 1, 0, 1);
        return n;
    }
    
    private int sys_chdir(final int n) throws ErrnoException, FaultException {
        final String normalizePath = this.normalizePath(this.cstring(n));
        final FStat stat = this.gs.stat(this, normalizePath);
        if (stat == null) {
            return -2;
        }
        if (stat.type() != 16384) {
            return -20;
        }
        this.cwd = normalizePath;
        return 0;
    }
    
    private int sys_getdents(final int n, final int n2, int min, final int n3) throws FaultException, ErrnoException {
        min = Math.min(min, 16776192);
        if (n < 0 || n >= 64) {
            return -81;
        }
        if (this.fds[n] == null) {
            return -81;
        }
        final byte[] byteBuf = this.byteBuf(min);
        final int getdents = this.fds[n].getdents(byteBuf, 0, min);
        this.copyout(byteBuf, n2, getdents);
        return getdents;
    }
    
    void _preCloseFD(final FD fd) {
        final Seekable seekable = fd.seekable();
        if (seekable == null) {
            return;
        }
        try {
            for (int i = 0; i < this.gs.locks.length; ++i) {
                final Seekable.Lock lock = this.gs.locks[i];
                if (lock != null) {
                    if (seekable.equals(lock.seekable()) && lock.getOwner() == this) {
                        lock.release();
                        this.gs.locks[i] = null;
                    }
                }
            }
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    void _postCloseFD(final FD fd) {
        if (fd.isMarkedForDeleteOnClose()) {
            try {
                this.gs.unlink(this, fd.getNormalizedPath());
            }
            catch (Throwable t) {}
        }
    }
    
    private int sys_fcntl_lock(final int n, final int n2, final int n3) throws FaultException {
        if (n2 != 7 && n2 != 8) {
            return this.sys_fcntl(n, n2, n3);
        }
        if (n < 0 || n >= 64) {
            return -81;
        }
        if (this.fds[n] == null) {
            return -81;
        }
        final FD fd = this.fds[n];
        if (n3 == 0) {
            return -22;
        }
        final int memRead = this.memRead(n3);
        int memRead2 = this.memRead(n3 + 4);
        final int memRead3 = this.memRead(n3 + 8);
        final int n4 = memRead >> 16;
        final int n5 = memRead & 0xFF;
        final Seekable.Lock[] locks = this.gs.locks;
        final Seekable seekable = fd.seekable();
        if (seekable == null) {
            return -22;
        }
        try {
            switch (n5) {
                case 0: {
                    break;
                }
                case 1: {
                    memRead2 += seekable.pos();
                    break;
                }
                case 2: {
                    memRead2 += seekable.length();
                    break;
                }
                default: {
                    return -1;
                }
            }
            if (n2 == 7) {
                for (int i = 0; i < locks.length; ++i) {
                    if (locks[i] != null) {
                        if (seekable.equals(locks[i].seekable())) {
                            if (locks[i].overlaps(memRead2, memRead3)) {
                                if (locks[i].getOwner() != this) {
                                    if (!locks[i].isShared() || n4 != 1) {
                                        return 0;
                                    }
                                }
                            }
                        }
                    }
                }
                final Seekable.Lock lock = seekable.lock(memRead2, memRead3, n4 == 1);
                if (lock != null) {
                    this.memWrite(n3, 196608);
                    lock.release();
                }
                return 0;
            }
            if (n2 != 8) {
                return -22;
            }
            if (n4 == 3) {
                for (int j = 0; j < locks.length; ++j) {
                    if (locks[j] != null) {
                        if (seekable.equals(locks[j].seekable())) {
                            if (locks[j].getOwner() == this) {
                                final int n6 = (int)locks[j].position();
                                if (n6 >= memRead2) {
                                    if (memRead2 == 0 || memRead3 == 0 || n6 + locks[j].size() <= memRead2 + memRead3) {
                                        locks[j].release();
                                        locks[j] = null;
                                    }
                                }
                            }
                        }
                    }
                }
                return 0;
            }
            if (n4 != 1 && n4 != 2) {
                return -22;
            }
            for (int k = 0; k < locks.length; ++k) {
                if (locks[k] != null) {
                    if (seekable.equals(locks[k].seekable())) {
                        if (locks[k].getOwner() == this) {
                            if (locks[k].contained(memRead2, memRead3)) {
                                locks[k].release();
                                locks[k] = null;
                            }
                            else if (locks[k].contains(memRead2, memRead3)) {
                                if (locks[k].isShared() == (n4 == 1)) {
                                    this.memWrite(n3 + 4, (int)locks[k].position());
                                    this.memWrite(n3 + 8, (int)locks[k].size());
                                    return 0;
                                }
                                locks[k].release();
                                locks[k] = null;
                            }
                        }
                        else if (locks[k].overlaps(memRead2, memRead3) && (!locks[k].isShared() || n4 == 2)) {
                            return -11;
                        }
                    }
                }
            }
            final Seekable.Lock lock2 = seekable.lock(memRead2, memRead3, n4 == 1);
            if (lock2 == null) {
                return -11;
            }
            lock2.setOwner(this);
            int n7;
            for (n7 = 0; n7 < locks.length && locks[n7] != null; ++n7) {}
            if (n7 == locks.length) {
                return -46;
            }
            locks[n7] = lock2;
            return 0;
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    private int sys_socket(final int n, final int n2, final int n3) {
        if (n != 2 || (n2 != 1 && n2 != 2)) {
            return -123;
        }
        return this.addFD(new SocketFD((int)((n2 != 1) ? 1 : 0)));
    }
    
    private SocketFD getSocketFD(final int n) throws ErrnoException {
        if (n < 0 || n >= 64) {
            throw new ErrnoException(81);
        }
        if (this.fds[n] == null) {
            throw new ErrnoException(81);
        }
        if (!(this.fds[n] instanceof SocketFD)) {
            throw new ErrnoException(108);
        }
        return (SocketFD)this.fds[n];
    }
    
    private int sys_connect(final int n, final int n2, final int n3) throws ErrnoException, FaultException {
        final SocketFD socketFD = this.getSocketFD(n);
        if (socketFD.type() == 0 && (socketFD.s != null || socketFD.ss != null)) {
            return -127;
        }
        final int memRead = this.memRead(n2);
        if ((memRead >>> 16 & 0xFF) != 0x2) {
            return -106;
        }
        final int connectPort = memRead & 0xFFFF;
        final byte[] array = new byte[4];
        this.copyin(n2 + 4, array, 4);
        InetAddress inetAddressFromBytes;
        try {
            inetAddressFromBytes = Platform.inetAddressFromBytes(array);
        }
        catch (UnknownHostException ex) {
            return -125;
        }
        socketFD.connectAddr = inetAddressFromBytes;
        socketFD.connectPort = connectPort;
        try {
            switch (socketFD.type()) {
                case 0: {
                    final Socket s = new Socket(inetAddressFromBytes, connectPort);
                    socketFD.s = s;
                    socketFD.setOptions();
                    socketFD.is = s.getInputStream();
                    socketFD.os = s.getOutputStream();
                    break;
                }
                case 1: {
                    break;
                }
                default: {
                    throw new Error("should never happen");
                }
            }
        }
        catch (IOException ex2) {
            return -111;
        }
        return 0;
    }
    
    private int sys_resolve_hostname(final int n, int n2, final int n3) throws FaultException {
        final String cstring = this.cstring(n);
        final int memRead = this.memRead(n3);
        InetAddress[] allByName;
        try {
            allByName = InetAddress.getAllByName(cstring);
        }
        catch (UnknownHostException ex) {
            return 1;
        }
        final int min = Runtime.min(memRead / 4, allByName.length);
        for (int i = 0; i < min; ++i, n2 += 4) {
            this.copyout(allByName[i].getAddress(), n2, 4);
        }
        this.memWrite(n3, min * 4);
        return 0;
    }
    
    private int sys_setsockopt(final int n, final int n2, final int n3, final int n4, final int n5) throws ReadFaultException, ErrnoException {
        final SocketFD socketFD = this.getSocketFD(n);
        switch (n2) {
            case 65535: {
                switch (n3) {
                    case 4:
                    case 8: {
                        if (n5 != 4) {
                            return -22;
                        }
                        if (this.memRead(n4) != 0) {
                            final SocketFD socketFD2 = socketFD;
                            socketFD2.options |= n3;
                        }
                        else {
                            final SocketFD socketFD3 = socketFD;
                            socketFD3.options &= ~n3;
                        }
                        socketFD.setOptions();
                        return 0;
                    }
                    default: {
                        System.err.println("Unknown setsockopt name passed: " + n3);
                        return -109;
                    }
                }
                break;
            }
            default: {
                System.err.println("Unknown setsockopt leve passed: " + n2);
                return -109;
            }
        }
    }
    
    private int sys_getsockopt(final int n, final int n2, final int n3, final int n4, final int n5) throws ErrnoException, FaultException {
        final SocketFD socketFD = this.getSocketFD(n);
        switch (n2) {
            case 65535: {
                switch (n3) {
                    case 4:
                    case 8: {
                        if (this.memRead(n5) < 4) {
                            return -22;
                        }
                        this.memWrite(n4, ((socketFD.options & n3) != 0x0) ? 1 : 0);
                        this.memWrite(n5, 4);
                        return 0;
                    }
                    default: {
                        System.err.println("Unknown setsockopt name passed: " + n3);
                        return -109;
                    }
                }
                break;
            }
            default: {
                System.err.println("Unknown setsockopt leve passed: " + n2);
                return -109;
            }
        }
    }
    
    private int sys_bind(final int n, final int n2, final int n3) throws FaultException, ErrnoException {
        final SocketFD socketFD = this.getSocketFD(n);
        if (socketFD.type() == 0 && (socketFD.s != null || socketFD.ss != null)) {
            return -127;
        }
        final int memRead = this.memRead(n2);
        if ((memRead >>> 16 & 0xFF) != 0x2) {
            return -106;
        }
        final int bindPort = memRead & 0xFFFF;
        InetAddress inetAddressFromBytes = null;
        if (this.memRead(n2 + 4) != 0) {
            final byte[] array = new byte[4];
            this.copyin(n2 + 4, array, 4);
            try {
                inetAddressFromBytes = Platform.inetAddressFromBytes(array);
            }
            catch (UnknownHostException ex) {
                return -125;
            }
        }
        switch (socketFD.type()) {
            case 0: {
                socketFD.bindAddr = inetAddressFromBytes;
                socketFD.bindPort = bindPort;
                return 0;
            }
            case 1: {
                if (socketFD.ds != null) {
                    socketFD.ds.close();
                }
                try {
                    socketFD.ds = ((inetAddressFromBytes != null) ? new DatagramSocket(bindPort, inetAddressFromBytes) : new DatagramSocket(bindPort));
                }
                catch (IOException ex2) {
                    return -112;
                }
                return 0;
            }
            default: {
                throw new Error("should never happen");
            }
        }
    }
    
    private int sys_listen(final int n, final int n2) throws ErrnoException {
        final SocketFD socketFD = this.getSocketFD(n);
        if (socketFD.type() != 0) {
            return -95;
        }
        if (socketFD.ss != null || socketFD.s != null) {
            return -127;
        }
        if (socketFD.bindPort < 0) {
            return -95;
        }
        try {
            socketFD.ss = new ServerSocket(socketFD.bindPort, n2, socketFD.bindAddr);
            final SocketFD socketFD2 = socketFD;
            socketFD2.flags |= 0x2;
            return 0;
        }
        catch (IOException ex) {
            return -112;
        }
    }
    
    private int sys_accept(final int n, final int n2, final int n3) throws ErrnoException, FaultException {
        final SocketFD socketFD = this.getSocketFD(n);
        if (socketFD.type() != 0) {
            return -95;
        }
        if (!socketFD.listen()) {
            return -95;
        }
        final int memRead = this.memRead(n3);
        final ServerSocket ss = socketFD.ss;
        Socket accept;
        try {
            accept = ss.accept();
        }
        catch (IOException ex) {
            return -5;
        }
        if (memRead >= 8) {
            this.memWrite(n2, 0x6020000 | accept.getPort());
            this.copyout(accept.getInetAddress().getAddress(), n2 + 4, 4);
            this.memWrite(n3, 8);
        }
        final SocketFD socketFD2 = new SocketFD(0);
        socketFD2.s = accept;
        try {
            socketFD2.is = accept.getInputStream();
            socketFD2.os = accept.getOutputStream();
        }
        catch (IOException ex2) {
            return -5;
        }
        final int addFD = this.addFD(socketFD2);
        if (addFD == -1) {
            socketFD2.close();
            return -23;
        }
        return addFD;
    }
    
    private int sys_shutdown(final int n, final int n2) throws ErrnoException {
        final SocketFD socketFD = this.getSocketFD(n);
        if (socketFD.type() != 0 || socketFD.listen()) {
            return -95;
        }
        if (socketFD.s == null) {
            return -128;
        }
        final Socket s = socketFD.s;
        try {
            if (n2 == 0 || n2 == 2) {
                Platform.socketHalfClose(s, false);
            }
            if (n2 == 1 || n2 == 2) {
                Platform.socketHalfClose(s, true);
            }
        }
        catch (IOException ex) {
            return -5;
        }
        return 0;
    }
    
    private int sys_sendto(final int n, final int n2, int min, final int n3, final int n4, final int n5) throws ErrnoException, ReadFaultException {
        final SocketFD socketFD = this.getSocketFD(n);
        if (n3 != 0) {
            throw new ErrnoException(22);
        }
        final int memRead = this.memRead(n4);
        if ((memRead >>> 16 & 0xFF) != 0x2) {
            return -106;
        }
        final int n6 = memRead & 0xFFFF;
        final byte[] array = new byte[4];
        this.copyin(n4 + 4, array, 4);
        InetAddress inetAddressFromBytes;
        try {
            inetAddressFromBytes = Platform.inetAddressFromBytes(array);
        }
        catch (UnknownHostException ex2) {
            return -125;
        }
        min = Math.min(min, 16776192);
        final byte[] byteBuf = this.byteBuf(min);
        this.copyin(n2, byteBuf, min);
        try {
            return socketFD.sendto(byteBuf, 0, min, inetAddressFromBytes, n6);
        }
        catch (ErrnoException ex) {
            if (ex.errno == 32) {
                this.exit(141, true);
            }
            throw ex;
        }
    }
    
    private int sys_recvfrom(final int n, final int n2, int min, final int n3, final int n4, final int n5) throws ErrnoException, FaultException {
        final SocketFD socketFD = this.getSocketFD(n);
        if (n3 != 0) {
            throw new ErrnoException(22);
        }
        final InetAddress[] array = (InetAddress[])((n4 == 0) ? null : new InetAddress[1]);
        final int[] array2 = (int[])((n4 == 0) ? null : new int[1]);
        min = Math.min(min, 16776192);
        final byte[] byteBuf = this.byteBuf(min);
        final int recvfrom = socketFD.recvfrom(byteBuf, 0, min, array, array2);
        this.copyout(byteBuf, n2, recvfrom);
        if (n4 != 0) {
            this.memWrite(n4, 0x20000 | array2[0]);
            this.copyout(array[0].getAddress(), n4 + 4, 4);
        }
        return recvfrom;
    }
    
    private int sys_select(final int n, final int n2, final int n3, final int n4, final int n5) throws ReadFaultException, ErrnoException {
        return -88;
    }
    
    private static String hostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        }
        catch (UnknownHostException ex) {
            return "darkstar";
        }
    }
    
    private int sys_sysctl(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) throws FaultException {
        if (n5 != 0) {
            return -1;
        }
        if (n2 == 0) {
            return -2;
        }
        if (n3 == 0) {
            return 0;
        }
        Serializable hostName = null;
        Label_0193: {
            switch (this.memRead(n)) {
                case 1: {
                    if (n2 != 2) {
                        break;
                    }
                    switch (this.memRead(n + 4)) {
                        case 1: {
                            hostName = "NestedVM";
                            break;
                        }
                        case 10: {
                            hostName = hostName();
                            break;
                        }
                        case 2: {
                            hostName = "1.0";
                            break;
                        }
                        case 4: {
                            hostName = "NestedVM Kernel Version 1.0";
                            break;
                        }
                    }
                    break;
                }
                case 6: {
                    if (n2 != 2) {
                        break;
                    }
                    switch (this.memRead(n + 4)) {
                        case 1: {
                            hostName = "NestedVM Virtual Machine";
                            break Label_0193;
                        }
                    }
                    break;
                }
            }
        }
        if (hostName == null) {
            return -2;
        }
        final int memRead = this.memRead(n4);
        if (hostName instanceof String) {
            final byte[] nullTerminatedBytes = Runtime.getNullTerminatedBytes((String)hostName);
            if (memRead < nullTerminatedBytes.length) {
                return -12;
            }
            final int length = nullTerminatedBytes.length;
            this.copyout(nullTerminatedBytes, n3, length);
            this.memWrite(n4, length);
        }
        else {
            if (!(hostName instanceof Integer)) {
                throw new Error("should never happen");
            }
            if (memRead < 4) {
                return -12;
            }
            this.memWrite(n3, (int)hostName);
        }
        return 0;
    }
    
    private String normalizePath(final String s) {
        final boolean startsWith = s.startsWith("/");
        final int length = this.cwd.length();
        if (!s.startsWith(".") && s.indexOf("./") == -1 && s.indexOf("//") == -1 && !s.endsWith(".")) {
            return startsWith ? s.substring(1) : ((length == 0) ? s : ((s.length() == 0) ? this.cwd : (this.cwd + "/" + s)));
        }
        final char[] array = new char[s.length() + 1];
        final char[] array2 = new char[array.length + (startsWith ? -1 : this.cwd.length())];
        s.getChars(0, s.length(), array, 0);
        int n = 0;
        int n2 = 0;
        if (startsWith) {
            do {
                ++n;
            } while (array[n] == '/');
        }
        else if (length != 0) {
            this.cwd.getChars(0, length, array2, 0);
            n2 = length;
        }
        while (array[n] != '\0') {
            if (n != 0) {
                while (array[n] != '\0' && array[n] != '/') {
                    array2[n2++] = array[n++];
                }
                if (array[n] == '\0') {
                    break;
                }
                while (array[n] == '/') {
                    ++n;
                }
            }
            if (array[n] == '\0') {
                break;
            }
            if (array[n] != '.') {
                array2[n2++] = '/';
                array2[n2++] = array[n++];
            }
            else if (array[n + 1] == '\0' || array[n + 1] == '/') {
                ++n;
            }
            else if (array[n + 1] == '.' && (array[n + 2] == '\0' || array[n + 2] == '/')) {
                n += 2;
                if (n2 > 0) {
                    --n2;
                }
                while (n2 > 0 && array2[n2] != '/') {
                    --n2;
                }
            }
            else {
                ++n;
                array2[n2++] = '/';
                array2[n2++] = '.';
            }
        }
        if (n2 > 0 && array2[n2 - 1] == '/') {
            --n2;
        }
        final int n3 = (array2[0] == '/') ? 1 : 0;
        return new String(array2, n3, n2 - n3);
    }
    
    FStat hostFStat(final File file, final Object o) {
        boolean b = false;
        try {
            final FileInputStream fileInputStream = new FileInputStream(file);
            switch (fileInputStream.read()) {
                case 127: {
                    b = (fileInputStream.read() == 69 && fileInputStream.read() == 76 && fileInputStream.read() == 70);
                    break;
                }
                case 35: {
                    b = (fileInputStream.read() == 33);
                    break;
                }
            }
            fileInputStream.close();
        }
        catch (IOException ex) {}
        final HostFS hostFS = (HostFS)o;
        return new HostFStat(file, b) {
            private final /* synthetic */ int val$inode = hostFS.inodes.get(file.getAbsolutePath());
            private final /* synthetic */ int val$devno = hostFS.devno;
            
            public int inode() {
                return this.val$inode;
            }
            
            public int dev() {
                return this.val$devno;
            }
        };
    }
    
    FD hostFSDirFD(final File file, final Object o) {
        return (HostFS)o.new HostDirFD(file);
    }
    
    private static void putInt(final byte[] array, final int n, final int n2) {
        array[n + 0] = (byte)(n2 >>> 24 & 0xFF);
        array[n + 1] = (byte)(n2 >>> 16 & 0xFF);
        array[n + 2] = (byte)(n2 >>> 8 & 0xFF);
        array[n + 3] = (byte)(n2 >>> 0 & 0xFF);
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
        defaultGS = new GlobalState();
        Method method;
        try {
            method = Class.forName("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.RuntimeCompiler").getMethod("compile", (UnixRuntime.class$org$ibex$nestedvm$util$Seekable == null) ? (UnixRuntime.class$org$ibex$nestedvm$util$Seekable = class$("org.bukkit.craftbukkit.libs.org.ibex.nestedvm.util.Seekable")) : UnixRuntime.class$org$ibex$nestedvm$util$Seekable, (UnixRuntime.class$java$lang$String == null) ? (UnixRuntime.class$java$lang$String = class$("java.lang.String")) : UnixRuntime.class$java$lang$String, (UnixRuntime.class$java$lang$String == null) ? (UnixRuntime.class$java$lang$String = class$("java.lang.String")) : UnixRuntime.class$java$lang$String);
        }
        catch (NoSuchMethodException ex) {
            method = null;
        }
        catch (ClassNotFoundException ex2) {
            method = null;
        }
        runtimeCompilerCompile = method;
    }
    
    private static class ProcessTableFullExn extends RuntimeException
    {
    }
    
    public static final class ForkedProcess extends Thread
    {
        private final UnixRuntime initial;
        
        public ForkedProcess(final UnixRuntime initial) {
            this.initial = initial;
            this.start();
        }
        
        public void run() {
            UnixRuntime.executeAndExec(this.initial);
        }
    }
    
    static class Pipe
    {
        private final byte[] pipebuf;
        private int readPos;
        private int writePos;
        public final FD reader;
        public final FD writer;
        
        Pipe() {
            this.pipebuf = new byte[2048];
            this.reader = new Reader();
            this.writer = new Writer();
        }
        
        public class Reader extends FD
        {
            protected FStat _fstat() {
                return new SocketFStat();
            }
            
            public int read(final byte[] array, final int n, int min) throws ErrnoException {
                if (min == 0) {
                    return 0;
                }
                synchronized (Pipe.this) {
                    while (Pipe.this.writePos != -1 && Pipe.this.readPos == Pipe.this.writePos) {
                        try {
                            Pipe.this.wait();
                        }
                        catch (InterruptedException ex) {}
                    }
                    if (Pipe.this.writePos == -1) {
                        return 0;
                    }
                    min = Math.min(min, Pipe.this.writePos - Pipe.this.readPos);
                    System.arraycopy(Pipe.this.pipebuf, Pipe.this.readPos, array, n, min);
                    Pipe.this.readPos += min;
                    if (Pipe.this.readPos == Pipe.this.writePos) {
                        Pipe.this.notify();
                    }
                    return min;
                }
            }
            
            public int flags() {
                return 0;
            }
            
            public void _close() {
                synchronized (Pipe.this) {
                    Pipe.this.readPos = -1;
                    Pipe.this.notify();
                }
            }
        }
        
        public class Writer extends FD
        {
            protected FStat _fstat() {
                return new SocketFStat();
            }
            
            public int write(final byte[] array, final int n, int min) throws ErrnoException {
                if (min == 0) {
                    return 0;
                }
                synchronized (Pipe.this) {
                    if (Pipe.this.readPos == -1) {
                        throw new ErrnoException(32);
                    }
                    if (Pipe.this.pipebuf.length - Pipe.this.writePos < Math.min(min, 512)) {
                        while (Pipe.this.readPos != -1 && Pipe.this.readPos != Pipe.this.writePos) {
                            try {
                                Pipe.this.wait();
                            }
                            catch (InterruptedException ex) {}
                        }
                        if (Pipe.this.readPos == -1) {
                            throw new ErrnoException(32);
                        }
                        Pipe.this.readPos = (Pipe.this.writePos = 0);
                    }
                    min = Math.min(min, Pipe.this.pipebuf.length - Pipe.this.writePos);
                    System.arraycopy(array, n, Pipe.this.pipebuf, Pipe.this.writePos, min);
                    if (Pipe.this.readPos == Pipe.this.writePos) {
                        Pipe.this.notify();
                    }
                    Pipe.this.writePos += min;
                    return min;
                }
            }
            
            public int flags() {
                return 1;
            }
            
            public void _close() {
                synchronized (Pipe.this) {
                    Pipe.this.writePos = -1;
                    Pipe.this.notify();
                }
            }
        }
    }
    
    static class SocketFD extends FD
    {
        public static final int TYPE_STREAM = 0;
        public static final int TYPE_DGRAM = 1;
        public static final int LISTEN = 2;
        int flags;
        int options;
        Socket s;
        ServerSocket ss;
        DatagramSocket ds;
        InetAddress bindAddr;
        int bindPort;
        InetAddress connectAddr;
        int connectPort;
        DatagramPacket dp;
        InputStream is;
        OutputStream os;
        private static final byte[] EMPTY;
        
        public int type() {
            return this.flags & 0x1;
        }
        
        public boolean listen() {
            return (this.flags & 0x2) != 0x0;
        }
        
        public SocketFD(final int flags) {
            this.bindPort = -1;
            this.connectPort = -1;
            this.flags = flags;
            if (flags == 1) {
                this.dp = new DatagramPacket(SocketFD.EMPTY, 0);
            }
        }
        
        public void setOptions() {
            try {
                if (this.s != null && this.type() == 0 && !this.listen()) {
                    Platform.socketSetKeepAlive(this.s, (this.options & 0x8) != 0x0);
                }
            }
            catch (SocketException ex) {
                ex.printStackTrace();
            }
        }
        
        public void _close() {
            try {
                if (this.s != null) {
                    this.s.close();
                }
                if (this.ss != null) {
                    this.ss.close();
                }
                if (this.ds != null) {
                    this.ds.close();
                }
            }
            catch (IOException ex) {}
        }
        
        public int read(final byte[] array, final int n, final int n2) throws ErrnoException {
            if (this.type() == 1) {
                return this.recvfrom(array, n, n2, null, null);
            }
            if (this.is == null) {
                throw new ErrnoException(32);
            }
            try {
                final int read = this.is.read(array, n, n2);
                return (read < 0) ? 0 : read;
            }
            catch (IOException ex) {
                throw new ErrnoException(5);
            }
        }
        
        public int recvfrom(final byte[] data, final int n, final int length, final InetAddress[] array, final int[] array2) throws ErrnoException {
            if (this.type() == 0) {
                return this.read(data, n, length);
            }
            if (n != 0) {
                throw new IllegalArgumentException("off must be 0");
            }
            this.dp.setData(data);
            this.dp.setLength(length);
            try {
                if (this.ds == null) {
                    this.ds = new DatagramSocket();
                }
                this.ds.receive(this.dp);
            }
            catch (IOException ex) {
                ex.printStackTrace();
                throw new ErrnoException(5);
            }
            if (array != null) {
                array[0] = this.dp.getAddress();
                array2[0] = this.dp.getPort();
            }
            return this.dp.getLength();
        }
        
        public int write(final byte[] array, final int n, final int n2) throws ErrnoException {
            if (this.type() == 1) {
                return this.sendto(array, n, n2, null, -1);
            }
            if (this.os == null) {
                throw new ErrnoException(32);
            }
            try {
                this.os.write(array, n, n2);
                return n2;
            }
            catch (IOException ex) {
                throw new ErrnoException(5);
            }
        }
        
        public int sendto(final byte[] data, final int n, final int length, InetAddress connectAddr, int connectPort) throws ErrnoException {
            if (n != 0) {
                throw new IllegalArgumentException("off must be 0");
            }
            if (this.type() == 0) {
                return this.write(data, n, length);
            }
            if (connectAddr == null) {
                connectAddr = this.connectAddr;
                connectPort = this.connectPort;
                if (connectAddr == null) {
                    throw new ErrnoException(128);
                }
            }
            this.dp.setAddress(connectAddr);
            this.dp.setPort(connectPort);
            this.dp.setData(data);
            this.dp.setLength(length);
            try {
                if (this.ds == null) {
                    this.ds = new DatagramSocket();
                }
                this.ds.send(this.dp);
            }
            catch (IOException ex) {
                ex.printStackTrace();
                if ("Network is unreachable".equals(ex.getMessage())) {
                    throw new ErrnoException(118);
                }
                throw new ErrnoException(5);
            }
            return this.dp.getLength();
        }
        
        public int flags() {
            return 2;
        }
        
        public FStat _fstat() {
            return new SocketFStat();
        }
        
        static {
            EMPTY = new byte[0];
        }
    }
    
    public static final class GlobalState
    {
        Hashtable execCache;
        final UnixRuntime[] tasks;
        int nextPID;
        Seekable.Lock[] locks;
        private MP[] mps;
        private FS root;
        
        public GlobalState() {
            this(255);
        }
        
        public GlobalState(final int n) {
            this(n, true);
        }
        
        public GlobalState(final int n, final boolean b) {
            this.execCache = new Hashtable();
            this.nextPID = 1;
            this.locks = new Seekable.Lock[16];
            this.mps = new MP[0];
            this.tasks = new UnixRuntime[n + 1];
            if (b) {
                File root;
                if (Platform.getProperty("nestedvm.root") != null) {
                    root = new File(Platform.getProperty("nestedvm.root"));
                    if (!root.isDirectory()) {
                        throw new IllegalArgumentException("nestedvm.root is not a directory");
                    }
                }
                else {
                    final String property = Platform.getProperty("user.dir");
                    root = Platform.getRoot(new File((property != null) ? property : "."));
                }
                this.addMount("/", new HostFS(root));
                if (Platform.getProperty("nestedvm.root") == null) {
                    final File[] listRoots = Platform.listRoots();
                    for (int i = 0; i < listRoots.length; ++i) {
                        String s = listRoots[i].getPath();
                        if (s.endsWith(File.separator)) {
                            s = s.substring(0, s.length() - 1);
                        }
                        if (s.length() != 0) {
                            if (s.indexOf(47) == -1) {
                                this.addMount("/" + s.toLowerCase(), new HostFS(listRoots[i]));
                            }
                        }
                    }
                }
                this.addMount("/dev", new DevFS());
                this.addMount("/resource", new ResourceFS());
                this.addMount("/cygdrive", new CygdriveFS());
            }
        }
        
        public String mapHostPath(final String s) {
            return this.mapHostPath(new File(s));
        }
        
        public String mapHostPath(File file) {
            final FS root;
            synchronized (this) {
                this.mps = this.mps;
                root = this.root;
            }
            if (!file.isAbsolute()) {
                file = new File(file.getAbsolutePath());
            }
            for (int i = this.mps.length; i >= 0; --i) {
                final FS fs = (i == this.mps.length) ? root : this.mps[i].fs;
                final String s = (i == this.mps.length) ? "" : this.mps[i].path;
                if (fs instanceof HostFS) {
                    File root2 = ((HostFS)fs).getRoot();
                    if (!root2.isAbsolute()) {
                        root2 = new File(root2.getAbsolutePath());
                    }
                    if (file.getPath().startsWith(root2.getPath())) {
                        final char separatorChar = File.separatorChar;
                        String substring = file.getPath().substring(root2.getPath().length());
                        if (separatorChar != '/') {
                            final char[] charArray = substring.toCharArray();
                            for (int j = 0; j < charArray.length; ++j) {
                                if (charArray[j] == '/') {
                                    charArray[j] = separatorChar;
                                }
                                else if (charArray[j] == separatorChar) {
                                    charArray[j] = '/';
                                }
                            }
                            substring = new String(charArray);
                        }
                        return "/" + ((s.length() == 0) ? "" : (s + "/")) + substring;
                    }
                }
            }
            return null;
        }
        
        public synchronized FS getMount(String substring) {
            if (!substring.startsWith("/")) {
                throw new IllegalArgumentException("Mount point doesn't start with a /");
            }
            if (substring.equals("/")) {
                return this.root;
            }
            substring = substring.substring(1);
            for (int i = 0; i < this.mps.length; ++i) {
                if (this.mps[i].path.equals(substring)) {
                    return this.mps[i].fs;
                }
            }
            return null;
        }
        
        public synchronized void addMount(String substring, final FS root) {
            if (this.getMount(substring) != null) {
                throw new IllegalArgumentException("mount point already exists");
            }
            if (!substring.startsWith("/")) {
                throw new IllegalArgumentException("Mount point doesn't start with a /");
            }
            if (root.owner != null) {
                root.owner.removeMount(root);
            }
            root.owner = this;
            if (substring.equals("/")) {
                this.root = root;
                root.devno = 1;
                return;
            }
            substring = substring.substring(1);
            final int length = this.mps.length;
            final MP[] mps = new MP[length + 1];
            if (length != 0) {
                System.arraycopy(this.mps, 0, mps, 0, length);
            }
            mps[length] = new MP(substring, root);
            Sort.sort(mps);
            this.mps = mps;
            int max = 0;
            for (int i = 0; i < this.mps.length; ++i) {
                max = Runtime.max(max, this.mps[i].fs.devno);
            }
            root.devno = max + 2;
        }
        
        public synchronized void removeMount(final FS fs) {
            for (int i = 0; i < this.mps.length; ++i) {
                if (this.mps[i].fs == fs) {
                    this.removeMount(i);
                    return;
                }
            }
            throw new IllegalArgumentException("mount point doesn't exist");
        }
        
        public synchronized void removeMount(String substring) {
            if (!substring.startsWith("/")) {
                throw new IllegalArgumentException("Mount point doesn't start with a /");
            }
            if (substring.equals("/")) {
                this.removeMount(-1);
            }
            else {
                int n;
                for (substring = substring.substring(1), n = 0; n < this.mps.length && !this.mps[n].path.equals(substring); ++n) {}
                if (n == this.mps.length) {
                    throw new IllegalArgumentException("mount point doesn't exist");
                }
                this.removeMount(n);
            }
        }
        
        private void removeMount(final int n) {
            if (n == -1) {
                this.root.owner = null;
                this.root = null;
                return;
            }
            final MP[] mps = new MP[this.mps.length - 1];
            System.arraycopy(this.mps, 0, mps, 0, n);
            System.arraycopy(this.mps, 0, mps, n, this.mps.length - n - 1);
            this.mps = mps;
        }
        
        private Object fsop(final int n, final UnixRuntime unixRuntime, final String s, final int n2, final int n3) throws ErrnoException {
            final int length = s.length();
            if (length != 0) {
                final MP[] mps;
                synchronized (this) {
                    mps = this.mps;
                }
                for (int i = 0; i < mps.length; ++i) {
                    final MP mp = mps[i];
                    final int length2 = mp.path.length();
                    if (s.startsWith(mp.path) && (length == length2 || s.charAt(length2) == '/')) {
                        return mp.fs.dispatch(n, unixRuntime, (length == length2) ? "" : s.substring(length2 + 1), n2, n3);
                    }
                }
            }
            return this.root.dispatch(n, unixRuntime, s, n2, n3);
        }
        
        public final FD open(final UnixRuntime unixRuntime, final String s, final int n, final int n2) throws ErrnoException {
            return (FD)this.fsop(1, unixRuntime, s, n, n2);
        }
        
        public final FStat stat(final UnixRuntime unixRuntime, final String s) throws ErrnoException {
            return (FStat)this.fsop(2, unixRuntime, s, 0, 0);
        }
        
        public final FStat lstat(final UnixRuntime unixRuntime, final String s) throws ErrnoException {
            return (FStat)this.fsop(3, unixRuntime, s, 0, 0);
        }
        
        public final void mkdir(final UnixRuntime unixRuntime, final String s, final int n) throws ErrnoException {
            this.fsop(4, unixRuntime, s, n, 0);
        }
        
        public final void unlink(final UnixRuntime unixRuntime, final String s) throws ErrnoException {
            this.fsop(5, unixRuntime, s, 0, 0);
        }
        
        static class MP implements Sort.Comparable
        {
            public String path;
            public FS fs;
            
            public MP(final String path, final FS fs) {
                this.path = path;
                this.fs = fs;
            }
            
            public int compareTo(final Object o) {
                if (!(o instanceof MP)) {
                    return 1;
                }
                return -this.path.compareTo(((MP)o).path);
            }
        }
        
        private static class CacheEnt
        {
            public final long time;
            public final long size;
            public final Object o;
            
            public CacheEnt(final long time, final long size, final Object o) {
                this.time = time;
                this.size = size;
                this.o = o;
            }
        }
    }
    
    public abstract static class FS
    {
        static final int OPEN = 1;
        static final int STAT = 2;
        static final int LSTAT = 3;
        static final int MKDIR = 4;
        static final int UNLINK = 5;
        GlobalState owner;
        int devno;
        
        Object dispatch(final int n, final UnixRuntime unixRuntime, final String s, final int n2, final int n3) throws ErrnoException {
            switch (n) {
                case 1: {
                    return this.open(unixRuntime, s, n2, n3);
                }
                case 2: {
                    return this.stat(unixRuntime, s);
                }
                case 3: {
                    return this.lstat(unixRuntime, s);
                }
                case 4: {
                    this.mkdir(unixRuntime, s, n2);
                    return null;
                }
                case 5: {
                    this.unlink(unixRuntime, s);
                    return null;
                }
                default: {
                    throw new Error("should never happen");
                }
            }
        }
        
        public FStat lstat(final UnixRuntime unixRuntime, final String s) throws ErrnoException {
            return this.stat(unixRuntime, s);
        }
        
        public abstract FD open(final UnixRuntime p0, final String p1, final int p2, final int p3) throws ErrnoException;
        
        public abstract FStat stat(final UnixRuntime p0, final String p1) throws ErrnoException;
        
        public abstract void mkdir(final UnixRuntime p0, final String p1, final int p2) throws ErrnoException;
        
        public abstract void unlink(final UnixRuntime p0, final String p1) throws ErrnoException;
    }
    
    public static class HostFS extends FS
    {
        InodeCache inodes;
        protected File root;
        
        public File getRoot() {
            return this.root;
        }
        
        protected File hostFile(String s) {
            final char separatorChar = File.separatorChar;
            if (separatorChar != '/') {
                final char[] charArray = s.toCharArray();
                for (int i = 0; i < charArray.length; ++i) {
                    final char c = charArray[i];
                    if (c == '/') {
                        charArray[i] = separatorChar;
                    }
                    else if (c == separatorChar) {
                        charArray[i] = '/';
                    }
                }
                s = new String(charArray);
            }
            return new File(this.root, s);
        }
        
        public HostFS(final String s) {
            this(new File(s));
        }
        
        public HostFS(final File root) {
            this.inodes = new InodeCache(4000);
            this.root = root;
        }
        
        public FD open(final UnixRuntime unixRuntime, final String s, final int n, final int n2) throws ErrnoException {
            return unixRuntime.hostFSOpen(this.hostFile(s), n, n2, this);
        }
        
        public void unlink(final UnixRuntime unixRuntime, final String s) throws ErrnoException {
            final File hostFile = this.hostFile(s);
            if (unixRuntime.sm != null && !unixRuntime.sm.allowUnlink(hostFile)) {
                throw new ErrnoException(1);
            }
            if (!hostFile.exists()) {
                throw new ErrnoException(2);
            }
            if (!hostFile.delete()) {
                boolean b = false;
                for (int i = 0; i < 64; ++i) {
                    if (unixRuntime.fds[i] != null) {
                        final String normalizedPath = unixRuntime.fds[i].getNormalizedPath();
                        if (normalizedPath != null && normalizedPath.equals(s)) {
                            unixRuntime.fds[i].markDeleteOnClose();
                            b = true;
                        }
                    }
                }
                if (!b) {
                    throw new ErrnoException(1);
                }
            }
        }
        
        public FStat stat(final UnixRuntime unixRuntime, final String s) throws ErrnoException {
            final File hostFile = this.hostFile(s);
            if (unixRuntime.sm != null && !unixRuntime.sm.allowStat(hostFile)) {
                throw new ErrnoException(13);
            }
            if (!hostFile.exists()) {
                return null;
            }
            return unixRuntime.hostFStat(hostFile, this);
        }
        
        public void mkdir(final UnixRuntime unixRuntime, final String s, final int n) throws ErrnoException {
            final File hostFile = this.hostFile(s);
            if (unixRuntime.sm != null && !unixRuntime.sm.allowWrite(hostFile)) {
                throw new ErrnoException(13);
            }
            if (hostFile.exists() && hostFile.isDirectory()) {
                throw new ErrnoException(17);
            }
            if (hostFile.exists()) {
                throw new ErrnoException(20);
            }
            final File parentFile = getParentFile(hostFile);
            if (parentFile != null && (!parentFile.exists() || !parentFile.isDirectory())) {
                throw new ErrnoException(20);
            }
            if (!hostFile.mkdir()) {
                throw new ErrnoException(5);
            }
        }
        
        private static File getParentFile(final File file) {
            final String parent = file.getParent();
            return (parent == null) ? null : new File(parent);
        }
        
        public class HostDirFD extends DirFD
        {
            private final File f;
            private final File[] children;
            
            public HostDirFD(final File f) {
                this.f = f;
                final String[] list = f.list();
                this.children = new File[list.length];
                for (int i = 0; i < list.length; ++i) {
                    this.children[i] = new File(f, list[i]);
                }
            }
            
            public int size() {
                return this.children.length;
            }
            
            public String name(final int n) {
                return this.children[n].getName();
            }
            
            public int inode(final int n) {
                return HostFS.this.inodes.get(this.children[n].getAbsolutePath());
            }
            
            public int parentInode() {
                final File access$400 = getParentFile(this.f);
                return (access$400 == null) ? this.myInode() : HostFS.this.inodes.get(access$400.getAbsolutePath());
            }
            
            public int myInode() {
                return HostFS.this.inodes.get(this.f.getAbsolutePath());
            }
            
            public int myDev() {
                return HostFS.this.devno;
            }
        }
    }
    
    public static class CygdriveFS extends HostFS
    {
        protected File hostFile(String string) {
            final char char1 = string.charAt(0);
            if (char1 < 'a' || char1 > 'z' || string.charAt(1) != '/') {
                return null;
            }
            string = char1 + ":" + string.substring(1).replace('/', '\\');
            return new File(string);
        }
        
        public CygdriveFS() {
            super("/");
        }
    }
    
    public abstract static class DirFD extends FD
    {
        private int pos;
        
        public DirFD() {
            this.pos = -2;
        }
        
        protected abstract int size();
        
        protected abstract String name(final int p0);
        
        protected abstract int inode(final int p0);
        
        protected abstract int myDev();
        
        protected abstract int parentInode();
        
        protected abstract int myInode();
        
        public int flags() {
            return 0;
        }
        
        public int getdents(final byte[] array, int n, int n2) {
            final int n3 = n;
        Label_0247:
            while (n2 > 0 && this.pos < this.size()) {
                Label_0234: {
                    int inode = 0;
                    int n4 = 0;
                    switch (this.pos) {
                        case -2:
                        case -1: {
                            inode = ((this.pos == -1) ? this.parentInode() : this.myInode());
                            if (inode == -1) {
                                break Label_0234;
                            }
                            n4 = 9 + ((this.pos == -1) ? 2 : 1);
                            if (n4 > n2) {
                                break Label_0247;
                            }
                            array[n + 8] = 46;
                            if (this.pos == -1) {
                                array[n + 9] = 46;
                                break;
                            }
                            break;
                        }
                        default: {
                            final byte[] bytes = Runtime.getBytes(this.name(this.pos));
                            n4 = bytes.length + 9;
                            if (n4 > n2) {
                                break Label_0247;
                            }
                            inode = this.inode(this.pos);
                            System.arraycopy(bytes, 0, array, n + 8, bytes.length);
                            break;
                        }
                    }
                    array[n + n4 - 1] = 0;
                    final int n5 = n4 + 3 & 0xFFFFFFFC;
                    putInt(array, n, n5);
                    putInt(array, n + 4, inode);
                    n += n5;
                    n2 -= n5;
                }
                ++this.pos;
            }
            return n - n3;
        }
        
        protected FStat _fstat() {
            return new FStat() {
                public int type() {
                    return 16384;
                }
                
                public int inode() {
                    return DirFD.this.myInode();
                }
                
                public int dev() {
                    return DirFD.this.myDev();
                }
            };
        }
    }
    
    public static class DevFS extends FS
    {
        private static final int ROOT_INODE = 1;
        private static final int NULL_INODE = 2;
        private static final int ZERO_INODE = 3;
        private static final int FD_INODE = 4;
        private static final int FD_INODES = 32;
        private FD devZeroFD;
        private FD devNullFD;
        
        public DevFS() {
            this.devZeroFD = new FD() {
                public int read(final byte[] array, final int n, final int n2) {
                    for (int i = n; i < n + n2; ++i) {
                        array[i] = 0;
                    }
                    return n2;
                }
                
                public int write(final byte[] array, final int n, final int n2) {
                    return n2;
                }
                
                public int seek(final int n, final int n2) {
                    return 0;
                }
                
                public FStat _fstat() {
                    return new DevFStat() {
                        public int inode() {
                            return 3;
                        }
                    };
                }
                
                public int flags() {
                    return 2;
                }
            };
            this.devNullFD = new FD() {
                public int read(final byte[] array, final int n, final int n2) {
                    return 0;
                }
                
                public int write(final byte[] array, final int n, final int n2) {
                    return n2;
                }
                
                public int seek(final int n, final int n2) {
                    return 0;
                }
                
                public FStat _fstat() {
                    return new DevFStat() {
                        public int inode() {
                            return 2;
                        }
                    };
                }
                
                public int flags() {
                    return 2;
                }
            };
        }
        
        public FD open(final UnixRuntime unixRuntime, final String s, final int n, final int n2) throws ErrnoException {
            if (s.equals("null")) {
                return this.devNullFD;
            }
            if (s.equals("zero")) {
                return this.devZeroFD;
            }
            if (s.startsWith("fd/")) {
                int int1;
                try {
                    int1 = Integer.parseInt(s.substring(4));
                }
                catch (NumberFormatException ex) {
                    return null;
                }
                if (int1 < 0 || int1 >= 64) {
                    return null;
                }
                if (unixRuntime.fds[int1] == null) {
                    return null;
                }
                return unixRuntime.fds[int1].dup();
            }
            else {
                if (s.equals("fd")) {
                    int n3 = 0;
                    for (int i = 0; i < 64; ++i) {
                        if (unixRuntime.fds[i] != null) {
                            ++n3;
                        }
                    }
                    final int[] array = new int[n3];
                    int n4 = 0;
                    for (int j = 0; j < 64; ++j) {
                        if (unixRuntime.fds[j] != null) {
                            array[n4++] = j;
                        }
                    }
                    return new DevDirFD() {
                        public int myInode() {
                            return 4;
                        }
                        
                        public int parentInode() {
                            return 1;
                        }
                        
                        public int inode(final int n) {
                            return 32 + n;
                        }
                        
                        public String name(final int n) {
                            return Integer.toString(array[n]);
                        }
                        
                        public int size() {
                            return array.length;
                        }
                    };
                }
                if (s.equals("")) {
                    return new DevDirFD() {
                        public int myInode() {
                            return 1;
                        }
                        
                        public int parentInode() {
                            return 1;
                        }
                        
                        public int inode(final int n) {
                            switch (n) {
                                case 0: {
                                    return 2;
                                }
                                case 1: {
                                    return 3;
                                }
                                case 2: {
                                    return 4;
                                }
                                default: {
                                    return -1;
                                }
                            }
                        }
                        
                        public String name(final int n) {
                            switch (n) {
                                case 0: {
                                    return "null";
                                }
                                case 1: {
                                    return "zero";
                                }
                                case 2: {
                                    return "fd";
                                }
                                default: {
                                    return null;
                                }
                            }
                        }
                        
                        public int size() {
                            return 3;
                        }
                    };
                }
                return null;
            }
        }
        
        public FStat stat(final UnixRuntime unixRuntime, final String s) throws ErrnoException {
            if (s.equals("null")) {
                return this.devNullFD.fstat();
            }
            if (s.equals("zero")) {
                return this.devZeroFD.fstat();
            }
            if (s.startsWith("fd/")) {
                int int1;
                try {
                    int1 = Integer.parseInt(s.substring(3));
                }
                catch (NumberFormatException ex) {
                    return null;
                }
                if (int1 < 0 || int1 >= 64) {
                    return null;
                }
                if (unixRuntime.fds[int1] == null) {
                    return null;
                }
                return unixRuntime.fds[int1].fstat();
            }
            else {
                if (s.equals("fd")) {
                    return new FStat() {
                        public int inode() {
                            return 4;
                        }
                        
                        public int dev() {
                            return DevFS.this.devno;
                        }
                        
                        public int type() {
                            return 16384;
                        }
                        
                        public int mode() {
                            return 292;
                        }
                    };
                }
                if (s.equals("")) {
                    return new FStat() {
                        public int inode() {
                            return 1;
                        }
                        
                        public int dev() {
                            return DevFS.this.devno;
                        }
                        
                        public int type() {
                            return 16384;
                        }
                        
                        public int mode() {
                            return 292;
                        }
                    };
                }
                return null;
            }
        }
        
        public void mkdir(final UnixRuntime unixRuntime, final String s, final int n) throws ErrnoException {
            throw new ErrnoException(30);
        }
        
        public void unlink(final UnixRuntime unixRuntime, final String s) throws ErrnoException {
            throw new ErrnoException(30);
        }
        
        private abstract class DevFStat extends FStat
        {
            public int dev() {
                return DevFS.this.devno;
            }
            
            public int mode() {
                return 438;
            }
            
            public int type() {
                return 8192;
            }
            
            public int nlink() {
                return 1;
            }
            
            public abstract int inode();
        }
        
        private abstract class DevDirFD extends DirFD
        {
            public int myDev() {
                return DevFS.this.devno;
            }
        }
    }
    
    public static class ResourceFS extends FS
    {
        final InodeCache inodes;
        
        public ResourceFS() {
            this.inodes = new InodeCache(500);
        }
        
        public FStat lstat(final UnixRuntime unixRuntime, final String s) throws ErrnoException {
            return this.stat(unixRuntime, s);
        }
        
        public void mkdir(final UnixRuntime unixRuntime, final String s, final int n) throws ErrnoException {
            throw new ErrnoException(30);
        }
        
        public void unlink(final UnixRuntime unixRuntime, final String s) throws ErrnoException {
            throw new ErrnoException(30);
        }
        
        FStat connFStat(final URLConnection urlConnection) {
            return new FStat() {
                public int type() {
                    return 32768;
                }
                
                public int nlink() {
                    return 1;
                }
                
                public int mode() {
                    return 292;
                }
                
                public int size() {
                    return urlConnection.getContentLength();
                }
                
                public int mtime() {
                    return (int)(urlConnection.getDate() / 1000L);
                }
                
                public int inode() {
                    return ResourceFS.this.inodes.get(urlConnection.getURL().toString());
                }
                
                public int dev() {
                    return ResourceFS.this.devno;
                }
            };
        }
        
        public FStat stat(final UnixRuntime unixRuntime, final String s) throws ErrnoException {
            final URL resource = unixRuntime.getClass().getResource("/" + s);
            if (resource == null) {
                return null;
            }
            try {
                return this.connFStat(resource.openConnection());
            }
            catch (IOException ex) {
                throw new ErrnoException(5);
            }
        }
        
        public FD open(final UnixRuntime unixRuntime, final String s, final int n, final int n2) throws ErrnoException {
            if ((n & 0xFFFFFFFC) != 0x0) {
                System.err.println("WARNING: Unsupported flags passed to ResourceFS.open(\"" + s + "\"): " + Runtime.toHex(n & 0xFFFFFFFC));
                throw new ErrnoException(134);
            }
            if ((n & 0x3) != 0x0) {
                throw new ErrnoException(30);
            }
            final URL resource = unixRuntime.getClass().getResource("/" + s);
            if (resource == null) {
                return null;
            }
            try {
                final URLConnection openConnection = resource.openConnection();
                return new SeekableFD((Seekable)new Seekable.InputStream(openConnection.getInputStream()), n) {
                    protected FStat _fstat() {
                        return ResourceFS.this.connFStat(openConnection);
                    }
                };
            }
            catch (FileNotFoundException ex) {
                if (ex.getMessage() != null && ex.getMessage().indexOf("Permission denied") >= 0) {
                    throw new ErrnoException(13);
                }
                return null;
            }
            catch (IOException ex2) {
                throw new ErrnoException(5);
            }
        }
    }
}
