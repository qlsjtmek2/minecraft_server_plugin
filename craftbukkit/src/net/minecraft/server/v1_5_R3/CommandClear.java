// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandClear extends CommandAbstract
{
    public String c() {
        return "clear";
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.clear.usage", new Object[0]);
    }
    
    public int a() {
        return 2;
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        final EntityPlayer entityPlayer = (array.length == 0) ? CommandAbstract.c(commandListener) : CommandAbstract.c(commandListener, array[0]);
        final int b = entityPlayer.inventory.b((array.length >= 2) ? CommandAbstract.a(commandListener, array[1], 1) : -1, (array.length >= 3) ? CommandAbstract.a(commandListener, array[2], 0) : -1);
        entityPlayer.defaultContainer.b();
        if (b == 0) {
            throw new CommandException("commands.clear.failure", new Object[] { entityPlayer.getLocalizedName() });
        }
        CommandAbstract.a(commandListener, "commands.clear.success", entityPlayer.getLocalizedName(), b);
    }
    
    public List a(final ICommandListener commandListener, final String[] array) {
        if (array.length == 1) {
            return CommandAbstract.a(array, this.d());
        }
        return null;
    }
    
    protected String[] d() {
        return MinecraftServer.getServer().getPlayers();
    }
    
    public boolean a(final String[] array, final int n) {
        return n == 0;
    }
}
