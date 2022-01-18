// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import org.bukkit.command.Command;
import org.bukkit.Bukkit;
import org.bukkit.World;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import java.util.List;

public class WeatherCommand extends VanillaCommand
{
    private static final List<String> WEATHER_TYPES;
    
    public WeatherCommand() {
        super("weather");
        this.description = "Changes the weather";
        this.usageMessage = "/weather <clear/rain/thunder> [duration in seconds]";
        this.setPermission("bukkit.command.weather");
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
            return false;
        }
        int duration = (300 + new Random().nextInt(600)) * 20;
        if (args.length >= 2) {
            duration = this.getInteger(sender, args[1], 1, 1000000) * 20;
        }
        final World world = Bukkit.getWorlds().get(0);
        world.setWeatherDuration(duration);
        world.setThunderDuration(duration);
        if ("clear".equalsIgnoreCase(args[0])) {
            world.setStorm(false);
            world.setThundering(false);
            Command.broadcastCommandMessage(sender, "Changed weather to clear for " + duration / 20 + " seconds.");
        }
        else if ("rain".equalsIgnoreCase(args[0])) {
            world.setStorm(true);
            world.setThundering(false);
            Command.broadcastCommandMessage(sender, "Changed weather to rainy for " + duration / 20 + " seconds.");
        }
        else if ("thunder".equalsIgnoreCase(args[0])) {
            world.setStorm(true);
            world.setThundering(true);
            Command.broadcastCommandMessage(sender, "Changed weather to thundering " + duration / 20 + " seconds.");
        }
        return true;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], WeatherCommand.WEATHER_TYPES, new ArrayList<String>(WeatherCommand.WEATHER_TYPES.size()));
        }
        return (List<String>)ImmutableList.of();
    }
    
    static {
        WEATHER_TYPES = ImmutableList.of("clear", "rain", "thunder");
    }
}
