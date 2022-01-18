// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntityTeleportEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private Location from;
    private Location to;
    
    public EntityTeleportEvent(final Entity what, final Location from, final Location to) {
        super(what);
        this.from = from;
        this.to = to;
        this.cancel = false;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public Location getFrom() {
        return this.from;
    }
    
    public void setFrom(final Location from) {
        this.from = from;
    }
    
    public Location getTo() {
        return this.to;
    }
    
    public void setTo(final Location to) {
        this.to = to;
    }
    
    public HandlerList getHandlers() {
        return EntityTeleportEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityTeleportEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
