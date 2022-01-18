// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerJoinEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private String joinMessage;
    
    public PlayerJoinEvent(final Player playerJoined, final String joinMessage) {
        super(playerJoined);
        this.joinMessage = joinMessage;
    }
    
    public String getJoinMessage() {
        return this.joinMessage;
    }
    
    public void setJoinMessage(final String joinMessage) {
        this.joinMessage = joinMessage;
    }
    
    public HandlerList getHandlers() {
        return PlayerJoinEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerJoinEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
