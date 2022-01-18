// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.sql;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.sql.SQLException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class PooledConnectionQueue
{
    private static final Logger logger;
    private static final TimeUnit MILLIS_TIME_UNIT;
    private final String name;
    private final DataSourcePool pool;
    private final FreeConnectionBuffer freeList;
    private final BusyConnectionBuffer busyList;
    private final ReentrantLock lock;
    private final Condition notEmpty;
    private int connectionId;
    private long waitTimeoutMillis;
    private long leakTimeMinutes;
    private int warningSize;
    private int maxSize;
    private int minSize;
    private int waitingThreads;
    private int waitCount;
    private int hitCount;
    private int highWaterMark;
    private long lastResetTime;
    private boolean doingShutdown;
    
    public PooledConnectionQueue(final DataSourcePool pool) {
        this.pool = pool;
        this.name = pool.getName();
        this.minSize = pool.getMinSize();
        this.maxSize = pool.getMaxSize();
        this.warningSize = pool.getWarningSize();
        this.waitTimeoutMillis = pool.getWaitTimeoutMillis();
        this.leakTimeMinutes = pool.getLeakTimeMinutes();
        this.busyList = new BusyConnectionBuffer(50, 20);
        this.freeList = new FreeConnectionBuffer(this.maxSize);
        this.lock = new ReentrantLock(true);
        this.notEmpty = this.lock.newCondition();
    }
    
    private DataSourcePool.Status createStatus() {
        return new DataSourcePool.Status(this.name, this.minSize, this.maxSize, this.freeList.size(), this.busyList.size(), this.waitingThreads, this.highWaterMark, this.waitCount, this.hitCount);
    }
    
    public String toString() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return this.createStatus().toString();
        }
        finally {
            lock.unlock();
        }
    }
    
    public DataSourcePool.Status getStatus(final boolean reset) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final DataSourcePool.Status s = this.createStatus();
            if (reset) {
                this.highWaterMark = this.busyList.size();
                this.hitCount = 0;
                this.waitCount = 0;
            }
            return s;
        }
        finally {
            lock.unlock();
        }
    }
    
    public void setMinSize(final int minSize) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (minSize > this.maxSize) {
                throw new IllegalArgumentException("minSize " + minSize + " > maxSize " + this.maxSize);
            }
            this.minSize = minSize;
        }
        finally {
            lock.unlock();
        }
    }
    
    public void setMaxSize(final int maxSize) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (maxSize < this.minSize) {
                throw new IllegalArgumentException("maxSize " + maxSize + " < minSize " + this.minSize);
            }
            this.freeList.setCapacity(maxSize);
            this.maxSize = maxSize;
        }
        finally {
            lock.unlock();
        }
    }
    
    public void setWarningSize(final int warningSize) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (warningSize > this.maxSize) {
                throw new IllegalArgumentException("warningSize " + warningSize + " > maxSize " + this.maxSize);
            }
            this.warningSize = warningSize;
        }
        finally {
            lock.unlock();
        }
    }
    
    private int totalConnections() {
        return this.freeList.size() + this.busyList.size();
    }
    
    public void ensureMinimumConnections() throws SQLException {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final int add = this.minSize - this.totalConnections();
            if (add > 0) {
                for (int i = 0; i < add; ++i) {
                    final PooledConnection c = this.pool.createConnectionForQueue(this.connectionId++);
                    this.freeList.add(c);
                }
                this.notEmpty.signal();
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    protected void returnPooledConnection(final PooledConnection c) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (!this.busyList.remove(c)) {
                PooledConnectionQueue.logger.log(Level.SEVERE, "Connection [" + c + "] not found in BusyList? ");
            }
            if (c.getCreationTime() <= this.lastResetTime) {
                c.closeConnectionFully(false);
            }
            else {
                this.freeList.add(c);
                this.notEmpty.signal();
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    private PooledConnection extractFromFreeList() {
        final PooledConnection c = this.freeList.remove();
        this.registerBusyConnection(c);
        return c;
    }
    
    public PooledConnection getPooledConnection() throws SQLException {
        try {
            final PooledConnection pc = this._getPooledConnection();
            pc.resetForUse();
            return pc;
        }
        catch (InterruptedException e) {
            final String msg = "Interrupted getting connection from pool " + e;
            throw new SQLException(msg);
        }
    }
    
    private int registerBusyConnection(final PooledConnection c) {
        final int busySize = this.busyList.add(c);
        if (busySize > this.highWaterMark) {
            this.highWaterMark = busySize;
        }
        return busySize;
    }
    
    private PooledConnection _getPooledConnection() throws InterruptedException, SQLException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            if (this.doingShutdown) {
                throw new SQLException("Trying to access the Connection Pool when it is shutting down");
            }
            ++this.hitCount;
            if (this.waitingThreads == 0) {
                final int freeSize = this.freeList.size();
                if (freeSize > 0) {
                    return this.extractFromFreeList();
                }
                if (this.busyList.size() < this.maxSize) {
                    final PooledConnection c = this.pool.createConnectionForQueue(this.connectionId++);
                    final int busySize = this.registerBusyConnection(c);
                    final String msg = "DataSourcePool [" + this.name + "] grow; id[" + c.getName() + "] busy[" + busySize + "] max[" + this.maxSize + "]";
                    PooledConnectionQueue.logger.info(msg);
                    this.checkForWarningSize();
                    return c;
                }
            }
            try {
                ++this.waitCount;
                ++this.waitingThreads;
                return this._getPooledConnectionWaitLoop();
            }
            finally {
                --this.waitingThreads;
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    private PooledConnection _getPooledConnectionWaitLoop() throws SQLException, InterruptedException {
        long nanos = PooledConnectionQueue.MILLIS_TIME_UNIT.toNanos(this.waitTimeoutMillis);
        while (nanos > 0L) {
            try {
                nanos = this.notEmpty.awaitNanos(nanos);
                if (!this.freeList.isEmpty()) {
                    return this.extractFromFreeList();
                }
                continue;
            }
            catch (InterruptedException ie) {
                this.notEmpty.signal();
                throw ie;
            }
        }
        final String msg = "Unsuccessfully waited [" + this.waitTimeoutMillis + "] millis for a connection to be returned." + " No connections are free. You need to Increase the max connections of [" + this.maxSize + "]" + " or look for a connection pool leak using datasource.xxx.capturestacktrace=true";
        throw new SQLException(msg);
    }
    
    public void shutdown() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            this.doingShutdown = true;
            final DataSourcePool.Status status = this.createStatus();
            PooledConnectionQueue.logger.info("DataSourcePool [" + this.name + "] shutdown: " + status);
            this.closeFreeConnections(true);
            if (!this.busyList.isEmpty()) {
                final String msg = "A potential connection leak was detected.  Busy connections: " + this.busyList.size();
                PooledConnectionQueue.logger.warning(msg);
                this.dumpBusyConnectionInformation();
                this.closeBusyConnections(0L);
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    public void reset(final long leakTimeMinutes) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final DataSourcePool.Status status = this.createStatus();
            PooledConnectionQueue.logger.info("Reseting DataSourcePool [" + this.name + "] " + status);
            this.lastResetTime = System.currentTimeMillis();
            this.closeFreeConnections(false);
            this.closeBusyConnections(leakTimeMinutes);
            final String busyMsg = "Busy Connections:\r\n" + this.getBusyConnectionInformation();
            PooledConnectionQueue.logger.info(busyMsg);
        }
        finally {
            lock.unlock();
        }
    }
    
    public void trim(final int maxInactiveTimeSecs) throws SQLException {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            this.trimInactiveConnections(maxInactiveTimeSecs);
            this.ensureMinimumConnections();
        }
        finally {
            lock.unlock();
        }
    }
    
    private int trimInactiveConnections(final int maxInactiveTimeSecs) {
        final int maxTrim = this.freeList.size() - this.minSize;
        if (maxTrim <= 0) {
            return 0;
        }
        int trimedCount = 0;
        final long usedSince = System.currentTimeMillis() - maxInactiveTimeSecs * 1000;
        final List<PooledConnection> freeListCopy = this.freeList.getShallowCopy();
        final Iterator<PooledConnection> it = freeListCopy.iterator();
        while (it.hasNext()) {
            final PooledConnection pc = it.next();
            if (pc.getLastUsedTime() < usedSince) {
                ++trimedCount;
                it.remove();
                pc.closeConnectionFully(true);
                if (trimedCount >= maxTrim) {
                    break;
                }
                continue;
            }
        }
        if (trimedCount > 0) {
            this.freeList.setShallowCopy(freeListCopy);
            final String msg = "DataSourcePool [" + this.name + "] trimmed [" + trimedCount + "] inactive connections. New size[" + this.totalConnections() + "]";
            PooledConnectionQueue.logger.info(msg);
        }
        return trimedCount;
    }
    
    public void closeFreeConnections(final boolean logErrors) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            while (!this.freeList.isEmpty()) {
                final PooledConnection c = this.freeList.remove();
                PooledConnectionQueue.logger.info("PSTMT Statistics: " + c.getStatistics());
                c.closeConnectionFully(logErrors);
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    public void closeBusyConnections(final long leakTimeMinutes) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final long olderThanTime = System.currentTimeMillis() - leakTimeMinutes * 60000L;
            final List<PooledConnection> copy = this.busyList.getShallowCopy();
            for (int i = 0; i < copy.size(); ++i) {
                final PooledConnection pc = copy.get(i);
                if (!pc.isLongRunning()) {
                    if (pc.getLastUsedTime() <= olderThanTime) {
                        this.busyList.remove(pc);
                        this.closeBusyConnection(pc);
                    }
                }
            }
        }
        finally {
            lock.unlock();
        }
    }
    
    private void closeBusyConnection(final PooledConnection pc) {
        try {
            final String methodLine = pc.getCreatedByMethod();
            final Date luDate = new Date();
            luDate.setTime(pc.getLastUsedTime());
            final String msg = "DataSourcePool closing leaked connection?  name[" + pc.getName() + "] lastUsed[" + luDate + "] createdBy[" + methodLine + "] lastStmt[" + pc.getLastStatement() + "]";
            PooledConnectionQueue.logger.warning(msg);
            this.logStackElement(pc, "Possible Leaked Connection: ");
            System.out.println("CLOSING BUSY CONNECTION ??? " + pc);
            pc.close();
        }
        catch (SQLException ex) {
            PooledConnectionQueue.logger.log(Level.SEVERE, null, ex);
        }
    }
    
    private void logStackElement(final PooledConnection pc, final String prefix) {
        final StackTraceElement[] stackTrace = pc.getStackTrace();
        if (stackTrace != null) {
            final String s = Arrays.toString(stackTrace);
            final String msg = prefix + " name[" + pc.getName() + "] stackTrace: " + s;
            PooledConnectionQueue.logger.warning(msg);
            System.err.println(msg);
        }
    }
    
    private void checkForWarningSize() {
        final int availableGrowth = this.maxSize - this.totalConnections();
        if (availableGrowth < this.warningSize) {
            this.closeBusyConnections(this.leakTimeMinutes);
            final String msg = "DataSourcePool [" + this.name + "] is [" + availableGrowth + "] connections from its maximum size.";
            this.pool.notifyWarning(msg);
        }
    }
    
    public String getBusyConnectionInformation() {
        return this.getBusyConnectionInformation(false);
    }
    
    public void dumpBusyConnectionInformation() {
        this.getBusyConnectionInformation(true);
    }
    
    private String getBusyConnectionInformation(final boolean toLogger) {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            if (toLogger) {
                PooledConnectionQueue.logger.info("Dumping busy connections: (Use datasource.xxx.capturestacktrace=true  ... to get stackTraces)");
            }
            final StringBuilder sb = new StringBuilder();
            final List<PooledConnection> copy = this.busyList.getShallowCopy();
            for (int i = 0; i < copy.size(); ++i) {
                final PooledConnection pc = copy.get(i);
                if (toLogger) {
                    PooledConnectionQueue.logger.info(pc.getDescription());
                    this.logStackElement(pc, "Busy Connection: ");
                }
                else {
                    sb.append(pc.getDescription()).append("\r\n");
                }
            }
            return sb.toString();
        }
        finally {
            lock.unlock();
        }
    }
    
    static {
        logger = Logger.getLogger(PooledConnectionQueue.class.getName());
        MILLIS_TIME_UNIT = TimeUnit.MILLISECONDS;
    }
}
