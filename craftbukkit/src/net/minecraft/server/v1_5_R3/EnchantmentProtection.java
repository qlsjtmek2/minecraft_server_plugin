// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class EnchantmentProtection extends Enchantment
{
    private static final String[] C;
    private static final int[] D;
    private static final int[] E;
    private static final int[] F;
    public final int a;
    
    public EnchantmentProtection(final int i, final int j, final int a) {
        super(i, j, EnchantmentSlotType.ARMOR);
        this.a = a;
        if (a == 2) {
            this.slot = EnchantmentSlotType.ARMOR_FEET;
        }
    }
    
    public int a(final int n) {
        return EnchantmentProtection.D[this.a] + (n - 1) * EnchantmentProtection.E[this.a];
    }
    
    public int b(final int n) {
        return this.a(n) + EnchantmentProtection.F[this.a];
    }
    
    public int getMaxLevel() {
        return 4;
    }
    
    public int a(final int n, final DamageSource damageSource) {
        if (damageSource.ignoresInvulnerability()) {
            return 0;
        }
        final float n2 = (6 + n * n) / 3.0f;
        if (this.a == 0) {
            return MathHelper.d(n2 * 0.75f);
        }
        if (this.a == 1 && damageSource.m()) {
            return MathHelper.d(n2 * 1.25f);
        }
        if (this.a == 2 && damageSource == DamageSource.FALL) {
            return MathHelper.d(n2 * 2.5f);
        }
        if (this.a == 3 && damageSource.c()) {
            return MathHelper.d(n2 * 1.5f);
        }
        if (this.a == 4 && damageSource.a()) {
            return MathHelper.d(n2 * 1.5f);
        }
        return 0;
    }
    
    public String a() {
        return "enchantment.protect." + EnchantmentProtection.C[this.a];
    }
    
    public boolean a(final Enchantment enchantment) {
        if (enchantment instanceof EnchantmentProtection) {
            final EnchantmentProtection enchantmentProtection = (EnchantmentProtection)enchantment;
            return enchantmentProtection.a != this.a && (this.a == 2 || enchantmentProtection.a == 2);
        }
        return super.a(enchantment);
    }
    
    public static int a(final Entity entity, int n) {
        final int enchantmentLevel = EnchantmentManager.getEnchantmentLevel(Enchantment.PROTECTION_FIRE.id, entity.getEquipment());
        if (enchantmentLevel > 0) {
            n -= MathHelper.d(n * (enchantmentLevel * 0.15f));
        }
        return n;
    }
    
    public static double a(final Entity entity, double n) {
        final int enchantmentLevel = EnchantmentManager.getEnchantmentLevel(Enchantment.PROTECTION_EXPLOSIONS.id, entity.getEquipment());
        if (enchantmentLevel > 0) {
            n -= MathHelper.floor(n * (enchantmentLevel * 0.15f));
        }
        return n;
    }
    
    static {
        C = new String[] { "all", "fire", "fall", "explosion", "projectile" };
        D = new int[] { 1, 10, 5, 5, 3 };
        E = new int[] { 11, 8, 6, 8, 6 };
        F = new int[] { 20, 12, 10, 12, 15 };
    }
}
