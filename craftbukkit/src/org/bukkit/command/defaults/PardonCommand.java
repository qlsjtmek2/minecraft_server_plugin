// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import java.util.Iterator;
import com.google.common.collect.ImmutableList;
import org.bukkit.util.StringUtil;
import org.bukkit.OfflinePlayer;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PardonCommand extends VanillaCommand
{
    public PardonCommand() {
        super("pardon");
        this.description = "Allows the specified player to use this server";
        this.usageMessage = "/pardon <player>";
        this.setPermission("bukkit.command.unban.player");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        Bukkit.getOfflinePlayer(args[0]).setBanned(false);
        Command.broadcastCommandMessage(sender, "Pardoned " + args[0]);
        return true;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            final List<String> completions = new ArrayList<String>();
            for (final OfflinePlayer player : Bukkit.getBannedPlayers()) {
                final String name = player.getName();
                if (StringUtil.startsWithIgnoreCase(name, args[0])) {
                    completions.add(name);
                }
            }
            return completions;
        }
        return (List<String>)ImmutableList.of();
    }
}
