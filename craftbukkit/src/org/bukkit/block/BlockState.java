// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.block;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.Material;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.Metadatable;

public interface BlockState extends Metadatable
{
    Block getBlock();
    
    MaterialData getData();
    
    Material getType();
    
    int getTypeId();
    
    byte getLightLevel();
    
    World getWorld();
    
    int getX();
    
    int getY();
    
    int getZ();
    
    Location getLocation();
    
    Location getLocation(final Location p0);
    
    Chunk getChunk();
    
    void setData(final MaterialData p0);
    
    void setType(final Material p0);
    
    boolean setTypeId(final int p0);
    
    boolean update();
    
    boolean update(final boolean p0);
    
    boolean update(final boolean p0, final boolean p1);
    
    byte getRawData();
    
    void setRawData(final byte p0);
}
