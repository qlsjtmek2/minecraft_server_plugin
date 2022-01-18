// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.server;

import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class RemoteServerCommandEvent extends ServerCommandEvent
{
    private static final HandlerList handlers;
    
    public RemoteServerCommandEvent(final CommandSender sender, final String command) {
        super(sender, command);
    }
    
    public HandlerList getHandlers() {
        return RemoteServerCommandEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return RemoteServerCommandEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
