// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntityAgeable;
import net.minecraft.server.v1_5_R3.PathEntity;
import org.bukkit.entity.AnimalTamer;
import net.minecraft.server.v1_5_R3.EntityAnimal;
import net.minecraft.server.v1_5_R3.EntityTameableAnimal;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Tameable;

public class CraftTameableAnimal extends CraftAnimals implements Tameable, Creature
{
    public CraftTameableAnimal(final CraftServer server, final EntityTameableAnimal entity) {
        super(server, entity);
    }
    
    public EntityTameableAnimal getHandle() {
        return (EntityTameableAnimal)super.getHandle();
    }
    
    public AnimalTamer getOwner() {
        if ("".equals(this.getOwnerName())) {
            return null;
        }
        AnimalTamer owner = this.getServer().getPlayerExact(this.getOwnerName());
        if (owner == null) {
            owner = this.getServer().getOfflinePlayer(this.getOwnerName());
        }
        return owner;
    }
    
    public String getOwnerName() {
        return this.getHandle().getOwnerName();
    }
    
    public boolean isTamed() {
        return this.getHandle().isTamed();
    }
    
    public void setOwner(final AnimalTamer tamer) {
        if (tamer != null) {
            this.setTamed(true);
            this.getHandle().setPathEntity(null);
            this.setOwnerName(tamer.getName());
        }
        else {
            this.setTamed(false);
            this.setOwnerName("");
        }
    }
    
    public void setOwnerName(final String ownerName) {
        this.getHandle().setOwnerName((ownerName == null) ? "" : ownerName);
    }
    
    public void setTamed(final boolean tame) {
        this.getHandle().setTamed(tame);
        if (!tame) {
            this.setOwnerName("");
        }
    }
    
    public boolean isSitting() {
        return this.getHandle().isSitting();
    }
    
    public void setSitting(final boolean sitting) {
        this.getHandle().getGoalSit().setSitting(sitting);
    }
    
    public String toString() {
        return this.getClass().getSimpleName() + "{owner=" + this.getOwner() + ",tamed=" + this.isTamed() + "}";
    }
}
