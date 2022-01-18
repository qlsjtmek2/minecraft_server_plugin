// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.block;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.block.BlockState;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class BlockPlaceEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    protected boolean cancel;
    protected boolean canBuild;
    protected Block placedAgainst;
    protected BlockState replacedBlockState;
    protected ItemStack itemInHand;
    protected Player player;
    
    public BlockPlaceEvent(final Block placedBlock, final BlockState replacedBlockState, final Block placedAgainst, final ItemStack itemInHand, final Player thePlayer, final boolean canBuild) {
        super(placedBlock);
        this.placedAgainst = placedAgainst;
        this.itemInHand = itemInHand;
        this.player = thePlayer;
        this.replacedBlockState = replacedBlockState;
        this.canBuild = canBuild;
        this.cancel = false;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public Block getBlockPlaced() {
        return this.getBlock();
    }
    
    public BlockState getBlockReplacedState() {
        return this.replacedBlockState;
    }
    
    public Block getBlockAgainst() {
        return this.placedAgainst;
    }
    
    public ItemStack getItemInHand() {
        return this.itemInHand;
    }
    
    public boolean canBuild() {
        return this.canBuild;
    }
    
    public void setBuild(final boolean canBuild) {
        this.canBuild = canBuild;
    }
    
    public HandlerList getHandlers() {
        return BlockPlaceEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return BlockPlaceEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
