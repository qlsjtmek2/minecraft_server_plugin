// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EnchantmentWeaponDamage extends Enchantment
{
    private static final String[] C;
    private static final int[] D;
    private static final int[] E;
    private static final int[] F;
    public final int a;
    
    public EnchantmentWeaponDamage(final int i, final int j, final int a) {
        super(i, j, EnchantmentSlotType.WEAPON);
        this.a = a;
    }
    
    public int a(final int n) {
        return EnchantmentWeaponDamage.D[this.a] + (n - 1) * EnchantmentWeaponDamage.E[this.a];
    }
    
    public int b(final int n) {
        return this.a(n) + EnchantmentWeaponDamage.F[this.a];
    }
    
    public int getMaxLevel() {
        return 5;
    }
    
    public int a(final int n, final EntityLiving entityLiving) {
        if (this.a == 0) {
            return MathHelper.d(n * 2.75f);
        }
        if (this.a == 1 && entityLiving.getMonsterType() == EnumMonsterType.UNDEAD) {
            return MathHelper.d(n * 4.5f);
        }
        if (this.a == 2 && entityLiving.getMonsterType() == EnumMonsterType.ARTHROPOD) {
            return MathHelper.d(n * 4.5f);
        }
        return 0;
    }
    
    public String a() {
        return "enchantment.damage." + EnchantmentWeaponDamage.C[this.a];
    }
    
    public boolean a(final Enchantment enchantment) {
        return !(enchantment instanceof EnchantmentWeaponDamage);
    }
    
    public boolean canEnchant(final ItemStack itemstack) {
        return itemstack.getItem() instanceof ItemAxe || super.canEnchant(itemstack);
    }
    
    static {
        C = new String[] { "all", "undead", "arthropods" };
        D = new int[] { 1, 5, 5 };
        E = new int[] { 11, 8, 8 };
        F = new int[] { 20, 20, 20 };
    }
}
