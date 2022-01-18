// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Commands;

import me.ThaH3lper.com.locations.Locations;
import java.util.List;
import java.util.Iterator;
import java.util.Collection;
import me.ThaH3lper.com.Boss.Boss;
import java.util.ArrayList;
import me.ThaH3lper.com.LoadBosses.LoadBoss;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import me.ThaH3lper.com.EpicBoss;

public class Bosses
{
    EpicBoss eb;
    String s;
    
    public Bosses(final EpicBoss neweb) {
        this.s = ChatColor.DARK_RED + "-------------------" + ChatColor.GRAY + "[ " + ChatColor.RED + ChatColor.BOLD + "EpicBoss" + ChatColor.GRAY + " ]" + ChatColor.DARK_RED + "-------------------";
        this.eb = neweb;
    }
    
    public void Command(final Player p, final Command cmd, final String commandlabel, final String[] args) {
        if (args.length == 1) {
            p.sendMessage(this.s);
            p.sendMessage(ChatColor.RED + "Bosses loaded:");
            if (this.eb.BossLoadList.size() != 0) {
                String bosses = "";
                for (final LoadBoss lb : this.eb.BossLoadList) {
                    bosses = String.valueOf(bosses) + ChatColor.DARK_RED + lb.getName() + ChatColor.GRAY + ", ";
                }
                p.sendMessage(bosses);
            }
            else {
                p.sendMessage(new StringBuilder().append(ChatColor.GRAY).append(ChatColor.ITALIC).append("There is no bosses in Bosses.yml").toString());
            }
            p.sendMessage(ChatColor.RED + "Current bosses: " + this.eb.BossList.size());
            p.sendMessage(ChatColor.RED + "/eb boss spawn <BossName> (location)" + ChatColor.GRAY + ChatColor.ITALIC + " Spawn a Boss");
            p.sendMessage(ChatColor.RED + "/eb boss killall " + ChatColor.GRAY + ChatColor.ITALIC + " kills all bosses");
            p.sendMessage(ChatColor.RED + "/eb boss killtype <BossName " + ChatColor.GRAY + ChatColor.ITALIC + " kills all bosses with that name");
        }
        if (args.length == 2 && args[1].equals("killall") && this.eb.BossList != null) {
            final List<Boss> remove = new ArrayList<Boss>();
            for (int i = 0; i < this.eb.BossList.size(); ++i) {
                if (this.eb.BossList.get(i).getTimer().equals("null")) {
                    remove.add(this.eb.BossList.get(i));
                }
            }
            for (final Boss b : remove) {
                if (b.getLivingEntity() != null) {
                    b.getLivingEntity().remove();
                }
            }
            this.eb.BossList.removeAll(remove);
            p.sendMessage(ChatColor.GREEN + "All Bosses removed!");
        }
        if (args.length == 3) {
            if (args[1].equals("spawn")) {
                final String name = args[2];
                final LoadBoss lb = this.eb.loadconfig.getLoadBoss(name);
                if (lb != null) {
                    p.sendMessage(ChatColor.GREEN + "You spawned " + ChatColor.DARK_PURPLE + lb.getName() + ChatColor.GREEN + " and he has " + ChatColor.DARK_PURPLE + lb.getHealth() + ChatColor.GREEN + " Hp");
                    this.eb.BossList.add(new Boss(lb.getName(), lb.getHealth(), p.getLocation(), lb.getType(), lb.getDamage(), lb.getShowhp(), lb.getItems(), lb.getSkills(), lb.getShowtitle(), lb.getSkin()));
                    this.eb.timer.despawn.DeSpawnEvent(this.eb);
                }
            }
            if (args[1].equals("killtype") && this.eb.BossList != null) {
                final List<Boss> remove = new ArrayList<Boss>();
                int i = 0;
                if (this.eb.loadconfig.getLoadBoss(args[2]) != null) {
                    while (i < this.eb.BossList.size()) {
                        if (this.eb.BossList.get(i).getTimer().equals("null") && this.eb.BossList.get(i).getName().equals(args[2])) {
                            remove.add(this.eb.BossList.get(i));
                        }
                        ++i;
                    }
                    for (final Boss b : remove) {
                        if (b.getLivingEntity() != null) {
                            b.getLivingEntity().remove();
                        }
                    }
                    this.eb.BossList.removeAll(remove);
                    p.sendMessage(ChatColor.GREEN + "All " + ChatColor.DARK_PURPLE + args[2] + ChatColor.GREEN + " removed!");
                }
                else {
                    p.sendMessage(ChatColor.RED + "There is no boss called " + args[2]);
                }
            }
        }
        if (args.length == 4 && args[1].equals("spawn")) {
            final String name = args[2];
            final LoadBoss lb = this.eb.loadconfig.getLoadBoss(name);
            if (lb != null && this.eb.locationstuff.locationExict(args[3])) {
                final Locations loc = this.eb.locationstuff.getLocations(args[3]);
                p.sendMessage(ChatColor.GREEN + "You spawned " + ChatColor.DARK_PURPLE + lb.getName() + ChatColor.GREEN + " and he has " + ChatColor.DARK_PURPLE + lb.getHealth() + ChatColor.GREEN + " Hp at " + ChatColor.DARK_PURPLE + args[3]);
                this.eb.BossList.add(new Boss(lb.getName(), lb.getHealth(), loc.getLocation(), lb.getType(), lb.getDamage(), lb.getShowhp(), lb.getItems(), lb.getSkills(), lb.getShowtitle(), lb.getSkin()));
                this.eb.timer.despawn.DeSpawnEvent(this.eb);
            }
        }
    }
}
