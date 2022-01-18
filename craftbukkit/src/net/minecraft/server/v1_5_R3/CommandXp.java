// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandXp extends CommandAbstract
{
    public String c() {
        return "xp";
    }
    
    public int a() {
        return 2;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.xp.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length > 0) {
            String substring = array[0];
            final boolean b = substring.endsWith("l") || substring.endsWith("L");
            if (b && substring.length() > 1) {
                substring = substring.substring(0, substring.length() - 1);
            }
            int a = CommandAbstract.a(commandListener, substring);
            final boolean b2 = a < 0;
            if (b2) {
                a *= -1;
            }
            EntityPlayer entityPlayer;
            if (array.length > 1) {
                entityPlayer = CommandAbstract.c(commandListener, array[1]);
            }
            else {
                entityPlayer = CommandAbstract.c(commandListener);
            }
            if (b) {
                if (b2) {
                    entityPlayer.levelDown(-a);
                    CommandAbstract.a(commandListener, "commands.xp.success.negative.levels", a, entityPlayer.getLocalizedName());
                }
                else {
                    entityPlayer.levelDown(a);
                    CommandAbstract.a(commandListener, "commands.xp.success.levels", a, entityPlayer.getLocalizedName());
                }
            }
            else {
                if (b2) {
                    throw new ExceptionUsage("commands.xp.failure.widthdrawXp", new Object[0]);
                }
                entityPlayer.giveExp(a);
                CommandAbstract.a(commandListener, "commands.xp.success", a, entityPlayer.getLocalizedName());
            }
            return;
        }
        throw new ExceptionUsage("commands.xp.usage", new Object[0]);
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length == 2) {
            return CommandAbstract.a(array, this.d());
        }
        return null;
    }
    
    protected String[] d() {
        return MinecraftServer.getServer().getPlayers();
    }
    
    public boolean a(final String[] array, final int n) {
        return n == 1;
    }
}
