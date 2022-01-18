// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.util.permissions;

import org.bukkit.permissions.PermissionDefault;
import org.bukkit.permissions.Permission;

public final class CommandPermissions
{
    private static final String ROOT = "bukkit.command";
    private static final String PREFIX = "bukkit.command.";
    
    private static Permission registerWhitelist(final Permission parent) {
        final Permission whitelist = DefaultPermissions.registerPermission("bukkit.command.whitelist", "Allows the user to modify the server whitelist", PermissionDefault.OP, parent);
        DefaultPermissions.registerPermission("bukkit.command.whitelist.add", "Allows the user to add a player to the server whitelist", whitelist);
        DefaultPermissions.registerPermission("bukkit.command.whitelist.remove", "Allows the user to remove a player from the server whitelist", whitelist);
        DefaultPermissions.registerPermission("bukkit.command.whitelist.reload", "Allows the user to reload the server whitelist", whitelist);
        DefaultPermissions.registerPermission("bukkit.command.whitelist.enable", "Allows the user to enable the server whitelist", whitelist);
        DefaultPermissions.registerPermission("bukkit.command.whitelist.disable", "Allows the user to disable the server whitelist", whitelist);
        DefaultPermissions.registerPermission("bukkit.command.whitelist.list", "Allows the user to list all the users on the server whitelist", whitelist);
        whitelist.recalculatePermissibles();
        return whitelist;
    }
    
    private static Permission registerBan(final Permission parent) {
        final Permission ban = DefaultPermissions.registerPermission("bukkit.command.ban", "Allows the user to ban people", PermissionDefault.OP, parent);
        DefaultPermissions.registerPermission("bukkit.command.ban.player", "Allows the user to ban players", ban);
        DefaultPermissions.registerPermission("bukkit.command.ban.ip", "Allows the user to ban IP addresses", ban);
        ban.recalculatePermissibles();
        return ban;
    }
    
    private static Permission registerUnban(final Permission parent) {
        final Permission unban = DefaultPermissions.registerPermission("bukkit.command.unban", "Allows the user to unban people", PermissionDefault.OP, parent);
        DefaultPermissions.registerPermission("bukkit.command.unban.player", "Allows the user to unban players", unban);
        DefaultPermissions.registerPermission("bukkit.command.unban.ip", "Allows the user to unban IP addresses", unban);
        unban.recalculatePermissibles();
        return unban;
    }
    
    private static Permission registerOp(final Permission parent) {
        final Permission op = DefaultPermissions.registerPermission("bukkit.command.op", "Allows the user to change operators", PermissionDefault.OP, parent);
        DefaultPermissions.registerPermission("bukkit.command.op.give", "Allows the user to give a player operator status", op);
        DefaultPermissions.registerPermission("bukkit.command.op.take", "Allows the user to take a players operator status", op);
        op.recalculatePermissibles();
        return op;
    }
    
    private static Permission registerSave(final Permission parent) {
        final Permission save = DefaultPermissions.registerPermission("bukkit.command.save", "Allows the user to save the worlds", PermissionDefault.OP, parent);
        DefaultPermissions.registerPermission("bukkit.command.save.enable", "Allows the user to enable automatic saving", save);
        DefaultPermissions.registerPermission("bukkit.command.save.disable", "Allows the user to disable automatic saving", save);
        DefaultPermissions.registerPermission("bukkit.command.save.perform", "Allows the user to perform a manual save", save);
        save.recalculatePermissibles();
        return save;
    }
    
    private static Permission registerTime(final Permission parent) {
        final Permission time = DefaultPermissions.registerPermission("bukkit.command.time", "Allows the user to alter the time", PermissionDefault.OP, parent);
        DefaultPermissions.registerPermission("bukkit.command.time.add", "Allows the user to fast-forward time", time);
        DefaultPermissions.registerPermission("bukkit.command.time.set", "Allows the user to change the time", time);
        time.recalculatePermissibles();
        return time;
    }
    
    public static Permission registerPermissions(final Permission parent) {
        final Permission commands = DefaultPermissions.registerPermission("bukkit.command", "Gives the user the ability to use all CraftBukkit commands", parent);
        registerWhitelist(commands);
        registerBan(commands);
        registerUnban(commands);
        registerOp(commands);
        registerSave(commands);
        registerTime(commands);
        DefaultPermissions.registerPermission("bukkit.command.kill", "Allows the user to commit suicide", PermissionDefault.TRUE, commands);
        DefaultPermissions.registerPermission("bukkit.command.me", "Allows the user to perform a chat action", PermissionDefault.TRUE, commands);
        DefaultPermissions.registerPermission("bukkit.command.tell", "Allows the user to privately message another player", PermissionDefault.TRUE, commands);
        DefaultPermissions.registerPermission("bukkit.command.say", "Allows the user to talk as the console", PermissionDefault.OP, commands);
        DefaultPermissions.registerPermission("bukkit.command.give", "Allows the user to give items to players", PermissionDefault.OP, commands);
        DefaultPermissions.registerPermission("bukkit.command.teleport", "Allows the user to teleport players", PermissionDefault.OP, commands);
        DefaultPermissions.registerPermission("bukkit.command.kick", "Allows the user to kick players", PermissionDefault.OP, commands);
        DefaultPermissions.registerPermission("bukkit.command.stop", "Allows the user to stop the server", PermissionDefault.OP, commands);
        DefaultPermissions.registerPermission("bukkit.command.list", "Allows the user to list all online players", PermissionDefault.OP, commands);
        DefaultPermissions.registerPermission("bukkit.command.help", "Allows the user to view the vanilla help menu", PermissionDefault.TRUE, commands);
        DefaultPermissions.registerPermission("bukkit.command.plugins", "Allows the user to view the list of plugins running on this server", PermissionDefault.TRUE, commands);
        DefaultPermissions.registerPermission("bukkit.command.reload", "Allows the user to reload the server settings", PermissionDefault.OP, commands);
        DefaultPermissions.registerPermission("bukkit.command.version", "Allows the user to view the version of the server", PermissionDefault.TRUE, commands);
        DefaultPermissions.registerPermission("bukkit.command.gamemode", "Allows the user to change the gamemode of another player", PermissionDefault.OP, commands);
        DefaultPermissions.registerPermission("bukkit.command.xp", "Allows the user to give themselves or others arbitrary values of experience", PermissionDefault.OP, commands);
        DefaultPermissions.registerPermission("bukkit.command.toggledownfall", "Allows the user to toggle rain on/off for a given world", PermissionDefault.OP, commands);
        DefaultPermissions.registerPermission("bukkit.command.defaultgamemode", "Allows the user to change the default gamemode of the server", PermissionDefault.OP, commands);
        DefaultPermissions.registerPermission("bukkit.command.seed", "Allows the user to view the seed of the world", PermissionDefault.OP, commands);
        DefaultPermissions.registerPermission("bukkit.command.effect", "Allows the user to add/remove effects on players", PermissionDefault.OP, commands);
        commands.recalculatePermissibles();
        return commands;
    }
}
