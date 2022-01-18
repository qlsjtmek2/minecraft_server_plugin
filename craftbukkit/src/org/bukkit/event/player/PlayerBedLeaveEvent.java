// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;

public class PlayerBedLeaveEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private final Block bed;
    
    public PlayerBedLeaveEvent(final Player who, final Block bed) {
        super(who);
        this.bed = bed;
    }
    
    public Block getBed() {
        return this.bed;
    }
    
    public HandlerList getHandlers() {
        return PlayerBedLeaveEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerBedLeaveEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
