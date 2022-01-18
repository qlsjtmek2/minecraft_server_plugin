// 
// Decompiled by Procyon v0.5.30
// 

package com.avaje.ebeaninternal.server.lib.cron;

import java.util.Calendar;

public class CronRunnable
{
    boolean isEnabled;
    CronSchedule schedule;
    Runnable runnable;
    
    public CronRunnable(final String schedule, final Runnable runnable) {
        this(new CronSchedule(schedule), runnable);
    }
    
    public CronRunnable(final CronSchedule schedule, final Runnable runnable) {
        this.isEnabled = true;
        this.schedule = schedule;
        this.runnable = runnable;
    }
    
    public boolean equals(final Object obj) {
        return obj != null && obj instanceof CronRunnable && this.hashCode() == obj.hashCode();
    }
    
    public int hashCode() {
        int hc = CronRunnable.class.getName().hashCode();
        hc += 31 * hc + this.schedule.hashCode();
        hc += 31 * hc + this.runnable.hashCode();
        return hc;
    }
    
    public boolean isScheduledToRunNow(final Calendar thisMinute) {
        return this.isEnabled && this.schedule.isScheduledToRunNow(thisMinute);
    }
    
    public void setSchedule(final String scheduleLine) {
        this.schedule.setSchedule(scheduleLine);
    }
    
    public String getSchedule() {
        return this.schedule.getSchedule();
    }
    
    public Runnable getRunnable() {
        return this.runnable;
    }
    
    public void setRunnable(final Runnable runnable) {
        this.runnable = runnable;
    }
    
    public boolean isEnabled() {
        return this.isEnabled;
    }
    
    public void setEnabled(final boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
    
    public String toString() {
        return "CronRunnable: isEnabled[" + this.isEnabled + "] sch[" + this.schedule.getSchedule() + "] [" + this.runnable.toString() + "]";
    }
}
