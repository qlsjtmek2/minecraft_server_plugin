// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.block;

import org.bukkit.inventory.BrewerInventory;

public interface BrewingStand extends BlockState, ContainerBlock
{
    int getBrewingTime();
    
    void setBrewingTime(final int p0);
    
    BrewerInventory getInventory();
}
