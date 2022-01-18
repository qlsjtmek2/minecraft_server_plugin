// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandBan extends CommandAbstract
{
    public String c() {
        return "ban";
    }
    
    public int a() {
        return 3;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.ban.usage", new Object[0]);
    }
    
    public boolean b(final ICommandListener commandListener) {
        return MinecraftServer.getServer().getPlayerList().getNameBans().isEnabled() && super.b(commandListener);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length >= 1 && array[0].length() > 0) {
            final EntityPlayer player = MinecraftServer.getServer().getPlayerList().getPlayer(array[0]);
            final BanEntry banEntry = new BanEntry(array[0]);
            banEntry.setSource(commandListener.getName());
            if (array.length >= 2) {
                banEntry.setReason(CommandAbstract.a(commandListener, array, 1));
            }
            MinecraftServer.getServer().getPlayerList().getNameBans().add(banEntry);
            if (player != null) {
                player.playerConnection.disconnect("You are banned from this server.");
            }
            CommandAbstract.a(commandListener, "commands.ban.success", array[0]);
            return;
        }
        throw new ExceptionUsage("commands.ban.usage", new Object[0]);
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length >= 1) {
            return CommandAbstract.a(array, MinecraftServer.getServer().getPlayers());
        }
        return null;
    }
}
