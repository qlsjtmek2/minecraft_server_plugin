// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.server;

import org.bukkit.map.MapView;
import org.bukkit.event.HandlerList;

public class MapInitializeEvent extends ServerEvent
{
    private static final HandlerList handlers;
    private final MapView mapView;
    
    public MapInitializeEvent(final MapView mapView) {
        this.mapView = mapView;
    }
    
    public MapView getMap() {
        return this.mapView;
    }
    
    public HandlerList getHandlers() {
        return MapInitializeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return MapInitializeEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
