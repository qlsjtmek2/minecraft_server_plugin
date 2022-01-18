// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class DeopCommand extends VanillaCommand
{
    public DeopCommand() {
        super("deop");
        this.description = "Takes the specified player's operator status";
        this.usageMessage = "/deop <player>";
        this.setPermission("bukkit.command.op.take");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length != 1 || args[0].length() == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        final OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
        player.setOp(false);
        if (player instanceof Player) {
            ((Player)player).sendMessage(ChatColor.YELLOW + "You are no longer op!");
        }
        Command.broadcastCommandMessage(sender, "De-opped " + args[0]);
        return true;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            final List<String> completions = new ArrayList<String>();
            for (final OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                final String playerName = player.getName();
                if (player.isOp() && StringUtil.startsWithIgnoreCase(playerName, args[0])) {
                    completions.add(playerName);
                }
            }
            return completions;
        }
        return (List<String>)ImmutableList.of();
    }
}
