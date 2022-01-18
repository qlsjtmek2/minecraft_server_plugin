// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerMoveEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private Location from;
    private Location to;
    
    public PlayerMoveEvent(final Player player, final Location from, final Location to) {
        super(player);
        this.cancel = false;
        this.from = from;
        this.to = to;
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
        return PlayerMoveEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerMoveEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
