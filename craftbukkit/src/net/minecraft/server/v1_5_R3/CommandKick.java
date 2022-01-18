// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandKick extends CommandAbstract
{
    public String c() {
        return "kick";
    }
    
    public int a() {
        return 3;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.kick.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length <= 0 || array[0].length() <= 1) {
            throw new ExceptionUsage("commands.kick.usage", new Object[0]);
        }
        final EntityPlayer player = MinecraftServer.getServer().getPlayerList().getPlayer(array[0]);
        String a = "Kicked by an operator.";
        boolean b = false;
        if (player == null) {
            throw new ExceptionPlayerNotFound();
        }
        if (array.length >= 2) {
            a = CommandAbstract.a(commandListener, array, 1);
            b = true;
        }
        player.playerConnection.disconnect(a);
        if (b) {
            CommandAbstract.a(commandListener, "commands.kick.success.reason", player.getLocalizedName(), a);
        }
        else {
            CommandAbstract.a(commandListener, "commands.kick.success", player.getLocalizedName());
        }
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length >= 1) {
            return CommandAbstract.a(array, MinecraftServer.getServer().getPlayers());
        }
        return null;
    }
}
