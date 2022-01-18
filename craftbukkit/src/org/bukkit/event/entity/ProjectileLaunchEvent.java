// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Projectile;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class ProjectileLaunchEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    
    public ProjectileLaunchEvent(final Entity what) {
        super(what);
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public Projectile getEntity() {
        return (Projectile)this.entity;
    }
    
    public HandlerList getHandlers() {
        return ProjectileLaunchEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ProjectileLaunchEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
