// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerExpChangeEvent extends PlayerEvent
{
    private static final HandlerList handlers;
    private int exp;
    
    public PlayerExpChangeEvent(final Player player, final int expAmount) {
        super(player);
        this.exp = expAmount;
    }
    
    public int getAmount() {
        return this.exp;
    }
    
    public void setAmount(final int amount) {
        this.exp = amount;
    }
    
    public HandlerList getHandlers() {
        return PlayerExpChangeEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerExpChangeEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
