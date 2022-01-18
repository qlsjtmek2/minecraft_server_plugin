// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Spawning;

import org.bukkit.block.Biome;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import me.ThaH3lper.com.LoadBosses.LoadBoss;
import java.util.Iterator;
import me.ThaH3lper.com.Boss.Boss;
import org.bukkit.event.entity.CreatureSpawnEvent;
import java.util.Random;
import me.ThaH3lper.com.EpicBoss;
import org.bukkit.event.Listener;

public class SpawnListener implements Listener
{
    EpicBoss eb;
    Random r;
    
    public SpawnListener(final EpicBoss eb) {
        this.r = new Random();
        this.eb = eb;
    }
    
    @EventHandler
    public void SpawningMob(final CreatureSpawnEvent e) {
        if (e.isCancelled()) {
            return;
        }
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            for (final Spawnings sp : this.eb.SpawningsList) {
                if (this.passSpawnReasons(e.getEntity(), sp)) {
                    final LoadBoss lb = sp.loadBoss;
                    final Boss boss = new Boss(lb.getName(), lb.getHealth(), e.getLocation(), lb.getType(), lb.getDamage(), lb.getShowhp(), lb.getItems(), lb.getSkills(), lb.getShowtitle(), lb.getSkin());
                    this.eb.BossList.add(boss);
                    this.eb.timer.despawn.DeSpawnBoss(boss);
                    boss.setNatural(true);
                    e.getEntity().remove();
                }
            }
        }
    }
    
    public boolean passSpawnReasons(final LivingEntity l, final Spawnings sp) {
        return this.seeType(l, sp) && this.seeChance(l, sp) && this.seeWorld(l, sp) && this.seeBiome(l, sp);
    }
    
    public boolean seeType(final LivingEntity l, final Spawnings sp) {
        final String[] types = sp.Replace.split(",");
        String[] array;
        for (int length = (array = types).length, i = 0; i < length; ++i) {
            final String type = array[i];
            if (type.equals("all")) {
                return true;
            }
            final Entity dummy = this.eb.mobs.SpawnMob(type, l.getLocation());
            if (dummy.getType().equals((Object)l.getType())) {
                dummy.remove();
                return true;
            }
            dummy.remove();
        }
        return false;
    }
    
    public boolean seeChance(final LivingEntity l, final Spawnings sp) {
        return sp.chance > this.r.nextFloat();
    }
    
    public boolean seeWorld(final LivingEntity l, final Spawnings sp) {
        final String[] worlds = sp.worlds.split(",");
        String[] array;
        for (int length = (array = worlds).length, i = 0; i < length; ++i) {
            final String w = array[i];
            if (l.getWorld().getName().equals(w)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean seeBiome(final LivingEntity l, final Spawnings sp) {
        final String[] Bio = sp.Biomes.split(",");
        String[] array;
        for (int length = (array = Bio).length, i = 0; i < length; ++i) {
            final String b = array[i];
            if (b.equals("all")) {
                return true;
            }
            if (l.getWorld().getBiome(l.getLocation().getBlockX(), l.getLocation().getBlockZ()).equals((Object)Biome.valueOf(b))) {
                return true;
            }
        }
        return false;
    }
}
