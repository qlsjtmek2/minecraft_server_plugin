// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib;

import java.util.logging.Level;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

public final class BackgroundThread
{
    private static final Logger logger;
    private Vector<BackgroundRunnable> list;
    private final Object monitor;
    private final Thread thread;
    private long sleepTime;
    private long count;
    private long exeTime;
    private boolean stopped;
    private Object threadMonitor;
    
    private BackgroundThread() {
        this.list = new Vector<BackgroundRunnable>();
        this.monitor = new Object();
        this.sleepTime = 1000L;
        this.threadMonitor = new Object();
        (this.thread = new Thread(new Runner(), "EbeanBackgroundThread")).setDaemon(true);
        this.thread.start();
    }
    
    public static void add(final int freqInSecs, final Runnable runnable) {
        add(new BackgroundRunnable(runnable, freqInSecs));
    }
    
    public static void add(final BackgroundRunnable backgroundRunnable) {
        Single.me.addTask(backgroundRunnable);
    }
    
    public static void shutdown() {
        Single.me.stop();
    }
    
    public static Iterator<BackgroundRunnable> runnables() {
        synchronized (Single.me.monitor) {
            return Single.me.list.iterator();
        }
    }
    
    private void addTask(final BackgroundRunnable backgroundRunnable) {
        synchronized (this.monitor) {
            this.list.add(backgroundRunnable);
        }
    }
    
    private void stop() {
        this.stopped = true;
        synchronized (this.threadMonitor) {
            try {
                this.threadMonitor.wait(10000L);
            }
            catch (InterruptedException ex) {}
        }
    }
    
    public String toString() {
        synchronized (this.monitor) {
            final StringBuffer sb = new StringBuffer();
            final Iterator<BackgroundRunnable> it = runnables();
            while (it.hasNext()) {
                final BackgroundRunnable bgr = it.next();
                sb.append(bgr);
            }
            return sb.toString();
        }
    }
    
    static {
        logger = Logger.getLogger(BackgroundThread.class.getName());
    }
    
    private static class Single
    {
        private static BackgroundThread me;
        
        static {
            Single.me = new BackgroundThread(null);
        }
    }
    
    private class Runner implements Runnable
    {
        public void run() {
            if (ShutdownManager.isStopping()) {
                return;
            }
            while (!BackgroundThread.this.stopped) {
                try {
                    long actualSleep = BackgroundThread.this.sleepTime - BackgroundThread.this.exeTime;
                    if (actualSleep < 0L) {
                        actualSleep = BackgroundThread.this.sleepTime;
                    }
                    Thread.sleep(actualSleep);
                    synchronized (BackgroundThread.this.monitor) {
                        this.runJobs();
                    }
                }
                catch (InterruptedException e) {
                    BackgroundThread.logger.log(Level.SEVERE, null, e);
                }
            }
            synchronized (BackgroundThread.this.threadMonitor) {
                BackgroundThread.this.threadMonitor.notifyAll();
            }
        }
        
        private void runJobs() {
            final long startTime = System.currentTimeMillis();
            for (final BackgroundRunnable bgr : BackgroundThread.this.list) {
                if (bgr.isActive()) {
                    final int freqInSecs = bgr.getFreqInSecs();
                    if (BackgroundThread.this.count % freqInSecs != 0L) {
                        continue;
                    }
                    final Runnable runable = bgr.getRunnable();
                    if (!bgr.runNow(startTime)) {
                        continue;
                    }
                    bgr.runStart();
                    if (BackgroundThread.logger.isLoggable(Level.FINER)) {
                        final String msg = BackgroundThread.this.count + " BGRunnable running [" + runable.getClass().getName() + "]";
                        BackgroundThread.logger.finer(msg);
                    }
                    runable.run();
                    bgr.runEnd();
                }
            }
            BackgroundThread.this.exeTime = System.currentTimeMillis() - startTime;
            BackgroundThread.this.count++;
            if (BackgroundThread.this.count == 86400L) {
                BackgroundThread.this.count = 0L;
            }
        }
    }
}
