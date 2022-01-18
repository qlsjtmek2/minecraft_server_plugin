// 
// Decompiled by Procyon v0.5.30
// 

package io.netty.handler.traffic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicLong;

public class TrafficCounter
{
    private final AtomicLong currentWrittenBytes;
    private final AtomicLong currentReadBytes;
    private final AtomicLong cumulativeWrittenBytes;
    private final AtomicLong cumulativeReadBytes;
    private long lastCumulativeTime;
    private long lastWriteThroughput;
    private long lastReadThroughput;
    private final AtomicLong lastTime;
    private long lastWrittenBytes;
    private long lastReadBytes;
    final AtomicLong checkInterval;
    final String name;
    private final AbstractTrafficShapingHandler trafficShapingHandler;
    private final ScheduledExecutorService executor;
    private Runnable monitor;
    private volatile ScheduledFuture<?> scheduledFuture;
    final AtomicBoolean monitorActive;
    
    public void start() {
        synchronized (this.lastTime) {
            if (this.monitorActive.get()) {
                return;
            }
            this.lastTime.set(System.currentTimeMillis());
            if (this.checkInterval.get() > 0L) {
                this.monitorActive.set(true);
                this.monitor = new TrafficMonitoringTask(this.trafficShapingHandler, this);
                this.scheduledFuture = this.executor.schedule(this.monitor, this.checkInterval.get(), TimeUnit.MILLISECONDS);
            }
        }
    }
    
    public void stop() {
        synchronized (this.lastTime) {
            if (!this.monitorActive.get()) {
                return;
            }
            this.monitorActive.set(false);
            this.resetAccounting(System.currentTimeMillis());
            if (this.trafficShapingHandler != null) {
                this.trafficShapingHandler.doAccounting(this);
            }
            if (this.scheduledFuture != null) {
                this.scheduledFuture.cancel(true);
            }
        }
    }
    
    void resetAccounting(final long newLastTime) {
        synchronized (this.lastTime) {
            final long interval = newLastTime - this.lastTime.getAndSet(newLastTime);
            if (interval == 0L) {
                return;
            }
            this.lastReadBytes = this.currentReadBytes.getAndSet(0L);
            this.lastWrittenBytes = this.currentWrittenBytes.getAndSet(0L);
            this.lastReadThroughput = this.lastReadBytes / interval * 1000L;
            this.lastWriteThroughput = this.lastWrittenBytes / interval * 1000L;
        }
    }
    
    public TrafficCounter(final AbstractTrafficShapingHandler trafficShapingHandler, final ScheduledExecutorService executor, final String name, final long checkInterval) {
        this.currentWrittenBytes = new AtomicLong();
        this.currentReadBytes = new AtomicLong();
        this.cumulativeWrittenBytes = new AtomicLong();
        this.cumulativeReadBytes = new AtomicLong();
        this.lastTime = new AtomicLong();
        this.checkInterval = new AtomicLong(1000L);
        this.monitorActive = new AtomicBoolean();
        this.trafficShapingHandler = trafficShapingHandler;
        this.executor = executor;
        this.name = name;
        this.lastCumulativeTime = System.currentTimeMillis();
        this.configure(checkInterval);
    }
    
    public void configure(final long newcheckInterval) {
        final long newInterval = newcheckInterval / 10L * 10L;
        if (this.checkInterval.get() != newInterval) {
            this.checkInterval.set(newInterval);
            if (newInterval <= 0L) {
                this.stop();
                this.lastTime.set(System.currentTimeMillis());
            }
            else {
                this.start();
            }
        }
    }
    
    void bytesRecvFlowControl(final long recv) {
        this.currentReadBytes.addAndGet(recv);
        this.cumulativeReadBytes.addAndGet(recv);
    }
    
    void bytesWriteFlowControl(final long write) {
        this.currentWrittenBytes.addAndGet(write);
        this.cumulativeWrittenBytes.addAndGet(write);
    }
    
    public long checkInterval() {
        return this.checkInterval.get();
    }
    
    public long lastReadThroughput() {
        return this.lastReadThroughput;
    }
    
    public long lastWriteThroughput() {
        return this.lastWriteThroughput;
    }
    
    public long lastReadBytes() {
        return this.lastReadBytes;
    }
    
    public long lastWrittenBytes() {
        return this.lastWrittenBytes;
    }
    
    public long currentReadBytes() {
        return this.currentReadBytes.get();
    }
    
    public long currentWrittenBytes() {
        return this.currentWrittenBytes.get();
    }
    
    public long lastTime() {
        return this.lastTime.get();
    }
    
    public long cumulativeWrittenBytes() {
        return this.cumulativeWrittenBytes.get();
    }
    
    public long cumulativeReadBytes() {
        return this.cumulativeReadBytes.get();
    }
    
    public long lastCumulativeTime() {
        return this.lastCumulativeTime;
    }
    
    public void resetCumulativeTime() {
        this.lastCumulativeTime = System.currentTimeMillis();
        this.cumulativeReadBytes.set(0L);
        this.cumulativeWrittenBytes.set(0L);
    }
    
    public String name() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return "Monitor " + this.name + " Current Speed Read: " + (this.lastReadThroughput >> 10) + " KB/s, Write: " + (this.lastWriteThroughput >> 10) + " KB/s Current Read: " + (this.currentReadBytes.get() >> 10) + " KB Current Write: " + (this.currentWrittenBytes.get() >> 10) + " KB";
    }
    
    private static class TrafficMonitoringTask implements Runnable
    {
        private final AbstractTrafficShapingHandler trafficShapingHandler1;
        private final TrafficCounter counter;
        
        protected TrafficMonitoringTask(final AbstractTrafficShapingHandler trafficShapingHandler, final TrafficCounter counter) {
            this.trafficShapingHandler1 = trafficShapingHandler;
            this.counter = counter;
        }
        
        @Override
        public void run() {
            if (!this.counter.monitorActive.get()) {
                return;
            }
            final long endTime = System.currentTimeMillis();
            this.counter.resetAccounting(endTime);
            if (this.trafficShapingHandler1 != null) {
                this.trafficShapingHandler1.doAccounting(this.counter);
            }
            this.counter.scheduledFuture = this.counter.executor.schedule(this, this.counter.checkInterval.get(), TimeUnit.MILLISECONDS);
        }
    }
}
