// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import com.google.common.collect.ImmutableList;
import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import java.util.Iterator;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import java.util.List;

public class WhitelistCommand extends VanillaCommand
{
    private static final List<String> WHITELIST_SUBCOMMANDS;
    
    public WhitelistCommand() {
        super("whitelist");
        this.description = "Manages the list of players allowed to use this server";
        this.usageMessage = "/whitelist (add|remove) <player>\n/whitelist (on|off|list|reload)";
        this.setPermission("bukkit.command.whitelist.reload;bukkit.command.whitelist.enable;bukkit.command.whitelist.disable;bukkit.command.whitelist.list;bukkit.command.whitelist.add;bukkit.command.whitelist.remove");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (this.badPerm(sender, "reload")) {
                    return true;
                }
                Bukkit.reloadWhitelist();
                Command.broadcastCommandMessage(sender, "Reloaded white-list from file");
                return true;
            }
            else if (args[0].equalsIgnoreCase("on")) {
                if (this.badPerm(sender, "enable")) {
                    return true;
                }
                Bukkit.setWhitelist(true);
                Command.broadcastCommandMessage(sender, "Turned on white-listing");
                return true;
            }
            else if (args[0].equalsIgnoreCase("off")) {
                if (this.badPerm(sender, "disable")) {
                    return true;
                }
                Bukkit.setWhitelist(false);
                Command.broadcastCommandMessage(sender, "Turned off white-listing");
                return true;
            }
            else if (args[0].equalsIgnoreCase("list")) {
                if (this.badPerm(sender, "list")) {
                    return true;
                }
                final StringBuilder result = new StringBuilder();
                for (final OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
                    if (result.length() > 0) {
                        result.append(", ");
                    }
                    result.append(player.getName());
                }
                sender.sendMessage("White-listed players: " + result.toString());
                return true;
            }
        }
        else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                if (this.badPerm(sender, "add")) {
                    return true;
                }
                Bukkit.getOfflinePlayer(args[1]).setWhitelisted(true);
                Command.broadcastCommandMessage(sender, "Added " + args[1] + " to white-list");
                return true;
            }
            else if (args[0].equalsIgnoreCase("remove")) {
                if (this.badPerm(sender, "remove")) {
                    return true;
                }
                Bukkit.getOfflinePlayer(args[1]).setWhitelisted(false);
                Command.broadcastCommandMessage(sender, "Removed " + args[1] + " from white-list");
                return true;
            }
        }
        sender.sendMessage(ChatColor.RED + "Correct command usage:\n" + this.usageMessage);
        return false;
    }
    
    private boolean badPerm(final CommandSender sender, final String perm) {
        if (!sender.hasPermission("bukkit.command.whitelist." + perm)) {
            sender.sendMessage(ChatColor.RED + "You do not have permission to perform this action.");
            return true;
        }
        return false;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], WhitelistCommand.WHITELIST_SUBCOMMANDS, new ArrayList<String>(WhitelistCommand.WHITELIST_SUBCOMMANDS.size()));
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("add")) {
                final List<String> completions = new ArrayList<String>();
                for (final OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                    final String name = player.getName();
                    if (StringUtil.startsWithIgnoreCase(name, args[1]) && !player.isWhitelisted()) {
                        completions.add(name);
                    }
                }
                return completions;
            }
            if (args[0].equalsIgnoreCase("remove")) {
                final List<String> completions = new ArrayList<String>();
                for (final OfflinePlayer player2 : Bukkit.getWhitelistedPlayers()) {
                    final String name2 = player2.getName();
                    if (StringUtil.startsWithIgnoreCase(name2, args[1])) {
                        completions.add(name2);
                    }
                }
                return completions;
            }
        }
        return (List<String>)ImmutableList.of();
    }
    
    static {
        WHITELIST_SUBCOMMANDS = ImmutableList.of("add", "remove", "on", "off", "list", "reload");
    }
}
