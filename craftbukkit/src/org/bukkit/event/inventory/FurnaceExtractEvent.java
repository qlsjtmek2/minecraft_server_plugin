// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.inventory;

import org.bukkit.block.Block;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockExpEvent;

public class FurnaceExtractEvent extends BlockExpEvent
{
    private final Player player;
    private final Material itemType;
    private final int itemAmount;
    
    public FurnaceExtractEvent(final Player player, final Block block, final Material itemType, final int itemAmount, final int exp) {
        super(block, exp);
        this.player = player;
        this.itemType = itemType;
        this.itemAmount = itemAmount;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public Material getItemType() {
        return this.itemType;
    }
    
    public int getItemAmount() {
        return this.itemAmount;
    }
}
