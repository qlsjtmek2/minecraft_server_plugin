// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.block.Block;

public class EntityDamageByBlockEvent extends EntityDamageEvent
{
    private final Block damager;
    
    public EntityDamageByBlockEvent(final Block damager, final Entity damagee, final DamageCause cause, final int damage) {
        super(damagee, cause, damage);
        this.damager = damager;
    }
    
    public Block getDamager() {
        return this.damager;
    }
}
