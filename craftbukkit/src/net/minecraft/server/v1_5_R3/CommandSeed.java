// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class CommandSeed extends CommandAbstract
{
    public boolean b(final ICommandListener commandListener) {
        return MinecraftServer.getServer().I() || super.b(commandListener);
    }
    
    public String c() {
        return "seed";
    }
    
    public int a() {
        return 2;
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        commandListener.sendMessage("Seed: " + ((commandListener instanceof EntityHuman) ? ((EntityHuman)commandListener).world : MinecraftServer.getServer().getWorldServer(0)).getSeed());
    }
}
