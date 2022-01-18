// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.weather;

import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class ThunderChangeEvent extends WeatherEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean canceled;
    private final boolean to;
    
    public ThunderChangeEvent(final World world, final boolean to) {
        super(world);
        this.to = to;
    }
    
    public boolean isCancelled() {
        return this.canceled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.canceled = cancel;
    }
    
    public boolean toThunderState() {
        return this.to;
    }
    
    public HandlerList getHandlers() {
        return ThunderChangeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ThunderChangeEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
