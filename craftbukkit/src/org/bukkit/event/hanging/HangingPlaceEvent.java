// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.hanging;

import org.bukkit.entity.Hanging;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class HangingPlaceEvent extends HangingEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final Player player;
    private final Block block;
    private final BlockFace blockFace;
    
    public HangingPlaceEvent(final Hanging hanging, final Player player, final Block block, final BlockFace blockFace) {
        super(hanging);
        this.player = player;
        this.block = block;
        this.blockFace = blockFace;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public Block getBlock() {
        return this.block;
    }
    
    public BlockFace getBlockFace() {
        return this.blockFace;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public HandlerList getHandlers() {
        return HangingPlaceEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return HangingPlaceEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
