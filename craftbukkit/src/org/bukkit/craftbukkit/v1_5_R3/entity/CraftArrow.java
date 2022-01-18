// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityArrow;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Arrow;

public class CraftArrow extends AbstractProjectile implements Arrow
{
    private final Spigot spigot;
    
    public CraftArrow(final CraftServer server, final EntityArrow entity) {
        super(server, entity);
        this.spigot = new Spigot() {
            public double getDamage() {
                return CraftArrow.this.getHandle().c();
            }
            
            public void setDamage(final double damage) {
                CraftArrow.this.getHandle().b(damage);
            }
        };
    }
    
    public LivingEntity getShooter() {
        if (this.getHandle().shooter != null) {
            return (LivingEntity)this.getHandle().shooter.getBukkitEntity();
        }
        return null;
    }
    
    public void setShooter(final LivingEntity shooter) {
        if (shooter instanceof CraftLivingEntity) {
            this.getHandle().shooter = ((CraftLivingEntity)shooter).getHandle();
        }
    }
    
    public EntityArrow getHandle() {
        return (EntityArrow)this.entity;
    }
    
    public String toString() {
        return "CraftArrow";
    }
    
    public EntityType getType() {
        return EntityType.ARROW;
    }
    
    public Spigot spigot() {
        return this.spigot;
    }
}
