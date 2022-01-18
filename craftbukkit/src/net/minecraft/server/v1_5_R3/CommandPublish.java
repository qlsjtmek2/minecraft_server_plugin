// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class CommandPublish extends CommandAbstract
{
    public String c() {
        return "publish";
    }
    
    public int a() {
        return 4;
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        final String a = MinecraftServer.getServer().a(EnumGamemode.SURVIVAL, false);
        if (a != null) {
            CommandAbstract.a(commandListener, "commands.publish.started", a);
        }
        else {
            CommandAbstract.a(commandListener, "commands.publish.failed", new Object[0]);
        }
    }
}
