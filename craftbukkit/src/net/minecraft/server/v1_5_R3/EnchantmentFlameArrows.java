// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EnchantmentFlameArrows extends Enchantment
{
    public EnchantmentFlameArrows(final int i, final int j) {
        super(i, j, EnchantmentSlotType.BOW);
        this.b("arrowFire");
    }
    
    public int a(final int n) {
        return 20;
    }
    
    public int b(final int n) {
        return 50;
    }
    
    public int getMaxLevel() {
        return 1;
    }
}
