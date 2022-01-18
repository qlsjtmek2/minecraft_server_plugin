// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerBucketEmptyEvent extends PlayerBucketEvent
{
    private static final HandlerList handlers;
    
    public PlayerBucketEmptyEvent(final Player who, final Block blockClicked, final BlockFace blockFace, final Material bucket, final ItemStack itemInHand) {
        super(who, blockClicked, blockFace, bucket, itemInHand);
    }
    
    public HandlerList getHandlers() {
        return PlayerBucketEmptyEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerBucketEmptyEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
