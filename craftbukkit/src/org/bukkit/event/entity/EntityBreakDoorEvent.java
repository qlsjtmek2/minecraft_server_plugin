// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.event.entity;

import org.bukkit.entity.Entity;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;

public class EntityBreakDoorEvent extends EntityChangeBlockEvent
{
    public EntityBreakDoorEvent(final LivingEntity entity, final Block targetBlock) {
        super(entity, targetBlock, Material.AIR, (byte)0);
    }
    
    public LivingEntity getEntity() {
        return (LivingEntity)this.entity;
    }
}
