// 
// Decompiled by Procyon v0.5.30
// 

package yt.codebukkit.scoreboardapi;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.CommandSender;

public class Helper
{
    private final ScoreboardAPI plugin;
    
    public Helper(final ScoreboardAPI plugin) {
        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
        this.plugin = plugin;
    }
    
    public boolean isConsole(final CommandSender s) {
        return s instanceof ConsoleCommandSender;
    }
    
    public String getHelp(final String cmd, final String description) {
        return ChatColor.GOLD + cmd + ChatColor.RESET + " - " + ChatColor.ITALIC + description;
    }
    
    public String getHeader(final String head) {
        return "§1[]-------§6" + ScoreboardAPI.NAME + " " + head + "§r§1------- []";
    }
}
