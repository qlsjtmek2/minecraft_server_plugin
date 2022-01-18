// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebean;

import java.util.concurrent.TimeUnit;

public interface BackgroundExecutor
{
    void execute(final Runnable p0);
    
    void executePeriodically(final Runnable p0, final long p1, final TimeUnit p2);
}
