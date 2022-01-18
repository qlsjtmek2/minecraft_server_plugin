// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class CommandSaveOff extends CommandAbstract
{
    public String c() {
        return "save-off";
    }
    
    public int a() {
        return 4;
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        final MinecraftServer server = MinecraftServer.getServer();
        for (int i = 0; i < server.worldServer.length; ++i) {
            if (server.worldServer[i] != null) {
                server.worldServer[i].savingDisabled = true;
            }
        }
        CommandAbstract.a(commandListener, "commands.save.disabled", new Object[0]);
    }
}
