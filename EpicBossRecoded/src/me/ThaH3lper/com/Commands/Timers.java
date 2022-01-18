// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Commands;

import java.util.Iterator;
import me.ThaH3lper.com.Timer.Timer;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import me.ThaH3lper.com.EpicBoss;

public class Timers
{
    EpicBoss eb;
    String s;
    
    public Timers(final EpicBoss neweb) {
        this.s = ChatColor.DARK_RED + "-------------------" + ChatColor.GRAY + "[ " + ChatColor.RED + ChatColor.BOLD + "EpicBoss" + ChatColor.GRAY + " ]" + ChatColor.DARK_RED + "-------------------";
        this.eb = neweb;
    }
    
    public void Command(final Player p, final Command cmd, final String commandlabel, final String[] args) {
        if (args.length == 1) {
            p.sendMessage(this.s);
            p.sendMessage(ChatColor.RED + "/eb timers <name> <boss> <location> <h:m:s>" + ChatColor.GRAY + ChatColor.ITALIC + " Create a new timer");
            p.sendMessage(ChatColor.RED + "/eb timers remove <name>" + ChatColor.GRAY + ChatColor.ITALIC + " remove timer");
            p.sendMessage(ChatColor.RED + "/eb timers list" + ChatColor.GRAY + ChatColor.ITALIC + " list all timers");
            p.sendMessage(ChatColor.RED + "/eb timers info <name>" + ChatColor.GRAY + ChatColor.ITALIC + " get info about timer");
        }
        if (args.length == 2 && args[1].equals("list")) {
            p.sendMessage(this.s);
            String string = "";
            if (this.eb.TimersList != null) {
                for (final Timer time : this.eb.TimersList) {
                    string = String.valueOf(string) + ChatColor.DARK_RED + time.getName() + ChatColor.GRAY + ", ";
                }
            }
            if (string.equals("")) {
                string = ChatColor.RED + "There is no Timers :o";
            }
            p.sendMessage(string);
        }
        if (args.length == 3) {
            if (args[1].equals("remove")) {
                if (this.eb.timerstuff.getTimer(args[2]) != null) {
                    this.eb.timerstuff.removeTimer(args[2]);
                    p.sendMessage(ChatColor.GREEN + "Timer " + ChatColor.DARK_PURPLE + args[2] + ChatColor.GREEN + " removed!");
                }
                else {
                    p.sendMessage(ChatColor.RED + "That Timer does not exict");
                }
            }
            if (args[1].equals("info")) {
                if (this.eb.timerstuff.getTimer(args[2]) != null) {
                    final Timer time2 = this.eb.timerstuff.getTimer(args[2]);
                    p.sendMessage(ChatColor.GREEN + "Name: " + ChatColor.DARK_PURPLE + time2.getName());
                    p.sendMessage(ChatColor.GREEN + "Boss type: " + ChatColor.DARK_PURPLE + time2.getBossName());
                    p.sendMessage(ChatColor.GREEN + "Location: " + ChatColor.DARK_PURPLE + time2.getLocationStr());
                    p.sendMessage(ChatColor.GREEN + "Respawn time: " + ChatColor.DARK_PURPLE + this.getTime(time2.getMaxTime()));
                    p.sendMessage(ChatColor.GREEN + "Respawn in: " + ChatColor.DARK_PURPLE + this.getTime(time2.getTime()));
                    p.sendMessage(ChatColor.GREEN + "(If Respawn in equals -1sec then the boss has spawned)");
                }
                else {
                    p.sendMessage(ChatColor.RED + "That Timer does not exict");
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
                        p.sendMessage(ChatColor.GREEN + "Timer " + ChatColor.DARK_PURPLE + args[1] + ChatColor.GREEN + " added!");
                    }
                    else {
                        p.sendMessage(ChatColor.RED + "That location does not exict!");
                    }
                }
                else {
                    p.sendMessage(ChatColor.RED + "That boss does not exict!");
                }
            }
            else {
                p.sendMessage(ChatColor.RED + "Timer already exict with that name!");
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
