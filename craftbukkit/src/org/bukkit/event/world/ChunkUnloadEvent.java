// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.world;

import org.bukkit.Chunk;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class ChunkUnloadEvent extends ChunkEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    
    public ChunkUnloadEvent(final Chunk chunk) {
        super(chunk);
        this.cancel = false;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public HandlerList getHandlers() {
        return ChunkUnloadEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ChunkUnloadEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
