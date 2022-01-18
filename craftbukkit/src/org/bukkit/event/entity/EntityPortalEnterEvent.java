// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;

public class EntityPortalEnterEvent extends EntityEvent
{
    private static final HandlerList handlers;
    private final Location location;
    
    public EntityPortalEnterEvent(final Entity entity, final Location location) {
        super(entity);
        this.location = location;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public HandlerList getHandlers() {
        return EntityPortalEnterEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityPortalEnterEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
