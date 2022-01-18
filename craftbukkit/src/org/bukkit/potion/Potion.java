// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.potion;

import com.google.common.collect.ImmutableList;
import java.util.Collection;
import org.bukkit.entity.LivingEntity;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.apache.commons.lang.Validate;

public class Potion
{
    private boolean extended;
    private boolean splash;
    private int level;
    private int name;
    private PotionType type;
    private static PotionBrewer brewer;
    private static final int EXTENDED_BIT = 64;
    private static final int POTION_BIT = 15;
    private static final int SPLASH_BIT = 16384;
    private static final int TIER_BIT = 32;
    private static final int TIER_SHIFT = 5;
    private static final int NAME_BIT = 63;
    
    public Potion(final PotionType type) {
        this.extended = false;
        this.splash = false;
        this.level = 1;
        this.name = -1;
        this.type = type;
        if (type != null) {
            this.name = type.getDamageValue();
        }
        if (type == null || type == PotionType.WATER) {
            this.level = 0;
        }
    }
    
    public Potion(final PotionType type, final Tier tier) {
        this(type, (tier == Tier.TWO) ? 2 : 1);
        Validate.notNull(type, "Type cannot be null");
    }
    
    public Potion(final PotionType type, final Tier tier, final boolean splash) {
        this(type, (tier == Tier.TWO) ? 2 : 1, splash);
    }
    
    public Potion(final PotionType type, final Tier tier, final boolean splash, final boolean extended) {
        this(type, tier, splash);
        this.extended = extended;
    }
    
    public Potion(final PotionType type, final int level) {
        this(type);
        Validate.notNull(type, "Type cannot be null");
        Validate.isTrue(type != PotionType.WATER, "Water bottles don't have a level!");
        Validate.isTrue(level > 0 && level < 3, "Level must be 1 or 2");
        this.level = level;
    }
    
    public Potion(final PotionType type, final int level, final boolean splash) {
        this(type, level);
        this.splash = splash;
    }
    
    public Potion(final PotionType type, final int level, final boolean splash, final boolean extended) {
        this(type, level, splash);
        this.extended = extended;
    }
    
    public Potion(final int name) {
        this(PotionType.getByDamageValue(name & 0xF));
        this.name = (name & 0x3F);
        if ((name & 0xF) == 0x0) {
            this.type = null;
        }
    }
    
    public Potion splash() {
        this.setSplash(true);
        return this;
    }
    
    public Potion extend() {
        this.setHasExtendedDuration(true);
        return this;
    }
    
    public void apply(final ItemStack to) {
        Validate.notNull(to, "itemstack cannot be null");
        Validate.isTrue(to.getType() == Material.POTION, "given itemstack is not a potion");
        to.setDurability(this.toDamageValue());
    }
    
    public void apply(final LivingEntity to) {
        Validate.notNull(to, "entity cannot be null");
        to.addPotionEffects(this.getEffects());
    }
    
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        final Potion other = (Potion)obj;
        return this.extended == other.extended && this.splash == other.splash && this.level == other.level && this.type == other.type;
    }
    
    public Collection<PotionEffect> getEffects() {
        if (this.type == null) {
            return (Collection<PotionEffect>)ImmutableList.of();
        }
        return getBrewer().getEffectsFromDamage(this.toDamageValue());
    }
    
    public int getLevel() {
        return this.level;
    }
    
    @Deprecated
    public Tier getTier() {
        return (this.level == 2) ? Tier.TWO : Tier.ONE;
    }
    
    public PotionType getType() {
        return this.type;
    }
    
    public boolean hasExtendedDuration() {
        return this.extended;
    }
    
    public int hashCode() {
        final int prime = 31;
        int result = 31 + this.level;
        result = 31 * result + (this.extended ? 1231 : 1237);
        result = 31 * result + (this.splash ? 1231 : 1237);
        result = 31 * result + ((this.type == null) ? 0 : this.type.hashCode());
        return result;
    }
    
    public boolean isSplash() {
        return this.splash;
    }
    
    public void setHasExtendedDuration(final boolean isExtended) {
        Validate.isTrue(this.type == null || !this.type.isInstant(), "Instant potions cannot be extended");
        this.extended = isExtended;
    }
    
    public void setSplash(final boolean isSplash) {
        this.splash = isSplash;
    }
    
    @Deprecated
    public void setTier(final Tier tier) {
        Validate.notNull(tier, "tier cannot be null");
        this.level = ((tier == Tier.TWO) ? 2 : 1);
    }
    
    public void setType(final PotionType type) {
        this.type = type;
    }
    
    public void setLevel(final int level) {
        Validate.notNull(this.type, "No-effect potions don't have a level.");
        final int max = this.type.getMaxLevel();
        Validate.isTrue(level > 0 && level <= max, "Level must be " + ((max == 1) ? "" : "between 1 and ") + max + " for this potion");
        this.level = level;
    }
    
    public short toDamageValue() {
        if (this.type == PotionType.WATER) {
            return 0;
        }
        short damage;
        if (this.type == null) {
            damage = (short)((this.name == 0) ? 8192 : this.name);
        }
        else {
            damage = (short)(this.level - 1);
            damage <<= 5;
            damage |= (short)this.type.getDamageValue();
        }
        if (this.splash) {
            damage |= 0x4000;
        }
        if (this.extended) {
            damage |= 0x40;
        }
        return damage;
    }
    
    public ItemStack toItemStack(final int amount) {
        return new ItemStack(Material.POTION, amount, this.toDamageValue());
    }
    
    public static Potion fromDamage(final int damage) {
        final PotionType type = PotionType.getByDamageValue(damage & 0xF);
        Potion potion;
        if (type == null || (type == PotionType.WATER && damage != 0)) {
            potion = new Potion(damage & 0x3F);
        }
        else {
            int level = (damage & 0x20) >> 5;
            ++level;
            potion = new Potion(type, level);
        }
        if ((damage & 0x4000) > 0) {
            potion = potion.splash();
        }
        if ((damage & 0x40) > 0) {
            potion = potion.extend();
        }
        return potion;
    }
    
    public static Potion fromItemStack(final ItemStack item) {
        Validate.notNull(item, "item cannot be null");
        if (item.getType() != Material.POTION) {
            throw new IllegalArgumentException("item is not a potion");
        }
        return fromDamage(item.getDurability());
    }
    
    public static PotionBrewer getBrewer() {
        return Potion.brewer;
    }
    
    public static void setPotionBrewer(final PotionBrewer other) {
        if (Potion.brewer != null) {
            throw new IllegalArgumentException("brewer can only be set internally");
        }
        Potion.brewer = other;
    }
    
    public int getNameId() {
        return this.name;
    }
    
    @Deprecated
    public enum Tier
    {
        ONE(0), 
        TWO(32);
        
        private int damageBit;
        
        private Tier(final int bit) {
            this.damageBit = bit;
        }
        
        public int getDamageBit() {
            return this.damageBit;
        }
        
        public static Tier getByDamageBit(final int damageBit) {
            for (final Tier tier : values()) {
                if (tier.damageBit == damageBit) {
                    return tier;
                }
            }
            return null;
        }
    }
}
