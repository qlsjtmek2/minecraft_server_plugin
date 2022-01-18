// 
// Decompiled by Procyon v0.5.30
// 

package me.ThaH3lper.com.Commands;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import me.ThaH3lper.com.EpicBoss;

public class Bossegg
{
    EpicBoss eb;
    String s;
    
    public Bossegg(final EpicBoss neweb) {
        this.s = ChatColor.DARK_RED + "-------------------" + ChatColor.GRAY + "[ " + ChatColor.RED + ChatColor.BOLD + "EpicBoss" + ChatColor.GRAY + " ]" + ChatColor.DARK_RED + "-------------------";
        this.eb = neweb;
    }
    
    public void Command(final Player p, final Command cmd, final String commandlabel, final String[] args) {
        if (args.length == 1) {
            p.sendMessage(this.s);
            p.sendMessage(ChatColor.RED + "/eb bossegg <BossName> (amount)" + ChatColor.GRAY + ChatColor.ITALIC + " get Bossegg");
        }
        if (args.length == 2) {
            this.eb.bossegg.giveBossEgg(p, args[1], 1);
        }
        if (args.length == 3) {
            this.eb.bossegg.giveBossEgg(p, args[1], Integer.parseInt(args[2]));
        }
    }
}
