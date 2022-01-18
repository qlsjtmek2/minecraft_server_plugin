// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.thread;

import java.util.Iterator;
import com.avaje.ebeaninternal.server.lib.BackgroundThread;
import com.avaje.ebean.config.GlobalProperties;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadPoolManager implements Runnable
{
    private static int debugLevel;
    private boolean isShuttingDown;
    private ConcurrentHashMap<String, ThreadPool> threadPoolCache;
    private long defaultIdleTime;
    
    private ThreadPoolManager() {
        this.isShuttingDown = false;
        this.threadPoolCache = new ConcurrentHashMap<String, ThreadPool>();
        this.initialise();
    }
    
    private void initialise() {
        ThreadPoolManager.debugLevel = GlobalProperties.getInt("threadpool.debugLevel", 0);
        this.defaultIdleTime = 1000 * GlobalProperties.getInt("threadpool.idletime", 60);
        final int freqIsSecs = GlobalProperties.getInt("threadpool.sleeptime", 30);
        BackgroundThread.add(freqIsSecs, this);
    }
    
    public static void setDebugLevel(final int level) {
        ThreadPoolManager.debugLevel = level;
    }
    
    public static int getDebugLevel() {
        return ThreadPoolManager.debugLevel;
    }
    
    public void run() {
        if (!this.isShuttingDown) {
            this.maintainPoolSize();
        }
    }
    
    public static ThreadPool getThreadPool(final String poolName) {
        return Single.me.getPool(poolName);
    }
    
    private ThreadPool getPool(final String poolName) {
        synchronized (this) {
            ThreadPool threadPool = this.threadPoolCache.get(poolName);
            if (threadPool == null) {
                threadPool = this.createThreadPool(poolName);
                this.threadPoolCache.put(poolName, threadPool);
            }
            return threadPool;
        }
    }
    
    public static Iterator<ThreadPool> pools() {
        return Single.me.threadPoolCache.values().iterator();
    }
    
    private void maintainPoolSize() {
        if (this.isShuttingDown) {
            return;
        }
        synchronized (this) {
            final Iterator<ThreadPool> e = pools();
            while (e.hasNext()) {
                final ThreadPool pool = e.next();
                pool.maintainPoolSize();
            }
        }
    }
    
    public static void shutdown() {
        Single.me.shutdownPools();
    }
    
    private void shutdownPools() {
        synchronized (this) {
            this.isShuttingDown = true;
            final Iterator<ThreadPool> i = pools();
            while (i.hasNext()) {
                final ThreadPool pool = i.next();
                pool.shutdown();
            }
        }
    }
    
    private ThreadPool createThreadPool(final String poolName) {
        final int min = GlobalProperties.getInt("threadpool." + poolName + ".min", 0);
        final int max = GlobalProperties.getInt("threadpool." + poolName + ".max", 100);
        long idle = 1000 * GlobalProperties.getInt("threadpool." + poolName + ".idletime", -1);
        if (idle < 0L) {
            idle = this.defaultIdleTime;
        }
        final boolean isDaemon = true;
        Integer priority = null;
        final String threadPriority = GlobalProperties.get("threadpool." + poolName + ".priority", null);
        if (threadPriority != null) {
            priority = new Integer(threadPriority);
        }
        final ThreadPool newPool = new ThreadPool(poolName, isDaemon, priority);
        newPool.setMaxSize(max);
        newPool.setMinSize(min);
        newPool.setMaxIdleTime(idle);
        return newPool;
    }
    
    static {
        ThreadPoolManager.debugLevel = 0;
    }
    
    private static final class Single
    {
        private static final ThreadPoolManager me;
        
        static {
            me = new ThreadPoolManager(null);
        }
    }
}
