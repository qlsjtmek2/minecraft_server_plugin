// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.command;

import java.util.Iterator;
import java.util.Set;
import org.bukkit.permissions.Permissible;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import java.util.Comparator;
import java.util.Collections;
import org.bukkit.util.StringUtil;
import com.google.common.collect.ImmutableList;
import org.bukkit.entity.Player;
import org.apache.commons.lang.Validate;
import java.util.Collection;
import java.util.ArrayList;
import java.util.List;

public abstract class Command
{
    private final String name;
    private String nextLabel;
    private String label;
    private List<String> aliases;
    private List<String> activeAliases;
    private CommandMap commandMap;
    protected String description;
    protected String usageMessage;
    private String permission;
    private String permissionMessage;
    
    protected Command(final String name) {
        this(name, "", "/" + name, new ArrayList<String>());
    }
    
    protected Command(final String name, final String description, final String usageMessage, final List<String> aliases) {
        this.commandMap = null;
        this.description = "";
        this.name = name;
        this.nextLabel = name;
        this.label = name;
        this.description = description;
        this.usageMessage = usageMessage;
        this.aliases = aliases;
        this.activeAliases = new ArrayList<String>(aliases);
    }
    
    public abstract boolean execute(final CommandSender p0, final String p1, final String[] p2);
    
    @Deprecated
    public List<String> tabComplete(final CommandSender sender, final String[] args) {
        return null;
    }
    
    public List<String> tabComplete(final CommandSender sender, final String alias, final String[] args) throws IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");
        if (!(sender instanceof Player) || args.length == 0) {
            return (List<String>)ImmutableList.of();
        }
        final String lastWord = args[args.length - 1];
        final Player senderPlayer = (Player)sender;
        final ArrayList<String> matchedPlayers = new ArrayList<String>();
        for (final Player player : sender.getServer().getOnlinePlayers()) {
            final String name = player.getName();
            if (senderPlayer.canSee(player) && StringUtil.startsWithIgnoreCase(name, lastWord)) {
                matchedPlayers.add(name);
            }
        }
        Collections.sort(matchedPlayers, String.CASE_INSENSITIVE_ORDER);
        return matchedPlayers;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getPermission() {
        return this.permission;
    }
    
    public void setPermission(final String permission) {
        this.permission = permission;
    }
    
    public boolean testPermission(final CommandSender target) {
        if (this.testPermissionSilent(target)) {
            return true;
        }
        if (this.permissionMessage == null) {
            target.sendMessage(ChatColor.RED + "I'm sorry, but you do not have permission to perform this command. Please contact the server administrators if you believe that this is in error.");
        }
        else if (this.permissionMessage.length() != 0) {
            for (final String line : this.permissionMessage.replace("<permission>", this.permission).split("\n")) {
                target.sendMessage(line);
            }
        }
        return false;
    }
    
    public boolean testPermissionSilent(final CommandSender target) {
        if (this.permission == null || this.permission.length() == 0) {
            return true;
        }
        for (final String p : this.permission.split(";")) {
            if (target.hasPermission(p)) {
                return true;
            }
        }
        return false;
    }
    
    public String getLabel() {
        return this.label;
    }
    
    public boolean setLabel(final String name) {
        this.nextLabel = name;
        if (!this.isRegistered()) {
            this.label = name;
            return true;
        }
        return false;
    }
    
    public boolean register(final CommandMap commandMap) {
        if (this.allowChangesFrom(commandMap)) {
            this.commandMap = commandMap;
            return true;
        }
        return false;
    }
    
    public boolean unregister(final CommandMap commandMap) {
        if (this.allowChangesFrom(commandMap)) {
            this.commandMap = null;
            this.activeAliases = new ArrayList<String>(this.aliases);
            this.label = this.nextLabel;
            return true;
        }
        return false;
    }
    
    private boolean allowChangesFrom(final CommandMap commandMap) {
        return null == this.commandMap || this.commandMap == commandMap;
    }
    
    public boolean isRegistered() {
        return null != this.commandMap;
    }
    
    public List<String> getAliases() {
        return this.activeAliases;
    }
    
    public String getPermissionMessage() {
        return this.permissionMessage;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getUsage() {
        return this.usageMessage;
    }
    
    public Command setAliases(final List<String> aliases) {
        this.aliases = aliases;
        if (!this.isRegistered()) {
            this.activeAliases = new ArrayList<String>(aliases);
        }
        return this;
    }
    
    public Command setDescription(final String description) {
        this.description = description;
        return this;
    }
    
    public Command setPermissionMessage(final String permissionMessage) {
        this.permissionMessage = permissionMessage;
        return this;
    }
    
    public Command setUsage(final String usage) {
        this.usageMessage = usage;
        return this;
    }
    
    public static void broadcastCommandMessage(final CommandSender source, final String message) {
        broadcastCommandMessage(source, message, true);
    }
    
    public static void broadcastCommandMessage(final CommandSender source, final String message, final boolean sendToSource) {
        final String result = source.getName() + ": " + message;
        if (source instanceof BlockCommandSender && ((BlockCommandSender)source).getBlock().getWorld().getGameRuleValue("commandBlockOutput").equalsIgnoreCase("false")) {
            Bukkit.getConsoleSender().sendMessage(result);
            return;
        }
        final Set<Permissible> users = Bukkit.getPluginManager().getPermissionSubscriptions("bukkit.broadcast.admin");
        final String colored = ChatColor.GRAY + "" + ChatColor.ITALIC + "[" + result + "]";
        if (sendToSource && !(source instanceof ConsoleCommandSender)) {
            source.sendMessage(message);
        }
        for (final Permissible user : users) {
            if (user instanceof CommandSender) {
                final CommandSender target = (CommandSender)user;
                if (target instanceof ConsoleCommandSender) {
                    target.sendMessage(result);
                }
                else {
                    if (target == source) {
                        continue;
                    }
                    target.sendMessage(colored);
                }
            }
        }
    }
    
    public String toString() {
        return this.getClass().getName() + '(' + this.name + ')';
    }
}
