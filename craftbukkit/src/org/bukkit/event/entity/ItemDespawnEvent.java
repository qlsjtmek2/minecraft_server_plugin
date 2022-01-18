// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class ItemDespawnEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean canceled;
    private final Location location;
    
    public ItemDespawnEvent(final Item despawnee, final Location loc) {
        super(despawnee);
        this.location = loc;
    }
    
    public boolean isCancelled() {
        return this.canceled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.canceled = cancel;
    }
    
    public Item getEntity() {
        return (Item)this.entity;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public HandlerList getHandlers() {
        return ItemDespawnEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ItemDespawnEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
