// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.inventory;

import org.bukkit.entity.HumanEntity;

public interface PlayerInventory extends Inventory
{
    ItemStack[] getArmorContents();
    
    ItemStack getHelmet();
    
    ItemStack getChestplate();
    
    ItemStack getLeggings();
    
    ItemStack getBoots();
    
    void setArmorContents(final ItemStack[] p0);
    
    void setHelmet(final ItemStack p0);
    
    void setChestplate(final ItemStack p0);
    
    void setLeggings(final ItemStack p0);
    
    void setBoots(final ItemStack p0);
    
    ItemStack getItemInHand();
    
    void setItemInHand(final ItemStack p0);
    
    int getHeldItemSlot();
    
    void setHeldItemSlot(final int p0);
    
    int clear(final int p0, final int p1);
    
    HumanEntity getHolder();
}
