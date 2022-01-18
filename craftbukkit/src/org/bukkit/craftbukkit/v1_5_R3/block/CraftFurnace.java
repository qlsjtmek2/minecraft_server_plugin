// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.block;

import org.bukkit.inventory.Inventory;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryFurnace;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.block.Block;
import net.minecraft.server.v1_5_R3.TileEntityFurnace;
import org.bukkit.block.Furnace;

public class CraftFurnace extends CraftBlockState implements Furnace
{
    private final TileEntityFurnace furnace;
    
    public CraftFurnace(final Block block) {
        super(block);
        this.furnace = (TileEntityFurnace)((CraftWorld)block.getWorld()).getTileEntityAt(this.getX(), this.getY(), this.getZ());
    }
    
    public FurnaceInventory getInventory() {
        return new CraftInventoryFurnace(this.furnace);
    }
    
    public boolean update(final boolean force, final boolean applyPhysics) {
        final boolean result = super.update(force, applyPhysics);
        if (result) {
            this.furnace.update();
        }
        return result;
    }
    
    public short getBurnTime() {
        return (short)this.furnace.burnTime;
    }
    
    public void setBurnTime(final short burnTime) {
        this.furnace.burnTime = burnTime;
    }
    
    public short getCookTime() {
        return (short)this.furnace.cookTime;
    }
    
    public void setCookTime(final short cookTime) {
        this.furnace.cookTime = cookTime;
    }
}
