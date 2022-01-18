// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EnchantmentOxygen extends Enchantment
{
    public EnchantmentOxygen(final int i, final int j) {
        super(i, j, EnchantmentSlotType.ARMOR_HEAD);
        this.b("oxygen");
    }
    
    public int a(final int n) {
        return 10 * n;
    }
    
    public int b(final int n) {
        return this.a(n) + 30;
    }
    
    public int getMaxLevel() {
        return 3;
    }
}
