// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.scheduler;

import org.bukkit.craftbukkit.v1_5_R3.SpigotTimings;
import org.bukkit.Bukkit;
import org.spigotmc.CustomTimingsHandler;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

class CraftTask implements BukkitTask, Runnable
{
    private volatile CraftTask next;
    private volatile long period;
    private long nextRun;
    private final Runnable task;
    private final Plugin plugin;
    private final int id;
    CustomTimingsHandler timings;
    
    CraftTask() {
        this(null, null, -1, -1L);
    }
    
    CraftTask(final Runnable task) {
        this(null, task, -1, -1L);
    }
    
    CraftTask(final Plugin plugin, final Runnable task, final int id, final long period) {
        this.next = null;
        this.timings = null;
        this.plugin = plugin;
        this.task = task;
        this.id = id;
        this.period = period;
    }
    
    public final int getTaskId() {
        return this.id;
    }
    
    public final Plugin getOwner() {
        return this.plugin;
    }
    
    public boolean isSync() {
        return true;
    }
    
    public void run() {
        if (!Bukkit.getServer().getPluginManager().useTimings()) {
            this.task.run();
            return;
        }
        if (this.timings == null && this.getOwner() != null && this.isSync()) {
            this.timings = SpigotTimings.getPluginTaskTimings(this, this.period);
        }
        if (this.timings != null) {
            this.timings.startTiming();
        }
        this.task.run();
        if (this.timings != null) {
            this.timings.stopTiming();
        }
    }
    
    long getPeriod() {
        return this.period;
    }
    
    void setPeriod(final long period) {
        this.period = period;
    }
    
    long getNextRun() {
        return this.nextRun;
    }
    
    void setNextRun(final long nextRun) {
        this.nextRun = nextRun;
    }
    
    CraftTask getNext() {
        return this.next;
    }
    
    void setNext(final CraftTask next) {
        this.next = next;
    }
    
    Class<? extends Runnable> getTaskClass() {
        return this.task.getClass();
    }
    
    public void cancel() {
        Bukkit.getScheduler().cancelTask(this.id);
    }
    
    boolean cancel0() {
        this.setPeriod(-2L);
        return true;
    }
}
