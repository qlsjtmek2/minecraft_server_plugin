// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.block;

import net.minecraft.server.v1_5_R3.World;
import net.minecraft.server.v1_5_R3.BlockDropper;
import org.bukkit.Material;
import net.minecraft.server.v1_5_R3.IInventory;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.block.Block;
import net.minecraft.server.v1_5_R3.TileEntityDropper;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.block.Dropper;

public class CraftDropper extends CraftBlockState implements Dropper
{
    private final CraftWorld world;
    private final TileEntityDropper dropper;
    
    public CraftDropper(final Block block) {
        super(block);
        this.world = (CraftWorld)block.getWorld();
        this.dropper = (TileEntityDropper)this.world.getTileEntityAt(this.getX(), this.getY(), this.getZ());
    }
    
    public Inventory getInventory() {
        return new CraftInventory(this.dropper);
    }
    
    public void drop() {
        final Block block = this.getBlock();
        if (block.getType() == Material.DROPPER) {
            final BlockDropper drop = (BlockDropper)net.minecraft.server.v1_5_R3.Block.DROPPER;
            drop.dispense(this.world.getHandle(), this.getX(), this.getY(), this.getZ());
        }
    }
    
    public boolean update(final boolean force, final boolean applyPhysics) {
        final boolean result = super.update(force, applyPhysics);
        if (result) {
            this.dropper.update();
        }
        return result;
    }
}
