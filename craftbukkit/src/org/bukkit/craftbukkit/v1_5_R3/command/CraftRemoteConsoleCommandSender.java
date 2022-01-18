// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.command;

import net.minecraft.server.v1_5_R3.RemoteControlCommandListener;
import org.bukkit.command.RemoteConsoleCommandSender;

public class CraftRemoteConsoleCommandSender extends ServerCommandSender implements RemoteConsoleCommandSender
{
    public void sendMessage(final String message) {
        RemoteControlCommandListener.instance.sendMessage(message + "\n");
    }
    
    public void sendMessage(final String[] messages) {
        for (final String message : messages) {
            this.sendMessage(message);
        }
    }
    
    public String getName() {
        return "Rcon";
    }
    
    public boolean isOp() {
        return true;
    }
    
    public void setOp(final boolean value) {
        throw new UnsupportedOperationException("Cannot change operator status of remote controller.");
    }
}
