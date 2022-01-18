// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.server;

import java.net.InetAddress;
import org.bukkit.event.HandlerList;

public class ServerListPingEvent extends ServerEvent
{
    private static final HandlerList handlers;
    private final InetAddress address;
    private String motd;
    private final int numPlayers;
    private int maxPlayers;
    
    public ServerListPingEvent(final InetAddress address, final String motd, final int numPlayers, final int maxPlayers) {
        this.address = address;
        this.motd = motd;
        this.numPlayers = numPlayers;
        this.maxPlayers = maxPlayers;
    }
    
    public InetAddress getAddress() {
        return this.address;
    }
    
    public String getMotd() {
        return this.motd;
    }
    
    public void setMotd(final String motd) {
        this.motd = motd;
    }
    
    public int getNumPlayers() {
        return this.numPlayers;
    }
    
    public int getMaxPlayers() {
        return this.maxPlayers;
    }
    
    public void setMaxPlayers(final int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
    
    public HandlerList getHandlers() {
        return ServerListPingEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ServerListPingEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
