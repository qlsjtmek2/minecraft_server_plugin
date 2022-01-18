// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import org.bukkit.inventory.InventoryHolder;
import org.bukkit.block.BrewingStand;
import org.bukkit.inventory.ItemStack;
import net.minecraft.server.v1_5_R3.IInventory;
import org.bukkit.inventory.BrewerInventory;

public class CraftInventoryBrewer extends CraftInventory implements BrewerInventory
{
    public CraftInventoryBrewer(final IInventory inventory) {
        super(inventory);
    }
    
    public ItemStack getIngredient() {
        return this.getItem(3);
    }
    
    public void setIngredient(final ItemStack ingredient) {
        this.setItem(3, ingredient);
    }
    
    public BrewingStand getHolder() {
        return (BrewingStand)this.inventory.getOwner();
    }
}
