// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.inventory.meta;

import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionEffect;
import java.util.List;

public interface PotionMeta extends ItemMeta
{
    boolean hasCustomEffects();
    
    List<PotionEffect> getCustomEffects();
    
    boolean addCustomEffect(final PotionEffect p0, final boolean p1);
    
    boolean removeCustomEffect(final PotionEffectType p0);
    
    boolean hasCustomEffect(final PotionEffectType p0);
    
    boolean setMainEffect(final PotionEffectType p0);
    
    boolean clearCustomEffects();
    
    PotionMeta clone();
}
