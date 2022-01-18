// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.block;

import org.bukkit.inventory.FurnaceInventory;

public interface Furnace extends BlockState, ContainerBlock
{
    short getBurnTime();
    
    void setBurnTime(final short p0);
    
    short getCookTime();
    
    void setCookTime(final short p0);
    
    FurnaceInventory getInventory();
}
