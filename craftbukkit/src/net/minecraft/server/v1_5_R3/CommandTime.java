// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandTime extends CommandAbstract
{
    public String c() {
        return "time";
    }
    
    public int a() {
        return 2;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.time.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length > 1) {
            if (array[0].equals("set")) {
                int a;
                if (array[1].equals("day")) {
                    a = 0;
                }
                else if (array[1].equals("night")) {
                    a = 12500;
                }
                else {
                    a = CommandAbstract.a(commandListener, array[1], 0);
                }
                this.a(commandListener, a);
                CommandAbstract.a(commandListener, "commands.time.set", new Object[] { a });
                return;
            }
            if (array[0].equals("add")) {
                final int a2 = CommandAbstract.a(commandListener, array[1], 0);
                this.b(commandListener, a2);
                CommandAbstract.a(commandListener, "commands.time.added", new Object[] { a2 });
                return;
            }
        }
        throw new ExceptionUsage("commands.time.usage", new Object[0]);
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1) {
            return CommandAbstract.a(array, "set", "add");
        }
        if (array.length == 2 && array[0].equals("set")) {
            return CommandAbstract.a(array, "day", "night");
        }
        return null;
    }
    
    protected void a(final ICommandListener commandListener, final int n) {
        for (int i = 0; i < MinecraftServer.getServer().worldServer.length; ++i) {
            MinecraftServer.getServer().worldServer[i].setDayTime(n);
        }
    }
    
    protected void b(final ICommandListener commandListener, final int n) {
        for (int i = 0; i < MinecraftServer.getServer().worldServer.length; ++i) {
            final WorldServer worldServer = MinecraftServer.getServer().worldServer[i];
            worldServer.setDayTime(worldServer.getDayTime() + n);
        }
    }
}
