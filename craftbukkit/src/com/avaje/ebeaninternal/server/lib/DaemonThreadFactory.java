// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ThreadFactory;

public class DaemonThreadFactory implements ThreadFactory
{
    private static final AtomicInteger poolNumber;
    private final ThreadGroup group;
    private final AtomicInteger threadNumber;
    private final String namePrefix;
    
    public DaemonThreadFactory(final String namePrefix) {
        this.threadNumber = new AtomicInteger(1);
        final SecurityManager s = System.getSecurityManager();
        this.group = ((s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup());
        this.namePrefix = ((namePrefix != null) ? namePrefix : ("pool-" + DaemonThreadFactory.poolNumber.getAndIncrement() + "-thread-"));
    }
    
    public Thread newThread(final Runnable r) {
        final Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
        t.setDaemon(true);
        if (t.getPriority() != 5) {
            t.setPriority(5);
        }
        return t;
    }
    
    static {
        poolNumber = new AtomicInteger(1);
    }
}
