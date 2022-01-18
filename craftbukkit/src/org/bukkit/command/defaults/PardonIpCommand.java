// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PardonIpCommand extends VanillaCommand
{
    public PardonIpCommand() {
        super("pardon-ip");
        this.description = "Allows the specified IP address to use this server";
        this.usageMessage = "/pardon-ip <address>";
        this.setPermission("bukkit.command.unban.ip");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        if (BanIpCommand.ipValidity.matcher(args[0]).matches()) {
            Bukkit.unbanIP(args[0]);
            Command.broadcastCommandMessage(sender, "Pardoned ip " + args[0]);
        }
        else {
            sender.sendMessage("Invalid ip");
        }
        return true;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Bukkit.getIPBans(), new ArrayList<String>());
        }
        return (List<String>)ImmutableList.of();
    }
}
