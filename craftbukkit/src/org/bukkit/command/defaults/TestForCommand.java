// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class TestForCommand extends VanillaCommand
{
    public TestForCommand() {
        super("testfor");
        this.description = "Tests whether a specifed player is online";
        this.usageMessage = "/testfor <player>";
        this.setPermission("bukkit.command.testfor");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        sender.sendMessage(ChatColor.RED + "/testfor is only usable by commandblocks with analog output.");
        return true;
    }
}
