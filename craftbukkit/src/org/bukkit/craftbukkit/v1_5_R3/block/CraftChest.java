// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.block;

import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventoryDoubleChest;
import org.bukkit.Material;
import net.minecraft.server.v1_5_R3.IInventory;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.block.Block;
import net.minecraft.server.v1_5_R3.TileEntityChest;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.block.Chest;

public class CraftChest extends CraftBlockState implements Chest
{
    private final CraftWorld world;
    private final TileEntityChest chest;
    
    public CraftChest(final Block block) {
        super(block);
        this.world = (CraftWorld)block.getWorld();
        this.chest = (TileEntityChest)this.world.getTileEntityAt(this.getX(), this.getY(), this.getZ());
    }
    
    public Inventory getBlockInventory() {
        return new CraftInventory(this.chest);
    }
    
    public Inventory getInventory() {
        final int x = this.getX();
        final int y = this.getY();
        final int z = this.getZ();
        CraftInventory inventory = new CraftInventory(this.chest);
        int id;
        if (this.world.getBlockTypeIdAt(x, y, z) == Material.CHEST.getId()) {
            id = Material.CHEST.getId();
        }
        else {
            if (this.world.getBlockTypeIdAt(x, y, z) != Material.TRAPPED_CHEST.getId()) {
                throw new IllegalStateException("CraftChest is not a chest but is instead " + this.world.getBlockAt(x, y, z));
            }
            id = Material.TRAPPED_CHEST.getId();
        }
        if (this.world.getBlockTypeIdAt(x - 1, y, z) == id) {
            final CraftInventory left = new CraftInventory((IInventory)this.world.getHandle().getTileEntity(x - 1, y, z));
            inventory = new CraftInventoryDoubleChest(left, inventory);
        }
        if (this.world.getBlockTypeIdAt(x + 1, y, z) == id) {
            final CraftInventory right = new CraftInventory((IInventory)this.world.getHandle().getTileEntity(x + 1, y, z));
            inventory = new CraftInventoryDoubleChest(inventory, right);
        }
        if (this.world.getBlockTypeIdAt(x, y, z - 1) == id) {
            final CraftInventory left = new CraftInventory((IInventory)this.world.getHandle().getTileEntity(x, y, z - 1));
            inventory = new CraftInventoryDoubleChest(left, inventory);
        }
        if (this.world.getBlockTypeIdAt(x, y, z + 1) == id) {
            final CraftInventory right = new CraftInventory((IInventory)this.world.getHandle().getTileEntity(x, y, z + 1));
            inventory = new CraftInventoryDoubleChest(inventory, right);
        }
        return inventory;
    }
    
    public boolean update(final boolean force, final boolean applyPhysics) {
        final boolean result = super.update(force, applyPhysics);
        if (result) {
            this.chest.update();
        }
        return result;
    }
}
