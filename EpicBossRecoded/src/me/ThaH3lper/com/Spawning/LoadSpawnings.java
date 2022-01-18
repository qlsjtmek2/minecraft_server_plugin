// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Spawning;

import me.ThaH3lper.com.LoadBosses.LoadBoss;
import java.util.Iterator;
import me.ThaH3lper.com.EpicBoss;

public class LoadSpawnings
{
    EpicBoss eb;
    
    public LoadSpawnings(final EpicBoss eb) {
        this.eb = eb;
        this.LoadSpawning();
    }
    
    public void LoadSpawning() {
        if (this.eb.Spawning.getCustomConfig().contains("Spawning")) {
            for (final String name : this.eb.Spawning.getCustomConfig().getConfigurationSection("Spawning").getKeys(false)) {
                String replace = null;
                String worlds = null;
                String Biomes = null;
                Float chance = null;
                boolean remove = false;
                int limit = 0;
                if (this.getLoadBoss(this.eb.Spawning.getCustomConfig().getString("Spawning." + name + ".Boss")) != null) {
                    final LoadBoss loadBoss = this.getLoadBoss(this.eb.Spawning.getCustomConfig().getString("Spawning." + name + ".Boss"));
                    if (this.eb.Spawning.getCustomConfig().contains("Spawning." + name + ".MobReplace")) {
                        replace = this.eb.Spawning.getCustomConfig().getString("Spawning." + name + ".MobReplace");
                    }
                    if (this.eb.Spawning.getCustomConfig().contains("Spawning." + name + ".Chance")) {
                        chance = (float)this.eb.Spawning.getCustomConfig().getDouble("Spawning." + name + ".Chance");
                    }
                    if (this.eb.Spawning.getCustomConfig().contains("Spawning." + name + ".Worlds")) {
                        worlds = this.eb.Spawning.getCustomConfig().getString("Spawning." + name + ".Worlds");
                    }
                    if (this.eb.Spawning.getCustomConfig().contains("Spawning." + name + ".Biomes")) {
                        Biomes = this.eb.Spawning.getCustomConfig().getString("Spawning." + name + ".Biomes");
                    }
                    if (this.eb.Spawning.getCustomConfig().contains("Spawning." + name + ".RemoveMob")) {
                        remove = this.eb.Spawning.getCustomConfig().getBoolean("Spawning." + name + ".RemoveMob");
                    }
                    if (this.eb.Spawning.getCustomConfig().contains("Spawning." + name + ".Limit")) {
                        limit = this.eb.Spawning.getCustomConfig().getInt("Spawning." + name + ".Limit");
                    }
                    this.eb.SpawningsList.add(new Spawnings(loadBoss, replace, chance, worlds, remove, limit, Biomes));
                }
            }
        }
    }
    
    public LoadBoss getLoadBoss(final String s) {
        for (final LoadBoss lb : this.eb.BossLoadList) {
            if (lb.getName().equals(s)) {
                return lb;
            }
        }
        return null;
    }
}
