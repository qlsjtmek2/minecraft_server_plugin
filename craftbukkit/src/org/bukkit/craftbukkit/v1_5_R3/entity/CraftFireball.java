// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.util.Vector;
import net.minecraft.server.v1_5_R3.EntityLiving;
import org.bukkit.entity.LivingEntity;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityFireball;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Fireball;

public class CraftFireball extends AbstractProjectile implements Fireball
{
    public CraftFireball(final CraftServer server, final EntityFireball entity) {
        super(server, entity);
    }
    
    public float getYield() {
        return this.getHandle().yield;
    }
    
    public boolean isIncendiary() {
        return this.getHandle().isIncendiary;
    }
    
    public void setIsIncendiary(final boolean isIncendiary) {
        this.getHandle().isIncendiary = isIncendiary;
    }
    
    public void setYield(final float yield) {
        this.getHandle().yield = yield;
    }
    
    public LivingEntity getShooter() {
        if (this.getHandle().shooter != null) {
            return (LivingEntity)this.getHandle().shooter.getBukkitEntity();
        }
        return null;
    }
    
    public void setShooter(final LivingEntity shooter) {
        if (shooter instanceof CraftLivingEntity) {
            this.getHandle().shooter = (EntityLiving)((CraftLivingEntity)shooter).entity;
        }
    }
    
    public Vector getDirection() {
        return new Vector(this.getHandle().dirX, this.getHandle().dirY, this.getHandle().dirZ);
    }
    
    public void setDirection(final Vector direction) {
        this.getHandle().setDirection(direction.getX(), direction.getY(), direction.getZ());
    }
    
    public EntityFireball getHandle() {
        return (EntityFireball)this.entity;
    }
    
    public String toString() {
        return "CraftFireball";
    }
    
    public EntityType getType() {
        return EntityType.UNKNOWN;
    }
}
