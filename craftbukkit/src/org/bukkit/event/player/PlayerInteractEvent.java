// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerInteractEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    protected ItemStack item;
    protected Action action;
    protected Block blockClicked;
    protected BlockFace blockFace;
    private Result useClickedBlock;
    private Result useItemInHand;
    
    public PlayerInteractEvent(final Player who, final Action action, final ItemStack item, final Block clickedBlock, final BlockFace clickedFace) {
        super(who);
        this.action = action;
        this.item = item;
        this.blockClicked = clickedBlock;
        this.blockFace = clickedFace;
        this.useItemInHand = Result.DEFAULT;
        this.useClickedBlock = ((clickedBlock == null) ? Result.DENY : Result.ALLOW);
    }
    
    public Action getAction() {
        return this.action;
    }
    
    public boolean isCancelled() {
        return this.useInteractedBlock() == Result.DENY;
    }
    
    public void setCancelled(final boolean cancel) {
        this.setUseInteractedBlock(cancel ? Result.DENY : ((this.useInteractedBlock() == Result.DENY) ? Result.DEFAULT : this.useInteractedBlock()));
        this.setUseItemInHand(cancel ? Result.DENY : ((this.useItemInHand() == Result.DENY) ? Result.DEFAULT : this.useItemInHand()));
    }
    
    public ItemStack getItem() {
        return this.item;
    }
    
    public Material getMaterial() {
        if (!this.hasItem()) {
            return Material.AIR;
        }
        return this.item.getType();
    }
    
    public boolean hasBlock() {
        return this.blockClicked != null;
    }
    
    public boolean hasItem() {
        return this.item != null;
    }
    
    public boolean isBlockInHand() {
        return this.hasItem() && this.item.getType().isBlock();
    }
    
    public Block getClickedBlock() {
        return this.blockClicked;
    }
    
    public BlockFace getBlockFace() {
        return this.blockFace;
    }
    
    public Result useInteractedBlock() {
        return this.useClickedBlock;
    }
    
    public void setUseInteractedBlock(final Result useInteractedBlock) {
        this.useClickedBlock = useInteractedBlock;
    }
    
    public Result useItemInHand() {
        return this.useItemInHand;
    }
    
    public void setUseItemInHand(final Result useItemInHand) {
        this.useItemInHand = useItemInHand;
    }
    
    public HandlerList getHandlers() {
        return PlayerInteractEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerInteractEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
