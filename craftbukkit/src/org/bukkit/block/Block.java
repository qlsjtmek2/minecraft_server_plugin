// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.block;

import java.util.Collection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.Material;
import org.bukkit.metadata.Metadatable;

public interface Block extends Metadatable
{
    byte getData();
    
    Block getRelative(final int p0, final int p1, final int p2);
    
    Block getRelative(final BlockFace p0);
    
    Block getRelative(final BlockFace p0, final int p1);
    
    Material getType();
    
    int getTypeId();
    
    byte getLightLevel();
    
    byte getLightFromSky();
    
    byte getLightFromBlocks();
    
    World getWorld();
    
    int getX();
    
    int getY();
    
    int getZ();
    
    Location getLocation();
    
    Location getLocation(final Location p0);
    
    Chunk getChunk();
    
    void setData(final byte p0);
    
    void setData(final byte p0, final boolean p1);
    
    void setType(final Material p0);
    
    boolean setTypeId(final int p0);
    
    boolean setTypeId(final int p0, final boolean p1);
    
    boolean setTypeIdAndData(final int p0, final byte p1, final boolean p2);
    
    BlockFace getFace(final Block p0);
    
    BlockState getState();
    
    Biome getBiome();
    
    void setBiome(final Biome p0);
    
    boolean isBlockPowered();
    
    boolean isBlockIndirectlyPowered();
    
    boolean isBlockFacePowered(final BlockFace p0);
    
    boolean isBlockFaceIndirectlyPowered(final BlockFace p0);
    
    int getBlockPower(final BlockFace p0);
    
    int getBlockPower();
    
    boolean isEmpty();
    
    boolean isLiquid();
    
    double getTemperature();
    
    double getHumidity();
    
    PistonMoveReaction getPistonMoveReaction();
    
    boolean breakNaturally();
    
    boolean breakNaturally(final ItemStack p0);
    
    Collection<ItemStack> getDrops();
    
    Collection<ItemStack> getDrops(final ItemStack p0);
}
