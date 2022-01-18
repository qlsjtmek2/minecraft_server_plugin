// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import org.bukkit.inventory.InventoryHolder;
import org.bukkit.block.Furnace;
import org.bukkit.inventory.ItemStack;
import net.minecraft.server.v1_5_R3.IInventory;
import net.minecraft.server.v1_5_R3.TileEntityFurnace;
import org.bukkit.inventory.FurnaceInventory;

public class CraftInventoryFurnace extends CraftInventory implements FurnaceInventory
{
    public CraftInventoryFurnace(final TileEntityFurnace inventory) {
        super(inventory);
    }
    
    public ItemStack getResult() {
        return this.getItem(2);
    }
    
    public ItemStack getFuel() {
        return this.getItem(1);
    }
    
    public ItemStack getSmelting() {
        return this.getItem(0);
    }
    
    public void setFuel(final ItemStack stack) {
        this.setItem(1, stack);
    }
    
    public void setResult(final ItemStack stack) {
        this.setItem(2, stack);
    }
    
    public void setSmelting(final ItemStack stack) {
        this.setItem(0, stack);
    }
    
    public Furnace getHolder() {
        return (Furnace)this.inventory.getOwner();
    }
}
