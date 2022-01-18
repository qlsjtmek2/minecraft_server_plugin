// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.plugin;

import org.bukkit.event.EventException;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

public interface EventExecutor
{
    void execute(final Listener p0, final Event p1) throws EventException;
}
