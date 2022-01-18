// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit;

import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.block.Block;

public interface Chunk
{
    int getX();
    
    int getZ();
    
    World getWorld();
    
    Block getBlock(final int p0, final int p1, final int p2);
    
    ChunkSnapshot getChunkSnapshot();
    
    ChunkSnapshot getChunkSnapshot(final boolean p0, final boolean p1, final boolean p2);
    
    Entity[] getEntities();
    
    BlockState[] getTileEntities();
    
    boolean isLoaded();
    
    boolean load(final boolean p0);
    
    boolean load();
    
    boolean unload(final boolean p0, final boolean p1);
    
    boolean unload(final boolean p0);
    
    boolean unload();
}
