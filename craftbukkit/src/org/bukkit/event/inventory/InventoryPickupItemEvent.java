// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.inventory;

import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class InventoryPickupItemEvent extends Event implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final Inventory inventory;
    private final Item item;
    
    public InventoryPickupItemEvent(final Inventory inventory, final Item item) {
        this.inventory = inventory;
        this.item = item;
    }
    
    public Inventory getInventory() {
        return this.inventory;
    }
    
    public Item getItem() {
        return this.item;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public HandlerList getHandlers() {
        return InventoryPickupItemEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return InventoryPickupItemEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
