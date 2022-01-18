// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power.types;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public interface PowerHit extends Power
{
    void hit(final Player p0, final LivingEntity p1);
}
