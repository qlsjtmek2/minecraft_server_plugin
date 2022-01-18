// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Timer;

import me.ThaH3lper.com.locations.Locations;
import me.ThaH3lper.com.LoadBosses.LoadBoss;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import me.ThaH3lper.com.Boss.Boss;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import me.ThaH3lper.com.EpicBoss;

public class TimerStuff
{
    EpicBoss eb;
    
    public TimerStuff(final EpicBoss neweb) {
        this.eb = neweb;
        this.LoadAllTimers();
    }
    
    public void addTimer(final String name, final String bossname, final String location, final int time) {
        this.eb.TimersList.add(new Timer(name, bossname, location, time, this.eb));
        this.saveAllTimers();
    }
    
    public void removeTimer(final String name) {
        if (this.eb.TimersList != null) {
            for (int i = 0; i < this.eb.TimersList.size(); ++i) {
                if (this.eb.TimersList.get(i).getName().equals(name)) {
                    this.eb.TimersList.remove(i);
                    this.saveAllTimers();
                }
            }
        }
    }
    
    public void saveAllTimers() {
        if (this.eb.TimersList != null) {
            final List<String> saved = new ArrayList<String>();
            for (final Timer time : this.eb.TimersList) {
                String save = String.valueOf(time.getName()) + ":" + time.getBossName() + ":" + time.getLocationStr() + ":" + time.getMaxTime();
                if (time.getText() != "") {
                    save = String.valueOf(time.getName()) + ":" + time.getBossName() + ":" + time.getLocationStr() + ":" + time.getMaxTime() + ":" + time.getText();
                }
                saved.add(save);
            }
            this.eb.SavedData.reloadCustomConfig();
            this.eb.SavedData.getCustomConfig().set("Timers", (Object)saved);
            this.eb.SavedData.saveCustomConfig();
        }
    }
    
    public void LoadAllTimers() {
        if (this.eb.SavedData.getCustomConfig().contains("Timers") && this.eb.SavedData.getCustomConfig().getStringList("Timers") != null) {
            for (final String s : this.eb.SavedData.getCustomConfig().getStringList("Timers")) {
                final String[] Splits = s.split(":");
                if (this.eb.loadconfig.getLoadBoss(Splits[1]) != null) {
                    if (this.eb.locationstuff.locationExict(Splits[2])) {
                        final Timer time = new Timer(Splits[0], Splits[1], Splits[2], Integer.parseInt(Splits[3]), this.eb);
                        this.eb.TimersList.add(time);
                        if (Splits.length == 5) {
                            time.setText(Splits[4]);
                        }
                        this.saveAllTimers();
                    }
                    else {
                        this.eb.logger.warning("Timer: " + Splits[0] + " could not be loaded since location " + Splits[2] + " dose not exict!");
                    }
                }
                else {
                    this.eb.logger.warning("Timer: " + Splits[0] + " could not be loaded since boss " + Splits[1] + " dose not exict!");
                }
            }
        }
    }
    
    public void lower() {
        if (this.eb.TimersList != null) {
            for (final Timer time : this.eb.TimersList) {
                time.lower();
            }
        }
    }
    
    public void Death(final Boss b) {
        if (this.getTimer(b.getTimer()) != null) {
            final Timer time = this.getTimer(b.getTimer());
            time.setTime(time.getMaxTime());
        }
    }
    
    public void spawndeath(final Timer time) {
        final LoadBoss lb = this.eb.loadconfig.getLoadBoss(time.getBossName());
        final Locations loc = this.eb.locationstuff.getLocations(time.getLocationStr());
        if (lb != null && loc != null) {
            final Boss b = new Boss(lb.getName(), lb.getHealth(), loc.getLocation(), lb.getType(), lb.getDamage(), lb.getShowhp(), lb.getItems(), lb.getSkills(), lb.getShowtitle(), lb.getSkin());
            b.setTimer(time.getName());
            this.eb.BossList.add(b);
            this.eb.timer.despawn.DeSpawnEvent(this.eb);
            if (!time.getText().equals("")) {
                String s = time.getText();
                s = s.replace("_", " ");
                s = ChatColor.translateAlternateColorCodes('&', s);
                Bukkit.broadcastMessage(s);
            }
        }
    }
    
    public Timer getTimer(final String name) {
        if (this.eb.TimersList != null) {
            for (final Timer time : this.eb.TimersList) {
                if (time.getName().equals(name)) {
                    return time;
                }
            }
        }
        return null;
    }
}
