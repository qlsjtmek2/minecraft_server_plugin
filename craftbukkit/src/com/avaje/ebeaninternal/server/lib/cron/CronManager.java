// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.cron;

import java.util.logging.Level;
import com.avaje.ebeaninternal.server.lib.ShutdownManager;
import java.util.Iterator;
import java.util.Enumeration;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import com.avaje.ebean.config.GlobalProperties;
import com.avaje.ebeaninternal.server.lib.thread.ThreadPoolManager;
import java.util.Vector;
import com.avaje.ebeaninternal.server.lib.thread.ThreadPool;
import java.util.logging.Logger;

public final class CronManager
{
    private static final Logger logger;
    private boolean running;
    private ThreadPool threadPool;
    private Vector<CronRunnable> runList;
    private Thread backgroundThread;
    private boolean isDowntime;
    private static final long SMALL_DELAY = 10L;
    
    private CronManager() {
        this.running = true;
        this.isDowntime = false;
        this.runList = new Vector<CronRunnable>();
        this.threadPool = ThreadPoolManager.getThreadPool("CronManager");
        (this.backgroundThread = new Thread(new Runner(), "CronManager Daemon")).setDaemon(true);
        this.backgroundThread.start();
    }
    
    private void init() {
        final CronRunnable sr = new CronRunnable("* * * * *", new HelloWorld());
        sr.setEnabled(false);
        add(sr);
        final CronRunnable dt = new CronRunnable("25 23 * * *", new Downtime(this));
        dt.setEnabled(false);
        final String downtimeSchedule = GlobalProperties.get("system.downtime.schedule", null);
        if (downtimeSchedule != null) {
            dt.setSchedule(downtimeSchedule);
            dt.setEnabled(true);
        }
        add(dt);
    }
    
    public static boolean isDowntime() {
        return getInstance().isDowntime;
    }
    
    protected void setDowntime(final boolean isDowntime) {
        this.isDowntime = isDowntime;
        if (isDowntime) {
            final String duration = GlobalProperties.get("system.downtime.duration", null);
            CronManager.logger.warning("System downtime has started for [" + duration + "] seconds");
        }
        else {
            CronManager.logger.warning("System downtime has finished.");
        }
    }
    
    public static void setRunning(final boolean running) {
        CronManagerHolder.me.running = running;
    }
    
    private void runScheduledJobs() {
        if (!this.running) {
            return;
        }
        final Date nowDate = new Date((System.currentTimeMillis() + 5000L) / 60000L * 60000L);
        final GregorianCalendar thisMinute = new GregorianCalendar();
        thisMinute.setTime(nowDate);
        final Enumeration<CronRunnable> en = this.runList.elements();
        while (en.hasMoreElements()) {
            final CronRunnable sr = en.nextElement();
            if (sr.isScheduledToRunNow(thisMinute)) {
                this.threadPool.assign(sr.getRunnable(), true);
            }
        }
    }
    
    private static CronManager getInstance() {
        return CronManagerHolder.me;
    }
    
    public static void add(final String schedule, final Runnable runnable) {
        final CronRunnable sr = new CronRunnable(schedule, runnable);
        add(sr);
    }
    
    public static void add(final CronRunnable runnable) {
        getInstance().runList.add(runnable);
    }
    
    public static Iterator<CronRunnable> iterator() {
        return getInstance().runList.iterator();
    }
    
    static {
        logger = Logger.getLogger(CronManager.class.getName());
    }
    
    private static class CronManagerHolder
    {
        private static CronManager me;
        
        static {
            CronManagerHolder.me = new CronManager(null);
        }
    }
    
    private class Runner implements Runnable
    {
        public void run() {
            CronManager.this.init();
        Label_0007_Outer:
            while (true) {
                while (true) {
                    try {
                        while (true) {
                            final long nextMinute = System.currentTimeMillis() / 60000L * 60000L + 60000L;
                            final long now = System.currentTimeMillis();
                            final long nextSleepTime = nextMinute - now + 10L;
                            if (nextSleepTime > 0L) {
                                Thread.sleep(nextSleepTime);
                            }
                            final long additionalDelay = nextMinute - System.currentTimeMillis();
                            if (additionalDelay > 0L) {
                                Thread.sleep(additionalDelay + 20L);
                            }
                            final boolean stopping = ShutdownManager.isStopping();
                            if (!stopping) {
                                CronManager.this.runScheduledJobs();
                            }
                            Thread.sleep(5000L);
                        }
                    }
                    catch (InterruptedException e) {
                        CronManager.logger.log(Level.SEVERE, "", e);
                        continue Label_0007_Outer;
                    }
                    continue;
                }
            }
        }
    }
}
