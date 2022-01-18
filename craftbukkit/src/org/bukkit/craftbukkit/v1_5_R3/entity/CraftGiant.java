// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityMonster;
import net.minecraft.server.v1_5_R3.EntityGiantZombie;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Giant;

public class CraftGiant extends CraftMonster implements Giant
{
    public CraftGiant(final CraftServer server, final EntityGiantZombie entity) {
        super(server, entity);
    }
    
    public EntityGiantZombie getHandle() {
        return (EntityGiantZombie)this.entity;
    }
    
    public String toString() {
        return "CraftGiant";
    }
    
    public EntityType getType() {
        return EntityType.GIANT;
    }
}
