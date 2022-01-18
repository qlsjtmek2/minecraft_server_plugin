// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerTeleportEvent extends PlayerMoveEvent
{
    private static final HandlerList handlers;
    private TeleportCause cause;
    
    public PlayerTeleportEvent(final Player player, final Location from, final Location to) {
        super(player, from, to);
        this.cause = TeleportCause.UNKNOWN;
    }
    
    public PlayerTeleportEvent(final Player player, final Location from, final Location to, final TeleportCause cause) {
        this(player, from, to);
        this.cause = cause;
    }
    
    public TeleportCause getCause() {
        return this.cause;
    }
    
    public HandlerList getHandlers() {
        return PlayerTeleportEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerTeleportEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
    
    public enum TeleportCause
    {
        ENDER_PEARL, 
        COMMAND, 
        PLUGIN, 
        NETHER_PORTAL, 
        END_PORTAL, 
        UNKNOWN;
    }
}
