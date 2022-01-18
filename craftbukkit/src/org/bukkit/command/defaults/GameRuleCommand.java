// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.bukkit.util.StringUtil;
import java.util.Arrays;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.World;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import java.util.List;

public class GameRuleCommand extends VanillaCommand
{
    private static final List<String> GAMERULE_STATES;
    
    public GameRuleCommand() {
        super("gamerule");
        this.description = "Sets a server's game rules";
        this.usageMessage = "/gamerule <rule name> <value> OR /gamerule <rule name>";
        this.setPermission("bukkit.command.gamerule");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length > 0) {
            final String rule = args[0];
            final World world = this.getGameWorld(sender);
            if (world.isGameRule(rule)) {
                if (args.length > 1) {
                    final String value = args[1];
                    world.setGameRuleValue(rule, value);
                    Command.broadcastCommandMessage(sender, "Game rule " + rule + " has been set to: " + value);
                }
                else {
                    final String value = world.getGameRuleValue(rule);
                    sender.sendMessage(rule + " = " + value);
                }
            }
            else {
                sender.sendMessage(ChatColor.RED + "No game rule called " + rule + " is available");
            }
            return true;
        }
        sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
        sender.sendMessage("Rules: " + this.createString(this.getGameWorld(sender).getGameRules(), 0, ", "));
        return true;
    }
    
    private World getGameWorld(final CommandSender sender) {
        if (sender instanceof HumanEntity) {
            final World world = ((HumanEntity)sender).getWorld();
            if (world != null) {
                return world;
            }
        }
        return Bukkit.getWorlds().get(0);
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList(this.getGameWorld(sender).getGameRules()), new ArrayList<String>());
        }
        if (args.length == 2) {
            return StringUtil.copyPartialMatches(args[1], GameRuleCommand.GAMERULE_STATES, new ArrayList<String>(GameRuleCommand.GAMERULE_STATES.size()));
        }
        return (List<String>)ImmutableList.of();
    }
    
    static {
        GAMERULE_STATES = ImmutableList.of("true", "false");
    }
}
