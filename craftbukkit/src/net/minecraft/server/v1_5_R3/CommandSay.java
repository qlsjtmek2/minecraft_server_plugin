// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandSay extends CommandAbstract
{
    public String c() {
        return "say";
    }
    
    public int a() {
        return 1;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.say.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length > 0 && array[0].length() > 0) {
            MinecraftServer.getServer().getPlayerList().k(String.format("[%s] %s", commandListener.getName(), CommandAbstract.a(commandListener, array, 0, true)));
            return;
        }
        throw new ExceptionUsage("commands.say.usage", new Object[0]);
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length >= 1) {
            return CommandAbstract.a(array, MinecraftServer.getServer().getPlayers());
        }
        return null;
    }
}
