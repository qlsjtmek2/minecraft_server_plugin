// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandTp extends CommandAbstract
{
    public String c() {
        return "tp";
    }
    
    public int a() {
        return 2;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.tp.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length >= 1) {
            EntityPlayer entityPlayer;
            if (array.length == 2 || array.length == 4) {
                entityPlayer = CommandAbstract.c(commandListener, array[0]);
                if (entityPlayer == null) {
                    throw new ExceptionPlayerNotFound();
                }
            }
            else {
                entityPlayer = CommandAbstract.c(commandListener);
            }
            if (array.length == 3 || array.length == 4) {
                if (entityPlayer.world != null) {
                    int n = array.length - 3;
                    final double a = this.a(commandListener, entityPlayer.locX, array[n++]);
                    final double a2 = this.a(commandListener, entityPlayer.locY, array[n++], 0, 0);
                    final double a3 = this.a(commandListener, entityPlayer.locZ, array[n++]);
                    entityPlayer.mount(null);
                    entityPlayer.enderTeleportTo(a, a2, a3);
                    CommandAbstract.a(commandListener, "commands.tp.success.coordinates", entityPlayer.getLocalizedName(), a, a2, a3);
                }
            }
            else if (array.length == 1 || array.length == 2) {
                final EntityPlayer c = CommandAbstract.c(commandListener, array[array.length - 1]);
                if (c == null) {
                    throw new ExceptionPlayerNotFound();
                }
                if (c.world != entityPlayer.world) {
                    CommandAbstract.a(commandListener, "commands.tp.notSameDimension", new Object[0]);
                    return;
                }
                entityPlayer.mount(null);
                entityPlayer.playerConnection.a(c.locX, c.locY, c.locZ, c.yaw, c.pitch);
                CommandAbstract.a(commandListener, "commands.tp.success", entityPlayer.getLocalizedName(), c.getLocalizedName());
            }
            return;
        }
        throw new ExceptionUsage("commands.tp.usage", new Object[0]);
    }
    
    private double a(final ICommandListener commandListener, final double n, final String s) {
        return this.a(commandListener, n, s, -30000000, 30000000);
    }
    
    private double a(final ICommandListener commandListener, final double n, String substring, final int n2, final int n3) {
        final boolean startsWith = substring.startsWith("~");
        double n4 = startsWith ? n : 0.0;
        if (!startsWith || substring.length() > 1) {
            final boolean contains = substring.contains(".");
            if (startsWith) {
                substring = substring.substring(1);
            }
            n4 += CommandAbstract.b(commandListener, substring);
            if (!contains && !startsWith) {
                n4 += 0.5;
            }
        }
        if (n2 != 0 || n3 != 0) {
            if (n4 < n2) {
                throw new ExceptionInvalidNumber("commands.generic.double.tooSmall", new Object[] { n4, n2 });
            }
            if (n4 > n3) {
                throw new ExceptionInvalidNumber("commands.generic.double.tooBig", new Object[] { n4, n3 });
            }
        }
        return n4;
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
