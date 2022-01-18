// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.block;

import net.minecraft.server.v1_5_R3.World;
import net.minecraft.server.v1_5_R3.BlockDispenser;
import org.bukkit.Material;
import net.minecraft.server.v1_5_R3.IInventory;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.block.Block;
import net.minecraft.server.v1_5_R3.TileEntityDispenser;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.block.Dispenser;

public class CraftDispenser extends CraftBlockState implements Dispenser
{
    private final CraftWorld world;
    private final TileEntityDispenser dispenser;
    
    public CraftDispenser(final Block block) {
        super(block);
        this.world = (CraftWorld)block.getWorld();
        this.dispenser = (TileEntityDispenser)this.world.getTileEntityAt(this.getX(), this.getY(), this.getZ());
    }
    
    public Inventory getInventory() {
        return new CraftInventory(this.dispenser);
    }
    
    public boolean dispense() {
        final Block block = this.getBlock();
        if (block.getType() == Material.DISPENSER) {
            final BlockDispenser dispense = (BlockDispenser)net.minecraft.server.v1_5_R3.Block.DISPENSER;
            dispense.dispense(this.world.getHandle(), this.getX(), this.getY(), this.getZ());
            return true;
        }
        return false;
    }
    
    public boolean update(final boolean force, final boolean applyPhysics) {
        final boolean result = super.update(force, applyPhysics);
        if (result) {
            this.dispenser.update();
        }
        return result;
    }
}
