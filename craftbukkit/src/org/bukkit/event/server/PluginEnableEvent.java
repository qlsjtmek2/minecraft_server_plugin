// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.server;

import org.bukkit.plugin.Plugin;
import org.bukkit.event.HandlerList;

public class PluginEnableEvent extends PluginEvent
{
    private static final HandlerList handlers;
    
    public PluginEnableEvent(final Plugin plugin) {
        super(plugin);
    }
    
    public HandlerList getHandlers() {
        return PluginEnableEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PluginEnableEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
