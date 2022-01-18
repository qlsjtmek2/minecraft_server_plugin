// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerQuitEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private String quitMessage;
    
    public PlayerQuitEvent(final Player who, final String quitMessage) {
        super(who);
        this.quitMessage = quitMessage;
    }
    
    public String getQuitMessage() {
        return this.quitMessage;
    }
    
    public void setQuitMessage(final String quitMessage) {
        this.quitMessage = quitMessage;
    }
    
    public HandlerList getHandlers() {
        return PlayerQuitEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerQuitEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
