// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.util.internal.chmv8;

import java.security.PrivilegedActionException;
import java.security.AccessController;
import java.lang.reflect.Field;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.Collections;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.concurrent.Future;
import java.util.List;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.RejectedExecutionException;
import java.util.Arrays;
import java.security.Permission;
import sun.misc.Unsafe;
import java.util.concurrent.AbstractExecutorService;

final class ForkJoinPool extends AbstractExecutorService
{
    public static final ForkJoinWorkerThreadFactory defaultForkJoinWorkerThreadFactory;
    static final ThreadLocal<Submitter> submitters;
    private static final RuntimePermission modifyThreadPermission;
    static final ForkJoinPool common;
    static final int commonParallelism;
    private static int poolNumberSequence;
    private static final long IDLE_TIMEOUT = 2000000000L;
    private static final long FAST_IDLE_TIMEOUT = 200000000L;
    private static final long TIMEOUT_SLOP = 2000000L;
    private static final int MAX_HELP = 64;
    private static final int SEED_INCREMENT = 1640531527;
    private static final int AC_SHIFT = 48;
    private static final int TC_SHIFT = 32;
    private static final int ST_SHIFT = 31;
    private static final int EC_SHIFT = 16;
    private static final int SMASK = 65535;
    private static final int MAX_CAP = 32767;
    private static final int EVENMASK = 65534;
    private static final int SQMASK = 126;
    private static final int SHORT_SIGN = 32768;
    private static final int INT_SIGN = Integer.MIN_VALUE;
    private static final long STOP_BIT = 2147483648L;
    private static final long AC_MASK = -281474976710656L;
    private static final long TC_MASK = 281470681743360L;
    private static final long TC_UNIT = 4294967296L;
    private static final long AC_UNIT = 281474976710656L;
    private static final int UAC_SHIFT = 16;
    private static final int UTC_SHIFT = 0;
    private static final int UAC_MASK = -65536;
    private static final int UTC_MASK = 65535;
    private static final int UAC_UNIT = 65536;
    private static final int UTC_UNIT = 1;
    private static final int E_MASK = Integer.MAX_VALUE;
    private static final int E_SEQ = 65536;
    private static final int SHUTDOWN = Integer.MIN_VALUE;
    private static final int PL_LOCK = 2;
    private static final int PL_SIGNAL = 1;
    private static final int PL_SPINS = 256;
    static final int LIFO_QUEUE = 0;
    static final int FIFO_QUEUE = 1;
    static final int SHARED_QUEUE = -1;
    private static final int MIN_SCAN = 511;
    private static final int MAX_SCAN = 131071;
    volatile long pad00;
    volatile long pad01;
    volatile long pad02;
    volatile long pad03;
    volatile long pad04;
    volatile long pad05;
    volatile long pad06;
    volatile long stealCount;
    volatile long ctl;
    volatile int plock;
    volatile int indexSeed;
    final int config;
    WorkQueue[] workQueues;
    final ForkJoinWorkerThreadFactory factory;
    final Thread.UncaughtExceptionHandler ueh;
    final String workerNamePrefix;
    volatile Object pad10;
    volatile Object pad11;
    volatile Object pad12;
    volatile Object pad13;
    volatile Object pad14;
    volatile Object pad15;
    volatile Object pad16;
    volatile Object pad17;
    volatile Object pad18;
    volatile Object pad19;
    volatile Object pad1a;
    volatile Object pad1b;
    private static final Unsafe U;
    private static final long CTL;
    private static final long PARKBLOCKER;
    private static final int ABASE;
    private static final int ASHIFT;
    private static final long STEALCOUNT;
    private static final long PLOCK;
    private static final long INDEXSEED;
    private static final long QLOCK;
    
    private static void checkPermission() {
        final SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkPermission(ForkJoinPool.modifyThreadPermission);
        }
    }
    
    private static final synchronized int nextPoolId() {
        return ++ForkJoinPool.poolNumberSequence;
    }
    
    private int acquirePlock() {
        int spins = 256;
        int r = 0;
        int ps;
        int nps;
        while (((ps = this.plock) & 0x2) != 0x0 || !ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.PLOCK, ps, nps = ps + 2)) {
            if (r == 0) {
                final Thread t = Thread.currentThread();
                final WorkQueue w;
                if (t instanceof ForkJoinWorkerThread && (w = ((ForkJoinWorkerThread)t).workQueue) != null) {
                    r = w.seed;
                }
                else {
                    final Submitter z;
                    if ((z = ForkJoinPool.submitters.get()) != null) {
                        r = z.seed;
                    }
                    else {
                        r = 1;
                    }
                }
            }
            else if (spins >= 0) {
                r ^= r << 1;
                r ^= r >>> 3;
                r ^= r << 10;
                if (r < 0) {
                    continue;
                }
                --spins;
            }
            else {
                if (!ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.PLOCK, ps, ps | 0x1)) {
                    continue;
                }
                synchronized (this) {
                    if ((this.plock & 0x1) != 0x0) {
                        try {
                            this.wait();
                        }
                        catch (InterruptedException ie) {
                            try {
                                Thread.currentThread().interrupt();
                            }
                            catch (SecurityException ex) {}
                        }
                    }
                    else {
                        this.notifyAll();
                    }
                }
            }
        }
        return nps;
    }
    
    private void releasePlock(final int ps) {
        this.plock = ps;
        synchronized (this) {
            this.notifyAll();
        }
    }
    
    private void tryAddWorker() {
        long c;
        int u;
        while ((u = (int)((c = this.ctl) >>> 32)) < 0 && (u & 0x8000) != 0x0 && (int)c == 0) {
            final long nc = ((u + 1 & 0xFFFF) | (u + 65536 & 0xFFFF0000)) << 32;
            if (ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, c, nc)) {
                Throwable ex = null;
                ForkJoinWorkerThread wt = null;
                try {
                    final ForkJoinWorkerThreadFactory fac;
                    if ((fac = this.factory) != null && (wt = fac.newThread(this)) != null) {
                        wt.start();
                        break;
                    }
                }
                catch (Throwable e) {
                    ex = e;
                }
                this.deregisterWorker(wt, ex);
                break;
            }
        }
    }
    
    final WorkQueue registerWorker(final ForkJoinWorkerThread wt) {
        wt.setDaemon(true);
        final Thread.UncaughtExceptionHandler handler;
        if ((handler = this.ueh) != null) {
            wt.setUncaughtExceptionHandler(handler);
        }
        Unsafe u;
        long indexseed;
        int s;
        do {
            u = ForkJoinPool.U;
            indexseed = ForkJoinPool.INDEXSEED;
            s = this.indexSeed;
            s += 1640531527;
        } while (!u.compareAndSwapInt(this, indexseed, s, s) || s == 0);
        final WorkQueue w = new WorkQueue(this, wt, this.config >>> 16, s);
        int ps = 0;
        Label_0105: {
            if (((ps = this.plock) & 0x2) == 0x0) {
                final Unsafe u2 = ForkJoinPool.U;
                final long plock = ForkJoinPool.PLOCK;
                final int n2 = ps;
                ps += 2;
                if (u2.compareAndSwapInt(this, plock, n2, ps)) {
                    break Label_0105;
                }
            }
            ps = this.acquirePlock();
        }
        final int nps = (ps & Integer.MIN_VALUE) | (ps + 2 & Integer.MAX_VALUE);
        try {
            WorkQueue[] ws;
            if ((ws = this.workQueues) != null) {
                int n = ws.length;
                int m = n - 1;
                int r = s << 1 | 0x1;
                if (ws[r &= m] != null) {
                    int probes = 0;
                    for (int step = (n <= 4) ? 2 : ((n >>> 1 & 0xFFFE) + 2); ws[r = (r + step & m)] != null; ws = (this.workQueues = Arrays.copyOf(ws, n <<= 1)), m = n - 1, probes = 0) {
                        if (++probes >= n) {}
                    }
                }
                final WorkQueue workQueue = w;
                final WorkQueue workQueue2 = w;
                final int n3 = r;
                workQueue2.poolIndex = n3;
                workQueue.eventCount = n3;
                ws[r] = w;
            }
        }
        finally {
            if (!ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.PLOCK, ps, nps)) {
                this.releasePlock(nps);
            }
        }
        wt.setName(this.workerNamePrefix.concat(Integer.toString(w.poolIndex)));
        return w;
    }
    
    final void deregisterWorker(final ForkJoinWorkerThread wt, final Throwable ex) {
        WorkQueue w = null;
        if (wt != null && (w = wt.workQueue) != null) {
            w.qlock = -1;
            final long ns = w.nsteals;
            long sc;
            while (!ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.STEALCOUNT, sc = this.stealCount, sc + ns)) {}
            int ps = 0;
            Label_0090: {
                if (((ps = this.plock) & 0x2) == 0x0) {
                    final Unsafe u2 = ForkJoinPool.U;
                    final long plock = ForkJoinPool.PLOCK;
                    final int n = ps;
                    ps += 2;
                    if (u2.compareAndSwapInt(this, plock, n, ps)) {
                        break Label_0090;
                    }
                }
                ps = this.acquirePlock();
            }
            final int nps = (ps & Integer.MIN_VALUE) | (ps + 2 & Integer.MAX_VALUE);
            try {
                final int idx = w.poolIndex;
                final WorkQueue[] ws = this.workQueues;
                if (ws != null && idx >= 0 && idx < ws.length && ws[idx] == w) {
                    ws[idx] = null;
                }
            }
            finally {
                if (!ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.PLOCK, ps, nps)) {
                    this.releasePlock(nps);
                }
            }
        }
        long c;
        while (!ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, c = this.ctl, (c - 281474976710656L & 0xFFFF000000000000L) | (c - 4294967296L & 0xFFFF00000000L) | (c & 0xFFFFFFFFL))) {}
        if (!this.tryTerminate(false, false) && w != null && w.array != null) {
            w.cancelAll();
            int u;
            int e;
            while ((u = (int)((c = this.ctl) >>> 32)) < 0 && (e = (int)c) >= 0) {
                if (e > 0) {
                    final WorkQueue[] ws2;
                    final int i;
                    if ((ws2 = this.workQueues) == null || (i = (e & 0xFFFF)) >= ws2.length) {
                        break;
                    }
                    final WorkQueue v;
                    if ((v = ws2[i]) == null) {
                        break;
                    }
                    final long nc = (v.nextWait & Integer.MAX_VALUE) | u + 65536 << 32;
                    if (v.eventCount != (e | Integer.MIN_VALUE)) {
                        break;
                    }
                    if (!ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, c, nc)) {
                        continue;
                    }
                    v.eventCount = (e + 65536 & Integer.MAX_VALUE);
                    final Thread p;
                    if ((p = v.parker) != null) {
                        ForkJoinPool.U.unpark(p);
                        break;
                    }
                    break;
                }
                else {
                    if ((short)u < 0) {
                        this.tryAddWorker();
                        break;
                    }
                    break;
                }
            }
        }
        if (ex == null) {
            ForkJoinTask.helpExpungeStaleExceptions();
        }
        else {
            ForkJoinTask.rethrow(ex);
        }
    }
    
    final void externalPush(final ForkJoinTask<?> task) {
        final Submitter z;
        final WorkQueue[] ws;
        final int m;
        final WorkQueue q;
        if ((z = ForkJoinPool.submitters.get()) != null && this.plock > 0 && (ws = this.workQueues) != null && (m = ws.length - 1) >= 0 && (q = ws[m & z.seed & 0x7E]) != null && ForkJoinPool.U.compareAndSwapInt(q, ForkJoinPool.QLOCK, 0, 1)) {
            final int b = q.base;
            final int s = q.top;
            final ForkJoinTask<?>[] a;
            final int an;
            final int n;
            if ((a = q.array) != null && (an = a.length) > (n = s + 1 - b)) {
                final int j = ((an - 1 & s) << ForkJoinPool.ASHIFT) + ForkJoinPool.ABASE;
                ForkJoinPool.U.putOrderedObject(a, j, task);
                q.top = s + 1;
                q.qlock = 0;
                if (n <= 2) {
                    this.signalWork(q);
                }
                return;
            }
            q.qlock = 0;
        }
        this.fullExternalPush(task);
    }
    
    private void fullExternalPush(final ForkJoinTask<?> task) {
        int r = 0;
        Submitter z = ForkJoinPool.submitters.get();
        while (true) {
            if (z == null) {
                final Unsafe u = ForkJoinPool.U;
                final long indexseed = ForkJoinPool.INDEXSEED;
                r = this.indexSeed;
                r += 1640531527;
                if (!u.compareAndSwapInt(this, indexseed, r, r) || r == 0) {
                    continue;
                }
                ForkJoinPool.submitters.set(z = new Submitter(r));
            }
            else if (r == 0) {
                r = z.seed;
                r ^= r << 13;
                r ^= r >>> 17;
                z.seed = (r ^ r << 5);
            }
            else {
                int ps;
                if ((ps = this.plock) < 0) {
                    throw new RejectedExecutionException();
                }
                WorkQueue[] ws;
                final int m;
                if (ps == 0 || (ws = this.workQueues) == null || (m = ws.length - 1) < 0) {
                    final int p = this.config & 0xFFFF;
                    int n = (p > 1) ? (p - 1) : 1;
                    n |= n >>> 1;
                    n |= n >>> 2;
                    n |= n >>> 4;
                    n |= n >>> 8;
                    n |= n >>> 16;
                    n = n + 1 << 1;
                    final WorkQueue[] nws = (WorkQueue[])(((ws = this.workQueues) == null || ws.length == 0) ? new WorkQueue[n] : null);
                    Label_0288: {
                        if (((ps = this.plock) & 0x2) == 0x0) {
                            final Unsafe u2 = ForkJoinPool.U;
                            final long plock = ForkJoinPool.PLOCK;
                            final int n2 = ps;
                            ps += 2;
                            if (u2.compareAndSwapInt(this, plock, n2, ps)) {
                                break Label_0288;
                            }
                        }
                        ps = this.acquirePlock();
                    }
                    if (((ws = this.workQueues) == null || ws.length == 0) && nws != null) {
                        this.workQueues = nws;
                    }
                    final int nps = (ps & Integer.MIN_VALUE) | (ps + 2 & Integer.MAX_VALUE);
                    if (ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.PLOCK, ps, nps)) {
                        continue;
                    }
                    this.releasePlock(nps);
                }
                else {
                    final int k;
                    WorkQueue q;
                    if ((q = ws[k = (r & m & 0x7E)]) != null) {
                        if (q.qlock == 0 && ForkJoinPool.U.compareAndSwapInt(q, ForkJoinPool.QLOCK, 0, 1)) {
                            ForkJoinTask<?>[] a = q.array;
                            final int s = q.top;
                            boolean submitted = false;
                            try {
                                if ((a != null && a.length > s + 1 - q.base) || (a = q.growArray()) != null) {
                                    final int j = ((a.length - 1 & s) << ForkJoinPool.ASHIFT) + ForkJoinPool.ABASE;
                                    ForkJoinPool.U.putOrderedObject(a, j, task);
                                    q.top = s + 1;
                                    submitted = true;
                                }
                            }
                            finally {
                                q.qlock = 0;
                            }
                            if (submitted) {
                                this.signalWork(q);
                                return;
                            }
                        }
                        r = 0;
                    }
                    else if (((ps = this.plock) & 0x2) == 0x0) {
                        q = new WorkQueue(this, null, -1, r);
                        Label_0590: {
                            if (((ps = this.plock) & 0x2) == 0x0) {
                                final Unsafe u3 = ForkJoinPool.U;
                                final long plock2 = ForkJoinPool.PLOCK;
                                final int n3 = ps;
                                ps += 2;
                                if (u3.compareAndSwapInt(this, plock2, n3, ps)) {
                                    break Label_0590;
                                }
                            }
                            ps = this.acquirePlock();
                        }
                        if ((ws = this.workQueues) != null && k < ws.length && ws[k] == null) {
                            ws[k] = q;
                        }
                        final int nps2 = (ps & Integer.MIN_VALUE) | (ps + 2 & Integer.MAX_VALUE);
                        if (ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.PLOCK, ps, nps2)) {
                            continue;
                        }
                        this.releasePlock(nps2);
                    }
                    else {
                        r = 0;
                    }
                }
            }
        }
    }
    
    final void incrementActiveCount() {
        long c;
        while (!ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, c = this.ctl, c + 281474976710656L)) {}
    }
    
    final void signalWork(final WorkQueue q) {
        final int hint = q.poolIndex;
        long c;
        int u;
        while ((u = (int)((c = this.ctl) >>> 32)) < 0) {
            final int e;
            if ((e = (int)c) > 0) {
                final WorkQueue[] ws;
                final int i;
                final WorkQueue w;
                if ((ws = this.workQueues) == null || ws.length <= (i = (e & 0xFFFF)) || (w = ws[i]) == null || w.eventCount != (e | Integer.MIN_VALUE)) {
                    break;
                }
                final long nc = (w.nextWait & Integer.MAX_VALUE) | u + 65536 << 32;
                if (ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, c, nc)) {
                    w.hint = hint;
                    w.eventCount = (e + 65536 & Integer.MAX_VALUE);
                    final Thread p;
                    if ((p = w.parker) != null) {
                        ForkJoinPool.U.unpark(p);
                        break;
                    }
                    break;
                }
                else {
                    if (q.top - q.base <= 0) {
                        break;
                    }
                    continue;
                }
            }
            else {
                if ((short)u < 0) {
                    this.tryAddWorker();
                    break;
                }
                break;
            }
        }
    }
    
    final void runWorker(final WorkQueue w) {
        w.growArray();
        do {
            w.runTask(this.scan(w));
        } while (w.qlock >= 0);
    }
    
    private final ForkJoinTask<?> scan(final WorkQueue w) {
        final int ps = this.plock;
        WorkQueue[] ws;
        final int m;
        if (w != null && (ws = this.workQueues) != null && (m = ws.length - 1) >= 0) {
            final int ec = w.eventCount;
            int r = w.seed;
            r ^= r << 13;
            r ^= r >>> 17;
            r = (w.seed = (r ^ r << 5));
            w.hint = -1;
            int j = (m + m + 1 | 0x1FF) & 0x1FFFF;
            do {
                final WorkQueue q;
                final int b;
                final ForkJoinTask<?>[] a;
                if ((q = ws[r + j & m]) != null && (b = q.base) - q.top < 0 && (a = q.array) != null) {
                    final int i = ((a.length - 1 & b) << ForkJoinPool.ASHIFT) + ForkJoinPool.ABASE;
                    final ForkJoinTask<?> t = (ForkJoinTask<?>)ForkJoinPool.U.getObjectVolatile(a, i);
                    if (q.base == b && ec >= 0 && t != null && ForkJoinPool.U.compareAndSwapObject(a, i, t, null)) {
                        final WorkQueue workQueue = q;
                        final int base = b + 1;
                        workQueue.base = base;
                        if (base - q.top < 0) {
                            this.signalWork(q);
                        }
                        return t;
                    }
                    if ((ec < 0 || j < m) && (int)(this.ctl >> 48) <= 0) {
                        w.hint = (r + j & m);
                        break;
                    }
                    continue;
                }
            } while (--j >= 0);
            final int ns;
            if ((ns = w.nsteals) != 0) {
                final long sc;
                if (ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.STEALCOUNT, sc = this.stealCount, sc + ns)) {
                    w.nsteals = 0;
                }
            }
            else if (this.plock == ps) {
                long c;
                int e;
                if ((e = (int)(c = this.ctl)) < 0) {
                    w.qlock = -1;
                }
                else {
                    int h;
                    if ((h = w.hint) < 0) {
                        if (ec >= 0) {
                            final long nc = ec | (c - 281474976710656L & 0xFFFFFFFF00000000L);
                            w.nextWait = e;
                            w.eventCount = (ec | Integer.MIN_VALUE);
                            if (this.ctl != c || !ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, c, nc)) {
                                w.eventCount = ec;
                            }
                            else if ((int)(c >> 48) == 1 - (this.config & 0xFFFF)) {
                                this.idleAwaitWork(w, nc, c);
                            }
                        }
                        else if (w.eventCount < 0 && this.ctl == c) {
                            final Thread wt = Thread.currentThread();
                            Thread.interrupted();
                            ForkJoinPool.U.putObject(wt, ForkJoinPool.PARKBLOCKER, this);
                            w.parker = wt;
                            if (w.eventCount < 0) {
                                ForkJoinPool.U.park(false, 0L);
                            }
                            w.parker = null;
                            ForkJoinPool.U.putObject(wt, ForkJoinPool.PARKBLOCKER, null);
                        }
                    }
                    final WorkQueue q2;
                    if ((h >= 0 || (h = w.hint) >= 0) && (ws = this.workQueues) != null && h < ws.length && (q2 = ws[h]) != null) {
                        int n = (this.config & 0xFFFF) - 1;
                        do {
                            final int idleCount = (w.eventCount < 0) ? 0 : -1;
                            final int s;
                            final int u;
                            final int k;
                            if (((s = idleCount - q2.base + q2.top) <= n && (n = s) <= 0) || (u = (int)((c = this.ctl) >>> 32)) >= 0 || (e = (int)c) <= 0 || m < (k = (e & 0xFFFF))) {
                                break;
                            }
                            final WorkQueue v;
                            if ((v = ws[k]) == null) {
                                break;
                            }
                            final long nc2 = (v.nextWait & Integer.MAX_VALUE) | u + 65536 << 32;
                            if (v.eventCount != (e | Integer.MIN_VALUE)) {
                                break;
                            }
                            if (!ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, c, nc2)) {
                                break;
                            }
                            v.hint = h;
                            v.eventCount = (e + 65536 & Integer.MAX_VALUE);
                            final Thread p;
                            if ((p = v.parker) != null) {
                                ForkJoinPool.U.unpark(p);
                            }
                        } while (--n > 0);
                    }
                }
            }
        }
        return null;
    }
    
    private void idleAwaitWork(final WorkQueue w, final long currentCtl, final long prevCtl) {
        if (w != null && w.eventCount < 0 && !this.tryTerminate(false, false) && (int)prevCtl != 0 && this.ctl == currentCtl) {
            final int dc = -(short)(currentCtl >>> 32);
            final long parkTime = (dc < 0) ? 200000000L : ((dc + 1) * 2000000000L);
            final long deadline = System.nanoTime() + parkTime - 2000000L;
            final Thread wt = Thread.currentThread();
            while (this.ctl == currentCtl) {
                Thread.interrupted();
                ForkJoinPool.U.putObject(wt, ForkJoinPool.PARKBLOCKER, this);
                w.parker = wt;
                if (this.ctl == currentCtl) {
                    ForkJoinPool.U.park(false, parkTime);
                }
                w.parker = null;
                ForkJoinPool.U.putObject(wt, ForkJoinPool.PARKBLOCKER, null);
                if (this.ctl != currentCtl) {
                    break;
                }
                if (deadline - System.nanoTime() <= 0L && ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, currentCtl, prevCtl)) {
                    w.eventCount = (w.eventCount + 65536 | Integer.MAX_VALUE);
                    w.hint = -1;
                    w.qlock = -1;
                    break;
                }
            }
        }
    }
    
    private void helpSignal(final ForkJoinTask<?> task, final int origin) {
        int u;
        final WorkQueue[] ws;
        final int m;
        if (task != null && task.status >= 0 && (u = (int)(this.ctl >>> 32)) < 0 && u >> 16 < 0 && (ws = this.workQueues) != null && (m = ws.length - 1) >= 0) {
            int k = origin;
            int j = m;
        Label_0059:
            while (j >= 0) {
                final WorkQueue q = ws[k++ & m];
                int n = m;
                while (task.status >= 0) {
                    Label_0278: {
                        if (q != null) {
                            final int s;
                            if ((s = -q.base + q.top) > n || (n = s) > 0) {
                                final long c;
                                final int e;
                                final int i;
                                if ((u = (int)((c = this.ctl) >>> 32)) >= 0 || (e = (int)c) <= 0 || m < (i = (e & 0xFFFF))) {
                                    break;
                                }
                                final WorkQueue w;
                                if ((w = ws[i]) == null) {
                                    break;
                                }
                                final long nc = (w.nextWait & Integer.MAX_VALUE) | u + 65536 << 32;
                                if (w.eventCount != (e | Integer.MIN_VALUE)) {
                                    break;
                                }
                                if (!ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, c, nc)) {
                                    continue;
                                }
                                w.eventCount = (e + 65536 & Integer.MAX_VALUE);
                                final Thread p;
                                if ((p = w.parker) != null) {
                                    ForkJoinPool.U.unpark(p);
                                }
                                if (--n <= 0) {
                                    break Label_0278;
                                }
                                continue;
                                continue;
                            }
                        }
                    }
                    --j;
                    continue Label_0059;
                }
                break;
            }
        }
    }
    
    private int tryHelpStealer(final WorkQueue joiner, final ForkJoinTask<?> task) {
        int stat = 0;
        int steps = 0;
        Label_0429: {
            if (joiner != null && task != null) {
            Label_0013:
                while (true) {
                    ForkJoinTask<?> subtask = task;
                    WorkQueue j = joiner;
                    int s = 0;
                Label_0019:
                    while ((s = task.status) >= 0) {
                        final WorkQueue[] ws;
                        if ((ws = this.workQueues) == null) {
                            break Label_0429;
                        }
                        final int m;
                        if ((m = ws.length - 1) <= 0) {
                            break Label_0429;
                        }
                        WorkQueue v = null;
                        Label_0174: {
                            int h;
                            if ((v = ws[h = ((j.hint | 0x1) & m)]) == null || v.currentSteal != subtask) {
                                final int origin = h;
                                do {
                                    if (((h = (h + 2 & m)) & 0xF) == 0x1) {
                                        if (subtask.status < 0) {
                                            continue Label_0013;
                                        }
                                        if (j.currentJoin != subtask) {
                                            continue Label_0013;
                                        }
                                    }
                                    if ((v = ws[h]) != null && v.currentSteal == subtask) {
                                        j.hint = h;
                                        break Label_0174;
                                    }
                                } while (h != origin);
                                break Label_0429;
                            }
                        }
                        while (subtask.status >= 0) {
                            final int b;
                            final ForkJoinTask[] a;
                            if ((b = v.base) - v.top < 0 && (a = v.array) != null) {
                                final int i = ((a.length - 1 & b) << ForkJoinPool.ASHIFT) + ForkJoinPool.ABASE;
                                final ForkJoinTask<?> t = (ForkJoinTask<?>)ForkJoinPool.U.getObjectVolatile(a, i);
                                if (subtask.status < 0 || j.currentJoin != subtask) {
                                    break;
                                }
                                if (v.currentSteal != subtask) {
                                    break;
                                }
                                stat = 1;
                                if (t != null && v.base == b && ForkJoinPool.U.compareAndSwapObject(a, i, t, null)) {
                                    v.base = b + 1;
                                    joiner.runSubtask(t);
                                }
                                else {
                                    if (v.base == b && ++steps == 64) {
                                        break Label_0429;
                                    }
                                    continue;
                                }
                            }
                            else {
                                final ForkJoinTask<?> next = v.currentJoin;
                                if (subtask.status < 0 || j.currentJoin != subtask) {
                                    break;
                                }
                                if (v.currentSteal != subtask) {
                                    break;
                                }
                                if (next == null) {
                                    break Label_0429;
                                }
                                if (++steps == 64) {
                                    break Label_0429;
                                }
                                subtask = next;
                                j = v;
                                continue Label_0019;
                            }
                        }
                        continue Label_0013;
                    }
                    stat = s;
                    break;
                }
            }
        }
        return stat;
    }
    
    private int helpComplete(final ForkJoinTask<?> task, final int mode) {
        final WorkQueue[] ws;
        final int m;
        if (task != null && (ws = this.workQueues) != null && (m = ws.length - 1) >= 0) {
            int origin;
            int j = origin = 1;
            int s;
            while ((s = task.status) >= 0) {
                final WorkQueue q;
                if ((q = ws[j & m]) != null && q.pollAndExecCC(task)) {
                    origin = j;
                    if (mode != -1) {
                        continue;
                    }
                    final int u;
                    if ((u = (int)(this.ctl >>> 32)) >= 0) {
                        return 0;
                    }
                    if (u >> 16 >= 0) {
                        return 0;
                    }
                    continue;
                }
                else {
                    if ((j = (j + 2 & m)) == origin) {
                        return 0;
                    }
                    continue;
                }
            }
            return s;
        }
        return 0;
    }
    
    final boolean tryCompensate() {
        final int pc = this.config & 0xFFFF;
        final WorkQueue[] ws;
        final long c;
        final int e;
        if ((ws = this.workQueues) != null && (e = (int)(c = this.ctl)) >= 0) {
            final int i;
            final WorkQueue w;
            if (e != 0 && (i = (e & 0xFFFF)) < ws.length && (w = ws[i]) != null && w.eventCount == (e | Integer.MIN_VALUE)) {
                final long nc = (w.nextWait & Integer.MAX_VALUE) | (c & 0xFFFFFFFF00000000L);
                if (ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, c, nc)) {
                    w.eventCount = (e + 65536 & Integer.MAX_VALUE);
                    final Thread p;
                    if ((p = w.parker) != null) {
                        ForkJoinPool.U.unpark(p);
                    }
                    return true;
                }
            }
            else {
                final int tc;
                if ((tc = (short)(c >>> 32)) >= 0 && (int)(c >> 48) + pc > 1) {
                    final long nc = (c - 281474976710656L & 0xFFFF000000000000L) | (c & 0xFFFFFFFFFFFFL);
                    if (ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, c, nc)) {
                        return true;
                    }
                }
                else if (tc + pc < 32767) {
                    final long nc = (c + 4294967296L & 0xFFFF00000000L) | (c & 0xFFFF0000FFFFFFFFL);
                    if (ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, c, nc)) {
                        Throwable ex = null;
                        ForkJoinWorkerThread wt = null;
                        try {
                            final ForkJoinWorkerThreadFactory fac;
                            if ((fac = this.factory) != null && (wt = fac.newThread(this)) != null) {
                                wt.start();
                                return true;
                            }
                        }
                        catch (Throwable rex) {
                            ex = rex;
                        }
                        this.deregisterWorker(wt, ex);
                    }
                }
            }
        }
        return false;
    }
    
    final int awaitJoin(final WorkQueue joiner, final ForkJoinTask<?> task) {
        int s = 0;
        if (joiner != null && task != null && (s = task.status) >= 0) {
            final ForkJoinTask<?> prevJoin = joiner.currentJoin;
            joiner.currentJoin = task;
            while ((s = task.status) >= 0 && !joiner.isEmpty() && joiner.tryRemoveAndExec(task)) {}
            if (s >= 0 && (s = task.status) >= 0) {
                this.helpSignal(task, joiner.poolIndex);
                if ((s = task.status) >= 0 && task instanceof CountedCompleter) {
                    s = this.helpComplete(task, 0);
                }
            }
            while (s >= 0 && (s = task.status) >= 0) {
                if ((!joiner.isEmpty() || (s = this.tryHelpStealer(joiner, task)) == 0) && (s = task.status) >= 0) {
                    this.helpSignal(task, joiner.poolIndex);
                    if ((s = task.status) < 0 || !this.tryCompensate()) {
                        continue;
                    }
                    if (task.trySetSignal() && (s = task.status) >= 0) {
                        synchronized (task) {
                            if (task.status >= 0) {
                                try {
                                    task.wait();
                                }
                                catch (InterruptedException ie) {}
                            }
                            else {
                                task.notifyAll();
                            }
                        }
                    }
                    long c;
                    while (!ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, c = this.ctl, c + 281474976710656L)) {}
                }
            }
            joiner.currentJoin = prevJoin;
        }
        return s;
    }
    
    final void helpJoinOnce(final WorkQueue joiner, final ForkJoinTask<?> task) {
        int s;
        if (joiner != null && task != null && (s = task.status) >= 0) {
            final ForkJoinTask<?> prevJoin = joiner.currentJoin;
            joiner.currentJoin = task;
            while ((s = task.status) >= 0 && !joiner.isEmpty() && joiner.tryRemoveAndExec(task)) {}
            if (s >= 0 && (s = task.status) >= 0) {
                this.helpSignal(task, joiner.poolIndex);
                if ((s = task.status) >= 0 && task instanceof CountedCompleter) {
                    s = this.helpComplete(task, 0);
                }
            }
            if (s >= 0 && joiner.isEmpty()) {
                while (task.status >= 0 && this.tryHelpStealer(joiner, task) > 0) {}
            }
            joiner.currentJoin = prevJoin;
        }
    }
    
    private WorkQueue findNonEmptyStealQueue(final int r) {
        while (true) {
            final int ps = this.plock;
            final WorkQueue[] ws;
            final int m;
            if ((ws = this.workQueues) != null && (m = ws.length - 1) >= 0) {
                for (int j = m + 1 << 2; j >= 0; --j) {
                    final WorkQueue q;
                    if ((q = ws[(r + j << 1 | 0x1) & m]) != null && q.base - q.top < 0) {
                        return q;
                    }
                }
            }
            if (this.plock == ps) {
                return null;
            }
        }
    }
    
    final void helpQuiescePool(final WorkQueue w) {
        boolean active = true;
        while (true) {
            ForkJoinTask<?> t;
            if ((t = w.nextLocalTask()) != null) {
                if (w.base - w.top < 0) {
                    this.signalWork(w);
                }
                t.doExec();
            }
            else {
                final WorkQueue q;
                if ((q = this.findNonEmptyStealQueue(w.nextSeed())) != null) {
                    if (!active) {
                        active = true;
                        long c;
                        while (!ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, c = this.ctl, c + 281474976710656L)) {}
                    }
                    final int b;
                    if ((b = q.base) - q.top >= 0 || (t = q.pollAt(b)) == null) {
                        continue;
                    }
                    if (q.base - q.top < 0) {
                        this.signalWork(q);
                    }
                    w.runSubtask(t);
                }
                else if (active) {
                    final long c;
                    final long nc = (c = this.ctl) - 281474976710656L;
                    if ((int)(nc >> 48) + (this.config & 0xFFFF) == 0) {
                        return;
                    }
                    if (!ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, c, nc)) {
                        continue;
                    }
                    active = false;
                }
                else {
                    final long c;
                    if ((int)((c = this.ctl) >> 48) + (this.config & 0xFFFF) == 0 && ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, c, c + 281474976710656L)) {
                        return;
                    }
                    continue;
                }
            }
        }
    }
    
    final ForkJoinTask<?> nextTaskFor(final WorkQueue w) {
        ForkJoinTask<?> t;
        while ((t = w.nextLocalTask()) == null) {
            final WorkQueue q;
            if ((q = this.findNonEmptyStealQueue(w.nextSeed())) == null) {
                return null;
            }
            final int b;
            if ((b = q.base) - q.top < 0 && (t = q.pollAt(b)) != null) {
                if (q.base - q.top < 0) {
                    this.signalWork(q);
                }
                return t;
            }
        }
        return t;
    }
    
    static int getSurplusQueuedTaskCount() {
        final Thread t;
        if ((t = Thread.currentThread()) instanceof ForkJoinWorkerThread) {
            final ForkJoinWorkerThread wt;
            final ForkJoinPool pool;
            int p = (pool = (wt = (ForkJoinWorkerThread)t).pool).config & 0xFFFF;
            final WorkQueue q;
            final int n = (q = wt.workQueue).top - q.base;
            final int a = (int)(pool.ctl >> 48) + p;
            return n - ((a > (p >>>= 1)) ? 0 : ((a > (p >>>= 1)) ? 1 : ((a > (p >>>= 1)) ? 2 : ((a > (p >>>= 1)) ? 4 : 8))));
        }
        return 0;
    }
    
    private boolean tryTerminate(final boolean now, final boolean enable) {
        if (this == ForkJoinPool.common) {
            return false;
        }
        int ps;
        if ((ps = this.plock) >= 0) {
            if (!enable) {
                return false;
            }
            Label_0053: {
                if ((ps & 0x2) == 0x0) {
                    final Unsafe u = ForkJoinPool.U;
                    final long plock = ForkJoinPool.PLOCK;
                    final int n2 = ps;
                    ps += 2;
                    if (u.compareAndSwapInt(this, plock, n2, ps)) {
                        break Label_0053;
                    }
                }
                ps = this.acquirePlock();
            }
            final int nps = (ps + 2 & Integer.MAX_VALUE) | Integer.MIN_VALUE;
            if (!ForkJoinPool.U.compareAndSwapInt(this, ForkJoinPool.PLOCK, ps, nps)) {
                this.releasePlock(nps);
            }
        }
        long c;
        while (((c = this.ctl) & 0x80000000L) == 0x0L) {
            if (!now) {
                if ((int)(c >> 48) != -(this.config & 0xFFFF)) {
                    return false;
                }
                final WorkQueue[] ws;
                if ((ws = this.workQueues) != null) {
                    for (int i = 0; i < ws.length; ++i) {
                        final WorkQueue w;
                        if ((w = ws[i]) != null) {
                            if (!w.isEmpty()) {
                                this.signalWork(w);
                                return false;
                            }
                            if ((i & 0x1) != 0x0 && w.eventCount >= 0) {
                                return false;
                            }
                        }
                    }
                }
            }
            if (ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, c, c | 0x80000000L)) {
                for (int pass = 0; pass < 3; ++pass) {
                    final WorkQueue[] ws2;
                    if ((ws2 = this.workQueues) != null) {
                        final int n = ws2.length;
                        for (int j = 0; j < n; ++j) {
                            final WorkQueue w2;
                            if ((w2 = ws2[j]) != null) {
                                w2.qlock = -1;
                                if (pass > 0) {
                                    w2.cancelAll();
                                    final Thread wt;
                                    if (pass > 1 && (wt = w2.owner) != null) {
                                        if (!wt.isInterrupted()) {
                                            try {
                                                wt.interrupt();
                                            }
                                            catch (Throwable t) {}
                                        }
                                        ForkJoinPool.U.unpark(wt);
                                    }
                                }
                            }
                        }
                        int j;
                        WorkQueue w2;
                        long cc;
                        int e;
                        while ((e = ((int)(cc = this.ctl) & Integer.MAX_VALUE)) != 0 && (j = (e & 0xFFFF)) < n && j >= 0 && (w2 = ws2[j]) != null) {
                            final long nc = (w2.nextWait & Integer.MAX_VALUE) | (cc + 281474976710656L & 0xFFFF000000000000L) | (cc & 0xFFFF80000000L);
                            if (w2.eventCount == (e | Integer.MIN_VALUE) && ForkJoinPool.U.compareAndSwapLong(this, ForkJoinPool.CTL, cc, nc)) {
                                w2.eventCount = (e + 65536 & Integer.MAX_VALUE);
                                w2.qlock = -1;
                                final Thread p;
                                if ((p = w2.parker) == null) {
                                    continue;
                                }
                                ForkJoinPool.U.unpark(p);
                            }
                        }
                    }
                }
            }
        }
        if ((short)(c >>> 32) == -(this.config & 0xFFFF)) {
            synchronized (this) {
                this.notifyAll();
            }
        }
        return true;
    }
    
    static WorkQueue commonSubmitterQueue() {
        final Submitter z;
        final ForkJoinPool p;
        final WorkQueue[] ws;
        final int m;
        return ((z = ForkJoinPool.submitters.get()) != null && (p = ForkJoinPool.common) != null && (ws = p.workQueues) != null && (m = ws.length - 1) >= 0) ? ws[m & z.seed & 0x7E] : null;
    }
    
    static boolean tryExternalUnpush(final ForkJoinTask<?> t) {
        final Submitter z;
        final ForkJoinPool p;
        final WorkQueue[] ws;
        final int m;
        final WorkQueue q;
        final int s;
        final ForkJoinTask<?>[] a;
        if (t != null && (z = ForkJoinPool.submitters.get()) != null && (p = ForkJoinPool.common) != null && (ws = p.workQueues) != null && (m = ws.length - 1) >= 0 && (q = ws[m & z.seed & 0x7E]) != null && (s = q.top) != q.base && (a = q.array) != null) {
            final long j = ((a.length - 1 & s - 1) << ForkJoinPool.ASHIFT) + ForkJoinPool.ABASE;
            if (ForkJoinPool.U.getObject(a, j) == t && ForkJoinPool.U.compareAndSwapInt(q, ForkJoinPool.QLOCK, 0, 1)) {
                if (q.array == a && q.top == s && ForkJoinPool.U.compareAndSwapObject(a, j, t, null)) {
                    q.top = s - 1;
                    q.qlock = 0;
                    return true;
                }
                q.qlock = 0;
            }
        }
        return false;
    }
    
    private void externalHelpComplete(final WorkQueue q, final ForkJoinTask<?> root) {
        final ForkJoinTask<?>[] a;
        final int m;
        if (q != null && (a = q.array) != null && (m = a.length - 1) >= 0 && root != null && root.status >= 0) {
            while (true) {
                CountedCompleter<?> task = null;
                Label_0188: {
                    final int s;
                    if ((s = q.top) - q.base > 0) {
                        final long j = ((m & s - 1) << ForkJoinPool.ASHIFT) + ForkJoinPool.ABASE;
                        final Object o;
                        if ((o = ForkJoinPool.U.getObject(a, j)) != null && o instanceof CountedCompleter) {
                            CountedCompleter<?> r;
                            final CountedCompleter<?> t = r = (CountedCompleter<?>)o;
                            while (r != root) {
                                if ((r = r.completer) == null) {
                                    break Label_0188;
                                }
                            }
                            if (ForkJoinPool.U.compareAndSwapInt(q, ForkJoinPool.QLOCK, 0, 1)) {
                                if (q.array == a && q.top == s && ForkJoinPool.U.compareAndSwapObject(a, j, t, null)) {
                                    q.top = s - 1;
                                    task = t;
                                }
                                q.qlock = 0;
                            }
                        }
                    }
                }
                if (task != null) {
                    task.doExec();
                }
                final int u;
                if (root.status < 0 || (u = (int)(this.ctl >>> 32)) >= 0) {
                    break;
                }
                if (u >> 16 >= 0) {
                    break;
                }
                if (task != null) {
                    continue;
                }
                this.helpSignal(root, q.poolIndex);
                if (root.status >= 0) {
                    this.helpComplete(root, -1);
                    break;
                }
                break;
            }
        }
    }
    
    static void externalHelpJoin(final ForkJoinTask<?> t) {
        final Submitter z;
        final ForkJoinPool p;
        final WorkQueue[] ws;
        final int m;
        final WorkQueue q;
        final ForkJoinTask<?>[] a;
        if (t != null && (z = ForkJoinPool.submitters.get()) != null && (p = ForkJoinPool.common) != null && (ws = p.workQueues) != null && (m = ws.length - 1) >= 0 && (q = ws[m & z.seed & 0x7E]) != null && (a = q.array) != null) {
            final int am = a.length - 1;
            final int s;
            if ((s = q.top) != q.base) {
                final long j = ((am & s - 1) << ForkJoinPool.ASHIFT) + ForkJoinPool.ABASE;
                if (ForkJoinPool.U.getObject(a, j) == t && ForkJoinPool.U.compareAndSwapInt(q, ForkJoinPool.QLOCK, 0, 1)) {
                    if (q.array == a && q.top == s && ForkJoinPool.U.compareAndSwapObject(a, j, t, null)) {
                        q.top = s - 1;
                        q.qlock = 0;
                        t.doExec();
                    }
                    else {
                        q.qlock = 0;
                    }
                }
            }
            if (t.status >= 0) {
                if (t instanceof CountedCompleter) {
                    p.externalHelpComplete(q, t);
                }
                else {
                    p.helpSignal(t, q.poolIndex);
                }
            }
        }
    }
    
    public ForkJoinPool() {
        this(Math.min(32767, Runtime.getRuntime().availableProcessors()), ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, false);
    }
    
    public ForkJoinPool(final int parallelism) {
        this(parallelism, ForkJoinPool.defaultForkJoinWorkerThreadFactory, null, false);
    }
    
    public ForkJoinPool(final int parallelism, final ForkJoinWorkerThreadFactory factory, final Thread.UncaughtExceptionHandler handler, final boolean asyncMode) {
        checkPermission();
        if (factory == null) {
            throw new NullPointerException();
        }
        if (parallelism <= 0 || parallelism > 32767) {
            throw new IllegalArgumentException();
        }
        this.factory = factory;
        this.ueh = handler;
        this.config = (parallelism | (asyncMode ? 65536 : 0));
        final long np = -parallelism;
        this.ctl = ((np << 48 & 0xFFFF000000000000L) | (np << 32 & 0xFFFF00000000L));
        final int pn = nextPoolId();
        final StringBuilder sb = new StringBuilder("ForkJoinPool-");
        sb.append(Integer.toString(pn));
        sb.append("-worker-");
        this.workerNamePrefix = sb.toString();
    }
    
    ForkJoinPool(final int parallelism, final long ctl, final ForkJoinWorkerThreadFactory factory, final Thread.UncaughtExceptionHandler handler) {
        this.config = parallelism;
        this.ctl = ctl;
        this.factory = factory;
        this.ueh = handler;
        this.workerNamePrefix = "ForkJoinPool.commonPool-worker-";
    }
    
    public static ForkJoinPool commonPool() {
        return ForkJoinPool.common;
    }
    
    public <T> T invoke(final ForkJoinTask<T> task) {
        if (task == null) {
            throw new NullPointerException();
        }
        this.externalPush(task);
        return task.join();
    }
    
    public void execute(final ForkJoinTask<?> task) {
        if (task == null) {
            throw new NullPointerException();
        }
        this.externalPush(task);
    }
    
    @Override
    public void execute(final Runnable task) {
        if (task == null) {
            throw new NullPointerException();
        }
        ForkJoinTask<?> job;
        if (task instanceof ForkJoinTask) {
            job = (ForkJoinTask<?>)task;
        }
        else {
            job = new ForkJoinTask.AdaptedRunnableAction(task);
        }
        this.externalPush(job);
    }
    
    public <T> ForkJoinTask<T> submit(final ForkJoinTask<T> task) {
        if (task == null) {
            throw new NullPointerException();
        }
        this.externalPush(task);
        return task;
    }
    
    @Override
    public <T> ForkJoinTask<T> submit(final Callable<T> task) {
        final ForkJoinTask<T> job = new ForkJoinTask.AdaptedCallable<T>((Callable<? extends T>)task);
        this.externalPush(job);
        return job;
    }
    
    @Override
    public <T> ForkJoinTask<T> submit(final Runnable task, final T result) {
        final ForkJoinTask<T> job = new ForkJoinTask.AdaptedRunnable<T>(task, result);
        this.externalPush(job);
        return job;
    }
    
    @Override
    public ForkJoinTask<?> submit(final Runnable task) {
        if (task == null) {
            throw new NullPointerException();
        }
        ForkJoinTask<?> job;
        if (task instanceof ForkJoinTask) {
            job = (ForkJoinTask<?>)task;
        }
        else {
            job = new ForkJoinTask.AdaptedRunnableAction(task);
        }
        this.externalPush(job);
        return job;
    }
    
    @Override
    public <T> List<Future<T>> invokeAll(final Collection<? extends Callable<T>> tasks) {
        final ArrayList<Future<T>> futures = new ArrayList<Future<T>>(tasks.size());
        boolean done = false;
        try {
            for (final Callable<T> t : tasks) {
                final ForkJoinTask<T> f = new ForkJoinTask.AdaptedCallable<T>((Callable<? extends T>)t);
                futures.add(f);
                this.externalPush(f);
            }
            for (int i = 0, size = futures.size(); i < size; ++i) {
                ((ForkJoinTask)futures.get(i)).quietlyJoin();
            }
            done = true;
            return futures;
        }
        finally {
            if (!done) {
                for (int j = 0, size2 = futures.size(); j < size2; ++j) {
                    futures.get(j).cancel(false);
                }
            }
        }
    }
    
    public ForkJoinWorkerThreadFactory getFactory() {
        return this.factory;
    }
    
    public Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return this.ueh;
    }
    
    public int getParallelism() {
        return this.config & 0xFFFF;
    }
    
    public static int getCommonPoolParallelism() {
        return ForkJoinPool.commonParallelism;
    }
    
    public int getPoolSize() {
        return (this.config & 0xFFFF) + (short)(this.ctl >>> 32);
    }
    
    public boolean getAsyncMode() {
        return this.config >>> 16 == 1;
    }
    
    public int getRunningThreadCount() {
        int rc = 0;
        final WorkQueue[] ws;
        if ((ws = this.workQueues) != null) {
            for (int i = 1; i < ws.length; i += 2) {
                final WorkQueue w;
                if ((w = ws[i]) != null && w.isApparentlyUnblocked()) {
                    ++rc;
                }
            }
        }
        return rc;
    }
    
    public int getActiveThreadCount() {
        final int r = (this.config & 0xFFFF) + (int)(this.ctl >> 48);
        return (r <= 0) ? 0 : r;
    }
    
    public boolean isQuiescent() {
        return (int)(this.ctl >> 48) + (this.config & 0xFFFF) == 0;
    }
    
    public long getStealCount() {
        long count = this.stealCount;
        final WorkQueue[] ws;
        if ((ws = this.workQueues) != null) {
            for (int i = 1; i < ws.length; i += 2) {
                final WorkQueue w;
                if ((w = ws[i]) != null) {
                    count += w.nsteals;
                }
            }
        }
        return count;
    }
    
    public long getQueuedTaskCount() {
        long count = 0L;
        final WorkQueue[] ws;
        if ((ws = this.workQueues) != null) {
            for (int i = 1; i < ws.length; i += 2) {
                final WorkQueue w;
                if ((w = ws[i]) != null) {
                    count += w.queueSize();
                }
            }
        }
        return count;
    }
    
    public int getQueuedSubmissionCount() {
        int count = 0;
        final WorkQueue[] ws;
        if ((ws = this.workQueues) != null) {
            for (int i = 0; i < ws.length; i += 2) {
                final WorkQueue w;
                if ((w = ws[i]) != null) {
                    count += w.queueSize();
                }
            }
        }
        return count;
    }
    
    public boolean hasQueuedSubmissions() {
        final WorkQueue[] ws;
        if ((ws = this.workQueues) != null) {
            for (int i = 0; i < ws.length; i += 2) {
                final WorkQueue w;
                if ((w = ws[i]) != null && !w.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    protected ForkJoinTask<?> pollSubmission() {
        final WorkQueue[] ws;
        if ((ws = this.workQueues) != null) {
            for (int i = 0; i < ws.length; i += 2) {
                final WorkQueue w;
                final ForkJoinTask<?> t;
                if ((w = ws[i]) != null && (t = w.poll()) != null) {
                    return t;
                }
            }
        }
        return null;
    }
    
    protected int drainTasksTo(final Collection<? super ForkJoinTask<?>> c) {
        int count = 0;
        final WorkQueue[] ws;
        if ((ws = this.workQueues) != null) {
            for (int i = 0; i < ws.length; ++i) {
                final WorkQueue w;
                if ((w = ws[i]) != null) {
                    ForkJoinTask<?> t;
                    while ((t = w.poll()) != null) {
                        c.add(t);
                        ++count;
                    }
                }
            }
        }
        return count;
    }
    
    @Override
    public String toString() {
        long qt = 0L;
        long qs = 0L;
        int rc = 0;
        long st = this.stealCount;
        final long c = this.ctl;
        final WorkQueue[] ws;
        if ((ws = this.workQueues) != null) {
            for (int i = 0; i < ws.length; ++i) {
                final WorkQueue w;
                if ((w = ws[i]) != null) {
                    final int size = w.queueSize();
                    if ((i & 0x1) == 0x0) {
                        qs += size;
                    }
                    else {
                        qt += size;
                        st += w.nsteals;
                        if (w.isApparentlyUnblocked()) {
                            ++rc;
                        }
                    }
                }
            }
        }
        final int pc = this.config & 0xFFFF;
        final int tc = pc + (short)(c >>> 32);
        int ac = pc + (int)(c >> 48);
        if (ac < 0) {
            ac = 0;
        }
        String level;
        if ((c & 0x80000000L) != 0x0L) {
            level = ((tc == 0) ? "Terminated" : "Terminating");
        }
        else {
            level = ((this.plock < 0) ? "Shutting down" : "Running");
        }
        return super.toString() + "[" + level + ", parallelism = " + pc + ", size = " + tc + ", active = " + ac + ", running = " + rc + ", steals = " + st + ", tasks = " + qt + ", submissions = " + qs + "]";
    }
    
    @Override
    public void shutdown() {
        checkPermission();
        this.tryTerminate(false, true);
    }
    
    @Override
    public List<Runnable> shutdownNow() {
        checkPermission();
        this.tryTerminate(true, true);
        return Collections.emptyList();
    }
    
    @Override
    public boolean isTerminated() {
        final long c = this.ctl;
        return (c & 0x80000000L) != 0x0L && (short)(c >>> 32) == -(this.config & 0xFFFF);
    }
    
    public boolean isTerminating() {
        final long c = this.ctl;
        return (c & 0x80000000L) != 0x0L && (short)(c >>> 32) != -(this.config & 0xFFFF);
    }
    
    @Override
    public boolean isShutdown() {
        return this.plock < 0;
    }
    
    @Override
    public boolean awaitTermination(final long timeout, final TimeUnit unit) throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        if (this == ForkJoinPool.common) {
            this.awaitQuiescence(timeout, unit);
            return false;
        }
        final long nanos = unit.toNanos(timeout);
        if (this.isTerminated()) {
            return true;
        }
        final long startTime = System.nanoTime();
        boolean terminated = false;
        synchronized (this) {
            long waitTime = nanos;
            long millis = 0L;
            while (!(terminated = (this.isTerminated() || waitTime <= 0L || (millis = unit.toMillis(waitTime)) <= 0L))) {
                this.wait(millis);
                waitTime = nanos - (System.nanoTime() - startTime);
            }
        }
        return terminated;
    }
    
    public boolean awaitQuiescence(final long timeout, final TimeUnit unit) {
        final long nanos = unit.toNanos(timeout);
        final Thread thread = Thread.currentThread();
        final ForkJoinWorkerThread wt;
        if (thread instanceof ForkJoinWorkerThread && (wt = (ForkJoinWorkerThread)thread).pool == this) {
            this.helpQuiescePool(wt.workQueue);
            return true;
        }
        final long startTime = System.nanoTime();
        int r = 0;
        boolean found = true;
        WorkQueue[] ws;
        int m;
        while (!this.isQuiescent() && (ws = this.workQueues) != null && (m = ws.length - 1) >= 0) {
            if (!found) {
                if (System.nanoTime() - startTime > nanos) {
                    return false;
                }
                Thread.yield();
            }
            found = false;
            int j = m + 1 << 2;
            while (j >= 0) {
                final WorkQueue q;
                final int b;
                if ((q = ws[r++ & m]) != null && (b = q.base) - q.top < 0) {
                    found = true;
                    final ForkJoinTask<?> t;
                    if ((t = q.pollAt(b)) != null) {
                        if (q.base - q.top < 0) {
                            this.signalWork(q);
                        }
                        t.doExec();
                        break;
                    }
                    break;
                }
                else {
                    --j;
                }
            }
        }
        return true;
    }
    
    static void quiesceCommonPool() {
        ForkJoinPool.common.awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    }
    
    public static void managedBlock(final ManagedBlocker blocker) throws InterruptedException {
        final Thread t = Thread.currentThread();
        if (t instanceof ForkJoinWorkerThread) {
            final ForkJoinPool p = ((ForkJoinWorkerThread)t).pool;
            while (!blocker.isReleasable()) {
                final WorkQueue[] ws;
                final int m;
                if ((ws = p.workQueues) != null && (m = ws.length - 1) >= 0) {
                    for (int i = 0; i <= m; ++i) {
                        if (blocker.isReleasable()) {
                            return;
                        }
                        final WorkQueue q;
                        if ((q = ws[i]) != null && q.base - q.top < 0) {
                            p.signalWork(q);
                            final int u;
                            if ((u = (int)(p.ctl >>> 32)) >= 0) {
                                break;
                            }
                            if (u >> 16 >= 0) {
                                break;
                            }
                        }
                    }
                }
                if (p.tryCompensate()) {
                    try {
                        while (!blocker.isReleasable() && !blocker.block()) {}
                    }
                    finally {
                        p.incrementActiveCount();
                    }
                    break;
                }
            }
        }
        else {
            while (!blocker.isReleasable() && !blocker.block()) {}
        }
    }
    
    @Override
    protected <T> RunnableFuture<T> newTaskFor(final Runnable runnable, final T value) {
        return new ForkJoinTask.AdaptedRunnable<T>(runnable, value);
    }
    
    @Override
    protected <T> RunnableFuture<T> newTaskFor(final Callable<T> callable) {
        return new ForkJoinTask.AdaptedCallable<T>((Callable<? extends T>)callable);
    }
    
    private static Unsafe getUnsafe() {
        try {
            return Unsafe.getUnsafe();
        }
        catch (SecurityException tryReflectionInstead) {
            try {
                return AccessController.doPrivileged((PrivilegedExceptionAction<Unsafe>)new PrivilegedExceptionAction<Unsafe>() {
                    @Override
                    public Unsafe run() throws Exception {
                        final Class<Unsafe> k = Unsafe.class;
                        for (final Field f : k.getDeclaredFields()) {
                            f.setAccessible(true);
                            final Object x = f.get(null);
                            if (k.isInstance(x)) {
                                return k.cast(x);
                            }
                        }
                        throw new NoSuchFieldError("the Unsafe");
                    }
                });
            }
            catch (PrivilegedActionException e) {
                throw new RuntimeException("Could not initialize intrinsics", e.getCause());
            }
        }
    }
    
    static {
        try {
            U = getUnsafe();
            final Class<?> k = ForkJoinPool.class;
            CTL = ForkJoinPool.U.objectFieldOffset(k.getDeclaredField("ctl"));
            STEALCOUNT = ForkJoinPool.U.objectFieldOffset(k.getDeclaredField("stealCount"));
            PLOCK = ForkJoinPool.U.objectFieldOffset(k.getDeclaredField("plock"));
            INDEXSEED = ForkJoinPool.U.objectFieldOffset(k.getDeclaredField("indexSeed"));
            final Class<?> tk = Thread.class;
            PARKBLOCKER = ForkJoinPool.U.objectFieldOffset(tk.getDeclaredField("parkBlocker"));
            final Class<?> wk = WorkQueue.class;
            QLOCK = ForkJoinPool.U.objectFieldOffset(wk.getDeclaredField("qlock"));
            final Class<?> ak = ForkJoinTask[].class;
            ABASE = ForkJoinPool.U.arrayBaseOffset(ak);
            final int scale = ForkJoinPool.U.arrayIndexScale(ak);
            if ((scale & scale - 1) != 0x0) {
                throw new Error("data type scale not a power of two");
            }
            ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
        }
        catch (Exception e) {
            throw new Error(e);
        }
        submitters = new ThreadLocal<Submitter>();
        ForkJoinWorkerThreadFactory fac = defaultForkJoinWorkerThreadFactory = new DefaultForkJoinWorkerThreadFactory();
        modifyThreadPermission = new RuntimePermission("modifyThread");
        int par = 0;
        Thread.UncaughtExceptionHandler handler = null;
        try {
            final String pp = System.getProperty("java.util.concurrent.ForkJoinPool.common.parallelism");
            final String hp = System.getProperty("java.util.concurrent.ForkJoinPool.common.exceptionHandler");
            final String fp = System.getProperty("java.util.concurrent.ForkJoinPool.common.threadFactory");
            if (fp != null) {
                fac = (ForkJoinWorkerThreadFactory)ClassLoader.getSystemClassLoader().loadClass(fp).newInstance();
            }
            if (hp != null) {
                handler = (Thread.UncaughtExceptionHandler)ClassLoader.getSystemClassLoader().loadClass(hp).newInstance();
            }
            if (pp != null) {
                par = Integer.parseInt(pp);
            }
        }
        catch (Exception ex) {}
        if (par <= 0) {
            par = Runtime.getRuntime().availableProcessors();
        }
        if (par > 32767) {
            par = 32767;
        }
        commonParallelism = par;
        final long np = -par;
        final long ct = (np << 48 & 0xFFFF000000000000L) | (np << 32 & 0xFFFF00000000L);
        common = new ForkJoinPool(par, ct, fac, handler);
    }
    
    static final class DefaultForkJoinWorkerThreadFactory implements ForkJoinWorkerThreadFactory
    {
        @Override
        public final ForkJoinWorkerThread newThread(final ForkJoinPool pool) {
            return new ForkJoinWorkerThread(pool);
        }
    }
    
    static final class Submitter
    {
        int seed;
        
        Submitter(final int s) {
            this.seed = s;
        }
    }
    
    static final class EmptyTask extends ForkJoinTask<Void>
    {
        private static final long serialVersionUID = -7721805057305804111L;
        
        EmptyTask() {
            this.status = -268435456;
        }
        
        @Override
        public final Void getRawResult() {
            return null;
        }
        
        public final void setRawResult(final Void x) {
        }
        
        public final boolean exec() {
            return true;
        }
    }
    
    static final class WorkQueue
    {
        static final int INITIAL_QUEUE_CAPACITY = 8192;
        static final int MAXIMUM_QUEUE_CAPACITY = 67108864;
        volatile long pad00;
        volatile long pad01;
        volatile long pad02;
        volatile long pad03;
        volatile long pad04;
        volatile long pad05;
        volatile long pad06;
        int seed;
        volatile int eventCount;
        int nextWait;
        int hint;
        int poolIndex;
        final int mode;
        int nsteals;
        volatile int qlock;
        volatile int base;
        int top;
        ForkJoinTask<?>[] array;
        final ForkJoinPool pool;
        final ForkJoinWorkerThread owner;
        volatile Thread parker;
        volatile ForkJoinTask<?> currentJoin;
        ForkJoinTask<?> currentSteal;
        volatile Object pad10;
        volatile Object pad11;
        volatile Object pad12;
        volatile Object pad13;
        volatile Object pad14;
        volatile Object pad15;
        volatile Object pad16;
        volatile Object pad17;
        volatile Object pad18;
        volatile Object pad19;
        volatile Object pad1a;
        volatile Object pad1b;
        volatile Object pad1c;
        volatile Object pad1d;
        private static final Unsafe U;
        private static final long QLOCK;
        private static final int ABASE;
        private static final int ASHIFT;
        
        WorkQueue(final ForkJoinPool pool, final ForkJoinWorkerThread owner, final int mode, final int seed) {
            this.pool = pool;
            this.owner = owner;
            this.mode = mode;
            this.seed = seed;
            final int n = 4096;
            this.top = n;
            this.base = n;
        }
        
        final int queueSize() {
            final int n = this.base - this.top;
            return (n >= 0) ? 0 : (-n);
        }
        
        final boolean isEmpty() {
            final int s;
            final int n = this.base - (s = this.top);
            final ForkJoinTask<?>[] a;
            final int m;
            return n >= 0 || (n == -1 && ((a = this.array) == null || (m = a.length - 1) < 0 || WorkQueue.U.getObject(a, ((m & s - 1) << WorkQueue.ASHIFT) + WorkQueue.ABASE) == null));
        }
        
        final void push(final ForkJoinTask<?> task) {
            final int s = this.top;
            final ForkJoinTask<?>[] a;
            if ((a = this.array) != null) {
                final int m;
                final int j = (((m = a.length - 1) & s) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                WorkQueue.U.putOrderedObject(a, j, task);
                final int top = s + 1;
                this.top = top;
                final int n;
                if ((n = top - this.base) <= 2) {
                    final ForkJoinPool p;
                    if ((p = this.pool) != null) {
                        p.signalWork(this);
                    }
                }
                else if (n >= m) {
                    this.growArray();
                }
            }
        }
        
        final ForkJoinTask<?>[] growArray() {
            final ForkJoinTask<?>[] oldA = this.array;
            final int size = (oldA != null) ? (oldA.length << 1) : 8192;
            if (size > 67108864) {
                throw new RejectedExecutionException("Queue capacity exceeded");
            }
            final ForkJoinTask<?>[] array = (ForkJoinTask<?>[])new ForkJoinTask[size];
            this.array = array;
            final ForkJoinTask<?>[] a = array;
            final int oldMask;
            if (oldA != null && (oldMask = oldA.length - 1) >= 0) {
                final int t = this.top;
                int b;
                if (t - (b = this.base) > 0) {
                    final int mask = size - 1;
                    do {
                        final int oldj = ((b & oldMask) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                        final int j = ((b & mask) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                        final ForkJoinTask<?> x = (ForkJoinTask<?>)WorkQueue.U.getObjectVolatile(oldA, oldj);
                        if (x != null && WorkQueue.U.compareAndSwapObject(oldA, oldj, x, null)) {
                            WorkQueue.U.putObjectVolatile(a, j, x);
                        }
                    } while (++b != t);
                }
            }
            return a;
        }
        
        final ForkJoinTask<?> pop() {
            final ForkJoinTask<?>[] a;
            final int m;
            if ((a = this.array) != null && (m = a.length - 1) >= 0) {
                int s;
                while ((s = this.top - 1) - this.base >= 0) {
                    final long j = ((m & s) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                    final ForkJoinTask<?> t;
                    if ((t = (ForkJoinTask<?>)WorkQueue.U.getObject(a, j)) == null) {
                        break;
                    }
                    if (WorkQueue.U.compareAndSwapObject(a, j, t, null)) {
                        this.top = s;
                        return t;
                    }
                }
            }
            return null;
        }
        
        final ForkJoinTask<?> pollAt(final int b) {
            final ForkJoinTask<?>[] a;
            if ((a = this.array) != null) {
                final int j = ((a.length - 1 & b) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                final ForkJoinTask<?> t;
                if ((t = (ForkJoinTask<?>)WorkQueue.U.getObjectVolatile(a, j)) != null && this.base == b && WorkQueue.U.compareAndSwapObject(a, j, t, null)) {
                    this.base = b + 1;
                    return t;
                }
            }
            return null;
        }
        
        final ForkJoinTask<?> poll() {
            int b;
            ForkJoinTask<?>[] a;
            while ((b = this.base) - this.top < 0 && (a = this.array) != null) {
                final int j = ((a.length - 1 & b) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                final ForkJoinTask<?> t = (ForkJoinTask<?>)WorkQueue.U.getObjectVolatile(a, j);
                if (t != null) {
                    if (this.base == b && WorkQueue.U.compareAndSwapObject(a, j, t, null)) {
                        this.base = b + 1;
                        return t;
                    }
                    continue;
                }
                else {
                    if (this.base != b) {
                        continue;
                    }
                    if (b + 1 == this.top) {
                        break;
                    }
                    Thread.yield();
                }
            }
            return null;
        }
        
        final ForkJoinTask<?> nextLocalTask() {
            return (this.mode == 0) ? this.pop() : this.poll();
        }
        
        final ForkJoinTask<?> peek() {
            final ForkJoinTask<?>[] a = this.array;
            final int m;
            if (a == null || (m = a.length - 1) < 0) {
                return null;
            }
            final int i = (this.mode == 0) ? (this.top - 1) : this.base;
            final int j = ((i & m) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
            return (ForkJoinTask<?>)WorkQueue.U.getObjectVolatile(a, j);
        }
        
        final boolean tryUnpush(final ForkJoinTask<?> t) {
            final ForkJoinTask<?>[] a;
            int s;
            if ((a = this.array) != null && (s = this.top) != this.base && WorkQueue.U.compareAndSwapObject(a, ((a.length - 1 & --s) << WorkQueue.ASHIFT) + WorkQueue.ABASE, t, null)) {
                this.top = s;
                return true;
            }
            return false;
        }
        
        final void cancelAll() {
            ForkJoinTask.cancelIgnoringExceptions(this.currentJoin);
            ForkJoinTask.cancelIgnoringExceptions(this.currentSteal);
            ForkJoinTask<?> t;
            while ((t = this.poll()) != null) {
                ForkJoinTask.cancelIgnoringExceptions(t);
            }
        }
        
        final int nextSeed() {
            int r = this.seed;
            r ^= r << 13;
            r ^= r >>> 17;
            return this.seed = (r ^= r << 5);
        }
        
        private void popAndExecAll() {
            ForkJoinTask<?>[] a;
            int m;
            int s;
            long j;
            ForkJoinTask<?> t;
            while ((a = this.array) != null && (m = a.length - 1) >= 0 && (s = this.top - 1) - this.base >= 0 && (t = (ForkJoinTask<?>)WorkQueue.U.getObject(a, j = ((m & s) << WorkQueue.ASHIFT) + WorkQueue.ABASE)) != null) {
                if (WorkQueue.U.compareAndSwapObject(a, j, t, null)) {
                    this.top = s;
                    t.doExec();
                }
            }
        }
        
        private void pollAndExecAll() {
            ForkJoinTask<?> t;
            while ((t = this.poll()) != null) {
                t.doExec();
            }
        }
        
        final boolean tryRemoveAndExec(final ForkJoinTask<?> task) {
            boolean stat = true;
            boolean removed = false;
            boolean empty = true;
            final ForkJoinTask<?>[] a;
            final int m;
            int s;
            final int b;
            int n;
            if ((a = this.array) != null && (m = a.length - 1) >= 0 && (n = (s = this.top) - (b = this.base)) > 0) {
                while (true) {
                    final int j = ((--s & m) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                    final ForkJoinTask<?> t = (ForkJoinTask<?>)WorkQueue.U.getObjectVolatile(a, j);
                    if (t == null) {
                        break;
                    }
                    if (t == task) {
                        if (s + 1 == this.top) {
                            if (!WorkQueue.U.compareAndSwapObject(a, j, task, null)) {
                                break;
                            }
                            this.top = s;
                            removed = true;
                            break;
                        }
                        else {
                            if (this.base == b) {
                                removed = WorkQueue.U.compareAndSwapObject(a, j, task, new EmptyTask());
                                break;
                            }
                            break;
                        }
                    }
                    else {
                        if (t.status >= 0) {
                            empty = false;
                        }
                        else if (s + 1 == this.top) {
                            if (WorkQueue.U.compareAndSwapObject(a, j, t, null)) {
                                this.top = s;
                                break;
                            }
                            break;
                        }
                        if (--n != 0) {
                            continue;
                        }
                        if (!empty && this.base == b) {
                            stat = false;
                            break;
                        }
                        break;
                    }
                }
            }
            if (removed) {
                task.doExec();
            }
            return stat;
        }
        
        final boolean pollAndExecCC(final ForkJoinTask<?> root) {
            int b;
            ForkJoinTask<?>[] a;
        Label_0138:
            while ((b = this.base) - this.top < 0 && (a = this.array) != null) {
                final long j = ((a.length - 1 & b) << WorkQueue.ASHIFT) + WorkQueue.ABASE;
                final Object o;
                if ((o = WorkQueue.U.getObject(a, j)) == null) {
                    break;
                }
                if (!(o instanceof CountedCompleter)) {
                    break;
                }
                CountedCompleter<?> r;
                final CountedCompleter<?> t = r = (CountedCompleter<?>)o;
                while (r != root) {
                    if ((r = r.completer) == null) {
                        break Label_0138;
                    }
                }
                if (this.base == b && WorkQueue.U.compareAndSwapObject(a, j, t, null)) {
                    this.base = b + 1;
                    t.doExec();
                    return true;
                }
            }
            return false;
        }
        
        final void runTask(final ForkJoinTask<?> t) {
            if (t != null) {
                (this.currentSteal = t).doExec();
                this.currentSteal = null;
                ++this.nsteals;
                if (this.base - this.top < 0) {
                    if (this.mode == 0) {
                        this.popAndExecAll();
                    }
                    else {
                        this.pollAndExecAll();
                    }
                }
            }
        }
        
        final void runSubtask(final ForkJoinTask<?> t) {
            if (t != null) {
                final ForkJoinTask<?> ps = this.currentSteal;
                (this.currentSteal = t).doExec();
                this.currentSteal = ps;
            }
        }
        
        final boolean isApparentlyUnblocked() {
            final Thread wt;
            final Thread.State s;
            return this.eventCount >= 0 && (wt = this.owner) != null && (s = wt.getState()) != Thread.State.BLOCKED && s != Thread.State.WAITING && s != Thread.State.TIMED_WAITING;
        }
        
        static {
            try {
                U = getUnsafe();
                final Class<?> k = WorkQueue.class;
                final Class<?> ak = ForkJoinTask[].class;
                QLOCK = WorkQueue.U.objectFieldOffset(k.getDeclaredField("qlock"));
                ABASE = WorkQueue.U.arrayBaseOffset(ak);
                final int scale = WorkQueue.U.arrayIndexScale(ak);
                if ((scale & scale - 1) != 0x0) {
                    throw new Error("data type scale not a power of two");
                }
                ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
            }
            catch (Exception e) {
                throw new Error(e);
            }
        }
    }
    
    public interface ManagedBlocker
    {
        boolean block() throws InterruptedException;
        
        boolean isReleasable();
    }
    
    public interface ForkJoinWorkerThreadFactory
    {
        ForkJoinWorkerThread newThread(final ForkJoinPool p0);
    }
}
