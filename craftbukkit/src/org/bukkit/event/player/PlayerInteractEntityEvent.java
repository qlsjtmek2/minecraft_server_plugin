// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerInteractEntityEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    protected Entity clickedEntity;
    boolean cancelled;
    
    public PlayerInteractEntityEvent(final Player who, final Entity clickedEntity) {
        super(who);
        this.cancelled = false;
        this.clickedEntity = clickedEntity;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public Entity getRightClicked() {
        return this.clickedEntity;
    }
    
    public HandlerList getHandlers() {
        return PlayerInteractEntityEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerInteractEntityEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
