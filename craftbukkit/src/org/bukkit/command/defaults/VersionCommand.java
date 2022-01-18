// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command.defaults;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import com.google.common.collect.ImmutableList;
import org.bukkit.util.StringUtil;
import java.util.ArrayList;
import org.apache.commons.lang.Validate;
import java.util.List;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import java.util.Arrays;

public class VersionCommand extends BukkitCommand
{
    public VersionCommand(final String name) {
        super(name);
        this.description = "Gets the version of this server including any plugins in use";
        this.usageMessage = "/version [plugin name]";
        this.setPermission("bukkit.command.version");
        this.setAliases(Arrays.asList("ver", "about"));
    }
    
    public boolean execute(final CommandSender sender, final String currentAlias, final String[] args) {
        if (!this.testPermission(sender)) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("This server is running " + Bukkit.getName() + " version " + Bukkit.getVersion() + " (Implementing API version " + Bukkit.getBukkitVersion() + ")");
        }
        else {
            final StringBuilder name = new StringBuilder();
            for (final String arg : args) {
                if (name.length() > 0) {
                    name.append(' ');
                }
                name.append(arg);
            }
            String pluginName = name.toString();
            final Plugin exactPlugin = Bukkit.getPluginManager().getPlugin(pluginName);
            if (exactPlugin != null) {
                this.describeToSender(exactPlugin, sender);
                return true;
            }
            boolean found = false;
            pluginName = pluginName.toLowerCase();
            for (final Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                if (plugin.getName().toLowerCase().contains(pluginName)) {
                    this.describeToSender(plugin, sender);
                    found = true;
                }
            }
            if (!found) {
                sender.sendMessage("This server is not running any plugin by that name.");
                sender.sendMessage("Use /plugins to get a list of plugins.");
            }
        }
        return true;
    }
    
    private void describeToSender(final Plugin plugin, final CommandSender sender) {
        final PluginDescriptionFile desc = plugin.getDescription();
        sender.sendMessage(ChatColor.GREEN + desc.getName() + ChatColor.WHITE + " version " + ChatColor.GREEN + desc.getVersion());
        if (desc.getDescription() != null) {
            sender.sendMessage(desc.getDescription());
        }
        if (desc.getWebsite() != null) {
            sender.sendMessage("Website: " + ChatColor.GREEN + desc.getWebsite());
        }
        if (!desc.getAuthors().isEmpty()) {
            if (desc.getAuthors().size() == 1) {
                sender.sendMessage("Author: " + this.getAuthors(desc));
            }
            else {
                sender.sendMessage("Authors: " + this.getAuthors(desc));
            }
        }
    }
    
    private String getAuthors(final PluginDescriptionFile desc) {
        final StringBuilder result = new StringBuilder();
        final List<String> authors = desc.getAuthors();
        for (int i = 0; i < authors.size(); ++i) {
            if (result.length() > 0) {
                result.append(ChatColor.WHITE);
                if (i < authors.size() - 1) {
                    result.append(", ");
                }
                else {
                    result.append(" and ");
                }
            }
            result.append(ChatColor.GREEN);
            result.append(authors.get(i));
        }
        return result.toString();
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (args.length == 1) {
            final List<String> completions = new ArrayList<String>();
            final String toComplete = args[0].toLowerCase();
            for (final Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
                if (StringUtil.startsWithIgnoreCase(plugin.getName(), toComplete)) {
                    completions.add(plugin.getName());
                }
            }
            return completions;
        }
        return (List<String>)ImmutableList.of();
    }
}
