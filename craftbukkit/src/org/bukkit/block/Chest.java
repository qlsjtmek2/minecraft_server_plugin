// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.block;

import org.bukkit.inventory.Inventory;

public interface Chest extends BlockState, ContainerBlock
{
    Inventory getBlockInventory();
}
