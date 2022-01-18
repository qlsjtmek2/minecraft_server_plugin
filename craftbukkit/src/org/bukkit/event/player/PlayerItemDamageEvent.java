// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerItemDamageEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final ItemStack item;
    private int damage;
    private boolean cancelled;
    
    public PlayerItemDamageEvent(final Player player, final ItemStack what, final int damage) {
        super(player);
        this.cancelled = false;
        this.item = what;
        this.damage = damage;
    }
    
    public ItemStack getItem() {
        return this.item;
    }
    
    public int getDamage() {
        return this.damage;
    }
    
    public void setDamage(final int damage) {
        this.damage = damage;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public HandlerList getHandlers() {
        return PlayerItemDamageEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerItemDamageEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
