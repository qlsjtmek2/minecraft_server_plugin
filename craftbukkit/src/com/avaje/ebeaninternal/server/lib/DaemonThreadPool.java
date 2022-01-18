// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib;

import java.util.logging.Level;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import com.avaje.ebeaninternal.api.Monitor;
import java.util.logging.Logger;
import java.util.concurrent.ThreadPoolExecutor;

public final class DaemonThreadPool extends ThreadPoolExecutor
{
    private static final Logger logger;
    private final Monitor monitor;
    private final String namePrefix;
    private int shutdownWaitSeconds;
    
    public DaemonThreadPool(final int coreSize, final long keepAliveSecs, final int shutdownWaitSeconds, final String namePrefix) {
        super(coreSize, coreSize, keepAliveSecs, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new DaemonThreadFactory(namePrefix));
        this.monitor = new Monitor();
        this.shutdownWaitSeconds = shutdownWaitSeconds;
        this.namePrefix = namePrefix;
        Runtime.getRuntime().addShutdownHook(new ShutdownHook());
    }
    
    public void shutdown() {
        synchronized (this.monitor) {
            if (super.isShutdown()) {
                DaemonThreadPool.logger.fine("... DaemonThreadPool[" + this.namePrefix + "] already shut down");
                return;
            }
            try {
                DaemonThreadPool.logger.fine("DaemonThreadPool[" + this.namePrefix + "] shutting down...");
                super.shutdown();
                if (!super.awaitTermination(this.shutdownWaitSeconds, TimeUnit.SECONDS)) {
                    DaemonThreadPool.logger.info("DaemonThreadPool[" + this.namePrefix + "] shut down timeout exceeded. Terminating running threads.");
                    super.shutdownNow();
                }
            }
            catch (Exception e) {
                final String msg = "Error during shutdown of DaemonThreadPool[" + this.namePrefix + "]";
                DaemonThreadPool.logger.log(Level.SEVERE, msg, e);
                e.printStackTrace();
            }
        }
    }
    
    static {
        logger = Logger.getLogger(DaemonThreadPool.class.getName());
    }
    
    private class ShutdownHook extends Thread
    {
        public void run() {
            DaemonThreadPool.this.shutdown();
        }
    }
}
