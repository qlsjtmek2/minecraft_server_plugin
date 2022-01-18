// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ExceptionPlayerNotFound extends CommandException
{
    public ExceptionPlayerNotFound() {
        this("commands.generic.player.notFound", new Object[0]);
    }
    
    public ExceptionPlayerNotFound(final String s, final Object... array) {
        super(s, array);
    }
}
