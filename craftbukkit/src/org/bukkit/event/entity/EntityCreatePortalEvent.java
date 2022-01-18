// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.PortalType;
import org.bukkit.block.BlockState;
import java.util.List;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntityCreatePortalEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final List<BlockState> blocks;
    private boolean cancelled;
    private PortalType type;
    
    public EntityCreatePortalEvent(final LivingEntity what, final List<BlockState> blocks, final PortalType type) {
        super(what);
        this.cancelled = false;
        this.type = PortalType.CUSTOM;
        this.blocks = blocks;
        this.type = type;
    }
    
    public LivingEntity getEntity() {
        return (LivingEntity)this.entity;
    }
    
    public List<BlockState> getBlocks() {
        return this.blocks;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public PortalType getPortalType() {
        return this.type;
    }
    
    public HandlerList getHandlers() {
        return EntityCreatePortalEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityCreatePortalEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
