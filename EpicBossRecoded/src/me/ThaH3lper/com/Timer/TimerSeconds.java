// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Timer;

import java.util.Iterator;
import org.bukkit.Effect;
import me.ThaH3lper.com.Boss.Boss;
import org.bukkit.plugin.Plugin;
import me.ThaH3lper.com.EpicBoss;
import me.ThaH3lper.com.Timer.Spawn.Despawn;

public class TimerSeconds
{
    public Despawn despawn;
    private EpicBoss eb;
    
    public TimerSeconds(final EpicBoss boss) {
        this.eb = boss;
        this.despawn = new Despawn(this.eb);
        this.eb.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this.eb, (Runnable)new Runnable() {
            @Override
            public void run() {
                TimerSeconds.this.despawn.DeSpawnEvent(TimerSeconds.this.eb);
                TimerSeconds.this.eb.timerstuff.lower();
                TimerSeconds.this.skillsShow();
            }
        }, 100L, 100L);
    }
    
    public void skillsShow() {
        for (final Boss b : this.eb.BossList) {
            if (b.getEffects() != null && !b.getSaved()) {
                for (final String s : b.getEffects()) {
                    if (s.equalsIgnoreCase("fire")) {
                        b.getWorkingLocation().getWorld().playEffect(b.getWorkingLocation(), Effect.MOBSPAWNER_FLAMES, 0);
                    }
                    if (s.equalsIgnoreCase("ender")) {
                        b.getWorkingLocation().getWorld().playEffect(b.getWorkingLocation(), Effect.ENDER_SIGNAL, 0);
                    }
                }
            }
        }
    }
}
