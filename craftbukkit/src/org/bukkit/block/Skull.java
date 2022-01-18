// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.block;

import org.bukkit.SkullType;

public interface Skull extends BlockState
{
    boolean hasOwner();
    
    String getOwner();
    
    boolean setOwner(final String p0);
    
    BlockFace getRotation();
    
    void setRotation(final BlockFace p0);
    
    SkullType getSkullType();
    
    void setSkullType(final SkullType p0);
}
