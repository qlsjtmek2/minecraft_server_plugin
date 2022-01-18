// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import net.minecraft.server.v1_5_R3.EntityMonster;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityZombie;
import net.minecraft.server.v1_5_R3.EntityPigZombie;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.PigZombie;

public class CraftPigZombie extends CraftZombie implements PigZombie
{
    public CraftPigZombie(final CraftServer server, final EntityPigZombie entity) {
        super(server, entity);
    }
    
    public int getAnger() {
        return this.getHandle().angerLevel;
    }
    
    public void setAnger(final int level) {
        this.getHandle().angerLevel = level;
    }
    
    public void setAngry(final boolean angry) {
        this.setAnger(angry ? 400 : 0);
    }
    
    public boolean isAngry() {
        return this.getAnger() > 0;
    }
    
    public EntityPigZombie getHandle() {
        return (EntityPigZombie)this.entity;
    }
    
    public String toString() {
        return "CraftPigZombie";
    }
    
    public EntityType getType() {
        return EntityType.PIG_ZOMBIE;
    }
}
