// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.entity.Item;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerPickupItemEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final Item item;
    private boolean cancel;
    private final int remaining;
    
    public PlayerPickupItemEvent(final Player player, final Item item, final int remaining) {
        super(player);
        this.cancel = false;
        this.item = item;
        this.remaining = remaining;
    }
    
    public Item getItem() {
        return this.item;
    }
    
    public int getRemaining() {
        return this.remaining;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public HandlerList getHandlers() {
        return PlayerPickupItemEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerPickupItemEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
