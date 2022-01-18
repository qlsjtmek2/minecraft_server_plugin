// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.scheduler;

import org.bukkit.plugin.Plugin;

public interface BukkitTask
{
    int getTaskId();
    
    Plugin getOwner();
    
    boolean isSync();
    
    void cancel();
}
