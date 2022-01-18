// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerToggleSprintEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final boolean isSprinting;
    private boolean cancel;
    
    public PlayerToggleSprintEvent(final Player player, final boolean isSprinting) {
        super(player);
        this.cancel = false;
        this.isSprinting = isSprinting;
    }
    
    public boolean isSprinting() {
        return this.isSprinting;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public HandlerList getHandlers() {
        return PlayerToggleSprintEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerToggleSprintEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
