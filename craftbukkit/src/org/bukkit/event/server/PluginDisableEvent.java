// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.server;

import org.bukkit.plugin.Plugin;
import org.bukkit.event.HandlerList;

public class PluginDisableEvent extends PluginEvent
{
    private static final HandlerList handlers;
    
    public PluginDisableEvent(final Plugin plugin) {
        super(plugin);
    }
    
    public HandlerList getHandlers() {
        return PluginDisableEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PluginDisableEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
