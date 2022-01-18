// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.traffic;

import java.util.concurrent.ScheduledExecutorService;
import io.netty.channel.ChannelHandlerContext;

public class ChannelTrafficShapingHandler extends AbstractTrafficShapingHandler
{
    public ChannelTrafficShapingHandler(final long writeLimit, final long readLimit, final long checkInterval) {
        super(writeLimit, readLimit, checkInterval);
    }
    
    public ChannelTrafficShapingHandler(final long writeLimit, final long readLimit) {
        super(writeLimit, readLimit);
    }
    
    public ChannelTrafficShapingHandler(final long checkInterval) {
        super(checkInterval);
    }
    
    @Override
    public void beforeAdd(final ChannelHandlerContext ctx) throws Exception {
        final TrafficCounter trafficCounter = new TrafficCounter(this, ctx.executor(), "ChannelTC" + ctx.channel().id(), this.checkInterval);
        this.setTrafficCounter(trafficCounter);
        trafficCounter.start();
    }
    
    @Override
    public void afterRemove(final ChannelHandlerContext ctx) throws Exception {
        super.afterRemove(ctx);
        if (this.trafficCounter != null) {
            this.trafficCounter.stop();
        }
    }
}
