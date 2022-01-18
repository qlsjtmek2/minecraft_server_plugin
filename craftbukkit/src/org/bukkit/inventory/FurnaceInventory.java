// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.inventory;

import org.bukkit.block.Furnace;

public interface FurnaceInventory extends Inventory
{
    ItemStack getResult();
    
    ItemStack getFuel();
    
    ItemStack getSmelting();
    
    void setFuel(final ItemStack p0);
    
    void setResult(final ItemStack p0);
    
    void setSmelting(final ItemStack p0);
    
    Furnace getHolder();
}
