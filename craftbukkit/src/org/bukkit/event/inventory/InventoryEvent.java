// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.inventory;

import org.bukkit.entity.HumanEntity;
import java.util.List;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Event;

public class InventoryEvent extends Event
{
    private static final HandlerList handlers;
    protected InventoryView transaction;
    
    public InventoryEvent(final InventoryView transaction) {
        this.transaction = transaction;
    }
    
    public Inventory getInventory() {
        return this.transaction.getTopInventory();
    }
    
    public List<HumanEntity> getViewers() {
        return this.transaction.getTopInventory().getViewers();
    }
    
    public InventoryView getView() {
        return this.transaction;
    }
    
    public HandlerList getHandlers() {
        return InventoryEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return InventoryEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
