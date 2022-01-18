// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class CommandStop extends CommandAbstract
{
    public String c() {
        return "stop";
    }
    
    public int a() {
        return 4;
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        CommandAbstract.a(commandListener, "commands.stop.start", new Object[0]);
        MinecraftServer.getServer().safeShutdown();
    }
}
