// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.traffic;

import io.netty.buffer.Buf;
import io.netty.channel.ChannelPromise;
import io.netty.util.Attribute;
import java.util.concurrent.TimeUnit;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import io.netty.channel.ChannelOutboundByteHandler;
import io.netty.channel.ChannelInboundByteHandler;
import io.netty.channel.ChannelDuplexHandler;

public abstract class AbstractTrafficShapingHandler extends ChannelDuplexHandler implements ChannelInboundByteHandler, ChannelOutboundByteHandler
{
    public static final long DEFAULT_CHECK_INTERVAL = 1000L;
    private static final long MINIMAL_WAIT = 10L;
    protected TrafficCounter trafficCounter;
    private long writeLimit;
    private long readLimit;
    protected long checkInterval;
    private static final AttributeKey<Boolean> READ_SUSPENDED;
    private static final AttributeKey<Runnable> REOPEN_TASK;
    private static final AttributeKey<Runnable> BUFFER_UPDATE_TASK;
    
    void setTrafficCounter(final TrafficCounter newTrafficCounter) {
        this.trafficCounter = newTrafficCounter;
    }
    
    protected AbstractTrafficShapingHandler(final long writeLimit, final long readLimit, final long checkInterval) {
        this.checkInterval = 1000L;
        this.writeLimit = writeLimit;
        this.readLimit = readLimit;
        this.checkInterval = checkInterval;
    }
    
    protected AbstractTrafficShapingHandler(final long writeLimit, final long readLimit) {
        this(writeLimit, readLimit, 1000L);
    }
    
    protected AbstractTrafficShapingHandler() {
        this(0L, 0L, 1000L);
    }
    
    protected AbstractTrafficShapingHandler(final long checkInterval) {
        this(0L, 0L, checkInterval);
    }
    
    public void configure(final long newWriteLimit, final long newReadLimit, final long newCheckInterval) {
        this.configure(newWriteLimit, newReadLimit);
        this.configure(newCheckInterval);
    }
    
    public void configure(final long newWriteLimit, final long newReadLimit) {
        this.writeLimit = newWriteLimit;
        this.readLimit = newReadLimit;
        if (this.trafficCounter != null) {
            this.trafficCounter.resetAccounting(System.currentTimeMillis() + 1L);
        }
    }
    
    public void configure(final long newCheckInterval) {
        this.checkInterval = newCheckInterval;
        if (this.trafficCounter != null) {
            this.trafficCounter.configure(this.checkInterval);
        }
    }
    
    protected void doAccounting(final TrafficCounter counter) {
    }
    
    private static long getTimeToWait(final long limit, final long bytes, final long lastTime, final long curtime) {
        final long interval = curtime - lastTime;
        if (interval == 0L) {
            return 0L;
        }
        return (bytes * 1000L / limit - interval / 10L) * 10L;
    }
    
    @Override
    public ByteBuf newInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return ctx.nextInboundByteBuffer();
    }
    
    @Override
    public void discardInboundReadBytes(final ChannelHandlerContext ctx) throws Exception {
    }
    
    @Override
    public void freeInboundBuffer(final ChannelHandlerContext ctx) throws Exception {
    }
    
    @Override
    public ByteBuf newOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
        return ctx.nextOutboundByteBuffer();
    }
    
    @Override
    public void discardOutboundReadBytes(final ChannelHandlerContext ctx) throws Exception {
    }
    
    @Override
    public void freeOutboundBuffer(final ChannelHandlerContext ctx) throws Exception {
    }
    
    @Override
    public void inboundBufferUpdated(final ChannelHandlerContext ctx) throws Exception {
        final ByteBuf buf = ctx.inboundByteBuffer();
        final long curtime = System.currentTimeMillis();
        final long size = buf.readableBytes();
        if (this.trafficCounter != null) {
            this.trafficCounter.bytesRecvFlowControl(size);
            if (this.readLimit == 0L) {
                ctx.fireInboundBufferUpdated();
                return;
            }
            final long wait = getTimeToWait(this.readLimit, this.trafficCounter.currentReadBytes(), this.trafficCounter.lastTime(), curtime);
            if (wait >= 10L) {
                if (ctx.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).get()) {
                    final Attribute<Runnable> attr = ctx.attr(AbstractTrafficShapingHandler.BUFFER_UPDATE_TASK);
                    Runnable bufferUpdateTask = attr.get();
                    if (bufferUpdateTask == null) {
                        bufferUpdateTask = new Runnable() {
                            @Override
                            public void run() {
                                ctx.fireInboundBufferUpdated();
                            }
                        };
                        attr.set(bufferUpdateTask);
                    }
                    ctx.executor().schedule(bufferUpdateTask, wait, TimeUnit.MILLISECONDS);
                    return;
                }
                ctx.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).set(true);
                final Attribute<Runnable> attr = ctx.attr(AbstractTrafficShapingHandler.REOPEN_TASK);
                Runnable reopenTask = attr.get();
                if (reopenTask == null) {
                    reopenTask = new ReopenReadTimerTask(ctx);
                    attr.set(reopenTask);
                }
                ctx.executor().schedule(reopenTask, wait, TimeUnit.MILLISECONDS);
            }
        }
        ctx.fireInboundBufferUpdated();
    }
    
    @Override
    public void read(final ChannelHandlerContext ctx) {
        if (!ctx.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).get()) {
            ctx.read();
        }
    }
    
    @Override
    public void flush(final ChannelHandlerContext ctx, final ChannelPromise promise) throws Exception {
        final long curtime = System.currentTimeMillis();
        final long size = ctx.outboundByteBuffer().readableBytes();
        if (this.trafficCounter != null) {
            this.trafficCounter.bytesWriteFlowControl(size);
            if (this.writeLimit == 0L) {
                ctx.flush(promise);
                return;
            }
            final long wait = getTimeToWait(this.writeLimit, this.trafficCounter.currentWrittenBytes(), this.trafficCounter.lastTime(), curtime);
            if (wait >= 10L) {
                ctx.executor().schedule((Runnable)new Runnable() {
                    @Override
                    public void run() {
                        ctx.flush(promise);
                    }
                }, wait, TimeUnit.MILLISECONDS);
                return;
            }
        }
        ctx.flush(promise);
    }
    
    public TrafficCounter trafficCounter() {
        return this.trafficCounter;
    }
    
    @Override
    public void beforeRemove(final ChannelHandlerContext ctx) {
        if (this.trafficCounter != null) {
            this.trafficCounter.stop();
        }
    }
    
    @Override
    public String toString() {
        return "TrafficShaping with Write Limit: " + this.writeLimit + " Read Limit: " + this.readLimit + " and Counter: " + ((this.trafficCounter != null) ? this.trafficCounter.toString() : "none");
    }
    
    static {
        READ_SUSPENDED = new AttributeKey<Boolean>("readSuspended");
        REOPEN_TASK = new AttributeKey<Runnable>("reopenTask");
        BUFFER_UPDATE_TASK = new AttributeKey<Runnable>("bufferUpdateTask");
    }
    
    private static final class ReopenReadTimerTask implements Runnable
    {
        final ChannelHandlerContext ctx;
        
        ReopenReadTimerTask(final ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }
        
        @Override
        public void run() {
            this.ctx.attr(AbstractTrafficShapingHandler.READ_SUSPENDED).set(false);
            this.ctx.read();
        }
    }
}
