// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib;

public class BackgroundRunnable
{
    Runnable runnable;
    int freqInSecs;
    int runCount;
    long totalRunTime;
    long startTimeTemp;
    long startAfter;
    boolean isActive;
    
    public BackgroundRunnable(final Runnable runnable, final int freqInSecs) {
        this(runnable, freqInSecs, System.currentTimeMillis() + 1000 * (freqInSecs + 10));
    }
    
    public BackgroundRunnable(final Runnable runnable, final int freqInSecs, final long startAfter) {
        this.runCount = 0;
        this.totalRunTime = 0L;
        this.isActive = true;
        this.runnable = runnable;
        this.freqInSecs = freqInSecs;
        this.startAfter = startAfter;
    }
    
    public boolean runNow(final long now) {
        return now > this.startAfter;
    }
    
    public boolean isActive() {
        return this.isActive;
    }
    
    public void setActive(final boolean isActive) {
        this.isActive = isActive;
    }
    
    protected void runStart() {
        this.startTimeTemp = System.currentTimeMillis();
    }
    
    protected void runEnd() {
        ++this.runCount;
        final long exeTime = System.currentTimeMillis() - this.startTimeTemp;
        this.totalRunTime += exeTime;
    }
    
    public int getRunCount() {
        return this.runCount;
    }
    
    public long getAverageRunTime() {
        if (this.runCount == 0) {
            return 0L;
        }
        return this.totalRunTime / this.runCount;
    }
    
    public int getFreqInSecs() {
        return this.freqInSecs;
    }
    
    public void setFreqInSecs(final int freqInSecs) {
        this.freqInSecs = freqInSecs;
    }
    
    public Runnable getRunnable() {
        return this.runnable;
    }
    
    public void setRunnable(final Runnable runnable) {
        this.runnable = runnable;
    }
    
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("[");
        sb.append(this.runnable.getClass().getName());
        sb.append(" freq:").append(this.freqInSecs);
        sb.append(" count:").append(this.getRunCount());
        sb.append(" avgTime:").append(this.getAverageRunTime());
        sb.append("]");
        return sb.toString();
    }
}
