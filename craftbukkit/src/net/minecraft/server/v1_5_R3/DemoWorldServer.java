// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class DemoWorldServer extends WorldServer
{
    private static final long J;
    public static final WorldSettings a;
    
    public DemoWorldServer(final MinecraftServer minecraftServer, final IDataManager dataManager, final String s, final int n, final MethodProfiler methodProfiler, final IConsoleLogManager consoleLogManager) {
        super(minecraftServer, dataManager, s, n, DemoWorldServer.a, methodProfiler, consoleLogManager);
    }
    
    static {
        J = "North Carolina".hashCode();
        a = new WorldSettings(DemoWorldServer.J, EnumGamemode.SURVIVAL, true, false, WorldType.NORMAL).a();
    }
}
