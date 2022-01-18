// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.block;

import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class LeavesDecayEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    
    public LeavesDecayEvent(final Block block) {
        super(block);
        this.cancel = false;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public HandlerList getHandlers() {
        return LeavesDecayEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return LeavesDecayEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
