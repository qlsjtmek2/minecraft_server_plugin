// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.thread;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PooledThread implements Runnable
{
    private static final Logger logger;
    private boolean wasInterrupted;
    private long lastUsedTime;
    private Work work;
    private boolean isStopping;
    private boolean isStopped;
    private Thread thread;
    private ThreadPool threadPool;
    private String name;
    private Object threadMonitor;
    private Object workMonitor;
    private int totalWorkCount;
    private long totalWorkExecutionTime;
    
    protected PooledThread(final ThreadPool threadPool, final String name, final boolean isDaemon, final Integer threadPriority) {
        this.wasInterrupted = false;
        this.work = null;
        this.isStopping = false;
        this.isStopped = false;
        this.thread = null;
        this.name = null;
        this.threadMonitor = new Object();
        this.workMonitor = new Object();
        this.totalWorkCount = 0;
        this.totalWorkExecutionTime = 0L;
        this.name = name;
        this.threadPool = threadPool;
        this.lastUsedTime = System.currentTimeMillis();
        (this.thread = new Thread(this, name)).setDaemon(isDaemon);
        if (threadPriority != null) {
            this.thread.setPriority(threadPriority);
        }
    }
    
    protected void start() {
        this.thread.start();
    }
    
    public boolean assignWork(final Work work) {
        synchronized (this.workMonitor) {
            this.work = work;
            this.workMonitor.notifyAll();
        }
        return true;
    }
    
    public void run() {
        synchronized (this.workMonitor) {
            while (!this.isStopping) {
                try {
                    if (this.work == null) {
                        this.workMonitor.wait();
                    }
                }
                catch (InterruptedException ex) {}
                this.doTheWork();
            }
        }
        synchronized (this.threadMonitor) {
            this.threadMonitor.notifyAll();
        }
        this.isStopped = true;
    }
    
    private void doTheWork() {
        if (this.isStopping) {
            return;
        }
        final long startTime = System.currentTimeMillis();
        if (this.work != null) {
            try {
                this.work.setStartTime(startTime);
                this.work.getRunnable().run();
            }
            catch (Throwable ex) {
                PooledThread.logger.log(Level.SEVERE, null, ex);
                if (this.wasInterrupted) {
                    this.isStopping = true;
                    this.threadPool.removeThread(this);
                    PooledThread.logger.info("PooledThread [" + this.name + "] removed due to interrupt");
                    try {
                        this.thread.interrupt();
                    }
                    catch (Exception e) {
                        final String msg = "Error interrupting PooledThead[" + this.name + "]";
                        PooledThread.logger.log(Level.SEVERE, msg, e);
                    }
                    return;
                }
            }
        }
        this.lastUsedTime = System.currentTimeMillis();
        ++this.totalWorkCount;
        this.totalWorkExecutionTime = this.totalWorkExecutionTime + this.lastUsedTime - startTime;
        this.work = null;
        this.threadPool.returnThread(this);
    }
    
    public void interrupt() {
        this.wasInterrupted = true;
        try {
            this.thread.interrupt();
        }
        catch (SecurityException ex) {
            this.wasInterrupted = false;
            throw ex;
        }
    }
    
    public boolean isStopped() {
        return this.isStopped;
    }
    
    protected void stop() {
        this.isStopping = true;
        synchronized (this.threadMonitor) {
            this.assignWork(null);
            try {
                this.threadMonitor.wait(10000L);
            }
            catch (InterruptedException ex) {}
        }
        this.thread = null;
        this.threadPool.removeThread(this);
    }
    
    public String getName() {
        return this.name;
    }
    
    public Work getWork() {
        return this.work;
    }
    
    public int getTotalWorkCount() {
        return this.totalWorkCount;
    }
    
    public long getTotalWorkExecutionTime() {
        return this.totalWorkExecutionTime;
    }
    
    public long getLastUsedTime() {
        return this.lastUsedTime;
    }
    
    static {
        logger = Logger.getLogger(PooledThread.class.getName());
    }
}
