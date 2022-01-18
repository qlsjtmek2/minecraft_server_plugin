// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.scheduler;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import org.bukkit.plugin.Plugin;

public interface BukkitScheduler
{
    int scheduleSyncDelayedTask(final Plugin p0, final Runnable p1, final long p2);
    
    int scheduleSyncDelayedTask(final Plugin p0, final Runnable p1);
    
    int scheduleSyncRepeatingTask(final Plugin p0, final Runnable p1, final long p2, final long p3);
    
    @Deprecated
    int scheduleAsyncDelayedTask(final Plugin p0, final Runnable p1, final long p2);
    
    @Deprecated
    int scheduleAsyncDelayedTask(final Plugin p0, final Runnable p1);
    
    @Deprecated
    int scheduleAsyncRepeatingTask(final Plugin p0, final Runnable p1, final long p2, final long p3);
    
     <T> Future<T> callSyncMethod(final Plugin p0, final Callable<T> p1);
    
    void cancelTask(final int p0);
    
    void cancelTasks(final Plugin p0);
    
    void cancelAllTasks();
    
    boolean isCurrentlyRunning(final int p0);
    
    boolean isQueued(final int p0);
    
    List<BukkitWorker> getActiveWorkers();
    
    List<BukkitTask> getPendingTasks();
    
    BukkitTask runTask(final Plugin p0, final Runnable p1) throws IllegalArgumentException;
    
    BukkitTask runTaskAsynchronously(final Plugin p0, final Runnable p1) throws IllegalArgumentException;
    
    BukkitTask runTaskLater(final Plugin p0, final Runnable p1, final long p2) throws IllegalArgumentException;
    
    BukkitTask runTaskLaterAsynchronously(final Plugin p0, final Runnable p1, final long p2) throws IllegalArgumentException;
    
    BukkitTask runTaskTimer(final Plugin p0, final Runnable p1, final long p2, final long p3) throws IllegalArgumentException;
    
    BukkitTask runTaskTimerAsynchronously(final Plugin p0, final Runnable p1, final long p2, final long p3) throws IllegalArgumentException;
}
