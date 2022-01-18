// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class CommandToggleDownfall extends CommandAbstract
{
    public String c() {
        return "toggledownfall";
    }
    
    public int a() {
        return 2;
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        this.d();
        CommandAbstract.a(commandListener, "commands.downfall.success", new Object[0]);
    }
    
    protected void d() {
        MinecraftServer.getServer().worldServer[0].A();
        MinecraftServer.getServer().worldServer[0].getWorldData().setThundering(true);
    }
}
