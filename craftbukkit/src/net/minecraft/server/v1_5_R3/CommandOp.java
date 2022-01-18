// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.ArrayList;
import java.util.List;

public class CommandOp extends CommandAbstract
{
    public String c() {
        return "op";
    }
    
    public int a() {
        return 3;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.op.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1 && array[0].length() > 0) {
            MinecraftServer.getServer().getPlayerList().addOp(array[0]);
            CommandAbstract.a(commandListener, "commands.op.success", array[0]);
            return;
        }
        throw new ExceptionUsage("commands.op.usage", new Object[0]);
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1) {
            final String s = array[array.length - 1];
            final ArrayList<String> list = new ArrayList<String>();
            for (final String s2 : MinecraftServer.getServer().getPlayers()) {
                if (!MinecraftServer.getServer().getPlayerList().isOp(s2) && CommandAbstract.a(s, s2)) {
                    list.add(s2);
                }
            }
            return list;
        }
        return null;
    }
}
