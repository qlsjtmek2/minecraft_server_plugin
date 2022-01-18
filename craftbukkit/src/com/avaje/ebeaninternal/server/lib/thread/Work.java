// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.thread;

public class Work
{
    private Runnable runnable;
    private long exitQueueTime;
    private long enterQueueTime;
    private long startTime;
    
    public Work(final Runnable runnable) {
        this.runnable = runnable;
    }
    
    public Runnable getRunnable() {
        return this.runnable;
    }
    
    public long getStartTime() {
        return this.startTime;
    }
    
    public void setStartTime(final long startTime) {
        this.startTime = startTime;
    }
    
    public long getEnterQueueTime() {
        return this.enterQueueTime;
    }
    
    public void setEnterQueueTime(final long enterQueueTime) {
        this.enterQueueTime = enterQueueTime;
    }
    
    public long getExitQueueTime() {
        return this.exitQueueTime;
    }
    
    public void setExitQueueTime(final long exitQueueTime) {
        this.exitQueueTime = exitQueueTime;
    }
    
    public String toString() {
        return this.getDescription();
    }
    
    public String getDescription() {
        final StringBuffer sb = new StringBuffer();
        sb.append("Work[");
        if (this.runnable != null) {
            sb.append(this.runnable.toString());
        }
        sb.append("]");
        return sb.toString();
    }
}
