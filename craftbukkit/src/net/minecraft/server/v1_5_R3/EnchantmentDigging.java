// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EnchantmentDigging extends Enchantment
{
    protected EnchantmentDigging(final int i, final int j) {
        super(i, j, EnchantmentSlotType.DIGGER);
        this.b("digging");
    }
    
    public int a(final int n) {
        return 1 + 10 * (n - 1);
    }
    
    public int b(final int i) {
        return super.a(i) + 50;
    }
    
    public int getMaxLevel() {
        return 5;
    }
    
    public boolean canEnchant(final ItemStack itemstack) {
        return itemstack.getItem().id == Item.SHEARS.id || super.canEnchant(itemstack);
    }
}
