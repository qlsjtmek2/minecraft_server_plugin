// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandBanList extends CommandAbstract
{
    public String c() {
        return "banlist";
    }
    
    public int a() {
        return 3;
    }
    
    public boolean b(final ICommandListener commandListener) {
        return (MinecraftServer.getServer().getPlayerList().getIPBans().isEnabled() || MinecraftServer.getServer().getPlayerList().getNameBans().isEnabled()) && super.b(commandListener);
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.banlist.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length >= 1 && array[0].equalsIgnoreCase("ips")) {
            commandListener.sendMessage(commandListener.a("commands.banlist.ips", MinecraftServer.getServer().getPlayerList().getIPBans().getEntries().size()));
            commandListener.sendMessage(CommandAbstract.a(MinecraftServer.getServer().getPlayerList().getIPBans().getEntries().keySet().toArray()));
        }
        else {
            commandListener.sendMessage(commandListener.a("commands.banlist.players", MinecraftServer.getServer().getPlayerList().getNameBans().getEntries().size()));
            commandListener.sendMessage(CommandAbstract.a(MinecraftServer.getServer().getPlayerList().getNameBans().getEntries().keySet().toArray()));
        }
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1) {
            return CommandAbstract.a(array, "players", "ips");
        }
        return null;
    }
}
