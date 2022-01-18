// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandDeop extends CommandAbstract
{
    public String c() {
        return "deop";
    }
    
    public int a() {
        return 3;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.deop.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1 && array[0].length() > 0) {
            MinecraftServer.getServer().getPlayerList().removeOp(array[0]);
            CommandAbstract.a(commandListener, "commands.deop.success", array[0]);
            return;
        }
        throw new ExceptionUsage("commands.deop.usage", new Object[0]);
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1) {
            return CommandAbstract.a(array, MinecraftServer.getServer().getPlayerList().getOPs());
        }
        return null;
    }
}
