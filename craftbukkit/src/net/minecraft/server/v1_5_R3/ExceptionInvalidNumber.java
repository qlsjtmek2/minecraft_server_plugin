// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ExceptionInvalidNumber extends CommandException
{
    public ExceptionInvalidNumber() {
        this("commands.generic.num.invalid", new Object[0]);
    }
    
    public ExceptionInvalidNumber(final String s, final Object... array) {
        super(s, array);
    }
}
