// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Boss;

import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import java.util.Iterator;
import org.bukkit.entity.Entity;
import me.ThaH3lper.com.EpicBoss;

public class BossCalculations
{
    private EpicBoss eb;
    
    public BossCalculations(final EpicBoss boss) {
        this.eb = boss;
    }
    
    public boolean isBoss(final Entity e) {
        if (this.eb.BossList != null) {
            for (final Boss boss : this.eb.BossList) {
                if (boss.getLivingEntity() != null && e.getEntityId() == boss.getId()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean isBossLiv(final LivingEntity e) {
        if (this.eb.BossList != null) {
            for (final Boss boss : this.eb.BossList) {
                if (boss.getLivingEntity() != null && e.getEntityId() == boss.getId()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public Boss getBoss(final Entity e) {
        final int id = e.getEntityId();
        if (this.eb.BossList != null) {
            for (final Boss boss : this.eb.BossList) {
                if (!boss.getSaved() && id == boss.getId()) {
                    return boss;
                }
            }
        }
        return null;
    }
    
    public Boolean BossHited(final LivingEntity e) {
        if (this.eb.HeroesEnabled) {
            return false;
        }
        if (e.getHealth() == e.getMaxHealth()) {
            return true;
        }
        return false;
    }
    
    public Boolean BossExist(final Boss b) {
        if (b.getLivingEntity() != null) {
            for (final World w : Bukkit.getWorlds()) {
                try {
                    for (final Entity e : w.getEntities()) {
                        if (b.getId() == e.getEntityId()) {
                            return true;
                        }
                    }
                }
                catch (Exception e2) {
                    return null;
                }
            }
        }
        return false;
    }
}
