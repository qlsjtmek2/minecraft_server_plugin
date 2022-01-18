// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.HandlerList;

public class PlayerItemBreakEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private final ItemStack brokenItem;
    
    public PlayerItemBreakEvent(final Player player, final ItemStack brokenItem) {
        super(player);
        this.brokenItem = brokenItem;
    }
    
    public ItemStack getBrokenItem() {
        return this.brokenItem;
    }
    
    public HandlerList getHandlers() {
        return PlayerItemBreakEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerItemBreakEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
