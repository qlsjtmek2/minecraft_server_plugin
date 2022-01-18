// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

import org.bukkit.Material;

public interface FallingBlock extends Entity
{
    Material getMaterial();
    
    int getBlockId();
    
    byte getBlockData();
    
    boolean getDropItem();
    
    void setDropItem(final boolean p0);
}
