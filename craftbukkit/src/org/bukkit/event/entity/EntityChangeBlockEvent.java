// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntityChangeBlockEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final Block block;
    private boolean cancel;
    private final Material to;
    private final byte data;
    
    public EntityChangeBlockEvent(final LivingEntity what, final Block block, final Material to) {
        this(what, block, to, (byte)0);
    }
    
    public EntityChangeBlockEvent(final Entity what, final Block block, final Material to, final byte data) {
        super(what);
        this.block = block;
        this.cancel = false;
        this.to = to;
        this.data = data;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public Material getTo() {
        return this.to;
    }
    
    public byte getData() {
        return this.data;
    }
    
    public HandlerList getHandlers() {
        return EntityChangeBlockEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityChangeBlockEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
