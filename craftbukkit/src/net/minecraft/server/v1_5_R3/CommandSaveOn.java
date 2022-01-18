// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class CommandSaveOn extends CommandAbstract
{
    public String c() {
        return "save-on";
    }
    
    public int a() {
        return 4;
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        final MinecraftServer server = MinecraftServer.getServer();
        for (int i = 0; i < server.worldServer.length; ++i) {
            if (server.worldServer[i] != null) {
                server.worldServer[i].savingDisabled = false;
            }
        }
        CommandAbstract.a(commandListener, "commands.save.enabled", new Object[0]);
    }
}
