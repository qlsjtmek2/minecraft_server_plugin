// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import org.bukkit.inventory.ItemStack;
import net.minecraft.server.v1_5_R3.IInventory;
import net.minecraft.server.v1_5_R3.ContainerEnchantTableInventory;
import org.bukkit.inventory.EnchantingInventory;

public class CraftInventoryEnchanting extends CraftInventory implements EnchantingInventory
{
    public CraftInventoryEnchanting(final ContainerEnchantTableInventory inventory) {
        super(inventory);
    }
    
    public void setItem(final ItemStack item) {
        this.setItem(0, item);
    }
    
    public ItemStack getItem() {
        return this.getItem(0);
    }
    
    public ContainerEnchantTableInventory getInventory() {
        return (ContainerEnchantTableInventory)this.inventory;
    }
}
