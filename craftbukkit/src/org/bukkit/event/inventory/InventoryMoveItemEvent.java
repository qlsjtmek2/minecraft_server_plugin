// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.inventory;

import org.apache.commons.lang.Validate;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public class InventoryMoveItemEvent extends Event implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final Inventory sourceInventory;
    private final Inventory destinationInventory;
    private ItemStack itemStack;
    private final boolean didSourceInitiate;
    
    public InventoryMoveItemEvent(final Inventory sourceInventory, final ItemStack itemStack, final Inventory destinationInventory, final boolean didSourceInitiate) {
        Validate.notNull(itemStack, "ItemStack cannot be null");
        this.sourceInventory = sourceInventory;
        this.itemStack = itemStack;
        this.destinationInventory = destinationInventory;
        this.didSourceInitiate = didSourceInitiate;
    }
    
    public Inventory getSource() {
        return this.sourceInventory;
    }
    
    public ItemStack getItem() {
        return this.itemStack.clone();
    }
    
    public void setItem(final ItemStack itemStack) {
        Validate.notNull(itemStack, "ItemStack cannot be null.  Cancel the event if you want nothing to be transferred.");
        this.itemStack = itemStack.clone();
    }
    
    public Inventory getDestination() {
        return this.destinationInventory;
    }
    
    public Inventory getInitiator() {
        return this.didSourceInitiate ? this.sourceInventory : this.destinationInventory;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public HandlerList getHandlers() {
        return InventoryMoveItemEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return InventoryMoveItemEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
