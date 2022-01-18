// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.block;

import org.bukkit.block.BlockFace;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class BlockFromToEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    protected Block to;
    protected BlockFace face;
    protected boolean cancel;
    
    public BlockFromToEvent(final Block block, final BlockFace face) {
        super(block);
        this.face = face;
        this.cancel = false;
    }
    
    public BlockFromToEvent(final Block block, final Block toBlock) {
        super(block);
        this.to = toBlock;
        this.face = BlockFace.SELF;
        this.cancel = false;
    }
    
    public BlockFace getFace() {
        return this.face;
    }
    
    public Block getToBlock() {
        if (this.to == null) {
            this.to = this.block.getRelative(this.face);
        }
        return this.to;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public HandlerList getHandlers() {
        return BlockFromToEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockFromToEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
