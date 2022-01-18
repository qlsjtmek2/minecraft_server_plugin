// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.world;

import org.bukkit.Chunk;
import org.bukkit.event.HandlerList;

public class ChunkPopulateEvent extends ChunkEvent
{
    private static final HandlerList handlers;
    
    public ChunkPopulateEvent(final Chunk chunk) {
        super(chunk);
    }
    
    public HandlerList getHandlers() {
        return ChunkPopulateEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ChunkPopulateEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
