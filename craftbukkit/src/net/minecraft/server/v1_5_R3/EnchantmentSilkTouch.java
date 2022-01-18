// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EnchantmentSilkTouch extends Enchantment
{
    protected EnchantmentSilkTouch(final int i, final int j) {
        super(i, j, EnchantmentSlotType.DIGGER);
        this.b("untouching");
    }
    
    public int a(final int n) {
        return 15;
    }
    
    public int b(final int i) {
        return super.a(i) + 50;
    }
    
    public int getMaxLevel() {
        return 1;
    }
    
    public boolean a(final Enchantment enchantment) {
        return super.a(enchantment) && enchantment.id != EnchantmentSilkTouch.LOOT_BONUS_BLOCKS.id;
    }
    
    public boolean canEnchant(final ItemStack itemstack) {
        return itemstack.getItem().id == Item.SHEARS.id || super.canEnchant(itemstack);
    }
}
