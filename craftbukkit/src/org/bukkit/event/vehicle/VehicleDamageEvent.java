// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.vehicle;

import org.bukkit.entity.Vehicle;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class VehicleDamageEvent extends VehicleEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final Entity attacker;
    private int damage;
    private boolean cancelled;
    
    public VehicleDamageEvent(final Vehicle vehicle, final Entity attacker, final int damage) {
        super(vehicle);
        this.attacker = attacker;
        this.damage = damage;
    }
    
    public Entity getAttacker() {
        return this.attacker;
    }
    
    public int getDamage() {
        return this.damage;
    }
    
    public void setDamage(final int damage) {
        this.damage = damage;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public HandlerList getHandlers() {
        return VehicleDamageEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return VehicleDamageEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
