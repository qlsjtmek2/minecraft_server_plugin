// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import org.bukkit.command.Command;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.spigotmc.CustomTimingsHandler;
import org.bukkit.command.CommandSender;
import java.util.Arrays;

public class ReloadCommand extends BukkitCommand
{
    public ReloadCommand(final String name) {
        super(name);
        this.description = "Reloads the server configuration and plugins";
        this.usageMessage = "/reload";
        this.setPermission("bukkit.command.reload");
        this.setAliases(Arrays.asList("rl"));
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        CustomTimingsHandler.reload();
        Bukkit.reload();
        Command.broadcastCommandMessage(sender, ChatColor.GREEN + "Reload complete.");
        return true;
    }
}
