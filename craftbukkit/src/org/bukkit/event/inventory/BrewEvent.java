// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.inventory;

import org.bukkit.block.Block;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockEvent;

public class BrewEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private BrewerInventory contents;
    private boolean cancelled;
    
    public BrewEvent(final Block brewer, final BrewerInventory contents) {
        super(brewer);
        this.contents = contents;
    }
    
    public BrewerInventory getContents() {
        return this.contents;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public HandlerList getHandlers() {
        return BrewEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BrewEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
