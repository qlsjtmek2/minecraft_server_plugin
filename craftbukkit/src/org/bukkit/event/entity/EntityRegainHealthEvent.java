// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntityRegainHealthEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private int amount;
    private final RegainReason regainReason;
    
    public EntityRegainHealthEvent(final Entity entity, final int amount, final RegainReason regainReason) {
        super(entity);
        this.amount = amount;
        this.regainReason = regainReason;
    }
    
    public int getAmount() {
        return this.amount;
    }
    
    public void setAmount(final int amount) {
        this.amount = amount;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public RegainReason getRegainReason() {
        return this.regainReason;
    }
    
    public HandlerList getHandlers() {
        return EntityRegainHealthEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityRegainHealthEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
    
    public enum RegainReason
    {
        REGEN, 
        SATIATED, 
        EATING, 
        ENDER_CRYSTAL, 
        MAGIC, 
        MAGIC_REGEN, 
        WITHER_SPAWN, 
        WITHER, 
        CUSTOM;
    }
}
