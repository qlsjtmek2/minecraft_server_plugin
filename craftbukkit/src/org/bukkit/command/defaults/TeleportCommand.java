// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class TeleportCommand extends VanillaCommand
{
    public TeleportCommand() {
        super("tp");
        this.description = "Teleports the given player (or yourself) to another player or coordinates";
        this.usageMessage = "/tp [player] <target> and/or <x> <y> <z>";
        this.setPermission("bukkit.command.teleport");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length < 1 || args.length > 4) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        Player player;
        if (args.length == 1 || args.length == 3) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Please provide a player!");
                return true;
            }
            player = (Player)sender;
        }
        else {
            player = Bukkit.getPlayerExact(args[0]);
        }
        if (player == null) {
            sender.sendMessage("Player not found: " + args[0]);
            return true;
        }
        if (args.length < 3) {
            final Player target = Bukkit.getPlayerExact(args[args.length - 1]);
            if (target == null) {
                sender.sendMessage("Can't find player " + args[args.length - 1] + ". No tp.");
                return true;
            }
            player.teleport(target, PlayerTeleportEvent.TeleportCause.COMMAND);
            Command.broadcastCommandMessage(sender, "Teleported " + player.getDisplayName() + " to " + target.getDisplayName());
        }
        else if (player.getWorld() != null) {
            final Location playerLocation = player.getLocation();
            final double x = this.getCoordinate(sender, playerLocation.getX(), args[args.length - 3]);
            final double y = this.getCoordinate(sender, playerLocation.getY(), args[args.length - 2], 0, 0);
            final double z = this.getCoordinate(sender, playerLocation.getZ(), args[args.length - 1]);
            if (x == -3.0000001E7 || y == -3.0000001E7 || z == -3.0000001E7) {
                sender.sendMessage("Please provide a valid location!");
                return true;
            }
            playerLocation.setX(x);
            playerLocation.setY(y);
            playerLocation.setZ(z);
            player.teleport(playerLocation);
            Command.broadcastCommandMessage(sender, String.format("Teleported %s to %.2f, %.2f, %.2f", player.getDisplayName(), x, y, z));
        }
        return true;
    }
    
    private double getCoordinate(final CommandSender sender, final double current, final String input) {
        return this.getCoordinate(sender, current, input, -30000000, 30000000);
    }
    
    private double getCoordinate(final CommandSender sender, final double current, String input, final int min, final int max) {
        final boolean relative = input.startsWith("~");
        double result = relative ? current : 0.0;
        if (!relative || input.length() > 1) {
            final boolean exact = input.contains(".");
            if (relative) {
                input = input.substring(1);
            }
            final double testResult = VanillaCommand.getDouble(sender, input);
            if (testResult == -3.0000001E7) {
                return -3.0000001E7;
            }
            result += testResult;
            if (!exact && !relative) {
                result += 0.5;
            }
        }
        if (min != 0 || max != 0) {
            if (result < min) {
                result = -3.0000001E7;
            }
            if (result > max) {
                result = -3.0000001E7;
            }
        }
        return result;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1 || args.length == 2) {
            return super.tabComplete(sender, alias, args);
        }
        return (List<String>)ImmutableList.of();
    }
}
