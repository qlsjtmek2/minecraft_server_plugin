// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import org.bukkit.craftbukkit.v1_5_R3.enchantments.CraftEnchantment;

public abstract class Enchantment
{
    public static final Enchantment[] byId;
    public static final Enchantment[] c;
    public static final Enchantment PROTECTION_ENVIRONMENTAL;
    public static final Enchantment PROTECTION_FIRE;
    public static final Enchantment PROTECTION_FALL;
    public static final Enchantment PROTECTION_EXPLOSIONS;
    public static final Enchantment PROTECTION_PROJECTILE;
    public static final Enchantment OXYGEN;
    public static final Enchantment WATER_WORKER;
    public static final Enchantment THORNS;
    public static final Enchantment DAMAGE_ALL;
    public static final Enchantment DAMAGE_UNDEAD;
    public static final Enchantment DAMAGE_ARTHROPODS;
    public static final Enchantment KNOCKBACK;
    public static final Enchantment FIRE_ASPECT;
    public static final Enchantment LOOT_BONUS_MOBS;
    public static final Enchantment DIG_SPEED;
    public static final Enchantment SILK_TOUCH;
    public static final Enchantment DURABILITY;
    public static final Enchantment LOOT_BONUS_BLOCKS;
    public static final Enchantment ARROW_DAMAGE;
    public static final Enchantment ARROW_KNOCKBACK;
    public static final Enchantment ARROW_FIRE;
    public static final Enchantment ARROW_INFINITE;
    public final int id;
    private final int weight;
    public EnchantmentSlotType slot;
    protected String name;
    
    protected Enchantment(final int i, final int j, final EnchantmentSlotType enchantmentslottype) {
        this.id = i;
        this.weight = j;
        this.slot = enchantmentslottype;
        if (Enchantment.byId[i] != null) {
            throw new IllegalArgumentException("Duplicate enchantment id!");
        }
        Enchantment.byId[i] = this;
        org.bukkit.enchantments.Enchantment.registerEnchantment(new CraftEnchantment(this));
    }
    
    public int getRandomWeight() {
        return this.weight;
    }
    
    public int getStartLevel() {
        return 1;
    }
    
    public int getMaxLevel() {
        return 1;
    }
    
    public int a(final int i) {
        return 1 + i * 10;
    }
    
    public int b(final int i) {
        return this.a(i) + 5;
    }
    
    public int a(final int i, final DamageSource damagesource) {
        return 0;
    }
    
    public int a(final int i, final EntityLiving entityliving) {
        return 0;
    }
    
    public boolean a(final Enchantment enchantment) {
        return this != enchantment;
    }
    
    public Enchantment b(final String s) {
        this.name = s;
        return this;
    }
    
    public String a() {
        return "enchantment." + this.name;
    }
    
    public String c(final int i) {
        final String s = LocaleI18n.get(this.a());
        return s + " " + LocaleI18n.get("enchantment.level." + i);
    }
    
    public boolean canEnchant(final ItemStack itemstack) {
        return this.slot.canEnchant(itemstack.getItem());
    }
    
    static {
        byId = new Enchantment[256];
        PROTECTION_ENVIRONMENTAL = new EnchantmentProtection(0, 10, 0);
        PROTECTION_FIRE = new EnchantmentProtection(1, 5, 1);
        PROTECTION_FALL = new EnchantmentProtection(2, 5, 2);
        PROTECTION_EXPLOSIONS = new EnchantmentProtection(3, 2, 3);
        PROTECTION_PROJECTILE = new EnchantmentProtection(4, 5, 4);
        OXYGEN = new EnchantmentOxygen(5, 2);
        WATER_WORKER = new EnchantmentWaterWorker(6, 2);
        THORNS = new EnchantmentThorns(7, 1);
        DAMAGE_ALL = new EnchantmentWeaponDamage(16, 10, 0);
        DAMAGE_UNDEAD = new EnchantmentWeaponDamage(17, 5, 1);
        DAMAGE_ARTHROPODS = new EnchantmentWeaponDamage(18, 5, 2);
        KNOCKBACK = new EnchantmentKnockback(19, 5);
        FIRE_ASPECT = new EnchantmentFire(20, 2);
        LOOT_BONUS_MOBS = new EnchantmentLootBonus(21, 2, EnchantmentSlotType.WEAPON);
        DIG_SPEED = new EnchantmentDigging(32, 10);
        SILK_TOUCH = new EnchantmentSilkTouch(33, 1);
        DURABILITY = new EnchantmentDurability(34, 5);
        LOOT_BONUS_BLOCKS = new EnchantmentLootBonus(35, 2, EnchantmentSlotType.DIGGER);
        ARROW_DAMAGE = new EnchantmentArrowDamage(48, 10);
        ARROW_KNOCKBACK = new EnchantmentArrowKnockback(49, 2);
        ARROW_FIRE = new EnchantmentFlameArrows(50, 2);
        ARROW_INFINITE = new EnchantmentInfiniteArrows(51, 1);
        final ArrayList arraylist = new ArrayList();
        for (final Enchantment enchantment : Enchantment.byId) {
            if (enchantment != null) {
                arraylist.add(enchantment);
            }
        }
        c = arraylist.toArray(new Enchantment[0]);
    }
}
