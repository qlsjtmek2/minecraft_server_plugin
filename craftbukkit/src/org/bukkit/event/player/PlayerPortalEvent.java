// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.TravelAgent;
import org.bukkit.event.HandlerList;

public class PlayerPortalEvent extends PlayerTeleportEvent
{
    private static final HandlerList handlers;
    protected boolean useTravelAgent;
    protected TravelAgent travelAgent;
    
    public PlayerPortalEvent(final Player player, final Location from, final Location to, final TravelAgent pta) {
        super(player, from, to);
        this.useTravelAgent = true;
        this.travelAgent = pta;
    }
    
    public PlayerPortalEvent(final Player player, final Location from, final Location to, final TravelAgent pta, final TeleportCause cause) {
        super(player, from, to, cause);
        this.useTravelAgent = true;
        this.travelAgent = pta;
    }
    
    public void useTravelAgent(final boolean useTravelAgent) {
        this.useTravelAgent = useTravelAgent;
    }
    
    public boolean useTravelAgent() {
        return this.useTravelAgent && this.travelAgent != null;
    }
    
    public TravelAgent getPortalTravelAgent() {
        return this.travelAgent;
    }
    
    public void setPortalTravelAgent(final TravelAgent travelAgent) {
        this.travelAgent = travelAgent;
    }
    
    public HandlerList getHandlers() {
        return PlayerPortalEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerPortalEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
