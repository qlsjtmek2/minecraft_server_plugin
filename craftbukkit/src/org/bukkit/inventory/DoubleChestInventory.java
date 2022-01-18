// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.inventory;

import org.bukkit.block.DoubleChest;

public interface DoubleChestInventory extends Inventory
{
    Inventory getLeftSide();
    
    Inventory getRightSide();
    
    DoubleChest getHolder();
}
