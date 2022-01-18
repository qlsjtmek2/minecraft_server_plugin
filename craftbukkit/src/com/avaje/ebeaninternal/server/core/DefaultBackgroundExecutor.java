// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.core;

import java.util.concurrent.TimeUnit;
import com.avaje.ebeaninternal.server.lib.DaemonScheduleThreadPool;
import com.avaje.ebeaninternal.server.lib.DaemonThreadPool;
import com.avaje.ebean.BackgroundExecutor;

public class DefaultBackgroundExecutor implements BackgroundExecutor
{
    private final DaemonThreadPool pool;
    private final DaemonScheduleThreadPool schedulePool;
    
    public DefaultBackgroundExecutor(final int mainPoolSize, final int schedulePoolSize, final long keepAliveSecs, final int shutdownWaitSeconds, final String namePrefix) {
        this.pool = new DaemonThreadPool(mainPoolSize, keepAliveSecs, shutdownWaitSeconds, namePrefix);
        this.schedulePool = new DaemonScheduleThreadPool(schedulePoolSize, shutdownWaitSeconds, namePrefix + "-periodic-");
    }
    
    public void execute(final Runnable r) {
        this.pool.execute(r);
    }
    
    public void executePeriodically(final Runnable r, final long delay, final TimeUnit unit) {
        this.schedulePool.scheduleWithFixedDelay(r, delay, delay, unit);
    }
}
