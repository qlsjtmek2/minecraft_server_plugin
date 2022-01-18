// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.entity.Item;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerDropItemEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final Item drop;
    private boolean cancel;
    
    public PlayerDropItemEvent(final Player player, final Item drop) {
        super(player);
        this.cancel = false;
        this.drop = drop;
    }
    
    public Item getItemDrop() {
        return this.drop;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public HandlerList getHandlers() {
        return PlayerDropItemEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerDropItemEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
