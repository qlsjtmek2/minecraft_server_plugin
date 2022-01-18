// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.server;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.event.HandlerList;

public class ServiceRegisterEvent extends ServiceEvent
{
    private static final HandlerList handlers;
    
    public ServiceRegisterEvent(final RegisteredServiceProvider<?> registeredProvider) {
        super(registeredProvider);
    }
    
    public HandlerList getHandlers() {
        return ServiceRegisterEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ServiceRegisterEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
