// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class EntityTargetEvent extends EntityEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancel;
    private Entity target;
    private final TargetReason reason;
    
    public EntityTargetEvent(final Entity entity, final Entity target, final TargetReason reason) {
        super(entity);
        this.cancel = false;
        this.target = target;
        this.reason = reason;
    }
    
    public boolean isCancelled() {
        return this.cancel;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public TargetReason getReason() {
        return this.reason;
    }
    
    public Entity getTarget() {
        return this.target;
    }
    
    public void setTarget(final Entity target) {
        this.target = target;
    }
    
    public HandlerList getHandlers() {
        return EntityTargetEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return EntityTargetEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
    
    public enum TargetReason
    {
        TARGET_DIED, 
        CLOSEST_PLAYER, 
        TARGET_ATTACKED_ENTITY, 
        PIG_ZOMBIE_TARGET, 
        FORGOT_TARGET, 
        TARGET_ATTACKED_OWNER, 
        OWNER_ATTACKED_TARGET, 
        RANDOM_TARGET, 
        DEFEND_VILLAGE, 
        CUSTOM;
    }
}
