// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.Map;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class CommandHelp extends CommandAbstract
{
    public String c() {
        return "help";
    }
    
    public int a() {
        return 0;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.help.usage", new Object[0]);
    }
    
    public List b() {
        return Arrays.asList("?");
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        final List d = this.d(commandListener);
        final int n = 7;
        final int n2 = (d.size() - 1) / n;
        int n3;
        try {
            n3 = ((array.length == 0) ? 0 : (CommandAbstract.a(commandListener, array[0], 1, n2 + 1) - 1));
        }
        catch (ExceptionInvalidNumber exceptionInvalidNumber) {
            final ICommand command = this.d().get(array[0]);
            if (command != null) {
                throw new ExceptionUsage(command.a(commandListener), new Object[0]);
            }
            throw new ExceptionUnknownCommand();
        }
        final int min = Math.min((n3 + 1) * n, d.size());
        commandListener.sendMessage(EnumChatFormat.DARK_GREEN + commandListener.a("commands.help.header", n3 + 1, n2 + 1));
        for (int i = n3 * n; i < min; ++i) {
            commandListener.sendMessage(d.get(i).a(commandListener));
        }
        if (n3 == 0 && commandListener instanceof EntityHuman) {
            commandListener.sendMessage(EnumChatFormat.GREEN + commandListener.a("commands.help.footer", new Object[0]));
        }
    }
    
    protected List d(final ICommandListener commandListener) {
        final List a = MinecraftServer.getServer().getCommandHandler().a(commandListener);
        Collections.sort((List<Comparable>)a);
        return a;
    }
    
    protected Map d() {
        return MinecraftServer.getServer().getCommandHandler().a();
    }
}
