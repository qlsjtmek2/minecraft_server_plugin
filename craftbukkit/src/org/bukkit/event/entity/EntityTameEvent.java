// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntityTameEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final AnimalTamer owner;
    
    public EntityTameEvent(final LivingEntity entity, final AnimalTamer owner) {
        super(entity);
        this.owner = owner;
    }
    
    public LivingEntity getEntity() {
        return (LivingEntity)this.entity;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public AnimalTamer getOwner() {
        return this.owner;
    }
    
    public HandlerList getHandlers() {
        return EntityTameEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityTameEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
