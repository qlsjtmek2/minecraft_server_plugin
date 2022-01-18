// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class CommandSaveAll extends CommandAbstract
{
    public String c() {
        return "save-all";
    }
    
    public int a() {
        return 4;
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        final MinecraftServer server = MinecraftServer.getServer();
        commandListener.sendMessage(commandListener.a("commands.save.start", new Object[0]));
        if (server.getPlayerList() != null) {
            server.getPlayerList().savePlayers();
        }
        try {
            for (int i = 0; i < server.worldServer.length; ++i) {
                if (server.worldServer[i] != null) {
                    final WorldServer worldServer = server.worldServer[i];
                    final boolean savingDisabled = worldServer.savingDisabled;
                    worldServer.savingDisabled = false;
                    worldServer.save(true, null);
                    worldServer.savingDisabled = savingDisabled;
                }
            }
            if (array.length > 0 && "flush".equals(array[0])) {
                commandListener.sendMessage(commandListener.a("commands.save.flushStart", new Object[0]));
                for (int j = 0; j < server.worldServer.length; ++j) {
                    if (server.worldServer[j] != null) {
                        final WorldServer worldServer2 = server.worldServer[j];
                        final boolean savingDisabled2 = worldServer2.savingDisabled;
                        worldServer2.savingDisabled = false;
                        worldServer2.flushSave();
                        worldServer2.savingDisabled = savingDisabled2;
                    }
                }
                commandListener.sendMessage(commandListener.a("commands.save.flushEnd", new Object[0]));
            }
        }
        catch (ExceptionWorldConflict exceptionWorldConflict) {
            CommandAbstract.a(commandListener, "commands.save.failed", exceptionWorldConflict.getMessage());
            return;
        }
        CommandAbstract.a(commandListener, "commands.save.success", new Object[0]);
    }
}
