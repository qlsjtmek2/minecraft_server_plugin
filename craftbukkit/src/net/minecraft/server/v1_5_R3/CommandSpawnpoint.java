// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandSpawnpoint extends CommandAbstract
{
    public String c() {
        return "spawnpoint";
    }
    
    public int a() {
        return 2;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.spawnpoint.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        final EntityPlayer entityPlayer = (array.length == 0) ? CommandAbstract.c(commandListener) : CommandAbstract.c(commandListener, array[0]);
        if (array.length == 4) {
            if (entityPlayer.world != null) {
                int n = 1;
                final int n2 = 30000000;
                final int a = CommandAbstract.a(commandListener, array[n++], -n2, n2);
                final int a2 = CommandAbstract.a(commandListener, array[n++], 0, 256);
                final int a3 = CommandAbstract.a(commandListener, array[n++], -n2, n2);
                entityPlayer.setRespawnPosition(new ChunkCoordinates(a, a2, a3), true);
                CommandAbstract.a(commandListener, "commands.spawnpoint.success", entityPlayer.getLocalizedName(), a, a2, a3);
            }
        }
        else {
            if (array.length > 1) {
                throw new ExceptionUsage("commands.spawnpoint.usage", new Object[0]);
            }
            final ChunkCoordinates b = entityPlayer.b();
            entityPlayer.setRespawnPosition(b, true);
            CommandAbstract.a(commandListener, "commands.spawnpoint.success", entityPlayer.getLocalizedName(), b.x, b.y, b.z);
        }
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1 || array.length == 2) {
            return CommandAbstract.a(array, MinecraftServer.getServer().getPlayers());
        }
        return null;
    }
    
    public boolean a(final String[] array, final int n) {
        return n == 0;
    }
}
