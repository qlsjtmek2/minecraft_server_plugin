// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Commands;

import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import me.ThaH3lper.com.EpicBoss;

public class CommandsPlayer
{
    private EpicBoss eb;
    String s;
    private Bosses bosses;
    private Timers timer;
    private Location location;
    private Bossegg bossegg;
    
    public CommandsPlayer(final EpicBoss boss) {
        this.s = ChatColor.DARK_RED + "-------------------" + ChatColor.GRAY + "[ " + ChatColor.RED + ChatColor.BOLD + "EpicBoss" + ChatColor.GRAY + " ]" + ChatColor.DARK_RED + "-------------------";
        this.eb = boss;
        this.bosses = new Bosses(this.eb);
        this.timer = new Timers(this.eb);
        this.location = new Location(this.eb);
        this.bossegg = new Bossegg(this.eb);
    }
    
    public void Command(final CommandSender sender, final Command cmd, final String commandlabel, final String[] args) {
        final Player p = (Player)sender;
        if (args.length == 0) {
            p.sendMessage(this.s);
            p.sendMessage(ChatColor.RED + "/eb boss" + ChatColor.GRAY + ChatColor.ITALIC + "   Commands/info about Bosses");
            p.sendMessage(ChatColor.RED + "/eb location" + ChatColor.GRAY + ChatColor.ITALIC + "  Commands/info about Locations");
            p.sendMessage(ChatColor.RED + "/eb timers" + ChatColor.GRAY + ChatColor.ITALIC + "  Commands/info about Timers");
            p.sendMessage(ChatColor.RED + "/eb bossegg" + ChatColor.GRAY + ChatColor.ITALIC + "  Commands/info about bossegg");
            p.sendMessage(ChatColor.RED + "/eb reload" + ChatColor.GRAY + ChatColor.ITALIC + "    Reload changes in Bosses.yml");
            p.sendMessage(new StringBuilder().append(ChatColor.DARK_GRAY).append(ChatColor.ITALIC).append("(Version 1.3.2, Coded by ThaH3lper)").toString());
        }
        if (args.length >= 1) {
            if (args[0].equals("reload")) {
                if (p.hasPermission("epicboss.reload")) {
                    this.eb.loadconfig.LoadBosses();
                    p.sendMessage(ChatColor.GREEN + "EpicBoss reloded!");
                }
            }
            else if (args[0].equals("boss")) {
                if (p.hasPermission("epicboss.boss")) {
                    this.bosses.Command(p, cmd, commandlabel, args);
                }
            }
            else if (args[0].equals("bossegg")) {
                if (p.hasPermission("epicboss.bossegg")) {
                    this.bossegg.Command(p, cmd, commandlabel, args);
                }
            }
            else if (args[0].equals("timers")) {
                if (p.hasPermission("epicboss.timers")) {
                    this.timer.Command(p, cmd, commandlabel, args);
                }
            }
            else if (args[0].equals("location")) {
                if (p.hasPermission("epicboss.location")) {
                    this.location.Command(p, cmd, commandlabel, args);
                }
            }
            else {
                p.sendMessage(this.s);
                p.sendMessage(ChatColor.RED + "/eb boss" + ChatColor.GRAY + ChatColor.ITALIC + "   Commands/info about Bosses");
                p.sendMessage(ChatColor.RED + "/eb location" + ChatColor.GRAY + ChatColor.ITALIC + "  Commands/info about Locations");
                p.sendMessage(ChatColor.RED + "/eb timers" + ChatColor.GRAY + ChatColor.ITALIC + "  Commands/info about Timers");
                p.sendMessage(ChatColor.RED + "/eb bossegg" + ChatColor.GRAY + ChatColor.ITALIC + "  Commands/info about bossegg");
                p.sendMessage(ChatColor.RED + "/eb reload" + ChatColor.GRAY + ChatColor.ITALIC + "    Reload changes in Bosses.yml");
            }
        }
    }
}
