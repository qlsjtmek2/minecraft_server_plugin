// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Commands;

import java.util.Iterator;
import me.ThaH3lper.com.locations.Locations;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import me.ThaH3lper.com.EpicBoss;

public class Location
{
    EpicBoss eb;
    String s;
    
    public Location(final EpicBoss neweb) {
        this.s = ChatColor.DARK_RED + "-------------------" + ChatColor.GRAY + "[ " + ChatColor.RED + ChatColor.BOLD + "EpicBoss" + ChatColor.GRAY + " ]" + ChatColor.DARK_RED + "-------------------";
        this.eb = neweb;
    }
    
    public void Command(final Player p, final Command cmd, final String commandlabel, final String[] args) {
        if (args.length == 1) {
            p.sendMessage(this.s);
            p.sendMessage(ChatColor.RED + "/eb location add <name>" + ChatColor.GRAY + ChatColor.ITALIC + " Create a new location");
            p.sendMessage(ChatColor.RED + "/eb location remove <name>" + ChatColor.GRAY + ChatColor.ITALIC + " remove location");
            p.sendMessage(ChatColor.RED + "/eb location list" + ChatColor.GRAY + ChatColor.ITALIC + " list all locations");
            p.sendMessage(ChatColor.RED + "/eb location teleport <name>" + ChatColor.GRAY + ChatColor.ITALIC + " teleport to location");
        }
        if (args.length == 2 && args[1].equals("list")) {
            p.sendMessage(this.s);
            String string = "";
            if (this.eb.LocationList != null) {
                for (final Locations loc : this.eb.LocationList) {
                    string = String.valueOf(string) + ChatColor.DARK_RED + loc.getName() + ChatColor.GRAY + ", ";
                }
            }
            if (string.equals("")) {
                string = ChatColor.RED + "There is no Locations :o";
            }
            p.sendMessage(string);
        }
        if (args.length == 3) {
            if (args[1].equals("add")) {
                if (!this.eb.locationstuff.locationExict(args[2])) {
                    this.eb.locationstuff.addLocation(args[2], p.getLocation());
                    p.sendMessage(ChatColor.GREEN + "Location " + ChatColor.DARK_PURPLE + args[2] + ChatColor.GREEN + " set!");
                }
                else {
                    p.sendMessage(ChatColor.RED + "A location with that name allready exicts");
                }
            }
            if (args[1].equals("remove")) {
                if (this.eb.locationstuff.locationExict(args[2])) {
                    this.eb.locationstuff.removeLocation(args[2]);
                    p.sendMessage(ChatColor.GREEN + "Location " + ChatColor.DARK_PURPLE + args[2] + ChatColor.GREEN + " removed!");
                }
                else {
                    p.sendMessage(ChatColor.RED + "That location do not exict!");
                }
            }
            if (args[1].equals("teleport")) {
                if (this.eb.locationstuff.locationExict(args[2])) {
                    final Locations loc2 = this.eb.locationstuff.getLocations(args[2]);
                    p.teleport(loc2.getLocation());
                    p.sendMessage(ChatColor.GREEN + "Teleported to " + ChatColor.DARK_PURPLE + args[2]);
                }
                else {
                    p.sendMessage(ChatColor.RED + "That location do not exict!");
                }
            }
        }
    }
}
