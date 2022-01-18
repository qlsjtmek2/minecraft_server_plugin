// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.server.v1_5_R3;

public class ServerCommand
{
    public final String command;
    public final ICommandListener source;
    
    public ServerCommand(final String command, final ICommandListener source) {
        this.command = command;
        this.source = source;
    }
}
