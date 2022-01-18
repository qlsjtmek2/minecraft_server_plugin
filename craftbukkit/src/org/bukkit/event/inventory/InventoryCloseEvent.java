// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.inventory;

import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.InventoryView;
import org.bukkit.event.HandlerList;

public class InventoryCloseEvent extends InventoryEvent
{
    private static final HandlerList handlers;
    
    public InventoryCloseEvent(final InventoryView transaction) {
        super(transaction);
    }
    
    public final HumanEntity getPlayer() {
        return this.transaction.getPlayer();
    }
    
    public HandlerList getHandlers() {
        return InventoryCloseEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return InventoryCloseEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
