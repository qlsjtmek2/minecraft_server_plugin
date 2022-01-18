// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.util;

import org.bukkit.material.MaterialData;
import java.util.ArrayList;
import net.minecraft.server.v1_5_R3.World;
import org.bukkit.block.BlockState;
import java.util.List;
import org.bukkit.craftbukkit.v1_5_R3.CraftWorld;
import org.bukkit.BlockChangeDelegate;

public class StructureGrowDelegate implements BlockChangeDelegate
{
    private final CraftWorld world;
    private final List<BlockState> blocks;
    
    public StructureGrowDelegate(final World world) {
        this.blocks = new ArrayList<BlockState>();
        this.world = world.getWorld();
    }
    
    public boolean setRawTypeId(final int x, final int y, final int z, final int type) {
        return this.setRawTypeIdAndData(x, y, z, type, 0);
    }
    
    public boolean setRawTypeIdAndData(final int x, final int y, final int z, final int type, final int data) {
        final BlockState state = this.world.getBlockAt(x, y, z).getState();
        state.setTypeId(type);
        state.setData(new MaterialData(type, (byte)data));
        this.blocks.add(state);
        return true;
    }
    
    public boolean setTypeId(final int x, final int y, final int z, final int typeId) {
        return this.setRawTypeId(x, y, z, typeId);
    }
    
    public boolean setTypeIdAndData(final int x, final int y, final int z, final int typeId, final int data) {
        return this.setRawTypeIdAndData(x, y, z, typeId, data);
    }
    
    public int getTypeId(final int x, final int y, final int z) {
        return this.world.getBlockTypeIdAt(x, y, z);
    }
    
    public int getHeight() {
        return this.world.getMaxHeight();
    }
    
    public List<BlockState> getBlocks() {
        return this.blocks;
    }
    
    public boolean isEmpty(final int x, final int y, final int z) {
        return this.world.getBlockAt(x, y, z).isEmpty();
    }
}
