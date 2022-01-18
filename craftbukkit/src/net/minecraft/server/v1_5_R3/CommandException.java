// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class CommandException extends RuntimeException
{
    private Object[] a;
    
    public CommandException(final String s, final Object... a) {
        super(s);
        this.a = a;
    }
    
    public Object[] a() {
        return this.a;
    }
}
