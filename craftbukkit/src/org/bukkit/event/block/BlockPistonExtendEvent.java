// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.block;

import java.util.Collections;
import java.util.ArrayList;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Block;
import java.util.List;
import org.bukkit.event.HandlerList;

public class BlockPistonExtendEvent extends BlockPistonEvent
{
    private static final HandlerList handlers;
    private final int length;
    private List<Block> blocks;
    
    public BlockPistonExtendEvent(final Block block, final int length, final BlockFace direction) {
        super(block, direction);
        this.length = length;
    }
    
    public int getLength() {
        return this.length;
    }
    
    public List<Block> getBlocks() {
        if (this.blocks == null) {
            final ArrayList<Block> tmp = new ArrayList<Block>();
            for (int i = 0; i < this.getLength(); ++i) {
                tmp.add(this.block.getRelative(this.getDirection(), i + 1));
            }
            this.blocks = Collections.unmodifiableList((List<? extends Block>)tmp);
        }
        return this.blocks;
    }
    
    public HandlerList getHandlers() {
        return BlockPistonExtendEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockPistonExtendEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
