// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntityDamageEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private int damage;
    private boolean cancelled;
    private final DamageCause cause;
    
    public EntityDamageEvent(final Entity damagee, final DamageCause cause, final int damage) {
        super(damagee);
        this.cause = cause;
        this.damage = damage;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public int getDamage() {
        return this.damage;
    }
    
    public void setDamage(final int damage) {
        this.damage = damage;
    }
    
    public DamageCause getCause() {
        return this.cause;
    }
    
    public HandlerList getHandlers() {
        return EntityDamageEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityDamageEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
    
    public enum DamageCause
    {
        CONTACT, 
        ENTITY_ATTACK, 
        PROJECTILE, 
        SUFFOCATION, 
        FALL, 
        FIRE, 
        FIRE_TICK, 
        MELTING, 
        LAVA, 
        DROWNING, 
        BLOCK_EXPLOSION, 
        ENTITY_EXPLOSION, 
        VOID, 
        LIGHTNING, 
        SUICIDE, 
        STARVATION, 
        POISON, 
        MAGIC, 
        WITHER, 
        FALLING_BLOCK, 
        THORNS, 
        CUSTOM;
    }
}
