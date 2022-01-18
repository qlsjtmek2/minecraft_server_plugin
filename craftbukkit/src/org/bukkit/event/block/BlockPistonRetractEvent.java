// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.block;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;

public class BlockPistonRetractEvent extends BlockPistonEvent
{
    private static final HandlerList handlers;
    
    public BlockPistonRetractEvent(final Block block, final BlockFace direction) {
        super(block, direction);
    }
    
    public Location getRetractLocation() {
        return this.getBlock().getRelative(this.getDirection(), 2).getLocation();
    }
    
    public HandlerList getHandlers() {
        return BlockPistonRetractEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockPistonRetractEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
