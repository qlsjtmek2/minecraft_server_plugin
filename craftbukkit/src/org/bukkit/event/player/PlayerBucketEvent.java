// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.Cancellable;

public abstract class PlayerBucketEvent extends PlayerEvent implements Cancellable
{
    private ItemStack itemStack;
    private boolean cancelled;
    private final Block blockClicked;
    private final BlockFace blockFace;
    private final Material bucket;
    
    public PlayerBucketEvent(final Player who, final Block blockClicked, final BlockFace blockFace, final Material bucket, final ItemStack itemInHand) {
        super(who);
        this.cancelled = false;
        this.blockClicked = blockClicked;
        this.blockFace = blockFace;
        this.itemStack = itemInHand;
        this.bucket = bucket;
    }
    
    public Material getBucket() {
        return this.bucket;
    }
    
    public ItemStack getItemStack() {
        return this.itemStack;
    }
    
    public void setItemStack(final ItemStack itemStack) {
        this.itemStack = itemStack;
    }
    
    public Block getBlockClicked() {
        return this.blockClicked;
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
}
