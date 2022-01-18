// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityMonster;
import net.minecraft.server.v1_5_R3.EntitySkeleton;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Skeleton;

public class CraftSkeleton extends CraftMonster implements Skeleton
{
    public CraftSkeleton(final CraftServer server, final EntitySkeleton entity) {
        super(server, entity);
    }
    
    public EntitySkeleton getHandle() {
        return (EntitySkeleton)this.entity;
    }
    
    public String toString() {
        return "CraftSkeleton";
    }
    
    public EntityType getType() {
        return EntityType.SKELETON;
    }
    
    public SkeletonType getSkeletonType() {
        return SkeletonType.getType(this.getHandle().getSkeletonType());
    }
    
    public void setSkeletonType(final SkeletonType type) {
        Validate.notNull(type);
        this.getHandle().setSkeletonType(type.getId());
    }
}
