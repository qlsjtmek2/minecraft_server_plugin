// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.potion;

public enum PotionType
{
    WATER(0, (PotionEffectType)null, 0), 
    REGEN(1, PotionEffectType.REGENERATION, 2), 
    SPEED(2, PotionEffectType.SPEED, 2), 
    FIRE_RESISTANCE(3, PotionEffectType.FIRE_RESISTANCE, 1), 
    POISON(4, PotionEffectType.POISON, 2), 
    INSTANT_HEAL(5, PotionEffectType.HEAL, 2), 
    NIGHT_VISION(6, PotionEffectType.NIGHT_VISION, 1), 
    WEAKNESS(8, PotionEffectType.WEAKNESS, 1), 
    STRENGTH(9, PotionEffectType.INCREASE_DAMAGE, 2), 
    SLOWNESS(10, PotionEffectType.SLOW, 1), 
    INSTANT_DAMAGE(12, PotionEffectType.HARM, 2), 
    INVISIBILITY(14, PotionEffectType.INVISIBILITY, 1);
    
    private final int damageValue;
    private final int maxLevel;
    private final PotionEffectType effect;
    
    private PotionType(final int damageValue, final PotionEffectType effect, final int maxLevel) {
        this.damageValue = damageValue;
        this.effect = effect;
        this.maxLevel = maxLevel;
    }
    
    public PotionEffectType getEffectType() {
        return this.effect;
    }
    
    public int getDamageValue() {
        return this.damageValue;
    }
    
    public int getMaxLevel() {
        return this.maxLevel;
    }
    
    public boolean isInstant() {
        return this.effect == null || this.effect.isInstant();
    }
    
    public static PotionType getByDamageValue(final int damage) {
        for (final PotionType type : values()) {
            if (type.damageValue == damage) {
                return type;
            }
        }
        return null;
    }
    
    public static PotionType getByEffect(final PotionEffectType effectType) {
        if (effectType == null) {
            return PotionType.WATER;
        }
        for (final PotionType type : values()) {
            if (effectType.equals(type.effect)) {
                return type;
            }
        }
        return null;
    }
}
