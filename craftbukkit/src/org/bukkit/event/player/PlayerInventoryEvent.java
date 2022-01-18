// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.HandlerList;

@Deprecated
public class PlayerInventoryEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    protected Inventory inventory;
    
    public PlayerInventoryEvent(final Player player, final Inventory inventory) {
        super(player);
        this.inventory = inventory;
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
    
    public HandlerList getHandlers() {
        return PlayerInventoryEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerInventoryEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
