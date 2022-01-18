// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.inventory;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockEvent;

public class FurnaceBurnEvent extends BlockEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final ItemStack fuel;
    private int burnTime;
    private boolean cancelled;
    private boolean burning;
    
    public FurnaceBurnEvent(final Block furnace, final ItemStack fuel, final int burnTime) {
        super(furnace);
        this.fuel = fuel;
        this.burnTime = burnTime;
        this.cancelled = false;
        this.burning = true;
    }
    
    @Deprecated
    public Block getFurnace() {
        return this.getBlock();
    }
    
    public ItemStack getFuel() {
        return this.fuel;
    }
    
    public int getBurnTime() {
        return this.burnTime;
    }
    
    public void setBurnTime(final int burnTime) {
        this.burnTime = burnTime;
    }
    
    public boolean isBurning() {
        return this.burning;
    }
    
    public void setBurning(final boolean burning) {
        this.burning = burning;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public HandlerList getHandlers() {
        return FurnaceBurnEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return FurnaceBurnEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
