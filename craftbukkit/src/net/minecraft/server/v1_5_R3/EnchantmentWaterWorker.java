// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EnchantmentWaterWorker extends Enchantment
{
    public EnchantmentWaterWorker(final int i, final int j) {
        super(i, j, EnchantmentSlotType.ARMOR_HEAD);
        this.b("waterWorker");
    }
    
    public int a(final int n) {
        return 1;
    }
    
    public int b(final int n) {
        return this.a(n) + 40;
    }
    
    public int getMaxLevel() {
        return 1;
    }
}
