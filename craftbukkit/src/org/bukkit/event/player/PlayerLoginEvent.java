// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import java.net.InetAddress;
import org.bukkit.event.HandlerList;

public class PlayerLoginEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private final InetAddress address;
    private final String hostname;
    private Result result;
    private String message;
    private final InetAddress realAddress;
    
    public PlayerLoginEvent(final Player player) {
        this(player, "", null);
    }
    
    public PlayerLoginEvent(final Player player, final String hostname) {
        this(player, hostname, null);
    }
    
    public PlayerLoginEvent(final Player player, final String hostname, final InetAddress address, final InetAddress realAddress) {
        super(player);
        this.result = Result.ALLOWED;
        this.message = "";
        this.hostname = hostname;
        this.address = address;
        this.realAddress = address;
    }
    
    public PlayerLoginEvent(final Player player, final String hostname, final InetAddress address) {
        this(player, hostname, address, address);
    }
    
    public PlayerLoginEvent(final Player player, final Result result, final String message) {
        this(player, "", null, result, message, null);
    }
    
    public PlayerLoginEvent(final Player player, final String hostname, final InetAddress address, final Result result, final String message, final InetAddress realAddress) {
        this(player, hostname, address, realAddress);
        this.result = result;
        this.message = message;
    }
    
    public InetAddress getRealAddress() {
        return this.realAddress;
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
    
    public String getHostname() {
        return this.hostname;
    }
    
    public void allow() {
        this.result = Result.ALLOWED;
        this.message = "";
    }
    
    public void disallow(final Result result, final String message) {
        this.result = result;
        this.message = message;
    }
    
    public InetAddress getAddress() {
        return this.address;
    }
    
    public HandlerList getHandlers() {
        return PlayerLoginEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerLoginEvent.handlers;
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
