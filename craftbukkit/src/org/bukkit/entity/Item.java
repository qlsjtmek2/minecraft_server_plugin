// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

import org.bukkit.inventory.ItemStack;

public interface Item extends Entity
{
    ItemStack getItemStack();
    
    void setItemStack(final ItemStack p0);
    
    int getPickupDelay();
    
    void setPickupDelay(final int p0);
}
