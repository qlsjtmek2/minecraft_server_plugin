// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.inventory.meta;

import java.util.Map;
import org.bukkit.enchantments.Enchantment;

public interface EnchantmentStorageMeta extends ItemMeta
{
    boolean hasStoredEnchants();
    
    boolean hasStoredEnchant(final Enchantment p0);
    
    int getStoredEnchantLevel(final Enchantment p0);
    
    Map<Enchantment, Integer> getStoredEnchants();
    
    boolean addStoredEnchant(final Enchantment p0, final int p1, final boolean p2);
    
    boolean removeStoredEnchant(final Enchantment p0) throws IllegalArgumentException;
    
    boolean hasConflictingStoredEnchant(final Enchantment p0);
    
    EnchantmentStorageMeta clone();
}
