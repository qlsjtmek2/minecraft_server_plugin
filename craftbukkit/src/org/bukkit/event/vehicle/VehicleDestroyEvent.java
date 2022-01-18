// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.vehicle;

import org.bukkit.entity.Vehicle;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class VehicleDestroyEvent extends VehicleEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final Entity attacker;
    private boolean cancelled;
    
    public VehicleDestroyEvent(final Vehicle vehicle, final Entity attacker) {
        super(vehicle);
        this.attacker = attacker;
    }
    
    public Entity getAttacker() {
        return this.attacker;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public HandlerList getHandlers() {
        return VehicleDestroyEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return VehicleDestroyEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
