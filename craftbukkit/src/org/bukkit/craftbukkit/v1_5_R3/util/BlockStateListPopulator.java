// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.util;

import java.util.Iterator;
import java.util.ArrayList;
import org.bukkit.block.BlockState;
import java.util.List;
import org.bukkit.World;

public class BlockStateListPopulator
{
    private final World world;
    private final List<BlockState> list;
    
    public BlockStateListPopulator(final World world) {
        this(world, new ArrayList<BlockState>());
    }
    
    public BlockStateListPopulator(final World world, final List<BlockState> list) {
        this.world = world;
        this.list = list;
    }
    
    public void setTypeId(final int x, final int y, final int z, final int type) {
        final BlockState state = this.world.getBlockAt(x, y, z).getState();
        state.setTypeId(type);
        this.list.add(state);
    }
    
    public void updateList() {
        for (final BlockState state : this.list) {
            state.update(true);
        }
    }
    
    public List<BlockState> getList() {
        return this.list;
    }
    
    public World getWorld() {
        return this.world;
    }
}
