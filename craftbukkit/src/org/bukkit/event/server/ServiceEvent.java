// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.server;

import org.bukkit.plugin.RegisteredServiceProvider;

public abstract class ServiceEvent extends ServerEvent
{
    private final RegisteredServiceProvider<?> provider;
    
    public ServiceEvent(final RegisteredServiceProvider<?> provider) {
        this.provider = provider;
    }
    
    public RegisteredServiceProvider<?> getProvider() {
        return this.provider;
    }
}
