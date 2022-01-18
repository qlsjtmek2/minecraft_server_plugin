// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import org.bukkit.entity.LivingEntity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Creature;

public class CraftCreature extends CraftLivingEntity implements Creature
{
    public CraftCreature(final CraftServer server, final EntityCreature entity) {
        super(server, entity);
    }
    
    public void setTarget(final LivingEntity target) {
        final EntityCreature entity = this.getHandle();
        if (target == null) {
            entity.target = null;
        }
        else if (target instanceof CraftLivingEntity) {
            entity.target = ((CraftLivingEntity)target).getHandle();
            entity.pathEntity = entity.world.findPath(entity, entity.target, 16.0f, true, false, false, true);
        }
    }
    
    public CraftLivingEntity getTarget() {
        if (this.getHandle().target == null) {
            return null;
        }
        if (!(this.getHandle().target instanceof EntityLiving)) {
            return null;
        }
        return (CraftLivingEntity)this.getHandle().target.getBukkitEntity();
    }
    
    public EntityCreature getHandle() {
        return (EntityCreature)this.entity;
    }
    
    public String toString() {
        return "CraftCreature";
    }
}
