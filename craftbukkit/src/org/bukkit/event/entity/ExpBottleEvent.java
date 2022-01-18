// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.HandlerList;

public class ExpBottleEvent extends ProjectileHitEvent
{
    private static final HandlerList handlers;
    private int exp;
    private boolean showEffect;
    
    public ExpBottleEvent(final ThrownExpBottle bottle, final int exp) {
        super(bottle);
        this.showEffect = true;
        this.exp = exp;
    }
    
    public ThrownExpBottle getEntity() {
        return (ThrownExpBottle)this.entity;
    }
    
    public boolean getShowEffect() {
        return this.showEffect;
    }
    
    public void setShowEffect(final boolean showEffect) {
        this.showEffect = showEffect;
    }
    
    public int getExperience() {
        return this.exp;
    }
    
    public void setExperience(final int exp) {
        this.exp = exp;
    }
    
    public HandlerList getHandlers() {
        return ExpBottleEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return ExpBottleEvent.handlers;
    }
    
    static {
        handlers = new HandlerList();
    }
}
