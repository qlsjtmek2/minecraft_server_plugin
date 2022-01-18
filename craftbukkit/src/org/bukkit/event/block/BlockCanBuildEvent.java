// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.block;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;

public class BlockCanBuildEvent extends BlockEvent
{
    private static final HandlerList handlers;
    protected boolean buildable;
    protected int material;
    
    public BlockCanBuildEvent(final Block block, final int id, final boolean canBuild) {
        super(block);
        this.buildable = canBuild;
        this.material = id;
    }
    
    public boolean isBuildable() {
        return this.buildable;
    }
    
    public void setBuildable(final boolean cancel) {
        this.buildable = cancel;
    }
    
    public Material getMaterial() {
        return Material.getMaterial(this.material);
    }
    
    public int getMaterialId() {
        return this.material;
    }
    
    public HandlerList getHandlers() {
        return BlockCanBuildEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockCanBuildEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
