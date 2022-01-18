// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;

public class EntityDamageByEntityEvent extends EntityDamageEvent
{
    private final Entity damager;
    
    public EntityDamageByEntityEvent(final Entity damager, final Entity damagee, final DamageCause cause, final int damage) {
        super(damagee, cause, damage);
        this.damager = damager;
    }
    
    public Entity getDamager() {
        return this.damager;
    }
}
