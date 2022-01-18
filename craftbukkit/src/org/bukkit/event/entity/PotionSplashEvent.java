// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.apache.commons.lang.Validate;
import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.entity.LivingEntity;
import java.util.Map;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Cancellable;

public class PotionSplashEvent extends ProjectileHitEvent implements Cancellable
{
    private static final HandlerList handlers;
    private boolean cancelled;
    private final Map<LivingEntity, Double> affectedEntities;
    
    public PotionSplashEvent(final ThrownPotion potion, final Map<LivingEntity, Double> affectedEntities) {
        super(potion);
        this.affectedEntities = affectedEntities;
    }
    
    public ThrownPotion getEntity() {
        return (ThrownPotion)this.entity;
    }
    
    public ThrownPotion getPotion() {
        return this.getEntity();
    }
    
    public Collection<LivingEntity> getAffectedEntities() {
        return new ArrayList<LivingEntity>(this.affectedEntities.keySet());
    }
    
    public double getIntensity(final LivingEntity entity) {
        final Double intensity = this.affectedEntities.get(entity);
        return (intensity != null) ? intensity : 0.0;
    }
    
    public void setIntensity(final LivingEntity entity, final double intensity) {
        Validate.notNull(entity, "You must specify a valid entity.");
        if (intensity <= 0.0) {
            this.affectedEntities.remove(entity);
        }
        else {
            this.affectedEntities.put(entity, Math.min(intensity, 1.0));
        }
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public HandlerList getHandlers() {
        return PotionSplashEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return PotionSplashEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
