// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.cron;

import java.util.logging.Level;
import com.avaje.ebean.config.GlobalProperties;
import java.util.logging.Logger;

public class Downtime implements Runnable
{
    private static final Logger logger;
    private CronManager manager;
    
    public Downtime(final CronManager manager) {
        this.manager = manager;
    }
    
    public void run() {
        final String downtime = GlobalProperties.get("system.downtime.duration", null);
        if (downtime == null) {
            Downtime.logger.info("system.downtime not set");
        }
        else {
            final int downTimeSecs = Integer.parseInt(downtime);
            final int offsetSecs = 2;
            final long offsetTime = System.currentTimeMillis() + offsetSecs * 1000;
            final long endTime = System.currentTimeMillis() + downTimeSecs * 1000;
            try {
                boolean isFinished = false;
                if (offsetSecs > 0) {
                    while (!isFinished) {
                        Thread.sleep(500L);
                        if (System.currentTimeMillis() >= offsetTime) {
                            isFinished = true;
                        }
                    }
                }
                this.manager.setDowntime(true);
                for (isFinished = false; !isFinished; isFinished = true) {
                    Thread.sleep(500L);
                    if (System.currentTimeMillis() >= endTime) {}
                }
            }
            catch (InterruptedException ex) {
                Downtime.logger.log(Level.SEVERE, "", ex);
            }
            this.manager.setDowntime(false);
        }
    }
    
    public String toString() {
        return "System Downtime";
    }
    
    static {
        logger = Logger.getLogger(Downtime.class.getName());
    }
}
