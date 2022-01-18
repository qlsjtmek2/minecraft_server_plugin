// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib;

import java.util.logging.Level;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadFactory;
import com.avaje.ebeaninternal.api.Monitor;
import java.util.logging.Logger;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public final class DaemonScheduleThreadPool extends ScheduledThreadPoolExecutor
{
    private static final Logger logger;
    private final Monitor monitor;
    private int shutdownWaitSeconds;
    
    public DaemonScheduleThreadPool(final int coreSize, final int shutdownWaitSeconds, final String namePrefix) {
        super(coreSize, new DaemonThreadFactory(namePrefix));
        this.monitor = new Monitor();
        this.shutdownWaitSeconds = shutdownWaitSeconds;
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
    }
    
    public void shutdown() {
        synchronized (this.monitor) {
            if (super.isShutdown()) {
                DaemonScheduleThreadPool.logger.fine("... DaemonScheduleThreadPool already shut down");
                return;
            }
            try {
                DaemonScheduleThreadPool.logger.fine("DaemonScheduleThreadPool shutting down...");
                super.shutdown();
                if (!super.awaitTermination(this.shutdownWaitSeconds, TimeUnit.SECONDS)) {
                    DaemonScheduleThreadPool.logger.info("ScheduleService shut down timeout exceeded. Terminating running threads.");
                    super.shutdownNow();
                }
            }
            catch (Exception e) {
                final String msg = "Error during shutdown";
                DaemonScheduleThreadPool.logger.log(Level.SEVERE, msg, e);
                e.printStackTrace();
            }
        }
    }
    
    static {
        logger = Logger.getLogger(DaemonScheduleThreadPool.class.getName());
    }
    
    private class ShutdownHook extends Thread
    {
        public void run() {
            DaemonScheduleThreadPool.this.shutdown();
        }
    }
}
