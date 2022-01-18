// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.painting;

import org.bukkit.entity.Painting;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.Warning;
import org.bukkit.event.Cancellable;

@Deprecated
@Warning(reason = "This event has been replaced by HangingPlaceEvent")
public class PaintingPlaceEvent extends PaintingEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final Player player;
    private final Block block;
    private final BlockFace blockFace;
    
    public PaintingPlaceEvent(final Painting painting, final Player player, final Block block, final BlockFace blockFace) {
        super(painting);
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
        return PaintingPlaceEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PaintingPlaceEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
