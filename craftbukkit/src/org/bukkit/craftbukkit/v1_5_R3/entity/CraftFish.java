// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import org.apache.commons.lang.Validate;
import net.minecraft.server.v1_5_R3.MathHelper;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityHuman;
import org.bukkit.entity.LivingEntity;
import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityFishingHook;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Fish;

public class CraftFish extends AbstractProjectile implements Fish
{
    private double biteChance;
    
    public CraftFish(final CraftServer server, final EntityFishingHook entity) {
        super(server, entity);
        this.biteChance = -1.0;
    }
    
    public LivingEntity getShooter() {
        if (this.getHandle().owner != null) {
            return this.getHandle().owner.getBukkitEntity();
        }
        return null;
    }
    
    public void setShooter(final LivingEntity shooter) {
        if (shooter instanceof CraftHumanEntity) {
            this.getHandle().owner = (EntityHuman)((CraftHumanEntity)shooter).entity;
        }
    }
    
    public EntityFishingHook getHandle() {
        return (EntityFishingHook)this.entity;
    }
    
    public String toString() {
        return "CraftFish";
    }
    
    public EntityType getType() {
        return EntityType.FISHING_HOOK;
    }
    
    public double getBiteChance() {
        final EntityFishingHook hook = this.getHandle();
        if (this.biteChance != -1.0) {
            return this.biteChance;
        }
        if (hook.world.F(MathHelper.floor(hook.locX), MathHelper.floor(hook.locY) + 1, MathHelper.floor(hook.locZ))) {
            return 0.0033333333333333335;
        }
        return 0.002;
    }
    
    public void setBiteChance(final double chance) {
        Validate.isTrue(chance >= 0.0 && chance <= 1.0, "The bite chance must be between 0 and 1.");
        this.biteChance = chance;
    }
}
