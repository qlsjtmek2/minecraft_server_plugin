// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.vehicle;

import org.bukkit.entity.Vehicle;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;

public class VehicleBlockCollisionEvent extends VehicleCollisionEvent
{
    private static final HandlerList handlers;
    private final Block block;
    
    public VehicleBlockCollisionEvent(final Vehicle vehicle, final Block block) {
        super(vehicle);
        this.block = block;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public HandlerList getHandlers() {
        return VehicleBlockCollisionEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return VehicleBlockCollisionEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
