// 
// Decompiled by Procyon v0.5.30
// 

package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.util.concurrent.TimeUnit;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.Beta;

@Beta
@GwtCompatible(emulated = true)
public final class Stopwatch
{
    private final Ticker ticker;
    private boolean isRunning;
    private long elapsedNanos;
    private long startTick;
    
    public Stopwatch() {
        this(Ticker.systemTicker());
    }
    
    public Stopwatch(final Ticker ticker) {
        this.ticker = Preconditions.checkNotNull(ticker);
    }
    
    public boolean isRunning() {
        return this.isRunning;
    }
    
    public Stopwatch start() {
        Preconditions.checkState(!this.isRunning);
        this.isRunning = true;
        this.startTick = this.ticker.read();
        return this;
    }
    
    public Stopwatch stop() {
        final long tick = this.ticker.read();
        Preconditions.checkState(this.isRunning);
        this.isRunning = false;
        this.elapsedNanos += tick - this.startTick;
        return this;
    }
    
    public Stopwatch reset() {
        this.elapsedNanos = 0L;
        this.isRunning = false;
        return this;
    }
    
    private long elapsedNanos() {
        return this.isRunning ? (this.ticker.read() - this.startTick + this.elapsedNanos) : this.elapsedNanos;
    }
    
    public long elapsedTime(final TimeUnit desiredUnit) {
        return desiredUnit.convert(this.elapsedNanos(), TimeUnit.NANOSECONDS);
    }
    
    public long elapsedMillis() {
        return this.elapsedTime(TimeUnit.MILLISECONDS);
    }
    
    @GwtIncompatible("String.format()")
    public String toString() {
        return this.toString(4);
    }
    
    @GwtIncompatible("String.format()")
    public String toString(final int significantDigits) {
        final long nanos = this.elapsedNanos();
        final TimeUnit unit = chooseUnit(nanos);
        final double value = nanos / TimeUnit.NANOSECONDS.convert(1L, unit);
        return String.format("%." + significantDigits + "g %s", value, abbreviate(unit));
    }
    
    private static TimeUnit chooseUnit(final long nanos) {
        if (TimeUnit.SECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
            return TimeUnit.SECONDS;
        }
        if (TimeUnit.MILLISECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
            return TimeUnit.MILLISECONDS;
        }
        if (TimeUnit.MICROSECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
            return TimeUnit.MICROSECONDS;
        }
        return TimeUnit.NANOSECONDS;
    }
    
    private static String abbreviate(final TimeUnit unit) {
        switch (unit) {
            case NANOSECONDS: {
                return "ns";
            }
            case MICROSECONDS: {
                return "\u03bcs";
            }
            case MILLISECONDS: {
                return "ms";
            }
            case SECONDS: {
                return "s";
            }
            default: {
                throw new AssertionError();
            }
        }
    }
}
