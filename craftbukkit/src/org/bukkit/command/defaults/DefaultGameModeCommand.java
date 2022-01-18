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
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import java.util.List;

public class DefaultGameModeCommand extends VanillaCommand
{
    private static final List<String> GAMEMODE_NAMES;
    
    public DefaultGameModeCommand() {
        super("defaultgamemode");
        this.description = "Set the default gamemode";
        this.usageMessage = "/defaultgamemode <mode>";
        this.setPermission("bukkit.command.defaultgamemode");
    }
    
    public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("Usage: " + this.usageMessage);
            return false;
        }
        final String modeArg = args[0];
        int value = -1;
        try {
            value = Integer.parseInt(modeArg);
        }
        catch (NumberFormatException ex) {}
        GameMode mode = GameMode.getByValue(value);
        if (mode == null) {
            if (modeArg.equalsIgnoreCase("creative") || modeArg.equalsIgnoreCase("c")) {
                mode = GameMode.CREATIVE;
            }
            else if (modeArg.equalsIgnoreCase("adventure") || modeArg.equalsIgnoreCase("a")) {
                mode = GameMode.ADVENTURE;
            }
            else {
                mode = GameMode.SURVIVAL;
            }
        }
        Bukkit.getServer().setDefaultGameMode(mode);
        Command.broadcastCommandMessage(sender, "Default game mode set to " + mode.toString().toLowerCase());
        return true;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], DefaultGameModeCommand.GAMEMODE_NAMES, new ArrayList<String>(DefaultGameModeCommand.GAMEMODE_NAMES.size()));
        }
        return (List<String>)ImmutableList.of();
    }
    
    static {
        GAMEMODE_NAMES = ImmutableList.of("adventure", "creative", "survival");
    }
}
