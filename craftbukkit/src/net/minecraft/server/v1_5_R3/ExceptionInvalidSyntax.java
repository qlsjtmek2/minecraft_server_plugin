// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ExceptionInvalidSyntax extends CommandException
{
    public ExceptionInvalidSyntax() {
        this("commands.generic.snytax", new Object[0]);
    }
    
    public ExceptionInvalidSyntax(final String s, final Object... array) {
        super(s, array);
    }
}
