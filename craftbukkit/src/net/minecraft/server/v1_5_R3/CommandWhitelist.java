// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import java.util.List;

public class CommandWhitelist extends CommandAbstract
{
    public String c() {
        return "whitelist";
    }
    
    public int a() {
        return 3;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.whitelist.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length >= 1) {
            if (array[0].equals("on")) {
                MinecraftServer.getServer().getPlayerList().setHasWhitelist(true);
                CommandAbstract.a(commandListener, "commands.whitelist.enabled", new Object[0]);
                return;
            }
            if (array[0].equals("off")) {
                MinecraftServer.getServer().getPlayerList().setHasWhitelist(false);
                CommandAbstract.a(commandListener, "commands.whitelist.disabled", new Object[0]);
                return;
            }
            if (array[0].equals("list")) {
                commandListener.sendMessage(commandListener.a("commands.whitelist.list", MinecraftServer.getServer().getPlayerList().getWhitelisted().size(), MinecraftServer.getServer().getPlayerList().getSeenPlayers().length));
                commandListener.sendMessage(CommandAbstract.a((Object[])MinecraftServer.getServer().getPlayerList().getWhitelisted().toArray(new String[0])));
                return;
            }
            if (array[0].equals("add")) {
                if (array.length < 2) {
                    throw new ExceptionUsage("commands.whitelist.add.usage", new Object[0]);
                }
                MinecraftServer.getServer().getPlayerList().addWhitelist(array[1]);
                CommandAbstract.a(commandListener, "commands.whitelist.add.success", array[1]);
                return;
            }
            else if (array[0].equals("remove")) {
                if (array.length < 2) {
                    throw new ExceptionUsage("commands.whitelist.remove.usage", new Object[0]);
                }
                MinecraftServer.getServer().getPlayerList().removeWhitelist(array[1]);
                CommandAbstract.a(commandListener, "commands.whitelist.remove.success", array[1]);
                return;
            }
            else if (array[0].equals("reload")) {
                MinecraftServer.getServer().getPlayerList().reloadWhitelist();
                CommandAbstract.a(commandListener, "commands.whitelist.reloaded", new Object[0]);
                return;
            }
        }
        throw new ExceptionUsage("commands.whitelist.usage", new Object[0]);
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1) {
            return CommandAbstract.a(array, "on", "off", "list", "add", "remove", "reload");
        }
        if (array.length == 2) {
            if (array[0].equals("add")) {
                final String[] seenPlayers = MinecraftServer.getServer().getPlayerList().getSeenPlayers();
                final ArrayList<String> list = new ArrayList<String>();
                final String s = array[array.length - 1];
                for (final String s2 : seenPlayers) {
                    if (CommandAbstract.a(s, s2) && !MinecraftServer.getServer().getPlayerList().getWhitelisted().contains(s2)) {
                        list.add(s2);
                    }
                }
                return list;
            }
            if (array[0].equals("remove")) {
                return CommandAbstract.a(array, MinecraftServer.getServer().getPlayerList().getWhitelisted());
            }
        }
        return null;
    }
}
