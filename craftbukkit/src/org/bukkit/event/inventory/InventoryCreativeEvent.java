// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.inventory;

import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventoryCreativeEvent extends InventoryClickEvent
{
    private ItemStack item;
    
    public InventoryCreativeEvent(final InventoryView what, final InventoryType.SlotType type, final int slot, final ItemStack newItem) {
        super(what, type, slot, ClickType.CREATIVE, InventoryAction.PLACE_ALL);
        this.item = newItem;
    }
    
    public ItemStack getCursor() {
        return this.item;
    }
    
    public void setCursor(final ItemStack item) {
        this.item = item;
    }
}
