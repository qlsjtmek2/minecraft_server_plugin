// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandPardon extends CommandAbstract
{
    public String c() {
        return "pardon";
    }
    
    public int a() {
        return 3;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.unban.usage", new Object[0]);
    }
    
    public boolean b(final ICommandListener commandListener) {
        return MinecraftServer.getServer().getPlayerList().getNameBans().isEnabled() && super.b(commandListener);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1 && array[0].length() > 0) {
            MinecraftServer.getServer().getPlayerList().getNameBans().remove(array[0]);
            CommandAbstract.a(commandListener, "commands.unban.success", array[0]);
            return;
        }
        throw new ExceptionUsage("commands.unban.usage", new Object[0]);
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1) {
            return CommandAbstract.a(array, MinecraftServer.getServer().getPlayerList().getNameBans().getEntries().keySet());
        }
        return null;
    }
}
