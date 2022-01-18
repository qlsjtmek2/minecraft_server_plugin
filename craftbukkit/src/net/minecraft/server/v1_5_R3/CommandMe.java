// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandMe extends CommandAbstract
{
    public String c() {
        return "me";
    }
    
    public int a() {
        return 0;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.me.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length > 0) {
            MinecraftServer.getServer().getPlayerList().sendAll(new Packet3Chat("* " + commandListener.getName() + " " + CommandAbstract.a(commandListener, array, 0, commandListener.a(1, "me"))));
            return;
        }
        throw new ExceptionUsage("commands.me.usage", new Object[0]);
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        return CommandAbstract.a(array, MinecraftServer.getServer().getPlayers());
    }
}
