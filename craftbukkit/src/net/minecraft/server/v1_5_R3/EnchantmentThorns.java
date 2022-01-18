// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Random;

public class EnchantmentThorns extends Enchantment
{
    public EnchantmentThorns(final int i, final int j) {
        super(i, j, EnchantmentSlotType.ARMOR_TORSO);
        this.b("thorns");
    }
    
    public int a(final int n) {
        return 10 + 20 * (n - 1);
    }
    
    public int b(final int i) {
        return super.a(i) + 50;
    }
    
    public int getMaxLevel() {
        return 3;
    }
    
    public boolean canEnchant(final ItemStack itemstack) {
        return itemstack.getItem() instanceof ItemArmor || super.canEnchant(itemstack);
    }
    
    public static boolean a(final int n, final Random random) {
        return n > 0 && random.nextFloat() < 0.15f * n;
    }
    
    public static int b(final int n, final Random random) {
        if (n > 10) {
            return n - 10;
        }
        return 1 + random.nextInt(4);
    }
    
    public static void a(final Entity entity, final EntityLiving entityLiving, final Random random) {
        final int thornsEnchantmentLevel = EnchantmentManager.getThornsEnchantmentLevel(entityLiving);
        final ItemStack a = EnchantmentManager.a(Enchantment.THORNS, entityLiving);
        if (a(thornsEnchantmentLevel, random)) {
            entity.damageEntity(DamageSource.a(entityLiving), b(thornsEnchantmentLevel, random));
            entity.makeSound("damage.thorns", 0.5f, 1.0f);
            if (a != null) {
                a.damage(3, entityLiving);
            }
        }
        else if (a != null) {
            a.damage(1, entityLiving);
        }
    }
}
