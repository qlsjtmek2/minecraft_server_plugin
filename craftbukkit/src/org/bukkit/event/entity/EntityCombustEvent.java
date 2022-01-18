// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntityCombustEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private int duration;
    private boolean cancel;
    
    public EntityCombustEvent(final Entity combustee, final int duration) {
        super(combustee);
        this.duration = duration;
        this.cancel = false;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public int getDuration() {
        return this.duration;
    }
    
    public void setDuration(final int duration) {
        this.duration = duration;
    }
    
    public HandlerList getHandlers() {
        return EntityCombustEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityCombustEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
