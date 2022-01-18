// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class BlockBurnEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    
    public BlockBurnEvent(final Block block) {
        super(block);
        this.cancelled = false;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public HandlerList getHandlers() {
        return BlockBurnEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockBurnEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
