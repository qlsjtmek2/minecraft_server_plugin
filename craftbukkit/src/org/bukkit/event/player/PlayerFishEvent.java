// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.player;

import org.bukkit.entity.Player;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PlayerFishEvent extends PlayerEvent implements Cancellable
{
    private static final HandlerList handlers;
    private final Entity entity;
    private boolean cancel;
    private int exp;
    private final State state;
    private final Fish hookEntity;
    
    public PlayerFishEvent(final Player player, final Entity entity, final State state) {
        this(player, entity, null, state);
    }
    
    public PlayerFishEvent(final Player player, final Entity entity, final Fish hookEntity, final State state) {
        super(player);
        this.cancel = false;
        this.entity = entity;
        this.hookEntity = hookEntity;
        this.state = state;
    }
    
    public Entity getCaught() {
        return this.entity;
    }
    
    public Fish getHook() {
        return this.hookEntity;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public int getExpToDrop() {
        return this.exp;
    }
    
    public void setExpToDrop(final int amount) {
        this.exp = amount;
    }
    
    public State getState() {
        return this.state;
    }
    
    public HandlerList getHandlers() {
        return PlayerFishEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PlayerFishEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
    
    public enum State
    {
        FISHING, 
        CAUGHT_FISH, 
        CAUGHT_ENTITY, 
        IN_GROUND, 
        FAILED_ATTEMPT;
    }
}
