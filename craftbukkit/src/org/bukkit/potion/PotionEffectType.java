// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.potion;

import java.util.HashMap;
import org.apache.commons.lang.Validate;
import java.util.Map;

public abstract class PotionEffectType
{
    public static final PotionEffectType SPEED;
    public static final PotionEffectType SLOW;
    public static final PotionEffectType FAST_DIGGING;
    public static final PotionEffectType SLOW_DIGGING;
    public static final PotionEffectType INCREASE_DAMAGE;
    public static final PotionEffectType HEAL;
    public static final PotionEffectType HARM;
    public static final PotionEffectType JUMP;
    public static final PotionEffectType CONFUSION;
    public static final PotionEffectType REGENERATION;
    public static final PotionEffectType DAMAGE_RESISTANCE;
    public static final PotionEffectType FIRE_RESISTANCE;
    public static final PotionEffectType WATER_BREATHING;
    public static final PotionEffectType INVISIBILITY;
    public static final PotionEffectType BLINDNESS;
    public static final PotionEffectType NIGHT_VISION;
    public static final PotionEffectType HUNGER;
    public static final PotionEffectType WEAKNESS;
    public static final PotionEffectType POISON;
    public static final PotionEffectType WITHER;
    private final int id;
    private static final PotionEffectType[] byId;
    private static final Map<String, PotionEffectType> byName;
    private static boolean acceptingNew;
    
    protected PotionEffectType(final int id) {
        this.id = id;
    }
    
    public PotionEffect createEffect(final int duration, final int amplifier) {
        return Potion.getBrewer().createEffect(this, duration, amplifier);
    }
    
    public abstract double getDurationModifier();
    
    public int getId() {
        return this.id;
    }
    
    public abstract String getName();
    
    public abstract boolean isInstant();
    
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PotionEffectType)) {
            return false;
        }
        final PotionEffectType other = (PotionEffectType)obj;
        return this.id == other.id;
    }
    
    public int hashCode() {
        return this.id;
    }
    
    public String toString() {
        return "PotionEffectType[" + this.id + ", " + this.getName() + "]";
    }
    
    public static PotionEffectType getById(final int id) {
        if (id >= PotionEffectType.byId.length || id < 0) {
            return null;
        }
        return PotionEffectType.byId[id];
    }
    
    public static PotionEffectType getByName(final String name) {
        Validate.notNull(name, "name cannot be null");
        return PotionEffectType.byName.get(name.toLowerCase());
    }
    
    public static void registerPotionEffectType(final PotionEffectType type) {
        if (PotionEffectType.byId[type.id] != null || PotionEffectType.byName.containsKey(type.getName().toLowerCase())) {
            throw new IllegalArgumentException("Cannot set already-set type");
        }
        if (!PotionEffectType.acceptingNew) {
            throw new IllegalStateException("No longer accepting new potion effect types (can only be done by the server implementation)");
        }
        PotionEffectType.byId[type.id] = type;
        PotionEffectType.byName.put(type.getName().toLowerCase(), type);
    }
    
    public static void stopAcceptingRegistrations() {
        PotionEffectType.acceptingNew = false;
    }
    
    public static PotionEffectType[] values() {
        return PotionEffectType.byId.clone();
    }
    
    static {
        SPEED = new PotionEffectTypeWrapper(1);
        SLOW = new PotionEffectTypeWrapper(2);
        FAST_DIGGING = new PotionEffectTypeWrapper(3);
        SLOW_DIGGING = new PotionEffectTypeWrapper(4);
        INCREASE_DAMAGE = new PotionEffectTypeWrapper(5);
        HEAL = new PotionEffectTypeWrapper(6);
        HARM = new PotionEffectTypeWrapper(7);
        JUMP = new PotionEffectTypeWrapper(8);
        CONFUSION = new PotionEffectTypeWrapper(9);
        REGENERATION = new PotionEffectTypeWrapper(10);
        DAMAGE_RESISTANCE = new PotionEffectTypeWrapper(11);
        FIRE_RESISTANCE = new PotionEffectTypeWrapper(12);
        WATER_BREATHING = new PotionEffectTypeWrapper(13);
        INVISIBILITY = new PotionEffectTypeWrapper(14);
        BLINDNESS = new PotionEffectTypeWrapper(15);
        NIGHT_VISION = new PotionEffectTypeWrapper(16);
        HUNGER = new PotionEffectTypeWrapper(17);
        WEAKNESS = new PotionEffectTypeWrapper(18);
        POISON = new PotionEffectTypeWrapper(19);
        WITHER = new PotionEffectTypeWrapper(20);
        byId = new PotionEffectType[21];
        byName = new HashMap<String, PotionEffectType>();
        PotionEffectType.acceptingNew = true;
    }
}
