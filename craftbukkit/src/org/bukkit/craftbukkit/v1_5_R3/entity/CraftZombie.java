// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityMonster;
import net.minecraft.server.v1_5_R3.EntityZombie;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Zombie;

public class CraftZombie extends CraftMonster implements Zombie
{
    public CraftZombie(final CraftServer server, final EntityZombie entity) {
        super(server, entity);
    }
    
    public EntityZombie getHandle() {
        return (EntityZombie)this.entity;
    }
    
    public String toString() {
        return "CraftZombie";
    }
    
    public EntityType getType() {
        return EntityType.ZOMBIE;
    }
    
    public boolean isBaby() {
        return this.getHandle().isBaby();
    }
    
    public void setBaby(final boolean flag) {
        this.getHandle().setBaby(flag);
    }
    
    public boolean isVillager() {
        return this.getHandle().isVillager();
    }
    
    public void setVillager(final boolean flag) {
        this.getHandle().setVillager(flag);
    }
}
