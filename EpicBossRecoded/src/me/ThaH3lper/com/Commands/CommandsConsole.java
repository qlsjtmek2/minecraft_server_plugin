// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Commands;

import org.bukkit.Location;
import me.ThaH3lper.com.LoadBosses.LoadBoss;
import java.util.Iterator;
import java.util.List;
import me.ThaH3lper.com.Timer.Timer;
import me.ThaH3lper.com.locations.Locations;
import java.util.Collection;
import me.ThaH3lper.com.Boss.Boss;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import me.ThaH3lper.com.EpicBoss;

public class CommandsConsole
{
    EpicBoss eb;
    
    public CommandsConsole(final EpicBoss neweb) {
        this.eb = neweb;
    }
    
    public void Command(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "/eb boss spawn <BossName> <location>" + ChatColor.GRAY + ChatColor.ITALIC + " Spawn a Boss");
            sender.sendMessage(ChatColor.RED + "/eb boss spawn <BossName> <Player>" + ChatColor.GRAY + ChatColor.ITALIC + " Spawn a Boss at Player");
            sender.sendMessage(ChatColor.RED + "/eb boss killall " + ChatColor.GRAY + ChatColor.ITALIC + " kills all bosses");
            sender.sendMessage(ChatColor.RED + "/eb boss killtype <BossName " + ChatColor.GRAY + ChatColor.ITALIC + " kills all bosses with that name");
            sender.sendMessage(ChatColor.RED + "/eb location list" + ChatColor.GRAY + ChatColor.ITALIC + " list all locations");
            sender.sendMessage(ChatColor.RED + "/eb timers <name> <boss> <location> <h:m:s>" + ChatColor.GRAY + ChatColor.ITALIC + " Create a new timer");
            sender.sendMessage(ChatColor.RED + "/eb timers remove <name>" + ChatColor.GRAY + ChatColor.ITALIC + " remove timer");
            sender.sendMessage(ChatColor.RED + "/eb timers list" + ChatColor.GRAY + ChatColor.ITALIC + " list all timers");
            sender.sendMessage(ChatColor.RED + "/eb timers info <name>" + ChatColor.GRAY + ChatColor.ITALIC + " get info about timer");
            sender.sendMessage(ChatColor.RED + "/eb reload" + ChatColor.GRAY + ChatColor.ITALIC + " Reload changes in Bosses.yml");
        }
        if (args.length == 1 && args[0].equals("reload")) {
            this.eb.loadconfig.LoadBosses();
            sender.sendMessage(ChatColor.GREEN + "EpicBoss reloded!");
        }
        if (args.length == 2) {
            if (args[1].equals("killall") && args[0].equals("boss") && this.eb.BossList != null) {
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
                sender.sendMessage(ChatColor.GREEN + "All Bosses removed!");
            }
            if (args[1].equals("list") && args[0].equals("location")) {
                String string = "";
                if (this.eb.LocationList != null) {
                    for (final Locations loc : this.eb.LocationList) {
                        string = String.valueOf(string) + ChatColor.DARK_RED + loc.getName() + ChatColor.GRAY + ", ";
                    }
                }
                if (string.equals("")) {
                    string = ChatColor.RED + "There is no Locations :o";
                }
                sender.sendMessage(string);
            }
            if (args[1].equals("list") && args[0].equals("timers")) {
                String string = "";
                if (this.eb.TimersList != null) {
                    for (final Timer time : this.eb.TimersList) {
                        string = String.valueOf(string) + ChatColor.DARK_RED + time.getName() + ChatColor.GRAY + ", ";
                    }
                }
                if (string.equals("")) {
                    string = ChatColor.RED + "There is no Timers :o";
                }
                sender.sendMessage(string);
            }
        }
        if (args.length == 3) {
            if (args[1].equals("killtype") && args[0].equals("boss") && this.eb.BossList != null) {
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
                    sender.sendMessage(ChatColor.GREEN + "All " + ChatColor.DARK_PURPLE + args[2] + ChatColor.GREEN + " removed!");
                }
                else {
                    sender.sendMessage(ChatColor.RED + "There is no boss called " + args[2]);
                }
            }
            if (args[1].equals("remove") && args[0].equals("timers")) {
                if (this.eb.timerstuff.getTimer(args[2]) != null) {
                    this.eb.timerstuff.removeTimer(args[2]);
                    sender.sendMessage(ChatColor.GREEN + "Timer " + ChatColor.DARK_PURPLE + args[2] + ChatColor.GREEN + " removed!");
                }
                else {
                    sender.sendMessage(ChatColor.RED + "That Timer does not exict");
                }
            }
            if (args[1].equals("info") && args[0].equals("timers")) {
                if (this.eb.timerstuff.getTimer(args[2]) != null) {
                    final Timer time2 = this.eb.timerstuff.getTimer(args[2]);
                    sender.sendMessage(ChatColor.GREEN + "Name: " + ChatColor.DARK_PURPLE + time2.getName());
                    sender.sendMessage(ChatColor.GREEN + "Boss type: " + ChatColor.DARK_PURPLE + time2.getBossName());
                    sender.sendMessage(ChatColor.GREEN + "Location: " + ChatColor.DARK_PURPLE + time2.getLocationStr());
                    sender.sendMessage(ChatColor.GREEN + "Respawn time: " + ChatColor.DARK_PURPLE + this.getTime(time2.getMaxTime()));
                    sender.sendMessage(ChatColor.GREEN + "Respawn in: " + ChatColor.DARK_PURPLE + this.getTime(time2.getTime()));
                    sender.sendMessage(ChatColor.GREEN + "(If Respawn in equals -1sec then the boss has spawned)");
                }
                else {
                    sender.sendMessage(ChatColor.RED + "That Timer does not exict");
                }
            }
        }
        if (args.length == 4 && args[1].equals("spawn") && args[0].equals("boss")) {
            final String name = args[2];
            final LoadBoss lb = this.eb.loadconfig.getLoadBoss(name);
            if (lb != null) {
                if (this.eb.locationstuff.locationExict(args[3])) {
                    final Locations loc2 = this.eb.locationstuff.getLocations(args[3]);
                    sender.sendMessage(ChatColor.GREEN + "You spawned " + ChatColor.DARK_PURPLE + lb.getName() + ChatColor.GREEN + " and he has " + ChatColor.DARK_PURPLE + lb.getHealth() + ChatColor.GREEN + " Hp at " + ChatColor.DARK_PURPLE + args[3]);
                    this.eb.BossList.add(new Boss(lb.getName(), lb.getHealth(), loc2.getLocation(), lb.getType(), lb.getDamage(), lb.getShowhp(), lb.getItems(), lb.getSkills(), lb.getShowtitle(), lb.getSkin()));
                    this.eb.timer.despawn.DeSpawnEvent(this.eb);
                }
                else if (this.eb.locationstuff.getPlayer(args[3]) != null) {
                    final Location loc3 = this.eb.locationstuff.getPlayer(args[3]).getLocation();
                    sender.sendMessage(ChatColor.GREEN + "You spawned " + ChatColor.DARK_PURPLE + lb.getName() + ChatColor.GREEN + " and he has " + ChatColor.DARK_PURPLE + lb.getHealth() + ChatColor.GREEN + " Hp at player " + ChatColor.DARK_PURPLE + args[3]);
                    this.eb.BossList.add(new Boss(lb.getName(), lb.getHealth(), loc3, lb.getType(), lb.getDamage(), lb.getShowhp(), lb.getItems(), lb.getSkills(), lb.getShowtitle(), lb.getSkin()));
                    this.eb.timer.despawn.DeSpawnEvent(this.eb);
                }
            }
        }
        if (args.length == 5) {
            if (this.eb.timerstuff.getTimer(args[1]) == null) {
                if (this.eb.loadconfig.getLoadBoss(args[2]) != null) {
                    if (this.eb.locationstuff.locationExict(args[3])) {
                        final String[] Splits = args[4].split(":");
                        final int time3 = Integer.parseInt(Splits[0]) * 60 * 60 + Integer.parseInt(Splits[1]) * 60 + Integer.parseInt(Splits[2]);
                        this.eb.timerstuff.addTimer(args[1], args[2], args[3], time3);
                        sender.sendMessage(ChatColor.GREEN + "Timer " + ChatColor.DARK_PURPLE + args[1] + ChatColor.GREEN + " added!");
                    }
                    else {
                        sender.sendMessage(ChatColor.RED + "That location does not exict!");
                    }
                }
                else {
                    sender.sendMessage(ChatColor.RED + "That boss does not exict!");
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "Timer already exict with that name!");
            }
        }
    }
    
    public String getTime(int i) {
        int m = 0;
        int h = 0;
        int s = 0;
        if (i < 60) {
            s = i;
            return String.valueOf(h) + "hour(s), " + m + "minut(s), " + s + "second(s)";
        }
        s = i % 60;
        i -= s;
        i /= 60;
        if (i >= 60) {
            m = i % 60;
            i -= m;
            h = i / 60;
            return String.valueOf(h) + "hour(s), " + m + "minut(s), " + s + "second(s)";
        }
        m = i;
        return String.valueOf(h) + "hour(s), " + m + "minut(s), " + s + "second(s)";
    }
}
