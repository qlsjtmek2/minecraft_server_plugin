// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EnchantmentInstance extends WeightedRandomChoice
{
    public final Enchantment enchantment;
    public final int level;
    
    public EnchantmentInstance(final Enchantment enchantment, final int level) {
        super(enchantment.getRandomWeight());
        this.enchantment = enchantment;
        this.level = level;
    }
    
    public EnchantmentInstance(final int n, final int n2) {
        this(Enchantment.byId[n], n2);
    }
}
