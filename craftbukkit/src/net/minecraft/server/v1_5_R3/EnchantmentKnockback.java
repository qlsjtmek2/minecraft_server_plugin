// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EnchantmentKnockback extends Enchantment
{
    protected EnchantmentKnockback(final int i, final int j) {
        super(i, j, EnchantmentSlotType.WEAPON);
        this.b("knockback");
    }
    
    public int a(final int n) {
        return 5 + 20 * (n - 1);
    }
    
    public int b(final int i) {
        return super.a(i) + 50;
    }
    
    public int getMaxLevel() {
        return 2;
    }
}
