// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class BlockFadeEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final BlockState newState;
    
    public BlockFadeEvent(final Block block, final BlockState newState) {
        super(block);
        this.newState = newState;
        this.cancelled = false;
    }
    
    public BlockState getNewState() {
        return this.newState;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public HandlerList getHandlers() {
        return BlockFadeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockFadeEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
