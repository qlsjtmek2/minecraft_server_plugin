// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerAnimationEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final PlayerAnimationType animationType;
    private boolean isCancelled;
    
    public PlayerAnimationEvent(final Player player) {
        super(player);
        this.isCancelled = false;
        this.animationType = PlayerAnimationType.ARM_SWING;
    }
    
    public PlayerAnimationType getAnimationType() {
        return this.animationType;
    }
    
    public boolean isCancelled() {
        return this.isCancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.isCancelled = cancel;
    }
    
    public HandlerList getHandlers() {
        return PlayerAnimationEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerAnimationEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
