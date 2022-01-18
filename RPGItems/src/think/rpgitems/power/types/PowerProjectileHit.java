// 
// Decompiled by Procyon v0.5.30
// 

package think.rpgitems.power.types;

import org.bukkit.entity.Projectile;
import org.bukkit.entity.Player;

public interface PowerProjectileHit extends Power
{
    void projectileHit(final Player p0, final Projectile p1);
}
