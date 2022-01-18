// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Arrays;
import java.util.List;

public class CommandTell extends CommandAbstract
{
    public List b() {
        return Arrays.asList("w", "msg");
    }
    
    public String c() {
        return "tell";
    }
    
    public int a() {
        return 0;
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length < 2) {
            throw new ExceptionUsage("commands.message.usage", new Object[0]);
        }
        final EntityPlayer c = CommandAbstract.c(commandListener, array[0]);
        if (c == null) {
            throw new ExceptionPlayerNotFound();
        }
        if (c == commandListener) {
            throw new ExceptionPlayerNotFound("commands.message.sameTarget", new Object[0]);
        }
        final String a = CommandAbstract.a(commandListener, array, 1, !(commandListener instanceof EntityHuman));
        c.sendMessage(EnumChatFormat.GRAY + "" + EnumChatFormat.ITALIC + c.a("commands.message.display.incoming", commandListener.getName(), a));
        commandListener.sendMessage(EnumChatFormat.GRAY + "" + EnumChatFormat.ITALIC + commandListener.a("commands.message.display.outgoing", c.getName(), a));
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        return CommandAbstract.a(array, MinecraftServer.getServer().getPlayers());
    }
    
    public boolean a(final String[] array, final int n) {
        return n == 0;
    }
}
