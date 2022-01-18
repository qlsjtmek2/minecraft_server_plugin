// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class TellCommand extends VanillaCommand
{
    public TellCommand() {
        super("tell");
        this.description = "Sends a private message to the given player";
        this.usageMessage = "/tell <player> <message>";
        this.setPermission("bukkit.command.tell");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        final Player player = Bukkit.getPlayerExact(args[0]);
        if (player == null || (sender instanceof Player && !((Player)sender).canSee(player))) {
            sender.sendMessage("There's no player by that name online.");
        }
        else {
            final StringBuilder message = new StringBuilder();
            for (int i = 1; i < args.length; ++i) {
                if (i > 1) {
                    message.append(" ");
                }
                message.append(args[i]);
            }
            final String result = ChatColor.GRAY + sender.getName() + " whispers " + (Object)message;
            sender.sendMessage("[" + sender.getName() + "->" + player.getName() + "] " + (Object)message);
            player.sendMessage(result);
        }
        return true;
    }
    
    public boolean matches(final String input) {
        return input.equalsIgnoreCase("tell") || input.equalsIgnoreCase("w") || input.equalsIgnoreCase("msg");
    }
}
