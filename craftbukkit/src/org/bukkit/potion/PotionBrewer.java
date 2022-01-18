// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.potion;

import java.util.Collection;

public interface PotionBrewer
{
    PotionEffect createEffect(final PotionEffectType p0, final int p1, final int p2);
    
    Collection<PotionEffect> getEffectsFromDamage(final int p0);
}
