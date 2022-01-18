// 
// Decompiled by Procyon v0.5.30
// 

package com.onikur.changebackitem.util;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;

public class Msg
{
    private static String PLUGIN_TAG;
    
    public static void setup(final PluginDescriptionFile pdfile) {
        Msg.PLUGIN_TAG = ChatColor.AQUA + "[" + pdfile.getName() + " " + pdfile.getVersion() + "] ";
    }
    
    public static void send(final CommandSender sender, final String msg) {
        sender.sendMessage(formatMsg(msg));
    }
    
    public static void sendRawWithConsole(final CommandSender sender, String msg) {
        msg = msg.replace('§', '&');
        sender.sendMessage(msg);
        Bukkit.getConsoleSender().sendMessage(msg);
    }
    
    public static void send(final Player p, final String msg) {
        if (!msg.equalsIgnoreCase("")) {
            p.sendMessage(formatMsg(msg));
        }
    }
    
    public static void sendConsole(final String msg) {
        Bukkit.getConsoleSender().sendMessage(formatMsg(msg));
    }
    
    public static String formatMsg(final String msg) {
        return Msg.PLUGIN_TAG + ChatColor.translateAlternateColorCodes('&', msg);
    }
    
    static {
        Msg.PLUGIN_TAG = ChatColor.AQUA + "[]";
    }
}
