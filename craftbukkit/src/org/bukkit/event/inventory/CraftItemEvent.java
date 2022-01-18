// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.CraftingInventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.Recipe;

public class CraftItemEvent extends InventoryClickEvent
{
    private Recipe recipe;
    
    public CraftItemEvent(final Recipe recipe, final InventoryView what, final InventoryType.SlotType type, final int slot, final boolean right, final boolean shift) {
        this(recipe, what, type, slot, right ? (shift ? ClickType.SHIFT_RIGHT : ClickType.RIGHT) : (shift ? ClickType.SHIFT_LEFT : ClickType.LEFT), InventoryAction.PICKUP_ALL);
    }
    
    public CraftItemEvent(final Recipe recipe, final InventoryView what, final InventoryType.SlotType type, final int slot, final ClickType click, final InventoryAction action) {
        super(what, type, slot, click, action);
        this.recipe = recipe;
    }
    
    public CraftItemEvent(final Recipe recipe, final InventoryView what, final InventoryType.SlotType type, final int slot, final ClickType click, final InventoryAction action, final int key) {
        super(what, type, slot, click, action, key);
        this.recipe = recipe;
    }
    
    public Recipe getRecipe() {
        return this.recipe;
    }
    
    public CraftingInventory getInventory() {
        return (CraftingInventory)super.getInventory();
    }
}
