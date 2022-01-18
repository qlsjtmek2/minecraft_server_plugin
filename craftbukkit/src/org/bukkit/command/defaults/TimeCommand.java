// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import java.util.Iterator;
import org.bukkit.command.Command;
import org.bukkit.World;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import java.util.List;

public class TimeCommand extends VanillaCommand
{
    private static final List<String> TABCOMPLETE_ADD_SET;
    private static final List<String> TABCOMPLETE_DAY_NIGHT;
    
    public TimeCommand() {
        super("time");
        this.description = "Changes the time on each world";
        this.usageMessage = "/time set <value>\n/time add <value>";
        this.setPermission("bukkit.command.time.add;bukkit.command.time.set");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Incorrect usage. Correct usage:\n" + this.usageMessage);
            return false;
        }
        if (args[0].equals("set")) {
            if (!sender.hasPermission("bukkit.command.time.set")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to set the time");
                return true;
            }
            int value;
            if (args[1].equals("day")) {
                value = 0;
            }
            else if (args[1].equals("night")) {
                value = 12500;
            }
            else {
                value = this.getInteger(sender, args[1], 0);
            }
            for (final World world : Bukkit.getWorlds()) {
                world.setTime(value);
            }
            Command.broadcastCommandMessage(sender, "Set time to " + value);
        }
        else if (args[0].equals("add")) {
            if (!sender.hasPermission("bukkit.command.time.add")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to set the time");
                return true;
            }
            final int value = this.getInteger(sender, args[1], 0);
            for (final World world : Bukkit.getWorlds()) {
                world.setFullTime(world.getFullTime() + value);
            }
            Command.broadcastCommandMessage(sender, "Added " + value + " to time");
        }
        else {
            sender.sendMessage("Unknown method. Usage: " + this.usageMessage);
        }
        return true;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], TimeCommand.TABCOMPLETE_ADD_SET, new ArrayList<String>(TimeCommand.TABCOMPLETE_ADD_SET.size()));
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            return StringUtil.copyPartialMatches(args[1], TimeCommand.TABCOMPLETE_DAY_NIGHT, new ArrayList<String>(TimeCommand.TABCOMPLETE_DAY_NIGHT.size()));
        }
        return (List<String>)ImmutableList.of();
    }
    
    static {
        TABCOMPLETE_ADD_SET = ImmutableList.of("add", "set");
        TABCOMPLETE_DAY_NIGHT = ImmutableList.of("day", "night");
    }
}
