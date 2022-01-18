// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandGamerule extends CommandAbstract
{
    public String c() {
        return "gamerule";
    }
    
    public int a() {
        return 2;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.gamerule.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length == 2) {
            final String s = array[0];
            final String s2 = array[1];
            final GameRules d = this.d();
            if (d.e(s)) {
                d.set(s, s2);
                CommandAbstract.a(commandListener, "commands.gamerule.success", new Object[0]);
            }
            else {
                CommandAbstract.a(commandListener, "commands.gamerule.norule", s);
            }
            return;
        }
        if (array.length == 1) {
            final String s3 = array[0];
            final GameRules d2 = this.d();
            if (d2.e(s3)) {
                commandListener.sendMessage(s3 + " = " + d2.get(s3));
            }
            else {
                CommandAbstract.a(commandListener, "commands.gamerule.norule", s3);
            }
            return;
        }
        if (array.length == 0) {
            commandListener.sendMessage(CommandAbstract.a((Object[])this.d().b()));
            return;
        }
        throw new ExceptionUsage("commands.gamerule.usage", new Object[0]);
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1) {
            return CommandAbstract.a(array, this.d().b());
        }
        if (array.length == 2) {
            return CommandAbstract.a(array, "true", "false");
        }
        return null;
    }
    
    private GameRules d() {
        return MinecraftServer.getServer().getWorldServer(0).getGameRules();
    }
}
