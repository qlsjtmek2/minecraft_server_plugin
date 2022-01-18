// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.block;

import net.minecraft.server.v1_5_R3.IInventory;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.block.Block;
import net.minecraft.server.v1_5_R3.TileEntityHopper;
import org.bukkit.block.Hopper;

public class CraftHopper extends CraftBlockState implements Hopper
{
    private final TileEntityHopper hopper;
    
    public CraftHopper(final Block block) {
        super(block);
        this.hopper = (TileEntityHopper)((CraftWorld)block.getWorld()).getTileEntityAt(this.getX(), this.getY(), this.getZ());
    }
    
    public Inventory getInventory() {
        return new CraftInventory(this.hopper);
    }
    
    public boolean update(final boolean force, final boolean applyPhysics) {
        final boolean result = super.update(force, applyPhysics);
        if (result) {
            this.hopper.update();
        }
        return result;
    }
}
