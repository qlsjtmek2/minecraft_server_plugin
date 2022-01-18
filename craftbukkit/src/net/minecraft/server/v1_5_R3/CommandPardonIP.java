// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandPardonIP extends CommandAbstract
{
    public String c() {
        return "pardon-ip";
    }
    
    public int a() {
        return 3;
    }
    
    public boolean b(final ICommandListener commandListener) {
        return MinecraftServer.getServer().getPlayerList().getIPBans().isEnabled() && super.b(commandListener);
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.unbanip.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length != 1 || array[0].length() <= 1) {
            throw new ExceptionUsage("commands.unbanip.usage", new Object[0]);
        }
        if (CommandBanIp.a.matcher(array[0]).matches()) {
            MinecraftServer.getServer().getPlayerList().getIPBans().remove(array[0]);
            CommandAbstract.a(commandListener, "commands.unbanip.success", array[0]);
            return;
        }
        throw new ExceptionInvalidSyntax("commands.unbanip.invalid", new Object[0]);
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1) {
            return CommandAbstract.a(array, MinecraftServer.getServer().getPlayerList().getIPBans().getEntries().keySet());
        }
        return null;
    }
}
