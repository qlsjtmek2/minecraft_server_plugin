// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.event.Event;

public abstract class BlockEvent extends Event
{
    protected Block block;
    
    public BlockEvent(final Block theBlock) {
        this.block = theBlock;
    }
    
    public final Block getBlock() {
        return this.block;
    }
}
