// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class CommandTestFor extends CommandAbstract
{
    public String c() {
        return "testfor";
    }
    
    public int a() {
        return 2;
    }
    
    public void b(final ICommandListener commandListener, final String[] array) {
        if (array.length != 1) {
            throw new ExceptionUsage("commands.testfor.usage", new Object[0]);
        }
        if (!(commandListener instanceof TileEntityCommand)) {
            throw new CommandException("commands.testfor.failed", new Object[0]);
        }
        CommandAbstract.c(commandListener, array[0]);
    }
    
    public boolean a(final String[] array, final int n) {
        return n == 0;
    }
}
