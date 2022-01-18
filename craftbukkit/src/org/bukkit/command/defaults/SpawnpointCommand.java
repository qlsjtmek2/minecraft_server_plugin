// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.World;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.Location;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;

public class SpawnpointCommand extends VanillaCommand
{
    public SpawnpointCommand() {
        super("spawnpoint");
        this.description = "Sets a player's spawn point";
        this.usageMessage = "/spawnpoint OR /spawnpoint <player> OR /spawnpoint <player> <x> <y> <z>";
        this.setPermission("bukkit.command.spawnpoint");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        Player player;
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Please provide a player!");
                return true;
            }
            player = (Player)sender;
        }
        else {
            player = Bukkit.getPlayerExact(args[0]);
            if (player == null) {
                sender.sendMessage("Can't find player " + args[0]);
                return true;
            }
        }
        final World world = player.getWorld();
        if (args.length == 4) {
            if (world != null) {
                int pos = 1;
                int x;
                int y;
                int z;
                try {
                    x = this.getInteger(sender, args[pos++], -30000000, 30000000, true);
                    y = this.getInteger(sender, args[pos++], 0, world.getMaxHeight());
                    z = this.getInteger(sender, args[pos], -30000000, 30000000, true);
                }
                catch (NumberFormatException ex) {
                    sender.sendMessage(ex.getMessage());
                    return true;
                }
                player.setBedSpawnLocation(new Location(world, x, y, z), true);
                Command.broadcastCommandMessage(sender, "Set " + player.getDisplayName() + "'s spawnpoint to " + x + ", " + y + ", " + z);
            }
        }
        else {
            if (args.length > 1) {
                sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
                return false;
            }
            final Location location = player.getLocation();
            player.setBedSpawnLocation(location, true);
            Command.broadcastCommandMessage(sender, "Set " + player.getDisplayName() + "'s spawnpoint to " + location.getX() + ", " + location.getY() + ", " + location.getZ());
        }
        return true;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            return super.tabComplete(sender, alias, args);
        }
        return (List<String>)ImmutableList.of();
    }
}
