// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.block;

import org.bukkit.entity.Player;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class BlockIgniteEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final IgniteCause cause;
    private final Entity ignitingEntity;
    private final Block ignitingBlock;
    private boolean cancel;
    
    public BlockIgniteEvent(final Block theBlock, final IgniteCause cause, final Player thePlayer) {
        this(theBlock, cause, (Entity)thePlayer);
    }
    
    public BlockIgniteEvent(final Block theBlock, final IgniteCause cause, final Entity ignitingEntity) {
        this(theBlock, cause, ignitingEntity, null);
    }
    
    public BlockIgniteEvent(final Block theBlock, final IgniteCause cause, final Block ignitingBlock) {
        this(theBlock, cause, null, ignitingBlock);
    }
    
    public BlockIgniteEvent(final Block theBlock, final IgniteCause cause, final Entity ignitingEntity, final Block ignitingBlock) {
        super(theBlock);
        this.cause = cause;
        this.ignitingEntity = ignitingEntity;
        this.ignitingBlock = ignitingBlock;
        this.cancel = false;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public IgniteCause getCause() {
        return this.cause;
    }
    
    public Player getPlayer() {
        if (this.ignitingEntity instanceof Player) {
            return (Player)this.ignitingEntity;
        }
        return null;
    }
    
    public Entity getIgnitingEntity() {
        return this.ignitingEntity;
    }
    
    public Block getIgnitingBlock() {
        return this.ignitingBlock;
    }
    
    public HandlerList getHandlers() {
        return BlockIgniteEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockIgniteEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
    
    public enum IgniteCause
    {
        LAVA, 
        FLINT_AND_STEEL, 
        SPREAD, 
        LIGHTNING, 
        FIREBALL, 
        ENDER_CRYSTAL, 
        EXPLOSION;
    }
}
