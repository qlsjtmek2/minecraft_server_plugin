// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.enchantments;

import java.util.HashMap;
import org.bukkit.command.defaults.EnchantCommand;
import org.bukkit.inventory.ItemStack;
import java.util.Map;

public abstract class Enchantment
{
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
    private static final Map<Integer, Enchantment> byId;
    private static final Map<String, Enchantment> byName;
    private static boolean acceptingNew;
    private final int id;
    
    public Enchantment(final int id) {
        this.id = id;
    }
    
    public int getId() {
        return this.id;
    }
    
    public abstract String getName();
    
    public abstract int getMaxLevel();
    
    public abstract int getStartLevel();
    
    public abstract EnchantmentTarget getItemTarget();
    
    public abstract boolean conflictsWith(final Enchantment p0);
    
    public abstract boolean canEnchantItem(final ItemStack p0);
    
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Enchantment)) {
            return false;
        }
        final Enchantment other = (Enchantment)obj;
        return this.id == other.id;
    }
    
    public int hashCode() {
        return this.id;
    }
    
    public String toString() {
        return "Enchantment[" + this.id + ", " + this.getName() + "]";
    }
    
    public static void registerEnchantment(final Enchantment enchantment) {
        if (Enchantment.byId.containsKey(enchantment.id) || Enchantment.byName.containsKey(enchantment.getName())) {
            throw new IllegalArgumentException("Cannot set already-set enchantment");
        }
        if (!isAcceptingRegistrations()) {
            throw new IllegalStateException("No longer accepting new enchantments (can only be done by the server implementation)");
        }
        Enchantment.byId.put(enchantment.id, enchantment);
        Enchantment.byName.put(enchantment.getName(), enchantment);
    }
    
    public static boolean isAcceptingRegistrations() {
        return Enchantment.acceptingNew;
    }
    
    public static void stopAcceptingRegistrations() {
        Enchantment.acceptingNew = false;
        EnchantCommand.buildEnchantments();
    }
    
    public static Enchantment getById(final int id) {
        return Enchantment.byId.get(id);
    }
    
    public static Enchantment getByName(final String name) {
        return Enchantment.byName.get(name);
    }
    
    public static Enchantment[] values() {
        return Enchantment.byId.values().toArray(new Enchantment[Enchantment.byId.size()]);
    }
    
    static {
        PROTECTION_ENVIRONMENTAL = new EnchantmentWrapper(0);
        PROTECTION_FIRE = new EnchantmentWrapper(1);
        PROTECTION_FALL = new EnchantmentWrapper(2);
        PROTECTION_EXPLOSIONS = new EnchantmentWrapper(3);
        PROTECTION_PROJECTILE = new EnchantmentWrapper(4);
        OXYGEN = new EnchantmentWrapper(5);
        WATER_WORKER = new EnchantmentWrapper(6);
        THORNS = new EnchantmentWrapper(7);
        DAMAGE_ALL = new EnchantmentWrapper(16);
        DAMAGE_UNDEAD = new EnchantmentWrapper(17);
        DAMAGE_ARTHROPODS = new EnchantmentWrapper(18);
        KNOCKBACK = new EnchantmentWrapper(19);
        FIRE_ASPECT = new EnchantmentWrapper(20);
        LOOT_BONUS_MOBS = new EnchantmentWrapper(21);
        DIG_SPEED = new EnchantmentWrapper(32);
        SILK_TOUCH = new EnchantmentWrapper(33);
        DURABILITY = new EnchantmentWrapper(34);
        LOOT_BONUS_BLOCKS = new EnchantmentWrapper(35);
        ARROW_DAMAGE = new EnchantmentWrapper(48);
        ARROW_KNOCKBACK = new EnchantmentWrapper(49);
        ARROW_FIRE = new EnchantmentWrapper(50);
        ARROW_INFINITE = new EnchantmentWrapper(51);
        byId = new HashMap<Integer, Enchantment>();
        byName = new HashMap<String, Enchantment>();
        Enchantment.acceptingNew = true;
    }
}
