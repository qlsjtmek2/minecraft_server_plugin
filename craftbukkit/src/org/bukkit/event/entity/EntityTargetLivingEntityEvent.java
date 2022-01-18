// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Entity;

public class EntityTargetLivingEntityEvent extends EntityTargetEvent
{
    public EntityTargetLivingEntityEvent(final Entity entity, final LivingEntity target, final TargetReason reason) {
        super(entity, target, reason);
    }
    
    public LivingEntity getTarget() {
        return (LivingEntity)super.getTarget();
    }
    
    public void setTarget(final Entity target) {
        if (target == null || target instanceof LivingEntity) {
            super.setTarget(target);
        }
    }
}
