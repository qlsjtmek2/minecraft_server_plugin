// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EnchantmentArrowDamage extends Enchantment
{
    public EnchantmentArrowDamage(final int i, final int j) {
        super(i, j, EnchantmentSlotType.BOW);
        this.b("arrowDamage");
    }
    
    public int a(final int n) {
        return 1 + (n - 1) * 10;
    }
    
    public int b(final int n) {
        return this.a(n) + 15;
    }
    
    public int getMaxLevel() {
        return 5;
    }
}
