// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MeCommand extends VanillaCommand
{
    public MeCommand() {
        super("me");
        this.description = "Performs the specified action in chat";
        this.usageMessage = "/me <action>";
        this.setPermission("bukkit.command.me");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        final StringBuilder message = new StringBuilder();
        message.append(sender.getName());
        for (final String arg : args) {
            message.append(" ");
            message.append(arg);
        }
        Bukkit.broadcastMessage("* " + message.toString());
        return true;
    }
}
