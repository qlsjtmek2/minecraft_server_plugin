// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import java.net.InetAddress;
import org.bukkit.event.HandlerList;
import org.bukkit.Warning;
import org.bukkit.event.Event;

@Deprecated
@Warning(reason = "This event causes a login thread to synchronize with the main thread")
public class PlayerPreLoginEvent extends Event
{
    private static final HandlerList handlers;
    private Result result;
    private String message;
    private final String name;
    private final InetAddress ipAddress;
    
    public PlayerPreLoginEvent(final String name, final InetAddress ipAddress) {
        this.result = Result.ALLOWED;
        this.message = "";
        this.name = name;
        this.ipAddress = ipAddress;
    }
    
    public Result getResult() {
        return this.result;
    }
    
    public void setResult(final Result result) {
        this.result = result;
    }
    
    public String getKickMessage() {
        return this.message;
    }
    
    public void setKickMessage(final String message) {
        this.message = message;
    }
    
    public void allow() {
        this.result = Result.ALLOWED;
        this.message = "";
    }
    
    public void disallow(final Result result, final String message) {
        this.result = result;
        this.message = message;
    }
    
    public String getName() {
        return this.name;
    }
    
    public InetAddress getAddress() {
        return this.ipAddress;
    }
    
    public HandlerList getHandlers() {
        return PlayerPreLoginEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerPreLoginEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
    
    public enum Result
    {
        ALLOWED, 
        KICK_FULL, 
        KICK_BANNED, 
        KICK_WHITELIST, 
        KICK_OTHER;
    }
}
