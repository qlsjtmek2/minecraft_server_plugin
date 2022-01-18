// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;
import org.bukkit.event.HandlerList;

public class EntityPortalExitEvent extends EntityTeleportEvent
{
    private static final HandlerList handlers;
    private Vector before;
    private Vector after;
    
    public EntityPortalExitEvent(final Entity entity, final Location from, final Location to, final Vector before, final Vector after) {
        super(entity, from, to);
        this.before = before;
        this.after = after;
    }
    
    public Vector getBefore() {
        return this.before.clone();
    }
    
    public Vector getAfter() {
        return this.after.clone();
    }
    
    public void setAfter(final Vector after) {
        this.after = after.clone();
    }
    
    public HandlerList getHandlers() {
        return EntityPortalExitEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityPortalExitEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
