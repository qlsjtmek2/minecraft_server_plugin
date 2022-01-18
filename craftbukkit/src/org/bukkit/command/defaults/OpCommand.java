// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import java.util.Comparator;
import java.util.Collections;
import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import org.bukkit.entity.Player;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class OpCommand extends VanillaCommand
{
    public OpCommand() {
        super("op");
        this.description = "Gives the specified player operator status";
        this.usageMessage = "/op <player>";
        this.setPermission("bukkit.command.op.give");
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
        player.setOp(true);
        Command.broadcastCommandMessage(sender, "Opped " + args[0]);
        return true;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length != 1) {
            return (List<String>)ImmutableList.of();
        }
        if (!(sender instanceof Player)) {
            return (List<String>)ImmutableList.of();
        }
        final String lastWord = args[0];
        if (lastWord.length() == 0) {
            return (List<String>)ImmutableList.of();
        }
        final Player senderPlayer = (Player)sender;
        final ArrayList<String> matchedPlayers = new ArrayList<String>();
        for (final Player player : sender.getServer().getOnlinePlayers()) {
            final String name = player.getName();
            if (senderPlayer.canSee(player)) {
                if (!player.isOp()) {
                    if (StringUtil.startsWithIgnoreCase(name, lastWord)) {
                        matchedPlayers.add(name);
                    }
                }
            }
        }
        Collections.sort(matchedPlayers, String.CASE_INSENSITIVE_ORDER);
        return matchedPlayers;
    }
}
