// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.event.HandlerList;

public class PrepareItemCraftEvent extends InventoryEvent
{
    private static final HandlerList handlers;
    private boolean repair;
    private CraftingInventory matrix;
    
    public PrepareItemCraftEvent(final CraftingInventory what, final InventoryView view, final boolean isRepair) {
        super(view);
        this.matrix = what;
        this.repair = isRepair;
    }
    
    public Recipe getRecipe() {
        return this.matrix.getRecipe();
    }
    
    public CraftingInventory getInventory() {
        return this.matrix;
    }
    
    public boolean isRepair() {
        return this.repair;
    }
    
    public HandlerList getHandlers() {
        return PrepareItemCraftEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PrepareItemCraftEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
