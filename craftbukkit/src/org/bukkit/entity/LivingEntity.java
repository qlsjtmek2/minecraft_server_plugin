// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.entity;

import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffectType;
import java.util.Collection;
import org.bukkit.potion.PotionEffect;
import org.bukkit.block.Block;
import java.util.List;
import java.util.HashSet;
import org.bukkit.Location;

public interface LivingEntity extends Entity, Damageable
{
    double getEyeHeight();
    
    double getEyeHeight(final boolean p0);
    
    Location getEyeLocation();
    
    List<Block> getLineOfSight(final HashSet<Byte> p0, final int p1);
    
    Block getTargetBlock(final HashSet<Byte> p0, final int p1);
    
    List<Block> getLastTwoTargetBlocks(final HashSet<Byte> p0, final int p1);
    
    @Deprecated
    Egg throwEgg();
    
    @Deprecated
    Snowball throwSnowball();
    
    @Deprecated
    Arrow shootArrow();
    
     <T extends Projectile> T launchProjectile(final Class<? extends T> p0);
    
    int getRemainingAir();
    
    void setRemainingAir(final int p0);
    
    int getMaximumAir();
    
    void setMaximumAir(final int p0);
    
    int getMaximumNoDamageTicks();
    
    void setMaximumNoDamageTicks(final int p0);
    
    int getLastDamage();
    
    void setLastDamage(final int p0);
    
    int getNoDamageTicks();
    
    void setNoDamageTicks(final int p0);
    
    Player getKiller();
    
    boolean addPotionEffect(final PotionEffect p0);
    
    boolean addPotionEffect(final PotionEffect p0, final boolean p1);
    
    boolean addPotionEffects(final Collection<PotionEffect> p0);
    
    boolean hasPotionEffect(final PotionEffectType p0);
    
    void removePotionEffect(final PotionEffectType p0);
    
    Collection<PotionEffect> getActivePotionEffects();
    
    boolean hasLineOfSight(final Entity p0);
    
    boolean getRemoveWhenFarAway();
    
    void setRemoveWhenFarAway(final boolean p0);
    
    EntityEquipment getEquipment();
    
    void setCanPickupItems(final boolean p0);
    
    boolean getCanPickupItems();
    
    void setCustomName(final String p0);
    
    String getCustomName();
    
    void setCustomNameVisible(final boolean p0);
    
    boolean isCustomNameVisible();
}
