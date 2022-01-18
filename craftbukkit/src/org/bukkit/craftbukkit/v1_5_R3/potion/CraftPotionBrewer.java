// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.potion;

import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import org.bukkit.potion.PotionEffectType;
import net.minecraft.server.v1_5_R3.MobEffect;
import java.util.ArrayList;
import org.bukkit.potion.PotionEffect;
import java.util.Collection;
import java.util.Map;
import org.bukkit.potion.PotionBrewer;

public class CraftPotionBrewer implements PotionBrewer
{
    private static final Map<Integer, Collection<PotionEffect>> cache;
    
    public Collection<PotionEffect> getEffectsFromDamage(final int damage) {
        if (CraftPotionBrewer.cache.containsKey(damage)) {
            return CraftPotionBrewer.cache.get(damage);
        }
        final List<?> mcEffects = (List<?>)net.minecraft.server.v1_5_R3.PotionBrewer.getEffects(damage, false);
        final List<PotionEffect> effects = new ArrayList<PotionEffect>();
        if (mcEffects == null) {
            return effects;
        }
        for (final Object raw : mcEffects) {
            if (raw != null) {
                if (!(raw instanceof MobEffect)) {
                    continue;
                }
                final MobEffect mcEffect = (MobEffect)raw;
                final PotionEffect effect = new PotionEffect(PotionEffectType.getById(mcEffect.getEffectId()), mcEffect.getDuration(), mcEffect.getAmplifier());
                effects.add(effect);
            }
        }
        CraftPotionBrewer.cache.put(damage, effects);
        return effects;
    }
    
    public PotionEffect createEffect(final PotionEffectType potion, final int duration, final int amplifier) {
        return new PotionEffect(potion, potion.isInstant() ? 1 : ((int)(duration * potion.getDurationModifier())), amplifier);
    }
    
    static {
        cache = Maps.newHashMap();
    }
}
