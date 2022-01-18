// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Explosive;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class ExplosionPrimeEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private float radius;
    private boolean fire;
    
    public ExplosionPrimeEvent(final Entity what, final float radius, final boolean fire) {
        super(what);
        this.cancel = false;
        this.radius = radius;
        this.fire = fire;
    }
    
    public ExplosionPrimeEvent(final Explosive explosive) {
        this(explosive, explosive.getYield(), explosive.isIncendiary());
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public float getRadius() {
        return this.radius;
    }
    
    public void setRadius(final float radius) {
        this.radius = radius;
    }
    
    public boolean getFire() {
        return this.fire;
    }
    
    public void setFire(final boolean fire) {
        this.fire = fire;
    }
    
    public HandlerList getHandlers() {
        return ExplosionPrimeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ExplosionPrimeEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
