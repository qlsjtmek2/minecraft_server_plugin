// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.vehicle;

import org.bukkit.entity.Vehicle;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class VehicleEntityCollisionEvent extends VehicleCollisionEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final Entity entity;
    private boolean cancelled;
    private boolean cancelledPickup;
    private boolean cancelledCollision;
    
    public VehicleEntityCollisionEvent(final Vehicle vehicle, final Entity entity) {
        super(vehicle);
        this.cancelled = false;
        this.cancelledPickup = false;
        this.cancelledCollision = false;
        this.entity = entity;
    }
    
    public Entity getEntity() {
        return this.entity;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public boolean isPickupCancelled() {
        return this.cancelledPickup;
    }
    
    public void setPickupCancelled(final boolean cancel) {
        this.cancelledPickup = cancel;
    }
    
    public boolean isCollisionCancelled() {
        return this.cancelledCollision;
    }
    
    public void setCollisionCancelled(final boolean cancel) {
        this.cancelledCollision = cancel;
    }
    
    public HandlerList getHandlers() {
        return VehicleEntityCollisionEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return VehicleEntityCollisionEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
