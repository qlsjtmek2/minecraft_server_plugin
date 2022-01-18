// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.thread;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

public class ThreadPool
{
    private static final Logger logger;
    private long maxIdleTime;
    private String poolName;
    private int minSize;
    private boolean isDaemon;
    private boolean isStopping;
    private Integer threadPriority;
    private int uniqueThreadID;
    private Vector<PooledThread> freeList;
    private Vector<PooledThread> busyList;
    private Vector<Work> workOverflowQueue;
    private int maxSize;
    private boolean stopThePool;
    
    public ThreadPool(final String poolName, final boolean isDaemon, final Integer threadPriority) {
        this.isStopping = false;
        this.freeList = new Vector<PooledThread>();
        this.busyList = new Vector<PooledThread>();
        this.workOverflowQueue = new Vector<Work>();
        this.maxSize = 100;
        this.poolName = poolName;
        this.stopThePool = false;
        this.isDaemon = isDaemon;
        this.threadPriority = threadPriority;
    }
    
    public boolean isStopping() {
        return this.isStopping;
    }
    
    public String getName() {
        return this.poolName;
    }
    
    public void setMinSize(final int minSize) {
        if (minSize > 0) {
            if (minSize > this.maxSize) {
                this.maxSize = minSize;
            }
            this.minSize = minSize;
            this.maintainPoolSize();
        }
    }
    
    public int getMinSize() {
        return this.minSize;
    }
    
    public void setMaxSize(final int maxSize) {
        if (maxSize > 0) {
            if (this.minSize > maxSize) {
                this.minSize = maxSize;
            }
            this.maxSize = maxSize;
            this.maintainPoolSize();
        }
    }
    
    public int getMaxSize() {
        return this.maxSize;
    }
    
    public int size() {
        return this.busyList.size() + this.freeList.size();
    }
    
    public int getBusyCount() {
        return this.busyList.size();
    }
    
    public boolean assign(final Runnable work, final boolean addToQueueIfFull) {
        if (this.stopThePool) {
            throw new RuntimeException("Pool is stopping... no more work please.");
        }
        final Work runWork = new Work(work);
        final PooledThread thread = this.getNextAvailableThread();
        if (thread != null) {
            this.busyList.add(thread);
            thread.assignWork(runWork);
            return true;
        }
        if (addToQueueIfFull) {
            runWork.setEnterQueueTime(System.currentTimeMillis());
            this.workOverflowQueue.add(runWork);
        }
        return false;
    }
    
    protected void removeThread(final PooledThread thread) {
        synchronized (this.freeList) {
            this.busyList.remove(thread);
            this.freeList.remove(thread);
            this.freeList.notify();
        }
    }
    
    protected void returnThread(final PooledThread thread) {
        synchronized (this.freeList) {
            this.busyList.remove(thread);
            if (!this.workOverflowQueue.isEmpty()) {
                final Work queuedWork = this.workOverflowQueue.remove(0);
                queuedWork.setExitQueueTime(System.currentTimeMillis());
                this.busyList.add(thread);
                thread.assignWork(queuedWork);
            }
            else {
                this.freeList.add(thread);
                this.freeList.notify();
            }
        }
    }
    
    private PooledThread getNextAvailableThread() {
        synchronized (this.freeList) {
            if (!this.freeList.isEmpty()) {
                return this.freeList.remove(0);
            }
            if (this.size() < this.maxSize) {
                return this.growPool(true);
            }
            return null;
        }
    }
    
    public Iterator<PooledThread> getBusyThreads() {
        synchronized (this.freeList) {
            return this.busyList.iterator();
        }
    }
    
    protected void shutdown() {
        synchronized (this.freeList) {
            this.isStopping = true;
            final int size = this.size();
            if (size > 0) {
                String msg = null;
                msg = "ThreadPool [" + this.poolName + "] Shutting down; threadCount[" + this.size() + "] busyCount[" + this.getBusyCount() + "]";
                ThreadPool.logger.info(msg);
            }
            this.stopThePool = true;
            while (!this.freeList.isEmpty()) {
                final PooledThread thread = this.freeList.remove(0);
                thread.stop();
            }
            try {
                while (this.getBusyCount() > 0) {
                    final String msg = "ThreadPool [" + this.poolName + "] has [" + this.getBusyCount() + "] busy threads, waiting for those to finish.";
                    ThreadPool.logger.info(msg);
                    final Iterator<PooledThread> busyThreads = this.getBusyThreads();
                    while (busyThreads.hasNext()) {
                        final PooledThread busyThread = busyThreads.next();
                        final String threadName = busyThread.getName();
                        final Work work = busyThread.getWork();
                        final String busymsg = "Busy thread [" + threadName + "] work[" + work + "]";
                        ThreadPool.logger.info(busymsg);
                    }
                    this.freeList.wait();
                    final PooledThread thread2 = this.freeList.remove(0);
                    if (thread2 != null) {
                        thread2.stop();
                    }
                }
            }
            catch (InterruptedException e) {
                ThreadPool.logger.log(Level.SEVERE, null, e);
            }
        }
    }
    
    protected void maintainPoolSize() {
        synchronized (this.freeList) {
            if (this.isStopping) {
                return;
            }
            int numToStop = this.size() - this.minSize;
            if (numToStop > 0) {
                final long usedAfter = System.currentTimeMillis() - this.maxIdleTime;
                final ArrayList<PooledThread> stopList = new ArrayList<PooledThread>();
                for (Iterator<PooledThread> it = this.freeList.iterator(); it.hasNext() && numToStop > 0; --numToStop) {
                    final PooledThread thread = it.next();
                    if (thread.getLastUsedTime() < usedAfter) {
                        stopList.add(thread);
                    }
                }
                for (final PooledThread thread2 : stopList) {
                    thread2.stop();
                }
            }
            final int numToAdd = this.minSize - this.size();
            if (numToAdd > 0) {
                for (int i = 0; i < numToAdd; ++i) {
                    this.growPool(false);
                }
            }
        }
    }
    
    public PooledThread interrupt(final String threadName) {
        final PooledThread thread = this.getBusyThread(threadName);
        if (thread != null) {
            thread.interrupt();
            return thread;
        }
        return null;
    }
    
    public PooledThread getBusyThread(final String threadName) {
        synchronized (this.freeList) {
            final Iterator<PooledThread> it = this.getBusyThreads();
            while (it.hasNext()) {
                final PooledThread pt = it.next();
                if (pt.getName().equals(threadName)) {
                    return pt;
                }
            }
            return null;
        }
    }
    
    private PooledThread growPool(final boolean andReturn) {
        synchronized (this.freeList) {
            final String threadName = this.poolName + "." + this.uniqueThreadID++;
            final PooledThread bgw = new PooledThread(this, threadName, this.isDaemon, this.threadPriority);
            bgw.start();
            if (ThreadPool.logger.isLoggable(Level.FINE)) {
                ThreadPool.logger.fine("ThreadPool grow created [" + threadName + "] size[" + this.size() + "]");
            }
            if (andReturn) {
                return bgw;
            }
            this.freeList.add(bgw);
            return null;
        }
    }
    
    public long getMaxIdleTime() {
        return this.maxIdleTime;
    }
    
    public void setMaxIdleTime(final long maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }
    
    static {
        logger = Logger.getLogger(ThreadPool.class.getName());
    }
}
