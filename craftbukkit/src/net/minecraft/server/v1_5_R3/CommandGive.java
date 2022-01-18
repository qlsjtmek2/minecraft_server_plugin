// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

import java.util.List;

public class CommandGive extends CommandAbstract
{
    public String c() {
        return "give";
    }
    
    public int a() {
        return 2;
    }
    
    public String a(final ICommandListener commandListener) {
        return commandListener.a("commands.give.usage", new Object[0]);
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length < 2) {
            throw new ExceptionUsage("commands.give.usage", new Object[0]);
        }
        final EntityPlayer c = CommandAbstract.c(commandListener, array[0]);
        final int a = CommandAbstract.a(commandListener, array[1], 1);
        int a2 = 1;
        int a3 = 0;
        if (Item.byId[a] == null) {
            throw new ExceptionInvalidNumber("commands.give.notFound", new Object[] { a });
        }
        if (array.length >= 3) {
            a2 = CommandAbstract.a(commandListener, array[2], 1, 64);
        }
        if (array.length >= 4) {
            a3 = CommandAbstract.a(commandListener, array[3]);
        }
        final ItemStack itemstack = new ItemStack(a, a2, a3);
        c.drop(itemstack).pickupDelay = 0;
        CommandAbstract.a(commandListener, "commands.give.success", Item.byId[a].k(itemstack), a, a2, c.getLocalizedName());
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
