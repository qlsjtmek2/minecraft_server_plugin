// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerShearEntityEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private final Entity what;
    
    public PlayerShearEntityEvent(final Player who, final Entity what) {
        super(who);
        this.cancel = false;
        this.what = what;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public Entity getEntity() {
        return this.what;
    }
    
    public HandlerList getHandlers() {
        return PlayerShearEntityEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerShearEntityEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
