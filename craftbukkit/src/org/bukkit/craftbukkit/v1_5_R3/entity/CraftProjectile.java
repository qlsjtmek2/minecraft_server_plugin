// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.EntityProjectile;
import net.minecraft.server.v1_5_R3.EntityLiving;
import org.bukkit.entity.LivingEntity;
import net.minecraft.server.v1_5_R3.Entity;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Projectile;

public abstract class CraftProjectile extends AbstractProjectile implements Projectile
{
    public CraftProjectile(final CraftServer server, final net.minecraft.server.v1_5_R3.Entity entity) {
        super(server, entity);
    }
    
    public LivingEntity getShooter() {
        if (this.getHandle().getShooter() != null) {
            return (LivingEntity)this.getHandle().getShooter().getBukkitEntity();
        }
        return null;
    }
    
    public void setShooter(final LivingEntity shooter) {
        if (shooter instanceof CraftLivingEntity) {
            this.getHandle().shooter = (EntityLiving)((CraftLivingEntity)shooter).entity;
            if (shooter instanceof CraftHumanEntity) {
                this.getHandle().shooterName = ((CraftHumanEntity)shooter).getName();
            }
        }
    }
    
    public EntityProjectile getHandle() {
        return (EntityProjectile)this.entity;
    }
    
    public String toString() {
        return "CraftProjectile";
    }
}
