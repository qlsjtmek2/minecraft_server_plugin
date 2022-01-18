// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.vehicle;

import org.bukkit.entity.Vehicle;
import org.bukkit.event.HandlerList;

public class VehicleUpdateEvent extends VehicleEvent
{
    private static final HandlerList handlers;
    
    public VehicleUpdateEvent(final Vehicle vehicle) {
        super(vehicle);
    }
    
    public HandlerList getHandlers() {
        return VehicleUpdateEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return VehicleUpdateEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
