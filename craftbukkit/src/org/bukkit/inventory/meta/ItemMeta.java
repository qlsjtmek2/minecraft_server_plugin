// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.inventory.meta;

import java.util.Map;
import org.bukkit.enchantments.Enchantment;
import java.util.List;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface ItemMeta extends Cloneable, ConfigurationSerializable
{
    boolean hasDisplayName();
    
    String getDisplayName();
    
    void setDisplayName(final String p0);
    
    boolean hasLore();
    
    List<String> getLore();
    
    void setLore(final List<String> p0);
    
    boolean hasEnchants();
    
    boolean hasEnchant(final Enchantment p0);
    
    int getEnchantLevel(final Enchantment p0);
    
    Map<Enchantment, Integer> getEnchants();
    
    boolean addEnchant(final Enchantment p0, final int p1, final boolean p2);
    
    boolean removeEnchant(final Enchantment p0);
    
    boolean hasConflictingEnchant(final Enchantment p0);
    
    ItemMeta clone();
}
