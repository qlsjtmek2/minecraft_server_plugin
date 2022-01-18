// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class EnchantmentDurability extends Enchantment
{
    protected EnchantmentDurability(final int i, final int j) {
        super(i, j, EnchantmentSlotType.DIGGER);
        this.b("durability");
    }
    
    public int a(final int n) {
        return 5 + (n - 1) * 8;
    }
    
    public int b(final int i) {
        return super.a(i) + 50;
    }
    
    public int getMaxLevel() {
        return 3;
    }
    
    public boolean canEnchant(final ItemStack itemstack) {
        return itemstack.g() || super.canEnchant(itemstack);
    }
    
    public static boolean a(final ItemStack itemStack, final int n, final Random random) {
        return (!(itemStack.getItem() instanceof ItemArmor) || random.nextFloat() >= 0.6f) && random.nextInt(n + 1) > 0;
    }
}
