// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Timer.Spawn;

import org.bukkit.Location;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.getspout.spoutapi.player.EntitySkinType;
import org.getspout.spoutapi.Spout;
import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import me.ThaH3lper.com.Boss.Boss;
import java.util.ArrayList;
import me.ThaH3lper.com.EpicBoss;

public class Despawn
{
    private EpicBoss eb;
    
    public Despawn(final EpicBoss eb) {
        this.eb = eb;
    }
    
    public void DeSpawnEvent(final EpicBoss eb) {
        final List<Boss> remove = new ArrayList<Boss>();
        if (eb.BossList != null) {
            for (final Boss boss : eb.BossList) {
                if (!boss.getSaved() && !boss.getNatural()) {
                    if (boss.getLocation() != null && !this.PlayersInside(boss.getLocation()) && !boss.getEntitySpawnName().equals("enderdragon")) {
                        boss.setSaved(true);
                        boss.setSavedLocation(boss.getLocation());
                        boss.getLivingEntity().remove();
                        if (eb.regain) {
                            boss.sethealth(boss.getMaxHealth());
                        }
                    }
                }
                else if (boss.getSaved()) {
                    if (this.PlayersInside(boss.getSavedLocation()) || boss.getNatural()) {
                        boss.setSaved(false);
                        final LivingEntity l = (LivingEntity)eb.mobs.SpawnMob(boss.getEntitySpawnName(), boss.getSavedLocation());
                        l.setHealth(l.getMaxHealth() - 1);
                        boss.setEntity(l);
                        eb.loadbossequip.SetEqupiment(boss);
                        eb.skillhandler.skills(boss, null);
                        if (eb.HeroesEnabled) {
                            eb.heroes.getCharacterManager().getMonster(l).setDamage(boss.getDamage());
                            eb.heroes.getCharacterManager().getMonster(l).setMaxHealth(999999999);
                        }
                        if (eb.SpoutEnabled && boss.getSkinUrl() != null) {
                            Spout.getServer().setEntitySkin(l, boss.getSkinUrl(), EntitySkinType.DEFAULT);
                            Bukkit.broadcastMessage("!");
                        }
                        if (eb.SpoutEnabled && boss.isTitleShowed()) {
                            final String s = boss.getName().replace("_", " ");
                            Spout.getServer().setTitle(l, s);
                        }
                    }
                }
                else if (!eb.bossCalculator.BossExist(boss)) {
                    for (int i = 0; i < eb.BossList.size(); ++i) {
                        if (eb.BossList.get(i).equals(boss)) {
                            remove.add(eb.BossList.get(i));
                        }
                    }
                    for (final Boss b : remove) {
                        if (b.getLivingEntity() != null) {
                            b.getLivingEntity().remove();
                        }
                    }
                }
                if (!boss.getSaved() && !boss.getNatural() && this.PlayersInside(boss.getSavedLocation()) && eb.bossCalculator.BossExist(boss) != null && !eb.bossCalculator.BossExist(boss)) {
                    boss.setSaved(true);
                    boss.setSavedLocation(boss.getLocation());
                }
            }
            eb.BossList.removeAll(remove);
        }
    }
    
    public void DeSpawnBoss(final Boss b) {
        if (b.getNatural()) {
            b.setSaved(false);
            final LivingEntity l = (LivingEntity)this.eb.mobs.SpawnMob(b.getEntitySpawnName(), b.getSavedLocation());
            l.setHealth(l.getMaxHealth() - 1);
            b.setEntity(l);
            this.eb.loadbossequip.SetEqupiment(b);
            this.eb.skillhandler.skills(b, null);
            if (this.eb.HeroesEnabled) {
                this.eb.heroes.getCharacterManager().getMonster(l).setDamage(b.getDamage());
                this.eb.heroes.getCharacterManager().getMonster(l).setMaxHealth(999999999);
            }
            if (this.eb.SpoutEnabled && b.getSkinUrl() != null) {
                Spout.getServer().setEntitySkin(l, b.getSkinUrl(), EntitySkinType.DEFAULT);
                Bukkit.broadcastMessage("!");
            }
            if (this.eb.SpoutEnabled && b.isTitleShowed()) {
                final String s = b.getName().replace("_", " ");
                Spout.getServer().setTitle(l, s);
            }
        }
    }
    
    public boolean PlayersInside(final Location l) {
        Player[] onlinePlayers;
        for (int length = (onlinePlayers = Bukkit.getServer().getOnlinePlayers()).length, i = 0; i < length; ++i) {
            final Player p = onlinePlayers[i];
            if (l.getWorld() == p.getWorld() && l.distance(p.getLocation()) < 40.0) {
                return true;
            }
        }
        return false;
    }
}
