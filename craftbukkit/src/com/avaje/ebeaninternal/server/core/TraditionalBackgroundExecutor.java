// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import java.util.concurrent.TimeUnit;
import com.avaje.ebeaninternal.server.lib.DaemonScheduleThreadPool;
import com.avaje.ebeaninternal.server.lib.thread.ThreadPool;
import com.avaje.ebean.BackgroundExecutor;

public class TraditionalBackgroundExecutor implements BackgroundExecutor
{
    private final ThreadPool pool;
    private final DaemonScheduleThreadPool schedulePool;
    
    public TraditionalBackgroundExecutor(final ThreadPool pool, final int schedulePoolSize, final int shutdownWaitSeconds, final String namePrefix) {
        this.pool = pool;
        this.schedulePool = new DaemonScheduleThreadPool(schedulePoolSize, shutdownWaitSeconds, namePrefix + "-periodic-");
    }
    
    public void execute(final Runnable r) {
        this.pool.assign(r, true);
    }
    
    public void executePeriodically(final Runnable r, final long delay, final TimeUnit unit) {
        this.schedulePool.scheduleWithFixedDelay(r, delay, delay, unit);
    }
}
