// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.block;

import org.bukkit.inventory.Inventory;
import net.minecraft.server.v1_5_R3.IInventory;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryBrewer;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.block.Block;
import net.minecraft.server.v1_5_R3.TileEntityBrewingStand;
import org.bukkit.block.BrewingStand;

public class CraftBrewingStand extends CraftBlockState implements BrewingStand
{
    private final TileEntityBrewingStand brewingStand;
    
    public CraftBrewingStand(final Block block) {
        super(block);
        this.brewingStand = (TileEntityBrewingStand)((CraftWorld)block.getWorld()).getTileEntityAt(this.getX(), this.getY(), this.getZ());
    }
    
    public BrewerInventory getInventory() {
        return new CraftInventoryBrewer(this.brewingStand);
    }
    
    public boolean update(final boolean force, final boolean applyPhysics) {
        final boolean result = super.update(force, applyPhysics);
        if (result) {
            this.brewingStand.update();
        }
        return result;
    }
    
    public int getBrewingTime() {
        return this.brewingStand.brewTime;
    }
    
    public void setBrewingTime(final int brewTime) {
        this.brewingStand.brewTime = brewTime;
    }
}
