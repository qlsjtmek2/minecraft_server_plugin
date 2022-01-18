// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ExpCommand extends VanillaCommand
{
    public ExpCommand() {
        super("xp");
        this.description = "Gives the specified player a certain amount of experience. Specify <amount>L to give levels instead, with a negative amount resulting in taking levels.";
        this.usageMessage = "/xp <amount> [player] OR /xp <amount>L [player]";
        this.setPermission("bukkit.command.xp");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length <= 0) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        String inputAmount = args[0];
        Player player = null;
        final boolean isLevel = inputAmount.endsWith("l") || inputAmount.endsWith("L");
        if (isLevel && inputAmount.length() > 1) {
            inputAmount = inputAmount.substring(0, inputAmount.length() - 1);
        }
        int amount = this.getInteger(sender, inputAmount, Integer.MIN_VALUE, Integer.MAX_VALUE);
        final boolean isTaking = amount < 0;
        if (isTaking) {
            amount *= -1;
        }
        if (args.length > 1) {
            player = Bukkit.getPlayer(args[1]);
        }
        else if (sender instanceof Player) {
            player = (Player)sender;
        }
        if (player != null) {
            if (isLevel) {
                if (isTaking) {
                    player.giveExpLevels(-amount);
                    Command.broadcastCommandMessage(sender, "Taken " + amount + " level(s) from " + player.getName());
                }
                else {
                    player.giveExpLevels(amount);
                    Command.broadcastCommandMessage(sender, "Given " + amount + " level(s) to " + player.getName());
                }
            }
            else {
                if (isTaking) {
                    sender.sendMessage(ChatColor.RED + "Taking experience can only be done by levels, cannot give players negative experience points");
                    return false;
                }
                player.giveExp(amount);
                Command.broadcastCommandMessage(sender, "Given " + amount + " experience to " + player.getName());
            }
            return true;
        }
        sender.sendMessage("Can't find player, was one provided?\n" + ChatColor.RED + "Usage: " + this.usageMessage);
        return false;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 2) {
            return super.tabComplete(sender, alias, args);
        }
        return (List<String>)ImmutableList.of();
    }
}
