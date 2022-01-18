// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.bukkit.plugin.Plugin;
import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import java.util.Arrays;

public class PluginsCommand extends BukkitCommand
{
    public PluginsCommand(final String name) {
        super(name);
        this.description = "Gets a list of plugins running on the server";
        this.usageMessage = "/plugins";
        this.setPermission("bukkit.command.plugins");
        this.setAliases(Arrays.asList("pl"));
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        sender.sendMessage("Plugins " + this.getPluginList());
        return true;
    }
    
    private String getPluginList() {
        final StringBuilder pluginList = new StringBuilder();
        final Plugin[] arr$;
        final Plugin[] plugins = arr$ = Bukkit.getPluginManager().getPlugins();
        for (final Plugin plugin : arr$) {
            if (pluginList.length() > 0) {
                pluginList.append(ChatColor.WHITE);
                pluginList.append(", ");
            }
            pluginList.append(plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED);
            pluginList.append(plugin.getDescription().getName());
        }
        return "(" + plugins.length + "): " + pluginList.toString();
    }
}
