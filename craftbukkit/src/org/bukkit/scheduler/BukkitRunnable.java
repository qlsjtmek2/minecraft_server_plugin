// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.scheduler;

import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;

public abstract class BukkitRunnable implements Runnable
{
    private int taskId;
    
    public BukkitRunnable() {
        this.taskId = -1;
    }
    
    public synchronized void cancel() throws IllegalStateException {
        Bukkit.getScheduler().cancelTask(this.getTaskId());
    }
    
    public synchronized BukkitTask runTask(final Plugin plugin) throws IllegalArgumentException, IllegalStateException {
        this.checkState();
        return this.setupId(Bukkit.getScheduler().runTask(plugin, this));
    }
    
    public synchronized BukkitTask runTaskAsynchronously(final Plugin plugin) throws IllegalArgumentException, IllegalStateException {
        this.checkState();
        return this.setupId(Bukkit.getScheduler().runTaskAsynchronously(plugin, this));
    }
    
    public synchronized BukkitTask runTaskLater(final Plugin plugin, final long delay) throws IllegalArgumentException, IllegalStateException {
        this.checkState();
        return this.setupId(Bukkit.getScheduler().runTaskLater(plugin, this, delay));
    }
    
    public synchronized BukkitTask runTaskLaterAsynchronously(final Plugin plugin, final long delay) throws IllegalArgumentException, IllegalStateException {
        this.checkState();
        return this.setupId(Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, this, delay));
    }
    
    public synchronized BukkitTask runTaskTimer(final Plugin plugin, final long delay, final long period) throws IllegalArgumentException, IllegalStateException {
        this.checkState();
        return this.setupId(Bukkit.getScheduler().runTaskTimer(plugin, this, delay, period));
    }
    
    public synchronized BukkitTask runTaskTimerAsynchronously(final Plugin plugin, final long delay, final long period) throws IllegalArgumentException, IllegalStateException {
        this.checkState();
        return this.setupId(Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this, delay, period));
    }
    
    public synchronized int getTaskId() throws IllegalStateException {
        final int id = this.taskId;
        if (id == -1) {
            throw new IllegalStateException("Not scheduled yet");
        }
        return id;
    }
    
    private void checkState() {
        if (this.taskId != -1) {
            throw new IllegalStateException("Already scheduled as " + this.taskId);
        }
    }
    
    private BukkitTask setupId(final BukkitTask task) {
        this.taskId = task.getTaskId();
        return task;
    }
}
