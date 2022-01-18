// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.Cancellable;

public abstract class BlockPistonEvent extends BlockEvent implements Cancellable
{
    private boolean cancelled;
    private final BlockFace direction;
    
    public BlockPistonEvent(final Block block, final BlockFace direction) {
        super(block);
        this.direction = direction;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }
    
    public boolean isSticky() {
        return this.block.getType() == Material.PISTON_STICKY_BASE;
    }
    
    public BlockFace getDirection() {
        return this.direction;
    }
}
