// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.LoadBosses;

import org.bukkit.Location;
import org.bukkit.Bukkit;
import me.ThaH3lper.com.Boss.Boss;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;
import me.ThaH3lper.com.EpicBoss;

public class LoadConfigs
{
    public EpicBoss eb;
    
    public LoadConfigs(final EpicBoss boss) {
        this.eb = boss;
        this.LoadBosses();
    }
    
    public void LoadBosses() {
        this.eb.BossLoadList.clear();
        this.eb.Bosses.reloadCustomConfig();
        if (this.eb.Bosses.getCustomConfig().contains("Bosses")) {
            for (final String name : this.eb.Bosses.getCustomConfig().getConfigurationSection("Bosses").getKeys(false)) {
                String Skin = null;
                List<String> Items = new ArrayList<String>();
                List<String> Skills = new ArrayList<String>();
                boolean showtitle = false;
                final String Name = name;
                final String Type = this.eb.Bosses.getCustomConfig().getString("Bosses." + name + ".Type");
                final int Health = this.eb.Bosses.getCustomConfig().getInt("Bosses." + name + ".Health");
                final int Damage = this.eb.Bosses.getCustomConfig().getInt("Bosses." + name + ".Damage");
                if (this.eb.Bosses.getCustomConfig().contains("Bosses." + name + ".Drops")) {
                    Items = (List<String>)this.eb.Bosses.getCustomConfig().getStringList("Bosses." + name + ".Drops");
                }
                if (this.eb.Bosses.getCustomConfig().contains("Bosses." + name + ".Skills")) {
                    Skills = (List<String>)this.eb.Bosses.getCustomConfig().getStringList("Bosses." + name + ".Skills");
                }
                if (this.eb.Bosses.getCustomConfig().contains("Bosses." + name + ".Skin")) {
                    Skin = this.eb.Bosses.getCustomConfig().getString("Bosses." + name + ".Skin");
                }
                if (this.eb.Bosses.getCustomConfig().contains("Bosses." + name + ".Showtitle")) {
                    showtitle = this.eb.Bosses.getCustomConfig().getBoolean("Bosses." + name + ".Showtitle");
                }
                final boolean showhp = this.eb.Bosses.getCustomConfig().getBoolean("Bosses." + name + ".Showhp");
                this.eb.BossLoadList.add(new LoadBoss(Name, Type, Health, Damage, Items, showhp, Skills, Skin, showtitle));
            }
        }
    }
    
    public LoadBoss getLoadBoss(final String s) {
        if (this.eb.BossLoadList != null) {
            for (final LoadBoss lb : this.eb.BossLoadList) {
                if (lb.getName().equals(s)) {
                    return lb;
                }
            }
        }
        return null;
    }
    
    public void SaveAllBosses() {
        if (this.eb.BossList != null) {
            final List<String> saved = new ArrayList<String>();
            for (final Boss boss : this.eb.BossList) {
                if (!boss.getNatural()) {
                    if (boss.getTimer().equals("null")) {
                        final String save = String.valueOf(boss.getName()) + ":" + boss.getHealth() + ":" + boss.getWorkingLocation().getWorld().getName() + ":" + boss.getWorkingLocation().getBlockX() + ":" + boss.getWorkingLocation().getBlockY() + ":" + boss.getWorkingLocation().getBlockZ() + ":" + boss.getTimer();
                        saved.add(save);
                    }
                    if (boss.getSaved()) {
                        continue;
                    }
                    boss.getLivingEntity().remove();
                }
            }
            this.eb.SavedData.reloadCustomConfig();
            this.eb.SavedData.getCustomConfig().set("Bosses", (Object)saved);
            this.eb.SavedData.saveCustomConfig();
        }
    }
    
    public void LoadAllBosses() {
        if (this.eb.SavedData.getCustomConfig().contains("Bosses") && this.eb.SavedData.getCustomConfig().getStringList("Bosses") != null) {
            for (final String s : this.eb.SavedData.getCustomConfig().getStringList("Bosses")) {
                final String[] Splits = s.split(":");
                if (this.getLoadBoss(Splits[0]) != null) {
                    final LoadBoss lb = this.getLoadBoss(Splits[0]);
                    final Location l = new Location(Bukkit.getWorld(Splits[2]), (double)Integer.parseInt(Splits[3]), (double)Integer.parseInt(Splits[4]), (double)Integer.parseInt(Splits[5]));
                    final Boss bs = new Boss(lb.getName(), lb.getHealth(), l, lb.getType(), lb.getDamage(), lb.getShowhp(), lb.getItems(), lb.getSkills(), lb.getShowtitle(), lb.getSkin());
                    bs.sethealth(Integer.parseInt(Splits[1]));
                    if (!Splits[6].equals("null")) {}
                    bs.setTimer(Splits[6]);
                    this.eb.BossList.add(bs);
                }
            }
        }
    }
}
