// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;

public class SeedCommand extends VanillaCommand
{
    public SeedCommand() {
        super("seed");
        this.description = "Shows the world seed";
        this.usageMessage = "/seed";
        this.setPermission("bukkit.command.seed");
    }
    
    public boolean execute(final CommandSender sender, final String commandLabel, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        long seed;
        if (sender instanceof Player) {
            seed = ((Player)sender).getWorld().getSeed();
        }
        else {
            seed = Bukkit.getWorlds().get(0).getSeed();
        }
        sender.sendMessage("Seed: " + seed);
        return true;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        return (List<String>)ImmutableList.of();
    }
}
