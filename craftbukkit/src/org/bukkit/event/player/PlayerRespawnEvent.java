// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;

public class PlayerRespawnEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private Location respawnLocation;
    private final boolean isBedSpawn;
    
    public PlayerRespawnEvent(final Player respawnPlayer, final Location respawnLocation, final boolean isBedSpawn) {
        super(respawnPlayer);
        this.respawnLocation = respawnLocation;
        this.isBedSpawn = isBedSpawn;
    }
    
    public Location getRespawnLocation() {
        return this.respawnLocation;
    }
    
    public void setRespawnLocation(final Location respawnLocation) {
        Validate.notNull(respawnLocation, "Respawn location can not be null");
        Validate.notNull(respawnLocation.getWorld(), "Respawn world can not be null");
        this.respawnLocation = respawnLocation;
    }
    
    public boolean isBedSpawn() {
        return this.isBedSpawn;
    }
    
    public HandlerList getHandlers() {
        return PlayerRespawnEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerRespawnEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
