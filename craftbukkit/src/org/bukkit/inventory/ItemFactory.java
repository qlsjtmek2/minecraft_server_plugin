// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.inventory;

import org.bukkit.Color;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

public interface ItemFactory
{
    ItemMeta getItemMeta(final Material p0);
    
    boolean isApplicable(final ItemMeta p0, final ItemStack p1) throws IllegalArgumentException;
    
    boolean isApplicable(final ItemMeta p0, final Material p1) throws IllegalArgumentException;
    
    boolean equals(final ItemMeta p0, final ItemMeta p1) throws IllegalArgumentException;
    
    ItemMeta asMetaFor(final ItemMeta p0, final ItemStack p1) throws IllegalArgumentException;
    
    ItemMeta asMetaFor(final ItemMeta p0, final Material p1) throws IllegalArgumentException;
    
    Color getDefaultLeatherColor();
}
