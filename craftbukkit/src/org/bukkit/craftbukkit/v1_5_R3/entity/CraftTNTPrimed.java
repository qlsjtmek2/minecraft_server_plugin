// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.EntityLiving;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityTNTPrimed;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.TNTPrimed;

public class CraftTNTPrimed extends CraftEntity implements TNTPrimed
{
    public CraftTNTPrimed(final CraftServer server, final EntityTNTPrimed entity) {
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
    
    public int getFuseTicks() {
        return this.getHandle().fuseTicks;
    }
    
    public void setFuseTicks(final int fuseTicks) {
        this.getHandle().fuseTicks = fuseTicks;
    }
    
    public EntityTNTPrimed getHandle() {
        return (EntityTNTPrimed)this.entity;
    }
    
    public String toString() {
        return "CraftTNTPrimed";
    }
    
    public EntityType getType() {
        return EntityType.PRIMED_TNT;
    }
    
    public Entity getSource() {
        final EntityLiving source = this.getHandle().getSource();
        if (source != null) {
            final Entity bukkitEntity = source.getBukkitEntity();
            if (bukkitEntity.isValid()) {
                return bukkitEntity;
            }
        }
        return null;
    }
}
