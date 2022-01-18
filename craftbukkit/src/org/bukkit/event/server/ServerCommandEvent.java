// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.server;

import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;

public class ServerCommandEvent extends ServerEvent
{
    private static final HandlerList handlers;
    private String command;
    private final CommandSender sender;
    
    public ServerCommandEvent(final CommandSender sender, final String command) {
        this.command = command;
        this.sender = sender;
    }
    
    public String getCommand() {
        return this.command;
    }
    
    public void setCommand(final String message) {
        this.command = message;
    }
    
    public CommandSender getSender() {
        return this.sender;
    }
    
    public HandlerList getHandlers() {
        return ServerCommandEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ServerCommandEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
