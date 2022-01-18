// 
// Decompiled by Procyon v0.5.30
// 

package org.bukkit.craftbukkit.v1_5_R3.entity;

import net.minecraft.server.v1_5_R3.Entity;
import net.minecraft.server.v1_5_R3.EntityLiving;
import net.minecraft.server.v1_5_R3.EntityCreature;
import org.bukkit.entity.EntityType;
import net.minecraft.server.v1_5_R3.EntityMonster;
import net.minecraft.server.v1_5_R3.EntityWitch;
import org.bukkit.craftbukkit.v1_5_R3.CraftServer;
import org.bukkit.entity.Witch;

public class CraftWitch extends CraftMonster implements Witch
{
    public CraftWitch(final CraftServer server, final EntityWitch entity) {
        super(server, entity);
    }
    
    public EntityWitch getHandle() {
        return (EntityWitch)this.entity;
    }
    
    public String toString() {
        return "CraftWitch";
    }
    
    public EntityType getType() {
        return EntityType.WITCH;
    }
}
