// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.world;

import org.bukkit.block.BlockState;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.TreeType;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class StructureGrowEvent extends WorldEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final Location location;
    private final TreeType species;
    private final boolean bonemeal;
    private final Player player;
    private final List<BlockState> blocks;
    
    public StructureGrowEvent(final Location location, final TreeType species, final boolean bonemeal, final Player player, final List<BlockState> blocks) {
        super(location.getWorld());
        this.cancelled = false;
        this.location = location;
        this.species = species;
        this.bonemeal = bonemeal;
        this.player = player;
        this.blocks = blocks;
    }
    
    public Location getLocation() {
        return this.location;
    }
    
    public TreeType getSpecies() {
        return this.species;
    }
    
    public boolean isFromBonemeal() {
        return this.bonemeal;
    }
    
    public Player getPlayer() {
        return this.player;
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
    
    public HandlerList getHandlers() {
        return StructureGrowEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return StructureGrowEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
