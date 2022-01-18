// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EnchantmentLootBonus extends Enchantment
{
    protected EnchantmentLootBonus(final int i, final int j, final EnchantmentSlotType enchantmentslottype) {
        super(i, j, enchantmentslottype);
        this.b("lootBonus");
        if (enchantmentslottype == EnchantmentSlotType.DIGGER) {
            this.b("lootBonusDigger");
        }
    }
    
    public int a(final int n) {
        return 15 + (n - 1) * 9;
    }
    
    public int b(final int i) {
        return super.a(i) + 50;
    }
    
    public int getMaxLevel() {
        return 3;
    }
    
    public boolean a(final Enchantment enchantment) {
        return super.a(enchantment) && enchantment.id != EnchantmentLootBonus.SILK_TOUCH.id;
    }
}
