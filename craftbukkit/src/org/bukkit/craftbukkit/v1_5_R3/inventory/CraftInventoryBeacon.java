// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.inventory;

import org.bukkit.inventory.ItemStack;
import net.minecraft.server.v1_5_R3.IInventory;
import net.minecraft.server.v1_5_R3.TileEntityBeacon;
import org.bukkit.inventory.BeaconInventory;

public class CraftInventoryBeacon extends CraftInventory implements BeaconInventory
{
    public CraftInventoryBeacon(final TileEntityBeacon beacon) {
        super(beacon);
    }
    
    public void setItem(final ItemStack item) {
        this.setItem(0, item);
    }
    
    public ItemStack getItem() {
        return this.getItem(0);
    }
}
