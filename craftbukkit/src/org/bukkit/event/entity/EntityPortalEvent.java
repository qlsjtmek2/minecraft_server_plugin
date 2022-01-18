// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.TravelAgent;
import org.bukkit.event.HandlerList;

public class EntityPortalEvent extends EntityTeleportEvent
{
    private static final HandlerList handlers;
    protected boolean useTravelAgent;
    protected TravelAgent travelAgent;
    
    public EntityPortalEvent(final Entity entity, final Location from, final Location to, final TravelAgent pta) {
        super(entity, from, to);
        this.useTravelAgent = true;
        this.travelAgent = pta;
    }
    
    public void useTravelAgent(final boolean useTravelAgent) {
        this.useTravelAgent = useTravelAgent;
    }
    
    public boolean useTravelAgent() {
        return this.useTravelAgent;
    }
    
    public TravelAgent getPortalTravelAgent() {
        return this.travelAgent;
    }
    
    public void setPortalTravelAgent(final TravelAgent travelAgent) {
        this.travelAgent = travelAgent;
    }
    
    public HandlerList getHandlers() {
        return EntityPortalEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityPortalEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
