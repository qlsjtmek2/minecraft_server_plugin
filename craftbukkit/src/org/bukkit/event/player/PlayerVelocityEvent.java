// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerVelocityEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private Vector velocity;
    
    public PlayerVelocityEvent(final Player player, final Vector velocity) {
        super(player);
        this.cancel = false;
        this.velocity = velocity;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public Vector getVelocity() {
        return this.velocity;
    }
    
    public void setVelocity(final Vector velocity) {
        this.velocity = velocity;
    }
    
    public HandlerList getHandlers() {
        return PlayerVelocityEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerVelocityEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
