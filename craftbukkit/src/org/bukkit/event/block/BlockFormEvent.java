// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.block;

import org.bukkit.block.BlockState;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class BlockFormEvent extends BlockGrowEvent implements Cancellable
{
    private static final HandlerList handlers;
    
    public BlockFormEvent(final Block block, final BlockState newState) {
        super(block, newState);
    }
    
    public HandlerList getHandlers() {
        return BlockFormEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockFormEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
