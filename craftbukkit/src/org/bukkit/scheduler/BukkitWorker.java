// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.scheduler;

import org.bukkit.plugin.Plugin;

public interface BukkitWorker
{
    int getTaskId();
    
    Plugin getOwner();
    
    Thread getThread();
}
