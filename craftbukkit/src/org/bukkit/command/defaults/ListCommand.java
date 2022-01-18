// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class ListCommand extends VanillaCommand
{
    public ListCommand() {
        super("list");
        this.description = "Lists all online players";
        this.usageMessage = "/list";
        this.setPermission("bukkit.command.list");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        final StringBuilder online = new StringBuilder();
        final Player[] arr$;
        final Player[] players = arr$ = Bukkit.getOnlinePlayers();
        for (final Player player : arr$) {
            if (!(sender instanceof Player) || ((Player)sender).canSee(player)) {
                if (online.length() > 0) {
                    online.append(", ");
                }
                online.append(player.getDisplayName());
            }
        }
        sender.sendMessage("There are " + players.length + "/" + Bukkit.getMaxPlayers() + " players online:\n" + online.toString());
        return true;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        return (List<String>)ImmutableList.of();
    }
}
