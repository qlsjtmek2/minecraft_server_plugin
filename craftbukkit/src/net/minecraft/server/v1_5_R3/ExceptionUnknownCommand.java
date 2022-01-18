// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ExceptionUnknownCommand extends CommandException
{
    public ExceptionUnknownCommand() {
        this("commands.generic.notFound", new Object[0]);
    }
    
    public ExceptionUnknownCommand(final String s, final Object... array) {
        super(s, array);
    }
}
