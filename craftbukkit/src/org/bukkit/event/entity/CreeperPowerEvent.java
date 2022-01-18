// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.LightningStrike;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class CreeperPowerEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean canceled;
    private final PowerCause cause;
    private LightningStrike bolt;
    
    public CreeperPowerEvent(final Creeper creeper, final LightningStrike bolt, final PowerCause cause) {
        this(creeper, cause);
        this.bolt = bolt;
    }
    
    public CreeperPowerEvent(final Creeper creeper, final PowerCause cause) {
        super(creeper);
        this.cause = cause;
    }
    
    public boolean isCancelled() {
        return this.canceled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.canceled = cancel;
    }
    
    public Creeper getEntity() {
        return (Creeper)this.entity;
    }
    
    public LightningStrike getLightning() {
        return this.bolt;
    }
    
    public PowerCause getCause() {
        return this.cause;
    }
    
    public HandlerList getHandlers() {
        return CreeperPowerEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return CreeperPowerEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
    
    public enum PowerCause
    {
        LIGHTNING, 
        SET_ON, 
        SET_OFF;
    }
}
