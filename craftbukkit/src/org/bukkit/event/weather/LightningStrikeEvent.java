// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.weather;

import org.bukkit.World;
import org.bukkit.entity.LightningStrike;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class LightningStrikeEvent extends WeatherEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean canceled;
    private final LightningStrike bolt;
    
    public LightningStrikeEvent(final World world, final LightningStrike bolt) {
        super(world);
        this.bolt = bolt;
    }
    
    public boolean isCancelled() {
        return this.canceled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.canceled = cancel;
    }
    
    public LightningStrike getLightning() {
        return this.bolt;
    }
    
    public HandlerList getHandlers() {
        return LightningStrikeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return LightningStrikeEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
