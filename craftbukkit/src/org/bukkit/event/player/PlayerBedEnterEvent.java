// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerBedEnterEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private final Block bed;
    
    public PlayerBedEnterEvent(final Player who, final Block bed) {
        super(who);
        this.cancel = false;
        this.bed = bed;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public Block getBed() {
        return this.bed;
    }
    
    public HandlerList getHandlers() {
        return PlayerBedEnterEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerBedEnterEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
