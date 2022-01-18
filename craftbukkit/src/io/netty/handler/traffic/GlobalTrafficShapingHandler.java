// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.traffic;

import io.netty.util.concurrent.EventExecutor;
import java.util.concurrent.ScheduledExecutorService;
import io.netty.channel.ChannelHandler;

@ChannelHandler.Sharable
public class GlobalTrafficShapingHandler extends AbstractTrafficShapingHandler
{
    void createGlobalTrafficCounter(final ScheduledExecutorService executor) {
        if (executor == null) {
            throw new NullPointerException("executor");
        }
        final TrafficCounter tc = new TrafficCounter(this, executor, "GlobalTC", this.checkInterval);
        this.setTrafficCounter(tc);
        tc.start();
    }
    
    public GlobalTrafficShapingHandler(final ScheduledExecutorService executor, final long writeLimit, final long readLimit, final long checkInterval) {
        super(writeLimit, readLimit, checkInterval);
        this.createGlobalTrafficCounter(executor);
    }
    
    public GlobalTrafficShapingHandler(final ScheduledExecutorService executor, final long writeLimit, final long readLimit) {
        super(writeLimit, readLimit);
        this.createGlobalTrafficCounter(executor);
    }
    
    public GlobalTrafficShapingHandler(final ScheduledExecutorService executor, final long checkInterval) {
        super(checkInterval);
        this.createGlobalTrafficCounter(executor);
    }
    
    public GlobalTrafficShapingHandler(final EventExecutor executor) {
        this.createGlobalTrafficCounter(executor);
    }
    
    public final void release() {
        if (this.trafficCounter != null) {
            this.trafficCounter.stop();
        }
    }
}
