// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EnchantmentArrowKnockback extends Enchantment
{
    public EnchantmentArrowKnockback(final int i, final int j) {
        super(i, j, EnchantmentSlotType.BOW);
        this.b("arrowKnockback");
    }
    
    public int a(final int n) {
        return 12 + (n - 1) * 20;
    }
    
    public int b(final int n) {
        return this.a(n) + 25;
    }
    
    public int getMaxLevel() {
        return 2;
    }
}
