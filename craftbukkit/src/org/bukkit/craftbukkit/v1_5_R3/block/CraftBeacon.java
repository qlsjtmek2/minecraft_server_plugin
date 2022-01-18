// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.block;

import net.minecraft.server.v1_5_R3.IInventory;
import org.bukkit.craftbukkit.v1_5_R3.inventory.CraftInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.block.Block;
import net.minecraft.server.v1_5_R3.TileEntityBeacon;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.block.Beacon;

public class CraftBeacon extends CraftBlockState implements Beacon
{
    private final CraftWorld world;
    private final TileEntityBeacon beacon;
    
    public CraftBeacon(final Block block) {
        super(block);
        this.world = (CraftWorld)block.getWorld();
        this.beacon = (TileEntityBeacon)this.world.getTileEntityAt(this.getX(), this.getY(), this.getZ());
    }
    
    public Inventory getInventory() {
        return new CraftInventory(this.beacon);
    }
    
    public boolean update(final boolean force, final boolean applyPhysics) {
        final boolean result = super.update(force, applyPhysics);
        if (result) {
            this.beacon.update();
        }
        return result;
    }
}
