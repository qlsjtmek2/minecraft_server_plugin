// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class SayCommand extends VanillaCommand
{
    public SayCommand() {
        super("say");
        this.description = "Broadcasts the given message as the console";
        this.usageMessage = "/say <message>";
        this.setPermission("bukkit.command.say");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        final StringBuilder message = new StringBuilder();
        if (args.length > 0) {
            message.append(args[0]);
            for (int i = 1; i < args.length; ++i) {
                message.append(" ");
                message.append(args[i]);
            }
        }
        if (sender instanceof Player) {
            Bukkit.getLogger().info("[" + sender.getName() + "] " + (Object)message);
        }
        Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "[Server] " + (Object)message);
        return true;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        if (args.length >= 1) {
            return super.tabComplete(sender, alias, args);
        }
        return (List<String>)ImmutableList.of();
    }
}
