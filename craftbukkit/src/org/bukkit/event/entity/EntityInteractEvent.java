// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntityInteractEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    protected Block block;
    private boolean cancelled;
    
    public EntityInteractEvent(final Entity entity, final Block block) {
        super(entity);
        this.block = block;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public HandlerList getHandlers() {
        return EntityInteractEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityInteractEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
