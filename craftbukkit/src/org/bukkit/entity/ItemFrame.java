// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

import org.bukkit.Rotation;
import org.bukkit.inventory.ItemStack;

public interface ItemFrame extends Hanging
{
    ItemStack getItem();
    
    void setItem(final ItemStack p0);
    
    Rotation getRotation();
    
    void setRotation(final Rotation p0) throws IllegalArgumentException;
}
