// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class CommandList extends CommandAbstract
{
    public String c() {
        return "list";
    }
    
    public int a() {
        return 0;
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        commandListener.sendMessage(commandListener.a("commands.players.list", MinecraftServer.getServer().y(), MinecraftServer.getServer().z()));
        commandListener.sendMessage(MinecraftServer.getServer().getPlayerList().c());
    }
}
