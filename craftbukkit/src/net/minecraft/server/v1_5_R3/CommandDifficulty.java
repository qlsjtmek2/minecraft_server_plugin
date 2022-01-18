// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandDifficulty extends CommandAbstract
{
    private static final String[] a;
    
    public String c() {
        return "difficulty";
    }
    
    public int a() {
        return 2;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.difficulty.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length > 0) {
            final int e = this.e(commandListener, array[0]);
            MinecraftServer.getServer().c(e);
            CommandAbstract.a(commandListener, "commands.difficulty.success", LocaleI18n.get(CommandDifficulty.a[e]));
            return;
        }
        throw new ExceptionUsage("commands.difficulty.usage", new Object[0]);
    }
    
    protected int e(final ICommandListener commandListener, final String s) {
        if (s.equalsIgnoreCase("peaceful") || s.equalsIgnoreCase("p")) {
            return 0;
        }
        if (s.equalsIgnoreCase("easy") || s.equalsIgnoreCase("e")) {
            return 1;
        }
        if (s.equalsIgnoreCase("normal") || s.equalsIgnoreCase("n")) {
            return 2;
        }
        if (s.equalsIgnoreCase("hard") || s.equalsIgnoreCase("h")) {
            return 3;
        }
        return CommandAbstract.a(commandListener, s, 0, 3);
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1) {
            return CommandAbstract.a(array, "peaceful", "easy", "normal", "hard");
        }
        return null;
    }
    
    static {
        a = new String[] { "options.difficulty.peaceful", "options.difficulty.easy", "options.difficulty.normal", "options.difficulty.hard" };
    }
}
