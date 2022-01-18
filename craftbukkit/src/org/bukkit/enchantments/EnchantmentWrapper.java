// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.enchantments;

import org.bukkit.inventory.ItemStack;

public class EnchantmentWrapper extends Enchantment
{
    public EnchantmentWrapper(final int id) {
        super(id);
    }
    
    public Enchantment getEnchantment() {
        return Enchantment.getById(this.getId());
    }
    
    public int getMaxLevel() {
        return this.getEnchantment().getMaxLevel();
    }
    
    public int getStartLevel() {
        return this.getEnchantment().getStartLevel();
    }
    
    public EnchantmentTarget getItemTarget() {
        return this.getEnchantment().getItemTarget();
    }
    
    public boolean canEnchantItem(final ItemStack item) {
        return this.getEnchantment().canEnchantItem(item);
    }
    
    public String getName() {
        return this.getEnchantment().getName();
    }
    
    public boolean conflictsWith(final Enchantment other) {
        return this.getEnchantment().conflictsWith(other);
    }
}
